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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import org.rmj.guanzongroup.ghostrider.epacss.R;
import org.rmj.guanzongroup.ghostrider.epacss.ViewModel.VMHomeContainer;
import org.rmj.guanzongroup.ghostrider.epacss.ui.home.Fragment_AH_Dashboard;
import org.rmj.guanzongroup.ghostrider.epacss.ui.home.Fragment_Dashboard;
import org.rmj.guanzongroup.ghostrider.notifications.Fragment.Fragment_MessageList;
import org.rmj.guanzongroup.ghostrider.notifications.Fragment.Fragment_NotificationList;
import org.rmj.guanzongroup.onlinecreditapplication.Adapter.FragmentAdapter;

public class Fragment_HomeContainer extends Fragment {

    private VMHomeContainer galleryViewModel;
    private TextView lblHeader;

    private final Fragment[] fragment = {new Fragment_AH_Dashboard(),new Fragment_MessageList(),new Fragment_NotificationList()};

    private final int[] toggled_icons = {R.drawable.ic_home_dashboard_toggled,
                                        R.drawable.ic_baseline_settings_24,
                                        R.drawable.ic_home_notification_toggled};

    private final int[] icons = {R.drawable.ic_home_dashboard,
                                R.drawable.ic_baseline_settings_24,
                                R.drawable.ic_home_notification};

    private final String[] header = {"Dashboard", "Messages", "Notifications"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel = new ViewModelProvider(this).get(VMHomeContainer.class);
        View root = inflater.inflate(R.layout.fragment_home_container, container, false);
        TabLayout tabLayout = root.findViewById(R.id.tab_home);
        ViewPager viewPager = root.findViewById(R.id.viewpager_home);
        lblHeader = root.findViewById(R.id.lbl_dashBoardHeader);
        viewPager.setAdapter(new FragmentAdapter(getChildFragmentManager(), fragment));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(toggled_icons[0]);
        tabLayout.getTabAt(1).setIcon(icons[1]);
        tabLayout.getTabAt(2).setIcon(icons[2]);
        tabLayout.getTabAt(1).getOrCreateBadge().setNumber(4);
        tabLayout.getTabAt(2).getOrCreateBadge().setNumber(4);
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
        return root;
    }
}