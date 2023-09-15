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
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.android.material.divider.MaterialDivider;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;
import  com.google.android.material.checkbox.MaterialCheckBox;


import org.guanzongroup.com.creditevaluation.Core.oPreview;
import org.guanzongroup.com.creditevaluation.R;

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
        private MaterialTextView txtHeader, txtDescrp, txtLabelx, txtContnt;

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
