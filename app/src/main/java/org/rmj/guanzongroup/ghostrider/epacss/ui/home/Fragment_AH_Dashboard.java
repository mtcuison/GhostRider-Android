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

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.g3appdriver.dev.DeptCode;
import org.rmj.g3appdriver.etc.AppAssistantConfig;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity.Activity_Application;
import org.rmj.guanzongroup.ghostrider.epacss.Activity.Activity_SplashScreen;
import org.rmj.guanzongroup.ghostrider.epacss.R;
import org.rmj.guanzongroup.ghostrider.epacss.ViewModel.VMAHDashboard;
import org.rmj.guanzongroup.ghostrider.settings.Activity.Activity_Help;
import org.rmj.guanzongroup.ghostrider.settings.Activity.Activity_Settings;

public class Fragment_AH_Dashboard extends Fragment {

    private VMAHDashboard mViewModel;

    private TextView lblFullNme,
            lblEmail,
            lblUserLvl,
            lblDept,
            lblBranch,
            lblAddx,
            lblSelfie,
            lblVersion;

    private MaterialButton btnSelfie, btnSettings, btnLogout;

    public static Fragment_AH_Dashboard newInstance() {
        return new Fragment_AH_Dashboard();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ah_dashboard, container, false);

        lblFullNme = view.findViewById(R.id.lbl_userFullName);
        lblEmail = view.findViewById(R.id.lbl_userEmail);
        lblUserLvl = view.findViewById(R.id.lbl_userLevel);
        lblDept = view.findViewById(R.id.lbl_userDepartment);
        lblBranch = view.findViewById(R.id.lbl_userBranch);
        lblAddx = view.findViewById(R.id.lbl_userAddress);
        lblSelfie = view.findViewById(R.id.lbl_badge_selfieLog);
        lblVersion = view.findViewById(R.id.lbl_versionInfo);

        btnSelfie = view.findViewById(R.id.btn_selfieLogin);
        btnLogout = view.findViewById(R.id.btn_logout);
        btnSettings = view.findViewById(R.id.btn_settings);

        btnSelfie.setOnClickListener(v -> {
            if (!AppAssistantConfig.getInstance(getActivity()).getHELP_SLOGIN_NOTICE()){
                Intent intent = new Intent(getActivity(), Activity_Help.class);
                intent.putExtra("help", AppConstants.INTENT_SELFIE_LOGIN);
                getActivity().startActivityForResult(intent, AppConstants.INTENT_SELFIE_LOGIN);
            }else{
                Intent intent = new Intent(getActivity(), Activity_Application.class);
                intent.putExtra("app", AppConstants.INTENT_SELFIE_LOGIN);
                startActivity(intent);
            }

        });

        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Activity_Settings.class);
            startActivity(intent);

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMAHDashboard.class);

        mViewModel.getCurrentLogTimeIfExist().observe(getViewLifecycleOwner(), eLog_selfies -> {
            if(eLog_selfies.size()>0){
                lblSelfie.setText("✔");
                lblSelfie.setBackground(getResources().getDrawable(R.drawable.bg_badge_green));
            } else {
                lblSelfie.setText("!");
                lblSelfie.setBackground(getResources().getDrawable(R.drawable.bg_badge_red));
            }
        });

        mViewModel.getVersionInfo().observe(getViewLifecycleOwner(), s -> lblVersion.setText(s));

        mViewModel.getEmployeeInfo().observe(getViewLifecycleOwner(), eEmployeeInfo -> {
            try {
                lblFullNme.setText(eEmployeeInfo.getUserName());
                lblEmail.setText(eEmployeeInfo.getEmailAdd());
                lblUserLvl.setText(DeptCode.parseUserLevel(Integer.parseInt(eEmployeeInfo.getEmpLevID())));
                lblDept.setText(DeptCode.getDepartmentName(eEmployeeInfo.getDeptIDxx()));
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getUserBranchInfo().observe(getViewLifecycleOwner(), eBranchInfo -> {
            try {
                lblBranch.setText(eBranchInfo.getBranchNm());
                lblAddx.setText(eBranchInfo.getAddressx());
            } catch (Exception e){
                e.printStackTrace();
            }
        });
    }
}