package hu.psprog.leaflet.mobile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
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
        WebView entryContent = findViewById(R.id.entryDetailsContent);
        String detailsPageCSSTag = getString(R.string.detailsPageCSSTag);

        viewHelper.hide(entryTitle, entryContent);
        viewHelper.show(progressBar);

        ResultReceiverCallback<WrapperBodyDataModel<ExtendedEntryDataModel>> callback
                = new EntryDetailsRequestCallback(entryTitle, entryContent, progressBar, viewHelper, detailsPageCSSTag);
        entryAPIRequestService.requestEntryDetails(getApplicationContext(), callback, getIntent().getStringExtra(Constants.INTENT_PARAMETER_CALL_PARAMETERS));
    }

    private static class EntryDetailsRequestCallback implements ResultReceiverCallback<WrapperBodyDataModel<ExtendedEntryDataModel>> {

        private static final String BASE_URL = "file:///android_asset/";
        private static final String MIME_TYPE = "text/html";
        private static final String ENCODING = "UTF-8";
        private static final String HISTORY_URL = null;

        private WeakReference<TextView> entryTitle;
        private WeakReference<WebView> entryContent;
        private WeakReference<ProgressBar> progressBar;
        private ViewHelper viewHelper;
        private String detailsPageCSSTag;

        public EntryDetailsRequestCallback(TextView entryTitle, WebView entryContent, ProgressBar progressBar, ViewHelper viewHelper, String detailsPageCSSTag) {
            this.entryTitle = new WeakReference<>(entryTitle);
            this.entryContent = new WeakReference<>(entryContent);
            this.progressBar = new WeakReference<>(progressBar);
            this.viewHelper = viewHelper;
            this.detailsPageCSSTag = detailsPageCSSTag;
        }

        @Override
        public void onSuccess(WrapperBodyDataModel<ExtendedEntryDataModel> data) {
            entryTitle.get().setText(data.getBody().getTitle());
            entryContent.get().loadDataWithBaseURL(BASE_URL, prepareContent(data), MIME_TYPE, ENCODING, HISTORY_URL);

            viewHelper.hide(progressBar.get());
            viewHelper.show(entryTitle.get(), entryContent.get());
        }

        private String prepareContent(WrapperBodyDataModel<ExtendedEntryDataModel> dataModel) {
            return detailsPageCSSTag + dataModel.getBody().getContent();
        }

        @Override
        public void onFailure(Exception exception) {
            viewHelper.hide(progressBar.get());
        }
    }
}
