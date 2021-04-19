package org.rmj.guanzongroup.ghostrider.epacss.Service;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.rmj.g3appdriver.GRider.Database.Entities.ETokenInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.AppTokenManager;
import org.rmj.guanzongroup.ghostrider.notifications.Object.GNotifBuilder;

import java.util.Date;

public class GMessagingService extends FirebaseMessagingService {
    private static final String TAG = GMessagingService.class.getSimpleName();

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        AppTokenManager poToken = new AppTokenManager(getApplication());
        ETokenInfo loToken = new ETokenInfo();
        loToken.setTokenInf(s);
        poToken.setTokenInfo(loToken);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);


        Log.d(TAG, remoteMessage.getData().get("msgtype"));
        int lnChannelID = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        GNotifBuilder.createNotification(GMessagingService.this,
                remoteMessage.getData().get("title"),
                remoteMessage.getData().get("body"),
                lnChannelID).show();
    }
}
