package hu.psprog.leaflet.mobile.communication.service.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import hu.psprog.leaflet.mobile.communication.intent.APIRequestIntentService;
import hu.psprog.leaflet.mobile.communication.request.handler.APIRequestAction;
import hu.psprog.leaflet.mobile.communication.response.APIRequestResultReceiver;
import hu.psprog.leaflet.mobile.communication.response.ResultReceiverCallback;

import java.io.Serializable;

import static hu.psprog.leaflet.mobile.communication.domain.common.Constants.INTENT_PARAMETER_CALL_PARAMETERS;
import static hu.psprog.leaflet.mobile.communication.domain.common.Constants.INTENT_PARAMETER_RECEIVER;

/**
 * @author Peter Smith
 */
public class IntentServiceCallFactory {

    private static final Class<APIRequestIntentService> API_REQUEST_INTENT_SERVICE_CLASS = APIRequestIntentService.class;

    void callIntentService(Context context, ResultReceiverCallback<? extends Serializable> callback, APIRequestAction action, Serializable parameters) {
        context.startService(createIntent(context, callback, action, parameters));
    }

    private Intent createIntent(Context context, ResultReceiverCallback<? extends Serializable> callback, APIRequestAction action, Serializable parameters) {

        Intent intent = new Intent(context, API_REQUEST_INTENT_SERVICE_CLASS);
        intent.setAction(action.name());
        intent.putExtra(INTENT_PARAMETER_RECEIVER, createReceiver(context, callback));
        intent.putExtra(INTENT_PARAMETER_CALL_PARAMETERS, parameters);

        return intent;
    }

    private APIRequestResultReceiver createReceiver(Context context, ResultReceiverCallback<? extends Serializable> callback) {
        return new APIRequestResultReceiver<>(new Handler(context.getMainLooper()), callback);
    }
}
