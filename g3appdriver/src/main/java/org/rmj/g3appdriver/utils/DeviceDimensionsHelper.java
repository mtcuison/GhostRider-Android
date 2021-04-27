/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.g3appdriver.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;

public class DeviceDimensionsHelper {

    public static Bitmap scaleToActualAspectRatio(Context context,Bitmap bitmap) {
        if (bitmap != null) {
            boolean flag = true;
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            int deviceWidth = displayMetrics.widthPixels;
            int deviceHeight = displayMetrics.heightPixels;
            int bitmapHeight = bitmap.getHeight();
            int bitmapWidth = bitmap.getWidth();

            if (bitmapWidth > deviceWidth) {
                flag = false;
                int scaledWidth = deviceWidth;
                int scaledHeight = (scaledWidth * bitmapHeight) / bitmapWidth;

                try {
                    if (scaledHeight > deviceHeight)
                        scaledHeight = deviceHeight;

                    bitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth,
                            scaledHeight, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (flag) {
                if (bitmapHeight > deviceHeight) {
                    // scale According to HEIGHT
                    int scaledHeight = deviceHeight;
                    int scaledWidth = (bitmapWidth *  scaledHeight)
                            / bitmapHeight;

                    try {
                        if (scaledWidth > deviceWidth)
                            scaledWidth = deviceWidth;

                        bitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth,
                                scaledHeight, true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return bitmap;
    }
}
