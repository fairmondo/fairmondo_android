package de.handler.mobile.android.fairmondo.presentation.fragments;


import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.graphics.Palette;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
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
import de.handler.mobile.android.fairmondo.presentation.activities.WebActivity_;
import de.handler.mobile.android.fairmondo.presentation.views.CustomNetworkImageView;

/**
 * Displays one product.
 */
@EFragment(R.layout.fragment_product)
public class ProductFragment extends Fragment implements OnCartChangeListener, View.OnClickListener {
    // TODO display fair, öko, etc tags
    // TODO display product condition
    // TODO display seller details / contact seller
    // TODO show incl. vat if available

    @FragmentArg
    Parcelable mProductParcelable;

    @App
    FairmondoApp app;

    @Bean
    RestCommunicator restController;

    @ViewById(R.id.fragment_product_image_view)
    CustomNetworkImageView productImageView;

    @ViewById(R.id.fragment_product_content_container)
    RelativeLayout layoutContent;

    @ViewById(R.id.fragment_product_title)
    TextView textViewTitle;

    @ViewById(R.id.fragment_product_textview_price)
    TextView textViewPrice;

    @ViewById(R.id.fragment_product_textview_price_vat)
    TextView textViewVat;

    @ViewById(R.id.fragment_product_textview_donation_title)
    TextView textViewDonation;

    @ViewById(R.id.fragment_product_textview_description_title)
    TextView textViewDescriptionTitle;

    @ViewById(R.id.fragment_product_textview_terms_title)
    TextView textViewTermsTitle;

    @ViewById(R.id.fragment_product_textview_description_body)
    TextView textViewDescriptionBody;

    @ViewById(R.id.fragment_product_textview_transport_body)
    TextView textViewTransportBody;

    @ViewById(R.id.fragment_product_textview_payment_body)
    TextView textViewPaymentBody;

    @ViewById(R.id.fragment_product_button_buy)
    View buttonBuy;

    private Product mProduct;

    @AfterViews
    public void init() {
        restController.setCartChangeListener(this);
        mProduct = Parcels.unwrap(mProductParcelable);
        if (mProduct != null) {
            textViewTitle.setText(mProduct.getTitle());

            // Image
            String url = mProduct.getTitleImageUrl();
            productImageView.setErrorImageResId(R.drawable.fairmondo);
            if (mProduct.getTitleImage() != null && !mProduct.getTitleImage().getOriginalUrl().equals("")) {
                url = mProduct.getTitleImage().getOriginalUrl();
            }
            if (url != null) {
                app.getImageLoader().get(url, new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(final ImageLoader.ImageContainer response, final boolean isImmediate) {
                        final Bitmap bitmap = response.getBitmap();
                        if (bitmap != null) {
                            setIndividualFragmentColors(bitmap);
                        }
                    }

                    @Override
                    public void onErrorResponse(final VolleyError error) {
                    }
                });
            }

            // Description
            if (mProduct.getContent() != null && !mProduct.getContent().equals("")) {
                textViewDescriptionBody.setText(this.parseHtml(mProduct.getContent()));
            }

            // Terms
            if (mProduct.getSeller() != null && mProduct.getSeller().getTerms() != null && !mProduct.getSeller().getTerms().equals("")) {
                textViewTermsTitle.setVisibility(View.VISIBLE);
            }

            // Fair Percent        android:layout_alignParentRight="true"

            if (mProduct.getTransportHtml() != null && !mProduct.getTransportHtml().equals("")) {
                textViewTransportBody.setText(this.parseHtml(mProduct.getTransportHtml()));
            }

            // Payment
            if (mProduct.getPaymentHtml() != null && !mProduct.getPaymentHtml().equals("")) {
                textViewPaymentBody.setText(this.parseHtml(mProduct.getPaymentHtml()));
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
            textViewPrice.setText(price + " " + format.getCurrency().getSymbol());

            if (mProduct.getVat() > 0) {
                textViewVat.setVisibility(View.GONE);
            }

            // Click Listeners
            textViewTermsTitle.setOnClickListener(this);
            buttonBuy.setOnClickListener(this);
        }
    }

    /**
     * parse the html text retrieved from the Fairmondo server to normal text.
     */
    private Spanned parseHtml(final String html) {
        return Html.fromHtml(html);
    }

    @UiThread
    public void setIndividualFragmentColors(final Bitmap bitmap) {
        Palette.Builder builder = Palette.from(bitmap);
        builder.generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(final Palette palette) {
                int background = palette.getLightMutedColor(R.color.fairmondo_gray_light);
                int foreground = palette.getVibrantColor(R.color.fairmondo_gray_light);
                layoutContent.setBackgroundColor(background);
                productImageView.setBackgroundColor(background);
                // buttonBuy.setBackgroundColor(foreground);
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
    public void onClick(final View view) {
        if (view.getId() == R.id.fragment_product_button_buy) {
            restController.addToCard(Integer.parseInt(mProduct.getId()));
        } else if (view.getId() == R.id.fragment_product_textview_terms_title) {
            WebActivity_.intent(getActivity()).mHtml(mProduct.getSeller().getTerms()).start();
        }
    }
}
