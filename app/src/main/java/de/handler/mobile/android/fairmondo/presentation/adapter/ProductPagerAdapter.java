package de.handler.mobile.android.fairmondo.presentation.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.parceler.Parcels;

import java.util.List;

import de.handler.mobile.android.fairmondo.data.businessobject.Product;
import de.handler.mobile.android.fairmondo.presentation.fragments.ProductFragment_;

/**
 * Adapter for Products ViewPager
 */
public class ProductPagerAdapter extends FragmentStatePagerAdapter {
    private final List<Product> mProducts;

    /**
     * Creates an instance of the ProductPagerAdapter.
     */
    public ProductPagerAdapter(@NonNull final FragmentManager fm, @NonNull final List<Product> products) {
        super(fm);
        mProducts = products;
    }

    @Override
    public Fragment getItem(final int position) {
        return ProductFragment_.builder().mProductParcelable(Parcels.wrap(mProducts.get(position))).build();
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
