/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.settings
 * Electronic Personnel Access Control Security System
 * project file created : 6/23/21 2:51 PM
 * project file last modified : 6/23/21 2:51 PM
 */

package org.rmj.guanzongroup.ghostrider.settings.Objects;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.guanzongroup.ghostrider.settings.R;
import org.rmj.guanzongroup.ghostrider.settings.ViewModel.VMLocalData;

import java.util.List;

public class AdapterLocalData extends RecyclerView.Adapter<AdapterLocalData.DataHolder> {

    private final List<LocalData> poData;
    private final OnDataClickListener mListener;

    public interface OnDataClickListener{
        void OnClick();
    }

    public AdapterLocalData(List<LocalData> poData, OnDataClickListener listener) {
        this.poData = poData;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public DataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_local_data, parent, false);
        return new DataHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DataHolder holder, int position) {
        LocalData loData = poData.get(position);
        holder.lblDataNme.setText(loData.getDataName());
        holder.lblRefresh.setText("Data Record : " + loData.getDataCount());

        holder.lblRefresh.setOnClickListener(v -> {
            if(position != RecyclerView.NO_POSITION){
                mListener.OnClick();
            }
        });
    }

    @Override
    public int getItemCount() {
        return poData.size();
    }

    public static class DataHolder extends RecyclerView.ViewHolder{

        public TextView lblDataNme;
        public TextView lblRefresh;

        public DataHolder(@NonNull View itemView) {
            super(itemView);

            lblDataNme = itemView.findViewById(R.id.lbl_data_name);
            lblRefresh = itemView.findViewById(R.id.btn_refresh);
        }
    }
}
