/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.app
 * Electronic Personnel Access Control Security System
 * project file created : 9/27/21, 11:28 AM
 * project file last modified : 9/27/21, 11:28 AM
 */

package org.rmj.guanzongroup.ghostrider.epacss.Service.Sync;

import static org.rmj.g3appdriver.etc.WebFileServer.RequestAccessToken;
import static org.rmj.g3appdriver.etc.WebFileServer.RequestClientToken;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ESelfieLog;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RSelfieLog;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.dev.Telephony;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.g3appdriver.utils.WebApi;

import java.util.List;
import java.util.Objects;

public class BackgroundSync {
    private static final String TAG = BackgroundSync.class.getSimpleName();

    private final AppConfigPreference poConfig;
    private final HttpHeaders poHeaders;
    private final SessionManager poSession;
    private final Telephony poDevice;
    private final WebApi poApi;

    private String psClient;
    private String psTokenx;

    private String message;

    public String getMessage() {
        return message;
    }

    public BackgroundSync(Application instance) {
        this.poSession = new SessionManager(instance);
        this.poConfig = AppConfigPreference.getInstance(instance);
        this.poHeaders = HttpHeaders.getInstance(instance);
        this.poDevice = new Telephony(instance);
        this.poApi = new WebApi(poConfig.getTestStatus());
    }

    public boolean syncSelfieLogInfo(RImageInfo poImage, List<EImageInfo> loginImageInfo){
        if(loginImageInfo.size() > 0) {
            psClient = RequestClientToken(poConfig.ProducID(), poSession.getClientId(), poSession.getUserID());
            psTokenx = RequestAccessToken(psClient);
            if (!psTokenx.isEmpty()) {
                boolean[] isSent = new boolean[loginImageInfo.size()];
                for (int x = 0; x < loginImageInfo.size(); x++) {
                    try {
                        EImageInfo loImage = loginImageInfo.get(x);
                        org.json.simple.JSONObject loResult = WebFileServer.UploadFile(
                                loImage.getFileLoct(),
                                psTokenx,
                                loImage.getFileCode(),
                                loImage.getDtlSrcNo(),
                                loImage.getImageNme(),
                                poSession.getBranchCode(),
                                loImage.getSourceCD(),
                                loImage.getTransNox(),
                                "");

                        String lsResponse = (String) loResult.get("result");
                        Log.e(TAG, "Uploading image result : " + lsResponse);

                        if (Objects.requireNonNull(lsResponse).equalsIgnoreCase("success")) {
                            String lsTransNo = (String) loResult.get("sTransNox");
                            poImage.updateImageInfo(lsTransNo, loImage.getTransNox());
                            isSent[x] = true;
                        } else {
                            isSent[x] = false;
                        }

                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                        isSent[x] = false;
                    }
                }
                boolean allDataSent = true;
                for (boolean b : isSent)
                    if (!b) {
                        allDataSent = false;
                        break;
                    }
                if (allDataSent) {
                    message = "All selfie login images has been sent.\n ";
                } else {
                    message = "Some selfie login images has been sent.\n ";
                }
            } else {
                message = "Failed to send images. Reason: failed requesting access token.\n ";
            }
        } else {
            message = "No record to sync.";
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public boolean uploadLoginDetails(RSelfieLog poLog, List<ESelfieLog> loginDetails) {
        if (loginDetails.size() > 0) {
            boolean[] isSent = new boolean[loginDetails.size()];
            for (int x = 0; x < loginDetails.size(); x++) {
                try {
                    ESelfieLog selfieLog = loginDetails.get(x);
                    JSONObject loJson = new JSONObject();
                    loJson.put("sEmployID", selfieLog.getEmployID());
                    loJson.put("dLogTimex", selfieLog.getLogTimex());
                    loJson.put("nLatitude", selfieLog.getLatitude());
                    loJson.put("nLongitud", selfieLog.getLongitud());

                    String lsResponse = WebClient.sendRequest(poApi.getUrlPostSelfielog(poConfig.isBackUpServer()), loJson.toString(), poHeaders.getHeaders());

                    if (lsResponse == null) {
                        Log.e(TAG, "Sending selfie log info. Server no response");
                        isSent[x] = false;
                    } else {
                        JSONObject loResponse = new JSONObject(lsResponse);
                        String result = loResponse.getString("result");
                        if (result.equalsIgnoreCase("success")) {
                            String TransNox = loResponse.getString("sTransNox");
                            String OldTrans = selfieLog.getTransNox();
                            poLog.updateEmployeeLogStatus(TransNox, OldTrans);
                            Log.e(TAG, "Selfie log has been uploaded successfully.");
                            isSent[x] = true;
                        } else {
                            isSent[x] = false;
                        }
                    }

                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                    isSent[x] = false;
                }
            }
            boolean allDataSent = true;
            for (boolean b : isSent)
                if (!b) {
                    allDataSent = false;
                    break;
                }
            if (allDataSent) {
                message = message + "All selfie login info has been sent.\n";
            } else {
                message = message + "Some selfie login info has been sent.\n";
            }
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public boolean uploadPaidCollectionDetail(RDailyCollectionPlan poDcp, List<EDCPCollectionDetail> collectionDetails) {
        if (collectionDetails.size() > 0) {
            boolean[] isSent = new boolean[collectionDetails.size()];
            for (int x = 0; x < collectionDetails.size(); x++) {
                try {
                    EDCPCollectionDetail loDetail = collectionDetails.get(x);
                    JSONObject loData = new JSONObject();
                    loData.put("sPRNoxxxx", loDetail.getPRNoxxxx());
                    loData.put("nTranAmtx", loDetail.getTranAmtx());
                    loData.put("nDiscount", loDetail.getDiscount());
                    loData.put("nOthersxx", loDetail.getOthersxx());
                    loData.put("cTranType", loDetail.getTranType());
                    loData.put("nTranTotl", loDetail.getTranTotl());
                    if (loDetail.getBankIDxx() == null) {
                        loData.put("sBankIDxx", "");
                        loData.put("sCheckDte", "");
                        loData.put("sCheckNox", "");
                        loData.put("sCheckAct", "");
                    } else {
                        loData.put("sBankIDxx", loDetail.getBankIDxx());
                        loData.put("sCheckDte", loDetail.getCheckDte());
                        loData.put("sCheckNox", loDetail.getCheckNox());
                        loData.put("sCheckAct", loDetail.getCheckAct());
                    }
                    JSONObject loJson = new JSONObject();
                    loJson.put("sTransNox", loDetail.getTransNox());
                    loJson.put("nEntryNox", loDetail.getEntryNox());
                    loJson.put("sAcctNmbr", loDetail.getAcctNmbr());
                    loJson.put("sRemCodex", loDetail.getRemCodex());
                    loJson.put("sJsonData", loData);
                    loJson.put("dReceived", "");
                    loJson.put("sUserIDxx", poSession.getUserID());
                    loJson.put("sDeviceID", poDevice.getDeviceID());
                    Log.e(TAG, loJson.toString());
                    String lsResponse = WebClient.sendRequest(poApi.getUrlDcpSubmit(poConfig.isBackUpServer()), loJson.toString(), poHeaders.getHeaders());

                    if (lsResponse == null) {
                        Log.e(TAG, "Sending selfie log info. Server no response");
                        isSent[x] = false;
                    } else {
                        JSONObject loResponse = new JSONObject(lsResponse);
                        if (loResponse.getString("result").equalsIgnoreCase("success")) {
                            loDetail.setSendStat("1");
                            loDetail.setModified(new AppConstants().DATE_MODIFIED);
                            poDcp.updateCollectionDetailInfo(loDetail);
                            isSent[x] = true;
                        } else {
                            isSent[x] = false;
                        }
                    }

                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                    isSent[x] = false;
                }
            }
            boolean allDataSent = true;
            for (boolean b : isSent)
                if (!b) {
                    allDataSent = false;
                    break;
                }
            if (allDataSent) {
                message = message + "DCP Paid transactions has been sent.\n";
            } else {
                message = message + "Unable to send some DCP Paid transactions.\n";
            }
        }
        return true;
    }
}
