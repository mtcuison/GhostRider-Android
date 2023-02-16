/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.guanzongroup.ghostrider.ahmonitoring.Model.ReimburseInfo;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.android.material.divider.MaterialDivider;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;
import java.util.List;

public class ReimbursementAdapter extends RecyclerView.Adapter<ReimbursementAdapter.ExpViewHolder> {

    private final List<ReimburseInfo> plExpList;
    private final OnExpButtonClickListener mListener;

    public ReimbursementAdapter(List<ReimburseInfo> plExpList, OnExpButtonClickListener listener) {
        this.plExpList = plExpList;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ExpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_expenses, parent, false);
        return new ExpViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpViewHolder holder, int position) {
        ReimburseInfo info = plExpList.get(position);
        holder.lblDetail.setText(info.getsDetlInfo());
        holder.lblAmount.setText("₱ " + String.valueOf(info.getsAmountxx()));
        holder.btnEdit.setOnClickListener(view -> {
            if(position != RecyclerView.NO_POSITION){
                mListener.OnEdit(position, info.getsDetlInfo(), info.getsAmountxx());
            }
        });
        holder.btnDlte.setOnClickListener(view -> {
            if(position != RecyclerView.NO_POSITION){
                mListener.OnDelete(position, info.getsDetlInfo(), info.getsAmountxx());
            }
        });
    }

    @Override
    public int getItemCount() {
        return plExpList.size();
    }

    public static class ExpViewHolder extends RecyclerView.ViewHolder{

        MaterialTextView lblDetail, lblAmount;
        MaterialButton btnEdit, btnDlte;

        public ExpViewHolder(@NonNull View itemView) {
            super(itemView);
            lblDetail = itemView.findViewById(R.id.lbl_list_expDetail);
            lblAmount = itemView.findViewById(R.id.lbl_list_expAmount);
            btnEdit = itemView.findViewById(R.id.btn_item_edit);
            btnDlte = itemView.findViewById(R.id.btn_item_delete);

        }
    }

    public interface OnExpButtonClickListener{
        void OnEdit(int position, String Detail, int Amount);
        void OnDelete(int position, String Detail, int Amount);
    }
}
