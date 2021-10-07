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

import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeBusinessTrip;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeLeave;
import org.rmj.g3appdriver.GRider.Etc.FormatUIText;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;

import java.util.List;

public class EmployeeApplicationAdapter extends RecyclerView.Adapter<EmployeeApplicationAdapter.ApplicationViewHolder> {

    private List<EEmployeeLeave> poLeave;
    private List<EEmployeeBusinessTrip> poBusTrip;
    private OnLeaveItemClickListener mLvListener;
    private OnOBItemClickListener mObListener;

    public interface OnLeaveItemClickListener{
        void OnClick(String TransNox);
    }
    public interface OnOBItemClickListener{
        void OnClick(String TransNox);
    }

    public EmployeeApplicationAdapter(List<EEmployeeLeave> poLeave, OnLeaveItemClickListener listener) {
        this.poLeave = poLeave;
        this.mLvListener = listener;
    }

    public EmployeeApplicationAdapter(List<EEmployeeBusinessTrip> poBusTrip, OnOBItemClickListener mListener) {
        this.poBusTrip = poBusTrip;
        this.mObListener = mListener;
    }

    @NonNull
    @Override
    public ApplicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_applications, parent, false);
        return new ApplicationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApplicationViewHolder holder, int position) {
        if(poLeave != null) {
            EEmployeeLeave loLeave = poLeave.get(position);
            holder.lblTransNox.setText(loLeave.getTransNox());
            holder.lblEmplName.setText(loLeave.getEmployID());
            holder.lblDeptName.setText(loLeave.getDeptName());
            holder.lblBrnchNme.setText(loLeave.getBranchNm());
            holder.lblDateFrom.setText(FormatUIText.formatGOCasBirthdate(loLeave.getAppldFrx()));
            holder.lblDateThru.setText(FormatUIText.formatGOCasBirthdate(loLeave.getAppldTox()));
            holder.txtPurpose.setText(loLeave.getPurposex());
            holder.itemView.setOnClickListener(v -> {
                if(mLvListener != null){
                    mLvListener.OnClick(loLeave.getTransNox());
                }
            });
        } else if(poBusTrip != null){
            EEmployeeBusinessTrip loBusTrip = poBusTrip.get(position);
            holder.lblTransNox.setText(loBusTrip.getTransNox());
            holder.lblEmplName.setText(loBusTrip.getEmployee());
            holder.lblDeptName.setText(loBusTrip.getDeptName());
            holder.lblBrnchNme.setText(loBusTrip.getBranchNm());
            holder.lblDateFrom.setText(FormatUIText.formatGOCasBirthdate(loBusTrip.getDateFrom()));
            holder.lblDateThru.setText(FormatUIText.formatGOCasBirthdate(loBusTrip.getDateThru()));
            holder.txtPurpose.setText(loBusTrip.getRemarksx());
            holder.itemView.setOnClickListener(v -> {
                if(mObListener != null){
                    mObListener.OnClick(loBusTrip.getTransNox());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(poLeave != null) {
            return poLeave.size();
        } else {
            return poBusTrip.size();
        }
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
