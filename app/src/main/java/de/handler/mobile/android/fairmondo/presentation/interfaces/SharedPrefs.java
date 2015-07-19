package de.handler.mobile.android.fairmondo.presentation.interfaces;

import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Interface for generating Shared Preferences.
 */
@SharedPref
public interface SharedPrefs {
    @DefaultString("")
    String cookie();
}
