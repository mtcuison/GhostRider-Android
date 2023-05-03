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

package org.rmj.guanzongroup.onlinecreditapplication.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import org.rmj.g3appdriver.GCircle.Apps.integsys.CreditApp.model.Dependent;
import org.rmj.guanzongroup.onlinecreditapplication.R;

import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class DependentAdapter extends RecyclerView.Adapter<DependentAdapter.ItemViewHolder>{

    private final List<Dependent.DependentInfo> infoModels;
    private final OnRemoveDependent listener;

    public interface OnRemoveDependent{
        void OnRemove(int args);
    }

    public DependentAdapter(List<Dependent.DependentInfo> dependentInfoModels, OnRemoveDependent listener) {
        this.infoModels = dependentInfoModels;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_dependent, parent, false);
        return new ItemViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Dependent.DependentInfo dependentsInfoModel = infoModels.get(position);

        holder.lblDpdName.setText("Fullname. : " + dependentsInfoModel.getFullName());
        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnRemove(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return infoModels.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        private MaterialTextView lblDpdName;
        private MaterialTextView lblDpdRelation;
        private MaterialButton btnRemove;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            lblDpdName = itemView.findViewById(R.id.lbl_list_cap_dependent);
            btnRemove = itemView.findViewById(R.id.btn_list_dpdRemove);
        }
    }


}
