/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.dailyCollectionPlan
 * Electronic Personnel Access Control Security System
 * project file created : 2/28/22, 1:08 PM
 * project file last modified : 2/28/22, 1:08 PM
 */

package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.dev.Database.Entities.EDCPCollectionDetail;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;

import java.util.List;

public class PostDcpAdapter extends RecyclerView.Adapter<PostDcpAdapter.ClientHolder> {

    private final List<EDCPCollectionDetail> poColList;
    private final OnPostDcpClick mListener;

    public PostDcpAdapter(List<EDCPCollectionDetail> collectionDetails, OnPostDcpClick mListener) {
        this.poColList = collectionDetails;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ClientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_dcp_adapter, parent, false);
        return new ClientHolder(view, mListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ClientHolder holder, int position) {
        EDCPCollectionDetail loDetail = poColList.get(position);
        holder.loDetail = loDetail;
        if(!loDetail.getAcctNmbr().isEmpty()) {
            holder.lblAccNox.setText("Account No. : " + loDetail.getAcctNmbr());
        } else {
            holder.lblAccNox.setText("MC Serial No. : " + loDetail.getSerialNo());
        }
        holder.lblClientNm.setText(loDetail.getFullName());
        holder.lblRemarks.setText(loDetail.getRemCodex());
    }

    @Override
    public int getItemCount() {
        return poColList.size();
    }

    public static class ClientHolder extends RecyclerView.ViewHolder{

        EDCPCollectionDetail loDetail;
        ImageView icUpload;
        TextView lblAccNox, lblClientNm, lblRemarks;

        public ClientHolder(@NonNull View itemView, OnPostDcpClick mListener) {
            super(itemView);
            icUpload = itemView.findViewById(R.id.ic_upload);
            lblAccNox = itemView.findViewById(R.id.lbl_AccountNo);
            lblClientNm = itemView.findViewById(R.id.lbl_clientNm);
            lblRemarks = itemView.findViewById(R.id.lbl_dcpRemarks);

            icUpload.setOnClickListener(v -> {
                if(getAdapterPosition() != RecyclerView.NO_POSITION){
                    mListener.onClick(loDetail);
                }
            });
        }
    }

    public interface OnPostDcpClick {
        void onClick(EDCPCollectionDetail dcpDetail);
    }

}
