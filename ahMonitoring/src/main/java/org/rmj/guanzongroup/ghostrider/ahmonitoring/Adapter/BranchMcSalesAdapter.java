/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 6/15/21 2:08 PM
 * project file last modified : 6/15/21 2:08 PM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.GCircle.room.Entities.EBranchPerformance;

public class BranchMcSalesAdapter extends RecyclerView.Adapter<BranchMcSalesAdapter.McSalesViewHolder> {

    private final LiveData<EBranchPerformance> poPerformance;
    private final OnPerformanceClickListener mListener;

    public interface OnPerformanceClickListener{
        void OnClick();
    }

    public BranchMcSalesAdapter(LiveData<EBranchPerformance> poPerformance, OnPerformanceClickListener mListener) {
        this.poPerformance = poPerformance;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public McSalesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull McSalesViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class McSalesViewHolder extends RecyclerView.ViewHolder{


        public McSalesViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
