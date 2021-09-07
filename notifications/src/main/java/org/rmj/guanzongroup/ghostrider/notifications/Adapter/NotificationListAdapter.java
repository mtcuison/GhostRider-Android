/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.notifications
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.notifications.Adapter;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.guanzongroup.ghostrider.notifications.Object.NotificationItemList;
import org.rmj.guanzongroup.ghostrider.notifications.R;

import java.util.List;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.ItemViewHolder> {

    private final List<NotificationItemList> notificationItemLists;
    private final OnItemClickListener mListener;

    public NotificationListAdapter(List<NotificationItemList> notificationItemLists, OnItemClickListener listener) {
        this.notificationItemLists = notificationItemLists;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_list_item, parent, false);
        return new ItemViewHolder(v);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        NotificationItemList message = notificationItemLists.get(position);
        holder.message = message;
        if(!message.getName().isEmpty()) {
            holder.lblSender.setText(message.getName());
            holder.imgSendr.setImageResource(R.drawable.ic_user_profile);
        } else {
            holder.lblSender.setText("SYSTEM NOTIFICATION");
            holder.imgSendr.setImageResource(R.drawable.ic_guanzon_logo);
        }
        holder.lblTitlex.setText(message.getTitle());
        holder.lblBodyxx.setText(message.getMessage());
        holder.lblDateTm.setText(message.getDateTime());

        if(message.getStatus().equalsIgnoreCase("2")){
            holder.lblSender.setTypeface(Typeface.DEFAULT_BOLD);
            holder.lblTitlex.setTypeface(Typeface.DEFAULT_BOLD);
            holder.lblBodyxx.setTypeface(Typeface.DEFAULT_BOLD);
            holder.lblDateTm.setTypeface(Typeface.DEFAULT_BOLD);
        }
    }

    @Override
    public int getItemCount() {
        return notificationItemLists.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        public NotificationItemList message;
        public ImageView imgSendr;
        public TextView lblSender;
        public TextView lblTitlex;
        public TextView lblBodyxx;
        public TextView lblDateTm;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            imgSendr = itemView.findViewById(R.id.img_messageSender);
            lblSender = itemView.findViewById(R.id.lbl_messageSender);
            lblTitlex = itemView.findViewById(R.id.lbl_messageTitle);
            lblBodyxx = itemView.findViewById(R.id.lbl_messageBody);
            lblDateTm = itemView.findViewById(R.id.lbl_messageDateTime);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    mListener.OnClick(message.getMessageID(), message.getTitle(), message.getMessage(), message.getName(), message.getDateTime(), message.getReceipt());
                }
            });
        }
    }

    public interface OnItemClickListener{
        void OnClick(String ID, String Title, String Message, String Sender, String Date, String Receipt);
        void OnActionButtonClick(String message);
    }
}
