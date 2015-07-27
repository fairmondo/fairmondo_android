package de.handler.mobile.android.fairmondo.presentation.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import de.handler.mobile.android.fairmondo.presentation.fragments.WebFragment_;

/**
 * A pager for web pages.
 */
public class WebPager extends FragmentStatePagerAdapter {
    private List<String> mURLs;

    /**
     * Creates an instance of the WebPager.
     */
    public WebPager(@NonNull final FragmentManager fm) {
        super(fm);
        mURLs = new ArrayList<>();
    }

    /**
     * Creates an instance of the WebPager.
     */
    public WebPager(@NonNull final FragmentManager childFragmentManager, @NonNull final List<String> urls) {
        super(childFragmentManager);
        mURLs = new ArrayList<>();
        mURLs = urls;
    }

    /**
     * Adds urls to the viewPager.
     */
    public void addItems(@Nullable final List<String> urls) {
        mURLs = urls;
    }

    @Override
    public Fragment getItem(final int position) {
        return WebFragment_.builder().mUri(mURLs.get(position)).build();
    }

    @Override
    public int getItemPosition(final Object object) {
        // POSITION_NONE makes it possible to reload the PagerAdapter
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return mURLs.size();
    }
}
