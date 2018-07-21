package hu.psprog.leaflet.mobile;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import dagger.android.AndroidInjection;
import hu.psprog.leaflet.api.rest.response.entry.EntryListDataModel;
import hu.psprog.leaflet.mobile.communication.domain.common.Pagination;
import hu.psprog.leaflet.mobile.communication.response.ResultReceiverCallback;
import hu.psprog.leaflet.mobile.communication.service.EntryAPIRequestService;
import hu.psprog.leaflet.mobile.view.adapter.EntryItemAdapter;

import javax.inject.Inject;
import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    @Inject
    EntryAPIRequestService entryAPIRequestService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView entryListView = findViewById(R.id.entryList);
        ProgressBar progressBar = findViewById(R.id.networkCallProgress);

        setNetworkCallProgressBarVisibility(progressBar, true);
        EntryRequestCallback entryRequestCallback = new EntryRequestCallback(getApplicationContext(), entryListView, progressBar);
        entryAPIRequestService.requestPublicEntries(getApplicationContext(), entryRequestCallback, Pagination.usingDefaults(1));
    }

    private static class EntryRequestCallback implements ResultReceiverCallback<EntryListDataModel> {

        private WeakReference<Context> context;
        private WeakReference<ListView> entryListView;
        private WeakReference<ProgressBar> progressBar;

        private EntryRequestCallback(Context context, ListView entryListView, ProgressBar progressBar) {
            this.context = new WeakReference<>(context);
            this.entryListView = new WeakReference<>(entryListView);
            this.progressBar = new WeakReference<>(progressBar);
        }

        @Override
        public void onSuccess(EntryListDataModel data) {
            BaseAdapter adapter = new EntryItemAdapter(context.get(), data);
            entryListView.get().setAdapter(adapter);
            setNetworkCallProgressBarVisibility(progressBar.get(),false);
        }

        @Override
        public void onFailure(Exception exception) {
            setNetworkCallProgressBarVisibility(progressBar.get(), false);
        }
    }

    private static void setNetworkCallProgressBarVisibility(ProgressBar progressBar, boolean visible) {
        progressBar.setVisibility(visible
                ? View.VISIBLE
                : View.INVISIBLE);
    }
}
