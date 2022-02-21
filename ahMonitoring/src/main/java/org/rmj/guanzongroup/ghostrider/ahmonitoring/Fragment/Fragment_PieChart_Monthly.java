/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 2/21/22, 9:35 AM
 * project file last modified : 2/21/22, 9:35 AM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Fragment;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DBranchPerformance;
import org.rmj.g3appdriver.GRider.Etc.BranchPerformancePeriod;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity.Activity_BranchPerformance;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Adapter.AreaPerformanceMonitoringAdapter;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMAreaPerfromanceMonitoring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fragment_PieChart_Monthly extends Fragment {
    private VMAreaPerfromanceMonitoring mViewModel;
    private PieChart pieChart;
    private RecyclerView recyclerView;
    private AreaPerformanceMonitoringAdapter poAdapter;
    private ArrayList<String> poPeriods = new ArrayList<>();
    private String[] brnSales = {"MC Sales","SP Sales","JO Sales"};
    private TextView lblArea, lblDate;
    private TextView lblItem1;
    private TextView lblItem2;
    private TextView lblSelectd;
    private ColorStateList poColor;

    public Fragment_PieChart_Monthly() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pie_chart_monthly, container, false);

        initWidgets(v);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(VMAreaPerfromanceMonitoring.class);
        mViewModel.getAreaNameFromCode().observe(getActivity(), sAreaName-> {
            try {
                lblArea.setText(sAreaName);
            } catch(NullPointerException e) {
                e.printStackTrace();
            }
        });

        poPeriods = BranchPerformancePeriod.getSortedPeriodList(BranchPerformancePeriod.getList());
        initPieChart();
        mViewModel.getType().observe(getActivity(), s -> setChartValue(s, "202201"));
    }

    private void initWidgets(View v) {
        pieChart = v.findViewById(R.id.pie_chart);
        lblArea = v.findViewById(R.id.tvArea);
        lblDate = v.findViewById(R.id.lbl_date);
        recyclerView = v.findViewById(R.id.recyclerview_area_performance);
        lblItem1 = v.findViewById(R.id.item1);
        lblItem2 = v.findViewById(R.id.item2);
        lblSelectd = v.findViewById(R.id.select);
        poColor = lblItem2.getTextColors();
        lblItem1.setOnClickListener(new TabClickHandler());
        lblItem2.setOnClickListener(new TabClickHandler());
    }

    private void initPieChart(){
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setRotationEnabled(false);
        pieChart.setDragDecelerationFrictionCoef(0.9f);
        pieChart.setRotationAngle(0);
        pieChart.setHighlightPerTapEnabled(true);
        pieChart.animateY(1400, Easing.EaseInOutQuad);
        pieChart.setHoleRadius(40f);
        pieChart.setTransparentCircleRadius(45f);
    }

    public void setChartValue(String sales, String fsPeriodx){

        mViewModel.getMonthlyPieChartData(fsPeriodx).observe(getActivity(), monthlyPieChart -> {
            try {
                ArrayList<PieEntry> pieEntries = new ArrayList<>();
                String label = "type";
                if(sales.equalsIgnoreCase("MC")) {
                    pieEntries.clear();
                    pieEntries.add(new PieEntry(monthlyPieChart.mcGoal, "MC Goal"));
                    pieEntries.add(new PieEntry(monthlyPieChart.mcActual, "MC Actual"));
                    if(monthlyPieChart.mcActual > monthlyPieChart.mcGoal) {
                        // Remove this if you want to make it as ratio type.
                        pieEntries.clear();
                        int lnActual = monthlyPieChart.mcActual;
                        int lnGoal = monthlyPieChart.mcGoal;
                        pieEntries.add(new PieEntry(lnActual - lnGoal, "Exceeded MC Sales"));
                    }
                } else {
                    pieEntries.clear();
                    pieEntries.add(new PieEntry(monthlyPieChart.spGoal, "SP Goal"));
                    pieEntries.add(new PieEntry(monthlyPieChart.spActual, "SP Actual"));
                    if(monthlyPieChart.spActual > monthlyPieChart.spGoal) {
                        // Remove this if you want to make it as ratio type.
                        pieEntries.clear();
                        int lnActual = monthlyPieChart.spActual;
                        int lnGoal = monthlyPieChart.spGoal;
                        pieEntries.add(new PieEntry(lnActual - lnGoal, "Exceeded SP Sales"));
                    }
                }

                ArrayList<Integer> colors = new ArrayList<>();
                colors.add(Color.parseColor("#454545"));
                colors.add(Color.parseColor("#FF8200"));
                colors.add(Color.parseColor("#114F87"));
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

        mViewModel.getAreaBranchesSalesPerformance(fsPeriodx).observe(getActivity(), branchPerformances -> {
            try {
                poAdapter = new AreaPerformanceMonitoringAdapter(
                        getActivity(), sales,
                        branchPerformances, sBranchCd -> {
                    try {
                        Intent loIntent = new Intent(
                                getActivity(),
                                Activity_BranchPerformance.class);
                        loIntent.putExtra("brnCD", sBranchCd);
                        startActivity(loIntent);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
                recyclerView.setHasFixedSize(true);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(poAdapter);
                poAdapter.notifyDataSetChanged();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

    }

    private String getPeriodText(String fsPeriodx) {
        String lsYearNox = fsPeriodx.substring(0,4);
        String lsMonthNo = "";
        String lsMonthNm = "";


        if (fsPeriodx.length() > 2)
        {
            lsMonthNo = fsPeriodx.substring(fsPeriodx.length() - 2);
        }
        else
        {
            lsMonthNo = fsPeriodx;
        }

        switch(lsMonthNo) {
            case "01":
                lsMonthNm = "January";
                break;
            case "02":
                lsMonthNm = "February";
                break;
            case "03":
                lsMonthNm = "March";
                break;
            case "04":
                lsMonthNm = "April";
                break;
            case "05":
                lsMonthNm = "May";
                break;
            case "06":
                lsMonthNm = "June";
                break;
            case "07":
                lsMonthNm = "July";
                break;
            case "08":
                lsMonthNm = "August";
                break;
            case "09":
                lsMonthNm = "September";
                break;
            case "10":
                lsMonthNm = "October";
                break;
            case "11":
                lsMonthNm = "November";
                break;
            case "12":
                lsMonthNm = "December";
                break;
            default:
                lsMonthNm = "";
                break;
        }

        return lsMonthNm + " " + lsYearNox;
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