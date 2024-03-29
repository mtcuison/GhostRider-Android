/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.griderScanner
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.griderscanner.base;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


//import org.opencv.core.MatOfPoint2f;
//import org.opencv.core.Point;
import org.rmj.guanzongroup.ghostrider.griderscanner.R;
import org.rmj.guanzongroup.ghostrider.griderscanner.libraries.NativeClass;
import org.rmj.guanzongroup.ghostrider.griderscanner.libraries.PolygonView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static org.rmj.guanzongroup.ghostrider.griderscanner.ImageCrop.cropImage;

public abstract class DocumentScanActivity extends AppCompatActivity {

    protected CompositeDisposable disposable = new CompositeDisposable();
    public static Bitmap selectedImage;
    private final NativeClass nativeClass = new NativeClass();


    protected abstract FrameLayout getHolderImageCrop();

    protected abstract ImageView getImageView();

    protected abstract PolygonView getPolygonView();

    protected abstract void showProgressBar();

    protected abstract void hideProgressBar();

    protected abstract void showError(CropperErrorType errorType);

    protected abstract Bitmap getBitmapImage();

    private void setImageRotation() {
//        Bitmap tempBitmap = selectedImage.copy(selectedImage.getConfig(), true);
//        for (int i = 1; i <= 4; i++) {
//            MatOfPoint2f point2f = nativeClass.getPoint(tempBitmap);
//            if (point2f == null) {
//                tempBitmap = rotateBitmap(tempBitmap, 90 * i);
//            } else {
//                selectedImage = tempBitmap.copy(selectedImage.getConfig(), true);
//                break;
//            }
//        }
    }

    protected Bitmap rotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        Bitmap sourceCreated = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
        return sourceCreated;
    }

    private void setProgressBar(boolean isShow) {
        if (isShow)
            showProgressBar();
        else
            hideProgressBar();
    }

    protected boolean startCropping() {
        selectedImage = getBitmapImage();
        //selectedImage = cropImage;
        setProgressBar(true);
        disposable.add(Observable.fromCallable(() -> {
                    setImageRotation();
                    return false;
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe((result) -> {
                            initializeCropping();
                            setProgressBar(false);
                        })
        );
        return selectedImage!= null;
    }


    public boolean initializeCropping() {
        Bitmap scaledBitmap = scaledBitmap(selectedImage, getHolderImageCrop().getWidth(), getHolderImageCrop().getHeight());
        getImageView().setImageBitmap(scaledBitmap);

        Bitmap tempBitmap = ((BitmapDrawable) getImageView().getDrawable()).getBitmap();
        Map<Integer, PointF> pointFs = null;
        try {
            pointFs = getEdgePoints(tempBitmap);
            getPolygonView().setPoints(pointFs);
            getPolygonView().setVisibility(View.VISIBLE);

            int padding = (int) getResources().getDimension(R.dimen.scanPadding);

            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(tempBitmap.getWidth() + 2 * padding, tempBitmap.getHeight() + 2 * padding);
            layoutParams.gravity = Gravity.CENTER;

            getPolygonView().setLayoutParams(layoutParams);
            getPolygonView().setPointColor(getResources().getColor(R.color.blue));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    protected Bitmap getCroppedImage() {
        try {
            Map<Integer, PointF> points = getPolygonView().getPoints();

            float xRatio = (float) selectedImage.getWidth() / getImageView().getWidth();
            float yRatio = (float) selectedImage.getHeight() / getImageView().getHeight();

            float x1 = (Objects.requireNonNull(points.get(0)).x) * xRatio;
            float x2 = (Objects.requireNonNull(points.get(1)).x) * xRatio;
            float x3 = (Objects.requireNonNull(points.get(2)).x) * xRatio;
            float x4 = (Objects.requireNonNull(points.get(3)).x) * xRatio;
            float y1 = (Objects.requireNonNull(points.get(0)).y) * yRatio;
            float y2 = (Objects.requireNonNull(points.get(1)).y) * yRatio;
            float y3 = (Objects.requireNonNull(points.get(2)).y) * yRatio;
            float y4 = (Objects.requireNonNull(points.get(3)).y) * yRatio;

            Bitmap finalBitmap = getBitmapImage();
//            nativeClass.getScannedBitmap(finalBitmap, x1, y1, x2, y2, x3, y3, x4, y4);
            return null;
        } catch (Exception e) {
            showError(CropperErrorType.CROP_ERROR);
            return null;
        }
    }

    protected Bitmap scaledBitmap(Bitmap bitmap, int width, int height) {
        Matrix m = new Matrix();
        m.setRectToRect(new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight()), new RectF(0, 0, width, height), Matrix.ScaleToFit.CENTER);
        Bitmap createdBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
        return createdBitmap;
    }

    private Map<Integer, PointF> getEdgePoints(Bitmap tempBitmap) throws Exception {
        List<PointF> pointFs = getContourEdgePoints(tempBitmap);
        Map<Integer, PointF> orderedPoints = orderedValidEdgePoints(tempBitmap, pointFs);
        return orderedPoints;
    }

    private List<PointF> getContourEdgePoints(Bitmap tempBitmap) {
//        MatOfPoint2f point2f = nativeClass.getPoint(tempBitmap);
//        if (point2f == null)
//            point2f = new MatOfPoint2f();
//        List<Point> points = Arrays.asList(point2f.toArray());
        List<PointF> result = new ArrayList<>();
//        for (int i = 0; i < points.size(); i++) {
//            result.add(new PointF(((float) points.get(i).x), ((float) points.get(i).y)));
//        }

        return result;

    }

    private Map<Integer, PointF> getOutlinePoints(Bitmap tempBitmap) {
        Map<Integer, PointF> outlinePoints = new HashMap<>();
        outlinePoints.put(0, new PointF(0, 0));
        outlinePoints.put(1, new PointF(tempBitmap.getWidth(), 0));
        outlinePoints.put(2, new PointF(0, tempBitmap.getHeight()));
        outlinePoints.put(3, new PointF(tempBitmap.getWidth(), tempBitmap.getHeight()));
        return outlinePoints;
    }

    private Map<Integer, PointF> orderedValidEdgePoints(Bitmap tempBitmap, List<PointF> pointFs) {
        Map<Integer, PointF> orderedPoints = getPolygonView().getOrderedPoints(pointFs);
        if (!getPolygonView().isValidShape(orderedPoints)) {
            orderedPoints = getOutlinePoints(tempBitmap);
        }
        return orderedPoints;
    }

    @Override
    protected void onStop() {
        super.onStop();
        disposable.clear();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
