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

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GRider.Etc.FormatUIText;
import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.guanzongroup.onlinecreditapplication.Activity.Activity_CreditApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.TextFormatter;
import org.rmj.guanzongroup.onlinecreditapplication.Model.DisbursementInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMDisbursementInfo;

import java.util.Objects;

public class Fragment_DisbursementInfo extends Fragment implements ViewModelCallBack {
    private static final String TAG = Fragment_DisbursementInfo.class.getSimpleName();
    private VMDisbursementInfo mViewModel;
    private DisbursementInfoModel infoModel;
    private String TransNox;
    private View v;

    private AutoCompleteTextView spnTypex;
    private String typeX ="";
    private TextInputEditText tieElctx;
    private TextInputEditText tieWater;
    private TextInputEditText tieFoodx;
    private TextInputEditText tieLoans;
    private TextInputEditText tieBankN;
    private TextInputEditText tieCCBnk;
    private TextInputEditText tieLimit;
    private TextInputEditText tieYearS;

    private Button btnPrev;
    private Button btnNext;

    public static Fragment_DisbursementInfo newInstance() {
        return new Fragment_DisbursementInfo();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "Initialized");
        v = inflater.inflate(R.layout.fragment_disbursement, container, false);
        TransNox = Activity_CreditApplication.getInstance().getTransNox();
        infoModel = new DisbursementInfoModel();
        setupWidgets(v);
        return v;
    }
    private void setupWidgets(View view){

        tieElctx = view.findViewById(R.id.tie_cap_dbmElectricity);
        tieWater = view.findViewById(R.id.tie_cap_dbmWater);
        tieFoodx = view.findViewById(R.id.tie_cap_dbmFood);
        tieLoans = view.findViewById(R.id.tie_cap_dbmLoans);
        tieBankN = view.findViewById(R.id.tie_cap_dbmBankName);
        spnTypex = view.findViewById(R.id.spinner_cap_dbmAccountType);
        tieCCBnk = view.findViewById(R.id.tie_cap_dbmBankNameCC);
        tieLimit = view.findViewById(R.id.tie_cap_dbmCreditLimit);
        tieYearS = view.findViewById(R.id.tie_cap_dbmYearStarted);

        tieElctx.addTextChangedListener(new FormatUIText.CurrencyFormat(tieElctx));
        tieWater.addTextChangedListener(new FormatUIText.CurrencyFormat(tieWater));
        tieFoodx.addTextChangedListener(new FormatUIText.CurrencyFormat(tieFoodx));
        tieLoans.addTextChangedListener(new FormatUIText.CurrencyFormat(tieLoans));
        tieLimit.addTextChangedListener(new FormatUIText.CurrencyFormat(tieLimit));

        btnNext = view.findViewById(R.id.btn_creditAppNext);
        btnPrev = view.findViewById(R.id.btn_creditAppPrvs);

        btnPrev.setOnClickListener(v -> {
            try {
                Log.e(TAG , String.valueOf(mViewModel.getPreviousPage()));
                Activity_CreditApplication.getInstance().moveToPageNumber(mViewModel.getPreviousPage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(VMDisbursementInfo.class);
        mViewModel.setTransNox(Activity_CreditApplication.getInstance().getTransNox());
        mViewModel.getCreditApplicationInfo().observe(getViewLifecycleOwner(), eCreditApplicantInfo -> {
            try {
                mViewModel.setCreditApplicantInfo(eCreditApplicantInfo);
                setFieldValues(eCreditApplicantInfo);
            } catch(NullPointerException e) {
                e.printStackTrace();
            }
        });

        mViewModel.getAccountType().observe(getViewLifecycleOwner(), stringArrayAdapter -> {
            spnTypex.setAdapter(stringArrayAdapter);
            spnTypex.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        });

        tieElctx.addTextChangedListener(new TextFormatter.OnTextChangedCurrencyFormatter(tieElctx));
        tieWater.addTextChangedListener(new TextFormatter.OnTextChangedCurrencyFormatter(tieWater));
        tieFoodx.addTextChangedListener(new TextFormatter.OnTextChangedCurrencyFormatter(tieFoodx));
        tieLoans.addTextChangedListener(new TextFormatter.OnTextChangedCurrencyFormatter(tieLoans));
        tieLimit.addTextChangedListener(new TextFormatter.OnTextChangedCurrencyFormatter(tieLimit));
        spnTypex.setOnItemClickListener((parent, view, position, id) -> {
            typeX = (String.valueOf(position) != null) ? String.valueOf(position) : "";
            mViewModel.setType(typeX);
        });
        btnNext.setOnClickListener(view -> {
            infoModel.setStypeX(typeX);
            infoModel.setElctX(Objects.requireNonNull(tieElctx.getText()).toString().replace(",", ""));
            infoModel.setFoodX(Objects.requireNonNull(tieFoodx.getText().toString().replace(",", "")));
            infoModel.setWaterX(Objects.requireNonNull(tieWater.getText().toString().replace(",", "")));
            infoModel.setLoans(Objects.requireNonNull(tieLoans.getText().toString().replace(",", "")));
            infoModel.setBankN(tieBankN.getText().toString());
            infoModel.setCcBnk(tieCCBnk.getText().toString());
            infoModel.setLimitCC(tieLimit.getText().toString().replace(",", ""));
            infoModel.setYearS(tieYearS.getText().toString());
            mViewModel.SubmitApplicationInfo(infoModel, Fragment_DisbursementInfo.this);
        });
    }

    @Override
    public void onSaveSuccessResult(String args) {
        Activity_CreditApplication.getInstance().moveToPageNumber(13);
    }

    @Override
    public void onFailedResult(String message) {
        GToast.CreateMessage(getActivity(), message, GToast.ERROR).show();
    }

    private void setFieldValues(ECreditApplicantInfo foCredApp) {
        if(foCredApp.getDisbrsmt() != null) {
            try {
                JSONObject loJson = new JSONObject(foCredApp.getDisbrsmt());
                Log.e(TAG + " jsonCon", foCredApp.getDisbrsmt());
                // Value setter goes here

                JSONObject loCreditCd = loJson.getJSONObject("credit_card");
                JSONObject loMonthExp = loJson.getJSONObject("monthly_expenses");
                JSONObject loBankAcct = loJson.getJSONObject("bank_account");

                // Monthly Expenses
                tieElctx.setText(String.valueOf(loMonthExp.get("nElctrcBl")));
                tieWater.setText(String.valueOf(loMonthExp.get("nWaterBil")));
                tieFoodx.setText(String.valueOf(loMonthExp.get("nFoodAllw")));
                tieLoans.setText(String.valueOf(loMonthExp.get("nLoanAmtx")));

                //Bank Account
                tieBankN.setText(loBankAcct.getString("sBankName"));
                if(loBankAcct.getString("sAcctType") != null) {
                    if(!loBankAcct.getString("sAcctType").equalsIgnoreCase("")) {
                        spnTypex.setText(CreditAppConstants.ACCOUNT_TYPE[Integer.parseInt(loBankAcct.getString("sAcctType"))]);
                        spnTypex.setSelection(Integer.parseInt(loBankAcct.getString("sAcctType")));
                    }
                }

                typeX = loBankAcct.getString("sAcctType");
                //Credit Card Account
                tieCCBnk.setText(loCreditCd.getString("sBankName"));
                tieLimit.setText(String.valueOf(loCreditCd.get("nCrdLimit")));
                tieYearS.setText(String.valueOf(loCreditCd.get("nSinceYrx")));

            } catch(JSONException e) {
                e.printStackTrace();
            }
        } else {
            tieElctx.setText("");
            tieWater.setText("");
            tieFoodx.setText("");
            tieLoans.setText("");

            //Bank Account
            tieBankN.setText("");
            spnTypex.getText().clear();
            spnTypex.getText().clear();
            //Credit Card Account
            tieCCBnk.setText("");
            tieLimit.setText("");
            tieYearS.setText("");
        }
    }

}