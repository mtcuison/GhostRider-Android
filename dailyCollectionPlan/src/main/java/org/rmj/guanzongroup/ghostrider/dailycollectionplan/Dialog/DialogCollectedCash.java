package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;

public class DialogCollectedCash {
    private static final String TAG = DialogCollectedCash.class.getSimpleName();
    private AlertDialog poDialogx;
    private final Context context;

    private TextView lblCltdCash,
                    lblCltdChck,
                    lblRmtBrnch,
                    lblRmtBankx,
                    lblRmtOther,
                    lblOHCashxx,
                    lblOHCheckx;

    private Button btnOkay;

    public DialogCollectedCash(Context context) {
        this.context = context;
    }

    public void initDialog() {
        AlertDialog.Builder loBuilder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_collection_cash_info, null, false);
        loBuilder.setCancelable(false)
                .setView(view);
        poDialogx = loBuilder.create();
        poDialogx.setCancelable(false);

        lblCltdCash = view.findViewById(R.id.lbl_collectedCash);
        lblCltdChck = view.findViewById(R.id.lbl_collectedCheck);
        lblRmtBrnch = view.findViewById(R.id.lbl_remittanceBranch);
        lblRmtBankx = view.findViewById(R.id.lbl_remittanceBank);
        lblRmtOther = view.findViewById(R.id.lbl_remittanceOthers);
        lblOHCashxx = view.findViewById(R.id.lbl_remittanceCash);
        lblOHCheckx = view.findViewById(R.id.lbl_remittanceCheck);
        btnOkay = view.findViewById(R.id.btn_confirm);

        lblCltdCash.setText("Cash :" + "10,000");
        lblCltdChck.setText("Check :" + "10,000");
        lblRmtBrnch.setText("Branch :" + "10,000");
        lblRmtBankx.setText("Bank :" + "10,000");
        lblRmtOther.setText("Others :" + "10,000");
        lblOHCashxx.setText("Cash :" + "10,000");
        lblOHCheckx.setText("Check :" + "10,000");
        btnOkay.setOnClickListener(v -> poDialogx.dismiss());
    }

    public void show(){
        if(!poDialogx.isShowing()){
            poDialogx.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            poDialogx.getWindow().getAttributes().windowAnimations = org.rmj.g3appdriver.R.style.PopupAnimation;
            poDialogx.show();
        }
    }
}
