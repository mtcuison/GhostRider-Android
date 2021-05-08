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

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity.Activity_Application;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Model.Area;
import org.rmj.guanzongroup.ghostrider.epacss.Activity.Activity_SplashScreen;
import org.rmj.guanzongroup.ghostrider.epacss.ViewModel.VMHome;
import org.rmj.guanzongroup.ghostrider.epacss.adapter.NewsEventsAdapter;
import org.rmj.guanzongroup.ghostrider.epacss.adapter.NewsEventsModel;
import org.rmj.guanzongroup.ghostrider.epacss.ui.ProgressBar.AreaMonitoringDashbordAdapter;
import org.rmj.guanzongroup.ghostrider.epacss.ui.etc.NewsEventSpacing;
import org.rmj.guanzongroup.ghostrider.imgcapture.ImageFileCreator;

import org.rmj.g3appdriver.GRider.Etc.GeoLocator;
import org.rmj.g3appdriver.dev.DeptCode;
import org.rmj.guanzongroup.ghostrider.epacss.R;
import org.rmj.guanzongroup.ghostrider.settings.Activity_Settings;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

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
            lblBranch;
    private String photoPath;
    private double latitude, longitude;
    private RecyclerView newsRecyclerView;
    private List<NewsEventsModel> newsList;
    private NewsEventsModel infoModel;
    private NewsEventsAdapter adapter;
//    private Button btn_settings, btn_notif ;
    private MaterialButton btnSelfie;
    private ImageView btn_messages,btn_settings, btn_notif;
    private RecyclerView recyclerView;
    private MessageBox loMessage;
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
        newsRecyclerView = view.findViewById(R.id.newRecyclerView);
        btnSelfie = view.findViewById(R.id.btn_selfieLogin);
        btn_settings = view.findViewById(R.id.btn_settings);
        btn_notif = view.findViewById(R.id.btn_notif);
        btn_messages = view.findViewById(R.id.btn_messages);
        recyclerView = view.findViewById(R.id.recyclerview_monitoring);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMHome.class);
//        mViewModel.getMobileNo().observe(getViewLifecycleOwner(), s -> lblMobile.setText(s));
        mViewModel.getAreaPerformanceInfoList().observe(getViewLifecycleOwner(), areaPerformances -> {
            List<Area> areaList = new ArrayList<>();
            Log.e("perf", String.valueOf(areaPerformances));
            for(int x = 0; x < 5; x++){
                Area area = new Area(areaPerformances.get(x).getAreaCode(),
                        areaPerformances.get(x).getAreaDesc(),
                        String.valueOf(areaPerformances.get(x).getMCGoalxx()),
                        String.valueOf(areaPerformances.get(x).getMCActual()));
                areaList.add(area);
            }
            AreaMonitoringDashbordAdapter loAdapter = new AreaMonitoringDashbordAdapter(areaList);
            LinearLayoutManager loManager = new LinearLayoutManager(getActivity());

//            loManager.setOrientation(RecyclerView.VERTICAL);
//            recyclerView.setLayoutManager(loManager);
//            recyclerView.setAdapter(loAdapter);

//            recyclerView.setLayoutManager(new SpanningLinearLayoutManager(getContext(),  LinearLayoutManager.HORIZONTAL, false));
//            recyclerView.setAdapter(loAdapter);

            recyclerView.setHasFixedSize(true);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),  LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setAdapter(loAdapter);


        });
        mViewModel.getEmployeeInfo().observe(getViewLifecycleOwner(), eEmployeeInfo -> {
            try {
                lblEmail.setText(eEmployeeInfo.getEmailAdd());
                lblUserLvl.setText(DeptCode.parseUserLevel(Integer.parseInt(eEmployeeInfo.getEmpLevID())));
                lblFullNme.setText(eEmployeeInfo.getUserName());
                lblDept.setText(DeptCode.getDepartmentName(eEmployeeInfo.getDeptIDxx()));
            } catch (Exception e){
                e.printStackTrace();
            }
        });
        infoModel = new NewsEventsModel(
                "https://www.guanzongroup.com.ph/wp-content/uploads/2021/02/Grand-Opening-4-786x786.png",
                "Grand Opening of Guanzon Mobitek SM City North Edsa-Annex branch",
                "24",
                "Feb" ,
                "POSTED ON FEBRUARY 24, 2021 BY GUANZON_ADMIN");
        newsList.add(infoModel);
        infoModel = new NewsEventsModel(
                "https://www.guanzongroup.com.ph/wp-content/uploads/2020/11/mcc-dasol-multi-2020-786x517.jpg",
                "GUANZON OPENS A MULTI-BRAND SHOP AT DASOL PANGASINAN",
                "25",
                "Nov" ,
                "POSTED ON NOVEMBER 25, 2020 BY GUANZON_ADMIN");
        newsList.add(infoModel);
        infoModel = new NewsEventsModel(
                "https://www.guanzongroup.com.ph/wp-content/uploads/2020/11/uemi-san-fabian-yamaha-2020-2-786x482.jpg",
                "GUANZON OPENS ITS YAMAHA 3s SHOP AT SAN FABIAN, PANGASINAN",
                "25",
                "Nov" ,
                "POSTED ON NOVEMBER 25, 2020 BY GUANZON_ADMIN");
        newsList.add(infoModel);
        infoModel = new NewsEventsModel(
                "https://www.guanzongroup.com.ph/wp-content/uploads/2020/10/suzuki-mangaldan-opening-oct-2020-786x440.jpg",
                "GUANZON GROUP OFFICIALLY INAUGURATES ITS SUZUKI 3S BRANCH AT MANGALDAN",
                "28",
                "Oct" ,
                "POSTED ON OCTOBER 28, 2020 BY GUANZON_ADMIN");
        newsList.add(infoModel);
        infoModel = new NewsEventsModel(
                "https://www.guanzongroup.com.ph/wp-content/uploads/2020/10/SM-Mega-Center-Cabanatuan-opening-oct-2020-1-786x786.jpg",
                "Guanzon Group Opens New Stores in SM Mega Center Cabanatuan",
                "21",
                "Oct" ,
                "POSTED ON OCTOBER 21, 2020 BY GUANZON_ADMIN");
        newsList.add(infoModel);
        infoModel = new NewsEventsModel(
                "https://www.guanzongroup.com.ph/wp-content/uploads/2020/10/mcc-burgos-open-oct-2020-1-786x482.jpg",
                "GUANZON OPENS A MULTI-BRAND SHOP AT BURGOS PANGASINAN",
                "20",
                "Oct" ,
                "POSTED ON OCTOBER 20, 2020 BY GUANZON_ADMIN");
        newsList.add(infoModel);
        infoModel = new NewsEventsModel(
                "https://www.guanzongroup.com.ph/wp-content/uploads/2020/10/mcc-cabarroguis-soft-open-oct-2020-border-blk-786x465.jpg",
                "GUANZON OPENS ITS FIRST BRANCH AT QUIRINO PROVINCE",
                "20",
                "Oct" ,
                "POSTED ON OCTOBER 20, 2020 BY GUANZON_ADMIN");
        newsList.add(infoModel);

        infoModel = new NewsEventsModel(
                "https://www.guanzongroup.com.ph/wp-content/uploads/2020/10/guanzon-opening-urdaneta-oct-2020-1-786x786.jpg",
                "Guanzon’s Opens 4 branches at the Magic Mall Urdaneta: Amidst the Covid – 19 pandemic",
                "14",
                "Oct" ,
                "POSTED ON OCTOBER 14, 2020 BY GUANZON_ADMIN");
        newsList.add(infoModel);

        newsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        newsRecyclerView.addItemDecoration(new NewsEventSpacing(newsList.size(), 30, false));
        newsRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        btnSelfie.setOnClickListener(v -> {
            Intent loIntent = new Intent(getActivity(), Activity_Application.class);
            loIntent.putExtra("app", AppConstants.INTENT_SELFIE_LOGIN);
            startActivity(loIntent);
        });
        btn_notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GToast.CreateMessage(getActivity(), "Feature not yet implemented", GToast.INFORMATION).show();
            }
        });
        btn_messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GToast.CreateMessage(getActivity(), "Feature not yet implemented", GToast.INFORMATION).show();
            }
        });
        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Activity_Settings.class);
                startActivity(intent);
            }
        });

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