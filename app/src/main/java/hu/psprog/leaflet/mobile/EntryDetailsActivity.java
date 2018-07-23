package hu.psprog.leaflet.mobile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.ProgressBar;
import android.widget.TextView;
import dagger.android.AndroidInjection;
import hu.psprog.leaflet.api.rest.response.common.WrapperBodyDataModel;
import hu.psprog.leaflet.api.rest.response.entry.ExtendedEntryDataModel;
import hu.psprog.leaflet.mobile.communication.domain.common.Constants;
import hu.psprog.leaflet.mobile.communication.response.ResultReceiverCallback;
import hu.psprog.leaflet.mobile.communication.service.EntryAPIRequestService;
import hu.psprog.leaflet.mobile.view.helper.ViewHelper;

import javax.inject.Inject;
import java.lang.ref.WeakReference;

import static android.text.Html.FROM_HTML_MODE_COMPACT;

public class EntryDetailsActivity extends AppCompatActivity {

    @Inject
    EntryAPIRequestService entryAPIRequestService;

    @Inject
    ViewHelper viewHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ProgressBar progressBar = findViewById(R.id.networkCallProgressOnEntryDetails);
        TextView entryTitle = findViewById(R.id.entryDetailsTitle);
        TextView entryContent = findViewById(R.id.entryDetailsContent);

        viewHelper.hide(entryTitle, entryContent);
        viewHelper.show(progressBar);

        ResultReceiverCallback<WrapperBodyDataModel<ExtendedEntryDataModel>> callback = new EntryDetailsRequestCallback(entryTitle, entryContent, progressBar, viewHelper);
        entryAPIRequestService.requestEntryDetails(getApplicationContext(), callback, getIntent().getStringExtra(Constants.INTENT_PARAMETER_CALL_PARAMETERS));
    }

    private static class EntryDetailsRequestCallback implements ResultReceiverCallback<WrapperBodyDataModel<ExtendedEntryDataModel>> {

        private WeakReference<TextView> entryTitle;
        private WeakReference<TextView> entryContent;
        private WeakReference<ProgressBar> progressBar;
        private ViewHelper viewHelper;

        public EntryDetailsRequestCallback(TextView entryTitle, TextView entryContent, ProgressBar progressBar, ViewHelper viewHelper) {
            this.entryTitle = new WeakReference<>(entryTitle);
            this.entryContent = new WeakReference<>(entryContent);
            this.progressBar = new WeakReference<>(progressBar);
            this.viewHelper = viewHelper;
        }

        @Override
        public void onSuccess(WrapperBodyDataModel<ExtendedEntryDataModel> data) {
            entryTitle.get().setText(data.getBody().getTitle());
            entryContent.get().setText(Html.fromHtml(data.getBody().getContent(), FROM_HTML_MODE_COMPACT));

            viewHelper.hide(progressBar.get());
            viewHelper.show(entryTitle.get(), entryContent.get());
        }

        @Override
        public void onFailure(Exception exception) {
            viewHelper.hide(progressBar.get());
        }
    }
}
