package org.rmj.guanzongroup.onlinecreditapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

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
    }

    private void initWidgets() {

        toolbar = findViewById(R.id.toolbar_OtherInfo);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

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
        tieAddProv = findViewById(R.id.tie_cap_referenceAddProv);
        tieAddTown = findViewById(R.id.tie_cap_referenceAddTown);

        recyclerView = findViewById(R.id.recyclerview_references);

        tilOthrUser.setVisibility(View.GONE);
        tilOthrPayr.setVisibility(View.GONE);
        tilOtherSrc.setVisibility(View.GONE);
        btnNext = findViewById(R.id.btn_creditAppNext);
        btnPrevs = findViewById(R.id.btn_creditAppPrvs);

        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_OtherInfo.this, Activity_CoMaker.class);
            startActivity(intent);
            finish();
        });
        btnPrevs.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_OtherInfo.this, Activity_Properties.class);
            startActivity(intent);
            finish();
        });

    }
}