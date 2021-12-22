/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 6/8/21 4:50 PM
 * project file last modified : 6/8/21 4:50 PM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.g3appdriver.dev.DeptCode;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Etc.DialogKwikSearch;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Model.CashCountInfoModel;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Model.RequestNamesInfoModel;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMCashCountSubmit;

import java.util.List;
import java.util.Objects;

public class Activity_CashCountSubmit extends AppCompatActivity implements VMCashCountSubmit.OnKwikSearchCallBack, VMCashCountSubmit.OnSaveCashCountCallBack {
    public static final String TAG = Activity_CashCountSubmit.class.getSimpleName();
    private VMCashCountSubmit mViewModel;
    private CashCountInfoModel infoModel;
    private TextInputLayout tilRequestID;
    private TextInputLayout tilOfflRecpt;
    private TextInputLayout tilSalesRcpt;
    private TextInputLayout tilPrvnlRcpt;
    private TextInputLayout tilCllctRcpt;

    private TextInputEditText txtCurr_DateTime,
            txtRequestID,
            txtOfficialReceipt,
            txtSalesInvoice,
            txtProvisionalReceipt,
            txtCollectionReceipt,
            txtTransNox;
    private Button btnSendToServer;

    private TextView lblBranch, lblAddxx;
    ImageButton btnQuickSearch;

    private LoadDialog poDialogx;
    private MessageBox poMessage;

    private String BranchCd = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_count_submit);
        initWidgets();
        try {
            BranchCd = getIntent().getStringExtra("BranchCd");
        } catch (Exception e){
            e.printStackTrace();
        }
        infoModel = new CashCountInfoModel();
        mViewModel = new ViewModelProvider(this).get(VMCashCountSubmit.class);
        mViewModel.getUserBranchInfo().observe(Activity_CashCountSubmit.this, eBranchInfo -> {
            try {
                lblBranch.setText(eBranchInfo.getBranchNm());
                lblAddxx.setText(eBranchInfo.getAddressx());
            } catch (Exception e){
                e.printStackTrace();
            }
        });
        mViewModel.getTransNox().observe(Activity_CashCountSubmit.this, s-> {
            txtTransNox.setText(s);
            txtCurr_DateTime.setText(new AppConstants().CURRENT_DATE_WORD);
        });
        btnQuickSearch.setOnClickListener(v ->  {
            if (txtRequestID.getText().toString().isEmpty()){
                emptNameDialog();
            }else {
                mViewModel.importRequestNames(txtRequestID.getText().toString(), Activity_CashCountSubmit.this);
            }
        });
        btnSendToServer.setOnClickListener(v->{
            if (txtRequestID.getText().toString().isEmpty()){
                emptNameDialog();
            }else {
                JSONObject parameters = getParameters();
                try{
                    parameters.put("sTransNox", txtTransNox.getText().toString());
                    parameters.put("sORNoxxxx", txtOfficialReceipt.getText().toString());
                    parameters.put("sSINoxxxx", txtSalesInvoice.getText().toString());
                    parameters.put("sPRNoxxxx", txtProvisionalReceipt.getText().toString());
                    parameters.put("sCRNoxxxx", txtCollectionReceipt.getText().toString());
                    parameters.put("EntryTime", new AppConstants().DATE_MODIFIED);
                    parameters.put("sReqstdBy", txtRequestID.getText().toString());
                    infoModel.setCrNoxxxx(txtCollectionReceipt.getText().toString());
                    infoModel.setPrNoxxxx(txtProvisionalReceipt.getText().toString());
                    infoModel.setSiNoxxxx(txtSalesInvoice.getText().toString());
                    infoModel.setOrNoxxxx(txtOfficialReceipt.getText().toString());
                    infoModel.setEntryTme(new AppConstants().DATE_MODIFIED);
                    mViewModel.saveCashCount(infoModel, parameters, Activity_CashCountSubmit.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    public void emptNameDialog(){
        poDialogx.dismiss();
        poMessage.initDialog();
        poMessage.setTitle("Kwik Search");
        poMessage.setMessage("Request Name Required!");
        poMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
        poMessage.show();
    }
    public void initWidgets(){
        Toolbar toolbar = findViewById(R.id.toolbar_cashCountSubmit);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        poDialogx = new LoadDialog(Activity_CashCountSubmit.this);
        poMessage = new MessageBox(Activity_CashCountSubmit.this);
        lblBranch = findViewById(R.id.lbl_headerBranch);
        lblAddxx = findViewById(R.id.lbl_headerAddress);

        txtOfficialReceipt = findViewById(R.id.txtOfficialReceipt);
        txtSalesInvoice = findViewById(R.id.txtSalesInvoice);
        txtProvisionalReceipt = findViewById(R.id.txtProvisionalReceipt);
        txtCollectionReceipt = findViewById(R.id.txtCollectionReceipt);
        txtTransNox = findViewById(R.id.txtTransNox);
        btnSendToServer = findViewById(R.id.btnSendToServer);
        txtCurr_DateTime = findViewById(R.id.txtCurrentDateTime);
        txtRequestID = findViewById(R.id.txt_nameSearch);

        btnQuickSearch = findViewById(R.id.btn_quick_search);

        tilOfflRecpt = findViewById(R.id.til_ccOR);
        tilSalesRcpt = findViewById(R.id.til_ccSI);
        tilPrvnlRcpt = findViewById(R.id.til_ccPR);
        tilCllctRcpt = findViewById(R.id.til_ccCR);
    }

    @Override
    public void onStartKwikSearch() {
        poDialogx.initDialog("Kwik Search", "Sending request. Please wait...", false);
        poDialogx.show();
    }

    @Override
    public void onSuccessKwikSearch(List<RequestNamesInfoModel> infoList) {
        poDialogx.dismiss();
        initDialog(infoList);
    }

    @Override
    public void onKwikSearchFailed(String message) {
        poDialogx.dismiss();
        initDialog("Kwik Search",message);
    }
    public void initDialog(List<RequestNamesInfoModel> infoList){
        DialogKwikSearch loDialog = new DialogKwikSearch(Activity_CashCountSubmit.this,infoList);
        loDialog.initDialogKwikSearch((dialog, name) -> {
            txtRequestID.setText(name);
            loDialog.dismiss();
        });
        loDialog.show();
    }
    /**
     * Get the parameters from
     * Cash counter activity which is pass through intent
     * JSON is converted into string...*/
    private JSONObject getParameters(){
        try {
            JSONObject jsonObject = new JSONObject(getIntent().getStringExtra("params"));
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void initDialog(String title, String message){
        poMessage.initDialog();
        poMessage.setTitle(title);
        poMessage.setMessage(message);
        poMessage.setPositiveButton("Okay", (view, dialog) ->
                dialog.dismiss());
        poMessage.show();
    }

    @Override
    public void onStartSaveCashCount() {
        poDialogx.initDialog("Cash Count", "Sending cash count. Please wait...", false);
        poDialogx.show();
    }

    @Override
    public void onSuccessSaveCashCount() {
        poMessage.initDialog();
        poMessage.setTitle("Cast Count");
        poMessage.setMessage("Cash count has been saved successfully.");
        poMessage.setPositiveButton("Okay", (view, dialog) ->{
                    dialog.dismiss();
                    checkEmployeeLevelForInventory();
                });
        poMessage.show();
    }

    @Override
    public void onSaveCashCountFailed(String message) {
        initDialog("Cash Count",message);
        checkEmployeeLevelForInventory();
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

    private void checkEmployeeLevelForInventory(){
        if(mViewModel.getEmployeeLevel().equalsIgnoreCase(String.valueOf(DeptCode.LEVEL_AREA_MANAGER))) {
            mViewModel.CheckConnectivity(isDeviceConnected -> {
                if (isDeviceConnected) {
                    poMessage.initDialog();
                    poMessage.setTitle("GhostRider");
                    poMessage.setMessage("To complete your selfie log. Please proceed to Random Stock Inventory");
                    poMessage.setPositiveButton("Proceed", (btnView, mDialog) -> {
                        mDialog.dismiss();
                        Intent loIntent = new Intent(Activity_CashCountSubmit.this, Activity_Inventory.class);
                        loIntent.putExtra("BranchCd", BranchCd);
                        startActivity(loIntent);
                        Activity_CashCounter.getInstance().finish();
                        finish();
                    });
                    poMessage.show();
                } else {
                    Activity_CashCounter.getInstance().finish();
                    finish();
                }
            });
        } else {
            Activity_CashCounter.getInstance().finish();
            finish();
            this.overridePendingTransition(R.anim.anim_pop_in,R.anim.anim_pop_out);
        }
    }
}