package org.rmj.g3appdriver.lib.gConnect.GCard.DigitalGcard;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.json.JSONObject;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.dev.Database.GConnect.DataAccessObject.DGcardApp;
import org.rmj.g3appdriver.dev.Database.GConnect.Entities.EGcardApp;
import org.rmj.g3appdriver.dev.Database.GConnect.GGC_GConnectDB;
import org.rmj.g3appdriver.lib.gConnect.GCard.DigitalGcard.pojo.GcardCredentials;
import org.rmj.g3appdriver.utils.ConnectionUtil;

public class GCard {
    private static final String TAG = GCard.class.getSimpleName();

    private final Application instance;

    private final DGcardApp poDao;
    private final HttpHeaders poHeaders;

    private String message;

    public GCard(Application instance) {
        this.instance = instance;
        this.poDao = GGC_GConnectDB.getInstance(instance).EGcardAppDao();
        this.poHeaders = HttpHeaders.getInstance(instance);
    }

    public String getMessage() {
        return message;
    }

    public boolean ImportGcard(){
        try{

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    /**
     *
     * @param Gcard pass gcard credential object for
     * @return 0-> if process failed.
     *  1-> if process succeed.
     *  2-> if need confirmation
     */
    public int AddGcard(GcardCredentials Gcard){
        try{
            if(!Gcard.isDataValid()){
                message = Gcard.getMessage();
                return 0;
            }

            String lsResponse = WebClient.sendRequest(poAPI.getAddNewGCardAPI(), gcardInfo.getJSONParameters(), poHeaders.getHeaders());
            if(lsResponse == null){
                message = "No server response.";
                return 0;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loResponse.getJSONObject("error");
                String lsCode = loError.getString("code");
                if(lsCode.equalsIgnoreCase("CNF")){
                    message = loError.getString("message");
                    return 2;
                }

                message = loError.getString("message");
                return 0;
            }
            return 1;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return 0;
        }
    }

    public boolean SetActiveGCard(String GCardNo){
        try{

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    LiveData<EGcardApp> GetActiveGCardInfo(){
        return poDao.GetActiveGCcardInfo();
    }
}
