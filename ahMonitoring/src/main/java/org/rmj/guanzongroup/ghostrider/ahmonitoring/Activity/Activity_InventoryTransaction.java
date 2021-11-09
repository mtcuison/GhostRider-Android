/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 10/25/21, 11:51 AM
 * project file last modified : 10/25/21, 11:51 AM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity;

import static org.rmj.g3appdriver.GRider.Constants.AppConstants.CURRENT_DATE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;

public class Activity_InventoryTransaction extends AppCompatActivity {
    private TextView lblTransNox;
    private TextInputEditText txtDesc,
            txtDate,
            txtRemarks1,
            txtEntryNox1,
            txtActualQty,
            txtQtyOnHand,
            txtRemarks2,
            txtEntryNox2,
            txtWareHouse,
            txtSection,
            txtBin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_transaction);
        initWidgets();
        try {
            lblTransNox.setText(getIntent().getStringExtra("transno"));
            txtDesc.setText(getIntent().getStringExtra("desc"));
            txtDate.setText(AppConstants.CURRENT_DATE);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }
    public void initWidgets(){
//        Toolbar toolbar = findViewById(R.id.toolbar_inventory_transaction);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Inventory");
//        lblTransNox = findViewById(R.id.lbl_invTransNox);
//        txtDesc = findViewById(R.id.txt_invDescription);
//        txtDate = findViewById(R.id.txt_invDate);
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