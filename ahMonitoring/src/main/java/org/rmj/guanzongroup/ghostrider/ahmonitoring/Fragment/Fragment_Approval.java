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

import androidx.appcompat.app.AppCompatActivity;
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

import org.rmj.guanzongroup.ghostrider.ahmonitoring.Adaper.ActivityFragmentAdapter;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMFragmentApproval;

public class Fragment_Approval extends Fragment {

    private VMFragmentApproval mViewModel;
    private View view;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private int[] leave_tab = {
            R.drawable.ic_application_leave,
            R.drawable.ic_application_business_trip};
    private String[] tab_Title = {
            "Leave Approval",
            "Business Trip Approval"};
    public static Fragment_Approval newInstance() {
        return new Fragment_Approval();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_approval, container, false);
        setupWidgets();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(tab_Title[tabLayout.getSelectedTabPosition()]);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabLayout.getTabAt(tab.getPosition()).setIcon(leave_tab[tab.getPosition()]);
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(tab_Title[tab.getPosition()]);
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMFragmentApproval.class);
        // TODO: Use the ViewModel
    }
    private void setupWidgets(){
        viewPager = view.findViewById(R.id.viewpager_leave_ob_fragment_view);
        tabLayout = view.findViewById(R.id.tabLayout_leave_ob_fragment_indicator);

        ActivityFragmentAdapter adapter = new ActivityFragmentAdapter(getChildFragmentManager());
        adapter.addFragment(new Fragment_LeaveApproval());
        adapter.addFragment(new Fragment_BusinessTripApproval());
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(leave_tab[0]);
        tabLayout.getTabAt(1).setIcon(leave_tab[1]);
    }
}