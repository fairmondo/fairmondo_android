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
import android.widget.CheckBox;
import android.widget.CompoundButton;

import de.handler.mobile.android.fairmondo.R;
import de.handler.mobile.android.fairmondo.presentation.interfaces.OnFilterSelectedListener;

/**
 * Shows the sorting options and reacts to user inputs.
 */
public class FilterFragment extends DialogFragment {
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

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View rootView = inflater.inflate(R.layout.fragment_filter, null);

        ((CheckBox) rootView.findViewById(R.id.fragment_filter_checkbox_fair)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                mOnFilterSelectedListener.onFairFilterSelected(isChecked);
            }
        });

        ((CheckBox) rootView.findViewById(R.id.fragment_filter_checkbox_ecological)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                mOnFilterSelectedListener.onEcologicalFilterSelected(isChecked);
            }
        });

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(rootView).setTitle(R.string.text_filter_title).setPositiveButton(android.R.string.ok, null);
        return builder.create();
    }
}
