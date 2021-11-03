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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import org.rmj.g3appdriver.dev.DeptCode;
import org.rmj.guanzongroup.ghostrider.epacss.R;
import org.rmj.guanzongroup.ghostrider.epacss.ViewModel.VMHomeContainer;
import org.rmj.guanzongroup.ghostrider.epacss.ui.home.Fragment_Associate_Dashboard;
import org.rmj.guanzongroup.ghostrider.epacss.ui.home.Fragment_Home;
import org.rmj.guanzongroup.ghostrider.epacss.ui.home.Fragment_BH_Dashboard;
import org.rmj.guanzongroup.ghostrider.notifications.Fragment.Fragment_NotificationList;
import org.rmj.guanzongroup.onlinecreditapplication.Adapter.FragmentAdapter;

import java.util.Objects;

public class Fragment_HomeContainer extends Fragment {

    private VMHomeContainer mViewModel;
    private AppBarLayout appBarHome;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private Fragment[] fragment;

    private final int[] toggled_icons = {R.drawable.ic_home_dashboard_toggled,
                                        R.drawable.ic_home_notification_toggled};

    private final int[] icons = {R.drawable.ic_home_dashboard,
                                R.drawable.ic_home_notification};

    private final String[] header = {"Dashboard", "Messages", "Notifications"};

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home_container, container, false);
        appBarHome = root.findViewById(R.id.appbar_home);
        tabLayout = root.findViewById(R.id.tab_home);
        viewPager = root.findViewById(R.id.viewpager_home);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMHomeContainer.class);
        try{
            if(mViewModel.getEmployeeLevel().equalsIgnoreCase(String.valueOf(DeptCode.LEVEL_RANK_FILE))){
                fragment = new Fragment[]{new Fragment_Associate_Dashboard(), new Fragment_NotificationList()};
                appBarHome.setVisibility(View.VISIBLE);
            }  else if(mViewModel.getEmployeeLevel().equalsIgnoreCase(String.valueOf(DeptCode.LEVEL_BEANCH_HEAD))) {
                fragment = new Fragment[]{new Fragment_BH_Dashboard(), new Fragment_NotificationList()};
                appBarHome.setVisibility(View.VISIBLE);
            } else {
                fragment = new Fragment[]{new Fragment_Home()};
                appBarHome.setVisibility(View.GONE);
            }
//            fragment = new Fragment[]{new Fragment_Associate_Dashboard(), new Fragment_NotificationList()};
            viewPager.setAdapter(new FragmentAdapter(getChildFragmentManager(), fragment));
            if(fragment.length > 1){
                tabLayout.setupWithViewPager(viewPager);
                Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(toggled_icons[0]);
                Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(icons[1]);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Objects.requireNonNull(tabLayout.getTabAt(tab.getPosition())).setIcon(toggled_icons[tab.getPosition()]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Objects.requireNonNull(tabLayout.getTabAt(tab.getPosition())).setIcon(icons[tab.getPosition()]);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mViewModel.getUnreadNotificationsCount().observe(getViewLifecycleOwner(), userNotificationInfos -> {
            try {
                if(userNotificationInfos > 0) {
                    Objects.requireNonNull(tabLayout.getTabAt(1)).getOrCreateBadge().setNumber(userNotificationInfos);
                } else {
                    Objects.requireNonNull(tabLayout.getTabAt(1)).removeBadge();
                }
            } catch (NullPointerException e){
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }
}