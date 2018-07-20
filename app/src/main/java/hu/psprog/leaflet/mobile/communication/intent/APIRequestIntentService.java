package hu.psprog.leaflet.mobile.communication.intent;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import hu.psprog.leaflet.mobile.communication.exception.APICallException;
import hu.psprog.leaflet.mobile.communication.request.handler.impl.registry.APIRequestHandlerRegistry;

import java.io.Serializable;

import static hu.psprog.leaflet.mobile.communication.domain.common.Constants.BUNDLE_PARAMETER_RESULT;
import static hu.psprog.leaflet.mobile.communication.domain.common.Constants.INTENT_PARAMETER_RECEIVER;
import static hu.psprog.leaflet.mobile.communication.response.APIRequestResultReceiver.RESULT_CODE_FAILURE;
import static hu.psprog.leaflet.mobile.communication.response.APIRequestResultReceiver.RESULT_CODE_OK;

/**
 * @author Peter Smith
 */
public class APIRequestIntentService extends IntentService {

    private static final String INTENT_SERVICE_NAME = "api-request-intent-service";

    private APIRequestHandlerRegistry apiRequestHandlerRegistry;

    public APIRequestIntentService() {
        super(INTENT_SERVICE_NAME);
        apiRequestHandlerRegistry = new APIRequestHandlerRegistry();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if (intent != null) {
            Serializable apiCallResult;
            int resultCode = RESULT_CODE_OK;
            try {
                apiCallResult = apiRequestHandlerRegistry.handleRequest(intent);
            } catch (APICallException e) {
                resultCode = RESULT_CODE_FAILURE;
                apiCallResult = e;
            }

            notifyReceiver(intent, apiCallResult, resultCode);
        }
    }

    private void notifyReceiver(Intent intent, Serializable result, int resultCode) {
        ResultReceiver resultReceiver = intent.getParcelableExtra(INTENT_PARAMETER_RECEIVER);
        resultReceiver.send(resultCode, packBundle(result));
    }

    private Bundle packBundle(Serializable result) {

        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_PARAMETER_RESULT, result);

        return bundle;
    }
}
