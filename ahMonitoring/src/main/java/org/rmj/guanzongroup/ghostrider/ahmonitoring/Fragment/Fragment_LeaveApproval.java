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

import androidx.cardview.widget.CardView;
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
import org.rmj.g3appdriver.GRider.Etc.FormatUIText;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Model.RequestLeaveObInfoModel;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VmLeaveOBApproval;

public class Fragment_LeaveApproval extends Fragment {
    public static final String TAG = Fragment_LeaveApproval.class.getSimpleName();
    private VmLeaveOBApproval mViewModel;
    private TextInputEditText txtSearch;
    ImageButton btnQuickSearch;
    private RequestLeaveObInfoModel infoModel;
    private LoadDialog poDialogx;
    private MessageBox poMessage;
    private CardView cvLeaveOb;
    private MaterialButton btnCancel, bntConfirm;
    private TextView lblTransNox, lblEmployeNm, lblDateFrom, lblDateThru, lblRemarks;
    private TextInputEditText tieDateFrom, tieDateThru, tieDApplied, txtPurpse;
    private TextInputLayout tilRemarks;
    public static Fragment_LeaveApproval newInstance() {
        return new Fragment_LeaveApproval();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leave_approval, container, false);
        infoModel = new RequestLeaveObInfoModel();
        initWidgets(view);
        return view;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VmLeaveOBApproval.class);
        Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.roboto_bold);
        tilRemarks.setTypeface(typeface);
        btnQuickSearch.setOnClickListener(v -> mViewModel.importRequestLeaveApplication(txtSearch.getText().toString(), new VmLeaveOBApproval.OnKwikSearchCallBack() {
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
                    lblTransNox.setText("Transaction No. : " + leave.getString("sTransNox"));
                    lblEmployeNm.setText(leave.getString("xEmployee"));
                    tieDApplied.setText(FormatUIText.formatGOCasBirthdate(leave.getString("dTransact")));
                    tieDateFrom.setText(FormatUIText.formatGOCasBirthdate(leave.getString("dAppldFrx")));
                    tieDateThru.setText(FormatUIText.formatGOCasBirthdate(leave.getString("dAppldTox")));
                    txtPurpse.setText(leave.getString("sPurposex"));
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
            mViewModel.saveObLeave(infoModel, WebApi.URL_CONFIRM_OB_APPLICATION, new VmLeaveOBApproval.OnConfirmOBLeaveListener() {
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
        });
        btnCancel.setOnClickListener(v -> {
            mViewModel.saveObLeave(infoModel, WebApi.URL_CONFIRM_OB_APPLICATION, new VmLeaveOBApproval.OnConfirmOBLeaveListener() {
                @Override
                public void onConfirm() {

                }

                @Override
                public void onSuccess() {

                }

                @Override
                public void onFailed(String message) {

                }
            });
        });
    }
    public void initWidgets(View view){
        poDialogx = new LoadDialog(getActivity());
        poMessage = new MessageBox(getActivity());
        txtSearch = view.findViewById(R.id.txt_leave_ob_search);
        btnQuickSearch = view.findViewById(R.id.btn_quick_search);
        cvLeaveOb = view.findViewById(R.id.cv_leave_ob);
        lblTransNox = view.findViewById(R.id.lbl_transNox);
        lblEmployeNm = view.findViewById(R.id.lbl_clientNm);
        tieDateFrom = view.findViewById(R.id.txt_dateFrom);
        tieDApplied = view.findViewById(R.id.txt_dateApplied);
        tieDateThru = view.findViewById(R.id.txt_dateTo);
        tilRemarks = view.findViewById(R.id.tilRemarks);
        txtPurpse = view.findViewById(R.id.txt_purpose);

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
}