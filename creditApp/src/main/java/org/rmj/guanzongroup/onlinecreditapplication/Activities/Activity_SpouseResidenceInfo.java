package org.rmj.guanzongroup.onlinecreditapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;
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
        json();
    }

    private void json() {
        Intent receiveIntent = getIntent();
        String param = receiveIntent.getStringExtra("params");
        try {
            JSONObject object = new JSONObject(param);
            object.put("stxtLandMark",txtLandMark);
            object.put("stxtHouseNox",txtHouseNox);
            object.put("stxtAddress1",txtAddress1);
            object.put("stxtAddress2",txtAddress2);
            object.put("stxtProvince",txtProvince);
            object.put("stxtTown",txtTown);
            object.put("stxtBarangay",txtBarangay);

            btnNext.setOnClickListener(v -> {
                Intent intent = new Intent(Activity_SpouseResidenceInfo.this, Activity_SpouseEmploymentInfo.class);
                intent.putExtra("params",object.toString());
                startActivity(intent);
                finish();
            });
            btnPrvs.setOnClickListener(v -> {
                Intent intent = new Intent(Activity_SpouseResidenceInfo.this, Activity_SpouseInfo.class);
                startActivity(intent);
                finish();
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initWidgets() {
        toolbar = findViewById(R.id.toolbar_SpouseResidenceInfo);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Spouse Residence Info");


        txtLandMark = findViewById(R.id.txt_landmark);
        txtHouseNox = findViewById(R.id.txt_houseNox);
        txtAddress1 = findViewById(R.id.txt_address);
        txtAddress2 = findViewById(R.id.txt_address2);
        txtProvince = findViewById(R.id.txt_province);
        txtTown = findViewById(R.id.txt_town);
        txtBarangay = findViewById(R.id.txt_barangay);

        btnNext = findViewById(R.id.btn_creditAppNext);
        btnPrvs = findViewById(R.id.btn_creditAppPrvs);



    }
}