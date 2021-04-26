/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.creditApp
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.onlinecreditapplication.Fragment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rmj.guanzongroup.onlinecreditapplication.Activity.Activity_CreditApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Adapter.FragmentAdapter;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMMeansInfo;

public class Fragment_MeansInfo extends Fragment {

    private VMMeansInfo mViewModel;

    private ViewPager viewPager;
    private String psTransno;

    public static Fragment_MeansInfo newInstance() {
        return new Fragment_MeansInfo();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_means_info, container, false);
        psTransno = Activity_CreditApplication.getInstance().getTransNox();
        viewPager = view.findViewById(R.id.viewpager_meansInfo);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMMeansInfo.class);
        mViewModel.setTransNox(psTransno);
        //mViewModel.getMeansInfo().observe(getViewLifecycleOwner(), s -> mViewModel.getMeansInfoPages(s).observe(getViewLifecycleOwner(), fragments -> viewPager.setAdapter(new FragmentAdapter(getChildFragmentManager(), fragments))));
    }
}