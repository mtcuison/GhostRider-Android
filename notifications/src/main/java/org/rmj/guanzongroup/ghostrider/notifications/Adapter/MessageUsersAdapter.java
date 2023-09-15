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

import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DMessages;
import org.rmj.g3appdriver.etc.FormatUIText;
import org.rmj.guanzongroup.ghostrider.notifications.R;

import java.util.List;

public class MessageUsersAdapter extends RecyclerView.Adapter<MessageUsersAdapter.ItemViewHolder> {

    private final List<DMessages.MessageUsers> messages;
    private final OnItemClickListener mListener;

    public MessageUsersAdapter(List<DMessages.MessageUsers> messages, OnItemClickListener mListener) {
        this.messages = messages;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_message, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        DMessages.MessageUsers message = messages.get(position);
        holder.lblName.setText(message.sUserName);
        holder.lblBody.setText(message.sMessagex);
        holder.lblDate.setText(FormatUIText.FormatSenderMessageDateTime(message.dReceived));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnClick(message.sUserName, message.sMessagex, message.sUserIDxx);
            }
        });
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

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
        }
    }

    public interface OnItemClickListener{
        void OnClick(String Title, String Message, String SenderID);
    }
}
