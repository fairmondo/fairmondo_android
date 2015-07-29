package de.handler.mobile.android.fairmondo.presentation.activities;


import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import de.handler.mobile.android.fairmondo.FairmondoApp;
import de.handler.mobile.android.fairmondo.R;
import de.handler.mobile.android.fairmondo.data.businessobject.Product;
import de.handler.mobile.android.fairmondo.presentation.adapter.ProductPagerAdapter;

/**
 * Displays a ViewPager with the products from the search so that.
 * users can swipe in between the products
 */
@EActivity(R.layout.activity_gallery)
@OptionsMenu(R.menu.product)
public class ProductGalleryActivity extends AbstractActivity {
    @Extra
    int mPosition;

    @Extra
    Parcelable mProductsParcelable;

    @App
    FairmondoApp mApp;

    @ViewById(R.id.activity_result_pager)
    ViewPager mViewPager;

    @ViewById(R.id.activity_gallery_toolbar)
    Toolbar mToolbar;

    private ShareActionProvider mShareActionProvider;
    private ArrayList<Product> mProducts;


    @AfterViews
    void init() {
        setHomeUpEnabled(mToolbar);

        mProducts = Parcels.unwrap(mProductsParcelable);
        this.setupViewPager(mPosition, mProducts);
    }

    private void setupViewPager(@NonNull final Integer position, @NonNull final List<Product> products) {
        final ProductPagerAdapter productPagerAdapter = new ProductPagerAdapter(getSupportFragmentManager(), products);

        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(productPagerAdapter);
        mViewPager.setCurrentItem(position, true);
    }

    /**
     * ActionBar settings.
     */
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Locate MenuItem with ShareActionProvider
        final MenuItem menuItem = menu.findItem(R.id.menu_item_share);
        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        final String text = this.buildSharingText(mProducts.get(mViewPager.getCurrentItem()));
        this.setShareIntent(this.getDefaultIntent(text));
        return true;
    }

    // Build the string which is used to share a fairmondo
    // product recommendation with other android apps
    private String buildSharingText(final Product product) {
        return getString(R.string.text_share) + getString(R.string.base_url) + product.getSlug();
    }

    // Call to update the share intent
    private void setShareIntent(final Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    private Intent getDefaultIntent(final String text) {
        final Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");
        return sendIntent;
    }
}
