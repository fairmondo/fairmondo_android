package de.handler.mobile.android.fairmondo.presentation.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import de.handler.mobile.android.fairmondo.R;
import de.handler.mobile.android.fairmondo.presentation.interfaces.OnFilterSelectedListener;
import de.handler.mobile.android.fairmondo.presentation.interfaces.OnSortingSelectedListener;

/**
 * Shows the sorting options and reacts to user inputs.
 */
@EFragment(R.layout.fragment_filter)
public class FilterFragment extends Fragment {
    @ViewById(R.id.fragment_filter_checkbox_fair)
    CheckBox mCheckboxFair;

    @ViewById(R.id.fragment_filter_checkbox_ecological)
    CheckBox mCheckboxEcological;

    @ViewById(R.id.fragment_filter_button_ok)
    Button mButtonOk;

    private OnFilterSelectedListener mOnFilterSelectedListener;

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        try {
            mOnFilterSelectedListener = (OnFilterSelectedListener) activity;
        } catch (final ClassCastException e) {
            Log.e(getClass().getCanonicalName(), "The activity has to implement mOnFilterSelectedListener");
        }
    }

    @AfterViews
    void init() {
        mCheckboxFair.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                mOnFilterSelectedListener.onFairFilterSelected(isChecked);
            }
        });

        mCheckboxEcological.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                mOnFilterSelectedListener.onEcologicalFilterSelected(isChecked);
            }
        });

        mButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mOnFilterSelectedListener.onFilterFinish();
            }
        });
    }
}
