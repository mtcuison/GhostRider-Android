package org.rmj.guanzongroup.onlinecreditapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.rmj.g3appdriver.etc.OnDateSetListener;
import org.rmj.guanzongroup.onlinecreditapplication.R;

import java.util.Objects;

public class Activity_PersonalInfo extends AppCompatActivity {

    private TextInputEditText txtLastNm, txtFrstNm, txtMiddNm, txtSuffixx, txtNickNm, txtBirthDt,
            txtMothNm, txtMobileNo1, txtMobileNo2, txtMobileNo3, txtMobileYr1, txtMobileYr2,
            txtMobileYr3, txtTellNox, txtEmailAdd, txtFbAccount, txtViberAccount ;
    private TextInputLayout tilMothNm, tilMobileYr1, tilMobileYr2, tilMobileYr3;
    private AutoCompleteTextView  txtProvince,txtTown ,txtCitizen, spnCivilStatus, spnMobile1,
            spnMobile2, spnMobile3;
    private RadioGroup rgGender;
    private MaterialButton btnNext;
    private Toolbar toolbar;

    private String psMob1NetTp = "-1";
    private String psMob2NetTp = "-1";
    private String psMob3NetTp = "-1";

    private String transnox;
    private TextInputEditText[] txtMobileNo;
    private AutoCompleteTextView[] txtMobileType;
    private TextInputEditText[] txtMobileYear;
    private TextInputLayout[] tilMobileYear;

    private String[] psMobNetTp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        initWidgets();
    }

    private void initWidgets() {

        toolbar = findViewById(R.id.toolbar_PersonalInfo);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        txtLastNm = findViewById(R.id.txt_lastname);
        txtFrstNm = findViewById(R.id.txt_firstname);
        txtMiddNm = findViewById(R.id.txt_middname);
        txtSuffixx = findViewById(R.id.txt_suffix);
        txtNickNm = findViewById(R.id.txt_nickname);
        txtBirthDt = findViewById(R.id.txt_birthdate);
        tilMothNm = findViewById(R.id.til_motherNme);
        txtMothNm = findViewById(R.id.txt_motherNme);

//        txtMobileNo1 = findViewById(R.id.txt_mobileNo1);
//        txtMobileNo2 = findViewById(R.id.txt_mobileNo2);
//        txtMobileNo3 = findViewById(R.id.txt_mobileNo3);
//        txtMobileYr1 = findViewById(R.id.txt_mobileNo1Year);
//        tilMobileYr1 = findViewById(R.id.til_mobileNo1Year);
//        txtMobileYr2 = findViewById(R.id.txt_mobileNo2Year);
//        tilMobileYr2 = findViewById(R.id.til_mobileNo2Year);
//        txtMobileYr3 = findViewById(R.id.txt_mobileNo3Year);
//        tilMobileYr3 = findViewById(R.id.til_mobileNo3Year);

        txtTellNox = findViewById(R.id.txt_telephoneNo);
        txtEmailAdd = findViewById(R.id.txt_emailAdd);
        txtFbAccount = findViewById(R.id.txt_fbAccount);
        txtViberAccount = findViewById(R.id.txt_viberAccount);

        txtProvince = findViewById(R.id.txt_bpProvince);
        txtTown = findViewById(R.id.txt_bpTown);
        txtCitizen = findViewById(R.id.txt_citizenship);
        rgGender = findViewById(R.id.rg_gender);
        spnCivilStatus = findViewById(R.id.spn_civilStatus);

//         spnMobile1 = findViewById(R.id.spn_mobile1Type);
//         spnMobile2 = findViewById(R.id.spn_mobile2Type);
//         spnMobile3 = findViewById(R.id.spn_mobile3Type);

        txtBirthDt.addTextChangedListener(new OnDateSetListener(txtBirthDt));

        btnNext = findViewById(R.id.btn_creditAppNext);

        txtMobileNo = new TextInputEditText[]{
                findViewById(R.id.txt_mobileNo1),
                findViewById(R.id.txt_mobileNo2),
                findViewById(R.id.txt_mobileNo3),
        };
        tilMobileYear = new TextInputLayout[]{
                findViewById(R.id.til_mobileNo1Year),
                findViewById(R.id.til_mobileNo2Year),
                findViewById(R.id.til_mobileNo3Year)
        };
        txtMobileYear = new TextInputEditText[]{
                findViewById(R.id.txt_mobileNo1Year),
                findViewById(R.id.txt_mobileNo2Year),
                findViewById(R.id.txt_mobileNo3Year)
        };
        txtMobileType = new AutoCompleteTextView[]{
                findViewById(R.id.spn_mobile1Type),
                findViewById(R.id.spn_mobile2Type),
                findViewById(R.id.spn_mobile3Type)
        };
        psMobNetTp = new String[]{
                "-1",
                "-1",
                "-1"
        };

        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_PersonalInfo.this,Activity_ResidenceInfo.class);
            startActivity(intent);
            finish();
        });

    }
}