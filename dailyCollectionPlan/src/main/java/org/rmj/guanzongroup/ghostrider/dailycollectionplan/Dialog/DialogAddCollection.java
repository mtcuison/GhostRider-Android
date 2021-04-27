/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.dailyCollectionPlan
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;

import java.util.Objects;

public class DialogAddCollection {
    private static final String TAG = DialogAddCollection.class.getSimpleName();

    private AlertDialog poDialogx;
    private final Context context;
    private String psType = "";

    public DialogAddCollection(Context context) {
        this.context = context;
    }

    public void initDialog(OnDialogButtonClickListener listener){
        AlertDialog.Builder loBuilder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_get_client, null, false);
        loBuilder.setCancelable(false)
                .setView(view);
        poDialogx = loBuilder.create();
        poDialogx.setCancelable(false);

        TextInputEditText txtAccntNo = view.findViewById(R.id.txt_dcpParameter);
        RadioGroup rgColllect = view.findViewById(R.id.rg_collection_tp);
        RadioButton rbArClient = view.findViewById(R.id.rb_ar_client);
        RadioButton rbInsrnce = view.findViewById(R.id.rb_insurance_client);
        rgColllect.setOnCheckedChangeListener(new OnRadioButtonSelectListener());

        Button btnDownLoad = view.findViewById(R.id.btn_dcpDownload);
        Button btnCancel = view.findViewById(R.id.btn_cancel);

        btnDownLoad.setOnClickListener(view1 -> {
            if(!rbArClient.isChecked() && !rbInsrnce.isChecked()) {
                GToast.CreateMessage(context,"Please select a collection type.", GToast.WARNING).show();
            }
            else if(Objects.requireNonNull(txtAccntNo.getText()).toString().trim().isEmpty()) {
                GToast.CreateMessage(context,
                        "Please enter client name.",
                        GToast.WARNING).show();
            } else {
                listener.OnDownloadClick(poDialogx,
                        txtAccntNo.getText().toString(), psType.trim());
            }
        });

        btnCancel.setOnClickListener(view12 -> listener.OnCancel(poDialogx));
    }

    public void show(){
        if(!poDialogx.isShowing()){
            poDialogx.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            poDialogx.getWindow().getAttributes().windowAnimations = org.rmj.g3appdriver.R.style.PopupAnimation;
            poDialogx.show();
        }
    }

    private class OnRadioButtonSelectListener implements RadioGroup.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            if(radioGroup.getId() == R.id.rg_collection_tp){
                if (i == R.id.rb_ar_client) {
                    psType = "0";
                } else if (i == R.id.rb_insurance_client) {
                    psType = "1";
                }
            }
        }
    }

    public interface OnDialogButtonClickListener{
        void OnDownloadClick(Dialog Dialog, String args, String fsType);
        void OnCancel(Dialog Dialog);
    }
}
