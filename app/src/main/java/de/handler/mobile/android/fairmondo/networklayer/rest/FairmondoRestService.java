package de.handler.mobile.android.fairmondo.networklayer.rest;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.RequiresCookie;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.annotations.rest.SetsCookie;
import org.androidannotations.api.rest.RestClientErrorHandling;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import java.util.List;

import de.handler.mobile.android.fairmondo.networklayer.rest.dto.Product;
import de.handler.mobile.android.fairmondo.networklayer.rest.dto.model.Cart;
import de.handler.mobile.android.fairmondo.networklayer.rest.dto.model.article.FairmondoCategory;

/**
 * Connects the app to the Fairmondo server via restful requests
 * returns JSON
 */
@Rest(rootUrl = "", converters = {GsonHttpMessageConverter.class}, interceptors = {HttpResponseInterceptor.class})
public interface FairmondoRestService extends RestClientErrorHandling {

    @Get("https://www.fairmondo.de/articles.json?article_search_form[q]={searchString}&article_search_form[category_id]={categoryId}")
    public Articles getProducts(String searchString, int categoryId);

    @Get("https://www.fairmondo.de/articles.json?article_search_form[q]={searchString}")
    public Articles getProducts(String searchString);

    @Get("{url}.json")
    public Product getDetailedProduct(String url);

    @Get("https://www.fairmondo.de/categories.json")
    public List<FairmondoCategory> getCategories();

    @Get("https://www.fairmondo.de/categories/{id}.json")
    public List<FairmondoCategory> getSubCategories(int id);

    @SetsCookie({"cart"})
    @RequiresCookie({"cart"})
    @Post("https://www.fairmondo.de/line_items.json?line_item[article_id]={productId}&line_item[requested_quantity]={quantity}")
    public Cart addProductToCart(int productId, int quantity);

    void setCookie(String name, String value);
    String getCookie(String name);
}
