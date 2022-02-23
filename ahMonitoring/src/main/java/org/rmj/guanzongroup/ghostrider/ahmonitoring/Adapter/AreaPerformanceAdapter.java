/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 2/23/22, 2:32 PM
 * project file last modified : 2/23/22, 2:32 PM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.GRider.Database.Entities.EAreaPerformance;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;

import java.text.DecimalFormat;
import java.util.List;

public class AreaPerformanceAdapter extends RecyclerView.Adapter<AreaPerformanceAdapter.ChartViewHolder> {

    private final List<EAreaPerformance> areaPerformances;
    private final OnBranchPerformanceClickListener mListener;
    private final String psType;

    public interface OnBranchPerformanceClickListener{
        void OnClick(EAreaPerformance eAreaPerformance);
    }

    public AreaPerformanceAdapter(List<EAreaPerformance> areaPerformances, String fsType, OnBranchPerformanceClickListener listener){
        this.areaPerformances = areaPerformances;
        this.mListener = listener;
        this.psType = fsType;
    }

    @NonNull
    @Override
    public ChartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_bar_chart, parent, false);
        return new ChartViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ChartViewHolder holder, int position) {
        EAreaPerformance area = areaPerformances.get(position);
        holder.txtPeriod.setText(area.getPeriodxx());
        holder.eArea = areaPerformances.get(position);

        if("MC".equalsIgnoreCase(psType)) {

            int lnGoal = getPercentageProgress(area.getMCGoalxx(),area.getMCGoalxx());
            holder.pGoal.setMax(getDynamicSize(area.getMCGoalxx()));
            holder.pGoal.setProgress(lnGoal);
            holder.lblGoal.setText(getPercentageValue(area.getMCGoalxx(),area.getMCGoalxx()));

            int lnActual = getPercentageProgress(area.getMCActual(), area.getMCGoalxx());
            holder.pActual.setMax(getDynamicSize(area.getMCGoalxx()));
            holder.pActual.setProgress(lnActual);
            holder.lblActual.setText(getPercentageValue(area.getMCActual(), area.getMCGoalxx()));

        } else if("SP".equalsIgnoreCase(psType)) {
            int lnGoal = getPercentageProgress(area.getSPGoalxx(),area.getSPGoalxx());
            holder.pGoal.setMax(getDynamicSize(area.getSPGoalxx()));
            holder.pGoal.setProgress(lnGoal);
            holder.lblGoal.setText(getPercentageValue(area.getSPGoalxx(),area.getSPGoalxx()));

            int lnActual = getPercentageProgress(area.getSPActual(), area.getSPGoalxx());
            holder.pActual.setMax(getDynamicSize(area.getSPGoalxx()));
            holder.pActual.setProgress(lnActual);
            holder.lblActual.setText(getPercentageValue(area.getSPActual(), area.getSPGoalxx()));

        }

////        holder.txtPrct.setText(branch.getMCSalesPercentage());
//
//        int mcSales = getPercentageProgress(area.getMCActual(), area.getMCGoalxx());
//        holder.mcSales.setMax(getDynamicSize(area.getMCGoalxx()));
//        holder.mcSales.setProgress(mcSales);
//        int spSales = getPercentageProgress(area.getSPActual(), area.getSPGoalxx());
//        holder.spSales.setMax(getDynamicSize(area.getSPGoalxx()));
//        holder.spSales.setProgress(spSales);
//        int jbOrder =getPercentageProgress(area.getLRActual(), area.getLRGoalxx());
//        holder.jbOrder.setMax(getDynamicSize(area.getLRGoalxx()));
//        holder.jbOrder.setProgress(jbOrder);
//
//        holder.lblSpSales.setText(getPercentageValue(area.getSPActual(), area.getSPGoalxx()));
//        holder.lblMcSales.setText(String.valueOf(area.getMCActual()));
//        holder.lblJbOrder.setText(getPercentageValue(area.getLRActual(), area.getLRGoalxx()));
////        holder.progressBar.setScaleY(55f);
////        holder.progressBar.setProgress(getParseValue(branch.getSalesPercentage()));
////
////        progress = getParseValue(branch.getSalesPercentage().replace("%",""));
////        holder.progressBar.setProgress(progress);
    }

    @Override
    public int getItemCount() {
        return areaPerformances.size();
    }

    public static class ChartViewHolder extends RecyclerView.ViewHolder{

        public TextView txtPeriod;
        public TextView lblGoal;
        public TextView lblActual;
        public ProgressBar pGoal;
        public ProgressBar pActual;
        public EAreaPerformance eArea;

        public ChartViewHolder(@NonNull View itemView, OnBranchPerformanceClickListener listener) {
            super(itemView);
            txtPeriod = itemView.findViewById(R.id.lbl_period);
            pGoal = itemView.findViewById(R.id.progress_goal);
            pActual = itemView.findViewById(R.id.progress_actual);
            lblGoal = itemView.findViewById(R.id.lbl_goal);
            lblActual = itemView.findViewById(R.id.lbl_actual);

            itemView.setOnClickListener(v -> listener.OnClick(eArea));
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
