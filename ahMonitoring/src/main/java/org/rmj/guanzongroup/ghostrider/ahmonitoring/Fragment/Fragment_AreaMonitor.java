package org.rmj.guanzongroup.ghostrider.ahmonitoring.Fragment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rmj.guanzongroup.ghostrider.ahmonitoring.Adaper.AreaMonitoringAdapter;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Model.Area;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMAreaMonitor;

import java.util.ArrayList;
import java.util.List;

public class Fragment_AreaMonitor extends Fragment {

    private VMAreaMonitor mViewModel;

    private RecyclerView recyclerView;

    public static Fragment_AreaMonitor newInstance() {
        return new Fragment_AreaMonitor();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_area_monitor, container, false);

        recyclerView = view.findViewById(R.id.recyclerview_monitoring);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMAreaMonitor.class);

        mViewModel.getAreaPerformanceInfoList().observe(this, areaPerformances -> {
            List<Area> areaList = new ArrayList<>();
            for(int x = 0; x < areaPerformances.size(); x++){
                Area area = new Area(areaPerformances.get(x).getAreaCode(),
                                    areaPerformances.get(x).getAreaDesc(),
                                    String.valueOf(areaPerformances.get(x).getMCGoalxx()),
                                    String.valueOf(areaPerformances.get(x).getMCActual()));
                areaList.add(area);
            }
            AreaMonitoringAdapter loAdapter = new AreaMonitoringAdapter(areaList);
            LinearLayoutManager loManager = new LinearLayoutManager(getActivity());
            loManager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(loManager);
            recyclerView.setAdapter(loAdapter);
        });
    }

}