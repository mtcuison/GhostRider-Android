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
import android.content.Intent;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.guanzongroup.onlinecreditapplication.Activity.Activity_CreditApplication;
import org.rmj.g3appdriver.etc.OnDateSetListener;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
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
    private TextInputEditText[] txtMobileNo;
    private AutoCompleteTextView[] txtMobileType;
    private TextInputEditText[] txtMobileYear;
    private TextInputLayout[] tilMobileYear;

    private String[] psMobNetTp;
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
//        txtMobileNo1 = v.findViewById(R.id.txt_mobileNo1);
//        txtMobileNo2 = v.findViewById(R.id.txt_mobileNo2);
//        txtMobileNo3 = v.findViewById(R.id.txt_mobileNo3);
//        txtMobileYr1 = v.findViewById(R.id.txt_mobileNo1Year);
//        tilMobileYr1 = v.findViewById(R.id.til_mobileNo1Year);
//        txtMobileYr2 = v.findViewById(R.id.txt_mobileNo2Year);
//        tilMobileYr2 = v.findViewById(R.id.til_mobileNo2Year);
//        txtMobileYr3 = v.findViewById(R.id.txt_mobileNo3Year);
//        tilMobileYr3 = v.findViewById(R.id.til_mobileNo3Year);

        txtTellNox = v.findViewById(R.id.txt_telephoneNo);
        txtEmailAdd = v.findViewById(R.id.txt_emailAdd);
        txtFbAccount = v.findViewById(R.id.txt_fbAccount);
        txtViberAccount = v.findViewById(R.id.txt_viberAccount);

        txtProvince = v.findViewById(R.id.txt_bpProvince);
        txtTown = v.findViewById(R.id.txt_bpTown);
        txtCitizen = v.findViewById(R.id.txt_citizenship);
        rgGender = v.findViewById(R.id.rg_gender);
        spnCivilStatus = v.findViewById(R.id.spn_civilStatus);
//        spnMobile1 = v.findViewById(R.id.spn_mobile1Type);
//        spnMobile2 = v.findViewById(R.id.spn_mobile2Type);
//        spnMobile3 = v.findViewById(R.id.spn_mobile3Type);
        txtBirthDt.addTextChangedListener(new OnDateSetListener(txtBirthDt));
        MaterialButton btnNext = v.findViewById(R.id.btn_creditAppNext);
        txtMobileNo =  new TextInputEditText[] {
                v.findViewById(R.id.txt_mobileNo1),
                v.findViewById(R.id.txt_mobileNo2),
                v.findViewById(R.id.txt_mobileNo3),
        };
        tilMobileYear =  new TextInputLayout[] {
                v.findViewById(R.id.til_mobileNo1Year),
                v.findViewById(R.id.til_mobileNo2Year),
                v.findViewById(R.id.til_mobileNo3Year)
        };
        txtMobileYear =  new TextInputEditText[] {
                v.findViewById(R.id.txt_mobileNo1Year),
                v.findViewById(R.id.txt_mobileNo2Year),
                v.findViewById(R.id.txt_mobileNo3Year)
        };
        txtMobileType =  new AutoCompleteTextView[] {
                v.findViewById(R.id.spn_mobile1Type),
                v.findViewById(R.id.spn_mobile2Type),
                v.findViewById(R.id.spn_mobile3Type)
        };
        psMobNetTp = new String[]{
                "-1",
                "-1",
                "-1"
        };
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

                setUpFieldsFromLocalDB(eCreditApplicantInfo);
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
            for (int i = 0; i < txtMobileType.length; i++ ){
                txtMobileType[i].setAdapter(stringArrayAdapter);
                txtMobileType[i].setDropDownBackgroundResource(R.drawable.bg_gradient_light);
            }
//            spnMobile1.setAdapter(stringArrayAdapter);
//            spnMobile2.setAdapter(stringArrayAdapter);
//            spnMobile3.setAdapter(stringArrayAdapter);
//            spnMobile1.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
//            spnMobile2.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
//            spnMobile3.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        });

//        spnMobile1.setOnItemClickListener(new OnItemClickListener(spnMobile1));
//        spnMobile2.setOnItemClickListener(new OnItemClickListener(spnMobile2));
//        spnMobile3.setOnItemClickListener(new OnItemClickListener(spnMobile3));
        for (int i = 0; i < txtMobileType.length; i++ ){
            txtMobileType[i].setOnItemClickListener(new OnItemClickListener(txtMobileType[i]));
        }
    }
    @SuppressLint("NewApi")
    public void setUpFieldsFromLocalDB(ECreditApplicantInfo credits) throws JSONException {
        if (credits.getApplInfo() != null){
            try {

                JSONObject appInfo = new JSONObject(credits.getApplInfo());
//
                txtLastNm.setText(appInfo.getString("sLastName"));
                txtFrstNm.setText(appInfo.getString("sFrstName"));
                txtMiddNm.setText(appInfo.getString("sMiddName"));
                txtSuffixx.setText(appInfo.getString("sSuffixNm"));
                txtNickNm.setText(appInfo.getString("sNickName"));
                txtBirthDt.setText(appInfo.getString("dBirthDte"));
//                txtMothNm.setText(appInfo.getString("sLastName"));
//                txtMobileNo1.setText(appInfo.getString("sLastName"));
//                txtMobileNo2.setText(appInfo.getString("sLastName"));
//                txtMobileNo3.setText(appInfo.getString("sLastName"));
//                txtMobileYr1.setText(appInfo.getString("sLastName"));
//                tilMobileYr1.setText(appInfo.getString("sLastName"));
//                txtMobileYr2.setText(appInfo.getString("sLastName"));
//                tilMobileYr2.setText(appInfo.getString("sLastName"));
//                txtMobileYr3.setText(appInfo.getString("sLastName"));
//                tilMobileYr3.setText(appInfo.getString("sLastName"));
//
//                txtTellNox.setText(appInfo.getString("sLastName"));;
//                txtFbAccount.setText(appInfo.getString("sLastName"));
//                txtViberAccount.setText(appInfo.getString("sLastName"));
//                txtProvince.setText(appInfo.getString("sLastName"));
//                txtTown.setText(appInfo.getString("sLastName"));
//                mViewModel.getProvinceNameFromProvID(appInfo.getString("sCitizenx"));

                spnCivilStatus.setText(CreditAppConstants.CIVIL_STATUS[Integer.parseInt(appInfo.getString("cCvilStat"))], false);
                spnCivilStatus.setSelection(Integer.parseInt(appInfo.getString("cCvilStat")));
                mViewModel.setCvlStats(appInfo.getString("cCvilStat"));
                mViewModel.getTownProvinceByTownID(appInfo.getString("sBirthPlc")).observe(getViewLifecycleOwner(), townProvinceInfo -> {
                    txtTown.setText(townProvinceInfo.sTownName);
                    txtProvince.setText(townProvinceInfo.sProvName);
                    mViewModel.setTownID(townProvinceInfo.sTownIDxx);
                    mViewModel.setProvID(townProvinceInfo.sProvIDxx);
                });
                mViewModel.getClientCitizenship(appInfo.getString("sCitizenx")).observe(getViewLifecycleOwner(), citizen -> {
                    txtCitizen.setText(citizen);
                    try {
                        mViewModel.setCitizenship(appInfo.getString("sCitizenx"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
                for(int i = 0; i < rgGender.getChildCount(); i++){
                    if (i == Integer.parseInt(appInfo.getString("cGenderCd"))){
                        mViewModel.setGender(appInfo.getString("cGenderCd"));
                        ((RadioButton)rgGender.getChildAt(i)).setChecked(true);
                    }
                }
//                if (appInfo.getString("cGenderCd").equalsIgnoreCase("0")) {
//                    rgGender.check(R.id.rb_male);
//                    mViewModel.setGender("0");
//                } else if (appInfo.getString("cGenderCd").equalsIgnoreCase("1")) {
//                    rgGender.check(R.id.rb_female);
//                    mViewModel.setGender("1");
//                } else if (appInfo.getString("cGenderCd").equalsIgnoreCase("2")) {
//                    rgGender.check(R.id.rb_lgbt);
//                    mViewModel.setGender("2");
//                }

                JSONArray arrayEmail = new JSONArray(appInfo.getString("email_address"));
                JSONObject emailObj = new JSONObject(arrayEmail.get(0).toString());
                txtEmailAdd.setText(emailObj.getString("sEmailAdd"));
                JSONArray arrayContact = new JSONArray(appInfo.getString("mobile_number"));
                for (int j = 0; j < arrayContact.length(); j++){
                    JSONObject contact = new JSONObject(arrayContact.get(j).toString());
                    txtMobileNo[j].setText(contact.getString("sMobileNo"));
                    txtMobileYear[j].setText(contact.getInt("nPostYear") + "");
                    txtMobileType[j].setText(CreditAppConstants.MOBILE_NO_TYPE[Integer.parseInt(contact.getString("cPostPaid"))], false);
                    psMobNetTp[j] = contact.getString("cPostPaid");
                    if (contact.getString("cPostPaid").equalsIgnoreCase("0")){
                        tilMobileYear[j].setVisibility(View.GONE);
                        txtMobileType[j].setSelection(0);
                    }else{
                        tilMobileYear[j].setVisibility(View.VISIBLE);
                        txtMobileType[j].setSelection(1);
                    }

                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        }else {
            txtLastNm.getText().clear();
            txtFrstNm.getText().clear();
            txtMiddNm.getText().clear();
            txtSuffixx.getText().clear();
            txtNickNm.getText().clear();
            txtBirthDt.getText().clear();
            txtCitizen.getText().clear();
            txtTown.getText().clear();
            txtProvince.getText().clear();
            rgGender.clearCheck();
            spnCivilStatus.getText().clear();
            txtMobileNo[0].getText().clear();
            txtMobileNo[1].getText().clear();
            txtMobileNo[2].getText().clear();
            txtMobileType[0].getText().clear();
            txtMobileType[1].getText().clear();
            txtMobileType[2].getText().clear();
            txtMobileYear[0].getText().clear();
            txtMobileYear[1].getText().clear();
            txtMobileYear[2].getText().clear();
            txtEmailAdd.getText().clear();
            txtTellNox.getText().clear();
            txtViberAccount.getText().clear();
            txtFbAccount.getText().clear();
        }

    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("sample", infoModel);
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
        if(txtMobileNo[0] != null || !Objects.requireNonNull(txtMobileNo[0].getText()).toString().trim().isEmpty()) {
            if(Integer.parseInt(psMob1NetTp) == 1) {
                infoModel.setMobileNo(txtMobileNo[0].getText().toString(), psMobNetTp[0], Integer.parseInt(Objects.requireNonNull(txtMobileYear[0].getText()).toString()));
            } else {
                infoModel.setMobileNo(txtMobileNo[0].getText().toString(), psMobNetTp[0], 0);
            }
        }
        if(!Objects.requireNonNull(txtMobileNo[1].getText()).toString().trim().isEmpty()) {
            if(Integer.parseInt(psMob2NetTp) == 1) {
                infoModel.setMobileNo(txtMobileNo[1].getText().toString(), psMobNetTp[1], Integer.parseInt(Objects.requireNonNull(txtMobileYear[1].getText()).toString()));
            } else {
                infoModel.setMobileNo(txtMobileNo[1].getText().toString(), psMobNetTp[1], 0);
            }
        }
        if(!Objects.requireNonNull(txtMobileNo[2].getText()).toString().trim().isEmpty()) {
            if(Integer.parseInt(psMob3NetTp) == 1) {
                infoModel.setMobileNo(txtMobileNo[2].getText().toString(), psMobNetTp[2], Integer.parseInt(Objects.requireNonNull(txtMobileYear[2].getText()).toString()));
            } else {
                infoModel.setMobileNo(txtMobileNo[2].getText().toString(), psMobNetTp[2], 0);
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
            if (txtMobileType[0].equals(poView)) {
                psMob1NetTp = String.valueOf(i);
                psMobNetTp[0] = String.valueOf(i);
                if(i == 1){
                    tilMobileYear[0].setVisibility(View.VISIBLE);
                } else {
                    tilMobileYear[0].setVisibility(View.GONE);
                }

            }
            if (txtMobileType[1].equals(poView)) {
                psMob2NetTp = String.valueOf(i);
                psMobNetTp[1] = String.valueOf(i);
                if(i == 1){
                    tilMobileYear[1].setVisibility(View.VISIBLE);
                } else {
                    tilMobileYear[1].setVisibility(View.GONE);
                }
            }
            if (txtMobileType[2].equals(poView)) {
                psMob3NetTp = String.valueOf(i);
                psMobNetTp[2] = String.valueOf(i);
                if(i == 1){
                    tilMobileYear[2].setVisibility(View.VISIBLE);
                } else {
                    tilMobileYear[2].setVisibility(View.GONE);
                }
            }
        }
    }

}