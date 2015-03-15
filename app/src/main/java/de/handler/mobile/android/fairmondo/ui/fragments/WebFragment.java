package de.handler.mobile.android.fairmondo.ui.fragments;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import de.handler.mobile.android.fairmondo.R;

/**
 * Displays Web Content.
 */
@EFragment(R.layout.fragment_web)
public class WebFragment extends Fragment {
    public static final String URI = "activity_web_uri";
    public static final String HTTP_CONTENT = "http_content";
    public static final String COOKIE = "cart_cookie";

    private String mCookie = "";

    @ViewById(R.id.fragment_web_webview)
    WebView webView;

    @ViewById(R.id.fragment_web_progress_container)
    RelativeLayout progressContainer;

    @AfterViews
    public void init() {
        // Get the bundle arguments
        String html = getArguments().getString(HTTP_CONTENT);
        String uri = getArguments().getString(URI);
        mCookie = getArguments().getString(COOKIE);

        // if the html string is null a uri shall be displayed
        if (html == null) {
            if (uri == null || uri.equals("")) {
                return;
            }
            progressContainer.setVisibility(View.VISIBLE);
            if (mCookie == null) {
                webView.setWebViewClient(new RedirectWebViewClient());
            } else {
                this.setCookie(webView, mCookie, uri);
            }
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webView.loadUrl(uri);
        } else {
            webView.loadData(html, "text/html; charset=UTF-8", null);
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    void setCookie(WebView webView, String cookie, String uri) {
        webView.setWebViewClient(new RedirectWebViewClient(false));
        // Since LOLLIPOP the cookie manager has not to be explicitly retrieved any more.
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.createInstance(getActivity());
        }

        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setCookie(uri, "cart=" + cookie);
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.getInstance().sync();
        }
    }

    /**
     * Web View Client which
     * 1. Displays a ProgressBar until the content is completely shown and
     * 2. Prompts the user to use its own browser to be able to navigate to further links.
     * Workaround for dividing the app from the internet page as some content as the cart
     * up to now only works on the internet page.
     */
    private class RedirectWebViewClient extends WebViewClient {
        boolean handleKeyEvent = true;

        RedirectWebViewClient (boolean handleKeyEvent) {
            this.handleKeyEvent = handleKeyEvent;
        }

        RedirectWebViewClient() {}

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            super.shouldOverrideUrlLoading(view, url);
            Log.d(getClass().getCanonicalName(), "redirected to: " + url);

            if (handleKeyEvent) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            } else {
                if (mCookie != null) {
                    setCookie(webView, mCookie, url);
                }
            }
            return handleKeyEvent;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressContainer.setVisibility(View.GONE);
        }
    }
}
