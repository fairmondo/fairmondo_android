package de.handler.mobile.android.fairmondo.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import de.handler.mobile.android.fairmondo.FairmondoApp;
import de.handler.mobile.android.fairmondo.R;
import de.handler.mobile.android.fairmondo.data.businessobject.Cart;
import de.handler.mobile.android.fairmondo.data.businessobject.product.FairmondoCategory;
import de.handler.mobile.android.fairmondo.data.interfaces.OnCartChangeListener;
import de.handler.mobile.android.fairmondo.data.interfaces.OnCategoriesListener;
import de.handler.mobile.android.fairmondo.data.interfaces.OnDetailedProductListener;
import de.handler.mobile.android.fairmondo.data.interfaces.OnNetworkAvailableListener;
import de.handler.mobile.android.fairmondo.data.interfaces.OnSearchResultListener;
import de.handler.mobile.android.fairmondo.network.FairmondoRestService;
import de.handler.mobile.android.fairmondo.network.RestServiceErrorHandler;
import de.handler.mobile.android.fairmondo.network.dto.Articles;
import de.handler.mobile.android.fairmondo.network.dto.Product;
import de.handler.mobile.android.fairmondo.presentation.controller.UIInformationController;
import de.handler.mobile.android.fairmondo.presentation.interfaces.SharedPrefs_;

/**
 * Encapsulates all communication with the server
 */
@EBean
public class RestCommunicator {
    @Bean
    RestServiceErrorHandler mErrorHandler;

    @RestService
    FairmondoRestService mRestService;

    @Pref
    SharedPrefs_ mPrefs;

    @App
    FairmondoApp mApp;

    private OnSearchResultListener mProductListener;
    private OnCategoriesListener mCategoriesListener;
    private OnDetailedProductListener mDetailedProductListener;
    private OnCartChangeListener mCartChangeListener;
    private final Context mContext;

    public RestCommunicator(@NonNull final Context context) {
        mContext = context;
    }

    @AfterInject
    public void initRestService() {
        mErrorHandler.setContext(mContext);
        mRestService.setRestErrorHandler(mErrorHandler);
    }

    /**
     * Sets the OnSearchResultListener which informs the implementing class that the products have been fetched.
     */
    public void setProductListener(@NonNull final OnSearchResultListener productListener) {
        mProductListener = productListener;
    }

    /**
     * Sets the OnCategoriesListener which informs the implementing class that the categories and sub categories have been fetched.
     */
    public void setCategoriesListener(@NonNull final OnCategoriesListener categoriesListener) {
        mCategoriesListener = categoriesListener;
    }

    /**
     * Sets the OnDetailedProductListener which informs the implementing class that the detailed product information has been fetched.
     */
    public void setDetailedProductListener(@NonNull final OnDetailedProductListener detailedProductListener) {
        mDetailedProductListener = detailedProductListener;
    }

    /**
     * Sets the OnCartChangeListener which informs the implementing class that the cart has been updated.
     */
    public void setCartChangeListener(@NonNull final OnCartChangeListener cartChangeListener) {
        mCartChangeListener = cartChangeListener;
    }

    @Background
    public void getProducts(@NonNull final String searchString) {
        if (mApp.isConnected()) {
            final Articles articles = mRestService.getProducts(searchString);
            final Type listType = new TypeToken<List<de.handler.mobile.android.fairmondo.data.businessobject.Product>>() { }.getType();
            if (articles == null || articles.articles == null) {
                mProductListener.onProductsSearchResponse(null);
            } else {
                List<de.handler.mobile.android.fairmondo.data.businessobject.Product> products = mApp.getModelMapper().map(articles.articles, listType);
                mProductListener.onProductsSearchResponse(products);
            }
        } else {
            this.makeNetworkDialog(mContext.getString(R.string.app_not_connected), new OnNetworkAvailableListener() {
                @Override
                public void onNetworkAvailable() {
                    getProducts(searchString);
                }
            });
        }

    }

    @Background
    public void getProducts(@NonNull final String searchString, @NonNull final String categoryId) {
        if (mApp.isConnected()) {
            final Articles articles = mRestService.getProducts(searchString, categoryId);
            if (articles == null || articles.articles == null || articles.articles.length < 1) {
                mProductListener.onProductsSearchResponse(null);
                Log.e(getClass().getCanonicalName(), " getProducts: articles are null");
            } else {
                final Type listType = new TypeToken<List<de.handler.mobile.android.fairmondo.data.businessobject.Product>>() { }.getType();
                final List<de.handler.mobile.android.fairmondo.data.businessobject.Product> products = mApp.getModelMapper().map(Arrays.asList(articles.articles), listType);
                mProductListener.onProductsSearchResponse(products);
            }
        } else {
            this.makeNetworkDialog(mContext.getString(R.string.app_not_connected), new OnNetworkAvailableListener() {
                @Override
                public void onNetworkAvailable() {
                    getProducts(searchString, categoryId);
                }
            });
        }
    }

    @Background
    public void getProducts(@NonNull final String searchString, @NonNull final String categoryId, @NonNull final Integer page) {
        if (mApp.isConnected()) {
            final Articles articles = mRestService.getProducts(searchString, categoryId, page);
            if (articles == null || articles.articles == null || articles.articles.length < 1) {
                mProductListener.onProductsSearchResponse(null);
                Log.e(getClass().getCanonicalName(), " getProducts: articles are null");
            } else {
                final Type listType = new TypeToken<List<de.handler.mobile.android.fairmondo.data.businessobject.Product>>() { }.getType();
                final List<de.handler.mobile.android.fairmondo.data.businessobject.Product> products = mApp.getModelMapper().map(Arrays.asList(articles.articles), listType);
                mProductListener.onProductsSearchResponse(products);
            }
        } else {
            this.makeNetworkDialog(mContext.getString(R.string.app_not_connected), new OnNetworkAvailableListener() {
                @Override
                public void onNetworkAvailable() {
                    getProducts(searchString, categoryId);
                }
            });
        }
    }

    @Background
    public void getDetailedProduct(@NonNull final String slug) {
        if (mApp.isConnected()) {
            final Product article = mRestService.getDetailedProduct(slug);
            if (article == null) {
                mDetailedProductListener.onDetailedProductResponse(null);
            } else {
                final de.handler.mobile.android.fairmondo.data.businessobject.Product product = mApp.getModelMapper().map(article, de.handler.mobile.android.fairmondo.data.businessobject.Product.class);
                mDetailedProductListener.onDetailedProductResponse(product);
            }
        } else {
            this.makeNetworkDialog(mContext.getString(R.string.app_not_connected), new OnNetworkAvailableListener() {
                @Override
                public void onNetworkAvailable() {
                    getDetailedProduct(slug);
                }
            });
        }
    }

    @Background(id = "cancellable_task")
    public void getCategories() {
        if (mApp.isConnected()) {
            final Type listType = new TypeToken<List<FairmondoCategory>>() { }.getType();
            final List<de.handler.mobile.android.fairmondo.network.dto.product.FairmondoCategory> dtoCategories = mRestService.getCategories();
            if (null != dtoCategories) {
                final List<FairmondoCategory> categories = mApp.getModelMapper().map(dtoCategories, listType);
                mCategoriesListener.onCategoriesResponse(categories);
            }
        } else {
            this.makeNetworkDialog(mContext.getString(R.string.app_not_connected), new OnNetworkAvailableListener() {
                @Override
                public void onNetworkAvailable() {
                    getCategories();
                }
            });
        }
    }

    @Background(id = "cancellable_task")
    public void getSubCategories(@NonNull final String id) {
        if (mApp.isConnected()) {
            final Type listType = new TypeToken<List<FairmondoCategory>>() {
            }.getType();
            final List<de.handler.mobile.android.fairmondo.network.dto.product.FairmondoCategory> dtoCategories = mRestService.getSubCategories(id);
            final List<FairmondoCategory> categories = mApp.getModelMapper().map(dtoCategories, listType);
            mCategoriesListener.onSubCategoriesResponse(categories);
        } else {
            this.makeNetworkDialog(mContext.getString(R.string.app_not_connected), new OnNetworkAvailableListener() {
                @Override
                public void onNetworkAvailable() {
                    getSubCategories(id);
                }
            });
        }
    }

    @Background(id = "cancellable_task")
    public void addToCard(@NonNull final String productId) {
        if (mApp.isConnected()) {
            mRestService.setCookie("cart", mApp.getCookie());
            final de.handler.mobile.android.fairmondo.network.dto.Cart cartDTO = mRestService.addProductToCart(productId, 1);
            final Cart cart = mApp.getModelMapper().map(cartDTO, Cart.class);
            final String cookie = mRestService.getCookie("cart");

            if (null == cart || null == cart.getCartId()) {
                this.makeToast(mContext.getString(R.string.item_amount_too_big));
            } else {
                mApp.setCookie(cookie);
                mPrefs.cookie().put(cookie);
                mApp.setCart(cart);
                mCartChangeListener.onCartChanged(cart);
            }
        } else {
            this.makeNetworkDialog(mContext.getString(R.string.app_not_connected), new OnNetworkAvailableListener() {
                @Override
                public void onNetworkAvailable() {
                    addToCard(productId);
                }
            });
        }
    }

    @UiThread
    public void makeToast(final String text) {
        UIInformationController.displayToastInformation(mContext, text);
    }

    @UiThread
    public void makeNetworkDialog(final String text, final OnNetworkAvailableListener listener) {
        UIInformationController.displayDialogInformation(mContext, text, listener).show();
    }
}
