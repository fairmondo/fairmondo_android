package de.handler.mobile.android.fairmondo.presentation.fragments;

import android.support.v4.app.Fragment;
import android.widget.ProgressBar;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import de.handler.mobile.android.fairmondo.R;

/**
 * A Fragment which displays a progress bar as soon as an AsyncTask is running.
 */
@EFragment(R.layout.fragment_progress)
public class ProgressFragment extends Fragment {
    @ViewById(R.id.fragment_progress_bar)
    ProgressBar mProgressBar;
}
