package org.rmj.guanzongroup.onlinecreditapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.etc.FormatUIText;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.g3appdriver.lib.integsys.CreditApp.OnSaveInfoListener;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.Disbursement;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.OnParseListener;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMDisbursement;

import java.util.Objects;

public class Activity_DisbursementInfo extends AppCompatActivity {

    private VMDisbursement mViewModel;
    private MessageBox poMessage;
    private AutoCompleteTextView spnTypex;
    private String typeX = "";
    private TextInputEditText tieFoodx, tieElctx, tieWater, tieLoans, tieBankN, tieCCBnk, tieLimit, tieYearS;
    private Button btnPrev, btnNext;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(Activity_DisbursementInfo.this).get(VMDisbursement.class);
        poMessage = new MessageBox(Activity_DisbursementInfo.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disbursement_info);
        initWidgets();
        mViewModel.InitializeApplication(getIntent());
        mViewModel.GetApplication().observe(Activity_DisbursementInfo.this, new Observer<ECreditApplicantInfo>() {
            @Override
            public void onChanged(ECreditApplicantInfo app) {
                try {
                    mViewModel.getModel().setTransNox(app.getTransNox());
                    mViewModel.ParseData(app, new OnParseListener() {
                        @Override
                        public void OnParse(Object args) {
                            Disbursement loDetail = (Disbursement) args;
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        spnTypex.setAdapter(new ArrayAdapter<>(Activity_DisbursementInfo.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.ACCOUNT_TYPE));
        spnTypex.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        spnTypex.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mViewModel.getModel().setAcctType(String.valueOf(position));
            }
        });

        btnNext.setOnClickListener(v -> SaveDisbursementInfo());
        btnPrev.setOnClickListener(v -> finish());

    }

    private void SaveDisbursementInfo() {
        if(tieElctx.getText().toString().trim().isEmpty()){
            mViewModel.getModel().setElectric(0);
        }else {
            mViewModel.getModel().setElectric(Double.parseDouble(Objects.requireNonNull(tieElctx.getText()).toString().trim()));
        }

        if(tieWater.getText().toString().trim().isEmpty()){
            mViewModel.getModel().setWaterExp(0);
        }else {
            mViewModel.getModel().setWaterExp(Double.parseDouble(
                    Objects.requireNonNull(tieWater.getText()).toString().trim()));
        }

        if(tieFoodx.getText().toString().trim().isEmpty()){
            mViewModel.getModel().setFoodExps(0);
        }else {
            mViewModel.getModel().setFoodExps(Double.parseDouble(
                    Objects.requireNonNull(tieFoodx.getText()).toString().trim()));
        }

        if(tieLoans.getText().toString().trim().isEmpty()){
            mViewModel.getModel().setLoanExps(0);
        }else {
            mViewModel.getModel().setLoanExps(Double.parseDouble(
                    Objects.requireNonNull(tieLoans.getText()).toString().trim()));
        }

        mViewModel.getModel().setBankName(Objects.requireNonNull(tieBankN.getText()).toString().trim());
        mViewModel.getModel().setCrdtBank(Objects.requireNonNull(tieCCBnk.getText()).toString().trim());

        if(tieLimit.getText().toString().trim().isEmpty()){
            mViewModel.getModel().setCrdtLimt(0);
        }else {
            mViewModel.getModel().setCrdtLimt(Double.parseDouble(
                    Objects.requireNonNull(tieLimit.getText()).toString().trim()));
        }
        if(tieYearS.getText().toString().trim().isEmpty()){
            mViewModel.getModel().setCrdtYear(0);
        }else{
            mViewModel.getModel().setCrdtYear(Integer.parseInt(
                    Objects.requireNonNull(tieYearS.getText()).toString().trim()));
        }

        mViewModel.SaveData(new OnSaveInfoListener() {
            @Override
            public void OnSave(String args) {
                Intent loIntent = new Intent(Activity_DisbursementInfo.this, Activity_Properties.class);
                loIntent.putExtra("sTransNox", args);
                startActivity(loIntent);
                overridePendingTransition(R.anim.anim_intent_slide_in_right, R.anim.anim_intent_slide_out_left);
            }

            @Override
            public void OnFailed(String message) {
                poMessage.initDialog();
                poMessage.setTitle("Credit Online Application");
                poMessage.setMessage(message);
                poMessage.setPositiveButton("Okay", (view1, dialog) -> dialog.dismiss());
                poMessage.show();
            }
        });
    }


    private void initWidgets() {
        toolbar = findViewById(R.id.toolbar_DisbursementInfo);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Disbursement Info");

        tieElctx = findViewById(R.id.tie_cap_dbmElectricity);
        tieWater = findViewById(R.id.tie_cap_dbmWater);
        tieFoodx = findViewById(R.id.tie_cap_dbmFood);
        tieLoans = findViewById(R.id.tie_cap_dbmLoans);
        tieBankN = findViewById(R.id.tie_cap_dbmBankName);

        spnTypex = findViewById(R.id.spinner_cap_dbmAccountType);

        tieCCBnk = findViewById(R.id.tie_cap_dbmBankNameCC);
        tieLimit = findViewById(R.id.tie_cap_dbmCreditLimit);
        tieYearS = findViewById(R.id.tie_cap_dbmYearStarted);

//        tieElctx.addTextChangedListener(new FormatUIText.CurrencyFormat(tieElctx));
//        tieWater.addTextChangedListener(new FormatUIText.CurrencyFormat(tieWater));
//        tieFoodx.addTextChangedListener(new FormatUIText.CurrencyFormat(tieFoodx));
//        tieLoans.addTextChangedListener(new FormatUIText.CurrencyFormat(tieLoans));
//        tieLimit.addTextChangedListener(new FormatUIText.CurrencyFormat(tieLimit));

        btnNext = findViewById(R.id.btn_creditAppNext);
        btnPrev = findViewById(R.id.btn_creditAppPrvs);

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_intent_slide_in_left, R.anim.anim_intent_slide_out_right);
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

    @Override
    protected void onDestroy() {
        getViewModelStore().clear();
        super.onDestroy();
    }
}