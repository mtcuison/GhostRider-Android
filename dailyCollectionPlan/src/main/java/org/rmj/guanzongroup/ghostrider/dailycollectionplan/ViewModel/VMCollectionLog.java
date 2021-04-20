package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EAddressUpdate;
import org.rmj.g3appdriver.GRider.Database.Entities.EBankInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionMaster;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCP_Remittance;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EMobileUpdate;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBankInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCollectionUpdate;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDCP_Remittance;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class VMCollectionLog extends AndroidViewModel {
    private static final String TAG = VMCollectionLog.class.getSimpleName();
    private final RDailyCollectionPlan poDcp;
    private final RBranch poBranch;
    private final RImageInfo poImage;
    private final RDCP_Remittance poRemit;
    private final RCollectionUpdate poUpdate;
    private final RBankInfo poBank;
    private final ConnectionUtil poConn;
    private final HttpHeaders poHeaders;

    private final MutableLiveData<EDCPCollectionMaster> poMaster = new MutableLiveData<>();
    private final MutableLiveData<List<EImageInfo>> plImageLst = new MutableLiveData<>();
    private final MutableLiveData<List<EAddressUpdate>> paAddress = new MutableLiveData<>();
    private final MutableLiveData<List<EMobileUpdate>> paMobile = new MutableLiveData<>();
    private final MutableLiveData<String> dTransact = new MutableLiveData<>();
    private final MutableLiveData<String> nCashOHnd = new MutableLiveData<>();

    private double nTotRemit = 0;
    private double nTotCollt = 0;

    private final MutableLiveData<List<DDCPCollectionDetail.CollectionDetail>> plDetail = new MutableLiveData<>();

    public VMCollectionLog(@NonNull Application application) {
        super(application);
        this.poDcp = new RDailyCollectionPlan(application);
        this.poBranch = new RBranch(application);
        this.poImage = new RImageInfo(application);
        this.poUpdate = new RCollectionUpdate(application);
        this.dTransact.setValue(AppConstants.CURRENT_DATE);
        this.poRemit = new RDCP_Remittance(application);
        this.poBank = new RBankInfo(application);
        this.poConn = new ConnectionUtil(application);
        this.poHeaders = HttpHeaders.getInstance(application);
    }

    public LiveData<EBranchInfo> getUserBranchInfo(){
        return poBranch.getUserBranchInfo();
    }

    public LiveData<EDCPCollectionMaster> getCollectionMaster(){
        return poDcp.getCollectionMaster();
    }

    public void setCollectionMaster(EDCPCollectionMaster collectionMaster){
        try {
            this.poMaster.setValue(collectionMaster);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public LiveData<List<EImageInfo>> getUnsentImageInfoList(){
        return poImage.getUnsentImageList();
    }

    public LiveData<String> getCollectedTotal(String fsTrasact){
        return poDcp.getCollectedTotal(fsTrasact);
    }

    public LiveData<String> getTotalRemittedPayment(String fsTransact){
        return poDcp.getTotalRemittedPayment(fsTransact);
    }

    public void Calculate_COH_Remitted(String fsTrasact, RDCP_Remittance.OnCalculateCallback callback){
        poRemit.Calculate_COH_Remitted(fsTrasact, callback);
    }

    public void Calculate_Check_Remitted(String fsTrasact, RDCP_Remittance.OnCalculateCallback callback){
        poRemit.Calculate_Check_Remitted(fsTrasact, callback);
    }

    public LiveData<List<EBankInfo>> getBankInfoList(){
        return poBank.getBankInfoList();
    }

    public LiveData<List<EBranchInfo>> getBranchInfoList(){
        return poBranch.getAllBranchInfo();
    }

    public LiveData<String> getTotalRemittedCollection(String fsTrasact){
        return poRemit.getTotalRemittedCollection(fsTrasact);
    }

    public void setnTotRemit(double nTotRemit) {
        this.nTotRemit = nTotRemit;
        calc_CashOnHand();
    }

    public void setnTotCollt(double nTotCollt) {
        this.nTotCollt = nTotCollt;
        calc_CashOnHand();
    }

    public LiveData<String> getCashOnHand(){
        return nCashOHnd;
    }

    private void calc_CashOnHand(){
        double lnTotal = nTotCollt - nTotRemit;
        nCashOHnd.setValue(String.valueOf(lnTotal));
    }

    public void setDateTransact(String fsTransact){
        try {
            @SuppressLint("SimpleDateFormat") Date loDate = new SimpleDateFormat("MMMM dd, yyyy").parse(fsTransact);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat lsFormatter = new SimpleDateFormat("yyyy-MM-dd");
            this.dTransact.setValue(lsFormatter.format(Objects.requireNonNull(loDate)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public LiveData<String> getDateTransact(){
        return dTransact;
    }

    public LiveData<List<EDCPCollectionDetail>> getCollectionDetailForDate(String dTransact){
        return poDcp.getCollectionDetailForDate(dTransact);
    }

    public void setImageInfoList(List<EImageInfo> imageInfoList){
        this.plImageLst.setValue(imageInfoList);
    }

    public LiveData<List<EAddressUpdate>> getAllAddress(){
        return poUpdate.getAddressList();
    }

    public LiveData<List<EMobileUpdate>> getAllMobileNox(){
        return poUpdate.getMobileList();
    }

    public void setAddressList(List<EAddressUpdate> paAddress) {
        this.paAddress.setValue(paAddress);
    }

    public void setMobileList(List<EMobileUpdate> paMobile) {
        this.paMobile.setValue(paMobile);
    }


    public void RemitCollection(EDCP_Remittance foRemittance, VMCollectionRemittance.OnRemitCollectionCallback callback){
        new RemitCollectionTask(callback).execute(foRemittance);
    }

    @SuppressLint("StaticFieldLeak")
    private class RemitCollectionTask extends AsyncTask<EDCP_Remittance, Void, String> {

        VMCollectionRemittance.OnRemitCollectionCallback callback;

        public RemitCollectionTask(VMCollectionRemittance.OnRemitCollectionCallback callback) {
            this.callback = callback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnRemit();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(EDCP_Remittance... edcp_remittances) {
            String lsResult = "";
            EDCP_Remittance loRemit = edcp_remittances[0];
            try{
                String lsTransNox = poRemit.getTransnoxMaster(loRemit.getTransact());
                String lsEntryNox = poRemit.getRemittanceEntry(loRemit.getTransact());
                loRemit.setTransNox(lsTransNox);
                loRemit.setEntryNox(lsEntryNox);
                poRemit.insert(loRemit);
                if(!poConn.isDeviceConnected()){
                    lsResult = AppConstants.LOCAL_EXCEPTION_ERROR("Cash Remittance has been save.");
                } else {
                    JSONObject param = new JSONObject();
                    param.put("sTransNox", loRemit.getTransNox());
                    param.put("nEntryNox", 2);
                    param.put("dTransact", loRemit.getTransact());
                    param.put("cRemitTyp", loRemit.getRemitTyp());
                    param.put("sCompnyNm", loRemit.getCompnyNm());
                    param.put("sBankAcct", loRemit.getBankAcct());
                    param.put("sReferNox", loRemit.getReferNox());
                    param.put("cPaymType", loRemit.getPaymForm());
                    param.put("nAmountxx", Double.parseDouble(loRemit.getAmountxx()));

                    String lsResponse = WebClient.httpsPostJSon(WebApi.URL_DCP_REMITTANCE, param.toString(), poHeaders.getHeaders());
                    JSONObject loJson = new JSONObject(lsResponse);
                    String result = loJson.getString("result");
                    if(result.equalsIgnoreCase("success")){
                        poRemit.updateSendStat(loRemit.getTransNox(), loRemit.getEntryNox());
                    }
                    lsResult = lsResponse;
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            return lsResult;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try{
                JSONObject loJson = new JSONObject(s);
                String lsResult = loJson.getString("result");
                if(lsResult.equalsIgnoreCase("success")){
                    callback.OnSuccess();
                } else {
                    JSONObject loError = loJson.getJSONObject("error");
                    String message = loError.getString("message");
                    callback.OnFailed(message);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
