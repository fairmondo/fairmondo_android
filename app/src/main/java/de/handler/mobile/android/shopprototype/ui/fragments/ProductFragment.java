package de.handler.mobile.android.shopprototype.ui.fragments;


import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import de.handler.mobile.android.shopprototype.R;
import de.handler.mobile.android.shopprototype.ShopApp;
import de.handler.mobile.android.shopprototype.interfaces.OnCartChangeListener;
import de.handler.mobile.android.shopprototype.rest.RestController;
import de.handler.mobile.android.shopprototype.rest.json.Article;
import de.handler.mobile.android.shopprototype.rest.json.model.Cart;
import de.handler.mobile.android.shopprototype.ui.WebActivity_;
import de.handler.mobile.android.shopprototype.util.CustomNetworkImageView;

/**
 * Displays one product
 */
@EFragment(R.layout.fragment_product)
public class ProductFragment extends Fragment implements OnCartChangeListener, View.OnClickListener {

    public static final String PRODUCT_EXTRA = "product_extra";

    @App
    ShopApp app;

    @Bean
    RestController restController;

    @ViewById(R.id.fragment_product_image_view)
    CustomNetworkImageView productImageView;

    @ViewById(R.id.fragment_product_item_count_text_view)
    TextView itemCountTextView;

    @ViewById(R.id.fragment_product_title)
    TextView titleTextView;

    @ViewById(R.id.fragment_product_price)
    TextView priceTextView;
    
    @ViewById(R.id.fragment_product_button_description)
    Button buttonDescription;

    @ViewById(R.id.fragment_product_button_fair_percent)
    Button buttonFairPercent;

    @ViewById(R.id.fragment_product_button_terms)
    Button buttonTerms;

    private Article mProduct;


    @AfterViews
    public void init() {
        restController.setCartChangeListener(this);

        mProduct = getArguments().getParcelable(PRODUCT_EXTRA);
        if (mProduct != null) {
            // Create custom typeface
            Typeface myTypeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Thin.ttf");

            titleTextView.setText(mProduct.getTitle());
            titleTextView.setTypeface(myTypeface);
            priceTextView.setTypeface(myTypeface);

            productImageView.setImageUrl(mProduct.getTitle_image_url(), app.getImageLoader());
            if (mProduct.getTitle_image() != null) {
                productImageView.setImageUrl(mProduct.getTitle_image().getOriginal_url(), app.getImageLoader());
            }

            //TODO not yet working
            productImageView.buildDrawingCache(true);
            if (productImageView.getDrawingCache() != null) {
                Palette.generateAsync(productImageView.getDrawingCache(), new Palette.PaletteAsyncListener() {
                    public void onGenerated(Palette palette) {
                        int color = palette.getLightMutedColor(android.R.color.transparent);
                        productImageView.setBackgroundColor(color);
                    }
                });
            }

            StringBuilder price = new StringBuilder(
                    String.valueOf(mProduct.getPrice_cents()));

            if (price.length() < 3) {
                if (price.length() < 2) {
                    price.insert(0, '0');
                }
                price.insert(0, '0');
            }

            price = price.reverse().insert(2, ',').reverse();
            priceTextView.setText(price + " €");

            buttonDescription.setOnClickListener(this);
            buttonTerms.setOnClickListener(this);
            buttonFairPercent.setOnClickListener(this);

            if (mProduct.getContent_html() == null) {
                buttonDescription.setVisibility(View.GONE);
            }
            if (mProduct.getTerms_html() == null) {
                buttonTerms.setVisibility(View.GONE);
            }
            if (mProduct.getFair_percent_html() == null) {
                buttonFairPercent.setVisibility(View.GONE);
            }
        }


    }

    @Click(R.id.fragment_product_button_buy)
    public void addToCart() {
        restController.addToCard(mProduct.getId());
    }

    @UiThread
    @Override
    public void onCartChanged(Cart cart) {
        if (cart != null && cart.getLine_item() != null) {
            int itemCount = cart.getLine_item().getRequested_quantity();
            itemCountTextView.setText(String.valueOf(itemCount));
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), WebActivity_.class);

        switch (v.getId()) {
            case R.id.fragment_product_button_description:
                intent.putExtra(WebFragment.HTTP_CONTENT, mProduct.getContent_html());
                break;
            case R.id.fragment_product_button_terms:
                intent.putExtra(WebFragment.HTTP_CONTENT, mProduct.getTerms_html());
                break;
            case R.id.fragment_product_button_fair_percent:
                intent.putExtra(WebFragment.HTTP_CONTENT, mProduct.getFair_percent_html());
                break;
        }

        startActivity(intent);
    }
}
