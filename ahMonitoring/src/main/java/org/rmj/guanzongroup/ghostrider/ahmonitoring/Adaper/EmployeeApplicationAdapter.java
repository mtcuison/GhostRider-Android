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

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Adaper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeLeave;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;

import java.util.List;

public class EmployeeApplicationAdapter extends RecyclerView.Adapter<EmployeeApplicationAdapter.ApplicationViewHolder> {

    private final List<EEmployeeLeave> poLeave;
    private final OnLeaveItemClickListener mListener;

    public interface OnLeaveItemClickListener{
        void OnClick(String TransNox);
    }

    public EmployeeApplicationAdapter(List<EEmployeeLeave> poLeave, OnLeaveItemClickListener listener) {
        this.poLeave = poLeave;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ApplicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_applications, parent, false);
        return new ApplicationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApplicationViewHolder holder, int position) {
        EEmployeeLeave loLeave = poLeave.get(position);
        holder.lblTransNox.setText(loLeave.getTransNox());
        holder.lblEmplName.setText(loLeave.getEmployID());
        holder.lblDeptName.setText(loLeave.getDeptName());
        holder.lblBrnchNme.setText(loLeave.getBranchNm());
        holder.lblDateFrom.setText(loLeave.getDateFrom());
        holder.lblDateThru.setText(loLeave.getDateThru());
        holder.txtPurpose.setText(loLeave.getPurposex());
        holder.itemView.setOnClickListener(v -> mListener.OnClick(loLeave.getTransNox()));
    }

    @Override
    public int getItemCount() {
        return poLeave.size();
    }

    public static class ApplicationViewHolder extends RecyclerView.ViewHolder{

        TextView lblTransNox,
                 lblEmplName,
                 lblDeptName,
                 lblBrnchNme,
                 lblDateFrom,
                 lblDateThru;

        TextInputEditText txtPurpose;

        public ApplicationViewHolder(@NonNull View view) {
            super(view);
            lblTransNox = view.findViewById(R.id.lbl_transNox);
            lblEmplName = view.findViewById(R.id.lbl_employeeName);
            lblBrnchNme = view.findViewById(R.id.lbl_branchNm);
            lblDeptName = view.findViewById(R.id.lbl_deptNme);
            lblDateFrom = view.findViewById(R.id.lbl_dateFrom);
            lblDateThru = view.findViewById(R.id.lbl_dateThru);
            txtPurpose = view.findViewById(R.id.txt_purpose);
        }
    }
}
