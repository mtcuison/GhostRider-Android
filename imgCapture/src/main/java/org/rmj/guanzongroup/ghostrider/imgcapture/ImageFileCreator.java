package org.rmj.guanzongroup.ghostrider.imgcapture;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;

import androidx.core.content.FileProvider;

import org.rmj.g3appdriver.GRider.Etc.GeoLocator;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageFileCreator {
    public static final String TAG = ImageFileCreator.class.getSimpleName();
    private final Context poContext;

    private String cameraUsage;
    private String imgName;
    private  String folder_name;
    String currentPhotoPath;
    private double latitude, longitude;

    GeoLocator poLocator;

    File image;

    public static int GCAMERA = 112;

    public static class FILE_CODE{
        public static String DCP = "dcp";
        public static String SELFIE_LOGIN = "Selfie_Login";
        //TODO: add more file code for additional feature for app
    }

    public ImageFileCreator(Context context, String usage) {
        this.poContext = context;
        this.cameraUsage = usage;
    }

    public ImageFileCreator(Context context,String folder, String usage, String imgName) {
        this.poContext = context;
        this.folder_name = folder;
        this.cameraUsage = usage;
        this.imgName = imgName;
    }

    public void setImageName(String fsName){
        this.imgName = fsName;
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
                        generateImageFileName(),
                        latitude,
                        longitude);
            }
        }
    }
    public File createImageFile() throws IOException {
        image = new File(
                generateStorageDir(),
                generateImageFileName());

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @SuppressLint("SimpleDateFormat")
    public String generateTimestamp() {
        return new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    }

    public String generateImageFileName() {
        String lsResult = cameraUsage + "_" + generateTimestamp() + ".jpeg";
        return lsResult;
    }

    public String generateDCPImageFileName() {

        return imgName + "_" + generateTimestamp() + "_";
    }

    public File generateStorageDir() {
        return poContext.getExternalFilesDir( "/" + cameraUsage);
    }

    public File generateMainStorageDir() {
        return poContext.getExternalFilesDir( "/"+ folder_name + "/" + cameraUsage);
    }

    //CreateFile for Document Scanner Camera
    @SuppressLint("QueryPermissionsNeeded")
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

        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
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

    //CreateFile for Document Scanner Camera

    public void CreateDCPFile(OnImageFileDCPWithLocationCreatedListener listener) {
        poLocator = new GeoLocator(poContext, (Activity) poContext);
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(poContext.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createDCPImageFile();
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
                listener.OpenDCPCameraWithLocation(
                        takePictureIntent,
                        currentPhotoPath,
                        latitude,
                        longitude);
            }
        }
    }

    public File createDCPImageFile() throws IOException {
        image = File.createTempFile(
                generateDCPImageFileName(),  /* prefix */
                ".jpg",         /* suffix */
                generateMainStorageDir()      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
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

    //Edited by mike 2021/02/26
    //Added String FileName for Creating MD5Hash
    public interface OnImageFileWithLocationCreatedListener{
        void OpenCameraWithLocation(Intent openCamera,String camUsage, String photPath, String FileName, double latitude, double longitude);
    }

    public interface OnImageFileDCPWithLocationCreatedListener{
        void OpenDCPCameraWithLocation(Intent openCamera,String photPath, double latitude, double longitude);
    }

}
