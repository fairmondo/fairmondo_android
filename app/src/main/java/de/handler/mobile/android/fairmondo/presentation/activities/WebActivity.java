package de.handler.mobile.android.fairmondo.presentation.activities;

import android.support.v7.widget.Toolbar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import de.handler.mobile.android.fairmondo.FairmondoApp;
import de.handler.mobile.android.fairmondo.R;
import de.handler.mobile.android.fairmondo.presentation.FragmentHelper;
import de.handler.mobile.android.fairmondo.presentation.fragments.WebFragment;
import de.handler.mobile.android.fairmondo.presentation.fragments.WebFragment_;

/**
 * Contains a webView for displaying homepage content.
 */
@EActivity(R.layout.activity_web)
 public class WebActivity extends AbstractActivity {
    @App
    FairmondoApp mApp;

    @Extra
    String mHtml;

    @Extra
    String mUri;

    @Extra
    String mCookie;

    @ViewById(R.id.activity_web_toolbar)
    Toolbar mToolbar;

    @AfterViews
    void init() {
        setHomeUpEnabled(mToolbar);

        if ((mUri != null && mApp.isConnected()) || mHtml != null) {
            final WebFragment webFragment = WebFragment_.builder().mHtml(mHtml).mUri(mUri).mCookie(mCookie).build();
            FragmentHelper.replaceFragmentWithTag(R.id.activity_web_container, webFragment, getSupportFragmentManager(), "webfragment");
        } else {
            finish();
        }
    }

    @OptionsItem(android.R.id.home)
    void home() {
        finish();
    }
}
