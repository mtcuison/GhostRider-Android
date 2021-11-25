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

import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.g3appdriver.utils.CopyToClipboard;
import org.rmj.guanzongroup.ghostrider.approvalcode.Etc.ViewModelCallback;
import org.rmj.guanzongroup.ghostrider.approvalcode.Model.ManualLog;
import org.rmj.guanzongroup.ghostrider.approvalcode.R;
import org.rmj.guanzongroup.ghostrider.approvalcode.ViewModel.VMManualLog;

import java.util.Calendar;

public class Fragment_ManualLog extends Fragment implements ViewModelCallback {

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
        View v = inflater.inflate(R.layout.fragment_manual_log, container, false);
        loManual = new ManualLog();
        poDialog = new LoadDialog(getActivity());
        poMsgBox = new MessageBox(getActivity());
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
                message = "Knox pin copied to clipboard.";
            } else {
                message = "Unable to copy empty content.";
            }
            Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        });
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMManualLog.class);
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
            mViewModel.creatApprovalCode(loManual, new VMManualLog.CodeApprovalCreatedListener() {
                @Override
                public void OnCreate(String args) {
                    txtAppCode.setText(args);
                }

                @Override
                public void OnCreateFailed(String message) {
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                }
            });
        });
    }

    @Override
    public void OnLoadData(String Title, String Message) {
        poDialog.initDialog(Title, Message, false);
        poDialog.show();
    }

    @Override
    public void OnSuccessResult(String args) {
        poDialog.dismiss();
    }

    @Override
    public void OnFailedResult(String message) {
        poDialog.dismiss();
        poMsgBox.initDialog();
        poMsgBox.setTitle("Approval Code");
        poMsgBox.setMessage(message);
        poMsgBox.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
        poMsgBox.show();
    }
}