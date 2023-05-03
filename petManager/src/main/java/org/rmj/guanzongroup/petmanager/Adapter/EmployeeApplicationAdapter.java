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

package org.rmj.guanzongroup.petmanager.Adapter;

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
import org.rmj.g3appdriver.GCircle.room.Entities.EEmployeeLeave;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.etc.FormatUIText;
import org.rmj.guanzongroup.petmanager.R;

import java.util.List;

public class EmployeeApplicationAdapter extends RecyclerView.Adapter<EmployeeApplicationAdapter.ApplicationViewHolder> {

    private List<EEmployeeLeave> poLeave;
    private List<EEmployeeBusinessTrip> poBusTrip;
    private OnLeaveItemClickListener mLvListener;
    private OnOBItemClickListener mObListener;
    private boolean forViewing = false;

    public interface OnLeaveItemClickListener{
        void OnClick(String TransNox);
    }
    public interface OnOBItemClickListener{
        void OnClick(String TransNox);
    }

    public EmployeeApplicationAdapter(List<EEmployeeLeave> poLeave, boolean value, OnLeaveItemClickListener listener) {
        this.poLeave = poLeave;
        this.forViewing = value;
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ApplicationViewHolder holder, int position) {
        if(forViewing){
            holder.lnStatus.setVisibility(View.VISIBLE);
        } else {
            holder.lnStatus.setVisibility(View.GONE);
        }
        if(poLeave != null) {
            EEmployeeLeave loLeave = poLeave.get(position);
            holder.imgType.setImageResource(R.drawable.ic_application_leave);
            holder.lblTransNox.setText(loLeave.getTransNox());
            holder.lblEmplName.setText(loLeave.getEmployID());
            holder.lblDeptName.setText(loLeave.getDeptName());
            holder.lblBrnchNme.setText(loLeave.getBranchNm());
            holder.lblDateFrom.setText(FormatUIText.formatGOCasBirthdate(loLeave.getDateFrom()));
            holder.lblDateThru.setText(FormatUIText.formatGOCasBirthdate(loLeave.getDateThru()));
            holder.lblAppStats.setText(AppConstants.getLeaveStatus(loLeave.getTranStat()));
            holder.lblLeaveTpe.setText("Leave Type : " + LEAVE_TYPE[Integer.parseInt(loLeave.getLeaveTyp())]);
            holder.lblRemarksx.setText(loLeave.getPurposex());
            holder.itemView.setOnClickListener(v -> {
                if(mLvListener != null){
                    mLvListener.OnClick(loLeave.getTransNox());
                }
            });
        } else if(poBusTrip != null){
            EEmployeeBusinessTrip loBusTrip = poBusTrip.get(position);
            holder.imgType.setImageResource(R.drawable.ic_application_business_trip);
            holder.lblTransNox.setText(loBusTrip.getTransNox());
            holder.lblEmplName.setText(loBusTrip.getFullName());
            holder.lblDeptName.setText(loBusTrip.getDeptName());
            holder.lblBrnchNme.setText(loBusTrip.getBranchNm());
            holder.lblDateFrom.setText(FormatUIText.formatGOCasBirthdate(loBusTrip.getDateFrom()));
            holder.lblDateThru.setText(FormatUIText.formatGOCasBirthdate(loBusTrip.getDateThru()));
            holder.lblRemarksx.setText(loBusTrip.getRemarksx());
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

        MaterialTextView lblTransNox,
                 lblEmplName,
                 lblDeptName,
                 lblBrnchNme,
                 lblDateFrom,
                 lblDateThru,
                 lblLeaveTpe,
                 lblAppStats,
                 lblRemarksx;

        LinearLayout lnStatus;

        ShapeableImageView imgType;

        public ApplicationViewHolder(@NonNull View view) {
            super(view);
            lblTransNox = view.findViewById(R.id.lbl_transNox);
            lblEmplName = view.findViewById(R.id.lbl_employeeName);
            lblBrnchNme = view.findViewById(R.id.lbl_branchNm);
            lblDeptName = view.findViewById(R.id.lbl_deptNme);
            lblDateFrom = view.findViewById(R.id.lbl_dateFrom);
            lblDateThru = view.findViewById(R.id.lbl_dateThru);
            lblLeaveTpe = view.findViewById(R.id.lbl_leaveType);
            lblAppStats = view.findViewById(R.id.lbl_leaveStatus);
            lblRemarksx = view.findViewById(R.id.lbl_remarks);
            lnStatus = view.findViewById(R.id.linear_status);
            imgType = view.findViewById(R.id.img_appType);
        }
    }
}
