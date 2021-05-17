/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.CreditEvaluator
 * Electronic Personnel Access Control Security System
 * project file created : 5/17/21 10:58 AM
 * project file last modified : 5/17/21 10:57 AM
 */

package org.rmj.guanzongroup.ghostrider.creditevaluator.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.TextView;

import org.rmj.guanzongroup.ghostrider.creditevaluator.R;
import org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel.VMCIHistoryInfo;
import org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel.VMEvaluationList;

public class Activity_CIHistoryInfo extends AppCompatActivity {
    private TextView lblLandmark, lblHOwnership, lblHHold, lblHType, lblGarage, lblHOthers;
    private VMCIHistoryInfo mViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ci_history_info);
        initWidgets();
        mViewModel = new ViewModelProvider(Activity_CIHistoryInfo.this).get(VMCIHistoryInfo.class);
        mViewModel.getCIByTransNox(getIntent().getStringExtra("sTransNox")).observe(Activity_CIHistoryInfo.this, eciEvaluation -> {
            lblLandmark.setText(eciEvaluation.getLandMark());
            lblHOwnership.setText(mViewModel.parseHouseOwn(eciEvaluation.getOwnershp()));
            lblHHold.setText(mViewModel.parseHouseHold(eciEvaluation.getOwnOther()));
            lblHType.setText(mViewModel.parseHouseType(eciEvaluation.getHouseTyp()));
            lblGarage.setText(mViewModel.getAnswer(eciEvaluation.getGaragexx()));
            lblHOthers.setText(mViewModel.getAnswer(eciEvaluation.getHasOther()));
        });
    }

    private void initWidgets() {
        lblLandmark = findViewById(R.id.lbl_landmark);
        lblHOwnership = findViewById(R.id.lbl_ownership);
        lblHHold =  findViewById(R.id.lbl_households);
        lblHType = findViewById(R.id.lbl_housetype);
        lblGarage =  findViewById(R.id.lbl_garage);
        lblHOthers = findViewById(R.id.lbl_hasother);
    }

}