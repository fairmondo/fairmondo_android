package de.handler.mobile.android.fairmondo.network;

import android.util.Log;

import org.androidannotations.annotations.EBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * Intercepts the response from the server.
 */
@EBean(scope = EBean.Scope.Singleton)
public class HttpResponseInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(final HttpRequest httpRequest, final byte[] bytes, final ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        HttpHeaders headers = httpRequest.getHeaders();
        Log.d("\n HTTP URI ", httpRequest.getURI().toString());
        Log.d("\n HTTP URI ", headers.toString());

        ClientHttpResponse response = clientHttpRequestExecution.execute(httpRequest, bytes);
        Log.d("\n HTTP RESPONSE ", response.getStatusText());
        return response;
    }
}
