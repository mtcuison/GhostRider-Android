package org.rmj.guanzongroup.onlinecreditapplication.Fragment;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.simple.JSONObject;
import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.guanzongroup.onlinecreditapplication.Activity.Activity_CreditApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.TextFormatter;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMIntroductoryQuestion;

import java.util.Objects;

public class Fragment_IntroductoryQuestion extends Fragment implements ViewModelCallBack {
    public static final String TAG = Fragment_IntroductoryQuestion.class.getSimpleName();
    private VMIntroductoryQuestion mViewModel;

    private AutoCompleteTextView txtBranchNm, txtBrandNm, txtModelNm;
    private TextInputEditText txtDownPymnt, txtAmort;
    private Spinner spnApplType, spnCustomerType, spnTerm;
    private MaterialButton btnCreate;

    public static Fragment_IntroductoryQuestion newInstance() {
        return new Fragment_IntroductoryQuestion();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_introductory_question, container, false);

        txtBranchNm = v.findViewById(R.id.txt_branchName);
        txtBrandNm = v.findViewById(R.id.txt_brandName);
        txtModelNm = v.findViewById(R.id.txt_modelName);
        txtDownPymnt = v.findViewById(R.id.txt_downpayment);
        txtAmort = v.findViewById(R.id.txt_monthlyAmort);

        spnApplType = v.findViewById(R.id.spn_applicationType);
        spnCustomerType = v.findViewById(R.id.spn_customerType);
        spnTerm = v.findViewById(R.id.spn_installmentTerm);

        btnCreate = v.findViewById(R.id.btn_createCreditApp);
        txtDownPymnt.addTextChangedListener(new TextFormatter.OnTextChangedCurrencyFormatter(txtDownPymnt));
        btnCreate.setOnClickListener(view -> mViewModel.CreateNewApplication(Fragment_IntroductoryQuestion.this));
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMIntroductoryQuestion.class);

        mViewModel.getApplicationType().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnApplType.setAdapter(stringArrayAdapter));

        mViewModel.getCustomerType().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnCustomerType.setAdapter(stringArrayAdapter));

        mViewModel.getAllBranchNames().observe(getViewLifecycleOwner(), strings -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
            txtBranchNm.setAdapter(adapter);
        });

        txtBranchNm.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getAllBranchInfo().observe(getViewLifecycleOwner(), eBranchInfos -> {
            for(int x = 0; x < eBranchInfos.size(); x++){
                if(txtBranchNm.getText().toString().equalsIgnoreCase(eBranchInfos.get(x).getBranchNm())){
                    mViewModel.setBanchCde(eBranchInfos.get(x).getBranchCd());
                    break;
                }
            }
        }));

        mViewModel.getAllBrandNames().observe(getViewLifecycleOwner(), strings -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
            txtBrandNm.setAdapter(adapter);
        });

        txtBrandNm.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getAllMcBrand().observe(getViewLifecycleOwner(), eMcBrands -> {
            for(int x = 0; x < eMcBrands.size(); x++){
                if(txtBrandNm.getText().toString().equalsIgnoreCase(eMcBrands.get(x).getBrandNme())){
                    mViewModel.setLsBrandID(eMcBrands.get(x).getBrandIDx());
                    break;
                }
            }
        }));

        mViewModel.getBrandID().observe(getViewLifecycleOwner(), s -> mViewModel.getAllBrandModelName(s).observe(getViewLifecycleOwner(), strings -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
            txtModelNm.setAdapter(adapter);
            Log.e(TAG, "Array Adapter has been updated.");
        }));

        txtModelNm.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getAllBrandModelInfo().observe(getViewLifecycleOwner(), eMcModels -> {
            for(int x = 0; x < eMcModels.size(); x++){
                if(txtModelNm.getText().toString().equalsIgnoreCase(eMcModels.get(x).getModelNme() +" "+ eMcModels.get(x).getModelCde())){
                    mViewModel.setLsModelCd(eMcModels.get(x).getModelIDx());
                    break;
                }
            }
        }));

        mViewModel.getModelID().observe(getViewLifecycleOwner(), s -> {
            mViewModel.getDpInfo(s).observe(getViewLifecycleOwner(), mcDPInfo -> {
                try{
                    JSONObject loJson = new JSONObject();
                    loJson.put("sModelIDx", mcDPInfo.ModelIDx);
                    loJson.put("sModelNme", mcDPInfo.ModelNme);
                    loJson.put("nRebatesx", mcDPInfo.Rebatesx);
                    loJson.put("nMiscChrg", mcDPInfo.MiscChrg);
                    loJson.put("nEndMrtgg", mcDPInfo.EndMrtgg);
                    loJson.put("nMinDownx", mcDPInfo.MinDownx);
                    loJson.put("nSelPrice", mcDPInfo.SelPrice);
                    loJson.put("nLastPrce", mcDPInfo.LastPrce);
                    mViewModel.setLoDpInfo(loJson);
                } catch (Exception e){
                    e.printStackTrace();
                }
            });

            mViewModel.getMonthlyAmortInfo(s).observe(getViewLifecycleOwner(), mcAmortInfo -> {
                try{
                    JSONObject loJson = new JSONObject();
                    loJson.put("nSelPrice", mcAmortInfo.SelPrice);
                    loJson.put("nMinDownx", mcAmortInfo.MinDownx);
                    loJson.put("nMiscChrg", mcAmortInfo.MiscChrg);
                    loJson.put("nRebatesx", mcAmortInfo.Rebatesx);
                    loJson.put("nEndMrtgg", mcAmortInfo.EndMrtgg);
                    loJson.put("nAcctThru", mcAmortInfo.AcctThru);
                    loJson.put("nFactorRt", mcAmortInfo.FactorRt);
                    mViewModel.setLoMonthlyInfo(loJson);
                } catch (Exception e){
                    e.printStackTrace();
                }
            });
        });

        mViewModel.getDownpayment().observe(getViewLifecycleOwner(), s -> txtDownPymnt.setText(s));

        txtDownPymnt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try{
                    mViewModel.calculateMonthlyPayment(Objects.requireNonNull(txtDownPymnt.getText()).toString().replace(",", ""));
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {            }
        });

        mViewModel.getInstallmentTerm().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnTerm.setAdapter(stringArrayAdapter));

        mViewModel.getSelectedInstallmentTerm().observe(getViewLifecycleOwner(), integer -> mViewModel.calculateMonthlyPayment());

        spnCustomerType.setOnItemSelectedListener(new SpinnerSelectionListener(mViewModel));

        spnTerm.setOnItemSelectedListener(new SpinnerSelectionListener(mViewModel));

        mViewModel.getMonthlyAmort().observe(getViewLifecycleOwner(), s -> txtAmort.setText(s));
    }

    @Override
    public void onSaveSuccessResult(String args) {
        Intent loIntent = new Intent(getActivity(), Activity_CreditApplication.class);
        loIntent.putExtra("transno", args);
        startActivity(loIntent);

    }

    @Override
    public void onFailedResult(String message) {
        GToast.CreateMessage(getActivity(), message, GToast.ERROR).show();
    }

    private static class SpinnerSelectionListener implements AdapterView.OnItemSelectedListener{
        private final VMIntroductoryQuestion vm;

        SpinnerSelectionListener(VMIntroductoryQuestion viewModel){
            this.vm = viewModel;
        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if(adapterView.getId() == R.id.spn_customerType){
                String type = "";
                switch (i){
                    case 0:
                        break;
                    case 1:
                        type = "0";
                        break;
                    case 2:
                        type = "1";
                        break;
                }
                vm.setCustomerType(type);
            }
            if(adapterView.getId() == R.id.spn_installmentTerm) {
                int term = 0;
                switch (i) {
                    case 0:
                    case 1:
                        term = 36;
                        break;
                    case 2:
                        term = 24;
                        break;
                    case 3:
                        term = 18;
                        break;
                    case 4:
                        term = 12;
                        break;
                    case 5:
                        term = 6;
                        break;
                }
                vm.setLsIntTerm(term);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }
}