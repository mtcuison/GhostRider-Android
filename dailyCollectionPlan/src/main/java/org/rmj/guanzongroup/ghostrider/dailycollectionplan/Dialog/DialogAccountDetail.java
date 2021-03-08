package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Dialog;

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

import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DDCPCollectionDetail;
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
    EClientUpdate eDetail = new EClientUpdate();
    private String[] civilStatus = DCP_Constants.CIVIL_STATUS;
    private String[] gender = {"Male", "Female", "LGBT"};

    TextView dFullName;
    TextView dAddress;
    TextView dGender;
    TextView dCivil;
    TextView dBDate;
    TextView dBPlace;
    TextView dTelNo;
    TextView dMobileNo;
    TextView dEmail;
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
//        TextView lblTranDte = view.findViewById(R.id.lbl_dcpTranDate);
        TextView lblAccntNo = view.findViewById(R.id.lbl_dcpAccNo);
        TextView lblSerialx = view.findViewById(R.id.lbl_dcpPRNo);
        TextView lblAmountx = view.findViewById(R.id.lbl_dcpAmount);
        TextView lblDueDate = view.findViewById(R.id.lbl_dcpDueDate);
//        TextView lblOthersx = view.findViewById(R.id.lbl_dcpOthers);
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

        Spinner spnTransact = view.findViewById(R.id.spn_transaction);
        Button btnConfirm = view.findViewById(R.id.btn_confirm);
        Button btnCancelx = view.findViewById(R.id.btn_cancel);

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
                linearLayout.setVisibility(View.VISIBLE);
            }else {
                linearLayout.setVisibility(View.GONE);
            }
            transType.setText(DCP_Constants.getRemarksDescription(foDetail.getRemCodex()));
//            lblRemarks.setText(DCP_Constants.getRemarksDescription(foDetail.getRemCodex()));
        }
        Log.e("Remarks code", foDetail.getRemCodex() + "");
        //lblReferNo.setText(foDetail.getReferNox());
        lblTransNo.setText(foDetail.getTransNox());
        lblAccntNo.setText(foDetail.getAcctNmbr());
        lblSerialx.setText(foDetail.getSerialNo());
        lblAmountx.setText(FormatUIText.getCurrencyUIFormat(foDetail.getAmtDuexx()));
        lblDueDate.setText(FormatUIText.formatGOCasBirthdate(foDetail.getDueDatex()));

        spnTransact.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, DCP_Constants.TRANSACTION_TYPE));

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
    public void showClientUpdateInfo(Activity_CollectionList activities, EDCPCollectionDetail foDetails){
        getClientUpdateInfo(foDetails.getAcctNmbr()).observe(activities, eClientUpdate ->{
            eDetail = eClientUpdate;
            Log.e("Town  ", eDetail.getTownIDxx());
            Log.e("Province  ", eDetail.getBarangay());
            getBrgyTownProvinceInfo(eDetail.getBarangay()).observe(activities, eTownInfo -> {
                dAddress.setText(eDetail.getHouseNox() + " " + eDetail.getAddressx() + ", " + eTownInfo.sBrgyName + ", " + eTownInfo.sTownName +", " + eTownInfo.sProvName);
            });
            getTownProvinceInfo(eDetail.getTownIDxx()).observe(activities, eTownInfo -> {
                dBPlace.setText(eTownInfo.sTownName +", " + eTownInfo.sProvName);
            });

            String fullname = eDetail.getLastName() + ", " + eDetail.getFrstName() + " " + eDetail.getSuffixNm() + " " + eDetail.getMiddName();
            Log.e("Brgy ", fullname);
            dFullName.setText(eDetail.getLastName() + ", " + eDetail.getFrstName() + " " + eDetail.getSuffixNm() + " " + eDetail.getMiddName());
            dGender.setText(gender[Integer.parseInt(eDetail.getGenderxx())]);
            dCivil.setText(civilStatus[Integer.parseInt(eDetail.getCivlStat())]);
            dBDate.setText(eDetail.getBirthDte());

            if (eDetail.getLandline() == null || eDetail.getLandline().isEmpty()){
                dTelNo.setText("N/A");
            }else {
                dTelNo.setText(eDetail.getLandline());
            }
            if (eDetail.getEmailAdd() == null || eDetail.getEmailAdd().isEmpty()){
                dEmail.setText("N/A");
            }else {
                dEmail.setText(eDetail.getEmailAdd());
            }

            dMobileNo.setText(eDetail.getMobileNo());

        });
    }
}
