/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 2/24/22, 9:03 AM
 * project file last modified : 2/24/22, 9:03 AM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Fragment;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.android.material.divider.MaterialDivider;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.rmj.g3appdriver.lib.BullsEye.BranchPerformancePeriod;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity.Activity_BranchPerformance;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Adapter.BranchPerformanceAdapter;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMBranchMonitor;

import java.util.ArrayList;

public class Fragment_BranchPerformance_LineChart extends Fragment implements OnChartValueSelectedListener {
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
    private MaterialTextView lblItem1, lblItem2, lblSelectd;

    public Fragment_BranchPerformance_LineChart() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_branch_performance_line_chart,
                container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;
        height = metrics.heightPixels;
        mViewModel = new ViewModelProvider(requireActivity()).get(VMBranchMonitor.class);
//        brnCD = Activity_BranchPerformance.getInstance().getBranchCode();
//        mViewModel.getBranchName(brnCD).observe(getViewLifecycleOwner(),bnName->{
//            lblBranch.setText(bnName);
//        });

        mViewModel.getType().observe(getViewLifecycleOwner(), s -> setChartValue(s));
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

    private void initViews(View v) {
        lblBranch = v.findViewById(R.id.tvBranch);
        recyclerView = v.findViewById(R.id.recyclerview_branch_performance);
        lineChart = v.findViewById(R.id.activity_branch_linechart);
        lblItem1 = v.findViewById(R.id.item1);
        lblItem2 = v.findViewById(R.id.item2);
        lblSelectd = v.findViewById(R.id.select);
        poColor = lblItem2.getTextColors();
        lblItem1.setOnClickListener(new TabClickHandler());
        lblItem2.setOnClickListener(new TabClickHandler());
    }

    private void setChartValue(String sales){
        mViewModel.getAllBranchPerformanceInfoByBranch(brnCD).observe(getViewLifecycleOwner(), eperformance ->{
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
            adapter = new BranchPerformanceAdapter(getActivity(), sales, eperformance);
            recyclerView.setHasFixedSize(true);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),  LinearLayoutManager.VERTICAL, false);
            linearLayoutManager.setReverseLayout(true);
            linearLayoutManager.setStackFromEnd(true);
            recyclerView.setLayoutManager(linearLayoutManager);

            recyclerView.setAdapter(adapter);

        });
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