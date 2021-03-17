package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;
import android.database.Cursor;
import android.os.AsyncTask;
import android.telephony.ims.RcsUceAdapter;

import androidx.lifecycle.LiveData;

import org.json.JSONObject;
import org.rmj.appdriver.base.GConnection;
import org.rmj.apprdiver.util.MiscUtil;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.AppDatabase;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DClientUpdate;
import org.rmj.g3appdriver.GRider.Database.DbConnection;
import org.rmj.g3appdriver.GRider.Database.Entities.EClientUpdate;

import java.util.List;

public class RClientUpdate {
    private static final String TAG = RClientUpdate.class.getSimpleName();
    private final DClientUpdate clientDao;
    private Application app;

    private JSONObject poDetail;

    public RClientUpdate(Application application){
        this.clientDao = AppDatabase.getInstance(application).ClientUpdateDao();
        this.app = application;
    }

    public void insertClientUpdateInfo(EClientUpdate clientUpdate){
        this.clientDao.insertClientUpdateInfo(clientUpdate);
    }

    public void updateClientInfo(EClientUpdate clientUpdate){
        this.clientDao.updateClientInfo(clientUpdate);
    }
    public LiveData<EClientUpdate> selectClient(String sSourceNo, String DtlSrcNo){
        return this.clientDao.selectClient(sSourceNo, DtlSrcNo);
    }

    public LiveData<List<EClientUpdate>> selectClientUpdate(){
        return clientDao.selectClientUpdate();
    }

    public void UpdateClientInfoStatus(String ClientID){
        this.clientDao.updateClientInfoImage(ClientID, AppConstants.DATE_MODIFIED);
    }

    public void UpdateClientImage(String ClientID, String ImageName){
        this.clientDao.updateClientInfoImage(ClientID, ImageName);
    }

    /**
     *
     * @param ClientID pass a unique key to determine which info must be converted to json...
     * @return returns JSON object that is being created by doInbackground method.
     *          JSONObject contains all info inside Client_Update_Request table...
     */
    public JSONObject getClientUpdateDetail(String ClientID){
        ClientUpdateToJsonTask loUpdate = new ClientUpdateToJsonTask(clientDao);
        loUpdate.execute(ClientID);
        return loUpdate.getDetail();
    }

    private static class ClientUpdateToJsonTask extends AsyncTask<String, Void, JSONObject>{
        private JSONObject loDetail;
        private DClientUpdate clientDao;

        public ClientUpdateToJsonTask(DClientUpdate clientDao) {
            this.clientDao = clientDao;
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            JSONObject loJson = new JSONObject();
            try{
                EClientUpdate loClient = clientDao.getClientUpdateInfo(strings[0]).getValue();
                loJson.put("sClientID", loClient.getClientID());
                loJson.put("sSourceCd", loClient.getSourceCd());
                loJson.put("sSourceNo", loClient.getSourceNo());
                loJson.put("sDtlSrcNo", loClient.getDtlSrcNo());
                loJson.put("sLastName", loClient.getLastName());
                loJson.put("sFrstName", loClient.getFrstName());
                loJson.put("sMiddName", loClient.getMiddName());
                loJson.put("sSuffixNm", loClient.getSuffixNm());
                loJson.put("sHouseNox", loClient.getHouseNox());
                loJson.put("sAddressx", loClient.getAddressx());
                loJson.put("sTownIDxx", loClient.getTownIDxx());
                loJson.put("cGenderxx", loClient.getGenderxx());
                loJson.put("cCivlStat", loClient.getCivlStat());
                loJson.put("dBirthDte", loClient.getBirthDte());
                loJson.put("dBirthPlc", loClient.getBirthPlc());
                loJson.put("sLandline", loClient.getLandline());
                loJson.put("sMobileNo", loClient.getMobileNo());
                loJson.put("sEmailAdd", loClient.getEmailAdd());
                loJson.put("sImageNme", loClient.getImageNme());
            } catch (Exception e){
                e.printStackTrace();
            }
            return loJson;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            loDetail = jsonObject;
        }

        public JSONObject getDetail(){
            return loDetail;
        }
    }

    public String getClientNextCode(){
        String lsNextCode = "";
        GConnection loConn = DbConnection.doConnect(app);
        try{
            lsNextCode = MiscUtil.getNextCode("Client_Update_Request", "sClientID", true, loConn.getConnection(), "", 12, false);
        } catch (Exception e){
            e.printStackTrace();
        }
        loConn = null;
        return lsNextCode;
    }
}
