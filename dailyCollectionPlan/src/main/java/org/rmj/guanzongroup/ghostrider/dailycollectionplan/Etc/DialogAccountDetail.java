package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Etc;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;

public class DialogAccountDetail {
    private static final String TAG = DialogAccountDetail.class.getSimpleName();

    private AlertDialog poDialogx;
    private final Context context;

    public DialogAccountDetail(Context context){
        this.context = context;
    }

    public void initAccountDetail(DialogButtonClickListener listener){
        AlertDialog.Builder loBuilder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_account_detail, null, false);
        loBuilder.setCancelable(false)
                .setView(view);
        poDialogx = loBuilder.create();
        poDialogx.setCancelable(false);

        TextView lblReferNo = view.findViewById(R.id.lbl_dcpReferNo);
        TextView lblTransNo = view.findViewById(R.id.lbl_dcpTransNo);
        TextView lblTranDte = view.findViewById(R.id.lbl_dcpTranDate);
        TextView lblAccntNo = view.findViewById(R.id.lbl_dcpAccNo);
        TextView lblPRNoxxx = view.findViewById(R.id.lbl_dcpPRNo);
        TextView lblAmountx = view.findViewById(R.id.lbl_dcpAmount);
        TextView lblDscount = view.findViewById(R.id.lbl_dcpDiscount);
        TextView lblOthersx = view.findViewById(R.id.lbl_dcpOthers);
        Spinner spnTransact = view.findViewById(R.id.spn_transaction);
        Button btnConfirm = view.findViewById(R.id.btn_confirm);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnClick();
            }
        });
    }

    public void show(){
        poDialogx.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        poDialogx.show();
    }

    public void dismiss(){
        if(poDialogx != null && poDialogx.isShowing()){
            poDialogx.dismiss();
        }
    }

    public interface DialogButtonClickListener{
        void OnClick();
    }
}
