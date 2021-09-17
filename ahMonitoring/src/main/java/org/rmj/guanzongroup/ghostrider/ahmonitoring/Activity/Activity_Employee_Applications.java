/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 9/16/21, 9:35 AM
 * project file last modified : 9/16/21, 9:35 AM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Adaper.EmployeeApplicationAdapter;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMEmployeeApplications;

import java.util.Objects;

public class Activity_Employee_Applications extends AppCompatActivity {

    private VMEmployeeApplications mViewModel;

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private TextView lblBrnchNm, lblBrnchAdd;
    private MaterialButton btnSearch;

    private MessageBox poMessage;
    private LoadDialog poDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_applications);
        mViewModel = new ViewModelProvider(this).get(VMEmployeeApplications.class);
        setupWidgets();

        mViewModel.DownloadLeaveForApproval(new VMEmployeeApplications.OnDownloadLeaveListListener() {
            @Override
            public void OnDownload(String message) {
                poDialog.initDialog("PET Manager", message, false);
                poDialog.show();
            }

            @Override
            public void OnDownloadSuccess() {
                poDialog.dismiss();
            }

            @Override
            public void OnDownloadFailed(String message) {
                poDialog.dismiss();
                poMessage.initDialog();
                poMessage.setTitle("PET Manager");
                poMessage.setMessage(message);
                poMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
                poMessage.show();
            }
        });

        mViewModel.getUserBranchInfo().observe(this, eBranchInfo -> {
            try{
                lblBrnchNm.setText(eBranchInfo.getBranchNm());
                lblBrnchAdd.setText(eBranchInfo.getAddressx());
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getEmployeeLeaveForApprovalList().observe(this, eEmployeeLeaves -> {
            try{
                LinearLayoutManager loManager = new LinearLayoutManager(Activity_Employee_Applications.this);
                loManager.setOrientation(RecyclerView.VERTICAL);
                recyclerView.setLayoutManager(loManager);
                recyclerView.setAdapter(new EmployeeApplicationAdapter(eEmployeeLeaves, TransNox -> {
                    Intent loIntent = new Intent(Activity_Employee_Applications.this, Activity_Employee_Applications.class);
                    loIntent.putExtra("app", AppConstants.INTENT_LEAVE_APPROVAL);
                }));
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    private void setupWidgets(){
        toolbar = findViewById(R.id.toolbar_application);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.recyclerview_applications);
        lblBrnchNm = findViewById(R.id.lbl_headerBranch);
        lblBrnchAdd = findViewById(R.id.lbl_headerAddress);
        btnSearch = findViewById(R.id.btn_manualSearch);

        poMessage = new MessageBox(Activity_Employee_Applications.this);
        poDialog = new LoadDialog(Activity_Employee_Applications.this);

        btnSearch.setOnClickListener(v -> {
            Intent loIntent = new Intent(Activity_Employee_Applications.this, Activity_Application.class);
            loIntent.putExtra("app", AppConstants.INTENT_APPLICATION_APPROVAL);
            startActivity(loIntent);
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_intent_slide_in_left, R.anim.anim_intent_slide_out_right);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.anim_intent_slide_in_right, R.anim.anim_intent_slide_out_left);
    }
}