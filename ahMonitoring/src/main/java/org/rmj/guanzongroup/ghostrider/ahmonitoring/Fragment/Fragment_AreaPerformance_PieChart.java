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

import static org.rmj.g3appdriver.lib.BullsEye.PerformancePeriod.getLatestCompletePeriod;
import static org.rmj.g3appdriver.lib.BullsEye.PerformancePeriod.getList;
import static org.rmj.g3appdriver.lib.BullsEye.PerformancePeriod.getPeriodText;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.PieChart;
import com.google.android.material.textview.MaterialTextView;

import org.rmj.g3appdriver.lib.BullsEye.PerformancePeriod;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Adapter.AreaPerformanceMonitoringAdapter;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMAreaPerfromanceMonitoring;

import java.util.ArrayList;

public class Fragment_AreaPerformance_PieChart extends Fragment {

    private VMAreaPerfromanceMonitoring mViewModel;
    private PieChart pieChart, pieChart2;
    private RecyclerView recyclerView;
    private AreaPerformanceMonitoringAdapter poAdapter;
    private ArrayList<String> poPeriods = new ArrayList<>();
    private String[] brnSales = {"MC Sales","SP Sales","JO Sales"};
    private MaterialTextView lblArea, lblDate, lblItem1, lblItem2, lblSelectd, lgdGoal, lgdActual, lgdExcess;
    private ColorStateList poColor;
    private RadioGroup rgPeriodx;

    public Fragment_AreaPerformance_PieChart() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_area_performance_pie_chart, container, false);
        initWidgets(v);
        mViewModel = new ViewModelProvider(requireActivity()).get(VMAreaPerfromanceMonitoring.class);
        mViewModel.getAreaNameFromCode().observe(getViewLifecycleOwner(), sAreaName-> {
            try {
                lblArea.setText(sAreaName);
            } catch(NullPointerException e) {
                e.printStackTrace();
            }
        });

        poPeriods = PerformancePeriod.getSortedPeriodList(getList());
        initPieChart();
        mViewModel.getType().observe(getViewLifecycleOwner(), s -> setChartValue(s, getLatestCompletePeriod()));
        rgPeriodx.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.rb_month) {
                    pieChart.setVisibility(View.VISIBLE);
                    pieChart2.setVisibility(View.GONE);
                } else if(i == R.id.rb_year) {
                    pieChart.setVisibility(View.GONE);
                    pieChart2.setVisibility(View.VISIBLE);
                }
            }
        });
        return v;
    }

    private void initWidgets(View v) {
        pieChart = v.findViewById(R.id.pie_chart_month);
        pieChart2 = v.findViewById(R.id.pie_chart_year);
        lblArea = v.findViewById(R.id.tvArea);
        lblDate = v.findViewById(R.id.lbl_date);
        recyclerView = v.findViewById(R.id.recyclerview_area_performance);
        lgdGoal = v.findViewById(R.id.lgd_goal);
        lgdActual = v.findViewById(R.id.lgd_actual);
        lgdExcess = v.findViewById(R.id.lgd_excess);
        lblItem1 = v.findViewById(R.id.item1);
        lblItem2 = v.findViewById(R.id.item2);
        lblSelectd = v.findViewById(R.id.select);
        poColor = lblItem2.getTextColors();
        rgPeriodx = v.findViewById(R.id.rg_period);
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
        pieChart.setCenterText(getPeriodText(getLatestCompletePeriod()) + " Performance");
        pieChart.setCenterTextSize(14f);

        pieChart2.setUsePercentValues(false);
        pieChart2.getDescription().setEnabled(false);
        pieChart2.setRotationEnabled(false);
        pieChart2.setRotationAngle(270f);
        pieChart2.setHighlightPerTapEnabled(true);
        pieChart2.setHoleRadius(0f);
        pieChart2.setTransparentCircleRadius(0f);
        pieChart2.setEntryLabelColor(Color.WHITE);
        pieChart2.setCenterText("12 Months Performance");
        pieChart2.setCenterTextSize(14f);

    }

    public void setChartValue(String sales, String fsPeriodx){
        /**Pie Chart Data*/
        setMonthPieChartData(sales, fsPeriodx);
        setYearPieChartData(sales);

        /** RecyclerView Data*/
        lblDate.setText(getPeriodText(fsPeriodx));
        setTableData(sales, fsPeriodx);
    }

    private void setMonthPieChartData(String sales, String fsPeriodx) {
//        mViewModel.getMonthlyPieChartData(fsPeriodx).observe(getViewLifecycleOwner(), monthlyPieChart -> {
//            try {
//                ArrayList<PieEntry> pieEntries = new ArrayList<>();
//                ArrayList<Integer> colors = new ArrayList<>();
//                String label = "type";
//
//                if(sales.equalsIgnoreCase("MC")) {
//                    lgdGoal.setText("MC Goal");
//                    lgdActual.setText("MC Actual");
//                    lgdExcess.setText("MC Excess");
//                    pieEntries.clear();
//                    colors.clear();
//                    colors.add(Color.parseColor("#454545"));
//                    colors.add(Color.parseColor("#FF8200"));
//                    pieEntries.add(new PieEntry(monthlyPieChart.mcGoal, ""));
//                    pieEntries.add(new PieEntry(monthlyPieChart.mcActual, ""));
//                    if(monthlyPieChart.mcActual > monthlyPieChart.mcGoal) {
//                        pieEntries.clear();
//                        colors.clear();
//                        colors.add(Color.parseColor("#FF8200"));
//                        colors.add(Color.parseColor("#114F87"));
//                        float lnActual = monthlyPieChart.mcActual;
//                        float lnGoal = monthlyPieChart.mcGoal;
//                        pieEntries.add(new PieEntry(monthlyPieChart.mcActual, ""));
//                        pieEntries.add(new PieEntry(lnActual - lnGoal, ""));
//                    }
//                } else {
//                    lgdGoal.setText("SP Goal");
//                    lgdActual.setText("SP Actual");
//                    lgdExcess.setText("SP Excess");
//                    pieEntries.clear();
//                    colors.clear();
//                    colors.add(Color.parseColor("#454545"));
//                    colors.add(Color.parseColor("#FF8200"));
//                    pieEntries.add(new PieEntry(monthlyPieChart.spGoal, ""));
//                    pieEntries.add(new PieEntry(monthlyPieChart.spActual, ""));
//                    if(monthlyPieChart.spActual > monthlyPieChart.spGoal) {
//                        pieEntries.clear();
//                        colors.clear();
//                        colors.add(Color.parseColor("#FF8200"));
//                        colors.add(Color.parseColor("#114F87"));
//                        float lnActual = monthlyPieChart.spActual;
//                        float lnGoal = monthlyPieChart.spGoal;
//                        pieEntries.add(new PieEntry(monthlyPieChart.spActual, ""));
//                        pieEntries.add(new PieEntry(lnActual - lnGoal, ""));
//                    }
//                }
//
//                PieDataSet pieDataSet = new PieDataSet(pieEntries,label);
//                pieDataSet.setValueTextSize(12f);
//                pieDataSet.setColors(colors);
//                PieData pieData = new PieData(pieDataSet);
//                pieData.setDrawValues(true);
//                pieChart.getLegend().setEnabled(false);
//                pieChart.setData(pieData);
//                pieChart.invalidate();
//
//            } catch(Exception e) {
//                e.printStackTrace();
//            }
//        });
    }

    private void setYearPieChartData(String sales) {
//        mViewModel.get12MonthPieChartData(getList().get(getList().size()-1), getList().get(0)).observe(getViewLifecycleOwner(), monthlyPieChart -> {
//            try {
//                ArrayList<PieEntry> pieEntries = new ArrayList<>();
//                ArrayList<Integer> colors = new ArrayList<>();
//                String label = "type";
//
//                if(sales.equalsIgnoreCase("MC")) {
//                    lgdGoal.setText("MC Goal");
//                    lgdActual.setText("MC Actual");
//                    lgdExcess.setText("MC Excess");
//                    pieEntries.clear();
//                    colors.clear();
//                    colors.add(Color.parseColor("#454545"));
//                    colors.add(Color.parseColor("#FF8200"));
//                    pieEntries.add(new PieEntry(monthlyPieChart.mcGoal, ""));
//                    pieEntries.add(new PieEntry(monthlyPieChart.mcActual, ""));
//                    if(monthlyPieChart.mcActual > monthlyPieChart.mcGoal) {
//                        pieEntries.clear();
//                        colors.clear();
//                        colors.add(Color.parseColor("#FF8200"));
//                        colors.add(Color.parseColor("#114F87"));
//                        float lnActual = monthlyPieChart.mcActual;
//                        float lnGoal = monthlyPieChart.mcGoal;
//                        pieEntries.add(new PieEntry(monthlyPieChart.mcActual, ""));
//                        pieEntries.add(new PieEntry(lnActual - lnGoal, ""));
//                    }
//                } else {
//                    lgdGoal.setText("SP Goal");
//                    lgdActual.setText("SP Actual");
//                    lgdExcess.setText("SP Excess");
//                    pieEntries.clear();
//                    colors.clear();
//                    colors.add(Color.parseColor("#454545"));
//                    colors.add(Color.parseColor("#FF8200"));
//                    pieEntries.add(new PieEntry(monthlyPieChart.spGoal, ""));
//                    pieEntries.add(new PieEntry(monthlyPieChart.spActual, ""));
//                    if(monthlyPieChart.spActual > monthlyPieChart.spGoal) {
//                        pieEntries.clear();
//                        colors.clear();
//                        colors.add(Color.parseColor("#FF8200"));
//                        colors.add(Color.parseColor("#114F87"));
//                        float lnActual = monthlyPieChart.spActual;
//                        float lnGoal = monthlyPieChart.spGoal;
//                        pieEntries.add(new PieEntry(monthlyPieChart.spActual, ""));
//                        pieEntries.add(new PieEntry(lnActual - lnGoal, ""));
//                    }
//                }
//
//                PieDataSet pieDataSet = new PieDataSet(pieEntries,label);
//                pieDataSet.setValueTextSize(12f);
//                pieDataSet.setColors(colors);
//                PieData pieData = new PieData(pieDataSet);
//                pieData.setDrawValues(true);
//                pieChart2.getLegend().setEnabled(false);
//                pieChart2.setData(pieData);
//                pieChart2.invalidate();
//            } catch(Exception e) {
//                e.printStackTrace();
//            }
//        });
    }

    private void setTableData(String sales, String fsPeriodx) {
//        mViewModel.getAreaBranchesSalesPerformance(fsPeriodx, sales).observe(getViewLifecycleOwner(), branchPerformances -> {
//            try {
//                poAdapter = new AreaPerformanceMonitoringAdapter(
//                        getActivity(), sales,
//                        branchPerformances, sBranchCd -> {
//                    try {
//                        Intent loIntent = new Intent(
//                                getActivity(),
//                                Activity_BranchPerformance.class);
//                        loIntent.putExtra("brnCD", sBranchCd);
//                        startActivity(loIntent);
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//                });
//                recyclerView.setHasFixedSize(true);
//                recyclerView.setItemAnimator(new DefaultItemAnimator());
//                recyclerView.setLayoutManager(
//                        new LinearLayoutManager(getActivity(),
//                                LinearLayoutManager.VERTICAL,
//                                false));
//                recyclerView.setAdapter(poAdapter);
//                poAdapter.notifyDataSetChanged();
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        });
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