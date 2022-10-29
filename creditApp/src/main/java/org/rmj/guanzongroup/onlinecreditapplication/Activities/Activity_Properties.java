package org.rmj.guanzongroup.onlinecreditapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.guanzongroup.onlinecreditapplication.R;

import java.util.Objects;

public class Activity_Properties extends AppCompatActivity {

    private String TransNox;

    private TextInputEditText txtLot1, txtLot2, txtLot3;
    private CheckBox cb4Wheels, cb3Wheels, cb2Wheels, cbAircon, cbRefxx, cbTelevsn;
    private String s4Wheels = "",
            s3Wheels = "",
            s2Wheels = "",
            sAircon = "",
            sRefxx = "",
            sTelevsn = "";
    private Button btnPrvs, btnNext;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_properties);
        initWidgets();
        json();
    }

    private void json() {
        Intent receiveIntent = getIntent();
        String param = receiveIntent.getStringExtra("params");
        try {
            JSONObject object = new JSONObject(param);
            object.put("stxtLot1",txtLot1.getText().toString().trim());
            object.put("stxtLot2",txtLot2.getText().toString().trim());
            object.put("stxtLot3",txtLot3.getText().toString().trim());
            object.put("scb4Wheels",s4Wheels);
            object.put("scb3Wheels",s3Wheels);
            object.put("scb2Wheels",s2Wheels);
            object.put("scbAircon",sAircon);
            object.put("scbRefxx",sRefxx);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

        if (cb4Wheels.isChecked()){
            s4Wheels = "1";
        }else {
            s4Wheels = "0";
        }
        if (cb3Wheels.isChecked()){
            s3Wheels = "1";
        }else {
            s3Wheels = "0";
        }
        if (cb2Wheels.isChecked()){
            s2Wheels = "1";
        }else {
            s2Wheels = "0";
        }
        if (cbAircon.isChecked()){
            sAircon = "1";
        }else {
            sAircon = "0";
        }
        if (cbRefxx.isChecked()){
            sRefxx = "1";
        }else {
            sRefxx = "0";
        }
        if (cbTelevsn.isChecked()){
            sTelevsn = "1";
        }else {
            sTelevsn = "0";
        }

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