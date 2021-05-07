/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office 
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 5/7/21 11:41 AM
 * project file last modified : 5/7/21 11:41 AM
 */

package org.rmj.guanzongroup.ghostrider.epacss.ui.ProgressBar;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.guanzongroup.ghostrider.ahmonitoring.Model.Area;
import org.rmj.guanzongroup.ghostrider.epacss.R;

import java.util.List;

public class AreaMonitoringDashbordAdapter extends RecyclerView.Adapter<AreaMonitoringDashbordAdapter.ChartViewHolder> {

    List<Area> areaPerformances;

    public AreaMonitoringDashbordAdapter(List<Area> areaPerformances){
        this.areaPerformances = areaPerformances;
    }

    @NonNull
    @Override
    public AreaMonitoringDashbordAdapter.ChartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_item_area_monitoring, parent, false);
        return new AreaMonitoringDashbordAdapter.ChartViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull AreaMonitoringDashbordAdapter.ChartViewHolder holder, int position) {
        int progress = 0;
        Area area = areaPerformances.get(position);
        holder.txtArea.setText(area.getAreaName());
        holder.txtPrct.setText(area.getSalesPercentage());
//        holder.progressBar.setMax(area.getDynamicSize());

        progress = getParseValue(area.getSalesPercentage().replace("%",""));
//        holder.progressBar.setMax(150);
        Log.e("Max", String.valueOf(area.getDynamicSize()));
        Log.e("Progress", String.valueOf(area.getSalesPercentage()));
        holder.progressBar.setProgress(progress);
//        holder.progressBar.getProgressDrawable().setColorFilter(R.color.guanzon_orange, PorterDuff.Mode.SRC_IN);

    }

    @Override
    public int getItemCount() {
        return areaPerformances.size();
    }

    public static class ChartViewHolder extends RecyclerView.ViewHolder{

        public TextView txtArea;
        public TextView txtPrct;
        public VerticalProgressBar progressBar;

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
