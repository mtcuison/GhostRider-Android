package org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.utils.ConnectionUtil;

public class VMSelfieLogin extends AndroidViewModel {
    private static final String TAG = VMSelfieLogin.class.getSimpleName();
    private final Application instance;
    private final RImageInfo poImage;
    private final REmployee poUser;

    public interface OnLoginTimekeeperListener{
        void OnSuccess(String args);
        void OnFailed(String message);
    }

    public VMSelfieLogin(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poImage = new RImageInfo(application);
        this.poUser = new REmployee(application);
    }

    public LiveData<EEmployeeInfo> getUserInfo(){
        return poUser.getUserInfo();
    }

    public void loginTimeKeeper(EImageInfo loImage, OnLoginTimekeeperListener callback){
        try {
            loImage.setTransNox(poImage.getImageNextCode());
            poImage.insertImageInfo(loImage);
            callback.OnSuccess("Login Success");

            //JSONObject loJson = new JSONObject();

            //new LoginTimekeeperTask(loImage, instance, callback).execute(loJson);
        } catch (Exception e){
            e.printStackTrace();
            callback.OnFailed(e.getMessage());
        }
    }

    private static class LoginTimekeeperTask extends AsyncTask<JSONObject, Void, String>{
        private final HttpHeaders poHeaders;
        private final ConnectionUtil poConn;
        private final RImageInfo poImage;
        private final EImageInfo poImageInfo;
        private final OnLoginTimekeeperListener callback;

        public LoginTimekeeperTask(EImageInfo foImage, Application instance, OnLoginTimekeeperListener callback){
            this.poImageInfo = foImage;
            this.poHeaders = HttpHeaders.getInstance(instance);
            this.poConn = new ConnectionUtil(instance);
            this.poImage = new RImageInfo(instance);
            this.callback = callback;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(JSONObject... loJson) {
            try{
                if(poConn.isDeviceConnected()){
                    String lsResponse = WebClient.httpsPostJSon("", loJson[0].toString(), poHeaders.getHeaders());
                    if(lsResponse == null){
                        //TODO: create logic here...
                    } else {
                        JSONObject loResponse = new JSONObject(lsResponse);
                        String lsResult = loResponse.getString("result");
                        if(lsResult.equalsIgnoreCase("success")){
                            poImageInfo.setSendStat('1');
                            poImageInfo.setSendDate(AppConstants.DATE_MODIFIED);
                            poImage.updateImageInfo(poImageInfo);
                        }
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }
}