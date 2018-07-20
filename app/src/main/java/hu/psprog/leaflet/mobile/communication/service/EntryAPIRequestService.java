package hu.psprog.leaflet.mobile.communication.service;

import android.content.Context;
import hu.psprog.leaflet.api.rest.response.entry.EntryListDataModel;
import hu.psprog.leaflet.mobile.communication.domain.common.Pagination;
import hu.psprog.leaflet.mobile.communication.response.ResultReceiverCallback;

/**
 * @author Peter Smith
 */
public interface EntryAPIRequestService {

    void requestPublicEntries(Context context, ResultReceiverCallback<EntryListDataModel> callback, Pagination pagination);
}
