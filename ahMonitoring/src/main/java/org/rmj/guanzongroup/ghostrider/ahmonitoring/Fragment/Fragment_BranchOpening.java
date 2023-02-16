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

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.rmj.g3appdriver.dev.DeptCode;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.etc.FormatUIText;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Adapter.BranchOpeningAdapter;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMBranchOpening;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Fragment_BranchOpening extends Fragment {

    private VMBranchOpening mViewModel;

    private MaterialTextView lblUserName,
                    lblPosition,
                    lblBranch,
                    lblDate;

    private RecyclerView recyclerView;

    public static Fragment_BranchOpening newInstance() {
        return new Fragment_BranchOpening();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_branch_opening, container, false);

        lblUserName = view.findViewById(R.id.lbl_username);
        lblPosition = view.findViewById(R.id.lbl_userPosition);
        lblBranch = view.findViewById(R.id.lbl_userBranch);
        lblDate = view.findViewById(R.id.lbl_date);
        recyclerView = view.findViewById(R.id.recyclerview_openings);
        lblDate.setText(FormatUIText.formatGOCasBirthdate(new AppConstants().CURRENT_DATE));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMBranchOpening.class);

        mViewModel.getUserInfo().observe(getViewLifecycleOwner(), eEmployeeInfo -> {
            lblUserName.setText(eEmployeeInfo.getUserName());
            lblPosition.setText(DeptCode.getDepartmentName(eEmployeeInfo.getDeptIDxx()));
            lblBranch.setText(eEmployeeInfo.getBranchNm());
        });

        lblDate.setOnClickListener(v -> {
            final Calendar newCalendar = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat") final SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM dd, yyyy");
            final DatePickerDialog StartTime = new DatePickerDialog(getActivity(), (view131, year, monthOfYear, dayOfMonth) -> {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String lsDate = dateFormatter.format(newDate.getTime());
                mViewModel.setDateSelected(lsDate);
                lblDate.setText(lsDate);
            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
            StartTime.getDatePicker().setMaxDate(System.currentTimeMillis() + 1000);
            StartTime.show();
        });

        mViewModel.getDateSelected().observe(getViewLifecycleOwner(), s -> mViewModel.getBranchOpeningsForDate(s).observe(getViewLifecycleOwner(), eBranchOpenMonitors -> {
            recyclerView.setHasFixedSize(true);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),  LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(new BranchOpeningAdapter(getActivity(), eBranchOpenMonitors, () -> {

            }));
        }));

    }

}