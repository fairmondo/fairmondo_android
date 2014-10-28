package de.handler.mobile.android.shopprototype.rest;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.RestClientErrorHandling;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import java.util.ArrayList;

import de.handler.mobile.android.shopprototype.rest.json.model.Category;


/**
 * Connects the app to the Fairmondo server via restful requests
 * returns JSON
 */
@Rest(rootUrl = "https://www.fairmondo.de/", converters = GsonHttpMessageConverter.class)
public interface FairmondoRestService extends RestClientErrorHandling {

    @Get("articles.json?article_search_form[q]={searchString}&article_search_form[category_id]={categoryId}")
    public Articles getProduct(String searchString, int categoryId);

    @Get("articles.json?article_search_form[q]={searchString}")
    public Articles getProduct(String searchString);

    @Get("categories.json")
    public ArrayList<Category> getCategories();

    @Get("categories/{id}.json")
    public ArrayList<Category> getSubCategories(int id);

}
