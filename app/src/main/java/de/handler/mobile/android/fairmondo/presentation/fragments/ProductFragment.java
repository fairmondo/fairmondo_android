package de.handler.mobile.android.fairmondo.presentation.fragments;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.software.shell.fab.ActionButton;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.parceler.Parcels;

import java.text.NumberFormat;
import java.util.Locale;

import de.handler.mobile.android.fairmondo.FairmondoApp;
import de.handler.mobile.android.fairmondo.R;
import de.handler.mobile.android.fairmondo.data.RestCommunicator;
import de.handler.mobile.android.fairmondo.data.businessobject.Cart;
import de.handler.mobile.android.fairmondo.data.businessobject.Product;
import de.handler.mobile.android.fairmondo.data.interfaces.OnCartChangeListener;
import de.handler.mobile.android.fairmondo.presentation.views.CustomNetworkImageView;

/**
 * Displays one product.
 */
@EFragment(R.layout.fragment_product)
public class ProductFragment extends Fragment implements OnCartChangeListener, View.OnClickListener {
    public static final String PRODUCT_EXTRA = "product_extra";

    // TODO display fair, öko, etc tags
    // TODO display product condition
    // TODO display seller details / contact seller
    // TODO show incl. vat if available

    @App
    FairmondoApp app;

    @Bean
    RestCommunicator restController;

    @ViewById(R.id.fragment_product_image_view)
    CustomNetworkImageView productImageView;

    @ViewById(R.id.fragment_product_content_container)
    RelativeLayout layoutContent;

    @ViewById(R.id.fragment_product_title)
    TextView titleTextView;

    @ViewById(R.id.fragment_product_price)
    TextView priceTextView;

    @ViewById(R.id.fragment_product_price_vat)
    TextView vatTextview;

    @ViewById(R.id.fragment_product_donation)
    TextView textViewDonation;

    @ViewById(R.id.fragment_product_button_description)
    Button buttonDescription;

    @ViewById(R.id.fragment_product_button_fair_percent)
    Button buttonFairPercent;

    @ViewById(R.id.fragment_product_button_terms)
    Button buttonTerms;

    @ViewById(R.id.fragment_product_button_transport)
    Button buttonTransport;

    @ViewById(R.id.fragment_product_button_payment)
    Button buttonPayment;

    @ViewById(R.id.fragment_product_button_buy)
    ActionButton buttonBuy;

    private Product mProduct;

    @AfterViews
    public void init() {
        restController.setCartChangeListener(this);

        mProduct = Parcels.unwrap(getArguments().getParcelable(PRODUCT_EXTRA));
        if (mProduct != null) {
            // Create custom typeface
            // Typeface myTypeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Thin.ttf");

            titleTextView.setText(mProduct.getTitle());
            // Image
            String url = mProduct.getTitleImageUrl();
            if (mProduct.getTitleImage() != null && !mProduct.getTitleImage().getOriginalUrl().equals("")) {
                url = mProduct.getTitleImage().getOriginalUrl();
            }
            if (url != null) {
                app.getImageLoader().get(url, new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(final ImageLoader.ImageContainer response, final boolean isImmediate) {
                        final Bitmap bitmap = response.getBitmap();
                        if (bitmap != null) {
                            setFragmentColors(bitmap);
                        }
                    }

                    @Override
                    public void onErrorResponse(final VolleyError error) {
                    }
                });
            }

            // Description
            if (mProduct.getContent() == null || mProduct.getContent().equals("")) {
                buttonDescription.setVisibility(View.GONE);
            }

            // Terms
            if (mProduct.getSeller() == null || mProduct.getSeller().getTerms() == null || mProduct.getSeller().getTerms().equals("")) {
                buttonTerms.setVisibility(View.GONE);
            }

            // Transport
            if (mProduct.getTransportHtml() == null || mProduct.getTransportHtml().equals("")) {
                buttonFairPercent.setVisibility(View.GONE);
            }

            // Payment
            if (mProduct.getPaymentHtml() == null || mProduct.getPaymentHtml().equals("")) {
                buttonFairPercent.setVisibility(View.GONE);
            }

            // Donation
            if (mProduct.getDonation() != null) {
                textViewDonation.setText(
                        "Diese*r Anbieter*in spendet "
                                + mProduct.getDonation().getPercent()
                                + " % an "
                                + mProduct.getDonation().getOrganization().getName());
            }

            // Price TODO: if last number is a 0 it is omitted -  change if possible
            double priceValue = (mProduct.getPriceCents() / 100.00);
            // Localized price value (e.g. instead of '.' use ',' in German) and corresponding currency
            NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
            String price = format.format(priceValue);
            priceTextView.setText(price + " " + format.getCurrency().getSymbol());

            if (mProduct.getVat() > 0) {
                vatTextview.setVisibility(View.GONE);
            }

            // Click Listeners
            buttonDescription.setOnClickListener(this);
            buttonTerms.setOnClickListener(this);
            buttonFairPercent.setOnClickListener(this);
            buttonTransport.setOnClickListener(this);
            buttonPayment.setOnClickListener(this);
            buttonBuy.setOnClickListener(this);
        }
    }

    @UiThread
    public void setFragmentColors(final Bitmap bitmap) {
        final int[] backgroundColor = new int[1];
        final int[] foregroundColor = new int[1];

        Palette.Builder builder = Palette.from(bitmap);
        builder.generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(final Palette palette) {
                Palette.Swatch swatch = palette.getLightMutedSwatch();
                if (swatch != null) {
                    backgroundColor[0] = swatch.getRgb();
                    foregroundColor[0] = swatch.getTitleTextColor();
                } else {
                    backgroundColor[0] = palette.getLightMutedColor(android.R.color.transparent);
                    foregroundColor[0] = palette.getLightMutedColor(android.R.color.transparent);
                }

                productImageView.setBackgroundColor(backgroundColor[0]);
                layoutContent.setBackgroundColor(backgroundColor[0]);
                buttonBuy.setButtonColor(foregroundColor[0]);
            }
        });

        productImageView.setLocalImageBitmap(bitmap);
    }

    @UiThread
    @Override
    public void onCartChanged(final Cart cart) {
        if (cart != null && cart.getCartItem() != null) {
            int itemCount = cart.getCartItem().getRequestedQuantity();
            Toast.makeText(getActivity(), itemCount + " items added to your cart", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(final View v) {
        boolean web = true;
        //Intent intent = new Intent(getActivity(), WebActivity_.class);
        //Bundle bundle = ActivityOptionsCompat.makeScaleUpAnimation(v, 0, 0, v.getWidth(), v.getHeight()).toBundle();
        Bundle bundle = new Bundle();

        ProductDetailDialog dialog = new ProductDetailDialog_();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        switch (v.getId()) {
            case R.id.fragment_product_button_buy:
                restController.addToCard(Integer.parseInt(mProduct.getId()));
                web = false;
                break;
            case R.id.fragment_product_button_description:
                //intent.putExtra(WebFragment.HTTP_CONTENT, mProduct.getContent_html());
                bundle.putString(WebFragment.HTTP_CONTENT, mProduct.getContent());
                dialog.setArguments(bundle);
                break;
            case R.id.fragment_product_button_terms:
                //intent.putExtra(WebFragment.HTTP_CONTENT, mProduct.getTerms_html());
                bundle.putString(WebFragment.HTTP_CONTENT, mProduct.getSeller().getTerms());
                dialog.setArguments(bundle);
                break;
            case R.id.fragment_product_button_transport:
                bundle.putString(WebFragment.HTTP_CONTENT, mProduct.getTransportHtml());
                dialog.setArguments(bundle);
                break;
            case R.id.fragment_product_button_payment:
                bundle.putString(WebFragment.HTTP_CONTENT, mProduct.getPaymentHtml());
                dialog.setArguments(bundle);
                break;
            default:
                // nothing is done here
        }

        if (web) {
            transaction.add(dialog, "Dialog");
            transaction.setTransition(android.R.anim.decelerate_interpolator).commit();
            /*if (Build.VERSION.SDK_INT > 15) {
                getActivity().startActivity(intent, bundle);
            } else {
                startActivity(intent);
            }*/
        }
    }
}
