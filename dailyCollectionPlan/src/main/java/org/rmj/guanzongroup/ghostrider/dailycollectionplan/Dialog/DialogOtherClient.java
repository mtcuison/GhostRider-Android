package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;

public class DialogOtherClient {
    private static final String TAG = DialogOtherClient.class.getSimpleName();

    private AlertDialog poDialogx;
    private final Context context;

    public DialogOtherClient(Context context) {
        this.context = context;
    }

    public void initDialog(TYPE dialogType, OnDialogButtonClickListener listener){
        AlertDialog.Builder loBuilder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_get_client, null, false);
        loBuilder.setCancelable(false)
                .setView(view);
        poDialogx = loBuilder.create();
        poDialogx.setCancelable(false);

        TextInputLayout tilParms = view.findViewById(R.id.til_dcpParameter);
        TextInputEditText txtAccntNo = view.findViewById(R.id.txt_dcpParameter);
        Button btnDownLoad = view.findViewById(R.id.btn_dcpDownload);
        Button btnCancel = view.findViewById(R.id.btn_cancel);

        if(dialogType == TYPE.ACCOUNT_RECIEVABLE){
            tilParms.setHint("Account Number");
        } else {
            tilParms.setHint("Serial Number");
        }

        btnDownLoad.setOnClickListener(view1 -> listener.OnDownloadClick(poDialogx, txtAccntNo.getText().toString()));

        btnCancel.setOnClickListener(view12 -> listener.OnCancel(poDialogx));
    }

    public void show(){
        if(!poDialogx.isShowing()){
            poDialogx.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            poDialogx.getWindow().getAttributes().windowAnimations = org.rmj.g3appdriver.R.style.PopupAnimation;
            poDialogx.show();
        }
    }

    public interface OnDialogButtonClickListener{
        void OnDownloadClick(Dialog Dialog, String args);
        void OnCancel(Dialog Dialog);
    }

    public enum TYPE{
        ACCOUNT_RECIEVABLE,
        INSURANCE
    }
}
