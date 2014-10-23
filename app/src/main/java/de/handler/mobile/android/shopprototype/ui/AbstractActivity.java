package de.handler.mobile.android.shopprototype.ui;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EActivity;

import de.handler.mobile.android.shopprototype.R;
import de.handler.mobile.android.shopprototype.ShopApp;

/**
 * Abstract Activity implemented by all other activities
 */
@EActivity
public abstract class AbstractActivity extends ActionBarActivity {

    @App
    ShopApp app;

    //TODO: make app aware of internet connection state


    /**
     * ActionBar settings
     */
    public ActionBar setupActionBar() {

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();

        // make action bar transparent
        actionBar.setBackgroundDrawable(new ColorDrawable(R.color.fairmondo_blue_light));
        actionBar.setStackedBackgroundDrawable(new ColorDrawable(R.color.transparent_white_80));
        actionBar.setSplitBackgroundDrawable(new ColorDrawable(R.color.transparent_white_80));

        // title bar and icon
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        return actionBar;
    }
}
