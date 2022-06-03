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

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Adapter.InventoryItemAdapter;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Dialog.DialogPostInventory;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMInventory;

import java.util.Objects;

public class Activity_Inventory extends AppCompatActivity {
    private VMInventory mViewModel;

    private RecyclerView recyclerView;
    private TextView lblCountx, lblBranchNm, lblStatus;
    private MaterialButton btnSelect, btnPost;

    private LoadDialog poDialog;
    private MessageBox poMessage;

    private String Transnox;

    private String BranchCde = "";
    private boolean cancelable = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        initWidgets();
        mViewModel = new ViewModelProvider(this).get(VMInventory.class);
        mViewModel.getSelfieLogBranchInfo().observe(Activity_Inventory.this, eBranchInfo -> {
            try{
                mViewModel.setBranchCde(eBranchInfo.getBranchCd());
            } catch (Exception e){
                e.printStackTrace();
            }
        });
        mViewModel.getBranchCode().observe(Activity_Inventory.this, fsBranchCde -> {
            try {
//                if (fsBranchCde.isEmpty()) {
//                    lblStatus.setText("No branch detected on selfie log. Please take a selfie login to request random inventory items");
//                    mViewModel.getAreaBranchList().observe(Activity_Inventory.this, eBranchInfos -> {
//                        try{
//                            DialogBranchSelection loSelect = new DialogBranchSelection(Activity_Inventory.this, eBranchInfos);
//                            loSelect.initDialog(true, new DialogBranchSelection.OnBranchSelectedCallback() {
//                                @Override
//                                public void OnSelect(String BranchCode, AlertDialog dialog) {
//                                    mViewModel.setBranchCde(BranchCode);
//                                    dialog.dismiss();
//                                }
//
//                                @Override
//                                public void OnCancel() {
//
//                                }
//                            });
//                        } catch (Exception e){
//                            e.printStackTrace();
//                        }
//                    });
//                } else {
                    mViewModel.getBranchName(fsBranchCde).observe(Activity_Inventory.this, s -> {
                        try {
                            lblBranchNm.setText(s);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    });
                    mViewModel.getInventoryMasterForBranch(fsBranchCde).observe(Activity_Inventory.this, inventoryMaster -> {
                        if(inventoryMaster == null){
                            btnPost.setEnabled(false);
                            recyclerView.setVisibility(View.GONE);
                            lblStatus.setVisibility(View.VISIBLE);
                            lblStatus.setText("No inventory items found. Tap here to retry downloading items.");
//                            mViewModel.RequestRandomStockInventory(fsBranchCde, new VMInventory.OnRequestInventoryCallback() {
//                                @Override
//                                public void OnRequest(String title, String message) {
//                                    poDialog.initDialog(title, message, false);
//                                    poDialog.show();
//                                }
//
//                                @Override
//                                public void OnSuccessResult(String message) {
//                                    poDialog.dismiss();
//                                    poMessage.initDialog();
//                                    poMessage.setTitle("Random Stock Inventory");
//                                    poMessage.setMessage(message);
//                                    poMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
//                                    poMessage.show();
//                                }
//
//                                @Override
//                                public void OnNoStockRetrieve(String message) {
//                                    poDialog.dismiss();
//                                    poMessage.initDialog();
//                                    poMessage.setTitle("Random Stock Inventory");
//                                    poMessage.setMessage(message);
//                                    poMessage.setPositiveButton("Okay", (view, dialog) -> {
//                                        dialog.dismiss();
//                                        finish();
//                                    });
//                                    poMessage.show();
//                                }
//
//                                @Override
//                                public void OnFaileResult(String message) {
//                                    poDialog.dismiss();
//                                    poMessage.initDialog();
//                                    poMessage.setTitle("Random Stock Inventory");
//                                    poMessage.setMessage(message);
//                                    poMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
//                                    poMessage.show();
//                                }
//                            });
                        } else if(!inventoryMaster.getTranStat().equalsIgnoreCase("2")){
                            cancelable = false;
                            recyclerView.setVisibility(View.VISIBLE);
                            lblStatus.setVisibility(View.GONE);
                            btnPost.setEnabled(true);
                            Transnox = inventoryMaster.getTransNox();
                            mViewModel.getInventoryDetailForBranch(inventoryMaster.getTransNox()).observe(Activity_Inventory.this, randomItems -> {
                                mViewModel.getInventoryCountForBranch(inventoryMaster.getTransNox()).observe(Activity_Inventory.this, itemCount -> {
                                    try{
                                        lblCountx.setText("Updated Inventory Items : " + itemCount);
                                    } catch (Exception e){
                                        e.printStackTrace();
                                    }
                                });
                                if(randomItems.size() != 0){
                                    LinearLayoutManager manager = new LinearLayoutManager(Activity_Inventory.this);
                                    manager.setOrientation(RecyclerView.VERTICAL);
                                    recyclerView.setLayoutManager(manager);
                                    recyclerView.setAdapter(new InventoryItemAdapter(randomItems, (TransNox, PartID, BarCode, isUpdated, args) -> {
                                        if(!isUpdated) {
                                            Intent loIntent = new Intent(Activity_Inventory.this, Activity_InventoryEntry.class);
                                            loIntent.putExtra("transno", TransNox);
                                            loIntent.putExtra("partID", PartID);
                                            loIntent.putExtra("barcode", BarCode);
                                            startActivity(loIntent);
                                        } else {
                                            poMessage.initDialog();
                                            poMessage.setTitle("Random Stock Inventory");
                                            poMessage.setMessage("Update inventory details for " + args[0] + "? \n" +
                                                    "\n" +
                                                    "Stock: " + args[2] + "\n" +
                                                    "Remarks: " + args[1]);
                                            poMessage.setPositiveButton("Update", (view, dialog) -> {
                                                dialog.dismiss();
                                                Intent loIntent = new Intent(Activity_Inventory.this, Activity_InventoryEntry.class);
                                                loIntent.putExtra("transno", TransNox);
                                                loIntent.putExtra("partID", PartID);
                                                loIntent.putExtra("barcode", BarCode);
                                                startActivity(loIntent);
                                            });
                                            poMessage.setNegativeButton("Cancel", (view, dialog) -> dialog.dismiss());
                                            poMessage.show();
                                        }
                                    }));
                                }
                            });
                        } else {
                            cancelable = true;
                            recyclerView.setVisibility(View.GONE);
                            lblStatus.setVisibility(View.VISIBLE);
                            lblStatus.setText("Inventory is already posted");
                            btnPost.setEnabled(false);
                        }
                    });
//                }
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        lblStatus.setOnClickListener(v ->{
            mViewModel.RequestRandomStockInventory(BranchCde, new VMInventory.OnRequestInventoryCallback() {
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
                public void OnNoStockRetrieve(String message) {
                    poDialog.dismiss();
                    poMessage.initDialog();
                    poMessage.setTitle("Random Stock Inventory");
                    poMessage.setMessage(message);
                    poMessage.setPositiveButton("Okay", (view, dialog) -> {
                        dialog.dismiss();
                        finish();
                    });
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
//                if(!BranchCde.isEmpty()) {
//
//                } else {
//                    poDialog.dismiss();
//                    poMessage.initDialog();
//                    poMessage.setTitle("Random Stock Inventory");
//                    poMessage.setMessage("Unable to download random stock inventory. No branch provided to download inventory details.");
//                    poMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
//                    poMessage.show();
//                }
                });

        btnSelect.setOnClickListener(v -> mViewModel.getAreaBranchList().observe(Activity_Inventory.this, eBranchInfos -> {
            try{
//                DialogBranchSelection loSelect = new DialogBranchSelection(Activity_Inventory.this, eBranchInfos);
//                loSelect.initDialog(true, BranchCode -> mViewModel.setBranchCde(BranchCode));
            } catch (Exception e){
                e.printStackTrace();
            }
        }));

        btnPost.setOnClickListener(v -> new DialogPostInventory(Activity_Inventory.this).initDialog(Remarks -> {
            mViewModel.PostInventory(Transnox, Remarks, new VMInventory.OnRequestInventoryCallback() {
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
                    poMessage.setPositiveButton("Okay", (view, dialog) -> {
                        dialog.dismiss();
                        finish();
                    });
                    poMessage.show();
                }

                @Override
                public void OnNoStockRetrieve(String message) {
                    poDialog.dismiss();
                    poMessage.initDialog();
                    poMessage.setTitle("Random Stock Inventory");
                    poMessage.setMessage(message);
                    poMessage.setPositiveButton("Okay", (view, dialog) -> {
                        dialog.dismiss();
                        finish();
                    });
                    poMessage.show();
                }

                @Override
                public void OnFaileResult(String message) {
                    poDialog.dismiss();
                    showPostingMessage(message);
                }
            });
        }));
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.anim_intent_slide_in_right, R.anim.anim_intent_slide_out_left);
    }

    public void initWidgets(){
        Toolbar toolbar = findViewById(R.id.toolbar_inventory);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.recyclerview_inventory);
        lblCountx = findViewById(R.id.lbl_inventoryCount);
        lblBranchNm = findViewById(R.id.lbl_branchNm);
        lblStatus = findViewById(R.id.lbl_inventoryStatus);
        btnPost = findViewById(R.id.btn_post);
        btnSelect = findViewById(R.id.btn_selectBranch);

        poDialog = new LoadDialog(Activity_Inventory.this);
        poMessage = new MessageBox(Activity_Inventory.this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            if(cancelable) {
                finish();
                overridePendingTransition(R.anim.anim_intent_slide_in_left, R.anim.anim_intent_slide_out_right);
            } else {
                poMessage.initDialog();
                poMessage.setTitle("Random Stock Inventory");
                poMessage.setMessage("Exit Random Stock Inventory?");
                poMessage.setPositiveButton("Yes", (view, dialog) -> {
                    dialog.dismiss();
                    finish();
                });
                poMessage.setNegativeButton("No", (view, dialog) -> {
                    dialog.dismiss();
                });
                poMessage.show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(cancelable) {
            finish();
            overridePendingTransition(R.anim.anim_intent_slide_in_left, R.anim.anim_intent_slide_out_right);
        } else {
            poMessage.initDialog();
            poMessage.setTitle("Random Stock Inventory");
            poMessage.setMessage("Exit Random Stock Inventory?");
            poMessage.setPositiveButton("Yes", (view, dialog) -> {
                dialog.dismiss();
                finish();
            });
            poMessage.setNegativeButton("No", (view, dialog) -> {
                dialog.dismiss();
            });
            poMessage.show();
        }
    }

    private void showPostingMessage(String message){
        poMessage.initDialog();
        poMessage.setTitle("Random Stock Inventory");
        poMessage.setMessage(message);
        poMessage.setPositiveButton("Okay", (view, dialog) -> {
            dialog.dismiss();
        });
        poMessage.show();
    }
}