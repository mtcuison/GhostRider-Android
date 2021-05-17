/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.notifications
 * Electronic Personnel Access Control Security System
 * project file created : 5/17/21 2:47 PM
 * project file last modified : 5/17/21 2:47 PM
 */

package org.rmj.guanzongroup.ghostrider.notifications.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DNotifications;
import org.rmj.guanzongroup.ghostrider.notifications.R;

import java.util.List;

public class MessagesViewAdapter extends RecyclerView.Adapter<MessagesViewAdapter.MessagesViewHolder> {

    private List<DNotifications.UserNotificationInfo> plMessage;

    public MessagesViewAdapter(List<DNotifications.UserNotificationInfo> plMessage) {
        this.plMessage = plMessage;
    }

    @NonNull
    @Override
    public MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_messages, parent, false);
        return new MessagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesViewHolder holder, int position) {
        holder.cvSenderMsg.setVisibility(View.VISIBLE);
        holder.lblSenderMsg.setText(plMessage.get(position).Messagex);
    }

    @Override
    public int getItemCount() {
        return plMessage.size();
    }

    public static class MessagesViewHolder extends RecyclerView.ViewHolder{

        public TextView lblSenderMsg,
                        lblRecpntMsg;
        public CardView cvSenderMsg,
                cvRecpntMsg;

        public MessagesViewHolder(@NonNull View itemView) {
            super(itemView);
            lblSenderMsg = itemView.findViewById(R.id.lbl_sender_message);
            lblRecpntMsg = itemView.findViewById(R.id.lbl_recipient_message);
            cvSenderMsg = itemView.findViewById(R.id.cv_sender_message);
            cvRecpntMsg = itemView.findViewById(R.id.cv_recipient_message);
        }
    }
}
