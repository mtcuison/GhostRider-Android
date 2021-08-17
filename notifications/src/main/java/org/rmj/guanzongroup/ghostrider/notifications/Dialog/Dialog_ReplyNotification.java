/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.notifications
 * Electronic Personnel Access Control Security System
 * project file created : 8/17/21 1:17 PM
 * project file last modified : 8/17/21 1:17 PM
 */

package org.rmj.guanzongroup.ghostrider.notifications.Dialog;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.textfield.TextInputEditText;

import org.rmj.guanzongroup.ghostrider.notifications.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class Dialog_ReplyNotification {
    private static final String TAG = Dialog_ReplyNotification.class.getSimpleName();

    private AlertDialog poDialogx;
    private final Context context;
    private final String psSender;

    public Dialog_ReplyNotification(Context context, String sender){
        Objects.requireNonNull(this.context = context);
        Objects.requireNonNull(this.psSender = sender);
    }

    public void initDialog(OnDialogButtonClickListener listener){
        AlertDialog.Builder loBuilder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_reply_notification, null, false);
        loBuilder.setCancelable(false)
                .setView(view);
        poDialogx = loBuilder.create();
        poDialogx.setCancelable(false);

        TextView lblSender = view.findViewById(R.id.lbl_sender);
        TextInputEditText txtMessage = view.findViewById(R.id.txt_notification_reply);
        Button btnReply = view.findViewById(R.id.btn_send_reply);
        Button btnCancel = view.findViewById(R.id.btn_cancel);

        lblSender.setText(psSender);

        btnReply.setOnClickListener(v -> listener.OnSend(poDialogx, txtMessage.getText().toString()));

        btnCancel.setOnClickListener(v -> listener.OnCancel(poDialogx));
    }

    public void show(){
        if(!poDialogx.isShowing()){
            poDialogx.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            poDialogx.getWindow().getAttributes().windowAnimations = org.rmj.g3appdriver.R.style.PopupAnimation;
            poDialogx.show();
        }
    }

    public interface OnDialogButtonClickListener{
        void OnSend(Dialog Dialog, String fsMessage);
        void OnCancel(Dialog Dialog);
    }
}
