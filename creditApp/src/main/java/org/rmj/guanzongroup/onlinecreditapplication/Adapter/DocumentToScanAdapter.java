/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.creditApp
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.onlinecreditapplication.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DCreditApplicationDocuments;
import org.rmj.guanzongroup.onlinecreditapplication.R;

import com.google.android.material.textview.MaterialTextView;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class DocumentToScanAdapter extends RecyclerView.Adapter<DocumentToScanAdapter.FileCodeViewHolder> {

    public interface OnItemClickListener {
        void OnClick(int position);
        void OnClick(String args, String args1);
    }

    private final List<DCreditApplicationDocuments.ApplicationDocument> documentsInfo;
    private final OnItemClickListener mListener;

    public DocumentToScanAdapter(List<DCreditApplicationDocuments.ApplicationDocument> documentsInfo, OnItemClickListener mListener) {
        this.documentsInfo = documentsInfo;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public FileCodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_document_to_scan, parent, false);
        return new FileCodeViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FileCodeViewHolder holder, int position) {
        try {
            DCreditApplicationDocuments.ApplicationDocument document = documentsInfo.get(position);

            holder.lbl_fileDsc.setText(document.sBriefDsc);
            holder.lbl_fileLoc.setText(document.sFileLoct);
            if (document.sImageNme != null){
                holder.fileStat.setImageResource(R.drawable.ic_baseline_done_24);
                holder.fileStat.setTag(R.drawable.ic_baseline_done_24);
            }

            if (document.cSendStat != null){
                holder.imgDB.setVisibility(View.VISIBLE);
            }

            holder.itemView.setOnClickListener(view -> {
                if (position != RecyclerView.NO_POSITION && holder.fileStat.getTag() == null) {
                    mListener.OnClick(position);
                }else {
                    mListener.OnClick(document.sTransNox, document.sFileLoct);
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return documentsInfo.size();
    }


    public static class FileCodeViewHolder extends RecyclerView.ViewHolder {

        public MaterialTextView lbl_fileDsc;
        public MaterialTextView lbl_fileLoc;
        public ShapeableImageView fileStat;
        public ShapeableImageView imgDB;

        public FileCodeViewHolder(@NonNull View itemView) {
            super(itemView);
            lbl_fileDsc = itemView.findViewById(R.id.lbl_fileCode);
            lbl_fileLoc = itemView.findViewById(R.id.lbl_fileLoc);
            fileStat = itemView.findViewById(R.id.tick_cross);
            imgDB = itemView.findViewById(R.id.imgDB);
        }
    }


}
