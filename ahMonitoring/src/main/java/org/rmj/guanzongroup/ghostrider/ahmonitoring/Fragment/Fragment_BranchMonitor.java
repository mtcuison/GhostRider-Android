/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Fragment;

import static org.rmj.g3appdriver.etc.AppConstants.CHART_MONTH_LABEL;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textview.MaterialTextView;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DBranchPerformance;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMBranchMonitor;

import java.util.ArrayList;
import java.util.List;

public class Fragment_BranchMonitor extends Fragment {

    private VMBranchMonitor mViewModel;
    ArrayList<Entry> values1, values2, values3;

    private ArrayList<Entry> poActual, poGoalxx;
    private LineChart lineChart;
    private TabLayout tabLayout;
    private String BranchCd;
    private int width;
    private int height;
    private MaterialTextView sample;
    private MaterialCardView cvMCSales, cvSPSales, cvJobOrder;
    public static Fragment_BranchMonitor newInstance() {
        return new Fragment_BranchMonitor();
    }
    private CircularProgressIndicator mcIndicator,spIndicator,joIndicator;
    private MaterialTextView mcGoalPerc,spGoalPerc,joGoalPerc,mcFraction,spFraction,joFraction;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(VMBranchMonitor.class);
        View view = inflater.inflate(R.layout.fragment_branch_monitor, container, false);
        DisplayMetrics metrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;
        height = metrics.heightPixels;
        mcIndicator = view.findViewById(R.id.cpi_mc_sales);
        spIndicator = view.findViewById(R.id.cpi_sp_sales);
        joIndicator = view.findViewById(R.id.cpi_jo);

        mcGoalPerc = view.findViewById(R.id.lbl_mc_percentage);
        spGoalPerc = view.findViewById(R.id.lbl_sp_percentage);
        joGoalPerc = view.findViewById(R.id.lbl_jo_percentage);

        mcFraction = view.findViewById(R.id.lbl_mc_goal);
        spFraction = view.findViewById(R.id.lbl_sp_goal);
        joFraction = view.findViewById(R.id.lbl_jo_goal);

        cvMCSales = view.findViewById(R.id.cv_mc_sales);
        cvSPSales = view.findViewById(R.id.cv_sp_sales);
        cvJobOrder = view.findViewById(R.id.cv_jo);

        tabLayout = view.findViewById(R.id.tabLayout);
        lineChart = view.findViewById(R.id.activity_branch_linechart);

        tabLayout.addTab(tabLayout.newTab().setText("MC Sales"));
        tabLayout.addTab(tabLayout.newTab().setText("SP Sales"));
        tabLayout.addTab(tabLayout.newTab().setText("Job Order"));

        mViewModel.getEmployeeInfo().observe(getViewLifecycleOwner(), eEmployeeInfo -> {
            try {
                BranchCd = eEmployeeInfo.getBranchCD();

                mViewModel.GetMCSalesPeriodicPerformance(BranchCd).observe(getViewLifecycleOwner(),  BranchPerforamancebyMC -> {
                    try{
                        InitializeBranchList(BranchPerforamancebyMC, 0);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                });
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){

                    mViewModel.GetMCSalesPeriodicPerformance(BranchCd).observe(getViewLifecycleOwner(),  BranchPerforamancebyMC -> {
                        try{
                            InitializeBranchList(BranchPerforamancebyMC, tab.getPosition());

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    });
                } else if (tab.getPosition() == 1) {
                   mViewModel.GetSPSalesPeriodicPerformance(BranchCd).observe(getViewLifecycleOwner(),  BranchPerforamancebySP -> {
                        try{
                            InitializeBranchList(BranchPerforamancebySP, tab.getPosition());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    });
                } else{
                    mViewModel.GetJobOrderPeriodicPerformance(BranchCd).observe(getViewLifecycleOwner(),  BranchPerforamancebyJO -> {
                        try{
                            InitializeBranchList(BranchPerforamancebyJO, tab.getPosition());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    });
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        initGoalPercentage();
        return view;
    }

    private void InitializeBranchList(List<DBranchPerformance.PeriodicalPerformance> list, int priority){
        poActual = new ArrayList<>();
        poGoalxx = new ArrayList<>();
        for(int x = 0; x < list.size(); x ++){
            poActual.add(new Entry(x, Float.valueOf(list.get(x).Actual)));
            poGoalxx.add(new Entry(x, Float.valueOf(list.get(x).Goal)));
        }

        LineDataSet loActual = new LineDataSet(poActual, "Actual");
        LineDataSet loGoalxx = new LineDataSet(poGoalxx, "Goal");

        loActual.setLineWidth(2);
        loActual.setColors(getResources().getColor(R.color.guanzon_orange));

        loGoalxx.setLineWidth(2);
        loGoalxx.setColors(getResources().getColor(R.color.check_green));

        ArrayList<ILineDataSet> loDataSet = new ArrayList<>();
        loDataSet.add(loActual);
        loDataSet.add(loGoalxx);

        LineData data = new LineData(loDataSet);
        lineChart.setData(data);
        int color = AppConstants.getThemeTextColor(requireActivity());
        lineChart.getData().setValueTextColor(color);
        lineChart.getData().setValueTextColor(color);
        lineChart.getXAxis().setTextColor(color);
        lineChart.getAxisLeft().setTextColor(color);
        lineChart.getAxisRight().setTextColor(color);
        lineChart.getLegend().setTextColor(color);
        lineChart.setDescription(null);
        lineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(CHART_MONTH_LABEL));
        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.getXAxis().setTextSize(14f);
        lineChart.setExtraOffsets(0,0,10f,18f);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, (height/3));
        lineChart.setLayoutParams(params);
        lineChart.getLegend().setEnabled(false);
        lineChart.setDrawGridBackground(false);
        lineChart.setDrawBorders(true);
        lineChart.setBorderWidth(1);
        lineChart.setBorderColor(getResources().getColor(R.color.color_dadada));
        lineChart.animateX(500);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.invalidate();
    }

    public void initGoalPercentage(){
        mViewModel.GetCurrentMCSalesPerformance(BranchCd).observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String mc_goal) {

                try {
                    if(mc_goal == null){
                        return;
                    }
                    mcFraction.setText(mc_goal);
                    if (mc_goal.contains("/")) {
                        String[] rat = mc_goal.split("/");
                        if ((Double.parseDouble(rat[0]) == 0) || (Double.parseDouble(rat[1]) == 0)) {
                            mcGoalPerc.setText("0%");
                            mcIndicator.setProgress(0);
                        } else {
                            double ratio = Double.parseDouble(rat[0]) / Double.parseDouble(rat[1]) * 100;
                            mcGoalPerc.setText(String.valueOf(Math.round(ratio)) + "%");
                            mcIndicator.setProgress((int) (Math.round(ratio)));
                        }
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mViewModel.GetCurrentSPSalesPerformance(BranchCd).observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String sp_goal) {
                if(sp_goal == null){
                    return;
                }
                try {

                    spFraction.setText(sp_goal);
                    if (sp_goal.contains("/")) {
                        String[] rat = sp_goal.split("/");
                        if ((Double.parseDouble(rat[0]) == 0) || (Double.parseDouble(rat[1]) == 0)) {
                            spGoalPerc.setText("0%");
                            spIndicator.setProgress(0);
                        } else {
                            double ratio = Double.parseDouble(rat[0]) / Double.parseDouble(rat[1]) * 100;
                            spGoalPerc.setText(String.valueOf(Math.round(ratio)) + "%");
                            spIndicator.setProgress((int) (Math.round(ratio)));
                        }
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mViewModel.GetJobOrderPerformance(BranchCd).observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String jo_goal) {
                try {
                    if(jo_goal == null){
                        return;
                    }
                    joFraction.setText(jo_goal);
                    if (jo_goal.contains("/")) {
                        String[] rat = jo_goal.split("/");
                        if ((Double.parseDouble(rat[0]) == 0) || (Double.parseDouble(rat[1]) == 0)) {
                            joGoalPerc.setText("0%");
                            joIndicator.setProgress(0);
                        } else {
                            double ratio = Double.parseDouble(rat[0]) / Double.parseDouble(rat[1]) * 100;
                            joGoalPerc.setText(String.valueOf(Math.round(ratio)) + "%");
                            joIndicator.setProgress((int) (Math.round(ratio)));
                        }
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}