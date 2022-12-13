package org.rmj.guanzongroup.onlinecreditapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.g3appdriver.lib.integsys.CreditApp.OnSaveInfoListener;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.RadioGridGroup;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMMeasnInfo;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMPersonalInfo;

import java.util.Objects;

public class Activity_MeansInfoSelection extends AppCompatActivity  {

    private RadioButton rbEmployed, rbSEmployd, rbFinancex, rbPensionx;
    private RadioGridGroup rgMeans;
    private Button btnNext, btnPrvs;
    private Toolbar toolbar;
    private VMMeasnInfo mViewModel;

    private MessageBox poMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_means_info_selection);
        initWidgets();
        mViewModel = new ViewModelProvider(Activity_MeansInfoSelection.this).get(VMMeasnInfo.class);
        poMessage = new MessageBox(Activity_MeansInfoSelection.this);
        mViewModel.InitializeApplication(getIntent());

        btnNext.setOnClickListener(v -> SaveMeansInfo());
        btnPrvs.setOnClickListener(v -> finish());

    }

    private void initWidgets() {
        toolbar = findViewById(R.id.toolbar_MeansInfoSelection);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Means Info");

        rbEmployed = findViewById(R.id.rb_employed);
        rbSEmployd = findViewById(R.id.rb_sEmployed);
        rbFinancex = findViewById(R.id.rb_finance);
        rbPensionx = findViewById(R.id.rb_pension);
        btnNext = findViewById(R.id.btn_creditAppNext);
        btnPrvs = findViewById(R.id.btn_creditAppPrvs);
        rgMeans = findViewById(R.id.rgMeans);
        rgMeans.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_employed){
                mViewModel.getModel().setIncmeSrc("0");
            }else if (checkedId == R.id.rb_sEmployed){
                mViewModel.getModel().setIncmeSrc("1");
            }else if (checkedId == R.id.rb_finance){
                mViewModel.getModel().setIncmeSrc("2");
            }else if (checkedId == R.id.rb_pension){
                mViewModel.getModel().setIncmeSrc("3");
            }
            Log.e("index = ",mViewModel.getModel().getIncmeSrc());
        });

    }
    private void SaveMeansInfo() {

        mViewModel.SaveData(new OnSaveInfoListener() {
            @Override
            public void OnSave(String args) {
//                Intent loIntent = new Intent(Activity_ResidenceInfo.this, Activity_EmploymentInfo.class);
                Intent loIntent = new Intent(Activity_MeansInfoSelection.this, Activity_EmploymentInfo.class);
                loIntent.putExtra("sTransNox", args);
                startActivity(loIntent);
                overridePendingTransition(R.anim.anim_intent_slide_in_right, R.anim.anim_intent_slide_out_left);
            }

            @Override
            public void OnFailed(String message) {
                poMessage.initDialog();
                poMessage.setTitle("Credit Online Application");
                poMessage.setMessage(message);
                poMessage.setPositiveButton("Okay", (view1, dialog) -> dialog.dismiss());
                poMessage.show();
            }
        });


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