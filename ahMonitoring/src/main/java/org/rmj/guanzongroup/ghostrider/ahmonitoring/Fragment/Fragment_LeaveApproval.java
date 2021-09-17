/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 8/16/21 8:21 AM
 * project file last modified : 8/16/21 8:21 AM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Fragment;

import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Etc.FormatUIText;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Model.LeaveApprovalInfo;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VmLeaveApproval;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Fragment_LeaveApproval extends Fragment {
    public static final String TAG = Fragment_LeaveApproval.class.getSimpleName();
    private VmLeaveApproval mViewModel;

    private LeaveApprovalInfo infoModel;
    private LoadDialog poDialogx;
    private MessageBox poMessage;

    private MaterialButton btnCancel, bntConfirm;
    private TextView
            lblTransNox,
            lblEmployeNm,
            lblDeptName,
            lblBranchNm,
            lblLeaveCrd,
            lblDateAppl,
            lblDateFrom,
            lblDateThru,
            lblDateAppr;
    private TextInputEditText txtSearch,
            tieDateFrom,
            tieDateThru,
            txtPurpse,
            tieWithPy,
            tieWOPay;
    private TextInputLayout tilRemarks;
    private ImageButton btnQuickSearch;

    public static Fragment_LeaveApproval newInstance() {
        return new Fragment_LeaveApproval();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leave_approval, container, false);
        infoModel = new LeaveApprovalInfo();
        initWidgets(view);
        return view;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VmLeaveApproval.class);
        Typeface typeface = ResourcesCompat.getFont(requireActivity(), R.font.roboto_bold);
        tilRemarks.setTypeface(typeface);
        btnQuickSearch.setOnClickListener(v -> mViewModel.downloadLeaveApplication(txtSearch.getText().toString(), new VmLeaveApproval.OnKwikSearchCallBack() {
            @Override
            public void onStartKwikSearch() {
                txtSearch.setText("");
                poDialogx.initDialog("Leave Application", "Searching leave application. Please wait...", false);
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccessKwikSearch(JSONObject leave) {
                poDialogx.dismiss();
                try{
                    infoModel.setTransNox(leave.getString("sTransNox"));
                    infoModel.setAppldFrx(leave.getString("dAppldFrx"));
                    infoModel.setAppldTox(leave.getString("dAppldTox"));
                    lblTransNox.setText("Transaction No. : " + leave.getString("sTransNox"));
                    lblEmployeNm.setText(leave.getString("xEmployee"));
                    lblDeptName.setText(leave.getString("sDeptName"));
                    lblBranchNm.setText(leave.getString("sBranchNm"));
                    lblLeaveCrd.setText("Leave Credits : " + leave.getString("nLveCredt"));
                    lblDateAppr.setText(AppConstants.CURRENT_DATE);
                    lblDateAppl.setText(FormatUIText.formatGOCasBirthdate(leave.getString("dTransact")));
                    lblDateFrom.setText(FormatUIText.formatGOCasBirthdate(leave.getString("dAppldFrx")));
                    lblDateThru.setText(FormatUIText.formatGOCasBirthdate(leave.getString("dAppldTox")));
                    lblDateAppl.setText(FormatUIText.formatGOCasBirthdate(leave.getString("dTransact")));
                    tieDateFrom.setText(FormatUIText.formatGOCasBirthdate(leave.getString("dAppldFrx")));
                    tieDateThru.setText(FormatUIText.formatGOCasBirthdate(leave.getString("dAppldTox")));
                    txtPurpse.setText(leave.getString("sPurposex"));
                    int lnCredits = Integer.parseInt(leave.getString("nLveCredt"));
                    String lsFromx = leave.getString("dAppldFrx");
                    String lsDteTo = leave.getString("dAppldTox");
                    setWithPay(lnCredits, lsFromx, lsDteTo);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onKwikSearchFailed(String message) {
                poDialogx.dismiss();
                initErrorDialog("Leave Application", message);
            }
        }));

        bntConfirm.setOnClickListener(v -> {
            infoModel.setTranStat("1");
            sendLeaveUpdate();
        });

        btnCancel.setOnClickListener(v -> {
            infoModel.setTranStat("3");
            sendLeaveUpdate();
        });
    }

    private void sendLeaveUpdate(){
        mViewModel.confirmLeaveApplication(infoModel, new VmLeaveApproval.OnConfirmOBLeaveListener() {
            @Override
            public void onConfirm() {
                poDialogx.initDialog("Leave Application", "Confirming leave application. Please wait...", false);
                poDialogx.show();
            }

            @Override
            public void onSuccess() {
                poDialogx.dismiss();
            }

            @Override
            public void onFailed(String message) {
                poDialogx.dismiss();
                initErrorDialog("Leave Application", message);
            }
        });
    }

    public void initWidgets(View view){
        poDialogx = new LoadDialog(getActivity());
        poMessage = new MessageBox(getActivity());
        txtSearch = view.findViewById(R.id.txt_leave_ob_search);
        btnQuickSearch = view.findViewById(R.id.btn_quick_search);
        lblTransNox = view.findViewById(R.id.lbl_transNox);
        lblEmployeNm = view.findViewById(R.id.lbl_clientNm);
        tieDateFrom = view.findViewById(R.id.txt_dateFrom);
        lblDeptName = view.findViewById(R.id.lbl_deptNme);
        lblBranchNm = view.findViewById(R.id.lbl_branchNm);
        lblLeaveCrd = view.findViewById(R.id.lbl_leaveCrdt);
        lblDateAppl = view.findViewById(R.id.lbl_dateApplied);
        lblDateFrom = view.findViewById(R.id.lbl_dateFrom);
        lblDateThru = view.findViewById(R.id.lbl_dateThru);
        lblDateAppr = view.findViewById(R.id.lbl_dateApproved);
        tieDateThru = view.findViewById(R.id.txt_dateTo);
        tilRemarks = view.findViewById(R.id.tilRemarks);
        txtPurpse = view.findViewById(R.id.txt_purpose);

        tieWithPy = view.findViewById(R.id.txt_withPay);
        tieWOPay = view.findViewById(R.id.txt_withoutPay);

        btnCancel = view.findViewById(R.id.btn_cancel);
        bntConfirm = view.findViewById(R.id.btn_confirm);
    }

    public void initErrorDialog(String title, String message){
        poMessage.initDialog();
        poMessage.setTitle(title);
        poMessage.setMessage(message);
        poMessage.setPositiveButton("Okay", (view, dialog) ->
                dialog.dismiss());
        poMessage.show();
    }

    private void setWithPay(int credits, String fsDateFrm, String fsDateTo) throws ParseException {
        @SuppressLint("SimpleDateFormat") final SimpleDateFormat loDate = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressLint("SimpleDateFormat") final SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM dd, yyyy");
        Date dateFrom = loDate.parse(Objects.requireNonNull(fsDateFrm));
        Date dateTo = loDate.parse(Objects.requireNonNull(fsDateTo));
        long diff = dateTo.getTime() - dateFrom.getTime();
        long noOfDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1;
        if(credits == 0){
            tieWOPay.setText(String.valueOf(noOfDays));
        } else {
            tieWithPy.setText(String.valueOf(noOfDays));
        }
    }
}