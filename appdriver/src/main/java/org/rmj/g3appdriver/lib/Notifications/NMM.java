package org.rmj.g3appdriver.lib.Notifications;

import android.app.Application;

import com.google.firebase.messaging.RemoteMessage;

import org.rmj.g3appdriver.lib.Notifications.Obj.NMM_CustomerService;
import org.rmj.g3appdriver.lib.Notifications.Obj.NMM_Events;
import org.rmj.g3appdriver.lib.Notifications.Obj.NMM_MPOrderStatus;
import org.rmj.g3appdriver.lib.Notifications.Obj.NMM_MPQuestions;
import org.rmj.g3appdriver.lib.Notifications.Obj.NMM_MPReview;
import org.rmj.g3appdriver.lib.Notifications.Obj.NMM_Panalo;
import org.rmj.g3appdriver.lib.Notifications.Obj.NMM_Promotions;
import org.rmj.g3appdriver.lib.Notifications.Obj.NMM_Regular;
import org.rmj.g3appdriver.lib.Notifications.Obj.NMM_TableUpdate;

public class NMM {
    private static final String TAG = NMM.class.getSimpleName();

    private final Application instance;

    public NMM(Application instance) {
        this.instance = instance;
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
                return new NMM_Panalo(instance);
        }
    }
}
