package de.handler.mobile.android.shopprototype.ui.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import de.handler.mobile.android.shopprototype.database.Product;
import de.handler.mobile.android.shopprototype.ui.fragments.ProductFragment;
import de.handler.mobile.android.shopprototype.ui.fragments.ProductFragment_;

/**
 * Adapter for Products ViewPager
 */
public class ProductPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Product> mProducts;

    public ProductPagerAdapter(FragmentManager fm, ArrayList<Product> fragments) {
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
