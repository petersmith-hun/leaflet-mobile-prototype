package hu.psprog.leaflet.mobile;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import hu.psprog.leaflet.api.rest.response.entry.EntryListDataModel;
import hu.psprog.leaflet.bridge.client.domain.OrderBy;
import hu.psprog.leaflet.bridge.client.domain.OrderDirection;
import hu.psprog.leaflet.bridge.client.exception.CommunicationFailureException;
import hu.psprog.leaflet.bridge.service.EntryBridgeService;
import hu.psprog.leaflet.bridge.service.impl.BridgeServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.WeakReference;

/**
 * @author Peter Smith
 */
class EntryRequestTask extends AsyncTask<Void, Void, EntryListDataModel> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntryRequestTask.class);

    private EntryBridgeService entryBridgeService;
    private WeakReference<Context> context;
    private WeakReference<ListView> entryListView;
    private WeakReference<ProgressBar> progressBar;

    EntryRequestTask(Context context, Activity activity) {
        this.context = new WeakReference<>(context);
        this.entryListView = new WeakReference<>(activity.findViewById(R.id.entryList));
        this.progressBar = new WeakReference<>(activity.findViewById(R.id.networkCallProgress));
        this.entryBridgeService = new BridgeServiceRegistry().getEntryBridgeService();
    }

    @Override
    protected EntryListDataModel doInBackground(Void... voids) {

        EntryListDataModel entryListDataModel = null;
        setNetworkCallProgressBarVisibility(true);
        try {
            entryListDataModel = entryBridgeService.getPageOfPublicEntries(1, 10, OrderBy.Entry.CREATED, OrderDirection.DESC).getBody();
        } catch (CommunicationFailureException e) {
            LOGGER.error("Communication failure", e);
        } catch (Exception e) {
            LOGGER.error("Unexpected error", e);
        }

        return entryListDataModel;
    }

    @Override
    protected void onPostExecute(EntryListDataModel entryListDataModel) {
        BaseAdapter adapter = new EntryItemAdapter(context.get(), entryListDataModel);
        entryListView.get().setAdapter(adapter);
        setNetworkCallProgressBarVisibility(false);
    }

    private void setNetworkCallProgressBarVisibility(boolean visible) {
        progressBar.get().setVisibility(visible
                ? View.VISIBLE
                : View.INVISIBLE);
    }
}
