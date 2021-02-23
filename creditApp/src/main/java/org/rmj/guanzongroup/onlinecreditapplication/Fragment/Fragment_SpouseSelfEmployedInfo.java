package org.rmj.guanzongroup.onlinecreditapplication.Fragment;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

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
import android.widget.Spinner;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.GRider.Database.Entities.EProvinceInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ETownInfo;
import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.guanzongroup.onlinecreditapplication.Activity.Activity_CreditApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Model.SpouseSelfEmployedInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMSpouseSelfEmployedInfo;

import java.util.List;
import java.util.Objects;

public class Fragment_SpouseSelfEmployedInfo extends Fragment implements ViewModelCallBack {
    private static final String TAG = Fragment_SpouseSelfEmployedInfo.class.getSimpleName();
    private VMSpouseSelfEmployedInfo mViewModel;
    private SpouseSelfEmployedInfoModel infoModel;
    private TextInputEditText txtBizName, txtBizAddrss, txtBizLength, txtMonthlyInc, txtMonthlyExp;
    private AppCompatAutoCompleteTextView spnBizIndustry, spnMonthOrYr, txtProvince, txtTown, spnBizType, spnBizSize;
    private MaterialButton btnNext;

    public static Fragment_SpouseSelfEmployedInfo newInstance() {
        return new Fragment_SpouseSelfEmployedInfo();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spouse_self_employed_info, container, false);
        infoModel = new SpouseSelfEmployedInfoModel();
        initWidgets(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(VMSpouseSelfEmployedInfo.class);
        mViewModel.setTransNox(Activity_CreditApplication.getInstance().getTransNox());
        mViewModel.getActiveGOCasApplication().observe(getViewLifecycleOwner(), eCreditApplicantInfo -> mViewModel.setDetailInfo(eCreditApplicantInfo.getDetlInfo()));

        mViewModel.getProvinceName().observe(getViewLifecycleOwner(), strings -> {
                try{
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
                    txtProvince.setAdapter(adapter);
                } catch (Exception e){
                    e.printStackTrace();
                }
         });

        txtProvince.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mViewModel.getProvinceInfo().observe(getViewLifecycleOwner(), new Observer<List<EProvinceInfo>>() {
                    @Override
                    public void onChanged(List<EProvinceInfo> eProvinceInfos) {
                        for(int x = 0; x < eProvinceInfos.size(); x++){
                            if(txtProvince.getText().toString().equalsIgnoreCase(eProvinceInfos.get(x).getProvName())){
                                mViewModel.setProvinceID(eProvinceInfos.get(x).getProvIDxx());
                                break;
                            }
                        }

                        mViewModel.getTownNameList().observe(getViewLifecycleOwner(), strings -> {
                                try{
                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
                                    txtTown.setAdapter(adapter);
                                } catch (Exception e){
                                    e.printStackTrace();
                                }
                        });

                    }
                });
            }
        });

        txtTown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mViewModel.getTownInfoList().observe(getViewLifecycleOwner(), eTownInfos -> {
                        for(int x = 0; x < eTownInfos.size(); x++){
                            if(txtTown.getText().toString().equalsIgnoreCase(eTownInfos.get(x).getTownName())){ //from id to town name
                                mViewModel.setTownID(eTownInfos.get(x).getTownIDxx());
                                break;
                            }
                        }
                });
            }
        });

        mViewModel.getNatureOfBusiness().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnBizIndustry.setAdapter(stringArrayAdapter));
        mViewModel.getTypeOfBusiness().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnBizType.setAdapter(stringArrayAdapter));
        mViewModel.getSizeOfBusiness().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnBizSize.setAdapter(stringArrayAdapter));
        mViewModel.getLengthOfService().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnMonthOrYr.setAdapter(stringArrayAdapter));
    }

    private void initWidgets(View v) {
        spnMonthOrYr = v.findViewById(R.id.spn_monthOrYr);
        txtBizName = v.findViewById(R.id.txt_bizName);
        txtBizAddrss = v.findViewById(R.id.txt_bizAddress);
        txtBizLength = v.findViewById(R.id.txt_bizLength);
        txtMonthlyInc = v.findViewById(R.id.txt_monthlyInc);
        txtMonthlyExp = v.findViewById(R.id.txt_monthlyExp);
        spnBizIndustry = v.findViewById(R.id.spn_bizIndustry);
        txtProvince = v.findViewById(R.id.txt_province);
        txtTown = v.findViewById(R.id.txt_town);
        spnBizType = v.findViewById(R.id.spn_bizType);
        spnBizSize = v.findViewById(R.id.spn_bizSize);
        btnNext = v.findViewById(R.id.btn_creditAppNext);

        spnMonthOrYr.setOnItemClickListener(new OnItemClickListener(spnMonthOrYr));
        spnBizIndustry.setOnItemClickListener(new OnItemClickListener(spnBizIndustry));
        spnBizType.setOnItemClickListener(new OnItemClickListener(spnBizType));
        spnBizSize.setOnItemClickListener(new OnItemClickListener(spnBizSize));

        btnNext.setOnClickListener(view -> save());
    }

    private void save() {
        infoModel.setsBizName(Objects.requireNonNull(txtBizName.getText().toString()));
        infoModel.setsBizAddress(Objects.requireNonNull(txtBizAddrss.getText().toString()));
        infoModel.setsBizYrs(Objects.requireNonNull(txtBizLength.getText().toString()));
        infoModel.setsGrossMonthly(Objects.requireNonNull(txtMonthlyInc.getText().toString()));
        infoModel.setsMonthlyExps(Objects.requireNonNull(txtMonthlyExp.getText().toString()));
        mViewModel.Save(infoModel, Fragment_SpouseSelfEmployedInfo.this);
    }

    @Override
    public void onSaveSuccessResult(String args) {
        Activity_CreditApplication.getInstance().moveToPageNumber(6);
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
            if (spnBizIndustry.equals(poView)) {
                mViewModel.setBizIndustry(String.valueOf(i));
            }
            if (spnBizType.equals(poView)) {
                mViewModel.setBizType(String.valueOf(i));
            }
            if (spnBizSize.equals(poView)) {
                mViewModel.setBizSize(String.valueOf(i));
            }
            if (spnMonthOrYr.equals(poView)) {
                mViewModel.setMosOrYr(String.valueOf(i));
            }
        }
    }

}