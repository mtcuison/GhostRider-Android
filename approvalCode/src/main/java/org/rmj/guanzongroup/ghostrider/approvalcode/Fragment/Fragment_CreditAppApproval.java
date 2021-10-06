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

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.g3appdriver.utils.CopyToClipboard;
import org.rmj.guanzongroup.ghostrider.approvalcode.Activity.Activity_ApprovalCode;
import org.rmj.guanzongroup.ghostrider.approvalcode.Etc.ViewModelCallback;
import org.rmj.guanzongroup.ghostrider.approvalcode.Model.CreditApp;
import org.rmj.guanzongroup.ghostrider.approvalcode.R;
import org.rmj.guanzongroup.ghostrider.approvalcode.ViewModel.VMCreditAppApproval;

import java.util.Objects;

public class Fragment_CreditAppApproval extends Fragment implements ViewModelCallback, VMCreditAppApproval.OnLoadApplicationListener {

    private VMCreditAppApproval mViewModel;

    private TextInputEditText txtTransNox, txtReqDate, txtMiscInfo, txtRemarks, txtAppNotes;
    private AutoCompleteTextView txtBranch, txtReBranch;
    private EditText txtAppCode;
    private ImageButton btnCopy;
    private MaterialButton btnLoadApp, btnApprove, btnDApprove, btnAppNoCI;
    private LoadDialog poDialog;
    private MessageBox poMsgBox;

    private String sSystemCD;

    public static Fragment_CreditAppApproval newInstance() {
        return new Fragment_CreditAppApproval();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_credit_app_approval, container, false);
        sSystemCD = Activity_ApprovalCode.getInstance().getSystemCode();
        poDialog = new LoadDialog(getActivity());
        poMsgBox = new MessageBox(getActivity());
        initWidgets(view);
        return view;
    }

    private void initWidgets(View v){
        txtTransNox = v.findViewById(R.id.txt_appTransNox);
        txtReqDate = v.findViewById(R.id.txt_appRequestDate);
        txtMiscInfo = v.findViewById(R.id.txt_appMiscInfo);
        txtRemarks = v.findViewById(R.id.txt_appRemarks);
        txtAppNotes = v.findViewById(R.id.txt_appNotes);
        txtBranch = v.findViewById(R.id.txt_appBranch);
        txtReBranch = v.findViewById(R.id.txt_appReqstBranch);
        txtAppCode = v.findViewById(R.id.txt_approvalCode);
        btnCopy = v.findViewById(R.id.btn_CopyToClipboard);
        btnLoadApp = v.findViewById(R.id.btn_loadApplication);
        btnApprove = v.findViewById(R.id.btn_ApproveWithCI);
        btnDApprove = v.findViewById(R.id.btn_DisapproveApp);
        btnAppNoCI = v.findViewById(R.id.btn_ApproveAppNoCI);

        txtReBranch.setEnabled(false);
        txtReqDate.setEnabled(false);
        txtMiscInfo.setEnabled(false);
        txtRemarks.setEnabled(false);

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
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMCreditAppApproval.class);
        btnLoadApp.setOnClickListener(view -> {
            String lsTransNo = Objects.requireNonNull(txtTransNox.getText()).toString();
            String lsBrnchCd = txtBranch.getText().toString();
            CreditApp loApp = new CreditApp(sSystemCD, lsTransNo, lsBrnchCd);
            mViewModel.LoadApplication(loApp, Fragment_CreditAppApproval.this);
        });

        btnApprove.setOnClickListener(view -> {
            String lsTransNo = Objects.requireNonNull(txtTransNox.getText()).toString();
            String lsReasonx = Objects.requireNonNull(txtAppNotes.getText()).toString();
            CreditApp.Application loApp = new CreditApp.Application(lsTransNo, lsReasonx, "yes");
            mViewModel.ApproveApplication(loApp, Fragment_CreditAppApproval.this);
        });

        btnDApprove.setOnClickListener(view -> {
            String lsTransNo = Objects.requireNonNull(txtTransNox.getText()).toString();
            String lsReasonx = Objects.requireNonNull(txtAppNotes.getText()).toString();
            CreditApp.Application loApp = new CreditApp.Application(lsTransNo, lsReasonx, "no");
            mViewModel.ApproveApplication(loApp, Fragment_CreditAppApproval.this);
        });

        btnAppNoCI.setOnClickListener(view -> {
            String lsTransNo = Objects.requireNonNull(txtTransNox.getText()).toString();
            String lsReasonx = Objects.requireNonNull(txtAppNotes.getText()).toString();
            CreditApp.Application loApp = new CreditApp.Application(lsTransNo, lsReasonx, "na");
            mViewModel.ApproveApplication(loApp, Fragment_CreditAppApproval.this);
        });
    }

    @Override
    public void OnLoadData(String Title, String Message) {
        poDialog.initDialog(Title, Message, false);
        poDialog.show();
    }

    @Override
    public void OnSuccessResult(String args) {
        txtAppCode.setText(args);
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

    @Override
    public void OnLoad(String Title, String Message) {
        poDialog.initDialog(Title, Message, false);
        poDialog.show();
    }

    @Override
    public void OnLoadSuccess(String args) {
        poDialog.dismiss();
        try{
            JSONObject loJson = new JSONObject(args);
            txtReBranch.setText(loJson.getString("rqstinfo"));
            txtReqDate.setText(loJson.getString("reqstdxx"));
            txtMiscInfo.setText(loJson.getString("miscinfo"));
            txtRemarks.setText(loJson.getString("remarks1"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnLoadFailed(String message) {
        poDialog.dismiss();
        poMsgBox.initDialog();
        poMsgBox.setTitle("Approval Code");
        poMsgBox.setMessage(message);
        poMsgBox.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
        poMsgBox.show();
    }
}