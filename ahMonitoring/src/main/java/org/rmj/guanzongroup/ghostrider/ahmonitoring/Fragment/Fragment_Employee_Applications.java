/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 9/9/21, 11:51 AM
 * project file last modified : 9/9/21, 11:51 AM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import org.rmj.guanzongroup.ghostrider.ahmonitoring.Adapter.EmployeeApplicationAdapter;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMEmployeeApplications;

public class Fragment_Employee_Applications extends Fragment {

    private VMEmployeeApplications mViewModel;

    public static Fragment_Employee_Applications newInstance() {
        return new Fragment_Employee_Applications();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(VMEmployeeApplications.class);
        View view = inflater.inflate(R.layout.fragment_employee_applications, container, false);
        TabLayout tabLayout = view.findViewById(R.id.tablayout_approval_list);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_applications);
        tabLayout.addTab(tabLayout.newTab().setText("Leave"));
        tabLayout.addTab(tabLayout.newTab().setText("Business Trip"));

        LinearLayoutManager loManager = new LinearLayoutManager(requireActivity());
        loManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(loManager);

        mViewModel.GetApplicationType().observe(getViewLifecycleOwner(), integer -> {
            try{
                if(integer > 0){
                    mViewModel.GetApproveBusTrip().observe(getViewLifecycleOwner(), eEmployeeBusinessTrips -> {
                        try{
                            recyclerView.setAdapter(new EmployeeApplicationAdapter(eEmployeeBusinessTrips, TransNox -> {

                            }));
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    });
                } else {
                    mViewModel.getApproveLeaveList().observe(getViewLifecycleOwner(), eEmployeeLeaves -> {
                        try{
                            recyclerView.setAdapter(new EmployeeApplicationAdapter(eEmployeeLeaves, true, TransNox -> {

                            }));
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    });
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewModel.setApplicationType(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }
}