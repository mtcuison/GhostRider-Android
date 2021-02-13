package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Fragments;

import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.VMCamera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static android.Manifest.permission_group.CAMERA;

public class Fragment_Camera extends Fragment {

    private VMCamera mViewModel;

    public static Fragment_Camera newInstance() {
        return new Fragment_Camera();
    }

    private final int PREFERED_IMAGE_WIDTH_SIZE = 1200;

    private final int CAMERA_PERMISSION_CODE = 300;
    private final int RESULT_CAMERA_LOAD_IMG = 1889;

    private FrameLayout frameLayout;
    private ImageView image_view;
    private Activity activity;

    private List<String> imagesFilesPaths = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_camera, container, false);

        return root;


    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activity = getActivity();
        image_view = getActivity().findViewById(R.id.image_view);
        frameLayout = getActivity().findViewById(R.id.frameLayout);


        takePhotoFromCamera();
    }

    // THIS FUNCTION CAN BE CALLED WHENEVER YOU WANT TO TAKE NEW PHOTO
    private void takePhotoFromCamera(){
        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()), CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{CAMERA}, CAMERA_PERMISSION_CODE);
            }
        } else  {
            openCameraIntent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == CAMERA_PERMISSION_CODE){
            if (permissions.length > 0 && grantResults.length > 0) {

                boolean flag = true;
                for (int grantResult : grantResults) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        flag = false;
                    }
                }
                if (flag) {
                    openCameraIntent();
                }
            }
        }else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void openCameraIntent(){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(cameraIntent.resolveActivity(Objects.requireNonNull(activity).getPackageManager()) != null){
            //Create a file to store the image
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(Objects.requireNonNull(getContext()),"com.smartestmedia.camerainfragment.provider", photoFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(cameraIntent, RESULT_CAMERA_LOAD_IMG);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        imagesFilesPaths.add(image.getAbsolutePath());
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RESULT_CAMERA_LOAD_IMG && resultCode == Activity.RESULT_OK){

            String tempImageFilePath = imagesFilesPaths.get(imagesFilesPaths.size()-1);

            Uri tempImageURI = Uri.fromFile(new File( tempImageFilePath ));

            resizeThanLoadImage(tempImageFilePath, tempImageURI);

        }

    }

    private void resizeThanLoadImage(String tempImageFilePath, Uri tempImageURI){
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(Objects.requireNonNull(activity).getContentResolver(), tempImageURI);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(bitmap == null)return;

        int sizeDivisor = 1;

        double imageSize = bitmap.getWidth();
        if(bitmap.getHeight() > bitmap.getWidth())imageSize = bitmap.getHeight();
        sizeDivisor = round(imageSize / PREFERED_IMAGE_WIDTH_SIZE);
        if(sizeDivisor == 0)sizeDivisor = 1;


        Bitmap bitmapScaled = Bitmap.createScaledBitmap(bitmap, (bitmap.getWidth()/sizeDivisor), (bitmap.getHeight()/sizeDivisor), true);

        storeBitmapInFile(bitmapScaled, tempImageFilePath);

        onImageLoaded(tempImageURI);
    }

    private int round(double d){
        double dAbs = Math.abs(d);
        int i = (int) dAbs;
        double result = dAbs - (double) i;
        if(result<0.5){
            return d<0 ? -i : i;
        }else{
            return d<0 ? -(i+1) : i+1;
        }
    }

    private void storeBitmapInFile(Bitmap image, String filePath) {
        File pictureFile = new File(filePath);
        String TAG = "TakePhotosFragment>>>> ";
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.JPEG, 80, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d(TAG, "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, "Error accessing file: " + e.getMessage());
        }
    }

    private void onImageLoaded(Uri imageUri) {

        // LOAD YOUR IMAGE in imageview
        image_view.setImageURI(imageUri);
    }
}