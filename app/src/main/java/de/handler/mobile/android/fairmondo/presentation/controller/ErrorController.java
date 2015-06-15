package de.handler.mobile.android.fairmondo.presentation.controller;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import de.handler.mobile.android.fairmondo.R;

/**
 * An controller displaying error messages to the user.
 */
public class ErrorController {
    public static void displayErrorToast(final Context context, final String errorMessage) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
    }

    public static void displayErrorSnackbar(final View parentView, final String errorMessage) {
        Snackbar.make(parentView, errorMessage, Snackbar.LENGTH_SHORT)
                .setActionTextColor(parentView.getResources().getColor(R.color.fairmondo_blue_dark))
                .show();
    }
}
