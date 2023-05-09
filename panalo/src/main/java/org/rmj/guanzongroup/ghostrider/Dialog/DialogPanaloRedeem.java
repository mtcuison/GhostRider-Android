package org.rmj.guanzongroup.ghostrider.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.rmj.g3appdriver.dev.encryp.CodeGenerator;
import org.rmj.g3appdriver.lib.Panalo.model.PanaloRewards;
import org.rmj.guanzongroup.ghostrider.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

public class DialogPanaloRedeem {

    private final AlertDialog poDialog;
    private final TextView lblAddress;

    public DialogPanaloRedeem(Context context, PanaloRewards reward){
        AlertDialog.Builder poBuilder = new AlertDialog.Builder(context);
        Bitmap loBmp = null;
        switch (reward.getPanaloCD()){
            case "0001":
                loBmp = new CodeGenerator().GeneratePanaloOtherRedemptionQC(reward);
                break;
            case "0002":
                loBmp = new CodeGenerator().GeneratePanaloRebateRedemptionQC(reward);
                break;
            default:
                break;
        }
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_qr_redeem, null);
        poBuilder.setCancelable(false)
                .setView(view);
        poDialog = poBuilder.create();
        poDialog.setCancelable(false);

        lblAddress = view.findViewById(R.id.lbl_dialogLocation);
        ShapeableImageView img = view.findViewById(R.id.imgQrCode);
        img.setImageBitmap(loBmp);
        MaterialButton btnClose = view.findViewById(R.id.btn_dialogClose);
        btnClose.setOnClickListener(view1 -> poDialog.dismiss());
    }

    public void setAddress(String Address){
        lblAddress.setText(Address);
    }

    public void show(){
        poDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        poDialog.show();
    }
}
