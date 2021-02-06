package com.example.imgcapture;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;

import androidx.core.content.FileProvider;

import org.rmj.g3appdriver.GRider.Etc.GeoLocator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageFileCreator {
    public static final String TAG = ImageFileCreator.class.getSimpleName();
    private final Context poContext;

    private String cameraUsage;
    String currentPhotoPath;
    private double latitude, longitude;

    GeoLocator poLocator;

    File image;

    public ImageFileCreator(Context context, String usage) {
        this.poContext = context;
        this.cameraUsage = usage;
    }

    public String getCameraUsage() {
        return cameraUsage;
    }

    public void CreateFile(OnImageFileWithLocationCreatedListener listener) {
        poLocator = new GeoLocator(poContext, (Activity) poContext);
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(poContext.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
                latitude = poLocator.getLattitude();
                longitude = poLocator.getLongitude();
            } catch (IOException ex) {
                // Error occurred while creating the File...
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(poContext,
                        "org.rmj.guanzongroup.ghostrider.epacss"+ ".provider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                listener.OpenCameraWithLocation(
                        takePictureIntent,
                        cameraUsage,
                        currentPhotoPath,
                        latitude,
                        longitude);
            }
        }
    }
    public File createImageFile() throws IOException {
        image = File.createTempFile(
                generateImageFileName(),  /* prefix */
                ".jpg",         /* suffix */
                generateStorageDir()      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public String generateTimestamp() {
        return new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    }

    public String generateImageFileName() {

        return cameraUsage + "_" + generateTimestamp() + "_";
    }

    public File generateStorageDir() {
        return poContext.getExternalFilesDir( "/" + cameraUsage);
    }

    //CreateFile for Document Scanner Camera
    public void CreateScanFile(OnImageFileCreatedListener listener) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(poContext.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageScanFile();
            } catch (IOException ex) {
            }
            if (photoFile != null) {
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                listener.OpenCamera(takePictureIntent, currentPhotoPath);
            }
        }
    }

    //CreateFile for Document Scanner Camera
    private File createImageScanFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = cameraUsage + "_" + timeStamp + ".png";
        File storageDir = poContext.getExternalFilesDir( "/" + cameraUsage );
        //File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
//        String imageFileName = "cropped_" + timeStamp + ".png";
        File mypath = new File(storageDir, imageFileName);
        currentPhotoPath = mypath.getAbsolutePath();
        Log.e("Image Path ", cameraUsage);
        return mypath;
    }
    public boolean galleryAddPic(String photoPath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(photoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        poContext.sendBroadcast(mediaScanIntent);

        return f != null;
    }

    public interface OnImageFileCreatedListener{
        void OpenCamera(Intent openCamera, String photPath);
    }

    public interface OnImageFileWithLocationCreatedListener{
        void OpenCameraWithLocation(Intent openCamera, String camUsage, String photPath, double latitude, double longitude);
    }
}
