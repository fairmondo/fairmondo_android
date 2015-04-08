package de.handler.mobile.android.fairmondo.network;

/**
 * Network Exception which is thrown whenever something goes wrong in the Network Layer.
 */
public class NetworkException extends RuntimeException {
    public NetworkException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
