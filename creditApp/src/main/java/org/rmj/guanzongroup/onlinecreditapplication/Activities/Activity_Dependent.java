package org.rmj.guanzongroup.onlinecreditapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.R;

import java.util.Objects;

public class Activity_Dependent extends AppCompatActivity {

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
    private String sDepStud = "", sDepEmp = "";
    private Button btnPrev, btnNext;
    private Toolbar toolbar;
    private String scbDependent, scbHouseHold, scbIsMarried, scbScholarxx;

    private int mRelationPosition = -1;
    private int mEducLvlPosition = 0;

    public String Employment = "";
    private String IsStudentx = "-1", IsEmployed = "-1", Dependentx = "0", HouseHoldx = "0", IsMarriedx = "0", IsScholarx = "0", IsPrivatex = "0";
    private String actRelationshipX = "-1";

    private static String dpdSchoolType = "", dpdSchoolLvl = "", dpdEmpType = "", dpdRelationX = "", TownID = "";
//    private DependentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dependent);
        initWidgets();
        json();

    }

    private void json() {
        Intent receiveIntent = getIntent();
        String param = receiveIntent.getStringExtra("params");
        try {
            JSONObject object = new JSONObject(param);
            object.put("stieFullname", tieFullname.getText().toString().trim());
            object.put("stieDpdAgexx", tieDpdAgexx.getText().toString().trim());
            object.put("stieCompName", tieCompName.getText().toString().trim());
            object.put("stieSchoolNm", tieSchoolNm.getText().toString().trim());
            object.put("stieSchlAddx", tieSchlAddx.getText().toString().trim());
            object.put("stieSchlProv", tieSchlProv.getText().toString().trim());
            object.put("stieSchlTown", tieSchlTown.getText().toString().trim());
            object.put("sactRelationx", actRelationx.getText().toString().trim());
            object.put("ssDepStud", sDepStud);
            object.put("ssDepEmp", sDepEmp);
            object.put("scbDependent", scbDependent);
            object.put("scbHouseHold", scbHouseHold);
            object.put("scbIsMarried", scbIsMarried);
            object.put("scbScholarxx", scbScholarxx);

            btnNext.setOnClickListener(v -> {
                Intent intent = new Intent(Activity_Dependent.this, Activity_Properties.class);
                intent.putExtra("params", object.toString());
                startActivity(intent);
                finish();
            });
            btnPrev.setOnClickListener(v -> {
                Intent intent = new Intent(Activity_Dependent.this, Activity_DisbursementInfo.class);
                startActivity(intent);
                finish();
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
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

            if (checked) {
                scbDependent = "yes";
            } else {
                scbDependent = "no";
            }
        });
        cbHouseHold.setOnClickListener(v -> {
            boolean checked = ((CheckBox) v).isChecked();
            if (checked) {
                scbHouseHold = "yes";
            } else {
                scbHouseHold = "no";
            }

        });
        cbIsMarried.setOnClickListener(v -> {
            boolean checked = ((CheckBox) v).isChecked();
            if (checked) {
                scbIsMarried = "yes";
            } else {
                scbIsMarried = "no";
            }
        });
        cbScholarxx.setOnClickListener(v -> {
            boolean checked = ((CheckBox) v).isChecked();
            if (checked) {
                scbScholarxx = "yes";
            } else {
                scbScholarxx = "no";
            }
        });

        rgDpdStudent.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rbDpdStudentYes.isChecked()) {
                    sDepStud = "Yes";
                } else if (rbDpdStudentNo.isChecked()) {
                    sDepStud = "No";
                }
            }
        });

        rgDpdEmployd.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rbDpdEmploydYes.isChecked()) {
                    sDepEmp = "Yes";
                } else if (rbDpdEmploydNo.isChecked()) {
                    sDepEmp = "No";
                }
            }
        });

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


    }
}