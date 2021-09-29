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
import android.app.DatePickerDialog;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Etc.FormatUIText;
import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity.Activity_Application;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Model.LeaveApprovalInfo;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMLeaveApproval;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Fragment_LeaveApproval extends Fragment implements VMLeaveApproval.OnDownloadLeaveAppInfo {
    public static final String TAG = Fragment_LeaveApproval.class.getSimpleName();
    private VMLeaveApproval mViewModel;

    private LeaveApprovalInfo infoModel;
    private LoadDialog poDialogx;
    private MessageBox poMessage;

    private int lnCredits;

    private LinearLayout lnSearch;
    private MaterialButton btnCancel, bntConfirm;
    private TextView
            lblHdBranch,
            lblHdAddrss,
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

    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMLeaveApproval.class);
        Typeface typeface = ResourcesCompat.getFont(requireActivity(), R.font.roboto_bold);
        tilRemarks.setTypeface(typeface);
        String TransNox = Activity_Application.getInstance().getTransNox();
        if(TransNox.isEmpty()){
            lnSearch.setVisibility(View.VISIBLE);
        } else {
            lnSearch.setVisibility(View.GONE);
        }
        mViewModel.setTransNox(TransNox);
        mViewModel.getTransNox().observe(getViewLifecycleOwner(), s -> {
            if(!s.isEmpty()){
                txtSearch.setVisibility(View.GONE);
                mViewModel.getEmployeeLeaveInfo(s).observe(requireActivity(), eEmployeeLeave -> {
                    try {
                        if(eEmployeeLeave == null){
                            mViewModel.downloadLeaveApplication(TransNox, Fragment_LeaveApproval.this);
                        } else {
                            infoModel.setTransNox(TransNox);
                            infoModel.setAppldFrx(eEmployeeLeave.getAppldFrx());
                            infoModel.setAppldTox(eEmployeeLeave.getAppldTox());
                            lblTransNox.setText("Transaction No. : " + eEmployeeLeave.getTransNox());
                            lblEmployeNm.setText(eEmployeeLeave.getEmployID());
                            lblDeptName.setText(eEmployeeLeave.getDeptName());
                            lblBranchNm.setText(eEmployeeLeave.getBranchNm());
                            lblLeaveCrd.setText("Leave Credits : " + eEmployeeLeave.getLveCredt());
                            lblDateAppr.setText(FormatUIText.formatGOCasBirthdate(AppConstants.CURRENT_DATE));
                            lblDateAppl.setText(FormatUIText.formatGOCasBirthdate(eEmployeeLeave.getTransact()));
                            lblDateFrom.setText(FormatUIText.formatGOCasBirthdate(eEmployeeLeave.getAppldFrx()));
                            lblDateThru.setText(FormatUIText.formatGOCasBirthdate(eEmployeeLeave.getAppldTox()));
                            tieDateFrom.setText(FormatUIText.formatGOCasBirthdate(eEmployeeLeave.getAppldFrx()));
                            tieDateThru.setText(FormatUIText.formatGOCasBirthdate(eEmployeeLeave.getAppldTox()));
                            txtPurpse.setText(eEmployeeLeave.getPurposex());
                            lnCredits = Integer.parseInt(eEmployeeLeave.getLveCredt());
                            String lsFromx = eEmployeeLeave.getAppldFrx();
                            String lsDteTo = eEmployeeLeave.getAppldTox();
                            setWithPay(lnCredits, lsFromx, lsDteTo);
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                });
            }
        });

        mViewModel.getUserInfo().observe(requireActivity(), eEmployeeInfo -> {
            try{
                infoModel.setApproved(eEmployeeInfo.getEmployID());
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getUserBranchInfo().observe(requireActivity(), eBranchInfo -> {
            try{
                lblHdBranch.setText(eBranchInfo.getBranchNm());
                lblHdAddrss.setText(eBranchInfo.getAddressx());
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        btnQuickSearch.setOnClickListener(v -> mViewModel.downloadLeaveApplication(Objects.requireNonNull(txtSearch.getText()).toString(), this));

        bntConfirm.setOnClickListener(v -> {
            infoModel.setTranStat("1");
            sendLeaveUpdate();
        });

        btnCancel.setOnClickListener(v -> {
            infoModel.setTranStat("3");
            sendLeaveUpdate();
        });


        tieDateFrom.setOnClickListener(v -> {
            final Calendar newCalendar = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat") final SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM dd, yyyy");
            @SuppressLint("SimpleDateFormat") final SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
            final DatePickerDialog dateFrom = new DatePickerDialog(getActivity(), (view, year, month, dayOfMonth) -> {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                try {
                    Date dateTo = dataFormat.parse(Objects.requireNonNull(FormatUIText.formatTextToData(Objects.requireNonNull(tieDateThru.getText()).toString())));
                    Date dteFrm = newDate.getTime();
                    if(dteFrm.before(dateTo)) {
                        infoModel.setAppldFrx(dataFormat.format(newDate.getTime()));
                        tieDateFrom.setText(dateFormatter.format(newDate.getTime()));
                        setWithPay(lnCredits,
                                dataFormat.format(newDate.getTime()),
                                FormatUIText.formatTextToData(Objects.requireNonNull(tieDateThru.getText()).toString()));
                    } else {
                        GToast.CreateMessage(requireActivity(), "Invalid date selected.", GToast.ERROR).show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    Toast.makeText(requireActivity(), "Error while selecting date. " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
            dateFrom.show();
        });

        tieDateThru.setOnClickListener(v -> {
            final Calendar newCalendar = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat") final SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM dd, yyyy");
            @SuppressLint("SimpleDateFormat") final SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
            final DatePickerDialog dateFrom = new DatePickerDialog(getActivity(), (view, year, month, dayOfMonth) -> {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                try {
                    Date dateFrmx = dataFormat.parse(Objects.requireNonNull(FormatUIText.formatTextToData(Objects.requireNonNull(tieDateFrom.getText()).toString())));
                    Date dateThru = newDate.getTime();
                    if (dateThru.after(dateFrmx)){
                        infoModel.setAppldFrx(dataFormat.format(newDate.getTime()));
                        tieDateThru.setText(dateFormatter.format(newDate.getTime()));
                        setWithPay(lnCredits,
                                FormatUIText.formatTextToData(Objects.requireNonNull(tieDateFrom.getText()).toString()),
                                dataFormat.format(newDate.getTime()));
                    } else {
                        GToast.CreateMessage(requireActivity(), "Invalid date selected.", GToast.ERROR).show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    Toast.makeText(requireActivity(), "Error while selecting date. " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
            dateFrom.show();
        });
    }

    private void sendLeaveUpdate(){
        mViewModel.confirmLeaveApplication(infoModel, new VMLeaveApproval.OnConfirmLeaveAppCallback() {
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
                initErrorDialog("PET Manager", message);
            }
        });
    }

    public void initWidgets(View view){
        poDialogx = new LoadDialog(getActivity());
        poMessage = new MessageBox(getActivity());
        lnSearch = view.findViewById(R.id.linear_search);
        txtSearch = view.findViewById(R.id.txt_leave_ob_search);
        btnQuickSearch = view.findViewById(R.id.btn_quick_search);
        lblHdBranch = view.findViewById(R.id.lbl_headerBranch);
        lblHdAddrss = view.findViewById(R.id.lbl_headerAddress);
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
        Date dateFrom = loDate.parse(Objects.requireNonNull(fsDateFrm));
        Date dateTo = loDate.parse(Objects.requireNonNull(fsDateTo));
        long diff = Objects.requireNonNull(dateTo).getTime() - Objects.requireNonNull(dateFrom).getTime();
        long noOfDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1;
        if(credits == 0){
            tieWOPay.setText(String.valueOf(noOfDays));
            infoModel.setWithOPay(String.valueOf(noOfDays));
        } else {
            tieWithPy.setText(String.valueOf(noOfDays));
            infoModel.setWithPayx(String.valueOf(noOfDays));
        }
    }

    @Override
    public void OnDownload() {
        txtSearch.setText("");
        poDialogx.initDialog("Leave Application", "Searching leave application. Please wait...", false);
        poDialogx.show();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void OnSuccessDownload(String TransNox) {
        poDialogx.dismiss();
        mViewModel.setTransNox(TransNox);
    }

    @Override
    public void OnFailedDownload(String message) {
        poDialogx.dismiss();
        initErrorDialog("Leave Application", message);
    }
}