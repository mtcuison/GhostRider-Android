/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.creditApp
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.guanzongroup.com.creditevaluation.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import org.guanzongroup.com.creditevaluation.R;

public class CreditEvaluationAdapter extends RecyclerView.Adapter<CreditEvaluationAdapter.ItemViewHolder>{

//    List<CreditApplicationCIItemModel> infoModels;
//
//    public CreditApplicationCIAdapter(List<CreditApplicationCIItemModel> dependentInfoModels) {
//        this.infoModels = dependentInfoModels;
//    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_ci_items, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
//        CreditApplicationCIItemModel ciInfoModel = infoModels.get(position);
//
//        holder.lblCiTitleValue.setText(ciInfoModel.getCiTitleValue());
//        holder.lblCiTitle.setText(ciInfoModel.getCiTitle());

        //holder.lblDpdRelation.setText("Address : " + dependentsInfoModel.getDpdRlationship() + " Age: " + dependentsInfoModel.getDpdAge());

    }

    @Override
    public int getItemCount() {
//        return infoModels.size();
        return 0;
    }


//    @Override
//    public int getItemCount() {
//        return otherInfoModels.size();
//    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        private EditText txtciTitleValue;
        private LinearLayout layoutCiValue;

        private RadioGroup rgCiFindingsValue;
        private TextView lblCiTitle;
        private TextView lblCiTitleValue;
        private MaterialButton btnRemove;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            lblCiTitle = itemView.findViewById(R.id.ciTitle);
            lblCiTitleValue = itemView.findViewById(R.id.lbltitleVal);
            rgCiFindingsValue = itemView.findViewById(R.id.rg_ci_ciFindingsValue);
            layoutCiValue = itemView.findViewById(R.id.layoutCiValue);
        }
    }


}
