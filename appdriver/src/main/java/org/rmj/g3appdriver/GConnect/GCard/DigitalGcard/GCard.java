package org.rmj.g3appdriver.GConnect.GCard.DigitalGcard;

import static org.rmj.g3appdriver.dev.Api.ApiResult.SERVER_NO_RESPONSE;
import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;
import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;

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

import java.util.List;

public class GCard {
    private static final String TAG = GCard.class.getSimpleName();

    private final DGcardApp poDao;
    private final HttpHeaders poHeaders;
    private final GConnectApi poApi;
    private final ClientSession poSession;
    private final AppConfigPreference poConfig;

    private String message;

    public enum QrCodeType{
        NEW_GCARD,
        TRANSACTION
    }

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

            poDao.InitDefaultActiveGCard();
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public boolean SetActiveGCard(String GcardNmbr){
        try{
            poDao.SetActiveGCard(GcardNmbr);

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public boolean AddGCard(String fsVal){
        try{
            JSONObject params = new JSONObject();
            params.put("secureno", CodeGenerator.generateSecureNo(fsVal));
            String lsResponse = WebClient.sendRequest(
                    poApi.getAddNewGCardAPI(),
                    params.toString(),
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

            EGcardApp loGCard = new EGcardApp();
            loGCard.setGCardNox(loResponse.getString("sGCardNox"));
            loGCard.setCardNmbr(loResponse.getString("sCardNmbr"));
            loGCard.setUserIDxx(poSession.getUserID());
            loGCard.setNmOnCard(loResponse.getString("sNmOnCard"));
            loGCard.setMemberxx(loResponse.getString("dMemberxx"));
            loGCard.setCardType(loResponse.getString("cCardType"));
            loGCard.setAvlPoint(loResponse.getString("nAvlPoint"));
            loGCard.setTotPoint(loResponse.getString("nTotPoint"));
            loGCard.setTranStat(loResponse.getString("cCardStat"));
            loGCard.setActvStat("0");
            loGCard.setNotified("1");
            poDao.Save(loGCard);
            return 1;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return 0;
        }
    }

    public boolean ConfirmGCard(GcardCredentials Gcard){
        try{
            Gcard.setsConfirmx("1");
            if(!Gcard.isDataValid()){
                message = Gcard.getMessage();
                return false;
            }

            String lsResponse = WebClient.sendRequest(poApi.getAddNewGCardAPI(), Gcard.getJSONParameters(), poHeaders.getHeaders());
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

            EGcardApp loGCard = new EGcardApp();
            loGCard.setGCardNox(loResponse.getString("sGCardNox"));
            loGCard.setCardNmbr(loResponse.getString("sCardNmbr"));
            loGCard.setUserIDxx(poSession.getUserID());
            loGCard.setNmOnCard(loResponse.getString("sNmOnCard"));
            loGCard.setMemberxx(loResponse.getString("dMemberxx"));
            loGCard.setCardType(loResponse.getString("cCardType"));
            loGCard.setAvlPoint(loResponse.getString("nAvlPoint"));
            loGCard.setTotPoint(loResponse.getString("nTotPoint"));
            loGCard.setTranStat(loResponse.getString("cCardStat"));
            loGCard.setActvStat("0");
            loGCard.setNotified("1");
            poDao.Save(loGCard);
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
            String lsCardNo = poDao.GetGCardNumber();
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

    public QrCodeType ParseQrCode(String QrCode){
        try{
            CodeGenerator loCode = new CodeGenerator();
            loCode.setEncryptedQrCode(QrCode);

            if(!loCode.isCodeValid()){
                message = "Invalid QR Code. The scanned QR code is not recognized or supported.";
                return null;
            }

            if(loCode.isQrCodeTransaction()){
                return QrCodeType.TRANSACTION;
            }

            return QrCodeType.NEW_GCARD;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return null;
        }
    }

    public String GetTransactionPIN(String QrCode){
        try{
            String lsMobileNo = poSession.getMobileNo();
            String lsUserIDxx = poSession.getUserID();
            String lsGCardNox = poDao.GetCardNox();

            CodeGenerator loCode = new CodeGenerator();
            loCode.setEncryptedQrCode(QrCode);

            if(loCode.isTransactionVoid()){
                return loCode.getTransactionPIN();
            }

            if(lsUserIDxx.isEmpty()){
                message = "No user account detected. Please make sure you login account before proceeding.";
                return null;
            }

            if(lsMobileNo.isEmpty()){
                message = "Unable to retrieve device mobile no. Please make sure your device has mobile no.";
                return null;
            }

            if(lsGCardNox.isEmpty()){
                message = "No GCard number is registered or active in this account. Please make sure a GCard is active.";
                return null;
            }

            if(!loCode.isDeviceValid(lsMobileNo, lsGCardNox)) {
                Log.d(TAG, "Device Mobile number: " + lsMobileNo);
                Log.d(TAG, "Current GCard number: " + lsGCardNox);
                message = "Transaction Confirmation Error. The Provided Mobile Number or Account is Invalid for Transaction Confirmation.";
                return null;
            }

            return loCode.getTransactionPIN();
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return null;
        }
    }

    public String GetNewGCardNumber(String QrCode){
        try{
            String lsMobileNo = poSession.getMobileNo();
            String lsUserIDxx = poSession.getUserID();

            CodeGenerator loCode = new CodeGenerator();
            loCode.setEncryptedQrCode(QrCode);

            if(lsUserIDxx.isEmpty()){
                message = "User Account Not Found. Please Log In to Your Account Before Proceeding.";
                return null;
            }

            if(lsMobileNo.isEmpty()){
                message = "Unable to retrieve device mobile no. Please make sure your device has mobile no.";
                return null;
            }

            return loCode.getGCardNumber();
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return null;
        }
    }

    public LiveData<EGcardApp> GetActiveGCardInfo(){
        return poDao.GetActiveGCcardInfo();
    }

    public LiveData<List<EGcardApp>> GetGCardList(){
        return poDao.GetAllGCardInfo();
    }
}
