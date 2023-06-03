package org.rmj.g3appdriver.lib.Notifications.Obj;

import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import androidx.core.content.FileProvider;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DPayslip;
import org.rmj.g3appdriver.GCircle.room.Entities.ENotificationMaster;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.g3appdriver.lib.Notifications.NOTIFICATION_STATUS;
import org.rmj.g3appdriver.lib.Notifications.Obj.Receiver.NMM_Regular;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class Payslip extends NMM_Regular {
    private static final String TAG = Payslip.class.getSimpleName();

    private final Application instance;

    private final DPayslip poDao;

    public Payslip(Application instance) {
        super(instance);
        this.instance = instance;
        this.poDao = GGC_GCircleDB.getInstance(instance).payslipDao();
    }

    public LiveData<List<DPayslip.Payslip>> GetPaySliplist(){
        return poDao.GetPaySlipList();
    }

    public LiveData<Integer> GetUnreadPayslipCount(){
        return poDao.GetUnreadPayslipCount();
    }

    @Override
    public ENotificationMaster SendResponse(String mesgID, NOTIFICATION_STATUS status) {
        return super.SendResponse(mesgID, status);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public Uri DownloadPaySlip(String args){
        try{
            URL url = new URL(args);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();

            String PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/Payslip/";
            File file = new File(PATH);
            if (!file.exists()) {
                file.mkdirs();
            }

            String fieldValue = c.getHeaderField("Content-Disposition");
            String filename = fieldValue.substring(fieldValue.indexOf("filename=\"") + 10, fieldValue.length() - 1);

            Log.e(TAG, filename);
            File pdfFile = new File(file, filename);

            if(pdfFile.exists()){
                return FileProvider.getUriForFile(instance, "org.rmj.guanzongroup.ghostrider.epacss" + ".provider", pdfFile);
            }

            FileOutputStream fos = new FileOutputStream(pdfFile);
            BigDecimal fileLength = BigDecimal.valueOf(c.getContentLength());
            InputStream is = c.getInputStream();

            BigDecimal ONE_HUNDRED = new BigDecimal(100);

            byte[] buffer = new byte[4096];
            BigDecimal total = BigDecimal.valueOf(4096);
            int len1;
            while ((len1 = is.read(buffer)) != -1) {
                total = total.add(BigDecimal.valueOf(len1));
                if (fileLength.intValue() > 0) {
                    BigDecimal percentage = percentage(total, fileLength).multiply(ONE_HUNDRED);
                    Log.d(TAG, "Downloading Payslip. " +percentage+ " Please wait...");
                }
                fos.write(buffer, 0, len1);
            }
            fos.close();
            is.close();

            return FileProvider.getUriForFile(instance, "org.rmj.guanzongroup.ghostrider.epacss" + ".provider", pdfFile);
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return null;
        }
    }

    private static BigDecimal percentage(BigDecimal base, BigDecimal pct) {
        return base.divide(pct, 2, RoundingMode.HALF_UP);
    }
}
