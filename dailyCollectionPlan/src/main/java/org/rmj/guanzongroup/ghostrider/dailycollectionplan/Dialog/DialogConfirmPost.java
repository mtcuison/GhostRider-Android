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

public class DialogConfirmPost {
    private static final String TAG = DialogConfirmPost.class.getSimpleName();

    private AlertDialog poDialogx;
    private final Context context;

    public interface DialogPostUnfinishedListener{
        void OnConfirm(AlertDialog dialog, String Remarks);
        void OnCancel(AlertDialog dialog);
    }

    public DialogConfirmPost(Context context) {
        this.context = context;
    }

    public void iniDialog(DialogPostUnfinishedListener listener){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_confirm_post , null, false);
        AlertDialog.Builder loBuilder = new AlertDialog.Builder(context);
        loBuilder.setView(view)
                .setCancelable(false);
        poDialogx = loBuilder.create();
        poDialogx.setCancelable(false);

        TextInputEditText txtRemarks = view.findViewById(R.id.txt_dcpRemarks);
        Button btnConfirm = view.findViewById(R.id.btn_dcpConfirm);
        Button btnCancel = view.findViewById(R.id.btn_cancel);

        btnConfirm.setOnClickListener(v -> {
            String lsRemarks = Objects.requireNonNull(txtRemarks.getText()).toString();
            if(!lsRemarks.trim().isEmpty()) {
                listener.OnConfirm(poDialogx, lsRemarks);
            } else {
                GToast.CreateMessage(context, "Please enter remarks.", GToast.ERROR).show();
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
