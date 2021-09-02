/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.notifications
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.notifications.Fragment;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Spannable;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import org.rmj.g3appdriver.GRider.Etc.FormatUIText;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.notifications.Activity.Activity_Notifications;
import org.rmj.guanzongroup.ghostrider.notifications.Dialog.Dialog_ReplyNotification;
import org.rmj.guanzongroup.ghostrider.notifications.R;
import org.rmj.guanzongroup.ghostrider.notifications.ViewModel.VMViewNotification;

public class Fragment_ViewNotification extends Fragment {

    private VMViewNotification mViewModel;
    private MessageBox poMsgBox;
    private MaterialButton btnDelete, btnReply;
    private String MessageID, Sender;
    private TextView title, sender, recepient, date, message;
    private ImageView imgSender;

    private LoadDialog poProgress;
    private MessageBox loMessage;
    public static Fragment_ViewNotification newInstance() {
        return new Fragment_ViewNotification();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_message, container, false);
        poProgress = new LoadDialog(getActivity());
        loMessage = new MessageBox(getActivity());
        setWidgets(view);
        return view;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MessageID = Activity_Notifications.getInstance().getMessageID();
        mViewModel = new ViewModelProvider(this).get(VMViewNotification.class);
        poMsgBox = new MessageBox(getActivity());

        mViewModel.getNotificationInfo(MessageID).observe(getViewLifecycleOwner(), notification -> {
            try {
                title.setText(notification.MsgTitle);
                if(!notification.CreatrNm.isEmpty()) {
                    sender.setText(notification.CreatrNm);
                    btnReply.setEnabled(true);
                    imgSender.setImageResource(R.drawable.ic_user_profile);
                } else {
                    sender.setText("SYSTEM NOTIFICATION");
                    btnReply.setEnabled(false);
                    imgSender.setImageResource(R.drawable.ic_guanzon_logo);
                }
                Sender = notification.CreatrNm;
                recepient.setText(notification.Receipt);
                date.setText(FormatUIText.getParseDateTime(notification.Received));
//                StringUrl();

                message.setMovementMethod(LinkMovementMethod.getInstance());
                message.setText(notification.Messagex, TextView.BufferType.SPANNABLE);
                int lnStart = notification.Messagex.indexOf("[");
                int lnEnd = notification.Messagex.indexOf("]");
                Spannable loSpan = (Spannable)message.getText();
                ClickableSpan loClickSpan = new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        String subsTringVal = notification.Messagex.substring(lnStart+1, lnEnd);
                        Uri uri = Uri.parse(subsTringVal);
                        Log.e("URL", uri.toString());
                        mViewModel.DownloadPDF(uri.toString(), new VMViewNotification.onDownLoadPDF() {
                            @Override
                            public void OnDownloadPDF(String title, String message) {
                                poProgress.initDialog(title, message, false);
                                poProgress.show();
                            }

                            @Override
                            public void OnFinishDownload(Intent intent) {
                                poProgress.dismiss();
                                requireActivity().startActivity(intent);
                            }

                            @Override
                            public void OnFailedDownload(String message) {
                                poProgress.dismiss();
                                initErrorDialog("Notification", message);
                            }
                        });
                    }
                };
                loSpan.setSpan(loClickSpan, lnStart, lnEnd + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } catch (ClassCastException e){
                e.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        btnReply.setOnClickListener(v -> {
            Dialog_ReplyNotification loDialog = new Dialog_ReplyNotification(getContext(), Sender);
            loDialog.initDialog(new Dialog_ReplyNotification.OnDialogButtonClickListener() {
                @Override
                public void OnSend(Dialog Dialog, String fsMessage) {
                    Toast.makeText(getContext(), "Reply currently not available.", Toast.LENGTH_SHORT).show();
                    Dialog.dismiss();
                    mViewModel.sendReply("", "Hello Guanzon."); //
                }

                @Override
                public void OnCancel(Dialog Dialog) {
                    Dialog.dismiss();
                }
            });
            loDialog.show();
        });

        btnDelete.setOnClickListener(v -> {
            poMsgBox.initDialog();
            poMsgBox.setTitle("Confirmation");
            poMsgBox.setMessage("Are you sure you want to delete this notification?");
            poMsgBox.setPositiveButton("Yes", (view, dialog) -> {
                dialog.dismiss();
                // TODO: Initialize viewModel code here.
                mViewModel.DeleteNotification(MessageID);
                // ~> Til Here
                requireActivity().finish();
            });
            poMsgBox.setNegativeButton("No", (view, dialog) -> dialog.dismiss());
            poMsgBox.show();
        });
        // TODO: Use the ViewModel
        mViewModel.UpdateMessageStatus(Activity_Notifications.getInstance().getMessageID());
    }

    private void setWidgets(View v) {
        btnReply = v.findViewById(R.id.btn_messageReply);
        btnDelete = v.findViewById(R.id.btn_messageDelete);
        title = v.findViewById(R.id.lbl_messageTitle);
        sender = v.findViewById(R.id.lbl_messageSender);
        recepient = v.findViewById(R.id.lbl_messageRecipient);
        date = v.findViewById(R.id.lbl_messageDateTime);
        message = v.findViewById(R.id.lbl_messageBody);
        imgSender = v.findViewById(R.id.img_sender);
    }

    public void initErrorDialog(String title, String message){
        loMessage.initDialog();
        loMessage.setTitle(title);
        loMessage.setMessage(message);
        loMessage.setPositiveButton("Okay", (view, dialog) ->
                dialog.dismiss());
        loMessage.show();
    }
}