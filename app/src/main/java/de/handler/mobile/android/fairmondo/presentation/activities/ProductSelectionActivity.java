package de.handler.mobile.android.fairmondo.presentation.activities;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;
import org.parceler.Parcels;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.handler.mobile.android.fairmondo.FairmondoApp;
import de.handler.mobile.android.fairmondo.R;
import de.handler.mobile.android.fairmondo.data.businessobject.Cart;
import de.handler.mobile.android.fairmondo.data.businessobject.Product;
import de.handler.mobile.android.fairmondo.presentation.FragmentHelper;
import de.handler.mobile.android.fairmondo.presentation.controller.UIInformationController;
import de.handler.mobile.android.fairmondo.presentation.fragments.FilterFragment_;
import de.handler.mobile.android.fairmondo.presentation.fragments.ProductSelectionFragment;
import de.handler.mobile.android.fairmondo.presentation.fragments.ProductSelectionFragment_;
import de.handler.mobile.android.fairmondo.presentation.interfaces.OnFilterSelectedListener;

/**
 * An Activity displaying products.
 * Either all or a selection.
 */
@EActivity(R.layout.activity_product_selection)
@OptionsMenu(R.menu.product_selection)
public class ProductSelectionActivity extends AbstractActivity implements OnFilterSelectedListener {
    @Extra
    Parcelable mProductListParcelable;

    @App
    FairmondoApp mApp;

    @ViewById(R.id.activity_product_selection_toolbar)
    Toolbar mToolbar;

    private Fragment mFilterFragment;
    private List<Product> mProducts;

    @AfterViews
    void init() {
        setHomeUpEnabled(mToolbar);

        mFilterFragment = FilterFragment_.builder().build();
        mProducts = Parcels.unwrap(mProductListParcelable);
        this.startProductSelectionFragment(mProducts);
    }

    /**
     * Is called when the action item "show cart" is called and leads to the web cart page.
     */
    @OptionsItem(R.id.action_cart)
    void openCart() {
        final Cart cart = mApp.getCart();
        if (cart == null || cart.getCartItem() == null || cart.getCartItem().getRequestedQuantity() <= 0) {
            UIInformationController.displaySnackbarInformation(findViewById(R.id.activity_product_selection_main_container), getString(R.string.cart_has_no_items));
        } else {
            WebActivity_.intent(this).mUri(cart.getCartUrl()).mCookie(mApp.getCookie()).start();
        }
    }

    /**
     * Is called when the action item "show cart" is called and leads to the web cart page.
     */
    @OptionsItem(R.id.action_filter)
    void filterProducts() {
        FragmentHelper.replaceFragment(android.R.id.content, mFilterFragment, getSupportFragmentManager());
    }

    @Override
    public void onPriceFilterSelected(final boolean selection) {
        final List<Product> products = this.sortProductsForPrice(mProducts);
        this.startProductSelectionFragment(products);
    }

    @Override
    public void onAlphabeticalFilterSelected(final boolean selection) {
        final List<Product> products = this.sortProductsAlphabetically(mProducts);
        this.startProductSelectionFragment(products);
    }

    @Override
    public void onConditionFilterSelected(boolean selection) {
        final List<Product> products = this.sortProductsForCondition(mProducts);
        this.startProductSelectionFragment(products);
    }

    @Override
    public void onFilterFinish() {
        FragmentHelper.removeFragment(mFilterFragment, getSupportFragmentManager());
    }

    @NonNull
    private List<Product> sortProductsForPrice(@NonNull final List<Product> products) {
        Collections.sort(products, new ProductPriceComparator());
        return products;
    }

    @NonNull
    private List<Product> sortProductsAlphabetically(@NonNull final List<Product> products) {
        Collections.sort(products, new ProductAlphabetComparator());
        return products;
    }

    @NonNull
    private List<Product> sortProductsForCondition(@NonNull final List<Product> products) {
        Collections.sort(products, new ProductConditionComparator());
        return products;
    }

    private void startProductSelectionFragment(final @Nullable List<Product> products) {
        ProductSelectionFragment selectionFragment = ProductSelectionFragment_.builder().build();
        if (products != null) {
            selectionFragment = ProductSelectionFragment_.builder().mProductsParcelable(Parcels.wrap(List.class, products)).build();
        }
        FragmentHelper.replaceFragment(R.id.activity_product_selection_main_container, selectionFragment, getSupportFragmentManager());
    }

    @OptionsItem(android.R.id.home)
    void goUp() {
        finish();
    }

    /**
     * Class for comparing products according to their prices.
     */
    private final class ProductPriceComparator implements Comparator<Product> {
        @Override
        public int compare(final Product leftProduct, final Product rightProduct) {
            return leftProduct.getPriceCents().compareTo(rightProduct.getPriceCents());
        }
    }

    /**
     * Class for comparing product titles alphabetically.
     */
    private final class ProductAlphabetComparator implements Comparator<Product> {
        @Override
        public int compare(final Product leftProduct, final Product rightProduct) {
            return leftProduct.getTitle().compareTo(rightProduct.getTitle());
        }
    }

    /**
     * Class for comparing product titles alphabetically.
     */
    private final class ProductConditionComparator implements Comparator<Product> {
        @Override
        public int compare(final Product leftProduct, final Product rightProduct) {
            if (null != leftProduct.getTags() && null != rightProduct.getTags()) {
                return leftProduct.getTags().getCondition().compareTo(rightProduct.getTags().getCondition());
            }
            return 0;
        }
    }
}
