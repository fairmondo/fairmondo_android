package de.handler.mobile.android.shopprototype.ui.fragments;

import android.support.v4.app.Fragment;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewsById;

import java.util.ArrayList;

import de.handler.mobile.android.shopprototype.R;
import de.handler.mobile.android.shopprototype.ShopApp;
import de.handler.mobile.android.shopprototype.models.Product;
import de.handler.mobile.android.shopprototype.utils.CustomNetworkImageView;

/**
 * Displays featured products when nothing is selected in category spinner
 */
@EFragment(R.layout.fragment_featured)
public class FeatureFragment extends Fragment {

    public static final String FEATURED_PRODUCTS_EXTRA = "featured_product_parcelable_array_list_extra";


    @ViewsById({R.id.fragment_feature_image_first,
            R.id.fragment_feature_image_second,
            R.id.fragment_feature_image_third})
    ArrayList<CustomNetworkImageView> featuredProductsImageViews;

    @ViewsById({R.id.fragment_feature_text_first,
            R.id.fragment_feature_text_second,
            R.id.fragment_feature_text_third})
    ArrayList<TextView> featuredProductsTitles;

    @App
    ShopApp app;

    @AfterViews
    public void init() {
        ArrayList<Product> products = getArguments().getParcelableArrayList(FEATURED_PRODUCTS_EXTRA);

        for (int i = 0; i < featuredProductsImageViews.size(); i++) {
            featuredProductsImageViews.get(i).setImageUrl(
                    products.get(i).getImageUrl(),
                    app.getImageLoader());
            featuredProductsTitles.get(i).setText(products.get(i).getTitle());
        }
    }
}
