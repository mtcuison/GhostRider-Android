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

import org.rmj.g3appdriver.dev.Database.Entities.EBranchPerformance;
import org.rmj.g3appdriver.lib.BullsEye.obj.BranchPerformancePeriod;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;

import java.text.DecimalFormat;
import java.util.List;

public class BranchPerformanceAdapter extends RecyclerView.Adapter<BranchPerformanceAdapter.OpeningViewHolder> {

    private final Context mContext;
    private final List<EBranchPerformance> brnPerformances;
    private final String category;
    public static int index = -1;
    private final DecimalFormat currency_total = new DecimalFormat("###,###,###.###");
    public BranchPerformanceAdapter(Context context, String cat,List<EBranchPerformance> brnPerformance) {
        this.mContext = context;
        this.brnPerformances = brnPerformance;
        this.category = cat;
    }

    public static void setIndexPosition(int pos) {
        index = pos;
    }


    @NonNull
    @Override
    public OpeningViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_branch_performance_layout, parent, false);
//        return new OpeningViewHolder(view, mListener);
        return new OpeningViewHolder(view,mContext, index);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull OpeningViewHolder holder, @SuppressLint("RecyclerView") int position) {
        EBranchPerformance brnPerformance = brnPerformances.get(position);
        holder.lblMonth.setText(BranchPerformancePeriod.parseDateLabel(brnPerformance.getPeriodxx()));
        holder.indexPosition = position;
        if(category.equalsIgnoreCase("MC")){
            holder.lblGoal.setText(String.valueOf(brnPerformance.getMCGoalxx()));
            holder.lblActual.setText(String.valueOf(brnPerformance.getMCActual()));
        }else if(category.equalsIgnoreCase("SP")){
            holder.lblGoal.setText(currency_total.format(brnPerformance.getSPGoalxx()));
            holder.lblActual.setText(currency_total.format(brnPerformance.getSPActual()));
        }else if(category.equalsIgnoreCase("JO")){
            holder.lblGoal.setText(currency_total.format(brnPerformance.getJOGoalxx()));
            holder.lblActual.setText(currency_total.format(brnPerformance.getJOGoalxx()));
        }

        if (position == index){
            holder.indexLayout.setBackgroundColor(mContext.getResources().getColor(R.color.guanzon_digital_orange_lighten));
        }else{
            holder.indexLayout.setBackgroundColor(mContext.getResources().getColor(android.R.color.transparent));
        }
    }

    @Override
    public int getItemCount() {
        return brnPerformances.size();
    }

    public static class OpeningViewHolder extends RecyclerView.ViewHolder{

        public LinearLayout indexLayout;
        public TextView lblMonth, lblGoal, lblActual;
        public Context mContext;
        public int indexPosition;
//        public OpeningViewHolder(@NonNull View itemView, OnAdapterItemClickListener listener) {
        public OpeningViewHolder(@NonNull View itemView, Context context, int pos) {
            super(itemView);
            mContext = context;
            indexLayout = itemView.findViewById(R.id.indexLayout);
            lblMonth = itemView.findViewById(R.id.lbl_list_month);
            lblGoal = itemView.findViewById(R.id.lbl_list_goal);
            lblActual = itemView.findViewById(R.id.lbl_list_actual);
//            itemView.setOnClickListener(v -> listener.OnClick());

        }

    }
}
