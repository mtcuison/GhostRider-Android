/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.dailyCollectionPlan
 * Electronic Personnel Access Control Security System
 * project file created : 3/2/22, 10:52 AM
 * project file last modified : 3/2/22, 10:52 AM
 */

package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textview.MaterialTextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EImageInfo;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;

import java.util.List;

public class AdapterImageLog extends RecyclerView.Adapter<AdapterImageLog.ImageInfoHolder> {

    private final List<EImageInfo> poImgList;

    public AdapterImageLog(List<EImageInfo> foImgList) {
        this.poImgList = foImgList;
    }

    @NonNull
    @Override
    public ImageInfoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_image_log, parent, false);
        return new ImageInfoHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ImageInfoHolder holder, int position) {
        EImageInfo loImgDetl = poImgList.get(position);
        holder.lblTransN.setText(loImgDetl.getTransNox());
        holder.lblImgNme.setText(loImgDetl.getImageNme());
    }

    @Override
    public int getItemCount() {
        return poImgList.size();
    }

    public static class ImageInfoHolder extends RecyclerView.ViewHolder{

        MaterialTextView lblTransN, lblImgNme;

        public ImageInfoHolder(@NonNull View itemView) {
            super(itemView);
            lblTransN = itemView.findViewById(R.id.lbl_transNo);
            lblImgNme = itemView.findViewById(R.id.lbl_imgName);
        }

    }

}

