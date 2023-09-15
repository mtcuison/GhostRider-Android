package org.rmj.g3appdriver.GConnect.Account;

import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;
import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;

import org.json.JSONObject;
import org.rmj.g3appdriver.GConnect.Api.GConnectApi;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.GConnect.room.Entities.EClientInfo;
import org.rmj.g3appdriver.etc.AppConstants;

public class AccountUpdate {
    private static final String TAG = AccountUpdate.class.getSimpleName();

    private final HttpHeaders poHeaders;
    private final GConnectApi poApi;

    private String message;

    public AccountUpdate(Application instance) {
        this.poHeaders = HttpHeaders.getInstance(instance);
        this.poApi = new GConnectApi(instance);
    }

    public String getMessage() {
        return message;
    }

    public boolean UpdateAccountInfo(EClientInfo foValue){
        try{
            JSONObject param = new JSONObject();
            param.put("cGenderCd", foValue.getGenderCd());
            param.put("cCvilStat", foValue.getCvilStat());
            param.put("sCitizenx", foValue.getCitizenx());
            param.put("sTaxIDNox", foValue.getTaxIDNox());

            String lsResponse = WebClient.sendRequest(
                    poApi.getUpdateAccountInfo(),
                    param.toString(),
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

    public boolean UpdateMobileNo(String fsArgs){
        try{
            JSONObject param = new JSONObject();
            param.put("dTransact", new AppConstants().DATE_MODIFIED);
            param.put("sMobileNo", fsArgs);

            String lsResponse = WebClient.sendRequest(
                    poApi.getMobileUpdateAPI(),
                    param.toString(),
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

    public boolean UpdateEmailAddress(String fsArgs){
        try{
            JSONObject param = new JSONObject();
            param.put("dTransact", new AppConstants().DATE_MODIFIED);
            param.put("sEmailAdd", fsArgs);

            String lsResponse = WebClient.sendRequest(
                    poApi.getEmailUpdateAPI(),
                    param.toString(),
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

    public boolean UpdateAddress(EClientInfo foVal){
        try{
            JSONObject param = new JSONObject();
            param.put("dTransact", new AppConstants().DATE_MODIFIED);
            param.put("sHouseNo1", foVal.getHouseNo1());
            param.put("sAddress1", foVal.getAddress1());
            param.put("sBrgyIDx1", foVal.getBrgyIDx1());
            param.put("sTownIDx1", foVal.getTownIDx1());
            param.put("sHouseNo2", foVal.getHouseNo2());
            param.put("sAddress2", foVal.getAddress2());
            param.put("sBrgyIDx2", foVal.getBrgyIDx2());
            param.put("sTownIDx2", foVal.getTownIDx2());

            String lsResponse = WebClient.sendRequest(
                    poApi.getAddressUpdateAPI(),
                    param.toString(),
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
