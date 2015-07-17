package de.handler.mobile.android.fairmondo.network;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.RequiresCookie;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.annotations.rest.SetsCookie;
import org.androidannotations.api.rest.RestClientErrorHandling;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import java.util.List;

import de.handler.mobile.android.fairmondo.network.dto.Articles;
import de.handler.mobile.android.fairmondo.network.dto.Cart;
import de.handler.mobile.android.fairmondo.network.dto.Product;
import de.handler.mobile.android.fairmondo.network.dto.product.FairmondoCategory;

/**
 * Connects the app to the Fairmondo server via restful requests.
 * returns JSON
 */
@Rest(rootUrl = "https://www.fairmondo.de/", converters = {GsonHttpMessageConverter.class}, interceptors = {HttpResponseInterceptor.class})
public interface FairmondoRestService extends RestClientErrorHandling {
    @Get("articles.json?article_search_form[q]={searchString}&article_search_form[category_id]={categoryId}")
    Articles getProducts(final String searchString, final String categoryId);

    @Get("articles.json?article_search_form[q]={searchString}")
    Articles getProducts(final String searchString);

    @Get("articles/{slug}.json")
    Product getDetailedProduct(final String slug);

    @Get("categories.json")
    List<FairmondoCategory> getCategories();

    @Get("categories/{id}.json")
    List<FairmondoCategory> getSubCategories(final String id);

    @SetsCookie({"cart"})
    @RequiresCookie({"cart"})
    @Post("line_items.json?line_item[article_id]={productId}&line_item[requested_quantity]={quantity}")
    Cart addProductToCart(final String productId, final int quantity);

    void setCookie(final String name, final String value);

    String getCookie(final String name);
}
