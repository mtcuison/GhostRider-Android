package org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity;

import static org.rmj.g3appdriver.etc.AppConstants.CHART_MONTH_LABEL;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
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
    ArrayList<Entry> values1, values2, values3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mViewModel = new ViewModelProvider(this).get(VMBranchPerformanceMonitor.class);
        setContentView(R.layout.activity_branch_performance_monitoring);
        this.BranchCD = getIntent().getStringExtra("BranchCD");

        piechart = findViewById(R.id.pie_chart);
        linechart = findViewById(R.id.line_chart);

        tabLayout = findViewById(R.id.tabLayout);

        tabLayout.addTab(tabLayout.newTab().setText("MC Sales"));
        tabLayout.addTab(tabLayout.newTab().setText("SP Sales"));
        tabLayout.addTab(tabLayout.newTab().setText("Joborder"));

        initTablayout();
    }
    private void initTablayout(){

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){

//                    mViewModel.GetCurrentMCSalesPerformance(BranchCD).observe(getViewLifecycleOwner(),  BranchPerforamancebyMC -> {
//                        try{
////                            InitializeBranchList(BranchPerforamancebyMC, tab.getPosition());
//
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
//                    });
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

        Description desc = new Description();
        desc.setText("Over All Sales");
        desc.setTextSize(28);
        Log.e("teejei", String.valueOf(list.size()));
        values1 = new ArrayList<>();
        values2 = new ArrayList<>();
        values3 = new ArrayList<>();
        for (int x = 0; x< list.size(); x++){
            DBranchPerformance.PeriodicalPerformance info = list.get(x);
            values1.add(new Entry(Float.valueOf(info.Goal), Float.valueOf(info.Actual),Float.valueOf(info.Period)));
            values2.add(new Entry(Float.valueOf(info.Goal), Float.valueOf(info.Actual),Float.valueOf(info.Period)));
            values3.add(new Entry(Float.valueOf(info.Goal), Float.valueOf(info.Actual),Float.valueOf(info.Period)));

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
        linechart.setData(lineData);
        linechart.invalidate();
        linechart.setDescription(desc);
        linechart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(CHART_MONTH_LABEL));
        linechart.setDoubleTapToZoomEnabled(false);
    }
}