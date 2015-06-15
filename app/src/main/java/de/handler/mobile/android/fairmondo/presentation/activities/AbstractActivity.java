package de.handler.mobile.android.fairmondo.presentation.activities;

import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Receiver;
import org.androidannotations.annotations.SystemService;

import de.handler.mobile.android.fairmondo.FairmondoApp;

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
    public ActionBar setupActionBar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        return getSupportActionBar();
    }

    // Override the method as a custom configuration change has been
    // set in the manifest for all Activities but don't do anything as
    // nothing should be altered on orientation change
    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
