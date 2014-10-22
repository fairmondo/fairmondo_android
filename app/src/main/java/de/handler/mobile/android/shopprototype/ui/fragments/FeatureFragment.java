package de.handler.mobile.android.shopprototype.ui.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewsById;

import java.util.ArrayList;

import de.handler.mobile.android.shopprototype.R;
import de.handler.mobile.android.shopprototype.ShopApp;
import de.handler.mobile.android.shopprototype.rest.json.Article;
import de.handler.mobile.android.shopprototype.ui.WebActivity;
import de.handler.mobile.android.shopprototype.ui.WebActivity_;
import de.handler.mobile.android.shopprototype.util.CustomNetworkImageView;

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
    ShopApp app;

    @AfterViews
    public void init() {
        ArrayList<Article> products = getArguments().getParcelableArrayList(FEATURED_PRODUCTS_EXTRA);

        int i;
        if (products.size() >= featuredProductsImageViews.size()) {
            i = 0;
        } else {
            i = featuredProductsImageViews.size() - products.size();
        }

        for (; i < featuredProductsImageViews.size(); i++) {
            featuredProductsImageViews.get(i).setImageUrl(
                    products.get(i).getTitle_image_url(),
                    app.getImageLoader());
            featuredProductsImageViews.get(i).setOnClickListener(this);
            featuredProductsTitles.get(i).setText(products.get(i).getTitle());
        }
    }


    @Override
    public void onClick(View v) {
        String url = "https://www.fairmondo.de";

        switch (v.getId()) {
            case R.id.fragment_feature_image_first:
                url ="https://www.fairmondo.de/categories/bucher";
                break;
            case R.id.fragment_feature_image_second:
                url = "https://www.fairmondo.de/users/coffee-circle?pk_campaign=billboards&pk_kwd=kaffee";
                break;
            case R.id.fragment_feature_image_third:
                url = "https://www.fairmondo.de/libraries/6867?pk_campaign=billboards&pk_kwd=entdecken_unterstuetzen";
                break;
        }

        Intent intent = new Intent(getActivity(), WebActivity_.class);
        intent.putExtra(WebActivity.URI, url);
        startActivity(intent);
    }
}
