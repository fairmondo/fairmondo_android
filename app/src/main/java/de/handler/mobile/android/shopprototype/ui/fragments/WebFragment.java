package de.handler.mobile.android.shopprototype.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import de.handler.mobile.android.shopprototype.R;
import de.handler.mobile.android.shopprototype.ShopApp;

/**
 * Displays Web Content
 */
@EFragment(R.layout.fragment_web)
public class WebFragment extends Fragment {

    public static final String URI = "activity_web_uri";
    public static final String HTTP_CONTENT = "http_content";

    @App
    ShopApp app;

    @ViewById(R.id.fragment_web_webview)
    WebView webView;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        setRetainInstance(true);
    }

    @AfterViews
    public void init() {

        String http = getArguments().getString(HTTP_CONTENT);
        String uri = getArguments().getString(URI);

        if (http != null) {
            webView.loadData(http, "text/html; charset=UTF-8", null);

        } else if (uri != null && !uri.equals("")) {
                webView.setWebViewClient(new RedirectWebViewClient());
                WebSettings webSettings = webView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webView.loadUrl(uri);
        }
    }

    private class RedirectWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            super.shouldOverrideUrlLoading(view, url);
            Log.d(getClass().getCanonicalName(), "redirected to: " + url);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
            return true;
        }
    }

}
