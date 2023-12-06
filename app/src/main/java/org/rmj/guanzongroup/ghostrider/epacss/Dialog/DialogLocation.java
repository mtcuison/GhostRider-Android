/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.app
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.ghostrider.epacss.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import org.rmj.guanzongroup.ghostrider.epacss.R;

public class DialogLocation {

    private final AlertDialog poDialog;
    private final MaterialTextView lblTitle, lblMsg;
    private final MaterialButton btnOk;
    public DialogLocation(Context context){
        AlertDialog.Builder poBuilder = new AlertDialog.Builder(context.getApplicationContext());
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_location, null);
        poBuilder.setCancelable(false)
                .setView(view);
        poDialog = poBuilder.create();
        poDialog.setCancelable(false);

        lblTitle = view.findViewById(R.id.lbl_dialogTitle);
        lblMsg = view.findViewById(R.id.lbl_dialogMessage);
        btnOk = view.findViewById(R.id.btn_okay);
        initWidgets();
        btnOk.setOnClickListener(view1 -> {
            poDialog.dismiss();

            Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            view1.getContext().startActivity(intent);

//            Intent serviceIntent = new Intent(view1.getContext(), LocationService.class);
//            view1.getContext().startService(serviceIntent);

//            Intent settingsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            view1.getContext().startService(settingsIntent);
        });

    }

private  void initWidgets(){
        lblTitle.setText("Location");
       lblMsg.setText("Location services are disabled. Please enable them to proceed.");
}

    public void show(){
        poDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        poDialog.show();
    }
}
