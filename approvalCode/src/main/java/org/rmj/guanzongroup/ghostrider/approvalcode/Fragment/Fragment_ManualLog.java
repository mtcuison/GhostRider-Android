/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.approvalCode
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.approvalcode.Fragment;

import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.etc.LoadDialog;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.g3appdriver.utils.CopyToClipboard;
import org.rmj.g3appdriver.lib.ApprovalCode.ManualLog;
import org.rmj.guanzongroup.ghostrider.approvalcode.R;
import org.rmj.guanzongroup.ghostrider.approvalcode.ViewModel.VMManualLog;

import java.util.Calendar;

public class Fragment_ManualLog extends Fragment {

    private VMManualLog mViewModel;

    private AutoCompleteTextView txtBranch;
    private TextInputEditText txtReqDate;
    private TextInputEditText txtRemarks;
    private EditText txtAppCode;
    private CheckBox cbTimeInAM;
    private CheckBox cbTimeInPM;
    private CheckBox cbTmeOutAM;
    private CheckBox cbTmeOutPM;
    private CheckBox cbTimeInOT;
    private CheckBox cbTmeOutOT;
    private ImageButton btnCopy;
    private MaterialButton btnReqAppCode;
    private ManualLog loManual;

    private LoadDialog poDialog;
    private MessageBox poMsgBox;

    public static Fragment_ManualLog newInstance() {
        return new Fragment_ManualLog();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(VMManualLog.class);
        View v = inflater.inflate(R.layout.fragment_manual_log, container, false);
        loManual = new ManualLog();
        poDialog = new LoadDialog(requireActivity());
        poMsgBox = new MessageBox(requireActivity());
        txtBranch = v.findViewById(R.id.txt_appBranch);
        txtReqDate = v.findViewById(R.id.txt_appRequestDate);
        txtAppCode = v.findViewById(R.id.txt_approvalCode);
        txtRemarks = v.findViewById(R.id.txt_appRemarks);
        cbTimeInAM = v.findViewById(R.id.cb_timeInAM);
        cbTimeInPM = v.findViewById(R.id.cb_timeInPM);
        cbTmeOutAM = v.findViewById(R.id.cb_timeOutAM);
        cbTmeOutPM = v.findViewById(R.id.cb_timeOutPM);
        cbTimeInOT = v.findViewById(R.id.cb_timeInOT);
        cbTmeOutOT = v.findViewById(R.id.cb_timeOutOT);
        btnCopy = v.findViewById(R.id.btn_CopyToClipboard);
        btnReqAppCode = v.findViewById(R.id.btn_requestAppCode);

        txtReqDate.setInputType(InputType.TYPE_NULL);
        txtReqDate.setOnFocusChangeListener((view, b) -> {
            if(b){
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                DatePickerDialog picker = new DatePickerDialog(getActivity(),
                        (view1, year1, monthOfYear, dayOfMonth) -> {
                            String lsMonth = String.valueOf(monthOfYear + 1);
                            String lsDay = String.valueOf(dayOfMonth);

                            if ((monthOfYear + 1) < 10) lsMonth = "0" + (monthOfYear + 1);
                            if (dayOfMonth < 10) lsDay = "0" + dayOfMonth;

                            txtReqDate.setText(year1 + "-"+ lsMonth + "-" + lsDay);
                        }, year, month, day);
                picker.show();
            }
        });

        btnCopy.setOnClickListener(view -> {
            String KnoxPin = txtAppCode.getText().toString();
            String message;
            if (!KnoxPin.isEmpty()) {
                new CopyToClipboard(getActivity()).CopyTextClip("Knox_Pin", KnoxPin);
                message = "Approval code copied to clipboard.";
            } else {
                message = "Unable to copy empty content.";
            }
            Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
        });

        mViewModel.getBranchNames().observe(getViewLifecycleOwner(), strings -> {
            ArrayAdapter<String> loAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
            txtBranch.setAdapter(loAdapter);
        });

        txtBranch.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getBranchInfoList().observe(getViewLifecycleOwner(), eBranchInfos -> {
            for(int x = 0; x < eBranchInfos.size(); x++){
                if(txtBranch.getText().toString().equalsIgnoreCase(eBranchInfos.get(x).getBranchNm())){
                    loManual.setBranchCd(eBranchInfos.get(x).getBranchCd());
                    break;
                }
            }
        }));

        btnReqAppCode.setOnClickListener(view -> {
            loManual.setTimeInAM(cbTimeInAM.isChecked()? "1" : "0");
            loManual.setTimeInPM(cbTimeInPM.isChecked() ? "1" : "0");
            loManual.setTimeOutAM(cbTmeOutAM.isChecked() ? "1" : "0");
            loManual.setTimeOutPM(cbTmeOutPM.isChecked() ? "1" : "0");
            loManual.setTimeInOT(cbTimeInOT.isChecked() ? "1" : "0");
            loManual.setTimeOutOT(cbTmeOutOT.isChecked() ? "1" : "0");
            loManual.setRemarks(txtRemarks.getText().toString());
            loManual.setReqDatex(txtReqDate.getText().toString());
            loManual.setSysCode("ML");
            mViewModel.GenerateCode(loManual, new VMManualLog.OnGenerateApprovalCodeListener() {
                @Override
                public void OnGenerate(String title, String message) {
                    poDialog.initDialog(title, message, false);
                    poDialog.show();
                }

                @Override
                public void OnSuccess(String args) {
                    poDialog.dismiss();
                    txtAppCode.setText(args);
                }

                @Override
                public void OnFailed(String message) {
                    poDialog.dismiss();
                    poMsgBox.initDialog();
                    poMsgBox.setTitle("Approval Code");
                    poMsgBox.setMessage(message);
                    poMsgBox.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
                    poMsgBox.show();
                }
            });
        });

        return v;
    }
}