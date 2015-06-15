package de.handler.mobile.android.fairmondo.presentation.fragments;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import de.handler.mobile.android.fairmondo.FairmondoApp;
import de.handler.mobile.android.fairmondo.R;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_title)
public class TitleFragment extends Fragment {
    @FragmentArg
    Integer mDrawable;

    @FragmentArg
    String mSlogan;

    @App
    FairmondoApp app;

    @ViewById(R.id.fragment_title_image)
    ImageView mImageViewTitle;

    @ViewById(R.id.fragment_title_image_description)
    TextView mTextViewDescription;

    @AfterViews
    public void init() {
        if (mDrawable == null) {
            mImageViewTitle.setVisibility(View.GONE);
            mTextViewDescription.setVisibility(View.GONE);
        } else {
            this.setDrawable();
            mTextViewDescription.setText(mSlogan);
        }
    }

    /**
     * Set the image which will be displayed in the title.
     * Consider the different APIs
     */
    private void setDrawable() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mImageViewTitle.setImageDrawable(getActivity().getResources().getDrawable(mDrawable, getActivity().getTheme()));
        } else {
            mImageViewTitle.setImageDrawable(getActivity().getResources().getDrawable(mDrawable));
        }
    }
}
