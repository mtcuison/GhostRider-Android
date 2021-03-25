package org.rmj.guanzongroup.ghostrider.griderscanner.helpers;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import java.io.Serializable;

public class ScannerConstants implements Serializable {
    public static Bitmap selectedImageBitmap;
    public static Uri selectedImageUri;
    public static String cropText="Crop",backText="Close",
            imageError= "No image selected, please try again.",
            cropError="You have not selected a valid field. Please make corrections until the lines turn blue.";
    public static String cropColor="#6666ff",backColor="#ff0000",progressColor="#f88222";
    public static boolean saveStorage=false;
    public static String TransNox;
    public static String DocTransNox;
    public static String FileCode;
    public static String PhotoPath;

    public static String Folder;
    public static String SubFolder;
    public static String Usage;
    public static int EntryNox;
    public static String FileName;
    public static String FileDesc;
    public static double Latt;
    public static double Longi;
    public static String SourceCode;

}
