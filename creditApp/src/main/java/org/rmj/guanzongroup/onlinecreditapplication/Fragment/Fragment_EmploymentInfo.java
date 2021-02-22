package org.rmj.guanzongroup.onlinecreditapplication.Fragment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.guanzongroup.onlinecreditapplication.Activity.Activity_CreditApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Model.EmploymentInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMEmploymentInfo;

import java.util.Objects;

public class Fragment_EmploymentInfo extends Fragment implements ViewModelCallBack {
    private static final String TAG = Fragment_EmploymentInfo.class.getSimpleName();
    private VMEmploymentInfo mViewModel;
    private EmploymentInfoModel infoModel;
//    private Spinner spnEmpSts, spnServce;
    private AutoCompleteTextView spnCmpLvl,
            spnEmpLvl,
            spnBusNtr,
            spnEmpSts,
            spnServce;
    private String  spnCmpLvlPosition = "-1",
            spnEmpLvlPosition = "-1",
            spnBusNtrPosition = "-1",
            spnServcePosition = "-1",
            spnEmpStsPosition = "-1";
    private AutoCompleteTextView txtCntryx,
            txtProvNm,
            txtTownNm,
            txtJobNme;
    private TextInputLayout tilCntryx,
            tilCompNm;
    private TextInputEditText txtCompNm,
            txtCompAd,
            txtSpcfJb,
            txtLngthS,
            txtEsSlry,
            txtCompCn;
    private LinearLayout lnGovInfo,
            lnEmpInfo;
    private Button btnNext;

    public static Fragment_EmploymentInfo newInstance() {
        return new Fragment_EmploymentInfo();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employment_info, container, false);
        infoModel = new EmploymentInfoModel();
        infoModel.setEmploymentSector("1");
        initWidgets(view);

        return view;
    }

    private void initWidgets(View v){
        RadioGroup rgSectorx = v.findViewById(R.id.rg_sector);
        RadioGroup rgUniform = v.findViewById(R.id.rg_uniformPersonel);
        RadioGroup rgMiltary = v.findViewById(R.id.rg_militaryPersonal);
        spnCmpLvl = v.findViewById(R.id.spn_employmentLevel);
        spnEmpLvl = v.findViewById(R.id.spn_employeeLevel);
        spnBusNtr = v.findViewById(R.id.spn_businessNature);
        spnEmpSts = v.findViewById(R.id.spn_employmentStatus);
        spnServce = v.findViewById(R.id.spn_lengthService);
        txtCntryx = v.findViewById(R.id.txt_countryNme);
        txtProvNm = v.findViewById(R.id.txt_province);
        txtTownNm = v.findViewById(R.id.txt_town);
        txtJobNme = v.findViewById(R.id.txt_jobPosition);
        tilCntryx = v.findViewById(R.id.til_countryNme);
        tilCompNm = v.findViewById(R.id.til_companyNme);
        txtCompNm = v.findViewById(R.id.txt_companyNme);
        txtCompAd = v.findViewById(R.id.txt_companyAdd);
        txtSpcfJb = v.findViewById(R.id.txt_specificJob);
        txtLngthS = v.findViewById(R.id.txt_lenghtService);
        txtEsSlry = v.findViewById(R.id.txt_monthlySalary);
        txtCompCn = v.findViewById(R.id.txt_companyContact);
        lnGovInfo = v.findViewById(R.id.linear_governmentSector);
        lnEmpInfo = v.findViewById(R.id.linear_employmentInfo);
        Button btnPrvs = v.findViewById(R.id.btn_creditAppPrvs);
        btnNext = v.findViewById(R.id.btn_creditAppNext);

        rgSectorx.setOnCheckedChangeListener(new OnRadioButtonSelectListener());
        rgUniform.setOnCheckedChangeListener(new OnRadioButtonSelectListener());
        rgMiltary.setOnCheckedChangeListener(new OnRadioButtonSelectListener());
//        spnEmpSts.setOnItemSelectedListener(new OnJobStatusSelectedListener());

        btnPrvs.setOnClickListener(view -> Activity_CreditApplication.getInstance().moveToPageNumber(2));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMEmploymentInfo.class);
        mViewModel.setTransNox(Activity_CreditApplication.getInstance().getTransNox());
        mViewModel.getCreditApplicationInfo().observe(getViewLifecycleOwner(), eCreditApplicantInfo -> mViewModel.setCreditApplicantInfo(eCreditApplicantInfo));
        mViewModel.getCompanyLevelList().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnCmpLvl.setAdapter(stringArrayAdapter));
        mViewModel.getPsCmpLvl().observe(getViewLifecycleOwner(), s -> {
            spnCmpLvl.setSelection(Integer.parseInt(s));
            Log.e("company ", s);
        });
        mViewModel.getEmployeeLevelList().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnEmpLvl.setAdapter(stringArrayAdapter));
        mViewModel.getPsEmpLvl().observe(getViewLifecycleOwner(), s -> {
                spnEmpLvl.setSelection(Integer.parseInt(s));
                Log.e("Employee ", s);
        });

        mViewModel.getBusinessNature().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnBusNtr.setAdapter(stringArrayAdapter));
        mViewModel.getPsBsnssLvl().observe(getViewLifecycleOwner(), s -> {
                spnBusNtr.setSelection(Integer.parseInt(s));
                Log.e("Business ", s);
                });

        mViewModel.getCountryNameList().observe(getViewLifecycleOwner(), strings -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
            txtCntryx.setAdapter(adapter);
        });

        txtCntryx.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getCountryInfoList().observe(getViewLifecycleOwner(), countryInfos -> {
            for(int x = 0; x < countryInfos.size(); x++){
                if(txtCntryx.getText().toString().equalsIgnoreCase(countryInfos.get(x).getCntryNme())){
                    mViewModel.setCountry(countryInfos.get(x).getCntryCde());
                    break;
                }
            }
        }));
//        mViewModel.getCountryCD().observe(getViewLifecycleOwner(), s -> {
//            txtCntryx.setSelection(Integer.parseInt(s));
//            Log.e("company ", s);
//        });
        mViewModel.getProvinceName().observe(getViewLifecycleOwner(), strings -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
            txtProvNm.setAdapter(adapter);
        });
        txtProvNm.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getProvinceInfo().observe(getViewLifecycleOwner(), eProvinceInfos -> {
            for(int x = 0; x < eProvinceInfos.size(); x++){
                if(txtProvNm.getText().toString().equalsIgnoreCase(eProvinceInfos.get(x).getProvName())){
                    mViewModel.setProvinceID(eProvinceInfos.get(x).getProvIDxx());
                    break;
                }
            }

            mViewModel.getTownNameList().observe(getViewLifecycleOwner(), strings -> {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
                txtTownNm.setAdapter(adapter);
            });
        }));

        txtTownNm.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getTownInfoList().observe(getViewLifecycleOwner(), eTownInfos -> {
            for(int x = 0; x < eTownInfos.size(); x++){
                if(txtTownNm.getText().toString().equalsIgnoreCase(eTownInfos.get(x).getTownName())){
                    mViewModel.setTownID(eTownInfos.get(x).getTownIDxx());
                    break;
                }
            }
        }));
        mViewModel.getJobTitleNameList().observe(getViewLifecycleOwner(), strings -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
            txtJobNme.setAdapter(adapter);
        });

        txtJobNme.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getJobTitleInfoList().observe(getViewLifecycleOwner(), occupationInfos -> {
            for(int x = 0; x < occupationInfos.size(); x++){
                if(txtJobNme.getText().toString().equalsIgnoreCase(occupationInfos.get(x).getOccptnNm())){
                    mViewModel.setJobTitle(occupationInfos.get(x).getOccptnID());
                    break;
                }
            }
        }));

        mViewModel.getEmploymentStatus().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnEmpSts.setAdapter(stringArrayAdapter));
        mViewModel.getLengthOfService().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnServce.setAdapter(stringArrayAdapter));
        mViewModel.getPsService().observe(getViewLifecycleOwner(), s -> {
            spnServce.setSelection(Integer.parseInt(s));
            Log.e("Employee ", s);
        });

        spnCmpLvl.setOnItemClickListener(new OnItemClickListener(spnCmpLvl));
        spnEmpLvl.setOnItemClickListener(new OnItemClickListener(spnEmpLvl));
        spnBusNtr.setOnItemClickListener(new OnItemClickListener(spnBusNtr));
        spnServce.setOnItemClickListener(new OnItemClickListener(spnServce));
        spnEmpSts.setOnItemClickListener(new OnItemClickListener(spnEmpSts));
        btnNext.setOnClickListener(view -> {
            infoModel.setCompanyName(Objects.requireNonNull(txtCompNm.getText()).toString());
            infoModel.setCompanyAddress(Objects.requireNonNull(txtCompAd.getText()).toString());
            infoModel.setSpecificJob(Objects.requireNonNull(txtSpcfJb.getText()).toString());
            infoModel.setLengthOfService(Objects.requireNonNull(txtLngthS.getText()).toString());
            infoModel.setIsYear(spnServcePosition);
            infoModel.setsMonthlyIncome(Objects.requireNonNull(txtEsSlry.getText()).toString());
            infoModel.setContact(Objects.requireNonNull(txtCompCn.getText()).toString());
            mViewModel.SaveEmploymentInfo(infoModel, Fragment_EmploymentInfo.this);
        });
    }

    @Override
    public void onSaveSuccessResult(String args) {
        Activity_CreditApplication.getInstance().moveToPageNumber(4);
    }

    @Override
    public void onFailedResult(String message) {
        GToast.CreateMessage(getActivity(), message, GToast.ERROR).show();
    }
    class OnItemClickListener implements AdapterView.OnItemClickListener {
        AutoCompleteTextView poView;

        public OnItemClickListener(AutoCompleteTextView view) {
            this.poView = view;
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if (spnCmpLvl.equals(poView)) {
                spnCmpLvlPosition = String.valueOf(i);
                mViewModel.setPsCmpLvl(String.valueOf(i));
            }
            if (spnEmpLvl.equals(poView)) {
                spnEmpLvlPosition = String.valueOf(i);
                mViewModel.setPsEmpLvl(String.valueOf(i));
            }
            if (spnBusNtr.equals(poView)) {
                spnBusNtrPosition = String.valueOf(i);
                mViewModel.setPsBsnssLvl(String.valueOf(i));
            }
            if (spnServce.equals(poView)) {
                spnServcePosition = String.valueOf(i);
                mViewModel.setPsService(String.valueOf(i));
            }
            if (spnEmpSts.equals(poView)) {
                if (adapterView.getItemAtPosition(i).toString().equalsIgnoreCase("Tap here to select.")) {
                    mViewModel.setEmploymentStatus("");
                } else if (adapterView.getItemAtPosition(i).toString().equalsIgnoreCase("Regular")) {
                    mViewModel.setEmploymentStatus("R");
                } else if (adapterView.getItemAtPosition(i).toString().equalsIgnoreCase("Probationary")) {
                    mViewModel.setEmploymentStatus("P");
                } else if (adapterView.getItemAtPosition(i).toString().equalsIgnoreCase("Contractual")) {
                    mViewModel.setEmploymentStatus("C");
                } else if (adapterView.getItemAtPosition(i).toString().equalsIgnoreCase("Seasonal")) {
                    mViewModel.setEmploymentStatus("S");
                }
//                GToast.CreateMessage(getActivity(), adapterView.getItemAtPosition(i).toString(), GToast.INFORMATION).show();
            }
        }
    }

    private class OnRadioButtonSelectListener implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            if(radioGroup.getId() == R.id.rg_sector){
                if (i == R.id.rb_private) {
                    mViewModel.setEmploymentSector("1");
                    lnGovInfo.setVisibility(View.GONE);
                    lnEmpInfo.setVisibility(View.VISIBLE);
                    tilCntryx.setVisibility(View.GONE);
                    tilCompNm.setHint("Company Name");
                    spnBusNtr.setVisibility(View.VISIBLE);
                    mViewModel.getCompanyLevelList().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnCmpLvl.setAdapter(stringArrayAdapter));
                    mViewModel.getEmployeeLevelList().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnEmpLvl.setAdapter(stringArrayAdapter));
                } else if (i == R.id.rb_government) {
                    mViewModel.setEmploymentSector("0");
                    mViewModel.setEmploymentSector("1");
                    lnGovInfo.setVisibility(View.VISIBLE);
                    lnEmpInfo.setVisibility(View.VISIBLE);
                    tilCntryx.setVisibility(View.GONE);
                    tilCompNm.setHint("Government Institution");
                    spnBusNtr.setVisibility(View.GONE);
                    mViewModel.getGovernmentLevelList().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnCmpLvl.setAdapter(stringArrayAdapter));
                    mViewModel.getEmployeeLevelList().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnEmpLvl.setAdapter(stringArrayAdapter));
                } else if (i == R.id.rb_ofw) {
                    mViewModel.setEmploymentSector("2");
                    mViewModel.setEmploymentSector("1");
                    lnGovInfo.setVisibility(View.GONE);
                    lnEmpInfo.setVisibility(View.GONE);
                    tilCntryx.setVisibility(View.VISIBLE);
                    spnBusNtr.setVisibility(View.GONE);
                    mViewModel.getRegionList().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnCmpLvl.setAdapter(stringArrayAdapter));
                    mViewModel.getWorkCategoryList().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnEmpLvl.setAdapter(stringArrayAdapter));
                }
            }
            if(radioGroup.getId() == R.id.rg_uniformPersonel){
                if(i == R.id.rb_uniform_yes){
                    mViewModel.setUniformPersonnel("Y");
                } else if(i == R.id.rb_uniform_no){
                    mViewModel.setUniformPersonnel("N");
                }
            }
            if(radioGroup.getId() == R.id.rg_militaryPersonal){
                if(i == R.id.rb_military_yes){
                    mViewModel.setMilitaryPersonnel("Y");
                } else if(i == R.id.rb_military_no){
                    mViewModel.setMilitaryPersonnel("N");
                }
            }
        }
    }

//    class OnJobStatusSelectedListener implements AdapterView.OnItemSelectedListener{
//
//        @Override
//        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//            if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("Tap here to select.")) {
//                mViewModel.setEmploymentStatus("");
//            } else if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("Regular")) {
//                mViewModel.setEmploymentStatus("R");
//            } else if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("Probationary")) {
//                mViewModel.setEmploymentStatus("P");
//            } else if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("Contractual")) {
//                mViewModel.setEmploymentStatus("C");
//            } else if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("Seasonal")) {
//                mViewModel.setEmploymentStatus("S");
//            }
//            GToast.CreateMessage(getActivity(), parent.getItemAtPosition(position).toString(), GToast.INFORMATION).show();
//        }
//
//        @Override
//        public void onNothingSelected(AdapterView<?> parent) {
//
//        }
//    }
}