/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.CreditEvaluator
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.guanzongroup.com.creditevaluation.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.android.material.divider.MaterialDivider;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;
import  com.google.android.material.checkbox.MaterialCheckBox;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.textfield.TextInputEditText;

import org.guanzongroup.com.creditevaluation.R;
import org.rmj.g3appdriver.etc.GToast;

public class DialogCIReason {
    Context mContex;
    private AlertDialog poDialogx;
    private String approval = "";
    public DialogCIReason(Context context){
        this.mContex = context;
    }

    public void initDialogCIReason(DialogButtonClickListener listener){
        AlertDialog.Builder loBuilder = new AlertDialog.Builder(mContex);
        View view = LayoutInflater.from(mContex).inflate(R.layout.dialog_ci_approval, null, false);
        loBuilder.setCancelable(false)
                .setView(view);
        poDialogx = loBuilder.create();
        poDialogx.setCancelable(false);
        RadioGroup rgApproval = view.findViewById(R.id.rgApproval);
        rgApproval.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                MaterialRadioButton checkedRadioButton = (MaterialRadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (checkedId == R.id.rb_ci_approved)
                {
                    // Set value of cTranstat when check approval"
                    approval = "1";

                }else{
                    // Set value of cTranstat when check disapproval"
                    approval = "0";
                }
            }
        });
        TextInputEditText tieReason = view.findViewById(R.id.txt_ci_reason);
        MaterialButton btnConfirm = view.findViewById(R.id.btn_dialogConfirm);
        MaterialButton btnCancelx = view.findViewById(R.id.btn_dialogCancel);
        btnConfirm.setOnClickListener(view1 -> {
            if (approval.isEmpty()){
                GToast.CreateMessage(mContex, "Please select approval/disapproval status dialog.",GToast.WARNING).show();
            }else if(tieReason.getText().toString().isEmpty()){
                GToast.CreateMessage(mContex, "Please enter reason for approval/disapproval evaluation dialog.",GToast.WARNING).show();
            }else{
                listener.OnClick(poDialogx, approval, tieReason.getText().toString());
            }
        });

        btnCancelx.setOnClickListener(view12 -> dismiss());
    }


    public void show(){
        poDialogx.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        poDialogx.getWindow().getAttributes().windowAnimations = org.rmj.g3appdriver.R.style.PopupAnimation;
        poDialogx.show();
    }

    public void dismiss(){
        if(poDialogx != null && poDialogx.isShowing()){
            poDialogx.dismiss();
        }
    }

    public interface DialogButtonClickListener{
        void OnClick(Dialog dialog, String transtat, String reason);
    }

}
