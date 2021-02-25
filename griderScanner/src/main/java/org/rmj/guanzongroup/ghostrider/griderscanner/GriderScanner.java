package org.rmj.guanzongroup.ghostrider.griderscanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import org.rmj.guanzongroup.ghostrider.imgcapture.ImageFileCreator;
import com.google.android.material.button.MaterialButton;

import org.rmj.guanzongroup.ghostrider.griderscanner.helpers.ScannerConstants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class GriderScanner extends AppCompatActivity {

    public ImageView imgBitmap;
    public static String mCurrentPhotoPath;
    public MaterialButton mbCamera, mbGallery, mbCropOkay;
    private ImageFileCreator poFilexx;
    private final String FOLDER_NAME = "DocumentScan";
    public static ContentResolver contentResolver;
    public static int CAMERA_REQUEST_CODE = 1231, GALLERY_REQUEST_CODE = 1111, CROP_REQUEST_CODE = 1234;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grider_scanner);
        Toolbar toolbar = findViewById(R.id.toolbar_scanner);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        poFilexx = new ImageFileCreator(this, FOLDER_NAME);
        imgBitmap = findViewById(R.id.imgBitmap);
        mbCamera = findViewById(R.id.mbCamera);
        mbGallery = findViewById(R.id.mbGallery);
        mbCropOkay= findViewById(R.id.btnCropOkay);
        contentResolver = this.getContentResolver();
        mbCamera.setOnClickListener(view -> poFilexx.CreateScanFile((openCamera, photPath) -> {
            mCurrentPhotoPath = photPath;
            startActivityForResult(openCamera, CAMERA_REQUEST_CODE);
        }));
        mbGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, GALLERY_REQUEST_CODE);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            if(mCurrentPhotoPath != null){
                File finalFile = new File(getRealPathFromURI(mCurrentPhotoPath));
                finalFile.delete();
            }
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            galleryPick(selectedImage);
            startActivityForResult(new Intent(this, ImageCrop.class), CROP_REQUEST_CODE);

        } else if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            cameraCapture(mCurrentPhotoPath);
            startActivityForResult(new Intent(this,ImageCrop.class), CROP_REQUEST_CODE);
        } else if (requestCode == CROP_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            cropPic();
        }
    }


    /**
     * Calling this will delete the images from cache directory
     * useful to clear some memory
     * @param contentUri
     */
    public String getRealPathFromURI (String contentUri) {
        File target = new File(contentUri);
        if (target.exists() && target.isFile() && target.canWrite()) {
            target.delete();
            Log.d("d_file", "" + target.getName());
        }
        return target.toString();
    }

    public static boolean galleryPick(Uri imgSelected){
        Bitmap bitmap = null;
        if (imgSelected != null) {
            InputStream inputStream;
            try {
                inputStream = contentResolver.openInputStream(imgSelected);
                if (inputStream != null) {
                    bitmap = BitmapFactory.decodeStream(inputStream, null, null);
                    ScannerConstants.selectedImageBitmap = bitmap;
                    mCurrentPhotoPath = imgSelected.getPath();
                    return true;
                }
            } catch (FileNotFoundException e) {
                Log.e("Unable Uri", "Unable to open overlay image Uri " + imgSelected, e);
                return false;
            }

        }
        return ScannerConstants.selectedImageBitmap != null;
    }

    public boolean cameraCapture (String photoPath) {
        try {
            ScannerConstants.selectedImageBitmap = MediaStore.Images.Media.getBitmap(
                    contentResolver,Uri.fromFile(new File(photoPath)));
        } catch (IOException e) {
            return false;
        }

        return ScannerConstants.selectedImageBitmap != null;
    }

    public boolean cropPic () {
        if (ScannerConstants.selectedImageBitmap != null) {
            imgBitmap.setImageBitmap(ScannerConstants.selectedImageBitmap);
            imgBitmap.setVisibility(View.VISIBLE);
            mbCropOkay.setVisibility(View.VISIBLE);

            if (mCurrentPhotoPath != null){
                File finalFile = new File(getRealPathFromURI(mCurrentPhotoPath));
                finalFile.delete();
                return true;
            } else
                return false;
        } else{
            //Toast.makeText(this, "Crop image failed!", Toast.LENGTH_LONG).show();
            return false;
        }
    }
}