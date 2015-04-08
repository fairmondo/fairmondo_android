package de.handler.mobile.android.fairmondo.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.api.BackgroundExecutor;
import org.androidannotations.api.rest.RestErrorHandler;
import org.springframework.core.NestedRuntimeException;

import de.handler.mobile.android.fairmondo.R;
import de.handler.mobile.android.fairmondo.data.interfaces.OnSearchResultListener;


/**
 * Handles Rest Error Messages
 * If an error occurs while communicating with the
 * server this error handler is called
 */
@EBean
public class RestServiceErrorHandler implements RestErrorHandler {

    private Context mContext;
    private OnSearchResultListener mListener;

    @Override
    public void onRestClientExceptionThrown(NestedRuntimeException e) {
        Log.e("REST_ERROR_HANDLER", e.getMessage());
        BackgroundExecutor.cancelAll("cancellable_task", true);
        this.showToast(e.getLocalizedMessage());
        this.hideProgressBar();
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public void setListener(OnSearchResultListener listener) {
        this.mListener = listener;
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

    @UiThread
    public void hideProgressBar() {
        mListener.hideProgressBar();
    }
}
