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

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.etc.LoadDialog;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.guanzongroup.onlinecreditapplication.Activity.Activity_CreditApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Data.UploadCreditApp;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.Model.CoMakerResidenceModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMComakerResidence;

import java.util.Objects;

public class Fragment_ComakerResidence extends Fragment implements ViewModelCallBack {
    private static final String TAG = Fragment_ComakerResidence.class.getSimpleName();
    private VMComakerResidence mViewModel;
    private CoMakerResidenceModel infoModel;

    private TextInputEditText txtLandMark,
            txtHouseNox,
            txtAddress1,
            txtAddress2,
            txtRelationship,
            txtLgnthStay,
            txtMonthlyExp;
    private AutoCompleteTextView txtBarangay,
            txtMunicipality,
            txtProvince;
    private AutoCompleteTextView spnLgnthStay,
            spnHouseHold,
            spnHouseType;

    private String spnLgnthStayPosition = "-1",
            spnHouseHoldPosition = "-1",
            spnHouseTypePosition = "-1";

    private RadioGroup rgOwnsership, rgGarage;

    private TextInputLayout tilRelationship;
    private LinearLayout lnOtherInfo;
    private Button btnNext;
    private Button btnPrvs;

    MessageBox poMessage;
    private LoadDialog poDialogx;

    public static Fragment_ComakerResidence newInstance() {
        return new Fragment_ComakerResidence();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comaker_residence, container, false);
        poMessage = new MessageBox(getActivity());
        poDialogx = new LoadDialog(getActivity());
        initWidgets(view);
        return view;
    }

    private void initWidgets(View v){
        infoModel = new CoMakerResidenceModel();
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

        spnLgnthStay = v.findViewById(R.id.spn_lenghtStay);
        spnHouseHold = v.findViewById(R.id.spn_houseHold);
        spnHouseType = v.findViewById(R.id.spn_houseType);
        rgOwnsership = v.findViewById(R.id.rg_ownership);
        rgGarage = v.findViewById(R.id.rg_garage);
        tilRelationship = v.findViewById(R.id.til_relationship);
        lnOtherInfo = v.findViewById(R.id.linear_otherInfo);

        spnHouseHold.setOnItemClickListener(new OnItemClickListener(spnHouseHold));
        spnHouseType.setOnItemClickListener(new OnItemClickListener(spnHouseType));
        spnLgnthStay.setOnItemClickListener(new OnItemClickListener(spnLgnthStay));

        rgOwnsership.setOnCheckedChangeListener(new OnHouseOwnershipSelectListener());
        rgGarage.setOnCheckedChangeListener(new OnHouseOwnershipSelectListener());
        btnNext = v.findViewById(R.id.btn_creditAppNext);
        btnPrvs = v.findViewById(R.id.btn_creditAppPrvs);

        btnPrvs.setOnClickListener(v1 -> Activity_CreditApplication.getInstance().moveToPageNumber(16));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMComakerResidence.class);
        String TransNox = Activity_CreditApplication.getInstance().getTransNox();
        mViewModel.setTransNox(TransNox);
        mViewModel.getCreditApplicantInfo().observe(getViewLifecycleOwner(), eCreditApplicantInfo -> {
            try{
                mViewModel.setCreditApplicantInfo(eCreditApplicantInfo);
                setFieldValues(eCreditApplicantInfo);
            } catch (NullPointerException e){
                e.printStackTrace();
            }
        });
        mViewModel.getProvinceNameList().observe(getViewLifecycleOwner(), strings -> {
            ArrayAdapter<String> loAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
            txtProvince.setAdapter(loAdapter);
            txtProvince.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        });

        txtProvince.setOnItemClickListener((parent, view, position, id) -> mViewModel.getProvinceInfo().observe(getViewLifecycleOwner(), eProvinceInfos -> {
            for(int x = 0; x < eProvinceInfos.size(); x++){
                if(txtProvince.getText().toString().equalsIgnoreCase(eProvinceInfos.get(x).getProvName())){
                    mViewModel.setProvinceID(eProvinceInfos.get(x).getProvIDxx());
                    break;
                }
            }

            mViewModel.getTownNameList().observe(getViewLifecycleOwner(), strings -> {
                ArrayAdapter<String> loAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
                txtMunicipality.setAdapter(loAdapter);
                txtMunicipality.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
            });
        }));

        txtMunicipality.setOnItemClickListener((parent, view, position, id) -> mViewModel.getTownInfoList().observe(getViewLifecycleOwner(), eTownInfos -> {
            for(int x = 0; x < eTownInfos.size(); x++){
                if(txtMunicipality.getText().toString().equalsIgnoreCase(eTownInfos.get(x).getTownName())){
                    mViewModel.setTownID(eTownInfos.get(x).getTownIDxx());
                    infoModel.setsMuncplID(eTownInfos.get(x).getTownIDxx());
                    break;
                }
            }

            mViewModel.getBarangayListName().observe(getViewLifecycleOwner(), strings -> {
                ArrayAdapter<String> loAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
                txtBarangay.setAdapter(loAdapter);
                txtBarangay.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
            });
        }));

        txtBarangay.setOnItemClickListener((parent, view, position, id) -> mViewModel.getBarangayInfoList().observe(getViewLifecycleOwner(), eBarangayInfos -> {
            for(int x = 0; x < eBarangayInfos.size(); x++){
                if(txtBarangay.getText().toString().equalsIgnoreCase(eBarangayInfos.get(x).getBrgyName())){
                    infoModel.setsBrgyIDxx(eBarangayInfos.get(x).getBrgyIDxx());
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

        btnNext.setOnClickListener(v -> {
            infoModel.setsLandMark(Objects.requireNonNull(txtLandMark.getText()).toString());
            infoModel.setsHouseNox(Objects.requireNonNull(txtHouseNox.getText()).toString());
            infoModel.setsAddress1(Objects.requireNonNull(txtAddress1.getText()).toString());
            infoModel.setsAddress2(Objects.requireNonNull(txtAddress2.getText()).toString());
            infoModel.setsProvncNm(txtProvince.getText().toString());
            infoModel.setsMuncplNm(txtMunicipality.getText().toString());
            infoModel.setsBrgyName(txtBarangay.getText().toString());
            infoModel.setsRelation(Objects.requireNonNull(txtRelationship.getText()).toString());
            infoModel.setsLenghtSt(Objects.requireNonNull(txtLgnthStay.getText()).toString());
            infoModel.setsExpenses(Objects.requireNonNull(txtMonthlyExp.getText()).toString());
            mViewModel.SaveCoMakerResidence(infoModel, Fragment_ComakerResidence.this);
        });
    }

    @Override
    public void onSaveSuccessResult(String args) {
        Activity_CreditApplication.getInstance().moveToPageNumber(18);
    }

    @Override
    public void onFailedResult(String message) {
        poMessage.initDialog();
        poMessage.setTitle("Credit Application");
        poMessage.setMessage(message);
        poMessage.setPositiveButton("Okay", (view1, dialog1) -> dialog1.dismiss());
        poMessage.show();
    }

    private void setFieldValues(ECreditApplicantInfo foCredApp) {
        if(foCredApp.getCmResidx() != null) {
            try {
                JSONObject loJson = new JSONObject(foCredApp.getCmResidx());
                Log.e(TAG + " jsonCon", loJson.toString());
                // Value setter goes here

                JSONObject loJsAddrs = loJson.getJSONObject("present_address");
                    txtLandMark.setText( (!loJsAddrs.getString("sLandMark").equalsIgnoreCase("")) ?  loJsAddrs.getString("sLandMark") : "");
                    txtHouseNox.setText( (!loJsAddrs.getString("sHouseNox").equalsIgnoreCase("")) ?  loJsAddrs.getString("sHouseNox") : "");

                    txtAddress1.setText(( (!loJsAddrs.getString("sAddress1").equalsIgnoreCase("")) ?  loJsAddrs.getString("sAddress1") : ""));
                    txtAddress2.setText(( (!loJsAddrs.getString("sAddress2").equalsIgnoreCase("")) ?  loJsAddrs.getString("sAddress2") : ""));

                    mViewModel.getBrgyTownProvinceInfoWithID(loJsAddrs.getString("sBrgyIDxx")).observe(getViewLifecycleOwner(), townProvinceInfo -> {
                        txtMunicipality.setText(townProvinceInfo.sTownName);
                        txtProvince.setText(townProvinceInfo.sProvName);
                        txtBarangay.setText(townProvinceInfo.sBrgyName);
                        mViewModel.setProvinceID(townProvinceInfo.sProvIDxx);
                        mViewModel.setTownID(townProvinceInfo.sTownIDxx);
                        infoModel.setsBrgyIDxx(townProvinceInfo.sBrgyIDxx);
                        infoModel.setsMuncplID(townProvinceInfo.sTownIDxx);
                    });

                if (loJson.getString("cOwnershp").equalsIgnoreCase("0")) {
                    rgOwnsership.check(R.id.rb_owned);
                    infoModel.setsHouseOwn("0");
                } else if (loJson.getString("cOwnershp").equalsIgnoreCase("1")) {
                    rgOwnsership.check(R.id.rb_rent);
                    infoModel.setsHouseOwn("1");
                } else if (loJson.getString("cOwnershp").equalsIgnoreCase("2")) {
                    rgOwnsership.check(R.id.rb_careTaker);
                    infoModel.setsHouseOwn("2");
                }

                if(!loJson.getString("cOwnOther").equalsIgnoreCase("")) {
                    spnHouseHold.setText(CreditAppConstants.HOUSEHOLDS[Integer.parseInt(loJson.getString("cOwnOther"))]);
                    infoModel.setsHouseHld(loJson.getString("cOwnOther"));
                }

                if(!loJson.getString("cHouseTyp").equalsIgnoreCase("")) {
                    spnHouseType.setText(CreditAppConstants.HOUSE_TYPE[Integer.parseInt(loJson.getString("cHouseTyp"))]);
                    infoModel.setsHouseTpe(loJson.getString("cHouseTyp"));
                }

                if(loJson.getString("cGaragexx").equalsIgnoreCase("1")){
                    rgGarage.check(R.id.rb_yes);
                    infoModel.setsHasGarge("1");
                }
                else if(loJson.getString("cGaragexx").equalsIgnoreCase("0")){
                    rgGarage.check(R.id.rb_no);
                    infoModel.setsHasGarge("0");
                }
                txtRelationship.setText(loJson.getString("sCtkReltn"));

            } catch(JSONException e) {
                e.printStackTrace();
            }
        } else {
            txtLandMark.getText().clear();
            txtHouseNox.getText().clear();
            txtAddress1.getText().clear();
            txtAddress2.getText().clear();
            txtMunicipality.getText().clear();
            txtProvince.getText().clear();
            txtBarangay.getText().clear();
            rgOwnsership.clearCheck();

            spnHouseHold.getText().clear();
            spnHouseType.getText().clear();
            rgGarage.clearCheck();
            txtRelationship.getText().clear();
        }
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
                infoModel.setsHouseHld(spnHouseHoldPosition);
            }
            if (spnHouseType.equals(poView)) {
                spnHouseTypePosition = String.valueOf(i);
                infoModel.setsHouseTpe(spnHouseTypePosition);
            }
            if (spnLgnthStay.equals(poView)) {
                spnLgnthStayPosition = String.valueOf(i);
                infoModel.setcIsYearxx(spnLgnthStayPosition);
            }

        }
    }
    private class OnHouseOwnershipSelectListener implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            if(radioGroup.getId() == R.id.rg_ownership){
                if(i == R.id.rb_owned){
                    lnOtherInfo.setVisibility(View.GONE);
                    infoModel.setsHouseOwn("0");
                }
                if(i == R.id.rb_rent){
                    lnOtherInfo.setVisibility(View.VISIBLE);
                    tilRelationship.setVisibility(View.GONE);
                    infoModel.setsHouseOwn("1");
                }
                if(i == R.id.rb_careTaker){
                    lnOtherInfo.setVisibility(View.VISIBLE);
                    tilRelationship.setVisibility(View.VISIBLE);
                    infoModel.setsHouseOwn("2");
                }
            } else {
                if(i == R.id.rb_yes){
                    infoModel.setsHasGarge("1");
                }
                if(i == R.id.rb_no){
                    infoModel.setsHasGarge("0");
                }
            }
        }
    }
}