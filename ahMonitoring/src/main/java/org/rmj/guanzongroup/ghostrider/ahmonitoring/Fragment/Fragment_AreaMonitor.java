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

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textview.MaterialTextView;

import org.rmj.g3appdriver.dev.Database.Entities.EBranchPerformance;
import org.rmj.g3appdriver.lib.BullsEye.OnImportPerformanceListener;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity.Activity_AreaPerformanceMonitoring;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity.Activity_BranchPerformanceMonitoring;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Adapter.AreaMonitoringAdapter;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMAreaMonitor;

import java.util.List;

public class Fragment_AreaMonitor extends Fragment {
    private static final String TAG = Fragment_AreaMonitor.class.getSimpleName();

    private VMAreaMonitor mViewModel;
    private View view;
    private TabLayout tabLayout;
    private RecyclerView recyclerView;
    private ConstraintLayout lblContainer;
    private MaterialCardView btnMCPerformance,btnSPPerformance,btnJOPerformance;
    private CircularProgressIndicator mcIndicator,spIndicator,joIndicator;
    private MaterialTextView mcGoalPerc,spGoalPerc,joGoalPerc,mcFraction,spFraction,joFraction;
    public static Fragment_AreaMonitor newInstance() {
        return new Fragment_AreaMonitor();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(VMAreaMonitor.class);
        view = inflater.inflate(R.layout.fragment_area_monitor, container, false);
        initWidgets();
        initPerformanceUpdate();
        tabLayout.addTab(tabLayout.newTab().setText("MC Sales"));
        tabLayout.addTab(tabLayout.newTab().setText("SP Sales"));
        tabLayout.addTab(tabLayout.newTab().setText("Joborder"));
        initCardPerformance();
        initGoalPercentage();

        mViewModel.GetTopBranchPerformerForMCSales().observe(getViewLifecycleOwner(),  areaTopBranchbyMC -> {
            try{
                InitializeBranchList(areaTopBranchbyMC,0);
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){
                    mViewModel.GetTopBranchPerformerForMCSales().observe(getViewLifecycleOwner(),  areaTopBranchbyMC -> {
                        try{
                            InitializeBranchList(areaTopBranchbyMC, tab.getPosition());

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    });
                } else if (tab.getPosition() == 1) {
                    mViewModel.GetTopBranchPerformerForSPSales().observe(getViewLifecycleOwner(),  areaTopBranchbySP -> {
                        try{
                            InitializeBranchList(areaTopBranchbySP, tab.getPosition());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    });
                } else{
                    mViewModel.GetTopBranchPerformerForJobOrder().observe(getViewLifecycleOwner(),  areaTopBranchbyJO -> {
                        try{
                            InitializeBranchList(areaTopBranchbyJO, tab.getPosition());
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
        return view;
    }

    private void initWidgets(){
        recyclerView = view.findViewById(R.id.rvTopPerformingBranch);
        lblContainer = view.findViewById(R.id.lblContainer);

        btnMCPerformance = view.findViewById(R.id.cv_mc_sales);
        btnSPPerformance = view.findViewById(R.id.cv_sp_sales);
        btnJOPerformance = view.findViewById(R.id.cv_jo);

        mcIndicator = view.findViewById(R.id.cpi_mc_sales);
        spIndicator = view.findViewById(R.id.cpi_sp_sales);
        joIndicator = view.findViewById(R.id.cpi_jo);

        mcGoalPerc = view.findViewById(R.id.lbl_mc_percentage);
        spGoalPerc = view.findViewById(R.id.lbl_sp_percentage);
        joGoalPerc = view.findViewById(R.id.lbl_jo_percentage);

        mcFraction = view.findViewById(R.id.lbl_mc_goal);
        spFraction = view.findViewById(R.id.lbl_sp_goal);
        joFraction = view.findViewById(R.id.lbl_jo_goal);

        tabLayout = view.findViewById(R.id.tabLayout);
    }

    private void initPerformanceUpdate(){
        mViewModel.ImportPerformance(new OnImportPerformanceListener() {
            @Override
            public void OnImport() {
                Log.d(TAG, "Updating area performance records...");
            }

            @Override
            public void OnSuccess() {
                Log.d(TAG, "Performance records updated successfully!");
            }

            @Override
            public void OnFailed(String message) {
                Log.d(TAG, message);
            }
        });
    }

    public void initGoalPercentage(){
        mViewModel.GetCurrentMCSalesPerformance().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String mc_goal) {
                try {
                    mcFraction.setText(mc_goal);
                    if (mc_goal.contains("/")) {
                        String[] rat = mc_goal.split("/");
                        if ((Double.parseDouble(rat[0]) == 0) || (Double.parseDouble(rat[1]) == 0)) {
                            mcGoalPerc.setText("0%");
                            mcIndicator.setProgress(0);
                        } else {
                            double ratio = Double.parseDouble(rat[0]) / Double.parseDouble(rat[1]) * 100;
                            mcGoalPerc.setText(String.valueOf(Math.round(ratio)) + "%");
                            mcIndicator.setProgress((int) (Math.round(ratio)));
                            Log.e("this is mcgoal", mc_goal);
                        }
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mViewModel.GetCurentSPSalesPerformance().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String sp_goal) {
                try {
                    spFraction.setText(sp_goal);
                    if (sp_goal.contains("/")) {
                        String[] rat = sp_goal.split("/");
                        if ((Double.parseDouble(rat[0]) == 0) || (Double.parseDouble(rat[1]) == 0)) {
                            spGoalPerc.setText("0%");
                            spIndicator.setProgress(0);
                        } else {
                            double ratio = Double.parseDouble(rat[0]) / Double.parseDouble(rat[1]) * 100;
                            spGoalPerc.setText(String.valueOf(Math.round(ratio)) + "%");
                            spIndicator.setProgress((int) (Math.round(ratio)));
                            Log.e("this is spgoal", sp_goal);
                        }
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mViewModel.GetJobOrderPerformance().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String jo_goal) {
                try {
                    joFraction.setText(jo_goal);
                    if (jo_goal.contains("/")) {
                        String[] rat = jo_goal.split("/");
                        if ((Double.parseDouble(rat[0]) == 0) || (Double.parseDouble(rat[1]) == 0)) {
                            joGoalPerc.setText("0%");
                            joIndicator.setProgress(0);
                        } else {
                            double ratio = Double.parseDouble(rat[0]) / Double.parseDouble(rat[1]) * 100;
                            joGoalPerc.setText(String.valueOf(Math.round(ratio)) + "%");
                            joIndicator.setProgress((int) (Math.round(ratio)));
                            Log.e("this is jogoal", jo_goal);
                        }
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void initCardPerformance(){
        btnMCPerformance.setOnClickListener(new View.OnClickListener() {
            Intent loIntent;
            @Override
            public void onClick(View view) {
                loIntent = new Intent(getActivity(), Activity_AreaPerformanceMonitoring.class);
                loIntent.putExtra("index","0");
                startActivity(loIntent);
                requireActivity().overridePendingTransition(R.anim.anim_intent_slide_in_right, R.anim.anim_intent_slide_out_left);
            }
        });
        btnSPPerformance.setOnClickListener(new View.OnClickListener() {
            Intent loIntent;
            @Override
            public void onClick(View view) {
                loIntent = new Intent(getActivity(), Activity_AreaPerformanceMonitoring.class);
                loIntent.putExtra("index","1");
                startActivity(loIntent);
                requireActivity().overridePendingTransition(R.anim.anim_intent_slide_in_right, R.anim.anim_intent_slide_out_left);
            }
        });
        btnJOPerformance.setOnClickListener(new View.OnClickListener() {
            Intent loIntent;
            @Override
            public void onClick(View view) {
                loIntent = new Intent(getActivity(), Activity_AreaPerformanceMonitoring .class);
                loIntent.putExtra("index","2");
                startActivity(loIntent);
                requireActivity().overridePendingTransition(R.anim.anim_intent_slide_in_right, R.anim.anim_intent_slide_out_left);
            }
        });

    }



    private void InitializeBranchList(List<EBranchPerformance> list, int priority){
        AreaMonitoringAdapter loAdapter = new AreaMonitoringAdapter(list, priority, new AreaMonitoringAdapter.OnBranchPerformanceClickListener() {
            @Override
            public void OnClick(String sBranchCd,String sBranchnm) {
                Intent loIntent = new Intent(getActivity(), Activity_BranchPerformanceMonitoring.class);
                loIntent.putExtra("brnCD", sBranchCd);
                loIntent.putExtra("brnNM", sBranchnm);
                startActivity(loIntent);
            }
        });
        LinearLayoutManager loManager = new LinearLayoutManager(getActivity());
        loManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(loManager);
        recyclerView.setAdapter(loAdapter);
        recyclerView.setVisibility(View.VISIBLE);
        lblContainer.setVisibility(View.GONE);
    }
}