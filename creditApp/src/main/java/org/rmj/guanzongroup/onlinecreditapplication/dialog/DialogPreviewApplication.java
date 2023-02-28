package org.rmj.guanzongroup.onlinecreditapplication.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import com.google.android.material.tabs.TabLayout;
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
import  com.google.android.material.checkbox.MaterialCheckBox;


import org.rmj.g3appdriver.dev.Database.DataAccessObject.DCreditApplication;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplication;
import org.rmj.g3appdriver.etc.FormatUIText;
import org.rmj.guanzongroup.onlinecreditapplication.R;

public class DialogPreviewApplication {
    private static final String TAG = DialogPreviewApplication.class.getSimpleName();

    private final Context mContext;

    private AlertDialog poDialogx;

    public interface OnDialogActionClickListener{
        void DocumentScan(DCreditApplication.ApplicationLog creditApp);
    }

    public DialogPreviewApplication(Context context) {
        this.mContext = context;
    }

    public void initDialog(DCreditApplication.ApplicationLog args, OnDialogActionClickListener listener){
        AlertDialog.Builder poBuilder = new AlertDialog.Builder(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_preview_application, null);
        poBuilder.setCancelable(false)
                .setView(view);
        poDialogx = poBuilder.create();
        poDialogx.setCancelable(false);

        MaterialTextView lblTransNo = view.findViewById(R.id.lbl_TransNox),
                lblApplName = view.findViewById(R.id.lbl_applicantName),
                lblGOCasNo = view.findViewById(R.id.lbl_gocasNox),
                lblDateAppl = view.findViewById(R.id.lbl_dateCreated),
                lblDateSent = view.findViewById(R.id.lbl_dateSent),
                lblStatus = view.findViewById(R.id.lbl_applStatus),
                lblDateAppr = view.findViewById(R.id.lbl_dateApproved);

        MaterialButton btnScan = view.findViewById(R.id.btn_docScan),
                btnClose = view.findViewById(R.id.btn_close);

        lblTransNo.setText(args.sTransNox);
        lblApplName.setText(args.sClientNm);
        if(args.sGOCASNox.equalsIgnoreCase("null")){
            lblGOCasNo.setText("");
        } else {
            lblGOCasNo.setText(args.sGOCASNox);
        }
        lblDateAppl.setText(FormatUIText.getParseDateTime(args.dCreatedx));
        lblDateSent.setText(FormatUIText.getParseDateTime(args.dReceived));
        if(args.cTranStat.isEmpty() ||
            args.cTranStat.equalsIgnoreCase("0")){
            lblStatus.setText("Waiting for approval");
        } else if(args.cWithCIxx.equalsIgnoreCase("1")){
            lblStatus.setText("For C.I");
        } else {
            lblStatus.setText("");
        }

        lblDateAppr.setText(FormatUIText.getParseDateTime(args.dVerified));

        btnScan.setOnClickListener(v -> {
            poDialogx.dismiss();
            listener.DocumentScan(args);
        });

        btnClose.setOnClickListener(v -> poDialogx.dismiss());
    }

    public void show(){
        poDialogx.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        poDialogx.getWindow().getAttributes().windowAnimations = org.rmj.g3appdriver.R.style.PopupAnimation;
        poDialogx.show();
    }
}
