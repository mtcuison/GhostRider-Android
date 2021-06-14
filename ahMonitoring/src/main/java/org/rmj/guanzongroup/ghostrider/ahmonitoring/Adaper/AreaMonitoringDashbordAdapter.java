/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office 
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 6/10/21 9:34 AM
 * project file last modified : 6/10/21 9:31 AM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Adaper;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.etc.ProgressBar.VerticalProgressBar;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Model.Area;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;

import java.util.List;

public class AreaMonitoringDashbordAdapter extends RecyclerView.Adapter<AreaMonitoringDashbordAdapter.ChartViewHolder> {

    private final List<Area> areaPerformances;
    private final OnAdapterItemClickListener mListener;


    public interface OnAdapterItemClickListener{
        void OnClick();
    }

    public AreaMonitoringDashbordAdapter(List<Area> areaPerformances, OnAdapterItemClickListener listener){
        this.areaPerformances = areaPerformances;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public AreaMonitoringDashbordAdapter.ChartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_branch_performance, parent, false);
        return new AreaMonitoringDashbordAdapter.ChartViewHolder(view, mListener);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull AreaMonitoringDashbordAdapter.ChartViewHolder holder, int position) {
        int progress = 0;
        Area area = areaPerformances.get(position);
        holder.txtArea.setText(area.getAreaName());
        holder.txtPrct.setText(area.getSalesPercentage());

        progress = getParseValue(area.getSalesPercentage().replace("%",""));
        holder.progressBar.setProgress(progress);

    }

    @Override
    public int getItemCount() {
        return areaPerformances.size();
    }

    public static class ChartViewHolder extends RecyclerView.ViewHolder{

        public TextView txtArea;
        public TextView txtPrct;
        public VerticalProgressBar progressBar;

        public ChartViewHolder(@NonNull View itemView, OnAdapterItemClickListener listener) {
            super(itemView);
            txtArea = itemView.findViewById(R.id.lbl_AreaBranchItem);
            txtPrct = itemView.findViewById(R.id.lbl_listItemPercentage);
            progressBar = itemView.findViewById(R.id.progress_monitor);

            itemView.setOnClickListener(v -> listener.OnClick());
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
