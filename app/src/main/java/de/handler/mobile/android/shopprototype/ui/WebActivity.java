package de.handler.mobile.android.shopprototype.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import de.handler.mobile.android.shopprototype.R;

/**
 * Contains a webView for displaying homepages
 */
@EActivity(R.layout.activity_web)
public class WebActivity extends AbstractActivity {

    public static final String URI = "activity_web_uri";


    @ViewById(R.id.activity_web_web_view)
    WebView webView;


    @AfterViews
    void init() {
        ActionBar actionBar = this.setupActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        String uri = getIntent().getStringExtra(URI);

        if (uri != null && !uri.equals("")) {
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
            finish();
            return true;
        }
    }

}
