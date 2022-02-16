/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 10/8/21, 1:18 PM
 * project file last modified : 10/8/21, 1:18 PM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity;

import static org.rmj.g3appdriver.GRider.Constants.AppConstants.CHART_MONTH_LABEL;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.rmj.g3appdriver.GRider.Etc.BranchPerformancePeriod;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Adapter.BranchPerformanceAdapter;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMBranchMonitor;

import java.util.ArrayList;
import java.util.Objects;

public class Activity_BranchPerformance extends AppCompatActivity implements OnChartValueSelectedListener {
    private VMBranchMonitor mViewModel;
    private RecyclerView recyclerView;
    private BranchPerformanceAdapter adapter;
    private String[] brnSales = {"MC Sales","SP Sales","JO Sales"};
    private String brnCD;
    ArrayList<Entry> chartValues;
    private LineChart lineChart;
    public int width;
    public int height;
    private TextView lblBranch;

    private ColorStateList poColor;
    private TextView lblItem1;
    private TextView lblItem2;
    private TextView lblSelectd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_performance);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;
        height = metrics.heightPixels;
        mViewModel = new ViewModelProvider(Activity_BranchPerformance.this).get(VMBranchMonitor.class);
        brnCD = getIntent().getStringExtra("brnCD");
        mViewModel.getBranchName(brnCD).observe(Activity_BranchPerformance.this,bnName->{
            lblBranch.setText(bnName);
        });
        initWidgets();

        mViewModel.getType().observe(this, s -> setChartValue(s));
    }
    private void initWidgets(){
        lblBranch = findViewById(R.id.tvBranch);
        recyclerView = findViewById(R.id.recyclerview_branch_performance);
        lineChart = findViewById(R.id.activity_branch_linechart);
        Toolbar toolbar = findViewById(R.id.toolbar_branch_performance);
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
        mViewModel.getAllBranchPerformanceInfoByBranch(brnCD).observe(Activity_BranchPerformance.this, eperformance ->{
            chartValues = new ArrayList<>();
            if (sales.equalsIgnoreCase("MC")){
                for (int x = 0; x< eperformance.size(); x++){
                    chartValues.add(new Entry(x, eperformance.get(x).getMCActual()));
                }

                BranchPerformanceAdapter.setIndexPosition(-1);
            }else {
                for (int x = 0; x< eperformance.size(); x++){
                    chartValues.add(new Entry(x, eperformance.get(x).getSPActual()));
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
            lineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(BranchPerformancePeriod.getBranchTableLabel(eperformance)));
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
            lineChart.setOnChartValueSelectedListener(this);
            lineChart.invalidate();
//          SET RECYLERVIEW
            adapter = new BranchPerformanceAdapter(this,sales, eperformance);
            recyclerView.setHasFixedSize(true);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setLayoutManager(new LinearLayoutManager(this,  LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(adapter);

        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onValueSelected(Entry e, Highlight h) {
        try{
            BranchPerformanceAdapter.setIndexPosition((int) e.getX());
            adapter.notifyDataSetChanged();
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

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_intent_slide_in_left, R.anim.anim_intent_slide_out_right);
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