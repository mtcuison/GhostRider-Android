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

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.rmj.g3appdriver.etc.GToast;
import org.rmj.guanzongroup.ghostrider.notifications.Activity.Activity_Notifications;
import org.rmj.guanzongroup.ghostrider.notifications.Adapter.NotificationListAdapter;
import org.rmj.guanzongroup.ghostrider.notifications.Obj.NotificationItemList;
import org.rmj.guanzongroup.ghostrider.notifications.R;
import org.rmj.guanzongroup.ghostrider.notifications.ViewModel.VMNotificationList;

import java.util.ArrayList;
import java.util.List;

public class Fragment_NotificationList extends Fragment {

    private VMNotificationList mViewModel;

    private RecyclerView recyclerView;
    private NotificationListAdapter poAdapter;
    private LinearLayoutManager manager;
    private RelativeLayout rl_list;
    private LinearLayout ln_empty;
    private boolean isLoading = false;
    private List<NotificationItemList> notificationItemLists = new ArrayList<>();

    /** Limit of displayaed list before load on fetch **/
    private int pnLimitxx = 10;

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
        /** Call 1st batch of notifications / Request query for selecting notifs with limit - 2 params  **/
        mViewModel.getUserNotificationList().observe(getViewLifecycleOwner(), userNotificationInfos -> {
            try {
                if (userNotificationInfos.size() > 0) {
                    rl_list.setVisibility(View.VISIBLE);
                    ln_empty.setVisibility(View.GONE);

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

                    poAdapter = new NotificationListAdapter(notificationItemLists, new NotificationListAdapter.OnItemClickListener() {
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
                            requireActivity().overridePendingTransition(R.anim.anim_intent_slide_in_right, R.anim.anim_intent_slide_out_left);
                        }

                        @Override
                        public void OnActionButtonClick(String message) {
                            GToast.CreateMessage(getActivity(), message, GToast.INFORMATION).show();
                        }
                    });
                    poAdapter.notifyDataSetChanged();
                    manager = new LinearLayoutManager(getActivity());
                    manager.setOrientation(RecyclerView.VERTICAL);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(poAdapter);

                } else {
                    rl_list.setVisibility(View.GONE);
                    ln_empty.setVisibility(View.VISIBLE);
                }
            } catch(NullPointerException e) {
                e.printStackTrace();
            }
        });

        initScrollListener();
    }

    private void setupWidgets(View v) {
        recyclerView = v.findViewById(R.id.recyclerview_notifications);
        rl_list = v.findViewById(R.id.rl_list);
        ln_empty = v.findViewById(R.id.ln_empty);
    }

    private void initScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!isLoading) {
                    if (manager != null && manager.findLastCompletelyVisibleItemPosition() == notificationItemLists.size() - 1) {
                        //bottom of list!
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });


    }

    private void loadMore() {
        notificationItemLists.add(null);
        poAdapter.notifyItemInserted(notificationItemLists.size() - 1);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                notificationItemLists.remove(notificationItemLists.size() - 1);
                int scrollPosition = notificationItemLists.size();
                poAdapter.notifyItemRemoved(scrollPosition);
                int currentSize = scrollPosition;

                /** Call method that selects limited list to load
                 *  (For second batch of list to display) **/

//                callTheMethod(currentSize, pnLimitxx) {
//                    notificationItemLists.addAll(list);
//                    poAdapter.notifyDataSetChanged();
//                    isLoading = false;
//                }

            }
        }, 2000);


    }

}