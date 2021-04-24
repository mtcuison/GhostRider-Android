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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import org.rmj.guanzongroup.onlinecreditapplication.R;

import java.util.ArrayList;
import java.util.List;

public class LoanAppDetailReviewAdapter extends RecyclerView.Adapter<LoanAppDetailReviewAdapter.DetailViewHolder> {

    private final List<ReviewAppDetail> plRevDetails;
    private final OnActionButtonClickListener mListener;

    public interface OnActionButtonClickListener{
        void onInfoClickListener();
    }

    public LoanAppDetailReviewAdapter(List<ReviewAppDetail> plRevDetails, OnActionButtonClickListener listener) {
        this.plRevDetails = plRevDetails;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_loan_review, parent, false);
        return new DetailViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder holder, int position) {
        ReviewAppDetail loDetail = plRevDetails.get(position);
        holder.lnHeader.setVisibility(loDetail.isHeader());
        holder.lblHeader.setText(loDetail.getHeader());
        holder.lnContnt.setVisibility(loDetail.isContent());
        holder.lblDetail.setText(loDetail.getLabel());
        holder.lblContnt.setText(loDetail.getContent());
    }

    @Override
    public int getItemCount() {
        return plRevDetails.size();
    }

    public static class DetailViewHolder extends RecyclerView.ViewHolder{

        LinearLayout lnHeader;
        LinearLayout lnContnt;
        TextView lblHeader;
        TextView lblDetail;
        TextView lblContnt;

        public DetailViewHolder(@NonNull View itemView, OnActionButtonClickListener listener) {
            super(itemView);

            lnHeader = itemView.findViewById(R.id.linear_list_header);
            lblHeader = itemView.findViewById(R.id.lbl_list_header);
            lnContnt = itemView.findViewById(R.id.linear_list_content);
            lblDetail = itemView.findViewById(R.id.lbl_list_detailInfo);
            lblContnt = itemView.findViewById(R.id.lbl_list_detailContent);
        }
    }
}
