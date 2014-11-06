package de.handler.mobile.android.shopprototype.rest;

import android.util.Log;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.List;

import de.handler.mobile.android.shopprototype.ShopApp;

/**
 * Intercepts the response from the server
 */
@EBean(scope = EBean.Scope.Singleton)
public class HttpResponseInterceptor implements ClientHttpRequestInterceptor {

    @App
    ShopApp app;

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        HttpHeaders headers = httpRequest.getHeaders();
        Log.d("\n HTTP_INTERCEPTOR: URI ", httpRequest.getURI().toString());

        //headers.add("Set-Cookie", app.getCookie());
        List<String> cookies = headers.get("Set-Cookie");
        //Log.d("\n HTTP_INTERCEPTOR: COOKIES SIZE ", String.valueOf(cookies.size()));


        ClientHttpResponse response = clientHttpRequestExecution.execute(httpRequest, bytes);
        Log.d("\n HTTP_INTERCEPTOR: RESPONSE ", response.getStatusText());

        //cookies = response.getHeaders().get("Set-Cookie");
        //if (cookies.get(0).contains("cart")) {
        //    app.setCookie(cookies.get(0));
        //}
        return response;
    }
}
