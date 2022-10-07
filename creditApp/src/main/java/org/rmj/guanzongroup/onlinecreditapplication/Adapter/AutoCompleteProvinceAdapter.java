/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.creditApp
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.onlinecreditapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.rmj.g3appdriver.dev.Database.Entities.EProvinceInfo;
import org.rmj.guanzongroup.onlinecreditapplication.R;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteProvinceAdapter extends ArrayAdapter<EProvinceInfo> {

    private final List<EProvinceInfo> allProvinceInfo;
    private List<EProvinceInfo> filteredProvinceInfo;

    public AutoCompleteProvinceAdapter(@NonNull Context context, @NonNull List<EProvinceInfo> provinceInfoList){
        super(context, 0, provinceInfoList);
        allProvinceInfo = new ArrayList<>();
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return placeFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_address, parent, false
            );
        }

        TextView placeLabel = convertView.findViewById(R.id.lbl_itemList_address);

        EProvinceInfo place = getItem(position);
        if (place != null) {
            placeLabel.setText(place.getProvName());
        }

        return convertView;
    }

    private Filter placeFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            filteredProvinceInfo = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredProvinceInfo.addAll(allProvinceInfo);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (EProvinceInfo provinceInfo: allProvinceInfo) {
                    if (provinceInfo.getProvName().toLowerCase().contains(filterPattern)) {
                        filteredProvinceInfo.add(provinceInfo);
                    }
                }
            }

            results.values = filteredProvinceInfo;
            results.count = filteredProvinceInfo.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((EProvinceInfo) resultValue).getProvName();
        }
    };
}
