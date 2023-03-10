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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.guanzongroup.ghostrider.ahmonitoring.Adapter.AreaMonitoringAdapter;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMAreaMonitor;

public class Fragment_AreaMonitor extends Fragment {

    private VMAreaMonitor mViewModel;

    private RecyclerView recyclerView;
    private ConstraintLayout lblContainer;

    public static Fragment_AreaMonitor newInstance() {
        return new Fragment_AreaMonitor();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(VMAreaMonitor.class);
        View view = inflater.inflate(R.layout.fragment_area_monitor, container, false);
        initTopPerformingBrach();
        recyclerView = view.findViewById(R.id.rvTopPerformingBranch);
        lblContainer = view.findViewById(R.id.lblContainer);

        return view;
    }

    public void initTopPerformingBrach(){
        mViewModel.GetTopBranchPerformerForMCSales().observe(getViewLifecycleOwner(),  areaTopBranch -> {
            try{
                AreaMonitoringAdapter loAdapter = new AreaMonitoringAdapter(areaTopBranch);
                LinearLayoutManager loManager = new LinearLayoutManager(getActivity());
                loManager.setOrientation(RecyclerView.VERTICAL);
                recyclerView.setLayoutManager(loManager);
                recyclerView.setAdapter(loAdapter);
                recyclerView.setVisibility(View.VISIBLE);
                lblContainer.setVisibility(View.GONE);
            }catch (Exception e){
                e.printStackTrace();
            }
        });

    }
}