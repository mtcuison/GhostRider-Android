package org.rmj.guanzongroup.ghostrider.griderscanner.viewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DCreditApplication;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DCreditApplicationDocuments;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchLoanApplication;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicationDocuments;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EFileCode;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranchLoanApplication;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCollectionUpdate;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplication;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplicationDocument;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Database.Repositories.RFileCode;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.GRider.ImportData.ImportDataCallback;
import org.rmj.g3appdriver.GRider.ImportData.Import_CreditAppList;
import org.rmj.g3appdriver.GRider.ImportData.Import_LoanApplications;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.rmj.g3appdriver.utils.WebApi.URL_BRANCH_LOAN_APP;

public class VMMainScanner extends AndroidViewModel {
    private final Application instance;
    private final RFileCode peFileCode;
    private final LiveData<List<EFileCode>> collectionList;
    private final RBranchLoanApplication poCreditApp;
    private final Import_CreditAppList poImport;
    private final REmployee poEmploye;
    private final RCreditApplicationDocument poDocument;
    private List<DCreditApplicationDocuments.ApplicationDocument> documentInfo = new ArrayList<>();
    public VMMainScanner(@NonNull Application application) {
        super(application);
        this.instance = application;
        peFileCode = new RFileCode(application);
        this.collectionList = peFileCode.getAllFileCode();
        this.poCreditApp = new RBranchLoanApplication(application);
        this.poImport = new Import_CreditAppList(application);
        poEmploye = new REmployee(application);
        poDocument = new RCreditApplicationDocument(application);
    }
    public LiveData<List<EFileCode>> getFileCode(){
        return this.collectionList;
    }
    public interface OnImportCallBack{
        void onStartImport();
        void onSuccessImport();
        void onImportFailed(String message);
    }
//    public void LoadApplications(ViewModelCallBack callBack){
//        poImport.ImportData(new ImportDataCallback() {
//            @Override
//            public void OnSuccessImportData() {
//                callBack.onSaveSuccessResult("");
//            }
//
//            @Override
//            public void OnFailedImportData(String message) {
//                callBack.onFailedResult(message);
//            }
//        });
//    }
    public LiveData<EEmployeeInfo> getEmployeeInfo(){
        return poEmploye.getEmployeeInfo();
    }

    public LiveData<List<EBranchLoanApplication>> getBranchCreditApplication(String BrnCD){
        return poCreditApp.getBranchCreditApplication(BrnCD);
    }
    public void ImportRBranchApplications(OnImportCallBack callBack, String brnCode){

        JSONObject loJson = new JSONObject();
        try {
            loJson.put("bycode", true);
            loJson.put("value", brnCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new ImportBranchApplications(instance, callBack).execute(loJson);
    }


    private static class ImportBranchApplications extends AsyncTask<JSONObject, Void, String> {
        private final HttpHeaders headers;
        private final RBranchLoanApplication brnRepo;
        private final ConnectionUtil conn;
        private final WebApi webApi;
        private final OnImportCallBack callback;

        public ImportBranchApplications(Application instance,  OnImportCallBack callback) {
            this.headers = HttpHeaders.getInstance(instance);
            this.brnRepo = new RBranchLoanApplication(instance);
            this.conn = new ConnectionUtil(instance);
            this.webApi = new WebApi(instance);
            this.callback = callback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.onStartImport();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(JSONObject... strings) {
            String response = "";
            try{
                if(conn.isDeviceConnected()) {
                    response = WebClient.httpsPostJSon(URL_BRANCH_LOAN_APP, strings[0].toString(), headers.getHeaders());
                    JSONObject jsonResponse = new JSONObject(response);
                    String lsResult = jsonResponse.getString("result");
                    if (lsResult.equalsIgnoreCase("success")) {
                        JSONArray laJson = jsonResponse.getJSONArray("detail");
                        if(!brnRepo.insertBranchApplicationInfos(laJson)){
                            response = AppConstants.ERROR_SAVING_TO_LOCAL();
                        }
                    }
                } else {
                    response = AppConstants.SERVER_NO_RESPONSE();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject loJson = new JSONObject(s);
                Log.e("VMMAinScanner", loJson.getString("result"));
                String lsResult = loJson.getString("result");
                if(lsResult.equalsIgnoreCase("success")){
                    callback.onSuccessImport();
                } else {
                    JSONObject loError = loJson.getJSONObject("error");
                    String message = loError.getString("message");
                    callback.onImportFailed(message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                callback.onImportFailed(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                callback.onImportFailed(e.getMessage());
            }
        }
//        boolean saveDetailDataToLocal(@NonNull JSONArray faJson) throws JSONException {
//            List<EBranchLoanApplication> eBranchLoanApplications = new ArrayList<>();
//            for(int x = 0; x < faJson.length(); x++){
//                JSONObject loJson = faJson.getJSONObject(x);
//                EBranchLoanApplication branchLoanApplication = new EBranchLoanApplication();
//                branchLoanApplication.setTransNox(loJson.getString("sTransNox"));
//                branchLoanApplication.setTransact(loJson.getString("dTransact"));
//                branchLoanApplication.setBranchCD(loJson.getString("sBranchCd"));
//                branchLoanApplication.setCredInvx(loJson.getString("sCredInvx"));
//                branchLoanApplication.setCompnyNm(loJson.getString("sCompnyNm"));
//                branchLoanApplication.setSpouseNm(loJson.getString("sSpouseNm"));
//                branchLoanApplication.setAddressx(loJson.getString("sAddressx"));
//                branchLoanApplication.setMobileNo(loJson.getString("sMobileNo"));
//                branchLoanApplication.setQMAppCde(loJson.getString("sQMAppCde"));
//                branchLoanApplication.setModelNme(loJson.getString("sModelNme"));
//                branchLoanApplication.setDownPaym(loJson.getString("nDownPaym"));
//                branchLoanApplication.setAcctTerm(loJson.getString("nAcctTerm"));
//                branchLoanApplication.setTranStat(loJson.getString("cTranStat"));
//                branchLoanApplication.setTimeStmp(loJson.getString("dTimeStmp"));
//                eBranchLoanApplications.add(branchLoanApplication);
//            }
//
//            try {
//                brnRepo.insertDetailBulkData(eBranchLoanApplications);
//                return true;
//            } catch (Exception e) {
//                e.printStackTrace();
//                return false;
//            }
//        }
//
    }

}
