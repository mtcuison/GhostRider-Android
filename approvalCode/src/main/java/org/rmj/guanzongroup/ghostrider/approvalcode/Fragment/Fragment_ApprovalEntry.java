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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.g3appdriver.utils.CopyToClipboard;
import org.rmj.guanzongroup.ghostrider.approvalcode.Activity.Activity_ApprovalCode;
import org.rmj.guanzongroup.ghostrider.approvalcode.Etc.ViewModelCallback;
import org.rmj.guanzongroup.ghostrider.approvalcode.Model.ApprovalEntry;
import org.rmj.guanzongroup.ghostrider.approvalcode.R;
import org.rmj.guanzongroup.ghostrider.approvalcode.ViewModel.VMApprovalEntry;

import java.util.Calendar;
import java.util.Objects;

public class Fragment_ApprovalEntry extends Fragment implements ViewModelCallback {

    private VMApprovalEntry mViewModel;

    private String psSysType, psSysCode, psSCAType;

    private AutoCompleteTextView txtBranch;
    private TextInputLayout tilReferNo;
    private TextInputEditText txtDate, txtReferNo, txtLastNm, txtFrstNm, txtMiddNm, txtSuffix, txtRemarks;
    private EditText txtAppCode;
    private LinearLayout lnFullNm;
    private MaterialButton btnCreate;
    private ImageButton btnCopy;
    private ApprovalEntry poEntryxx;
    private MessageBox poMesgBox;

    public static Fragment_ApprovalEntry newInstance() {
        return new Fragment_ApprovalEntry();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_approval_entry, container, false);
        poEntryxx = new ApprovalEntry();
        poMesgBox = new MessageBox(getActivity());
        psSysType = Activity_ApprovalCode.getInstance().getSystemType();
        psSysCode = Activity_ApprovalCode.getInstance().getSystemCode();
        psSCAType = Activity_ApprovalCode.getInstance().getPsSCAType();
        poEntryxx.setSysTypex(psSysType);
        poEntryxx.setSystemCd(psSysCode);
        poEntryxx.setSCAType(psSCAType);
        initWidgets(v);
        return v;
    }

    private void initWidgets(View v){
        txtBranch = v.findViewById(R.id.txt_appBranch);
        tilReferNo = v.findViewById(R.id.til_appReferNox);
        txtReferNo = v.findViewById(R.id.txt_appReferNox);
        txtDate = v.findViewById(R.id.txt_appRequestDate);
        lnFullNm = v.findViewById(R.id.linear_appFullname);
        txtLastNm = v.findViewById(R.id.txt_appLastName);
        txtFrstNm = v.findViewById(R.id.txt_appFirstName);
        txtMiddNm = v.findViewById(R.id.txt_appMiddName);
        txtSuffix = v.findViewById(R.id.txt_appSuffix);
        txtRemarks = v.findViewById(R.id.txt_appRemarks);
        txtAppCode = v.findViewById(R.id.txt_approvalCode);
        btnCreate = v.findViewById(R.id.btn_requestAppCode);
        btnCopy = v.findViewById(R.id.btn_CopyToClipboard);

        txtDate.setInputType(InputType.TYPE_NULL);
        txtDate.setOnFocusChangeListener((view, b) -> {
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

                            txtDate.setText(year1 + "-"+ lsMonth + "-" + lsDay);
                        }, year, month, day);
                picker.show();
            }
        });

        switch (psSysType){
            case "1":
                lnFullNm.setVisibility(View.GONE);
                break;
            case "2":
                tilReferNo.setVisibility(View.GONE);
                break;
        }

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
        mViewModel = new ViewModelProvider(this).get(VMApprovalEntry.class);

        mViewModel.getBranchNameList().observe(getViewLifecycleOwner(), strings -> {
            ArrayAdapter<String> loAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
            txtBranch.setAdapter(loAdapter);
        });

        txtBranch.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getAllBranchInfo().observe(getViewLifecycleOwner(), eBranchInfos -> {
            for(int x = 0; x < eBranchInfos.size(); x++){
                if(txtBranch.getText().toString().equalsIgnoreCase(eBranchInfos.get(x).getBranchNm())){
                    poEntryxx.setBranchCd(eBranchInfos.get(x).getBranchCd());
                    break;
                }
            }
        }));

        btnCreate.setOnClickListener(view -> {
            poEntryxx.setReqDatex(Objects.requireNonNull(txtDate.getText()).toString());
            poEntryxx.setReferNox(Objects.requireNonNull(txtReferNo.getText()).toString());
            poEntryxx.setLastName(Objects.requireNonNull(txtLastNm.getText()).toString());
            poEntryxx.setFrstName(Objects.requireNonNull(txtFrstNm.getText()).toString());
            poEntryxx.setSuffix(Objects.requireNonNull(txtSuffix.getText()).toString());
            poEntryxx.setMiddName(Objects.requireNonNull(txtMiddNm.getText()).toString());
            poEntryxx.setRemarks(Objects.requireNonNull(txtRemarks.getText()).toString());
            mViewModel.CreateApprovalCode(poEntryxx, new VMApprovalEntry.CodeApprovalCreatedListener() {
                @Override
                public void OnCreate(String args) {
                    txtAppCode.setText(args);
                }

                @Override
                public void OnCreateFailed(String message) {
                    poMesgBox.initDialog();
                    poMesgBox.setPositiveButton("Okay", (view1, dialog) -> dialog.dismiss());
                    poMesgBox.setTitle("Approval Code");
                    poMesgBox.setMessage(message);
                    poMesgBox.show();
                }
            });
        });
    }

    @Override
    public void OnLoadData(String Title, String Message) {

    }

    @Override
    public void OnSuccessResult(String args) {
        txtAppCode.setText(args);
    }

    @Override
    public void OnFailedResult(String message) {
        poMesgBox.initDialog();
        poMesgBox.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
        poMesgBox.setTitle("Approval Code");
        poMesgBox.setMessage(message);
        poMesgBox.show();
    }
}