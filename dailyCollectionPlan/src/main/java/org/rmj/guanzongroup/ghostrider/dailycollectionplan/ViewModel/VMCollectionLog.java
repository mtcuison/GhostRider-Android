package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionMaster;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VMCollectionLog extends AndroidViewModel {
    private static final String TAG = VMCollectionLog.class.getSimpleName();
    private final Application instance;
    private final RDailyCollectionPlan poDcp;
    private final RBranch poBranch;

    private MutableLiveData<EDCPCollectionMaster> poMaster = new MutableLiveData<>();
    private MutableLiveData<List<EDCPCollectionDetail>> plTranList = new MutableLiveData<>();

    public VMCollectionLog(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poDcp = new RDailyCollectionPlan(application);
        this.poBranch = new RBranch(application);
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

    public LiveData<List<EDCPCollectionDetail>> getCollectionList(){
        return poDcp.getCollectionDetailLog();
    }

    public void setTransactionList(List<EDCPCollectionDetail> transactionList){
        this.plTranList.setValue(transactionList);
    }

    public void postTransactions(PostTransactionCallback callback){
        List<EDCPCollectionDetail> loDetails = plTranList.getValue();
        new PostTask(instance, callback).execute(loDetails);
    }

    private static class PostTask extends AsyncTask<List<EDCPCollectionDetail>, Void, String>{
        private final PostTransactionCallback callback;
        private final ConnectionUtil poConn;
        private final HttpHeaders poHeaders;
        private final RDailyCollectionPlan poDcp;

        public PostTask(Application instance, PostTransactionCallback callback) {
            this.callback = callback;
            this.poConn = new ConnectionUtil(instance);
            this.poHeaders = HttpHeaders.getInstance(instance);
            this.poDcp = new RDailyCollectionPlan(instance);
        }

        @Override
        protected void onPreExecute() {
            callback.OnLoad();
            super.onPreExecute();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(List<EDCPCollectionDetail>... lists) {
            try {
                if (poConn.isDeviceConnected()) {
                    for(int x = 0; x < lists[0].size(); x++){
                        EDCPCollectionDetail loDetail = lists[0].get(x);
                        JSONObject loJson = new JSONObject();
                        JSONObject loData = new JSONObject();
                        loJson.put("sTransNox", loDetail.getTransNox());
                        loJson.put("nEntryNox", loDetail.getEntryNox());
                        loJson.put("sAcctNmbr", loDetail.getAcctNmbr());
                        loJson.put("sRemCodex", loDetail.getRemCodex());
                        loData.put("", "");
                        loJson.put("dReceived", "edcpCollectionDetails[0].");
                        loJson.put("sUserIDxx", "edcpCollectionDetails[0].");
                        loJson.put("sDeviceID", "edcpCollectionDetails[0].");

                        String lsResponse = WebClient.httpsPostJSon(WebApi.URL_DCP_SUBMIT, loJson.toString(), poHeaders.getHeaders());
                        if(lsResponse == null){
                            Log.e(TAG, "Server no response.");
                        } else {
                            JSONObject loResponse = new JSONObject(lsResponse);
                            String lsResult = loResponse.getString("result");
                            if (lsResult.equalsIgnoreCase("success")){
                                loDetail.setSendStat("1");
                                loDetail.setModified(AppConstants.DATE_MODIFIED);
                                poDcp.updateCollectionDetailInfo(loDetail);
                            } else {
                                Log.e(TAG, loResponse.getString(loResponse.toString()));
                            }
                        }
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            callback.OnPostSuccess(new String[]{s});
            super.onPostExecute(s);
        }
    }

    public interface PostTransactionCallback{
        void OnLoad();
        void OnPostSuccess(String[] args);
        void OnPostFailed(String message);
    }
}
