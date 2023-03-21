/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 8/16/21 1:56 PM
 * project file last modified : 8/16/21 1:55 PM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Fragment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;


import org.rmj.g3appdriver.etc.ActivityFragmentAdapter;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMFragmentApproval;

import java.util.Objects;

public class Fragment_Approval extends Fragment {

    private VMFragmentApproval mViewModel;
    private View view;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private int[] leave_tab = {
            R.drawable.ic_application_leave,
            R.drawable.ic_application_business_trip};

    public static Fragment_Approval newInstance() {
        return new Fragment_Approval();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(VMFragmentApproval.class);
        view = inflater.inflate(R.layout.fragment_approval, container, false);
        setupWidgets();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabLayout.getTabAt(tab.getPosition()).setIcon(leave_tab[tab.getPosition()]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tabLayout.getTabAt(tab.getPosition()).setIcon(leave_tab[tab.getPosition()]);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }

    private void setupWidgets(){
        viewPager = view.findViewById(R.id.viewpager_leave_ob_fragment_view);
        tabLayout = view.findViewById(R.id.tabLayout_leave_ob_fragment_indicator);

        ActivityFragmentAdapter adapter = new ActivityFragmentAdapter(getChildFragmentManager());
        adapter.addFragment(new Fragment_LeaveApproval());
        adapter.addFragment(new Fragment_BusinessTripApproval());
        adapter.addFragment(new Fragment_Employee_Applications());
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(leave_tab[0]);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(leave_tab[1]);
    }
}