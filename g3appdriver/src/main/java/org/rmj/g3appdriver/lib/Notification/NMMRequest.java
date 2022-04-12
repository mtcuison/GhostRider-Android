package org.rmj.g3appdriver.lib.Notification;

import android.app.Application;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.appdriver.base.GConnection;
import org.rmj.emm.EMM;
import org.rmj.emm.EMMFactory;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.DbConnection;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchOpenMonitor;
import org.rmj.g3appdriver.GRider.Database.Entities.ENotificationMaster;
import org.rmj.g3appdriver.GRider.Database.Entities.ENotificationRecipient;
import org.rmj.g3appdriver.GRider.Database.Entities.ENotificationUser;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranchOpeningMonitor;
import org.rmj.g3appdriver.GRider.Database.Repositories.RNotificationInfo;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.RemoteMessageParser;
import org.rmj.g3appdriver.utils.WebApi;

import java.util.Objects;

public class NMMRequest {
    private static final String TAG = NMMRequest.class.getSimpleName();

    private final Application instance;

    private String message;

    public NMMRequest(Application instance) {
        this.instance = instance;
    }

    public String getMessage() {
        return message;
    }

    public boolean SaveSendMessage(String fsRecepient, String fsType, String fsTitle, String fsMessage){
        try{
            ENotificationMaster loMaster = new ENotificationMaster();
            ENotificationRecipient loRecpnt = new ENotificationRecipient();

            RNotificationInfo loNotif = new RNotificationInfo(instance);
            String lsTransNox = loNotif.getClientNextMasterCode();

            loMaster.setMsgTypex(fsType);

            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean SendRequest(String fsTransNox){
        try {
            RNotificationInfo loNotif = new RNotificationInfo(instance);
            ENotificationMaster loMaster = loNotif.getNotificationMaster(fsTransNox);
            ENotificationRecipient loRecpnt = loNotif.getNotificationRecipient(fsTransNox);

            JSONArray rcpts = new JSONArray();
            JSONObject rcpt = new JSONObject();
            rcpt.put("app", "gRider"); //Product ID of Recepient
            rcpt.put("user", loRecpnt.getRecpntID());
            rcpts.put(rcpt);

            //Create the parameters needed by the API
            JSONObject param = new JSONObject();
            param.put("type", "00000"); //Message Type

            param.put("parent", null); //Message ID of first notification
            param.put("title", loMaster.getMsgTitle());
            param.put("message", loMaster.getMessagex());
            param.put("rcpt", rcpts);

            WebApi loApi = new WebApi(AppConfigPreference.getInstance(instance).getTestStatus());

            String lsResponse = WebClient.sendRequest(
                    loApi.getUrlSendRequest(),
                    param.toString(),
                    HttpHeaders.getInstance(instance).getHeaders());
            if(lsResponse == null) {
                message = "No server response";
                return false;
            } else {
                JSONObject loResponse = new JSONObject(lsResponse);
                String lsResult =  loResponse.getString("result");
                if(lsResult.equalsIgnoreCase("success")){
                    return true;
                } else {
                    JSONObject loError = loResponse.getJSONObject("error");
                    message = loError.getString("message");
                    return false;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            message = "Something went wrong while sending error report. " + e.getMessage();
            return false;
        }
    }

    public boolean SaveReceiveMessage(RemoteMessage foMessage){
        try{
            RemoteMessageParser loParser = new RemoteMessageParser(foMessage);
            String MesgID = loParser.getValueOf("transno");
            RNotificationInfo loNotif = new RNotificationInfo(instance);
            if(loNotif.getNotificationIfExist(MesgID) >=  1){
                String lsStatus = loParser.getValueOf("status");
                loNotif.updateNotificationStatusFromOtherDevice(MesgID, lsStatus);
            } else {
                ENotificationMaster loMaster = new ENotificationMaster();
                loMaster.setTransNox(loNotif.getClientNextMasterCode());
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

                loNotif.insertNotificationInfo(loMaster, loRecpnt, loUser);

                String lsValue = loParser.getValueOf("infox");
                if (!lsValue.isEmpty()) {
                    JSONObject loJSON = new JSONObject(lsValue);
                    GConnection loDbConn = DbConnection.doConnect(instance);
                    if ("00001".equalsIgnoreCase(loJSON.getString("module"))) { //table update
                        EMM instance = EMMFactory.make(EMMFactory.NMM_SysMon_Type.TABLE_UPDATE);
                        instance.setConnection(loDbConn); //pass the iGConnection here
                        instance.setData(lsValue);
                        if (!instance.execute()) System.err.println(instance.getMessage());
                    } else if ("00002".equalsIgnoreCase(loJSON.getString("module"))) {
                        JSONObject loData = loJSON.getJSONObject("data");
                        EBranchOpenMonitor loMonitor = new EBranchOpenMonitor();
                        loMonitor.setBranchCD(loData.getString("sBranchCD"));
                        loMonitor.setTransact(loData.getString("dTransact"));
                        loMonitor.setTimeOpen(loData.getString("sTimeOpen"));
                        loMonitor.setOpenNowx(loData.getString("sOpenNowx"));
//                            loMonitor.setSendDate(loData.getString("dSendDate"));
//                            loMonitor.setNotified(loData.getString("dNotified"));
//                            loMonitor.setTimeStmp(loData.getString("dTimeStmp"));
                        new RBranchOpeningMonitor(instance).insert(loMonitor);
                    }
                }

                String lsMessageID = loMaster.getMesgIDxx();
                loNotif.updateRecipientRecievedStat(lsMessageID);
            }
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    public boolean SendResponse(String fsTransNo, String fsStatus){
        try{
            WebApi loApi = new WebApi(AppConfigPreference.getInstance(instance).getTestStatus());
            JSONObject params = new JSONObject();
            params.put("transno", fsTransNo);
            params.put("status", fsStatus);
            params.put("stamp", new AppConstants().DATE_MODIFIED);
            params.put("infox", "");

            String lsResponse = WebClient.sendRequest(
                    loApi.getUrlSendResponse(),
                    params.toString(),
                    HttpHeaders.getInstance(instance).getHeaders());
            if (lsResponse == null){
                message = "Server no response";
                return false;
            } else {
                JSONObject loResponse = new JSONObject(Objects.requireNonNull(lsResponse));
                String lsResult = loResponse.getString("result");
                if (!lsResult.equalsIgnoreCase("success")) {
                    JSONObject loError = loResponse.getJSONObject("error");
                    message = loError.getString("message");
                    return false;
                } else {
                    Log.d(TAG, "Receive response sent.");
                    return true;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    public boolean RequestPreviousNotifications(int fnIndex){
        try{
            WebApi loApi = new WebApi(AppConfigPreference.getInstance(instance).getTestStatus());
            JSONObject params = new JSONObject();
            params.put("nLimitxxx", fnIndex);
            String lsResponse = WebClient.sendRequest(
                    loApi.getUrlRequestPreviousNotifications(),
                    params.toString(),
                    HttpHeaders.getInstance(instance).getHeaders());

            if(lsResponse == null){
                message = "Server no response";
            } else {
                JSONObject loResponse = new JSONObject();
                JSONArray laMaster = loResponse.getJSONArray("master");
                JSONArray laRcpntx = loResponse.getJSONArray("recipient");

                for(int x = 0; x < laMaster.length(); x++){
                    JSONObject joMaster = laMaster.getJSONObject(x);
                    ENotificationMaster loMaster = new ENotificationMaster();
                    loMaster.setTransNox(joMaster.getString("sTransNox"));
                    loMaster.setParentxx(joMaster.getString("sParentxx"));
                    loMaster.setCreatedx(joMaster.getString("dCreatedx"));
                    loMaster.setAppSrcex(joMaster.getString("sAppSrcex"));
                    loMaster.setCreatrID(joMaster.getString("sCreatedx"));
                    loMaster.setMsgTitle(joMaster.getString("sMsgTitle"));
                    loMaster.setMessagex(joMaster.getString("sMessagex"));
                    loMaster.setURLxxxxx(joMaster.getString("sImageURL"));
                    loMaster.setDataSndx(joMaster.getString("sDataSndx"));
                    loMaster.setMsgTypex(joMaster.getString("sMsgTypex"));
                }

                for(int x = 0; x < laRcpntx.length(); x++){
                    JSONObject joRecpnt = laRcpntx.getJSONObject(x);
                    ENotificationRecipient loRecpnt = new ENotificationRecipient();
                    loRecpnt.setRecpntNm(joRecpnt.getString("sRecpntGr"));
                    loRecpnt.setAppRcptx(joRecpnt.getString(""));
                    loRecpnt.setAppRcptx(joRecpnt.getString(""));
                    loRecpnt.setMonitorx(joRecpnt.getString(""));
                    loRecpnt.setMesgStat(joRecpnt.getString(""));
                    loRecpnt.setSentxxxx(joRecpnt.getString(""));
                }
            }
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }
}
