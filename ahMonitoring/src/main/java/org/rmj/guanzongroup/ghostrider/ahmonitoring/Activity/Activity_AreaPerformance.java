/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 10/14/21, 10:43 AM
 * project file last modified : 10/14/21, 10:43 AM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity;

import static org.rmj.g3appdriver.GRider.Constants.AppConstants.CHART_MONTH_LABEL;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.GRider.Etc.BranchPerformancePeriod;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Adaper.AreaPerformanceMonitoringAdapter;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMAreaPerfromanceMonitoring;

import java.util.ArrayList;
import java.util.Objects;

public class Activity_AreaPerformance extends AppCompatActivity{
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
    private TextView lblItem1;
    private TextView lblItem2;
    private TextView lblSelectd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_performance);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;
        height = metrics.heightPixels;
        mViewModel = new ViewModelProvider(Activity_AreaPerformance.this).get(VMAreaPerfromanceMonitoring.class);
        mViewModel.getAreaNameFromCode().observe(Activity_AreaPerformance.this, sAreaName-> {
            try {
                lblArea.setText(sAreaName);
            } catch(NullPointerException e) {
                e.printStackTrace();
            }
        });
        poPeriods = BranchPerformancePeriod.getList();
        initWidgets();
        mViewModel.getType().observe(this, s -> setChartValue(s));
    }
    private void initWidgets(){
        lblArea = findViewById(R.id.tvArea);
        lblDate = findViewById(R.id.lbl_date);
        recyclerView = findViewById(R.id.recyclerview_area_performance);
        lineChart = findViewById(R.id.activity_area_linechart);
        Toolbar toolbar = findViewById(R.id.toolbar_area_performance);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        lblItem1 = findViewById(R.id.item1);
        lblItem2 = findViewById(R.id.item2);
        lblSelectd = findViewById(R.id.select);
        poColor = lblItem2.getTextColors();
        lblItem1.setOnClickListener(new TabClickHandler());
        lblItem2.setOnClickListener(new TabClickHandler());
    }

    public void setChartValue(String sales){
        mViewModel.getAreaPerformanceInfoList().observe(Activity_AreaPerformance.this, performances -> {
            try {
                chartValues = new ArrayList<>();
                if (sales.equalsIgnoreCase("MC")){
                    for (int x = 0; x< performances.size(); x++){
                        chartValues.add(new Entry(x, performances.get(x).getMCActual()));
                    }
                    AreaPerformanceMonitoringAdapter.setIndexPosition(-1);
                }else {
                    for (int x = 0; x< performances.size(); x++){
                        chartValues.add(new Entry(x, performances.get(x).getSPActual()));
                    }

                    AreaPerformanceMonitoringAdapter.setIndexPosition(-1);
                }
                LineDataSet lineDataSet1 = new LineDataSet(chartValues, "");

                // Set line attributes
                lineDataSet1.setLineWidth(2);
                lineDataSet1.setColors(getResources().getColor(R.color.guanzon_orange));
                //ArrayList<ILineDataSet> Contains list of LineDataSets
                ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                dataSets.add(lineDataSet1);


                // LineData Contains ArrayList<ILineDataSet>
                LineData data = new LineData(dataSets);
                lineChart.setData(data);
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
                lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                    @Override
                    public void onValueSelected(Entry e, Highlight h) {
//                            AreaPerformanceMonitoringAdapter.setIndexPosition((int) e.getX());
                        lblDate.setText(getPeriodText(poPeriods.get((int) e.getX())));
                        mViewModel.getAreaBranchesSalesPerformance(poPeriods.get((int) e.getX())).observe(Activity_AreaPerformance.this, branchPerformances -> {
                            try {
                                poAdapter = new AreaPerformanceMonitoringAdapter(
                                        Activity_AreaPerformance.this, sales,
                                        branchPerformances, sBranchCd -> {
                                    try {
                                        Intent loIntent = new Intent(
                                                Activity_AreaPerformance.this,
                                                Activity_BranchPerformance.class);
                                        loIntent.putExtra("brnCD", sBranchCd);
                                        startActivity(loIntent);
                                    } catch (NullPointerException ex) {
                                        ex.printStackTrace();
                                    }
                                });
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setLayoutManager(new LinearLayoutManager(Activity_AreaPerformance.this, LinearLayoutManager.VERTICAL, false));
                                recyclerView.setAdapter(poAdapter);
                            } catch (NullPointerException ex) {
                                ex.printStackTrace();
                            }
                        });
                        poAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onNothingSelected() {

                    }
                });
                lineChart.invalidate();
//          SET RECYLERVIEW
                lblDate.setText(getPeriodText(BranchPerformancePeriod.getLatestCompletePeriod()));
                mViewModel.getAreaBranchesSalesPerformance(BranchPerformancePeriod.getLatestCompletePeriod()).observe(Activity_AreaPerformance.this, branchPerformances -> {
                    try {
                        poAdapter = new AreaPerformanceMonitoringAdapter(
                                Activity_AreaPerformance.this, sales,
                                branchPerformances, sBranchCd -> {
                                    try {
                                        Intent loIntent = new Intent(
                                                Activity_AreaPerformance.this,
                                                Activity_BranchPerformance.class);
                                        loIntent.putExtra("brnCD", sBranchCd);
                                        startActivity(loIntent);
                                    } catch (NullPointerException e) {
                                        e.printStackTrace();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                         });
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setLayoutManager(new LinearLayoutManager(this,  LinearLayoutManager.VERTICAL, false));
                        recyclerView.setAdapter(poAdapter);
                    } catch(NullPointerException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                 e.printStackTrace();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
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