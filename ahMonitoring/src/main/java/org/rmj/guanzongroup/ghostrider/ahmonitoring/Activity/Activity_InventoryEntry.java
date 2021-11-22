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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EInventoryDetail;
import org.rmj.g3appdriver.GRider.Etc.FormatUIText;
import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Model.RandomItem;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMInventoryEntry;

import java.util.Objects;

public class Activity_InventoryEntry extends AppCompatActivity {
    private static final String TAG = Activity_InventoryEntry.class.getSimpleName();

    private VMInventoryEntry mViewModel;
    private RandomItem poItem;

    private MessageBox poMessage;

    private TextView lblTransNox, lblItemxx;
    private TextInputEditText txtDate,
            txtRemarks1,
            txtEntryNox1,
            txtActualQty,
            txtQtyOnHand,
            txtWareHouse,
            txtSection,
            txtBin;
    private MaterialButton btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_entry);
        initWidgets();
        mViewModel = new ViewModelProvider(Activity_InventoryEntry.this).get(VMInventoryEntry.class);
        String lsTransNox = getIntent().getStringExtra("transno");
        String lsPartIDxx = getIntent().getStringExtra("partID");
        String lsBarCodex = getIntent().getStringExtra("barcode");
        mViewModel.getInventoryItemDetail(lsTransNox, lsPartIDxx, lsBarCodex).observe(Activity_InventoryEntry.this, new Observer<EInventoryDetail>() {
            @Override
            public void onChanged(EInventoryDetail item) {
                try{
                    poItem = new RandomItem(item.getTransNox(), item.getPartsIDx(), item.getBarrCode());
                    lblItemxx.setText(item.getDescript());
                    lblTransNox.setText(item.getTransNox());
                    txtDate.setText(FormatUIText.formatGOCasBirthdate(AppConstants.CURRENT_DATE));
                    txtEntryNox1.setText(String.valueOf(item.getEntryNox()));
                    txtQtyOnHand.setText(String.valueOf(item.getQtyOnHnd()));
                    txtWareHouse.setText(item.getWHouseNm());
                    txtSection.setText(item.getSectnNme());
                    txtBin.setText(item.getBinNamex());
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                poItem.setItemQtyx(Integer.parseInt(txtActualQty.getText().toString()));
                poItem.setRemarksx(txtRemarks1.getText().toString());
                mViewModel.saveInventoryUpdate(poItem, new VMInventoryEntry.OnInventoryUpdateCallBack() {
                    @Override
                    public void OnUpdate(String message) {
                        GToast.CreateMessage(Activity_InventoryEntry.this, message, GToast.INFORMATION).show();
                        finish();
                    }

                    @Override
                    public void OnError(String message) {
                        GToast.CreateMessage(Activity_InventoryEntry.this, message, GToast.INFORMATION).show();
                        finish();
                    }
                });
            }
        });
    }
    public void initWidgets(){
        Toolbar toolbar = findViewById(R.id.toolbar_inventory_transaction);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Inventory");
        lblTransNox = findViewById(R.id.lbl_invTransNox);
        lblItemxx = findViewById(R.id.lbl_inventoryItem);
        txtDate = findViewById(R.id.txt_invDate);
        txtRemarks1 = findViewById(R.id.txt_invRemarks1);
        txtEntryNox1 = findViewById(R.id.txt_invEntryNox1);
        txtActualQty = findViewById(R.id.txt_invQty);
        txtQtyOnHand = findViewById(R.id.txt_invQtyOnHand);
        txtWareHouse = findViewById(R.id.txt_invWareHouse);
        txtSection = findViewById(R.id.txt_invSection);
        txtBin = findViewById(R.id.txt_invBin);
        btnSave = findViewById(R.id.btn_saveInventory);

        poMessage = new MessageBox(Activity_InventoryEntry.this);
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