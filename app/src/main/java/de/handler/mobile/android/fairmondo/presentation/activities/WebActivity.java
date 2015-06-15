package de.handler.mobile.android.fairmondo.presentation.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import de.handler.mobile.android.fairmondo.FairmondoApp;
import de.handler.mobile.android.fairmondo.R;
import de.handler.mobile.android.fairmondo.presentation.fragments.WebFragment;
import de.handler.mobile.android.fairmondo.presentation.fragments.WebFragment_;

/**
 * Contains a webView for displaying homepage content.
 */
@EActivity(R.layout.activity_web)
@OptionsMenu(R.menu.web)
public class WebActivity extends AbstractActivity {
    @App
    FairmondoApp app;

    @Extra
    String mHtml;

    @Extra
    String mUri;

    @Extra
    String mCookie;

    @ViewById(R.id.activity_web_toolbar)
    Toolbar toolbar;

    @AfterViews
    void init() {
        ActionBar actionBar = this.setupActionBar(toolbar);
        actionBar.setDisplayHomeAsUpEnabled(true);

        WebFragment webFragment = WebFragment_.builder().mHtml(mHtml).mUri(mUri).mCookie(mCookie).build();
        if ((mUri != null && app.isConnected()) || mHtml != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_web_container, webFragment)
                    .commit();
        } else {
            this.finish();
        }
    }
}
