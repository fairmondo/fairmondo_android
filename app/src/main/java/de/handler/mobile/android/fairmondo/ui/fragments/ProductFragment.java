package de.handler.mobile.android.fairmondo.ui.fragments;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.text.NumberFormat;
import java.util.Locale;

import de.handler.mobile.android.fairmondo.FairmondoApp;
import de.handler.mobile.android.fairmondo.R;
import de.handler.mobile.android.fairmondo.interfaces.OnCartChangeListener;
import de.handler.mobile.android.fairmondo.rest.RestController;
import de.handler.mobile.android.fairmondo.rest.json.Article;
import de.handler.mobile.android.fairmondo.rest.json.model.Cart;
import de.handler.mobile.android.fairmondo.ui.views.CustomNetworkImageView;

/**
 * Displays one product
 */
@EFragment(R.layout.fragment_product)
public class ProductFragment extends Fragment implements OnCartChangeListener, View.OnClickListener {

    public static final String PRODUCT_EXTRA = "product_extra";

    @App
    FairmondoApp app;

    @Bean
    RestController restController;

    @ViewById(R.id.fragment_product_image_view)
    CustomNetworkImageView productImageView;

    @ViewById(R.id.fragment_product_progressBar)
    ProgressBar progressBar;

    @ViewById(R.id.fragment_product_item_count_text_view)
    TextView itemCountTextView;

    @ViewById(R.id.fragment_product_title)
    TextView titleTextView;

    @ViewById(R.id.fragment_product_price)
    TextView priceTextView;
    
    @ViewById(R.id.fragment_product_button_description)
    Button buttonDescription;

    @ViewById(R.id.fragment_product_button_fair_percent)
    Button buttonFairPercent;

    @ViewById(R.id.fragment_product_button_terms)
    Button buttonTerms;

    private Article mProduct;


    @AfterViews
    public void init() {
        restController.setCartChangeListener(this);

        mProduct = getArguments().getParcelable(PRODUCT_EXTRA);
        if (mProduct != null) {
            // Create custom typeface
            // Typeface myTypeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Thin.ttf");

            titleTextView.setText(mProduct.getTitle());

            // Image
            String url = mProduct.getTitle_image_url();
            if (mProduct.getTitle_image() != null && !mProduct.getTitle_image().getOriginal_url().equals("")) {
                url = mProduct.getTitle_image().getOriginal_url();
            }

            productImageView.setErrorImageResId(R.drawable.watermark);

            if (url != null) {
                app.getImageLoader().get(url, new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(final ImageLoader.ImageContainer response, boolean isImmediate) {
                        final Bitmap bitmap = response.getBitmap();
                        if (bitmap != null) {
                            setImageBackground(bitmap);
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
            }


            // Description
            if (mProduct.getContent_html() == null || mProduct.getContent_html().equals("")) {
                buttonDescription.setVisibility(View.GONE);
            }

            // Terms
            if (mProduct.getTerms_html() == null || mProduct.getTerms_html().equals("")) {
                buttonTerms.setVisibility(View.GONE);
            }

            // Fair Percent
            if (mProduct.getFair_percent_html() == null || mProduct.getFair_percent_html().equals("")) {
                buttonFairPercent.setVisibility(View.GONE);
            }

            // Price //TODO: if last number is a 0 it is omitted -  change if possible
            double priceValue = (mProduct.getPrice_cents() / 100.0);
            // Localized price value (e.g. instead of '.' use ',' in German) and corresponding currency
            NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
            String price = format.format(priceValue);
            priceTextView.setText(price + " " + format.getCurrency().getSymbol());


            // Click Listeners
            buttonDescription.setOnClickListener(this);
            buttonTerms.setOnClickListener(this);
            buttonFairPercent.setOnClickListener(this);
        }
    }


    @UiThread
    public void setImageBackground(final Bitmap bitmap) {
        //final BitmapDrawable drawable = new BitmapDrawable(getActivity().getResources(), bitmap);
        //drawable.setAlpha(150);

        Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette palette) {
                int color = palette.getLightMutedColor(android.R.color.transparent);
                productImageView.setBackgroundColor(color);
                productImageView.setLocalImageBitmap(bitmap);
            }
        });

        progressBar.setVisibility(View.GONE);
    }


    @Click(R.id.fragment_product_button_buy)
    public void addToCart() {
        restController.addToCard(mProduct.getId());
    }

    @UiThread
    @Override
    public void onCartChanged(Cart cart) {
        if (cart != null && cart.getLine_item() != null) {
            int itemCount = cart.getLine_item().getRequested_quantity();
            itemCountTextView.setVisibility(View.VISIBLE);
            itemCountTextView.setText(String.valueOf(itemCount));
        } else {
            itemCountTextView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        //Intent intent = new Intent(getActivity(), WebActivity_.class);
        //Bundle bundle = ActivityOptionsCompat.makeScaleUpAnimation(v, 0, 0, v.getWidth(), v.getHeight()).toBundle();
        Bundle bundle = new Bundle();

        ProductDetailDialog dialog = new ProductDetailDialog_();
        FragmentTransaction transaction =
                getActivity().getSupportFragmentManager().beginTransaction();

        switch (v.getId()) {
            case R.id.fragment_product_button_description:
                //intent.putExtra(WebFragment.HTTP_CONTENT, mProduct.getContent_html());
                bundle.putString(WebFragment.HTTP_CONTENT, mProduct.getContent_html());
                dialog.setArguments(bundle);

                break;
            case R.id.fragment_product_button_terms:
                //intent.putExtra(WebFragment.HTTP_CONTENT, mProduct.getTerms_html());
                bundle.putString(WebFragment.HTTP_CONTENT, mProduct.getTerms_html());
                dialog.setArguments(bundle);

                break;
            case R.id.fragment_product_button_fair_percent:
                //intent.putExtra(WebFragment.HTTP_CONTENT, mProduct.getFair_percent_html());
                bundle.putString(WebFragment.HTTP_CONTENT, mProduct.getFair_percent_html());
                dialog.setArguments(bundle);

                break;
        }

        transaction.add(dialog, "Dialog");
        transaction.setTransition(android.R.anim.decelerate_interpolator).commit();
        /*if (Build.VERSION.SDK_INT > 15) {
            getActivity().startActivity(intent, bundle);
        } else {
            startActivity(intent);
        }*/
    }
}
