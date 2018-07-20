package hu.psprog.leaflet.mobile.communication.service.impl;

import android.content.Context;
import hu.psprog.leaflet.api.rest.response.entry.EntryListDataModel;
import hu.psprog.leaflet.mobile.communication.domain.common.Pagination;
import hu.psprog.leaflet.mobile.communication.request.handler.APIRequestAction;
import hu.psprog.leaflet.mobile.communication.response.ResultReceiverCallback;
import hu.psprog.leaflet.mobile.communication.service.EntryAPIRequestService;

/**
 * @author Peter Smith
 */
public class EntryAPIRequestServiceImpl implements EntryAPIRequestService {

    private IntentServiceCallFactory intentServiceCallFactory;

    public EntryAPIRequestServiceImpl() {
        this.intentServiceCallFactory = new IntentServiceCallFactory();
    }

    @Override
    public void requestPublicEntries(Context context, ResultReceiverCallback<EntryListDataModel> callback, Pagination pagination) {
        intentServiceCallFactory.callIntentService(context, callback, APIRequestAction.PUBLIC_ENTRIES, pagination);
    }
}
