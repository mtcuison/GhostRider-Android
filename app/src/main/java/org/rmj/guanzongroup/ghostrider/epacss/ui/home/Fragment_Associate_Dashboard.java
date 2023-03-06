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

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textview.MaterialTextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.android.material.divider.MaterialDivider;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;

import org.rmj.g3appdriver.dev.DeptCode;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;
import org.rmj.guanzongroup.ghostrider.epacss.Activity.Activity_Main;
import org.rmj.guanzongroup.ghostrider.epacss.Activity.Activity_SplashScreen;
import org.rmj.guanzongroup.ghostrider.epacss.R;
import org.rmj.guanzongroup.ghostrider.epacss.ViewModel.VMAHDashboard;
import org.rmj.guanzongroup.ghostrider.settings.Activity.Activity_Settings;

import static android.app.Activity.RESULT_OK;
import static org.rmj.g3appdriver.etc.AppConstants.SETTINGS;

public class Fragment_Associate_Dashboard extends Fragment {

    private VMAHDashboard mViewModel;

    private MaterialTextView lblFullNme,
            lblUserLvl,
            lblDept,
            lblVersion;

    public static Fragment_Associate_Dashboard newInstance() {
        return new Fragment_Associate_Dashboard();
    }


    @SuppressLint("NewApi")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_associate_dashboard, container, false);
        lblFullNme = view.findViewById(R.id.lbl_userFullName);
        lblUserLvl = view.findViewById(R.id.lbl_userLevel);
        lblDept = view.findViewById(R.id.lbl_userDepartment);
//        imgUser = view.findViewById(R.id.img_userLogo);
        lblVersion = view.findViewById(R.id.lbl_versionInfo);

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
//                lblEmail.setText(eEmployeeInfo.getEmailAdd());
                lblUserLvl.setText(DeptCode.parseUserLevel(eEmployeeInfo.getEmpLevID()));
                lblDept.setText(DeptCode.getDepartmentName(eEmployeeInfo.getDeptIDxx()));
//                imgUser.setImageResource(AppConstants.getUserIcon(eEmployeeInfo.getUserLevl()));
//                if(eEmployeeInfo.getDeptIDxx().equalsIgnoreCase(DeptCode.MANAGEMENT_INFORMATION_SYSTEM)){
//                    lnDevMode.setVisibility(View.VISIBLE);
//                } else {
//                    lnDevMode.setVisibility(View.GONE);
//                }
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getUserBranchInfo().observe(getViewLifecycleOwner(), eBranchInfo -> {
            try {
                if(eBranchInfo != null) {
//                    lblBranch.setText(eBranchInfo.getBranchNm());
//                    lblAddx.setText(eBranchInfo.getAddressx());
                } else {
//                    lblBranch.setText("Downloading Data");
//                    lblAddx.setText("Please wait...");
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