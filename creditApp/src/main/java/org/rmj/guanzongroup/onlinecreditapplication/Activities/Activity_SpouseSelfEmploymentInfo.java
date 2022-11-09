package org.rmj.guanzongroup.onlinecreditapplication.Activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;

import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
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
        getSupportActionBar().setTitle("Spouse Self Employment Info");

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


        spnBizIndustry.setAdapter(new ArrayAdapter<>(Activity_SpouseSelfEmploymentInfo.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.BUSINESS_NATURE));
        spnBizIndustry.setDropDownBackgroundResource(R.drawable.bg_gradient_light);

        spnBizType.setAdapter(new ArrayAdapter<>(Activity_SpouseSelfEmploymentInfo.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.BUSINESS_TYPE));
        spnBizType.setDropDownBackgroundResource(R.drawable.bg_gradient_light);

        spnBizSize.setAdapter(new ArrayAdapter<>(Activity_SpouseSelfEmploymentInfo.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.BUSINESS_SIZE));
        spnBizSize.setDropDownBackgroundResource(R.drawable.bg_gradient_light);

        spnMonthOrYr.setAdapter(new ArrayAdapter<>(Activity_SpouseSelfEmploymentInfo.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.LENGTH_OF_STAY));
        spnMonthOrYr.setDropDownBackgroundResource(R.drawable.bg_gradient_light);


    }
}