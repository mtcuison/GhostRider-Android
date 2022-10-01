package org.rmj.guanzongroup.onlinecreditapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.R;

import java.util.Objects;

public class Activity_Finance extends AppCompatActivity {

    private AutoCompleteTextView spnRelation;
    private String relationX = "-1";
    private TextInputEditText txtFNamex, txtFIncme, txtFMoble, txtFFacbk, txtFEmail;

    private AutoCompleteTextView txtFCntry;

    private Button btnNext, btnPrvs;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance);
        initWidgets();
        json();
    }

    private void json() {
        Intent receiveIntent = getIntent();
        String param = receiveIntent.getStringExtra("params");
        try {
            JSONObject object = new JSONObject(param);
            object.put("stxtFNamex",txtFNamex);
            object.put("stxtFIncme",txtFIncme);
            object.put("stxtFCntry",txtFCntry);
            object.put("stxtFMoble",txtFMoble);
            object.put("stxtFFacbk",txtFFacbk);
            object.put("stxtFEmail",txtFEmail);

            btnNext.setOnClickListener(v -> {
                Intent intent = new Intent(Activity_Finance.this, Activity_PensionInfo.class);
                intent.putExtra("params",object.toString());
                startActivity(intent);
                finish();
            });
            btnPrvs.setOnClickListener(v -> {
                Intent intent = new Intent(Activity_Finance.this, Activity_SelfEmployedInfo.class);
                startActivity(intent);
                finish();
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initWidgets() {
        toolbar = findViewById(R.id.toolbar_Finance);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Finance Info");


        spnRelation = findViewById(R.id.spn_financierRelation);

        txtFNamex = findViewById(R.id.txt_financierName);
        txtFIncme = findViewById(R.id.txt_financierInc);
        txtFCntry = findViewById(R.id.txt_financierCntry);
        txtFMoble = findViewById(R.id.txt_financierContact);
        txtFFacbk = findViewById(R.id.txt_financierFb);
        txtFEmail = findViewById(R.id.txt_financierEmail);

        btnNext = findViewById(R.id.btn_creditAppNext);
        btnPrvs = findViewById(R.id.btn_creditAppPrvs);

        spnRelation.setAdapter(new ArrayAdapter<>(Activity_Finance.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.FINANCE_SOURCE));
        spnRelation.setDropDownBackgroundResource(R.drawable.bg_gradient_light);


    }
}