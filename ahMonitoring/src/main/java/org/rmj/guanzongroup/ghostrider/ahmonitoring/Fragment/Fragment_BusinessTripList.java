/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 9/22/21, 4:20 PM
 * project file last modified : 9/22/21, 4:20 PM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Fragment;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rmj.g3appdriver.dev.Database.Entities.EEmployeeBusinessTrip;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.etc.LoadDialog;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity.Activity_Application;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Adapter.EmployeeApplicationAdapter;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMBusinessTripList;

import java.util.List;

public class Fragment_BusinessTripList extends Fragment {

    private VMBusinessTripList mViewModel;

    private RecyclerView recyclerView;

    private MessageBox poMessage;
    private LoadDialog poDialog;

    private boolean forViewing;

    public static Fragment_BusinessTripList newInstance() {
        return new Fragment_BusinessTripList();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(VMBusinessTripList.class);
        View view = inflater.inflate(R.layout.fragment_business_trip_list, container, false);

        setupWidgets(view);

        boolean forViewing = requireActivity().getIntent().getBooleanExtra("type", false);
        if (forViewing) {
            mViewModel.getForPreviewList().observe(getViewLifecycleOwner(), this::setupList);
        } else {
            mViewModel.getForApprovalList().observe(getViewLifecycleOwner(), this::setupList);
        }

        return view;
    }

    private void setupList(List<EEmployeeBusinessTrip> fsList){
        try {
            LinearLayoutManager loManager = new LinearLayoutManager(getActivity());
            loManager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(loManager);
            recyclerView.setAdapter(new EmployeeApplicationAdapter(fsList, TransNox -> {
                if (!forViewing) {
                    Intent loIntent = new Intent(requireActivity(), Activity_Application.class);
                    loIntent.putExtra("app", AppConstants.INTENT_OB_APPROVAL);
                    loIntent.putExtra("sTransNox", TransNox);
                    startActivity(loIntent);
                }
            }));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupWidgets(View v){
        recyclerView = v.findViewById(R.id.recyclerview_applications);

        poMessage = new MessageBox(requireActivity());
        poDialog = new LoadDialog(requireActivity());
    }
}