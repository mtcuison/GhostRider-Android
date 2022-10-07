/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.app
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.ghostrider.epacss.Service;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.rmj.g3appdriver.dev.Database.Entities.ETokenInfo;
import org.rmj.g3appdriver.dev.Database.Repositories.AppTokenManager;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.guanzongroup.ghostrider.notifications.Function.AndroidNotificationManager;

public class GMessagingService extends FirebaseMessagingService {
    private static final String TAG = GMessagingService.class.getSimpleName();

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        AppTokenManager poToken = new AppTokenManager(getApplication());
        ETokenInfo loToken = new ETokenInfo();
        loToken.setTokenInf(s);
        poToken.setTokenInfo(loToken);
        AppConfigPreference.getInstance(GMessagingService.this).setAppToken(s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        new AndroidNotificationManager(getApplication()).DoAction(remoteMessage);
    }
}
