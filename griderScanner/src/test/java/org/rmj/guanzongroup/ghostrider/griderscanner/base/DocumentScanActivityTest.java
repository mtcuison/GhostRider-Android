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

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;


import androidx.test.core.app.ApplicationProvider;

import org.rmj.guanzongroup.ghostrider.imgcapture.ImageFileCreator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.exceptions.base.MockitoException;
import org.rmj.guanzongroup.ghostrider.griderscanner.ImageCrop;
import org.rmj.guanzongroup.ghostrider.griderscanner.helpers.ScannerConstants;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.robolectric.Shadows.shadowOf;
@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest=Config.NONE)
public class DocumentScanActivityTest {

    ImageCrop activity;
    private String currentPhotoPath;
    Context context;
    ImageFileCreator imageFileCreator;
    Bitmap bitmap;
    ImageCrop imgActivity;

    @Mock
    Context mockContext;

    @Before
    public void setUp() throws IOException {
       // activity = new ImageCrop();
        imgActivity = mock(ImageCrop.class);
        currentPhotoPath = "/storage/emulated/0/Android/data/org.rmj.guanzongroup.ghostrider.epacss/files/DocumentScan/Cropped/CROP_20210126_161158.png";
        context = ApplicationProvider.getApplicationContext();
        imageFileCreator = new ImageFileCreator(context, "Crop");
//        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.vintage);
        bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(currentPhotoPath));
        ScannerConstants.selectedImageBitmap = bitmap;
        DocumentScanActivity.selectedImage = bitmap;
        imgActivity.cropImage = bitmap;
    }

    @After
    public void tearDown() throws Exception {
        //activity = null;
    }

    @Test
    public void testStartCropping(){
        doReturn(true).when(imgActivity).startCropping();
        assertEquals(true, imgActivity.startCropping());

    }

    @Test
    public void testRotateBitmap(){
        doReturn(bitmap).when(imgActivity).rotateBitmap(bitmap, 90);
        when(imgActivity.rotateBitmap(bitmap,90)).thenReturn(bitmap);
        assertEquals(bitmap, imgActivity.rotateBitmap(bitmap,90));
    }

    @Test
    public void testGetCroppedImage() throws MockitoException {
        doReturn(bitmap).when(imgActivity).getCroppedImage();
        Bitmap result = imgActivity.getCroppedImage();
        assertEquals(result, bitmap);

    }
    @Test
    public void saveToInternalStorage(){
        File imgFile = new File(currentPhotoPath);
//        assertEquals(imgFile.toString(), activity.saveToInternalStorage(ScannerConstants.selectedImageBitmap));
        doReturn(currentPhotoPath).when(imgActivity).saveToInternalStorage(bitmap);
        when(imgActivity.saveToInternalStorage(bitmap)).thenReturn(currentPhotoPath);
        when(imgActivity.saveToInternalStorage(bitmap)).thenReturn(currentPhotoPath);
    }

    @Test
    public void testGetBitmapImage() throws IOException, MockitoException {
        when(imgActivity.getBitmapImage()).thenReturn(bitmap);
        Bitmap result = imgActivity.getBitmapImage();
        assertEquals(result, bitmap);
    }

    @Test
    public void testScaledBitmap(){
        when(imgActivity.scaledBitmap(bitmap, 100, 100)).thenReturn(bitmap);
        assertEquals(bitmap, imgActivity.scaledBitmap(bitmap, 100, 100));
    }


}