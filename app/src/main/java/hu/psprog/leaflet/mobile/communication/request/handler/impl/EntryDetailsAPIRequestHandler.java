package hu.psprog.leaflet.mobile.communication.request.handler.impl;

import android.content.Intent;
import hu.psprog.leaflet.bridge.client.exception.CommunicationFailureException;
import hu.psprog.leaflet.bridge.service.EntryBridgeService;
import hu.psprog.leaflet.mobile.communication.domain.common.Constants;
import hu.psprog.leaflet.mobile.communication.exception.APICallException;
import hu.psprog.leaflet.mobile.communication.request.handler.APIRequestAction;
import hu.psprog.leaflet.mobile.communication.request.handler.APIRequestHandler;

import java.io.Serializable;

/**
 * @author Peter Smith
 */
public class EntryDetailsAPIRequestHandler implements APIRequestHandler {

    private EntryBridgeService entryBridgeService;

    public EntryDetailsAPIRequestHandler(EntryBridgeService entryBridgeService) {
        this.entryBridgeService = entryBridgeService;
    }

    @Override
    public Serializable handleRequest(Intent intent) {

        try {
            return entryBridgeService.getEntryByLink(extractEntryLink(intent));
        } catch (CommunicationFailureException e) {
            throw new APICallException("failed to call entry details endpoint", e);
        }
    }

    @Override
    public APIRequestAction assignedAction() {
        return APIRequestAction.ENTRY_DETAILS;
    }

    private String extractEntryLink(Intent intent) {
        return intent.getStringExtra(Constants.INTENT_PARAMETER_CALL_PARAMETERS);
    }
}
