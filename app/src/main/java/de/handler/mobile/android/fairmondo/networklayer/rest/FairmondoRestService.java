package de.handler.mobile.android.fairmondo.networklayer.rest;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.RequiresCookie;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.annotations.rest.SetsCookie;
import org.androidannotations.api.rest.RestClientErrorHandling;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import java.util.ArrayList;

import de.handler.mobile.android.fairmondo.datasource.database.Category;
import de.handler.mobile.android.fairmondo.networklayer.rest.json.Article;
import de.handler.mobile.android.fairmondo.networklayer.rest.json.model.Cart;


/**
 * Connects the app to the Fairmondo server via restful requests
 * returns JSON
 */
@Rest(rootUrl = "https://www.fairmondo.de/", converters = {GsonHttpMessageConverter.class}, interceptors = {HttpResponseInterceptor.class})
public interface FairmondoRestService extends RestClientErrorHandling {

    @Get("articles.json?article_search_form[q]={searchString}&article_search_form[category_id]={categoryId}")
    public Articles getProduct(String searchString, int categoryId);

    @Get("articles.json?article_search_form[q]={searchString}")
    public Articles getProduct(String searchString);

    @Get("articles/{slug}.json")
    public Article getDetailedProduct(String slug);

    @Get("categories.json")
    public ArrayList<Category> getCategories();

    @Get("categories/{id}.json")
    public ArrayList<Category> getSubCategories(int id);

    @SetsCookie({"cart"})
    @RequiresCookie({"cart"})
    @Post("line_items.json?line_item[article_id]={productId}&line_item[requested_quantity]={quantity}")
    public Cart addProductToCart(int productId, int quantity);

    void setCookie(String name, String value);
    String getCookie(String name);
}
