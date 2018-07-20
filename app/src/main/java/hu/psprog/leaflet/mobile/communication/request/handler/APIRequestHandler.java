package hu.psprog.leaflet.mobile.communication.request.handler;

import android.content.Intent;

import java.io.Serializable;

/**
 * @author Peter Smith
 */
public interface APIRequestHandler {

    Serializable handleRequest(Intent intent);

    APIRequestAction assignedAction();
}
