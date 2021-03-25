package org.rmj.guanzongroup.ghostrider.imgcapture;

import android.content.Context;
import android.content.Intent;

public class ImageManager {
    private static final String TAG = ImageManager.class.getSimpleName();

    private final Context mContext;

    private String psUsagexx;
    private String psImageNm;
    private String psFoldrNm;
    private String psFilePth;
    private String psLatitud;
    private String lsLongitd;

    public interface OnImageFileCreatedListener{
        void OnCreated(Intent foIntent, String usage, String filePath, String imgName, );
        void OnFailed(String message);
    }
}
