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

import static org.rmj.g3appdriver.GRider.Constants.AppConstants.CHART_MONTH_LABEL;

import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.rmj.g3appdriver.GRider.Database.Entities.EBranchPerformance;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity.Activity_Monitoring;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMBranchMonitor;

import java.util.ArrayList;
import java.util.List;

public class Fragment_BranchMonitor extends Fragment {

    private VMBranchMonitor mViewModel;
    ArrayList<Entry> values1, values2, values3;
    private LineChart lineChart;


    public static Fragment_BranchMonitor newInstance() {
        return new Fragment_BranchMonitor();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_branch_monitor, container, false);
        values1 = new ArrayList<>();
        values2 = new ArrayList<>();
        values3 = new ArrayList<>();
        initWidgets(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMBranchMonitor.class);
        mViewModel.getAllBranchPerformanceInfoByBranch(Activity_Monitoring.getInstance().getBranchCD()).observe(getViewLifecycleOwner(),eperformance ->{
            Description desc = new Description();
            desc.setText("Over All Sales");
            desc.setTextSize(28);
            for (int x = 0; x< eperformance.size(); x++){
                values1.add(new Entry(x, eperformance.get(x).getSPActual()));
                values2.add(new Entry(x, eperformance.get(x).getMCActual()));
                values3.add(new Entry(x, eperformance.get(x).getJOGoalxx()));
                Log.e("MC", String.valueOf(eperformance.get(x).getMCActual()));

            }
            LineDataSet lineDataSet1 = new LineDataSet(values1, "SP Sales");
            LineDataSet lineDataSet2 = new LineDataSet(values2, "MC Sales");
            LineDataSet lineDataSet3 = new LineDataSet(values3, "JO Sales");

            // Set line attributes
            lineDataSet1.setLineWidth(2);
            lineDataSet2.setLineWidth(2);
            lineDataSet3.setLineWidth(2);
            lineDataSet1.setColors(getResources().getColor(R.color.guanzon_orange));
            lineDataSet2.setColors(getResources().getColor(R.color.guanzon_deep_dark_grey));
            lineDataSet3.setColors(getResources().getColor(R.color.guanzon_dark_grey));

            //ArrayList<ILineDataSet> Contains list of LineDataSets
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(lineDataSet1);
            dataSets.add(lineDataSet2);
            dataSets.add(lineDataSet3);

            // LineData Contains ArrayList<ILineDataSet>
            LineData data = new LineData(dataSets);
            lineChart.setData(data);
            lineChart.invalidate();
            lineChart.setDescription(desc);
            lineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(CHART_MONTH_LABEL));
            lineChart.setDoubleTapToZoomEnabled(false);


        });
        // TODO: Use the ViewModel
    }

    private void initWidgets(View v){

        lineChart = v.findViewById(R.id.activity_main_linechart);
    }

}