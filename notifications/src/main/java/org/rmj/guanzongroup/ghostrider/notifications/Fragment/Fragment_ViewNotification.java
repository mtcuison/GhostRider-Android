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

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import org.rmj.g3appdriver.GRider.Etc.FormatUIText;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity.Activity_CashCounter;
import org.rmj.guanzongroup.ghostrider.notifications.Activity.Activity_Notifications;
import org.rmj.guanzongroup.ghostrider.notifications.R;
import org.rmj.guanzongroup.ghostrider.notifications.ViewModel.VMViewNotification;

import java.util.Objects;

public class Fragment_ViewNotification extends Fragment {
//    private VMViewNotification mViewModel;
    private TextView title, sender, recepient, date, message;

    public static Fragment_ViewNotification newInstance() {
        return new Fragment_ViewNotification();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_message, container, false);
        setWidgets(view);
        setContent(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(VMViewNotification.class);
        // TODO: Use the ViewModel
    }

    private void setWidgets(View v) {
        title = v.findViewById(R.id.lbl_messageTitle);
        sender = v.findViewById(R.id.lbl_messageSender);
        recepient = v.findViewById(R.id.lbl_messageRecipient);
        date = v.findViewById(R.id.lbl_messageDateTime);
        message = v.findViewById(R.id.lbl_messageBody);
    }

    private void setContent(View v) {
        try {
            title.setText(Activity_Notifications.getInstance().getMessageTitle());
            sender.setText(Activity_Notifications.getInstance().getSender());
            recepient.setText(Activity_Notifications.getInstance().getReceipt());
            date.setText(FormatUIText.getParseDateTime(Activity_Notifications.getInstance().getDate()));
            message.setText(Activity_Notifications.getInstance().getMessage());
            requestCashCount(v, Activity_Notifications.getInstance().getMsgType());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
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

}