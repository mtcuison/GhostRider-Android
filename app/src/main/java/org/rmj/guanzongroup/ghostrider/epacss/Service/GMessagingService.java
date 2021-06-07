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

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;
import org.rmj.appdriver.base.GConnection;
import org.rmj.emm.EMM;
import org.rmj.emm.EMMFactory;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.DbConnection;
import org.rmj.g3appdriver.GRider.Database.Entities.ENotificationMaster;
import org.rmj.g3appdriver.GRider.Database.Entities.ENotificationRecipient;
import org.rmj.g3appdriver.GRider.Database.Entities.ENotificationUser;
import org.rmj.g3appdriver.GRider.Database.Entities.ETokenInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.AppTokenManager;
import org.rmj.g3appdriver.GRider.Database.Repositories.RNotificationInfo;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebClient;
import org.rmj.guanzongroup.ghostrider.notifications.Etc.RemoteMessageParser;
import org.rmj.guanzongroup.ghostrider.notifications.Etc.NotificationAssets;
import org.rmj.guanzongroup.ghostrider.notifications.Function.AndroidNotificationManager;
import org.rmj.guanzongroup.ghostrider.notifications.Object.GNotifBuilder;

import java.util.Date;
import java.util.Objects;

import static org.rmj.g3appdriver.utils.WebApi.URL_SEND_RESPONSE;

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

    @SuppressLint("WrongThread")
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        new AndroidNotificationManager(getApplication()).DoAction(remoteMessage);
    }
}
