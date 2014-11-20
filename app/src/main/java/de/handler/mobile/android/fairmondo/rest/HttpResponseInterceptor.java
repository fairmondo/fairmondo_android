package de.handler.mobile.android.fairmondo.rest;

import android.util.Log;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

import de.handler.mobile.android.fairmondo.FairmondoApp;

/**
 * Intercepts the response from the server
 */
@EBean(scope = EBean.Scope.Singleton)
public class HttpResponseInterceptor implements ClientHttpRequestInterceptor {

    @App
    FairmondoApp app;

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        HttpHeaders headers = httpRequest.getHeaders();
        Log.d("\n HTTP_INTERCEPTOR: URI ", httpRequest.getURI().toString());
        Log.d("\n HTTP_INTERCEPTOR: URI ", headers.toString());

        ClientHttpResponse response = clientHttpRequestExecution.execute(httpRequest, bytes);
        Log.d("\n HTTP_INTERCEPTOR: RESPONSE ", response.getStatusText());

        return response;
    }
}
