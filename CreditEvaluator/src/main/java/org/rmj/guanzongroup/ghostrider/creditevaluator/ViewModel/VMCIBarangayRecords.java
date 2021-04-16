package org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.GRider.Database.Entities.ECIEvaluation;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCIEvaluation;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;
import org.rmj.g3appdriver.etc.SessionManager;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Etc.ViewModelCallBack;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Model.CIBarangayRecordInfoModel;

import java.util.ArrayList;
import java.util.List;


public class VMCIBarangayRecords extends AndroidViewModel {
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

    private List<EImageInfo> imgInfo = new ArrayList<>();
    public VMCIBarangayRecords(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poCI = new RCIEvaluation(application);
        this.evaluation = new ECIEvaluation();
        this.poImage = new RImageInfo(application);
        this.poUser = new SessionManager(application);
    }
    public interface OnSaveNeighbor{
        void onSuccessNeighbor(String args);
        void onFailedNeighbor(String message);
    }

    public void setCurrentCIDetail(ECIEvaluation detail){
        this.poCIDetail.setValue(detail);
    }
    public void setsTransNox(String transNox){
        this.sTransNox.setValue(transNox);
    }

    public LiveData<ECIEvaluation> getCIByTransNox(String transNox){
        return poCI.getAllCIApplication(transNox);
    }
    public boolean saveNeighbor(CIBarangayRecordInfoModel infoModel, String btnText, ViewModelCallBack callback) {
        try {

            new UpdateTask(poCI, infoModel, btnText, callback).execute(poCIDetail.getValue());
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
    private  class UpdateTask extends AsyncTask<ECIEvaluation, Void, String> {
        private final RCIEvaluation poCIEvaluation;
        private final CIBarangayRecordInfoModel infoModel;
        private final ViewModelCallBack callback;
        private final String btnText;

        public UpdateTask(RCIEvaluation poCIEvaluation, CIBarangayRecordInfoModel infoModel,String btnText,ViewModelCallBack callback) {
            this.poCIEvaluation = poCIEvaluation;
            this.infoModel = infoModel;
            this.callback = callback;
            this.btnText = btnText;
        }

        @Override
        protected String doInBackground(ECIEvaluation... detail) {
            try {

                    if(btnText.equalsIgnoreCase("Neighbor1")){
                        if (!infoModel.isValidNeigbor1()) {
                            return infoModel.getMessage();
                        } else {
                            ECIEvaluation loDetail = detail[0];
                            loDetail.setNeighbr1(infoModel.getNeighbr1());
                            loDetail.setReltnCD1(infoModel.getReltnCD1());
                            loDetail.setFeedBck1(infoModel.getFeedBck1());
                            loDetail.setFBRemrk1(infoModel.getFBRemrk1());
                            loDetail.setMobileN1(infoModel.getMobileN1());
                            poCIEvaluation.updateCiNeighbor1(loDetail);
                        }
                    }else if(btnText.equalsIgnoreCase("Neighbor2")){
                        if (!infoModel.isValidNeigbor2()) {
                            return infoModel.getMessage();
                        } else {
                            ECIEvaluation loDetail = detail[0];
                            loDetail.setNeighbr2(infoModel.getNeighbr2());
                            loDetail.setReltnCD2(infoModel.getReltnCD2());
                            loDetail.setFeedBck2(infoModel.getFeedBck2());
                            loDetail.setFBRemrk2(infoModel.getFBRemrk2());
                            loDetail.setMobileN2(infoModel.getMobileN2());
                            poCIEvaluation.updateCiNeighbor2(loDetail);
                        }
                    }else if(btnText.equalsIgnoreCase("Neighbor3")){
                        if (!infoModel.isValidNeigbor3()) {
                            return infoModel.getMessage();
                            } else {
                            ECIEvaluation loDetail = detail[0];
                            loDetail.setNeighbr3(infoModel.getNeighbr3());
                            loDetail.setReltnCD3(infoModel.getReltnCD3());
                            loDetail.setFeedBck3(infoModel.getFeedBck3());
                            loDetail.setFBRemrk3(infoModel.getFBRemrk3());
                            loDetail.setMobileN3(infoModel.getMobileN3());
                            poCIEvaluation.updateCiNeighbor3(loDetail);
                        }
                    }else {
                        if (!infoModel.isValidNeighbor()) {
                            return infoModel.getMessage();
                        }else{
                            ECIEvaluation loDetail = detail[0];
                            loDetail.setRemRecrd(infoModel.getRemRecrd());
                            loDetail.setHasRecrd(infoModel.getHasRecrd());
                            poCIEvaluation.updateCiNeighbor(loDetail);
                        }
                    }
                    return "success";

            } catch (Exception e){
                e.printStackTrace();
                return e.getMessage();
            }
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equalsIgnoreCase("success")){
                callback.onSaveSuccessResult(btnText);
            } else {
                callback.onFailedResult(s);
            }
        }
    }


}