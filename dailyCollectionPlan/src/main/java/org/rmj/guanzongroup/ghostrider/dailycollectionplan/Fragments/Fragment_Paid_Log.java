package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities.Activity_TransactionLog;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Etc.DCP_Constants;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.VMClientDetl_Log;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.VMPaid_Log;

public class Fragment_Paid_Log extends Fragment {
    private VMPaid_Log mViewModel;
    private TextView txtAcctNo, txtClientName, txtClientAddress;
    private TextView txtPaymentTp, txtPRNoxx, txtTransAmtx, txtDiscount, txtPenalty, txtTotalAmtx, txtRemarksx;

    public Fragment_Paid_Log() { }

    public static Fragment_Paid_Log newInstance() {
        return new Fragment_Paid_Log();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_paid_log, container, false);
        mViewModel = ViewModelProviders.of(this).get(VMPaid_Log.class);
        initWidgets(v);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Functions
        txtAcctNo.setText(Activity_TransactionLog.acctNox);
        txtClientName.setText(Activity_TransactionLog.fullNme);
        txtClientAddress.setText(Activity_TransactionLog.clientAddress);
        mViewModel.setParameters(Activity_TransactionLog.transNox,
                Activity_TransactionLog.entryNox,
                Activity_TransactionLog.acctNox);

        mViewModel.getPaidCollectionDetail().observe(getViewLifecycleOwner(), collectPaidDetl -> {
            txtPaymentTp.setText(DCP_Constants.PAYMENT_TYPE[Integer.parseInt(collectPaidDetl.getTranType())]);
            txtPRNoxx.setText(collectPaidDetl.getPRNoxxxx());
            txtTransAmtx.setText(getString(R.string.peso_sign) + collectPaidDetl.getTranAmtx());
            txtDiscount.setText(getString(R.string.peso_sign) + collectPaidDetl.getDiscount());
            txtPenalty.setText(getString(R.string.peso_sign) + collectPaidDetl.getOthersxx());
            txtTotalAmtx.setText(getString(R.string.peso_sign) + collectPaidDetl.getTranTotl());
            txtRemarksx.setText(collectPaidDetl.getRemarksx());
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
    }
}