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

import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.guanzongroup.ghostrider.notifications.Activity.Activity_Notifications;
import org.rmj.guanzongroup.ghostrider.notifications.Adapter.NotificationListAdapter;
import org.rmj.guanzongroup.ghostrider.notifications.R;
import org.rmj.guanzongroup.ghostrider.notifications.ViewModel.VMNotificationList;

public class Fragment_NotificationList extends Fragment {

    private VMNotificationList mViewModel;

    private RecyclerView recyclerView;

    public static Fragment_NotificationList newInstance() {
        return new Fragment_NotificationList();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerview_notifications);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMNotificationList.class);
        mViewModel.getMessageList().observe(getViewLifecycleOwner(), notificationItemLists -> {
            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
            manager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(new NotificationListAdapter(notificationItemLists, new NotificationListAdapter.OnItemClickListener() {
                @Override
                public void OnClick(String ID, String Title, String Message, String Sender, String Date) {
                    Intent loIntent = new Intent(getActivity(), Activity_Notifications.class);
                    loIntent.putExtra("id", ID);
                    loIntent.putExtra("title", Title);
                    loIntent.putExtra("message", Message);
                    loIntent.putExtra("sender", Sender);
                    loIntent.putExtra("date", Date);
                    loIntent.putExtra("type", "notification");
                    startActivity(loIntent);
                }

                @Override
                public void OnActionButtonClick(String message) {
                    GToast.CreateMessage(getActivity(), message, GToast.INFORMATION).show();
                }
            }));
        });
    }

}