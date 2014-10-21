package de.handler.mobile.android.shopprototype.ui;


import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import de.handler.mobile.android.shopprototype.R;
import de.handler.mobile.android.shopprototype.database.Product;
import de.handler.mobile.android.shopprototype.ui.adapter.ProductPagerAdapter;

/**
 * Displays a ViewPager with the products from the search
 */
@EActivity(R.layout.activity_gallery)
public class ProductGalleryActivity extends AbstractActivity {

    public static final String PAGER_POSITION_EXTRA = "pager_position_extra";
    public static final String PRODUCT_ARRAY_LIST_EXTRA = "product_array_list_extra";


    @ViewById(R.id.activity_result_pager)
    ViewPager viewPager;


    @AfterInject
    public void overlayActionBar() {
        // Request Action Bar overlay before setting content view a.k.a. before @AfterViews
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
    }


    @AfterViews
    public void init() {
        this.setupActionBar();

        ArrayList<Product> products = getIntent().getParcelableArrayListExtra(PRODUCT_ARRAY_LIST_EXTRA);
        int position = getIntent().getIntExtra(PAGER_POSITION_EXTRA, 0);

        this.setupViewPager(position, products);
    }



    private void setupViewPager(int position, ArrayList<Product> products) {
        ProductPagerAdapter productPagerAdapter = new ProductPagerAdapter(getSupportFragmentManager(), products);

        // Set up the ViewPager with the sections adapter.
        viewPager.setAdapter(productPagerAdapter);
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
}
