package org.rmj.guanzongroup.onlinecreditapplication.Activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.rmj.guanzongroup.onlinecreditapplication.R;

import java.util.Objects;

public class Activity_MeansInfoSelection extends AppCompatActivity {

    private CheckBox cbEmployed, cbSEmployd, cbFinancex, cbPensionx;

    private Button btnNext, btnPrvs;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_means_info_selection);
        initWidgets();


        btnPrvs.setOnClickListener(v -> finish());

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