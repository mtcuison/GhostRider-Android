package org.rmj.guanzongroup.ghostrider.ahmonitoring.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;

import java.util.Objects;

public class DialogSelfieLogRemarks {
    private static final String TAG = DialogSelfieLogRemarks.class.getSimpleName();

    private final Context mContext;
    private AlertDialog poDialog;

    public interface OnDialogRemarksEntry{
        void OnConfirm(String args);
        void OnCancel();
    }

    public DialogSelfieLogRemarks(Context context) {
        this.mContext = context;
    }

    public void initDialog(OnDialogRemarksEntry callback){
        AlertDialog.Builder poBuilder = new AlertDialog.Builder(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_selfie_log_remarks, null);
        poBuilder.setCancelable(false)
                .setView(view);
        poDialog = poBuilder.create();
        poDialog.setCancelable(false);

        TextInputEditText txtRemarks = view.findViewById(R.id.txt_remarks);
        MaterialButton btnConfirm = view.findViewById(R.id.btn_confirm);
        MaterialButton btnCancel = view.findViewById(R.id.btn_cancel);

        btnConfirm.setOnClickListener(v -> {
            poDialog.dismiss();
            String lsRemarks = Objects.requireNonNull(txtRemarks.getText()).toString();
            callback.OnConfirm(lsRemarks);
        });

        btnCancel.setOnClickListener(v -> poDialog.dismiss());

        poDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        poDialog.getWindow().getAttributes().windowAnimations = org.rmj.g3appdriver.R.style.PopupAnimation;
        poDialog.show();
    }
}
