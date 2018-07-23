package hu.psprog.leaflet.mobile.communication.service.impl;

import android.content.Context;
import hu.psprog.leaflet.api.rest.response.common.WrapperBodyDataModel;
import hu.psprog.leaflet.api.rest.response.entry.EntryListDataModel;
import hu.psprog.leaflet.api.rest.response.entry.ExtendedEntryDataModel;
import hu.psprog.leaflet.mobile.communication.domain.common.Pagination;
import hu.psprog.leaflet.mobile.communication.request.handler.APIRequestAction;
import hu.psprog.leaflet.mobile.communication.response.ResultReceiverCallback;
import hu.psprog.leaflet.mobile.communication.service.EntryAPIRequestService;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Peter Smith
 */
@Singleton
public class EntryAPIRequestServiceImpl implements EntryAPIRequestService {

    private IntentServiceCallFactory intentServiceCallFactory;

    @Inject
    public EntryAPIRequestServiceImpl(IntentServiceCallFactory intentServiceCallFactory) {
        this.intentServiceCallFactory = intentServiceCallFactory;
    }

    @Override
    public void requestPublicEntries(Context context, ResultReceiverCallback<EntryListDataModel> callback, Pagination pagination) {
        intentServiceCallFactory.callIntentService(context, callback, APIRequestAction.PUBLIC_ENTRIES, pagination);
    }

    @Override
    public void requestEntryDetails(Context context, ResultReceiverCallback<WrapperBodyDataModel<ExtendedEntryDataModel>> callback, String entryLink) {
        intentServiceCallFactory.callIntentService(context, callback, APIRequestAction.ENTRY_DETAILS, entryLink);
    }
}
