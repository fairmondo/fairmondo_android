package de.handler.mobile.android.shopprototype.rest;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.api.BackgroundExecutor;
import org.androidannotations.api.rest.RestErrorHandler;
import org.springframework.core.NestedRuntimeException;

import de.handler.mobile.android.shopprototype.R;


/**
 * Handles Rest Error Messages
 * If an error occurs while communicating with the
 * server this error handler is called
 */
@EBean
public class RestServiceErrorHandler implements RestErrorHandler {

    private Context mContext;

    @Override
    public void onRestClientExceptionThrown(NestedRuntimeException e) {
        Log.e("REST_ERROR_HANDLER", e.getMessage());
        BackgroundExecutor.cancelAll("cancellable_task", true);
        this.showToast(e.getLocalizedMessage());
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    @UiThread
    public void showToast(String message) {


            if (mContext != null) {
                if (message.contains("500") || message.contains("expected")) {
                    message = mContext.getString(R.string.server_error);
                } else if (message.contains("502") || message.contains("I/O error")) {
                    message = mContext.getString(R.string.server_temporarily_not_available);
                } else if (message.contains("404")) {
                    message = mContext.getString(R.string.server_method_not_available);
                }

                // Show a Toast in corresponding activity with error message
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }

    }
}
