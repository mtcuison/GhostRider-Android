package org.rmj.guanzongroup.onlinecreditapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.GRider.Etc.FormatUIText;
import org.rmj.guanzongroup.onlinecreditapplication.R;

import java.util.Objects;

public class Activity_SelfEmployedInfo extends AppCompatActivity {

    private AutoCompleteTextView spnBussNtr, spnBussTyp, spnBussSze, spnLngSrvc;

    private String  bussNtrPosition = "-1",
                    bussTypPosition = "-1",
                    bussSzePosition = "-1",
                    lngSrvcPosition = "-1";

    private TextInputEditText txtBussName, txtBussAdds, txtLnghtSrv, txtMnthlyIn, txtMnthlyEx;
    private AutoCompleteTextView txtProvnc, txtTownxx;

    private Button btnNext, btnPrvs;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_employed_info);
        initWidgets();
    }

    private void initWidgets() {
        toolbar = findViewById(R.id.toolbar_SelfEmployedInfo);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        spnBussNtr = findViewById(R.id.spn_businessNature);
        spnBussTyp = findViewById(R.id.spn_businessType);
        spnBussSze = findViewById(R.id.spn_businessSize);
        spnLngSrvc = findViewById(R.id.spn_lenghtSrvc);

        txtBussName = findViewById(R.id.txt_businessName);
        txtBussAdds = findViewById(R.id.txt_businessAddress);
        txtLnghtSrv = findViewById(R.id.txt_lenghtService);
        txtMnthlyIn = findViewById(R.id.txt_monthlyInc);
        txtMnthlyEx = findViewById(R.id.txt_monthlyExp);
        txtProvnc = findViewById(R.id.txt_province);
        txtTownxx = findViewById(R.id.txt_town);

        btnPrvs = findViewById(R.id.btn_creditAppPrvs);
        btnNext = findViewById(R.id.btn_creditAppNext);

        txtMnthlyIn.addTextChangedListener(new FormatUIText.CurrencyFormat(txtMnthlyIn));
        txtMnthlyEx.addTextChangedListener(new FormatUIText.CurrencyFormat(txtMnthlyEx));

        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_SelfEmployedInfo.this,Activity_Finance.class);
            startActivity(intent);
            finish();
        });
        btnPrvs.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_SelfEmployedInfo.this,Activity_EmploymentInfo.class);
            startActivity(intent);
            finish();
        });

    }
}