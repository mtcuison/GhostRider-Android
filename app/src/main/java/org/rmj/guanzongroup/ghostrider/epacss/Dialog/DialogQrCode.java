package org.rmj.guanzongroup.ghostrider.epacss.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import org.rmj.guanzongroup.ghostrider.epacss.R;

public class DialogQrCode {

    private final AlertDialog poDialog;
    private final TextView lblAddress;

    public DialogQrCode(Context context){
        AlertDialog.Builder poBuilder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_qr_code, null);
        poBuilder.setCancelable(false)
                .setView(view);
        poDialog = poBuilder.create();
        poDialog.setCancelable(false);

        lblAddress = view.findViewById(R.id.lbl_dialogLocation);
        MaterialButton btnClose = view.findViewById(R.id.btn_dialogClose);
        btnClose.setOnClickListener(view1 -> poDialog.dismiss());
    }

    public void setAddress(String Address){
        lblAddress.setText(Address);
    }

    public void show(){
        poDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        poDialog.show();
    }
}
