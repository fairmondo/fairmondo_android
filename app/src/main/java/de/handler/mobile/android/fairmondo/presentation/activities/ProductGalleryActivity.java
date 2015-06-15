package de.handler.mobile.android.fairmondo.presentation.activities;


import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
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

    private ShareActionProvider mShareActionProvider;
    private ArrayList<Product> mProducts;

    @App
    FairmondoApp app;

    @ViewById(R.id.activity_result_pager)
    ViewPager viewPager;

    @ViewById(R.id.activity_gallery_toolbar)
    Toolbar toolbar;

    @AfterViews
    void init() {
        ActionBar actionBar = this.setupActionBar(toolbar);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mProducts = Parcels.unwrap(mProductsParcelable);
        this.setupViewPager(mPosition, mProducts);
    }

    private void setupViewPager(final int position, final List<Product> products) {
        ProductPagerAdapter productPagerAdapter = new ProductPagerAdapter(getSupportFragmentManager(), products);

        // Set up the ViewPager with the sections adapter.
        viewPager.setAdapter(productPagerAdapter);
        viewPager.setCurrentItem(position, true);
    }

    /**
     * ActionBar settings.
     */
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Locate MenuItem with ShareActionProvider
        MenuItem menuItem = menu.findItem(R.id.menu_item_share);
        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        String text = this.buildSharingText(mProducts.get(viewPager.getCurrentItem()));
        this.setShareIntent(this.getDefaultIntent(text));
        return true;
    }

    // Build the string which is used to share a fairmondo
    // product recommendation with other android apps
    private String buildSharingText(final Product product) {
        return getString(R.string.share_text) + product.getHtmlUrl();
    }

    // Call to update the share intent
    private void setShareIntent(final Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    private Intent getDefaultIntent(final String text) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");
        return sendIntent;
    }

    @OptionsItem(R.id.action_settings)
    void openSettings() {
        // TODO: to implement
    }
}
