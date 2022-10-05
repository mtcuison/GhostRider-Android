package org.rmj.guanzongroup.onlinecreditapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.R;

import java.util.Objects;

public class Activity_EmploymentInfo extends AppCompatActivity {

    private AutoCompleteTextView spnCmpLvl, spnEmpLvl, spnBusNtr, spnEmpSts, spnServce, txtCntryx, txtProvNm, txtTownNm, txtJobNme;
    private TextInputLayout tilCntryx, tilCompNm, tilBizNatr;
    private TextInputEditText txtCompNm, txtCompAd, txtSpcfJb, txtLngthS, txtEsSlry, txtCompCn;
    private LinearLayout lnGovInfo, lnEmpInfo;
    //    private JSONObject object;
    private Button btnNext, btnPrvs;
    private String Employed, SEmployd, Financex, Pensionx;
    private RadioGroup rgSectorx, rgUniform, rgMiltary;
    private RadioButton rbPrivate, rbGovernment, rbOFW, rbUniformYes, rbUniformNo, rbMilitaryYes, rbMilitaryNo;
    private String sEmploymentInfo, sUniform, sMilitary;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employment_info);
        initWidgets();
        json();

    }
    private void json() {
        try {
            Intent receiveIntent = getIntent();
            String param = receiveIntent.getStringExtra("params");
            JSONObject object = new JSONObject(param);
            object.put("sEmplyInfo", sEmploymentInfo);
            object.put("sUniform", sUniform);
            object.put("sMilitary", sMilitary);

            object.put("sspnCmpLvl", spnCmpLvl.getText().toString().trim());
            object.put("sspnEmpLvl", spnEmpLvl.getText().toString().trim());
            object.put("sspnBusNtr", spnBusNtr.getText().toString().trim());
            object.put("sspnEmpSts", spnEmpSts.getText().toString().trim());
            object.put("sspnServce", spnServce.getText().toString().trim());

            object.put("stxtCntryx", txtCntryx.getText().toString().trim());
            object.put("stxtProvNm", txtProvNm.getText().toString().trim());
            object.put("stxtTownNm", txtTownNm.getText().toString().trim());
            object.put("stxtJobNme", txtJobNme.getText().toString().trim());
            object.put("stxtCompNm", txtCompNm.getText().toString().trim());
            object.put("stxtCompAd", txtCompAd.getText().toString().trim());
            object.put("stxtSpcfJb", txtSpcfJb.getText().toString().trim());
            object.put("stxtLngthS", txtLngthS.getText().toString().trim());
            object.put("stxtEsSlry", txtEsSlry.getText().toString().trim());
            object.put("stxtCompCn", txtCompCn.getText().toString().trim());

            btnNext.setOnClickListener(v -> {
                Intent intent = new Intent(Activity_EmploymentInfo.this, Activity_SelfEmployedInfo.class);
                intent.putExtra("params", object.toString());
                startActivity(intent);
                finish();
            });
            btnPrvs.setOnClickListener(v -> {
                Intent intent = new Intent(Activity_EmploymentInfo.this, Activity_MeansInfoSelection.class);
                startActivity(intent);
                finish();
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initWidgets() {
        toolbar = findViewById(R.id.toolbar_EmploymentInfo);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Employment Info");

        rgSectorx = findViewById(R.id.rg_sector);
        rgUniform = findViewById(R.id.rg_uniformPersonel);
        rgMiltary = findViewById(R.id.rg_militaryPersonal);

        spnCmpLvl = findViewById(R.id.spn_employmentLevel);
        spnEmpLvl = findViewById(R.id.spn_employeeLevel);
        spnBusNtr = findViewById(R.id.spn_businessNature);
        spnEmpSts = findViewById(R.id.spn_employmentStatus);
        spnServce = findViewById(R.id.spn_lengthService);

        txtCntryx = findViewById(R.id.txt_countryNme);
        txtProvNm = findViewById(R.id.txt_province);
        txtTownNm = findViewById(R.id.txt_town);
        txtJobNme = findViewById(R.id.txt_jobPosition);

        txtCompNm = findViewById(R.id.txt_companyNme);
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

        rbUniformYes = findViewById(R.id.rb_uniform_yes);
        rbUniformNo = findViewById(R.id.rb_uniform_no);

        rbMilitaryYes = findViewById(R.id.rb_military_yes);
        rbMilitaryNo = findViewById(R.id.rb_military_no);


        rgSectorx.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rbPrivate.isChecked()) {
                    sEmploymentInfo = "Private";
                } else if (rbGovernment.isChecked()) {
                    sEmploymentInfo = "Government";
                } else if (rbOFW.isChecked()) {
                    sEmploymentInfo = "OFW";
                }
            }
        });

        rgUniform.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rbUniformYes.isChecked()) {
                    sUniform = "yes";
                } else if (rbUniformNo.isChecked()) {
                    sUniform = "no";
                }
            }
        });

        rgMiltary.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rbMilitaryYes.isChecked()) {
                    sMilitary = "yes";
                } else if (rbMilitaryNo.isChecked()) {
                    sMilitary = "no";
                }
            }
        });

        spnCmpLvl.setAdapter(new ArrayAdapter<>(Activity_EmploymentInfo.this, android.R.layout.simple_list_item_1, CreditAppConstants.COMPANY_LEVEL));
        spnCmpLvl.setDropDownBackgroundResource(R.drawable.bg_gradient_light);

        spnEmpLvl.setAdapter(new ArrayAdapter<>(Activity_EmploymentInfo.this, android.R.layout.simple_list_item_1, CreditAppConstants.EMPLOYEE_LEVEL));
        spnEmpLvl.setDropDownBackgroundResource(R.drawable.bg_gradient_light);

        spnBusNtr.setAdapter(new ArrayAdapter<>(Activity_EmploymentInfo.this, android.R.layout.simple_list_item_1, CreditAppConstants.BUSINESS_NATURE));
        spnBusNtr.setDropDownBackgroundResource(R.drawable.bg_gradient_light);

        spnEmpSts.setAdapter(new ArrayAdapter<>(Activity_EmploymentInfo.this, android.R.layout.simple_list_item_1, CreditAppConstants.EMPLOYMENT_STATUS));
        spnEmpSts.setDropDownBackgroundResource(R.drawable.bg_gradient_light);

        spnServce.setAdapter(new ArrayAdapter<>(Activity_EmploymentInfo.this, android.R.layout.simple_list_item_1, CreditAppConstants.LENGTH_OF_STAY));
        spnServce.setDropDownBackgroundResource(R.drawable.bg_gradient_light);

    }
}