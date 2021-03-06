package hu.psprog.leaflet.mobile.communication.request.handler.impl;

import android.content.Intent;
import hu.psprog.leaflet.bridge.client.domain.OrderBy;
import hu.psprog.leaflet.bridge.client.exception.CommunicationFailureException;
import hu.psprog.leaflet.bridge.service.EntryBridgeService;
import hu.psprog.leaflet.mobile.communication.domain.common.Pagination;
import hu.psprog.leaflet.mobile.communication.exception.APICallException;
import hu.psprog.leaflet.mobile.communication.request.handler.APIRequestAction;
import hu.psprog.leaflet.mobile.communication.request.handler.APIRequestHandler;

import java.io.Serializable;

/**
 * @author Peter Smith
 */
public class PublicEntriesAPIRequestHandler extends AbstractPaginationCapableAPIRequestHandler<OrderBy.Entry> implements APIRequestHandler {

    private EntryBridgeService entryBridgeService;

    public PublicEntriesAPIRequestHandler(EntryBridgeService entryBridgeService) {
        this.entryBridgeService = entryBridgeService;
    }

    @Override
    public Serializable handleRequest(Intent intent) {

        try {
            Pagination pagination = extractPagination(intent);
            return entryBridgeService
                    .getPageOfPublicEntries(pagination.getPage(), pagination.getLimit(), convertOrderBy(pagination), convertOrderDirection(pagination));
        } catch (CommunicationFailureException e) {
            throw new APICallException("failed to call public entries endpoint", e);
        }
    }

    @Override
    public APIRequestAction assignedAction() {
        return APIRequestAction.PUBLIC_ENTRIES;
    }

    @Override
    Class<OrderBy.Entry> orderByEnumType() {
        return OrderBy.Entry.class;
    }

    @Override
    OrderBy.Entry defaultOrderBy() {
        return OrderBy.Entry.CREATED;
    }
}
