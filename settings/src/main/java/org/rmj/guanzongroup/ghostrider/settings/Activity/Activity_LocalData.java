/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.settings
 * Electronic Personnel Access Control Security System
 * project file created : 6/23/21 2:03 PM
 * project file last modified : 6/23/21 2:03 PM
 */

package org.rmj.guanzongroup.ghostrider.settings.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.button.MaterialButton;

import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DRawDao;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.settings.Objects.AdapterLocalData;
import org.rmj.guanzongroup.ghostrider.settings.Objects.LocalData;
import org.rmj.guanzongroup.ghostrider.settings.R;
import org.rmj.guanzongroup.ghostrider.settings.ViewModel.VMLocalData;

import java.util.List;

public class Activity_LocalData extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private MaterialButton btnRefresh;

    private VMLocalData mViewModel;

    private LoadDialog poDialog;
    private MessageBox poMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_data);

        mViewModel = new ViewModelProvider(this).get(VMLocalData.class);

        toolbar = findViewById(R.id.toolbar_localData);
        recyclerView = findViewById(R.id.recyclerview_localData);
        btnRefresh = findViewById(R.id.btn_refreshRecords);

        poDialog = new LoadDialog(Activity_LocalData.this);
        poMessage = new MessageBox(Activity_LocalData.this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(Activity_LocalData.this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        mViewModel.getAppLocalData().observe(this, appLocalData -> {
            try{
                mViewModel.setAppLocalData(appLocalData);
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getDataList().observe(this, localData -> {
            AdapterLocalData loAdapter = new AdapterLocalData(localData, () -> {

            });
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(loAdapter);
            recyclerView.getRecycledViewPool().clear();
            loAdapter.notifyDataSetChanged();
        });

        btnRefresh.setOnClickListener(v -> mViewModel.RefreshAllRecords(new VMLocalData.OnRefreshDataCallback() {
            @Override
            public void OnLoad(String Title, String Message) {
                poDialog.initDialog(Title, Message, false);
                poDialog.show();
            }

            @Override
            public void OnSuccess() {
                poDialog.dismiss();
            }

            @Override
            public void OnFailed() {
                poDialog.dismiss();
            }
        }));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}