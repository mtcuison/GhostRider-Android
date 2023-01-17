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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.guanzongroup.ghostrider.notifications.Obj.MessageItemList;
import org.rmj.guanzongroup.ghostrider.notifications.R;

import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ItemViewHolder> {

    private final List<MessageItemList> messages;
    private final OnItemClickListener mListener;

    public MessageListAdapter(List<MessageItemList> messages, OnItemClickListener mListener) {
        this.messages = messages;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.messages_list_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        MessageItemList message = messages.get(position);
        holder.message = message;
        holder.lblName.setText(message.getName());
        holder.lblBody.setText(message.getMessage());
        holder.lblDate.setText(message.getDateTime());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        public MessageItemList message;
        public ImageView imgSender;
        public TextView lblName;
        public TextView lblBody;
        public TextView lblDate;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            imgSender = itemView.findViewById(R.id.img_messageSender);
            lblName = itemView.findViewById(R.id.lbl_messageSender);
            lblBody = itemView.findViewById(R.id.lbl_messageBody);
            lblDate = itemView.findViewById(R.id.lbl_messageDateTime);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    mListener.OnClick(message.getName(), message.getMessage(), message.getSendrID());
                }
            });
        }
    }

    public interface OnItemClickListener{
        void OnClick(String Title, String Message, String SenderID);
    }
}
