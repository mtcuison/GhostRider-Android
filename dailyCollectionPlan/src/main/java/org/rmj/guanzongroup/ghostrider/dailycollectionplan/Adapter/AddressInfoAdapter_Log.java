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

import org.rmj.g3appdriver.lib.addressbook.data.dao.DAddressRequest;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;

import java.util.ArrayList;
import java.util.List;

public class AddressInfoAdapter_Log extends RecyclerView.Adapter<AddressInfoAdapter_Log.AddressHolder> {

    private List<DAddressRequest.CNA_AddressInfo> addressUpdates = new ArrayList<>();

    @NonNull
    @Override
    public AddressHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_address_log, parent, false);
        return new AddressHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressHolder holder, int position) {
        DAddressRequest.CNA_AddressInfo current = addressUpdates.get(position);
        if(current.addressPrimaryx.equalsIgnoreCase("1")){
            holder.tvPrimary.setVisibility(View.VISIBLE);
            holder.tvPrimary.setText("Primary");
        }
        holder.tvAddressTp.setVisibility(View.VISIBLE);
        holder.tvAddressTp.setText((current.cAddrssTp.equalsIgnoreCase("0")) ? "Permanent" : "Present");
        holder.tvDetails.setText(current.townName +  ", " + current.provName);
        holder.tvAddress.setText(current.sHouseNox + " " + current.sAddressx + ", " + current.brgyName);
        holder.tvRemarks.setText(current.addressRemarksx);
    }

    @Override
    public int getItemCount() {
        return addressUpdates.size();
    }

    public void setAddress(List<DAddressRequest.CNA_AddressInfo> addressUpdates) {
        this.addressUpdates = addressUpdates;
        notifyDataSetChanged();
    }

    class AddressHolder extends RecyclerView.ViewHolder {
        private MaterialTextView tvPrimary;
        private MaterialTextView tvAddressTp;
        private MaterialTextView tvDetails;
        private MaterialTextView tvAddress;
        private MaterialTextView tvRemarks;

        public AddressHolder(@NonNull View itemView) {
            super(itemView);
            tvPrimary = itemView.findViewById(R.id.tvPrimary);
            tvAddressTp = itemView.findViewById(R.id.tvAddressTp);
            tvDetails = itemView.findViewById(R.id.tvDetails);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvRemarks = itemView.findViewById(R.id.tvRemarks);
        }
    }

}

