package de.handler.mobile.android.fairmondo.ui.animation;

import android.view.View;

/**
 * Contains several animations
 */
public class AnimationHandler {

    public static void fade(View view, int percent) {
        view.animate().alpha(percent).setDuration(250);
    }

    public static void rotate(View view) {
        view.animate().rotationYBy(360).setDuration(250);
    }
}
