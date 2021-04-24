/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.imgCapture
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.ghostrider.imgcapture;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.imgcapture.R;

import com.google.android.material.button.MaterialButton;

public class DCPPhotoCapture extends AppCompatActivity {

    private static String CAMERA_USAGE = "CreditApplication";
    private static String MAIN_FOLDER = "CreditApplication";
    private ImageView imgProfile;
    private TextView directory,latitude, longitude;
    private MaterialButton camera, list;

    private ImageFileCreator poFilexx;

    private String photoPath;
    private double varLatitude, varLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_c_p_photo_capture);

        imgProfile = findViewById(R.id.img_profile);
        directory = findViewById(R.id.directory);
        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
        camera = findViewById(R.id.camera);

        poFilexx = new ImageFileCreator(this, CAMERA_USAGE);

        camera.setOnClickListener(view -> {
            poFilexx.CreateFile((openCamera, camUsage, photPath, FileName, latitude, longitude) -> {
                photoPath = photPath;
                varLatitude = latitude;
                varLongitude = longitude;

                Log.e("Latitude: ", String.valueOf(latitude));
                Log.e("Longitude: ", String.valueOf(longitude));

                startActivityForResult(openCamera, 1);
            });
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Save to mysqlLite DB
            poFilexx.galleryAddPic(photoPath);
            setPic();
            directory.setText("Directory: " + photoPath);
            latitude.setText("Latitude: " + String.valueOf(varLatitude));
            longitude.setText("Longitude: " + String.valueOf(varLongitude));
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