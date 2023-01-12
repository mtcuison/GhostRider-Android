/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.dev.Database.Entities.EInventoryDetail;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;

import java.util.List;

public class InventoryItemAdapter extends RecyclerView.Adapter<InventoryItemAdapter.ItemViewHolder> {

    private final List<EInventoryDetail> randomItems;
    private final OnItemClickListener mListener;

    public InventoryItemAdapter(List<EInventoryDetail> randomItems, OnItemClickListener mListener) {
        this.randomItems = randomItems;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_inventory, parent, false);
        return new ItemViewHolder(view, mListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        EInventoryDetail item = randomItems.get(position);
        holder.item = item;
        holder.lblTransNox.setText("Transaction No. : " + item.getPartsIDx());
        holder.lblItemCode.setText("Item Code : " + item.getBarrCode());
        holder.lblItemDesc.setText("Description : " + item.getDescript());

        if(item.getTranStat().equalsIgnoreCase("0")){
            holder.imgStatusx.setImageResource(R.drawable.ic_baseline_add_24);
        } else {
            holder.imgStatusx.setImageResource(R.drawable.ic_baseline_done_24);
        }
    }

    @Override
    public int getItemCount() {
        return randomItems.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        public EInventoryDetail item;
        public TextView lblTransNox;
        public TextView lblItemCode;
        public TextView lblItemDesc;
        public ImageView imgStatusx;

        public ItemViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            lblTransNox = itemView.findViewById(R.id.lbl_itemTransNox);
            lblItemCode = itemView.findViewById(R.id.lbl_itemCode);
            lblItemDesc = itemView.findViewById(R.id.lbl_itemDescription);
            imgStatusx = itemView.findViewById(R.id.img_status);

            itemView.setOnClickListener(v -> {
                boolean isUpdated = false;
                if(item != null){
                    isUpdated = item.getTranStat() != null && item.getTranStat().equalsIgnoreCase("1");
                }
                listener.OnClick(item.getTransNox(),
                        item.getPartsIDx(),
                        item.getBarrCode(),
                        isUpdated,
                        new String[]{item.getDescript(), item.getRemarksx(), String.valueOf(item.getActCtr01())});
            });
        }
    }

    public interface OnItemClickListener{
        void OnClick(String TransNox,
                     String PartsID,
                     String BarCode,
                     boolean isUpdated,
                     String[] args);
    }
}
