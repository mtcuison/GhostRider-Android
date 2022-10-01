package org.rmj.guanzongroup.onlinecreditapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.rmj.g3appdriver.etc.OnDateSetListener;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.R;

import java.util.Objects;

public class Activity_CoMaker extends AppCompatActivity {

    private String spnIncomePosition = "-1";
    private String spnCoRelationPosition = "-1";
    private String spnPrmryCntctPosition = "-1";
    private String spnScndCntctPosition = "-1";
    private String spnTrtCntctPosition = "-1";

    private TextInputEditText tieLastname, tieFrstname, tieMiddname, tiePrmCntct, tieScnCntct,
            tieTrtCntct, tieNickname, tieBrthDate, tiePrmCntctPlan, tieScnCntctPlan, tieTrtCntctPlan,
            tieFbAcctxx;
    private AutoCompleteTextView tieSuffixxx, tieBrthProv, tieBrthTown, spnPrmCntct, spnScnCntct,
            spnTrtCntct, spnIncmSrce, spnBrwrRltn;
    private TextInputLayout tilPrmCntctPlan, tilScnCntctPlan, tilTrtCntctPlan;

    private Button btnPrvs, btnNext;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_co_maker);
        initWidgets();
    }

    private void initWidgets() {
        toolbar = findViewById(R.id.toolbar_CoMaker);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Co-Maker Info");

        tieLastname = findViewById(R.id.tie_cap_cmrLastname);
        tieFrstname = findViewById(R.id.tie_cap_cmrFirstname);
        tieMiddname = findViewById(R.id.tie_cap_cmrMiddname);
        tieSuffixxx = findViewById(R.id.tie_cap_cmrSuffix);
        tieNickname = findViewById(R.id.tie_cap_cmrNickname);
        tieBrthDate = findViewById(R.id.tie_cap_cmrBirthdate);
        tieBrthProv = findViewById(R.id.tie_cap_cmrBirthProv);
        tieBrthTown = findViewById(R.id.tie_cap_cmrBirthTown);
        tiePrmCntct = findViewById(R.id.tie_cap_cmrPrimaryContactNo);
        tieScnCntct = findViewById(R.id.tie_cap_cmrSecondaryContactNo);
        tieTrtCntct = findViewById(R.id.tie_cap_cmrTertiaryContactNo);

        tilPrmCntctPlan = findViewById(R.id.til_cap_cmrPrimaryCntctPlan);
        tilScnCntctPlan = findViewById(R.id.til_cap_cmrSecondaryCntctPlan);
        tilTrtCntctPlan = findViewById(R.id.til_cap_cmrTertiaryCntctPlan);

        tiePrmCntctPlan = findViewById(R.id.tie_cap_cmrPrimaryCntctPlan);
        tieScnCntctPlan = findViewById(R.id.tie_cap_cmrSecondaryCntctPlan);
        tieTrtCntctPlan = findViewById(R.id.tie_cap_cmrTertiaryCntctPlan);
        tieFbAcctxx = findViewById(R.id.tie_cap_cmrFacebookacc);

        spnIncmSrce = findViewById(R.id.spinner_cap_cmrIncomeSrc);
        spnBrwrRltn = findViewById(R.id.spinner_cap_cmrBarrowerRelation);
        spnPrmCntct = findViewById(R.id.spinner_cap_cmrPrimaryCntctStats);
        spnScnCntct = findViewById(R.id.spinner_cap_cmrSecondaryCntctStats);
        spnTrtCntct = findViewById(R.id.spinner_cap_cmrTertiaryCntctStats);

        tieBrthDate.addTextChangedListener(new OnDateSetListener(tieBrthDate));
        btnNext = findViewById(R.id.btn_creditAppNext);
        btnPrvs = findViewById(R.id.btn_creditAppPrvs);


        spnIncmSrce.setAdapter(new ArrayAdapter<>(Activity_CoMaker.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.CO_MAKER_INCOME_SOURCE));
        spnIncmSrce.setDropDownBackgroundResource(R.drawable.bg_gradient_light);

        spnBrwrRltn.setAdapter(new ArrayAdapter<>(Activity_CoMaker.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.CO_MAKER_RELATIONSHIP));
        spnBrwrRltn.setDropDownBackgroundResource(R.drawable.bg_gradient_light);

        spnPrmCntct.setAdapter(new ArrayAdapter<>(Activity_CoMaker.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.MOBILE_NO_TYPE));
        spnPrmCntct.setDropDownBackgroundResource(R.drawable.bg_gradient_light);

        spnScnCntct.setAdapter(new ArrayAdapter<>(Activity_CoMaker.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.MOBILE_NO_TYPE));
        spnScnCntct.setDropDownBackgroundResource(R.drawable.bg_gradient_light);

        spnTrtCntct.setAdapter(new ArrayAdapter<>(Activity_CoMaker.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.MOBILE_NO_TYPE));
        spnTrtCntct.setDropDownBackgroundResource(R.drawable.bg_gradient_light);

        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_CoMaker.this, Activity_ComakerResidence.class);
            startActivity(intent);
            finish();
        });
        btnPrvs.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_CoMaker.this, Activity_OtherInfo.class);
            startActivity(intent);
            finish();
        });

    }


}