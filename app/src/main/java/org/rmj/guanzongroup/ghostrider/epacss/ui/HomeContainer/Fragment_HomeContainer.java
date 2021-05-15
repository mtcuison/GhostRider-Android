/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.app
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.ghostrider.epacss.ui.HomeContainer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DNotifications;
import org.rmj.guanzongroup.ghostrider.epacss.R;
import org.rmj.guanzongroup.ghostrider.epacss.ViewModel.VMHomeContainer;
import org.rmj.guanzongroup.ghostrider.epacss.ui.home.Fragment_AH_Dashboard;
import org.rmj.guanzongroup.ghostrider.notifications.Fragment.Fragment_MessageList;
import org.rmj.guanzongroup.ghostrider.notifications.Fragment.Fragment_NotificationList;
import org.rmj.guanzongroup.onlinecreditapplication.Adapter.FragmentAdapter;

import java.util.List;
import java.util.Objects;

public class Fragment_HomeContainer extends Fragment {

    private VMHomeContainer mViewModel;
    private TextView lblHeader;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private final Fragment[] fragment = {new Fragment_AH_Dashboard(),new Fragment_MessageList(),new Fragment_NotificationList()};

    private final int[] toggled_icons = {R.drawable.ic_home_dashboard_toggled,
                                        R.drawable.ic_home_message_toggled,
                                        R.drawable.ic_home_notification_toggled};

    private final int[] icons = {R.drawable.ic_home_dashboard,
                                R.drawable.ic_home_message,
                                R.drawable.ic_home_notification};

    private final String[] header = {"Dashboard", "Messages", "Notifications"};

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home_container, container, false);
        tabLayout = root.findViewById(R.id.tab_home);
        viewPager = root.findViewById(R.id.viewpager_home);
        lblHeader = root.findViewById(R.id.lbl_dashBoardHeader);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMHomeContainer.class);
        viewPager.setAdapter(new FragmentAdapter(getChildFragmentManager(), fragment));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(toggled_icons[0]);
        tabLayout.getTabAt(1).setIcon(icons[1]);
        tabLayout.getTabAt(2).setIcon(icons[2]);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabLayout.getTabAt(tab.getPosition()).setIcon(toggled_icons[tab.getPosition()]);
                lblHeader.setText(header[tab.getPosition()]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tabLayout.getTabAt(tab.getPosition()).setIcon(icons[tab.getPosition()]);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mViewModel.getUserMessageList().observe(getViewLifecycleOwner(), userMessageList -> {
            try {
                Objects.requireNonNull(tabLayout.getTabAt(1)).getOrCreateBadge().setNumber(userMessageList.size());
            } catch (Exception e){
                e.printStackTrace();
            }
        });
    }
}