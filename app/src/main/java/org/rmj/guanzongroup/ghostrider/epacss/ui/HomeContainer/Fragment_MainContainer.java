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

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rmj.guanzongroup.ghostrider.ahmonitoring.Etc.FragmentAdapter;
import org.rmj.guanzongroup.ghostrider.epacss.R;
import org.rmj.guanzongroup.ghostrider.epacss.ViewModel.VMMainContainer;
import org.rmj.guanzongroup.ghostrider.epacss.ui.etc.appConstants;

public class Fragment_MainContainer extends Fragment {

    private VMMainContainer mViewModel;

    private static Fragment_MainContainer instance;
    private static ViewPager viewPager;
    private String transNox;
    public static Fragment_MainContainer newInstance() {
        return new Fragment_MainContainer();
    }

    public static Fragment_MainContainer getInstance(){
        return instance;
    }

    public String getTransNox(){
        return transNox;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_container, container, false);
        instance = this;
        viewPager = view.findViewById(R.id.viewpager_mainContainer);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMMainContainer.class);

        mViewModel.getEmployeeInfo().observe(getViewLifecycleOwner(), eEmployeeInfo -> {
            try {
                viewPager.setAdapter(new FragmentAdapter(getParentFragmentManager(), appConstants.getHomePages(Integer.parseInt(eEmployeeInfo.getEmpLevID()))));
            } catch (Exception e){
                e.printStackTrace();
            }
        });
    }
}