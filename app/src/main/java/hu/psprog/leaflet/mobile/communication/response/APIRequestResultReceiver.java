package hu.psprog.leaflet.mobile.communication.response;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import java.io.Serializable;

import static hu.psprog.leaflet.mobile.communication.domain.common.Constants.BUNDLE_PARAMETER_RESULT;

/**
 * @author Peter Smith
 */
public class APIRequestResultReceiver<T extends Serializable> extends ResultReceiver {

    public static final int RESULT_CODE_OK = 0;
    public static final int RESULT_CODE_FAILURE = 1;

    private ResultReceiverCallback<T> callback;

    public APIRequestResultReceiver(Handler handler, ResultReceiverCallback<T> callback) {
        super(handler);
        this.callback = callback;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {

        if (resultCode == RESULT_CODE_OK) {
            callback.onSuccess((T) resultData.getSerializable(BUNDLE_PARAMETER_RESULT));
        } else {
            callback.onFailure((Exception) resultData.getSerializable(BUNDLE_PARAMETER_RESULT));
        }
    }
}
