package org.rmj.guanzongroup.onlinecreditapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.R;

import java.util.Objects;

public class Activity_SpouseEmploymentInfo extends AppCompatActivity {

    private String spnCmpLvlPosition = "-1",
            spnEmpLvlPosition = "-1",
            spnBusNtrPosition = "-1",
            spnServcePosition = "-1",
            spnEmpStsPosition = "-1";
    private AutoCompleteTextView spnCmpLvl, spnEmpLvl, spnBusNtr, spnEmpSts, spnServce;
    private AutoCompleteTextView txtCntryx, txtProvNm, txtTownNm, txtJobNme;
    private TextInputLayout tilCntryx, tilCompNm, tilJobTitle, tilCmpLvl, tilBizNature, tilEmpLvl;
    private TextInputEditText txtCompNm, txtCompAd, txtSpcfJb, txtLngthS, txtEsSlry, txtCompCn;
    private LinearLayout lnGovInfo, lnEmpInfo;
    private Button btnNext, btnPrvs;
    private RadioButton rbPrivate, rbGovernment, rbOFW, rbUniformYes, rbUniformNo, rbMilitaryYes, rbMilitaryNo;
    private TextView lblBizNature;
    private String sEmploymentInfo, sUniform, sMilitary;
    private RadioGroup rgSectorx, rgUniform, rgMiltary;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spouse_employment_info);
        initWidgets();
        json();
    }

    private void json() {
        Intent receiveIntent = getIntent();
        String param = receiveIntent.getStringExtra("params");
        try {
            JSONObject object = new JSONObject(param);
            object.put("sEmplyInfox", sEmploymentInfo);
            object.put("sUniformx", sUniform);
            object.put("sMilitaryx", sMilitary);

            object.put("sspnCmpLvlx", spnCmpLvl.getText().toString().trim());
            object.put("sspnEmpLvlx", spnEmpLvl.getText().toString().trim());
            object.put("sspnBusNtrx", spnBusNtr.getText().toString().trim());
            object.put("sspnEmpStsx", spnEmpSts.getText().toString().trim());
            object.put("sspnServcex", spnServce.getText().toString().trim());

            object.put("stxtCntryxx", txtCntryx.getText().toString().trim());
            object.put("stxtProvNmx", txtProvNm.getText().toString().trim());
            object.put("stxtTownNmx", txtTownNm.getText().toString().trim());
            object.put("stxtJobNmex", txtJobNme.getText().toString().trim());
            object.put("stxtCompNmx", txtCompNm.getText().toString().trim());
            object.put("stxtCompAdx", txtCompAd.getText().toString().trim());
            object.put("stxtSpcfJbx", txtSpcfJb.getText().toString().trim());
            object.put("stxtLngthSx", txtLngthS.getText().toString().trim());
            object.put("stxtEsSlryx", txtEsSlry.getText().toString().trim());
            object.put("stxtCompCnx", txtCompCn.getText().toString().trim());

            btnNext.setOnClickListener(v -> {
                Intent intent = new Intent(Activity_SpouseEmploymentInfo.this, Activity_SpouseSelfEmploymentInfo.class);
                intent.putExtra("params", object.toString());
                startActivity(intent);
                finish();
            });
            btnPrvs.setOnClickListener(v -> {
                Intent intent = new Intent(Activity_SpouseEmploymentInfo.this, Activity_SpouseResidenceInfo.class);
                startActivity(intent);
                finish();
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initWidgets() {
        toolbar = findViewById(R.id.toolbar_SpouseEmploymentInfo);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Spouse Employment Info");


        rgSectorx = findViewById(R.id.rg_sector);
        rgUniform = findViewById(R.id.rg_uniformPersonel);
        rgMiltary = findViewById(R.id.rg_militaryPersonal);

        lblBizNature = findViewById(R.id.lbl_biz_nature);

        spnCmpLvl = findViewById(R.id.spn_employmentLevel);
        spnEmpLvl = findViewById(R.id.spn_employeeLevel);
        spnBusNtr = findViewById(R.id.spn_businessNature);
        spnEmpSts = findViewById(R.id.spn_employmentStatus);
        spnServce = findViewById(R.id.spn_lengthService);

        txtCntryx = findViewById(R.id.txt_countryNme);
        txtProvNm = findViewById(R.id.txt_province);
        txtTownNm = findViewById(R.id.txt_town);
        txtJobNme = findViewById(R.id.txt_jobPosition);

        tilCntryx = findViewById(R.id.til_countryNme);
        tilCompNm = findViewById(R.id.til_companyNme);
        tilJobTitle = findViewById(R.id.til_job_title);
        tilCmpLvl = findViewById(R.id.til_cmpLevel);
        tilBizNature = findViewById(R.id.til_bizNature);
        tilEmpLvl = findViewById(R.id.til_empLvl);

        txtCompNm = findViewById(R.id.txt_companyNme);
        txtCompAd = findViewById(R.id.txt_companyAdd);
        txtSpcfJb = findViewById(R.id.txt_specificJob);
        txtLngthS = findViewById(R.id.txt_lenghtService);
        txtEsSlry = findViewById(R.id.txt_monthlySalary);
        txtCompCn = findViewById(R.id.txt_companyContact);

        rbPrivate = findViewById(R.id.rb_private);
        rbGovernment = findViewById(R.id.rb_government);
        rbOFW = findViewById(R.id.rb_ofw);

        rbUniformYes = findViewById(R.id.rb_uniform_yes);
        rbUniformNo = findViewById(R.id.rb_uniform_no);

        rbMilitaryYes = findViewById(R.id.rb_military_yes);
        rbMilitaryNo = findViewById(R.id.rb_military_no);

        lnGovInfo = findViewById(R.id.linear_governmentSector);
        lnEmpInfo = findViewById(R.id.linear_employmentInfo);

        btnPrvs = findViewById(R.id.btn_creditAppPrvs);
        btnNext = findViewById(R.id.btn_creditAppNext);

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

        spnCmpLvl.setAdapter(new ArrayAdapter<>(Activity_SpouseEmploymentInfo.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.COMPANY_LEVEL));
        spnCmpLvl.setDropDownBackgroundResource(R.drawable.bg_gradient_light);

        spnEmpLvl.setAdapter(new ArrayAdapter<>(Activity_SpouseEmploymentInfo.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.EMPLOYEE_LEVEL));
        spnEmpLvl.setDropDownBackgroundResource(R.drawable.bg_gradient_light);

        spnBusNtr.setAdapter(new ArrayAdapter<>(Activity_SpouseEmploymentInfo.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.BUSINESS_NATURE));
        spnBusNtr.setDropDownBackgroundResource(R.drawable.bg_gradient_light);

        spnEmpSts.setAdapter(new ArrayAdapter<>(Activity_SpouseEmploymentInfo.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.EMPLOYMENT_STATUS));
        spnEmpSts.setDropDownBackgroundResource(R.drawable.bg_gradient_light);

        spnServce.setAdapter(new ArrayAdapter<>(Activity_SpouseEmploymentInfo.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.LENGTH_OF_STAY));
        spnServce.setDropDownBackgroundResource(R.drawable.bg_gradient_light);



    }
}