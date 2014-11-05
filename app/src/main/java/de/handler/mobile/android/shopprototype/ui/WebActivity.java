package de.handler.mobile.android.shopprototype.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import de.handler.mobile.android.shopprototype.R;
import de.handler.mobile.android.shopprototype.ShopApp;

/**
 * Contains a webView for displaying homepages
 */
@EActivity(R.layout.activity_web)
public class WebActivity extends AbstractActivity {

    public static final String URI = "activity_web_uri";
    public static final String HTTP_CONTENT = "http_content";


    @ViewById(R.id.activity_web_web_view)
    WebView webView;

    @App
    ShopApp app;


    @AfterViews
    void init() {
        ActionBar actionBar = this.setupActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        String http = getIntent().getStringExtra(HTTP_CONTENT);
        String uri = getIntent().getStringExtra(URI);

        if (http != null) {
            webView.loadData(http, "text/html; charset=UTF-8", null);

        } else if (app.isConnected()) {

            if (uri != null && !uri.equals("")) {
                webView.setWebViewClient(new RedirectWebViewClient());
                WebSettings webSettings = webView.getSettings();
                webSettings.setJavaScriptEnabled(true);

                webView.loadUrl(uri);
            }

        } else {
            Toast.makeText(this, getString(R.string.app_not_connected), Toast.LENGTH_SHORT).show();
            this.finish();
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
            finish();
            return true;
        }
    }

}
