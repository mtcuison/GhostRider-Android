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
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import org.rmj.g3appdriver.dev.Database.Entities.EBranchPerformance;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;

import java.util.List;

public class AreaMonitoringAdapter extends RecyclerView.Adapter<AreaMonitoringAdapter.ChartViewHolder> {

    List<EBranchPerformance> areaPerformances;

    public AreaMonitoringAdapter(List<EBranchPerformance> areaPerformances){
        this.areaPerformances = areaPerformances;
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
            holder.txtMCGoal.setText(area.getMCGoalxx());
            holder.txtSPGoal.setText((int) area.getSPGoalxx());
            holder.txtJOGoal.setText(area.getJOGoalxx());
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return areaPerformances.size();
    }

    public static class ChartViewHolder extends RecyclerView.ViewHolder{

        public MaterialTextView txtBranch, txtMCGoal,txtSPGoal,txtJOGoal;

        public ChartViewHolder(@NonNull View itemView) {
            super(itemView);
            txtBranch = itemView.findViewById(R.id.lbl_top_branches);
            txtMCGoal = itemView.findViewById(R.id.lblmcSalesgoal);
            txtSPGoal = itemView.findViewById(R.id.lblspSalesgoal);
            txtJOGoal = itemView.findViewById(R.id.lblJOgoal);

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