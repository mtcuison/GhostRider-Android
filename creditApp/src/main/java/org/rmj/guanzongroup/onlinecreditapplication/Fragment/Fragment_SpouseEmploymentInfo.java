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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.etc.FormatUIText;
import org.rmj.g3appdriver.etc.GToast;
import org.rmj.guanzongroup.onlinecreditapplication.Activity.Activity_CreditApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.Model.EmploymentInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.SpouseEmploymentInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMEmploymentInfo;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMSpouseEmploymentInfo;

import java.util.Objects;

public class Fragment_SpouseEmploymentInfo extends Fragment implements ViewModelCallBack {
    private static final String TAG = Fragment_EmploymentInfo.class.getSimpleName();

    private AutoCompleteTextView spnCmpLvl,
            spnEmpLvl,
            spnBusNtr,
            spnEmpSts,
            spnServce;
    private String spnCmpLvlPosition = "-1",
            spnEmpLvlPosition = "-1",
            spnBusNtrPosition = "-1",
            spnServcePosition = "-1",
            spnEmpStsPosition = "-1";
    private AutoCompleteTextView txtCntryx,
            txtProvNm,
            txtTownNm,
            txtJobNme;
    private TextInputLayout tilCntryx,
            tilCompNm,
            tilJobTitle,
            tilCmpLvl,
            tilBizNature,
            tilEmpLvl;
    private TextInputEditText txtCompNm,
            txtCompAd,
            txtSpcfJb,
            txtLngthS,
            txtEsSlry,
            txtCompCn;
    private LinearLayout lnGovInfo,
            lnEmpInfo;
    private Button btnNext;

    private TextView lblBizNature;

    private RadioGroup rgSectorx, rgUniform, rgMiltary;

    private VMSpouseEmploymentInfo mViewModel;
    private SpouseEmploymentInfoModel infoModel;

    public static Fragment_SpouseEmploymentInfo newInstance() {
        return new Fragment_SpouseEmploymentInfo();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spouse_employment_info, container, false);
        infoModel = new SpouseEmploymentInfoModel();
        initWidgets(view);
        return view;
    }

    private void initWidgets(View v) {
        rgSectorx = v.findViewById(R.id.rg_sector);
        rgUniform = v.findViewById(R.id.rg_uniformPersonel);
        rgMiltary = v.findViewById(R.id.rg_militaryPersonal);
        lblBizNature = v.findViewById(R.id.lbl_biz_nature);
        spnCmpLvl = v.findViewById(R.id.spn_employmentLevel);
        spnEmpLvl = v.findViewById(R.id.spn_employeeLevel);
        spnBusNtr = v.findViewById(R.id.spn_businessNature);
        spnEmpSts = v.findViewById(R.id.spn_employmentStatus);
        spnServce = v.findViewById(R.id.spn_lengthService);
        txtCntryx = v.findViewById(R.id.txt_countryNme);
        txtProvNm = v.findViewById(R.id.txt_province);
        txtTownNm = v.findViewById(R.id.txt_town);
        txtJobNme = v.findViewById(R.id.txt_jobPosition);
        tilCntryx = v.findViewById(R.id.til_countryNme);
        tilCompNm = v.findViewById(R.id.til_companyNme);
        tilJobTitle = v.findViewById(R.id.til_job_title);
        tilCmpLvl = v.findViewById(R.id.til_cmpLevel);
        tilBizNature = v.findViewById(R.id.til_bizNature);
        tilEmpLvl = v.findViewById(R.id.til_empLvl);
        txtCompNm = v.findViewById(R.id.txt_companyNme);
        txtCompAd = v.findViewById(R.id.txt_companyAdd);
        txtSpcfJb = v.findViewById(R.id.txt_specificJob);
        txtLngthS = v.findViewById(R.id.txt_lenghtService);
        txtEsSlry = v.findViewById(R.id.txt_monthlySalary);
        txtCompCn = v.findViewById(R.id.txt_companyContact);
        lnGovInfo = v.findViewById(R.id.linear_governmentSector);
        lnEmpInfo = v.findViewById(R.id.linear_employmentInfo);
        Button btnPrvs = v.findViewById(R.id.btn_creditAppPrvs);
        btnNext = v.findViewById(R.id.btn_creditAppNext);

        rgSectorx.setOnCheckedChangeListener(new Fragment_SpouseEmploymentInfo.OnRadioButtonSelectListener());
        rgUniform.setOnCheckedChangeListener(new Fragment_SpouseEmploymentInfo.OnRadioButtonSelectListener());
        rgMiltary.setOnCheckedChangeListener(new Fragment_SpouseEmploymentInfo.OnRadioButtonSelectListener());
       // spnEmpSts.setOnItemSelectedListener(new Fragment_SpouseEmploymentInfo.OnJobStatusSelectedListener());

        txtEsSlry.addTextChangedListener(new FormatUIText.CurrencyFormat(txtEsSlry));

        btnPrvs.setOnClickListener(view -> Activity_CreditApplication.getInstance().moveToPageNumber(8));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(VMSpouseEmploymentInfo.class);
        mViewModel.setTransNox(Activity_CreditApplication.getInstance().getTransNox());

        mViewModel.getActiveGOCasApplication().observe(getViewLifecycleOwner(), eCreditApplicantInfo -> {
            try {
                mViewModel.setDetailInfo(eCreditApplicantInfo);
                setFieldValues(eCreditApplicantInfo);
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();;
            }
        });

        mViewModel.getCompanyLevelList().observe(getViewLifecycleOwner(), stringArrayAdapter -> {
            spnCmpLvl.setAdapter(stringArrayAdapter);
            spnCmpLvl.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        });
        mViewModel.getEmployeeLevelList().observe(getViewLifecycleOwner(), stringArrayAdapter -> {
            spnEmpLvl.setAdapter(stringArrayAdapter);
            spnEmpLvl.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        });
        mViewModel.getBusinessNature().observe(getViewLifecycleOwner(), stringArrayAdapter -> {
            spnBusNtr.setAdapter(stringArrayAdapter);
            spnBusNtr.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        });
        mViewModel.getPsCmpLvl().observe(getViewLifecycleOwner(), s -> {
            spnCmpLvl.setSelection(s.length());
            Log.e("company ", s);
        });
        mViewModel.getPsEmpLvl().observe(getViewLifecycleOwner(), s -> {
            spnEmpLvl.setSelection(s.length());
            Log.e("Employee ", s);
        });
        mViewModel.getPsBsnssLvl().observe(getViewLifecycleOwner(), s -> {
            spnBusNtr.setSelection(s.length());
            Log.e("Business ", s);
        });
        mViewModel.getCountryNameList().observe(getViewLifecycleOwner(), strings -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
            txtCntryx.setAdapter(adapter);
            txtCntryx.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        });
        txtCntryx.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getCountryInfoList().observe(getViewLifecycleOwner(), countryInfos -> {
            for (int x = 0; x < countryInfos.size(); x++) {
                if (txtCntryx.getText().toString().equalsIgnoreCase(countryInfos.get(x).getCntryNme())) {
                    mViewModel.setCountry(countryInfos.get(x).getCntryCde());
                    break;
                }
            }
        }));
        mViewModel.getProvinceName().observe(getViewLifecycleOwner(), strings -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
            txtProvNm.setAdapter(adapter);
            txtProvNm.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        });

        txtProvNm.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getProvinceInfo().observe(getViewLifecycleOwner(), eProvinceInfos -> {
            for (int x = 0; x < eProvinceInfos.size(); x++) {
                if (txtProvNm.getText().toString().equalsIgnoreCase(eProvinceInfos.get(x).getProvName())) {
                    mViewModel.setProvinceID(eProvinceInfos.get(x).getProvIDxx());
                    break;
                }
            }

            mViewModel.getTownNameList().observe(getViewLifecycleOwner(), strings -> {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
                txtTownNm.setAdapter(adapter);
                txtTownNm.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
            });
        }));

        txtTownNm.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getTownInfoList().observe(getViewLifecycleOwner(), eTownInfos -> {
            for (int x = 0; x < eTownInfos.size(); x++) {
                if (txtTownNm.getText().toString().equalsIgnoreCase(eTownInfos.get(x).getTownName())) {
                    mViewModel.setTownID(eTownInfos.get(x).getTownIDxx());
                    break;
                }
            }
        }));

        mViewModel.getJobTitleNameList().observe(getViewLifecycleOwner(), strings -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
            txtJobNme.setAdapter(adapter);
            txtJobNme.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        });

        txtJobNme.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getJobTitleInfoList().observe(getViewLifecycleOwner(), occupationInfos -> {
            for (int x = 0; x < occupationInfos.size(); x++) {
                if (txtJobNme.getText().toString().equalsIgnoreCase(occupationInfos.get(x).getOccptnNm())) {
                    mViewModel.setJobTitle(occupationInfos.get(x).getOccptnID());
                    break;
                }
            }
        }));

        mViewModel.getEmploymentStatus().observe(getViewLifecycleOwner(), stringArrayAdapter -> {
            spnEmpSts.setAdapter(stringArrayAdapter);
            spnEmpSts.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        });
        mViewModel.getLengthOfService().observe(getViewLifecycleOwner(), stringArrayAdapter -> {
            spnServce.setAdapter(stringArrayAdapter);
            spnServce.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        });
        mViewModel.getPsService().observe(getViewLifecycleOwner(), s -> {
            spnServce.setSelection(s.length());
            Log.e("Employee ", s);
        });

        spnCmpLvl.setOnItemClickListener(new OnItemClickListener(spnCmpLvl));
        spnEmpLvl.setOnItemClickListener(new OnItemClickListener(spnEmpLvl));
        spnBusNtr.setOnItemClickListener(new OnItemClickListener(spnBusNtr));
        spnServce.setOnItemClickListener(new OnItemClickListener(spnServce));
        spnEmpSts.setOnItemClickListener(new OnItemClickListener(spnEmpSts));
        btnNext.setOnClickListener(view -> {
            infoModel.setCompanyName(Objects.requireNonNull(txtCompNm.getText()).toString());
            infoModel.setCompAddress(Objects.requireNonNull(txtCompAd.getText()).toString());
            infoModel.setJobSpecific(Objects.requireNonNull(txtSpcfJb.getText()).toString());
            infoModel.setLengthOfService(Objects.requireNonNull(txtLngthS.getText()).toString());
            infoModel.setMonthOrYear(spnServcePosition);
            infoModel.setGrossMonthly(Objects.requireNonNull(txtEsSlry.getText()).toString());
            infoModel.setCompTelNox(Objects.requireNonNull(txtCompCn.getText()).toString());
            mViewModel.Save(infoModel, Fragment_SpouseEmploymentInfo.this);
        });
    }

    @Override
    public void onSaveSuccessResult(String args) {
        Activity_CreditApplication.getInstance().moveToPageNumber(10);
    }

    @Override
    public void onFailedResult(String message) {
        GToast.CreateMessage(getActivity(), message, GToast.ERROR).show();
    }

    private void setFieldValues(ECreditApplicantInfo foCredtAp) {
        if(foCredtAp.getSpsEmplx() != null) {
            try {
                JSONObject loJson =  new JSONObject(foCredtAp.getSpsEmplx());

                if (loJson.getString("cEmpSectr").equalsIgnoreCase("1")) {
                    // PRIVATE SECTOR
                    rgSectorx.check(R.id.rb_private);
                    if(!"".equalsIgnoreCase(loJson.getString("cCompLevl"))) {
                        spnCmpLvl.setText(CreditAppConstants.COMPANY_LEVEL[Integer.parseInt(loJson.getString("cCompLevl"))]);
                        mViewModel.setPsCmpLvl(loJson.getString("cCompLevl"));
                    }
                    if(!"".equalsIgnoreCase(loJson.getString("cEmpLevlx"))) {
                        spnEmpLvl.setText(CreditAppConstants.EMPLOYEE_LEVEL[Integer.parseInt(loJson.getString("cEmpLevlx"))]);
                        mViewModel.setPsEmpLvl(loJson.getString("cEmpLevlx"));
                    }
                    if(!"".equalsIgnoreCase(loJson.getString("sIndstWrk"))) {
                        spnBusNtr.setText(loJson.getString("sIndstWrk"));
                        mViewModel.setPsBsnssLvl(loJson.getString("sIndstWrk"));
                    }
                    txtCompNm.setText( (!"".equalsIgnoreCase(loJson.getString("sEmployer"))) ? loJson.getString("sEmployer") : "" );
                    txtCompAd.setText( (!"".equalsIgnoreCase(loJson.getString("sWrkAddrx"))) ? loJson.getString("sWrkAddrx") : "" );
                    mViewModel.getTownProvinceByTownID(loJson.getString("sWrkTownx")).observe(getViewLifecycleOwner(), townProvinceInfo -> {
                        txtTownNm.setText(townProvinceInfo.sTownName);
                        txtProvNm.setText(townProvinceInfo.sProvName);
                        mViewModel.setTownID(townProvinceInfo.sTownIDxx);
                        mViewModel.setProvinceID(townProvinceInfo.sProvIDxx);
                    });
                    if(!"".equalsIgnoreCase(loJson.getString("sPosition"))) {
                        mViewModel.setJobTitle(loJson.getString("sPosition"));
                        mViewModel.getLiveOccupationName(loJson.getString("sPosition")).observe(getViewLifecycleOwner(), s -> {
                            try {
                                txtJobNme.setText(s);
                            } catch(NullPointerException e) {
                                e.printStackTrace();
                            } catch(Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }
                    txtSpcfJb.setText( (!"".equalsIgnoreCase(loJson.getString("sFunction"))) ? loJson.getString("sFunction") : "" );

                    if(!"".equalsIgnoreCase(loJson.getString("cEmpStatx"))) {
                        spnEmpSts.setText(employmentStatChecker(loJson.getString("cEmpStatx")));
                        mViewModel.setEmploymentStatus(loJson.getString("cEmpStatx"));
                    }

                    txtLngthS.setText( (!"".equalsIgnoreCase(loJson.getString("nLenServc"))) ? String.valueOf(loJson.get("nLenServc")) : "" );
                    txtEsSlry.setText( (!"".equalsIgnoreCase(loJson.getString("nSalaryxx"))) ? String.valueOf(loJson.get("nSalaryxx")) : "" );
                    txtCompCn.setText( (!"".equalsIgnoreCase(loJson.getString("sWrkTelno"))) ? String.valueOf(loJson.get("sWrkTelno")) : "" );

                } else if (loJson.getString("cEmpSectr").equalsIgnoreCase("0")) {
                    // GOVERNMENT SECTOR
                    rgSectorx.check(R.id.rb_government);

                    // is Uniformed
                    if(!"".equalsIgnoreCase(loJson.getString("cUniforme"))) {
                        if(loJson.getString("cUniforme").equalsIgnoreCase("Y")) {
                            rgUniform.check(R.id.rb_uniform_yes);
                            mViewModel.setUniformPersonnel("Y");
                        } else if(loJson.getString("cUniforme").equalsIgnoreCase("N")) {
                            rgUniform.check(R.id.rb_uniform_no);
                            mViewModel.setUniformPersonnel("N");
                        }
                    }

                    // is Military
                    if(!"".equalsIgnoreCase(loJson.getString("cMilitary"))) {
                        if(loJson.getString("cMilitary").equalsIgnoreCase("Y")) {
                            rgMiltary.check(R.id.rb_military_yes);
                            mViewModel.setMilitaryPersonnel("Y");
                        } else if(loJson.getString("cMilitary").equalsIgnoreCase("N")) {
                            rgMiltary.check(R.id.rb_military_no);
                            mViewModel.setMilitaryPersonnel("N");
                        }
                    }

                    // Govt Level
                    if(!"".equalsIgnoreCase(loJson.getString("cCompLevl"))) {
                        spnCmpLvl.setText(CreditAppConstants.GOVERMENT_LEVEL[Integer.parseInt(loJson.getString("cCompLevl"))]);
                        mViewModel.setPsCmpLvl(loJson.getString("cCompLevl"));
                    }
                    // Employee Level
                    if(!"".equalsIgnoreCase(loJson.getString("cEmpLevlx"))) {
                        spnEmpLvl.setText(CreditAppConstants.EMPLOYEE_LEVEL[Integer.parseInt(loJson.getString("cEmpLevlx"))]);
                        mViewModel.setPsEmpLvl(loJson.getString("cEmpLevlx"));
                    }
                    // Govt Institution
                    txtCompNm.setText( (!"".equalsIgnoreCase(loJson.getString("sEmployer"))) ? loJson.getString("sEmployer") : "" );
                    txtCompAd.setText( (!"".equalsIgnoreCase(loJson.getString("sWrkAddrx"))) ? loJson.getString("sWrkAddrx") : "" );
                    mViewModel.getTownProvinceByTownID(loJson.getString("sWrkTownx")).observe(getViewLifecycleOwner(), townProvinceInfo -> {
                        txtTownNm.setText(townProvinceInfo.sTownName);
                        txtProvNm.setText(townProvinceInfo.sProvName);
                        mViewModel.setTownID(townProvinceInfo.sTownIDxx);
                        mViewModel.setProvinceID(townProvinceInfo.sProvIDxx);
                    });

                    // Specific Job
                    txtSpcfJb.setText( (!"".equalsIgnoreCase(loJson.getString("sFunction"))) ? loJson.getString("sFunction") : "" );

                    // Employment Stats
                    if(!"".equalsIgnoreCase(loJson.getString("cEmpStatx"))) {
                        spnEmpSts.setText(employmentStatChecker(loJson.getString("cEmpStatx")));
                        mViewModel.setEmploymentStatus(loJson.getString("cEmpStatx"));
                    }

                    txtLngthS.setText( (!"".equalsIgnoreCase(loJson.getString("nLenServc"))) ? String.valueOf(loJson.get("nLenServc")) : "" );
                    txtEsSlry.setText( (!"".equalsIgnoreCase(loJson.getString("nSalaryxx"))) ? String.valueOf(loJson.get("nSalaryxx")) : "" );
                    txtCompCn.setText( (!"".equalsIgnoreCase(loJson.getString("sWrkTelno"))) ? String.valueOf(loJson.get("sWrkTelno")) : "" );

                } else if(loJson.getString("cEmpSectr").equalsIgnoreCase("2")) {
                    // OFW
                    rgSectorx.check(R.id.rb_ofw);

                    // Region
                    if(!"".equalsIgnoreCase(loJson.getString("cOFWRegnx"))) {
                        spnCmpLvl.setText(CreditAppConstants.OFW_REGION[Integer.parseInt(loJson.getString("cOFWRegnx"))]);
                        mViewModel.setPsCmpLvl(loJson.getString("cOFWRegnx"));
                    }

                    // OFW Category
                    if(!"".equalsIgnoreCase(loJson.getString("cOcCatgry"))) {
                        spnEmpLvl.setText(CreditAppConstants.OFW_CATEGORY[Integer.parseInt(loJson.getString("cOcCatgry"))]);
                        mViewModel.setPsEmpLvl(loJson.getString("cOcCatgry"));
                    }

                    // Country
                    if(!"".equalsIgnoreCase(loJson.getString("sOFWNatnx"))) {
                        mViewModel.setCountry(loJson.getString("sOFWNatnx"));
                        mViewModel.getCountryNameFromId(loJson.getString("sOFWNatnx")).observe(getViewLifecycleOwner(), string -> {
                            try {
                                txtCntryx.setText(string);
                            } catch(NullPointerException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            // clear fields
//            .getText().clear()
        }
    }

    private String employmentStatChecker(String fsEmpStat) {
        switch(fsEmpStat) {
            case "R":
                return "Regular";
            case "P":
                return "Probationary";
            case "C":
                return "Contractual";
            case "S":
                return "Seasonal";
            default:
                return "";
        }
    }

    private class OnRadioButtonSelectListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            if (radioGroup.getId() == R.id.rg_sector) {
                if (i == R.id.rb_private) {
                    mViewModel.setEmploymentSector("1");
                    lnGovInfo.setVisibility(View.GONE);
                    lnEmpInfo.setVisibility(View.VISIBLE);
                    tilCntryx.setVisibility(View.GONE);
                    tilJobTitle.setVisibility(View.VISIBLE);
                    tilCmpLvl.setHint("Company Level (Required)");
                    tilCompNm.setHint("Company Name");
                    tilEmpLvl.setHint("Employee Level (Required)");
                    tilBizNature.setVisibility(View.VISIBLE);
                    lblBizNature.setVisibility(View.VISIBLE);
                    lblBizNature.setText("Nature of Business");
                    mViewModel.getCompanyLevelList().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnCmpLvl.setAdapter(stringArrayAdapter));
                    mViewModel.getEmployeeLevelList().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnEmpLvl.setAdapter(stringArrayAdapter));
                } else if (i == R.id.rb_government) {
                    mViewModel.setEmploymentSector("0");
                    lnGovInfo.setVisibility(View.VISIBLE);
                    lnEmpInfo.setVisibility(View.VISIBLE);
                    tilCntryx.setVisibility(View.GONE);
                    tilJobTitle.setVisibility(View.GONE);
                    tilCmpLvl.setHint("Government Level (Required)");
                    tilCompNm.setHint("Government Institution");
                    tilEmpLvl.setHint("Employee Level (Required)");
                    tilBizNature.setVisibility(View.GONE);
                    lblBizNature.setVisibility(View.VISIBLE);
                    lblBizNature.setText("Government Office");
                    mViewModel.getGovernmentLevelList().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnCmpLvl.setAdapter(stringArrayAdapter));
                    mViewModel.getEmployeeLevelList().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnEmpLvl.setAdapter(stringArrayAdapter));
                } else if (i == R.id.rb_ofw) {
                    mViewModel.setEmploymentSector("2");
                    lnGovInfo.setVisibility(View.GONE);
                    lnEmpInfo.setVisibility(View.GONE);
                    tilCntryx.setVisibility(View.VISIBLE);
                    tilCmpLvl.setHint("Region (Required)");
                    tilEmpLvl.setHint("Overseas Work Category");
                    tilBizNature.setVisibility(View.GONE);
                    lblBizNature.setVisibility(View.GONE);
                    mViewModel.getRegionList().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnCmpLvl.setAdapter(stringArrayAdapter));
                    mViewModel.getWorkCategoryList().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnEmpLvl.setAdapter(stringArrayAdapter));
                }
            }
            if (radioGroup.getId() == R.id.rg_uniformPersonel) {
                if (i == R.id.rb_uniform_yes) {
                    mViewModel.setUniformPersonnel("Y");
                } else if (i == R.id.rb_uniform_no) {
                    mViewModel.setUniformPersonnel("N");
                }
            }
            if (radioGroup.getId() == R.id.rg_militaryPersonal) {
                if (i == R.id.rb_military_yes) {
                    mViewModel.setMilitaryPersonnel("Y");
                } else if (i == R.id.rb_military_no) {
                    mViewModel.setMilitaryPersonnel("N");
                }
            }
        }
    }

    class OnItemClickListener implements AdapterView.OnItemClickListener {
        AutoCompleteTextView poView;

        public OnItemClickListener(AutoCompleteTextView view) {
            this.poView = view;
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if (spnCmpLvl.equals(poView)) {
                spnCmpLvlPosition = String.valueOf(i);
                mViewModel.setPsCmpLvl(String.valueOf(i));
            }
            if (spnEmpLvl.equals(poView)) {
                spnEmpLvlPosition = String.valueOf(i);
                mViewModel.setPsEmpLvl(String.valueOf(i));
            }
            if (spnBusNtr.equals(poView)) {
                spnBusNtrPosition = String.valueOf(i);
                mViewModel.setPsBsnssLvl(adapterView.getItemAtPosition(i).toString());
            }
            if (spnServce.equals(poView)) {
                spnServcePosition = String.valueOf(i);
                mViewModel.setPsService(String.valueOf(i));
            }
            if (spnEmpSts.equals(poView)) {
                if (adapterView.getItemAtPosition(i).toString().equalsIgnoreCase("Tap here to select.")) {
                    mViewModel.setEmploymentStatus("");
                } else if (adapterView.getItemAtPosition(i).toString().equalsIgnoreCase("Regular")) {
                    mViewModel.setEmploymentStatus("R");
                } else if (adapterView.getItemAtPosition(i).toString().equalsIgnoreCase("Probationary")) {
                    mViewModel.setEmploymentStatus("P");
                } else if (adapterView.getItemAtPosition(i).toString().equalsIgnoreCase("Contractual")) {
                    mViewModel.setEmploymentStatus("C");
                } else if (adapterView.getItemAtPosition(i).toString().equalsIgnoreCase("Seasonal")) {
                    mViewModel.setEmploymentStatus("S");
                }
                GToast.CreateMessage(getActivity(), adapterView.getItemAtPosition(i).toString(), GToast.INFORMATION).show();
            }
        }
    }
}

