package de.handler.mobile.android.fairmondo.ui.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.util.AttributeSet;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

/**
 * Custom implementation of Volley's NetworkImageView which enriches the class with a fade
 * when the image has been retrieved and corrects a bug which makes it impossible to set
 * bitmaps manually.
 */
public class CustomNetworkImageView extends NetworkImageView {
    private static final int FADE_IN_TIME_MS = 350;
    private Bitmap  mLocalBitmap;
    private boolean mShowLocalBitmap;

    /**
     * First of three standard constructors for custom views.
     */
    public CustomNetworkImageView(Context context) {
        this(context, null);
    }

    /**
     * Second of three standard constructors for custom views.
     */
    public CustomNetworkImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Third of three standard constructors for custom views.
     */
    public CustomNetworkImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Set a bitmap manually.
     * @param bitmap the image which should be displayed
     */
    public void setLocalImageBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            mShowLocalBitmap = true;
        }
        this.mLocalBitmap = bitmap;
        requestLayout();
    }

    /**
     * set the image url from where the image should be retrieved.
     * @param url the url from which the image should be retrieved
     * @param imageLoader the image loader volley uses to get the images from the url
     */
    @Override
    public void setImageUrl(String url, ImageLoader imageLoader) {
        mShowLocalBitmap = false;
        super.setImageUrl(url, imageLoader);
    }

    /**
     * Add a fade to the standard setImageBitmap method.
     * @param bm the image which has just been retrieved from volley
     */
    @Override
    public void setImageBitmap(Bitmap bm) {
        TransitionDrawable td = new TransitionDrawable(new Drawable[]{
                new ColorDrawable(android.R.color.transparent),
                new BitmapDrawable(getContext().getResources(), bm)
        });

        setImageDrawable(td);
        td.startTransition(FADE_IN_TIME_MS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mShowLocalBitmap) {
            setImageBitmap(mLocalBitmap);
        }
    }
}
