package org.rmj.guanzongroup.onlinecreditapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.rmj.guanzongroup.onlinecreditapplication.R;

import java.util.Objects;

public class Activity_MeansInfoSelection extends AppCompatActivity {

    private CheckBox cbEmployed,cbSEmployd,cbFinancex,cbPensionx;

    private Button btnNext, btnPrvs;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_means_info_selection);
        initWidgets();
    }

    private void initWidgets() {
        toolbar = findViewById(R.id.toolbar_MeansInfoSelection);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        cbEmployed = findViewById(R.id.cb_employed);
        cbSEmployd = findViewById(R.id.cb_sEmployed);
        cbFinancex = findViewById(R.id.cb_finance);
        cbPensionx = findViewById(R.id.cb_pension);

        btnNext = findViewById(R.id.btn_creditAppNext);
        btnPrvs = findViewById(R.id.btn_creditAppPrvs);

        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_MeansInfoSelection.this,Activity_EmploymentInfo.class);
            startActivity(intent);
            finish();
        });
        btnPrvs.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_MeansInfoSelection.this,Activity_ResidenceInfo.class);
            startActivity(intent);
            finish();
        });
    }
}