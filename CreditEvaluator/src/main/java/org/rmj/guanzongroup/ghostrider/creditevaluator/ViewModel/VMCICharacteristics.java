package org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.ECIEvaluation;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCIEvaluation;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;
import org.rmj.g3appdriver.etc.SessionManager;
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


    public void setCurrentCIDetail(ECIEvaluation detail) {
        this.poCIDetail.setValue(detail);
    }

    public void setsTransNox(String transNox) {
        this.sTransNox.setValue(transNox);
    }


    public LiveData<ECIEvaluation> getCIByTransNox(String transNox) {
        return poCI.getAllCIApplication(transNox);
    }

    public boolean saveCICHaracterTraits(CharacterTraitsInfoModel infoModel, ViewModelCallBack callback) {
        try {

            new UpdateTask(poCI, infoModel, callback).execute(poCIDetail.getValue());
            return true;
        } catch (NullPointerException e) {
            e.printStackTrace();
//            callback.OnFailedResult(e.getMessage());
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
    private class UpdateTask extends AsyncTask<ECIEvaluation, Void, String> {
        private final RCIEvaluation poCIEvaluation;
        private final CharacterTraitsInfoModel infoModel;
        private final ViewModelCallBack callback;

        public UpdateTask(RCIEvaluation poCIEvaluation, CharacterTraitsInfoModel infoModel, ViewModelCallBack callback) {
            this.poCIEvaluation = poCIEvaluation;
            this.infoModel = infoModel;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(ECIEvaluation... detail) {
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
                    loDetail.setApproved(AppConstants.DATE_MODIFIED);
                    loDetail.setTimeStmp(AppConstants.DATE_MODIFIED);
                    loDetail.setReceived(AppConstants.DATE_MODIFIED);
                    poCIEvaluation.updateCiDisbursement(loDetail);
                    Log.e(TAG, "CI Residence info has been updated!");

                    return "success";
                }
            } catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equalsIgnoreCase("success")) {
                callback.onSaveSuccessResult("Character traits info has been save.");
            } else {
                callback.onFailedResult(s);
            }
        }
    }


}
