/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 6/10/21 11:47 AM
 * project file last modified : 6/10/21 11:47 AM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Adaper.CashCountLogAdapter;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Model.CashCountInfoModel;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMCashCountLog;

import java.util.ArrayList;
import java.util.List;

public class Activity_CashCountLog extends AppCompatActivity {

    private CashCountLogAdapter adapter;
    private VMCashCountLog mViewModel;
    private RecyclerView recyclerView;
    private List<CashCountInfoModel> infoModelList;
    private LinearLayout loading,layoutNoRecord;
    private TextInputEditText txtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_count_log);
        initWidgets();
        mViewModel = new ViewModelProvider(Activity_CashCountLog.this).get(VMCashCountLog.class);
        mViewModel.getAllCashCountLog().observe(Activity_CashCountLog.this, ciList -> {
            if(ciList.size()>0) {
                loading.setVisibility(View.GONE);
                infoModelList = new ArrayList<>();
                for (int x = 0; x < ciList.size(); x++) {
                    CashCountInfoModel loan = new CashCountInfoModel();
                    loan.setTransNox(ciList.get(x).getTransNox());
                    loan.setTranDate(ciList.get(x).getTransact());
                    loan.setReqstdNm(ciList.get(x).getReqstdBy());
                    infoModelList.add(loan);
                }
                adapter = new CashCountLogAdapter(infoModelList, new CashCountLogAdapter.OnApplicationClickListener() {
                    @Override
                    public void OnClick(int position, List<CashCountInfoModel> loanList) {

                    }
                });
                LinearLayoutManager layoutManager = new LinearLayoutManager(Activity_CashCountLog.this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(layoutManager);
                adapter.notifyDataSetChanged();

                txtSearch.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        try {

                            adapter.getFilter().filter(s.toString());
                            adapter.notifyDataSetChanged();
                            if (adapter.getItemCount() == 0){
                                layoutNoRecord.setVisibility(View.VISIBLE);

                            }else {
                                layoutNoRecord.setVisibility(View.GONE);
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }else{
                layoutNoRecord.setVisibility(View.VISIBLE);
            }
        });
    }
    public void initWidgets(){
        Toolbar toolbar = findViewById(R.id.toolbar_cashCountLog);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.recyclerview_cashcountLog);
        loading = findViewById(R.id.linear_progress);
        layoutNoRecord = findViewById(R.id.layout_cash_count_noRecord);
        txtSearch = findViewById(R.id.txt_nameSearch);
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
}