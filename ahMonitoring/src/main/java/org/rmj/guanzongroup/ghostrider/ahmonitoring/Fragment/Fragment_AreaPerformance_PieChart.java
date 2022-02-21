/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 2/18/22, 1:28 PM
 * project file last modified : 2/18/22, 1:28 PM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity.Activity_AreaPerformance;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Etc.FragmentAdapter;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMAreaPerfromanceMonitoring;

public class Fragment_AreaPerformance_PieChart extends Fragment {
    private static final String TAG = Fragment_AreaPerformance_PieChart.class.getSimpleName();
    private VMAreaPerfromanceMonitoring mViewModel;
    private static ViewPager viewPager;
    private Fragment[] poPages = new Fragment[] {
            new Fragment_PieChart_Monthly(),
            new Fragment_PieChart_Yearly()
    };

    public Fragment_AreaPerformance_PieChart() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_area_performance_pie_chart, container, false);
        initWidgets(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(VMAreaPerfromanceMonitoring.class);
    }

    private void initWidgets(View v) {
        viewPager = v.findViewById(R.id.piechart_viewPager);
        viewPager.setAdapter(new FragmentAdapter(getActivity().getSupportFragmentManager(), poPages));
    }

}