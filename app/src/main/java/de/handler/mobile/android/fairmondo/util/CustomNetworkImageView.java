package de.handler.mobile.android.fairmondo.util;

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
 * Workaround as setting Bitmap with NetworkImageView did not work out of the box
 */
public class CustomNetworkImageView extends NetworkImageView {
    private static final int FADE_IN_TIME_MS = 250;

    private Bitmap  mLocalBitmap;
    private BitmapDrawable mLocalDrawable;

    private boolean mShowLocalBitmap;
    private boolean mShowLocalDrawable;

    public void setLocalImageBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            mShowLocalBitmap = true;
        }
        this.mLocalBitmap = bitmap;
        requestLayout();
    }

    public void setLocalImageDrawable(BitmapDrawable drawable) {
        if (drawable != null) {
            mShowLocalDrawable = true;
        }
        this.mLocalDrawable = drawable;
        requestLayout();
    }

    @Override
    public void setImageUrl(String url, ImageLoader imageLoader) {
        mShowLocalBitmap = false;
        mShowLocalDrawable = false;
        super.setImageUrl(url, imageLoader);
    }

    public CustomNetworkImageView(Context context) {
        this(context, null);
    }

    public CustomNetworkImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomNetworkImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        TransitionDrawable td = new TransitionDrawable(new Drawable[]{
                new ColorDrawable(android.R.color.transparent),
                new BitmapDrawable(getContext().getResources(), bm)
        });

        setImageDrawable(td);
        td.startTransition(FADE_IN_TIME_MS);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        super.onLayout(changed, left, top, right, bottom);
        if (mShowLocalBitmap) {
            setImageBitmap(mLocalBitmap);
        } else if (mShowLocalDrawable) {
            setImageDrawable(mLocalDrawable);
        }
    }
}
