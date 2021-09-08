/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.creditApp
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.onlinecreditapplication.Fragment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.guanzongroup.onlinecreditapplication.Activity.Activity_CreditApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ResidenceInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMResidenceInfo;

import java.util.Objects;

public class Fragment_ResidenceInfo extends Fragment implements ViewModelCallBack {

    private VMResidenceInfo mViewModel;
    private ResidenceInfoModel infoModel;
    private TextInputEditText txtLandMark,
            txtHouseNox,
            txtAddress1,
            txtAddress2,
            txtRelationship,
            txtLgnthStay,
            txtMonthlyExp,
            txtPLandMark,
            txtPHouseNox,
            txtPAddress1,
            txtPAddress2;
    private AutoCompleteTextView txtBarangay,
            txtMunicipality,
            txtProvince,
            txtPBarangay,
            txtPMunicipl,
            txtPProvince;
    private CheckBox cbOneAddress;
    private AutoCompleteTextView spnLgnthStay,
            spnHouseHold,
            spnHouseType;
    private String spnLgnthStayPosition = "-1",
            spnHouseHoldPosition = "-1",
            spnHouseTypePosition = "-1";
    private TextInputLayout tilRelationship;
    private LinearLayout lnOtherInfo, lnPermaAddx;
    private Button btnNext;
    private Button btnPrvs;

    private String TransNox = "";

    public static Fragment_ResidenceInfo newInstance() {
        return new Fragment_ResidenceInfo();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_residence_info, container, false);
        infoModel = new ResidenceInfoModel();
        TransNox = Activity_CreditApplication.getInstance().getTransNox();
        initWidgets(view);
        return view;
    }

    private void initWidgets(View v){
        cbOneAddress = v.findViewById(R.id.cb_oneAddress);
        txtLandMark = v.findViewById(R.id.txt_landmark);
        txtHouseNox = v.findViewById(R.id.txt_houseNox);
        txtAddress1 = v.findViewById(R.id.txt_address);
        txtAddress2 = v.findViewById(R.id.txt_address2);
        txtBarangay = v.findViewById(R.id.txt_barangay);
        txtMunicipality = v.findViewById(R.id.txt_town);
        txtProvince = v.findViewById(R.id.txt_province);
        txtRelationship = v.findViewById(R.id.txt_relationship);
        txtLgnthStay = v.findViewById(R.id.txt_lenghtStay);
        txtMonthlyExp = v.findViewById(R.id.txt_monthlyExp);
        txtPLandMark = v.findViewById(R.id.txt_perm_landmark);
        txtPHouseNox = v.findViewById(R.id.txt_perm_houseNox);
        txtPAddress1 = v.findViewById(R.id.txt_perm_address);
        txtPAddress2 = v.findViewById(R.id.txt_perm_address2);
        txtPBarangay = v.findViewById(R.id.txt_perm_barangay);
        txtPMunicipl = v.findViewById(R.id.txt_perm_town);
        txtPProvince = v.findViewById(R.id.txt_perm_province);

        spnLgnthStay = v.findViewById(R.id.spn_lenghtStay);
        spnHouseHold = v.findViewById(R.id.spn_houseHold);
        spnHouseType = v.findViewById(R.id.spn_houseType);
        RadioGroup rgOwnsership = v.findViewById(R.id.rg_ownership);
        RadioGroup rgGarage = v.findViewById(R.id.rg_garage);
        tilRelationship = v.findViewById(R.id.til_relationship);
        lnOtherInfo = v.findViewById(R.id.linear_otherInfo);
        lnPermaAddx = v.findViewById(R.id.linear_permanentAdd);

        cbOneAddress.setOnCheckedChangeListener(new OnAddressSetListener());
        rgOwnsership.setOnCheckedChangeListener(new OnHouseOwnershipSelectListener());
        rgGarage.setOnCheckedChangeListener(new OnHouseOwnershipSelectListener());
        btnNext = v.findViewById(R.id.btn_creditAppNext);
        btnPrvs = v.findViewById(R.id.btn_creditAppPrvs);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMResidenceInfo.class);
        mViewModel.setTransNox(TransNox);
        mViewModel.getCreditApplicationInfo().observe(getViewLifecycleOwner(), eCreditApplicantInfo -> mViewModel.setGOCasDetailInfo(eCreditApplicantInfo));

        mViewModel.getProvinceNameList().observe(getViewLifecycleOwner(), strings -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
            txtProvince.setAdapter(adapter);
            txtPProvince.setAdapter(adapter);
            txtProvince.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
            txtPProvince.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        });

        txtProvince.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getProvinceInfoList().observe(getViewLifecycleOwner(), eProvinceInfos -> {
            for(int x = 0; x < eProvinceInfos.size(); x++){
                if(txtProvince.getText().toString().equalsIgnoreCase(eProvinceInfos.get(x).getProvName())){
                    mViewModel.setProvinceID(eProvinceInfos.get(x).getProvIDxx());
                    break;
                }
            }
            mViewModel.getTownNameList().observe(getViewLifecycleOwner(), strings -> {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
                txtMunicipality.setAdapter(adapter);
                txtMunicipality.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
            });
        }));

        txtMunicipality.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getTownInfoList().observe(getViewLifecycleOwner(), eTownInfos -> {
            for(int x = 0; x < eTownInfos.size(); x++){
                if(txtMunicipality.getText().toString().equalsIgnoreCase(eTownInfos.get(x).getTownName())){
                    mViewModel.setTownID(eTownInfos.get(x).getTownIDxx());
                    break;
                }
            }

            mViewModel.getBarangayNameList().observe(getViewLifecycleOwner(), strings -> {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
                txtBarangay.setAdapter(adapter);
                txtBarangay.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
            });
        }));

        txtBarangay.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getBarangayInfoList().observe(getViewLifecycleOwner(), eBarangayInfos -> {
            for(int x = 0; x < eBarangayInfos.size(); x++){
                if(txtBarangay.getText().toString().equalsIgnoreCase(eBarangayInfos.get(x).getBrgyName())){
                    mViewModel.setBarangayID(eBarangayInfos.get(x).getBrgyIDxx());
                    break;
                }
            }
        }));

        txtPProvince.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getProvinceInfoList().observe(getViewLifecycleOwner(), eProvinceInfos -> {
            for(int x = 0; x < eProvinceInfos.size(); x++){
                if(txtPProvince.getText().toString().equalsIgnoreCase(eProvinceInfos.get(x).getProvName())){
                    mViewModel.setPermanentProvinceID(eProvinceInfos.get(x).getProvIDxx());
                    break;
                }
            }

            mViewModel.getPermanentTownNameList().observe(getViewLifecycleOwner(), strings -> {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
                txtPMunicipl.setAdapter(adapter);
                txtPMunicipl.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
            });
        }));

        txtPMunicipl.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getPermanentTownInfoList().observe(getViewLifecycleOwner(), eTownInfos -> {
            for(int x = 0; x < eTownInfos.size(); x++){
                if(txtPMunicipl.getText().toString().equalsIgnoreCase(eTownInfos.get(x).getTownName())){
                    mViewModel.setPermanentTownID(eTownInfos.get(x).getTownIDxx());
                    break;
                }
            }

            mViewModel.getPermanentBarangayNameList().observe(getViewLifecycleOwner(), strings -> {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
                txtPBarangay.setAdapter(adapter);
                txtPBarangay.setDropDownBackgroundResource(R.drawable.bg_gradient_light);

            });
        }));

        txtPBarangay.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getPermanentBarangayInfoList().observe(getViewLifecycleOwner(), eBarangayInfos -> {
            for(int x = 0; x < eBarangayInfos.size(); x++){
                if(txtPBarangay.getText().toString().equalsIgnoreCase(eBarangayInfos.get(x).getBrgyName())){
                    mViewModel.setPermanentBarangayID(eBarangayInfos.get(x).getBrgyIDxx());
                    break;
                }
            }
        }));

        mViewModel.getHouseHolds().observe(getViewLifecycleOwner(), stringArrayAdapter -> {
            spnHouseHold.setAdapter(stringArrayAdapter);
            spnHouseHold.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        });

        mViewModel.getHouseType().observe(getViewLifecycleOwner(), stringArrayAdapter -> {
            spnHouseType.setAdapter(stringArrayAdapter);
            spnHouseType.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        });

        mViewModel.getLenghtOfStay().observe(getViewLifecycleOwner(), stringArrayAdapter -> {
            spnLgnthStay.setAdapter(stringArrayAdapter);
            spnLgnthStay.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        });

        spnHouseHold.setOnItemClickListener(new OnItemClickListener(spnHouseHold));
        spnHouseType.setOnItemClickListener(new OnItemClickListener(spnHouseType));
        spnLgnthStay.setOnItemClickListener(new OnItemClickListener(spnLgnthStay));
        btnNext.setOnClickListener(view -> SaveResidenceInfo());
        btnPrvs.setOnClickListener(view -> Activity_CreditApplication.getInstance().moveToPageNumber(0));
    }

    private void SaveResidenceInfo(){
        infoModel.setOneAddress(cbOneAddress.isChecked());
        infoModel.setLandMark(Objects.requireNonNull(txtLandMark.getText()).toString());
        infoModel.setHouseNox(Objects.requireNonNull(txtHouseNox.getText()).toString());
        infoModel.setAddress1(Objects.requireNonNull(txtAddress1.getText()).toString());
        infoModel.setAddress2(Objects.requireNonNull(txtAddress2.getText()).toString());
        infoModel.setProvinceNm(txtProvince.getText().toString());
        infoModel.setMunicipalNm(txtMunicipality.getText().toString());
        infoModel.setBarangayName(txtBarangay.getText().toString());
        infoModel.setHouseHold(spnHouseHoldPosition);
        infoModel.setHouseType(spnHouseTypePosition);
        infoModel.setOwnerRelation(Objects.requireNonNull(txtRelationship.getText()).toString());
        infoModel.setLenghtOfStay(Objects.requireNonNull(txtLgnthStay.getText()).toString());
        infoModel.setMonthlyExpenses(Objects.requireNonNull(txtMonthlyExp.getText()).toString());
        infoModel.setIsYear(spnLgnthStayPosition);
        infoModel.setPermanentLandMark(Objects.requireNonNull(txtPLandMark.getText()).toString());
        infoModel.setPermanentHouseNo(Objects.requireNonNull(txtPHouseNox.getText()).toString());
        infoModel.setPermanentAddress1(Objects.requireNonNull(txtPAddress1.getText()).toString());
        infoModel.setPermanentAddress2(Objects.requireNonNull(txtPAddress2.getText()).toString());
        infoModel.setPermanentProvinceNm(txtPProvince.getText().toString());
        infoModel.setPermanentMunicipalNm(txtPMunicipl.getText().toString());
        infoModel.setPermanentBarangayName(txtPBarangay.getText().toString());
        mViewModel.SaveResidenceInfo(infoModel, Fragment_ResidenceInfo.this);
    }

    @Override
    public void onSaveSuccessResult(String args) {
        Activity_CreditApplication.getInstance().moveToPageNumber(2);
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
            if (spnHouseHold.equals(poView)) {
                spnHouseHoldPosition = String.valueOf(i);
            }
            if (spnHouseType.equals(poView)) {
                spnHouseTypePosition = String.valueOf(i);
            }
            if (spnLgnthStay.equals(poView)) {
                spnLgnthStayPosition = String.valueOf(i);
            }

        }
    }
    private class OnHouseOwnershipSelectListener implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            if(radioGroup.getId() == R.id.rg_ownership){
                if(i == R.id.rb_owned){
                    lnOtherInfo.setVisibility(View.GONE);
                    infoModel.setHouseOwn("0");
                }
                if(i == R.id.rb_rent){
                    lnOtherInfo.setVisibility(View.VISIBLE);
                    tilRelationship.setVisibility(View.GONE);
                    infoModel.setHouseOwn("1");
                }
                if(i == R.id.rb_careTaker){
                    lnOtherInfo.setVisibility(View.VISIBLE);
                    tilRelationship.setVisibility(View.VISIBLE);
                    infoModel.setHouseOwn("2");
                }
            } else {
                if(i == R.id.rb_yes){
                    infoModel.setHasGarage("1");
                }
                if(i == R.id.rb_no){
                    infoModel.setHasGarage("0");
                }
            }
        }
    }

    private class OnAddressSetListener implements CompoundButton.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if(b){
                lnPermaAddx.setVisibility(View.GONE);
            } else {
                lnPermaAddx.setVisibility(View.VISIBLE);
            }
        }
    }
}