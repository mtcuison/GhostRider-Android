package org.rmj.guanzongroup.ghostrider.ahmonitoring.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;

import java.util.Objects;

public class DialogPostInventory {
    private static final String TAG = DialogPostInventory.class.getSimpleName();

    private final Context mContext;
    private AlertDialog poDialog;

    public DialogPostInventory(Context mContext) {
        this.mContext = mContext;
    }

    public interface RemarksEntryCallback{
        void OnConfirm(String Remarks);
    }

    public void initDialog(RemarksEntryCallback callback){
        AlertDialog.Builder poBuilder = new AlertDialog.Builder(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_inventory_master_remarks, null);
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

        poDialog.show();
    }
}
