package de.handler.mobile.android.fairmondo.presentation.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import de.handler.mobile.android.fairmondo.R;
import de.handler.mobile.android.fairmondo.presentation.interfaces.OnSortingSelectedListener;

/**
 * Shows the sorting options and reacts to user inputs.
 */
@EFragment(R.layout.fragment_sort)
public class SortFragment extends Fragment {
    @ViewById(R.id.fragment_sort_price_radio_button)
    RadioButton mPriceRadioButton;

    @ViewById(R.id.fragment_sort_alphabetical_radio_button)
    RadioButton mAlphabeticalRadioButton;

    @ViewById(R.id.fragment_sort_condition_radio_button)
    RadioButton mConditionRadioButton;

    @ViewById(R.id.fragment_sort_button_ok)
    Button mButtonOk;

    private OnSortingSelectedListener mOnSortingSelectedListener;


    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        try {
            mOnSortingSelectedListener = (OnSortingSelectedListener) activity;
        } catch (final ClassCastException e) {
            Log.e(getClass().getCanonicalName(), "The activity has to implement mOnSortingSelectedListener");
        }
    }

    @AfterViews
    void init() {
        mPriceRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                if (isChecked) {
                    mOnSortingSelectedListener.onPriceSortSelected(true);
                }
            }
        });

        mAlphabeticalRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                if (isChecked) {
                    mOnSortingSelectedListener.onAlphabeticalSortSelected(true);
                }
            }
        });

        mConditionRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                if (isChecked) {
                    mOnSortingSelectedListener.onConditionSortSelected(true);
                }
            }
        });

        mButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mOnSortingSelectedListener.onSortFinish();
            }
        });
    }
}
