package org.rmj.guanzongroup.onlinecreditapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.rmj.guanzongroup.onlinecreditapplication.R;

import java.util.Objects;

public class Activity_SpouseEmploymentInfo extends AppCompatActivity {

    private String  spnCmpLvlPosition = "-1",
                    spnEmpLvlPosition = "-1",
                    spnBusNtrPosition = "-1",
                    spnServcePosition = "-1",
                    spnEmpStsPosition = "-1";
    private AutoCompleteTextView spnCmpLvl, spnEmpLvl, spnBusNtr, spnEmpSts, spnServce;
    private AutoCompleteTextView txtCntryx, txtProvNm, txtTownNm, txtJobNme;
    private TextInputLayout tilCntryx, tilCompNm, tilJobTitle, tilCmpLvl, tilBizNature, tilEmpLvl;
    private TextInputEditText txtCompNm, txtCompAd, txtSpcfJb, txtLngthS, txtEsSlry, txtCompCn;
    private LinearLayout lnGovInfo, lnEmpInfo;
    private Button btnNext,btnPrvs;
    private TextView lblBizNature;
    private RadioGroup rgSectorx, rgUniform, rgMiltary;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spouse_employment_info);
        initWidgets();
    }

    private void initWidgets() {
        toolbar = findViewById(R.id.toolbar_SpouseEmploymentInfo);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

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

        lnGovInfo = findViewById(R.id.linear_governmentSector);
        lnEmpInfo = findViewById(R.id.linear_employmentInfo);

        btnPrvs = findViewById(R.id.btn_creditAppPrvs);
        btnNext = findViewById(R.id.btn_creditAppNext);

        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_SpouseEmploymentInfo.this,Activity_SpouseSelfEmploymentInfo.class);
            startActivity(intent);
            finish();
        });
        btnPrvs.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_SpouseEmploymentInfo.this,Activity_SpouseResidenceInfo.class);
            startActivity(intent);
            finish();
        });

    }
}