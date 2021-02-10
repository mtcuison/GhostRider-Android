package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model.PaidTransactionModel;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.VMPaidTransaction;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.ViewModelCallback;

public class Fragment_PaidTransaction extends Fragment implements ViewModelCallback {

    private VMPaidTransaction mViewModel;

    private TextView lblBranch, lblAddress;

    private Spinner spnType;
    private TextInputEditText txtRemarks, txtAmount, txtDiscount, txtOthers, txtTotAmnt;
    private MaterialButton btnConfirm;

    public static Fragment_PaidTransaction newInstance() {
        return new Fragment_PaidTransaction();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_paid_transaction, container, false);
        initWidgets(view);

        return view;
    }

    private void initWidgets(View v){
        lblBranch = v.findViewById(R.id.lbl_headerBranch);
        lblAddress = v.findViewById(R.id.lbl_headerAddress);

        spnType = v.findViewById(R.id.spn_paymentType);
        txtRemarks = v.findViewById(R.id.txt_dcpRemarks);
        txtAmount = v.findViewById(R.id.txt_dcpAmount);
        txtDiscount = v.findViewById(R.id.txt_dcpDiscount);
        txtOthers = v.findViewById(R.id.txt_dcpOthers);
        txtTotAmnt = v.findViewById(R.id.txt_dcpTotAmount);
        btnConfirm = v.findViewById(R.id.btn_confirm);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMPaidTransaction.class);

        mViewModel.getUserBranchEmployee().observe(getViewLifecycleOwner(), eBranchInfo -> {
            lblBranch.setText(eBranchInfo.getBranchNm());
            lblAddress.setText(eBranchInfo.getAddressx());
        });

        mViewModel.getPaymentType().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnType.setAdapter(stringArrayAdapter));

        btnConfirm.setOnClickListener(view -> {
            PaidTransactionModel infoModel = new PaidTransactionModel();
            infoModel.setPayment(spnType.getSelectedItem().toString());
            infoModel.setRemarks(txtRemarks.getText().toString());
            infoModel.setAmountx(txtAmount.getText().toString());
            infoModel.setDscount(txtDiscount.getText().toString());
            infoModel.setOthersx(txtDiscount.getText().toString());
            infoModel.setTotAmnt(txtTotAmnt.getText().toString());
            mViewModel.savePaidInfo(infoModel, Fragment_PaidTransaction.this);
        });
    }

    @Override
    public void OnStartSaving() {

    }

    @Override
    public void OnSuccessResult(String[] args) {

    }

    @Override
    public void OnFailedResult(String message) {

    }
}