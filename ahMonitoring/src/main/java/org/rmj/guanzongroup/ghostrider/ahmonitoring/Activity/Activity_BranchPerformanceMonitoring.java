package org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity;

import static org.rmj.g3appdriver.etc.AppConstants.CHART_MONTH_LABEL;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.tabs.TabLayout;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DBranchPerformance;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMBranchPerformanceMonitor;

import java.util.ArrayList;
import java.util.List;

public class Activity_BranchPerformanceMonitoring extends AppCompatActivity {

    private VMBranchPerformanceMonitor mViewModel;
    private String BranchCD;
    private LineChart linechart;
    private PieChart piechart;

    private TabLayout tabLayout;
    private ArrayList<Entry> poActual, poGoalxx;
    private int width;
    private int height;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mViewModel = new ViewModelProvider(this).get(VMBranchPerformanceMonitor.class);
        setContentView(R.layout.activity_branch_performance_monitoring);
        this.BranchCD = getIntent().getStringExtra("BranchCD");
        DisplayMetrics metrics = new DisplayMetrics();
//        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;
        height = metrics.heightPixels;
        piechart = findViewById(R.id.pie_chart);
        linechart = findViewById(R.id.line_chart);

        tabLayout = findViewById(R.id.tabLayout);

        tabLayout.addTab(tabLayout.newTab().setText("MC Sales"));
        tabLayout.addTab(tabLayout.newTab().setText("SP Sales"));
        tabLayout.addTab(tabLayout.newTab().setText("Joborder"));

        initTablayout();
        mViewModel.GetMCSalesPeriodicPerformance(BranchCD).observe(Activity_BranchPerformanceMonitoring.this,  BranchPerforamancebyMC -> {
            try{
                InitializeBranchList(BranchPerforamancebyMC, 0);
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }



    private void initTablayout(){

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){
                    mViewModel.GetMCSalesPeriodicPerformance(BranchCD).observe(Activity_BranchPerformanceMonitoring.this,  BranchPerforamancebyMC -> {
                        try{
                            InitializeBranchList(BranchPerforamancebyMC, tab.getPosition());

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    });
                } else if (tab.getPosition() == 1) {
//                    mViewModel.GetSPSalesPeriodicPerformance(BranchCd).observe(getViewLifecycleOwner(),  BranchPerforamancebySP -> {
//                        try{
//                            InitializeBranchList(BranchPerforamancebySP, tab.getPosition());
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
//                    });
                } else{
//                    mViewModel.GetJobOrderPeriodicPerformance(BranchCd).observe(getViewLifecycleOwner(),  BranchPerforamancebyJO -> {
//                        try{
//                            InitializeBranchList(BranchPerforamancebyJO, tab.getPosition());
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
//                    });
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
    private void InitializeBranchList(List<DBranchPerformance.PeriodicalPerformance> list, int priority){

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
}