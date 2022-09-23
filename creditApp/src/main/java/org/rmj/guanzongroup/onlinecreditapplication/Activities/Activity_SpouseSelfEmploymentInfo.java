package org.rmj.guanzongroup.onlinecreditapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;

import org.rmj.guanzongroup.onlinecreditapplication.Etc.TextFormatter;
import org.rmj.guanzongroup.onlinecreditapplication.R;

import java.util.Objects;

public class Activity_SpouseSelfEmploymentInfo extends AppCompatActivity {

    private TextInputEditText txtBizName, txtBizAddrss, txtBizLength, txtMonthlyInc, txtMonthlyExp;
    private AppCompatAutoCompleteTextView spnBizIndustry, spnMonthOrYr, txtProvince, txtTown,
            spnBizType, spnBizSize;
    private Button btnNext, btnPrvs;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spouse_self_employment_info);
        initWidgets();
    }

    private void initWidgets() {
        toolbar = findViewById(R.id.toolbar_SpouseSelfEmploymentInfo);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        spnMonthOrYr = findViewById(R.id.spn_monthOrYr);
        txtBizName = findViewById(R.id.txt_bizName);
        txtBizAddrss = findViewById(R.id.txt_bizAddress);
        txtBizLength = findViewById(R.id.txt_bizLength);
        txtMonthlyInc = findViewById(R.id.txt_monthlyInc);
        txtMonthlyExp = findViewById(R.id.txt_monthlyExp);
        spnBizIndustry = findViewById(R.id.spn_bizIndustry);
        txtProvince = findViewById(R.id.txt_province);
        txtTown = findViewById(R.id.txt_town);
        spnBizType = findViewById(R.id.spn_bizType);
        spnBizSize = findViewById(R.id.spn_bizSize);
        btnNext = findViewById(R.id.btn_creditAppNext);
        btnPrvs = findViewById(R.id.btn_creditAppPrvs);

        txtMonthlyInc.addTextChangedListener(new TextFormatter.OnTextChangedCurrencyFormatter(txtMonthlyInc));
        txtMonthlyExp.addTextChangedListener(new TextFormatter.OnTextChangedCurrencyFormatter(txtMonthlyExp));

        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_SpouseSelfEmploymentInfo.this,Activity_SpousePensionInfo.class);
            startActivity(intent);
            finish();
        });
        btnPrvs.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_SpouseSelfEmploymentInfo.this,Activity_SpouseResidenceInfo.class);
            startActivity(intent);
            finish();
        });




    }
}