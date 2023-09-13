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

import org.rmj.g3appdriver.lib.addressbook.data.entity.EAddressUpdate;
import org.rmj.g3appdriver.lib.addressbook.data.entity.EMobileUpdate;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;

import java.util.List;

public class UpdateRequestAdapter extends RecyclerView.Adapter<UpdateRequestAdapter.RequestViewHolder>{
    private static final String TAG = "Update Request Adapter";

    private final List<EAddressUpdate> addressList;
    private final List<EMobileUpdate> mobileList;

    public UpdateRequestAdapter(List<EAddressUpdate> addressList, List<EMobileUpdate> mobileList) {
        this.addressList = addressList;
        this.mobileList = mobileList;
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_update_request, parent, false);
        return new RequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        if(addressList.size() > 0){
            holder.addressUpdate = addressList.get(position);
            holder.lblDetail.setText(addressList.get(position).getHouseNox());
        }
        if(mobileList.size() > 0){
            holder.mobileUpdate = mobileList.get(position);
            holder.lblDetail.setText(mobileList.get(position).getMobileNo());
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class RequestViewHolder extends RecyclerView.ViewHolder {

        public EAddressUpdate addressUpdate;
        public EMobileUpdate mobileUpdate;
        public MaterialTextView lblDetail;

        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);
            lblDetail = itemView.findViewById(R.id.lbl_list_item_detail);
        }
    }

    private String formatAddress(String HouseNox, String Address, String Barangay, String Town){
        String lsAddress = "";
        if(!HouseNox.trim().isEmpty()){
            lsAddress = lsAddress + HouseNox +", ";
        }
        if(!Address.trim().isEmpty()){
            lsAddress = lsAddress + Address + ", ";
        }
        if(!Barangay.trim().isEmpty()){
            lsAddress = lsAddress + Barangay + ", ";
        }
        return lsAddress;
    }
}
