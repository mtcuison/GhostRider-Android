package org.rmj.guanzongroup.onlinecreditapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;

import org.rmj.guanzongroup.onlinecreditapplication.R;

import java.util.Objects;

public class Activity_SpouseResidenceInfo extends AppCompatActivity {

    private CheckBox cbLivingWithSpouse;
    private TextInputEditText txtLandMark, txtHouseNox, txtAddress1, txtAddress2;
    private AutoCompleteTextView txtBarangay, txtTown, txtProvince;
    private Button btnNext, btnPrvs;
    private Toolbar toolbar;
    private String transnox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spouse_residence_info);
        initWidgets();
    }

    private void initWidgets() {
        toolbar = findViewById(R.id.toolbar_SpouseResidenceInfo);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        txtLandMark = findViewById(R.id.txt_landmark);
        txtHouseNox = findViewById(R.id.txt_houseNox);
        txtAddress1 = findViewById(R.id.txt_address);
        txtAddress2 = findViewById(R.id.txt_address2);
        txtProvince = findViewById(R.id.txt_province);
        txtTown = findViewById(R.id.txt_town);
        txtBarangay = findViewById(R.id.txt_barangay);

        btnNext = findViewById(R.id.btn_creditAppNext);
        btnPrvs = findViewById(R.id.btn_creditAppPrvs);

        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_SpouseResidenceInfo.this,Activity_SpouseEmploymentInfo.class);
            startActivity(intent);
            finish();
        });
        btnPrvs.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_SpouseResidenceInfo.this,Activity_SpouseInfo.class);
            startActivity(intent);
            finish();
        });

    }
}