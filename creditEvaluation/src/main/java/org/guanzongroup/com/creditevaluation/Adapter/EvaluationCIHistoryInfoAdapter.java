/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.creditEvaluation
 * Electronic Personnel Access Control Security System
 * project file created : 4/8/22, 9:45 AM
 * project file last modified : 4/8/22, 9:45 AM
 */

package org.guanzongroup.com.creditevaluation.Adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import org.guanzongroup.com.creditevaluation.Core.oPreview;
import org.guanzongroup.com.creditevaluation.R;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchPerformance;

import java.util.HashMap;
import java.util.List;

public class EvaluationCIHistoryInfoAdapter extends RecyclerView.Adapter<EvaluationCIHistoryInfoAdapter.EvaluationViewHolder> {

    private final List<oPreview> poDetails;

    public EvaluationCIHistoryInfoAdapter(List<oPreview> foDetails) {
        this.poDetails = foDetails;
    }

    @NonNull
    @Override
    public EvaluationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_evaluation_ci_history_info, parent, false);
        return new EvaluationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EvaluationViewHolder holder, int position) {
        oPreview loDetail = poDetails.get(position);
        holder.lnHeaderx.setVisibility(loDetail.isHeader());
        holder.lnDataxxx.setVisibility(loDetail.isContent());
        holder.txtHeader.setText(loDetail.getTitle());
        holder.txtLabelx.setText(loDetail.getLabel());
        holder.txtContnt.setText(loDetail.getValue());
        String lsValue = loDetail.getValue();
        if(lsValue.equalsIgnoreCase("Correct")){
            holder.txtContnt.setTextColor(Color.GREEN);
        } else if(lsValue.equalsIgnoreCase("Incorrect")){
            holder.txtContnt.setTextColor(Color.RED);
        } else {
            holder.txtContnt.setTextColor(Color.BLACK);
        }
//        if(!loDetail.getValue().equalsIgnoreCase("Correct") &&
//                !loDetail.getValue().equalsIgnoreCase("Incorrect")) {
//            holder.txtContnt.setTextColor(Color.parseColor("#FF0000"));
//        } else if(loDetail.getValue().equalsIgnoreCase("Correct")) {
//            holder.txtContnt.setTextColor(Color.parseColor("#009A17"));
//        } else if(loDetail.getValue().equalsIgnoreCase("Incorrect")) {
//            holder.txtContnt.setTextColor(Color.parseColor("#CD001A"));
//        }
        if(!"".equalsIgnoreCase(loDetail.getDescription())) {
            holder.txtDescrp.setVisibility(View.VISIBLE);
            holder.txtDescrp.setText(loDetail.getDescription());
        } else {
            holder.txtDescrp.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return poDetails.size();
    }

    private void displayHeader(EvaluationViewHolder foHolderx, oPreview foPreview) {
        foHolderx.txtHeader.setText(foPreview.getTitle());
    }

    private void displayData(EvaluationViewHolder foHolderx, oPreview foPreview) {
        foHolderx.txtLabelx.setText(foPreview.getLabel());
        foHolderx.txtContnt.setText(foPreview.getValue());
    }

    public static class EvaluationViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout lnHeaderx, lnDataxxx;
        private TextView txtHeader, txtDescrp, txtLabelx, txtContnt;

        public EvaluationViewHolder(@NonNull View itemView) {
            super(itemView);
            lnHeaderx = itemView.findViewById(R.id.layout_header);
            lnDataxxx = itemView.findViewById(R.id.layout_data);
            txtHeader = itemView.findViewById(R.id.txt_header);
            txtDescrp = itemView.findViewById(R.id.txt_description);
            txtLabelx = itemView.findViewById(R.id.txt_label);
            txtContnt = itemView.findViewById(R.id.txt_content);
        }

    }

}
