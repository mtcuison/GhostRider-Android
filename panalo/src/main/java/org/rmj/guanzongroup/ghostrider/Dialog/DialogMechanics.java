package org.rmj.guanzongroup.ghostrider.Dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import org.rmj.guanzongroup.ghostrider.R;

public class DialogMechanics {
    private final AlertDialog poDialog;
    private MaterialTextView dialogMsg;
    private MaterialButton btnClose;
    @SuppressLint("MissingInflatedId")
    public DialogMechanics(Context context){
        AlertDialog.Builder poBuilder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_mechanics, null);
        poBuilder.setCancelable(false)
                .setView(view);
        poDialog = poBuilder.create();
        poDialog.setCancelable(false);
        btnClose = view.findViewById(R.id.btn_dialogClose);
        dialogMsg = view.findViewById(R.id.lbl_dialogMessage);

        btnClose.setOnClickListener(view1 -> poDialog.dismiss());
        dialogMsg.setText("DRAW MECHANICS \n" +
                " \n" +
                "1.\tDraw will be every Monday of the week. First draw will be on  o June 26/June 28/ July 3 \n" +
                "2.\tThe draw will be done electronically c/o MIS \n" +
                "3.\tMs. Aprilyn Garcia with the assistance of MIS will facilitate the draw every Monday. In case of her absence, she will assign someone on her behalf \n" +
                "\n" +
                "DRAW PROCEDURE \n" +
                " \n" +
                "a. Every Monday, MIS will prepare three (3) initial sets of possible winners. One (1) set includes the ff: \n" +
                "\t\tOne (1) winner from the entries of officers \n" +
                "\t\tOne (1) winner from the entries of eligible associate \n" +
                "\t\tOne (1) winner from the entries of customer \n" +
                "\t\tSelling branch of where the customer buys the unit will automatically win a prize of Jollibee Food Pack \n" +
                "b.\tAll winners will be announced thru FB live and email c/o HCM every Monday at AM and PM Breaks \n" +
                "c.\tFor customers, they will be informed by the selling branch via SMS or phone call. Upon claiming, winning customer must present one (1) valid government-issued ID as proof of identity \n" +
                " \n" +
                "PRIZES \n" +
                " \n" +
                "Every week, there will be four (4) winners \n" +
                "\t\tOfficer: Baguio Trip \n" +
                "\t\tAssociate: Gift Certificates (Pedritos, Monarch, Xtreme Appliances) \n" +
                "\t\tCustomer: In a month, there will be Php1000.00 winners for three (3) draws; a winner of Php3000.00 for one (1) draw o Include provision for 1 MC unit (surprise draw) \n" +
                "\t\tBranch: Jollibee Food Pack worth Php600.00 ");
        dialogMsg.setMovementMethod(new ScrollingMovementMethod());
        //        lblAddress = view.findViewById(R.id.lbl_dialogLocation);
//        MaterialButton btnClose = view.findViewById(R.id.btn_dialogClose);
//        btnClose.setOnClickListener(view1 -> poDialog.dismiss());
    }

    public void show() {
        if(!poDialog.isShowing()){
            poDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            poDialog.getWindow().getAttributes().windowAnimations = org.rmj.g3appdriver.R.style.PopupAnimation;
            poDialog.show();
        }
    }
}