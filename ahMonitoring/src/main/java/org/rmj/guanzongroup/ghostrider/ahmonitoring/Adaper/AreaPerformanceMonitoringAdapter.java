/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 10/14/21, 11:09 AM
 * project file last modified : 10/14/21, 11:08 AM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Adaper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EAreaPerformance;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchPerformance;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;

import java.text.DecimalFormat;
import java.util.List;

public class AreaPerformanceMonitoringAdapter extends RecyclerView.Adapter<AreaPerformanceMonitoringAdapter.OpeningViewHolder> {

    private final Context mContext;
    private final List<EBranchPerformance> poBranches;
    private final OnBranchClickListener mListener;
    private final int pnCategry;
    public static int index = -1;
    private final DecimalFormat currency_total = new DecimalFormat("###,###,###.###");

    public AreaPerformanceMonitoringAdapter(Context context, int fnCategry, List<EBranchPerformance> foBranches, OnBranchClickListener mListener) {
        this.mContext = context;
        this.mListener = mListener;
        this.poBranches = foBranches;
        this.pnCategry = fnCategry;
    }

    @NonNull
    @Override
    public OpeningViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_area_performance_layout, parent, false);
        return new OpeningViewHolder(view, mContext, index, mListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull OpeningViewHolder holder, @SuppressLint("RecyclerView") int position) {
        EBranchPerformance loBranch= poBranches.get(position);
        holder.sBranchCd = loBranch.getBranchCd();
        holder.lblBranch.setText(loBranch.getBranchNm());
        holder.indexPosition = position;
        if(pnCategry == 0 ){
            holder.lblGoal.setText(String.valueOf(loBranch.getMCGoalxx()));
            holder.lblActual.setText(String.valueOf(loBranch.getMCActual()));
        }else if(pnCategry == 1){
            holder.lblGoal.setText(currency_total.format(loBranch.getSPGoalxx()));
            holder.lblActual.setText(currency_total.format(loBranch.getSPActual()));
        }else if(pnCategry == 2){
            holder.lblGoal.setText(currency_total.format(loBranch.getJOGoalxx()));
            holder.lblActual.setText(currency_total.format(loBranch.getJOGoalxx()));
        }

        if (position == index){
            holder.indexLayout.setBackgroundColor(mContext.getResources().getColor(R.color.guanzon_digital_orange_lighten));
        }else{
            holder.indexLayout.setBackgroundColor(mContext.getResources().getColor(android.R.color.transparent));
        }
    }

    @Override
    public int getItemCount() {
        return poBranches.size();
    }

    public static void setIndexPosition(int pos) {
        index = pos;
    }

    public static class OpeningViewHolder extends RecyclerView.ViewHolder{

        public LinearLayout indexLayout;
        public String sBranchCd;
        public TextView lblBranch, lblGoal, lblActual;
        public Context mContext;
        public int indexPosition;

        public OpeningViewHolder(@NonNull View itemView, Context context, int pos, OnBranchClickListener mListener) {
            super(itemView);
            mContext = context;
            indexLayout = itemView.findViewById(R.id.indexLayout);
            lblBranch = itemView.findViewById(R.id.lbl_list_branch);
            lblGoal = itemView.findViewById(R.id.lbl_list_goal);
            lblActual = itemView.findViewById(R.id.lbl_list_actual);

            indexLayout.setOnClickListener(v -> {
                mListener.onClick(sBranchCd);
            });
        }

    }

    public interface OnBranchClickListener {
        void onClick(String sBranchCd);
    }
}
