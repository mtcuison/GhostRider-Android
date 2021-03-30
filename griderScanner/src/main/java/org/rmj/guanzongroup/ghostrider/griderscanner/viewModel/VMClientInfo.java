package org.rmj.guanzongroup.ghostrider.griderscanner.viewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.simple.JSONObject;
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
import org.rmj.g3appdriver.etc.SessionManager;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.guanzongroup.ghostrider.griderscanner.helpers.ScannerConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VMClientInfo extends AndroidViewModel {
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
    public VMClientInfo(@NonNull Application application) {
        super(application);
        this.instance = application;
        peFileCode = new RFileCode(application);
        this.fileCodeList = peFileCode.getAllFileCode();
        poDocument = new RCreditApplicationDocument(application);
        poImage = new RImageInfo(application);
    }
    public void setImgParameter(String fsTransNox, int fnEntryNox, String fsFileCode, String fsImgname, String fsFileLoc){
        try{
            this.sTransNox.setValue(fsTransNox);
            this.nEntryNox.setValue(fnEntryNox);
            this.sFileCode.setValue(fsFileCode);
            this.sImgName.setValue(fsImgname);
            this.sFileLoc.setValue(fsFileLoc);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public  void  setsTransNox(String transNox){
        this.docFileList = poDocument.getDocument(transNox);
        this.imgListInfo =poImage.getImageListInfo(transNox).getValue();
        this.sTransNox.setValue(transNox);
    }
    public LiveData<List<DCreditApplicationDocuments.ApplicationDocument>> getDocumentInfos(String TransNox){
        return this.poDocument.getDocumentInfos(TransNox);
    }

    public void initAppDocs(String transNox){
        try{
            poDocument.insertDocumentsInfo(transNox);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
        public void saveDocumentInfoFromCamera(String TransNox, String FileCD){
            try{
                poDocument.updateDocumentsInfo(TransNox, FileCD);

            } catch (Exception e){
                e.printStackTrace();
            }
    }

    public void saveImageInfo(EImageInfo foImage){
        try{
            boolean isImgExist = false;
            String tansNo = "";
            for (int i = 0; i < imgInfo.size(); i++){
                if(foImage.getSourceNo().equalsIgnoreCase(imgInfo.get(i).getSourceNo())
                        && foImage.getDtlSrcNo().equalsIgnoreCase(imgInfo.get(i).getDtlSrcNo())) {
                    tansNo = imgInfo.get(i).getTransNox();
//                    File finalFile = new File(getRealPathFromURI(imgInfo.get(i).getFileLoct()));
//                    finalFile.delete();
                    isImgExist = true;
                }
            }
            if (isImgExist){
                foImage.setTransNox(tansNo);
                Log.e("Img TransNox", tansNo);
                poImage.updateImageInfo(foImage);
                Log.e("VMClient ", "Image info has been updated!");
            }else{
                foImage.setTransNox(poImage.getImageNextCode());
                poImage.insertImageInfo(foImage);
                Log.e("VMClient ", "Image info has been save!");

            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void PostDocumentScanDetail( ECreditApplicationDocuments poDocumentsInfo,ViewModelCallBack callback) {
        try {
            new PostDocumentScanDetail(instance, poDocumentsInfo, poDocumentsInfo.getTransNox(), poDocumentsInfo.getFileCode(), poDocumentsInfo.getEntryNox(), poDocumentsInfo.getImageNme(), poDocumentsInfo.getFileLoc(), callback).execute();
        } catch (Exception e) {
        }
    }

    public static class PostDocumentScanDetail extends AsyncTask<Void, Void, String> {
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

        public PostDocumentScanDetail(Application instance, ECreditApplicationDocuments poDocumentsInfo, String TransNox, String FilCode, int EntryNox, String ImgName,
                                      String FileLoc, ViewModelCallBack callback) {
            this.poConn = new ConnectionUtil(instance);
            this.poUser = new SessionManager(instance);
            this.pnEntryNox = EntryNox;
            this.psImageName = ImgName;
            this.psTransNox = TransNox;
            this.psFileCode = FilCode;
            this.psFileLoc = FileLoc;
            this.poDocumentsInfos = poDocumentsInfo;
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
                        JSONObject loUpload = WebFileServer.UploadFile(psFileLoc,
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
                        Log.e(TAG, "Uploading image result : " + lsResponse);

                        if (Objects.requireNonNull(lsResponse).equalsIgnoreCase("success")) {
                            String lsTransNo = (String) loUpload.get("sTransNox");
                            poImage.updateImageInfo(lsTransNo, psTransNox);
                        } else {
                            Log.e(TAG, "Image file of Account No. " + psTransNox + ", Entry No. " + pnEntryNox + " was not uploaded to server.");
                            Log.e(TAG, "Reason : " + lsResponse);

                            JSONParser loParser = new JSONParser();
                            JSONObject loError = (JSONObject) loParser.parse((String) loUpload.get("error"));
                            lsResult = (String) loError.get("message");
                            Log.e(TAG, "Reason : " + lsResult);
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
                JSONParser loParser = new JSONParser();
                JSONObject loJson = (JSONObject) loParser.parse(s);
                if ("success".equalsIgnoreCase((String) loJson.get("result"))) {
                    callback.OnSuccessResult(new String[]{ScannerConstants.FileDesc + " has been posted successfully."});
                } else {
                    JSONObject loError = (JSONObject) loParser.parse((String) loJson.get("error"));
                    callback.OnFailedResult((String) loError.get("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //mac 2021.03.30
    //  download image from server
    public void DownloadDocumentFile(ECreditApplicationDocuments poDocumentsInfo, String fsSourceNo, ViewModelCallBack callback) {
        try {
            new DownloadDocumentFile(instance, poDocumentsInfo, fsSourceNo, callback).execute();
        } catch (Exception e) {
        }
    }

    public static class DownloadDocumentFile extends AsyncTask<Void, Void, String> {
        private final ConnectionUtil poConn;
        private final ViewModelCallBack callback;
        private final SessionManager poUser;
        private final RImageInfo poImage;
        private final ECreditApplicationDocuments poFileInfo;
        private final String psSourceNo;

        public DownloadDocumentFile(Application instance, ECreditApplicationDocuments foFileInfo, String fsSourceNo, ViewModelCallBack callback) {
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
                        JSONObject loDownload = WebFileServer.DownloadFile(lsAccess,
                                                                            poFileInfo.getFileCode(),
                                                                            "",
                                                                            poFileInfo.getImageNme(),
                                                                            "COAD",
                                                                            psSourceNo,
                                                                            "");

                        String lsResponse = (String) loDownload.get("result");
                        lsResult = String.valueOf(loDownload);
                        Log.e(TAG, "Downloading image result : " + lsResponse);

                        if (Objects.requireNonNull(lsResponse).equalsIgnoreCase("success")) {
                            //convert to image and save to proper file location
                            if (WebFile.Base64ToFile((String) loDownload.get("data"),
                                                        (String) loDownload.get("hash"),
                                                        poFileInfo.getFileLoc(),
                                                        (String) loDownload.get("filename")))
                                Log.d(TAG, "File hash was converted to file successfully.");
                            else
                                Log.e(TAG, "Unable to convert file.");
                            //end - convert to image and save to proper file location

                            //insert entry to image info
                            EImageInfo loImage = new EImageInfo();
                            loImage.setTransNox((String) loDownload.get("transnox"));
                            //loImage....
                            //end - insert entry to image info

                            //todo:
                            //insert/update entry to credit_online_application_documents
                        } else {
                            Log.e(TAG, "Unable to download image from server. Transaction No.: " + poFileInfo.getTransNox() + ", Filename: " + poFileInfo.getImageNme() + ".");
                            Log.e(TAG, "Reason : " + lsResponse);

                            JSONParser loParser = new JSONParser();
                            JSONObject loError = (JSONObject) loParser.parse((String) loDownload.get("error"));
                            lsResult = (String) loError.get("message");
                            Log.e(TAG, "Reason : " + lsResult);
                        }
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
                JSONParser loParser = new JSONParser();
                JSONObject loJson = (JSONObject) loParser.parse(s);
                if ("success".equalsIgnoreCase((String) loJson.get("result"))) {
                    callback.OnSuccessResult(new String[]{ScannerConstants.FileDesc + " has been downloaded successfully."});
                } else {
                    JSONObject loError = (JSONObject) loParser.parse((String) loJson.get("error"));
                    callback.OnFailedResult((String) loError.get("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //end - mac 2021.03.30
}
