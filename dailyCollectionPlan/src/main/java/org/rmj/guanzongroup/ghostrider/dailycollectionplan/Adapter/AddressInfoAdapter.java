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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DAddressRequest;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;

import java.util.ArrayList;
import java.util.List;

public class AddressInfoAdapter extends RecyclerView.Adapter<AddressInfoAdapter.AddressHolder> {

    private List<DAddressRequest.CustomerAddressInfo> addressUpdates = new ArrayList<>();
    private final OnDeleteInfoListener mListener;

    public AddressInfoAdapter(OnDeleteInfoListener listener){
        this.mListener = listener;
    }

    @NonNull
    @Override
    public AddressHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new AddressHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressHolder holder, int position) {
        DAddressRequest.CustomerAddressInfo current = addressUpdates.get(position);
        if(current.cPrimaryx.equalsIgnoreCase("1")){
            holder.tvPrimary.setVisibility(View.VISIBLE);
            holder.tvPrimary.setText("Primary");
        }
        holder.tvAddressTp.setVisibility(View.VISIBLE);
        holder.tvAddressTp.setText((current.cAddrssTp.equalsIgnoreCase("0")) ? "Permanent" : "Present");
        holder.tvDetails.setText(current.sTownName +  ", " + current.sProvName);
        holder.tvAddress.setText(current.sHouseNox + " " + current.sAddressx + ", " + current.sBrgyName);
    }

    @Override
    public int getItemCount() {
        return addressUpdates.size();
    }

    public void setAddress(List<DAddressRequest.CustomerAddressInfo> addressUpdates) {
        this.addressUpdates = addressUpdates;
        notifyDataSetChanged();
    }

    static class AddressHolder extends RecyclerView.ViewHolder {
        private final TextView tvPrimary;
        private final TextView tvAddressTp;
        private final TextView tvDetails;
        private final TextView tvAddress;

        public AddressHolder(@NonNull View itemView, OnDeleteInfoListener listener) {
            super(itemView);
            tvPrimary = itemView.findViewById(R.id.tvPrimary);
            tvAddressTp = itemView.findViewById(R.id.tvAddressTp);
            tvDetails = itemView.findViewById(R.id.tvDetails);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            ImageView icDelete = itemView.findViewById(R.id.icDelete);

            icDelete.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION) {
                    listener.OnDelete(position);
                }
            });
        }
    }

    public interface OnDeleteInfoListener{
        void OnDelete(int position);
    }

}

