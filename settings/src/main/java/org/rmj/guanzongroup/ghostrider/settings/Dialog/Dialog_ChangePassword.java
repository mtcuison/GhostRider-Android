/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.settings
 * Electronic Personnel Access Control Security System
 * project file created : 5/26/21 4:29 PM
 * project file last modified : 5/26/21 4:29 PM
 */

package org.rmj.guanzongroup.ghostrider.settings.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.etc.GToast;
import org.rmj.guanzongroup.ghostrider.settings.R;

public class Dialog_ChangePassword {

    private final AlertDialog poDialogx;

    private final TextInputEditText txtOldPass;
    private final TextInputEditText txtNewPass;
    private final TextInputEditText txtCfmPass;

    private String message;

    public interface ChangePasswordDialogCallback{
        void OnConfirm(String OldPassword, String NewPassword, AlertDialog dialog);
    }

    public Dialog_ChangePassword(Context context, ChangePasswordDialogCallback callback) {
        AlertDialog.Builder loBuilder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_change_password, null, false);
        loBuilder.setCancelable(false)
                .setView(view);
        poDialogx = loBuilder.create();
        poDialogx.setCancelable(false);

        txtOldPass = view.findViewById(R.id.txt_oldPassword);
        txtNewPass = view.findViewById(R.id.txt_newPassword);
        txtCfmPass = view.findViewById(R.id.txt_ConfirmNew);

        Button btnConfirm = view.findViewById(R.id.btn_dialog_confirm);
        Button btnCancel = view.findViewById(R.id.btn_dialog_cancel);

        btnConfirm.setOnClickListener(v -> {
            if(isDataValid()) {
                String lsOld = txtOldPass.getText().toString();
                String lsNew = txtNewPass.getText().toString();
                callback.OnConfirm(lsOld, lsNew, poDialogx);
                poDialogx.dismiss();
            } else {
                GToast.CreateMessage(context, message, GToast.ERROR).show();
            }
        });

        btnCancel.setOnClickListener(v -> poDialogx.dismiss());
    }

    public void show(){
        if(!poDialogx.isShowing()){
            poDialogx.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            poDialogx.getWindow().getAttributes().windowAnimations = org.rmj.g3appdriver.R.style.PopupAnimation;
            poDialogx.show();
        }
    }

    private boolean isDataValid(){
        if(txtOldPass.getText().toString().isEmpty()){
            message = "Please enter old password";
            return false;
        }
        if(txtNewPass.getText().toString().isEmpty()){
            message = "Please enter new password";
            return false;
        }
        if(txtCfmPass.getText().toString().isEmpty()){
            message = "Please confirm new password";
            return false;
        }
        if(!txtNewPass.getText().toString().equalsIgnoreCase(txtCfmPass.getText().toString())){
            message = "New password does not match";
            return false;
        }
        if(txtNewPass.getText().toString().length() < 8){
            message = "Please enter atleast 8 characters password";
            return false;
        }
        if(txtNewPass.getText().toString().length() > 15){
            message = "Password is too long. Must be less than 15 characters";
            return false;
        }
        return true;
    }
}
