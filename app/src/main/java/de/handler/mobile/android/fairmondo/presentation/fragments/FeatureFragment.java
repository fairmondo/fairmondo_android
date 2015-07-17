package de.handler.mobile.android.fairmondo.presentation.fragments;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewsById;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import de.handler.mobile.android.fairmondo.FairmondoApp;
import de.handler.mobile.android.fairmondo.R;
import de.handler.mobile.android.fairmondo.data.businessobject.Product;
import de.handler.mobile.android.fairmondo.presentation.activities.WebActivity_;
import de.handler.mobile.android.fairmondo.presentation.views.CustomNetworkImageView;

/**
 * Displays featured products when nothing is selected in category spinner.
 */
@EFragment(R.layout.fragment_featured)
public class FeatureFragment extends Fragment implements View.OnClickListener {
    @FragmentArg
    Parcelable mProductsParcelable;

    @ViewsById({R.id.fragment_feature_image_first, R.id.fragment_feature_image_second, R.id.fragment_feature_image_third})
    List<CustomNetworkImageView> mFeaturedProductsImageViews;

    @ViewsById({R.id.fragment_feature_text_first, R.id.fragment_feature_text_second, R.id.fragment_feature_text_third})
    List<TextView> mFeaturedProductsTitles;

    @App
    FairmondoApp mApp;

    private ArrayList<Product> mProducts = new ArrayList<>();

    @AfterViews
    public void init() {
         mProducts = Parcels.unwrap(mProductsParcelable);

        int i;
        if (mProducts.size() >= mFeaturedProductsImageViews.size()) {
            i = 0;
        } else {
            i = mFeaturedProductsImageViews.size() - mProducts.size();
        }

        for (; i < mFeaturedProductsImageViews.size(); i++) {
            mFeaturedProductsImageViews.get(i).setImageUrl(
                    mProducts.get(i).getTitleImage().getOriginalUrl(),
                    mApp.getImageLoader());
            mFeaturedProductsImageViews.get(i).setOnClickListener(this);
            mFeaturedProductsTitles.get(i).setText(mProducts.get(i).getTitle());
        }
    }

    @Override
    public void onClick(final View v) {
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
                default:
                    // Nothing happening here
            }
        }

        WebActivity_.intent(getActivity()).mUri(url).start();
    }
}
