/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.creditApp
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.onlinecreditapplication.Fragment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.google.android.material.textfield.TextInputEditText;

import org.rmj.guanzongroup.onlinecreditapplication.Activity.Activity_CreditApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMProperties;

public class Fragment_Properties extends Fragment implements ViewModelCallBack {

    private VMProperties mViewModel;

    private String TransNox;

    private TextInputEditText txtLot1, txtLot2, txtLot3;
    private CheckBox cb4Wheels, cb3Wheels, cb2Wheels, cbAircon, cbRefxx, cbTelevsn;
    private Button btnPrvs, btnNext;

    public static Fragment_Properties newInstance() {
        return new Fragment_Properties();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_properties, container, false);
        initWidgets(view);

        return view;
    }

    private void initWidgets(View view){
        txtLot1 = view.findViewById(R.id.tie_cap_propertyLot1);
        txtLot2 = view.findViewById(R.id.tie_cap_propertyLot2);
        txtLot3 = view.findViewById(R.id.tie_cap_propertyLot3);

        cb4Wheels = view.findViewById(R.id.cb_4Wheels);
        cb3Wheels = view.findViewById(R.id.cb_3Wheels);
        cb2Wheels = view.findViewById(R.id.cb_2Wheels);
        cbAircon = view.findViewById(R.id.cb_Aircon);
        cbRefxx = view.findViewById(R.id.cb_refrigerator);
        cbTelevsn = view.findViewById(R.id.cb_television);

        btnPrvs = view.findViewById(R.id.btn_creditAppPrvs);
        btnNext = view.findViewById(R.id.btn_creditAppNext);

        btnPrvs.setOnClickListener(v -> Activity_CreditApplication.getInstance().moveToPageNumber(13));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TransNox = Activity_CreditApplication.getInstance().getTransNox();
        mViewModel = new ViewModelProvider(this).get(VMProperties.class);

        mViewModel.setTransNox(TransNox);
        mViewModel.getCurrentApplicantInfo().observe(getViewLifecycleOwner(), eCreditApplicantInfo -> {
            try {
                mViewModel.setCreditApplicantInfo(eCreditApplicantInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btnNext.setOnClickListener(v -> {
            mViewModel.setPsLot1Addx(txtLot1.getText().toString());
            mViewModel.setPsLot2Addx(txtLot2.getText().toString());
            mViewModel.setPsLot3Addx(txtLot3.getText().toString());
            mViewModel.setPs4Wheelsx(cb4Wheels.isChecked()? "1" : "0");
            mViewModel.setPs3Wheelsx(cb3Wheels.isChecked()? "1" : "0");
            mViewModel.setPs2Wheelsx(cb2Wheels.isChecked()? "1" : "0");
            mViewModel.setPsAirConxx(cbAircon.isChecked()? "1" : "0");
            mViewModel.setPsFridgexx(cbRefxx.isChecked()? "1" : "0");
            mViewModel.setPsTelevsnx(cbTelevsn.isChecked()? "1" : "0");
            mViewModel.SavePropertiesInfo(Fragment_Properties.this);
        });
    }

    @Override
    public void onSaveSuccessResult(String args) {
        Activity_CreditApplication.getInstance().moveToPageNumber(15);
    }

    @Override
    public void onFailedResult(String message) {

    }
}