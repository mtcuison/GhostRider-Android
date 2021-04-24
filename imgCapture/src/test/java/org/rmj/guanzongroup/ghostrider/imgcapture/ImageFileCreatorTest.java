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

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class ImageFileCreatorTest {
    private ImageFileCreator poFileCreator;
    private String cameraUsage, fakePath;
    File actualDir;

    @Mock
    Context mockContext;

    @Before
    public void setUp() {
        cameraUsage = "CreditApplication";
        fakePath = "/folder/";
        poFileCreator = new ImageFileCreator(mockContext, cameraUsage);
        actualDir = poFileCreator.generateStorageDir();

    }

    @Test
    public void test_generateTimestamp() {
        assertEquals(new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()), poFileCreator.generateTimestamp());
    }

    @Test
    public void test_generateImageFileName() {
        assertEquals(cameraUsage + "_" + poFileCreator.generateTimestamp() + "_", poFileCreator.generateImageFileName());
    }

    //    Why the fuck is it returning null?
    @Test
    public void test_generateStorageDir() {
        assertEquals(mockContext.getExternalFilesDir( "/" + cameraUsage), poFileCreator.generateStorageDir());
    }

    @Test
    public void test_createImageFile() {
        try {
            assertNotNull(poFileCreator.createImageFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_galleryAddPic() {
        assertTrue(poFileCreator.galleryAddPic(fakePath));
    }


}