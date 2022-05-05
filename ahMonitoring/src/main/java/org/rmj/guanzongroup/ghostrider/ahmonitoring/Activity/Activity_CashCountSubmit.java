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
import org.rmj.g3appdriver.GRider.Etc.FormatUIText;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.g3appdriver.dev.DeptCode;
import org.rmj.g3appdriver.etc.AppConfigPreference;
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

    private boolean isTapSubmit = false;

    private TextInputEditText txtCurr_DateTime,
            txtRequestID,
            txtOfficialReceipt,
            txtSalesInvoice,
            txtProvisionalReceipt,
            txtCollectionReceipt,
            txtORNorthPoint,
            txtPRNorthPoint,
            txtDeliveryRcpt,
            txtPettyCashxxx,
            txtTotalSalesxx,
            txtTransNox;
    private Button btnSendToServer;

    private TextView lblBranch, lblAddxx;
    ImageButton btnQuickSearch;

    private LoadDialog poDialogx;
    private MessageBox poMessage;

    private String BranchCd = "";
    private String EmployID = ""; //EmployeeID of requesting employee
    private boolean cIsMobile = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_count_submit);
        initWidgets();
        infoModel = new CashCountInfoModel();
        mViewModel = new ViewModelProvider(this).get(VMCashCountSubmit.class);
        mViewModel.getSelfieLogBranchInfo().observe(Activity_CashCountSubmit.this, eBranchInfo -> {
            try {
                lblBranch.setText(eBranchInfo.getBranchNm());
                lblAddxx.setText(eBranchInfo.getAddressx());
                BranchCd = eBranchInfo.getBranchCd();
                cIsMobile = BranchCd.startsWith("C");
            } catch (Exception e){
                e.printStackTrace();
            }
        });
        mViewModel.getTransNox().observe(Activity_CashCountSubmit.this, s-> {
            try {
                JSONObject loJson = getParameters();
                txtTransNox.setText(s);
                txtTotalSalesxx.setText(FormatUIText.getCurrencyUIFormat(loJson.getString("nTotSales")));
                txtCurr_DateTime.setText(new AppConstants().CURRENT_DATE_WORD);
            } catch (Exception e){
                e.printStackTrace();
            }
        });
        btnQuickSearch.setOnClickListener(v ->  {
            if (txtRequestID.getText().toString().isEmpty()){
                emptNameDialog();
            }else {
                mViewModel.importRequestNames(txtRequestID.getText().toString(), Activity_CashCountSubmit.this);
            }
        });
        btnSendToServer.setOnClickListener(v->{
            if (isTapSubmit) {
                return;
            }
            this.isTapSubmit = true;
            JSONObject parameters = getParameters();
            try{
                int lnPetty = 0;
                if(!txtPettyCashxxx.getText().toString().isEmpty()){
                    lnPetty = Integer.parseInt(Objects.requireNonNull(txtPettyCashxxx.getText()).toString().replace(",", ""));
                }
                parameters.put("sTransNox", Objects.requireNonNull(txtTransNox.getText()).toString());
                parameters.put("sBranchCd", BranchCd);
                parameters.put("nPettyAmt", lnPetty);
                parameters.put("sORNoxxxx", Objects.requireNonNull(txtOfficialReceipt.getText()).toString());
                parameters.put("sSINoxxxx", Objects.requireNonNull(txtSalesInvoice.getText()).toString());
                parameters.put("sPRNoxxxx", Objects.requireNonNull(txtProvisionalReceipt.getText()).toString());
                parameters.put("sCRNoxxxx", Objects.requireNonNull(txtCollectionReceipt.getText()).toString());
                parameters.put("sORNoxNPt", Objects.requireNonNull(txtORNorthPoint.getText()).toString());
                parameters.put("sPRNoxNPt", Objects.requireNonNull(txtPRNorthPoint.getText()).toString());
                parameters.put("sDRNoxxxx", Objects.requireNonNull(txtDeliveryRcpt.getText()).toString());
                parameters.put("dTransact", AppConstants.CURRENT_DATE);
                parameters.put("dEntryDte", new AppConstants().DATE_MODIFIED);
                parameters.put("sReqstdBy", EmployID);
                infoModel.setCrNoxxxx(txtCollectionReceipt.getText().toString());
                infoModel.setPrNoxxxx(txtProvisionalReceipt.getText().toString());
                infoModel.setSiNoxxxx(txtSalesInvoice.getText().toString());
                infoModel.setOrNoxxxx(txtOfficialReceipt.getText().toString());
                infoModel.setEntryTme(new AppConstants().DATE_MODIFIED);
                mViewModel.saveCashCount(infoModel, parameters, Activity_CashCountSubmit.this);
            } catch (JSONException e) {
                e.printStackTrace();
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
        txtORNorthPoint = findViewById(R.id.txtORNorthPoint);
        txtPRNorthPoint = findViewById(R.id.txtPRNorthPoint);
        txtDeliveryRcpt = findViewById(R.id.txtDeliveryReceipt);
        txtPettyCashxxx = findViewById(R.id.txtPettyCash);
        txtTotalSalesxx = findViewById(R.id.txtTotalSales);
        txtTransNox = findViewById(R.id.txtTransNox);
        btnSendToServer = findViewById(R.id.btnSendToServer);
        txtCurr_DateTime = findViewById(R.id.txtCurrentDateTime);
        txtRequestID = findViewById(R.id.txt_nameSearch);

        btnQuickSearch = findViewById(R.id.btn_quick_search);

        tilOfflRecpt = findViewById(R.id.til_ccOR);
        tilSalesRcpt = findViewById(R.id.til_ccSI);
        tilPrvnlRcpt = findViewById(R.id.til_ccPR);
        tilCllctRcpt = findViewById(R.id.til_ccCR);

        txtPettyCashxxx.addTextChangedListener(new FormatUIText.CurrencyFormat(txtPettyCashxxx));
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
        poMessage.initDialog();
        poMessage.setTitle("Kwik Search");
        poMessage.setMessage(message);
        poMessage.setPositiveButton("Okay", (view, dialog) ->{
            dialog.dismiss();
        });
        poMessage.show();

    }
    public void initDialog(List<RequestNamesInfoModel> infoList){
        DialogKwikSearch loDialog = new DialogKwikSearch(Activity_CashCountSubmit.this,infoList);
        loDialog.initDialogKwikSearch((dialog, name, employID) -> {
            txtRequestID.setText(name);
            EmployID = employID;
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
            return new JSONObject(getIntent().getStringExtra("params"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void initDialog(String title, String message){
        poMessage.initDialog();
        poMessage.setTitle(title);
        poMessage.setMessage(message);
        poMessage.setPositiveButton("Okay", (view, dialog) -> {
            dialog.dismiss();
            checkEmployeeLevelForInventory();
        });
        poMessage.show();
    }

    @Override
    public void onStartSaveCashCount() {
        poDialogx.initDialog("Cash Count", "Sending cash count. Please wait...", false);
        poDialogx.show();
    }

    @Override
    public void onSuccessSaveCashCount() {
        isTapSubmit = false;
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
        isTapSubmit = false;
//        checkEmployeeLevelForInventory();
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
        if(!cIsMobile && mViewModel.getEmployeeLevel().equalsIgnoreCase(String.valueOf(DeptCode.LEVEL_AREA_MANAGER))) {
            mViewModel.CheckConnectivity(isDeviceConnected -> {
                if (!isDeviceConnected) {
                    Activity_CashCounter.getInstance().finish();
                    finish();
                } else if(!AppConfigPreference.getInstance(Activity_CashCountSubmit.this).hasInventory()){
                    Activity_CashCounter.getInstance().finish();
                    finish();
                } else {
                    Intent loIntent = new Intent(Activity_CashCountSubmit.this, Activity_Inventory.class);
                    loIntent.putExtra("BranchCd", BranchCd);
                    startActivity(loIntent);
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