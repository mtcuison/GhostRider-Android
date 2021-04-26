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

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Adaper;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.GRider.Database.Entities.ELog_Selfie;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;

import java.util.List;

public class TimeLogAdapter extends RecyclerView.Adapter<TimeLogAdapter.TimeLogViewHolder> {

    private final List<ELog_Selfie> selfieLogList;

    private final OnTimeLogActionListener mListener;

    public interface OnTimeLogActionListener{
        void OnImagePreview(String sTransNox);
    }

    public TimeLogAdapter(List<ELog_Selfie> selfieLogList, OnTimeLogActionListener listener) {
        this.selfieLogList = selfieLogList;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public TimeLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_time_log, parent, false);
        return new TimeLogViewHolder(view, mListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TimeLogViewHolder holder, int position) {
        try {
            holder.logSelfie = selfieLogList.get(position);
            String[] dTime = selfieLogList.get(position).getLogTimex().split(" ");
            holder.lblDateLog.setText(dTime[0]);
            holder.lblTimeLog.setText(dTime[1]);
            if (selfieLogList.get(position).getSendStat().equalsIgnoreCase("1")) {
                holder.lblStatusx.setText("Uploaded");
                holder.lblStatusx.setTextColor(Color.parseColor("008000"));
            } else {
                holder.lblStatusx.setText("Sending");
                holder.lblStatusx.setTextColor(Color.RED);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return selfieLogList.size();
    }

    public static class TimeLogViewHolder extends RecyclerView.ViewHolder {

        ELog_Selfie logSelfie;
        TextView lblTimeLog;
        TextView lblDateLog;
        TextView lblStatusx;
        ImageButton btnPreview;

        public TimeLogViewHolder(@NonNull View itemView, OnTimeLogActionListener listener) {
            super(itemView);

            lblDateLog = itemView.findViewById(R.id.lbl_list_logDate);
            lblTimeLog = itemView.findViewById(R.id.lbl_list_logTime);
            lblStatusx = itemView.findViewById(R.id.lbl_list_logStatus);
            btnPreview = itemView.findViewById(R.id.btn_list_logImagePreview);

            btnPreview.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    listener.OnImagePreview(logSelfie.getTransNox());
                }
            });
        }
    }
}
