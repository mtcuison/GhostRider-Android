package org.rmj.guanzongroup.ghostrider.griderscanner;

import android.net.Uri;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class GriderScannerTest {
    private String currentPhotoPath;
    String CAMERA_USAGE;
    GriderScanner activity;
    @Before
    public void setUp() throws Exception {
        activity = new GriderScanner();
        CAMERA_USAGE = "DocumentScan";
        currentPhotoPath = "\\storage\\emulated\\0\\Android\\data\\org.rmj.guanzongroup.ghostrider.epacss\\files\\DocumentScan\\Cropped\\CROP_20210116_135529.png";
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getRealPathFromURI() {
        File target = new File(currentPhotoPath);

        assertEquals(target.toString(), activity.getRealPathFromURI(currentPhotoPath));
    }

    @Test
    public void galleryPick() {
        assertTrue(!activity.galleryPick(Uri.parse(currentPhotoPath)));
    }

    @Test
    public void cameraCapture() {
        assertTrue(!activity.cameraCapture(currentPhotoPath));
    }

    @Test
    public void cropPic() {
        assertTrue(!activity.cropPic());
    }
}