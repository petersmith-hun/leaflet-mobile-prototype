package hu.psprog.leaflet.mobile.communication.request.handler.impl;

import android.content.Intent;
import hu.psprog.leaflet.bridge.client.domain.OrderBy;
import hu.psprog.leaflet.bridge.client.domain.OrderDirection;
import hu.psprog.leaflet.bridge.client.exception.CommunicationFailureException;
import hu.psprog.leaflet.bridge.service.impl.BridgeServiceRegistry;
import hu.psprog.leaflet.mobile.communication.request.handler.APIRequestAction;
import hu.psprog.leaflet.mobile.communication.request.handler.APIRequestHandler;
import hu.psprog.leaflet.mobile.communication.exception.APICallException;

import java.io.Serializable;

/**
 * @author Peter Smith
 */
public class PublicEntriesAPIRequestHandler implements APIRequestHandler {

    @Override
    public Serializable handleRequest(Intent intent) {

        try {
            return new BridgeServiceRegistry().getEntryBridgeService()
                    .getPageOfPublicEntries(1, 10, OrderBy.Entry.CREATED, OrderDirection.DESC)
                    .getBody();
        } catch (CommunicationFailureException e) {
            throw new APICallException("failed to call public entries endpoints", e);
        }
    }

    @Override
    public APIRequestAction assignedAction() {
        return APIRequestAction.PUBLIC_ENTRIES;
    }
}
