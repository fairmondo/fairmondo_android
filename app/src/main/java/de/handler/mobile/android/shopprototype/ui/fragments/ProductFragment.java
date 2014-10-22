package de.handler.mobile.android.shopprototype.ui.fragments;


import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import de.handler.mobile.android.shopprototype.R;
import de.handler.mobile.android.shopprototype.ShopApp;
import de.handler.mobile.android.shopprototype.rest.json.Article;
import de.handler.mobile.android.shopprototype.util.Cart;
import de.handler.mobile.android.shopprototype.util.CustomNetworkImageView;
import de.handler.mobile.android.shopprototype.util.RoundNetworkImageView;

/**
 * Displays one product
 */
@EFragment(R.layout.fragment_product)
public class ProductFragment extends Fragment {

    public static final String PRODUCT_EXTRA = "product_extra";

    @App
    ShopApp app;

    @Bean
    Cart cart;

    @ViewById(R.id.fragment_product_image_view)
    CustomNetworkImageView productImageView;

    @ViewById(R.id.fragment_product_cart_add_image_button)
    RoundNetworkImageView addProductImageView;

    @ViewById(R.id.fragment_product_cart_remove_image_button)
    RoundNetworkImageView removeProductImageView;

    @ViewById(R.id.fragment_product_title)
    TextView titleTextView;

    @ViewById(R.id.fragment_product_description)
    TextView descriptionTextView;

    @ViewById(R.id.fragment_product_price)
    TextView priceTextView;

    private Article mProduct;


    @AfterViews
    public void init() {
        mProduct = getArguments().getParcelable(PRODUCT_EXTRA);

        addProductImageView.setImageUrl("https://raw.githubusercontent.com/fairmondo/fairmondo/develop/app/assets/images/old/buy.png", app.getImageLoader());
        removeProductImageView.setImageUrl("https://openclipart.org/image/100px/svg_to_png/26082/Anselmus_Green_Checkmark_and_Red_Minus_1.png", app.getImageLoader());

        if (mProduct != null) {
            // Create custom typeface
            Typeface myTypeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Thin.ttf");

            productImageView.setImageUrl(mProduct.getTitle_image_url(), app.getImageLoader());
            titleTextView.setText(mProduct.getTitle());
            descriptionTextView.setText(mProduct.getSlug());

            titleTextView.setTypeface(myTypeface);
            descriptionTextView.setTypeface(myTypeface);

            //TODO: also display cents
            priceTextView.setText(String.valueOf(mProduct.getPrice_cents() / 100) + " €");
            priceTextView.setTypeface(myTypeface);

            if (mProduct.getCount() > 0) {
                removeProductImageView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Click(R.id.fragment_product_cart_add_image_button)
    public void addToCart() {
        Toast.makeText(getActivity(), getString(R.string.fragment_product_added_to_cart), Toast.LENGTH_SHORT).show();

        if (cart.getArticles().containsKey(mProduct.getId().toString())) {
            mProduct.setCount(mProduct.getCount() + 1);
        } else {
            cart.addArticle(mProduct.getId().toString(), mProduct);
            mProduct.setCount(1);
        }

        if (mProduct.getCount() >= 1) {
            removeProductImageView.setVisibility(View.VISIBLE);
        }
    }

    @Click(R.id.fragment_product_cart_remove_image_button)
    public void removeFromCart() {
        Toast.makeText(getActivity(), getString(R.string.fragment_product_removed_from_cart), Toast.LENGTH_SHORT).show();

        if (mProduct.getCount() >= 1) {
            mProduct.setCount(mProduct.getCount() - 1);
            if (mProduct.getCount() == 0) {
                removeProductImageView.setVisibility(View.GONE);
                cart.removeArticle(mProduct.getId().toString());
            }
        }
    }
}
