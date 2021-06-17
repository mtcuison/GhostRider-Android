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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchPerformance;
import org.rmj.g3appdriver.etc.ProgressBar.VerticalProgressBar;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Model.Branch;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;

import java.text.DecimalFormat;
import java.util.List;

public class BranchMonitoringAdapter extends RecyclerView.Adapter<BranchMonitoringAdapter.ChartViewHolder> {

    private final List<EBranchPerformance> branchPerformances;
    private final OnBranchPerformanceClickListener mListener;

    public interface OnBranchPerformanceClickListener{
        void OnClick();
    }

    public BranchMonitoringAdapter(List<EBranchPerformance> areaPerformances, OnBranchPerformanceClickListener listener){
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
        EBranchPerformance branch = branchPerformances.get(position);
        holder.txtArea.setText(branch.getBranchCd());
//        holder.txtPrct.setText(branch.getMCSalesPercentage());

        int mcSales = getPercentageProgress(branch.getMCActual(), branch.getMCGoalxx());
        holder.mcSales.setMax(getDynamicSize(branch.getMCGoalxx()));
        holder.mcSales.setProgress(mcSales);
        int spSales = getPercentageProgress(branch.getSPActual(), branch.getSPGoalxx());
        holder.spSales.setMax(getDynamicSize(branch.getSPGoalxx()));
        holder.spSales.setProgress(spSales);
        int jbOrder =getPercentageProgress(branch.getLRActual(), branch.getLRGoalxx());
        holder.jbOrder.setMax(getDynamicSize(branch.getLRGoalxx()));
        holder.jbOrder.setProgress(jbOrder);

        holder.lblSpSales.setText(getPercentageValue(branch.getSPActual(), branch.getSPGoalxx()));
        holder.lblMcSales.setText(getPercentageValue(branch.getMCActual(), branch.getMCGoalxx()));
        holder.lblJbOrder.setText(getPercentageValue(branch.getLRActual(), branch.getLRGoalxx()));
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
        public TextView lblSpSales;
        public TextView lblMcSales;
        public TextView lblJbOrder;
        public ProgressBar mcSales;
        public ProgressBar spSales;
        public ProgressBar jbOrder;

        public ChartViewHolder(@NonNull View itemView, OnBranchPerformanceClickListener listener) {
            super(itemView);
            txtArea = itemView.findViewById(R.id.lbl_AreaBranchItem);
            mcSales = itemView.findViewById(R.id.progress_mcSales);
            spSales = itemView.findViewById(R.id.progress_spSales);
            jbOrder = itemView.findViewById(R.id.progress_jobOrder);
            lblSpSales = itemView.findViewById(R.id.lbl_spSales);
            lblMcSales = itemView.findViewById(R.id.lbl_mcSales);
            lblJbOrder = itemView.findViewById(R.id.lbl_jbOrder);

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

    public String getPercentageValue(float actual, float goal) {
        float percentage = actual /goal;
        float result = percentage * 100;
        return new DecimalFormat("##").format(result) + "%";
    }

    public int getPercentageProgress(float actual, float goal) {
        float percentage = actual /goal;
        float result = percentage * 100;
        return (int) result;
    }

    public int getDynamicSize(float Goal) {
        float adds = Goal * .05f;
        int result = Integer.parseInt(new DecimalFormat("##").format(100 + adds));
        return result;
    }
}
