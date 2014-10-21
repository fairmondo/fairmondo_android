package de.handler.mobile.android.shopprototype.rest;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.RestClientErrorHandling;
import org.springframework.http.converter.json.GsonHttpMessageConverter;


/**
 * Connects the app to the Fairmondo server via restful requests
 * returns JSON
 */
@Rest(rootUrl = "https://www.fairmondo.de/articles.json?article_search_form", converters = GsonHttpMessageConverter.class)
public interface FairmondoRestService extends RestClientErrorHandling {

    @Get("[q]={searchString}&article_search_form[category_id]={categoryId}")
    public Articles getProduct(String searchString, int categoryId);

    @Get("[q]={searchString}")
    public Articles getProduct(String searchString);


}
