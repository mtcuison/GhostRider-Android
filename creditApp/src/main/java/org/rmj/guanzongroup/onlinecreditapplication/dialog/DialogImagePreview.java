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

package org.rmj.guanzongroup.onlinecreditapplication.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

import org.rmj.g3appdriver.utils.DeviceDimensionsHelper;
import org.rmj.guanzongroup.onlinecreditapplication.R;


public class DialogImagePreview {
    private AlertDialog poDialogx;
    private final Context context;

    public DialogImagePreview(Context context){
        this.context = context;
    }

    public void initDialog(Bitmap imgBitmap, OnDialogButtonClickListener listener){
        AlertDialog.Builder loBuilder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_image_preview, null, false);
        loBuilder.setCancelable(false)
                .setView(view);
        poDialogx = loBuilder.create();
        poDialogx.setCancelable(false);
        ShapeableImageView imgPreview = view.findViewById(R.id.imgPreview);
        MaterialTextView dTitle = view.findViewById(R.id.dialogTitle);

        MaterialButton btnCancel = view.findViewById(R.id.btnPreviewOkay);
        try {
            imgPreview.setVisibility(View.VISIBLE);
            imgPreview.setImageBitmap(DeviceDimensionsHelper.scaleToActualAspectRatio(context,imgBitmap));
        }catch (NullPointerException e){
            e.printStackTrace();
        }


        btnCancel.setOnClickListener(view1 -> listener.OnCancel(poDialogx));
////        imgPreview.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Intent intent = new Intent(context, ImageMap.class);
////                context.startActivity(intent);
////            }
//        });
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