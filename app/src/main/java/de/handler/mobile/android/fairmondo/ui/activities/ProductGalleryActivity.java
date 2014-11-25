package de.handler.mobile.android.fairmondo.ui.activities;


import android.content.res.Configuration;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import de.handler.mobile.android.fairmondo.R;
import de.handler.mobile.android.fairmondo.datasource.database.Product;
import de.handler.mobile.android.fairmondo.ui.adapter.ProductPagerAdapter;

/**
 * Displays a ViewPager with the products from the search
 */
@EActivity(R.layout.activity_gallery)
public class ProductGalleryActivity extends AbstractActivity {

    public static final String PAGER_POSITION_EXTRA = "pager_position_extra";
    public static final String PRODUCT_ARRAY_LIST_EXTRA = "product_array_list_extra";


    @ViewById(R.id.activity_result_pager)
    ViewPager viewPager;



    @AfterViews
    public void init() {
        ActionBar actionBar = this.setupActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ArrayList<Product> products = getIntent().getParcelableArrayListExtra(PRODUCT_ARRAY_LIST_EXTRA);
        int position = getIntent().getIntExtra(PAGER_POSITION_EXTRA, 0);

        this.setupViewPager(position, products);
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
        return true;
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

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
