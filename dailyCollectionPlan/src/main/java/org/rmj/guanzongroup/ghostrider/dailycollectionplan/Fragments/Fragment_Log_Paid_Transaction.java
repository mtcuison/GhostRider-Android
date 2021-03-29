package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities.Activity_LogTransaction;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Etc.DCP_Constants;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.VMLogPaidTransaction;

public class Fragment_Log_Paid_Transaction extends Fragment {
    private VMLogPaidTransaction mViewModel;
    private TextView txtAcctNo, txtClientName, txtClientAddress;
    private TextView txtPaymentTp, txtPRNoxx, txtTransAmtx, txtDiscount, txtPenalty, txtTotalAmtx, txtRemarksx, txtCheckPayment;

    public Fragment_Log_Paid_Transaction() { }

    public static Fragment_Log_Paid_Transaction newInstance() {
        return new Fragment_Log_Paid_Transaction();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_paid_log, container, false);
        mViewModel = ViewModelProviders.of(this).get(VMLogPaidTransaction.class);
        initWidgets(v);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Functions
        txtAcctNo.setText(Activity_LogTransaction.acctNox);
        txtClientName.setText(Activity_LogTransaction.fullNme);
        txtClientAddress.setText(Activity_LogTransaction.clientAddress);
        mViewModel.setParameters(Activity_LogTransaction.transNox,
                Activity_LogTransaction.acctNox,
                Activity_LogTransaction.remCodex);

        mViewModel.getPostedCollectionDetail().observe(getViewLifecycleOwner(), collectPaidDetl -> {
            try {
                if(collectPaidDetl.getBankIDxx() != null) {
                    txtCheckPayment.setVisibility(View.VISIBLE);
                } else {
                    txtCheckPayment.setVisibility(View.GONE);
                }
                txtPaymentTp.setText(DCP_Constants.PAYMENT_TYPE[Integer.parseInt(collectPaidDetl.getTranType())]);
                txtPRNoxx.setText(collectPaidDetl.getPRNoxxxx());
                txtTransAmtx.setText(getString(R.string.peso_sign) + collectPaidDetl.getTranAmtx());
                txtDiscount.setText(getString(R.string.peso_sign) + collectPaidDetl.getDiscount());
                txtPenalty.setText(getString(R.string.peso_sign) + collectPaidDetl.getOthersxx());
                txtTotalAmtx.setText(getString(R.string.peso_sign) + collectPaidDetl.getTranTotl());
                txtRemarksx.setText(collectPaidDetl.getRemarksx());
            } catch(Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void initWidgets(View v) {
        // TODO: Initialize Widgets
        txtAcctNo = v.findViewById(R.id.txt_acctNo);
        txtClientName = v.findViewById(R.id.txt_clientName);
        txtClientAddress = v.findViewById(R.id.txt_client_address);

        txtPaymentTp = v.findViewById(R.id.txt_payment_type);
        txtPRNoxx = v.findViewById(R.id.txt_pr_no);
        txtTransAmtx = v.findViewById(R.id.txt_trans_amount);
        txtDiscount = v.findViewById(R.id.txt_discount);
        txtPenalty = v.findViewById(R.id.txt_penalty);
        txtTotalAmtx = v.findViewById(R.id.txt_total_amount);
        txtRemarksx = v.findViewById(R.id.txt_remarks);
        txtCheckPayment = v.findViewById(R.id.lbl_check_payment);
    }
}