package de.handler.mobile.android.shopprototype.ui.fragments;


import de.handler.mobile.android.shopprototype.R;
import de.handler.mobile.android.shopprototype.ShopApp;
import de.handler.mobile.android.shopprototype.utils.CustomNetworkImageView;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * A simple {@link Fragment} subclass.
 *
 */
@EFragment(R.layout.fragment_title)
public class TitleFragment extends Fragment implements View.OnClickListener {

    public static final String IMAGE_URL_STRING_EXTRA = "image_url_string_extra";
    public static final String IMAGE_DESCRIPTION_STRING_EXTRA = "image_description_string_extra";

    @ViewById(R.id.fragment_feature_image)
    CustomNetworkImageView imageViewFeature;

    @ViewById(R.id.fragment_feature_image_description)
    TextView textviewDescription;

    @App
    ShopApp app;


    @AfterViews
    public void init() {

        String imageUrl = getArguments().getString(IMAGE_URL_STRING_EXTRA);
        String imageDescription = getArguments().getString(IMAGE_DESCRIPTION_STRING_EXTRA);

        imageViewFeature.setImageUrl(imageUrl, app.getImageLoader());
        textviewDescription.setText(imageDescription);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_feature_image:
                // React to click event
                break;
        }
    }
}
