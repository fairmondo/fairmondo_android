package de.handler.mobile.android.fairmondo.ui.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewsById;

import java.util.ArrayList;

import de.handler.mobile.android.fairmondo.FairmondoApp;
import de.handler.mobile.android.fairmondo.R;
import de.handler.mobile.android.fairmondo.datalayer.businessobject.Product;
import de.handler.mobile.android.fairmondo.ui.activities.WebActivity_;
import de.handler.mobile.android.fairmondo.ui.views.CustomNetworkImageView;

/**
 * Displays featured products when nothing is selected in category spinner
 */
@EFragment(R.layout.fragment_featured)
public class FeatureFragment extends Fragment implements View.OnClickListener {

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
    FairmondoApp app;


    private ArrayList<Product> mProducts = new ArrayList<>();

    @AfterViews
    public void init() {
         mProducts = getArguments().getParcelableArrayList(FEATURED_PRODUCTS_EXTRA);

        int i;
        if (mProducts.size() >= featuredProductsImageViews.size()) {
            i = 0;
        } else {
            i = featuredProductsImageViews.size() - mProducts.size();
        }

        for (; i < featuredProductsImageViews.size(); i++) {
            featuredProductsImageViews.get(i).setImageUrl(
                    mProducts.get(i).getTitleImage().getOriginalUrl(),
                    app.getImageLoader());
            featuredProductsImageViews.get(i).setOnClickListener(this);
            featuredProductsTitles.get(i).setText(mProducts.get(i).getTitle());
        }
    }


    @Override
    public void onClick(View v) {
        String url = "https://www.fairmondo.de";

        if (mProducts != null) {

            switch (v.getId()) {
                case R.id.fragment_feature_image_first:
                    if (mProducts.size() > 0) {
                        url = mProducts.get(0).getHtmlUrl();
                    }
                    break;
                case R.id.fragment_feature_image_second:
                    if (mProducts.size() > 1) {
                        url = mProducts.get(1).getHtmlUrl();
                    }
                    break;
                case R.id.fragment_feature_image_third:
                    if (mProducts.size() > 2) {
                        url = mProducts.get(2).getHtmlUrl();
                    }
                    break;
            }
        }

        Intent intent = new Intent(getActivity(), WebActivity_.class);
        intent.putExtra(WebFragment.URI, url);
        startActivity(intent);
    }
}
