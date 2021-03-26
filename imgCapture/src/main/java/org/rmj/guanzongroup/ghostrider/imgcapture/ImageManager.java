package org.rmj.guanzongroup.ghostrider.imgcapture;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;

import org.rmj.g3appdriver.GRider.Constants.FileConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Etc.GeoLocator;
import org.rmj.g3appdriver.dev.GLocationManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageManager {
    private static final String TAG = ImageManager.class.getSimpleName();

    private final Context mContext;

    private final EImageInfo poImage;

    private final GeoLocator poLocator;

    private String psImageNm;

    public interface OnImageFileCreatedListener{
        void OnCreated(Intent foIntent, EImageInfo poInfo);
        void OnFailed(String message);
    }

    public void setImageName(String psImageNm) {
        this.psImageNm = psImageNm;
    }

    public ImageManager(Context context) {
        this.mContext = context;
        this.poLocator = new GeoLocator(mContext, (Activity)mContext);
        this.poImage = new EImageInfo();
    }

//    public void createImageFile(String SubFolder, OnImageFileCreatedListener listener){
//        Intent loIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if(GLocationManager.isLocationEnabled(mContext)) {
//
//            if (loIntent.resolveActivity(mContext.getPackageManager()) != null) {
//                File poImgFile;
//                try {
//                    poImgFile = getFileStorage(SubFolder);
//
//                    if (poImgFile != null) {
//                        Uri poUri = FileProvider.getUriForFile(mContext,
//                                "org.rmj.guanzongroup.ghostrider.epacss" + ".provider",
//                                poImgFile);
//
//                        loIntent.putExtra(MediaStore.EXTRA_OUTPUT, poUri);
//
//                        poImage.setImageNme(psImageNm);
//                        poImage.setFileLoct(psFilePth);
//                        poImage.setLatitude(String.valueOf(poLocator.getLattitude()));
//                        poImage.setLongitud(String.valueOf(poLocator.getLongitude()));
//
//                        listener.OnCreated(loIntent, poImage);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    listener.OnFailed("Something went wrong. Please report to MIS-SEG" + e.getMessage());
//                }
//            }
//        } else {
//            listener.OnFailed("Please enable device location");
//        }
//    }

    private File createImageFile(String SubFolder) {
        String lsRoot = Environment.getExternalStorageDirectory().toString();
        File lsRootDir = new File(lsRoot + FileConstants.ROOT_DIRECTORY + SubFolder);
        if(!lsRootDir.exists()){
            lsRootDir.mkdirs();
        }
        return new File(lsRootDir, "");
    }

    @SuppressLint("SimpleDateFormat")
    public String generateTimestamp() {
        return new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    }
}
