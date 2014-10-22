package de.handler.mobile.android.shopprototype.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.LinearLayout;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import de.handler.mobile.android.shopprototype.R;
import de.handler.mobile.android.shopprototype.rest.json.model.Cart;
import de.handler.mobile.android.shopprototype.ui.fragments.ProductSelectionFragment;
import de.handler.mobile.android.shopprototype.ui.fragments.ProductSelectionFragment_;

/**
 * Shows Views related to card transaction
 */
@EActivity(R.layout.activity_cart)
public class CartActivity extends AbstractActivity {

    @Bean
    Cart cart;

    @ViewById(R.id.activity_cart_products_container)
    LinearLayout productsContainer;


    @AfterInject
    public void overlayActionBar() {
        // Request Action Bar overlay before setting content view a.k.a. before @AfterViews
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
    }

    @AfterViews
    public void init() {
        this.setupActionBar();
        ProductSelectionFragment selectionFragment = new ProductSelectionFragment_();

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ProductSelectionFragment.SELECTION_ARRAY_LIST_EXTRA, cart.getArticles());
        selectionFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_cart_products_container, selectionFragment)
                .commit();
    }

    /**
     * ActionBar settings
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_send:
                Intent intent = new Intent(this, WebActivity_.class);
                intent.putExtra(WebActivity.URI, "https://www.fairmondo.de/carts/637/edit");
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
