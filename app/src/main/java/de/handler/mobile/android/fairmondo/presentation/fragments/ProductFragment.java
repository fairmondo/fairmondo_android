package de.handler.mobile.android.fairmondo.presentation.fragments;


import android.os.Build;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    FairmondoApp mApp;

    @Bean
    RestCommunicator mRestController;

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

    @ViewById(R.id.fragment_product_button_buy)
    Button mButtonBuy;

    private Product mProduct;

    @AfterViews
    public void init() {
        mRestController.setCartChangeListener(this);
        mProduct = Parcels.unwrap(mProductParcelable);
        if (mProduct != null) {
            this.displayProductData();
            this.setOnClickListeners();
        }
    }

    private void displayProductData() {

        mTextViewTitle.setText(mProduct.getTitle());

        // Image
        String url = mProduct.getTitleImageUrl();
        mProductImageView.setErrorImageResId(R.drawable.fairmondo);
        if (mProduct.getTitleImage() != null && !mProduct.getTitleImage().getOriginalUrl().equals("")) {
            url = mProduct.getTitleImage().getOriginalUrl();
        }

        mProductImageView.setImageUrl(url, mApp.getImageLoader());

        // Description
        if (mProduct.getContent() != null && !mProduct.getContent().equals("")) {
            mTextViewDescriptionBody.setText(this.parseHtml(mProduct.getContent()));
        }

        // Terms
        if (mProduct.getSeller() != null && mProduct.getSeller().getTerms() != null && !mProduct.getSeller().getTerms().equals("")) {
            mTextViewTermsTitle.setVisibility(View.VISIBLE);
        }

        // Fair Percent
        if (mProduct.getTransportHtml() != null && !mProduct.getTransportHtml().equals("")) {
            mTextViewTransportBody.setText(this.parseHtml(mProduct.getTransportHtml()));
        }

        // Payment
        if (mProduct.getPaymentHtml() != null && !mProduct.getPaymentHtml().equals("")) {
            mTextViewPaymentBody.setText(this.parseHtml(mProduct.getPaymentHtml()));
        }

        // Donation
        if (mProduct.getDonation() != null) {
            mTextViewDonation.setText(
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
        mTextViewPrice.setText(price + " " + format.getCurrency().getSymbol());

        if (mProduct.getVat() > 0) {
            mTextViewVat.setVisibility(View.GONE);
        }
    }

    private void setOnClickListeners() {
        // Click Listeners
        mTextViewTermsTitle.setOnClickListener(this);
        mButtonBuy.setOnClickListener(this);
    }

    /**
     * parse the html text retrieved from the Fairmondo server to normal text.
     */
    private Spanned parseHtml(final String html) {
        return Html.fromHtml(html);
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
            mRestController.addToCard(Integer.parseInt(mProduct.getId()));
        } else if (view.getId() == R.id.fragment_product_textview_terms_title) {
            WebActivity_.intent(getActivity()).mHtml(mProduct.getSeller().getTerms()).start();
        }
    }
}
