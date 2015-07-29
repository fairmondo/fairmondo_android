package de.handler.mobile.android.fairmondo.presentation.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import de.handler.mobile.android.fairmondo.R;
import de.handler.mobile.android.fairmondo.presentation.interfaces.OnSortingSelectedListener;

/**
 * Shows the sorting options and reacts to user inputs.
 */
public class SortFragment extends DialogFragment {
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

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View rootView = inflater.inflate(R.layout.fragment_sort, null);

        ((RadioButton) rootView.findViewById(R.id.fragment_sort_price_radio_button))
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                        if (isChecked) {
                            mOnSortingSelectedListener.onPriceSortSelected(true);
                        }
                    }
                });

        ((RadioButton) rootView.findViewById(R.id.fragment_sort_alphabetical_radio_button))
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                        if (isChecked) {
                            mOnSortingSelectedListener.onPriceSortSelected(true);
                        }
                    }
                });

        ((RadioButton) rootView.findViewById(R.id.fragment_sort_condition_radio_button))
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                        if (isChecked) {
                            mOnSortingSelectedListener.onPriceSortSelected(true);
                        }
                    }
                });

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(rootView).setTitle(R.string.text_sort_title).setPositiveButton(android.R.string.ok, null);
        return builder.create();
    }
}
