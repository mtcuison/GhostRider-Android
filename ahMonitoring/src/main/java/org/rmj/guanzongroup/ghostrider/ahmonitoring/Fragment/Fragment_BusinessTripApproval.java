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

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Dialog.DialogKwikSearchLeave;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Model.RequestLeaveObInfoModel;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VmLeaveOBApproval;

import java.util.List;

public class Fragment_BusinessTripApproval extends Fragment implements VmLeaveOBApproval.OnKwikSearchCallBack, VmLeaveOBApproval.OnConfirmOBLeaveListener {
    public static final String TAG = Fragment_BusinessTripApproval.class.getSimpleName();
    private VmLeaveOBApproval mViewModel;
    private TextInputEditText txtSearch;
    ImageButton btnQuickSearch;

    private MaterialButton btnCancel, bntConfirm;
    private TextView lblEmployeNm, lblDateFrom, lblDateThru, lblRemarks;
    private TextInputLayout tilRemarks;
    private RequestLeaveObInfoModel infoModel;
    private LoadDialog poDialogx;
    private MessageBox poMessage;
    private CardView cvLeaveOb;
    private TextInputEditText tieDateFrom, tieDateThru;
    public static Fragment_BusinessTripApproval newInstance() {
        return new Fragment_BusinessTripApproval();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_business_trip_approval, container, false);
        initWidgets(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VmLeaveOBApproval.class);
        Typeface typeface = ResourcesCompat.getFont(requireActivity(), R.font.roboto_bold);
        tilRemarks.setTypeface(typeface);
        btnQuickSearch.setOnClickListener(v ->  {
            if (txtSearch.getText().toString().isEmpty()){
                initEmptyDialog();
            }else {
                mViewModel.importRequestBusinessTrip(txtSearch.getText().toString(), Fragment_BusinessTripApproval.this);
            }
        });

        bntConfirm.setOnClickListener(v -> {
            mViewModel.saveObLeave(infoModel, WebApi.URL_CONFIRM_OB_APPLICATION,Fragment_BusinessTripApproval.this);
        });
        btnCancel.setOnClickListener(v -> {
            mViewModel.saveObLeave(infoModel, WebApi.URL_CONFIRM_OB_APPLICATION,Fragment_BusinessTripApproval.this);
        });
    }
    public void initWidgets(View view){
        infoModel = new RequestLeaveObInfoModel();
        poDialogx = new LoadDialog(getActivity());
        poMessage = new MessageBox(getActivity());
        txtSearch = view.findViewById(R.id.txt_leave_ob_search);
        btnQuickSearch = view.findViewById(R.id.btn_quick_search);
        cvLeaveOb = view.findViewById(R.id.cv_leave_ob);
        lblEmployeNm = view.findViewById(R.id.lbl_clientNm);
        tieDateFrom = view.findViewById(R.id.txt_dateFrom);
        tieDateThru = view.findViewById(R.id.txt_dateTo);
        tilRemarks = view.findViewById(R.id.tilRemarks);
//        lblRemarks = view.findViewById(R.id.lblRemarks);
        btnCancel = view.findViewById(R.id.btn_cancel);
        bntConfirm = view.findViewById(R.id.btn_confirm);
    }

    @Override
    public void onStartKwikSearch() {
        poDialogx.initDialog("OB Application", "Searching ob application. Please wait...", false);
        poDialogx.show();
    }

    @Override
    public void onSuccessKwikSearch(JSONObject leave) {
        poDialogx.dismiss();
//        initDialog(infoList);
    }

    @Override
    public void onKwikSearchFailed(String message) {
        poDialogx.dismiss();
        initErrorDialog("OB Application", message);
    }
    public void initDialog(List<RequestLeaveObInfoModel> infoList){
        DialogKwikSearchLeave loDialog = new DialogKwikSearchLeave(getActivity(),infoList);
        loDialog.initDialogKwikSearch((dialog, infoModel) -> {
            txtSearch.setText(infoModel.getsTransNox());
            infoModel = infoModel;
            cvLeaveOb.setVisibility(View.VISIBLE);
            loDialog.dismiss();
        });
        loDialog.show();
    }
    public void initEmptyDialog(){
        poDialogx.dismiss();
        poMessage.initDialog();
        poMessage.setTitle("OB Application");
        poMessage.setMessage("TransNox Required!");
        poMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
        poMessage.show();
    }
    public void initErrorDialog(String title, String message){
        poMessage.initDialog();
        poMessage.setTitle(title);
        poMessage.setMessage(message);
        poMessage.setPositiveButton("Okay", (view, dialog) ->
                dialog.dismiss());
        poMessage.show();
    }

    @Override
    public void onConfirm() {
        poDialogx.initDialog("OB Application", "Confirming ob application. Please wait...", false);
        poDialogx.show();
    }

    @Override
    public void onSuccess() {
        poDialogx.dismiss();
    }

    @Override
    public void onFailed(String message) {
        poDialogx.dismiss();
        initErrorDialog("OB Application", message);
    }
}