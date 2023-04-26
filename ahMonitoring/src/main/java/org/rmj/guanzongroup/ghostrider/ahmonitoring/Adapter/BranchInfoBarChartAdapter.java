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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.material.textview.MaterialTextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EBranchPerformance;
import org.rmj.g3appdriver.lib.BullsEye.PerformancePeriod;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;

import java.text.DecimalFormat;
import java.util.List;

public class BranchInfoBarChartAdapter extends RecyclerView.Adapter<BranchInfoBarChartAdapter.ChartViewHolder> {

    private final List<EBranchPerformance> branchPerformances;
    private final OnBranchPerformanceClickListener mListener;
    private final String psType;

    public interface OnBranchPerformanceClickListener{
        void OnClick(EBranchPerformance eAreaPerformance);
    }

    public BranchInfoBarChartAdapter(List<EBranchPerformance> branchPerformances, String fsType, OnBranchPerformanceClickListener listener){
        this.branchPerformances = branchPerformances;
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
        EBranchPerformance area = branchPerformances.get(position);
        holder.txtPeriod.setText(PerformancePeriod.parseDateLabel(area.getPeriodxx()));
        holder.eArea = branchPerformances.get(position);

        if("MC".equalsIgnoreCase(psType)) {
            int lnGoal = getPercentageProgress(area.getMCGoalxx(),area.getMCGoalxx());
            holder.pGoal.setMax(getDynamicSize(area.getMCGoalxx()));
            holder.pGoal.setProgress(lnGoal);
            holder.lblGoal.setText(getPercentageValue(area.getMCGoalxx(),area.getMCGoalxx()));

            int lnActual = getPercentageProgress(area.getMCActual(), area.getMCGoalxx());
            holder.pActual.setMax(getDynamicSize(area.getMCGoalxx()));
            holder.pActual.setProgress(lnActual);
            holder.lblActual.setText(getPercentageValue(area.getMCActual(), area.getMCGoalxx()));

            if(area.getMCActual() > area.getMCGoalxx()) {
                int lnExcess = getPercentageProgress(area.getMCActual() - area.getMCGoalxx(), area.getMCGoalxx());
                holder.pExcess.setMax(getDynamicSize(area.getMCGoalxx()));
                holder.pExcess.setProgress(lnExcess);
                holder.lblExcess.setText(getPercentageValue(area.getMCActual() - area.getMCGoalxx(), area.getMCGoalxx()));
            }

        } else {

            int lnGoal = getPercentageProgress(area.getSPGoalxx(),area.getSPGoalxx());
            holder.pGoal.setMax(getDynamicSize(area.getSPGoalxx()));
            holder.pGoal.setProgress(lnGoal);
            holder.lblGoal.setText(getPercentageValue(area.getSPGoalxx(),area.getSPGoalxx()));

            int lnActual = getPercentageProgress(area.getSPActual(), area.getSPGoalxx());
            holder.pActual.setMax(getDynamicSize(area.getSPGoalxx()));
            holder.pActual.setProgress(lnActual);
            holder.lblActual.setText(getPercentageValue(area.getSPActual(), area.getSPGoalxx()));

            if(area.getSPActual() > area.getSPGoalxx()) {
                int lnExcess = getPercentageProgress(area.getSPActual() - area.getSPGoalxx(), area.getSPGoalxx());
                holder.pExcess.setMax(getDynamicSize(area.getSPGoalxx()));
                holder.pExcess.setProgress(lnExcess);
                holder.lblExcess.setText(getPercentageValue(area.getSPActual() - area.getSPGoalxx(), area.getSPGoalxx()));
            }
        }

    }

    @Override
    public int getItemCount() {
        return branchPerformances.size();
    }

    public static class ChartViewHolder extends RecyclerView.ViewHolder{

        public MaterialTextView txtPeriod,lblGoal,lblActual ,lblExcess;

        public ProgressBar pGoal;
        public ProgressBar pActual;
        public ProgressBar pExcess;
        public EBranchPerformance eArea;

        public ChartViewHolder(@NonNull View itemView, OnBranchPerformanceClickListener listener) {
            super(itemView);
            txtPeriod = itemView.findViewById(R.id.lbl_period);
            pGoal = itemView.findViewById(R.id.progress_goal);
            pActual = itemView.findViewById(R.id.progress_actual);
            pExcess = itemView.findViewById(R.id.progress_excess);
            lblGoal = itemView.findViewById(R.id.lbl_goal);
            lblActual = itemView.findViewById(R.id.lbl_actual);
            lblExcess = itemView.findViewById(R.id.lbl_excess);

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
