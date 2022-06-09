/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.dailyCollectionPlan
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Etc.FormatUIText;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities.Activity_Transaction;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Dialog.DialogCheckPayment;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model.PaidTransactionModel;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.VMPaidTransaction;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.ViewModelCallback;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.StringTokenizer;

public class Fragment_PaidTransaction extends Fragment implements ViewModelCallback {

    private VMPaidTransaction mViewModel;
    private PaidTransactionModel infoModel;
    private MessageBox poMessage;
    private LoadDialog poDialog;
    private CheckBox cbCheckPymnt, cbRebate;
    private TextView lblBranch, lblAddress, lblAccNo, lblClientNm, lblTransNo;

    private Spinner spnType;
    private TextInputEditText txtPrNoxx, txtRemarks, txtAmount, txtDiscount, txtOthers, txtTotAmnt;
    private TextInputLayout tilDiscount;
    private Button btnAmort, btnRBlnce, btnClear;
    private MaterialButton btnConfirm;

    private String psMonthAmt, psRBalance, psAmntDue;

    private long mLastClickTime = 0;

    public static Fragment_PaidTransaction newInstance() {
        return new Fragment_PaidTransaction();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_paid_transaction, container, false);
        infoModel = new PaidTransactionModel();
        poMessage = new MessageBox(getActivity());
        poDialog = new LoadDialog(getActivity());
        initWidgets(view);
        return view;
    }

    private void initWidgets(View v){
        lblBranch = v.findViewById(R.id.lbl_headerBranch);
        lblAddress = v.findViewById(R.id.lbl_headerAddress);
        lblAccNo = v.findViewById(R.id.lbl_dcpAccNo);
        lblClientNm = v.findViewById(R.id.lbl_dcpClientNm);
        lblTransNo = v.findViewById(R.id.lbl_dcpTransNo);
        spnType = v.findViewById(R.id.spn_paymentType);
        cbRebate = v.findViewById(R.id.cb_rebate);
        cbCheckPymnt = v.findViewById(R.id.cb_dcpCheckPayment);
        txtPrNoxx = v.findViewById(R.id.txt_dcpPRNumber);
        txtRemarks = v.findViewById(R.id.txt_dcpRemarks);
        txtAmount = v.findViewById(R.id.txt_dcpAmount);
        tilDiscount = v.findViewById(R.id.til_dcpDiscount);
        txtDiscount = v.findViewById(R.id.txt_dcpDiscount);
        txtOthers = v.findViewById(R.id.txt_dcpOthers);
        txtTotAmnt = v.findViewById(R.id.txt_dcpTotAmount);
        btnConfirm = v.findViewById(R.id.btn_confirm);
        btnAmort = v.findViewById(R.id.btn_amortization);
        btnRBlnce = v.findViewById(R.id.btn_remainbalance);
        btnClear = v.findViewById(R.id.btn_clearText);

        txtAmount.addTextChangedListener(new OnAmountEnterTextWatcher(txtAmount));
        txtDiscount.addTextChangedListener(new OnAmountEnterTextWatcher(txtDiscount));
        txtOthers.addTextChangedListener(new OnAmountEnterTextWatcher(txtOthers));
        txtTotAmnt.addTextChangedListener(new FormatUIText.CurrencyFormat(txtTotAmnt));
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String Remarksx = Activity_Transaction.getInstance().getRemarksCode();
        String TransNox = Activity_Transaction.getInstance().getTransNox();
        int EntryNox = Activity_Transaction.getInstance().getEntryNox();
        mViewModel = new ViewModelProvider(this).get(VMPaidTransaction.class);
        mViewModel.setParameter(TransNox, EntryNox);
        mViewModel.getCollectionDetail().observe(getViewLifecycleOwner(), collectionDetail -> {
            try {
                lblAccNo.setText(collectionDetail.getAcctNmbr());
                lblClientNm.setText(collectionDetail.getFullName());
                lblTransNo.setText(collectionDetail.getTransNox());
                mViewModel.setCurrentCollectionDetail(collectionDetail);
                psAmntDue = collectionDetail.getAmtDuexx();
                psMonthAmt = collectionDetail.getMonAmort();
                psRBalance = collectionDetail.getABalance();
                mViewModel.setMonthlyAmort(Double.valueOf(psMonthAmt));
                mViewModel.setAmountDue(Double.valueOf(psAmntDue));
                Date loDate = new SimpleDateFormat("yyyy-MM-dd").parse(new AppConstants().CURRENT_DATE);
                if (new SimpleDateFormat("yyyy-MM-dd").parse(collectionDetail.getDueDatex()).before(loDate) ||
                        new SimpleDateFormat("yyyy-MM-dd").parse(collectionDetail.getDueDatex()).equals(loDate)) {
                    cbRebate.setEnabled(true);
                } else {
                    cbRebate.setChecked(false);
                    cbRebate.setEnabled(false);
                }
                btnAmort.setText("Amortization : " + FormatUIText.getCurrencyUIFormat(collectionDetail.getMonAmort()));
                btnRBlnce.setText("Amount Due : " + FormatUIText.getCurrencyUIFormat(collectionDetail.getAmtDuexx()));
                if (new SimpleDateFormat("MM/yyyy").parse(collectionDetail.getDueDatex()).after(new Date())) {
                    cbRebate.setEnabled(false);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getUserBranchEmployee().observe(getViewLifecycleOwner(), eBranchInfo -> {
            try {
                lblBranch.setText(eBranchInfo.getBranchNm());
                lblAddress.setText(eBranchInfo.getAddressx());
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getPaymentType().observe(getViewLifecycleOwner(), stringArrayAdapter -> {
            spnType.setAdapter(stringArrayAdapter);
            spnType.setSelection(1);
        });

        mViewModel.getPrNox().observe(getViewLifecycleOwner(), s -> {
            if(s != null){
                txtPrNoxx.setText(s);
            }
        });

        mViewModel.getRebate().observe(getViewLifecycleOwner(), aDouble -> {
            try{
                txtDiscount.setText(String.valueOf(aDouble));
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getPenalty().observe(getViewLifecycleOwner(), aDouble -> {
            try{
                txtOthers.setText(String.valueOf(aDouble));
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getRebateNotice().observe(getViewLifecycleOwner(), s -> {
            if(!s.isEmpty()){
                tilDiscount.setErrorEnabled(true);
                tilDiscount.setError(s);
            } else {
                tilDiscount.setErrorEnabled(false);
            }
        });

        txtDiscount.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus) {
                if (!txtDiscount.getText().toString().isEmpty()) {
                    mViewModel.setDiscount(Double.valueOf(txtDiscount.getText().toString().replace(",", "")));
                } else {
                    mViewModel.setDiscount(0.00);
                }
            }
        });

        mViewModel.getTotalAmount().observe(getViewLifecycleOwner(), aFloat -> txtTotAmnt.setText(String.valueOf(aFloat)));

        cbCheckPymnt.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                DialogCheckPayment loPayment = new DialogCheckPayment(getActivity());
                mViewModel.getBankNameList().observe(getViewLifecycleOwner(), strings -> {
                    loPayment.initDialog(strings, new DialogCheckPayment.OnCheckPaymentDialogListener() {
                        @Override
                        public void OnConfirm(AlertDialog dialog, String bank, String date, String checkNo, String AcctNo) {
                            infoModel.setCheckDt(date);
                            infoModel.setCheckNo(checkNo);
                            infoModel.setAccntNo(AcctNo);
                            mViewModel.getBankInfoList().observe(getViewLifecycleOwner(), eBankInfos -> {
                                for(int x = 0; x < eBankInfos.size(); x++){
                                    if(bank.equalsIgnoreCase(eBankInfos.get(x).getBankName())){
                                        infoModel.setBankNme(eBankInfos.get(x).getBankIDxx());
                                        break;
                                    }
                                }
                            });
                            dialog.dismiss();
                        }

                        @Override
                        public void OnCancel(AlertDialog dialog) {
                            cbCheckPymnt.setChecked(false);
                            dialog.dismiss();
                        }
                    });
                    loPayment.show();
                });
            }
        });

        cbRebate.setOnCheckedChangeListener((buttonView, isChecked) -> mViewModel.setIsRebated(isChecked));

        btnAmort.setOnClickListener(v -> txtAmount.setText(psMonthAmt));

        btnRBlnce.setOnClickListener(v -> txtAmount.setText(psAmntDue));

        btnClear.setOnClickListener(v -> {
            txtAmount.setText("");
            txtDiscount.setText("");
            txtOthers.setText("");
            txtTotAmnt.setText("");
        });

        btnConfirm.setOnClickListener(view -> {
            long time = SystemClock.elapsedRealtime() - mLastClickTime;
            if(time < 5000){
                Toast.makeText(requireContext(), "Please wait...", Toast.LENGTH_LONG).show();
            } else {
                mLastClickTime = SystemClock.elapsedRealtime();
                infoModel.setRemarksCode(Remarksx);
                infoModel.setPayment(String.valueOf(spnType.getSelectedItemPosition()));
                infoModel.setPrNoxxx(Objects.requireNonNull(txtPrNoxx.getText()).toString());
                infoModel.setRemarks(Objects.requireNonNull(txtRemarks.getText()).toString());
                infoModel.setAmountx(Objects.requireNonNull(txtAmount.getText()).toString());
                infoModel.setDscount(Objects.requireNonNull(txtDiscount.getText()).toString());
                infoModel.setOthersx(Objects.requireNonNull(txtOthers.getText()).toString());
                infoModel.setTotAmnt(Objects.requireNonNull(txtTotAmnt.getText()).toString());
                mViewModel.savePaidInfo(infoModel, Fragment_PaidTransaction.this);
            }
        });
    }

    @Override
    public void OnStartSaving() {
        poDialog.initDialog("Daily Collection Plan", "Posting transaction.Please wait...", false);
        poDialog.show();
    }

    @Override
    public void OnSuccessResult(String[] args) {
        poDialog.dismiss();
        poMessage.initDialog();
        poMessage.setTitle("Transaction Success");
        poMessage.setMessage(args[0]);
        poMessage.setPositiveButton("Okay", (view, dialog) -> {
            dialog.dismiss();
            requireActivity().finish();
        });
        poMessage.show();
    }

    @Override
    public void OnFailedResult(String message) {
        poDialog.dismiss();
        poMessage.initDialog();
        poMessage.setTitle("Daily Collection Plan");
        poMessage.setMessage(message);
        poMessage.setPositiveButton("Okay", (view, dialog) -> {
            dialog.dismiss();
            if(message.equalsIgnoreCase("Collection info has been save.")){
                requireActivity().finish();
            }
        });
        poMessage.show();
    }

    private class OnAmountEnterTextWatcher implements TextWatcher{
        private final TextInputEditText inputEditText;

        OnAmountEnterTextWatcher(TextInputEditText inputEditText){
            this.inputEditText = inputEditText;
        }


        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            try {
                inputEditText.removeTextChangedListener(this);
                if (inputEditText.getId() == R.id.txt_dcpAmount) {
                    if(!inputEditText.getText().toString().isEmpty()) {
                        mViewModel.setAmount(Double.valueOf(inputEditText.getText().toString().replace(",", "")));
                    } else {
                        mViewModel.setAmount((double) 0);
                    }
                } else if (inputEditText.getId() == R.id.txt_dcpOthers) {
                    if(!inputEditText.getText().toString().isEmpty()) {
                        mViewModel.setOthers(Double.valueOf(inputEditText.getText().toString().replace(",", "")));
                    } else {
                        mViewModel.setOthers((double) 0);
                    }
                }
                inputEditText.addTextChangedListener(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            FormatCurrency(inputEditText);
        }

        private void FormatCurrency(TextInputEditText txt){
            try
            {
                txt.removeTextChangedListener(this);
                String value = Objects.requireNonNull(txt.getText()).toString();

                if (!value.equals(""))
                {

                    if(value.startsWith(".")){
                        txt.setText("0.");
                    }
                    if(value.startsWith("0") && !value.startsWith("0.")){
                        txt.setText("");

                    }

                    String str = txt.getText().toString().replaceAll(",", "");
                    txt.setText(getDecimalFormattedString(str));
                    txt.setSelection(txt.getText().toString().length());
                }
                txt.addTextChangedListener(this);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                txt.addTextChangedListener(this);
            }

        }
    }

    private static String getDecimalFormattedString(String value)
    {
        StringTokenizer lst = new StringTokenizer(value, ".");
        String str1 = value;
        String str2 = "";
        if (lst.countTokens() > 1)
        {
            str1 = lst.nextToken();
            str2 = lst.nextToken();
        }
        StringBuilder str3 = new StringBuilder();
        int i = 0;
        int j = -1 + str1.length();
        if (str1.charAt( -1 + str1.length()) == '.')
        {
            j--;
            str3 = new StringBuilder(".");
        }
        for (int k = j;; k--)
        {
            if (k < 0)
            {
                if (str2.length() > 0)
                    str3.append(".").append(str2);
                return str3.toString();
            }
            if (i == 3)
            {
                str3.insert(0, ",");
                i = 0;
            }
            str3.insert(0, str1.charAt(k));
            i++;
        }

    }
}