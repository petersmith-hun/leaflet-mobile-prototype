package hu.psprog.leaflet.mobile.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import hu.psprog.leaflet.api.rest.response.entry.EntryDataModel;
import hu.psprog.leaflet.api.rest.response.entry.EntryListDataModel;
import hu.psprog.leaflet.mobile.R;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Peter Smith
 */
public class EntryItemAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<EntryDataModel> entryDataModelList;

    public EntryItemAdapter(Context context, EntryListDataModel entryListDataModel) {
        this.entryDataModelList = Optional.ofNullable(entryListDataModel)
                .map(EntryListDataModel::getEntries)
                .orElse(Collections.emptyList());
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return entryDataModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return entryDataModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        if (Objects.isNull(rowView)) {
            rowView = layoutInflater.inflate(R.layout.list_view_item, parent, false);
        }

        EntryDataModel entryDataModel = (EntryDataModel) getItem(position);
        setTitle(rowView, entryDataModel);
        setPrologue(rowView, entryDataModel);

        return rowView;
    }

    private void setTitle(View targetRowView, EntryDataModel entryDataModel) {
        setValue(targetRowView.findViewById(R.id.entryTitle), entryDataModel.getTitle());
    }

    private void setPrologue(View targetRowView, EntryDataModel entryDataModel) {
        setValue(targetRowView.findViewById(R.id.entryPrologue), entryDataModel.getPrologue());
    }

    private void setValue(TextView targetTextView, String value) {
        targetTextView.setText(value);
    }
}
