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

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textview.MaterialTextView;

import org.rmj.g3appdriver.dev.Database.Entities.EBranchPerformance;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;

import java.util.List;

public class AreaMonitoringAdapter extends RecyclerView.Adapter<AreaMonitoringAdapter.ChartViewHolder> {

    private final List<EBranchPerformance> areaPerformances;
    private final OnBranchPerformanceClickListener mListener;
    private final int nPriority;

    public interface OnBranchPerformanceClickListener{
        void OnClick(String sBranchCd);
    }

    public AreaMonitoringAdapter(List<EBranchPerformance> areaPerformances, int nPriority, OnBranchPerformanceClickListener listener){
        this.areaPerformances = areaPerformances;
        this.nPriority = nPriority ;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ChartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_top_performing, parent, false);
        return new ChartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChartViewHolder holder, int position) {
        try {
            EBranchPerformance area = areaPerformances.get(position);
            holder.txtBranch.setText(area.getBranchNm());
            holder.txtMCGoal.setText(String.valueOf(area.getMCGoalxx()));
            holder.txtSPGoal.setText(String.valueOf((int) area.getSPGoalxx()));
            holder.txtJOGoal.setText(String.valueOf(area.getJOGoalxx()));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.OnClick(area.getBranchCd());
                }
            });
            if (nPriority == 0) {
//                holder.lblPercentage.setText((Math.round(area.getMCActual() / area.getMCGoalxx() * 100)) + "%");
//                holder.pi.setProgress((int)(Math.round(area.getMCActual() / area.getMCGoalxx() * 100)));
            } else if (nPriority == 1) {
                holder.lblPercentage.setText(String.valueOf((Math.round(area.getSPActual() / area.getSPGoalxx() * 100))+ "%"));
                holder.pi.setProgress((Math.round(area.getSPActual() / area.getSPGoalxx() * 100)));
            } else {
                holder.lblPercentage.setText(String.valueOf((Math.round(area.getJOActual() / area.getJOGoalxx() * 100))+ "%"));
                holder.pi.setProgress((Math.round(area.getJOActual() / area.getJOGoalxx() * 100)));
            }


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return areaPerformances.size();
    }

    public static class ChartViewHolder extends RecyclerView.ViewHolder{

        public MaterialTextView txtBranch, txtMCGoal,txtSPGoal,txtJOGoal,lblPercentage;
        public CircularProgressIndicator pi;
        public ConstraintLayout btnBrPerformance;
        public ChartViewHolder(@NonNull View itemView) {
            super(itemView);
            txtBranch = itemView.findViewById(R.id.lbl_top_branches);
            txtMCGoal = itemView.findViewById(R.id.lblmcSalesgoal);
            txtSPGoal = itemView.findViewById(R.id.lblspSalesgoal);
            txtJOGoal = itemView.findViewById(R.id.lblJOgoal);
            lblPercentage = itemView.findViewById(R.id.lbl_percentage);
            pi = itemView.findViewById(R.id.cpi_percentage);
            btnBrPerformance = itemView.findViewById(R.id.btnBranchPerformance);

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