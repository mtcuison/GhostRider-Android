/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.notifications
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.notifications.ViewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.util.Log;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.lifecycle.AndroidViewModel;

import com.google.firebase.installations.Utils;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Repositories.RNotificationInfo;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Objects;

import static org.rmj.g3appdriver.utils.WebApi.URL_DOWNLOAD_UPDATE;
import static org.rmj.g3appdriver.utils.WebApi.URL_SEND_RESPONSE;

public class VMViewNotification extends AndroidViewModel {
    private static final String TAG = VMViewNotification.class.getSimpleName();
    private final Application instance;

    public VMViewNotification(@NonNull Application application) {
        super(application);
        Log.e(TAG, "Initialized.");
        this.instance = application;
    }

    public void sendReply(String fsTransNo, String fsMessage) {
        Log.e(TAG, "Reply notification currently not available");
    }

    public void deleteNotification(String fsTransNo) {
        Log.e(TAG, "Delete notification currently not available.");
        return;
    }

    public void UpdateMessageStatus(String MessageID){
        new UpdateMessagesTask(instance).execute(MessageID);
    }

    public interface onDownLoadPDF{
        void OnDownloadPDF(String title, String message);
        void OnFinishDownload(Intent intent);
        void OnFailedDownload(String message);
    }

    private static class UpdateMessagesTask extends AsyncTask<String, Void, String> {
        private final Application instance;
        private final ConnectionUtil loConn;
        private final HttpHeaders poHeaders;
        private final RNotificationInfo poNotif;

        public UpdateMessagesTask(Application application) {
            this.instance = application;
            this.loConn = new ConnectionUtil(instance);
            this.poHeaders = HttpHeaders.getInstance(instance);
            this.poNotif = new RNotificationInfo(instance);
        }

        @SuppressLint("NewApi")
        @Override
        protected String doInBackground(String... strings) {
            String lsResult = "";
            try {
                String lsMsgID = strings[0];
                poNotif.updateRecipientReadStatus(lsMsgID);
                if (loConn.isDeviceConnected()) {
                    String lsMessageID = lsMsgID;
                    String lsDateReadx = poNotif.getReadMessageTimeStamp(lsMessageID);
                    JSONObject params = new JSONObject();
                    params.put("transno", lsMessageID);
                    params.put("status", "3");
                    params.put("stamp", lsDateReadx);
                    params.put("infox", "");

                    String response = WebClient.httpsPostJSon(URL_SEND_RESPONSE, params.toString(), poHeaders.getHeaders());
                    JSONObject loJson = new JSONObject(Objects.requireNonNull(response));
                    String result = loJson.getString("result");
                    if (result.equalsIgnoreCase("success")) {
                        Log.e(TAG, "message status updated to READ");
                    }
                } else {
                    lsResult = AppConstants.NO_INTERNET();
                }
            } catch (Exception e){
                e.printStackTrace();
                lsResult = AppConstants.LOCAL_EXCEPTION_ERROR(e.getMessage());
            }
            return lsResult;
        }
    }
    public void DownloadPDF(String url,onDownLoadPDF callback){
        new DownloadPDF(instance, callback).execute(url);
    }

    public static class DownloadPDF extends AsyncTask<String, Integer, String> {
        private final Application instance;
        private final onDownLoadPDF callback;
        private final ConnectionUtil poConn;
        private final HttpHeaders poHeaders;
        private final String PATH;
        private final WebApi poApi;
        private String filename = "";
        private String filePath = "";
        private File pdfFile;

        public DownloadPDF(Application application, onDownLoadPDF callback) {
            this.instance = application;
            this.callback = callback;
            this.poConn = new ConnectionUtil(application);
            this.poHeaders = HttpHeaders.getInstance(application);
            PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/";
            this.poApi = new WebApi(application);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnDownloadPDF("Notification", "Downloading payslip. Please wait...");
        }

        @Override
        protected String doInBackground(String... strings) {
            String lsResult;
            try {
                if (poConn.isWifiConnected()) {
                    if (poConn.isDeviceConnected()) {
                        URL url = new URL(strings[0]);
                        HttpURLConnection c = (HttpURLConnection) url.openConnection();
                        c.setRequestMethod("GET");
                        c.setDoOutput(true);
                        c.connect();

                        File file = new File(PATH);
                        if (!file.exists()) {
                            file.mkdirs();
                        }
                        String fieldValue = c.getHeaderField("Content-Disposition");
                        filename = fieldValue.substring(fieldValue.indexOf("filename=\"") + 10, fieldValue.length() - 1);

                        Log.e(TAG, filename);
                        File outputFile = new File(file, filename);

                        filePath = file.getAbsolutePath();
                        pdfFile = outputFile;
                        FileOutputStream fos = new FileOutputStream(outputFile);
                        BigDecimal fileLength = BigDecimal.valueOf(c.getContentLength());
                        InputStream is = c.getInputStream();

                        byte[] buffer = new byte[4096];
                        BigDecimal total = BigDecimal.valueOf(4096);
                        int len1;
                        while ((len1 = is.read(buffer)) != -1) {
                            total = total.add(BigDecimal.valueOf(len1));
                            if (fileLength.intValue() > 0) {
                                BigDecimal percentage = percentage(total, fileLength).multiply(ONE_HUNDRED);
                                publishProgress(percentage.intValue());
                            }
                            fos.write(buffer, 0, len1);
                        }
                        fos.close();
                        is.close();
                        lsResult = AppConstants.APPROVAL_CODE_GENERATED("Download Complete. Ready to install.");
                    } else {
                        lsResult = AppConstants.NO_INTERNET();
                    }
                } else {
                    lsResult = AppConstants.LOCAL_EXCEPTION_ERROR("You need wifi connection in order to download updates.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                lsResult = AppConstants.LOCAL_EXCEPTION_ERROR(e.getMessage());
            }
            return lsResult;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject loJson = new JSONObject(s);
                String lsResult = loJson.getString("result");
                if (lsResult.equalsIgnoreCase("success")) {
                    Uri loUriFile = FileProvider.getUriForFile(instance, "org.rmj.guanzongroup.ghostrider.epacss" + ".provider", pdfFile);

                    Intent sharingIntent = new Intent(Intent.ACTION_VIEW);
                    sharingIntent.setDataAndTypeAndNormalize(loUriFile, "application/pdf");
                    sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    callback.OnFinishDownload(sharingIntent);

                } else {
                    JSONObject loError = loJson.getJSONObject("error");
                    callback.OnFailedDownload(loError.getString("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

        public static BigDecimal percentage(BigDecimal base, BigDecimal pct) {
            return base.divide(pct, 2, RoundingMode.HALF_UP);

        }
    }

}