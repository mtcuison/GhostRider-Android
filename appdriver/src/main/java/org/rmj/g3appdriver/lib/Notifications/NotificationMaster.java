package org.rmj.g3appdriver.lib.Notifications;

import android.app.Application;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DNotificationMaster;
import org.rmj.g3appdriver.dev.Database.Entities.ENotificationMaster;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NotificationMaster {
    private static final String TAG = NotificationMaster.class.getSimpleName();

    private final Application instance;

    private final DNotificationMaster poDao;

    private String message;

    public NotificationMaster(Application instance) {
        this.instance = instance;
        this.poDao = GGC_GriderDB.getInstance(instance).nMasterDao();
    }

    public String getMessage() {
        return message;
    }

    public boolean SaveMaster(RemoteMessage remoteMessage){
        try{
            RemoteMessageParser loParser = new RemoteMessageParser(remoteMessage);
            String lsTransNo = CreateUniqueID();

            ENotificationMaster loMaster = new ENotificationMaster();
            loMaster.setTransNox(lsTransNo);
            loMaster.setMesgIDxx(loParser.getValueOf("transno"));
            loMaster.setParentxx(loParser.getValueOf("parent"));
            loMaster.setCreatedx(loParser.getValueOf("stamp"));
            loMaster.setAppSrcex(loParser.getValueOf("appsrce"));
            loMaster.setCreatrID(loParser.getValueOf("srceid"));
            loMaster.setCreatrNm(loParser.getValueOf("srcenm"));
            loMaster.setDataSndx(loParser.getValueOf("infox"));
            loMaster.setMsgTitle(loParser.getDataValueOf("title"));
            loMaster.setMessagex(loParser.getDataValueOf("message"));
            loMaster.setMsgTypex(loParser.getValueOf("msgmon"));

            poDao.Insert(loMaster);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    private String CreateUniqueID(){
        String lsUniqIDx = "";
        try{
            String lsBranchCd = "MX01";
            String lsCrrYear = new SimpleDateFormat("yy", Locale.getDefault()).format(new Date());
            StringBuilder loBuilder = new StringBuilder(lsBranchCd);
            loBuilder.append(lsCrrYear);

            int lnLocalID = poDao.GetRowsCountForID() + 1;
            String lsPadNumx = String.format("%05d", lnLocalID);
            loBuilder.append(lsPadNumx);
            lsUniqIDx = loBuilder.toString();
        } catch (Exception e){
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
        Log.d(TAG, lsUniqIDx);
        return lsUniqIDx;
    }
}
