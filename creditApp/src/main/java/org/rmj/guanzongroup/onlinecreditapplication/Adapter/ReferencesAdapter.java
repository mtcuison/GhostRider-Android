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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.Reference;
import org.rmj.guanzongroup.onlinecreditapplication.R;

import java.util.List;

public class ReferencesAdapter extends RecyclerView.Adapter<ReferencesAdapter.ItemViewHolder>{

    private final List<Reference> referenceInfoModels;
    private final OnAdapterClick mListener;

    public ReferencesAdapter(List<Reference> referenceInfoModels, OnAdapterClick fmListenr) {
        this.referenceInfoModels = referenceInfoModels;
        this.mListener = fmListenr;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_preferences, parent, false);
        return new ItemViewHolder(view, mListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Reference reference = referenceInfoModels.get(position);
        Reference poRef = new Reference(reference.getFullname(),reference.getAddress1(),reference.getTownCity(),reference.getTownName(),reference.getContactN());
        String lsRefNoxx = String.valueOf(position+1);

        holder.lblReferenceNo.setText("Reference No. " + lsRefNoxx);
        holder.lblRefName.setText(reference.getFullname());
        holder.lblRefTown.setText(reference.getAddress1() + ", " + reference.getTownName());
        holder.lblRefContact.setText(reference.getContactN());
//        if (poRef.isDataValid()){
//            holder.lblReferenceNo.setText("Reference No. " + lsRefNoxx);
//            holder.lblRefName.setText(reference.getFullname());
//            holder.lblRefTown.setText(reference.getAddress1() + ", " + reference.getTownCity());
//            holder.lblRefContact.setText(reference.getContactN());
//        }else{
//            holder.referenceLayout.setVisibility(View.GONE);
//        }
    }

    @Override
    public int getItemCount() {
        return referenceInfoModels.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView lblRefName;
        private TextView lblRefTown;
        private TextView lblRefContact;
        private TextView lblReferenceNo;
        private ImageView imgRemove;
        private LinearLayout referenceLayout;

        public ItemViewHolder(@NonNull View itemView, OnAdapterClick listener) {
            super(itemView);

            lblRefName = itemView.findViewById(R.id.lbl_itemRefName);
            lblRefTown = itemView.findViewById(R.id.lbl_itemRefTown);
            lblRefContact = itemView.findViewById(R.id.lbl_itemRefContactN);
            lblReferenceNo = itemView.findViewById(R.id.lbl_reference_no);
            imgRemove = itemView.findViewById(R.id.img_remove);
            referenceLayout = itemView.findViewById(R.id.referenceItemLayout);

            imgRemove.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION) {
                    listener.onRemove(position);
                }
            });

            lblRefContact.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION) {
                    listener.onCallMobile(lblRefContact.getText().toString());
                }
            });
        }
    }

    public interface OnAdapterClick {
        void onRemove(int position);
        void onCallMobile(String fsMobileN);
    }

}
