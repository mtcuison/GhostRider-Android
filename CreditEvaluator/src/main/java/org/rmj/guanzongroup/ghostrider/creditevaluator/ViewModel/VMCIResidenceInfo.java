/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.CreditEvaluator
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.ECIEvaluation;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCIEvaluation;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCollectionUpdate;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.dev.Telephony;
import org.rmj.g3appdriver.etc.SessionManager;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Activity.Activity_CIApplication;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Etc.ViewModelCallBack;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Model.CIResidenceInfoModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class VMCIResidenceInfo extends AndroidViewModel {
    private static final String TAG = VMCIResidenceInfo.class.getSimpleName();
    private final Application instance;
    private final RCIEvaluation poCI;
    private final ECIEvaluation evaluation;
    private final RImageInfo poImage;
    private final SessionManager poUser;

    private final MutableLiveData<ECIEvaluation> poCIDetail = new MutableLiveData<>();
    private final MutableLiveData<String> sTransNox = new MutableLiveData<>();
    private final MutableLiveData<String> nLatitude = new MutableLiveData<>();
    private final MutableLiveData<String> nLogitude = new MutableLiveData<>();
    private final MutableLiveData<String> sCredInvxx = new MutableLiveData<>();

    private List<EImageInfo> imgInfo = new ArrayList<>();

    private final REmployee poEmploye;
    private final SessionManager poSession;
    public VMCIResidenceInfo(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poCI = new RCIEvaluation(application);
        this.evaluation = new ECIEvaluation();
        this.poImage = new RImageInfo(application);
        this.poEmploye = new REmployee(application);
        this.poUser = new SessionManager(application);
        this.poSession = new SessionManager(application);
    }
    public interface OnImportCallBack{
        void onPostResidenceInfo();
        void onSuccessResidenceInfo();
        void onFailedResidenceInfo(String message);
    }

    public LiveData<EEmployeeInfo> getEmplopyeInfo(){
        return this.poEmploye.getEmployeeInfo();
    }
    public void setEmployeeID(String empID){
        this.sCredInvxx.setValue(empID);
    }
    public void setCurrentCIDetail(ECIEvaluation detail){
        this.poCIDetail.setValue(detail);
    }
    public void setsTransNox(String transNox){
        this.sTransNox.setValue(transNox);
    }
    public void setnLogitude(String longitude){
        this.nLogitude.setValue(longitude);
    }
    public void setnLatitude(String latitude){
        this.nLatitude.setValue(latitude);
    }

    public LiveData<ECIEvaluation> getCIByTransNox(String transNox){
        return poCI.getAllCIApplication(transNox);
    }
    public void insertNewCiApplication(ECIEvaluation eciEvaluation){
        poCI.insertCiApplication(eciEvaluation);

    }


    public void saveResidenceImageInfo(EImageInfo foImage) {
        try {
            boolean isImgExist = false;
            String tansNo = "";
            for (int i = 0; i < imgInfo.size(); i++) {
                if (foImage.getSourceNo().equalsIgnoreCase(imgInfo.get(i).getSourceNo())
                        && foImage.getDtlSrcNo().equalsIgnoreCase(imgInfo.get(i).getDtlSrcNo())) {
                    tansNo = imgInfo.get(i).getTransNox();
                    isImgExist = true;
                }
            }
            if (isImgExist) {
                foImage.setTransNox(tansNo);
                poImage.updateImageInfo(foImage);
            } else {
                foImage.setTransNox(poImage.getImageNextCode());
                poImage.insertImageInfo(foImage);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean saveCIResidence(CIResidenceInfoModel infoModel, ViewModelCallBack callback) {
        try {

            new UpdateTask(poCI, sCredInvxx.getValue(), infoModel, callback).execute(poCIDetail.getValue());
            return true;
        } catch (NullPointerException e) {
            e.printStackTrace();
            callback.onFailedResult("NullPointerException error");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            callback.onFailedResult("Exception error");
            return false;
        }
    }

    //Added by Jonathan 2021/04/13
    //Need AsyncTask for background threading..
    //RoomDatabase requires background task in order to manipulate Tables...
    private  class UpdateTask extends AsyncTask<ECIEvaluation, Void, String> {
        private final RCIEvaluation poCIEvaluation;
        private final CIResidenceInfoModel infoModel;
        private final ViewModelCallBack callback;
        private final String sCredInvxx;

        public UpdateTask(RCIEvaluation poCIEvaluation,String sCredInvxx,CIResidenceInfoModel infoModel, ViewModelCallBack callback) {
            this.poCIEvaluation = poCIEvaluation;
            this.infoModel = infoModel;
            this.callback = callback;
            this.sCredInvxx = sCredInvxx;
        }

        @Override
        protected String doInBackground(ECIEvaluation... detail) {
            try {
                boolean isExist = false;
                if (!infoModel.isValidData()) {
                    return infoModel.getMessage();
                } else {
                    ECIEvaluation loDetail = detail[0];
                    loDetail.setTransNox(Activity_CIApplication.getInstance().getTransNox());
                    Log.e(TAG, "empID " + sCredInvxx);
//                    loDetail.setCredInvx(sCredInvxx);
                    loDetail.setLandMark(infoModel.getLandMark());
                    loDetail.setOwnershp(infoModel.getOwnershp());
                    loDetail.setOwnOther(infoModel.getOwnOther());
                    loDetail.setHouseTyp(infoModel.getHouseTyp());
                    loDetail.setGaragexx(infoModel.getGaragexx());
                    loDetail.setHasOther(infoModel.getHasOthers());
                    loDetail.setLatitude(infoModel.getLatitude());
                    loDetail.setLongitud(infoModel.getLongitud());
                    poCIEvaluation.updateCiResidences(loDetail);
                    return "success";
                }
            } catch (NullPointerException e){
                e.printStackTrace();
                return e.getMessage();
            }
            catch (Exception e){
                e.printStackTrace();
                return e.getMessage();
            }
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equalsIgnoreCase("success")){
                callback.onSaveSuccessResult("Residence info has been save.");
            } else {
                callback.onFailedResult(s);
            }
        }
    }
    public void PostResidenceDetail(EImageInfo psImageInfo,OnImportCallBack callback) {
        try {
            new PostResidenceDetail(instance, psImageInfo, callback).execute();
        } catch (Exception e) {
        }
    }

    //Added by Jonathan 2021/04/13
    //Need AsyncTask for background threading..
    //RoomDatabase requires background task in order to manipulate Tables...
    //Residence Image Post to server
    public class PostResidenceDetail extends AsyncTask<Void, Void, String> {
        private final ConnectionUtil poConn;
        private final OnImportCallBack callback;
        private final SessionManager poUser;
        private final RDailyCollectionPlan poDcp;
        private final RImageInfo poImage;
        private final Telephony poTelephony;
        private final HttpHeaders poHeaders;
        private final RCollectionUpdate rCollect;

        private final EImageInfo psImgInfo;
        public PostResidenceDetail(Application instance, EImageInfo psImgInfo, OnImportCallBack callback) {
            this.poConn = new ConnectionUtil(instance);
            this.poUser = new SessionManager(instance);
            this.psImgInfo = psImgInfo;
            this.callback = callback;
            this.poDcp = new RDailyCollectionPlan(instance);
            this.poImage = new RImageInfo(instance);
            this.poTelephony = new Telephony(instance);
            this.poHeaders = HttpHeaders.getInstance(instance);
            this.rCollect = new RCollectionUpdate(instance);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(Void... voids) {
            String lsResult;
            try {
                if (!poConn.isDeviceConnected()) {
                    lsResult = AppConstants.NO_INTERNET();
                } else {
                    String lsClient = WebFileServer.RequestClientToken("IntegSys", poUser.getClientId(), poUser.getUserID());
                    String lsAccess = WebFileServer.RequestAccessToken(lsClient);

                    if (lsClient.isEmpty() || lsAccess.isEmpty()) {
                        lsResult = AppConstants.LOCAL_EXCEPTION_ERROR("Failed to request generated Client or Access token.");
                    } else {
                        org.json.simple.JSONObject loUpload = WebFileServer.UploadFile(psImgInfo.getFileLoct(),
                                lsAccess,
                                "0102",
                                sTransNox.getValue(),
                                psImgInfo.getImageNme(),
                                poUser.getBranchCode(),
                                "CI",
                                sTransNox.getValue(),
                                "");


                        String lsResponse = (String) loUpload.get("result");
                        lsResult = String.valueOf(loUpload);
                        if (Objects.requireNonNull(lsResponse).equalsIgnoreCase("success")) {
                            String lsTransNo = (String) loUpload.get("sTransNox");
                            poImage.updateImageInfo(lsTransNo, psImgInfo.getTransNox());
                            //UpdateFileNameAndFolder(lsTransNo, psTransNox, pnEntryNox, psFileCode,psFileLoc, psImageName);

                        }

                        Thread.sleep(1000);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                lsResult = AppConstants.LOCAL_EXCEPTION_ERROR(e.getMessage());
            }
            return lsResult;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.onPostResidenceInfo();
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject loJson = new JSONObject(s);
                if (loJson.getString("result").equalsIgnoreCase("success")) {
                    callback.onSuccessResidenceInfo();
                } else {
                    JSONObject loError = loJson.getJSONObject("error");
                    callback.onFailedResidenceInfo(loError.getString("message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
//            this.cancel(true);

        }


    }

}