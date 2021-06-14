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

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Adaper;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.etc.ProgressBar.VerticalProgressBar;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Model.Area;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Model.Branch;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;

import java.util.List;

public class BranchMonitoringAdapter extends RecyclerView.Adapter<BranchMonitoringAdapter.ChartViewHolder> {

    private final List<Branch> branchPerformances;
    private final OnBranchPerformanceClickListener mListener;

    public interface OnBranchPerformanceClickListener{
        void OnClick();
    }

    public BranchMonitoringAdapter(List<Branch> areaPerformances, OnBranchPerformanceClickListener listener){
        this.branchPerformances = areaPerformances;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ChartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_branch_performance, parent, false);
        return new ChartViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ChartViewHolder holder, int position) {
        Branch branch = branchPerformances.get(position);
        int progress = 0;
        holder.txtArea.setText(branch.getBranchCode());
        holder.txtPrct.setText(branch.getSalesPercentage());

        progress = getParseValue(branch.getSalesPercentage().replace("%",""));
        holder.progressBar.setProgress(progress);

//        holder.progressBar.setScaleY(55f);
//        holder.progressBar.setProgress(getParseValue(branch.getSalesPercentage()));
//
//        progress = getParseValue(branch.getSalesPercentage().replace("%",""));
//        holder.progressBar.setProgress(progress);
    }

    @Override
    public int getItemCount() {
        return branchPerformances.size();
    }

    public static class ChartViewHolder extends RecyclerView.ViewHolder{

        public TextView txtArea;
        public TextView txtPrct;
        public VerticalProgressBar progressBar;

        public ChartViewHolder(@NonNull View itemView, OnBranchPerformanceClickListener listener) {
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
