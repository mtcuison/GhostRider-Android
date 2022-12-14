package org.rmj.g3appdriver.lib.Notifications.Obj;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DNotifications;
import org.rmj.g3appdriver.dev.Database.Entities.ENotificationMaster;
import org.rmj.g3appdriver.dev.Database.Entities.ENotificationRecipient;
import org.rmj.g3appdriver.dev.Database.Entities.ENotificationUser;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;
import org.rmj.g3appdriver.dev.HttpHeaders;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.lib.Notifications.NOTIFICATION_STATUS;
import org.rmj.g3appdriver.lib.Notifications.RemoteMessageParser;
import org.rmj.g3appdriver.lib.Notifications.iNotification;
import org.rmj.g3appdriver.lib.Notifications.model.NotificationItemList;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NMM_MPOrderStatus implements iNotification {
    private static final String TAG = NMM_MPOrderStatus.class.getSimpleName();

    private final DNotifications poDao;
    private final HttpHeaders poHeaders;
    private final AppConfigPreference poConfig;

    private String message;

    public NMM_MPOrderStatus(Application instance) {
        this.poDao = GGC_GriderDB.getInstance(instance).NotificationDao();
        this.poHeaders = HttpHeaders.getInstance(instance);
        this.poConfig = AppConfigPreference.getInstance(instance);
    }

    @Override
    public String Save(RemoteMessage foVal) {
        try{
            RemoteMessageParser loParser = new RemoteMessageParser(foVal);
            String lsMesgIDx = loParser.getValueOf("transno");
            if(poDao.CheckNotificationIfExist(lsMesgIDx) >= 1){
                String lsStatus = loParser.getValueOf("status");
                poDao.updateNotificationStatusFromOtherDevice(lsMesgIDx, lsStatus);
            } else {
                ENotificationMaster loMaster = new ENotificationMaster();
                loMaster.setTransNox(CreateUniqueID());
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

                ENotificationRecipient loRecpnt = new ENotificationRecipient();
                loRecpnt.setTransNox(loParser.getValueOf("transno"));
                loRecpnt.setAppRcptx(loParser.getValueOf("apprcpt"));
                loRecpnt.setRecpntID(loParser.getValueOf("rcptid"));
                loRecpnt.setRecpntNm(loParser.getValueOf("rcptnm"));
                loRecpnt.setMesgStat(loParser.getValueOf("status"));
                loRecpnt.setReceived(new AppConstants().DATE_MODIFIED);
                loRecpnt.setTimeStmp(new AppConstants().DATE_MODIFIED);

                ENotificationUser loUser = new ENotificationUser();
                loUser.setUserIDxx(loParser.getValueOf("srceid"));
                loUser.setUserName(loParser.getValueOf("srcenm"));

                poDao.insert(loMaster);
                poDao.insert(loRecpnt);
                if(poDao.CheckIfUserExist(loParser.getValueOf("srceid")) == null){
                    poDao.insert(loUser);
                }
            }
            return lsMesgIDx;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return null;
        }
    }

    @Override
    public ENotificationMaster SendResponse(String mesgID, NOTIFICATION_STATUS status) {
        try{
            WebApi loApis = new WebApi(poConfig.getTestStatus());

            String lsTranStat = "";

            switch (status){
                case OPEN:
                    lsTranStat = "0";
                    break;
                case DELIVERED:
                    lsTranStat = "1";
                    break;
                case RECEIVED:
                    lsTranStat = "2";
                    break;
                case READ:
                    lsTranStat = "3";
                    break;
                case DELETED:
                    lsTranStat = "4";
                    break;
            }

            JSONObject params = new JSONObject();
            params.put("transno", mesgID);
            params.put("status", lsTranStat);
            params.put("stamp", new AppConstants().DATE_MODIFIED);
            params.put("infox", "");

            String lsResponse = WebClient.httpsPostJSon(
                    loApis.getUrlSendResponse(poConfig.isBackUpServer()),
                    params.toString(),
                    poHeaders.getHeaders());
            if(lsResponse == null){
                message = "Server no response while sending response.";
                return null;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if (!lsResult.equalsIgnoreCase("success")) {
                JSONObject loError = loResponse.getJSONObject("error");
                message = loError.getString("message");
                return null;
            }

            poDao.UpdateSentResponseStatus(mesgID, lsTranStat, new AppConstants().DATE_MODIFIED);
            return poDao.CheckIfMasterExist(mesgID);
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return null;
        }
    }

    @Override
    public boolean CreateNotification(String title, String message) {
        try{

            return true;
        } catch (Exception e){
            e.printStackTrace();
            this.message = e.getMessage();
            return false;
        }
    }

    @Override
    public LiveData<List<NotificationItemList>> GetNotificationList() {
        return null;
    }

    @Override
    public String getMessage() {
        return message;
    }

    private String CreateUniqueID(){
        String lsUniqIDx = "";
        try{
            String lsBranchCd = "MX01";
            String lsCrrYear = new SimpleDateFormat("yy", Locale.getDefault()).format(new Date());
            StringBuilder loBuilder = new StringBuilder(lsBranchCd);
            loBuilder.append(lsCrrYear);

            int lnLocalID = poDao.GetNotificationCountForID() + 1;
            String lsPadNumx = String.format("%05d", lnLocalID);
            loBuilder.append(lsPadNumx);
            lsUniqIDx = loBuilder.toString();
        } catch (Exception e){
            e.printStackTrace();
        }
        return lsUniqIDx;
    }
}
