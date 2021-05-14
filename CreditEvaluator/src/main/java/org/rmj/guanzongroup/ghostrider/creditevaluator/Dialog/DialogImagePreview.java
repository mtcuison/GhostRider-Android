/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.CreditEvaluator
 * Electronic Personnel Access Control Security System
 * project file created : 5/14/21 2:00 PM
 * project file last modified : 5/14/21 2:00 PM
 */

package org.rmj.guanzongroup.ghostrider.creditevaluator.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.appcompat.app.AlertDialog;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;

import org.rmj.guanzongroup.ghostrider.creditevaluator.R;


public class DialogImagePreview{
    private static Bitmap selectedImageBitmap;
    private AlertDialog poDialogx;
    private final Context context;
    private String lsDate = "";


    public DialogImagePreview(Context context){
        this.context = context;
    }

    public void initDialog(DialogImagePreview.OnDialogButtonClickListener listener){
        AlertDialog.Builder loBuilder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_dialog_image_preview, null, false);
        loBuilder.setCancelable(false)
                .setView(view);
        poDialogx = loBuilder.create();
        poDialogx.setCancelable(false);
        ImageView imgPreview = view.findViewById(R.id.imgPreview);
        MaterialButton btnCancel = view.findViewById(R.id.btnPreviewOkay);

        if (selectedImageBitmap != null){
            imgPreview.setVisibility(View.VISIBLE);
            imgPreview.setImageBitmap(selectedImageBitmap);
        }

        btnCancel.setOnClickListener(view1 -> listener.OnCancel(poDialogx));
    }

    public void show(){
        if(!poDialogx.isShowing()){
            poDialogx.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            poDialogx.show();
        }
    }

    public interface OnDialogButtonClickListener{
        void OnCancel(Dialog Dialog);
    }

}