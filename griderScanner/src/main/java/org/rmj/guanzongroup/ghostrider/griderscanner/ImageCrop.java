package org.rmj.guanzongroup.ghostrider.griderscanner;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;


import androidx.appcompat.widget.Toolbar;

import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicationDocuments;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.guanzongroup.ghostrider.griderscanner.R;
import org.rmj.guanzongroup.ghostrider.griderscanner.base.CropperErrorType;
import org.rmj.guanzongroup.ghostrider.griderscanner.base.DocumentScanActivity;
import org.rmj.guanzongroup.ghostrider.griderscanner.helpers.ScannerConstants;
import org.rmj.guanzongroup.ghostrider.griderscanner.libraries.PolygonView;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static org.rmj.guanzongroup.ghostrider.griderscanner.ClientInfo.infoModel;
import static org.rmj.guanzongroup.ghostrider.griderscanner.ClientInfo.poDocumentsInfo;
import static org.rmj.guanzongroup.ghostrider.griderscanner.ClientInfo.poImageInfo;

public class ImageCrop extends DocumentScanActivity {

    public static FrameLayout holderImageCrop;
    private static ImageView imageView;
    private static PolygonView polygonView;
    private static boolean isInverted;
    private ProgressBar progressBar;
    public static Bitmap cropImage;
    private final View.OnClickListener btnImageEnhanceClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showProgressBar();
            disposable.add(
                    Observable.fromCallable(() -> {
                        cropImage = getCroppedImage();
                        if (cropImage == null)
                            return false;
                        if (!ScannerConstants.saveStorage)
                            saveToInternalStorage(cropImage);


                        return false;
                    })
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe((result) -> {
                                hideProgressBar();
                                if (cropImage != null) {
                                    ScannerConstants.selectedImageBitmap = cropImage;
                                    setResult(RESULT_OK);
                                    finish();
                                }
                            })
            );
        }
    };
    private final View.OnClickListener btnRebase = v -> {
        cropImage = ScannerConstants.selectedImageBitmap.copy(ScannerConstants.selectedImageBitmap.getConfig(), true);
        isInverted = false;
        startCropping();
    };
    private final View.OnClickListener btnCloseClick = v -> finish();
    private final View.OnClickListener btnInvertColor = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showProgressBar();
            disposable.add(
                    Observable.fromCallable(() -> {
                        invertColor();
                        return false;
                    })
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe((result) -> {
                                hideProgressBar();
                                Bitmap scaledBitmap = scaledBitmap(cropImage, holderImageCrop.getWidth(), holderImageCrop.getHeight());
                                imageView.setImageBitmap(scaledBitmap);
                            })
            );
        }
    };
    private final View.OnClickListener onRotateClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showProgressBar();
            disposable.add(
                    Observable.fromCallable(() -> {
                        if (isInverted)
                            invertColor();
                        cropImage = rotateBitmap(cropImage, 90);
                        return false;
                    })
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe((result) -> {
                                hideProgressBar();
                                startCropping();
                            })
            );
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_crop);
        cropImage = ScannerConstants.selectedImageBitmap;
        isInverted = false;
        if (ScannerConstants.selectedImageBitmap != null)
            initView();
        else {
            Toast.makeText(this, ScannerConstants.imageError, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    protected FrameLayout getHolderImageCrop() {
        return holderImageCrop;
    }

    @Override
    protected ImageView getImageView() {
        return imageView;
    }

    @Override
    protected PolygonView getPolygonView() {
        return polygonView;
    }

    @Override
    protected void showProgressBar() {
        RelativeLayout rlContainer = findViewById(R.id.rlContainer);
        setViewInteract(rlContainer, false);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void hideProgressBar() {
        RelativeLayout rlContainer = findViewById(R.id.rlContainer);
        setViewInteract(rlContainer, true);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void showError(CropperErrorType errorType) {
        switch (errorType) {
            case CROP_ERROR:
                //Toast.makeText(this, ScannerConstants.cropError, Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public Bitmap getBitmapImage() {
        return cropImage;
    }

    private void setViewInteract(View view, boolean canDo) {
        view.setEnabled(canDo);
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                setViewInteract(((ViewGroup) view).getChildAt(i), canDo);
            }
        }
    }

    private void initView() {
        Button btnImageCrop = findViewById(R.id.btnImageCrop);
        Button btnClose = findViewById(R.id.btnClose);
        holderImageCrop = findViewById(R.id.holderImageCrop);
        imageView = findViewById(R.id.imageView);
        ImageView ivRotate = findViewById(R.id.ivRotate);
        ImageView ivInvert = findViewById(R.id.ivInvert);
        ImageView ivRebase = findViewById(R.id.ivRebase);
        btnImageCrop.setText(ScannerConstants.cropText);
        btnClose.setText(ScannerConstants.backText);
        polygonView = findViewById(R.id.polygonView);
        progressBar = findViewById(R.id.progressBar);
        if (progressBar.getIndeterminateDrawable() != null && ScannerConstants.progressColor != null)
            progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor(ScannerConstants.progressColor), android.graphics.PorterDuff.Mode.MULTIPLY);
        else if (progressBar.getProgressDrawable() != null && ScannerConstants.progressColor != null)
            progressBar.getProgressDrawable().setColorFilter(Color.parseColor(ScannerConstants.progressColor), android.graphics.PorterDuff.Mode.MULTIPLY);
        btnImageCrop.setBackgroundColor(Color.parseColor(ScannerConstants.cropColor));
        btnClose.setBackgroundColor(Color.parseColor(ScannerConstants.backColor));
        btnImageCrop.setOnClickListener(btnImageEnhanceClick);
        btnClose.setOnClickListener(btnCloseClick);
        ivRotate.setOnClickListener(onRotateClick);
        ivInvert.setOnClickListener(btnInvertColor);
        ivRebase.setOnClickListener(btnRebase);
        Toolbar toolbar = findViewById(R.id.toolbar_scanner);
        setSupportActionBar(toolbar);
        setTitle("Image Crop");
        startCropping();
    }

    private void invertColor() {
        if (!isInverted) {
            Bitmap bmpMonochrome = Bitmap.createBitmap(cropImage.getWidth(), cropImage.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bmpMonochrome);
            ColorMatrix ma = new ColorMatrix();
            ma.setSaturation(0);
            Paint paint = new Paint();
            paint.setColorFilter(new ColorMatrixColorFilter(ma));
            canvas.drawBitmap(cropImage, 0, 0, paint);
            cropImage = bmpMonochrome.copy(bmpMonochrome.getConfig(), true);
        } else {
            cropImage = cropImage.copy(cropImage.getConfig(), true);
        }
        isInverted = !isInverted;
    }

    public String saveToInternalStorage(Bitmap bitmapImage) {

        poImageInfo = new EImageInfo();
        poDocumentsInfo = new ECreditApplicationDocuments();
        getRealPathFromURI (ScannerConstants.PhotoPath);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = ScannerConstants.TransNox + "_" + ScannerConstants.FileCode + "_" + timeStamp + ".png";
        File storageDir = getExternalFilesDir( "/" + ScannerConstants.Folder + "/" + ScannerConstants.Usage);
        //File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
//        String imageFileName = "cropped_" + timeStamp + ".png";
        File mypath = new File(storageDir, imageFileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ERROR Exception", e.getMessage());
        }
        Log.e("Crop Image Path", mypath.getAbsolutePath());
        infoModel.setDocFilePath(mypath.getAbsolutePath());
        poImageInfo.setDtlSrcNo(ScannerConstants.TransNox);
        poImageInfo.setSourceNo(ScannerConstants.TransNox);
        poImageInfo.setSourceCD("COAD");
        poImageInfo.setImageNme(imageFileName);
        poImageInfo.setFileLoct(mypath.getAbsolutePath());
        poImageInfo.setFileCode(ScannerConstants.FileCode);
        poImageInfo.setLatitude(String.valueOf(ScannerConstants.Latt));
        poImageInfo.setLongitud(String.valueOf(ScannerConstants.Longi));
//                        mViewModel.setLatitude(String.valueOf(latitude));
//                        mViewModel.setLongitude(String.valueOf(longitude));
//                        mViewModel.setImgName(FileName);
        poDocumentsInfo.setEntryNox(ScannerConstants.EntryNox);
        poDocumentsInfo.setTransNox(ScannerConstants.TransNox);
        poDocumentsInfo.setImageNme(imageFileName);
        poDocumentsInfo.setFileCode(ScannerConstants.FileCode);
        poDocumentsInfo.setFileLoc(mypath.getAbsolutePath());
        return storageDir.getAbsolutePath();

    }
    public String getRealPathFromURI (String contentUri) {
        File target = new File(contentUri);
        if (target.exists() && target.isFile() && target.canWrite()) {
            target.delete();
            Log.d("d_file", "" + target.getName());
        }
        return target.toString();
    }

}