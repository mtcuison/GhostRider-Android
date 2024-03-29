package org.rmj.guanzongroup.ghostrider.ahmonitoring.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;

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

        poDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        poDialog.getWindow().getAttributes().windowAnimations = org.rmj.g3appdriver.R.style.PopupAnimation;
        poDialog.show();
    }
}
