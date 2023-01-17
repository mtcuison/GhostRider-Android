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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.guanzongroup.ghostrider.notifications.R;
import org.rmj.guanzongroup.ghostrider.notifications.ViewModel.VMViewMessages;

public class Fragment_ViewMessages extends Fragment {

    private VMViewMessages mViewModel;

    private RecyclerView recyclerView;

    public static Fragment_ViewMessages newInstance() {
        return new Fragment_ViewMessages();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_messages, container, false);

        recyclerView = view.findViewById(R.id.recyclerview_messages);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMViewMessages.class);

//        String lsSenderID = Activity_Notifications.getInstance().getSender();
//        mViewModel.UpdateMessageStatus(lsSenderID);
//        mViewModel.getMessagesListFromSender(lsSenderID).observe(getViewLifecycleOwner(), userNotificationInfos -> {
//            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
//            manager.setOrientation(RecyclerView.VERTICAL);
//            recyclerView.setLayoutManager(manager);
//            recyclerView.setAdapter(new MessagesViewAdapter(userNotificationInfos));
//        });
    }
}