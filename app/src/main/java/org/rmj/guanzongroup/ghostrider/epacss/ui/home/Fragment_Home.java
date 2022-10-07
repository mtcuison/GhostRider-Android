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

import static android.app.Activity.RESULT_OK;

import static org.rmj.g3appdriver.etc.AppConstants.INTENT_BRANCH_OPENING;
import static org.rmj.g3appdriver.etc.AppConstants.SETTINGS;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.rmj.g3appdriver.dev.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EBranchPerformance;
import org.rmj.g3appdriver.dev.DeptCode;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.etc.GeoLocator;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity.Activity_Application;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity.Activity_AreaPerformance;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Adapter.BranchMonitoringAdapter;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Adapter.BranchOpeningAdapter;
import org.rmj.guanzongroup.ghostrider.epacss.Activity.Activity_Main;
import org.rmj.guanzongroup.ghostrider.epacss.Activity.Activity_SplashScreen;
import org.rmj.guanzongroup.ghostrider.epacss.R;
import org.rmj.guanzongroup.ghostrider.epacss.ViewModel.VMHome;
import org.rmj.guanzongroup.ghostrider.epacss.adapter.NewsEventsAdapter;
import org.rmj.guanzongroup.ghostrider.epacss.adapter.NewsEventsModel;
import org.rmj.guanzongroup.ghostrider.imgcapture.ImageFileCreator;
import org.rmj.guanzongroup.ghostrider.notifications.Activity.Activity_Container;
import org.rmj.guanzongroup.ghostrider.settings.Activity.Activity_Settings;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Home extends Fragment {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private final String CAMERA_USAGE = "Login";

    private VMHome mViewModel;
    private GeoLocator poLocator;
    private ImageFileCreator poFilexx;

    private ImageView imgProfile;
    private TextView lblFullNme,
            lblEmail,
            lblUserLvl,
            lblDept,
            lblAreaNme,
            lblSyncStat;
    private String photoPath;
    private double latitude, longitude;
    private List<NewsEventsModel> newsList;
    private NewsEventsAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewOpening;
    private MessageBox loMessage;
    private CardView cvAHMonitoring;
    private BottomNavigationView navHeader;

    @SuppressLint("NonConstantResourceId")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mViewModel = new ViewModelProvider(this).get(VMHome.class);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        poLocator = new GeoLocator(getActivity(), getActivity());
        poFilexx = new ImageFileCreator(getActivity(), CAMERA_USAGE);
        imgProfile = view.findViewById(R.id.img_profile);
        newsList = new ArrayList<>();
        loMessage = new MessageBox(getActivity());
        adapter = new NewsEventsAdapter(getContext(), newsList);
        lblFullNme = view.findViewById(R.id.lbl_userFullName);
        lblEmail = view.findViewById(R.id.lbl_userEmail);
        lblUserLvl = view.findViewById(R.id.lbl_userLevel);
        lblDept = view.findViewById(R.id.lbl_userDepartment);
        lblSyncStat = view.findViewById(R.id.lbl_syncStatus);
        lblAreaNme = view.findViewById(R.id.lbl_areaName);
        recyclerView = view.findViewById(R.id.recyclerview_monitoring);
        recyclerViewOpening = view.findViewById(R.id.recyclerview_openings);
        cvAHMonitoring = view.findViewById(R.id.cv_ahMonitoring);
        navHeader = view.findViewById(R.id.navHeader);

        navHeader.setOnNavigationItemSelectedListener(item -> {
            Intent loIntent;
            switch (item.getItemId()) {
                case R.id.menu_selfielog:
                    loIntent = new Intent(getActivity(), Activity_Application.class);
                    loIntent.putExtra("app", AppConstants.INTENT_SELFIE_LOGIN);
                    requireActivity().startActivity(loIntent);
                    requireActivity().overridePendingTransition(R.anim.anim_intent_slide_in_right, R.anim.anim_intent_slide_out_left);
                    return true;
                case R.id.menu_notif:
                    loIntent = new Intent(getActivity(), Activity_Container.class);
                    loIntent.putExtra("type", "notification");
                    requireActivity().startActivity(loIntent);
                    requireActivity().overridePendingTransition(R.anim.anim_intent_slide_in_right, R.anim.anim_intent_slide_out_left);
                    return true;
                case R.id.menu_settings:
                    loIntent = new Intent(getActivity(), Activity_Settings.class);
                    startActivityForResult(loIntent, SETTINGS);
                    requireActivity().overridePendingTransition(R.anim.anim_intent_slide_in_right, R.anim.anim_intent_slide_out_left);
                    return true;
                case R.id.menu_logout:
                    showDialog();
                    return true;
            }
            return false;
        });

        mViewModel.getEmployeeInfo().observe(getViewLifecycleOwner(), eEmployeeInfo -> {
            try {
                lblEmail.setText(eEmployeeInfo.getEmailAdd());
                lblUserLvl.setText(DeptCode.parseUserLevel(Integer.parseInt(eEmployeeInfo.getEmpLevID())));
                lblFullNme.setText(eEmployeeInfo.getUserName());
                lblDept.setText(DeptCode.getDepartmentName(eEmployeeInfo.getDeptIDxx()));
                mViewModel.setIntUserLvl(4);

            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.GetUserBranchInfo().observe(getViewLifecycleOwner(), new Observer<EBranchInfo>() {
            @Override
            public void onChanged(EBranchInfo eBranchInfo) {
                try{
                    if(eBranchInfo == null){
                        lblSyncStat.setVisibility(View.VISIBLE);
                    } else {
                        lblSyncStat.setVisibility(View.GONE);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        mViewModel.getUserAreaCodeForDashboard().observe(getViewLifecycleOwner(), s -> {
            try{
                lblAreaNme.setText("Area " + s);
            } catch (Exception e){
                e.printStackTrace();
            }
        });

//        mViewModel.getCv_ahMonitoring().observe(getViewLifecycleOwner(), integer -> cvAHMonitoring.setVisibility(integer));

        mViewModel.getBranchPerformance().observe(getViewLifecycleOwner(), eBranchPerformances -> {
            try {
                BranchMonitoringAdapter loAdapter = new BranchMonitoringAdapter(eBranchPerformances, (EBranchPerformance eBranchPerformance) -> {
                    mViewModel.getBranchAreaCode(eBranchPerformance.getBranchCd()).observe(getViewLifecycleOwner(), s -> {
                        try {
                            Intent loIntent = new Intent(getActivity(), Activity_AreaPerformance.class);
                            loIntent.putExtra("sAreaCode", s);
                            requireActivity().startActivity(loIntent);
                            requireActivity().overridePendingTransition(R.anim.anim_intent_slide_in_right, R.anim.anim_intent_slide_out_left);
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        } catch(Exception e) {
                            e.printStackTrace();
                        }
                    });
                });
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                recyclerView.setAdapter(loAdapter);
            } catch(NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        mViewModel.getUnreadNotificationsCount().observe(getViewLifecycleOwner(), integer -> {
            try {
                if(integer > 0) {
                    navHeader.getOrCreateBadge(R.id.menu_notif).setNumber(integer);
                } else {
                    navHeader.removeBadge(R.id.menu_notif);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getBranchOpeningInfoForDashBoard().observe(getViewLifecycleOwner(), branchOpeningInfos -> {
            recyclerViewOpening.setItemAnimator(new DefaultItemAnimator());
            recyclerViewOpening.setLayoutManager(new LinearLayoutManager(getContext(),  LinearLayoutManager.VERTICAL, false));
            recyclerViewOpening.setAdapter(new BranchOpeningAdapter(getActivity(), branchOpeningInfos, () -> {
                Intent loIntent = new Intent(getActivity(), Activity_Application.class);
                loIntent.putExtra("app", INTENT_BRANCH_OPENING);
                startActivity(loIntent);
            }));
        });

        adapter.notifyDataSetChanged();
        return view;
    }

    public void showDialog(){
        loMessage.initDialog();
        loMessage.setNegativeButton("No", (view, dialog) -> dialog.dismiss());
        loMessage.setPositiveButton("Yes", (view, dialog) -> {
            dialog.dismiss();
            requireActivity().finish();
            new EmployeeMaster(requireActivity().getApplication()).LogoutUserSession();
            AppConfigPreference.getInstance(getActivity()).setIsAppFirstLaunch(false);
            startActivity(new Intent(getActivity(), Activity_SplashScreen.class));
        });
        loMessage.setTitle("GhostRider Session");
        loMessage.setMessage("Are you sure you want to end session/logout?");
        loMessage.show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Save to mysqlLite DB
            poFilexx.galleryAddPic(photoPath);
            setPic();
        } else if(requestCode == SETTINGS){
            if(resultCode == Activity.RESULT_OK) {
                Intent loIntent = new Intent(getActivity(), Activity_Main.class);
                requireActivity().finish();
                startActivity(loIntent);
            }
        }
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = imgProfile.getWidth();
        int targetH = imgProfile.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(photoPath, bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.max(1, Math.min(photoW/targetW, photoH/targetH));

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(photoPath, bmOptions);
        imgProfile.setImageBitmap(bitmap);
    }
}