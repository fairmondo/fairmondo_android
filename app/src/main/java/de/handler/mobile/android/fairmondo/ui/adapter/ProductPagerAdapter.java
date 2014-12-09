package de.handler.mobile.android.fairmondo.ui.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import de.handler.mobile.android.fairmondo.rest.json.Article;
import de.handler.mobile.android.fairmondo.ui.fragments.ProductFragment;
import de.handler.mobile.android.fairmondo.ui.fragments.ProductFragment_;

/**
 * Adapter for Products ViewPager
 */
public class ProductPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Article> mProducts;

    public ProductPagerAdapter(FragmentManager fm, ArrayList<Article> fragments) {
        super(fm);

        mProducts = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        ProductFragment fragment = new ProductFragment_();

        Bundle bundle = new Bundle();
        bundle.putParcelable(ProductFragment.PRODUCT_EXTRA, mProducts.get(position));
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public int getItemPosition(Object object) {
        // POSITION_NONE makes it possible to reload the PagerAdapter
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return mProducts.size();
    }
}
