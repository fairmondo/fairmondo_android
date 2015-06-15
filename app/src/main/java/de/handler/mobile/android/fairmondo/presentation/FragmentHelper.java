package de.handler.mobile.android.fairmondo.presentation;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Handles the fragment transactions.
 */
public class FragmentHelper {
    /**
     * Private Contructor to prevent instantiation of helper class.
     */
    private FragmentHelper() { }

    /**
     * Adds a fragment to a container.
     *
     * @param container the container the Fragment should be placed in
     * @param fragment the fragment which should be added to the provided container
     * @param fragmentManager the fragment manager which does the transaction
     */
    public static void addFragment(@IdRes final int container, @NonNull final Fragment fragment, @NonNull final FragmentManager fragmentManager) {
        fragmentManager.beginTransaction().add(container, fragment).commit();
    }

    /**
     * Adds a fragment to a container.
     *
     * @param container the container the Fragment should be placed in
     * @param fragment the fragment which should be added to the provided container
     * @param tag the tag which adds the fragment to the back stack
     * @param fragmentManager the fragment manager which does the transaction
     */
    public static void addFragmentWithTag(@IdRes final int container, @NonNull final Fragment fragment, final String tag, @NonNull final FragmentManager fragmentManager) {
        fragmentManager.beginTransaction().add(container, fragment, tag).commit();
    }

    /**
     * Adds a fragment to a container.
     *
     * @param container the container the Fragment should be placed in
     * @param fragment the fragment which should be added to the provided container
     * @param tag the tag which adds the fragment to the back stack
     * @param fragmentManager the fragment manager which does the transaction
     */
    public static void addFragmentWithTagToBackStack(@IdRes final int container, @NonNull final Fragment fragment, final String tag, @NonNull final FragmentManager fragmentManager) {
        fragmentManager.beginTransaction().add(container, fragment).addToBackStack(tag).commit();
    }

    /**
     * Replaces a fragment in a container.
     *
     * @param container the container the Fragment should be placed in
     * @param fragment the fragment which should be added to the provided container
     * @param fragmentManager the fragment manager which does the transaction
     */
    public static void replaceFragment(@IdRes final int container, @NonNull final Fragment fragment, @NonNull final FragmentManager fragmentManager) {
        fragmentManager.beginTransaction().replace(container, fragment).commit();
    }

    /**
     * Replaces a fragment in a container and adds it to the back stack.
     *
     * @param container the container the Fragment should be placed in
     * @param fragment the fragment which should be added to the provided container
     * @param fragmentManager the fragment manager which does the transaction
     */
    public static void replaceFragmentWithTag(@IdRes final int container, @NonNull final Fragment fragment, @NonNull final FragmentManager fragmentManager, @NonNull final String tag) {
        if (fragmentManager.findFragmentByTag(tag) == null) {
            fragmentManager.beginTransaction().replace(container, fragment, tag).commit();
        }
    }

    /**
     * Replaces a fragment in a container and adds it to the back stack.
     *
     * @param container the container the Fragment should be placed in
     * @param fragment the fragment which should be added to the provided container
     * @param fragmentManager the fragment manager which does the transaction
     */
    public static void replaceFragmentWithTagToBackStack(@IdRes final int container, @NonNull final Fragment fragment, @NonNull final FragmentManager fragmentManager, @NonNull final String tag) {
        fragmentManager.beginTransaction().replace(container, fragment).addToBackStack(tag).commit();
    }

    /**
     * Removes a fragment from the fragment back stack and the container it is situated in.
     *
     * @param fragment the fragment which should be added to the provided container
     * @param fragmentManager the fragment manager which does the transaction
     */
    public static void removeFragment(@NonNull final Fragment fragment, @NonNull FragmentManager fragmentManager) {
        fragmentManager.beginTransaction().remove(fragment).commit();
    }
}
