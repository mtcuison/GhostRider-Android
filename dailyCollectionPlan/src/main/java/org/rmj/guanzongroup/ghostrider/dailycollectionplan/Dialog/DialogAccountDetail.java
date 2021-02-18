package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Etc.FormatUIText;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Etc.DCP_Constants;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;

public class DialogAccountDetail {
    private static final String TAG = DialogAccountDetail.class.getSimpleName();

    private AlertDialog poDialogx;
    private final Context context;

    public DialogAccountDetail(Context context){
        this.context = context;
    }

    public void initAccountDetail(EDCPCollectionDetail foDetail, DialogButtonClickListener listener){
        AlertDialog.Builder loBuilder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_account_detail, null, false);
        loBuilder.setCancelable(false)
                .setView(view);
        poDialogx = loBuilder.create();
        poDialogx.setCancelable(false);

        TextView lblReferNo = view.findViewById(R.id.lbl_dcpReferNo);
        TextView lblTransNo = view.findViewById(R.id.lbl_dcpTransNo);
//        TextView lblTranDte = view.findViewById(R.id.lbl_dcpTranDate);
        TextView lblAccntNo = view.findViewById(R.id.lbl_dcpAccNo);
        TextView lblSerialx = view.findViewById(R.id.lbl_dcpPRNo);
        TextView lblAmountx = view.findViewById(R.id.lbl_dcpAmount);
        TextView lblDueDate = view.findViewById(R.id.lbl_dcpDueDate);
//        TextView lblOthersx = view.findViewById(R.id.lbl_dcpOthers);
        Spinner spnTransact = view.findViewById(R.id.spn_transaction);
        Button btnConfirm = view.findViewById(R.id.btn_confirm);
        Button btnCancelx = view.findViewById(R.id.btn_cancel);

        lblReferNo.setText(foDetail.getReferNox());
        lblTransNo.setText(foDetail.getTransNox());
        lblAccntNo.setText(foDetail.getAcctNmbr());
        lblSerialx.setText(foDetail.getSerialNo());
        lblAmountx.setText(FormatUIText.getCurrencyUIFormat(foDetail.getAmtDuexx()));
        lblDueDate.setText(FormatUIText.formatGOCasBirthdate(foDetail.getDueDatex()));

        spnTransact.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, DCP_Constants.TRANSACTION_TYPE));

        btnConfirm.setOnClickListener(view1 -> listener.OnClick(poDialogx, spnTransact.getSelectedItem().toString()));
        btnCancelx.setOnClickListener(view12 -> dismiss());
    }

    public void show(){
        poDialogx.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        poDialogx.getWindow().getAttributes().windowAnimations = org.rmj.g3appdriver.R.style.PopupAnimation;
        poDialogx.show();
    }

    public void dismiss(){
        if(poDialogx != null && poDialogx.isShowing()){
            poDialogx.dismiss();
        }
    }

    public interface DialogButtonClickListener{
        void OnClick(Dialog dialog, String remarksCode);
    }
}
