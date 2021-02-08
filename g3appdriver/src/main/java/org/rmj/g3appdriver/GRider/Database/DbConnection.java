package org.rmj.g3appdriver.GRider.Database;

import android.app.Application;
import android.util.Log;

import org.rmj.appdriver.base.GConnection;
import org.rmj.appdriver.base.GProperty;
import org.rmj.appdriver.crypt.GCryptFactory;
import org.rmj.appdriver.iface.iGCrypt;
import org.rmj.g3appdriver.etc.AppConfigPreference;

import java.io.InputStream;

public class DbConnection {
    private static final String TAG = "AppDriver_Connection";

    public static GConnection doConnect(Application application){
        try{
            InputStream inputStream = application.getAssets().open("GhostRiderXP.properties");

            //initialize GProperty
            GProperty loProperty = new GProperty(inputStream, AppConfigPreference.getInstance(application).ProducID());
            if (loProperty.loadConfig()){
                Log.d(TAG, "Config File was loaded.");

                loProperty.setDBHost(application.getPackageName());
            } else {
                Log.e(TAG, "Unable to load config file.");
            }
            //initialize GCrypt
            iGCrypt loCrypt = GCryptFactory.make(GCryptFactory.CrypType.AESCrypt);

            GConnection loConn = new GConnection();
            loConn.setGProperty(loProperty);
            loConn.setGAESCrypt(loCrypt);

            if (!loConn.connect()){
                Log.e("TAG", "Unable to initialize connection");
                return null;
            } else {
                Log.d("TAG", "Connection was successful.");
                return loConn;
            }
        } catch (Exception ex){
            Log.e("TAG", ex.getMessage());
            return null;
        }
    }
}
