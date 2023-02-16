/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.android.material.divider.MaterialDivider;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.guanzongroup.ghostrider.ahmonitoring.Model.Area;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;

import java.util.List;

public class AreaMonitoringAdapter extends RecyclerView.Adapter<AreaMonitoringAdapter.ChartViewHolder> {

    List<Area> areaPerformances;

    public AreaMonitoringAdapter(List<Area> areaPerformances){
        this.areaPerformances = areaPerformances;
    }

    @NonNull
    @Override
    public ChartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_area_monitor, parent, false);
        return new ChartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChartViewHolder holder, int position) {
        Area area = areaPerformances.get(position);
        holder.txtArea.setText(area.getAreaName());
        holder.txtPrct.setText(area.getSalesPercentage());
        holder.progressBar.setScaleY(55f);
        holder.progressBar.setMax(area.getDynamicSize());
        holder.progressBar.setProgress(getParseValue(area.getSalesPercentage().replace("%","")));
    }

    @Override
    public int getItemCount() {
        return areaPerformances.size();
    }

    public static class ChartViewHolder extends RecyclerView.ViewHolder{

        public MaterialTextView txtArea, txtPrct;
        public ProgressBar progressBar;

        public ChartViewHolder(@NonNull View itemView) {
            super(itemView);
            txtArea = itemView.findViewById(R.id.lbl_AreaBranchItem);
            txtPrct = itemView.findViewById(R.id.lbl_listItemPercentage);
            progressBar = itemView.findViewById(R.id.progress_monitor);
        }
    }

    private int getParseValue(String value){
        try{
            return Integer.parseInt(value);
        } catch (Exception e) {
            e.printStackTrace();
            return 100;
        }
    }

}