package org.rmj.guanzongroup.ghostrider.griderscanner.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.button.MaterialButton;

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
                imgPreview.setImageBitmap(ImgBitmap);
            }
        }catch (NullPointerException e){

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