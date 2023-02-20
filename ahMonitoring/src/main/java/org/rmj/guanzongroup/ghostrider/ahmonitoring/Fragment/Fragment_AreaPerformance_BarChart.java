/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 2/18/22, 1:29 PM
 * project file last modified : 2/18/22, 1:29 PM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Fragment;

import static org.rmj.g3appdriver.lib.BullsEye.BranchPerformancePeriod.getLatestCompletePeriod;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity.Activity_BranchPerformance;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Adapter.AreaInfoBarChartAdapter;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Adapter.AreaPerformanceMonitoringAdapter;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMAreaPerfromanceMonitoring;

public class Fragment_AreaPerformance_BarChart extends Fragment {
    private static final String TAG = Fragment_AreaPerformance_BarChart.class.getSimpleName();
    private VMAreaPerfromanceMonitoring mViewModel;
    private RecyclerView rvTable, rvChart;
    private AreaInfoBarChartAdapter poChartAd;
    private AreaPerformanceMonitoringAdapter poTblAdpt;
    private String[] brnSales = {"MC Sales","SP Sales","JO Sales"};
    private MaterialTextView lblArea, lblDate, lblItem1, lblItem2, lblSelectd, lgdGoal, lgdActual, lgdExcess;
    private ColorStateList poColor;

    public Fragment_AreaPerformance_BarChart() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_area_performance_bar_chart, container, false);
        initWidgets(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(VMAreaPerfromanceMonitoring.class);
        mViewModel.getAreaNameFromCode().observe(getViewLifecycleOwner(), sAreaName-> {
            try {
                lblArea.setText(sAreaName);
            } catch(NullPointerException e) {
                e.printStackTrace();
            }
        });
        mViewModel.getType().observe(getViewLifecycleOwner(), s -> setValues(s, getLatestCompletePeriod()));
    }

    private void initWidgets(View v) {
        lblArea = v.findViewById(R.id.tvArea);
        lblDate = v.findViewById(R.id.lbl_date);
        rvChart = v.findViewById(R.id.recyclerView_chart);
        rvTable = v.findViewById(R.id.recyclerview_table);
        lgdGoal = v.findViewById(R.id.lgd_goal);
        lgdActual = v.findViewById(R.id.lgd_actual);
        lgdExcess = v.findViewById(R.id.lgd_excess);
        lblItem1 = v.findViewById(R.id.item1);
        lblItem2 = v.findViewById(R.id.item2);
        lblSelectd = v.findViewById(R.id.select);
        poColor = lblItem2.getTextColors();
        lblItem1.setOnClickListener(new TabClickHandler());
        lblItem2.setOnClickListener(new TabClickHandler());
    }

    private void setValues(String sales, String fsPeriodx) {
        setChartData(sales);
        setTableData(sales, fsPeriodx);
    }

    private void setChartData(String sales) {
        if(sales.equalsIgnoreCase("MC")) {
            lgdGoal.setText("MC Goal");
            lgdActual.setText("MC Actual");
            lgdExcess.setText("MC Excess");
        } else if(sales.equalsIgnoreCase("SP")) {
            lgdGoal.setText("SP Goal");
            lgdActual.setText("SP Actual");
            lgdExcess.setText("SP Excess");
        }
        mViewModel.getAreaPerformanceInfoList().observe(getViewLifecycleOwner(), performances -> {
            try {
                poChartAd = new AreaInfoBarChartAdapter(performances, sales, eAreaPerformance -> {

                });
                poChartAd.notifyDataSetChanged();
                rvChart.setHasFixedSize(true);
                rvChart.setItemAnimator(new DefaultItemAnimator());
                rvChart.setLayoutManager(
                        new LinearLayoutManager(getActivity(),
                                LinearLayoutManager.HORIZONTAL,
                                false));
                rvChart.setAdapter(poChartAd);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void setTableData(String sales, String fsPeriodx) {
        mViewModel.getAreaBranchesSalesPerformance(fsPeriodx, sales).observe(getViewLifecycleOwner(), branchPerformances -> {
            try {
                poTblAdpt = new AreaPerformanceMonitoringAdapter(
                        getActivity(), sales,
                        branchPerformances, sBranchCd -> {
                    try {
                        Intent loIntent = new Intent(
                                getActivity(),
                                Activity_BranchPerformance.class);
                        loIntent.putExtra("brnCD", sBranchCd);
                        startActivity(loIntent);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
                rvTable.setHasFixedSize(true);
                rvTable.setItemAnimator(new DefaultItemAnimator());
                rvTable.setLayoutManager(
                        new LinearLayoutManager(getActivity(),
                                LinearLayoutManager.VERTICAL,
                                false));
                rvTable.setAdapter(poTblAdpt);
                poTblAdpt.notifyDataSetChanged();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
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