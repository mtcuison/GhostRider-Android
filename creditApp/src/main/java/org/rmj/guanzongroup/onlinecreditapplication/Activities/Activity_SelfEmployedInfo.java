package org.rmj.guanzongroup.onlinecreditapplication.Activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.etc.FormatUIText;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.R;

import java.util.Objects;

public class Activity_SelfEmployedInfo extends AppCompatActivity {

    private AutoCompleteTextView spnBussNtr, spnBussTyp, spnBussSze, spnLngSrvc;

    private TextInputEditText txtBussName, txtBussAdds, txtLnghtSrv, txtMnthlyIn, txtMnthlyEx;
    private AutoCompleteTextView txtProvnc, txtTownxx;

    private Button btnNext, btnPrvs;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_employed_info);
        initWidgets();


        btnPrvs.setOnClickListener(v -> finish());
    }

    private void initWidgets() {
        toolbar = findViewById(R.id.toolbar_SelfEmployedInfo);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Self Employed Info");

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

// dropdown
        spnBussNtr.setAdapter(new ArrayAdapter<>(Activity_SelfEmployedInfo.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.BUSINESS_NATURE));
        spnBussNtr.setDropDownBackgroundResource(R.drawable.bg_gradient_light);

        spnBussTyp.setAdapter(new ArrayAdapter<>(Activity_SelfEmployedInfo.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.BUSINESS_TYPE));
        spnBussTyp.setDropDownBackgroundResource(R.drawable.bg_gradient_light);

        spnBussSze.setAdapter(new ArrayAdapter<>(Activity_SelfEmployedInfo.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.BUSINESS_SIZE));
        spnBussSze.setDropDownBackgroundResource(R.drawable.bg_gradient_light);

        spnLngSrvc.setAdapter(new ArrayAdapter<>(Activity_SelfEmployedInfo.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.LENGTH_OF_STAY));
        spnLngSrvc.setDropDownBackgroundResource(R.drawable.bg_gradient_light);


    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_intent_slide_in_left, R.anim.anim_intent_slide_out_right);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        getViewModelStore().clear();
        super.onDestroy();
    }
}