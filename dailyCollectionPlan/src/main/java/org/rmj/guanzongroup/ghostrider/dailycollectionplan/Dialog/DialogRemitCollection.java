package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
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

    private String psTotCash;
    private String psTotChck;
    private String psRmtBrnh;
    private String psRmtBank;
    private String psRmtOthr;
    private String psCashOHx;
    private String psCheckOH;

    public void setCollectedCash(String psTotCash) {
        this.psTotCash = psTotCash;
    }

    public void setCollectedCheck(String psTotChck) {
        this.psTotChck = psTotChck;
    }

    public void setRemittedBranch(String psRmtBrnh) {
        this.psRmtBrnh = psRmtBrnh;
    }

    public void setRemittedBank(String psRmtBank) {
        this.psRmtBank = psRmtBank;
    }

    public void setRemittedOthers(String psRmtOthr) {
        this.psRmtOthr = psRmtOthr;
    }

    public void setCashOnHand(String psCashOHx) {
        this.psCashOHx = psCashOHx;
    }

    public void setCheckOnHand(String psCheckOH) {
        this.psCheckOH = psCheckOH;
    }

    public interface RemitDialogListener{
        void OnRemit(AlertDialog dialog);
        void OnCancel(AlertDialog dialog);
    }

    public DialogRemitCollection(Context context) {
        this.context = context;
        this.poRemit = new EDCP_Remittance();
        this.poRemit.setRemitTyp("0");
        this.poRemit.setPaymForm("0");
    }

    @SuppressLint("SetTextI18n")
    public void initDialog(RemitDialogListener listener){
        AlertDialog.Builder loBuilder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_remit_collection, null, false);
        loBuilder.setCancelable(false)
                .setView(view);
        poDialogx = loBuilder.create();
        poDialogx.setCancelable(false);

        TextView lblCltdCash = view.findViewById(R.id.lbl_collectedCash);
        TextView lblCltdChck = view.findViewById(R.id.lbl_collectedCheck);
        TextView lblRmtBrnch = view.findViewById(R.id.lbl_remittanceBranch);
        TextView lblRmtBankx = view.findViewById(R.id.lbl_remittanceBank);
        TextView lblRmtOther = view.findViewById(R.id.lbl_remittanceOthers);
        TextView lblOHCashxx = view.findViewById(R.id.lbl_remittanceCash);
        TextView lblOHCheckx = view.findViewById(R.id.lbl_remittanceCheck);
        Button btnRemit = view.findViewById(R.id.btn_confirm);
        Button btnCancel = view.findViewById(R.id.btn_cancel);

        lblCltdCash.setText("Total Collected Cash : " + FormatUIText.getCurrencyUIFormat(psTotCash));
        lblCltdChck.setText("Total Collected Check : " + FormatUIText.getCurrencyUIFormat(psTotChck));
        lblRmtBrnch.setText("Remitted on Branch : " + FormatUIText.getCurrencyUIFormat(psRmtBrnh));
        lblRmtBankx.setText("Remitted on Bank : " + FormatUIText.getCurrencyUIFormat(psRmtBank));
        lblRmtOther.setText("Remitted on Payment Partner : " + FormatUIText.getCurrencyUIFormat(psRmtOthr));
        lblOHCashxx.setText("Cash-On-Hand : " + FormatUIText.getCurrencyUIFormat(psCashOHx));
        lblOHCheckx.setText("Check-On-Hand : " + FormatUIText.getCurrencyUIFormat(psCheckOH));

        btnRemit.setOnClickListener(v -> listener.OnRemit(poDialogx));

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
