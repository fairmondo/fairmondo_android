package de.handler.mobile.android.shopprototype.ui.fragments;


import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import de.handler.mobile.android.shopprototype.R;
import de.handler.mobile.android.shopprototype.ShopApp;

/**
 * A simple {@link Fragment} subclass.
 *
 */
@EFragment(R.layout.fragment_title)
public class TitleFragment extends Fragment implements View.OnClickListener {

    public static final String IMAGE_DRAWABLE_EXTRA = "image_drawable_extra";
    public static final String IMAGE_DESCRIPTION_STRING_EXTRA = "image_description_string_extra";

    @ViewById(R.id.fragment_title_image)
    ImageView imageViewTitle;

    @ViewById(R.id.fragment_title_image_description)
    TextView textviewDescription;

    @App
    ShopApp app;


    @AfterViews
    public void init() {

        int drawable = getArguments().getInt(IMAGE_DRAWABLE_EXTRA);
        String imageDescription = getArguments().getString(IMAGE_DESCRIPTION_STRING_EXTRA);

        imageViewTitle.setImageDrawable(getActivity().getResources().getDrawable(drawable));
        textviewDescription.setText(imageDescription);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_title_image:
                // React to click event
                break;
        }
    }
}
