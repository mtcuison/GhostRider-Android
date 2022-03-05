/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.CreditEvaluator
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.guanzongroup.com.creditevaluation.Dialog;

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

import org.guanzongroup.com.creditevaluation.R;
import org.rmj.g3appdriver.GRider.Etc.GToast;

public class DialogAddApplication {
    private static final String TAG = DialogAddApplication.class.getSimpleName();

    private AlertDialog poDialogx;
    private final Context context;
    private TextInputLayout tilAddApp;

    public DialogAddApplication(Context context) {
        this.context = context;
    }

    public void initDialog(OnDialogButtonClickListener listener){
        AlertDialog.Builder loBuilder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_add_application, null, false);
        loBuilder.setCancelable(false)
                .setView(view);
        poDialogx = loBuilder.create();
        poDialogx.setCancelable(false);

        tilAddApp = view.findViewById(R.id.til_add_application);
        TextInputEditText txtTransN = view.findViewById(R.id.tie_add_application);

        Button btnDownLoad = view.findViewById(R.id.btn_confirm);
        Button btnCancel = view.findViewById(R.id.btn_cancel);

        btnDownLoad.setOnClickListener(view1 -> {
            if(txtTransN.getText().toString().trim().isEmpty()) {
                GToast.CreateMessage(context, "Please enter transaction number.", GToast.WARNING).show();
            } else {
                listener.OnDownloadClick(poDialogx, txtTransN.getText().toString());
            }
        });

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
}
