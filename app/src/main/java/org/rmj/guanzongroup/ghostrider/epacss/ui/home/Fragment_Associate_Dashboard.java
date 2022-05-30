/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.app
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.ghostrider.epacss.ui.home;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.g3appdriver.dev.DeptCode;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.guanzongroup.ghostrider.epacss.Activity.Activity_Main;
import org.rmj.guanzongroup.ghostrider.epacss.Activity.Activity_SplashScreen;
import org.rmj.guanzongroup.ghostrider.epacss.R;
import org.rmj.guanzongroup.ghostrider.epacss.ViewModel.VMAHDashboard;
import org.rmj.guanzongroup.ghostrider.settings.Activity.Activity_Settings;

import static android.app.Activity.RESULT_OK;
import static org.rmj.g3appdriver.GRider.Constants.AppConstants.SETTINGS;

public class Fragment_Associate_Dashboard extends Fragment {

    private VMAHDashboard mViewModel;

    private TextView lblFullNme,
            lblEmail,
            lblUserLvl,
            lblDept,
            lblBranch,
            lblAddx,
            lblVersion,
            lblServerStat;
    private LinearLayout lnDevMode;
    private SwitchMaterial poSwitch;
    private AppConfigPreference poConfig;
//    private ImageView imgUser;

    private MaterialButton btnSettings, btnLogout;

    public static Fragment_Associate_Dashboard newInstance() {
        return new Fragment_Associate_Dashboard();
    }


    @SuppressLint("NewApi")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_associate_dashboard, container, false);
        poConfig = AppConfigPreference.getInstance(requireActivity());
        lblFullNme = view.findViewById(R.id.lbl_userFullName);
        lblEmail = view.findViewById(R.id.lbl_userEmail);
        lblUserLvl = view.findViewById(R.id.lbl_userLevel);
        lblDept = view.findViewById(R.id.lbl_userDepartment);
        lblBranch = view.findViewById(R.id.lbl_userBranch);
        lblAddx = view.findViewById(R.id.lbl_userAddress);
        lnDevMode = view.findViewById(R.id.ln_devMode);
        poSwitch = view.findViewById(R.id.sm_dcpTest);
        lblServerStat = view.findViewById(R.id.lbl_serverStatus);
//        imgUser = view.findViewById(R.id.img_userLogo);
        lblVersion = view.findViewById(R.id.lbl_versionInfo);
        btnLogout = view.findViewById(R.id.btn_logout);
        btnSettings = view.findViewById(R.id.btn_settings);

        poSwitch.setChecked(poConfig.getTestStatus());
        if(!poConfig.isBackUpServer()) {
            lblServerStat.setText("Connection : Live");
        } else {
            lblServerStat.setText("Connection : Back Up");
        }

        poSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean switchOn) {
                MessageBox loMessage = new MessageBox(getActivity());
                loMessage.initDialog();
                if(switchOn) {
                    loMessage.setNegativeButton("Cancel", (view1, dialog) -> {
                        poSwitch.setOnCheckedChangeListener(null);
                        poSwitch.setChecked(false);
                        poSwitch.setOnCheckedChangeListener(this);
                        dialog.dismiss();
                    });
                    loMessage.setPositiveButton("Continue", (view1, dialog) -> {
                        dialog.dismiss();
                        poConfig.setTestCase(switchOn);
                        requireActivity().finish();
                        new REmployee(requireActivity().getApplication()).LogoutUserSession();
                        AppConfigPreference.getInstance(getActivity()).setIsAppFirstLaunch(false);
                        startActivity(new Intent(getActivity(), Activity_SplashScreen.class));
                    });
                    loMessage.setTitle("GhostRider Dev Mode");
                    loMessage.setMessage("Test mode changes connection of this app \n From LIVE (240) to LOCAL(192.168.10.141) \n NOTE: Features that requires taking images will still be require but images will not be uploaded. \n Requires re-login account.");
                    loMessage.show();
                } else {
                    loMessage.setNegativeButton("Cancel", (view1, dialog) -> {
                        poSwitch.setOnCheckedChangeListener(null);
                        poSwitch.setChecked(false);
                        poSwitch.setOnCheckedChangeListener(this);
                        dialog.dismiss();
                    });
                    loMessage.setPositiveButton("Continue", (view1, dialog) -> {
                        dialog.dismiss();
                        poConfig.setTestCase(switchOn);
                        requireActivity().finish();
                        new REmployee(requireActivity().getApplication()).LogoutUserSession();
                        AppConfigPreference.getInstance(getActivity()).setIsAppFirstLaunch(false);
                        startActivity(new Intent(getActivity(), Activity_SplashScreen.class));
                    });
                    loMessage.setTitle("GhostRider Dev Mode");
                    loMessage.setMessage("Switching to Live Data Requires re-login account.");
                    loMessage.show();
                }
            }
        });

        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Activity_Settings.class);
            startActivityForResult(intent, SETTINGS);
            requireActivity().overridePendingTransition(R.anim.anim_intent_slide_in_right, R.anim.anim_intent_slide_out_left);
        });

        btnLogout.setOnClickListener(v -> {
            MessageBox loMessage = new MessageBox(getActivity());
            loMessage.initDialog();
            loMessage.setNegativeButton("No", (view1, dialog) -> dialog.dismiss());
            loMessage.setPositiveButton("Yes", (view1, dialog) -> {
                dialog.dismiss();
                requireActivity().finish();
                new REmployee(requireActivity().getApplication()).LogoutUserSession();
                AppConfigPreference.getInstance(getActivity()).setIsAppFirstLaunch(false);
                startActivity(new Intent(getActivity(), Activity_SplashScreen.class));
            });
            loMessage.setTitle("GhostRider Session");
            loMessage.setMessage("Are you sure you want to end session/logout?");
            loMessage.show();
        });

        return view;
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMAHDashboard.class);

        mViewModel.getVersionInfo().observe(getViewLifecycleOwner(), s -> lblVersion.setText(s));

        mViewModel.getEmployeeInfo().observe(getViewLifecycleOwner(), eEmployeeInfo -> {
            try {
                lblFullNme.setText(eEmployeeInfo.getUserName());
                lblEmail.setText(eEmployeeInfo.getEmailAdd());
                lblUserLvl.setText(DeptCode.parseUserLevel(Integer.parseInt(eEmployeeInfo.getEmpLevID())));
                lblDept.setText(DeptCode.getDepartmentName(eEmployeeInfo.getDeptIDxx()));
//                imgUser.setImageResource(AppConstants.getUserIcon(eEmployeeInfo.getUserLevl()));
                if(eEmployeeInfo.getDeptIDxx().equalsIgnoreCase(DeptCode.MANAGEMENT_INFORMATION_SYSTEM)){
                    lnDevMode.setVisibility(View.VISIBLE);
                } else {
                    lnDevMode.setVisibility(View.GONE);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getUserBranchInfo().observe(getViewLifecycleOwner(), eBranchInfo -> {
            try {
                if(eBranchInfo != null) {
                    lblBranch.setText(eBranchInfo.getBranchNm());
                    lblAddx.setText(eBranchInfo.getAddressx());
                } else {
                    lblBranch.setText("Downloading Data");
                    lblAddx.setText("Please wait...");
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == SETTINGS){
            if(resultCode == RESULT_OK) {
                Intent loIntent = new Intent(getActivity(), Activity_Main.class);
                requireActivity().finish();
                startActivity(loIntent);
            }
        }
    }
}