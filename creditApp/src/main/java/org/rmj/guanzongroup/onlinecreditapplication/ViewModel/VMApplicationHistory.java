package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.rmj.apprdiver.util.WebFile;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DCreditApplication;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DCreditApplicationDocuments;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplication;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;
import org.rmj.g3appdriver.GRider.ImportData.ImportDataCallback;
import org.rmj.g3appdriver.GRider.ImportData.Import_LoanApplications;
import org.rmj.g3appdriver.etc.SessionManager;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.guanzongroup.ghostrider.griderscanner.helpers.ScannerConstants;
import org.rmj.guanzongroup.onlinecreditapplication.Activity.Activity_ApplicationHistory;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.Model.DownloadImageCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class VMApplicationHistory extends AndroidViewModel {
    private static final String TAG = VMApplicationHistory.class.getSimpleName();
    private final Application instance;
    private final RCreditApplication poCreditApp;
    private final Import_LoanApplications poImport;
    private final RImageInfo poImage;
    public VMApplicationHistory(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poCreditApp = new RCreditApplication(application);
        this.poImport = new Import_LoanApplications(application);
        this.poImage = new RImageInfo(application);
    }

    public void LoadApplications(ViewModelCallBack callBack){
        poImport.ImportData(new ImportDataCallback() {
            @Override
            public void OnSuccessImportData() {
                callBack.onSaveSuccessResult("");
            }

            @Override
            public void OnFailedImportData(String message) {
                callBack.onFailedResult(message);
            }
        });
    }

    public LiveData<List<DCreditApplication.ApplicationLog>> getApplicationHistory(){
        return poCreditApp.getApplicationHistory();
    }
    public LiveData<EImageInfo> getImageLogPreview(String TransNox){
        return poImage.getImageLogPreview(TransNox);
    }

    public void ExportGOCasInfo(String TransNox){

    }

    public void DeleteGOCasInfo(String TransNox){

    }

    public void UpdateGOCasInfo(String TransNox){

    }

    public void saveImageFile(EImageInfo foImage) {
        foImage.setTransNox(poImage.getImageNextCode());
        poImage.insertImageInfo(foImage);
    }

    public void uploadImage(EImageInfo foImage){
        new UploadImageFileTask(instance, foImage).execute(foImage.getSourceNo());
    }

    public String getApplicantPhoto() {
        // TODO: Create AsyncTask to return image path
        return null;
    }

    @SuppressLint("StaticFieldLeak")
    private class UploadImageFileTask extends AsyncTask<String, Void, String>{
        private final Application instance;
        private String TransNox;
        private ConnectionUtil poConn;
        private SessionManager poUser;
        private final EImageInfo poPhoto;

        private UploadImageFileTask(Application instance, EImageInfo foImage){
            this.instance = instance;
            this.poPhoto = foImage;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            poConn = new ConnectionUtil(instance);
            poUser = new SessionManager(instance);
        }

        @Override
        protected String doInBackground(String... strings) {
            TransNox = strings[0];
            String lsResponse = "";
            if(poConn.isDeviceConnected()){
                String lsClient = WebFileServer.RequestClientToken("IntegSys",
                        poUser.getClientId(),
                        poUser.getUserID());
                String lsAccess = WebFileServer.RequestAccessToken(lsClient);

                if(!lsAccess.isEmpty()){

                    org.json.simple.JSONObject loUpload = WebFileServer.UploadFile(
                            poPhoto.getFileLoct(),
                            lsAccess,
                            "0029",
                            poUser.getUserID(),
                            poPhoto.getImageNme(),
                            poUser.getBranchCode(),
                            poPhoto.getSourceCD(),
                            TransNox,
                            "");

                    lsResponse = (String) loUpload.get("result");
                    Log.e(TAG, "Uploading image result : " + lsResponse);

                    if (Objects.requireNonNull(lsResponse).equalsIgnoreCase("success")) {
                        String lsTransNo = (String) loUpload.get("sTransNox");
                        poImage.updateImageInfo(lsTransNo, poPhoto.getTransNox());
                        poCreditApp.updateCustomerImageStat(TransNox);
                        lsResponse = loUpload.toJSONString();
                    } else {
                        lsResponse = loUpload.toJSONString();
                    }
                }
            }
            return lsResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                org.json.JSONObject loJson = new org.json.JSONObject(s);
                String lsResult = loJson.getString("result");
                if(lsResult.equalsIgnoreCase("success")){
                } else {
                    org.json.JSONObject loError = loJson.getJSONObject("error");
                    String lsMessage = loError.getString("message");
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void DownloadDocumentFile(String fsSourceNo, DownloadImageCallBack callback) {
        try {
            new DownloadDocumentFile(instance, fsSourceNo, callback).execute();
        } catch (Exception e) {
        }
    }

    public class DownloadDocumentFile extends AsyncTask<Void, Void, String> {
        private final ConnectionUtil poConn;
        private final DownloadImageCallBack callback;
        private final SessionManager poUser;
        private final RImageInfo poImage;
        private final String psSourceNo;

        public DownloadDocumentFile(Application instance, String fsSourceNo, DownloadImageCallBack callback) {
            this.poConn = new ConnectionUtil(instance);
            this.poUser = new SessionManager(instance);
            this.psSourceNo = fsSourceNo;
            this.callback = callback;
            this.poImage = new RImageInfo(instance);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(Void... voids) {
            String lsResult;
            try {
                if (!poConn.isDeviceConnected()) {
                    lsResult = AppConstants.NO_INTERNET();
                } else {

                    String lsClient = WebFileServer.RequestClientToken("IntegSys", poUser.getClientId(), poUser.getUserID());
                    String lsAccess = WebFileServer.RequestAccessToken(lsClient);

                    if (lsClient.isEmpty() || lsAccess.isEmpty()) {
                        lsResult = AppConstants.LOCAL_EXCEPTION_ERROR("Failed to request generated Client or Access token.");
                    } else {
                        String imageName = psSourceNo + "_0_" + "0029.png";
                        String root = Environment.getExternalStorageDirectory().toString();
                        File fileLoc = new File(root + "/"+ AppConstants.APP_PUBLIC_FOLDER + "/" + ScannerConstants.SubFolder  +"/" + psSourceNo + "/");
                        if (!fileLoc.exists()) {
                            fileLoc.mkdirs();
                        }
                        org.json.simple.JSONObject loDownload = WebFileServer.DownloadFile(lsAccess,
                                "0029",
                                "",
                                imageName,
                                "COAD",
                                psSourceNo,
                                "");

                        String lsResponse = (String) loDownload.get("result");
//                        lsResult = String.valueOf(loDownload);
                        Log.e(TAG, "Downloading image result : " + lsResponse);

                        if (Objects.requireNonNull(lsResponse).equalsIgnoreCase("success")) {
                            //convert to image and save to proper file location
                            JSONParser loParser = new JSONParser();
                            loDownload = (org.json.simple.JSONObject) loParser.parse(loDownload.get("payload").toString());
                            String location = fileLoc.getAbsolutePath() + "/";
                            if (WebFile.Base64ToFile((String) loDownload.get("data"),
                                    (String) loDownload.get("hash"),
                                    location,
                                    (String) loDownload.get("filename"))){
                                Log.d(TAG, "File hash was converted to file successfully.");
                                //insert entry to image info
                                EImageInfo loImage = new EImageInfo();
                                loImage.setTransNox((String) loDownload.get("transnox"));
                                loImage.setSourceCD("COAD");
                                loImage.setSourceNo(psSourceNo);
                                loImage.setDtlSrcNo(psSourceNo);
                                loImage.setFileCode("0029");
                                loImage.setMD5Hashx((String) loDownload.get("hash"));
                                loImage.setFileLoct(fileLoc.getAbsolutePath() +"/" + imageName);
                                loImage.setImageNme((String) loDownload.get("filename"));
                                loImage.setLatitude("0.0");
                                loImage.setLongitud("0.0");
                                loImage.setSendDate(AppConstants.DATE_MODIFIED);
                                loImage.setSendStat("1");
                                //loImage....
                                ScannerConstants.PhotoPath = loImage.getFileLoct();
                                poImage.insertDownloadedImageInfo(loImage);
                                //end - insert entry to image info
                                Log.e(TAG,loDownload.get("transnox").toString());
                                //todo:
                                //insert/update entry to credit_online_application_documents
                                //end - convert to image and save to proper file location
                                loDownload = (org.json.simple.JSONObject) loParser.parse("{\"result\":\"success\",\"message\":\""+ ScannerConstants.FileDesc + " has been downloaded successfully." + "\"}");
                                lsResult = String.valueOf(loDownload);
                                // callback.OnSuccessResult(new String[]{ScannerConstants.FileDesc + " has been downloaded successfully."});
                            } else{
                                Log.e(TAG, "Unable to convert file.");
                                //Log.e(TAG, (String) loDownload.get("hash"));
                                //JSONObject jsonobj=new JSONObject("{result:success,message:Unable to convert file.}");

                                loDownload = (org.json.simple.JSONObject) loParser.parse("{\"result\":\"success\",\"message\":\"Unable to convert file.\"}");
                                lsResult = String.valueOf(loDownload);
                                // callback.OnFailedResult("Unable to convert file.");

                            }
                        }else{
                            lsResult = String.valueOf(loDownload);
                        }

                        Thread.sleep(1000);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                lsResult = AppConstants.LOCAL_EXCEPTION_ERROR(e.getMessage());
            }
            return lsResult;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnStartSaving();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                org.json.JSONObject loJson = new org.json.JSONObject(s);
                if (loJson.getString("result").equalsIgnoreCase("success")) {
                    callback.onSaveSuccessResult(loJson.getString("message"));
                } else {
                    org.json.JSONObject loError = loJson.getJSONObject("error");
                    callback.onFailedResult(loError.getString("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
