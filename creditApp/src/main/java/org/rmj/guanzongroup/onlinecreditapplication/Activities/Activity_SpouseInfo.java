package org.rmj.guanzongroup.onlinecreditapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.rmj.guanzongroup.onlinecreditapplication.Activity.Activity_CreditApplication;
import org.rmj.guanzongroup.onlinecreditapplication.R;

import java.util.Objects;

public class Activity_SpouseInfo extends AppCompatActivity {

    private AutoCompleteTextView txtProvince, txtTownxx, txtCitizenx;
    private TextInputEditText txtLastName, txtFirstName, txtSuffix, txtMiddName, txtNickName,
            txtBDate, txtPrimeCntc, txtPrimeCntcYr, txtSecCntct, txtSecCntctYr, txtThirCntct,
            txtThirCntctYr, txtTelNox, txtEmailAdd, txtFbAcct, txtViberAcct, txtMobileYr1,
            txtMobileYr2, txtMobileYr3;

    private String transnox;

    private TextInputLayout tilMobileYr1 ,tilMobileYr2 ,tilMobileYr3;
    private AutoCompleteTextView spnMobile1, spnMobile2, spnMobile3;

    private String spnMobile1Position = "-1";
    private String spnMobile2Position = "-1";
    private String spnMobile3Position = "-1";
    private Button btnNext, btnPrvs;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spouse_info);
        initWidgets();
    }

    private void initWidgets() {

        toolbar = findViewById(R.id.toolbar_SpouseInfo);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        transnox = Activity_CreditApplication.getInstance().getTransNox();
        txtProvince = findViewById(R.id.txt_bpProvince);
        txtTownxx = findViewById(R.id.txt_bpTown);

        txtLastName = findViewById(R.id.tie_last_name);
        txtFirstName = findViewById(R.id.tie_first_name);
        txtSuffix = findViewById(R.id.tie_suffix);
        txtMiddName = findViewById(R.id.tie_midd_name);
        txtNickName = findViewById(R.id.tie_nick_name);
        txtBDate = findViewById(R.id.tie_birth_date);
        txtCitizenx = findViewById(R.id.tie_citizenship);

        txtPrimeCntc = findViewById(R.id.txt_mobileNo1);
        txtPrimeCntcYr = findViewById(R.id.txt_mobileNo1Year);

        txtSecCntct = findViewById(R.id.txt_mobileNo2);
        txtSecCntctYr = findViewById(R.id.txt_mobileNo2Year);

        txtThirCntct = findViewById(R.id.txt_mobileNo3);
        txtThirCntctYr = findViewById(R.id.txt_mobileNo3Year);

        txtTelNox = findViewById(R.id.tie_tel_no);
        txtEmailAdd = findViewById(R.id.tie_emailAdd);
        txtFbAcct = findViewById(R.id.tie_fbAcct);
        txtViberAcct = findViewById(R.id.tie_viberAcct);

        spnMobile1 = findViewById(R.id.spn_mobile1Type);
        spnMobile2 = findViewById(R.id.spn_mobile2Type);
        spnMobile3 = findViewById(R.id.spn_mobile3Type);

        txtMobileYr1 = txtPrimeCntcYr;
        tilMobileYr1 = findViewById(R.id.til_mobileNo1Year);
        txtMobileYr2 = txtSecCntctYr;
        tilMobileYr2 = findViewById(R.id.til_mobileNo2Year);
        txtMobileYr3 = txtThirCntctYr;
        tilMobileYr3 = findViewById(R.id.til_mobileNo3Year);

        btnNext = findViewById(R.id.btn_creditAppNext);
        btnPrvs = findViewById(R.id.btn_creditAppPrvs);

        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_SpouseInfo.this,Activity_SpouseResidenceInfo.class);
            startActivity(intent);
            finish();
        });
        btnPrvs.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_SpouseInfo.this,Activity_PensionInfo.class);
            startActivity(intent);
            finish();
        });

    }
}