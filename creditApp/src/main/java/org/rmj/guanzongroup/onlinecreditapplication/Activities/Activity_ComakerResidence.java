package org.rmj.guanzongroup.onlinecreditapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.rmj.guanzongroup.onlinecreditapplication.R;

import java.util.Objects;

public class Activity_ComakerResidence extends AppCompatActivity {

    private TextInputEditText txtLandMark, txtHouseNox, txtAddress1, txtAddress2, txtRelationship,
            txtLgnthStay, txtMonthlyExp;
    private AutoCompleteTextView txtBarangay, txtMunicipality, txtProvince;
    private AutoCompleteTextView spnLgnthStay, spnHouseHold, spnHouseType;

    private String  spnLgnthStayPosition = "-1",
                    spnHouseHoldPosition = "-1",
                    spnHouseTypePosition = "-1";

    private RadioGroup rgOwnsership, rgGarage;

    private TextInputLayout tilRelationship;
    private LinearLayout lnOtherInfo;
    private Button btnNext, btnPrvs;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comaker_residence);
        initWidgets();
    }

    private void initWidgets() {
        toolbar = findViewById(R.id.toolbar_CoMakerResidence);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        txtLandMark = findViewById(R.id.txt_landmark);
        txtHouseNox = findViewById(R.id.txt_houseNox);
        txtAddress1 = findViewById(R.id.txt_address);
        txtAddress2 = findViewById(R.id.txt_address2);
        txtBarangay = findViewById(R.id.txt_barangay);
        txtMunicipality = findViewById(R.id.txt_town);
        txtProvince = findViewById(R.id.txt_province);
        txtRelationship = findViewById(R.id.txt_relationship);
        txtLgnthStay = findViewById(R.id.txt_lenghtStay);
        txtMonthlyExp = findViewById(R.id.txt_monthlyExp);

        spnLgnthStay = findViewById(R.id.spn_lenghtStay);
        spnHouseHold = findViewById(R.id.spn_houseHold);
        spnHouseType = findViewById(R.id.spn_houseType);

        rgOwnsership = findViewById(R.id.rg_ownership);
        rgGarage = findViewById(R.id.rg_garage);

        tilRelationship = findViewById(R.id.til_relationship);

        lnOtherInfo = findViewById(R.id.linear_otherInfo);

        btnNext = findViewById(R.id.btn_creditAppNext);
        btnPrvs = findViewById(R.id.btn_creditAppPrvs);

        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_ComakerResidence.this, Activity_ReviewLoanApp.class);
            startActivity(intent);
            finish();
        });
        btnPrvs.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_ComakerResidence.this, Activity_CoMaker.class);
            startActivity(intent);
            finish();
        });

    }
}