package org.rmj.guanzongroup.onlinecreditapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.g3appdriver.lib.integsys.CreditApp.OnSaveInfoListener;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.Dependent;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.OnParseListener;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMDependent;

import java.util.Objects;

public class Activity_Dependent extends AppCompatActivity {

    private VMDependent mViewModel;
    private MessageBox poMessage;

    private TextInputEditText tieFullname, tieDpdAgexx, tieSchoolNm, tieSchlAddx;
    private AutoCompleteTextView tieSchlProv, tieSchlTown;
    private TextInputEditText tieCompName;
    private LinearLayout linearStudent, linearEmployd;
    private RecyclerView recyclerView;

    private AutoCompleteTextView actRelationx, actSchoolType, actSchoolLvl, actEmploymentType;
    private TextView actRelationPosition;

    private RadioGroup rgDpdEmployd, rgDpdStudent;
    private RadioButton rbDpdEmploydYes, rbDpdEmploydNo, rbDpdStudentYes, rbDpdStudentNo;
    private MaterialButton btnAddDependent;
    private CheckBox cbScholarxx, cbDependent, cbHouseHold;
    private CheckBox cbIsMarried;
    private Button btnPrev, btnNext;
    private Toolbar toolbar;


    public String Employment = "";
    //    private DependentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(Activity_Dependent.this).get(VMDependent.class);
        poMessage = new MessageBox(Activity_Dependent.this);
        setContentView(R.layout.activity_dependent);
        initWidgets();
        mViewModel.InitializeApplication(getIntent());
        mViewModel.GetApplication().observe(Activity_Dependent.this, new Observer<ECreditApplicantInfo>() {
            @Override
            public void onChanged(ECreditApplicantInfo app) {
                mViewModel.getModel().setTransNox(app.getTransNox());
                mViewModel.ParseData(app, new OnParseListener() {
                    @Override
                    public void OnParse(Object args) {
                        try {
                            Dependent loDetail = (Dependent) args;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        btnNext.setOnClickListener(v -> SaveDependentInfo());
        btnPrev.setOnClickListener(v -> finish());


    }

    private void SaveDependentInfo() {
        mViewModel.getModel().getDependentList().get(0).setFullName(Objects.requireNonNull(tieFullname.getText()).toString().trim());


        mViewModel.SaveData(new OnSaveInfoListener() {
            @Override
            public void OnSave(String args) {
                Intent loIntent = new Intent(Activity_Dependent.this, Activity_Properties.class);
                loIntent.putExtra("sTransNox", args);
                startActivity(loIntent);
                overridePendingTransition(R.anim.anim_intent_slide_in_right, R.anim.anim_intent_slide_out_left);
            }

            @Override
            public void OnFailed(String message) {

            }
        });
    }


    private void initWidgets() {
        toolbar = findViewById(R.id.toolbar_Dependent);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Dependent Info");

        tieFullname = findViewById(R.id.tie_cap_dpdFullname);
        tieDpdAgexx = findViewById(R.id.tie_cap_dpdAge);
        tieCompName = findViewById(R.id.tie_cap_dpdCompanyName);
        tieSchoolNm = findViewById(R.id.tie_cap_dpdSchoolName);
        tieSchlAddx = findViewById(R.id.tie_cap_dpdSchoolAddress);
        tieSchlProv = findViewById(R.id.tie_cap_dpdSchoolProv);
        tieSchlTown = findViewById(R.id.tie_cap_dpdSchoolTown);

        actRelationx = findViewById(R.id.spinner_cap_dpdRelation);

        rgDpdStudent = findViewById(R.id.rg_cap_dpdStudent);
        rbDpdStudentYes = findViewById(R.id.rb_cap_dpdStudentYes);
        rbDpdStudentNo = findViewById(R.id.rb_cap_dpdStudentNo);

        rgDpdEmployd = findViewById(R.id.rg_cap_dpdEmployed);
        rbDpdEmploydYes = findViewById(R.id.rb_cap_dpdEmployedYes);
        rbDpdEmploydNo = findViewById(R.id.rb_cap_dpdEmployedNo);

        actSchoolType = findViewById(R.id.spn_cap_dpdSchoolType);
        actSchoolLvl = findViewById(R.id.spinner_cap_educLevel);
        actEmploymentType = findViewById(R.id.spn_cap_dpdEmployedType);

        linearEmployd = findViewById(R.id.linearEmployd);
        linearStudent = findViewById(R.id.linearStudent);

        cbDependent = findViewById(R.id.cb_cap_Dependent);
        cbHouseHold = findViewById(R.id.cb_cap_HouseHold);
        cbIsMarried = findViewById(R.id.cb_cap_Married);
        cbScholarxx = findViewById(R.id.cb_cap_dpdScholar);

        recyclerView = findViewById(R.id.recyclerview_dependencies);

        btnAddDependent = findViewById(R.id.btn_dpd_add);
        btnNext = findViewById(R.id.btn_creditAppNext);
        btnPrev = findViewById(R.id.btn_creditAppPrvs);


        cbDependent.setOnClickListener(v -> {
            boolean checked = ((CheckBox) v).isChecked();


            actRelationx.setAdapter(new ArrayAdapter<>(Activity_Dependent.this,
                    android.R.layout.simple_list_item_1, CreditAppConstants.DEPENDENT_RELATIONSHIP));
            actRelationx.setDropDownBackgroundResource(R.drawable.bg_gradient_light);

            actSchoolType.setAdapter(new ArrayAdapter<>(Activity_Dependent.this,
                    android.R.layout.simple_list_item_1, CreditAppConstants.SCHOOL_TYPE));
            actRelationx.setDropDownBackgroundResource(R.drawable.bg_gradient_light);

            actSchoolLvl.setAdapter(new ArrayAdapter<>(Activity_Dependent.this,
                    android.R.layout.simple_list_item_1, CreditAppConstants.SCHOOL_LEVEL));
            actSchoolLvl.setDropDownBackgroundResource(R.drawable.bg_gradient_light);

            actEmploymentType.setAdapter(new ArrayAdapter<>(Activity_Dependent.this,
                    android.R.layout.simple_list_item_1, CreditAppConstants.EMPLOYMENT_SECTOR));
            actEmploymentType.setDropDownBackgroundResource(R.drawable.bg_gradient_light);

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