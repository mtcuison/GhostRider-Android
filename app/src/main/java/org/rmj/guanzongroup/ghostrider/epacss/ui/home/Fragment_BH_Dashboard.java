/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.app
 * Electronic Personnel Access Control Security System
 * project file created : 10/8/21, 3:39 PM
 * project file last modified : 10/8/21, 3:39 PM
 */

package org.rmj.guanzongroup.ghostrider.epacss.ui.home;

import static org.rmj.g3appdriver.etc.AppConstants.SETTINGS;
import static org.rmj.guanzongroup.ghostrider.epacss.ui.home.VMBHDashboard.MC_SALES;
import static org.rmj.guanzongroup.ghostrider.epacss.ui.home.VMBHDashboard.SP_SALES;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.button.MaterialButton;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DBranchPerformance;
import org.rmj.g3appdriver.dev.DeptCode;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.etc.FormatUIText;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity.Activity_BranchPerformance;
import org.rmj.guanzongroup.ghostrider.epacss.Activity.Activity_Main;
import org.rmj.guanzongroup.ghostrider.epacss.Activity.Activity_SplashScreen;
import org.rmj.guanzongroup.ghostrider.epacss.R;
import org.rmj.guanzongroup.ghostrider.epacss.ui.etc.AppDeptIcon;
import org.rmj.guanzongroup.ghostrider.settings.Activity.Activity_Settings;

import java.util.ArrayList;
import java.util.List;

public class Fragment_BH_Dashboard extends Fragment {

    private VMBHDashboard mViewModel;

    private TextView lblBranchNm,
                    lblUserName,
                    lblUserPost,
                    lblUserDept;

    private TextView lblPeriod,
                    lblProgrss,
                    lblPrgPrct,
                    lblGoalxxx,
                    lblPrdRnge;

    private MaterialButton btnSettng, btnLogout;

    private ColorStateList poColor;
    private TextView lblItem1;
    private TextView lblItem2;
    private TextView lblSelectd;
    private ImageView imgDept;
    private LineChart lineChart;

    private String BranchCd;

    public static Fragment_BH_Dashboard newInstance() {
        return new Fragment_BH_Dashboard();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bh_dashboard, container, false);
        setupWidgets(view);
        return view;
    }

    private void setupWidgets(View v){
        lblBranchNm = v.findViewById(R.id.lbl_BranchName);
        lblUserName = v.findViewById(R.id.lbl_employeeName);
        lblUserPost = v.findViewById(R.id.lbl_employee_position);
        lblUserDept = v.findViewById(R.id.lbl_employee_department);
        lblPeriod = v.findViewById(R.id.lbl_performance_current_period);
        lblProgrss = v.findViewById(R.id.lbl_performance_progress);
        lblPrgPrct = v.findViewById(R.id.lbl_performance_progress_percentage);
        lblGoalxxx = v.findViewById(R.id.lbl_performance_goal);
        lblPrdRnge = v.findViewById(R.id.lbl_performance_period_range);
        imgDept = v.findViewById(R.id.img_deptLogo);
        btnSettng = v.findViewById(R.id.btn_settings);
        btnLogout = v.findViewById(R.id.btn_logout);

        lblItem1 = v.findViewById(R.id.item1);
        lblItem2 = v.findViewById(R.id.item2);
        lblSelectd = v.findViewById(R.id.select);
        poColor = lblItem2.getTextColors();
        lblItem1.setOnClickListener(new TabClickHandler());
        lblItem2.setOnClickListener(new TabClickHandler());

        lineChart = v.findViewById(R.id.linechar_bh);

        lineChart.setOnClickListener(v13 -> {
            if(BranchCd != null) {
                Intent loIntent = new Intent(requireActivity(), Activity_BranchPerformance.class);
                loIntent.putExtra("brnCD", BranchCd);
                requireActivity().startActivity(loIntent);
            }
        });

        btnSettng.setOnClickListener(v12 -> {
            Intent intent = new Intent(getActivity(), Activity_Settings.class);
            startActivityForResult(intent, SETTINGS);
            requireActivity().overridePendingTransition(R.anim.anim_intent_slide_in_right, R.anim.anim_intent_slide_out_left);
        });

        btnLogout.setOnClickListener(v1 -> {
            MessageBox loMessage = new MessageBox(getActivity());
            loMessage.initDialog();
            loMessage.setNegativeButton("No", (view1, dialog) -> dialog.dismiss());
            loMessage.setPositiveButton("Yes", (view1, dialog) -> {
                dialog.dismiss();
                requireActivity().finish();
                new EmployeeMaster(requireActivity().getApplication()).LogoutUserSession();
                AppConfigPreference.getInstance(getActivity()).setIsAppFirstLaunch(false);
                startActivity(new Intent(getActivity(), Activity_SplashScreen.class));
            });
            loMessage.setTitle("GhostRider Session");
            loMessage.setMessage("Are you sure you want to end session/logout?");
            loMessage.show();
        });
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMBHDashboard.class);

        mViewModel.getUserInfo().observe(getViewLifecycleOwner(), eEmployeeInfo -> {
            try{
                lblUserName.setText(eEmployeeInfo.getUserName());
                lblUserPost.setText(DeptCode.parseUserLevel(eEmployeeInfo.getEmpLevID()));
                lblUserDept.setText(DeptCode.getDepartmentName(eEmployeeInfo.getDeptIDxx()));
                BranchCd = eEmployeeInfo.getBranchCD();
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getUserBranchInfo().observe(getViewLifecycleOwner(), eBranchInfo -> {
            try{
                lblBranchNm.setText(eBranchInfo.getBranchNm());
            } catch (Exception e){
                e.printStackTrace();
            }
        });
        mViewModel.getCurrentPeriodPerformance().observe(getViewLifecycleOwner(), eBranchPerformance -> {
            try{
                lblPeriod.setText("Progress and Goal for " + FormatUIText.dbPeriodToUI(eBranchPerformance.getPeriodxx()));
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getPeriodRange().observe(getViewLifecycleOwner(), periodRange -> {
            try{
                lblPrdRnge.setText("Periodic Performance Chart from "+ FormatUIText.dbPeriodToUI(periodRange.Start) + " to " + FormatUIText.dbPeriodToUI(periodRange.Current));
            } catch (Exception e){
                e.printStackTrace();
            }
        });
        mViewModel.getSales().observe(getViewLifecycleOwner(), s -> {

            if(s.equalsIgnoreCase(MC_SALES)){
                mViewModel.getMCBranchPerformance().observe(getViewLifecycleOwner(), actualGoal -> {
                    try {
                        lblProgrss.setText(actualGoal.Actual);
                        lblProgrss.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_performance_mc_unit, 0, 0, 0);
                        lblProgrss.setCompoundDrawablePadding(10);
                        if(actualGoal.Percentage != null) {
                            lblPrgPrct.setText(actualGoal.Percentage + "% Current Progress");
                        }
                        lblGoalxxx.setText(actualGoal.Goal);
                        lblGoalxxx.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_performance_goal, 0);
                        lblGoalxxx.setCompoundDrawablePadding(10);

                        mViewModel.getMCBranchPeriodicalPerformance().observe(getViewLifecycleOwner(), this::getEntries);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                });

            } else {
                mViewModel.getSPBranchPerformance().observe(getViewLifecycleOwner(), actualGoal -> {
                    try {
                        lblProgrss.setText(FormatUIText.getCurrencyUIFormat(actualGoal.Actual));
                        lblProgrss.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        lblProgrss.setCompoundDrawablePadding(0);
                        if(actualGoal.Percentage != null) {
                            lblPrgPrct.setText(actualGoal.Percentage + "% Current Progress");
                        }
                        lblGoalxxx.setText(FormatUIText.getCurrencyUIFormat(actualGoal.Goal));
                        lblGoalxxx.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        lblGoalxxx.setCompoundDrawablePadding(0);

                        mViewModel.getSPBranchPeriodicalPerformance().observe(getViewLifecycleOwner(), this::getEntries);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                });

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
                mViewModel.setSalesType(MC_SALES);
            } else if (view.getId() == R.id.item2){
                lblItem1.setTextColor(poColor);
                lblItem2.setTextColor(Color.WHITE);
                int size = lblItem2.getWidth();
                lblSelectd.animate().x(size).setDuration(100);
                mViewModel.setSalesType(SP_SALES);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getEntries(List<DBranchPerformance.PeriodicalPerformance> foPerformance) {
        final ArrayList laActual = new ArrayList<>();
        final ArrayList laGoalxx = new ArrayList<>();
        final String[] xAxisLabel = new String[foPerformance.size()];
        for(int x = 0; x < foPerformance.size(); x++) {
            float lnPeriod =  Float.parseFloat(foPerformance.get(x).Period.substring(4));
            laActual.add(new Entry(
                    lnPeriod,
                    Float.parseFloat(foPerformance.get(x).Actual)));

            laGoalxx.add(new Entry(
                    lnPeriod,
                    Float.parseFloat(foPerformance.get(x).Goal)));

            String lsPrdNm = "";
            if(lnPeriod >= 1){
                lsPrdNm = AppConstants.CHART_MONTH_LABEL[(int) lnPeriod - 1];
            }
            xAxisLabel[x] = lsPrdNm;
        }

        LineDataSet ldActual = new LineDataSet(laActual, "");
        LineDataSet ldGoalxx = new LineDataSet(laGoalxx, "");
        LineData lineData = new LineData(ldActual);
        lineData.addDataSet(ldActual);
        lineData.addDataSet(ldGoalxx);

        lineData.notifyDataChanged();

        ldActual.setDrawValues(false);
        ldActual.setColors(requireActivity().getColor(R.color.guanzon_orange));
        ldActual.setValueTextColor(Color.BLACK);
        ldActual.setValueTextSize(10f);

        ldGoalxx.setDrawValues(false);
        ldGoalxx.setColors(requireActivity().getColor(R.color.material_black));
        ldGoalxx.setValueTextColor(Color.BLACK);
        ldGoalxx.setValueTextSize(10f);

        lineChart.setDescription(null);
        lineChart.getLegend().setEnabled(false);
        lineChart.setData(lineData);
        lineChart.setScaleEnabled(false);
        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.setExtraBottomOffset(10);
        lineChart.setMaxVisibleValueCount(xAxisLabel.length);
        lineChart.animateX(500);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.disableGridDashedLine();
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));
        YAxis yAxis = lineChart.getAxisRight();
        yAxis.setEnabled(false);

        lineChart.notifyDataSetChanged();
        lineChart.invalidate();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SETTINGS){
            if(resultCode == Activity.RESULT_OK) {
                Intent loIntent = new Intent(getActivity(), Activity_Main.class);
                requireActivity().finish();
                startActivity(loIntent);
            }
        }
    }
}