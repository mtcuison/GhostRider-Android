package org.guanzongroup.com.itinerary.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.guanzongroup.com.itinerary.R;
import org.rmj.g3appdriver.GCircle.room.Entities.EItinerary;
import org.rmj.g3appdriver.etc.FormatUIText;

public class DialogEntryDetail {
    private static final String TAG = DialogEntryDetail.class.getSimpleName();

    private final Context mContext;

    private TextView lblDate, lblTime, lblLoct, lblPrps;

    public DialogEntryDetail(Context context) {
        this.mContext = context;
    }

    public void showDialog(EItinerary args){
        AlertDialog.Builder poBuilder = new AlertDialog.Builder(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_entry_detail, null);
        poBuilder.setCancelable(false)
                .setView(view);
        AlertDialog poDialogx = poBuilder.create();
        poDialogx.setCancelable(false);

        lblDate = view.findViewById(R.id.lbl_dateSched);
        lblTime = view.findViewById(R.id.lbl_tripTime);
        lblLoct = view.findViewById(R.id.lbl_location);
        lblPrps = view.findViewById(R.id.lbl_purpose);

        lblDate.setText("Date: " + FormatUIText.formatGOCasBirthdate(args.getTransact()));
        String lsTime = FormatUIText.formatTime_HHMMSS_to_HHMMAA(args.getTimeFrom()) + " - " + FormatUIText.formatTime_HHMMSS_to_HHMMAA(args.getTimeThru());
        lblTime.setText("Time: " + lsTime);
        lblLoct.setText("Location: " + args.getLocation());
        lblPrps.setText("Purpose: " + args.getRemarksx());

        view.findViewById(R.id.btn_cancel).setOnClickListener(view1 -> poDialogx.dismiss());

        poDialogx.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        poDialogx.getWindow().getAttributes().windowAnimations = org.rmj.g3appdriver.R.style.PopupAnimation;
        poDialogx.show();
    }
}
