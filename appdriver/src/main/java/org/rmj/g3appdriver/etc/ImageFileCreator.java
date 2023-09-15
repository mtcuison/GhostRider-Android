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

package org.rmj.g3appdriver.etc;

import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageFileCreator {
    public static final String TAG = ImageFileCreator.class.getSimpleName();
    private final Context poContext;

    private String cameraUsage;
    private String imgName;
    private  String folder_name;
    private String TransNox, FileCode;
    private int EntryNox;
    String currentPhotoPath;
    private double latitude, longitude;
    private String SUB_FOLDER;

    File image;
    File docImage;

    public static int GCAMERA = 112;

    public static class FILE_CODE{
        public static String DCP = "dcp";
        public static String CNA = "CNA";
        public static String SELFIE_LOGIN = "Selfie_Login";
        //TODO: add more file code for additional feature for app
    }

    public ImageFileCreator(Context context, String usage) {
        this.poContext = context;
        this.cameraUsage = usage;
    }

    public void setTransNox(String transNox){
        this.TransNox = transNox;
    }
    public ImageFileCreator(Context context, String usage, String imgName) {
        this.poContext = context;
        this.SUB_FOLDER = usage;
        this.imgName = imgName;
    }

    public ImageFileCreator(Context context, String subFolder, String fileCode, int entryNox, String transNox) {
        this.poContext = context;
        this.FileCode = fileCode;
        this.TransNox = transNox;
        this.SUB_FOLDER = subFolder;
        this.EntryNox = entryNox;
    }

    public void setImageName(String fsName){
        this.imgName = fsName;
    }

    public String getCameraUsage() {
        return cameraUsage;
    }

    public void CreateFile(OnImageFileWithLocationCreatedListener listener) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(poContext.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
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
                        generateImageFileName());
            }
        }
    }

    public File createImageFile() throws IOException {

        image = new File(
                generateMainStorageDir(),
                generateImageFileName());

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();

        Log.e(TAG, currentPhotoPath + " createImageFile");
        return image;
    }

    public File createScanImageFile() throws IOException {
        docImage = new File(
                generateMainStorageDirScan(),
//                generateStorageDir(),
                generateImageScanFileName());

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = docImage.getAbsolutePath();

        Log.e(TAG, currentPhotoPath);
        return docImage;
    }
///CreateFile for Document Scanner Camera
    private File createImageScanFile() throws IOException {

        String root = Environment.getExternalStorageDirectory().toString();
        File sd = new File(root + "/"+ poContext.getPackageName() + "/" + "COAD" + "/");
        String imageFileName = TransNox + "_" + EntryNox + "_" + FileCode + ".png";
        File mypath = new File(sd, imageFileName);
        currentPhotoPath = mypath.getAbsolutePath();
        return mypath;
    }

    @SuppressLint("SimpleDateFormat")
    public String generateTimestamp() {
        return new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    }

    public String generateImageFileName() {
        String lsResult = imgName + "_" + generateTimestamp() + ".png";
        return lsResult;
    }
    public String generateImageScanFileName() {
        String lsResult = TransNox + "_" + EntryNox + "_" + FileCode + ".png";
        return lsResult;
    }

    public String generateDCPImageFileName() {
        return imgName + "_" + generateTimestamp() + "_";
    }

    public File generateStorageDir() {
        return poContext.getExternalFilesDir( "/" + cameraUsage);
    }

    public File generateMainStorageDir() {
        String root = String.valueOf(poContext.getExternalFilesDir(null));
        File sd = new File(root + "/" + SUB_FOLDER + "/");
        if (!sd.exists()) {
            sd.mkdirs();
        }
        return sd;
//        return sd;
    }
    public File generateMainStorageDirScan() {
        String root = Environment.getExternalStorageDirectory().toString();
        File sd = new File(root + "/" + "/" + SUB_FOLDER+ "/" + TransNox + "/");
        if (!sd.exists() && !sd.mkdirs()){
            Log.d(TAG, "failed to create directory");
        }
        return sd;
    }


    //CreateFile for Document Scanner Camera
    public void CreateScanFile(OnImageFileWithLocationCreatedListener listener) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(poContext.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createScanImageFile();

            } catch (IOException e) {
                e.printStackTrace();
            }

            // Continue only if the File was successfully created

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(poContext,
                        "org.rmj.guanzongroup.ghostrider.epacss.provider",
                        photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                listener.OpenCameraWithLocation(
                        takePictureIntent,
                        cameraUsage,
                        currentPhotoPath,
                        generateImageScanFileName());
            }
        }
    }


    //CreateFile for Document Scanner Camera


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
        void OpenCameraWithLocation(Intent openCamera,String camUsage, String photPath, String FileName);
    }

    public interface OnImageFileDCPWithLocationCreatedListener{
        void OpenDCPCameraWithLocation(Intent openCamera,String photPath, double latitude, double longitude);
    }
    //Added String FileName for Creating MD5Hash
    public interface OnScanImageFileWithLocationCreatedListener{
        void OpenCameraWithLocation(Intent openCamera,String FileCode, String photPath, String TransNox, double latitude, double longitude);
    }

    private Intent loIntent;
    private String message = "Something went wrong please restart the app and try again.", sFileName, sFilePath;

    public String getFileName() {
        return sFileName;
    }

    public String getFilePath() {
        return sFilePath;
    }

    public String getMessage() {
        return message;
    }

    public Intent getCameraIntent() {
        return loIntent;
    }

    public boolean SaveDocumentScan(Bitmap bitmap){
        try{
            File photoFile = createImageFile();
            OutputStream outputStream = new FileOutputStream(photoFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();

            return false;
        } catch (Exception e){
            e.printStackTrace();
                    message = getLocalMessage(e);
            return false;
        }
    }

    public boolean IsFileCreated(boolean cSelfieLog) {
        try {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(poContext.getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    message = ex.getMessage();
                    return false;
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(poContext,
                            "org.rmj.guanzongroup.ghostrider.epacss" + ".provider",
                            photoFile);

                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    if(cSelfieLog) {
                        takePictureIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
                    }
                    sFileName = generateImageFileName();
                    sFilePath = currentPhotoPath;
                    loIntent = takePictureIntent;
                    return true;
                }
            }
            return false;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }
}
