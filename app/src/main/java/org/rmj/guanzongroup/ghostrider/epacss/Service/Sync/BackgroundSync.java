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
import android.util.Log;

import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.dev.Telephony;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.WebFileServer;

import java.util.List;
import java.util.Objects;

public class BackgroundSync {
    private static final String TAG = BackgroundSync.class.getSimpleName();

    private final AppConfigPreference poConfig;
    private final HttpHeaders poHeaders;
    private final SessionManager poSession;
    private final Telephony poDevice;

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
}
