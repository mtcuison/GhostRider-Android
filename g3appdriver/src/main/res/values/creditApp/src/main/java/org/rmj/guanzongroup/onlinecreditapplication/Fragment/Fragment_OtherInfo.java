package org.rmj.guanzongroup.onlinecreditapplication.Fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.android.material.textfield.TextInputLayout;

import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.gocas.base.GOCASApplication;
import org.rmj.gocas.pojo.OtherInfo;
import org.rmj.guanzongroup.onlinecreditapplication.Activity.Activity_CreditApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Adapter.PersonalReferencesAdapter;
import org.rmj.guanzongroup.onlinecreditapplication.Model.DisbursementInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.OtherInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMDisbursementInfo;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMOtherInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.LongFunction;

public class Fragment_OtherInfo extends Fragment implements ViewModelCallBack, VMOtherInfo.ExpActionListener {

    private static final String TAG = Fragment_OtherInfo.class.getSimpleName();
    private VMOtherInfo mViewModel;
    @SuppressLint("StaticFieldLeak")
    private static Fragment_OtherInfo instance;
//    private AutoSuggestAddress address;

    private String TransNox;
    private View v;
    private String ProvID = "";
    private String TownID = "";
//    private List<PersonalReferences> personalReferencesList;
//    private ReferencesAdapter adapter;

    private List<OtherInfoModel> personalReferencesList;
    private PersonalReferencesAdapter adapter;
    private OtherInfoModel otherInfo;
    private Spinner spnUnitUser;
    private Spinner spnUnitPrps;
    private Spinner spnUnitPayr;
    private Spinner spnSourcexx;
    private Spinner spnUserBuyr;
    private Spinner spnPayrBuyr;
    private TextInputLayout tilSpcfSrc;
    private TextInputLayout tilRefname;
    private TextInputLayout tilRefCntc;
    private TextInputLayout tilAddProv;
    private TextInputLayout tilAddTown;
    private TextInputEditText tieSpcfSrc;
    private TextInputEditText tieRefAdd1;
    private TextInputEditText tieRefName;
    private TextInputEditText tieRefCntc;
    private AutoCompleteTextView tieAddProv;
    private AutoCompleteTextView tieAddTown;
    private RecyclerView recyclerView;
    private MaterialButton btnAddReferencex;
    private MaterialButton btnPrevs;
    private MaterialButton btnNext;

    public Fragment_OtherInfo() {
        // Required empty public constructor
    }

    public static Fragment_OtherInfo newInstance() {
        return new Fragment_OtherInfo();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_other_info, container, false);
        TransNox = new Activity_CreditApplication().getInstance().getTransNox();
        otherInfo = new OtherInfoModel();
        personalReferencesList = new ArrayList<>();
        setupWidgets(v);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(VMOtherInfo.class);
        mViewModel.setTransNox(Activity_CreditApplication.getInstance().getTransNox());
        mViewModel.getCreditApplicationInfo().observe(getViewLifecycleOwner(), eCreditApplicantInfo -> mViewModel.setCreditApplicantInfo(eCreditApplicantInfo.getDetlInfo()));
        mViewModel.getUnitUser().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnUnitUser.setAdapter(stringArrayAdapter));
        mViewModel.getUnitPurpose().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnUnitPrps.setAdapter(stringArrayAdapter));
        mViewModel.getIntCompanyInfoSource().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnSourcexx.setAdapter(stringArrayAdapter));
        mViewModel.setUserBuyer().observe(getViewLifecycleOwner(), integer -> spnUserBuyr.setVisibility(integer));
        mViewModel.setPayerBuyer().observe(getViewLifecycleOwner(), integer -> spnPayrBuyr.setVisibility(integer));
        mViewModel.setCompanySource().observe(getViewLifecycleOwner(), integer -> tilSpcfSrc.setVisibility(integer));
        mViewModel.getUserBuyer().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnUserBuyr.setAdapter(stringArrayAdapter));
        mViewModel.getPayerBuyer().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnPayrBuyr.setAdapter(stringArrayAdapter));
        mViewModel.getUnitPayer().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnUnitPayr.setAdapter(stringArrayAdapter));

        //Clear Reference after adding
        // TODO: Use the ViewModel
        //RECYCLER VIEW

        mViewModel.getPersonalReference().observe(getViewLifecycleOwner(), referenceListUpdateObserver);



        spnUnitUser.setOnItemSelectedListener(new Fragment_OtherInfo.SpinnerSelectionListener(mViewModel));
        spnUnitPayr.setOnItemSelectedListener(new Fragment_OtherInfo.SpinnerSelectionListener(mViewModel));
        spnSourcexx.setOnItemSelectedListener(new Fragment_OtherInfo.SpinnerSelectionListener(mViewModel));
        mViewModel.getProvinceNameList().observe(getViewLifecycleOwner(), strings -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
            tieAddProv.setAdapter(adapter);
        });

        tieAddProv.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getProvinceInfoList().observe(getViewLifecycleOwner(), provinceInfos -> {
            for(int x = 0; x < provinceInfos.size(); x++){
                if(tieAddProv.getText().toString().equalsIgnoreCase(provinceInfos.get(x).getProvName())){
                    mViewModel.setProvID(provinceInfos.get(x).getProvIDxx());
                    break;
                }
            }

            mViewModel.getAllTownNames().observe(getViewLifecycleOwner(), strings -> {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
                tieAddTown.setAdapter(adapter);
            });
        }));

        tieAddTown.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getTownInfoList().observe(getViewLifecycleOwner(), townInfoList -> {
            for(int x = 0; x < townInfoList.size(); x++){
                if(tieAddTown.getText().toString().equalsIgnoreCase(townInfoList.get(x).getTownName())){
                    mViewModel.setTownID(townInfoList.get(x).getTownIDxx());
                    break;
                }
            }
        }));


    }

    private void setupWidgets(View view){
        spnUnitUser = view.findViewById(R.id.spinner_cap_unitUser);
        spnUnitPrps = view.findViewById(R.id.spinner_cap_purposeOfBuying);
        spnUnitPayr = view.findViewById(R.id.spinner_cap_monthlyPayer);
        spnUserBuyr = view.findViewById(R.id.spinner_cap_sUsr2buyr);
        spnPayrBuyr = view.findViewById(R.id.spinner_cap_sPyr2Buyr);
        spnSourcexx = view.findViewById(R.id.spinner_cap_source);
        tilSpcfSrc = view.findViewById(R.id.til_cap_CompanyInfoSource);
        tilRefname = view.findViewById(R.id.til_cap_referenceName);
        tilRefCntc = view.findViewById(R.id.til_cap_referenceContact);
        tilAddProv = view.findViewById(R.id.til_cap_referenceAddProv);
        tilAddTown = view.findViewById(R.id.til_cap_referenceAddTown);
        tieSpcfSrc = view.findViewById(R.id.tie_cap_CompanyInfoSource);
        tieRefName = view.findViewById(R.id.tie_cap_referenceName);
        tieRefCntc = view.findViewById(R.id.tie_cap_refereceContact);
        tieRefAdd1 = view.findViewById(R.id.tie_cap_refereceAddress);
        tieAddProv = view.findViewById(R.id.tie_cap_referenceAddProv);
        tieAddTown = view.findViewById(R.id.tie_cap_referenceAddTown);
        recyclerView = view.findViewById(R.id.recyclerview_references);
        btnAddReferencex = view.findViewById(R.id.btn_fragment_others_addReference);
        //btnPrevs = view.findViewById(R.id.btn_fragment_others_prevs);
        btnNext = view.findViewById(R.id.btn_creditAppNext);


        btnAddReferencex.setOnClickListener(v -> addReference());
        btnNext.setOnClickListener(v -> submitOtherInfo());
    }

    // Set Recycler view adapter
    Observer<List<OtherInfoModel>> referenceListUpdateObserver = new Observer<List<OtherInfoModel>>() {
        @Override
        public void onChanged(List<OtherInfoModel> userArrayList) {
            adapter = new PersonalReferencesAdapter(userArrayList);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(adapter);
        }
    };
    private void addReference(){
        try {
            String refName = (Objects.requireNonNull(tieRefName.getText()).toString());
            String refContact = (Objects.requireNonNull(tieRefCntc.getText()).toString());
            String refAddress = (Objects.requireNonNull(tieRefAdd1.getText()).toString());
            String refTown = (Objects.requireNonNull(tieAddTown.getText()).toString());
            OtherInfoModel otherInfos = new OtherInfoModel(refName, refAddress, refTown, refContact);
            //personalReferencesList.add(otherInfo);
            mViewModel.addReference(otherInfos, Fragment_OtherInfo.this);
        }catch (NullPointerException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();
    }

    //Submit
    private void submitOtherInfo(){
        otherInfo.setUnitUserModel(Objects.requireNonNull(String.valueOf(spnUnitUser.getSelectedItemPosition() - 1)));
        otherInfo.setUserBuyerModel(String.valueOf(spnUserBuyr.getSelectedItemPosition() - 1));
        otherInfo.setUserUnitPurposeModel(Objects.requireNonNull(String.valueOf(spnUnitPrps.getSelectedItemPosition() - 1)));
        otherInfo.setMonthlyPayerModel(Objects.requireNonNull(String.valueOf(spnUnitPayr.getSelectedItemPosition() - 1)));
        otherInfo.setPayer2BuyerModel(String.valueOf(spnPayrBuyr.getSelectedItemPosition() - 1));
        Log.e("Source Value", spnSourcexx.getSelectedItem().toString());
        otherInfo.setSourceModel(Objects.requireNonNull(spnSourcexx.getSelectedItem().toString()));
        otherInfo.setCompanyInfoSourceModel(tieSpcfSrc.getText().toString());
        mViewModel.SubmitOtherInfo(otherInfo, Fragment_OtherInfo.this);
    }
    @Override
    public void onSuccess(String message) {
        Log.e(TAG, message);
        tieRefName.setText("");
        tieRefCntc.setText("");
        tieRefAdd1.setText("");
        tieAddProv.setText("");
        tieAddTown.setText("");
    }

    @Override
    public void onFailed(String message) {
        Log.e(TAG, message);
        GToast.CreateMessage(getActivity(), message, GToast.ERROR).show();
    }

    @Override
    public void onSaveSuccessResult(String args) {
        Activity_CreditApplication.getInstance().moveToPageNumber(2);
    }

    @Override
    public void onFailedResult(String message) {
        GToast.CreateMessage(getActivity(), message, GToast.ERROR).show();
    }

    private static class SpinnerSelectionListener implements AdapterView.OnItemSelectedListener{
        private final VMOtherInfo vm;

        SpinnerSelectionListener(VMOtherInfo viewModel){
            this.vm = viewModel;
        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            if(adapterView.getId() == R.id.spinner_cap_unitUser){
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
                vm.setUnitUser(type);
            }
            if(adapterView.getId() == R.id.spinner_cap_monthlyPayer){
                String monthlyPayer = "";
                switch (i){
                    case 0:
                        break;
                    case 1:
                        monthlyPayer = "0";
                        break;
                    case 2:
                        monthlyPayer = "1";
                        break;

                }
                vm.setUnitPayer(monthlyPayer);
            }
            if(adapterView.getId() == R.id.spinner_cap_source){
                String source = "";
                switch (i){
                    case 0:
                        break;
                    case 1:
                        source = "0";
                        break;
                    case 2:
                        source = "1";
                        break;
                    case 3:
                        source = "2";
                        break;
                    case 4:
                        source = "3";
                        break;
                    case 5:
                        source = "4";
                        break;
                    case 6:
                        source = "5";
                        break;

                }
                vm.setIntCompanyInfoSource(source);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }
}