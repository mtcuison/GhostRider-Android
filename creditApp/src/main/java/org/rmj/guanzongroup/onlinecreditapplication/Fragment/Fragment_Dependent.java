package org.rmj.guanzongroup.onlinecreditapplication.Fragment;

import androidx.constraintlayout.solver.widgets.analyzer.Dependency;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;
import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.gocas.base.GOCASApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Activity.Activity_CreditApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Adapter.DependentAdapter;
import org.rmj.guanzongroup.onlinecreditapplication.Adapter.PersonalReferencesAdapter;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.Model.DependentsInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.OtherInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMDependent;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMOtherInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Fragment_Dependent extends Fragment implements ViewModelCallBack,VMDependent.ExpActionListener{

    private static final String TAG = Fragment_Dependent.class.getSimpleName();
    private VMDependent mViewModel;
    private String TransNox;
    private View v;

    public String Employment = "";
    private String IsStudentx = "-1";
    private String IsEmployed = "-1";
    private String Dependentx = "0";
    private String HouseHoldx = "0";
    private String IsMarriedx = "0";
    private String IsScholarx = "0";
    private String IsPrivatex = "0";


    private String actRelationshipX = "-1";

    private static String dpdSchoolType ="";
    private static String dpdSchoolLvl = "";
    private static String dpdEmpType = "";
    private static String dpdRelationX = "";
    private DependentAdapter adapter;

    private TextInputEditText tieFullname;
    private TextInputEditText tieDpdAgexx;
    private TextInputEditText tieSchoolNm;
    private TextInputEditText tieSchlAddx;
    private TextInputEditText tieSchlProv;
    private TextInputEditText tieSchlTown;
    private TextInputEditText tieCompName;
    private LinearLayout linearStudent;
    private LinearLayout linearEmployd;
    private RecyclerView recyclerView;

    private static AutoCompleteTextView actRelationx;
    private AutoCompleteTextView actSchoolType;
    private AutoCompleteTextView actSchoolLvl;
    private AutoCompleteTextView actEmploymentType;
    private static TextView actRelationPosition;

    private RadioGroup rgDpdEmployd;
    private RadioGroup rgDpdStudent;
    private MaterialButton btnAddDependent;
    private CheckBox cbScholarxx;
    private CheckBox cbDependent;
    private CheckBox cbHouseHold;
    private CheckBox cbIsMarried;
    private MaterialButton btnPrev;
    private MaterialButton btnNext;

    private int mRelationPosition = -1;
    private int mEducLvlPosition = 0;
    public static Fragment_Dependent newInstance() {
        return new Fragment_Dependent();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_dependent, container, false);
        setupWidgets(v);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(VMDependent.class);
        mViewModel.setTransNox(Activity_CreditApplication.getInstance().getTransNox());
        mViewModel.getCreditApplicationInfo().observe(getViewLifecycleOwner(), eCreditApplicantInfo -> mViewModel.setCreditApplicantInfo(eCreditApplicantInfo.getDetlInfo()));
        mViewModel.getSpnRelationx().observe(getViewLifecycleOwner(), stringArrayAdapter -> actRelationx.setAdapter(stringArrayAdapter));
        mViewModel.getSchoolType().observe(getViewLifecycleOwner(), stringArrayAdapter -> actSchoolType.setAdapter(stringArrayAdapter));
        mViewModel.getEducLevel().observe(getViewLifecycleOwner(), stringArrayAdapter -> actSchoolLvl.setAdapter(stringArrayAdapter));
        mViewModel.getSpnEmploymentType().observe(getViewLifecycleOwner(), stringArrayAdapter -> actEmploymentType.setAdapter(stringArrayAdapter));
        mViewModel.setLinearStudent().observe(getViewLifecycleOwner(), integer -> linearStudent.setVisibility(integer));
        mViewModel.setLinearEmployed().observe(getViewLifecycleOwner(), integer -> linearEmployd.setVisibility(integer));
        // TODO: Use the ViewModel
        mViewModel.getRelationX().observe(getViewLifecycleOwner(), s -> {
            actRelationx.setSelection(Integer.parseInt(s));
            mRelationPosition = Integer.parseInt(s);
            Log.e("Employee ", s);
        });
        mViewModel.getSchoolTypeX().observe(getViewLifecycleOwner(), s -> {
            actSchoolType.setSelection(Integer.parseInt(s));
            //mRelationPosition = Integer.parseInt(s);
            Log.e("Employee ", s);
        });
        mViewModel.getSchoolLvlX().observe(getViewLifecycleOwner(), s -> {
            actSchoolLvl.setSelection(Integer.parseInt(s));
            mEducLvlPosition = Integer.parseInt(s);
            Log.e("Employee ", s);
        });
        //RECYCLER VIEW
        mViewModel.getAllDependent().observe(getViewLifecycleOwner(), dependentListUpdateObserver);

        //RadioGroup
        rgDpdStudent.setOnCheckedChangeListener(new OnDependencyStatusSelectionListener(rgDpdStudent,mViewModel));
        rgDpdEmployd.setOnCheckedChangeListener(new OnDependencyStatusSelectionListener(rgDpdEmployd,mViewModel));

        //Checkbox

        cbDependent.setOnCheckedChangeListener(new OnDependencySelectionListener(cbDependent));
        cbHouseHold.setOnCheckedChangeListener(new OnDependencySelectionListener(cbHouseHold));
        cbIsMarried.setOnCheckedChangeListener(new OnDependencySelectionListener(cbIsMarried));
        cbScholarxx.setOnCheckedChangeListener(new OnDependencySelectionListener(cbScholarxx));

        //Spinner Auto complete textview
        actRelationx.setOnItemClickListener(new Fragment_Dependent.SpinnerSelectionListener(actRelationx, mViewModel));
        actSchoolType.setOnItemClickListener(new Fragment_Dependent.SpinnerSelectionListener(actSchoolType, mViewModel));
        actSchoolLvl.setOnItemClickListener(new Fragment_Dependent.SpinnerSelectionListener(actSchoolLvl, mViewModel));
        actEmploymentType.setOnItemClickListener(new Fragment_Dependent.SpinnerSelectionListener(actEmploymentType, mViewModel));

        btnAddDependent.setOnClickListener(v -> addDependent());
        btnNext.setOnClickListener(v -> {
            mViewModel.SubmitDependentInfo(Fragment_Dependent.this);
        });
    }

    private void setupWidgets(View view){
        tieFullname = view.findViewById(R.id.tie_cap_dpdFullname);
        tieDpdAgexx = view.findViewById(R.id.tie_cap_dpdAge);
        tieCompName = view.findViewById(R.id.tie_cap_dpdCompanyName);
        tieSchoolNm = view.findViewById(R.id.tie_cap_dpdSchoolName);
        tieSchlAddx = view.findViewById(R.id.tie_cap_dpdSchoolAddress);
        tieSchlProv = view.findViewById(R.id.tie_cap_dpdSchoolProv);
        tieSchlTown = view.findViewById(R.id.tie_cap_dpdSchoolTown);

        actRelationx = view.findViewById(R.id.spinner_cap_dpdRelation);
        rgDpdStudent = v.findViewById(R.id.rg_cap_dpdStudent);
        rgDpdEmployd = v.findViewById(R.id.rg_cap_dpdEmployed);
        actSchoolType = view.findViewById(R.id.spn_cap_dpdSchoolType);
        actSchoolLvl = view.findViewById(R.id.spinner_cap_educLevel);
        actEmploymentType = view.findViewById(R.id.spn_cap_dpdEmployedType);

        linearEmployd = view.findViewById(R.id.linearEmployd);
        linearStudent = view.findViewById(R.id.linearStudent);

        cbDependent = v.findViewById(R.id.cb_cap_Dependent);
        cbHouseHold = v.findViewById(R.id.cb_cap_HouseHold);
        cbIsMarried = v.findViewById(R.id.cb_cap_Married);
        cbScholarxx = v.findViewById(R.id.cb_cap_dpdScholar);
        recyclerView = view.findViewById(R.id.recyclerview_dependencies);

        btnAddDependent = view.findViewById(R.id.btn_dpd_add);
        btnNext = view.findViewById(R.id.btn_creditAppNext);

    }
    Observer<List<DependentsInfoModel>> dependentListUpdateObserver = new Observer<List<DependentsInfoModel>>() {
        @Override
        public void onChanged(List<DependentsInfoModel> userArrayList) {
            adapter = new DependentAdapter(userArrayList);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(adapter);
        }
    };

    @Override
    public void onSaveSuccessResult(String args) {

        Activity_CreditApplication.getInstance().moveToPageNumber(14);
    }

    @Override
    public void onFailedResult(String message) {

    }

    @Override
    public void onSuccess(String message) {
        Log.e(TAG, message);
        clearInputField();


    }

    @Override
    public void onFailed(String message) {
        Log.e(TAG, message);
        GToast.CreateMessage(getActivity(), message, GToast.ERROR).show();
    }

    class OnDependencyStatusSelectionListener implements RadioGroup.OnCheckedChangeListener{

        View rbView;
        VMDependent vm;

        OnDependencyStatusSelectionListener(View view, VMDependent vmDependent){
            this.rbView = view;
            this.vm = vmDependent;
        }

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(rbView.getId() == R.id.rg_cap_dpdStudent){
                if(checkedId == R.id.rb_cap_dpdStudentYes) {
                    IsStudentx = "1";
                }
                else if(checkedId == R.id.rb_cap_dpdStudentNo) {
                    IsStudentx = "0";
                }
                vm.setSpnStudentStatus(IsStudentx);
            }
            if(rbView.getId() == R.id.rg_cap_dpdEmployed){
                if(checkedId == R.id.rb_cap_dpdEmployedYes) {
                    IsEmployed = "1";
                }
                if(checkedId == R.id.rb_cap_dpdEmployedNo) {
                    IsEmployed = "0";
                }

                vm.setSpnEmpSector(IsEmployed);
            }

        }
    }
    class OnDependencySelectionListener implements CheckBox.OnCheckedChangeListener{

        View cbView;

        OnDependencySelectionListener(View view){
            this.cbView = view;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(cbView.getId() == R.id.cb_cap_Dependent){
                if(buttonView.isChecked()) {
                    Dependentx = "1";
                } else {
                    Dependentx = "0";
                }
            } else if(cbView.getId() == R.id.cb_cap_HouseHold){
                if(buttonView.isChecked()){
                    HouseHoldx = "1";
                } else {
                    HouseHoldx = "0";
                }
            } else if(cbView.getId() == R.id.cb_cap_Married){
                if(buttonView.isChecked()){
                    IsMarriedx = "1";
                } else {
                    IsMarriedx = "0";
                }
            }
            else if(cbView.getId() == R.id.cb_cap_dpdScholar){
                if(buttonView.isChecked()){
                    IsScholarx = "1";
                } else {
                    IsScholarx = "2";
                }
            }
        }
    }
//



    private class SpinnerSelectionListener implements AdapterView.OnItemClickListener{
        private final VMDependent vm;
        View spinView;
        SpinnerSelectionListener(View view, VMDependent viewModel){
            this.vm = viewModel;
            this.spinView = view;
        }

        @Override
        public void onItemClick(@NotNull AdapterView<?> adapterView, @NotNull View view, int i, long l) {
            if(spinView.getId() == R.id.spinner_cap_dpdRelation){
                String type = "";
                switch (i){
                    case 0:
                        type = "0";
                        break;
                    case 1:
                        type = "1";
                        break;
                    case 2:
                        type = "2";
                        break;
                    case 3:
                        type = "3";
                        break;

                }
                mRelationPosition = i;
                vm.setSpnRelationx(type);
            }
            if(spinView.getId() == R.id.spinner_cap_educLevel){
                String type = "";
                switch (i){
                    case 0:
                        type = "0";
                        break;
                    case 1:
                        type = "1";
                        break;
                    case 2:
                        type = "2";
                        break;
                    case 3:
                        type = "3";
                        break;
                    case 4:
                        type = "4";
                        break;

                }

                mEducLvlPosition = i;
                vm.setEducLevel(type);

            }
            if(spinView.getId() == R.id.spn_cap_dpdSchoolType){
                String type = "";
                switch (i){
                    case 0:
                        type = "0";
                        break;
                    case 1:
                        type = "1";
                        break;

                }

                IsPrivatex = type;
                vm.setSpnSchoolType(type);
            }
            if(spinView.getId() == R.id.spn_cap_dpdEmployedType){
                String type = "";
                switch (i){
                    case 0:
                        dpdEmpType = "0";
                        type = "0";
                        break;
                    case 1:
                        dpdEmpType = "1";
                        type = "1";
                        break;
                    case 2:
                        dpdEmpType = "2";
                        type = "2";
                        break;
                }
               // Employment
                Employment = type;
                vm.setSpnSchoolType(type);
            }
        }
    }


    @SuppressLint("NewApi")
    private void addDependent(){
        try {
            String dpdName = tieFullname.getText().toString();
            String dpdSchoolName = tieSchoolNm.getText().toString();
            String dpdSchoolAddress = tieSchlAddx.getText().toString();
            String dpdSchoolProv = tieSchlProv.getText().toString();
            String dpdSchoolTown = tieSchlTown.getText().toString();
            String dpdCompanyN = tieCompName.getText().toString();
            String dpdAge = tieDpdAgexx.getText().toString();


            DependentsInfoModel infoModel = new DependentsInfoModel(dpdName,
                    String.valueOf(mRelationPosition),
                    dpdAge,
                    IsStudentx,
                    IsPrivatex,
                    String.valueOf(mEducLvlPosition),
                    IsScholarx,
                    dpdSchoolName,
                    dpdSchoolAddress,
                    dpdSchoolProv,
                    dpdSchoolTown,
                    IsEmployed,
                    Employment,
                    dpdCompanyN,
                    Dependentx,
                    HouseHoldx,
                    IsMarriedx);
            mViewModel.AddDependent(infoModel, Fragment_Dependent.this);
        }catch (NullPointerException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();

    }

    private void clearInputField(){
        tieFullname.setText("");
        tieDpdAgexx.setText("");
        tieCompName.setText("");
        tieSchoolNm.setText("");
        tieSchlAddx.setText("");
        tieSchlTown.setText("");
        actRelationx.setText("");
        actSchoolType.setText("");
        actSchoolLvl.setText("");
        actEmploymentType.setText("");
        rgDpdEmployd.clearCheck();
        rgDpdStudent.clearCheck();
        cbDependent.setChecked(false);
        cbHouseHold.setChecked(false);
        cbIsMarried.setChecked(false);
        cbScholarxx.setChecked(false);
        adapter.notifyDataSetChanged();
    }
}