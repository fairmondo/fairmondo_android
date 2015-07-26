package de.handler.mobile.android.fairmondo.presentation.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import de.handler.mobile.android.fairmondo.R;
import de.handler.mobile.android.fairmondo.presentation.interfaces.OnFilterSelectedListener;

/**
 * Shows the filter options and reacts to user inputs.
 */
@EFragment(R.layout.fragment_filter)
public class FilterFragment extends Fragment {
    @ViewById(R.id.fragment_filter_price_radio_button)
    RadioButton mPriceRadioButton;

    @ViewById(R.id.fragment_filter_alphabetical_radio_button)
    RadioButton mAlphabeticalRadioButton;

    @ViewById(R.id.fragment_filter_condition_radio_button)
    RadioButton mConditionRadioButton;

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
        mPriceRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mOnFilterSelectedListener.onPriceFilterSelected(true);
                }
            }
        });

        mAlphabeticalRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mOnFilterSelectedListener.onAlphabeticalFilterSelected(true);
                }
            }
        });

        mConditionRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mOnFilterSelectedListener.onConditionFilterSelected(true);
                }
            }
        });

        mButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnFilterSelectedListener.onFilterFinish();
            }
        });
    }
}
