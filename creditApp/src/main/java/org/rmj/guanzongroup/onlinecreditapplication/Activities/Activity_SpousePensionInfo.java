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
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.g3appdriver.lib.integsys.CreditApp.OnSaveInfoListener;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.SpousePension;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.OnParseListener;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMSpousePensionInfo;

import java.util.Objects;

public class Activity_SpousePensionInfo extends AppCompatActivity {

    private VMSpousePensionInfo mViewModel;
    private MessageBox poMessage;

    private TextInputEditText txtPensionAmt, txtRetirementYr, txtOtherSrc, txtOtherSrcInc;
    private AutoCompleteTextView spnSector;
    private Button btnNext, btnPrvs;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(Activity_SpousePensionInfo.this).get(VMSpousePensionInfo.class);
        poMessage = new MessageBox(Activity_SpousePensionInfo.this);
        setContentView(R.layout.activity_spouse_pension_info);
        initWidgets();
        mViewModel.InitializeApplication(getIntent());
        mViewModel.GetApplication().observe(Activity_SpousePensionInfo.this, new Observer<ECreditApplicantInfo>() {
            @Override
            public void onChanged(ECreditApplicantInfo app) {
                mViewModel.getModel().setTransNox(app.getTransNox());
                mViewModel.ParseData(app, new OnParseListener() {
                    @Override
                    public void OnParse(Object args) {
                        SpousePension loDetail = (SpousePension) args;
                    }
                });
            }
        });


        spnSector.setAdapter(new ArrayAdapter<>(Activity_SpousePensionInfo.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.PENSION_SECTOR));
        spnSector.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        spnSector.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mViewModel.getModel().setPensionSector(String.valueOf(position));
            }
        });

        btnNext.setOnClickListener(v -> SaveSpousePensionInfo());
        btnPrvs.setOnClickListener(v -> finish());
    }

    private void SaveSpousePensionInfo() {

        mViewModel.getModel().setPensionIncomeRange(Long.parseLong(Objects.requireNonNull(txtPensionAmt.getText()).toString().trim()));
        mViewModel.getModel().setRetirementYear(Objects.requireNonNull(txtRetirementYr.getText()).toString().trim());
        mViewModel.getModel().setNatureOfIncome(Objects.requireNonNull(txtOtherSrc.getText()).toString().trim());
        mViewModel.getModel().setRangeOfIncom(Long.parseLong(txtOtherSrcInc.getText().toString().trim()));

        mViewModel.SaveData(new OnSaveInfoListener() {
            @Override
            public void OnSave(String args) {
                Intent loIntent = new Intent(Activity_SpousePensionInfo.this, Activity_DisbursementInfo.class);
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
        toolbar = findViewById(R.id.toolbar_SpousePensionInfo);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Spouse Pension Info");

        spnSector = findViewById(R.id.spn_psnSector);

        txtPensionAmt = findViewById(R.id.txt_pension_amt);
        txtRetirementYr = findViewById(R.id.txt_retirement_yr);
        txtOtherSrc = findViewById(R.id.txt_other_source);
        txtOtherSrcInc = findViewById(R.id.txt_other_source_income);

        btnNext = findViewById(R.id.btn_creditAppNext);
        btnPrvs = findViewById(R.id.btn_creditAppPrvs);


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