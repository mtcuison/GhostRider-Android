/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.griderScanner
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.griderscanner.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.button.MaterialButton;

import org.rmj.g3appdriver.utils.DeviceDimensionsHelper;
import org.rmj.guanzongroup.ghostrider.griderscanner.R;


public class DialogImagePreview {
    private AlertDialog poDialogx;
    private final Context context;
    private String lsDate = "";
    private Bitmap ImgBitmap;
    private String dialogTitle;


    public DialogImagePreview(Context context){
        this.context = context;
//        this.FilePath = filePath;
    }
    public DialogImagePreview(Context context, Bitmap imgBitmap, String title){
        this.context = context;
        this.ImgBitmap = imgBitmap;
        this.dialogTitle = title;
    }

    public void initDialog(OnDialogButtonClickListener listener){
        AlertDialog.Builder loBuilder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_image_preview, null, false);
        loBuilder.setCancelable(false)
                .setView(view);
        poDialogx = loBuilder.create();
        poDialogx.setCancelable(false);
        ImageView imgPreview = view.findViewById(R.id.imgPreview);
        TextView dTitle = view.findViewById(R.id.dialogTitle);
        dTitle.setText(dialogTitle);

        MaterialButton btnCancel = view.findViewById(R.id.btnPreviewOkay);
        try {
            if (ImgBitmap != null){
                imgPreview.setVisibility(View.VISIBLE);
                imgPreview.setImageBitmap(DeviceDimensionsHelper.scaleToActualAspectRatio(context,ImgBitmap));
//                imgPreview.setImageBitmap(bMapScaled);
            }
        }catch (NullPointerException e){
            e.printStackTrace();
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