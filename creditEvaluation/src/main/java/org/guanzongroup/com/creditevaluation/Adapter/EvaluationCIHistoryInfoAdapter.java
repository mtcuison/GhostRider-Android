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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import org.guanzongroup.com.creditevaluation.R;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchPerformance;

import java.util.HashMap;

public class EvaluationCIHistoryInfoAdapter extends RecyclerView.Adapter<EvaluationCIHistoryInfoAdapter.EvaluationViewHolder> {

    private final HashMap<String, String> poDetails;

    public EvaluationCIHistoryInfoAdapter(HashMap<String, String> foDetails) {
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
        /** Assign value based on hashmap structure **/
    }

    @Override
    public int getItemCount() {
        return poDetails.size();
    }

    public static class EvaluationViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout lnHeaderx, lnDataxxx;
        private TextView txtHeader, txtLabelx, txtContnt;

        public EvaluationViewHolder(@NonNull View itemView) {
            super(itemView);
            lnHeaderx = itemView.findViewById(R.id.layout_header);
            lnDataxxx = itemView.findViewById(R.id.layout_data);
            txtHeader = itemView.findViewById(R.id.txt_header);
            txtLabelx = itemView.findViewById(R.id.txt_label);
            txtContnt = itemView.findViewById(R.id.txt_content);

        }

    }

}
