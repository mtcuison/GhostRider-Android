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

import android.os.Build;
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
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.guanzongroup.ghostrider.ahmonitoring.Adaper.AreaPerformanceMonitoringAdapter;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Adaper.BranchPerformanceAdapter;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMAreaPerfromanceMonitoring;

import java.util.ArrayList;
import java.util.Objects;

public class Activity_AreaPerformance extends AppCompatActivity implements OnChartValueSelectedListener {
    private VMAreaPerfromanceMonitoring mViewModel;
    private RecyclerView recyclerView;
    private AreaPerformanceMonitoringAdapter poAdapter;
    private String[] brnSales = {"MC Sales","SP Sales","JO Sales"};
    private String psAreaCde;
    ArrayList<Entry> chartValues;
    private LineChart lineChart;
    public int width;
    public int height;
    private TextView lblArea;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_performance);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;
        height = metrics.heightPixels;
        mViewModel = new ViewModelProvider(Activity_AreaPerformance.this).get(VMAreaPerfromanceMonitoring.class);
        psAreaCde = getIntent().getStringExtra("sAreaCode");
        mViewModel.getAreaNameFromCode(psAreaCde).observe(Activity_AreaPerformance.this, sAreaName-> {
            try {
                lblArea.setText(sAreaName);
            } catch(NullPointerException e) {
                e.printStackTrace();
            }
        });
        initWidgets();

    }
    private void initWidgets(){
        lblArea = findViewById(R.id.tvArea);
        recyclerView = findViewById(R.id.recyclerview_area_performance);
        lineChart = findViewById(R.id.activity_area_linechart);
        Toolbar toolbar = findViewById(R.id.toolbar_area_performance);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        TabLayout tabLayout = findViewById(R.id.tablayout_area_performance);
        TabLayout.Tab mcTab = tabLayout.newTab();
        TabLayout.Tab spTab = tabLayout.newTab();
        TabLayout.Tab joTab = tabLayout.newTab();
        mcTab.setText("MC Sales");
        spTab.setText("SP Sales");
        joTab.setText("JO Sales");
        tabLayout.addTab(mcTab);
        tabLayout.addTab(spTab);
        tabLayout.addTab(joTab);
        setChartValue(0);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setChartValue(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
// called when tab unselected
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
// called when a tab is reselected
            }
        });
    }
    public void setChartValue(int pos){
        mViewModel.getAreaPerformanceInfoList().observe(Activity_AreaPerformance.this, performances -> {
            try {
                chartValues = new ArrayList<>();
                String sales = "";
                if (pos == 0){
                    sales = "MC";

                    for (int x = 0; x< performances.size(); x++){
                        chartValues.add(new Entry(x, performances.get(x).getMCActual()));
                    }

                    BranchPerformanceAdapter.setIndexPosition(-1);
                }else if(pos == 1){
                    sales = "SP";
                    for (int x = 0; x< performances.size(); x++){
                        chartValues.add(new Entry(x, performances.get(x).getSPActual()));
                    }

                    BranchPerformanceAdapter.setIndexPosition(-1);
                }else if(pos == 2){
                    sales = "JO";
                    for (int x = 0; x< performances.size(); x++){
                        chartValues.add(new Entry(x, performances.get(x).getJOGoalxx()));
                    }

                    BranchPerformanceAdapter.setIndexPosition(-1);
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
                XAxis xAxis = lineChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                lineChart.setOnChartValueSelectedListener(this);
                lineChart.invalidate();
//          SET RECYLERVIEW
                poAdapter = new AreaPerformanceMonitoringAdapter(Activity_AreaPerformance.this, sales, performances);
                recyclerView.setHasFixedSize(true);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setLayoutManager(new LinearLayoutManager(this,  LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(poAdapter);
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                 e.printStackTrace();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onValueSelected(Entry e, Highlight h) {
        try{
            BranchPerformanceAdapter.setIndexPosition((int) e.getX());
            poAdapter.notifyDataSetChanged();
        }catch (NullPointerException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void onNothingSelected() {

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}