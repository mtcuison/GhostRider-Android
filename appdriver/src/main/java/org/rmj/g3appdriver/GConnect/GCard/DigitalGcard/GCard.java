package org.rmj.g3appdriver.GConnect.GCard.DigitalGcard;

import static org.rmj.g3appdriver.dev.Api.ApiResult.SERVER_NO_RESPONSE;
import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;
import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.Build;

import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.GConnect.Account.ClientSession;
import org.rmj.g3appdriver.GConnect.Api.GConnectApi;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.GConnect.room.DataAccessObject.DGcardApp;
import org.rmj.g3appdriver.GConnect.room.Entities.EGcardApp;
import org.rmj.g3appdriver.GConnect.room.GGC_GConnectDB;
import org.rmj.g3appdriver.GConnect.GCard.DigitalGcard.pojo.GcardCredentials;
import org.rmj.g3appdriver.dev.encryp.CodeGenerator;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.AppConstants;

public class GCard {
    private static final String TAG = GCard.class.getSimpleName();

    private final DGcardApp poDao;
    private final HttpHeaders poHeaders;
    private final GConnectApi poApi;
    private final ClientSession poSession;
    private final AppConfigPreference poConfig;

    private String message;

    public GCard(Application instance) {
        this.poDao = GGC_GConnectDB.getInstance(instance).EGcardAppDao();
        this.poHeaders = HttpHeaders.getInstance(instance);
        this.poApi = new GConnectApi(instance);
        this.poSession = ClientSession.getInstance(instance);
        this.poConfig = AppConfigPreference.getInstance(instance);
    }

    public String getMessage() {
        return message;
    }

    public boolean ImportGcard(){
        try{
            JSONObject param = new JSONObject();
            param.put("user_id", CodeGenerator.generateSecureNo(poSession.getUserID()));
            String lsResponse = WebClient.sendRequest(
                    poApi.getImportGCardAPI(),
                    param.toString(),
                    poHeaders.getHeaders());
            if(lsResponse == null){
                message = SERVER_NO_RESPONSE;
                return false;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loResponse.getJSONObject("error");
                message = getErrorMessage(loError);
                return false;
            }

            JSONArray laJson = loResponse.getJSONArray("detail");
            for(int x = 0; x < laJson.length(); x++) {
                JSONObject loJson = laJson.getJSONObject(x);
                EGcardApp loGCard = new EGcardApp();
                loGCard.setGCardNox(loJson.getString("sGCardNox"));
                loGCard.setCardNmbr(loJson.getString("sCardNmbr"));
                loGCard.setUserIDxx(poSession.getUserID());
                loGCard.setNmOnCard(loJson.getString("sNmOnCard"));
                loGCard.setMemberxx(loJson.getString("dMemberxx"));
                loGCard.setCardType(loJson.getString("cCardType"));
                loGCard.setAvlPoint(loJson.getString("nAvlPoint"));
                loGCard.setTotPoint(loJson.getString("nTotPoint"));
                loGCard.setTranStat(loJson.getString("cCardStat"));
                loGCard.setActvStat("0");
                loGCard.setNotified("1");
                poDao.Save(loGCard);
            }
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
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

            String lsResponse = WebClient.sendRequest(poApi.getAddNewGCardAPI(), Gcard.getJSONParameters(), poHeaders.getHeaders());
            if(lsResponse == null){
                message = SERVER_NO_RESPONSE;
                return 0;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loResponse.getJSONObject("error");
                String lsCode = loError.getString("code");
                if(lsCode.equalsIgnoreCase("CNF")){
                    message = getErrorMessage(loError);
                    return 2;
                }

                message = getErrorMessage(loError);
                return 0;
            }
            return 1;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return 0;
        }
    }

    public boolean SetActiveGCard(String GCardNo){
        try{

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public Bitmap GenerateGCardQrCode(){
        try{
            String lsSource = "CARD";
            String lsDevcID = poConfig.getDeviceID();
            String lsCardNo = poDao.getCardNo();
            String lsUserID = poSession.getUserID();
            String lsMobNox = poConfig.getMobileNo();
            String lsDateTm = AppConstants.GCARD_DATE_TIME();
            double lsCardPt;
            if(poDao.getRedeemItemPoints() > 0){
                lsCardPt = Math.abs(poDao.getAvailableGcardPoints() - poDao.getRedeemItemPoints());
            } else {
                lsCardPt = poDao.getAvailableGcardPoints();
            }
            String lsModelx = Build.MODEL;
            String lsTransN = "";
            return CodeGenerator.generateGCardCodex(lsSource,
                    lsDevcID,
                    lsCardNo,
                    lsUserID,
                    lsMobNox,
                    lsDateTm,
                    lsCardPt,
                    lsModelx,
                    lsTransN);
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return null;
        }
    }

    public String GetScanResult(String QrCode){
        try{
            String lsMobileNo = poSession.getMobileNo();
            String lsUserIDxx = poSession.getUserID();
            String lsGCardNox = poDao.getCardNox();

            if(lsGCardNox == null){
                message = "";
                return null;
            }

            if(lsGCardNox.isEmpty()){
                message = "";
                return null;
            }



        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return null;
        }
    }

    LiveData<EGcardApp> GetActiveGCardInfo(){
        return poDao.GetActiveGCcardInfo();
    }
}
