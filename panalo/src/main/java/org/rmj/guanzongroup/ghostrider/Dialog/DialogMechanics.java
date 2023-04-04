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
        dialogMsg.setText("What is Lorem Ipsum?\n" +
                        "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
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