/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.g3appdriver.dev.Database.Repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.JSONObject;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DImageInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ETokenInfo;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.etc.SessionManager;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.WebFileServer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RImageInfo {
    private static final String TAG = "DB_Image_Repository";
    private final DImageInfo poDao;

    private final AppConfigPreference poConfig;
    private final SessionManager poSession;
    private final AppTokenManager poToken;

    private String message;

    public RImageInfo(Application instance){
        this.poDao = GGC_GriderDB.getInstance(instance).ImageInfoDao();
        this.poConfig = AppConfigPreference.getInstance(instance);
        this.poSession = new SessionManager(instance);
        this.poToken = new AppTokenManager(instance);
    }

    public String getMessage() {
        return message;
    }

    public LiveData<String> getImageLocationFromSrcId(String fsSource) {
        return poDao.getImageLocationFromSrcId(fsSource);
    }

    public LiveData<EImageInfo> getImageLocation(String sDtlSrcNo, String sImageNme) {
        return poDao.getImageLocation(sDtlSrcNo, sImageNme);
    }

    public LiveData<EImageInfo> getImageInfo(String sTransNox){
        return poDao.getImageInfo();
    }

    public void updateImageInfo(String TransNox, String oldTransNox){
        poDao.updateImageInfo(TransNox, new AppConstants().DATE_MODIFIED(), oldTransNox);
    }

    public void updateImageInfos(String TransNox, String sourceNo){
        poDao.updateImageInfos(TransNox, new AppConstants().DATE_MODIFIED(), sourceNo);
    }

    public List<EImageInfo> getUnsentSelfieLogImageList(){
        return poDao.getUnsentLoginImageInfo();
    }

//    public void updateImageInfo(EImageInfo imgInfo){
//        imageDao.updateImageInfo(imgInfo);
//    }

    /**
     *
     * @return returns a LiveData List of all unsent image info...
     */
    public LiveData<List<EImageInfo>> getUnsentImageList(){
        return poDao.getUnsentDCPImageInfoList();
    }

    public LiveData<List<EImageInfo>> getAllImageInfo(){
        return poDao.getImageInfoList();
    }

    public LiveData<List<EImageInfo>> getImageListInfo(String transNox){
        return poDao.getImageListInfo(transNox);
    }

    public EImageInfo CheckImageForCIExist(String fsSource, String fsDetail){
        return poDao.CheckImageForCIExist(fsSource, fsDetail);
    }

    public void UpdateImageInfoForCI(EImageInfo foImage){
        poDao.update(foImage);
    }

    public LiveData<EImageInfo> getImageLogPreview(String transNox){
        return poDao.getImageLogPreview(transNox);
    }

    public LiveData<List<EImageInfo>> getCurrentLogTimeIfExist(String fsDate){
        String DateLog = "%"+fsDate+"%";
        return poDao.getCurrentLogTimeIfExist(DateLog);
    }

    public EImageInfo getDCPImageInfoForPosting(String TransNox, String AccntNo){
        return poDao.getDCPImageInfoForPosting(TransNox, AccntNo);
    }

    public EImageInfo getCIImageForPosting(String TransNox){
        return poDao.getCIImageForPosting(TransNox);
    }

    public String CheckTokenAvailable(){
        try{
            String lsClient = poToken.GetClientToken();

            if(lsClient == null){
                message = poToken.getMessage();
                return null;
            }

            String lsAccess = poToken.GetAccessToken(lsClient);

            if(lsAccess == null){
                message = poToken.getMessage();
                return null;
            }

            return lsAccess;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return null;
        }
    }

    /**
     *
     * @param args image file name
     * @param args1 image file location
     * @param args2 current device location (Latitude)
     * @param args3 current device location (Longitude)
     * @return returns transaction no. if operation succeed, else null if operation fails. Call getMessage() to get error message.
     */
    public String SaveSelfieLogImage(String args, String args1, String args2, String args3){
        try{
            EImageInfo loImage = new EImageInfo();
            String lsTransNo = CreateUniqueID();
            loImage.setTransNox(lsTransNo);
            loImage.setFileCode("0021");
            loImage.setSourceNo(poSession.getClientId());
            loImage.setDtlSrcNo(poSession.getUserID());
            loImage.setSourceCD("LOGa");
            loImage.setMD5Hashx(WebFileServer.createMD5Hash(args1));
            loImage.setCaptured(new AppConstants().DATE_MODIFIED());
            loImage.setImageNme(args);
            loImage.setFileLoct(args1);
            loImage.setLatitude(args2);
            loImage.setLongitud(args3);
            poDao.SaveImageInfo(loImage);
            Log.d(TAG, "Selfie has been saved.");
            return lsTransNo;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return null;
        }
    }

    /**
     *
     * @param args client account no.
     * @param args1 collection transaction no.
     * @param args2 image file name.
     * @param args3 image file location.
     * @param args4 current device location(Latitude)
     * @param args5 current device location(Longitude)
     * @return transaction no. if operation success, else null if operation fails. Call getMessage() get error message.
     */
    public String SaveDcpImage(String args, String args1, String args2, String args3, String args4, String args5){
        try{
            EImageInfo loImage = new EImageInfo();
            String lsTransNo = CreateUniqueID();
            loImage.setTransNox(lsTransNo);
            loImage.setDtlSrcNo(args);
            loImage.setSourceNo(args1);
            loImage.setSourceCD("DCPa");
            loImage.setImageNme(args2);
            loImage.setFileLoct(args3);
            loImage.setFileCode("0020");
            loImage.setLatitude(args4);
            loImage.setLongitud(args5);
            loImage.setMD5Hashx(WebFileServer.createMD5Hash(args3));
            loImage.setCaptured(new AppConstants().DATE_MODIFIED());
            poDao.SaveImageInfo(loImage);
            Log.d(TAG, "DCP Selfie has been saved.");
            return lsTransNo;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return null;
        }
    }

    /**
     *
     * @param args Credit_Online_Application_CI transaction no.
     * @param args1 image file name
     * @param args2 image file location
     * @param args3 current device location (Latitude)
     * @param args4 current device location (Longitude)
     * @return transaction no. if operation success, else null if operation fails. Call getMessage() get error message.
     */
    public String SaveCIImage(String args, String args1, String args2, String args3, String args4){
        try{
            String lsTransNo;
            EImageInfo loDetail = poDao.CheckImageForCIExist(args, args);
            if(loDetail == null) {
                EImageInfo loImage = new EImageInfo();
                lsTransNo = CreateUniqueID();
                loImage.setTransNox(lsTransNo);
                loImage.setDtlSrcNo(args);
                loImage.setSourceNo(args);
                loImage.setSourceCD("COAD");
                loImage.setImageNme(args1);
                loImage.setFileLoct(args2);
                loImage.setFileCode("CI001");
                loImage.setLatitude(args3);
                loImage.setLongitud(args4);
                loImage.setMD5Hashx(WebFileServer.createMD5Hash(loImage.getFileLoct()));
                loImage.setCaptured(new AppConstants().DATE_MODIFIED());
                poDao.SaveImageInfo(loImage);
                Log.d(TAG, "CI tagging image has been saved.");
                return lsTransNo;
            }

            loDetail.setDtlSrcNo(args);
            loDetail.setSourceNo(args);
            loDetail.setSourceCD("COAD");
            loDetail.setImageNme(args1);
            loDetail.setFileLoct(args2);
            loDetail.setFileCode("CI001");
            loDetail.setLatitude(args3);
            loDetail.setLongitud(args4);
            loDetail.setMD5Hashx(WebFileServer.createMD5Hash(loDetail.getFileLoct()));
            loDetail.setCaptured(new AppConstants().DATE_MODIFIED());
            Log.d(TAG, "CI tagging image has been updated.");
            lsTransNo = loDetail.getTransNox();
            return lsTransNo;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return null;
        }
    }

    /**
     *
     * @param args Credit_Online_Application transaction no.
     * @param args1 FileCode of the document scanned.
     * @param args2 image file name
     * @param args3 image file location
     * @param args4 current device location (Latitude)
     * @param args5 current device location (Longitude)
     * @return transaction no. if operation success, else null if operation fails. Call getMessage() get error message.
     */
    public String SaveCreditAppDocumentsImage(String args, String args1, String args2, String args3, String args4, String args5){
        try{
            String lsTransNo;
            EImageInfo loDetail = poDao.CheckCreditAppDocumentIfExist(args, args1);
            if(loDetail == null) {
                lsTransNo = CreateUniqueID();
                EImageInfo loImage = new EImageInfo();
                loImage.setTransNox(lsTransNo);
                loImage.setDtlSrcNo(args);
                loImage.setSourceNo(args);
                loImage.setSourceCD("COAD");
                loImage.setFileCode(args1);
                loImage.setImageNme(args2);
                loImage.setFileLoct(args3);
                loImage.setLatitude(args4);
                loImage.setLongitud(args5);
                loImage.setMD5Hashx(WebFileServer.createMD5Hash(loImage.getFileLoct()));
                loImage.setCaptured(new AppConstants().DATE_MODIFIED());
                poDao.SaveImageInfo(loImage);
                Log.d(TAG, "Document scan image has been saved.");
                return lsTransNo;
            }

            loDetail.setDtlSrcNo(args);
            loDetail.setSourceNo(args);
            loDetail.setSourceCD("COAD");
            loDetail.setFileCode(args1);
            loDetail.setImageNme(args2);
            loDetail.setFileLoct(args3);
            loDetail.setLatitude(args4);
            loDetail.setLongitud(args5);
            loDetail.setMD5Hashx(WebFileServer.createMD5Hash(loDetail.getFileLoct()));
            loDetail.setCaptured(new AppConstants().DATE_MODIFIED());
            poDao.update(loDetail);
            Log.d(TAG, "Document scan image has been updated.");
            lsTransNo = loDetail.getTransNox();
            return lsTransNo;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return null;
        }
    }

    /**
     *
     * @param fsVal transaction no of image needed to upload.
     * @return true if operation succeed, else false if operation fails. Call getMessage() to get error message.
     */
    public String UploadImage(String fsVal){
        try{
            if(poConfig.getTestStatus()){
                message = "This feature is not available for testing...";
                return "";
            }

            EImageInfo loDetail = poDao.GetImageInfo(fsVal);
            if(loDetail == null){
                message = "Unable to find image to upload.";
                return null;
            }

            if(loDetail.getSendStat() != null){
                message = "Image is already uploaded.";
                return fsVal;
            }

            String lsClient = poToken.GetClientToken();

            if(lsClient == null){
                Log.e(TAG, poToken.getMessage());
                message = "No generated client token to upload image.";
                return null;
            }

            String lsAccess = poToken.GetAccessToken(lsClient);

            if(lsAccess == null){
                Log.e(TAG, message);
                message = "No generated access token to upload image.";
                return null;
            }

            org.json.simple.JSONObject loUpload = WebFileServer.UploadFile(
                    loDetail.getFileLoct(),
                    lsAccess,
                    loDetail.getFileCode(),
                    loDetail.getDtlSrcNo(),
                    loDetail.getImageNme(),
                    poSession.getBranchCode(),
                    loDetail.getSourceCD(),
                    loDetail.getTransNox(),
                    "");

            if (loUpload == null) {
                message = "Server no response.";
                return null;
            }

            JSONObject loResult = new JSONObject(loUpload.toJSONString());
            if(loResult.has("result")){
                String lsImgResult = (String) loUpload.get("result");
                Log.e(TAG, loUpload.toJSONString());
                if (lsImgResult.equalsIgnoreCase("error")) {
                    JSONObject loError = loResult.getJSONObject("error");
                    message = loError.getString("message");
                    return null;
                }
            } else {
                String lsImgResult = (String) loUpload.get("rhsult");
                Log.e(TAG, loUpload.toJSONString());
                if (lsImgResult.equalsIgnoreCase("error")) {
                    JSONObject loError = loResult.getJSONObject("error");
                    message = loError.getString("message");
                    return null;
                }
            }


            String lsTransnox = (String) loUpload.get("sTransNox");
            poDao.updateImageInfo(lsTransnox, new AppConstants().DATE_MODIFIED(), loDetail.getTransNox());
            return lsTransnox;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return null;
        }
    }

    private String CreateUniqueID(){
        String lsUniqIDx = "";
        try{
            String lsBranchCd = "MX01";
            String lsCrrYear = new SimpleDateFormat("yy", Locale.getDefault()).format(new Date());
            StringBuilder loBuilder = new StringBuilder(lsBranchCd);
            loBuilder.append(lsCrrYear);

            int lnLocalID = poDao.GetRowsCountForID() + 1;
            String lsPadNumx = String.format("%05d", lnLocalID);
            loBuilder.append(lsPadNumx);
            lsUniqIDx = loBuilder.toString();
        } catch (Exception e){
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
        Log.d(TAG, lsUniqIDx);
        return lsUniqIDx;
    }
}
