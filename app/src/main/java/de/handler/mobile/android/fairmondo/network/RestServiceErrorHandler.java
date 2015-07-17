package de.handler.mobile.android.fairmondo.network;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.api.BackgroundExecutor;
import org.androidannotations.api.rest.RestErrorHandler;
import org.springframework.core.NestedRuntimeException;

import de.handler.mobile.android.fairmondo.R;
import de.handler.mobile.android.fairmondo.presentation.controller.ProgressController;


/**
 * Handles Rest Error Messages.
 * If an error occurs while communicating with the
 * server this error handler is called
 */
@EBean
public class RestServiceErrorHandler implements RestErrorHandler {
    @Bean
    ProgressController mProgressController;

    private Context mContext;

    @Override
    public void onRestClientExceptionThrown(final NestedRuntimeException e) {
        Log.e("REST_ERROR_HANDLER", e.getMessage());
        BackgroundExecutor.cancelAll("cancellable_task", true);
        this.showToast(e.getLocalizedMessage());
        this.hideProgressBar();
    }

    public void setContext(@NonNull final Context context) {
        mContext = context;
    }

    @UiThread
    public void showToast(@NonNull final String message) {
        String toast = null;
            if (mContext != null) {
                if (message.contains("500") || message.contains("expected")) {
                    toast = mContext.getString(R.string.server_error);
                } else if (message.contains("502") || message.contains("I/O error")) {
                    toast = mContext.getString(R.string.server_temporarily_not_available);
                } else if (message.contains("404")) {
                    toast = mContext.getString(R.string.server_method_not_available);
                }

                // Show a Toast in corresponding activity with error message
                Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
            }

    }

    public void hideProgressBar() {
        mProgressController.stopProgress();
    }
}
