package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;

public class DialogRemitCollection {
    private static final String TAG = DialogRemitCollection.class.getSimpleName();
    private AlertDialog poDialogx;
    private final Context context;
    public interface RemitDialogListener{
        void OnConfirm(AlertDialog dialog);
        void OnCancel(AlertDialog dialog);
    }

    private RadioGroup rgRemitance;
    private AutoCompleteTextView txtBranch, txtBankNm;
    private TextInputEditText txtAmount, txtAccNo, txtRefNox, txtPaymPnr;
    private LinearLayout linearBrnch, linearBank, linearPaym;
    private TextInputLayout tilRefNox;
    private Button btnConfirm, btnCancel;

    public DialogRemitCollection(Context context) {
        this.context = context;
    }

    public void initDialog(RemitDialogListener listener){
        AlertDialog.Builder loBuilder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_remit_collection, null, false);
        loBuilder.setCancelable(false)
                .setView(view);
        poDialogx = loBuilder.create();
        poDialogx.setCancelable(false);

        rgRemitance = view.findViewById(R.id.rg_remittanceOutlet);
        txtBranch = view.findViewById(R.id.txt_remitBranch);
        txtBankNm = view.findViewById(R.id.txt_remitBankNme);
        txtAmount = view.findViewById(R.id.txt_remitAmount);
        txtAccNo = view.findViewById(R.id.txt_remitAccNo);
        txtRefNox = view.findViewById(R.id.txt_remitReferenceNo);
        txtPaymPnr = view.findViewById(R.id.txt_remitPaymPartner);
        tilRefNox = view.findViewById(R.id.til_remitReferenceNo);
        linearBrnch = view.findViewById(R.id.linear_remitBranch);
        linearBank = view.findViewById(R.id.linear_remitBank);
        linearPaym = view.findViewById(R.id.linear_remitPartner);
        btnConfirm = view.findViewById(R.id.btn_confirm);
        btnCancel = view.findViewById(R.id.btn_cancel);



        rgRemitance.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_remitBranch) {
                linearBrnch.setVisibility(View.VISIBLE);
                linearBank.setVisibility(View.GONE);
                linearPaym.setVisibility(View.GONE);
                tilRefNox.setVisibility(View.GONE);
            } else if(checkedId == R.id.rb_remitBank){
                linearBrnch.setVisibility(View.GONE);
                linearBank.setVisibility(View.VISIBLE);
                linearPaym.setVisibility(View.GONE);
                tilRefNox.setVisibility(View.VISIBLE);
            } else{
                linearBrnch.setVisibility(View.GONE);
                linearBank.setVisibility(View.GONE);
                linearPaym.setVisibility(View.VISIBLE);
                tilRefNox.setVisibility(View.VISIBLE);
            }
        });


        btnConfirm.setOnClickListener(v -> listener.OnConfirm(poDialogx));

        btnCancel.setOnClickListener(v -> listener.OnCancel(poDialogx));
    }

    public void show(){
        if(!poDialogx.isShowing()){
            poDialogx.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            poDialogx.getWindow().getAttributes().windowAnimations = org.rmj.g3appdriver.R.style.PopupAnimation;
            poDialogx.show();
        }
    }
}
