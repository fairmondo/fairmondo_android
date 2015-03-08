package de.handler.mobile.android.fairmondo.ui.activities;


import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import de.handler.mobile.android.fairmondo.R;
import de.handler.mobile.android.fairmondo.datalayer.businessobject.Product;
import de.handler.mobile.android.fairmondo.ui.adapter.ProductPagerAdapter;

/**
 * Displays a ViewPager with the products from the search so that
 * users can swipe in between the products
 */
@EActivity(R.layout.activity_gallery)
public class ProductGalleryActivity extends AbstractActivity {

    public static final String PAGER_POSITION_EXTRA = "pager_position_extra";
    public static final String PRODUCT_ARRAY_LIST_EXTRA = "product_array_list_extra";

    private ShareActionProvider mShareActionProvider;
    private ArrayList<Product> mProducts;


    @ViewById(R.id.activity_result_pager)
    ViewPager viewPager;


    @AfterViews
    public void init() {
        ActionBar actionBar = this.setupActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Get the bundle and the contained information
        // which was attached to the intent in either
        // ProductSelectionFragment or SearchResultFragment
        mProducts = getIntent().getParcelableArrayListExtra(PRODUCT_ARRAY_LIST_EXTRA);

        // The position is used for telling the ViewPager on which item the user touched
        // and which product therefore should be displayed in detail
        int position = getIntent().getIntExtra(PAGER_POSITION_EXTRA, 0);

        this.setupViewPager(position, mProducts);
    }


    private void setupViewPager(int position, ArrayList<Product> products) {
        ProductPagerAdapter productPagerAdapter = new ProductPagerAdapter(getSupportFragmentManager(), products);

        // Set up the ViewPager with the sections adapter.
        viewPager.setAdapter(productPagerAdapter);
        viewPager.setOffscreenPageLimit(5);
        viewPager.setCurrentItem(position, true);
    }


    /**
     * ActionBar settings
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.product, menu);

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
    private String buildSharingText(Product product) {
        return getString(R.string.share_text) +
                product.getHtmlUrl();
    }

    // Call to update the share intent
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    private Intent getDefaultIntent(String text) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");
        return sendIntent;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_settings:
                this.openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openSettings() {
        // TODO: to implement
    }
}
