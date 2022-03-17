package org.guanzongroup.com.creditevaluation.ViewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.guanzongroup.com.creditevaluation.Core.EvaluatorManager;
import org.guanzongroup.com.creditevaluation.Core.oChildFndg;
import org.guanzongroup.com.creditevaluation.Core.oParentFndg;
import org.guanzongroup.com.creditevaluation.Model.AdditionalInfoModel;
import org.rmj.g3appdriver.GRider.Database.Entities.ECIEvaluation;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditOnlineApplicationCI;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCIEvaluation;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VMEvaluation extends AndroidViewModel {
    private static final String TAG = VMEvaluation.class.getSimpleName();
    private final Application app;
    private final REmployee poUser;
    private final EvaluatorManager foManager;
    private MutableLiveData<HashMap<oParentFndg, List<oChildFndg>>> poEvaluate = new MutableLiveData<>();
    private MutableLiveData<ECreditOnlineApplicationCI> poData = new MutableLiveData<>();
    private MutableLiveData<String> TransNox = new MutableLiveData<>();
    private MutableLiveData<String> records = new MutableLiveData<>();
    private MutableLiveData<String> recordRemarks = new MutableLiveData<>();
    private MutableLiveData<ECreditOnlineApplicationCI> evaluationCI = new MutableLiveData<>();
    private MutableLiveData<HashMap<oParentFndg, List<oChildFndg>>> foEvaluate = new MutableLiveData<>();

    private List<EImageInfo> imgInfo = new ArrayList<>();
    private final RImageInfo poImage;
    public VMEvaluation(@NonNull Application application) {
        super(application);
        this.app = application;
        this.poUser = new REmployee(application);
        this.poImage = new RImageInfo(application);
        this.foManager = new EvaluatorManager(application);
        this.records.setValue("-1");
        this.recordRemarks.setValue("");
    }
    public void setRecords(String record){
        this.records.setValue(record);
    }
    public LiveData<String> getRecord(){
        return records;
    }
    public void setRecordRemarks(String val){
        this.recordRemarks.setValue(val);
    }
    public void setTransNox(String transNox){
        this.TransNox.setValue(transNox);
    }
    public String getTransNox(){
        return this.TransNox.getValue();
    }
    public interface onSaveAdditionalInfo {
        void OnSuccessResult(String args,String message);
        void OnFailedResult(String message);
    }
    public LiveData<ECreditOnlineApplicationCI> getCIEvaluation(String transNox){
        return foManager.getApplications(transNox);
    }

    public interface ORetrieveCallBack{
        void onRetrieveSuccess(HashMap<oParentFndg, List<oChildFndg>> foEvaluate, ECreditOnlineApplicationCI foData);
        void onRetrieveFailed(String message);
    }
    public void parseToEvaluationData(ECreditOnlineApplicationCI eCI) throws Exception {
        foEvaluate.setValue(foManager.parseToEvaluationData(eCI));
    }
    public LiveData<HashMap<oParentFndg, List<oChildFndg>>> getParsedEvaluationData(){
        return foEvaluate;
    }
//    public HashMap<oParentFndg, List<oChildFndg>> parseToEvaluationData(ECreditOnlineApplicationCI eCI){
//        try {
//            return foManager.parseToEvaluationData(eCI);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
    public void saveResidenceImageInfo(EImageInfo foImage) {
        try {
            boolean isImgExist = false;
            String tansNo = "";
            if(poImage.getAllImageInfo().getValue() != null){
                for (int i = 0; i < poImage.getAllImageInfo().getValue().size(); i++) {
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
            }else{
                foImage.setTransNox(poImage.getImageNextCode());
                poImage.insertImageInfo(foImage);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveAdditionalInfo(AdditionalInfoModel infoModel,String value, onSaveAdditionalInfo callback){
        new UpdateTask(foManager,infoModel,value, callback).execute(TransNox.getValue());
    }

    public void saveDataEvaluation(oParentFndg parentFndg,oChildFndg childFndg, onSaveAdditionalInfo callback){
        new UpdateDataEvaluationTask(foManager,parentFndg,childFndg, callback).execute(TransNox.getValue());
    }
    private  class UpdateTask extends AsyncTask<String, Void, String> {
        private final EvaluatorManager poCIEvaluation;
        private final AdditionalInfoModel infoModel;
        private final onSaveAdditionalInfo callback;
        private final String btnText;

        public UpdateTask(EvaluatorManager poCIEvaluation, AdditionalInfoModel infoModel,String btnText,onSaveAdditionalInfo callback) {
            this.poCIEvaluation = poCIEvaluation;
            this.infoModel = infoModel;
            this.callback = callback;
            this.btnText = btnText;
        }

        @Override
        protected String doInBackground(String... transNox) {
            try {
                if(btnText.equalsIgnoreCase("Neighbor1")){
                    if (!infoModel.isNeighbr1()) {
                        return infoModel.getMessage();
                    } else {
                        poCIEvaluation.UpdateNeighbor1(transNox[0],infoModel.getNeighbr1());
                    }
                }else if(btnText.equalsIgnoreCase("Neighbor2")){
                    if (!infoModel.isNeighbr2()) {
                        return infoModel.getMessage();
                    } else {
                        poCIEvaluation.UpdateNeighbor2(transNox[0],infoModel.getNeighbr2());
                    }
                }else if(btnText.equalsIgnoreCase("Neighbor3")){
                    if (!infoModel.isNeighbr3()) {
                        return infoModel.getMessage();
                    } else {
                        poCIEvaluation.UpdateNeighbor3(transNox[0],infoModel.getNeighbr3());
                    }
                }
                else if(btnText.equalsIgnoreCase("Personnel")){
                    poCIEvaluation.UpdatePresentBarangay(transNox[0],infoModel.getAsstPersonnel());

                }
                else if(btnText.equalsIgnoreCase("Position")){
                    poCIEvaluation.UpdatePosition(transNox[0],infoModel.getAsstPosition());

                }
                else if(btnText.equalsIgnoreCase("PhoneNo")){
                    if (!infoModel.isMobileNo()){
                        return infoModel.getMessage();
                    }else {
                        poCIEvaluation.UpdateContact(transNox[0],infoModel.getMobileNo());
                    }
                }
                else if(btnText.equalsIgnoreCase("Record")){
                    if (!infoModel.isHasRecord()){
                        return infoModel.getMessage();
                    }else {
                        poCIEvaluation.UpdateRecordInfo(transNox[0],infoModel.getHasRecrd());
                        poCIEvaluation.UpdateRecordRemarks(transNox[0],infoModel.getRemRecrd());
                    }
                }
                else if(btnText.equalsIgnoreCase("Approval")){
                    poCIEvaluation.SaveCIApproval(transNox[0], infoModel.getTranstat(), infoModel.getsRemarks(), new EvaluatorManager.OnActionCallback() {
                        @Override
                        public void OnSuccess(String args) {
//                                callback.OnSuccessResult("CI approval/disapproval successfully saved.");
                        }

                        @Override
                        public void OnFailed(String message) {
//                                callback.OnFailedResult(message);
                        }
                    });
                }
                return "success";

            } catch (NullPointerException e){
                e.printStackTrace();
                return e.getMessage();
            }catch (Exception e){
                e.printStackTrace();
                return e.getMessage();
            }
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equalsIgnoreCase("success")){
                callback.OnSuccessResult(btnText,infoModel.getMessage());
            } else {
                callback.OnFailedResult(s);
            }
        }
    }
    private  class UpdateDataEvaluationTask extends AsyncTask<String, Void, String> {
        private final EvaluatorManager poCIEvaluation;
        private final oParentFndg parentFndg;
        private final onSaveAdditionalInfo callback;
        private final oChildFndg childFndg;

        public UpdateDataEvaluationTask(EvaluatorManager poCIEvaluation, oParentFndg parentFndg,oChildFndg childFndg,onSaveAdditionalInfo callback) {
            this.poCIEvaluation = poCIEvaluation;
            this.parentFndg = parentFndg;
            this.callback = callback;
            this.childFndg = childFndg;
        }

        @Override
        protected String doInBackground(String... transNox) {
            try {
                poCIEvaluation.UpdateConfirmInfos(transNox[0], parentFndg, childFndg, new EvaluatorManager.OnActionCallback() {
                    @Override
                    public void OnSuccess(String args) {
                        Log.e("save success", args);
                    }

                    @Override
                    public void OnFailed(String message) {
                        Log.e("save failed", message);

                    }
                });
                return "success";

            } catch (NullPointerException e){
                e.printStackTrace();
                return e.getMessage();
            }catch (Exception e){
                e.printStackTrace();
                return e.getMessage();
            }
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equalsIgnoreCase("success")){
                callback.OnSuccessResult(s,s);
            } else {
                callback.OnFailedResult(s);
            }
        }
    }

}
