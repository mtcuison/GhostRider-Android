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

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.Button;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.guanzongroup.onlinecreditapplication.Activity.Activity_CreditApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Adapter.PersonalReferencesAdapter;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.Model.OtherInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.PersonalReferenceInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMOtherInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Fragment_OtherInfo extends Fragment implements ViewModelCallBack {

    private static final String TAG = Fragment_OtherInfo.class.getSimpleName();
    private static final int MOBILE_DIALER = 104;
    private VMOtherInfo mViewModel;

    private String TownID = "";
    private String psProvIdx;

    private PersonalReferencesAdapter adapter;
    private OtherInfoModel otherInfo;
    private AutoCompleteTextView spnUnitUser;
    private TextInputLayout tilOthrUser;
    private AutoCompleteTextView spnUnitPrps;
    private AutoCompleteTextView spnUnitPayr;
    private TextInputLayout tilOthrPayr;
    private AutoCompleteTextView spnSourcexx;
    private TextInputLayout tilOtherSrc;
    private AutoCompleteTextView spnOthrUser;
    private AutoCompleteTextView spnOthrPayr;

    private TextInputEditText tieOthrSrc;
    private TextInputEditText tieRefAdd1;
    private TextInputEditText tieRefName;
    private TextInputEditText tieRefCntc;
    private AutoCompleteTextView tieAddProv;
    private AutoCompleteTextView tieAddTown;
    private RecyclerView recyclerView;
    private MaterialButton btnAddReferencex;
    private Button btnPrevs;
    private Button btnNext;

    public Fragment_OtherInfo() {
        // Required empty public constructor
    }

    public static Fragment_OtherInfo newInstance() {
        return new Fragment_OtherInfo();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_other_info, container, false);
        otherInfo = new OtherInfoModel();
        setupWidgets(v);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(VMOtherInfo.class);
        mViewModel.setTransNox(Activity_CreditApplication.getInstance().getTransNox());
        mViewModel.getCreditApplicationInfo().observe(getViewLifecycleOwner(), eCreditApplicantInfo -> {
            try {
                mViewModel.setCreditApplicantInfo(eCreditApplicantInfo);
                setFieldValues(eCreditApplicantInfo);
            } catch(NullPointerException e) {
                e.printStackTrace();
            }
        });
        mViewModel.getReferenceList().observe(getViewLifecycleOwner(), personalReferenceInfoModels -> {
            if (personalReferenceInfoModels != null){
                adapter = new PersonalReferencesAdapter(personalReferenceInfoModels, new PersonalReferencesAdapter.OnAdapterClick() {
                    @Override
                    public void onRemove(int position) {
                        mViewModel.removeReference(position);
                        GToast.CreateMessage(getActivity(), "Reference removed from list.", GToast.INFORMATION).show();
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCallMobile(String fsMobileN) {
                        Intent mobileIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", fsMobileN, null));
                        startActivityForResult(mobileIntent, MOBILE_DIALER);
                    }
                });
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

        mViewModel.getUnitUser().observe(getViewLifecycleOwner(), adapter->{
            spnUnitUser.setAdapter(adapter);
            spnUnitUser.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        });
        mViewModel.getOtherUnitUser().observe(getViewLifecycleOwner(), adapter->{
            spnOthrUser.setAdapter(adapter);
            spnOthrUser.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        });
        mViewModel.getUnitPurpose().observe(getViewLifecycleOwner(), adapter->{
            spnUnitPrps.setAdapter(adapter);
            spnUnitPrps.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        });
        mViewModel.getUnitUser().observe(getViewLifecycleOwner(), adapter->{
            spnUnitPayr.setAdapter(adapter);
            spnUnitPayr.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        });
        mViewModel.getPayerBuyer().observe(getViewLifecycleOwner(), adapter->{
            spnOthrPayr.setAdapter(adapter);
            spnOthrPayr.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        });
        mViewModel.getIntCompanyInfoSource().observe(getViewLifecycleOwner(), adapter->{
            spnSourcexx.setAdapter(adapter);
            spnSourcexx.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        });
//        spnUnitUser.setAdapter(mViewModel.getUnitUser());
//        spnOthrUser.setAdapter(mViewModel.getOtherUnitUser());
//        spnUnitPrps.setAdapter(mViewModel.getUnitPurpose());
//        spnUnitPayr.setAdapter(mViewModel.getUnitUser());
//        spnOthrPayr.setAdapter(mViewModel.getPayerBuyer());
//        spnSourcexx.setAdapter(mViewModel.getIntCompanyInfoSource());
//        dropdown background color
//        spnUnitUser.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
//        spnOthrUser.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
//        spnUnitPrps.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
//        spnUnitPayr.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
//        spnOthrPayr.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
//        spnSourcexx.setDropDownBackgroundResource(R.drawable.bg_gradient_light);

        spnUnitUser.setOnItemClickListener(new Fragment_OtherInfo.SpinnerSelectionListener(spnUnitUser));
        spnUnitPayr.setOnItemClickListener(new Fragment_OtherInfo.SpinnerSelectionListener(spnUnitPayr));
        spnSourcexx.setOnItemClickListener(new Fragment_OtherInfo.SpinnerSelectionListener(spnSourcexx));

        spnUnitPrps.setOnItemClickListener(new Fragment_OtherInfo.SpinnerSelectionListener(spnUnitPrps));
        spnOthrUser.setOnItemClickListener(new Fragment_OtherInfo.SpinnerSelectionListener(spnOthrUser));
        spnOthrPayr.setOnItemClickListener(new Fragment_OtherInfo.SpinnerSelectionListener(spnOthrPayr));
        mViewModel.getProvinceNameList().observe(getViewLifecycleOwner(), strings -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
            tieAddProv.setAdapter(adapter);
            tieAddProv.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        });

        tieAddProv.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getProvinceInfoList().observe(getViewLifecycleOwner(), provinceInfos -> {
            for(int x = 0; x < provinceInfos.size(); x++){
                if(tieAddProv.getText().toString().equalsIgnoreCase(provinceInfos.get(x).getProvName())){
                    mViewModel.setProvID(provinceInfos.get(x).getProvIDxx());
                    psProvIdx = provinceInfos.get(x).getProvIDxx();
                    break;
                }
            }

            mViewModel.getAllTownNames().observe(getViewLifecycleOwner(), strings -> {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
                tieAddTown.setAdapter(adapter);
                tieAddTown.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
            });
        }));

        tieAddTown.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getTownInfoList().observe(getViewLifecycleOwner(), townInfoList -> {
            for(int x = 0; x < townInfoList.size(); x++){
                if(tieAddTown.getText().toString().equalsIgnoreCase(townInfoList.get(x).getTownName())
                        && townInfoList.get(x).getProvIDxx().equalsIgnoreCase(psProvIdx)){
                    TownID =  townInfoList.get(x).getTownIDxx();
                    break;
                }
            }
        }));
    }

    private void setupWidgets(View view){
        spnUnitUser = view.findViewById(R.id.spinner_cap_unitUser);
        tilOthrUser = view.findViewById(R.id.til_cap_otherUser);
        spnUnitPrps = view.findViewById(R.id.spinner_cap_purposeOfBuying);
        spnUnitPayr = view.findViewById(R.id.spinner_cap_monthlyPayer);
        tilOthrPayr = view.findViewById(R.id.til_cap_otherPayer);
        spnOthrUser = view.findViewById(R.id.spinner_cap_otherUser);
        spnOthrPayr = view.findViewById(R.id.spinner_cap_otherPayer);
        spnSourcexx = view.findViewById(R.id.spinner_cap_source);
        tieOthrSrc = view.findViewById(R.id.tie_cap_otherSource);
        tilOtherSrc = view.findViewById(R.id.til_cap_otherSource);
        tieRefName = view.findViewById(R.id.tie_cap_referenceName);
        tieRefCntc = view.findViewById(R.id.tie_cap_refereceContact);
        tieRefAdd1 = view.findViewById(R.id.tie_cap_refereceAddress);
        tieAddProv = view.findViewById(R.id.tie_cap_referenceAddProv);
        tieAddTown = view.findViewById(R.id.tie_cap_referenceAddTown);
        recyclerView = view.findViewById(R.id.recyclerview_references);
        btnAddReferencex = view.findViewById(R.id.btn_fragment_others_addReference);

        tilOthrUser.setVisibility(View.GONE);
        tilOthrPayr.setVisibility(View.GONE);
        tilOtherSrc.setVisibility(View.GONE);
        btnNext = view.findViewById(R.id.btn_creditAppNext);
        btnPrevs = view.findViewById(R.id.btn_creditAppPrvs);

        btnPrevs.setOnClickListener(v -> Activity_CreditApplication.getInstance().moveToPageNumber(13));
        btnAddReferencex.setOnClickListener(v -> {
            addReference();
        });
        btnNext.setOnClickListener(v -> {
            otherInfo.setCompanyInfoSource(Objects.requireNonNull(tieOthrSrc.getText()).toString());
            otherInfo.setTransNox(Activity_CreditApplication.getInstance().getTransNox());
            mViewModel.SubmitOtherInfo(otherInfo, Fragment_OtherInfo.this);
        });
    }

    private void addReference(){
        try {
            mViewModel.getLiveTownProvinceNames(TownID).observe(getViewLifecycleOwner(), townProvNme -> {
                String lsTownPrv = townProvNme.sTownName + ", " +townProvNme.sProvName;
                String refName = (Objects.requireNonNull(tieRefName.getText()).toString());
                String refContact = (Objects.requireNonNull(tieRefCntc.getText()).toString());
                String refAddress = (Objects.requireNonNull(tieRefAdd1.getText()).toString());
                PersonalReferenceInfoModel poRefInfo = new PersonalReferenceInfoModel(refName, refAddress, lsTownPrv, refContact);
                mViewModel.addReference(poRefInfo, new VMOtherInfo.AddPersonalInfoListener() {
                    @Override
                    public void OnSuccess() {
                        tieRefName.setText("");
                        tieRefCntc.setText("");
                        tieRefAdd1.setText("");
                        tieAddProv.setText("");
                        tieAddTown.setText("");
                        TownID = "";
                    }

                    @Override
                    public void onFailed(String message) {
                        GToast.CreateMessage(getContext(), message, GToast.ERROR).show();
                    }
                });
            });
        }catch (NullPointerException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();
    }

    @SuppressLint("NewApi")
    private void setFieldValues(ECreditApplicantInfo foCredApp) {
        if(foCredApp.getOthrInfo() != null) {
            try {
                JSONObject loJson = new JSONObject(foCredApp.getOthrInfo());
                Log.e(TAG + " jsonCon", loJson.toString());
                spnUnitUser.setText(CreditAppConstants.UNIT_USER[Integer.parseInt(loJson.getString("sUnitUser"))],false);
                otherInfo.setsUnitUser(loJson.getString("sUnitUser"));
                if(Integer.parseInt(loJson.getString("sUnitUser")) == 1){
                    spnOthrUser.setText(CreditAppConstants.UNIT_USER_OTHERS[Integer.parseInt(loJson.getString("sUsr2Buyr"))],false);
                    tilOthrUser.setVisibility(View.VISIBLE);
                    otherInfo.setsUsr2Buyr(loJson.getString("sUsr2Buyr"));
                } else {
                    tilOthrUser.setVisibility(View.GONE);
                }

                spnUnitPayr.setText(CreditAppConstants.UNIT_USER[Integer.parseInt(loJson.getString("sUnitPayr"))],false);
                otherInfo.setsUnitPayr(loJson.getString("sUnitPayr"));
                if(Integer.parseInt(loJson.getString("sUnitPayr")) == 1){
                    spnOthrPayr.setText(CreditAppConstants.UNIT_PURPOSE[Integer.parseInt(loJson.getString("sPyr2Buyr"))],false);
                    tilOthrPayr.setVisibility(View.VISIBLE);
                    otherInfo.setsPyr2Buyr(loJson.getString("sUnitPayr"));
                } else {
                    tilOthrPayr.setVisibility(View.GONE);
                }
                spnSourcexx.setText(loJson.getString("sSrceInfo"),false);
                spnUnitPrps.setText(CreditAppConstants.UNIT_PURPOSE[Integer.parseInt(loJson.getString("sPurposex"))],false);

                otherInfo.setsPurposex(loJson.getString("sPurposex"));

                otherInfo.setSource(loJson.getString("sSrceInfo"));

                JSONArray loJsonArr = loJson.getJSONArray("personal_reference");
                if(loJsonArr.length() > 0) {
                    List<PersonalReferenceInfoModel> poRef = new ArrayList<>();
                    for (int x = 0; x < loJsonArr.length(); x++) {
                        JSONObject loJsonRef = loJsonArr.getJSONObject(x);
                        String fullname = loJsonRef.getString("sRefrNmex");
                        String address1 = loJsonRef.getString("sRefrAddx");
                        String townCity = loJsonRef.getString("sRefrTown");
                        String contactN = loJsonRef.getString("sRefrMPNx");
                        PersonalReferenceInfoModel loRefs = new PersonalReferenceInfoModel(fullname,
                                address1, townCity, contactN);
                        if (!loRefs.isDataValid()) {
                            loJsonArr.remove(x);
                        }else {
                            poRef.add(loRefs);
                        };
                    }
                    mViewModel.setRetrievedReferenceList(poRef);
                }

            } catch(JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onSaveSuccessResult(String args) {
        Activity_CreditApplication.getInstance().moveToPageNumber(16);
    }

    @Override
    public void onFailedResult(String message) {
        GToast.CreateMessage(getActivity(), message, GToast.ERROR).show();
    }

    class SpinnerSelectionListener implements AdapterView.OnItemClickListener{
        private final AutoCompleteTextView poView;
        SpinnerSelectionListener(AutoCompleteTextView view){
            this.poView = view;
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if(spnUnitUser.equals(poView)){
                otherInfo.setsUnitUser(String.valueOf(i));
                if(i == 1){
                    tilOthrUser.setVisibility(View.VISIBLE);
                } else {
                    otherInfo.setsUsr2Buyr(null);
                    tilOthrUser.setVisibility(View.GONE);
                }
            } else if(spnUnitPayr.equals(poView)){
                otherInfo.setsUnitPayr(String.valueOf(i));
                if(i == 1){
                    tilOthrPayr.setVisibility(View.VISIBLE);
                } else {
                    otherInfo.setsPyr2Buyr(null);
                    tilOthrPayr.setVisibility(View.GONE);
                }
            } else if(spnSourcexx.equals(poView)){
                otherInfo.setSource(spnSourcexx.getText().toString());
                if(i == 5){
                    tilOtherSrc.setVisibility(View.VISIBLE);
                } else {
                    tilOtherSrc.setVisibility(View.GONE);
                }
            } else if(spnUnitPrps.equals(poView)){
                otherInfo.setsPurposex(String.valueOf(i));
            } else if(spnOthrUser.equals(poView)){
                otherInfo.setsUsr2Buyr(String.valueOf(i));
            } else if(spnOthrPayr.equals(poView)){
                otherInfo.setsPyr2Buyr(String.valueOf(i));
            }
        }
    }
}