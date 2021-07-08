/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.creditApp
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.rmj.apprdiver.util.WebFile;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DCreditApplicationDocuments;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicationDocuments;
import org.rmj.g3appdriver.GRider.Database.Entities.EFileCode;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCollectionUpdate;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplicationDocument;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GRider.Database.Repositories.RFileCode;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.dev.Telephony;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.guanzongroup.ghostrider.griderscanner.helpers.ScannerConstants;
import org.rmj.guanzongroup.ghostrider.griderscanner.viewModel.VMClientInfo;
import org.rmj.guanzongroup.ghostrider.griderscanner.viewModel.ViewModelCallBack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VMDocumentToScan extends AndroidViewModel {
    private static final String TAG = VMClientInfo.class.getSimpleName();
    private final RFileCode peFileCode;
    private final Application instance;
    private final LiveData<List<EFileCode>> fileCodeList;
    private LiveData<List<DCreditApplicationDocuments.ApplicationDocument>> docFileList;
    private final RCreditApplicationDocument poDocument;
    private final RImageInfo poImage;
    private List<EImageInfo> imgInfo = new ArrayList<>();
    private List<EImageInfo> imgListInfo = new ArrayList<>();
    private final MutableLiveData<String> sTransNox = new MutableLiveData<>();
    private final MutableLiveData<String> sImgName = new MutableLiveData<>();
    private final MutableLiveData<String> sFileCode = new MutableLiveData<>();
    private final MutableLiveData<Integer> nEntryNox = new MutableLiveData<>();
    private final MutableLiveData<String> sFileLoc = new MutableLiveData<>();

    private final MutableLiveData<List<DCreditApplicationDocuments.ApplicationDocument>> plDetail = new MutableLiveData();
    public VMDocumentToScan(@NonNull Application application) {
        super(application);
        this.instance = application;
        peFileCode = new RFileCode(application);
        this.fileCodeList = peFileCode.getAllFileCode();
        poDocument = new RCreditApplicationDocument(application);
        poImage = new RImageInfo(application);
    }

    public void setImgParameter(String fsTransNox, int fnEntryNox, String fsFileCode, String fsImgname, String fsFileLoc) {
        try {
            this.sTransNox.setValue(fsTransNox);
            this.nEntryNox.setValue(fnEntryNox);
            this.sFileCode.setValue(fsFileCode);
            this.sImgName.setValue(fsImgname);
            this.sFileLoc.setValue(fsFileLoc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setsTransNox(String transNox) {
        this.docFileList = poDocument.getDocument(transNox);
        this.imgListInfo = poImage.getImageListInfo(transNox).getValue();
        this.sTransNox.setValue(transNox);
    }

    public LiveData<List<DCreditApplicationDocuments.ApplicationDocument>> getDocumentInfos(String TransNox) {
        return this.poDocument.getDocumentInfos(TransNox);
    }
    public LiveData<List<DCreditApplicationDocuments.ApplicationDocument>> getDocumentDetailForPosting() {
        return this.poDocument.getDocumentDetailForPosting();
    }

    public void setDocumentListForPosting(List<DCreditApplicationDocuments.ApplicationDocument> documentList){
        this.plDetail.setValue(documentList);
    }



    public void initAppDocs(String transNox) {
        try {
            poDocument.insertDocumentsInfo(transNox);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveDocumentInfoFromCamera(String TransNox, String FileCD) {
        try {
            poDocument.updateDocumentsInfo(TransNox, FileCD);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updateDocumentInfoFromServer(String TransNox, String FileCD){
        try{
            poDocument.updateDocumentsInfoByFile(TransNox, FileCD);

        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void saveImageInfo(EImageInfo foImage) {
        try {
            boolean isImgExist = false;
            String tansNo = "";
            for (int i = 0; i < imgInfo.size(); i++) {
                if (foImage.getSourceNo().equalsIgnoreCase(imgInfo.get(i).getSourceNo())
                        && foImage.getDtlSrcNo().equalsIgnoreCase(imgInfo.get(i).getDtlSrcNo())) {
                    tansNo = imgInfo.get(i).getTransNox();
                    isImgExist = true;
                }
            }
            if (isImgExist) {
                foImage.setTransNox(tansNo);
                poImage.updateImageInfo(foImage);
            } else {
                foImage.setTransNox(poImage.getImageNextCode());
                poImage.insertImageInfo(foImage);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void PostDocumentScanDetail(EImageInfo psImageInfo,ECreditApplicationDocuments poDocumentsInfo, ViewModelCallBack callback) {
        try {
            new PostDocumentScanDetail(instance,imgInfo,psImageInfo, poDocumentsInfo, poDocumentsInfo.getTransNox(), poDocumentsInfo.getFileCode(), poDocumentsInfo.getEntryNox(), poDocumentsInfo.getImageNme(), poDocumentsInfo.getFileLoc(), callback).execute();
        } catch (Exception e) {
        }
    }

    public class PostDocumentScanDetail extends AsyncTask<Void, Void, String> {
        private final ConnectionUtil poConn;
        private final ViewModelCallBack callback;
        private final SessionManager poUser;
        private final RDailyCollectionPlan poDcp;
        private final RImageInfo poImage;
        private final Telephony poTelephony;
        private final HttpHeaders poHeaders;
        private final RCollectionUpdate rCollect;
        private final String psTransNox;
        private final String psFileCode;
        private final int pnEntryNox;
        private final String psImageName;
        private final String psFileLoc;
        private final ECreditApplicationDocuments poDocumentsInfos;

        private final List<EImageInfo> imageInfo;
        private final EImageInfo psImgInfo;
        public PostDocumentScanDetail(Application instance,List<EImageInfo> imgListInfos, EImageInfo psImgInfo, ECreditApplicationDocuments poDocumentsInfo, String TransNox, String FilCode, int EntryNox, String ImgName,
                                      String FileLoc, ViewModelCallBack callback) {
            this.poConn = new ConnectionUtil(instance);
            this.poUser = new SessionManager(instance);
            this.pnEntryNox = EntryNox;
            this.psImageName = ImgName;
            this.psTransNox = TransNox;
            this.psFileCode = FilCode;
            this.psFileLoc = FileLoc;
            this.poDocumentsInfos = poDocumentsInfo;
            this.imageInfo = imgListInfos;
            this.psImgInfo = psImgInfo;
            this.callback = callback;
            this.poDcp = new RDailyCollectionPlan(instance);
            this.poImage = new RImageInfo(instance);
            this.poTelephony = new Telephony(instance);
            this.poHeaders = HttpHeaders.getInstance(instance);
            this.rCollect = new RCollectionUpdate(instance);
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
                        org.json.simple.JSONObject loUpload = WebFileServer.UploadFile(psFileLoc,
                                lsAccess,
                                psFileCode,
                                psTransNox,
                                psImageName,
                                poUser.getBranchCode(),
                                "COAD",
                                psTransNox,
                                "");


                        String lsResponse = (String) loUpload.get("result");
                        lsResult = String.valueOf(loUpload);
                        if (Objects.requireNonNull(lsResponse).equalsIgnoreCase("success")) {
                            String lsTransNo = (String) loUpload.get("sTransNox");
                            poImage.updateImageInfo(lsTransNo, psImgInfo.getTransNox());
                            updateDocumentInfoFromServer(psTransNox,psFileCode);
    //                                      UpdateFileNameAndFolder(lsTransNo, psTransNox, pnEntryNox, psFileCode,psFileLoc, psImageName);

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
                JSONObject loJson = new JSONObject(s);
                if (loJson.getString("result").equalsIgnoreCase("success")) {
                    callback.OnSuccessResult(new String[]{ScannerConstants.FileDesc + " has been posted successfully."});
                } else {
                    JSONObject loError = loJson.getJSONObject("error");
                    callback.OnFailedResult(loError.getString("message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
//            this.cancel(true);

        }


    }
    //mac 2021.03.30
    //  download image from server
    public void DownloadDocumentFile(DCreditApplicationDocuments.ApplicationDocument poDocumentsInfo, String fsSourceNo, ViewModelCallBack callback) {
        try {
            new DownloadDocumentFile(instance, poDocumentsInfo, fsSourceNo, callback).execute();
        } catch (Exception e) {
        }
    }

    public class DownloadDocumentFile extends AsyncTask<Void, Void, String> {
        private final ConnectionUtil poConn;
        private final ViewModelCallBack callback;
        private final SessionManager poUser;
        private final RImageInfo poImage;
        private final DCreditApplicationDocuments.ApplicationDocument poFileInfo;
        private final String psSourceNo;

        public DownloadDocumentFile(Application instance, DCreditApplicationDocuments.ApplicationDocument foFileInfo, String fsSourceNo, ViewModelCallBack callback) {
            this.poConn = new ConnectionUtil(instance);
            this.poUser = new SessionManager(instance);
            this.poFileInfo = foFileInfo;
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
                        String imageName = poFileInfo.sTransNox + "_" + poFileInfo.nEntryNox + "_" + poFileInfo.sFileCode + ".png";
                        String root = Environment.getExternalStorageDirectory().toString();
                        File fileLoc = new File(root + "/"+ AppConstants.APP_PUBLIC_FOLDER + "/" + ScannerConstants.SubFolder  +"/" + poFileInfo.sTransNox + "/");
                        if (!fileLoc.exists()) {
                            fileLoc.mkdirs();
                        }
                        org.json.simple.JSONObject loDownload = WebFileServer.DownloadFile(lsAccess,
                                poFileInfo.sFileCode,
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
                                loImage.setSourceNo(poFileInfo.sTransNox);
                                loImage.setDtlSrcNo(poFileInfo.sTransNox);
                                loImage.setFileCode(poFileInfo.sFileCode);
                                loImage.setMD5Hashx((String) loDownload.get("hash"));
                                loImage.setFileLoct(fileLoc.getAbsolutePath() +"/" + imageName);
                                loImage.setImageNme((String) loDownload.get("filename"));
                                loImage.setLatitude("0.0");
                                loImage.setLongitud("0.0");
                                loImage.setSendDate(new AppConstants().DATE_MODIFIED);
                                loImage.setSendStat("1");
                                //loImage....
                                ScannerConstants.PhotoPath = loImage.getFileLoct();
                                poImage.insertDownloadedImageInfo(loImage);
                                //end - insert entry to image info
                                saveDocumentInfoFromCamera(poFileInfo.sTransNox, poFileInfo.sFileCode);
                                //todo:
                                //insert/update entry to credit_online_application_documents
                                //end - convert to image and save to proper file location
                                loDownload = (org.json.simple.JSONObject) loParser.parse("{\"result\":\"success\",\"convert\":\"true\",\"message\":\""+ ScannerConstants.FileDesc + " has been downloaded successfully." + "\"}");
                                lsResult = String.valueOf(loDownload);
                            } else{
                                Log.e(TAG, "Unable to convert file.");
                                loDownload = (org.json.simple.JSONObject) loParser.parse("{\"result\":\"success\",\"convert\":\"false\",\"message\":\"Unable to convert file.\"}");
                                lsResult = String.valueOf(loDownload);
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
                    if (loJson.getString("convert").equalsIgnoreCase(String.valueOf(true))){
                        callback.OnSuccessResult(new String[]{loJson.getString("message")});
                    }else{
                        callback.onFailedResult(loJson.getString("message"));
                    }
                } else {
                    org.json.JSONObject loError = loJson.getJSONObject("error");
                    callback.OnFailedResult(loError.getString("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void CheckFile(String fsSourceNo, ViewModelCallBack callback) {
        try {
            new CheckFile(instance, fsSourceNo, callback).execute();
        } catch (Exception e) {
        }
    }

    public class CheckFile extends AsyncTask<Void, Void, String> {
        private final ConnectionUtil poConn;
        private final ViewModelCallBack callback;
        private final SessionManager poUser;
        private final RImageInfo poImage;
        private final String psSourceNo;
        public CheckFile(Application instance, String fsSourceNo, ViewModelCallBack callback) {
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
                        org.json.simple.JSONObject loDownload = WebFileServer.CheckFile(lsAccess,
                                                                                        "",
                                                                                        "",
                                                                                        "COAD",
                                                                                        psSourceNo);
                        String lsResponse = (String) loDownload.get("result");
                        lsResult = String.valueOf(loDownload);
                        if (Objects.requireNonNull(lsResponse).equalsIgnoreCase("success")) {
                            //convert to image and save to proper file location
                            org.json.simple.JSONArray laJson = (org.json.simple.JSONArray)loDownload.get("detail");
                            for (int x = 0; x <  laJson.size(); x++){
                                org.json.simple.JSONObject obj = (org.json.simple.JSONObject) laJson.get(x);;
                                updateDocumentInfoFromServer(psSourceNo,obj.get("sFileCode").toString());

                            }
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
                    callback.OnSuccessResult(new String[]{"File has been downloaded successfully."});
                } else {
                    org.json.JSONObject loError = loJson.getJSONObject("error");
                    callback.OnFailedResult(loError.getString("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
//    public static void UpdateFileNameAndFolder(String newTransNox, String oldTransNox, int entryNox, String fileCode, String fileLoc, String oldImgName){
//        String root = Environment.getExternalStorageDirectory().toString();
//        File dir = new File(root + "/"+ AppConstants.APP_PUBLIC_FOLDER + "/" + oldTransNox + "/");
//        File newDir = new File(root + "/"+ AppConstants.APP_PUBLIC_FOLDER + "/" + newTransNox + "/");
//
//        if(dir.exists()){
//            dir.renameTo(newDir);
//            File from = new File(dir,oldImgName);
//            String newImgName = newTransNox + "_" + entryNox + "_" +fileCode + ".png";
//            File to = new File(dir,newImgName);
//            if(from.exists())
//                from.renameTo(to);
//        }
//    }
}