package de.handler.mobile.android.fairmondo.presentation.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import de.handler.mobile.android.fairmondo.R;

/**
 * An controller displaying error messages to the user.
 */
public class UIInformationController {
    /**
     * Displays a message as Toast.
     */
    public static void displayToastInformation(@NonNull final Context context, @NonNull final String errorMessage) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
    }

    /**
     * Displays a message as Snackbar.
     */
    public static void displaySnackbarInformation(@NonNull final View parentView, @NonNull final String errorMessage) {
        Snackbar.make(parentView, errorMessage, Snackbar.LENGTH_SHORT)
                .setActionTextColor(parentView.getResources().getColor(R.color.fairmondo_blue_dark))
                .show();
    }
}
