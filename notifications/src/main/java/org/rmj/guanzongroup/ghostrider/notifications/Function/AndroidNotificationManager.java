/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.notifications
 * Electronic Personnel Access Control Security System
 * project file created : 5/22/21 11:35 AM
 * project file last modified : 5/22/21 11:35 AM
 */

package org.rmj.guanzongroup.ghostrider.notifications.Function;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;
//import org.rmj.appdriver.base.GConnection;
//import org.rmj.emm.EMM;
//import org.rmj.emm.EMMFactory;
import org.rmj.g3appdriver.dev.Database.Entities.EBranchOpenMonitor;
import org.rmj.g3appdriver.dev.Database.Entities.ENotificationMaster;
import org.rmj.g3appdriver.dev.Database.Entities.ENotificationRecipient;
import org.rmj.g3appdriver.dev.Database.Entities.ENotificationUser;
import org.rmj.g3appdriver.dev.Database.Repositories.RBranch;
import org.rmj.g3appdriver.lib.Notifications.BranchOpeningMonitor;
import org.rmj.g3appdriver.dev.Database.Repositories.RNotificationInfo;
import org.rmj.g3appdriver.dev.HttpHeaders;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;
import org.rmj.guanzongroup.ghostrider.notifications.Etc.RemoteMessageParser;
import org.rmj.guanzongroup.ghostrider.notifications.Object.GNotifBuilder;

import java.util.Date;
import java.util.Objects;

public class AndroidNotificationManager {
    private static final String TAG  = AndroidNotificationManager.class.getSimpleName();

    private final Application instance;

    public AndroidNotificationManager(Application application) {
        this.instance = application;
    }

    public void DoAction(RemoteMessage foMessage){
        new SendResponseTask(instance, foMessage).execute();
    }

    private static class SendResponseTask extends AsyncTask<Void, Void, String> {
        private final Application instance;
        private final RemoteMessageParser loParser;
        private final ConnectionUtil loConn;
        private final HttpHeaders poHeaders;
        private final RNotificationInfo poNotification;
        private final BranchOpeningMonitor poOpening;
        private final AppConfigPreference loConfig;
        private final RBranch poBranch;
        private final WebApi poApi;
        private boolean pbSpecial = false;
        private char pcSpecial;
        private JSONObject poJson;
        private String BranchNm; //Use for Branch Opening Notification

        public SendResponseTask(Application application, RemoteMessage remoteMessage) {
            this.instance = application;
            this.loParser = new RemoteMessageParser(remoteMessage);
            this.loConn = new ConnectionUtil(instance);
            this.poHeaders = HttpHeaders.getInstance(instance);
            this.poNotification = new RNotificationInfo(instance);

            this.poOpening = new BranchOpeningMonitor(instance);
            this.poBranch = new RBranch(instance);
            this.loConfig = AppConfigPreference.getInstance(instance);
            this.poApi = new WebApi(loConfig.getTestStatus());
        }

        @SuppressLint("NewApi")
        @Override
        protected String doInBackground(Void... voids) {
            String lsResponse = "";
            try{
                String MesgID = loParser.getValueOf("transno");
                if(poNotification.getNotificationIfExist(MesgID) >=  1){
                    pbSpecial = true;
                    pcSpecial = '0';
                    String lsStatus = loParser.getValueOf("status");
                    poNotification.updateNotificationStatusFromOtherDevice(MesgID, lsStatus);
                } else {
                    pbSpecial = false;
                    ENotificationMaster loMaster = new ENotificationMaster();
//                    loMaster.setTransNox(poNotification.getClientNextMasterCode());
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

                    poNotification.insertNotificationInfo(loMaster, loRecpnt, loUser);

                    String lsValue = loParser.getValueOf("infox");

                    if (!lsValue.isEmpty()) {
                        pbSpecial = true;
                        JSONObject loJSON = new JSONObject(lsValue);
                        if ("00001".equalsIgnoreCase(loJSON.getString("module"))) { //table update
                            pcSpecial = '1';
//                            EMM instance = EMMFactory.make(EMMFactory.NMM_SysMon_Type.TABLE_UPDATE);
////                            instance.setConnection(loDbConn); //pass the iGConnection here
//                            instance.setData(lsValue);
//                            if (!instance.execute()) System.err.println(instance.getMessage());
                        } else if ("00002".equalsIgnoreCase(loJSON.getString("module"))) {
                            pcSpecial = '2';
                            JSONObject loData = loJSON.getJSONObject("data");
                            poJson = loData;
                            BranchNm = poBranch.getBranchNameForNotification(loData.getString("sBranchCD"));
                            EBranchOpenMonitor loMonitor = new EBranchOpenMonitor();
                            loMonitor.setBranchCD(loData.getString("sBranchCD"));
                            loMonitor.setTransact(loData.getString("dTransact"));
                            loMonitor.setTimeOpen(loData.getString("sTimeOpen"));
                            loMonitor.setOpenNowx(loData.getString("sOpenNowx"));
//                            loMonitor.setSendDate(loData.getString("dSendDate"));
//                            loMonitor.setNotified(loData.getString("dNotified"));
//                            loMonitor.setTimeStmp(loData.getString("dTimeStmp"));
                            poOpening.insert(loMonitor);
                        }
                    }

                    String lsMessageID = loMaster.getMesgIDxx();
                    poNotification.updateRecipientRecievedStat(lsMessageID);
                    if (loConn.isDeviceConnected()) {
                        JSONObject params = new JSONObject();
                        params.put("transno", lsMessageID);
                        params.put("status", "2");
                        params.put("stamp", new AppConstants().DATE_MODIFIED);
                        params.put("infox", "");

                        String response = WebClient.httpsPostJSon(poApi.getUrlSendResponse(loConfig.isBackUpServer()), params.toString(), poHeaders.getHeaders());
                        JSONObject loJson = new JSONObject(Objects.requireNonNull(response));
                        Log.e(TAG, loJson.getString("result"));
                        String lsResult = loJson.getString("result");
                        if (lsResult.equalsIgnoreCase("success")) {
                            Log.d(TAG, "Receive response sent.");
                        }
                    } else {
                        lsResponse = AppConstants.NO_INTERNET();
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            return lsResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            CreateNotification();
        }

        private void CreateNotification(){
            try {
                int lnChannelID = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
                if(!pbSpecial) {
                    GNotifBuilder.createFirebaseNotification(instance,
                            loParser.getValueOf("transno"),
                            loParser.getDataValueOf("title"),
                            loParser.getDataValueOf("message"),
                            lnChannelID).show();
                } else {
                    if(pcSpecial == '1') {
                        GNotifBuilder.createFirebaseNotification(instance,
                                loParser.getValueOf("transno"),
                                "System Configuration",
                                "GRider system configuration has been updated.",
                                lnChannelID).show();
                    } else if(pcSpecial == '2') {
                        GNotifBuilder.createFirebaseNotification(instance,
                                loParser.getValueOf("transno"),
                                "Branch Opening Monitor",
                                loParser.getDataValueOf("message"),
                                lnChannelID).show();
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
