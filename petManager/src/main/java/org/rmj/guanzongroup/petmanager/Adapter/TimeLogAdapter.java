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

package org.rmj.guanzongroup.petmanager.Adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import org.rmj.g3appdriver.dev.Database.Entities.ESelfieLog;
import org.rmj.g3appdriver.etc.FormatUIText;
import org.rmj.guanzongroup.petmanager.R;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;

public class TimeLogAdapter extends RecyclerView.Adapter<TimeLogAdapter.TimeLogViewHolder> {

    private final List<ESelfieLog> selfieLogList;

    private final OnTimeLogActionListener mListener;

    public interface OnTimeLogActionListener{
        void OnImagePreview(String sTransNox);
    }

    public TimeLogAdapter(List<ESelfieLog> selfieLogList, OnTimeLogActionListener listener) {
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
            holder.lblDateLog.setText(FormatUIText.formatGOCasBirthdate(dTime[0]));
            holder.lblTimeLog.setText(FormatUIText.HHMMSS_TO_HHMMA_12(dTime[1]));
            if(selfieLogList.get(position).getBranchCd() != null) {
                holder.lblBranchCD.setText(selfieLogList.get(position).getBranchCd());
            } else {
                holder.lblBranchCD.setVisibility(View.GONE);
            }
            if (selfieLogList.get(position).getSendStat().equalsIgnoreCase("1")) {
                holder.lblStatusx.setText("Uploaded");
                holder.lblStatusx.setTextColor(Color.parseColor("#008000"));
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

        ESelfieLog logSelfie;
        MaterialTextView lblBranchCD,lblTimeLog,lblDateLog, lblStatusx;
        MaterialButton btnPreview;

        public TimeLogViewHolder(@NonNull View itemView, OnTimeLogActionListener listener) {
            super(itemView);

            lblDateLog = itemView.findViewById(R.id.lbl_list_logDate);
            lblBranchCD = itemView.findViewById(R.id.lbl_list_BranchCD);
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
