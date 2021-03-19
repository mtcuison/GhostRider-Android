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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DCreditApplication;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DCreditApplicationDocuments;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EAddressUpdate;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicationDocuments;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EFileCode;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EMobileUpdate;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCollectionUpdate;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplication;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplicationDocument;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Database.Repositories.RFileCode;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.GRider.ImportData.ImportDataCallback;
import org.rmj.g3appdriver.GRider.ImportData.Import_LoanApplications;
import org.rmj.g3appdriver.dev.Telephony;
import org.rmj.g3appdriver.etc.SessionManager;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.guanzongroup.ghostrider.griderscanner.helpers.ScannerConstants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

public class VMClientInfo extends AndroidViewModel {
    private static final String TAG = VMClientInfo.class.getSimpleName();
    private final RFileCode peFileCode;
    private final Application instance;
    private final LiveData<List<EFileCode>> fileCodeList;
    private final RCreditApplicationDocument poDocument;
    private final RImageInfo poImage;
    private List<EImageInfo> imgInfo = new ArrayList<>();
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
    public LiveData<List<EFileCode>> getFileCode(){
        return this.fileCodeList;
    }

    public LiveData<List<DCreditApplicationDocuments.ApplicationDocument>> getDocument(String TransNox){
        return this.poDocument.getDocument(TransNox);
    }

    public void saveDocumentInfo(List<DCreditApplicationDocuments.ApplicationDocument> poDocs,ECreditApplicationDocuments documentsInfo){
        try{
            for (int i = 0; i < poDocs.size(); i++){
                if (poDocs.get(i).sTransNox.equalsIgnoreCase(documentsInfo.getTransNox())){
                    documentsInfo.setFileLoc(poDocs.get(i).sFileLoc);
                    documentsInfo.setImageNme(poDocs.get(i).sImageNme);
                    documentsInfo.setDocTransNox(documentsInfo.getDocTransNox());
                    poDocument.updateDocumentInfo(documentsInfo);
                }else{
                    documentsInfo.setDocTransNox(poDocument.getImageNextCode());
                    poDocument.insertDocumentInfo(documentsInfo);
                    Log.e(TAG, "Image info has been save!");
                }
            }


        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void saveDocumentInfoFromCamera(ECreditApplicationDocuments documentsInfo){
        try{


//                documentsInfo.setDocTransNox(psT);
//                Log.e("Img TransNox", tansNo);
//                poDocument.updateDocumentInfo(documentsInfo);
//                Log.e(TAG, "Image info has been updated!");


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

    public boolean PostDocumentScanDetail( ECreditApplicationDocuments poDocumentsInfo,ViewModelCallBack callback){
        try {
            new PostDocumentScanDetail(instance,poDocumentsInfo, poDocumentsInfo.getTransNox(), poDocumentsInfo.getFileCode(), poDocumentsInfo.getEntryNox(), poDocumentsInfo.getImageNme(),poDocumentsInfo.getFileLoc(),callback).execute();
            return true;
        }catch (Exception e){
            return false;
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

        public PostDocumentScanDetail(Application instance,ECreditApplicationDocuments poDocumentsInfo, String TransNox, String FilCode, int EntryNox, String ImgName,
                                      String FileLoc, ViewModelCallBack callback){
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

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnStartSaving();
        }



        @SafeVarargs
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected final String doInBackground(Void ... voids) {
            String lsResult;
            try {
                if(!poConn.isDeviceConnected()){
                    lsResult = AppConstants.NO_INTERNET();
                } else {

                    String lsClient = WebFileServer.RequestClientToken("IntegSys", poUser.getClientId(), poUser.getUserID());
                    String lsAccess = WebFileServer.RequestAccessToken(lsClient);

                    if(lsClient.isEmpty() || lsAccess.isEmpty()){
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
                        Log.e(TAG, "Uploading image result : " + lsResponse);
//
                        if (Objects.requireNonNull(lsResponse).equalsIgnoreCase("success"))
                       {

//                            Log.e(TAG, "Image file of Account No. " + psTransNox + ", Entry No. "+ pnEntryNox+ " was uploaded successfully");
                            String lsTransNo = (String) loUpload.get("sTransNox");
                            poImage.updateImageInfo(lsTransNo, psTransNox);
                            //poDcp.updateCollectionDetailImage(loDetail.sAcctNmbr);


                        } else {

                            Log.e(TAG, "Image file of Account No. " + psTransNox + ", Entry No. "+ pnEntryNox+ " was not uploaded to server.");
                            JSONObject loError = new JSONObject(lsResponse);
                            lsResult =loError.getString("message");
                            Log.e(TAG, "Reason : " + loError.getString("message"));
                        }
                            Thread.sleep(1000);
                        }
                    }

            } catch (Exception e){
                e.printStackTrace();
                lsResult = AppConstants.LOCAL_EXCEPTION_ERROR(e.getMessage());
            }
            return lsResult;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try{

                JSONObject loJson = new JSONObject(s);
                if(loJson.getString("result").equalsIgnoreCase("success")){
                    callback.OnSuccessResult(new String[]{ScannerConstants.FileDesc +" has been posted successfully."});
                } else {
                    JSONObject loError = loJson.getJSONObject("error");
                    callback.OnFailedResult(loError.getString("message"));
                }
            } catch (JSONException e){
                e.printStackTrace();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            this.cancel(true);
        }


    }
}
