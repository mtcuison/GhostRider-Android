package org.rmj.guanzongroup.onlinecreditapplication.Activity;

import android.content.Intent;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.simple.JSONObject;
import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.TextFormatter;
import org.rmj.guanzongroup.onlinecreditapplication.Model.PurchaseInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMIntroductoryQuestion;

import java.util.Objects;

public class Activity_IntroductoryQuestion extends AppCompatActivity implements ViewModelCallBack {
    public static final String TAG = Activity_IntroductoryQuestion.class.getSimpleName();
    public static String lsApplType = "-1";
    public static String lsCustType = "-1";
    public static String spnTermPosition = "36";
    private VMIntroductoryQuestion mViewModel;
    PurchaseInfoModel model;
    private TextView lblBranchNm, lblBrandAdd, lblDate;
    private AutoCompleteTextView txtBranchNm, txtBrandNm, txtModelNm;
    private TextInputEditText txtDownPymnt, txtAmort;
    private AutoCompleteTextView spnApplType, spnCustomerType, spnTerm;
    private MaterialButton btnCreate;
    public static Activity_IntroductoryQuestion newInstance() {
        return new Activity_IntroductoryQuestion();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory_question);
        initWidgets();
        txtDownPymnt.addTextChangedListener(new TextFormatter.OnTextChangedCurrencyFormatter(txtDownPymnt));
        mViewModel = new ViewModelProvider(this).get(VMIntroductoryQuestion.class);
        model = new PurchaseInfoModel();
        mViewModel.getApplicationType().observe(this, stringArrayAdapter -> spnApplType.setAdapter(stringArrayAdapter));
        mViewModel.getCustomerType().observe(this, stringArrayAdapter -> spnCustomerType.setAdapter(stringArrayAdapter));

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
        });
        mViewModel.getAllBrandNames().observe(this, strings -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, strings);
            txtBrandNm.setAdapter(adapter);
        });

        txtBrandNm.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getAllMcBrand().observe(this, eMcBrands -> {
            for(int x = 0; x < eMcBrands.size(); x++){
                if(txtBrandNm.getText().toString().equalsIgnoreCase(eMcBrands.get(x).getBrandNme())){
                    mViewModel.setLsBrandID(eMcBrands.get(x).getBrandIDx());
                    model.setsBrandIDxx(eMcBrands.get(x).getBrandIDx());
                    break;
                }
            }
        }));

        mViewModel.getBrandID().observe(this, s -> mViewModel.getAllBrandModelName(s).observe(this, strings -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, strings);
            txtModelNm.setAdapter(adapter);
            Log.e(TAG, "Array Adapter has been updated.");
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

        mViewModel.getInstallmentTerm().observe(this, stringArrayAdapter -> spnTerm.setAdapter(stringArrayAdapter));

        mViewModel.getSelectedInstallmentTerm().observe(this, integer -> mViewModel.calculateMonthlyPayment());

        spnApplType.setOnItemClickListener(new Activity_IntroductoryQuestion.OnItemClickListener(spnApplType, mViewModel));
        spnCustomerType.setOnItemClickListener(new Activity_IntroductoryQuestion.OnItemClickListener(spnCustomerType, mViewModel));
        spnTerm.setOnItemClickListener(new Activity_IntroductoryQuestion.OnItemClickListener(spnTerm, mViewModel));

        mViewModel.getMonthlyAmort().observe(this, s -> txtAmort.setText(s));
        btnCreate.setOnClickListener(view -> submitNewApplication());
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
        spnApplType = findViewById(R.id.spn_applicationType);
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
                        case 1:
                            term = 36;
                            break;
                        case 2:
                            term = 24;
                            break;
                        case 3:
                            term = 18;
                            break;
                        case 4:
                            term = 12;
                            break;
                        case 5:
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
        getViewModelStore().clear();
    }
}