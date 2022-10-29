package org.rmj.guanzongroup.onlinecreditapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.guanzongroup.onlinecreditapplication.R;

import java.util.Objects;

public class Activity_MeansInfoSelection extends AppCompatActivity {

    private CheckBox cbEmployed, cbSEmployd, cbFinancex, cbPensionx;
    private String sEmployed = "", sSEmployd = "", sFinancex = "", sPensionx = "";
//    private JSONObject object;

    private Button btnNext, btnPrvs;
    private Toolbar toolbar;
    private JSONObject object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_means_info_selection);

        initWidgets();


    }

    private void json() {

        try {
            Intent receiveIntent = getIntent();
            String param = receiveIntent.getStringExtra("params");
            object = new JSONObject(param);
            object.put("sEmployedx", (sEmployed));
            object.put("sSEmploydx", (sSEmployd));
            object.put("sFinancexx", (sFinancex));
            object.put("sPensionxx", (sPensionx));


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void initWidgets() {
        toolbar = findViewById(R.id.toolbar_MeansInfoSelection);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Means Info");

        cbEmployed = findViewById(R.id.cb_employed);
        cbSEmployd = findViewById(R.id.cb_sEmployed);
        cbFinancex = findViewById(R.id.cb_finance);
        cbPensionx = findViewById(R.id.cb_pension);

        btnNext = findViewById(R.id.btn_creditAppNext);
        btnPrvs = findViewById(R.id.btn_creditAppPrvs);


//        cbEmployed.setOnClickListener(v -> {
//            boolean checked = ((CheckBox) v).isChecked();
//            if (checked) {
//                sEmployed = "yes";
//            } else {
//                sEmployed = "no";
//            }
//        });
//        cbSEmployd.setOnClickListener(v -> {
//            boolean checked = ((CheckBox) v).isChecked();
//            if (checked) {
//                sSEmployd = "yes";
//            } else {
//                sSEmployd = "no";
//            }
//        });
//        cbFinancex.setOnClickListener(v -> {
//            boolean checked = ((CheckBox) v).isChecked();
//            if (checked) {
//                sFinancex = "yes";
//            } else {
//                sFinancex = "no";
//            }
//        });
//        cbPensionx.setOnClickListener(v -> {
//            boolean checked = ((CheckBox) v).isChecked();
//            if (checked) {
//                sPensionx = "yes";
//            } else {
//                sPensionx = "no";
//            }
//        });




        btnNext.setOnClickListener(v -> {

            if (cbEmployed.isChecked()) {
                sEmployed = "yes";
            } else {
                sEmployed = "no";
            }


            if (cbSEmployd.isChecked()) {
                sSEmployd = "yes";
            } else {
                sSEmployd = "no";
            }


            if (cbFinancex.isChecked()) {
                sFinancex = "yes";
            } else {
                sFinancex = "no";
            }


            if (cbPensionx.isChecked()) {
                sPensionx = "yes";
            } else {
                sPensionx = "no";
            }

            json();
            Intent intent = new Intent(Activity_MeansInfoSelection.this, Activity_ReviewLoanApp.class);
            intent.putExtra("params", object.toString());
            startActivity(intent);
            finish();
        });
        btnPrvs.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_MeansInfoSelection.this, Activity_ResidenceInfo.class);
            startActivity(intent);
            finish();
        });


    }
}