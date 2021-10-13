/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 6/14/21 10:59 AM
 * project file last modified : 6/14/21 10:59 AM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Fragment;

import static org.rmj.g3appdriver.GRider.Constants.AppConstants.CHART_MONTH_LABEL;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity.Activity_Monitoring;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMBranchMonitor;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMSPSales;

import java.util.ArrayList;

public class Fragment_SPSales extends Fragment {

    private VMSPSales mViewModel;
    ArrayList<Entry> values1, values2, values3;
    private LineChart lineChart;

    public static Fragment_SPSales newInstance() {
        return new Fragment_SPSales();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sp_sales, container, false);
        values1 = new ArrayList<>();
        values2 = new ArrayList<>();
        values3 = new ArrayList<>();
        initWidgets(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        mViewModel = new ViewModelProvider(this).get(VMSPSales.class);
        mViewModel.getAreaPerformanceInfoList().observe(getViewLifecycleOwner(), eperformance ->{
//            Description desc = new Description();
//            desc.setText("Spare Parts Sales");
//            desc.setTextSize(28);
//            desc.setTextColor(getResources().getColor(R.color.guanzon_orange));
            for (int x = 0; x< eperformance.size(); x++){
                values1.add(new Entry(x, eperformance.get(x).getSPActual()));
            }
            LineDataSet lineDataSet1 = new LineDataSet(values1, "");

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
            lineChart.invalidate();


        });
    }
    private void initWidgets(View v){

        lineChart = v.findViewById(R.id.activity_sp_linechart);
    }
}