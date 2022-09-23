package org.rmj.guanzongroup.onlinecreditapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.rmj.guanzongroup.onlinecreditapplication.R;

import java.util.Objects;

public class Activity_ResidenceInfo extends AppCompatActivity {

    private TextInputEditText txtLandMark, txtHouseNox, txtAddress1, txtAddress2, txtRelationship,
            txtLgnthStay, txtMonthlyExp, txtPLandMark, txtPHouseNox, txtPAddress1, txtPAddress2;
    private AutoCompleteTextView txtBarangay, txtMunicipality, txtProvince, txtPBarangay, txtPMunicipl,
            txtPProvince;
    private CheckBox cbOneAddress;
    private AutoCompleteTextView spnLgnthStay, spnHouseHold, spnHouseType;
    private String  spnLgnthStayPosition = "-1",
                    spnHouseHoldPosition = "-1",
                    spnHouseTypePosition = "-1";
    private TextInputLayout tilRelationship;
    private LinearLayout lnOtherInfo, lnPermaAddx;
    private Button btnNext;
    private Button btnPrvs;
    private RadioGroup rgOwnsership, rgGarage;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_residence_info);
        initWidgets();
    }

    private void initWidgets() {

        toolbar = findViewById(R.id.toolbar_PersonalInfo);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        cbOneAddress = findViewById(R.id.cb_oneAddress);
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
        txtPLandMark = findViewById(R.id.txt_perm_landmark);
        txtPHouseNox = findViewById(R.id.txt_perm_houseNox);
        txtPAddress1 = findViewById(R.id.txt_perm_address);
        txtPAddress2 = findViewById(R.id.txt_perm_address2);
        txtPBarangay = findViewById(R.id.txt_perm_barangay);
        txtPMunicipl = findViewById(R.id.txt_perm_town);
        txtPProvince = findViewById(R.id.txt_perm_province);

        spnLgnthStay = findViewById(R.id.spn_lenghtStay);
        spnHouseHold = findViewById(R.id.spn_houseHold);
        spnHouseType = findViewById(R.id.spn_houseType);

        rgOwnsership = findViewById(R.id.rg_ownership);
        rgGarage = findViewById(R.id.rg_garage);

        tilRelationship = findViewById(R.id.til_relationship);

        lnOtherInfo = findViewById(R.id.linear_otherInfo);
        lnPermaAddx = findViewById(R.id.linear_permanentAdd);

        btnNext = findViewById(R.id.btn_creditAppNext);
        btnPrvs = findViewById(R.id.btn_creditAppPrvs);

        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_ResidenceInfo.this,Activity_MeansInfoSelection.class);
            startActivity(intent);
            finish();
        });
        btnPrvs.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_ResidenceInfo.this,Activity_PersonalInfo.class);
            startActivity(intent);
            finish();
        });
    }
}