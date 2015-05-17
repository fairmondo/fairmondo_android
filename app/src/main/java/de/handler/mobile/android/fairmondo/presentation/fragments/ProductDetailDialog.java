package de.handler.mobile.android.fairmondo.presentation.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;

import org.androidannotations.annotations.EFragment;

import de.handler.mobile.android.fairmondo.R;

/**
 * Displays more detailed information from within the product fragment.
 * concerning the html content
 */
@EFragment
public class ProductDetailDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        // Get the layout inflater and inflate the layout
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_detail_product, null);

        String httpContent = getArguments().getString(WebFragment.HTTP_CONTENT);
        WebView webView = (WebView) view.findViewById(R.id.dialog_product_detail_webview);
        webView.loadData(httpContent, "text/html; charset=UTF-8", null);

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder .setView(view)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, final int which) {

                            }
                        });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
