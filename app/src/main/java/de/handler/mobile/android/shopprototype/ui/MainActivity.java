package de.handler.mobile.android.shopprototype.ui;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;

import de.handler.mobile.android.shopprototype.R;
import de.handler.mobile.android.shopprototype.ShopApp;
import de.handler.mobile.android.shopprototype.interfaces.OnCategoriesListener;
import de.handler.mobile.android.shopprototype.ui.fragments.CategoryFragment;
import de.handler.mobile.android.shopprototype.ui.fragments.CategoryFragment_;
import de.handler.mobile.android.shopprototype.ui.fragments.TitleFragment;
import de.handler.mobile.android.shopprototype.ui.fragments.TitleFragment_;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

@EActivity(R.layout.activity_main)
public class MainActivity extends AbstractActivity implements OnCategoriesListener {

    @App
    ShopApp app;

    @SystemService
    SearchManager searchManager;

    @ViewById(R.id.main_progress_bar)
    ProgressBar progressBar;


    @AfterInject
    public void overlayActionBar() {
        // Request Action Bar overlay before setting content view a.k.a. before @AfterViews
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
    }

    @AfterViews
    public void init() {
        this.setupActionBar();
        this.initTitleFragment();
        this.getCategories();
        this.getFakeCategories();
    }


    private void initTitleFragment() {
        TitleFragment titleFragment = new TitleFragment_();
        Bundle bundle = new Bundle();
        bundle.putString(TitleFragment.IMAGE_URL_STRING_EXTRA, "http://mitmachen.fairmondo.de/wp-content/uploads/2014/09/Profilfoto_Google.jpg");
        bundle.putString(TitleFragment.IMAGE_DESCRIPTION_STRING_EXTRA, getString(R.string.fairmondo_slogan));
        titleFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_title_container, titleFragment)
                .commit();
    }


    @Background
    public void getCategories() {
        progressBar.setVisibility(View.VISIBLE);
        // TODO get categories from server
    }

    private void getFakeCategories() {
        ArrayList<String> categories = new ArrayList<String>();
        categories.add("Kassetten");
        categories.add("Hormonpräparate");
        categories.add("Sendung mit der Maus");
        categories.add("Schallplatten");
        categories.add("Elektronik");
        categories.add("Hängematten");
        categories.add("Kinderspielzeug");
        categories.add("Arzneimittel");
        categories.add("Dienstleistungen");
        categories.add("Antiqitäten & Kunst");
        categories.add("Fahrzeuge");
        categories.add("Beauty");
        categories.add("Briefmarken");
        categories.add("Bücher");
        categories.add("Schreibwaren");
        categories.add("Feinschmecker");
        categories.add("Filme");
        categories.add("Garten");
        categories.add("Haustier");
        categories.add("Schmuck");

        this.onCategoriesResponse(categories);
    }


    @Override
    public void onCategoriesResponse(ArrayList<String> categories) {
        progressBar.setVisibility(View.GONE);

        CategoryFragment categoryFragment = new CategoryFragment_();

        Bundle bundle = new Bundle();
        bundle.putStringArrayList(CategoryFragment.CATEGORY_ARRAY_LIST_EXTRA, categories);
        categoryFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_categories_container, categoryFragment)
                .commit();
    }


    /**
     * ActionBar settings
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        //TODO: implement search suggestions for products --> https://developer.android.com/guide/topics/search/adding-custom-suggestions.html
        // Get the SearchView and set the searchable configuration
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(
                new ComponentName(this, SearchableActivity_.class)));
        searchView.setIconifiedByDefault(true); // Do not iconify the widget; expand it by default

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
