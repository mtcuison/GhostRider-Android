package org.rmj.guanzongroup.ghostrider.griderscanner.helpers;

import android.graphics.Bitmap;
import android.net.Uri;

public class ScannerConstants {
    public static Bitmap selectedImageBitmap;
    public static Uri selectedImageUri;
    public static String cropText="Crop",backText="Close",
            imageError= "No image selected, please try again.",
            cropError="You have not selected a valid field. Please make corrections until the lines turn blue.";
    public static String cropColor="#6666ff",backColor="#ff0000",progressColor="#331199";
    public static boolean saveStorage=false;
}
