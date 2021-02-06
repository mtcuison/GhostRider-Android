package org.rmj.g3appdriver.utils;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public class BarcodeUtil {
    /**
     *  Converts a string value into a QR Code bitmap value.
     * @param qrCodeData
     * @param charset
     * @param hintMap
     * @param qrCodeheight
     * @param qrCodewidth
     * @return Bitmap
     * @throws WriterException
     * @throws IOException
     */
    public static Bitmap createQRCode(
        String qrCodeData, String charset, Map hintMap, int qrCodeheight, int qrCodewidth){

        BitMatrix matrix = null;

        try {
            matrix = new MultiFormatWriter().encode(
                        new String(qrCodeData.getBytes(charset), charset),
                        BarcodeFormat.QR_CODE, qrCodewidth, qrCodeheight, hintMap);

            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();

            return barcodeEncoder.createBitmap(matrix);
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String readQRCode(Bitmap bMap, Map hintMap){
        if(bMap == null || hintMap == null) return null;

        int[] intArray = new int[bMap.getWidth() * bMap.getHeight()];

        bMap.getPixels(intArray, 0, bMap.getWidth(), 0, 0, bMap.getWidth(),
                bMap.getHeight());

        LuminanceSource source = new RGBLuminanceSource(bMap.getWidth(),
                bMap.getHeight(), intArray);

        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        Reader reader = new MultiFormatReader();// use this otherwise

        Result result = null;

        try {
            result = reader.decode(bitmap, hintMap);
        } catch (NotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (ChecksumException e) {
            e.printStackTrace();
            return null;
        } catch (FormatException e) {
            e.printStackTrace();
            return null;
        }

        return result.getText();
    }
}
