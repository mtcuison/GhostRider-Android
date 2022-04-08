/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.creditEvaluation
 * Electronic Personnel Access Control Security System
 * project file created : 4/8/22, 9:09 AM
 * project file last modified : 4/8/22, 9:09 AM
 */

package org.guanzongroup.com.creditevaluation.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.button.MaterialButton;

import org.guanzongroup.com.creditevaluation.Adapter.EvaluationCIHistoryInfoAdapter;
import org.guanzongroup.com.creditevaluation.Core.PreviewParser;
import org.guanzongroup.com.creditevaluation.R;
import org.guanzongroup.com.creditevaluation.ViewModel.VMEvaluationCIHistoryInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditOnlineApplicationCI;

import java.util.Objects;

public class Activity_EvaluationCIHistoryInfo extends AppCompatActivity {

    private VMEvaluationCIHistoryInfo mViewModel;
    private EvaluationCIHistoryInfoAdapter poAdapter;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private MaterialButton btnApprove;

    private String psTransNo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation_ci_history_info);
        initParams();
        getExtras();
        displayData();
        btnApprove.setOnClickListener(new OnApproveListener());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void initParams() {
        mViewModel = new ViewModelProvider(Activity_EvaluationCIHistoryInfo.this).get(VMEvaluationCIHistoryInfo.class);
        layoutManager = new LinearLayoutManager(Activity_EvaluationCIHistoryInfo.this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerview);
        btnApprove = findViewById(R.id.btn_approve);
    }

    private void getExtras() {
        psTransNo = Objects.requireNonNull(getIntent().getStringExtra("sTransNox"));
    }

    private void displayData() {
        mViewModel.RetrieveApplicationData(psTransNo).observe(Activity_EvaluationCIHistoryInfo.this, ciDetails -> {
            try {
                if(ciDetails != null) {
                    setAdapterValue(ciDetails);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void setAdapterValue(ECreditOnlineApplicationCI foCiDetlx) {
        poAdapter = new EvaluationCIHistoryInfoAdapter(PreviewParser.getCIResultPreview(foCiDetlx));
        poAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(poAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    private static class OnApproveListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

        }
    }

}