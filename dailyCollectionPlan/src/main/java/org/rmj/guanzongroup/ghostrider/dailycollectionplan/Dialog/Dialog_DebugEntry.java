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

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities.Activity_LogCollection;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
        TextInputEditText txtDateEnt = view.findViewById(R.id.txt_dcpDateEntry);
        Button btnConfirm = view.findViewById(R.id.btn_dcpConfirm);
        Button btnCancel = view.findViewById(R.id.btn_cancel);

        txtDateEnt.setText(new AppConstants().CURRENT_DATE);
        txtDateEnt.setOnClickListener(v -> {
            final Calendar newCalendar = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat") final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            final DatePickerDialog StartTime = new DatePickerDialog(context, (view131, year, monthOfYear, dayOfMonth) -> {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String lsDate = dateFormatter.format(newDate.getTime());
                txtDateEnt.setText(lsDate);
            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
            StartTime.getDatePicker().setMaxDate(System.currentTimeMillis() + 1000);
            StartTime.show();
        });

        btnConfirm.setOnClickListener(v -> {
            try {
                String lsRemarks = Objects.requireNonNull(txtRemarks.getText()).toString();
                String lsDateEnt = txtDateEnt.getText().toString();
                JSONObject loJson = new JSONObject();
                loJson.put("employid", lsRemarks);
                loJson.put("date", lsDateEnt);
                if (!lsRemarks.trim().isEmpty()) {
                    listener.onConfirm(loJson.toString());
                    poDialogx.dismiss();
                } else {
                    GToast.CreateMessage(context, "Please enter remarks.", GToast.ERROR).show();
                }
            } catch (Exception e){
                e.printStackTrace();
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
