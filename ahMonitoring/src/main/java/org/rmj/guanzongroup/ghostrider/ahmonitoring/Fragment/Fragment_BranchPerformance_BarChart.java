/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 2/24/22, 9:06 AM
 * project file last modified : 2/24/22, 9:06 AM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Fragment;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;

import org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity.Activity_BranchPerformance;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Adapter.AreaInfoBarChartAdapter;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Adapter.BranchInfoBarChartAdapter;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Adapter.BranchPerformanceAdapter;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMBranchMonitor;

import java.util.ArrayList;

public class Fragment_BranchPerformance_BarChart extends Fragment {
    private VMBranchMonitor mViewModel;
    private PieChart pieChart;
    private RecyclerView recyclerView, rvChart;
    private BranchPerformanceAdapter adapter;
    private String[] brnSales = {"MC Sales","SP Sales","JO Sales"};
    private String brnCD;
    ArrayList<Entry> chartValues;
    private LineChart lineChart;
    private TextView lblBranch, lgdGoal, lgdActual, lgdExcess;

    private ColorStateList poColor;
    private TextView lblItem1;
    private TextView lblItem2;
    private TextView lblSelectd;

    public Fragment_BranchPerformance_BarChart() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_branch_performance_bar_chart,
                container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(VMBranchMonitor.class);
        brnCD = Activity_BranchPerformance.getInstance().getBranchCode();
        mViewModel.getBranchName(brnCD).observe(getViewLifecycleOwner(),bnName->{
            lblBranch.setText(bnName);
        });
        mViewModel.getType().observe(getViewLifecycleOwner(), s -> setData(s));
    }

    private void initViews(View v) {
        lblBranch = v.findViewById(R.id.tvBranch);
        recyclerView = v.findViewById(R.id.recyclerview_branch_performance);
        rvChart = v.findViewById(R.id.recyclerView_chart);
        lineChart = v.findViewById(R.id.activity_branch_linechart);
        lblItem1 = v.findViewById(R.id.item1);
        lblItem2 = v.findViewById(R.id.item2);
        lblSelectd = v.findViewById(R.id.select);
        poColor = lblItem2.getTextColors();
        lgdGoal = v.findViewById(R.id.lgd_goal);
        lgdActual = v.findViewById(R.id.lgd_actual);
        lgdExcess = v.findViewById(R.id.lgd_excess);
        lblItem1.setOnClickListener(new TabClickHandler());
        lblItem2.setOnClickListener(new TabClickHandler());
    }

    private void setData(String sales) {
        mViewModel.getAllBranchPerformanceInfoByBranch(brnCD).observe(getViewLifecycleOwner(), eperformance ->{
            try {

                if(sales.equalsIgnoreCase("MC")) {
                    lgdGoal.setText("MC Goal");
                    lgdActual.setText("MC Actual");
                    lgdExcess.setText("MC Excess");
                } else if(sales.equalsIgnoreCase("SP")) {
                    lgdGoal.setText("SP Goal");
                    lgdActual.setText("SP Actual");
                    lgdExcess.setText("SP Excess");
                }

                BranchInfoBarChartAdapter poChartAd = new BranchInfoBarChartAdapter(eperformance, sales, eAreaPerformance -> {

                });
                poChartAd.notifyDataSetChanged();
                rvChart.setHasFixedSize(true);
                rvChart.setItemAnimator(new DefaultItemAnimator());
                rvChart.setLayoutManager(
                        new LinearLayoutManager(getActivity(),
                                LinearLayoutManager.HORIZONTAL,
                                false));
                rvChart.setAdapter(poChartAd);

                adapter = new BranchPerformanceAdapter(getActivity(), sales, eperformance);
                recyclerView.setHasFixedSize(true);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),  LinearLayoutManager.VERTICAL, false);
                linearLayoutManager.setReverseLayout(true);
                linearLayoutManager.setStackFromEnd(true);
                recyclerView.setLayoutManager(linearLayoutManager);

                recyclerView.setAdapter(adapter);
            } catch(Exception e) {
                e.printStackTrace();
            }
        });
    }

    private class TabClickHandler implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.item1){
                lblSelectd.animate().x(0).setDuration(100);
                lblItem1.setTextColor(Color.WHITE);
                lblItem2.setTextColor(poColor);
                mViewModel.setType("MC");
            } else if (view.getId() == R.id.item2){
                lblItem1.setTextColor(poColor);
                lblItem2.setTextColor(Color.WHITE);
                int size = lblItem2.getWidth();
                lblSelectd.animate().x(size).setDuration(100);
                mViewModel.setType("SP");
            }
        }
    }
}