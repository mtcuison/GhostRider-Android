/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 2/18/22, 1:27 PM
 * project file last modified : 2/18/22, 1:27 PM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Fragment;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.google.android.material.textview.MaterialTextView;

import org.rmj.g3appdriver.lib.BullsEye.PerformancePeriod;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Adapter.AreaPerformanceMonitoringAdapter;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMAreaPerfromanceMonitoring;

import java.util.ArrayList;

/** This is the default chart to be displayed when opening area performance.
 * */

public class Fragment_AreaPerformance_LineChart extends Fragment {
    private static final String TAG = Fragment_AreaPerformance_LineChart.class.getSimpleName();
    private VMAreaPerfromanceMonitoring mViewModel;
    private RecyclerView recyclerView;
    private AreaPerformanceMonitoringAdapter poAdapter;
    private ArrayList<String> poPeriods = new ArrayList<>();
    private String[] brnSales = {"MC Sales","SP Sales","JO Sales"};
    ArrayList<Entry> chartValues;
    private LineChart lineChart;
    public int width;
    public int height;
    private TextView lblArea, lblDate;

    private ColorStateList poColor;
    private MaterialTextView lblItem1,lblItem2,lblSelectd;

    public Fragment_AreaPerformance_LineChart() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_area_performance_line_chart, container, false);
        initWidgets(view);
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;
        height = metrics.heightPixels;
        mViewModel = new ViewModelProvider(requireActivity()).get(VMAreaPerfromanceMonitoring.class);
        mViewModel.getAreaNameFromCode().observe(getViewLifecycleOwner(), sAreaName-> {
            try {
                lblArea.setText(sAreaName);
            } catch(NullPointerException e) {
                e.printStackTrace();
            }
        });
        poPeriods = PerformancePeriod.getSortedPeriodList(PerformancePeriod.getList());
        mViewModel.getType().observe(getViewLifecycleOwner(), s -> setChartValue(s));
        return view;
    }

    private void initWidgets(View v){
        lblArea = v.findViewById(R.id.tvArea);
        lblDate = v.findViewById(R.id.lbl_date);
        recyclerView = v.findViewById(R.id.recyclerview_area_performance);
        lineChart = v.findViewById(R.id.activity_area_linechart);

        lblItem1 = v.findViewById(R.id.item1);
        lblItem2 = v.findViewById(R.id.item2);
        lblSelectd = v.findViewById(R.id.select);
        poColor = lblItem2.getTextColors();
        lblItem1.setOnClickListener(new TabClickHandler());
        lblItem2.setOnClickListener(new TabClickHandler());
    }

    public void setChartValue(String sales){
//        mViewModel.getAreaPerformanceInfoList().observe(getViewLifecycleOwner(), performances -> {
//            try {
//                chartValues = new ArrayList<>();
//                if (sales.equalsIgnoreCase("MC")){
//                    for (int x = 0; x< performances.size(); x++){
//                        chartValues.add(new Entry(x, performances.get(x).getMCActual()));
//                    }
//                    AreaPerformanceMonitoringAdapter.setIndexPosition(-1);
//                }else {
//                    for (int x = 0; x< performances.size(); x++){
//                        chartValues.add(new Entry(x, performances.get(x).getSPActual()));
//                    }
//
//                    AreaPerformanceMonitoringAdapter.setIndexPosition(-1);
//                }
//                LineDataSet lineDataSet1 = new LineDataSet(chartValues, "");
//
//                // Set line attributes
//                lineDataSet1.setLineWidth(2);
//                lineDataSet1.setColors(getResources().getColor(R.color.guanzon_orange));
//                //ArrayList<ILineDataSet> Contains list of LineDataSets
//                ArrayList<ILineDataSet> dataSets = new ArrayList<>();
//                dataSets.add(lineDataSet1);
//
//
//                // LineData Contains ArrayList<ILineDataSet>
//                LineData data = new LineData(dataSets);
//                lineChart.setData(data);
//                lineChart.setDescription(null);
//                lineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(PerformancePeriod.getAreaTableLabel(performances)));
//                lineChart.setDoubleTapToZoomEnabled(false);
//                lineChart.getXAxis().setTextSize(12f);
//                lineChart.setExtraOffsets(0,0,10f,18f);
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, (height/3));
//                lineChart.setLayoutParams(params);
//                lineChart.getLegend().setEnabled(false);
//                lineChart.setDrawGridBackground(false);
//                lineChart.setDrawBorders(true);
//                lineChart.setBorderWidth(1);
//                lineChart.setBorderColor(getResources().getColor(R.color.color_dadada));
//                lineChart.animateX(500);
//                XAxis xAxis = lineChart.getXAxis();
//                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//                lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
//                    @Override
//                    public void onValueSelected(Entry e, Highlight h) {
////                            AreaPerformanceMonitoringAdapter.setIndexPosition((int) e.getX());
//                        lblDate.setText(getPeriodText(poPeriods.get((int) e.getX())));
//                        mViewModel.getAreaBranchesSalesPerformance(poPeriods.get((int) e.getX()), sales).observe(getViewLifecycleOwner(), branchPerformances -> {
//                            try {
//                                poAdapter = new AreaPerformanceMonitoringAdapter(
//                                        getActivity(), sales,
//                                        branchPerformances, sBranchCd -> {
//                                    try {
//                                        Intent loIntent = new Intent(
//                                                getActivity(),
//                                                Activity_BranchPerformance.class);
//                                        loIntent.putExtra("brnCD", sBranchCd);
//                                        startActivity(loIntent);
//                                    } catch (Exception ex) {
//                                        ex.printStackTrace();
//                                    }
//                                });
//                                recyclerView.setHasFixedSize(true);
//                                recyclerView.setItemAnimator(new DefaultItemAnimator());
//                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
//                                recyclerView.setAdapter(poAdapter);
//                            } catch (Exception ex) {
//                                ex.printStackTrace();
//                            }
//                        });
//                        poAdapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onNothingSelected() {
//
//                    }
//                });
//                lineChart.invalidate();
////          SET RECYLERVIEW
//                lblDate.setText(getPeriodText(PerformancePeriod.getLatestCompletePeriod()));
//                mViewModel.getAreaBranchesSalesPerformance(PerformancePeriod.getLatestCompletePeriod(),sales).observe(getViewLifecycleOwner(), branchPerformances -> {
//                    try {
//                        poAdapter = new AreaPerformanceMonitoringAdapter(
//                                getActivity(), sales,
//                                branchPerformances, sBranchCd -> {
//                            try {
//                                Intent loIntent = new Intent(
//                                        getActivity(),
//                                        Activity_BranchPerformance.class);
//                                loIntent.putExtra("brnCD", sBranchCd);
//                                startActivity(loIntent);
//                            } catch (NullPointerException e) {
//                                e.printStackTrace();
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        });
//                        recyclerView.setHasFixedSize(true);
//                        recyclerView.setItemAnimator(new DefaultItemAnimator());
//                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),  LinearLayoutManager.VERTICAL, false));
//                        recyclerView.setAdapter(poAdapter);
//                    } catch(NullPointerException e) {
//                        e.printStackTrace();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                });
//            } catch (NullPointerException e) {
//                e.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
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