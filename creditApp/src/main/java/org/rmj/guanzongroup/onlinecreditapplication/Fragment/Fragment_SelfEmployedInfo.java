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

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.GRider.Etc.FormatUIText;
import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.guanzongroup.onlinecreditapplication.Activity.Activity_CreditApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Model.SelfEmployedInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMSelfEmployedInfo;

import java.util.Objects;

public class Fragment_SelfEmployedInfo extends Fragment implements ViewModelCallBack {
    private static final String TAG = Fragment_SelfEmployedInfo.class.getSimpleName();

    private AutoCompleteTextView spnBussNtr,
            spnBussTyp,
            spnBussSze,
            spnLngSrvc;

    private String bussNtrPosition = "-1",
            bussTypPosition = "-1",
            bussSzePosition = "-1",
            lngSrvcPosition = "-1";

    private TextInputEditText txtBussName,
            txtBussAdds,
            txtLnghtSrv,
            txtMnthlyIn,
            txtMnthlyEx;

    private AutoCompleteTextView txtProvnc,
            txtTownxx;

    private Button btnNext, btnPrvs;

    private SelfEmployedInfoModel infoModel;
    private VMSelfEmployedInfo mViewModel;

    public static Fragment_SelfEmployedInfo newInstance() {
        return new Fragment_SelfEmployedInfo();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_self_employed_info, container, false);
        infoModel = new SelfEmployedInfoModel();
        initWidgets(view);
        return view;
    }

    private void initWidgets(View view){
        spnBussNtr = view.findViewById(R.id.spn_businessNature);
        spnBussTyp = view.findViewById(R.id.spn_businessType);
        spnBussSze = view.findViewById(R.id.spn_businessSize);
        spnLngSrvc = view.findViewById(R.id.spn_lenghtSrvc);
        txtBussName = view.findViewById(R.id.txt_businessName);
        txtBussAdds = view.findViewById(R.id.txt_businessAddress);
        txtLnghtSrv = view.findViewById(R.id.txt_lenghtService);
        txtMnthlyIn = view.findViewById(R.id.txt_monthlyInc);
        txtMnthlyEx = view.findViewById(R.id.txt_monthlyExp);
        txtProvnc = view.findViewById(R.id.txt_province);
        txtTownxx = view.findViewById(R.id.txt_town);
        btnPrvs = view.findViewById(R.id.btn_creditAppPrvs);
        btnNext = view.findViewById(R.id.btn_creditAppNext);

        txtMnthlyIn.addTextChangedListener(new FormatUIText.CurrencyFormat(txtMnthlyIn));
        txtMnthlyEx.addTextChangedListener(new FormatUIText.CurrencyFormat(txtMnthlyEx));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(VMSelfEmployedInfo.class);
        String TransNox = Activity_CreditApplication.getInstance().getTransNox();
        mViewModel.setTransNox(TransNox);
        mViewModel.getCreditApplicantInfo().observe(getViewLifecycleOwner(), eCreditApplicantInfo -> {
            try {
                mViewModel.setGOCasDetailInfo(eCreditApplicantInfo);
                mViewModel.setMeansInfos(eCreditApplicantInfo.getAppMeans());
            } catch (Exception e){
                e.printStackTrace();
            }
        });
        mViewModel.getAllProvinceNames().observe(getViewLifecycleOwner(), strings -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
            txtProvnc.setAdapter(adapter);
        });

        mViewModel.getNatureOfBusiness().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnBussNtr.setAdapter(stringArrayAdapter));

        txtProvnc.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getAllProvinceInfo().observe(getViewLifecycleOwner(), eProvinceInfos -> {
            for(int x = 0; x < eProvinceInfos.size(); x++){
                if(txtProvnc.getText().toString().equalsIgnoreCase(eProvinceInfos.get(x).getProvName())){
                    mViewModel.setProvinceID(eProvinceInfos.get(x).getProvIDxx());
                    break;
                }
            }
            mViewModel.getAllTownNames().observe(getViewLifecycleOwner(), strings -> {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
                txtTownxx.setAdapter(adapter);
            });
        }));

        txtTownxx.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getAllTownInfo().observe(getViewLifecycleOwner(), eTownInfos -> {
            for(int x = 0; x < eTownInfos.size(); x++){
                if(txtTownxx.getText().toString().equalsIgnoreCase(eTownInfos.get(x).getTownName())){
                    mViewModel.setTownID(eTownInfos.get(x).getTownIDxx());
                    break;
                }
            }
        }));

        mViewModel.getTypeOfBusiness().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnBussTyp.setAdapter(stringArrayAdapter));

        mViewModel.getSizeOfBusiness().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnBussSze.setAdapter(stringArrayAdapter));

        mViewModel.getLenghtOfService().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnLngSrvc.setAdapter(stringArrayAdapter));
        mViewModel.getPsBsnssNature().observe(getViewLifecycleOwner(), s -> {
            spnBussNtr.setSelection(s.length());
        });
        mViewModel.getPsBsnssType().observe(getViewLifecycleOwner(), s -> {
            bussTypPosition = s;
            spnBussTyp.setSelection(Integer.parseInt(s));
            Log.e("Employee ", s);
        });
        mViewModel.getPsBsnssSize().observe(getViewLifecycleOwner(), s -> {
            bussSzePosition = s;
            spnBussSze.setSelection(Integer.parseInt(s));
            Log.e("Employee ", s);
        });
        mViewModel.getLngthStay().observe(getViewLifecycleOwner(), s -> {
            lngSrvcPosition = s;
            spnLngSrvc.setSelection(Integer.parseInt(s));
            Log.e("Employee ", s);
        });
        spnBussNtr.setOnItemClickListener(new OnItemClickListener(spnBussNtr));
        spnBussTyp.setOnItemClickListener(new OnItemClickListener(spnBussTyp));
        spnBussSze.setOnItemClickListener(new OnItemClickListener(spnBussSze));
        spnLngSrvc.setOnItemClickListener(new OnItemClickListener(spnLngSrvc));
        btnNext.setOnClickListener(view -> {
            infoModel.setNatureOfBusiness(spnBussNtr.getText().toString());
            infoModel.setTypeOfBusiness(bussTypPosition);
            infoModel.setSizeOfBusiness(bussSzePosition);
            infoModel.setNameOfBusiness(Objects.requireNonNull(txtBussName.getText()).toString());
            infoModel.setBusinessAddress(Objects.requireNonNull(txtBussAdds.getText()).toString());
            infoModel.setLengthOfService(Objects.requireNonNull(txtLnghtSrv.getText()).toString());
            infoModel.setLengthOfServiceSpinner(lngSrvcPosition);
            infoModel.setMonthlyIncome(Objects.requireNonNull(txtMnthlyIn.getText()).toString());
            infoModel.setMonthlyExpense(Objects.requireNonNull(txtMnthlyEx.getText()).toString());
            mViewModel.SaveSelfEmployedInfo(infoModel, Fragment_SelfEmployedInfo.this);
        });

        btnPrvs.setOnClickListener(view ->{
            try {
                Activity_CreditApplication.getInstance().moveToPageNumber(mViewModel.getPreviousPage());
            } catch (Exception e) {
                e.printStackTrace();
            }
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
    class OnItemClickListener implements AdapterView.OnItemClickListener {
        AutoCompleteTextView poView;

        public OnItemClickListener(AutoCompleteTextView view) {
            this.poView = view;
        }
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if (spnBussNtr.equals(poView)) {
                bussNtrPosition = String.valueOf(i);
                mViewModel.setPsBsnssNature(adapterView.getItemAtPosition(i).toString());
            }
            if (spnBussTyp.equals(poView)) {
                bussTypPosition = String.valueOf(i);
                mViewModel.setPsBsnssType(bussTypPosition);
            }
            if (spnBussSze.equals(poView)) {
                bussSzePosition = String.valueOf(i);
                mViewModel.setPsBsnssSize(bussSzePosition);
            }
            if (spnLngSrvc.equals(poView)) {
                lngSrvcPosition = String.valueOf(i);
                mViewModel.setLngthStay(lngSrvcPosition);
            }
        }
    }
}