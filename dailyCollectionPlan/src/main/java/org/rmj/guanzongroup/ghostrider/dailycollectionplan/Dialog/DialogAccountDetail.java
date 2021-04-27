/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.dailyCollectionPlan
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EClientUpdate;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GRider.Database.Repositories.RTown;
import org.rmj.g3appdriver.GRider.Etc.FormatUIText;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities.Activity_CollectionList;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Etc.DCP_Constants;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;

public class DialogAccountDetail {
    private static final String TAG = DialogAccountDetail.class.getSimpleName();
    private AlertDialog poDialogx;
    private final Context context;
    private org.rmj.g3appdriver.GRider.Database.Repositories.RTown RTown;
    private RDailyCollectionPlan poDCPRepo;
    private String[] civilStatus = DCP_Constants.CIVIL_STATUS;
    private String[] gender = {"Male", "Female", "LGBT"};

    private TextView dFullName;
    private TextView dAddress;
    private TextView dGender;
    private TextView dCivil;
    private TextView dBDate;
    private TextView dBPlace;
    private TextView dTelNo;
    private TextView dMobileNo;
    private TextView dEmail;
    private TextView dRemarks;
    private TextView lblBalnce;
    private TextView lblDelayx;
    private TextView lblLastPy;
    private TextView lblLastPd;

    public DialogAccountDetail(Context context){
        this.context = context;
    }

    public void initAccountDetail(Activity_CollectionList activity, EDCPCollectionDetail foDetail, DialogButtonClickListener listener){
        AlertDialog.Builder loBuilder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_account_detail, null, false);
        loBuilder.setCancelable(false)
                .setView(view);
        poDialogx = loBuilder.create();
        poDialogx.setCancelable(false);

        RTown = new RTown(activity.getApplication());
        poDCPRepo = new RDailyCollectionPlan(activity.getApplication());
        TextView lblReferNo = view.findViewById(R.id.lbl_dcpReferNo);
        TextView lblTransNo = view.findViewById(R.id.lbl_dcpTransNo);
        TextView lblAccntNo = view.findViewById(R.id.lbl_dcpAccNo);
        TextView lblSerialx = view.findViewById(R.id.lbl_dcpPRNo);
        TextView lblAmountx = view.findViewById(R.id.lbl_dcpAmountDue);
        TextView lblDueDate = view.findViewById(R.id.lbl_dcpDueDate);
        TextView transType = view.findViewById(R.id.lbl_transaction_type);
        LinearLayout linearLayout = view.findViewById(R.id.linear_lunInfo);
        dFullName = view.findViewById(R.id.dialog_fullName);
        dAddress = view.findViewById(R.id.dialog_address);
        dGender = view.findViewById(R.id.dialog_gender);
        dCivil = view.findViewById(R.id.dialog_civilStats);
        dBDate = view.findViewById(R.id.dialog_bDate);
        dBPlace = view.findViewById(R.id.dialog_bPlace);
        dTelNo = view.findViewById(R.id.dialog_tel);
        dMobileNo = view.findViewById(R.id.dialog_mobileNo);
        dEmail = view.findViewById(R.id.dialog_email);
        dRemarks = view.findViewById(R.id.dialog_remarks);
        lblBalnce = view.findViewById(R.id.lbl_dcpBalance);
        lblDelayx = view.findViewById(R.id.lbl_dcpDelayAvg);
        lblLastPy = view.findViewById(R.id.lbl_dcpAmountLastPay);
        lblLastPd = view.findViewById(R.id.lbl_dcpLastPaid);

        Spinner spnTransact = view.findViewById(R.id.spn_transaction);
        Button btnConfirm = view.findViewById(R.id.btn_confirm);
        Button btnCancelx = view.findViewById(R.id.btn_cancel);
        spnTransact.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, DCP_Constants.TRANSACTION_TYPE));

        if(foDetail.getRemCodex() == null || foDetail.getRemCodex().isEmpty()){
            transType.setText("");
            transType.setText("");
            linearLayout.setVisibility(View.GONE);
        }else {
            if(foDetail.getRemCodex().equalsIgnoreCase("FLA") ||
                    foDetail.getRemCodex().equalsIgnoreCase("TA") ||
                    foDetail.getRemCodex().equalsIgnoreCase("FO") ||
                    foDetail.getRemCodex().equalsIgnoreCase("LUn")) {

                showClientUpdateInfo(activity, foDetail);
                for (int i = 0; i< DCP_Constants.TRANSACTION_TYPE.length; i++){
                    if (DCP_Constants.TRANSACTION_TYPE[i].equalsIgnoreCase(DCP_Constants.getRemarksDescription(foDetail.getRemCodex()))){
                        spnTransact.setSelection(i);
                    }
                }




                linearLayout.setVisibility(View.VISIBLE);
            }else {
                linearLayout.setVisibility(View.GONE);
            }
            transType.setText(DCP_Constants.getRemarksDescription(foDetail.getRemCodex()));
//            lblRemarks.setText(DCP_Constants.getRemarksDescription(foDetail.getRemCodex()));
        }
        Log.e("Remarks code", foDetail.getRemCodex() + "");
        lblReferNo.setText(foDetail.getReferNox());
        lblTransNo.setText(foDetail.getTransNox());
        lblAccntNo.setText(foDetail.getAcctNmbr());
        lblSerialx.setText(foDetail.getSerialNo());
        lblAmountx.setText(FormatUIText.getCurrencyUIFormat(foDetail.getAmtDuexx()));
        lblDueDate.setText(FormatUIText.formatGOCasBirthdate(foDetail.getDueDatex()));
        lblBalnce.setText(FormatUIText.getCurrencyUIFormat(foDetail.getABalance()));
        lblDelayx.setText(foDetail.getDelayAvg());
        lblLastPy.setText(FormatUIText.getCurrencyUIFormat(foDetail.getLastPaym()));
        lblLastPd.setText(FormatUIText.formatGOCasBirthdate(foDetail.getLastPaid()));
        btnConfirm.setOnClickListener(view1 -> listener.OnClick(poDialogx, spnTransact.getSelectedItem().toString()));

        btnCancelx.setOnClickListener(view12 -> dismiss());
    }


    public void show(){
        poDialogx.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        poDialogx.getWindow().getAttributes().windowAnimations = org.rmj.g3appdriver.R.style.PopupAnimation;
        poDialogx.show();
    }

    public void dismiss(){
        if(poDialogx != null && poDialogx.isShowing()){
            poDialogx.dismiss();
        }
    }

    public interface DialogButtonClickListener{
        void OnClick(Dialog dialog, String remarksCode);
    }

    public LiveData<EClientUpdate> getClientUpdateInfo(String AccountNox){
        return poDCPRepo.getClientUpdateInfo(AccountNox);
    }
    public LiveData<DTownInfo.BrgyTownProvinceInfo> getBrgyTownProvinceInfo(String fsID){
        return RTown.getBrgyTownProvinceInfo(fsID);
    }

    public LiveData<DTownInfo.BrgyTownProvinceInfo> getTownProvinceInfo(String fsID){
        return RTown.getTownProvinceInfo(fsID);
    }
    @SuppressLint("SetTextI18n")
    public void showClientUpdateInfo(Activity_CollectionList activities, EDCPCollectionDetail foDetails){
        getClientUpdateInfo(foDetails.getAcctNmbr()).observe(activities, eClientUpdate ->{
            getBrgyTownProvinceInfo(eClientUpdate.getBarangay()).observe(activities, eTownInfo -> {
                dAddress.setText(eClientUpdate.getHouseNox() + " " + eClientUpdate.getAddressx() + ", " + eTownInfo.sBrgyName + ", " + eTownInfo.sTownName +", " + eTownInfo.sProvName);
                dBPlace.setText(eTownInfo.sTownName +", " + eTownInfo.sProvName);
            });
            getTownProvinceInfo(eClientUpdate.getTownIDxx()).observe(activities, eTownInfo -> {
                dBPlace.setText(eTownInfo.sTownName +", " + eTownInfo.sProvName);
            });
            String fullname = eClientUpdate.getLastName() + ", " + eClientUpdate.getFrstName() + " " + eClientUpdate.getSuffixNm() + " " + eClientUpdate.getMiddName();

            dFullName.setText(eClientUpdate.getLastName() + ", " + eClientUpdate.getFrstName() + " " + eClientUpdate.getSuffixNm() + " " + eClientUpdate.getMiddName());
            dGender.setText(gender[Integer.parseInt(eClientUpdate.getGenderxx())]);
            dCivil.setText(civilStatus[Integer.parseInt(eClientUpdate.getCivlStat())]);
            dBDate.setText(eClientUpdate.getBirthDte());

            if (eClientUpdate.getLandline() == null || eClientUpdate.getLandline().isEmpty()){
                dTelNo.setText("N/A");
            }else {
                dTelNo.setText(eClientUpdate.getLandline());
            }
            if (eClientUpdate.getEmailAdd() == null || eClientUpdate.getEmailAdd().isEmpty()){
                dEmail.setText("N/A");
            }else {
                dEmail.setText(eClientUpdate.getEmailAdd());
            }
            dRemarks.setText(foDetails.getRemarksx());
            dMobileNo.setText(eClientUpdate.getMobileNo());

        });
    }
}
