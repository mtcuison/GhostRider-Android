package org.rmj.guanzongroup.onlinecreditapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;

import org.rmj.guanzongroup.onlinecreditapplication.R;

import java.util.Objects;

public class Activity_PensionInfo extends AppCompatActivity {

    private AutoCompleteTextView spnSector;
    private String sectorPosition = "-1";
    private TextInputEditText txtRangxx, txtYearxx, txtOthInc, txtRngInc;
    private Button btnPrvs, btnNext;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pension_info);
        initWidgets();
    }

    private void initWidgets() {
        toolbar = findViewById(R.id.toolbar_PensionInfo);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        spnSector = findViewById(R.id.spn_psnSector);

        txtRangxx = findViewById(R.id.txt_psnIncRange);
        txtYearxx = findViewById(R.id.txt_psnRtrmntYear);
        txtOthInc = findViewById(R.id.txt_natureOfIncome);
        txtRngInc = findViewById(R.id.txt_incRange);

        btnPrvs = findViewById(R.id.btn_creditAppPrvs);
        btnNext = findViewById(R.id.btn_creditAppNext);

        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_PensionInfo.this,Activity_SpouseInfo.class);
            startActivity(intent);
            finish();
        });
        btnPrvs.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_PensionInfo.this,Activity_Finance.class);
            startActivity(intent);
            finish();
        });
    }
}