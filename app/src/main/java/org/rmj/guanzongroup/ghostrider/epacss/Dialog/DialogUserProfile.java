package org.rmj.guanzongroup.ghostrider.epacss.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import org.rmj.guanzongroup.ghostrider.epacss.R;

public class DialogUserProfile {

    private ImageView imgProfile;
    private TextView lblUserNm, lblEmailx, lblPstion, lblMobile, lblAddrss, lblDate;
    private final AlertDialog poDialog;
    public DialogUserProfile(Context context){
        AlertDialog.Builder poBuilder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_user_profile, null);
        poBuilder.setCancelable(false)
                .setView(view);
        poDialog = poBuilder.create();
        poDialog.setCancelable(false);

        lblAddrss = view.findViewById(R.id.dialog_lbl_location);
        lblUserNm = view.findViewById(R.id.dialog_lbl_employeeName);
        lblEmailx = view.findViewById(R.id.dialog_lbl_employeeEmail);
        lblPstion = view.findViewById(R.id.dialog_lbl_employeePosition);
        lblMobile = view.findViewById(R.id.dialog_lbl_employeeMobileNo);
        //lblDate = view.findViewById(R.id.dialog_lbl_date);
        MaterialButton btnClose = view.findViewById(R.id.btn_dialogClose);
        btnClose.setOnClickListener(view1 -> poDialog.dismiss());
    }

    public void setAddress(String Address){
        lblAddrss.setText(Address);
    }

    public void setEmpName(String Address){
        lblUserNm.setText(Address);
    }

    public void setEmpContctNo(String Address){
        lblMobile.setText(Address);
    }

    public void setEmpEmail(String Address){
        lblEmailx.setText(Address);
    }

    public void setEmpPosition(String Address){
        lblPstion.setText(Address);
    }

    /*public void setLblDate(String Address){
        lblDate.setText(Address);
    }*/

    public void show(){
        if(!poDialog.isShowing()){
            poDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            poDialog.getWindow().getAttributes().windowAnimations = org.rmj.g3appdriver.R.style.PopupAnimation;
            poDialog.show();
        }
    }
}
