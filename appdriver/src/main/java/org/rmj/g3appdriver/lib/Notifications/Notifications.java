package org.rmj.g3appdriver.lib.Notifications;

import android.app.Application;

import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;
import org.rmj.g3appdriver.dev.Database.Entities.EBranchOpenMonitor;
import org.rmj.g3appdriver.dev.Database.Entities.ENotificationMaster;
import org.rmj.g3appdriver.lib.Notifications.Obj.NMM_CustomerService;
import org.rmj.g3appdriver.lib.Notifications.Obj.NMM_Events;
import org.rmj.g3appdriver.lib.Notifications.Obj.NMM_MPOrderStatus;
import org.rmj.g3appdriver.lib.Notifications.Obj.NMM_MPQuestions;
import org.rmj.g3appdriver.lib.Notifications.Obj.NMM_MPReview;
import org.rmj.g3appdriver.lib.Notifications.Obj.NMM_Promotions;
import org.rmj.g3appdriver.lib.Notifications.Obj.NMM_Regular;
import org.rmj.g3appdriver.lib.Notifications.Obj.NMM_TableUpdate;

public class Notifications {
    private static final String TAG = Notifications.class.getSimpleName();

    private final Application instance;

    private final NotificationMaster poMaster;
    private final NotificationDetail poDetail;
    private final BranchOpeningMonitor poMonitor;

    private String message;

    public Notifications(Application instance) {
        this.instance = instance;
        this.poMaster = new NotificationMaster(instance);
        this.poDetail = new NotificationDetail(instance);
        this.poMonitor = new BranchOpeningMonitor(instance);
    }

    public String getMessage() {
        return message;
    }

    public iNotification getInstance(RemoteMessage remoteMessage){
        String lsSysMon = new RemoteMessageParser(remoteMessage).getValueOf("msgmon");
        switch (lsSysMon){
            case "00000":
                return new NMM_Regular(instance);
            case "00001":
                return new NMM_TableUpdate(instance);
            case "00002":
                return new NMM_MPOrderStatus(instance);
            case "00003":
                return new NMM_Promotions(instance);
            case "00004":
                return new NMM_Events(instance);
            case "00005":
                return new NMM_MPQuestions(instance);
            case "00006":
                return new NMM_MPReview(instance);
            case "00007":
                return new NMM_CustomerService(instance);
            default:
                return null;
        }
    }

    public JSONObject CreateNotification(RemoteMessage foVal){
        try{
            if(!poMaster.SaveMaster(foVal)){
                message = poMaster.getMessage();
                return null;
            }

            if(!poDetail.SaveDetail(foVal)){
                message = poDetail.getMessage();
                return null;
            }

            RemoteMessageParser loParser = new RemoteMessageParser(foVal);

            String lsType = loParser.getValueOf("infox");

            if(!lsType.isEmpty()) {
                JSONObject loModule = new JSONObject(lsType);
                String lsModule = loModule.getString("module");
                switch (lsType) {
                    case "00002":
                        JSONObject loData = loModule.getJSONObject("data");
                        EBranchOpenMonitor loBranch = new EBranchOpenMonitor();
                        loBranch.setBranchCD(loData.getString("sBranchCD"));
                        loBranch.setTransact(loData.getString("dTransact"));
                        loBranch.setTimeOpen(loData.getString("sTimeOpen"));
                        loBranch.setOpenNowx(loData.getString("sOpenNowx"));
                        break;
                }
            }
            return null;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return null;
        }
    }
}
