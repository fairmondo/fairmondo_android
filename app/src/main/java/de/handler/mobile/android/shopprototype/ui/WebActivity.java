package de.handler.mobile.android.shopprototype.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EActivity;

import de.handler.mobile.android.shopprototype.R;
import de.handler.mobile.android.shopprototype.ShopApp;
import de.handler.mobile.android.shopprototype.ui.fragments.WebFragment;
import de.handler.mobile.android.shopprototype.ui.fragments.WebFragment_;

/**
 * Contains a webView for displaying homepages
 */
@EActivity(R.layout.activity_web)
public class WebActivity extends AbstractActivity {

    @App
    ShopApp app;

    @AfterViews
    void init() {
        ActionBar actionBar = this.setupActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        String http = getIntent().getStringExtra(WebFragment.HTTP_CONTENT);
        String uri = getIntent().getStringExtra(WebFragment.URI);
        String cookie = getIntent().getStringExtra(WebFragment.COOKIE);

        Bundle bundle = new Bundle();
        bundle.putString(WebFragment.URI, uri);
        bundle.putString(WebFragment.HTTP_CONTENT, http);
        bundle.putString(WebFragment.COOKIE, cookie);

        WebFragment webFragment = new WebFragment_();
        webFragment.setArguments(bundle);


        if ((uri != null && app.isConnected()) || http != null) {

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_web_container, webFragment)
                    .commit();
        } else {
            this.finish();
        }
    }



}
