package org.rmj.g3appdriver.lib.BullsEye;

import android.app.Application;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ELog_Selfie;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RLogSelfie;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.g3appdriver.etc.WebFileServer;

public class SelfieLog {
    private static final String TAG = SelfieLog.class.getSimpleName();

    private final Application instance;

    private String message;

    public SelfieLog(Application application) {
        this.instance = application;
    }

    public String getMessage() {
        return message;
    }

    public boolean SaveSelfieLog(String fsBranchCd,
                                 String fnLatitude,
                                 String fnLongitde,
                                 String fsFileName,
                                 String fsPhotoPth){
        try{
            SessionManager loSession = new SessionManager(instance);
            RLogSelfie logSelfie = new RLogSelfie(instance);
            ELog_Selfie loLog = new ELog_Selfie();
            String lsTransNo = logSelfie.getLogNextCode();
            loLog.setTransNox(lsTransNo);
            loLog.setTransact(AppConstants.CURRENT_DATE);
            loLog.setBranchCd(fsBranchCd);
            loLog.setEmployID(loSession.getEmployeeID());
            loLog.setLogTimex(new AppConstants().DATE_MODIFIED);
            loLog.setLatitude(fnLatitude);
            loLog.setLongitud(fnLongitde);
            loLog.setSendStat("0");
            logSelfie.insertSelfieLog(loLog);

            RImageInfo loImgInfo = new RImageInfo(instance);
            EImageInfo loImage = new EImageInfo();
            loImage.setTransNox(loImgInfo.getImageNextCode());
            loImage.setFileCode("0021");
            loImage.setSourceNo(loSession.getClientId());
            loImage.setDtlSrcNo(loSession.getUserID());
            loImage.setSourceCD("LOGa");
            loImage.setImageNme(fsFileName);
            loImage.setFileLoct(fsPhotoPth);
            loImage.setLatitude(fnLatitude);
            loImage.setLongitud(fnLongitde);
            loImage.setMD5Hashx(WebFileServer.createMD5Hash(fsPhotoPth));
            loImage.setCaptured(new AppConstants().DATE_MODIFIED);
            loImgInfo.insertImageInfo(loImage);

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }
}
