package org.rmj.g3appdriver.GConnect.Account;

import static org.rmj.g3appdriver.dev.Api.ApiResult.SERVER_NO_RESPONSE;
import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;
import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.lib.Account.pojo.PhotoDetail;
import org.rmj.g3appdriver.GConnect.Api.GConnectApi;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.dev.Api.WebFileServer;
import org.rmj.g3appdriver.GConnect.room.DataAccessObject.DClientInfo;
import org.rmj.g3appdriver.GConnect.room.GGC_GConnectDB;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.AppConstants;

import java.util.ArrayList;
import java.util.List;

public class ClientVerification {
    private static final String TAG = ClientMaster.class.getSimpleName();

    private final DClientInfo poDao;
    private final HttpHeaders poHeaders;
    private final GConnectApi poApi;
    private final AppConfigPreference poConfig;

    private String message;

    public ClientVerification(Application instance) {
        this.poDao = GGC_GConnectDB.getInstance(instance).EClientDao();
        this.poHeaders = HttpHeaders.getInstance(instance);
        this.poApi = new GConnectApi(instance);
        this.poConfig = AppConfigPreference.getInstance(instance);
    }

    public String getMessage() {
        return message;
    }

    public boolean UploadVerificationImage(PhotoDetail foVal){
        try{
            String lsProdct = poConfig.ProducID();
            String lsClient = poDao.GetClientID();
            String lsUserID = poDao.GetUserID();

            boolean isTestCase = poConfig.getTestStatus();
            if(isTestCase){
                message = "App on testing mode.";
                return true;
            }

            if(lsClient == null){
                message = "Client id not found.";
                return false;
            }

            if(lsUserID == null){
                message = "User id not found.";
                return false;
            }

            String lsClntTk = WebFileServer.RequestClientToken(lsProdct, lsClient, lsUserID);

            if(lsClntTk == null){
                message = "Unable to generate client. Please try again later.";
                return false;
            }

            if(lsClntTk.isEmpty()){
                message = "Unable to generate client. Please try again later.";
                return false;
            }

            String lsAccess = WebFileServer.RequestAccessToken(lsClntTk);

            if(lsAccess == null){
                message = "Unable to generate access token. Please try again later.";
                return false;
            }

            if(lsAccess.isEmpty()){
                message = "Unable to generate access token. Please try again later.";
                return false;
            }

            org.json.simple.JSONObject loResponse = WebFileServer.UploadFile(
                    foVal.getFileLoct(),
                    lsAccess,
                    foVal.getFileCode(),
                    foVal.getDtlSrcNo(),
                    foVal.getImageNme(),
                    foVal.getDtlSrcNo(),
                    foVal.getSourceCD(),
                    foVal.getSourceNo(),
                    "");

            if (loResponse == null) {
                message = SERVER_NO_RESPONSE;
                return false;
            }

            String lsResult = (String) loResponse.get("result");
            if (!lsResult.equalsIgnoreCase("success")) {
                JSONObject loError = new JSONObject((String) loResponse.get("error"));
                message = getErrorMessage(loError);
                return false;
            }


//                        poJson = new JSONObject(loResponse.toJSONString());
            return true;

        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public boolean SubmitSelfieVerification(String fsDtrn,
                                            String fsName,
                                            String fsHash,
                                            String fsPath,
                                            String fsDate){
        try{
            JSONObject params = new JSONObject();
            params.put("dTransact", fsDtrn);
            params.put("sImageNme", fsName);
            params.put("sMD5Hashx", fsHash);
            params.put("sImagePth", fsPath);
            params.put("dImgeDate", fsDate);


            String lsResponse = WebClient.sendRequest(
                    poApi.getSubmitSelfieVerificationAPI(),
                    params.toString(),
                    poHeaders.getHeaders());

            if(lsResponse == null){
                message = "Unable to retrieve server response.";
                return false;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if(!lsResult.equalsIgnoreCase("success")){
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

    public List<JSONObject> ImportIDCode(){
        try {
            List<JSONObject> loList = new ArrayList<>();
            String lsResponse = WebClient.sendRequest(
                    poApi.getImportValidIdCodeAPI(),
                    new JSONObject().toString(),
                    poHeaders.getHeaders());

            if(lsResponse == null){
                message = "Unable to retrieve server response.";
                return null;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if(!lsResult.equalsIgnoreCase("success")){
                JSONObject loError = loResponse.getJSONObject("error");
                message = getErrorMessage(loError);
                return null;
            }

            JSONArray laJson = loResponse.getJSONArray("detail");
            for(int x = 0; x < laJson.length(); x++){
                JSONObject loDetail = laJson.getJSONObject(x);
                loList.add(loDetail);
            }

            return loList;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return null;
        }
    }

    public boolean SubmitIDVerification(JSONObject foVal){
        try{
            JSONObject params = new JSONObject();
            params.put("dTransact", new AppConstants().DATE_MODIFIED);
            params.put("sIDCodex1", foVal.getString("sIDName1x"));
            params.put("sIDNoxxx1", foVal.getString("sIDNmbr1x"));
            params.put("sIDFrntx1", foVal.getString("sIDFrnt1x"));
            params.put("sIDBackx1", foVal.getString("sIDBack1x"));
            params.put("dIDExpry1", foVal.getString("dExpiry1x"));
            params.put("sIDCodex2", foVal.getString("sIDName2x"));
            params.put("sIDNoxxx2", foVal.getString("sIDNmbr2x"));
            params.put("sIDFrntx2", foVal.getString("sIDFrnt2x"));
            params.put("sIDBackx2", foVal.getString("sIDBack2x"));
            params.put("dIDExpry2", foVal.getString("dExpiry2x"));

            String lsResponse = WebClient.sendRequest(
                    poApi.getSubmitIdVerificationAPI(),
                    params.toString(),
                    poHeaders.getHeaders());

            if(lsResponse == null){
                message = "Unable to retrieve server response.";
                return false;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if(!lsResult.equalsIgnoreCase("success")){
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
}
