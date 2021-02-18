package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Etc;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DialogImagePreview{
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

        if (DCP_Constants.selectedImageBitmap != null){
            imgPreview.setVisibility(View.VISIBLE);
            imgPreview.setImageBitmap(DCP_Constants.selectedImageBitmap);
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