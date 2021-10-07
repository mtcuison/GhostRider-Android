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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DBranchOpeningMonitor;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchPerformance;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity.Activity_Application;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity.Activity_Monitoring;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Adaper.BranchMonitoringAdapter;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Adaper.BranchOpeningAdapter;
import org.rmj.guanzongroup.ghostrider.epacss.Activity.Activity_Main;
import org.rmj.guanzongroup.ghostrider.epacss.Activity.Activity_SplashScreen;
import org.rmj.guanzongroup.ghostrider.epacss.ViewModel.VMHome;
import org.rmj.guanzongroup.ghostrider.epacss.adapter.NewsEventsAdapter;
import org.rmj.guanzongroup.ghostrider.epacss.adapter.NewsEventsModel;
import org.rmj.guanzongroup.ghostrider.imgcapture.ImageFileCreator;

import org.rmj.g3appdriver.GRider.Etc.GeoLocator;
import org.rmj.g3appdriver.dev.DeptCode;
import org.rmj.guanzongroup.ghostrider.epacss.R;
import org.rmj.guanzongroup.ghostrider.notifications.Activity.Activity_Container;
import org.rmj.guanzongroup.ghostrider.settings.Activity.Activity_Settings;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static org.rmj.g3appdriver.GRider.Constants.AppConstants.INTENT_BRANCH_OPENING;
import static org.rmj.g3appdriver.GRider.Constants.AppConstants.SETTINGS;

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
            lblBranch;
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
        lblBranch = view.findViewById(R.id.lbl_userBranch);
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
                    startActivity(loIntent);
                    return true;
                case R.id.menu_notif:
                    loIntent = new Intent(getActivity(), Activity_Container.class);
                    loIntent.putExtra("type", "notification");
                    startActivity(loIntent);
                    return true;
                case R.id.menu_settings:
                    loIntent = new Intent(getActivity(), Activity_Settings.class);
                    startActivityForResult(loIntent, SETTINGS);
                    return true;
                case R.id.menu_logout:
                    showDialog();
                    return true;
            }
            return false;
        });
        return view;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMHome.class);

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

        mViewModel.getUserAreaCodeForDashboard().observe(getViewLifecycleOwner(), s -> {
            try{
                lblAreaNme.setText("Area " + s);
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getCv_ahMonitoring().observe(getViewLifecycleOwner(), integer -> cvAHMonitoring.setVisibility(integer));

//        mViewModel.getAreaPerformanceDashboard().observe(getViewLifecycleOwner(), areaPerformances -> {
//            List<Area> areaList = new ArrayList<>();
//            for(int x = 0; x < areaPerformances.size(); x++){
//                Area area = new Area(areaPerformances.get(x).getAreaCode(),
//                        areaPerformances.get(x).getAreaDesc(),
//                        String.valueOf(areaPerformances.get(x).getMCGoalxx()),
//                        String.valueOf(areaPerformances.get(x).getMCActual()));
//                areaList.add(area);
//            }
//            AreaMonitoringDashbordAdapter loAdapter = new AreaMonitoringDashbordAdapter(areaList, () -> {
//                Intent loIntent = new Intent(getActivity(), Activity_Application.class);
//                loIntent.putExtra("app", INTENT_AREA_MONITORING);
//                startActivity(loIntent);
//            });
//
//            recyclerView.setHasFixedSize(true);
//            recyclerView.setItemAnimator(new DefaultItemAnimator());
//            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),  LinearLayoutManager.HORIZONTAL, false));
//            recyclerView.setAdapter(loAdapter);
//        });

        mViewModel.getBranchPerformance().observe(getViewLifecycleOwner(), new Observer<List<EBranchPerformance>>() {
            @Override
            public void onChanged(List<EBranchPerformance> eBranchPerformances) {
                BranchMonitoringAdapter loAdapter = new BranchMonitoringAdapter(eBranchPerformances, () -> {
                    Intent loIntent = new Intent(getActivity(), Activity_Monitoring.class);
//                    loIntent.putExtra("app", INTENT_BRANCH_MONITORING);
                    startActivity(loIntent);
                });

                recyclerView.setHasFixedSize(true);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),  LinearLayoutManager.HORIZONTAL, false));
                recyclerView.setAdapter(loAdapter);
            }
        });

//        mViewModel.getUnreadMessagesCount().observeForever(unReadMessageCount -> {
//            try {
//                if(unReadMessageCount > 0) {
//                    navHeader.getOrCreateBadge(R.id.menu_message).setNumber(unReadMessageCount);
//                } else {
//                    navHeader.removeBadge(R.id.menu_message);
//                }
//            } catch (Exception e){
//                e.printStackTrace();
//            }
//        });

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

//        mViewModel.getBranchOpeningMonitor().observe(getViewLifecycleOwner(), eBranchOpenMonitors -> {
//            recyclerViewOpening.setHasFixedSize(true);
//            recyclerViewOpening.setItemAnimator(new DefaultItemAnimator());
//            recyclerViewOpening.setLayoutManager(new LinearLayoutManager(getContext(),  LinearLayoutManager.VERTICAL, false));
//            recyclerViewOpening.setAdapter(new BranchOpeningAdapter(getActivity(), eBranchOpenMonitors, () -> {
//                Intent loIntent = new Intent(getActivity(), Activity_Application.class);
//                loIntent.putExtra("app", INTENT_BRANCH_OPENING);
//                startActivity(loIntent);
//            }));
//        });

        mViewModel.getBranchOpeningInfoForDashBoard().observe(getViewLifecycleOwner(), new Observer<List<DBranchOpeningMonitor.BranchOpeningInfo>>() {
            @Override
            public void onChanged(List<DBranchOpeningMonitor.BranchOpeningInfo> branchOpeningInfos) {
                recyclerViewOpening.setHasFixedSize(true);
                recyclerViewOpening.setItemAnimator(new DefaultItemAnimator());
                recyclerViewOpening.setLayoutManager(new LinearLayoutManager(getContext(),  LinearLayoutManager.VERTICAL, false));
                recyclerViewOpening.setAdapter(new BranchOpeningAdapter(getActivity(), branchOpeningInfos, () -> {
                    Intent loIntent = new Intent(getActivity(), Activity_Application.class);
                    loIntent.putExtra("app", INTENT_BRANCH_OPENING);
                    startActivity(loIntent);
                }));
            }
        });

        adapter.notifyDataSetChanged();
    }

    public void showDialog(){
        loMessage.initDialog();
        loMessage.setNegativeButton("No", (view, dialog) -> dialog.dismiss());
        loMessage.setPositiveButton("Yes", (view, dialog) -> {
            dialog.dismiss();
            requireActivity().finish();
            new REmployee(requireActivity().getApplication()).LogoutUserSession();
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