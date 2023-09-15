package org.rmj.guanzongroup.ghostrider.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import org.rmj.guanzongroup.ghostrider.R;

public class DialogReward {
    public DialogReward(Context context){
        AlertDialog.Builder poBuilder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_reward, null);
        poBuilder.setCancelable(false)
                .setView(view);
//        poDialog = poBuilder.create();
//        poDialog.setCancelable(false);
//
//        lblAddress = view.findViewById(R.id.lbl_dialogLocation);
//        MaterialButton btnClose = view.findViewById(R.id.btn_dialogClose);
//        btnClose.setO nClickListener(view1 -> poDialog.dismiss());
    }
}