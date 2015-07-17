package de.handler.mobile.android.fairmondo.presentation.controller;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;

import de.handler.mobile.android.fairmondo.presentation.FragmentHelper;
import de.handler.mobile.android.fairmondo.presentation.fragments.ProgressFragment;
import de.handler.mobile.android.fairmondo.presentation.fragments.ProgressFragment_;

/**
 * Coordinates the Progress of an AsyncTask.
 */
@EBean(scope = EBean.Scope.Singleton)
public class ProgressController {
    private final static String TAG = "progressFragment";
    private boolean mAdded = false;

    private FragmentManager mFragmentManager;
    private ProgressFragment mFragment;

    /**
     * Start the progress indication by overlaying the current screen with the ProgressFragment.
     * Is only called on the ui thread as it is ui related.
     */
    @UiThread
    public void startProgress(@NonNull final FragmentManager fragmentManager, @IdRes final int container) {
        mFragmentManager = fragmentManager;
        if (null == mFragment) {
            mFragment = ProgressFragment_.builder().build();
        }

        if (!mAdded) {
            FragmentHelper.addFragmentWithTag(container, mFragment, mFragmentManager, TAG);
            mAdded = true;
        }
    }

    /**
     * Remove the progress indication by removing the overlay with the ProgressFragment.
     * Is only called on the ui thread as it is ui related.
     */
    @UiThread
    public void stopProgress() {
        FragmentHelper.removeFragment(mFragment, mFragmentManager);
        mAdded = false;
    }
}
