package org.rmj.guanzongroup.pacitareward;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;

public class Dialog_SelectAction  {
    Context context;
    MaterialButton btn_evaluate;
    MaterialButton btn_vrec;
    MaterialButton btn_cancel;

    public Dialog_SelectAction(Context context){
        this.context = context;
    }

    public void initDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.activity_dialog_select_action, null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.getWindow().getAttributes().windowAnimations = org.rmj.g3appdriver.R.style.PopupAnimation;

        btn_evaluate = view.findViewById(R.id.btn_evaluate);
        btn_vrec = view.findViewById(R.id.btn_vrec);
        btn_cancel = view.findViewById(R.id.btn_cancel);

        btn_evaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), Activity_Branch_Rate.class);
                ContextCompat.startActivity(view.getContext(), intent, null);
                alertDialog.dismiss();
            }
        });

        btn_vrec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), Activity_Branch_Rate.class);
                ContextCompat.startActivity(view.getContext(), intent, null);
                alertDialog.dismiss();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }
}