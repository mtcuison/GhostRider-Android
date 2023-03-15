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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textview.MaterialTextView;

import org.rmj.g3appdriver.dev.Database.Entities.EBranchPerformance;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMBranchMonitor;

import java.util.ArrayList;
import java.util.List;

public class Fragment_BranchMonitor extends Fragment {

    private VMBranchMonitor mViewModel;
    ArrayList<Entry> values1, values2, values3;
    private LineChart lineChart;
    private TabLayout tabLayout;
    private String BranchCd;
    private MaterialTextView sample;

    public static Fragment_BranchMonitor newInstance() {
        return new Fragment_BranchMonitor();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(VMBranchMonitor.class);
        View view = inflater.inflate(R.layout.fragment_branch_monitor, container, false);

        tabLayout = view.findViewById(R.id.tabLayout);
        lineChart = view.findViewById(R.id.activity_branch_linechart);
        tabLayout.addTab(tabLayout.newTab().setText("MC Sales"));
        tabLayout.addTab(tabLayout.newTab().setText("SP Sales"));
        tabLayout.addTab(tabLayout.newTab().setText("Joborder"));
//        initWidgets(view);
        mViewModel.getEmployeeInfo().observe(getViewLifecycleOwner(), eEmployeeInfo -> {
            try {
                //lblEmail.setText(eEmployeeInfo.getEmailAdd());
//                lblUserLvl.setText(DeptCode.parseUserLevel(eEmployeeInfo.getEmpLevID()));
                BranchCd = eEmployeeInfo.getBranchCD();

            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getAllBranchPerformanceInfoByBranch(BranchCd).observe(getViewLifecycleOwner(),  BranchPerforamancebyMC -> {
            try{
                InitializeBranchList(BranchPerforamancebyMC, 0);
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){

                    mViewModel.getAllBranchPerformanceInfoByBranch(BranchCd).observe(getViewLifecycleOwner(),  BranchPerforamancebyMC -> {
                        try{
                            InitializeBranchList(BranchPerforamancebyMC, tab.getPosition());

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    });
                } else if (tab.getPosition() == 1) {
                   mViewModel.getAllBranchPerformanceInfoByBranch(BranchCd).observe(getViewLifecycleOwner(),  BranchPerforamancebySP -> {
                        try{
                            InitializeBranchList(BranchPerforamancebySP, tab.getPosition());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    });
                } else{
                    mViewModel.getAllBranchPerformanceInfoByBranch(BranchCd).observe(getViewLifecycleOwner(),  BranchPerforamancebySP -> {
                        try{
                            InitializeBranchList(BranchPerforamancebySP, tab.getPosition());
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
        return view;
    }

    private void InitializeBranchList(List<EBranchPerformance> list, int priority){

//            AreaMonitoringAdapter loAdapter = new AreaMonitoringAdapter(list, priority, new AreaMonitoringAdapter.OnBranchPerformanceClickListener() {
//                @Override
//                public void OnClick(String sBranchCd) {
//                    Intent loIntent = new Intent(getActivity(), Activity_BranchPerformance.class);
//                    loIntent.putExtra("brnCD", sBranchCd);
//                    startActivity(loIntent);
//                }
//            });
//            LinearLayoutManager loManager = new LinearLayoutManager(getActivity());
//            loManager.setOrientation(RecyclerView.VERTICAL);
//        recyclerView.setLayoutManager(loManager);
//        recyclerView.setAdapter(loAdapter);
//        recyclerView.setVisibility(View.VISIBLE);
//        lblContainer.setVisibility(View.GONE);
            Description desc = new Description();
            desc.setText("Over All Sales");
            desc.setTextSize(28);
            Log.e("teejei", String.valueOf(list.size()));
            values1 = new ArrayList<>();
            values2 = new ArrayList<>();
            values3 = new ArrayList<>();
            for (int x = 0; x< list.size(); x++){
                EBranchPerformance info = list.get(x);
                values1.add(new Entry(info.getMCGoalxx(), info.getMCActual()));
                values2.add(new Entry(x, info.getSPGoalxx()));
                values3.add(new Entry(x, info.getJOGoalxx()));

//                Log.e("MC", String.valueOf(info.getMCActual()));
//                Log.e("Val1", info.getBranchCd() + " " +  info.getMCGoalxx());
//                Log.e("Val2", info.getBranchNm() + " " +  info.getSPGoalxx());
//                Log.e("Val3", info.getBranchCd() + " " +  info.getJOGoalxx());
            }
            LineDataSet lineDataSet1 = new LineDataSet(values1, "MC Sales");
            LineDataSet lineDataSet2 = new LineDataSet(values2, "SP Sales");
            LineDataSet lineDataSet3 = new LineDataSet(values3, "JO Sales");

            // Set line attributes
            lineDataSet1.setLineWidth(4);
            lineDataSet2.setLineWidth(4);
            lineDataSet3.setLineWidth(4);
            lineDataSet1.setColors(getResources().getColor(R.color.guanzon_orange));
            lineDataSet2.setColors(getResources().getColor(R.color.guanzon_deep_dark_grey));
            lineDataSet3.setColors(getResources().getColor(R.color.guanzon_dark_grey));

            //ArrayList<ILineDataSet> Contains list of LineDataSets
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            if(priority == 0){
                dataSets.add(lineDataSet1);
            }else if(priority == 1){
                dataSets.add(lineDataSet2);
            }else if(priority == 2){
                dataSets.add(lineDataSet3);
            }
//            dataSets.add(lineDataSet2);
//            dataSets.add(lineDataSet3);

            // LineData Contains ArrayList<ILineDataSet>
            LineData lineData = new LineData(dataSets);
            Log.e("sample",lineData.getDataSets().toString());
            lineChart.setData(lineData);
            lineChart.invalidate();
            lineChart.setDescription(desc);
            lineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(CHART_MONTH_LABEL));
            lineChart.setDoubleTapToZoomEnabled(false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMBranchMonitor.class);


//        mViewModel.getAllBranchPerformanceInfoByBranch(Activity_Monitoring.getInstance().getAreaCode()).observe(getViewLifecycleOwner(),eperformance ->{
//            Description desc = new Description();
//            desc.setText("Over All Sales");
//            desc.setTextSize(28);
//            for (int x = 0; x< eperformance.size(); x++){
//                values1.add(new Entry(x, eperformance.get(x).getSPActual()));
//                values2.add(new Entry(x, eperformance.get(x).getMCActual()));
//                values3.add(new Entry(x, eperformance.get(x).getJOGoalxx()));
//                Log.e("MC", String.valueOf(eperformance.get(x).getMCActual()));
//
//            }
//            LineDataSet lineDataSet1 = new LineDataSet(values1, "SP Sales");
//            LineDataSet lineDataSet2 = new LineDataSet(values2, "MC Sales");
//            LineDataSet lineDataSet3 = new LineDataSet(values3, "JO Sales");
//
//            // Set line attributes
//            lineDataSet1.setLineWidth(2);
//            lineDataSet2.setLineWidth(2);
//            lineDataSet3.setLineWidth(2);
//            lineDataSet1.setColors(getResources().getColor(R.color.guanzon_orange));
//            lineDataSet2.setColors(getResources().getColor(R.color.guanzon_deep_dark_grey));
//            lineDataSet3.setColors(getResources().getColor(R.color.guanzon_dark_grey));
//
//            //ArrayList<ILineDataSet> Contains list of LineDataSets
//            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
//            dataSets.add(lineDataSet1);
//            dataSets.add(lineDataSet2);
//            dataSets.add(lineDataSet3);
//
//            // LineData Contains ArrayList<ILineDataSet>
//            LineData data = new LineData(dataSets);
//            lineChart.setData(data);
//            lineChart.invalidate();
//            lineChart.setDescription(desc);
//            lineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(CHART_MONTH_LABEL));
//            lineChart.setDoubleTapToZoomEnabled(false);
//
//
//        });
        // TODO: Use the ViewModel
    }



}