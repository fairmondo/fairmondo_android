package de.handler.mobile.android.fairmondo.presentation.activities;

import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Receiver;
import org.androidannotations.annotations.SystemService;

import de.handler.mobile.android.fairmondo.FairmondoApp;
import de.handler.mobile.android.fairmondo.FairmondoApp_;

/**
 * Abstract Activity implemented by all other activities.
 */
@EActivity
public abstract class AbstractActivity extends AppCompatActivity {
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
        Log.i("FAIRMONDO_APP ", "NETWORK_STATE_CHANGE");
    }

    protected void checkNetworkState() {
        final FairmondoApp app = (FairmondoApp_) getApplicationContext();
        final NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if (null != activeNetwork && activeNetwork.isConnected()) {
            app.setConnected(true);
        } else {
            app.setConnected(false);
        }
    }

    /**
     * ActionBar settings.
     */
    protected void setHomeUpEnabled(@NonNull final Toolbar toolbar) {
        final ActionBar actionBar = this.setupActionBar(toolbar);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    protected ActionBar setupActionBar(@NonNull final Toolbar toolbar) {
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
