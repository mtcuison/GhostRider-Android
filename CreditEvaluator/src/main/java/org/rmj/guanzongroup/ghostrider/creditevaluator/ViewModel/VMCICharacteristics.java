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

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.ECIEvaluation;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCIEvaluation;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.etc.SessionManager;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Etc.ViewModelCallBack;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Model.CIDisbursementInfoModel;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Model.CharacterTraitsInfoModel;

import java.text.DecimalFormat;

public class VMCICharacteristics extends AndroidViewModel {
    private static final String TAG = VMCIResidenceInfo.class.getSimpleName();
    private final Application instance;
    private final RCIEvaluation poCI;
    private final ECIEvaluation evaluation;
    private final SessionManager poUser;

    private final MutableLiveData<ECIEvaluation> poCIDetail = new MutableLiveData<>();
    private final MutableLiveData<String> sTransNox = new MutableLiveData<>();



    public VMCICharacteristics(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poCI = new RCIEvaluation(application);
        this.evaluation = new ECIEvaluation();
        this.poUser = new SessionManager(application);
    }

    public interface OnPostCallBack{
        void onStartPost();
        void onSuccessPost(String message);
        void onPostFailed(String message);
    }
    public void setCurrentCIDetail(ECIEvaluation detail) {
        this.poCIDetail.setValue(detail);
    }

    public void setsTransNox(String transNox) {
        this.sTransNox.setValue(transNox);
    }


    public LiveData<ECIEvaluation> getCIByTransNox() {
        return poCI.getAllCIApplication(sTransNox.getValue());
    }

    public boolean saveCICHaracterTraits(CharacterTraitsInfoModel infoModel, OnPostCallBack callback) {
        try {

            new UpdateTask(instance,poCI, infoModel, callback).execute(poCIDetail.getValue());
            return true;
        } catch (NullPointerException e) {
            e.printStackTrace();
//            callback.OnFailedResult(e.getMessage());
            callback.onPostFailed("NullPointerException error");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            callback.onPostFailed("Exception error");
            return false;
        }
    }



    //Added by Jonathan 2021/04/13
    //Need AsyncTask for background threading..
    //RoomDatabase requires background task in order to manipulate Tables...
    private class UpdateTask extends AsyncTask<ECIEvaluation, Void, String> {
        private final RCIEvaluation poCIEvaluation;
        private final CharacterTraitsInfoModel infoModel;
        private final OnPostCallBack callback;
        private final ConnectionUtil poConn;
        private final HttpHeaders poHeaders;
        public UpdateTask(Application instance,RCIEvaluation poCIEvaluation, CharacterTraitsInfoModel infoModel, OnPostCallBack callback) {
            this.poCIEvaluation = poCIEvaluation;
            this.infoModel = infoModel;
            this.callback = callback;
            this.poHeaders = HttpHeaders.getInstance(instance);
            this.poConn = new ConnectionUtil(instance);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.onStartPost();
        }

        @SafeVarargs
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected final String doInBackground(ECIEvaluation... detail) {
            String response = "";
            try {
                if (!infoModel.isValidCharaterData()){
                    return infoModel.getMessage();
                }else{
                    ECIEvaluation loDetail = detail[0];
                    loDetail.setGamblerx(infoModel.getCbGambler());
                    loDetail.setWomanizr(infoModel.getCbWomanizer());
                    loDetail.setHvyBrwer(infoModel.getCbHeavyBrrw());
                    loDetail.setWithRepo(infoModel.getCbRepo());
                    loDetail.setWithMort(infoModel.getCbMortage());
                    loDetail.setArrogant(infoModel.getCbArrogance());
                    loDetail.setOtherBad(infoModel.getCbOthers());
                    loDetail.setRemarksx(infoModel.getsRemarks());
                    loDetail.setTranStat(infoModel.getcTranstat());
                    loDetail.setApproved(AppConstants.DATE_MODIFIED);
                    loDetail.setTimeStmp(AppConstants.DATE_MODIFIED);
                    loDetail.setReceived(AppConstants.DATE_MODIFIED);
                    loDetail.setCredInvx(poUser.getUserID());
                    poCIEvaluation.updateCiDisbursement(loDetail);

                    if (!poConn.isDeviceConnected()) {
                        response = AppConstants.NO_INTERNET();
                    } else {
                        JSONObject loJSON = new JSONObject();
                        loJSON.put("sTransNox", loDetail.getTransNox());
                        loJSON.put("sCredInvx", loDetail.getCredInvx());
                        loJSON.put("sLandMark", loDetail.getLandMark());
                        loJSON.put("cOwnershp", loDetail.getOwnershp());
                        loJSON.put("cOwnOther", loDetail.getOwnOther());
                        loJSON.put("cHouseTyp", loDetail.getHouseTyp());
                        loJSON.put("cGaragexx", loDetail.getGaragexx());
                        loJSON.put("nLatitude", Double.parseDouble(loDetail.getLatitude()));
                        loJSON.put("nLongitud", Double.parseDouble(loDetail.getLongitud()));
                        loJSON.put("cHasOther", loDetail.getHasOther());
                        loJSON.put("cHasRecrd", loDetail.getHasRecrd());
                        loJSON.put("sRemRecrd", loDetail.getRemRecrd());
                        loJSON.put("sNeighbr1", loDetail.getNeighbr1());
                        loJSON.put("sAddress1", loDetail.getAddress1());
                        loJSON.put("sReltnCD1", loDetail.getReltnCD1());
                        loJSON.put("sMobileN1", loDetail.getMobileN1());
                        loJSON.put("cFeedBck1", loDetail.getFeedBck1());
                        loJSON.put("sFBRemrk1", loDetail.getFBRemrk1());
                        loJSON.put("sNeighbr2", loDetail.getNeighbr2());
                        loJSON.put("sAddress2", loDetail.getAddress2());
                        loJSON.put("sReltnCD2", loDetail.getReltnCD2());
                        loJSON.put("sMobileN2", loDetail.getMobileN2());
                        loJSON.put("cFeedBck2", loDetail.getFeedBck2());
                        loJSON.put("sFBRemrk2", loDetail.getFBRemrk2());
                        loJSON.put("sNeighbr3", loDetail.getNeighbr3());
                        loJSON.put("sAddress3", loDetail.getAddress3());
                        loJSON.put("sReltnCD3", loDetail.getReltnCD3());
                        loJSON.put("sMobileN3", loDetail.getMobileN3());
                        loJSON.put("cFeedBck3", loDetail.getFeedBck3());
                        loJSON.put("sFBRemrk3", loDetail.getFBRemrk3());
                        loJSON.put("nWaterBil", Integer.parseInt(loDetail.getWaterBil().replace(",", "")));
                        loJSON.put("nElctrcBl", Integer.parseInt(loDetail.getElctrcBl().replace(",", "")));
                        loJSON.put("nFoodAllw", Integer.parseInt(loDetail.getFoodAllw().replace(",", "")));
                        loJSON.put("nLoanAmtx", Integer.parseInt(loDetail.getLoanAmtx().replace(",", "")));
                        loJSON.put("nEducExpn", Integer.parseInt(loDetail.getEducExpn().replace(",", "")));
                        loJSON.put("nOthrExpn", Integer.parseInt(loDetail.getOthrExpn().replace(",", "")));
                        loJSON.put("cGamblerx", infoModel.getCbGambler());
                        loJSON.put("cWomanizr", infoModel.getCbWomanizer());
                        loJSON.put("cHvyBrwer", infoModel.getCbHeavyBrrw());
                        loJSON.put("cWithRepo", infoModel.getCbRepo());
                        loJSON.put("cWithMort", infoModel.getCbMortage());
                        loJSON.put("cArrogant", infoModel.getCbArrogance());
                        loJSON.put("sOtherBad", infoModel.getCbOthers());
                        loJSON.put("sRemarksx", infoModel.getsRemarks());
                        loJSON.put("cTranStat", infoModel.getcTranstat());
                        loJSON.put("dApproved", loDetail.getApproved());
                        Log.e(TAG, loJSON.toString());
                        Log.e(TAG, "Applicant's character traits info has been updated!");
                        String lsResponse1 = WebClient.httpsPostJSon(WebApi.URL_DCP_SUBMIT, loJSON.toString(), poHeaders.getHeaders());
                        if (lsResponse1 == null) {
                            response = "Server no response.";
                            Log.e(TAG, "Server no response.");
                        } else {
                            JSONObject loResponse = new JSONObject(lsResponse1);

                            String result = loResponse.getString("result");
                            if (result.equalsIgnoreCase("success")) {
                                Log.e(TAG, "Data of TransNox. " + loDetail.getTransNox() + " was uploaded successfully");
                                poCIEvaluation.updateCiDisbursement(loDetail);
//                                    if (loDetail.sRemCodex == null) {
//                                        poDcp.updateCollectionDetailStatusWithRemarks(loDetail.sTransNox, loDetail.nEntryNox, sRemarksx);
//                                    } else {
//                                        poDcp.updateCollectionDetailStatus(loDetail.sTransNox, loDetail.nEntryNox);
//                                    }
                                response = AppConstants.ALL_DATA_SENT();
                            } else {
                                JSONObject loError = loResponse.getJSONObject("error");
                                Log.e(TAG, loError.getString("message"));
                                response = AppConstants.LOCAL_EXCEPTION_ERROR(loError.getString("message"));

                            }
                        }
                    }
                    return response;
                }
            } catch (NullPointerException e){
                return AppConstants.LOCAL_EXCEPTION_ERROR(e.getMessage());
            }catch (Exception e){
                e.printStackTrace();
                return AppConstants.LOCAL_EXCEPTION_ERROR(e.getMessage());
            }
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try{
                JSONObject loJson = new JSONObject(s);
                if(loJson.getString("result").equalsIgnoreCase("success")){
                    callback.onSuccessPost("TransNox : "+sTransNox.getValue() + " has evaluated successfully.");
                } else {
                    JSONObject loError = loJson.getJSONObject("error");
                    callback.onPostFailed(loError.getString("message"));
                }
            } catch (Exception e){
                e.printStackTrace();
            }
//            if (s.equalsIgnoreCase("success")) {
//                callback.onSaveSuccessResult("TransNox : "+sTransNox.getValue() + " has evaluated successfully.");
//            } else {
//                callback.onFailedResult(s);
//            }
        }
    }


}
