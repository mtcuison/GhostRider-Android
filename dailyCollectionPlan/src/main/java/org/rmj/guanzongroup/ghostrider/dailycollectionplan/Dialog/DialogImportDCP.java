package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;

import java.util.Objects;

public class DialogImportDCP {
    private static final String TAG = DialogConfirmPost.class.getSimpleName();

    private AlertDialog poDialogx;
    private final Context context;

    public interface DialogPostUnfinishedListener{
        void OnConfirm(AlertDialog dialog, String Remarks);
        void OnCancel(AlertDialog dialog);
    }

    public DialogImportDCP(Context context) {
        this.context = context;
    }

    public void iniDialog(DialogPostUnfinishedListener listener){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_import_dcp , null, false);
        AlertDialog.Builder loBuilder = new AlertDialog.Builder(context);
        loBuilder.setView(view)
                .setCancelable(false);
        poDialogx = loBuilder.create();
        poDialogx.setCancelable(false);

        TextInputEditText txtFileName = view.findViewById(R.id.txt_dcpFileName);
        Button btnConfirm = view.findViewById(R.id.btn_dcpConfirm);
        Button btnCancel = view.findViewById(R.id.btn_cancel);

        btnConfirm.setOnClickListener(v -> {
            String lsFilename = Objects.requireNonNull(txtFileName.getText()).toString();
            if(!lsFilename.trim().isEmpty()) {
                listener.OnConfirm(poDialogx, lsFilename);
            } else {
                GToast.CreateMessage(context, "Please file name to export.", GToast.ERROR).show();
            }
        });

        btnCancel.setOnClickListener(v -> listener.OnCancel(poDialogx));
    }

    public void show(){
        poDialogx.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        poDialogx.getWindow().getAttributes().windowAnimations = org.rmj.g3appdriver.R.style.PopupAnimation;
        poDialogx.show();
    }
}
