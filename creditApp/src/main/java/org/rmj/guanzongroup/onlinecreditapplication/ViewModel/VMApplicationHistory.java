package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.simple.JSONObject;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DCreditApplication;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplication;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;
import org.rmj.g3appdriver.GRider.ImportData.ImportDataCallback;
import org.rmj.g3appdriver.GRider.ImportData.Import_LoanApplications;
import org.rmj.g3appdriver.etc.SessionManager;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.guanzongroup.onlinecreditapplication.Activity.Activity_ApplicationHistory;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;

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
}
