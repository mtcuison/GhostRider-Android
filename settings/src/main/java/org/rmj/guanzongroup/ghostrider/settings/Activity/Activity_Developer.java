/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.settings
 * Electronic Personnel Access Control Security System
 * project file created : 8/4/21 4:50 PM
 * project file last modified : 8/4/21 4:50 PM
 */

package org.rmj.guanzongroup.ghostrider.settings.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

import org.rmj.g3appdriver.dev.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.dev.DeptCode;
import org.rmj.guanzongroup.ghostrider.settings.R;
import org.rmj.guanzongroup.ghostrider.settings.ViewModel.VMDevMode;

import java.util.Objects;

public class Activity_Developer extends AppCompatActivity {

    private VMDevMode mViewModel;
    private Toolbar toolbar;
    private SwitchMaterial poSwitch;
    private Spinner spnLevl;
    private MaterialButton btnSave, btnRestore;

    private EEmployeeInfo poInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);
        mViewModel = new ViewModelProvider(this).get(VMDevMode.class);
        toolbar = findViewById(R.id.toolbar_deveMode);
        poSwitch = findViewById(R.id.sm_dcpTest);
        spnLevl = findViewById(R.id.spn_employeeLevel);
        btnRestore = findViewById(R.id.btn_restoreDefault);
        btnSave = findViewById(R.id.btn_Save);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        spnLevl.setAdapter(new ArrayAdapter<>(Activity_Developer.this, android.R.layout.simple_dropdown_item_1line, DeptCode.Employee_Levels));

        mViewModel.GetUserInfo().observe(Activity_Developer.this, info -> {
            try{
                poInfo = info;
                for(int x = 0; x < DeptCode.Employee_Levels.length; x++){
                    if(DeptCode.parseUserLevel(info.getEmpLevID()).equalsIgnoreCase(DeptCode.Employee_Levels[x])){
                        spnLevl.setSelection(x);
                        break;
                    }
                }

            } catch (Exception e){
                e.printStackTrace();
            }
        });

        spnLevl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                poInfo.setEmpLevID(spnLevl.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnSave.setOnClickListener(v -> {
            setResult(Activity.RESULT_OK);
            finish();
        });

        btnRestore.setOnClickListener(v -> mViewModel.RestoreDefault(() -> {
            setResult(Activity.RESULT_OK);
            finish();
        }));
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_intent_slide_in_left, R.anim.anim_intent_slide_out_right);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            setResult(Activity.RESULT_CANCELED);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}