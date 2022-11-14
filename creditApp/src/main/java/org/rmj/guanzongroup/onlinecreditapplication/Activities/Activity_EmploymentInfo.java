package org.rmj.guanzongroup.onlinecreditapplication.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.g3appdriver.lib.integsys.CreditApp.OnSaveInfoListener;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.Employment;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.OnParseListener;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMEmploymentInfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Activity_EmploymentInfo extends AppCompatActivity {


    private VMEmploymentInfo
            mViewModel;
    private MessageBox
            poMessage;
    private AutoCompleteTextView
            spnCmpLvl, spnEmpLvl, spnBusNtr, spnEmpSts, spnServce,
            txtCntryx, txtProvNm, txtTownNm, txtJobNme;
    private TextInputLayout
            tilCntryx, tilCompNm, tilBizNatr;
    private TextInputEditText
            txtCompNm, txtCompAd, txtSpcfJb, txtLngthS, txtEsSlry, txtCompCn;
    private LinearLayout
            lnGovInfo, lnEmpInfo;
    private Button
            btnNext, btnPrvs;
    private RadioGroup
            rgSectorx;
    private RadioButton
            rbPrivate, rbGovernment, rbOFW;
    private CheckBox
            cbUniformYes, cbMilitaryYes;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(Activity_EmploymentInfo.this).get(VMEmploymentInfo.class);
        poMessage = new MessageBox(Activity_EmploymentInfo.this);
        setContentView(R.layout.activity_employment_info);
        initWidgets();
        mViewModel.InitializeApplication(getIntent());
        mViewModel.GetApplication().observe(Activity_EmploymentInfo.this, app -> {
            try {
                mViewModel.getModel().setTransNox(app.getTransNox());
                mViewModel.ParseData(app, new OnParseListener() {
                    @Override
                    public void OnParse(Object args) {
                        Employment loDetail = (Employment) args;

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        spnCmpLvl.setAdapter(new ArrayAdapter<>(Activity_EmploymentInfo.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.COMPANY_LEVEL));
        spnCmpLvl.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        spnCmpLvl.setOnItemClickListener((parent, view, position, id) ->
                mViewModel.getModel().setCompanyLevel(String.valueOf(position)));

        spnEmpLvl.setAdapter(new ArrayAdapter<>(Activity_EmploymentInfo.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.EMPLOYEE_LEVEL));
        spnEmpLvl.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        spnEmpLvl.setOnItemClickListener((parent, view, position, id) ->
                mViewModel.getModel().setEmployeeLevel(String.valueOf(position)));

        spnBusNtr.setAdapter(new ArrayAdapter<>(Activity_EmploymentInfo.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.BUSINESS_NATURE));
        spnBusNtr.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        spnBusNtr.setOnItemClickListener((parent, view, position, id) ->
                mViewModel.getModel().setBusinessNature(String.valueOf(position)));

        spnEmpSts.setAdapter(new ArrayAdapter<>(Activity_EmploymentInfo.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.EMPLOYMENT_STATUS));
        spnEmpSts.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        spnEmpSts.setOnItemClickListener((parent, view, position, id) ->
                mViewModel.getModel().setEmployeeStatus(String.valueOf(position)));

        spnServce.setAdapter(new ArrayAdapter<>(Activity_EmploymentInfo.this, android.R.layout.simple_list_item_1, CreditAppConstants.LENGTH_OF_STAY));
        spnServce.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        spnServce.setOnItemClickListener((parent, view, position, id) ->
                mViewModel.getModel().setIsYear(String.valueOf(position)));

        mViewModel.GetTownProvinceList().observe(Activity_EmploymentInfo.this, new Observer<List<DTownInfo.TownProvinceInfo>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(List<DTownInfo.TownProvinceInfo> provList) {
                try {
                    ArrayList<String> strings = new ArrayList<>();
                    for (int x = 0; x < provList.size(); x++) {
                        String lsProv = "" + provList.get(x).sProvName;
//                        String lsTown =  loList.get(x).sProvName ;
                        strings.add(lsProv);

                        Set<Object> set = new HashSet<>();
                        strings.removeIf((String i) -> {
                            return !set.add(i);
                        });

                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(Activity_EmploymentInfo.this, android.R.layout.simple_spinner_dropdown_item, strings.toArray(new String[0]));
                    txtProvNm.setAdapter(adapter);
                    txtProvNm.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
                    txtProvNm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            for (int x = 0; x < provList.size(); x++) {
                                String lsLabel = provList.get(x).sProvName;
                                String lsSlctd = txtProvNm.getText().toString().trim();
                                if (lsSlctd.equalsIgnoreCase(lsLabel)) {
                                    mViewModel.getModel().setProvinceID(provList.get(x).sProvIDxx);
                                    mViewModel.getModel().setProvName(lsLabel);
                                    break;
                                }
                            }


                            mViewModel.GetTownProvinceList().observe(Activity_EmploymentInfo.this, new Observer<List<DTownInfo.TownProvinceInfo>>() {
                                @Override
                                public void onChanged(List<DTownInfo.TownProvinceInfo> townList) {
                                    try {
                                        ArrayList<String> string = new ArrayList<>();
                                        for (int x = 0; x < townList.size(); x++) {
                                            String lsTown = townList.get(x).sTownName + "";
//                        String lsTown =  loList.get(x).sProvName ;
                                            string.add(lsTown);
                                            Set<Object> set = new HashSet<>();
                                            string.removeIf((String i) -> {
                                                return !set.add(i);
                                            });

                                        }

                                        ArrayAdapter<String> adapters = new ArrayAdapter<>(Activity_EmploymentInfo.this, android.R.layout.simple_spinner_dropdown_item, string.toArray(new String[0]));
                                        txtTownNm.setAdapter(adapters);
                                        txtTownNm.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
                                        txtTownNm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                for (int x = 0; x < townList.size(); x++) {
                                                    String lsLabel = townList.get(x).sTownName;
                                                    String lsSlctd = txtTownNm.getText().toString().trim();
                                                    if (lsSlctd.equalsIgnoreCase(lsLabel)) {
                                                        mViewModel.getModel().setTownID(townList.get(x).sTownIDxx);
                                                        mViewModel.getModel().setTownName(lsLabel);
                                                        break;
                                                    }
                                                }
                                            }
                                        });

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnNext.setOnClickListener(v -> SaveEmploymentInfo());

    }

    private void SaveEmploymentInfo() {
        mViewModel.getModel().setsCountryN((txtCntryx.getText()).toString());
        mViewModel.getModel().setCompanyName(txtCompNm.getText().toString());
        mViewModel.getModel().setCompanyAddress((txtCompAd.getText()).toString());

//        mViewModel.getModel().setProvinceID(txtProvNm.getText().toString());
//        mViewModel.getModel().setTownID(txtTownNm.getText().toString());

        mViewModel.getModel().setJobTitle((txtJobNme.getText()).toString());
        mViewModel.getModel().setSpecificJob((txtSpcfJb.getText()).toString());
        mViewModel.getModel().setLengthOfService(Double.parseDouble((txtLngthS.getText()).toString()));
        mViewModel.getModel().setMonthlyIncome(Long.parseLong((txtEsSlry.getText()).toString()));
        mViewModel.getModel().setContact((txtCompCn.getText()).toString());

        mViewModel.SaveData(new OnSaveInfoListener() {
            @Override
            public void OnSave(String args) {
                Intent loIntent = new Intent(Activity_EmploymentInfo.this, Activity_SelfEmployedInfo.class);
                loIntent.putExtra("sTransNox", args);
                startActivity(loIntent);
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


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_intent_slide_in_left, R.anim.anim_intent_slide_out_right);
    }

    private void initWidgets() {
        toolbar = findViewById(R.id.toolbar_EmploymentInfo);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Employment Info");

        rgSectorx = findViewById(R.id.rg_sector);

        spnCmpLvl = findViewById(R.id.spn_employmentLevel);
        spnEmpLvl = findViewById(R.id.spn_employeeLevel);
        spnBusNtr = findViewById(R.id.spn_businessNature);
        spnEmpSts = findViewById(R.id.spn_employmentStatus);
        spnServce = findViewById(R.id.spn_lengthService);

        txtProvNm = findViewById(R.id.txt_province);
        txtCntryx = findViewById(R.id.txt_countryNme);
        txtTownNm = findViewById(R.id.txt_town);
        txtJobNme = findViewById(R.id.txt_jobPosition);

        txtCompNm = findViewById(R.id.txt_CompanyNme);
        txtCompAd = findViewById(R.id.txt_companyAdd);
        txtSpcfJb = findViewById(R.id.txt_specificJob);
        txtLngthS = findViewById(R.id.txt_lenghtService);
        txtEsSlry = findViewById(R.id.txt_monthlySalary);
        txtCompCn = findViewById(R.id.txt_companyContact);

        tilCntryx = findViewById(R.id.til_countryNme);
        tilCompNm = findViewById(R.id.til_companyNme);
        tilBizNatr = findViewById(R.id.til_bizNature);

        lnGovInfo = findViewById(R.id.linear_governmentSector);
        lnEmpInfo = findViewById(R.id.linear_employmentInfo);

        btnPrvs = findViewById(R.id.btn_creditAppPrvs);
        btnNext = findViewById(R.id.btn_creditAppNext);

        rbPrivate = findViewById(R.id.rb_private);
        rbGovernment = findViewById(R.id.rb_government);
        rbOFW = findViewById(R.id.rb_ofw);

        cbUniformYes = findViewById(R.id.cb_UniformPersonnelYes);
        cbMilitaryYes = findViewById(R.id.cb_MilitaryPersonalYes);


        rgSectorx.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rbGovernment.isChecked()) {
                    mViewModel.getModel().setEmploymentSector("0");
                    lnGovInfo.setVisibility(View.VISIBLE);
                    tilCntryx.setVisibility(View.GONE);

                } else if (rbPrivate.isChecked()) {
                    mViewModel.getModel().setEmploymentSector("1");
                    lnGovInfo.setVisibility(View.GONE);
                    tilCntryx.setVisibility(View.GONE);

                } else if (rbOFW.isChecked()) {
                    mViewModel.getModel().setEmploymentSector("2");
                    lnGovInfo.setVisibility(View.GONE);
                    tilCntryx.setVisibility(View.VISIBLE);
                }
            }
        });

        cbUniformYes.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (cbUniformYes.isChecked()) {
                mViewModel.getModel().setUniformPersonal("1");
            } else {
                mViewModel.getModel().setUniformPersonal("0");
            }
        });

        cbMilitaryYes.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (cbMilitaryYes.isChecked()) {
                mViewModel.getModel().setUniformPersonal("1");
            } else {
                mViewModel.getModel().setUniformPersonal("0");
            }
        });


    }
}