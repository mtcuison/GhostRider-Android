package org.rmj.guanzongroup.documentscanner;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class DocScanner {
    private static final String TAG = DocScanner.class.getSimpleName();

    private final AppCompatActivity instance;

    public interface OnScanDocumentListener{
        void OnScanned(Bitmap bitmap);
        void OnFailed(String message);
        void OnCancelled();
    }

    public DocScanner(AppCompatActivity activity) {
        this.instance = activity;
    }

    public void initScanner(OnScanDocumentListener listener){
        // display the first cropped image
        //                    croppedImageView.setImageBitmap(
        //                            BitmapFactory.decodeFile(croppedImageResults.get(0))
        //                    );
        // an error happened
        //                    Log.v("documentscannerlogs", errorMessage);
        // user canceled document scan
        //                    Log.v("documentscannerlogs", "User canceled document scan");

        DocumentScanner poScan = new DocumentScanner(
                instance,
                (croppedImageResults) -> {
                    //Decode the image result into bitmap to display the result on image view
                    listener.OnScanned(BitmapFactory.decodeFile(croppedImageResults.get(0)));
                    return null;
                },
                (errorMessage) -> {
                    // an error happened
                    Log.d(TAG, errorMessage);
                    listener.OnFailed(errorMessage);
                    return null;
                },
                () -> {
                    // user canceled document scan
                    Log.d(TAG, "User canceled document scan");
                    listener.OnCancelled();
                    return null;
                },
                null,
                null,
                null
        );

        poScan.startScan();
    }

    public static String saveBitmap2SD(Bitmap bitmap, String fsFileName) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { //Check sd whether usable.
            return null;
        }//from  w w w .j  a  v a2 s .co  m
//        String name = UserID + "-" + IDCode + "-" + FileCode + "_" + ImageType + ".jpg";
        String filePath1 = "/sdcard/DCIM/Camera/";
        File file = new File(filePath1);
        if (!file.exists()) {
            if (!file.mkdirs())
                return null;
        }
        String fileName1 = filePath1 + fsFileName;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(fileName1);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bos.flush();
            bos.close();
            fos.flush();
            //fos.write(data);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //        MediaStore.Images.Media.insertImage(context.getContentResolver(),bitmap,"","");
//        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri
//                .parse("file://"
//                        + Environment.getExternalStorageDirectory())));
        return fileName1;
    }
}
