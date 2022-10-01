package org.rmj.guanzongroup.onlinecreditapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.guanzongroup.onlinecreditapplication.R;

import java.util.Objects;

public class Activity_SpousePensionInfo extends AppCompatActivity {

    private RadioGroup rgPensionSector;
    private RadioButton rbGovt, rbPrivate;
    private TextInputEditText txtPensionAmt, txtRetirementYr, txtOtherSrc, txtOtherSrcInc;
    private String sPenSector;
    private Button btnNext, btnPrvs;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spouse_pension_info);
        initWidgets();
        json();
    }

    private void json() {
        Intent receiveIntent = getIntent();
        String param = receiveIntent.getStringExtra("params");
        try {
            JSONObject object = new JSONObject(param);
            object.put("sPenSector", sPenSector);
            object.put("stxtPensionAmt", txtPensionAmt);
            object.put("stxtRetirementYr", txtRetirementYr);
            object.put("stxtOtherSrc", txtOtherSrc);
            object.put("stxtOtherSrcInc", txtOtherSrcInc);

            btnNext.setOnClickListener(v -> {
                Intent intent = new Intent(Activity_SpousePensionInfo.this, Activity_DisbursementInfo.class);
                intent.putExtra("params",object.toString());
                startActivity(intent);
                finish();
            });
            btnPrvs.setOnClickListener(v -> {
                Intent intent = new Intent(Activity_SpousePensionInfo.this, Activity_SpouseSelfEmploymentInfo.class);
                startActivity(intent);
                finish();
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void initWidgets() {
        toolbar = findViewById(R.id.toolbar_SpousePensionInfo);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Spouse Pension Info");


        rgPensionSector = findViewById(R.id.rg_sector);
        rbGovt = findViewById(R.id.rb_government);
        rbPrivate = findViewById(R.id.rb_private);

        txtPensionAmt = findViewById(R.id.txt_pension_amt);
        txtRetirementYr = findViewById(R.id.txt_retirement_yr);
        txtOtherSrc = findViewById(R.id.txt_other_source);
        txtOtherSrcInc = findViewById(R.id.txt_other_source_income);

        btnNext = findViewById(R.id.btn_creditAppNext);
        btnPrvs = findViewById(R.id.btn_creditAppPrvs);

        rgPensionSector.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rbGovt.isChecked()){
                    sPenSector = "Government";
                }else if (rbPrivate.isChecked()){
                    sPenSector = "Private";
                }
            }
        });



    }
}