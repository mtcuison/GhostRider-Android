/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.creditApp
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.onlinecreditapplication.Activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DMcModel;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.etc.FormatUIText;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.LoanInfo;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMIntroductoryQuestion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class Activity_IntroductoryQuestion extends AppCompatActivity {
    public static final String TAG = Activity_IntroductoryQuestion.class.getSimpleName();
    private VMIntroductoryQuestion mViewModel;
    private LoanInfo poDetail;

    private TextView lblBranchNm, lblBrandAdd, lblDate;
    private AutoCompleteTextView txtBranchNm, txtBrandNm, txtModelNm;
    private TextInputLayout tilApplType;

    private TextInputEditText txtDownPymnt, txtAmort, txtDTarget;
    private AutoCompleteTextView spnApplType, spnCustType, spnAcctTerm;
    private MaterialButton btnCreate;

    public static Activity_IntroductoryQuestion newInstance() {
        return new Activity_IntroductoryQuestion();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMIntroductoryQuestion.class);
        poDetail = new LoanInfo();
        setContentView(R.layout.activity_introductory_question);
        initWidgets();

        mViewModel.GetUserInfo().observe(this, eBranchInfo -> {
            try {
                lblBranchNm.setText(eBranchInfo.sBranchNm);
                lblBrandAdd.setText(eBranchInfo.sAddressx);
                txtBranchNm.setText(eBranchInfo.sBranchNm);
                poDetail.setBranchCde(eBranchInfo.sBranchCd);
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.GetApplicationType().observe(this, stringArrayAdapter -> {
            spnApplType.setAdapter(stringArrayAdapter);
            spnApplType.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
            spnApplType.setSelection(0);
            poDetail.setAppTypex(0);
        });

        mViewModel.GetCustomerType().observe(this, stringArrayAdapter -> {
            spnCustType.setAdapter(stringArrayAdapter);
            spnCustType.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
            spnCustType.setSelection(0);
            poDetail.setAppTypex(0);
        });

        mViewModel.GetInstallmentTerm().observe(this, stringArrayAdapter -> {
            spnAcctTerm.setAdapter(stringArrayAdapter);
            spnAcctTerm.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
            spnAcctTerm.setSelection(0);

            //Default value has been set here instead inside of the model in order
            // to calculate monthly amortization upon selection of model.
            poDetail.setAppTypex(36);
        });

        txtDTarget.setText(new AppConstants().CURRENT_DATE_WORD);

        txtDTarget.setOnClickListener(v -> {
            final Calendar newCalendar = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat") final SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM dd, yyyy");
            final DatePickerDialog  StartTime = new DatePickerDialog(this, (view131, year, monthOfYear, dayOfMonth) -> {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String lsDate = dateFormatter.format(newDate.getTime());
                txtDTarget.setText(lsDate);
                poDetail.setTargetDte(lsDate);
            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
            StartTime.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            StartTime.show();
        });

        mViewModel.GetAllBranchInfo().observe(this, loList -> {
            try{
                ArrayList<String> strings = new ArrayList<>();
                for(int x = 0; x < loList.size(); x++){
                    strings.add(loList.get(x).getBranchNm());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(Activity_IntroductoryQuestion.this, android.R.layout.simple_spinner_dropdown_item, strings.toArray(new String[0]));
                txtBranchNm.setAdapter(adapter);
                txtBranchNm.setDropDownBackgroundResource(R.drawable.bg_gradient_light);

                txtBranchNm.setOnItemClickListener((adapterView, view, i, l) -> {
                    for(int x = 0; x < loList.size(); x++){
                        if(txtBranchNm.getText().toString().equalsIgnoreCase(loList.get(x).getBranchNm())){
                            poDetail.setBranchCde(loList.get(x).getBranchCd());
                            break;
                        }
                    }
                });

            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.GetAllMcBrand().observe(this, loList -> {
            try{
                ArrayList<String> strings = new ArrayList<>();
                for(int x = 0; x < loList.size(); x++){
                    strings.add(loList.get(x).getBrandNme());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(Activity_IntroductoryQuestion.this, android.R.layout.simple_spinner_dropdown_item, strings.toArray(new String[0]));
                txtBrandNm.setAdapter(adapter);
                txtBrandNm.setDropDownBackgroundResource(R.drawable.bg_gradient_light);

                txtBrandNm.setOnItemClickListener((adapterView, view, i, l) -> {
                    for(int x = 0; x < loList.size(); x++){
                        if(txtBrandNm.getText().toString().equalsIgnoreCase(loList.get(x).getBrandNme())){
                            mViewModel.setBrandID(loList.get(x).getBrandIDx());
                            poDetail.setBranchCde(loList.get(x).getBrandIDx());
                            break;
                        }
                    }
                });
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.GetBrandID().observe(Activity_IntroductoryQuestion.this, brandID -> {
            try {
                mViewModel.GetAllBrandModelInfo(brandID).observe(Activity_IntroductoryQuestion.this, loList -> {
                    ArrayList<String> strings = new ArrayList<>();
                    for(int x = 0; x < loList.size(); x++){
                        strings.add(loList.get(x).getModelNme() +" "+ loList.get(x).getModelCde());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, strings.toArray(new String[0]));
                    txtModelNm.setAdapter(adapter);
                    txtModelNm.setDropDownBackgroundResource(R.drawable.bg_gradient_light);

                    txtModelNm.setOnItemClickListener((adapterView, view, i, l) -> {
                        for(int x = 0; x < loList.size(); x++){
                            if(txtModelNm.getText().toString().equalsIgnoreCase(loList.get(x).getModelNme() +" "+ loList.get(x).getModelCde())){
                                poDetail.setModelIDxx(loList.get(x).getModelIDx());
                                mViewModel.setModelID(loList.get(x).getModelIDx());
                                break;
                            }
                        }
                    });

                });
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.GetModelID().observe(Activity_IntroductoryQuestion.this, modelID -> {
            try{
                mViewModel.GetMonthlyPayment(modelID, poDetail.getAccTermxx()).observe(Activity_IntroductoryQuestion.this, new Observer<DMcModel.McAmortInfo>() {
                    @Override
                    public void onChanged(DMcModel.McAmortInfo info) {
                        try {
                            Double lnMinDPx = Double.valueOf(info.nMinDownx);
                            Double lnAmortx = mViewModel.GetMonthlyPayment(info, lnMinDPx);
                            txtAmort.setText(FormatUIText.getCurrencyUIFormat(String.valueOf(lnAmortx)));
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        spnApplType.setOnItemClickListener(new OnItemClickListener());
        spnCustType.setOnItemClickListener(new OnItemClickListener());
        spnAcctTerm.setOnItemClickListener(new OnItemClickListener());

        txtDownPymnt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try{

                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        btnCreate.setOnClickListener(view -> {

        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_intent_slide_in_left, R.anim.anim_intent_slide_out_right);
    }

    private void initWidgets(){
        Toolbar toolbar = findViewById(R.id.toolbar_introduction);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        lblBranchNm = findViewById(R.id.lbl_headerBranch);
        lblBrandAdd = findViewById(R.id.lbl_headerAddress);
        lblDate = findViewById(R.id.lbl_headerDate);
        txtBranchNm = findViewById(R.id.txt_branchName);
        txtBrandNm = findViewById(R.id.txt_brandName);
        txtModelNm = findViewById(R.id.txt_modelName);
        txtDownPymnt = findViewById(R.id.txt_downpayment);
        txtAmort = findViewById(R.id.txt_monthlyAmort);
        txtDTarget = findViewById(R.id.txt_dateTarget);
        spnApplType = findViewById(R.id.spn_applicationType);
        tilApplType = findViewById(R.id.til_customerType);
        spnCustType = findViewById(R.id.spn_customerType);
        spnAcctTerm = findViewById(R.id.spn_installmentTerm);

        btnCreate = findViewById(R.id.btn_createCreditApp);
    }

    public void CalculatePayment(double lnArgs, int lnArgs1){
    }

//    private void submitNewApplication() {
//        try{
//        poDetail.setAppTypex(lsApplType);
//        poDetail.setCustTypex(lsCustType);
//        poDetail.setDownPaymt(Double.parseDouble((Objects.requireNonNull(txtDownPymnt.getText()).toString().replace(",", ""))));
//        poDetail.setAccTermxx(Integer.parseInt(spnTermPosition));
//        poDetail.setMonthlyAm(Double.parseDouble((Objects.requireNonNull(txtAmort.getText()).toString()).replace(",", "")));
//
//        mViewModel.CreateNewApplication(poDetail, Activity_IntroductoryQuestion.this);
//        }catch (NullPointerException e){
//            e.printStackTrace();
//            GToast.CreateMessage(this,"Something went wrong. Required information might not provided by user.",GToast.INFORMATION).show();
//        } catch (Exception e){
//            e.printStackTrace();
//            GToast.CreateMessage(this,"Something went wrong. Required information might not provided by user.",GToast.INFORMATION).show();
//        }
//    }
//
//    @Override
//    public void onSaveSuccessResult(String args) {
//        Intent loIntent = new Intent(this, Activity_CreditApplication.class);
//        loIntent.putExtra("transno", args);
//        startActivity(loIntent);
//        finish();
//    }
//
//    @Override
//    public void onFailedResult(String message) {
//        GToast.CreateMessage(this, message, GToast.ERROR).show();
//    }

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

    private class OnItemClickListener implements AdapterView.OnItemClickListener {

        @SuppressLint("ResourceAsColor")
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if(spnApplType.equals(view)) {
                poDetail.setAppTypex(i);
            } else if(spnCustType.equals(view)){
                poDetail.setCustTypex(i);
            } else if (spnAcctTerm.equals(view)) {
                poDetail.setAccTermxx(i);
            }
        }
    }
}