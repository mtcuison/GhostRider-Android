package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionMaster;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;

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
        List<JSONObject> params = new ArrayList<>();
        EDCPCollectionMaster loMaster = poMaster.getValue();
        List<EDCPCollectionDetail> loDetails = plTranList.getValue();
        try{
            for(int x = 0; x < Objects.requireNonNull(plTranList.getValue()).size(); x++) {
                JSONObject loJson = new JSONObject();
                JSONObject loData = new JSONObject();
                loJson.put("sTransNox", loMaster.getTransNox());
                loJson.put("nEntryNox", loMaster.getEntryNox());
                loJson.put("sAcctNmbr", loDetails.get(x).getAcctNmbr());
                loJson.put("sRemCodex", loDetails.get(x).getRemCodex());
                loData.put("", "");
                loJson.put("dReceived", "edcpCollectionDetails[0].");
                loJson.put("sUserIDxx", "edcpCollectionDetails[0].");
                loJson.put("sDeviceID", "edcpCollectionDetails[0].");
                params.add(loJson);
            }
            new PostTask(instance, callback).execute(params);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static class PostTask extends AsyncTask<List<JSONObject>, Void, String>{
        private final PostTransactionCallback callback;
        private final Application instance;

        public PostTask(Application instance, PostTransactionCallback callback) {
            this.callback = callback;
            this.instance = instance;
        }

        @Override
        protected void onPreExecute() {
            callback.OnLoad();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(List<JSONObject>... lists) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    public interface PostTransactionCallback{
        void OnLoad();
        void OnPostSuccess(String[] args);
        void OnPostFailed(String message);
    }
}
