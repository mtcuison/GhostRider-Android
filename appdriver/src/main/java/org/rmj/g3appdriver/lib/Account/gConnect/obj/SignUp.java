package org.rmj.g3appdriver.lib.Account.gConnect.obj;

import static org.rmj.g3appdriver.dev.Api.ApiResult.SERVER_NO_RESPONSE;
import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;
import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;

import org.json.JSONObject;
import org.rmj.g3appdriver.GConnect.Api.GConnectApi;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.lib.Account.Model.iAuth;
import org.rmj.g3appdriver.lib.Account.pojo.AccountCredentials;

public class SignUp implements iAuth {
    private static final String TAG = SignUp.class.getSimpleName();

    private final Application instance;
    private final GConnectApi poApi;
    private final HttpHeaders poHeaders;

    private String message;

    public SignUp(Application instance) {
        this.instance = instance;
        this.poApi = new GConnectApi(instance);
        this.poHeaders = HttpHeaders.getInstance(instance);
    }

    @Override
    public int DoAction(Object args) {
        try{
            AccountCredentials loInfo = (AccountCredentials) args;
            if(!loInfo.isDataValid()){
                message = loInfo.getMessage();
                return 0;
            }

            JSONObject params = new JSONObject();
            params.put("name", loInfo.getsUserName());
            params.put("mail", loInfo.getsEmailAdd());
            params.put("pswd", loInfo.getsPassword());
            params.put("mobile", loInfo.getsMobileNo());
            params.put("cAgreeTnC", loInfo.getcAgreeTnC());

            String lsResponse = WebClient.sendRequest(
                    poApi.getRegisterAcountAPI(),
                    params.toString(),
                    poHeaders.getHeaders());

            if(lsResponse == null){
                message = SERVER_NO_RESPONSE;
                return 0;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if(!lsResult.equalsIgnoreCase("success")){
                JSONObject loError = loResponse.getJSONObject("error");
                message = getErrorMessage(loError);
                return 0;
            }

            message = "An email has been sent to your inbox for account verification.";
            return 1;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return 0;
        }
    }

    @Override
    public String getMessage() {
        return message;
    }
}
