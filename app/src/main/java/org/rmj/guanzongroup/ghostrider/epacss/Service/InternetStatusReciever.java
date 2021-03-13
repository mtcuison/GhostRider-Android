package org.rmj.guanzongroup.ghostrider.epacss.Service;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.etc.SessionManager;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.g3appdriver.utils.ConnectionUtil;

import java.util.ArrayList;
import java.util.List;

public class InternetStatusReciever extends BroadcastReceiver {
    private static final String TAG = InternetStatusReciever.class.getSimpleName();

    private final Application instance;

    private ConnectionUtil poConn;
    private HttpHeaders poHeaders;
    private SessionManager poSession;


    private List<EImageInfo> loginImageInfo = new ArrayList<>();

    public InternetStatusReciever(Application instance) {
        this.instance = instance;
        this.poConn = new ConnectionUtil(instance);
        this.poHeaders = HttpHeaders.getInstance(instance);
        this.poSession = new SessionManager(instance);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(poConn.isDeviceConnected()){
            String lsClient = WebFileServer.RequestClientToken("IntegSys", poSession.getClientId(), poSession.getUserID());
            String lsAccess = WebFileServer.RequestAccessToken(lsClient);

            if(lsClient.isEmpty() || lsAccess.isEmpty()){

            }
        }
    }

    public void setLoginImageInfo(List<EImageInfo> imageInfoList){
        this.loginImageInfo = imageInfoList;
    }
}
