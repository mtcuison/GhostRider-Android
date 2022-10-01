package org.rmj.guanzongroup.onlinecreditapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;

import org.rmj.guanzongroup.onlinecreditapplication.R;

import java.util.Objects;

public class Activity_Properties extends AppCompatActivity {

    private String TransNox;

    private TextInputEditText txtLot1, txtLot2, txtLot3;
    private CheckBox cb4Wheels, cb3Wheels, cb2Wheels, cbAircon, cbRefxx, cbTelevsn;
    private Button btnPrvs, btnNext;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_properties);
        initWidgets();
    }

    private void initWidgets() {

        toolbar = findViewById(R.id.toolbar_Properties);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Properties Info");


        txtLot1 = findViewById(R.id.tie_cap_propertyLot1);
        txtLot2 = findViewById(R.id.tie_cap_propertyLot2);
        txtLot3 = findViewById(R.id.tie_cap_propertyLot3);

        cb4Wheels = findViewById(R.id.cb_4Wheels);
        cb3Wheels = findViewById(R.id.cb_3Wheels);
        cb2Wheels = findViewById(R.id.cb_2Wheels);
        cbAircon = findViewById(R.id.cb_Aircon);
        cbRefxx = findViewById(R.id.cb_refrigerator);
        cbTelevsn = findViewById(R.id.cb_television);

        btnPrvs = findViewById(R.id.btn_creditAppPrvs);
        btnNext = findViewById(R.id.btn_creditAppNext);

        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_Properties.this, Activity_OtherInfo.class);
            startActivity(intent);
            finish();
        });
        btnPrvs.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_Properties.this, Activity_Dependent.class);
            startActivity(intent);
            finish();
        });

    }
}