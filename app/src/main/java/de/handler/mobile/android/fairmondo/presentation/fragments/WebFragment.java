package de.handler.mobile.android.fairmondo.presentation.fragments;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
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
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import de.handler.mobile.android.fairmondo.R;

/**
 * Displays Web Content.
 */
@EFragment(R.layout.fragment_web)
public class WebFragment extends Fragment {
    @FragmentArg
    String mHtml;

    @FragmentArg
    String mUri;

    @FragmentArg
    String mCookie;

    @ViewById(R.id.fragment_web_webview)
    WebView mWebView;

    @ViewById(R.id.fragment_web_progress_container)
    RelativeLayout mProgressContainer;

    @AfterViews
    public void init() {
        // if the html string is null a uri shall be displayed
        if (null == mHtml) {
            if (null == mUri || mUri.equals("")) {
                return;
            }

            mProgressContainer.setVisibility(View.VISIBLE);
            if (null == mCookie) {
                mWebView.setWebViewClient(new RedirectWebViewClient());
            } else {
                this.setCookie(mWebView, mCookie, mUri);
            }

            final WebSettings webSettings = mWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            mWebView.loadUrl(mUri);
        } else {
            mWebView.loadData(mHtml, "text/html; charset=UTF-8", null);
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    void setCookie(@NonNull final WebView webView, @NonNull final String cookie, @NonNull final String uri) {
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
        private boolean mHandleKeyEvent = true;

        RedirectWebViewClient (final boolean handleKeyEvent) {
            this.mHandleKeyEvent = handleKeyEvent;
        }

        RedirectWebViewClient() { }

        @Override
        public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
            super.shouldOverrideUrlLoading(view, url);
            Log.d(getClass().getCanonicalName(), "redirected to: " + url);

            if (mHandleKeyEvent) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            } else {
                if (mCookie != null) {
                    setCookie(mWebView, mCookie, url);
                }
            }
            return mHandleKeyEvent;
        }

        @Override
        public void onPageFinished(final WebView view, final String url) {
            super.onPageFinished(view, url);
            mProgressContainer.setVisibility(View.GONE);
        }
    }
}
