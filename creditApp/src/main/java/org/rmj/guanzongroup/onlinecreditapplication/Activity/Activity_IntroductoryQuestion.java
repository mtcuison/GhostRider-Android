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

package org.rmj.guanzongroup.onlinecreditapplication.Activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.simple.JSONObject;
import org.rmj.g3appdriver.etc.GToast;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.TextFormatter;
import org.rmj.guanzongroup.onlinecreditapplication.Model.PurchaseInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMIntroductoryQuestion;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import static org.rmj.guanzongroup.onlinecreditapplication.R.color.androidx_core_ripple_material_light;
import static org.rmj.guanzongroup.onlinecreditapplication.R.color.material_white;

public class Activity_IntroductoryQuestion extends AppCompatActivity implements ViewModelCallBack {
    public static final String TAG = Activity_IntroductoryQuestion.class.getSimpleName();
    public static String lsApplType = "-1";
    public static String lsCustType = "-1";
    public static String spnTermPosition = "36";
    private VMIntroductoryQuestion mViewModel;
    private PurchaseInfoModel model;
    private TextView lblBranchNm, lblBrandAdd, lblDate;
    private AutoCompleteTextView txtBranchNm, txtBrandNm, txtModelNm;
    private TextInputLayout tilApplType;

    private TextInputEditText txtDownPymnt, txtAmort, txtDTarget;
    private AutoCompleteTextView spnApplType, spnCustomerType, spnTerm;
    private MaterialButton btnCreate;
    public static Activity_IntroductoryQuestion newInstance() {
        return new Activity_IntroductoryQuestion();
    }
    @SuppressLint({"ResourceAsColor", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory_question);
        initWidgets();
        txtDownPymnt.addTextChangedListener(new TextFormatter.OnTextChangedCurrencyFormatter(txtDownPymnt));
        mViewModel = new ViewModelProvider(this).get(VMIntroductoryQuestion.class);
        model = new PurchaseInfoModel();
        mViewModel.getApplicationType().observe(this, stringArrayAdapter -> {
            spnApplType.setAdapter(stringArrayAdapter);
            spnApplType.setDropDownBackgroundResource(R.drawable.bg_gradient_light);

        });
        mViewModel.getCustomerType().observe(this, stringArrayAdapter -> {
            spnCustomerType.setAdapter(stringArrayAdapter);
            spnCustomerType.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        });

        mViewModel.getUserBranchInfo().observe(this, eBranchInfo -> {
            try {
                lblBranchNm.setText(eBranchInfo.getBranchNm());
                lblBrandAdd.setText(eBranchInfo.getAddressx());
                txtBranchNm.setText(eBranchInfo.getBranchNm());
                mViewModel.setBanchCde(eBranchInfo.getBranchCd());
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        txtDTarget.setOnClickListener(v -> {
            final Calendar newCalendar = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat") final SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM dd, yyyy");
            final DatePickerDialog  StartTime = new DatePickerDialog(this, (view131, year, monthOfYear, dayOfMonth) -> {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String lsDate = dateFormatter.format(newDate.getTime());
                txtDTarget.setText(lsDate);
                model.setdTargetDte(lsDate);
            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
            StartTime.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            StartTime.show();
        });

        mViewModel.getAllBranchNames().observe(this, strings -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, strings);
            txtBranchNm.setAdapter(adapter);
            txtBranchNm.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        });

        txtBranchNm.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getAllBranchInfo().observe(this, eBranchInfos -> {
            for(int x = 0; x < eBranchInfos.size(); x++){
                if(txtBranchNm.getText().toString().equalsIgnoreCase(eBranchInfos.get(x).getBranchNm())){
                    model.setsBranchCde(eBranchInfos.get(x).getBranchCd());
                    break;
                }
            }
        }));

        mViewModel.getAllBrandNames().observe(this, strings -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, strings);
            txtBrandNm.setAdapter(adapter);
            txtBrandNm.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        });
        mViewModel.getAllBrandNames().observe(this, strings -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, strings);
            txtBrandNm.setAdapter(adapter);
            txtBrandNm.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        });

        txtBrandNm.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getAllMcBrand().observe(this, eMcBrands -> {
            for(int x = 0; x < eMcBrands.size(); x++){
                if(txtBrandNm.getText().toString().equalsIgnoreCase(eMcBrands.get(x).getBrandNme())){
                    mViewModel.setLsBrandID(eMcBrands.get(x).getBrandIDx());
                    model.setsBrandIDxx(eMcBrands.get(x).getBrandNme());
                    break;
                }
            }
        }));

        mViewModel.getBrandID().observe(this, s -> mViewModel.getAllBrandModelName(s).observe(this, strings -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, strings);
            txtModelNm.setAdapter(adapter);
            txtModelNm.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        }));

        txtModelNm.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getAllBrandModelInfo().observe(this, eMcModels -> {
            for(int x = 0; x < eMcModels.size(); x++){
                if(txtModelNm.getText().toString().equalsIgnoreCase(eMcModels.get(x).getModelNme() +" "+ eMcModels.get(x).getModelCde())){
                    mViewModel.setLsModelCd(eMcModels.get(x).getModelIDx());
                    model.setsModelIDxx(eMcModels.get(x).getModelIDx());
                    break;
                }
            }
        }));

        mViewModel.getModelID().observe(this, s -> {
            mViewModel.getDpInfo(s).observe(this, mcDPInfo -> {
                try{
                    JSONObject loJson = new JSONObject();
                    loJson.put("sModelIDx", mcDPInfo.ModelIDx);
                    loJson.put("sModelNme", mcDPInfo.ModelNme);
                    loJson.put("nRebatesx", mcDPInfo.Rebatesx);
                    loJson.put("nMiscChrg", mcDPInfo.MiscChrg);
                    loJson.put("nEndMrtgg", mcDPInfo.EndMrtgg);
                    loJson.put("nMinDownx", mcDPInfo.MinDownx);
                    loJson.put("nSelPrice", mcDPInfo.SelPrice);
                    loJson.put("nLastPrce", mcDPInfo.LastPrce);
                    mViewModel.setLoDpInfo(loJson);
                } catch (Exception e){
                    e.printStackTrace();
                }
            });

            mViewModel.getMonthlyAmortInfo(s).observe(this, mcAmortInfo -> {
                try{
                    JSONObject loJson = new JSONObject();
                    loJson.put("nSelPrice", mcAmortInfo.SelPrice);
                    loJson.put("nMinDownx", mcAmortInfo.MinDownx);
                    loJson.put("nMiscChrg", mcAmortInfo.MiscChrg);
                    loJson.put("nRebatesx", mcAmortInfo.Rebatesx);
                    loJson.put("nEndMrtgg", mcAmortInfo.EndMrtgg);
                    loJson.put("nAcctThru", mcAmortInfo.AcctThru);
                    loJson.put("nFactorRt", mcAmortInfo.FactorRt);
                    mViewModel.setLoMonthlyInfo(loJson);
                } catch (Exception e){
                    e.printStackTrace();
                }
            });
        });

        mViewModel.getDownpayment().observe(this, s -> txtDownPymnt.setText(s));

        txtDownPymnt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try{
                    mViewModel.calculateMonthlyPayment(Objects.requireNonNull(txtDownPymnt.getText()).toString().replace(",", ""));
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {            }
        });

        mViewModel.getInstallmentTerm().observe(this, stringArrayAdapter -> {
            spnTerm.setAdapter(stringArrayAdapter);
            spnTerm.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        });

        mViewModel.getSelectedInstallmentTerm().observe(this, integer -> mViewModel.calculateMonthlyPayment());

        spnApplType.setOnItemClickListener(new Activity_IntroductoryQuestion.OnItemClickListener(spnApplType, mViewModel));
        spnCustomerType.setOnItemClickListener(new Activity_IntroductoryQuestion.OnItemClickListener(spnCustomerType, mViewModel));
        spnTerm.setOnItemClickListener(new Activity_IntroductoryQuestion.OnItemClickListener(spnTerm, mViewModel));

        mViewModel.getMonthlyAmort().observe(this, s -> txtAmort.setText(s));
        btnCreate.setOnClickListener(view -> submitNewApplication());
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
        spnCustomerType = findViewById(R.id.spn_customerType);
        spnTerm = findViewById(R.id.spn_installmentTerm);

        btnCreate = findViewById(R.id.btn_createCreditApp);
    }

    private void submitNewApplication() {
        try{
        model.setsAppTypex(lsApplType);
        model.setsCustTypex(lsCustType);
        model.setsDownPaymt(Double.parseDouble((Objects.requireNonNull(txtDownPymnt.getText()).toString().replace(",", ""))));
        model.setsAccTermxx(Integer.parseInt(spnTermPosition));
        model.setsMonthlyAm(Double.parseDouble((Objects.requireNonNull(txtAmort.getText()).toString()).replace(",", "")));

        mViewModel.CreateNewApplication(model, Activity_IntroductoryQuestion.this);
        }catch (NullPointerException e){
            e.printStackTrace();
            GToast.CreateMessage(this,"Something went wrong. Required information might not provided by user.",GToast.INFORMATION).show();
        } catch (Exception e){
            e.printStackTrace();
            GToast.CreateMessage(this,"Something went wrong. Required information might not provided by user.",GToast.INFORMATION).show();
        }
    }
    @Override
    public void onSaveSuccessResult(String args) {
        Intent loIntent = new Intent(this, Activity_CreditApplication.class);
        loIntent.putExtra("transno", args);
        startActivity(loIntent);
        finish();
    }

    @Override
    public void onFailedResult(String message) {
        GToast.CreateMessage(this, message, GToast.ERROR).show();
    }
        class OnItemClickListener implements AdapterView.OnItemClickListener {
            AutoCompleteTextView poView;
            private final VMIntroductoryQuestion vm;
            public OnItemClickListener(AutoCompleteTextView view, VMIntroductoryQuestion viewModel) {
                this.poView = view;
                this.vm = viewModel;
            }
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(spnApplType.equals(poView)) {
                    String appType = String.valueOf(i);
                    lsApplType = appType;
                    vm.setApplicationType(appType);
                    Log.e("app position",appType);
                }
                if(spnCustomerType.equals(poView)){
                    String type = "";
                    switch (i){
                        case 0:
                            break;
                        case 1:
                            type = "0";
                            break;
                        case 2:
                            type = "1";
                            break;
                    }
                    lsCustType = String.valueOf(i);
                    vm.setCustomerType(type);
                }
                if(spnTerm.equals(poView)) {
                    int term = 0;
                    switch (i) {
                        case 0:
                            term = 36;
                            break;
                        case 1:
                            term = 24;
                            break;
                        case 2:
                            term = 18;
                            break;
                        case 3:
                            term = 12;
                            break;
                        case 4:
                            term = 6;
                            break;
                    }

                    spnTermPosition = String.valueOf(term);
                    vm.setLsIntTerm(term);
                }
            }
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