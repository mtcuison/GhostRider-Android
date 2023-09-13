/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.dailyCollectionPlan
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textview.MaterialTextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.lib.addressbook.data.dao.DMobileRequest;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;

import java.util.ArrayList;
import java.util.List;

public class MobileInfoAdapter_Log extends RecyclerView.Adapter<MobileInfoAdapter_Log.MobilenoxHolder>  {

    private List<DMobileRequest.CNAMobileInfo> mobileUpdates = new ArrayList<>();
    private final OnItemInfoClickListener mListener;

    public MobileInfoAdapter_Log(OnItemInfoClickListener mListener) {
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public MobilenoxHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_mobile_log, parent, false);
        return new MobilenoxHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MobilenoxHolder holder, int position) {
        DMobileRequest.CNAMobileInfo current = mobileUpdates.get(position);
        if(current.mobilePrimaryx.equalsIgnoreCase("1")) {
            holder.tvPrimary.setVisibility(View.VISIBLE);
            holder.tvPrimary.setText("Primary");
        }
        holder.tvDetails.setText(current.sMobileNo);
        holder.tvRemarks.setText(current.mobileRemarksx);
    }

    @Override
    public int getItemCount() {
        return mobileUpdates.size();
    }

    public void setMobileNox(List<DMobileRequest.CNAMobileInfo> mobileUpdates) {
        this.mobileUpdates = mobileUpdates;
        notifyDataSetChanged();
    }

    class MobilenoxHolder extends RecyclerView.ViewHolder {
        private MaterialTextView tvDetails;
        private MaterialTextView tvPrimary;
        private MaterialTextView tvRemarks;

        public MobilenoxHolder(@NonNull View itemView, OnItemInfoClickListener listener) {
            super(itemView);
            tvDetails = itemView.findViewById(R.id.tvDetails);
            tvPrimary = itemView.findViewById(R.id.tvPrimary);
            tvRemarks = itemView.findViewById(R.id.tvRemarks);

            tvDetails.setOnClickListener(v ->{
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION) {
                    listener.OnMobileNoClick(tvDetails.getText().toString());
                }
            });
        }
    }

    public interface OnItemInfoClickListener {
        void OnDelete(int position);
        void OnMobileNoClick(String MobileNo);
    }
}
