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

import java.util.ArrayList;
import java.util.HashMap;

import de.handler.mobile.android.shopprototype.R;
import de.handler.mobile.android.shopprototype.rest.json.Article;
import de.handler.mobile.android.shopprototype.ui.fragments.ProductSelectionFragment;
import de.handler.mobile.android.shopprototype.ui.fragments.ProductSelectionFragment_;
import de.handler.mobile.android.shopprototype.util.Cart;

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
    }


    @Override
    protected void onResume() {
        super.onResume();
        this.startSelectionFragment();
    }

    private void startSelectionFragment() {
        ProductSelectionFragment selectionFragment = new ProductSelectionFragment_();

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(
                ProductSelectionFragment.SELECTION_ARRAY_LIST_EXTRA,
                this.convertHashmapToList(cart.getArticles()));
        selectionFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.activity_cart_products_container, selectionFragment)
                .commit();

        if (cart.getArticles().size() < 1) {
            this.finish();
        }
    }


    private ArrayList<Article> convertHashmapToList(HashMap<String, Article> articles) {
        ArrayList<Article> arrayList = new ArrayList<Article>(articles.size());
        arrayList.addAll(articles.values());
        return arrayList;
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
