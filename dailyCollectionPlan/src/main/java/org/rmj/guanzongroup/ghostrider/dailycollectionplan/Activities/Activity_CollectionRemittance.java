package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.rmj.g3appdriver.GRider.Database.Entities.EDCP_Remittance;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.VMCollectionRemittance;

import java.util.Objects;

public class Activity_CollectionRemittance extends AppCompatActivity {

    private VMCollectionRemittance mViewModel;
    private LoadDialog poDialogx;
    private MessageBox poMessage;
    private String psCheckx, psCashxx;
    private EDCP_Remittance poRemit;

    private Toolbar toolbar;
    private TextView lblBranch, lblAddrss;
    private AutoCompleteTextView txtBranch, txtBankNm;
    private TextInputEditText txtAmount, txtAccNo, txtRefNox, txtPaymPnr;
    private LinearLayout linearBrnch, linearBank, linearPaym;
    private TextInputLayout tilRefNox;
    private RadioGroup rgRemitType, rgRemitance;
    private Button btnRemitAll, btnConfirm, btnCancel;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_remittance);
        poDialogx = new LoadDialog(Activity_CollectionRemittance.this);
        poMessage = new MessageBox(Activity_CollectionRemittance.this);
        poRemit = new EDCP_Remittance();
        mViewModel = new ViewModelProvider(this).get(VMCollectionRemittance.class);
        mViewModel.setTransact(getIntent().getStringExtra("dTransact"));

        initWidgets();

        mViewModel.getUserBranchInfo().observe(this, eBranchInfo -> {
            try{
                lblBranch.setText(eBranchInfo.getBranchNm());
                lblAddrss.setText(eBranchInfo.getAddressx());
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        rgRemitType.setOnCheckedChangeListener((group, checkedId) -> {
            if(checkedId == R.id.rb_remitCash){
                poRemit.setPaymForm("0");
                txtAmount.setText("");
//                if(psCltCashx != null) {
//                    if (!psCltCashx.isEmpty()) {
//                        btnRemitAll.setText("Total Cash-On-Hand" + FormatUIText.getCurrencyUIFormat(psCltCashx));
//                    } else {
//                        btnRemitAll.setText("No Cash On Hand");
//                    }
//                } else {
//                    btnRemitAll.setText("No Cash On Hand");
//                }
            } else if(checkedId == R.id.rb_remitCheck){
                poRemit.setPaymForm("1");
                txtAmount.setText("");
//                if(psCltCheck != null) {
//                    if (!psCltCheck.isEmpty()) {
//                        btnRemitAll.setText("Total Cash-On-Hand" + FormatUIText.getCurrencyUIFormat(psCltCheck));
//                    } else {
//                        btnRemitAll.setText("No Check Payment On Hand");
//                    }
//                } else {
//                    btnRemitAll.setText("No Check Payment On Hand");
//                }
            }
        });

        rgRemitance.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_remitBranch) {
                linearBrnch.setVisibility(View.VISIBLE);
                linearBank.setVisibility(View.GONE);
                linearPaym.setVisibility(View.GONE);
                tilRefNox.setVisibility(View.GONE);
                poRemit.setRemitTyp("0");
            } else if(checkedId == R.id.rb_remitBank){
                linearBrnch.setVisibility(View.GONE);
                linearBank.setVisibility(View.VISIBLE);
                linearPaym.setVisibility(View.GONE);
                tilRefNox.setVisibility(View.VISIBLE);
                poRemit.setRemitTyp("1");
            } else {
                linearBrnch.setVisibility(View.GONE);
                linearBank.setVisibility(View.GONE);
                linearPaym.setVisibility(View.VISIBLE);
                tilRefNox.setVisibility(View.VISIBLE);
                poRemit.setRemitTyp("2");
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
        txtBankNm = findViewById(R.id.txt_remitBankNme);
        txtAmount = findViewById(R.id.txt_remitAmount);
        txtAccNo = findViewById(R.id.txt_remitAccNo);
        txtRefNox = findViewById(R.id.txt_remitReferenceNo);
        txtPaymPnr = findViewById(R.id.txt_remitPaymPartner);
        tilRefNox = findViewById(R.id.til_remitReferenceNo);
        linearBrnch = findViewById(R.id.linear_remitBranch);
        linearBank = findViewById(R.id.linear_remitBank);
        linearPaym = findViewById(R.id.linear_remitPartner);
        btnRemitAll = findViewById(R.id.btn_remitAll);
        btnConfirm = findViewById(R.id.btn_confirm);
        btnCancel = findViewById(R.id.btn_cancel);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}