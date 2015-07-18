package de.handler.mobile.android.fairmondo.presentation.fragments;


import android.graphics.Color;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import de.handler.mobile.android.fairmondo.data.interfaces.OnDetailedProductListener;
import de.handler.mobile.android.fairmondo.presentation.activities.WebActivity_;
import de.handler.mobile.android.fairmondo.presentation.controller.ProgressController;
import de.handler.mobile.android.fairmondo.presentation.views.CustomNetworkImageView;

/**
 * Displays one product.
 */
@EFragment(R.layout.fragment_product)
public class ProductFragment extends Fragment implements OnCartChangeListener, OnDetailedProductListener, View.OnClickListener {
    @FragmentArg
    Parcelable mProductParcelable;

    @App
    FairmondoApp mApp;

    @Bean
    RestCommunicator mRestController;

    @Bean
    ProgressController mProgressController;

    @ViewById(R.id.fragment_product_image_view)
    CustomNetworkImageView mProductImageView;

    @ViewById(R.id.fragment_product_content_container)
    RelativeLayout mLayoutContent;

    @ViewById(R.id.fragment_product_title)
    TextView mTextViewTitle;

    @ViewById(R.id.fragment_product_textview_price)
    TextView mTextViewPrice;

    @ViewById(R.id.fragment_product_textview_price_vat)
    TextView mTextViewVat;

    @ViewById(R.id.fragment_product_textview_donation_title)
    TextView mTextViewDonation;

    @ViewById(R.id.fragment_product_textview_description_title)
    TextView mTextViewDescriptionTitle;

    @ViewById(R.id.fragment_product_textview_terms_title)
    TextView mTextViewTermsTitle;

    @ViewById(R.id.fragment_product_textview_description_body)
    TextView mTextViewDescriptionBody;

    @ViewById(R.id.fragment_product_textview_transport_body)
    TextView mTextViewTransportBody;

    @ViewById(R.id.fragment_product_textview_payment_body)
    TextView mTextViewPaymentBody;

    @ViewById(R.id.fragment_product_textview_condition_title)
    TextView mTextViewConditionTitle;

    @ViewById(R.id.fragment_product_textview_condition_body)
    TextView mTextViewConditionBody;

    @ViewById(R.id.fragment_product_button_buy)
    Button mButtonBuy;

    private Product mProduct;

    @AfterViews
    public void init() {
        mRestController.setCartChangeListener(this);
        mRestController.setDetailedProductListener(this);

        mProgressController.startProgress(getFragmentManager(), android.R.id.content);
        mProduct = Parcels.unwrap(mProductParcelable);
        mRestController.getDetailedProduct(mProduct.getSlug());
    }

    @Override
    public void onDetailedProductResponse(final Product product) {
        if (null != product) {
            this.displayProductData(product);
            this.setOnClickListeners();
        }
        mProgressController.stopProgress();
    }

    @UiThread
    void displayProductData(@NonNull final Product product) {
        // TODO display fair, öko, etc tags
        mTextViewTitle.setText(product.getTitle());

        // Image
        String url = product.getTitleImageUrl();
        mProductImageView.setErrorImageResId(R.drawable.fairmondo);
        if (null != product.getTitleImage() && !product.getTitleImage().getOriginalUrl().equals("")) {
            url = product.getTitleImage().getOriginalUrl();
        }

        mProductImageView.setImageUrl(url, mApp.getImageLoader());

        // Description
        if (null != product.getContent() && !product.getContent().equals("")) {
            mTextViewDescriptionBody.setText(this.parseHtml(product.getContent()));
        }

        // Terms
        if (null != product.getSeller() && null != product.getSeller().getTerms() && !product.getSeller().getTerms().equals("")) {
            mTextViewTermsTitle.setVisibility(View.VISIBLE);
        }

        // Fair Percent
        if (null != product.getTransportHtml() && !product.getTransportHtml().equals("")) {
            mTextViewTransportBody.setText(this.parseHtml(product.getTransportHtml()));
        }

        // Payment
        if (null != product.getPaymentHtml() && !product.getPaymentHtml().equals("")) {
            mTextViewPaymentBody.setText(this.parseHtml(product.getPaymentHtml()));
        }

        // Donation
        if (null != product.getDonation()) {
            mTextViewDonation.setText(
                    "Diese*r Anbieter*in spendet "
                            + product.getDonation().getPercent()
                            + " % an "
                            + product.getDonation().getOrganization().getName());
        }

        if (null != product.getTags()) {
            mTextViewConditionBody.setText(product.getTags().getCondition());
            mTextViewConditionTitle.setVisibility(View.VISIBLE);
        }

        // Price
        final double priceValue = (product.getPriceCents() / 100.00);
        // Localized price value (e.g. instead of '.' use ',' in German) and corresponding currency
        final NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
        format.setMinimumFractionDigits(2);
        final String price = format.format(priceValue);
        mTextViewPrice.setText(price + " €");

        if (product.getVat() > 0) {
            mTextViewVat.setVisibility(View.GONE);
        }
    }

    @UiThread
    void setOnClickListeners() {
        // Click Listeners
        mTextViewTermsTitle.setOnClickListener(this);
        mButtonBuy.setOnClickListener(this);
    }

    /**
     * parse the html text retrieved from the Fairmondo server to normal text.
     */
    private Spanned parseHtml(@NonNull final String html) {
        return Html.fromHtml(html);
    }

    @UiThread
    @Override
    public void onCartChanged(final Cart cart) {
        if (null != cart && null != cart.getCartItem()) {
            final int itemCount = cart.getCartItem().getRequestedQuantity();
            Snackbar.make(mLayoutContent, itemCount + " Element zum Einkaufswagen hinzugefügt", Snackbar.LENGTH_SHORT)
                    .setAction("Zum Einkaufswagen",
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            WebActivity_.intent(getActivity()).mUri(cart.getCartUrl()).mCookie(mApp.getCookie()).start();
                        }
                    })
                    .setActionTextColor(Color.YELLOW)
                    .show();
        }
    }

    @Override
    public void onClick(final View view) {
        if (view.getId() == R.id.fragment_product_button_buy) {
            mRestController.addToCard(mProduct.getId());
        } else if (view.getId() == R.id.fragment_product_textview_terms_title) {
            WebActivity_.intent(getActivity()).mHtml(mProduct.getSeller().getTerms()).start();
        }
    }
}
