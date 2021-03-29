package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.g3appdriver.etc.OnDateSetListener;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;

import java.util.Objects;

public class DialogCheckPayment {
    private static final String TAG = DialogCheckPayment.class.getSimpleName();

    private AlertDialog poDialogx;
    private final Context context;

    private String lsBank;

    public interface OnCheckPaymentDialogListener{
        void OnConfirm(AlertDialog dialog, String bank, String date, String checkNo, String AcctNo);
        void OnCancel(AlertDialog dialog);
    }

    public DialogCheckPayment(Context context) {
        this.context = context;
    }

    public void initDialog(String[] bankList, OnCheckPaymentDialogListener listener){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_check_payment, null, false);
        AlertDialog.Builder loBuilder = new AlertDialog.Builder(context);
        loBuilder.setView(view)
                .setCancelable(false);
        poDialogx = loBuilder.create();
        poDialogx.setCancelable(false);

        AutoCompleteTextView txtBankNme = view.findViewById(R.id.txt_dcpBankName);
        TextInputEditText txtCheckDt = view.findViewById(R.id.txt_dcpCheckDate);
        TextInputEditText txtCheckNo = view.findViewById(R.id.txt_dcpCheckNo);
        TextInputEditText txtAccntNo = view.findViewById(R.id.txt_dcpAcctNumber);

        Button btnConfirm = view.findViewById(R.id.btn_dcpConfirm);
        Button btnCancel = view.findViewById(R.id.btn_cancel);

        ArrayAdapter<String> loAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, bankList);
        txtBankNme.setAdapter(loAdapter);

        txtBankNme.setOnItemClickListener((parent, view1, position, id) -> lsBank = txtBankNme.getText().toString());

        txtCheckDt.addTextChangedListener(new OnDateSetListener(txtCheckDt));

        btnConfirm.setOnClickListener(v -> {
            String lsCheckDt = Objects.requireNonNull(txtCheckDt.getText()).toString();
            String lsCheckNo = Objects.requireNonNull(txtCheckNo.getText()).toString();
            String lsAccntNo = Objects.requireNonNull(txtAccntNo.getText()).toString();
            if(lsBank.trim().isEmpty()){
                GToast.CreateMessage(context, "Please enter bank name", GToast.ERROR).show();
            } else if(lsCheckDt.trim().isEmpty()){
                GToast.CreateMessage(context, "Please enter check date", GToast.ERROR).show();
            } else if(lsCheckNo.trim().isEmpty()){
                GToast.CreateMessage(context, "Please enter check no.", GToast.ERROR).show();
            } else if(lsAccntNo.trim().isEmpty()){
                GToast.CreateMessage(context, "Please enter check account no.", GToast.ERROR).show();
            } else {
                listener.OnConfirm(poDialogx, lsBank, lsCheckDt, lsCheckNo, lsAccntNo);
            }
        });

        btnCancel.setOnClickListener(v -> listener.OnCancel(poDialogx));
    }

    public void show(){
        poDialogx.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        poDialogx.getWindow().getAttributes().windowAnimations = org.rmj.g3appdriver.R.style.PopupAnimation;
        poDialogx.show();
    }
}
