package hu.psprog.leaflet.mobile.communication.response;

import java.io.Serializable;

/**
 * @author Peter Smith
 */
public interface ResultReceiverCallback<T extends Serializable> {

    void onSuccess(T data);
    void onFailure(Exception exception);
}
