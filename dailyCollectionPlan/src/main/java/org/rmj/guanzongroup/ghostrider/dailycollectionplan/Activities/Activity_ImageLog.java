/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.dailyCollectionPlan
 * Electronic Personnel Access Control Security System
 * project file created : 3/2/22, 10:55 AM
 * project file last modified : 3/2/22, 10:55 AM
 */

package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textview.MaterialTextView;

import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.VMImageLog;

import java.util.Objects;

public class Activity_ImageLog extends AppCompatActivity {

    private VMImageLog mViewModel;
    private MaterialTextView lblBranch, lblAddrss, lblNoImgs;
    private RecyclerView recyclerV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_log);
        mViewModel = new ViewModelProvider(Activity_ImageLog.this).get(VMImageLog.class);
        initWidgets();
        displayImageLog();
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
        super.onBackPressed();
        finish();
    }

    private void initWidgets() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar_collectionList);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        lblBranch = findViewById(R.id.lbl_headerBranch);
        lblAddrss = findViewById(R.id.lbl_headerAddress);
        lblNoImgs = findViewById(R.id.lbl_noAvailable);
        recyclerV = findViewById(R.id.recyclerview);
        LinearLayoutManager lnManager = new LinearLayoutManager(Activity_ImageLog.this);
        lnManager.setOrientation(RecyclerView.VERTICAL);
        recyclerV.setLayoutManager(lnManager);
    }

    private void displayImageLog() {
        mViewModel.getUserBranchInfo().observe(Activity_ImageLog.this, eBranchInfo -> {
            try {
                lblBranch.setText(eBranchInfo.getBranchNm());
                lblAddrss.setText(eBranchInfo.getAddressx());
            } catch (Exception e){
                e.printStackTrace();
            }
        });

//        mViewModel.getDCPImageInfoList().observe(Activity_ImageLog.this, imgInfos -> {
//            if(imgInfos.size() > 0) {
//                recyclerV.setVisibility(View.VISIBLE);
//                lblNoImgs.setVisibility(View.GONE);
//                try {
//                    AdapterImageLog poAdapter = new AdapterImageLog(imgInfos);
//                    recyclerV.setAdapter(poAdapter);
//                    poAdapter.notifyDataSetChanged();
//                } catch (NullPointerException e) {
//                    e.printStackTrace();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            } else {
//                recyclerV.setVisibility(View.GONE);
//                lblNoImgs.setVisibility(View.VISIBLE);
//            }
//        });
    }

}