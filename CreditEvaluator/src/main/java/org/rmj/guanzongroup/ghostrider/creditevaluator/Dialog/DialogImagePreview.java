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

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;

import org.rmj.guanzongroup.ghostrider.creditevaluator.R;


public class DialogImagePreview{
    private static Bitmap selectedImageBitmap;
    private AlertDialog poDialogx;
    private final Context context;
    private String lsDate = "";
    private String psImgSrcx;
    private Button btnOkay;
    private ImageView ivTransImg;


    public DialogImagePreview(Context context, String fsFileLoc){
        this.context = context;
        this.psImgSrcx = fsFileLoc;
    }

    public void initDialog(OnDialogButtonClickListener fmListenr) {
        android.app.AlertDialog.Builder loBuilder = new android.app.AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_dialog_image_preview, null, false);
        loBuilder.setCancelable(false)
                .setView(view);
        poDialogx = loBuilder.create();
        poDialogx.setCancelable(false);

        initViews(view);
        setTransactionImage(psImgSrcx);
        btnOkay.setOnClickListener(v -> fmListenr.onConfirm(poDialogx));
    }

    public void show(){
        if(!poDialogx.isShowing()){
            poDialogx.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            poDialogx.getWindow().getAttributes().windowAnimations = org.rmj.g3appdriver.R.style.PopupAnimation;
            poDialogx.show();
        }
    }

    private void initViews(View v) {
        ivTransImg = v.findViewById(R.id.iv_transaction_img2);
        btnOkay = v.findViewById(R.id.btn_okay);
    }

    private void setTransactionImage(String photoPath) {
        // Get the dimensions of the View
        int targetW = 1000;
        int targetH = 1000;

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(photoPath, bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.max(1, Math.min(photoW/targetW, photoH/targetH));

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(photoPath, bmOptions);

        Bitmap bOutput;
        float degrees = 90;
        Matrix matrix = new Matrix();
        matrix.setRotate(degrees);
        bOutput = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        ivTransImg.setImageBitmap(bOutput);
    }

    public interface OnDialogButtonClickListener{
        void onConfirm(Dialog Dialog);
    }

}