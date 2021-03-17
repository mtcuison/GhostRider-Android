package org.rmj.guanzongroup.ghostrider.griderscanner.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.button.MaterialButton;

import org.rmj.guanzongroup.ghostrider.griderscanner.R;
import org.rmj.guanzongroup.ghostrider.griderscanner.helpers.ScannerConstants;

import java.io.File;
import java.io.IOException;

import static org.rmj.guanzongroup.ghostrider.griderscanner.ClientInfo.contentResolver;


public class DialogImagePreview {
    private AlertDialog poDialogx;
    private final Context context;
    private String lsDate = "";
    private String FilePath;
    private String dialogTitle;


    public DialogImagePreview(Context context){
        this.context = context;
//        this.FilePath = filePath;
    }
    public DialogImagePreview(Context context,String filePath, String title){
        this.context = context;
        this.FilePath = filePath;
        this.dialogTitle = title;
    }

    public void initDialog(OnDialogButtonClickListener listener){
        AlertDialog.Builder loBuilder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_dialog_image_preview, null, false);
        loBuilder.setCancelable(false)
                .setView(view);
        poDialogx = loBuilder.create();
        poDialogx.setCancelable(false);
        ImageView imgPreview = view.findViewById(R.id.imgPreview);
        TextView dTitle = view.findViewById(R.id.dialogTitle);
        dTitle.setText(dialogTitle);
        MaterialButton btnCancel = view.findViewById(R.id.btnPreviewOkay);
        Log.e("File Path", FilePath);

        if (FilePath != null){
            imgPreview.setVisibility(View.VISIBLE);
            try {
                imgPreview.setImageBitmap(MediaStore.Images.Media.getBitmap(
                        contentResolver, Uri.fromFile(new File(FilePath))));
            } catch (IOException e) {
                e.printStackTrace();
            }
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