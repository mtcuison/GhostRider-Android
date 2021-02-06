package org.rmj.guanzongroup.onlinecreditapplication.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputEditText;

import org.rmj.guanzongroup.onlinecreditapplication.Activity.Activity_CreditApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Model.SelfEmployedInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMSelfEmployedInfo;

public class Fragment_SelfEmployedInfo extends Fragment implements ViewModelCallBack {
    private static final String TAG = Fragment_SelfEmployedInfo.class.getSimpleName();

    private Spinner spnBussNtr,
                    spnBussTyp,
                    spnBussSze;

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
        spnBussNtr = view.findViewById(R.id.spn_businessNature);
        spnBussTyp = view.findViewById(R.id.spn_businessType);
        spnBussSze = view.findViewById(R.id.spn_businessSize);
        txtBussName = view.findViewById(R.id.txt_businessName);
        txtBussAdds = view.findViewById(R.id.txt_businessAddress);
        txtLnghtSrv = view.findViewById(R.id.txt_lenghtService);
        txtMnthlyIn = view.findViewById(R.id.txt_monthlyInc);
        txtMnthlyEx = view.findViewById(R.id.txt_monthlyExp);
        txtProvnc = view.findViewById(R.id.txt_province);
        txtTownxx = view.findViewById(R.id.txt_town);
        btnPrvs = view.findViewById(R.id.btn_creditAppPrvs);
        btnNext = view.findViewById(R.id.btn_creditAppNext);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(VMSelfEmployedInfo.class);
        String TransNox = Activity_CreditApplication.getInstance().getTransNox();
        mViewModel.setTransNox(TransNox);
        mViewModel.getCreditApplicantInfo().observe(getViewLifecycleOwner(), eCreditApplicantInfo -> mViewModel.setGOCasDetailInfo(eCreditApplicantInfo.getDetlInfo()));
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

        btnNext.setOnClickListener(view -> {
            infoModel.setNatureOfBusiness(spnBussNtr.getSelectedItem().toString());
            infoModel.setNameOfBusiness(txtBussName.getText().toString());
            infoModel.setBusinessAddress(txtBussAdds.getText().toString());
            infoModel.setLengthOfService(txtLnghtSrv.getText().toString());
            infoModel.setMonthlyIncome(txtMnthlyIn.getText().toString());
            infoModel.setMonthlyExpense(txtMnthlyEx.getText().toString());
            mViewModel.SaveSelfEmployedInfo(infoModel, Fragment_SelfEmployedInfo.this);
        });

        btnPrvs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void onSaveSuccessResult(String args) {

    }

    @Override
    public void onFailedResult(String message) {

    }
}