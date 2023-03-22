package org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity;

import static org.rmj.g3appdriver.etc.AppConstants.CHART_MONTH_LABEL;

import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
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

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DBranchPerformance;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMBranchPerformanceMonitor;

import java.util.ArrayList;
import java.util.List;

public class Activity_BranchPerformanceMonitoring extends AppCompatActivity {

    private VMBranchPerformanceMonitor mViewModel;
    private String BranchCD;
    private String BranchNM;
    private LineChart linechart;
    private PieChart piechart;
    private MaterialTextView lblBranch;
    private TabLayout tabLayout;
    private ArrayList<Entry> poActual, poGoalxx;
    private int width, height;
    private RecyclerView rvAreaPerformance, rvBranchPerformance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mViewModel = new ViewModelProvider(Activity_BranchPerformanceMonitoring.this).get(VMBranchPerformanceMonitor.class);
        setContentView(R.layout.activity_branch_performance_monitoring);
        this.BranchCD = getIntent().getStringExtra("brnCD");
        this.BranchNM = getIntent().getStringExtra("brnNM");
        Log.e("ito ung branch",String.valueOf(getIntent().getStringExtra("brnCD")));

        Toolbar toolbar = findViewById(R.id.toolbar_monitoring);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        lblBranch = findViewById(R.id.lbl_AreaCde);

        width = metrics.widthPixels;
        height = metrics.heightPixels;
        piechart = findViewById(R.id.pie_chart);
        linechart = findViewById(R.id.line_chart);
        rvAreaPerformance = findViewById(R.id.recyclerview_area_performance);

        tabLayout = findViewById(R.id.tabLayout);

        tabLayout.addTab(tabLayout.newTab().setText("MC Sales"));
        tabLayout.addTab(tabLayout.newTab().setText("SP Sales"));
        tabLayout.addTab(tabLayout.newTab().setText("Job Order"));



        mViewModel.GetMCSalesPeriodicPerformance(BranchCD).observe(Activity_BranchPerformanceMonitoring.this,  BranchPerforamancebyMC -> {
            try{
                InitializeBranchList(BranchPerforamancebyMC);
                lblBranch.setText(BranchNM);
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        mViewModel.GetCurrentMCSalesPerformance(BranchCD).observe(Activity_BranchPerformanceMonitoring.this, BranchPerforamancebyMC ->{
            try{
                if (BranchPerforamancebyMC.contains("/")){
                    String[] rat = BranchPerforamancebyMC.split("/");
                    double ratio =Double.parseDouble(rat[0]) / Double.parseDouble(rat[1]) * 100;
                    InitializePieChart(BranchPerforamancebyMC);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        initTablayout();
    }

    private void initTablayout(){

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){
                    mViewModel.GetMCSalesPeriodicPerformance(BranchCD).observe(Activity_BranchPerformanceMonitoring.this,  BranchPerforamancebyMC -> {
                        try{
                            InitializeBranchList(BranchPerforamancebyMC);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    });
                    mViewModel.GetCurrentMCSalesPerformance(BranchCD).observe(Activity_BranchPerformanceMonitoring.this, BranchPerforamancebyMC ->{
                        try{
                            InitializePieChart(BranchPerforamancebyMC);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    });
                } else if (tab.getPosition() == 1) {
                    mViewModel.GetSPSalesPeriodicPerformance(BranchCD).observe(Activity_BranchPerformanceMonitoring.this,  BranchPerforamancebySP -> {
                        try{
                            InitializeBranchList(BranchPerforamancebySP);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    });
                    mViewModel.GetCurrentSPSalesPerformance(BranchCD).observe(Activity_BranchPerformanceMonitoring.this, BranchPerforamancebySP ->{
                        try{
                            InitializePieChart(BranchPerforamancebySP);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    });
                } else{
                    mViewModel.GetJobOrderPeriodicPerformance(BranchCD).observe(Activity_BranchPerformanceMonitoring.this,  BranchPerforamancebyJO -> {
                        try{
                            InitializeBranchList(BranchPerforamancebyJO);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    });
                    mViewModel.GetJobOrderPerformance(BranchCD).observe(Activity_BranchPerformanceMonitoring.this, BranchPerforamancebyJO ->{
                        try{
                            InitializePieChart(BranchPerforamancebyJO);
                            Log.e("value of ",BranchPerforamancebyJO);
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

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_intent_slide_in_left, R.anim.anim_intent_slide_out_right);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void InitializeBranchList(List<DBranchPerformance.PeriodicalPerformance> list){
        poActual = new ArrayList<>();
        poGoalxx = new ArrayList<>();
        for (int x = 0; x < list.size(); x++) {
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
}