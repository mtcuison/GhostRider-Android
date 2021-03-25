package org.rmj.guanzongroup.onlinecreditapplication.Etc;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import org.rmj.guanzongroup.onlinecreditapplication.R;

public class DialogPrimaryIncome {
    private static final String TAG = DialogPrimaryIncome.class.getSimpleName();

    private AlertDialog poDialogx;

    public interface PrimaryIncomeDialogListner{
        void OnSelect(AlertDialog dialog, String primary);
    }

    public DialogPrimaryIncome(Context context, String[] source, PrimaryIncomeDialogListner listner) {

        AlertDialog.Builder loBuilder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_primary_income, null, false);
        loBuilder.setCancelable(false)
                .setView(view);
        poDialogx = loBuilder.create();
        poDialogx.setCancelable(false);

        Spinner spnIncome = view.findViewById(R.id.spinner_cap_primaryIncome);
        Button btnConfirm = view.findViewById(R.id.btn_dialog_confirm);
        Button btnCancel = view.findViewById(R.id.btn_dialog_cancel);

        ArrayAdapter<String> loAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, source);
        spnIncome.setAdapter(loAdapter);
        btnConfirm.setOnClickListener(v -> listner.OnSelect(poDialogx, spnIncome.getSelectedItem().toString()));
        btnCancel.setOnClickListener(v -> poDialogx.dismiss());
    }

    public void show(){
        if(!poDialogx.isShowing()){
            poDialogx.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            poDialogx.getWindow().getAttributes().windowAnimations = org.rmj.g3appdriver.R.style.PopupAnimation;
            poDialogx.show();
        }
    }
}
