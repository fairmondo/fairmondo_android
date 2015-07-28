package de.handler.mobile.android.fairmondo.presentation.fragments;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;
import org.parceler.Parcels;

import java.util.List;

import de.handler.mobile.android.fairmondo.FairmondoApp;
import de.handler.mobile.android.fairmondo.R;
import de.handler.mobile.android.fairmondo.presentation.adapter.WebPager;

/**
 * Displays featured products when nothing is selected in category spinner.
 */
@EFragment(R.layout.fragment_featured)
public class FeatureFragment extends Fragment {
    @FragmentArg
    Parcelable mUrlArrayList;

    @ViewById(R.id.fragment_feature_view_pager)
    ViewPager mViewPager;

    @App
    FairmondoApp mApp;

    @AfterViews
    public void init() {
        final List<String> urls = Parcels.unwrap(mUrlArrayList);
        mViewPager.setAdapter(new WebPager(getChildFragmentManager(), urls));
    }
}
