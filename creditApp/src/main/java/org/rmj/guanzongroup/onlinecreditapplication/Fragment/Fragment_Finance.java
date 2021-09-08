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

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GRider.Etc.FormatUIText;
import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.guanzongroup.onlinecreditapplication.Activity.Activity_CreditApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.Model.FinanceInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMFinance;

import java.util.Objects;

public class Fragment_Finance extends Fragment implements ViewModelCallBack {
    private static final String TAG = Fragment_Finance.class.getSimpleName();
    private VMFinance mViewModel;
    private FinanceInfoModel infoModel;
    private AutoCompleteTextView spnRelation;
    private String relationX = "-1";
    private TextInputEditText txtFNamex,
                                txtFIncme,
                                txtFMoble,
                                txtFFacbk,
                                txtFEmail;

    private AutoCompleteTextView txtFCntry;

    private Button btnNext, btnPrvs;

    public static Fragment_Finance newInstance() {
        return new Fragment_Finance();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_finance, container, false);
        infoModel = new FinanceInfoModel();
        spnRelation = view.findViewById(R.id.spn_financierRelation);
        txtFNamex = view.findViewById(R.id.txt_financierName);
        txtFIncme = view.findViewById(R.id.txt_financierInc);
        txtFCntry = view.findViewById(R.id.txt_financierCntry);
        txtFMoble = view.findViewById(R.id.txt_financierContact);
        txtFFacbk = view.findViewById(R.id.txt_financierFb);
        txtFEmail = view.findViewById(R.id.txt_financierEmail);
        btnNext = view.findViewById(R.id.btn_creditAppNext);
        btnPrvs = view.findViewById(R.id.btn_creditAppPrvs);

        btnPrvs.setOnClickListener(v -> {
            try {
                Activity_CreditApplication.getInstance().moveToPageNumber(mViewModel.getPreviousPage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        txtFIncme.addTextChangedListener(new FormatUIText.CurrencyFormat(txtFIncme));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String TransNox = Activity_CreditApplication.getInstance().getTransNox();
        mViewModel = ViewModelProviders.of(this).get(VMFinance.class);
        mViewModel.setTransNox(TransNox);
        mViewModel.getApplicationInfo().observe(getViewLifecycleOwner(), eCreditApplicantInfo ->{
            mViewModel.setGOCasApplication(eCreditApplicantInfo);
            try {
                setUpFieldsFromLocalDB(eCreditApplicantInfo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        mViewModel.getCountryNameList().observe(getViewLifecycleOwner(), strings -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
            txtFCntry.setAdapter(adapter);
            txtFCntry.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        });

        txtFCntry.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getCountryInfoList().observe(getViewLifecycleOwner(), countryInfos -> {
            for(int x = 0; x < countryInfos.size(); x++){
                if(txtFCntry.getText().toString().equalsIgnoreCase(countryInfos.get(x).getCntryNme())){
                    infoModel.setCountryName(countryInfos.get(x).getCntryCde());
                    break;
                }
            }
        }));
        mViewModel.getFinanceSource().observe(getViewLifecycleOwner(), stringArrayAdapter -> {
            spnRelation.setAdapter(stringArrayAdapter);
            spnRelation.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        });
        mViewModel.getFinanceSourceSpn().observe(getViewLifecycleOwner(), s ->{
            relationX = s;
            spnRelation.setSelection(Integer.parseInt(s));
        });
        spnRelation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mViewModel.setFinanceSourceSpn(String.valueOf(position));
            }
        });
        btnNext.setOnClickListener(view -> {
            infoModel.setFinancierRelation(relationX);
            infoModel.setFinancierName(Objects.requireNonNull(txtFNamex.getText()).toString());
            infoModel.setEmail(Objects.requireNonNull(txtFEmail.getText()).toString());
            infoModel.setRangeOfIncome(Objects.requireNonNull(txtFIncme.getText()).toString());
            infoModel.setMobileNo(Objects.requireNonNull(txtFMoble.getText()).toString());
            infoModel.setFacebook(Objects.requireNonNull(txtFFacbk.getText()).toString());
            infoModel.setEmail(txtFEmail.getText().toString());
            mViewModel.SaveFinancierInfo(infoModel, Fragment_Finance.this);
        });
    }

    @Override
    public void onSaveSuccessResult(String args) {
        Activity_CreditApplication.getInstance().moveToPageNumber(Integer.parseInt(args));
    }


    @Override
    public void onFailedResult(String message) {
        GToast.CreateMessage(getActivity(), message, GToast.ERROR).show();
    }

    @SuppressLint("NewApi")
    public void setUpFieldsFromLocalDB(ECreditApplicantInfo credits) throws JSONException {
        if (credits.getFinancex() != null){
            JSONObject financeObj = new JSONObject(credits.getFinancex());
            spnRelation.setText(CreditAppConstants.FINANCE_SOURCE[Integer.parseInt(financeObj.getString("sReltnCde"))], false);
            spnRelation.setSelection(Integer.parseInt(financeObj.getString("sReltnCde")));
            relationX = financeObj.getString("sReltnCde");
            txtFNamex.setText(financeObj.getString("sFinancer"));
            txtFIncme.setText(financeObj.getString("nEstIncme"));
            txtFMoble.setText(financeObj.getString("sMobileNo"));
            txtFEmail.setText(financeObj.getString("sEmailAdd"));
            txtFFacbk.setText(financeObj.getString("sFBAcctxx"));
            mViewModel.getCountryInfoList().observe(getViewLifecycleOwner(), countryInfos -> {
                try {
                    String sCountryCode = financeObj.getString("sNatnCode");
                    for(int x = 0; x < countryInfos.size(); x++){
                        if(sCountryCode.equalsIgnoreCase(countryInfos.get(x).getCntryCde())){
                            txtFCntry.setText(countryInfos.get(x).getCntryNme());
                            infoModel.setCountryName(countryInfos.get(x).getCntryCde());
                            break;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            });
        }
    }
}