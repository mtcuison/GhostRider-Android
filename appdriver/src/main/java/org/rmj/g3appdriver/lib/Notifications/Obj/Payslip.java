package org.rmj.g3appdriver.lib.Notifications.Obj;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import androidx.core.content.FileProvider;
import androidx.lifecycle.LiveData;

import org.json.JSONObject;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.dev.Api.WebApi;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DPayslip;
import org.rmj.g3appdriver.dev.Database.Entities.ENotificationRecipient;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.AppConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Objects;

public class Payslip {
    private static final String TAG = Payslip.class.getSimpleName();

    private final Application instance;

    private final DPayslip poDao;
    private final AppConfigPreference poConfig;
    private final WebApi poApi;
    private final HttpHeaders poHeaders;

    private String message;

    public Payslip(Application instance) {
        this.instance = instance;
        this.poDao = GGC_GriderDB.getInstance(instance).payslipDao();
        this.poConfig = AppConfigPreference.getInstance(instance);
        this.poApi = new WebApi(poConfig.getTestStatus());
        this.poHeaders = HttpHeaders.getInstance(instance);
    }

    public String getMessage(){
        return message;
    }

    public LiveData<List<DPayslip.Payslip>> GetPaySliplist(){
        return poDao.GetPaySlipList();
    }

    public boolean NotificationRead(String MessageID){
        try{
            ENotificationRecipient loDetail = poDao.GetPaySlipNotification(MessageID);

            if(loDetail == null){
                message = "Unable to find notification for update.";
                return false;
            }

            if(loDetail.getMesgStat().equalsIgnoreCase("3")){
                message = "Message is already read.";
                return false;
            }

            loDetail.setMesgStat("3");
            loDetail.setReadxxxx(new AppConstants().DATE_MODIFIED);
            poDao.Update(loDetail);

            JSONObject params = new JSONObject();
            params.put("transno", loDetail.getTransNox());
            params.put("status", "3");
            params.put("stamp", loDetail.getReadxxxx());
            params.put("infox", "");

            String lsResponse = WebClient.sendRequest(
                    poApi.getUrlSendResponse(poConfig.isBackUpServer()),
                    params.toString(), poHeaders.getHeaders());
            JSONObject loResponse = new JSONObject(Objects.requireNonNull(lsResponse));
            String result = loResponse.getString("result");
            if (result.equalsIgnoreCase("error")) {
                JSONObject loError = loResponse.getJSONObject("error");
                Log.e(TAG, loError.getString("message"));
                return false;
            }

            loDetail.setStatSent("1");
            poDao.Update(loDetail);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
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
            message = e.getMessage();
            return null;
        }
    }

    private static BigDecimal percentage(BigDecimal base, BigDecimal pct) {
        return base.divide(pct, 2, RoundingMode.HALF_UP);
    }
}
