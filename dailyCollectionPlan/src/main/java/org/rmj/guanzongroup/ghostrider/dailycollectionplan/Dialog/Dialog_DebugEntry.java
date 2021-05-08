/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.dailyCollectionPlan
 * Electronic Personnel Access Control Security System
 * project file created : 5/7/21 4:06 PM
 * project file last modified : 5/7/21 4:06 PM
 */

package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;

import java.util.Objects;

public class Dialog_DebugEntry {

    private AlertDialog poDialogx;
    private final Context context;

    public interface OnDebugEntryListener{
        void onConfirm(String args);
    }

    public Dialog_DebugEntry(Context context) {
        this.context = context;
    }

    public void iniDialog(OnDebugEntryListener listener){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_debug_entry , null, false);
        AlertDialog.Builder loBuilder = new AlertDialog.Builder(context);
        loBuilder.setView(view)
                .setCancelable(false);
        poDialogx = loBuilder.create();
        poDialogx.setCancelable(false);

        TextInputEditText txtRemarks = view.findViewById(R.id.txt_dcpEmployeeID);
        Button btnConfirm = view.findViewById(R.id.btn_dcpConfirm);
        Button btnCancel = view.findViewById(R.id.btn_cancel);

        btnConfirm.setOnClickListener(v -> {
            String lsRemarks = Objects.requireNonNull(txtRemarks.getText()).toString();
            if(!lsRemarks.trim().isEmpty()) {
                listener.onConfirm(lsRemarks);
                poDialogx.dismiss();
            } else {
                GToast.CreateMessage(context, "Please enter remarks.", GToast.ERROR).show();
            }
        });

        btnCancel.setOnClickListener(v -> poDialogx.dismiss());
    }

    public void show(){
        poDialogx.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        poDialogx.getWindow().getAttributes().windowAnimations = org.rmj.g3appdriver.R.style.PopupAnimation;
        poDialogx.show();
    }
}
