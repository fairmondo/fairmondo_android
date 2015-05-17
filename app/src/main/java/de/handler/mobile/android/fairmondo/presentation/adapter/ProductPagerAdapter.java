package de.handler.mobile.android.fairmondo.presentation.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.parceler.Parcels;

import java.util.List;

import de.handler.mobile.android.fairmondo.data.businessobject.Product;
import de.handler.mobile.android.fairmondo.presentation.fragments.ProductFragment;
import de.handler.mobile.android.fairmondo.presentation.fragments.ProductFragment_;

/**
 * Adapter for Products ViewPager
 */
public class ProductPagerAdapter extends FragmentStatePagerAdapter {
    private final List<Product> mProducts;

    public ProductPagerAdapter(final FragmentManager fm, final List<Product> fragments) {
        super(fm);
        mProducts = fragments;
    }

    @Override
    public Fragment getItem(final int position) {
        //TODO change back as soon as the right content is there
        ProductFragment fragment = new ProductFragment_();

        Bundle bundle = new Bundle();
        bundle.putParcelable(ProductFragment.PRODUCT_EXTRA, Parcels.wrap(mProducts.get(position)));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getItemPosition(final Object object) {
        // POSITION_NONE makes it possible to reload the PagerAdapter
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return mProducts.size();
    }
}
