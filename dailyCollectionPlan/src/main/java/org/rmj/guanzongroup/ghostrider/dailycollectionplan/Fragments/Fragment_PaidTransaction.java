package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Etc.FormatUIText;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities.Activity_Transaction;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model.PaidTransactionModel;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.VMPaidTransaction;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.ViewModelCallback;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.StringTokenizer;

public class Fragment_PaidTransaction extends Fragment implements ViewModelCallback {

    private VMPaidTransaction mViewModel;
    private PaidTransactionModel infoModel;
    private MessageBox poMessage;
    private LoadDialog poDialog;
    private TextView lblBranch, lblAddress, lblAccNo, lblClientNm, lblTransNo;

    private Spinner spnType;
    private TextInputEditText txtPrNoxx, txtRemarks, txtAmount, txtDiscount, txtOthers, txtTotAmnt;
    private MaterialButton btnConfirm;

    public static Fragment_PaidTransaction newInstance() {
        return new Fragment_PaidTransaction();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_paid_transaction, container, false);
        infoModel = new PaidTransactionModel();
        poMessage = new MessageBox(getContext());
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
        txtPrNoxx = v.findViewById(R.id.txt_dcpPRNumber);
        txtRemarks = v.findViewById(R.id.txt_dcpRemarks);
        txtAmount = v.findViewById(R.id.txt_dcpAmount);
        txtDiscount = v.findViewById(R.id.txt_dcpDiscount);
        txtOthers = v.findViewById(R.id.txt_dcpOthers);
        txtTotAmnt = v.findViewById(R.id.txt_dcpTotAmount);
        btnConfirm = v.findViewById(R.id.btn_confirm);

        //txtAmount.addTextChangedListener(new FormatUIText.CurrencyFormat(txtAmount));
        txtAmount.addTextChangedListener(new OnAmountEnterTextWatcher(txtAmount));
        //txtDiscount.addTextChangedListener(new FormatUIText.CurrencyFormat(txtDiscount));
        txtDiscount.addTextChangedListener(new OnAmountEnterTextWatcher(txtDiscount));
        //txtOthers.addTextChangedListener(new FormatUIText.CurrencyFormat(txtOthers));
        txtOthers.addTextChangedListener(new OnAmountEnterTextWatcher(txtOthers));
        txtTotAmnt.addTextChangedListener(new FormatUIText.CurrencyFormat(txtTotAmnt));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String Remarksx = Activity_Transaction.getInstance().getRemarksCode();
        String TransNox = Activity_Transaction.getInstance().getTransNox();
        String EntryNox = Activity_Transaction.getInstance().getEntryNox();
        mViewModel = new ViewModelProvider(this).get(VMPaidTransaction.class);
        mViewModel.setParameter(TransNox, EntryNox);
        mViewModel.getCollectionDetail().observe(getViewLifecycleOwner(), collectionDetail -> {
            try {
                lblAccNo.setText(collectionDetail.getAcctNmbr());
                lblClientNm.setText(collectionDetail.getFullName());
                lblTransNo.setText(collectionDetail.getTransNox());
                mViewModel.setCurrentCollectionDetail(collectionDetail);
            } catch (Exception


                    e){
                e.printStackTrace();
            }
        });

        mViewModel.getUserBranchEmployee().observe(getViewLifecycleOwner(), eBranchInfo -> {
            lblBranch.setText(eBranchInfo.getBranchNm());
            lblAddress.setText(eBranchInfo.getAddressx());
        });

        mViewModel.getPaymentType().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnType.setAdapter(stringArrayAdapter));

        mViewModel.getTotalAmount().observe(getViewLifecycleOwner(), aFloat -> txtTotAmnt.setText(String.valueOf(aFloat)));

        btnConfirm.setOnClickListener(view -> {
            infoModel.setRemarksCode(Remarksx);
            infoModel.setPayment(spnType.getSelectedItem().toString());
            infoModel.setPrNoxxx(Objects.requireNonNull(txtPrNoxx.getText()).toString());
            infoModel.setRemarks(Objects.requireNonNull(txtRemarks.getText()).toString());
            infoModel.setAmountx(Objects.requireNonNull(txtAmount.getText()).toString());
            infoModel.setDscount(Objects.requireNonNull(txtDiscount.getText()).toString());
            infoModel.setOthersx(Objects.requireNonNull(txtOthers.getText()).toString());
            infoModel.setTotAmnt(Objects.requireNonNull(txtTotAmnt.getText()).toString());
            mViewModel.savePaidInfo(infoModel, Fragment_PaidTransaction.this);
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
        poMessage.setTitle("Transaction Success");
        poMessage.setMessage(args[0]);
        poMessage.setPositiveButton("Okay", (view, dialog) -> {
            dialog.dismiss();
            Objects.requireNonNull(getActivity()).finish();
        });
        poMessage.show();
    }

    @Override
    public void OnFailedResult(String message) {
        poDialog.dismiss();
        poMessage.setTitle("Transaction Failed");
        poMessage.setMessage(message);
        poMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
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
                if(!inputEditText.getText().toString().trim().isEmpty()) {
                    if (inputEditText.getId() == R.id.txt_dcpAmount) {
                        mViewModel.setAmount(Double.valueOf(inputEditText.getText().toString().replace(",", "")));
                    } else if (inputEditText.getId() == R.id.txt_dcpDiscount) {
                        mViewModel.setDiscount(Double.valueOf(inputEditText.getText().toString().replace(",", "")));
                    } else if (inputEditText.getId() == R.id.txt_dcpOthers) {
                        mViewModel.setOthers(Double.valueOf(inputEditText.getText().toString().replace(",", "")));
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
                String value = txt.getText().toString();

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