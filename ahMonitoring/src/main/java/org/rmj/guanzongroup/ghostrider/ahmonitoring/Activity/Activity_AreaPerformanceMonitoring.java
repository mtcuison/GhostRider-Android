package org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity;

import static org.rmj.g3appdriver.etc.AppConstants.CHART_MONTH_LABEL;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textview.MaterialTextView;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DAreaPerformance;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Adapter.Adapter_Area_Performance_Monitoring;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Adapter.Adapter_Branch_Performance_Monitoring;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMAreaPerfromanceMonitoring;

import java.util.ArrayList;
import java.util.List;

public class Activity_AreaPerformanceMonitoring extends AppCompatActivity {
    private VMAreaPerfromanceMonitoring mViewModel;
    private String BranchCD;
    private String BranchNM;
    private LineChart linechart;
    private PieChart piechart;
    private MaterialTextView lblNoDataAreaPerformance,lblNoDataBranchPerformance;
    private TabLayout tabLayout;
    private ArrayList<Entry> poActual, poGoalxx;
    private int width, height;
    private RecyclerView rvAreaPerformance, rvBranchPerformance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mViewModel = new ViewModelProvider(Activity_AreaPerformanceMonitoring.this).get(VMAreaPerfromanceMonitoring.class);
        setContentView(R.layout.activity_area_performance_monitoring);
        this.BranchCD = getIntent().getStringExtra("brnCD");
        this.BranchNM = getIntent().getStringExtra("brnNM");
        Log.e("ito ung branch",String.valueOf(getIntent().getStringExtra("brnCD")));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        Toolbar toolbar = findViewById(R.id.toolbar_monitoring);
//        setSupportActionBar(toolbar);
//        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        width = metrics.widthPixels;
        height = metrics.heightPixels;
        piechart = findViewById(R.id.pie_chart);
        linechart = findViewById(R.id.line_chart);
        rvAreaPerformance = findViewById(R.id.recyclerview_area_performance);
        rvBranchPerformance = findViewById(R.id.recyclerview_branches_performance);
        lblNoDataAreaPerformance = findViewById(R.id.lbl_NO_data_for_area_performance);
        lblNoDataBranchPerformance = findViewById(R.id.lbl_NO_data_for_branch_performance);
        tabLayout = findViewById(R.id.tabLayout);

        tabLayout.addTab(tabLayout.newTab().setText("MC Sales"));
        tabLayout.addTab(tabLayout.newTab().setText("SP Sales"));
        tabLayout.addTab(tabLayout.newTab().setText("Job Order"));

        mViewModel.GetMCSalesBranchesPerformance().observe(Activity_AreaPerformanceMonitoring.this,  AreaPerforamancebyMC -> {
            try{
                InitializeBranchList(AreaPerforamancebyMC);
                InitializeBranchPerformanceList(AreaPerforamancebyMC);
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        mViewModel.GetCurrentMCSalesPerformance().observe(Activity_AreaPerformanceMonitoring.this, AreaPerforamancebyMC ->{
            try{
                if (AreaPerforamancebyMC.contains("/")){
                    String[] rat = AreaPerforamancebyMC.split("/");
                    double ratio =Double.parseDouble(rat[0]) / Double.parseDouble(rat[1]) * 100;
                    InitializePieChart(AreaPerforamancebyMC);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        mViewModel.GetMCSalesPeriodicPerformance().observe(Activity_AreaPerformanceMonitoring.this, AreaMonitoringPerformancebyMC ->{
            try{
                InitializeAreaList(AreaMonitoringPerformancebyMC);
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        initTablayout();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initTablayout(){
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){
                    mViewModel.GetMCSalesBranchesPerformance().observe(Activity_AreaPerformanceMonitoring.this,  AreaPerforamancebyMC -> {
                        try{
                            InitializeBranchList(AreaPerforamancebyMC);
                            InitializeBranchPerformanceList(AreaPerforamancebyMC);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    });
                    mViewModel.GetCurrentMCSalesPerformance().observe(Activity_AreaPerformanceMonitoring.this, AreaPerforamancebyMC ->{
                        try{
                            InitializePieChart(AreaPerforamancebyMC);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    });
                    mViewModel.GetMCSalesPeriodicPerformance().observe(Activity_AreaPerformanceMonitoring.this, AreaMonitoringPerformancebyMC ->{
                        try{
                            InitializeAreaList(AreaMonitoringPerformancebyMC);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    });
                } else if (tab.getPosition() == 1) {
                    mViewModel.GetSPSalesBranchesPerformance().observe(Activity_AreaPerformanceMonitoring.this,  AreaPerforamancebySP -> {
                        try{
                            InitializeBranchList(AreaPerforamancebySP);
                            InitializeBranchPerformanceList(AreaPerforamancebySP);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    });
                    mViewModel.GetCurentSPSalesPerformance().observe(Activity_AreaPerformanceMonitoring.this, AreaPerforamancebySP ->{
                        try{
                            InitializePieChart(AreaPerforamancebySP);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    });
                    mViewModel.GetSPSalesPeriodicPerformance().observe(Activity_AreaPerformanceMonitoring.this, AreaMonitoringPerformancebySP ->{
                        try{
                            InitializeAreaList(AreaMonitoringPerformancebySP);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    });
                } else{
                    mViewModel.GetJobOrderBranchesPerformance().observe(Activity_AreaPerformanceMonitoring.this,  AreaPerforamancebyJO -> {
                        try{
                            InitializeBranchList(AreaPerforamancebyJO);
                            InitializeBranchPerformanceList(AreaPerforamancebyJO);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    });
                    mViewModel.GetJobOrderPerformance().observe(Activity_AreaPerformanceMonitoring.this, AreaPerforamancebyJO ->{
                        try{
                            InitializePieChart(AreaPerforamancebyJO);
                            Log.e("value of ",AreaPerforamancebyJO);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    });
                    mViewModel.GetJobOrderPeriodicPerformance().observe(Activity_AreaPerformanceMonitoring.this, AreaMonitoringPerformancebyJO ->{
                        try{
                            InitializeAreaList(AreaMonitoringPerformancebyJO);
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
    }

    private void InitializeBranchList(List<DAreaPerformance.BranchPerformance> list){
        poActual = new ArrayList<>();
        poGoalxx = new ArrayList<>();
        for (int x = 0; x < list.size(); x++) {
            poActual.add(new Entry(x, Float.valueOf(list.get(x).nActualxx)));
            poGoalxx.add(new Entry(x, Float.valueOf(list.get(x).nGoalxxxx)));
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
        linechart.setData(data);
        linechart.setDescription(null);
        linechart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(CHART_MONTH_LABEL));
        linechart.setDoubleTapToZoomEnabled(false);
        linechart.getXAxis().setTextSize(14f);
        linechart.setExtraOffsets(0, 0, 10f, 18f);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, (height / 3));
        linechart.setLayoutParams(params);
        linechart.getLegend().setEnabled(false);
        linechart.setDrawGridBackground(false);
        linechart.setDrawBorders(true);
        linechart.setBorderWidth(1);
        linechart.setBorderColor(getResources().getColor(R.color.color_dadada));
        linechart.animateX(500);
        XAxis xAxis = linechart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        linechart.invalidate();
    }
    private void InitializePieChart(String args){

        try{
            ArrayList<PieEntry> loEntries = new ArrayList<>();
            ArrayList<Integer> colors = new ArrayList<>();

            if(args.contains("/")){
                String[] rat = args.split("/");
                loEntries.add(new PieEntry((float) Double.parseDouble(rat[0]), "Actual")); //Set actual performance
//                loEntries.add(new PieEntry((float) Double.parseDouble(rat[1]), "Goal")); //Set goal
                loEntries.add(new PieEntry((float) Double.parseDouble(rat[1])-(float) Double.parseDouble(rat[0]) , "Remaining Goal"));
            }
//            loEntries.add(new PieEntry(80, "Actual")); //Set actual performance
//            loEntries.add(new PieEntry(20, "Balance")); //Set goal
//            loEntries.add(new PieEntry(100, "Goal")); //Set goal

            colors.add(Color.parseColor("#F47422"));
            colors.add(Color.parseColor("#1ED760"));
//            colors.add(Color.parseColor("#ffffff"));
            PieDataSet pieDataSet = new PieDataSet(loEntries, "");
            pieDataSet.setValueTextSize(12f);
            pieDataSet.setColors(colors);
            PieData pieData = new PieData(pieDataSet);
            pieData.setDrawValues(true);
            piechart.getLegend().setEnabled(false);
            piechart.setData(pieData);
            piechart.invalidate();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
    private void InitializeAreaList(List<DAreaPerformance.PeriodicPerformance> areaMonitoringPerformance) {
        Adapter_Area_Performance_Monitoring loAdapter = new Adapter_Area_Performance_Monitoring(areaMonitoringPerformance, new Adapter_Area_Performance_Monitoring.OnAreasClickListener() {
            @Override
            public void OnClick(String sBranchCd, String sBranchnm) {
                Intent loIntent = new Intent(Activity_AreaPerformanceMonitoring.this, Activity_BranchPerformanceMonitoring.class);
                loIntent.putExtra("brnCD", sBranchCd);
                loIntent.putExtra("brnNM", sBranchnm);
                startActivity(loIntent);
            }
        });
        LinearLayoutManager loManager = new LinearLayoutManager(Activity_AreaPerformanceMonitoring.this);
        loManager.setOrientation(RecyclerView.VERTICAL);
        rvAreaPerformance.setLayoutManager(loManager);
        rvAreaPerformance.setAdapter(loAdapter);
        rvAreaPerformance.setVisibility(View.VISIBLE);
        lblNoDataAreaPerformance.setVisibility(View.GONE);;
    }

    private void InitializeBranchPerformanceList(List<DAreaPerformance.BranchPerformance> BranchPerformanceList) {
        Adapter_Branch_Performance_Monitoring loAdapter = new Adapter_Branch_Performance_Monitoring(BranchPerformanceList, new Adapter_Branch_Performance_Monitoring.OnAreasClickListener() {
            @Override
            public void OnClick(String sBranchCd, String sBranchnm) {
                Intent loIntent = new Intent(Activity_AreaPerformanceMonitoring.this, Activity_BranchPerformanceMonitoring.class);
                loIntent.putExtra("brnCD", sBranchCd);
                loIntent.putExtra("brnNM", sBranchnm);
                startActivity(loIntent);
            }
        });
        LinearLayoutManager loManager = new LinearLayoutManager(Activity_AreaPerformanceMonitoring.this);
        loManager.setOrientation(RecyclerView.VERTICAL);
        rvBranchPerformance.setLayoutManager(loManager);
        rvBranchPerformance.setAdapter(loAdapter);
        rvBranchPerformance.setVisibility(View.VISIBLE);
        lblNoDataBranchPerformance.setVisibility(View.GONE);;
    }
}