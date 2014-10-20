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
import de.handler.mobile.android.shopprototype.database.Product;
import de.handler.mobile.android.shopprototype.util.CustomNetworkImageView;

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
                    "https://assets0.fairmondo.de/system/images/001/492/069/medium/dh201_plan_hw10_red_%281%29.jpg?1401609159",
                    app.getImageLoader());
            featuredProductsTitles.get(i).setText(products.get(i).getTitle());
        }
    }
}
