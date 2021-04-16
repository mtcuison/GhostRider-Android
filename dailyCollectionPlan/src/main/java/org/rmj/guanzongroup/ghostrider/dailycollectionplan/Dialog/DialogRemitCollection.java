package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.rmj.g3appdriver.GRider.Database.Entities.EBankInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCP_Remittance;
import org.rmj.g3appdriver.GRider.Etc.FormatUIText;
import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DialogRemitCollection {
    private static final String TAG = DialogRemitCollection.class.getSimpleName();
    private AlertDialog poDialogx;
    private final Context context;
    private final EDCP_Remittance poRemit;

    private List<EBankInfo> poBankList = new ArrayList<>();
    private List<EBranchInfo> poBrnchList = new ArrayList<>();

    private String psCltCheck = "";
    private String psCltCashx = "";

    public interface RemitDialogListener{
        void OnConfirm(AlertDialog dialog, EDCP_Remittance remittance);
        void OnCancel(AlertDialog dialog);
    }

    private AutoCompleteTextView txtBranch, txtBankNm;
    private TextInputEditText txtAmount, txtAccNo, txtRefNox, txtPaymPnr;
    private LinearLayout linearBrnch, linearBank, linearPaym;
    private TextInputLayout tilRefNox;

    public DialogRemitCollection(Context context) {
        this.context = context;
        this.poRemit = new EDCP_Remittance();
        this.poRemit.setRemitTyp("0");
        this.poRemit.setPaymForm("0");
    }

    public void setPoBankList(List<EBankInfo> poBankList) {
        this.poBankList = poBankList;

    }

    public void setPoBrnchList(List<EBranchInfo> poBrnchList) {
        this.poBrnchList = poBrnchList;
    }

    public void setPsCltCheck(String psCltCheck) {
        this.psCltCheck = psCltCheck;
    }

    public void setPsCltCashx(String psCltCashx) {
        this.psCltCashx = psCltCashx;
    }

    public void initDialog(RemitDialogListener listener){
        AlertDialog.Builder loBuilder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_remit_collection, null, false);
        loBuilder.setCancelable(false)
                .setView(view);
        poDialogx = loBuilder.create();
        poDialogx.setCancelable(false);

        RadioGroup rgRemitType = view.findViewById(R.id.rg_remittance);
        RadioGroup rgRemitance = view.findViewById(R.id.rg_remittanceOutlet);
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
        Button btnConfirm = view.findViewById(R.id.btn_confirm);
        Button btnCancel = view.findViewById(R.id.btn_cancel);

        String[] laBranchNm = new String[poBrnchList.size()];
        for(int x = 0; x < poBrnchList.size(); x++){
            laBranchNm[x] = poBrnchList.get(x).getBranchNm();
        }

        String[] laBankName = new String[poBankList.size()];
        for(int x = 0; x < poBankList.size(); x++){
            laBankName[x] = poBankList.get(x).getBankName();
        }

        txtBranch.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, laBranchNm));
        txtBankNm.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, laBankName));

        txtBranch.setOnItemClickListener((parent, view1, position, id) -> poRemit.setCompnyNm(txtBranch.getText().toString()));
        txtBankNm.setOnItemClickListener((parent, view12, position, id) -> poRemit.setCompnyNm(txtBankNm.getText().toString()));

        txtAmount.addTextChangedListener(new FormatUIText.CurrencyFormat(txtAmount));

        txtAmount.setText(psCltCashx);
        rgRemitType.setOnCheckedChangeListener((group, checkedId) -> {
            if(checkedId == R.id.rb_remitCash){
                poRemit.setPaymForm("0");
                txtAmount.setText(psCltCashx);
            } else if(checkedId == R.id.rb_remitCheck){
                poRemit.setPaymForm("1");
                txtAmount.setText(psCltCheck);
            }
        });

        rgRemitance.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_remitBranch) {
                linearBrnch.setVisibility(View.VISIBLE);
                linearBank.setVisibility(View.GONE);
                linearPaym.setVisibility(View.GONE);
                tilRefNox.setVisibility(View.GONE);
                poRemit.setRemitTyp("0");
            } else if(checkedId == R.id.rb_remitBank){
                linearBrnch.setVisibility(View.GONE);
                linearBank.setVisibility(View.VISIBLE);
                linearPaym.setVisibility(View.GONE);
                tilRefNox.setVisibility(View.VISIBLE);
                poRemit.setRemitTyp("1");
            } else{
                linearBrnch.setVisibility(View.GONE);
                linearBank.setVisibility(View.GONE);
                linearPaym.setVisibility(View.VISIBLE);
                tilRefNox.setVisibility(View.VISIBLE);
                poRemit.setRemitTyp("2");
            }
        });

        btnConfirm.setOnClickListener(v -> {
            if(isDataValid()) {
                switch (poRemit.getRemitTyp()) {
                    case "0":
                        poRemit.setAmountxx(Objects.requireNonNull(txtAmount.getText()).toString());
                        poRemit.setAmountxx(Objects.requireNonNull(txtAmount.getText()).toString());
                        break;
                    case "1":
                        poRemit.setCompnyNm(Objects.requireNonNull(txtBankNm.getText()).toString());
                        poRemit.setBankAcct(Objects.requireNonNull(txtAccNo.getText()).toString());
                        poRemit.setReferNox(Objects.requireNonNull(txtRefNox.getText()).toString());
                        poRemit.setAmountxx(Objects.requireNonNull(txtAmount.getText()).toString());
                        break;
                    case "2":
                        poRemit.setCompnyNm(Objects.requireNonNull(txtPaymPnr.getText()).toString());
                        poRemit.setBankAcct(Objects.requireNonNull(txtRefNox.getText()).toString());
                        poRemit.setAmountxx(Objects.requireNonNull(txtAmount.getText()).toString());
                        break;
                }
                listener.OnConfirm(poDialogx, poRemit);
            }
        });

        btnCancel.setOnClickListener(v -> listener.OnCancel(poDialogx));
    }

    public void show(){
        if(!poDialogx.isShowing()){
            poDialogx.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            poDialogx.getWindow().getAttributes().windowAnimations = org.rmj.g3appdriver.R.style.PopupAnimation;
            poDialogx.show();
        }
    }

    private boolean isDataValid(){
        if(poRemit.getRemitTyp().equalsIgnoreCase("0")){
            if(txtBranch.getText().toString().isEmpty()){
                GToast.CreateMessage(context, "Please enter branch", GToast.ERROR).show();
                return false;
            } else if(Objects.requireNonNull(txtAmount.getText()).toString().isEmpty()){
                GToast.CreateMessage(context, "Please enter amount", GToast.ERROR).show();
                return false;
            }
        } else if(poRemit.getRemitTyp().equalsIgnoreCase("1")){
            if(txtBankNm.getText().toString().isEmpty()){
                GToast.CreateMessage(context, "Please enter bank", GToast.ERROR).show();
                return false;
            } else if(Objects.requireNonNull(txtAccNo.getText()).toString().isEmpty()){
                GToast.CreateMessage(context, "Please enter account no", GToast.ERROR).show();
                return false;
            } else if(Objects.requireNonNull(txtRefNox.getText()).toString().isEmpty()){
                GToast.CreateMessage(context, "Please enter reference no", GToast.ERROR).show();
                return false;
            } else if(Objects.requireNonNull(txtAmount.getText()).toString().isEmpty()){
                GToast.CreateMessage(context, "Please enter amount", GToast.ERROR).show();
                return false;
            }
        } else {
            if(Objects.requireNonNull(txtPaymPnr.getText()).toString().isEmpty()){
                GToast.CreateMessage(context, "Please enter payment partner", GToast.ERROR).show();
                return false;
            } else if(Objects.requireNonNull(txtRefNox.getText()).toString().isEmpty()){
                GToast.CreateMessage(context, "Please enter reference no", GToast.ERROR).show();
                return false;
            } else if(Objects.requireNonNull(txtAmount.getText()).toString().isEmpty()){
                GToast.CreateMessage(context, "Please enter amount", GToast.ERROR).show();
                return false;
            }
        }
        return true;
    }
}
