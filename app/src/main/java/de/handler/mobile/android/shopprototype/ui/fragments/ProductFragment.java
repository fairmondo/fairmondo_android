package de.handler.mobile.android.shopprototype.ui.fragments;


import android.support.v4.app.Fragment;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import de.handler.mobile.android.shopprototype.R;
import de.handler.mobile.android.shopprototype.ShopApp;
import de.handler.mobile.android.shopprototype.models.Product;
import de.handler.mobile.android.shopprototype.utils.CustomNetworkImageView;

/**
 * Displays one product
 */
@EFragment(R.layout.fragment_product)
public class ProductFragment extends Fragment {

    public static final String PRODUCT_EXTRA = "product_extra";

    @App
    ShopApp app;

    @ViewById(R.id.fragment_product_image_view)
    CustomNetworkImageView productImageView;

    @ViewById(R.id.fragment_product_title)
    TextView titleTextView;

    @ViewById(R.id.fragment_product_description)
    TextView descriptionTextView;

    @ViewById(R.id.fragment_product_price)
    TextView priceTextView;


    @AfterViews
    public void init() {
        Product product = getArguments().getParcelable(PRODUCT_EXTRA);

        if (product != null) {
            productImageView.setImageUrl(product.getImageUrl(), app.getImageLoader());
            titleTextView.setText(product.getTitle());
            descriptionTextView.setText(product.getDescription());
            priceTextView.setText(String.valueOf(product.getPrice()) + " €");
        }
    }
}
