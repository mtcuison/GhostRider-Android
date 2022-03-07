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
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Adapter.AdapterImageLog;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Adapter.PostDcpAdapter;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Activity_ImageLog extends AppCompatActivity {

    private TextView lblBranch, lblAddrss;
    private RecyclerView recyclerV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_log);
        initWidgets();
        setUpDcpList();
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
        Toolbar toolbar = findViewById(R.id.toolbar_collectionList);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        lblBranch = findViewById(R.id.lbl_headerBranch);
        lblAddrss = findViewById(R.id.lbl_headerAddress);
        recyclerV = findViewById(R.id.recyclerView);
        LinearLayoutManager lnManager = new LinearLayoutManager(Activity_ImageLog.this);
        lnManager.setOrientation(RecyclerView.VERTICAL);
        recyclerV.setLayoutManager(lnManager);
    }

    private void setUpDcpList() {
        // TODO: Call the viewModel's dcp list getter.
        /**Sample dcp list*/
        List<EDCPCollectionDetail> loList = new ArrayList<EDCPCollectionDetail>();
        AdapterImageLog poAdapter = new AdapterImageLog(loList, dcpDetail -> {

        });
        recyclerV.setAdapter(poAdapter);
        poAdapter.notifyDataSetChanged();
    }

}