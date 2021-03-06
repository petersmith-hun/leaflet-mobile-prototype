package hu.psprog.leaflet.mobile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import dagger.android.AndroidInjection;
import hu.psprog.leaflet.api.rest.response.common.WrapperBodyDataModel;
import hu.psprog.leaflet.api.rest.response.entry.EntryDataModel;
import hu.psprog.leaflet.api.rest.response.entry.EntryListDataModel;
import hu.psprog.leaflet.mobile.communication.domain.common.Constants;
import hu.psprog.leaflet.mobile.communication.domain.common.Pagination;
import hu.psprog.leaflet.mobile.communication.response.ResultReceiverCallback;
import hu.psprog.leaflet.mobile.communication.service.EntryAPIRequestService;
import hu.psprog.leaflet.mobile.view.adapter.EntryItemAdapter;
import hu.psprog.leaflet.mobile.view.helper.ViewHelper;

import javax.inject.Inject;
import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {

    @Inject
    EntryAPIRequestService entryAPIRequestService;

    @Inject
    ViewHelper viewHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView entryListView = findViewById(R.id.entryList);
        ProgressBar progressBar = findViewById(R.id.networkCallProgress);
        FloatingActionButton nextPage = findViewById(R.id.pagingNext);
        FloatingActionButton previousPage = findViewById(R.id.pagingPrevious);
        AtomicInteger page = new AtomicInteger(1);

        viewHelper.show(progressBar);
        EntryRequestCallback entryRequestCallback = new EntryRequestCallback(getApplicationContext(), entryListView, progressBar, viewHelper, previousPage, nextPage);
        callService(entryRequestCallback, Pagination.usingDefaults(page.get()));

        entryListView.setOnItemClickListener((parent, view, position, id) -> {
            EntryDataModel entry = (EntryDataModel) parent.getItemAtPosition(position);
            Intent intent = new Intent(this, EntryDetailsActivity.class);
            intent.putExtra(Constants.INTENT_PARAMETER_CALL_PARAMETERS, entry.getLink());
            startActivity(intent);
        });

        nextPage.setOnClickListener(view -> callService(entryRequestCallback, Pagination.usingDefaults(page.incrementAndGet())));
        previousPage.setOnClickListener(view -> callService(entryRequestCallback, Pagination.usingDefaults(page.decrementAndGet())));
    }

    private void callService(EntryRequestCallback entryRequestCallback, Pagination pagination) {
        entryAPIRequestService.requestPublicEntries(getApplicationContext(), entryRequestCallback, pagination);
    }

    private static class EntryRequestCallback implements ResultReceiverCallback<WrapperBodyDataModel<EntryListDataModel>> {

        private WeakReference<Context> context;
        private WeakReference<ListView> entryListView;
        private WeakReference<ProgressBar> progressBar;
        private WeakReference<FloatingActionButton> previousPageButton;
        private WeakReference<FloatingActionButton> nextPageButton;
        private ViewHelper viewHelper;

        private EntryRequestCallback(Context context, ListView entryListView, ProgressBar progressBar, ViewHelper viewHelper,
                                     FloatingActionButton previousPageButton, FloatingActionButton nextPageButton) {
            this.context = new WeakReference<>(context);
            this.entryListView = new WeakReference<>(entryListView);
            this.progressBar = new WeakReference<>(progressBar);
            this.previousPageButton = new WeakReference<>(previousPageButton);
            this.nextPageButton = new WeakReference<>(nextPageButton);
            this.viewHelper = viewHelper;
        }

        @Override
        public void onSuccess(WrapperBodyDataModel<EntryListDataModel> data) {
            BaseAdapter adapter = new EntryItemAdapter(context.get(), data.getBody());
            entryListView.get().setAdapter(adapter);
            viewHelper.hide(progressBar.get());

            if (data.getPagination().isHasNext()) {
                viewHelper.show(nextPageButton.get());
            } else {
                viewHelper.hide(nextPageButton.get());
            }

            if (data.getPagination().isHasPrevious()) {
                viewHelper.show(previousPageButton.get());
            } else {
                viewHelper.hide(previousPageButton.get());
            }
        }

        @Override
        public void onFailure(Exception exception) {
            viewHelper.hide(progressBar.get());
        }
    }
}
