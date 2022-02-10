/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 6/8/21 1:10 PM
 * project file last modified : 6/8/21 1:10 PM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DBranchOpeningMonitor;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;

public class BranchOpeningAdapter extends RecyclerView.Adapter<BranchOpeningAdapter.OpeningViewHolder> {

    private final Context mContext;
    private final List<DBranchOpeningMonitor.BranchOpeningInfo> poOpening;
    private final OnAdapterItemClickListener mListener;

    public interface OnAdapterItemClickListener{
        void OnClick();
    }

    public BranchOpeningAdapter(Context context, List<DBranchOpeningMonitor.BranchOpeningInfo> poOpening, OnAdapterItemClickListener listener) {
        this.mContext = context;
        this.poOpening = poOpening;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public OpeningViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_branch_opening, parent, false);
        return new OpeningViewHolder(view, mListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull OpeningViewHolder holder, int position) {
        DBranchOpeningMonitor.BranchOpeningInfo loMonitor = poOpening.get(position);
        holder.lblBranch.setText(loMonitor.sBranchNm);
        holder.lblOpenTime.setText(loMonitor.sTimeOpen);
        holder.lblTimeOpened.setText(loMonitor.sOpenNowx);

        DateTimeFormatter format = new DateTimeFormatterBuilder()
                .appendPattern("hh:mm:ss a")
                .parseCaseInsensitive()
                .parseLenient()
                .toFormatter();
        LocalTime loTime1 = LocalTime.parse(loMonitor.sTimeOpen, format);
        LocalTime loTime2 = LocalTime.parse(loMonitor.sOpenNowx, format);
        if(loTime1.isAfter(loTime2)) {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.branch_opening));
        } else {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.branch_opening_late));
        }
    }

    @Override
    public int getItemCount() {
        return poOpening.size();
    }

    public static class OpeningViewHolder extends RecyclerView.ViewHolder{

        public CardView cardView;
        public TextView lblBranch, lblOpenTime, lblTimeOpened;

        public OpeningViewHolder(@NonNull View itemView, OnAdapterItemClickListener listener) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardview_branchOpening);
            lblBranch = itemView.findViewById(R.id.lbl_list_branchName);
            lblOpenTime = itemView.findViewById(R.id.lbl_list_openingTime);
            lblTimeOpened = itemView.findViewById(R.id.lbl_list_timeOpened);

            itemView.setOnClickListener(v -> listener.OnClick());
        }
    }
}
