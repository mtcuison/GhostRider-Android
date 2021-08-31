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

import android.annotation.SuppressLint;
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
import android.widget.RadioGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.guanzongroup.onlinecreditapplication.Activity.Activity_CreditApplication;
import org.rmj.g3appdriver.etc.OnDateSetListener;
import org.rmj.guanzongroup.onlinecreditapplication.Model.PersonalInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMPersonalInfo;

import java.util.Objects;

public class Fragment_PersonalInfo extends Fragment implements ViewModelCallBack {
    public static final String TAG = Fragment_PersonalInfo.class.getSimpleName();
    private static final String Fragment_PersonalInfo = "org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_PersonalInfo";

    private VMPersonalInfo mViewModel;
    private PersonalInfoModel infoModel;
    private TextInputEditText txtLastNm, txtFrstNm, txtMiddNm, txtSuffixx, txtNickNm, txtBirthDt, txtMothNm, txtMobileNo1, txtMobileNo2, txtMobileNo3, txtMobileYr1, txtMobileYr2, txtMobileYr3, txtTellNox, txtEmailAdd, txtFbAccount, txtViberAccount;
    private TextInputLayout tilMothNm, tilMobileYr1 ,tilMobileYr2 ,tilMobileYr3;
    private AutoCompleteTextView txtProvince, txtTown, txtCitizen;
    private RadioGroup rgGender;
    private AutoCompleteTextView spnCivilStatus, spnMobile1, spnMobile2, spnMobile3;

    private String psMob1NetTp = "-1";
    private String psMob2NetTp = "-1";
    private String psMob3NetTp = "-1";

    private String transnox;

    public static Fragment_PersonalInfo newInstance() {
        return new Fragment_PersonalInfo();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_info, container, false);
        transnox = Activity_CreditApplication.getInstance().getTransNox();
        initWidgets(view);
        return view;
    }

    private void initWidgets(View v){
        txtLastNm = v.findViewById(R.id.txt_lastname);
        txtFrstNm = v.findViewById(R.id.txt_firstname);
        txtMiddNm = v.findViewById(R.id.txt_middname);
        txtSuffixx = v.findViewById(R.id.txt_suffix);
        txtNickNm = v.findViewById(R.id.txt_nickname);
        txtBirthDt = v.findViewById(R.id.txt_birthdate);
        tilMothNm = v.findViewById(R.id.til_motherNme);
        txtMothNm = v.findViewById(R.id.txt_motherNme);
        txtMobileNo1 = v.findViewById(R.id.txt_mobileNo1);
        txtMobileNo2 = v.findViewById(R.id.txt_mobileNo2);
        txtMobileNo3 = v.findViewById(R.id.txt_mobileNo3);
        txtMobileYr1 = v.findViewById(R.id.txt_mobileNo1Year);
        tilMobileYr1 = v.findViewById(R.id.til_mobileNo1Year);
        txtMobileYr2 = v.findViewById(R.id.txt_mobileNo2Year);
        tilMobileYr2 = v.findViewById(R.id.til_mobileNo2Year);
        txtMobileYr3 = v.findViewById(R.id.txt_mobileNo3Year);
        tilMobileYr3 = v.findViewById(R.id.til_mobileNo3Year);

        txtTellNox = v.findViewById(R.id.txt_telephoneNo);
        txtEmailAdd = v.findViewById(R.id.txt_emailAdd);
        txtFbAccount = v.findViewById(R.id.txt_fbAccount);
        txtViberAccount = v.findViewById(R.id.txt_viberAccount);

        txtProvince = v.findViewById(R.id.txt_bpProvince);
        txtTown = v.findViewById(R.id.txt_bpTown);
        txtCitizen = v.findViewById(R.id.txt_citizenship);
        rgGender = v.findViewById(R.id.rg_gender);
        spnCivilStatus = v.findViewById(R.id.spn_civilStatus);
        spnMobile1 = v.findViewById(R.id.spn_mobile1Type);
        spnMobile2 = v.findViewById(R.id.spn_mobile2Type);
        spnMobile3 = v.findViewById(R.id.spn_mobile3Type);
        txtBirthDt.addTextChangedListener(new OnDateSetListener(txtBirthDt));
        MaterialButton btnNext = v.findViewById(R.id.btn_creditAppNext);

        btnNext.setOnClickListener(view -> SavePersonalInfo());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG, transnox);
        mViewModel = new ViewModelProvider(requireActivity()).get(VMPersonalInfo.class);
        mViewModel.setTransNox(transnox);
        mViewModel.getCreditApplicantInfo().observe(getViewLifecycleOwner(), eCreditApplicantInfo -> {
            try{
                mViewModel.setGOCasDetailInfo(eCreditApplicantInfo);
            } catch (Exception e){
                e.printStackTrace();
            }
        });
        mViewModel.getPersonalInfoModel().observe(getViewLifecycleOwner(), personalInfoModel -> infoModel = personalInfoModel);

        mViewModel.getProvinceNameList().observe(getViewLifecycleOwner(), strings -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
            txtProvince.setAdapter(adapter);
            txtProvince.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        });

        txtProvince.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getProvinceInfoList().observe(getViewLifecycleOwner(), provinceInfos -> {
            for(int x = 0; x < provinceInfos.size(); x++){
                if(txtProvince.getText().toString().equalsIgnoreCase(provinceInfos.get(x).getProvName())){
                    mViewModel.setProvID(provinceInfos.get(x).getProvIDxx());
                    break;
                }
            }

            mViewModel.getAllTownNames().observe(getViewLifecycleOwner(), strings -> {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
                txtTown.setAdapter(adapter);
                txtTown.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
            });
        }));

        txtTown.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getTownInfoList().observe(getViewLifecycleOwner(), townInfoList -> {
            for(int x = 0; x < townInfoList.size(); x++){
                if(txtTown.getText().toString().equalsIgnoreCase(townInfoList.get(x).getTownName())){
                    mViewModel.setTownID(townInfoList.get(x).getTownIDxx());
                    break;
                }
            }
        }));

        rgGender.setOnCheckedChangeListener((radioGroup, i) -> {
            if(i == R.id.rb_male){
                mViewModel.setGender("0");
            }
            if(i == R.id.rb_female){
                mViewModel.setGender("1");
            }
            if(i == R.id.rb_lgbt){
                mViewModel.setGender("2");
            }
        });

        mViewModel.getCivilStatus().observe(getViewLifecycleOwner(), stringArrayAdapter -> {
            spnCivilStatus.setAdapter(stringArrayAdapter);
            spnCivilStatus.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        });

        mViewModel.setMotherMaidenNameVisibility().observe(getViewLifecycleOwner(), integer -> tilMothNm.setVisibility(integer));

        spnCivilStatus.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.setCvlStats(String.valueOf(i)));

        mViewModel.getAllCountryCitizenNames().observe(getViewLifecycleOwner(), strings -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
            txtCitizen.setAdapter(adapter);
            txtCitizen.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        });

        txtCitizen.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getCountryInfoList().observe(getViewLifecycleOwner(), eCountryInfos -> {
            for(int x = 0; x < eCountryInfos.size(); x++){
                if(txtCitizen.getText().toString().equalsIgnoreCase(eCountryInfos.get(x).getNational())){
                    mViewModel.setCitizenship(eCountryInfos.get(x).getCntryCde());
                    break;
                }
            }
        }));

        mViewModel.getMobileNoType().observe(getViewLifecycleOwner(), stringArrayAdapter -> {
            spnMobile1.setAdapter(stringArrayAdapter);
            spnMobile2.setAdapter(stringArrayAdapter);
            spnMobile3.setAdapter(stringArrayAdapter);
            spnMobile1.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
            spnMobile2.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
            spnMobile3.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        });

        spnMobile1.setOnItemClickListener(new OnItemClickListener(spnMobile1));
        spnMobile2.setOnItemClickListener(new OnItemClickListener(spnMobile2));
        spnMobile3.setOnItemClickListener(new OnItemClickListener(spnMobile3));
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("sample", infoModel);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onSaveSuccessResult(String args) {

        Activity_CreditApplication.getInstance().moveToPageNumber(1);
    }

    @Override
    public void onFailedResult(String message) {
        GToast.CreateMessage(getActivity(), message, GToast.ERROR).show();
    }

    private void SavePersonalInfo(){
        infoModel.setLastName(Objects.requireNonNull(txtLastNm.getText()).toString());
        infoModel.setFrstName(Objects.requireNonNull(txtFrstNm.getText()).toString());
        infoModel.setMiddName(Objects.requireNonNull(txtMiddNm.getText()).toString());
        infoModel.setSuffix(Objects.requireNonNull(txtSuffixx.getText()).toString());
        infoModel.setNickName(Objects.requireNonNull(txtNickNm.getText()).toString());
        infoModel.setBrthDate(Objects.requireNonNull(txtBirthDt.getText()).toString());
        infoModel.setMotherNm(Objects.requireNonNull(txtMothNm.getText()).toString());
        infoModel.clearMobileNo();
        if(!Objects.requireNonNull(txtMobileNo1.getText()).toString().trim().isEmpty()) {
            if(Integer.parseInt(psMob1NetTp) == 1) {
                infoModel.setMobileNo(txtMobileNo1.getText().toString(), psMob1NetTp, Integer.parseInt(Objects.requireNonNull(txtMobileYr1.getText()).toString()));
            } else {
                infoModel.setMobileNo(txtMobileNo1.getText().toString(), psMob1NetTp, 0);
                Log.e("Postpaid index " + psMob1NetTp, infoModel.getPostPaid(0));
            }
        }
        if(!Objects.requireNonNull(txtMobileNo2.getText()).toString().trim().isEmpty()) {
            if(Integer.parseInt(psMob2NetTp) == 1) {
                infoModel.setMobileNo(txtMobileNo2.getText().toString(), psMob2NetTp, Integer.parseInt(Objects.requireNonNull(txtMobileYr2.getText()).toString()));
            } else {
                infoModel.setMobileNo(txtMobileNo2.getText().toString(), psMob2NetTp, 0);
            }
        }
        if(!Objects.requireNonNull(txtMobileNo3.getText()).toString().trim().isEmpty()) {
            if(Integer.parseInt(psMob3NetTp) == 1) {
                infoModel.setMobileNo(txtMobileNo3.getText().toString(), psMob3NetTp, Integer.parseInt(Objects.requireNonNull(txtMobileYr3.getText()).toString()));
            } else {
                infoModel.setMobileNo(txtMobileNo3.getText().toString(), psMob3NetTp, 0);
            }
        }
        infoModel.setPhoneNox(Objects.requireNonNull(txtTellNox.getText()).toString());
        infoModel.setEmailAdd(Objects.requireNonNull(txtEmailAdd.getText()).toString());
        infoModel.setFbAccntx(Objects.requireNonNull(txtFbAccount.getText()).toString());
        infoModel.setVbrAccnt(Objects.requireNonNull(txtViberAccount.getText()).toString());
        mViewModel.SavePersonalInfo(infoModel, Fragment_PersonalInfo.this);
    }

    class OnItemClickListener implements AdapterView.OnItemClickListener {
        AutoCompleteTextView poView;
        public OnItemClickListener(AutoCompleteTextView view) {
            this.poView = view;
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if (spnMobile1.equals(poView)) {
                psMob1NetTp = String.valueOf(i);
                if(i == 1){
                    tilMobileYr1.setVisibility(View.VISIBLE);
                } else {
                    tilMobileYr1.setVisibility(View.GONE);
                }

            }
            if (spnMobile2.equals(poView)) {
                psMob2NetTp = String.valueOf(i);
                if(i == 1){
                    tilMobileYr2.setVisibility(View.VISIBLE);
                } else {
                    tilMobileYr2.setVisibility(View.GONE);
                }
            }
            if (spnMobile3.equals(poView)) {
                psMob3NetTp = String.valueOf(i);
                if(i == 1){
                    tilMobileYr3.setVisibility(View.VISIBLE);
                } else {
                    tilMobileYr3.setVisibility(View.GONE);
                }
            }
        }
    }
}