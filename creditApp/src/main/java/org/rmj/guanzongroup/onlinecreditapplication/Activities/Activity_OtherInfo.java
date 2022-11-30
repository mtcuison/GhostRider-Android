package org.rmj.guanzongroup.onlinecreditapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.R;

import java.util.Objects;

public class Activity_OtherInfo extends AppCompatActivity {

    private String TownID = "";
    private String psProvIdx;

    private AutoCompleteTextView spnUnitUser, spnUnitPrps, spnUnitPayr, spnSourcexx, spnOthrUser, spnOthrPayr,
            tieAddProv, tieAddTown;
    private TextInputLayout tilOthrUser, tilOthrPayr, tilOtherSrc;

    private TextInputEditText tieOthrSrc, tieRefAdd1, tieRefName, tieRefCntc;

    private RecyclerView recyclerView;
    private Button btnPrevs;
    private Button btnNext;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_info);
        initWidgets();


        btnPrevs.setOnClickListener(v -> finish());

    }

    private void initWidgets() {

        toolbar = findViewById(R.id.toolbar_OtherInfo);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Other Info");

        tilOthrUser = findViewById(R.id.til_cap_otherUser);
        tilOthrPayr = findViewById(R.id.til_cap_otherPayer);
        tilOtherSrc = findViewById(R.id.til_cap_otherSource);

        spnUnitPrps = findViewById(R.id.spinner_cap_purposeOfBuying);
        spnUnitPayr = findViewById(R.id.spinner_cap_monthlyPayer);
        spnUnitUser = findViewById(R.id.spinner_cap_unitUser);
        spnOthrUser = findViewById(R.id.spinner_cap_otherUser);
        spnOthrPayr = findViewById(R.id.spinner_cap_otherPayer);
        spnSourcexx = findViewById(R.id.spinner_cap_source);

        tieOthrSrc = findViewById(R.id.tie_cap_otherSource);
        tieRefName = findViewById(R.id.tie_cap_referenceName);
        tieRefCntc = findViewById(R.id.tie_cap_refereceContact);
        tieRefAdd1 = findViewById(R.id.tie_cap_refereceAddress);
//        tieAddProv = findViewById(R.id.tie_cap_referenceAddProv);
        tieAddTown = findViewById(R.id.tie_cap_referenceAddTown);

        recyclerView = findViewById(R.id.recyclerview_references);

        tilOthrUser.setVisibility(View.GONE);
        tilOthrPayr.setVisibility(View.GONE);
        tilOtherSrc.setVisibility(View.GONE);

        btnNext = findViewById(R.id.btn_creditAppNext);
        btnPrevs = findViewById(R.id.btn_creditAppPrvs);

        spnUnitUser.setAdapter(new ArrayAdapter<>(Activity_OtherInfo.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.UNIT_USER));
        spnUnitUser.setDropDownBackgroundResource(R.drawable.bg_gradient_light);

        spnOthrUser.setAdapter(new ArrayAdapter<>(Activity_OtherInfo.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.UNIT_USER_OTHERS));
        spnOthrUser.setDropDownBackgroundResource(R.drawable.bg_gradient_light);

        spnUnitPrps.setAdapter(new ArrayAdapter<>(Activity_OtherInfo.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.UNIT_PURPOSE));
        spnUnitPrps.setDropDownBackgroundResource(R.drawable.bg_gradient_light);

        spnUnitPayr.setAdapter(new ArrayAdapter<>(Activity_OtherInfo.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.UNIT_USER));
        spnUnitPayr.setDropDownBackgroundResource(R.drawable.bg_gradient_light);

        spnOthrPayr.setAdapter(new ArrayAdapter<>(Activity_OtherInfo.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.UNIT_PAYER));
        spnOthrPayr.setDropDownBackgroundResource(R.drawable.bg_gradient_light);

        spnSourcexx.setAdapter(new ArrayAdapter<>(Activity_OtherInfo.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.UNIT_PAYER));
        spnSourcexx.setDropDownBackgroundResource(R.drawable.bg_gradient_light);

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