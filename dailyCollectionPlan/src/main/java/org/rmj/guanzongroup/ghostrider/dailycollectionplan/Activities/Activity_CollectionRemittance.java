/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.dailyCollectionPlan
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCP_Remittance;
import org.rmj.g3appdriver.GRider.Etc.FormatUIText;
import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model.CollectionRemittanceInfoModel;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.VMCollectionRemittance;

import java.util.Objects;

public class Activity_CollectionRemittance extends AppCompatActivity {

    private VMCollectionRemittance mViewModel;
    private LoadDialog poDialogx;
    private MessageBox poMessage;
    private EDCP_Remittance poRemit;

    private Toolbar toolbar;
    private TextView lblBranch,
            lblAddrss,
            lblCltdCash,
            lblCltdChck,
            lblRmtBrnch,
            lblRmtBankx,
            lblRmtOther,
            lblOHCashxx,
            lblOHCheckx;
    public AutoCompleteTextView txtBranch, txtAccNox;
    private TextInputEditText txtAmount, txtAccName, txtRefNox;
    private LinearLayout linearBrnch, linearBank;
    private TextInputLayout tilRefNox;
    private RadioGroup rgRemitType, rgRemitance;
    private Button btnRemitAll, btnRemit;

    private String psCltCashx = "0.0", psCltCheck = "0.0";

    private boolean isCheck = false;
    private CollectionRemittanceInfoModel infoModel;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_remittance);
        poDialogx = new LoadDialog(Activity_CollectionRemittance.this);
        poMessage = new MessageBox(Activity_CollectionRemittance.this);
        poRemit = new EDCP_Remittance();
        infoModel =  new CollectionRemittanceInfoModel();
        initWidgets();
        mViewModel = new ViewModelProvider(this).get(VMCollectionRemittance.class);
        mViewModel.setTransact(getIntent().getStringExtra("dTransact"));

        mViewModel.InitializeBranchAccountNox(new VMCollectionRemittance.OnInitializeBranchAccountCallback() {
            @Override
            public void OnDownload() {
                poDialogx.initDialog("Collection Remittance", "Initializing remittance accounts. Please wait...", false);
                poDialogx.dismiss();
            }

            @Override
            public void OnSuccessDownload() {
                poDialogx.dismiss();
            }

            @Override
            public void OnFailedDownload(String message) {
                poDialogx.dismiss();
            }
        });

        mViewModel.getUserBranchInfo().observe(this, eBranchInfo -> {
            try{
                lblBranch.setText(eBranchInfo.getBranchNm());
                lblAddrss.setText(eBranchInfo.getAddressx());
                txtBranch.setText(eBranchInfo.getBranchNm());
                poRemit.setBankAcct(eBranchInfo.getBranchCd());
                poRemit.setPaymForm("0");
                poRemit.setRemitTyp("0");
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getTotalCollectedCash().observe(this, s -> {
            try {
                lblCltdCash.setText("Total Collected Cash : " + FormatUIText.getCurrencyUIFormat(s));
                mViewModel.Calculate_COH_Remitted(result -> lblOHCashxx.setText("Cash-On-Hand : " + FormatUIText.getCurrencyUIFormat(result)));
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getTotalCollectedCheck().observe(this, s -> {
            try {
                lblCltdChck.setText("Total Collected Check : " + FormatUIText.getCurrencyUIFormat(s));
                mViewModel.Calculate_Check_Remitted(result -> lblOHCheckx.setText("Check-On-Hand : " + FormatUIText.getCurrencyUIFormat(result)));
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getTotalBranchRemittedCollection().observe(this, s -> {
            try {
                lblRmtBrnch.setText("Remitted on Branch : " + FormatUIText.getCurrencyUIFormat(s));
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getTotalBankRemittedCollection().observe(this, s -> {
            try {
                lblRmtBankx.setText("Remitted on Bank : " + FormatUIText.getCurrencyUIFormat(s));
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getTotalOtherRemittedCollection().observe(this, s -> {
            try {
                lblRmtOther.setText("Remitted on Payment Partner : " + FormatUIText.getCurrencyUIFormat(s));
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getBranchRemittanceList().observe(this, strings -> txtBranch.setAdapter(new ArrayAdapter<>(Activity_CollectionRemittance.this, android.R.layout.simple_spinner_dropdown_item, strings)));

        rgRemitType.setOnCheckedChangeListener((group, checkedId) -> {
            if(checkedId == R.id.rb_remitCash){
                isCheck = false;
                infoModel.setCheck(false);
                poRemit.setPaymForm("0");
                txtAmount.setText("");
                if(psCltCashx != null) {
                    if (!psCltCashx.isEmpty()) {
                        btnRemitAll.setText("Total Cash-On-Hand" + FormatUIText.getCurrencyUIFormat(psCltCashx));
                    } else {
                        btnRemitAll.setText("No Cash On Hand");
                    }
                } else {
                    btnRemitAll.setText("No Cash On Hand");
                }
            } else if(checkedId == R.id.rb_remitCheck){
                isCheck = true;
                infoModel.setCheck(true);
                poRemit.setPaymForm("1");
                txtAmount.setText("");
                if(psCltCheck != null) {
                    if (!psCltCheck.isEmpty()) {
                        btnRemitAll.setText("Total Check-On-Hand" + FormatUIText.getCurrencyUIFormat(psCltCheck));
                    } else {
                        btnRemitAll.setText("No Check Payment On Hand");
                    }
                } else {
                    btnRemitAll.setText("No Check Payment On Hand");
                }
            }
        });

        mViewModel.getTotalCollectedCash().observe(this, s -> {
            try{
                mViewModel.setTotalCash(Double.parseDouble(s));
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getTotalCollectedCheck().observe(this, s -> {
            try{
                mViewModel.setTotalCheck(Double.parseDouble(s));
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getTotalRemittedCash().observe(this, s -> {
            try{
                mViewModel.setTotalRemittedCash(Double.parseDouble(s));
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getTotalRemittedCheck().observe(this, s -> {
            try{
                mViewModel.setTotalRemittedCheck(Double.parseDouble(s));
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getCashOnHand().observe(this, s -> {
            try{
                psCltCashx = s;
                infoModel.setPsCltCashx(s);
                btnRemitAll.setText("Total Cash-On-Hand" + FormatUIText.getCurrencyUIFormat(psCltCashx));
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getCheckOnHand().observe(this, s -> {
            try{
                psCltCheck = s;
                infoModel.setPsCltCheck(s);
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        rgRemitance.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_remitBranch) {
                linearBrnch.setVisibility(View.VISIBLE);
                linearBank.setVisibility(View.GONE);
                tilRefNox.setVisibility(View.GONE);
                poRemit.setRemitTyp("0");
                txtAccName.setText("");
                txtAccNox.setText("");
            } else if(checkedId == R.id.rb_remitBank){
                linearBrnch.setVisibility(View.GONE);
                linearBank.setVisibility(View.VISIBLE);
                tilRefNox.setVisibility(View.VISIBLE);
                txtAccName.setText("");
                txtAccNox.setText("");
                poRemit.setRemitTyp("1");

                mViewModel.getRemittanceBankAccountsList().observe(this, remittanceAccounts -> {
                    try {
                        String[] lsAccounts = new String[remittanceAccounts.size()];
                        for(int x = 0; x < remittanceAccounts.size(); x++){
                            lsAccounts[x] = remittanceAccounts.get(x).getActNumbr() + " (" + remittanceAccounts.get(x).getActNamex() +")";
                        }
                        txtAccNox.setAdapter(new ArrayAdapter<>(Activity_CollectionRemittance.this, android.R.layout.simple_spinner_dropdown_item, lsAccounts));

                        txtAccNox.setOnItemClickListener((parent, view, position, id) -> {
                            String[] lsSplitStr = txtAccNox.getText().toString().split(" ");
                            String lsAcctNox = lsSplitStr[0];
                            for(int x = 0; x < remittanceAccounts.size(); x++){
                                if(lsAcctNox.equalsIgnoreCase(remittanceAccounts.get(x).getActNumbr())){
                                    txtAccName.setText(remittanceAccounts.get(x).getActNamex());
                                    txtAccNox.setText(remittanceAccounts.get(x).getActNumbr());
                                    break;
                                }
                            }
                        });
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                });
            } else {
                linearBrnch.setVisibility(View.GONE);
                linearBank.setVisibility(View.VISIBLE);
                tilRefNox.setVisibility(View.VISIBLE);
                poRemit.setRemitTyp("2");
                txtAccName.setText("");
                txtAccNox.setText("");
                mViewModel.getRemittanceOtherAccountList().observe(this, remittanceAccounts -> {
                    try {
                        String[] lsAccounts = new String[remittanceAccounts.size()];
                        for(int x = 0; x < remittanceAccounts.size(); x++){
                            lsAccounts[x] = remittanceAccounts.get(x).getActNumbr() + " (" + remittanceAccounts.get(x).getActNamex() +")";
                        }
                        txtAccNox.setAdapter(new ArrayAdapter<>(Activity_CollectionRemittance.this, android.R.layout.simple_spinner_dropdown_item, lsAccounts));

                        txtAccNox.setOnItemClickListener((parent, view, position, id) -> {
                            String[] lsSplitStr = txtAccNox.getText().toString().split(" ");
                            String lsAcctNox = lsSplitStr[0];
                            for(int x = 0; x < remittanceAccounts.size(); x++){
                                if(lsAcctNox.equalsIgnoreCase(remittanceAccounts.get(x).getActNumbr())){
                                    txtAccName.setText(remittanceAccounts.get(x).getActNamex());
                                    txtAccNox.setText(remittanceAccounts.get(x).getActNumbr());
                                    break;
                                }
                            }
                        });
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                });
            }
        });

        btnRemitAll.setOnClickListener(v -> {
            if(poRemit.getPaymForm().equalsIgnoreCase("0")){
                txtAmount.setText(psCltCashx);
            } else {
                txtAmount.setText(psCltCheck);
            }
        });

        btnRemit.setOnClickListener(v ->{

            infoModel.setRemitTyp(poRemit.getRemitTyp());

            switch (poRemit.getRemitTyp()) {
                case "0":
                    infoModel.setCompnyNm(txtBranch.getText().toString());
                    poRemit.setCompnyNm(txtBranch.getText().toString());
                    break;
                case "1":
                case "2":
                    infoModel.setCompnyNm(Objects.requireNonNull(txtAccName.getText()).toString());
                    infoModel.setBankAcct(txtAccNox.getText().toString());
                    infoModel.setReferNox(Objects.requireNonNull(txtRefNox.getText()).toString());

                    poRemit.setCompnyNm(Objects.requireNonNull(txtAccName.getText()).toString());
                    poRemit.setBankAcct(Objects.requireNonNull(txtAccNox.getText()).toString());
                    poRemit.setReferNox(Objects.requireNonNull(txtRefNox.getText()).toString());
                    break;
            }
            poRemit.setAmountxx(Objects.requireNonNull(txtAmount.getText()).toString().replace(",", ""));
            poRemit.setTimeStmp(new AppConstants().DATE_MODIFIED);
            infoModel.setTimeStmp(new AppConstants().DATE_MODIFIED);
            infoModel.setPsBranch(txtBranch.getText().toString());
            infoModel.setAmountxx(txtAmount.getText().toString());
            if(infoModel.isDataValid()) {
                mViewModel.RemitCollection(poRemit,new VMCollectionRemittance.OnRemitCollectionCallback() {
                    @Override
                    public void OnRemit() {
                        poDialogx.initDialog("Collection Remittance", "Sending collection remittance info. Please wait...", false);
                        poDialogx.show();
                    }

                    @Override
                    public void OnSuccess() {
                        poDialogx.dismiss();
                        poMessage.initDialog();
                        poMessage.setTitle("Collection Remittance");
                        poMessage.setMessage("Your remittance has been posted successfully.");
                        poMessage.setPositiveButton("Okay", (view, dialog) -> {
                            dialog.dismiss();
                            Intent loIntent = new Intent(Activity_CollectionRemittance.this, Activity_LogCollection.class);
                            startActivity(loIntent);
                            finish();
                        });
                        poMessage.show();
                    }

                    @Override
                    public void OnFailed(String message) {
                        poDialogx.dismiss();
                        poMessage.initDialog();
                        poMessage.setTitle("Collection Remittance");
                        poMessage.setMessage(message);
                        poMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
                        poMessage.show();
                    }
                });
            }else{
                GToast.CreateMessage(this, infoModel.getMessage(), GToast.ERROR).show();
            }
        });
    }

    private void initWidgets(){
        lblBranch = findViewById(R.id.lbl_headerBranch);
        lblAddrss = findViewById(R.id.lbl_headerAddress);

        toolbar = findViewById(R.id.toolbar_collectionRemit);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        rgRemitType = findViewById(R.id.rg_remittance);
        rgRemitance = findViewById(R.id.rg_remittanceOutlet);
        txtBranch = findViewById(R.id.txt_remitBranch);
        txtAccNox = findViewById(R.id.txt_remitAcctNox);
        txtAmount = findViewById(R.id.txt_remitAmount);
        txtAccName = findViewById(R.id.txt_remitAccName);
        txtRefNox = findViewById(R.id.txt_remitReferenceNo);
        tilRefNox = findViewById(R.id.til_remitReferenceNo);
        linearBrnch = findViewById(R.id.linear_remitBranch);
        linearBank = findViewById(R.id.linear_remitBankOthers);
        lblCltdCash = findViewById(R.id.lbl_collectedCash);
        lblCltdChck = findViewById(R.id.lbl_collectedCheck);
        lblRmtBrnch = findViewById(R.id.lbl_remittanceBranch);
        lblRmtBankx = findViewById(R.id.lbl_remittanceBank);
        lblRmtOther = findViewById(R.id.lbl_remittanceOthers);
        lblOHCashxx = findViewById(R.id.lbl_remittanceCash);
        lblOHCheckx = findViewById(R.id.lbl_remittanceCheck);
        btnRemitAll = findViewById(R.id.btn_remitAll);
        btnRemit = findViewById(R.id.btn_confirm);

        txtAmount.addTextChangedListener(new FormatUIText.CurrencyFormat(txtAmount));
        txtAccName.setEnabled(false);
    }

    @Override
    public void onBackPressed() {
        Intent loIntent = new Intent(Activity_CollectionRemittance.this, Activity_LogCollection.class);
        startActivity(loIntent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getViewModelStore().clear();
    }
}