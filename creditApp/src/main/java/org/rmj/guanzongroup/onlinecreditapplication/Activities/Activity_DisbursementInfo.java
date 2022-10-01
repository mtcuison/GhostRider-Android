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
import org.rmj.g3appdriver.GRider.Etc.FormatUIText;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.R;

import java.util.Objects;

public class Activity_DisbursementInfo extends AppCompatActivity {

    private AutoCompleteTextView spnTypex;
    private String typeX = "";
    private TextInputEditText tieFoodx, tieElctx, tieWater, tieLoans, tieBankN, tieCCBnk, tieLimit, tieYearS;
    private Button btnPrev, btnNext;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disbursement_info);
        initWidgets();
        json();
    }

    private void json() {
        Intent receiveIntent = getIntent();
        String param = receiveIntent.getStringExtra("params");
        try {
            JSONObject object = new JSONObject(param);
            object.put("stieElctx",tieElctx);
            object.put("stieWater",tieWater);
            object.put("stieFoodx",tieFoodx);
            object.put("stieLoans",tieLoans);
            object.put("stieBankN",tieBankN);
            object.put("sspnTypex",spnTypex);
            object.put("stieCCBnk",tieCCBnk);
            object.put("stieLimit",tieLimit);
            object.put("stieYearS",tieYearS);

            btnNext.setOnClickListener(v -> {
                Intent intent = new Intent(Activity_DisbursementInfo.this, Activity_Dependent.class);
                intent.putExtra("params", object.toString());
                startActivity(intent);
                finish();
            });
            btnPrev.setOnClickListener(v -> {
                Intent intent = new Intent(Activity_DisbursementInfo.this, Activity_SpousePensionInfo.class);
                startActivity(intent);
                finish();
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void initWidgets() {
        toolbar = findViewById(R.id.toolbar_DisbursementInfo);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Disbursement Info");

        tieElctx = findViewById(R.id.tie_cap_dbmElectricity);
        tieWater = findViewById(R.id.tie_cap_dbmWater);
        tieFoodx = findViewById(R.id.tie_cap_dbmFood);
        tieLoans = findViewById(R.id.tie_cap_dbmLoans);
        tieBankN = findViewById(R.id.tie_cap_dbmBankName);

        spnTypex = findViewById(R.id.spinner_cap_dbmAccountType);

        tieCCBnk = findViewById(R.id.tie_cap_dbmBankNameCC);
        tieLimit = findViewById(R.id.tie_cap_dbmCreditLimit);
        tieYearS = findViewById(R.id.tie_cap_dbmYearStarted);

        tieElctx.addTextChangedListener(new FormatUIText.CurrencyFormat(tieElctx));
        tieWater.addTextChangedListener(new FormatUIText.CurrencyFormat(tieWater));
        tieFoodx.addTextChangedListener(new FormatUIText.CurrencyFormat(tieFoodx));
        tieLoans.addTextChangedListener(new FormatUIText.CurrencyFormat(tieLoans));
        tieLimit.addTextChangedListener(new FormatUIText.CurrencyFormat(tieLimit));

        btnNext = findViewById(R.id.btn_creditAppNext);
        btnPrev = findViewById(R.id.btn_creditAppPrvs);

        spnTypex.setAdapter(new ArrayAdapter<>(Activity_DisbursementInfo.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.ACCOUNT_TYPE));
        spnTypex.setDropDownBackgroundResource(R.drawable.bg_gradient_light);



    }
}