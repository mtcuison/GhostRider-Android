package org.rmj.guanzongroup.onlinecreditapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.g3appdriver.lib.integsys.CreditApp.OnSaveInfoListener;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.Properties;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.OnParseListener;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMProperties;

import java.util.Objects;

public class Activity_Properties extends AppCompatActivity {

    private VMProperties mViewModel;
    private MessageBox poMessage;

    private TextInputEditText txtLot1, txtLot2, txtLot3;
    private CheckBox cb4Wheels, cb3Wheels, cb2Wheels, cbAircon, cbRefxx, cbTelevsn;
    private Button btnPrvs, btnNext;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(Activity_Properties.this).get(VMProperties.class);
        poMessage = new MessageBox(Activity_Properties.this);
        setContentView(R.layout.activity_properties);
        initWidgets();
        mViewModel.InitializeApplication(getIntent());
        mViewModel.GetApplication().observe(Activity_Properties.this, new Observer<ECreditApplicantInfo>() {
            @Override
            public void onChanged(ECreditApplicantInfo app) {
                try {
                    mViewModel.getModel().setTransNox(app.getTransNox());
                    mViewModel.ParseData(app, new OnParseListener() {
                        @Override
                        public void OnParse(Object args) {
                            Properties loDetail = (Properties) args;
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        cb4Wheels.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (cb4Wheels.isChecked()) {
                mViewModel.getModel().setPs4Wheelsx("1");
            } else {
                mViewModel.getModel().setPs4Wheelsx("0");
            }
        });

        cb3Wheels.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (cb3Wheels.isChecked()) {
                mViewModel.getModel().setPs3Wheelsx("1");
            } else {
                mViewModel.getModel().setPs3Wheelsx("0");
            }
        });

        cb2Wheels.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (cb2Wheels.isChecked()) {
                mViewModel.getModel().setPs2Wheelsx("1");
            } else {
                mViewModel.getModel().setPs2Wheelsx("0");
            }
        });

        cbAircon.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (cbAircon.isChecked()) {
                mViewModel.getModel().setPsAirConxx("1");
            } else {
                mViewModel.getModel().setPsAirConxx("0");
            }
        });

        cbRefxx.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (cbRefxx.isChecked()) {
                mViewModel.getModel().setPsFridgexx("1");
            } else {
                mViewModel.getModel().setPsFridgexx("0");
            }
        });

        cbTelevsn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (cbTelevsn.isChecked()) {
                mViewModel.getModel().setPsTelevsnx("1");
            } else {
                mViewModel.getModel().setPsTelevsnx("0");
            }
        });


        btnNext.setOnClickListener(v -> SavePropertiesInfo());
        btnPrvs.setOnClickListener(v -> finish());

    }

    private void SavePropertiesInfo() {
        mViewModel.getModel().setPsLot1Addx(txtLot1.getText().toString().trim());
        mViewModel.getModel().setPsLot2Addx(txtLot2.getText().toString().trim());
        mViewModel.getModel().setPsLot3Addx(txtLot3.getText().toString().trim());

        mViewModel.SaveData(new OnSaveInfoListener() {
            @Override
            public void OnSave(String args) {
                Intent loIntent = new Intent(Activity_Properties.this, Activity_CoMaker.class);
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


    private void initWidgets() {

        toolbar = findViewById(R.id.toolbar_Properties);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Properties Info");


        txtLot1 = findViewById(R.id.tie_cap_propertyLot1);
        txtLot2 = findViewById(R.id.tie_cap_propertyLot2);
        txtLot3 = findViewById(R.id.tie_cap_propertyLot3);

        cb4Wheels = findViewById(R.id.cb_4Wheels);
        cb3Wheels = findViewById(R.id.cb_3Wheels);
        cb2Wheels = findViewById(R.id.cb_2Wheels);
        cbAircon = findViewById(R.id.cb_Aircon);
        cbRefxx = findViewById(R.id.cb_refrigerator);
        cbTelevsn = findViewById(R.id.cb_television);

        btnPrvs = findViewById(R.id.btn_creditAppPrvs);
        btnNext = findViewById(R.id.btn_creditAppNext);
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