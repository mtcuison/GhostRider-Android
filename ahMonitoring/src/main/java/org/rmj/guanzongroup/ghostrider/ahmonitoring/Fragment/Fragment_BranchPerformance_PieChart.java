/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 2/24/22, 9:04 AM
 * project file last modified : 2/24/22, 9:04 AM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Fragment;

import static org.rmj.g3appdriver.lib.BullsEye.BranchPerformancePeriod.getList;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.android.material.divider.MaterialDivider;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity.Activity_BranchPerformance;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Adapter.BranchPerformanceAdapter;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMBranchMonitor;

import java.util.ArrayList;

public class Fragment_BranchPerformance_PieChart extends Fragment {

    private VMBranchMonitor mViewModel;
    private PieChart pieChart;
    private RecyclerView recyclerView;
    private BranchPerformanceAdapter adapter;
    private String[] brnSales = {"MC Sales","SP Sales","JO Sales"};
    private String brnCD;
    ArrayList<Entry> chartValues;
    private LineChart lineChart;
    private MaterialTextView lblBranch, lgdGoal, lgdActual, lgdExcess;

    private ColorStateList poColor;
    private MaterialTextView lblItem1;
    private MaterialTextView lblItem2;
    private MaterialTextView lblSelectd;

    public Fragment_BranchPerformance_PieChart() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_branch_performance_pie_chart,
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
        initPieChart();
        mViewModel.getType().observe(getViewLifecycleOwner(), s -> setChartValue(s));
    }

    private void initViews(View v) {
        pieChart = v.findViewById(R.id.pie_chart);
        lblBranch = v.findViewById(R.id.tvBranch);
        recyclerView = v.findViewById(R.id.recyclerview_branch_performance);
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

    private void initPieChart(){
        pieChart.setUsePercentValues(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setRotationEnabled(false);
        pieChart.setRotationAngle(270f);
        pieChart.setHighlightPerTapEnabled(true);
        pieChart.setHoleRadius(0f);
        pieChart.setTransparentCircleRadius(0f);
        pieChart.setEntryLabelColor(Color.WHITE);
        pieChart.setCenterText("12 Months Performance");
        pieChart.setCenterTextSize(14f);
    }

    private void setChartValue(String sales){
        setChartData(sales);
        setTableData(sales);
    }

    private void setChartData(String sales) {
        mViewModel.get12MonthBranchPieChartData(brnCD, getList().get(getList().size()-1), getList().get(0)).observe(getViewLifecycleOwner(), monthlyPieChart -> {
            try {
                ArrayList<PieEntry> pieEntries = new ArrayList<>();
                ArrayList<Integer> colors = new ArrayList<>();
                String label = "type";

                if(sales.equalsIgnoreCase("MC")) {
                    lgdGoal.setText("MC Goal");
                    lgdActual.setText("MC Actual");
                    lgdExcess.setText("MC Excess");
                    pieEntries.clear();
                    colors.clear();
                    colors.add(Color.parseColor("#454545"));
                    colors.add(Color.parseColor("#FF8200"));
                    pieEntries.add(new PieEntry(monthlyPieChart.mcGoal, ""));
                    pieEntries.add(new PieEntry(monthlyPieChart.mcActual, ""));
                    Log.e("Butatog", String.valueOf(monthlyPieChart.mcActual));
                    if(monthlyPieChart.mcActual > monthlyPieChart.mcGoal) {
                        pieEntries.clear();
                        colors.clear();
                        colors.add(Color.parseColor("#FF8200"));
                        colors.add(Color.parseColor("#114F87"));
                        float lnActual = monthlyPieChart.mcActual;
                        float lnGoal = monthlyPieChart.mcGoal;
                        pieEntries.add(new PieEntry(monthlyPieChart.mcActual, ""));
                        pieEntries.add(new PieEntry(lnActual - lnGoal, ""));
                    }
                } else {
                    lgdGoal.setText("SP Goal");
                    lgdActual.setText("SP Actual");
                    lgdExcess.setText("SP Excess");
                    pieEntries.clear();
                    colors.clear();
                    colors.add(Color.parseColor("#454545"));
                    colors.add(Color.parseColor("#FF8200"));
                    pieEntries.add(new PieEntry(monthlyPieChart.spGoal, ""));
                    pieEntries.add(new PieEntry(monthlyPieChart.spActual, ""));
                    if(monthlyPieChart.spActual > monthlyPieChart.spGoal) {
                        pieEntries.clear();
                        colors.clear();
                        colors.add(Color.parseColor("#FF8200"));
                        colors.add(Color.parseColor("#114F87"));
                        float lnActual = monthlyPieChart.spActual;
                        float lnGoal = monthlyPieChart.spGoal;
                        pieEntries.add(new PieEntry(monthlyPieChart.spActual, ""));
                        pieEntries.add(new PieEntry(lnActual - lnGoal, ""));
                    }
                }

                PieDataSet pieDataSet = new PieDataSet(pieEntries,label);
                pieDataSet.setValueTextSize(12f);
                pieDataSet.setColors(colors);
                PieData pieData = new PieData(pieDataSet);
                pieData.setDrawValues(true);
                pieChart.getLegend().setEnabled(false);
                pieChart.setData(pieData);
                pieChart.invalidate();

            } catch(Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void setTableData(String sales) {
        mViewModel.getAllBranchPerformanceInfoByBranch(brnCD).observe(getViewLifecycleOwner(), eperformance ->{
            try {
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