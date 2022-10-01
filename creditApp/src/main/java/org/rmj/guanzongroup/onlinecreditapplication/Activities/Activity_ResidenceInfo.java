package org.rmj.guanzongroup.onlinecreditapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
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

public class Activity_ResidenceInfo extends AppCompatActivity {

    private TextInputEditText txtLandMark, txtHouseNox, txtAddress1, txtAddress2, txtRelationship,
            txtLgnthStay, txtMonthlyExp, txtPLandMark, txtPHouseNox, txtPAddress1, txtPAddress2;
    private AutoCompleteTextView txtBarangay, txtMunicipality, txtProvince, txtPBarangay, txtPMunicipl,
            txtPProvince;
    private CheckBox cbOneAddress;
    private AutoCompleteTextView spnLgnthStay, spnHouseHold, spnHouseType;

    private TextInputLayout tilRelationship;
    private LinearLayout lnOtherInfo, lnPermaAddx;
    private Button btnNext;
    private Button btnPrvs;
    private RadioGroup rgOwnsership, rgGarage;
    private String scbAddress = "", sOwnership = "", sGarage ="";
//    private JSONObject object;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_residence_info);
        initWidgets();
        json();
    }

    private void json() {
        try {
            Intent receiveIntent =  getIntent();
            String param = receiveIntent.getStringExtra("params");
            JSONObject object = new JSONObject(param);
            object.put("stxtLandMark", (txtLandMark.getText()).toString().trim());
            object.put("stxtHouseNox", (txtHouseNox.getText()).toString().trim());
            object.put("stxtAddress1", (txtAddress1.getText()).toString().trim());
            object.put("stxtAddress2", (txtAddress2.getText()).toString().trim());
            object.put("stxtBarangay", txtBarangay.getText().toString().trim());
            object.put("stxtMunicipality", txtMunicipality.getText().toString().trim());
            object.put("stxtProvince", txtProvince.getText().toString().trim());
            object.put("stxtRelationship", (txtRelationship.getText()).toString().trim());
            object.put("stxtLgnthStay", (txtLgnthStay.getText()).toString().trim());
            object.put("stxtMonthlyExp", (txtMonthlyExp.getText()).toString().trim());
            object.put("stxtPLandMark", (txtPLandMark.getText()).toString().trim());
            object.put("stxtPHouseNox", (txtPHouseNox.getText()).toString().trim());
            object.put("stxtPAddress1", (txtPAddress1.getText()).toString().trim());
            object.put("stxtPAddress2", (txtPAddress2.getText()).toString().trim());
            object.put("stxtPBarangay", txtPBarangay.getText().toString().trim());
            object.put("stxtPMunicipl", txtPMunicipl.getText().toString().trim());
            object.put("stxtPProvince", txtPProvince.getText().toString().trim());

            object.put("sspnLgnthStay", spnLgnthStay.getText().toString().trim());
            object.put("sspnHouseHold", spnHouseHold.getText().toString().trim());
            object.put("sspnHouseType", spnHouseType.getText().toString().trim());

            object.put("sOwnership", sOwnership.toString().trim());
            object.put("sGarage", sGarage.toString().trim());

            btnNext.setOnClickListener(v -> {
                Intent intent = new Intent(Activity_ResidenceInfo.this, Activity_MeansInfoSelection.class);
                intent.putExtra("params", object.toString());
                startActivity(intent);
                this.finish();
            });
            btnPrvs.setOnClickListener(v -> {
                Intent intent = new Intent(Activity_ResidenceInfo.this, Activity_PersonalInfo.class);
                startActivity(intent);
                finish();
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void initWidgets() {

        toolbar = findViewById(R.id.toolbar_ResidenceInfo);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Residence Info");

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


        rgOwnsership.setOnCheckedChangeListener((radioGroup,i) -> {
            if (i == R.id.rb_owned) {
                sOwnership = "0";
            }
            if (i == R.id.rb_rent) {
                sOwnership = "1";
            }
            if (i == R.id.rb_careTaker) {
                sOwnership = "2";
            }
        });

        rgGarage.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == R.id.rb_yes) {
                sGarage = "0";
            }
            if (i == R.id.rb_no) {
                sGarage = "1";
            }
        });

        spnHouseHold.setAdapter(new ArrayAdapter<>(Activity_ResidenceInfo.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.HOUSEHOLDS));
        spnHouseHold.setDropDownBackgroundResource(R.drawable.bg_gradient_light);

        spnHouseType.setAdapter(new ArrayAdapter<>(Activity_ResidenceInfo.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.HOUSE_TYPE));
        spnHouseType.setDropDownBackgroundResource(R.drawable.bg_gradient_light);


    }
}