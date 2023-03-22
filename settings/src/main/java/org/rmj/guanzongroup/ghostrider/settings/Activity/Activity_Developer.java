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

import static org.rmj.g3appdriver.dev.DeptCode.Departments;
import static org.rmj.g3appdriver.dev.PositionCode.Positions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

import org.rmj.g3appdriver.dev.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.dev.DeptCode;
import org.rmj.g3appdriver.dev.PositionCode;
import org.rmj.guanzongroup.ghostrider.settings.R;
import org.rmj.guanzongroup.ghostrider.settings.ViewModel.VMDevMode;

import java.util.Objects;

public class Activity_Developer extends AppCompatActivity {

    private VMDevMode mViewModel;
    private Toolbar toolbar;
    private Spinner spnLevl;
    private AutoCompleteTextView txtDept, txtPost;
    private MaterialButton btnSave, btnRestore;
    private SwitchMaterial smTestMode;

    private EEmployeeInfo poInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMDevMode.class);
        setContentView(R.layout.activity_developer);
        toolbar = findViewById(R.id.toolbar_deveMode);
        spnLevl = findViewById(R.id.spn_employeeLevel);
        btnRestore = findViewById(R.id.btn_restoreDefault);
        txtDept = findViewById(R.id.txt_department);
        txtPost = findViewById(R.id.txt_position);
        btnSave = findViewById(R.id.btn_Save);
        smTestMode = findViewById(R.id.sm_TestMode);

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

                txtDept.setText(DeptCode.getDepartmentName(info.getDeptIDxx()));
                txtPost.setText(PositionCode.getPositionName(info.getPositnID()));

            } catch (Exception e){
                e.printStackTrace();
            }
        });

        txtDept.setAdapter(new ArrayAdapter<>(Activity_Developer.this, android.R.layout.simple_spinner_dropdown_item, Departments));
        txtPost.setAdapter(new ArrayAdapter<>(Activity_Developer.this, android.R.layout.simple_spinner_dropdown_item, Positions));

        txtDept.setOnItemClickListener((parent, view, position, id) -> poInfo.setDeptIDxx(DeptCode.getDepartmentCode(txtDept.getText().toString())));
        txtPost.setOnItemClickListener((parent, view, position, id) -> poInfo.setPositnID(PositionCode.getPositionCode(txtPost.getText().toString())));

        spnLevl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                poInfo.setEmpLevID(spnLevl.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnSave.setOnClickListener(v -> mViewModel.SaveChanges(poInfo, args -> Toast.makeText(Activity_Developer.this, args, Toast.LENGTH_SHORT).show()));

        btnRestore.setOnClickListener(v -> mViewModel.RestoreDefault(args -> Toast.makeText(Activity_Developer.this, args, Toast.LENGTH_SHORT).show()));
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