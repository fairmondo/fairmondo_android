package de.handler.mobile.android.fairmondo.presentation.activities;

import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Receiver;
import org.androidannotations.annotations.SystemService;

import de.handler.mobile.android.fairmondo.FairmondoApp;
import de.handler.mobile.android.fairmondo.R;

/**
 * Abstract Activity implemented by all other activities.
 */
@EActivity
public abstract class AbstractActivity extends ActionBarActivity {
    @SystemService
    ConnectivityManager connectivityManager;

    /**
     * Network receiver.
     * Checks if connectivity changes and reacts to the event
     * */
    @Receiver(actions = ConnectivityManager.CONNECTIVITY_ACTION, registerAt = Receiver.RegisterAt.OnResumeOnPause)
    @Background
    void onConnectionChange() {
        this.checkNetworkState();
        Log.i("FAIRMONDO_APP ", "NETWORKSTATECHANGE");
    }

    public void checkNetworkState() {
        FairmondoApp app = (FairmondoApp) getApplicationContext();
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if (activeNetwork != null) {
            app.setConnected(activeNetwork.isConnected());
        } else {
            app.setConnected(false);
        }
    }

    /**
     * ActionBar settings.
     */
    public ActionBar setupActionBar() {

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();

        // make action bar transparent
        actionBar.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        actionBar.setStackedBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        actionBar.setSplitBackgroundDrawable(new ColorDrawable(R.color.transparent_white_80));

        // title bar and icon
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        return actionBar;
    }

    // Override the method as a custom configuration change has been
    // set in the manifest for all Activities but don't do anything as
    // nothing should be altered on orientation change
    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
