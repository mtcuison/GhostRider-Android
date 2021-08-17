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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.guanzongroup.ghostrider.notifications.Activity.Activity_Notifications;
import org.rmj.guanzongroup.ghostrider.notifications.Adapter.NotificationListAdapter;
import org.rmj.guanzongroup.ghostrider.notifications.Object.NotificationItemList;
import org.rmj.guanzongroup.ghostrider.notifications.R;
import org.rmj.guanzongroup.ghostrider.notifications.ViewModel.VMNotificationList;

import java.util.ArrayList;
import java.util.List;

public class Fragment_NotificationList extends Fragment {

    private VMNotificationList mViewModel;

    private RecyclerView recyclerView;
    private RelativeLayout rl_list;
    private LinearLayout ln_empty;

    public static Fragment_NotificationList newInstance() {
        return new Fragment_NotificationList();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification_list, container, false);
        setupWidgets(view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMNotificationList.class);
        mViewModel.getUserNotificationList().observe(getViewLifecycleOwner(), userNotificationInfos -> {
            try {
                if (userNotificationInfos.size() > 0) {
                    rl_list.setVisibility(View.VISIBLE);
                    ln_empty.setVisibility(View.GONE);

                    List<NotificationItemList> notificationItemLists = new ArrayList<>();
                    notificationItemLists.clear();
                    for (int x = 0; x < userNotificationInfos.size(); x++) {
                        NotificationItemList loItemList = new NotificationItemList();
                        loItemList.setMessageID(userNotificationInfos.get(x).MesgIDxx);
                        loItemList.setMessage(userNotificationInfos.get(x).Messagex);
                        loItemList.setDateTime(userNotificationInfos.get(x).Received);
                        loItemList.setName(userNotificationInfos.get(x).CreatrNm);
                        loItemList.setTitle(userNotificationInfos.get(x).MsgTitle);
                        loItemList.setReceipt(userNotificationInfos.get(x).Receipt);
                        loItemList.setStatus(userNotificationInfos.get(x).Status);
                        notificationItemLists.add(loItemList);
                    }

                    LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                    manager.setOrientation(RecyclerView.VERTICAL);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(new NotificationListAdapter(notificationItemLists, new NotificationListAdapter.OnItemClickListener() {
                        @Override
                        public void OnClick(String ID, String Title, String Message, String Sender, String Date, String Receipt) {
                            Intent loIntent = new Intent(getActivity(), Activity_Notifications.class);
                            loIntent.putExtra("id", ID);
                            loIntent.putExtra("title", Title);
                            loIntent.putExtra("message", Message);
                            loIntent.putExtra("sender", Sender);
                            loIntent.putExtra("date", Date);
                            loIntent.putExtra("type", "notification");
                            loIntent.putExtra("receipt", Receipt);
                            startActivity(loIntent);
                        }

                        @Override
                        public void OnActionButtonClick(String message) {
                            GToast.CreateMessage(getActivity(), message, GToast.INFORMATION).show();
                        }
                    }));

                } else {
                    rl_list.setVisibility(View.GONE);
                    ln_empty.setVisibility(View.VISIBLE);
                }
            } catch(NullPointerException e) {
                e.printStackTrace();
            }
        });
    }

    private void setupWidgets(View v) {
        recyclerView = v.findViewById(R.id.recyclerview_notifications);
        rl_list = v.findViewById(R.id.rl_list);
        ln_empty = v.findViewById(R.id.ln_empty);
    }

}