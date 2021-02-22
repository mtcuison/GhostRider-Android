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
    private AutoCompleteTextView spnUnitUser;
    private AutoCompleteTextView spnUnitPrps;
    private AutoCompleteTextView spnUnitPayr;
    private AutoCompleteTextView spnSourcexx;
    private AutoCompleteTextView spnUserBuyr;
    private AutoCompleteTextView spnPayrBuyr;
    //autocomplete textview position

    private String unitUserX = "-1";
    private String unitPrpsX = "-1";
    private String unitPayrX = "-1";
    private String sourcexxX = "-1";
    private String userBuyrX = "-1";
    private String payrBuyrX = "-1";

    private TextInputLayout tilUserBuyr;
    private TextInputLayout tilPayrBuyr;
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
        mViewModel.setUserBuyer().observe(getViewLifecycleOwner(), integer -> tilUserBuyr.setVisibility(integer));
        mViewModel.setPayerBuyer().observe(getViewLifecycleOwner(), integer -> tilPayrBuyr.setVisibility(integer));
        mViewModel.setCompanySource().observe(getViewLifecycleOwner(), integer -> tilSpcfSrc.setVisibility(integer));
        mViewModel.getUserBuyer().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnUserBuyr.setAdapter(stringArrayAdapter));
        mViewModel.getPayerBuyer().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnPayrBuyr.setAdapter(stringArrayAdapter));
        mViewModel.getUnitPayer().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnUnitPayr.setAdapter(stringArrayAdapter));

        //Clear Reference after adding
        // TODO: Use the ViewModel
        //RECYCLER VIEW

        mViewModel.getPersonalReference().observe(getViewLifecycleOwner(), referenceListUpdateObserver);


        mViewModel.getSUnitUser().observe(getViewLifecycleOwner(), s -> {
            spnUnitUser.setSelection(Integer.parseInt(s));
            unitUserX = s;
            Log.e("Mobile 1", s);
        });


        mViewModel.getSPayerBuyer().observe(getViewLifecycleOwner(), s -> {
            spnPayrBuyr.setSelection(Integer.parseInt(s));
            payrBuyrX = s;
            Log.e("Mobile 1", s);
        });

        mViewModel.getSUnitPayer().observe(getViewLifecycleOwner(), s -> {
            spnUnitPayr.setSelection(Integer.parseInt(s));
            unitPayrX = s;
            Log.e("Mobile 1", s);
        });

        mViewModel.getSUnitPurpose().observe(getViewLifecycleOwner(), s -> {
            spnUnitPrps.setSelection(Integer.parseInt(s));
            unitPrpsX = s;
            Log.e("Mobile 1", s);
        });

        mViewModel.getSUserBuyer().observe(getViewLifecycleOwner(), s -> {
            spnUserBuyr.setSelection(Integer.parseInt(s));
            userBuyrX = s;
            Log.e("Mobile 1", s);
        });

        mViewModel.getSCompanyInfoSource().observe(getViewLifecycleOwner(), s -> {
            spnSourcexx.setSelection(Integer.parseInt(s));
            sourcexxX = s;
            Log.e("Mobile 1", s);
        });

        spnUnitUser.setOnItemClickListener(new Fragment_OtherInfo.SpinnerSelectionListener(spnUnitUser, mViewModel));
        spnUnitPayr.setOnItemClickListener(new Fragment_OtherInfo.SpinnerSelectionListener(spnUnitPayr, mViewModel));
        spnSourcexx.setOnItemClickListener(new Fragment_OtherInfo.SpinnerSelectionListener(spnSourcexx, mViewModel));

        spnUnitPrps.setOnItemClickListener(new Fragment_OtherInfo.SpinnerSelectionListener(spnUnitPrps, mViewModel));
        spnUserBuyr.setOnItemClickListener(new Fragment_OtherInfo.SpinnerSelectionListener(spnUserBuyr, mViewModel));
        spnPayrBuyr.setOnItemClickListener(new Fragment_OtherInfo.SpinnerSelectionListener(spnPayrBuyr, mViewModel));
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
        tilUserBuyr = view.findViewById(R.id.til_cap_sUsr2buyr);
        spnPayrBuyr = view.findViewById(R.id.spinner_cap_sPyr2Buyr);
        tilPayrBuyr = view.findViewById(R.id.til_cap_sPyr2Buyr);
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
        otherInfo.setUnitUserModel(Objects.requireNonNull(unitUserX));
        otherInfo.setUserBuyerModel(userBuyrX);
        otherInfo.setUserUnitPurposeModel(Objects.requireNonNull(unitPrpsX));
        otherInfo.setMonthlyPayerModel(Objects.requireNonNull(unitPayrX));
        otherInfo.setPayer2BuyerModel(payrBuyrX);
        Log.e("Source Value", sourcexxX);
        otherInfo.setSourceModel(Objects.requireNonNull(spnSourcexx.getText().toString()));
        otherInfo.setCompanyInfoSourceModel(tieSpcfSrc.getText().toString());
        mViewModel.SubmitOtherInfo(otherInfo, Fragment_OtherInfo.this);
        Log.e("Unit user",unitUserX);
        Log.e("Unit buyer",userBuyrX);
        Log.e("Unit purpose",unitPrpsX);
        Log.e("Unit payer",unitPayrX);
        Log.e("payer buyer",sourcexxX);
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
        Activity_CreditApplication.getInstance().moveToPageNumber(15);
    }

    @Override
    public void onFailedResult(String message) {
        GToast.CreateMessage(getActivity(), message, GToast.ERROR).show();
    }

    class SpinnerSelectionListener implements AdapterView.OnItemClickListener{
        private final VMOtherInfo vm;
        private final AutoCompleteTextView poView;
        SpinnerSelectionListener(AutoCompleteTextView view,VMOtherInfo viewModel){
            this.vm = viewModel;
            this.poView = view;
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


            if(spnUnitUser.equals(poView)){
                unitUserX = String.valueOf(i);
                vm.setUnitUser(unitUserX);
            }
            if(spnUnitPayr.equals(poView)){
                unitPayrX = String.valueOf(i);
                vm.setUnitPayer(unitPayrX);
            }
            if(spnSourcexx.equals(poView)){
                sourcexxX = String.valueOf(i);
                vm.setIntCompanyInfoSource(sourcexxX);
            }
            if(spnUnitPrps.equals(poView)){
                unitPrpsX = String.valueOf(i);
                vm.setSUnitPurpose(unitPrpsX);
            }
            if(spnUserBuyr.equals(poView)){
                userBuyrX = String.valueOf(i);
                vm.setSUserBuyer(userBuyrX);
            }
            if(spnPayrBuyr.equals(poView)){
                payrBuyrX = String.valueOf(i);
                vm.setSPayerBuyer(payrBuyrX);
            }
        }
    }
}