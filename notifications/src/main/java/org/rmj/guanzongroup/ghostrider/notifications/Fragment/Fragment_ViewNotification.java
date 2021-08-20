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

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import org.rmj.g3appdriver.GRider.Etc.FormatUIText;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity.Activity_CashCounter;
import org.rmj.guanzongroup.ghostrider.notifications.Activity.Activity_Notifications;
import org.rmj.guanzongroup.ghostrider.notifications.Dialog.Dialog_ReplyNotification;
import org.rmj.guanzongroup.ghostrider.notifications.R;
import org.rmj.guanzongroup.ghostrider.notifications.ViewModel.VMViewNotification;

public class Fragment_ViewNotification extends Fragment {
//    private static final int[] CDATA = ;
    private VMViewNotification mViewModel;
    private MessageBox poMsgBox;
    private MaterialButton btnDelete, btnReply;
    private TextView title, sender, recepient, date, message;

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
        setContent(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMViewNotification.class);
        poMsgBox = new MessageBox(getActivity());

        btnReply.setOnClickListener(v -> {
            Dialog_ReplyNotification loDialog = new Dialog_ReplyNotification(getContext(), Activity_Notifications.getInstance().getSender());
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
                mViewModel.deleteNotification("");
                // ~> Til Here
                requireActivity().finish();
            });
            poMsgBox.setNegativeButton("No", (view, dialog) -> dialog.dismiss());
            poMsgBox.show();
        });
//        mViewModel = new ViewModelProvider(this).get(VMViewNotification.class);
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
    }

    private void setContent(View v) {
        try {
            Log.e("SAmple", Activity_Notifications.getInstance().getMessage());
            title.setText(Activity_Notifications.getInstance().getMessageTitle());
            sender.setText(Activity_Notifications.getInstance().getSender());
            recepient.setText(Activity_Notifications.getInstance().getReceipt());
            date.setText(FormatUIText.getParseDateTime(Activity_Notifications.getInstance().getDate()));
            setTextViewHTML(message, Activity_Notifications.getInstance().getMessage());
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

//                message.setText(Html.fromHtml(Activity_Notifications.getInstance().getMessage(), Html.FROM_HTML_MODE_COMPACT));
//            } else {
//                setTextViewHTML(message, String.valueOf(Html.fromHtml(Activity_Notifications.getInstance().getMessage())));
//                message.setText(Html.fromHtml(Activity_Notifications.getInstance().getMessage()));
//            }
//            message.setMovementMethod(LinkMovementMethod.getInstance());


            requestCashCount(v, Activity_Notifications.getInstance().getMsgType());

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    protected void makeLinkClickable(SpannableStringBuilder strBuilder, final URLSpan span)
    {
        int start = strBuilder.getSpanStart(span);
        int end = strBuilder.getSpanEnd(span);
        int flags = strBuilder.getSpanFlags(span);
        ClickableSpan clickable = new ClickableSpan() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            public void onClick(View view) {
                // Do something with span.getURL() to handle the link click...
                Log.e("Notification", String.valueOf(span.getURL()));

//                PDFTools.showPDFUrl(getActivity(), span.getURL());
                mViewModel.DownloadPDF(span.getURL(), new VMViewNotification.onDownLoadPDF() {
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

//                    @Override
//                    public void OnFinishDownload(Intent intent) {
//                        poProgress.dismiss();
//                        GToast.CreateMessage(getActivity(), "Download Success.",GToast.INFORMATION).show();
//                        startActivity(intent);
//                    }

                    @Override
                    public void OnFailedDownload(String message) {
                        poProgress.dismiss();
                        initErrorDialog("Notification", message);
                    }
                });
            }
        };
        strBuilder.setSpan(clickable, start, end, flags);
        strBuilder.removeSpan(span);
    }

    protected void setTextViewHTML(TextView text, String html)
    {
        CharSequence sequence = Html.fromHtml(html);
        SpannableStringBuilder strBuilder = new SpannableStringBuilder(sequence);
        URLSpan[] urls = strBuilder.getSpans(0, sequence.length(), URLSpan.class);
        for(URLSpan span : urls) {
            makeLinkClickable(strBuilder, span);
        }
        text.setText(strBuilder);
        text.setMovementMethod(LinkMovementMethod.getInstance());
    }
    private void requestCashCount(View v, String fsMsgType) {
        if(!fsMsgType.equalsIgnoreCase("00003")) {
            return;
        } else {
            MaterialButton btnCashCnt = v.findViewById(R.id.btn_cash_count);
            btnCashCnt.setVisibility(View.VISIBLE);
            btnCashCnt.setOnClickListener(view -> {
                Intent loIntent = new Intent(getActivity(), Activity_CashCounter.class);
                loIntent.putExtra("sTransNox","1122");
                startActivity(loIntent);
                getActivity().finish();
            });
        }
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