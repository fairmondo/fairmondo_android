package de.handler.mobile.android.fairmondo.ui.animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;

/**
 * Contains several animations
 */
public class AnimationHandler {

    public static void fadeInAndOut(View view, int duration) {
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(view, "alpha",
                0f);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(view, "alpha",
                0f, 1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(duration);

        animatorSet.play(fadeIn).after(fadeOut);
        animatorSet.start();
    }

    public static void rotate(View view, float degrees) {
        ObjectAnimator rotate = ObjectAnimator.ofFloat(view, "rotation", degrees);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(rotate);
        animatorSet.start();
    }

    public static void growAndShrink(View view) {
        view.bringToFront();
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(view, "scaleX", 1.5f);
        scaleXAnimator.setRepeatMode(ValueAnimator.REVERSE);
        scaleXAnimator.setRepeatCount(1);
        scaleXAnimator.setDuration(1000);

        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(view, "scaleY", 1.5f);
        scaleYAnimator.setRepeatMode(ValueAnimator.REVERSE);
        scaleYAnimator.setRepeatCount(1);
        scaleYAnimator.setDuration(1000);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(scaleXAnimator, scaleYAnimator);
        set.start();
    }
}
