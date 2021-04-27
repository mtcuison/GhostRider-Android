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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.rmj.guanzongroup.ghostrider.epacss.ViewModel.VMHome;
import org.rmj.guanzongroup.ghostrider.imgcapture.ImageFileCreator;

import org.rmj.g3appdriver.GRider.Etc.GeoLocator;
import org.rmj.g3appdriver.dev.DeptCode;
import org.rmj.guanzongroup.ghostrider.epacss.R;

import static android.app.Activity.RESULT_OK;

public class Fragment_Home extends Fragment {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private final String CAMERA_USAGE = "Login";

    private VMHome mViewModel;
    private GeoLocator poLocator;
    private ImageFileCreator poFilexx;

    private ImageView imgProfile;
    private TextView lblUserNm, lblEmailx, lblPstion, lblMobile, lblAddrss;

    private String photoPath;
    private double latitude, longitude;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        poLocator = new GeoLocator(getActivity(), getActivity());
        poFilexx = new ImageFileCreator(getActivity(), CAMERA_USAGE);
        imgProfile = view.findViewById(R.id.img_profile);
        lblUserNm = view.findViewById(R.id.lbl_employeeName);
        lblEmailx = view.findViewById(R.id.lbl_employeeEmail);
        lblMobile = view.findViewById(R.id.lbl_employeeMobileNo);
        lblPstion = view.findViewById(R.id.lbl_employeePosition);
        lblAddrss = view.findViewById(R.id.lbl_location);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMHome.class);
        mViewModel.getMobileNo().observe(getViewLifecycleOwner(), s -> lblMobile.setText(s));
        mViewModel.getEmployeeInfo().observe(getViewLifecycleOwner(), eEmployeeInfo -> {
            try {
                lblEmailx.setText(eEmployeeInfo.getEmailAdd());
                lblUserNm.setText(eEmployeeInfo.getUserName());
                lblPstion.setText(DeptCode.getDepartmentName(eEmployeeInfo.getDeptIDxx()));
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        lblAddrss.setText(poLocator.getAddress());

        /*btnLoginx.setOnClickListener(view -> {
            DialogQrCode loDialog = new DialogQrCode(getActivity());
            //loDialog.setAddress(poLocator.getAddress());
            loDialog.setAddress(poLocator.getAddress() + "\n Longitude" + poLocator.getLongitude() + "\n Lattitude" +poLocator.getLattitude());
            loDialog.show();
        });*/

        /*btnSelfie.setOnClickListener(view -> {
            poFilexx.CreateFile((openCamera, camUsage, photPath, latitude, longitude) -> {
            photoPath = photPath;
            this.latitude = latitude;
            this.longitude = longitude;

            Log.e("Latitude: ", String.valueOf(latitude));
            Log.e("Longitude: ", String.valueOf(longitude));

            startActivityForResult(openCamera, REQUEST_IMAGE_CAPTURE);
            });
        });*/
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