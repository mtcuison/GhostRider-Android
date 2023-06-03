/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 9/16/21, 10:47 AM
 * project file last modified : 9/16/21, 10:47 AM
 */

package org.rmj.guanzongroup.ganado.Adapter;

import static org.rmj.g3appdriver.etc.AppConstants.LEAVE_TYPE;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import org.rmj.g3appdriver.GCircle.room.Entities.EEmployeeBusinessTrip;
import org.rmj.g3appdriver.GCircle.room.Entities.EMcModel;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.etc.FormatUIText;
import org.rmj.guanzongroup.ganado.R;

import java.util.List;

public class ProductSelectionAdapter extends RecyclerView.Adapter<ProductSelectionAdapter.ApplicationViewHolder> {

    private List<EMcModel> poModel;
    private OnModelClickListener listener;
    private boolean forViewing = false;


    public interface OnModelClickListener{
        void OnClick(String ModelID, String BrandID);
    }

    public ProductSelectionAdapter(List<EMcModel> poModel, OnModelClickListener listener) {
        this.poModel = poModel;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ApplicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mcmodelgrid_item, parent, false);
        return new ApplicationViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ApplicationViewHolder holder, int position) {
        EMcModel loModel = poModel.get(position);
        holder.itemName.setText(loModel.getModelNme());
        holder.itemView.setOnClickListener(v -> {
            if(listener != null){
                listener.OnClick(loModel.getModelIDx(), loModel.getBrandIDx());
            }
        });
    }

    @Override
    public int getItemCount() {
        return poModel.size();
    }

    public static class ApplicationViewHolder extends RecyclerView.ViewHolder{

        MaterialTextView itemName;

        LinearLayout lnStatus;

        ShapeableImageView imgType;

        public ApplicationViewHolder(@NonNull View view) {
            super(view);
            itemName = view.findViewById(R.id.itemName);
        }
    }
}
