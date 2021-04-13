package org.rmj.guanzongroup.ghostrider.creditevaluator.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LiveData;

import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EClientUpdate;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GRider.Database.Repositories.RTown;
import org.rmj.g3appdriver.GRider.Etc.FormatUIText;
import org.rmj.guanzongroup.ghostrider.creditevaluator.R;

public class DialogCIReason {
    Context mContex;
    private AlertDialog poDialogx;
    public DialogCIReason(Context context){
        this.mContex = context;
    }

    public void initDialogCIReason(DialogButtonClickListener listener){
        AlertDialog.Builder loBuilder = new AlertDialog.Builder(mContex);
        View view = LayoutInflater.from(mContex).inflate(R.layout.dialog_ci_approval, null, false);
        loBuilder.setCancelable(false)
                .setView(view);
        poDialogx = loBuilder.create();
        poDialogx.setCancelable(false);

        TextInputEditText tieReason = view.findViewById(R.id.txt_ci_reason);
        Button btnConfirm = view.findViewById(R.id.btn_dialogConfirm);
        Button btnCancelx = view.findViewById(R.id.btn_dialogCancel);
       // btnConfirm.setOnClickListener(view1 -> listener.OnClick(poDialogx, spnTransact.getSelectedItem().toString()));

        btnCancelx.setOnClickListener(view12 -> dismiss());
    }


    public void show(){
        poDialogx.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        poDialogx.getWindow().getAttributes().windowAnimations = org.rmj.g3appdriver.R.style.PopupAnimation;
        poDialogx.show();
    }

    public void dismiss(){
        if(poDialogx != null && poDialogx.isShowing()){
            poDialogx.dismiss();
        }
    }

    public interface DialogButtonClickListener{
        void OnClick(Dialog dialog, String remarksCode);
    }


}
