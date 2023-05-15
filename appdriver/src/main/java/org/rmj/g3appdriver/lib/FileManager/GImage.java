package org.rmj.g3appdriver.lib.FileManager;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class GImage {
    private static final String TAG = GImage.class.getSimpleName();

    private final Application instance;

    public GImage(Application instance) {
        this.instance = instance;
    }

    public static Bitmap GetImagePreview(String lsFilePath) {
        // Get the dimensions of the View
        int targetW = 1000;
        int targetH = 1000;

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(lsFilePath, bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.max(1, Math.min(photoW/targetW, photoH/targetH));

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(lsFilePath, bmOptions);

        Bitmap bOutput;
        float degrees = -90;
        Matrix matrix = new Matrix();
        matrix.setRotate(degrees);
        bOutput = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        return bOutput;
    }
}
