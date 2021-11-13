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

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Adaper.InventoryItemAdapter;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Dialog.DialogBranchSelection;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMInventory;

import java.util.Objects;

public class Activity_Inventory extends AppCompatActivity {
    private VMInventory mViewModel;

    private RecyclerView recyclerView;
    private TextView lblBranch, lblAddxx, lblDate;

    private LoadDialog poDialog;
    private MessageBox poMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        initWidgets();
        mViewModel = new ViewModelProvider(this).get(VMInventory.class);
        mViewModel.getUserBranchInfo().observe(Activity_Inventory.this, eBranchInfo -> {
            try {
                lblBranch.setText(eBranchInfo.getBranchNm());
                lblAddxx.setText(eBranchInfo.getAddressx());
                lblDate.setText(new AppConstants().CURRENT_DATE_WORD);
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getBranchCode().observe(Activity_Inventory.this, fsBranchCde -> {
            try {
                if (fsBranchCde.isEmpty()) {
                    mViewModel.getAreaBranchList().observe(Activity_Inventory.this, eBranchInfos -> {
                        try{
                            DialogBranchSelection loSelect = new DialogBranchSelection(Activity_Inventory.this, eBranchInfos);
                            loSelect.initDialog(BranchCode -> mViewModel.setBranchCde(BranchCode));
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    });
                } else {
                    mViewModel.getInventoryDetailForBranch(fsBranchCde).observe(Activity_Inventory.this, randomItems -> {
                        if(randomItems.size() <=0){
                            mViewModel.RequestRandomStockInventory(fsBranchCde, new VMInventory.OnRequestInventoryCallback() {
                                @Override
                                public void OnRequest(String title, String message) {
                                    poDialog.initDialog(title, message, false);
                                    poDialog.show();
                                }

                                @Override
                                public void OnSuccessResult(String message) {
                                    poDialog.dismiss();
                                    poMessage.initDialog();
                                    poMessage.setTitle("Random Stock Inventory");
                                    poMessage.setMessage(message);
                                    poMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
                                    poMessage.show();
                                }

                                @Override
                                public void OnFaileResult(String message) {
                                    poDialog.dismiss();
                                    poMessage.initDialog();
                                    poMessage.setTitle("Random Stock Inventory");
                                    poMessage.setMessage(message);
                                    poMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
                                    poMessage.show();
                                }
                            });
                        } else {
                            LinearLayoutManager manager = new LinearLayoutManager(Activity_Inventory.this);
                            manager.setOrientation(RecyclerView.VERTICAL);
                            recyclerView.setLayoutManager(manager);
                            recyclerView.setAdapter(new InventoryItemAdapter(randomItems, (TransNox, ItemCode, Description) -> {
                                Intent loIntent = new Intent(Activity_Inventory.this, Activity_InventoryTransaction.class);
                                loIntent.putExtra("transno", TransNox);
                                loIntent.putExtra("code", ItemCode);
                                loIntent.putExtra("desc", Description);
                                startActivity(loIntent);
                            }));
                        }
                    });
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    public void initWidgets(){
        Toolbar toolbar = findViewById(R.id.toolbar_inventory);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.recyclerview_inventory);
        lblBranch = findViewById(R.id.lbl_headerBranch);
        lblAddxx = findViewById(R.id.lbl_headerAddress);
        lblDate = findViewById(R.id.lbl_headerDate);

        poDialog = new LoadDialog(Activity_Inventory.this);
        poMessage = new MessageBox(Activity_Inventory.this);
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