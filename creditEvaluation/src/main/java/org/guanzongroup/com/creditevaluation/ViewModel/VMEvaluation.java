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
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditOnlineApplicationCI;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EOccupationInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.ROccupation;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;
import org.rmj.g3appdriver.utils.ConnectionUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VMEvaluation extends AndroidViewModel {
    private static final String TAG = VMEvaluation.class.getSimpleName();
    private final Application app;
    private final EmployeeMaster poUser;
    private final EvaluatorManager foManager;
    private final ROccupation poJob;
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
        this.poUser = new EmployeeMaster(application);
        this.poImage = new RImageInfo(application);
        this.foManager = new EvaluatorManager(application);
        this.poJob = new ROccupation(application);
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
    public interface onPostCIEvaluation {
        void OnPost();
        void OnSuccessPost(String args);
        void OnFailedPost(String message);
    }

    public LiveData<ECreditOnlineApplicationCI> getCIEvaluation(String transNox){
        return foManager.getApplications(transNox);
    }

    public LiveData<List<EOccupationInfo>> GetOccupationList(){
        return poJob.getAllOccupationInfo();
    }

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
//                    poImage.updateImageInfo(foImage);
                } else {
//                    foImage.setTransNox(poImage.getImageNextCode());
//                    poImage.insertImageInfo(foImage);
                }
            }else{
//                foImage.setTransNox(poImage.getImageNextCode());
//                poImage.insertImageInfo(foImage);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveAdditionalInfo(AdditionalInfoModel infoModel, String value, onSaveAdditionalInfo callback){
        new UpdateTask(app,foManager,infoModel,value, callback).execute(TransNox.getValue());
    }

    public void UploadEvaluationResult(onPostCIEvaluation callback){
        new  UploadEvaluationResultTask(app,foManager, callback).execute(TransNox.getValue());
    }

    public void saveDataEvaluation(oParentFndg parentFndg,oChildFndg childFndg, onSaveAdditionalInfo callback){
        new UpdateDataEvaluationTask(app,foManager,parentFndg,childFndg, callback).execute(TransNox.getValue());
    }
    public void postCIEvaluation(onPostCIEvaluation callback){
        new  PostEvaluationResultTask(app,foManager, callback).execute(TransNox.getValue());
    }
    private  class UpdateTask extends AsyncTask<String, Void, String> {
        private final EvaluatorManager poCIEvaluation;
        private final AdditionalInfoModel infoModel;
        private final onSaveAdditionalInfo callback;
        private final String btnText;
        private final ConnectionUtil poConn;
        public UpdateTask(Application app,EvaluatorManager poCIEvaluation, AdditionalInfoModel infoModel,String btnText,onSaveAdditionalInfo callback) {
            this.poCIEvaluation = poCIEvaluation;
            this.infoModel = infoModel;
            this.callback = callback;
            this.btnText = btnText;
            this.poConn = new ConnectionUtil(app);
        }

        @Override
        protected String doInBackground(String... transNox) {
            try {
                final String[] response = {""};
                if(btnText.equalsIgnoreCase("Additional")){
                    if(infoModel.isBrgyRecordValid()){
                        poCIEvaluation.UpdateRecordInfo(transNox[0],infoModel.getHasRecrd());
                        poCIEvaluation.UpdateRecordRemarks(transNox[0],infoModel.getRemRecrd());

                        poCIEvaluation.UpdatePresentBarangay(transNox[0],infoModel.getAsstPersonnel());
                        poCIEvaluation.UpdatePosition(transNox[0],infoModel.getAsstPosition());
                        poCIEvaluation.UpdateContact(transNox[0],infoModel.getMobileNo());
                        response[0] = "Barangay Records and Personnel Info successfully saved.";
                    }else{
                        response[0] = infoModel.getMessage();
                    }
                } else if(btnText.equalsIgnoreCase("Approval")){
                    poCIEvaluation.SaveCIApproval(transNox[0], infoModel.getTranstat(), infoModel.getsRemarks(), new EvaluatorManager.OnActionCallback() {
                        @Override
                        public void OnSuccess(String args) {
                            response[0] = "success";
                        }

                        @Override
                        public void OnFailed(String message) {
                            response[0] = message;
                        }
                    });
                    Thread.sleep(1000);
                }
                else if(btnText.equalsIgnoreCase("Neighbor1")){
                    if (!infoModel.isNeighbr1()) {
                        response[0] = infoModel.getMessage();
                    } else {
                        poCIEvaluation.UpdateNeighbor1(transNox[0],infoModel.getNeighbr1());
                        response[0] = "Neighbor 1 Info successfully saved.";
                    }
                }else if(btnText.equalsIgnoreCase("Neighbor2")){
                    if (!infoModel.isNeighbr2()) {
                        response[0] = infoModel.getMessage();
                    } else {
                        poCIEvaluation.UpdateNeighbor2(transNox[0],infoModel.getNeighbr2());
                        response[0] = "Neighbor 2 Info successfully saved.";
                    }
                }else if(btnText.equalsIgnoreCase("Neighbor3")){
                    if (!infoModel.isNeighbr3()) {
                        response[0] = infoModel.getMessage();
                    } else {
                        poCIEvaluation.UpdateNeighbor3(transNox[0],infoModel.getNeighbr3());
                        response[0] = "Neighbor 3 Info successfully saved.";
                    }
                }
                return response[0];

            } catch (Exception e){
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
        private final ConnectionUtil poConn;

        public UpdateDataEvaluationTask(Application app, EvaluatorManager poCIEvaluation, oParentFndg parentFndg,oChildFndg childFndg,onSaveAdditionalInfo callback) {
            this.poCIEvaluation = poCIEvaluation;
            this.parentFndg = parentFndg;
            this.callback = callback;
            this.childFndg = childFndg;
            this.poConn = new ConnectionUtil(app);
        }

        @Override
        protected String doInBackground(String... transNox) {
            try {
                final String[] response = {""};
                poCIEvaluation.UpdateConfirmInfos(transNox[0], parentFndg, childFndg, new EvaluatorManager.OnActionCallback() {
                    @Override
                    public void OnSuccess(String args) {
                        response[0] = "success";
                        Log.e("save success", args);
                    }

                    @Override
                    public void OnFailed(String message) {
                        response[0] = message;
                        Log.e("save failed", message);

                    }
                });

                return response[0];

            } catch (Exception e){
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

    private  class UploadEvaluationResultTask extends AsyncTask<String, Void, String> {
        private final EvaluatorManager poCIEvaluation;
        private final onPostCIEvaluation callback;
        private final ConnectionUtil poConn;
        public UploadEvaluationResultTask(Application app, EvaluatorManager poCIEvaluation,onPostCIEvaluation callback) {
            this.poCIEvaluation = poCIEvaluation;
            this.callback = callback;
            this.poConn = new ConnectionUtil(app);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnPost();
        }
        @Override
        protected String doInBackground(String... transNox) {
            try {
                final String[] response = {""};
                if (!poConn.isDeviceConnected()) {
                    response[0] = AppConstants.NO_INTERNET();
                } else {
                    poCIEvaluation.UploadEvaluationResult(transNox[0], new EvaluatorManager.OnActionCallback() {
                        @Override
                        public void OnSuccess(String args) {
                            response[0] = "success";
                            Log.e("upload success upload", args);
                        }

                        @Override
                        public void OnFailed(String message) {
                            response[0] =  message;
                            Log.e("upload failed upload", message);
                        }
                    });

                }
                Thread.sleep(1000);
                return response[0];

            } catch (Exception e){
                e.printStackTrace();
                return e.getMessage();
            }
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equalsIgnoreCase("success")){
                callback.OnSuccessPost("Credit evaluation has been posted successfully");
            } else {
                callback.OnFailedPost(s);
            }
        }
    }

    private  class PostEvaluationResultTask extends AsyncTask<String, Void, String> {
        private final EvaluatorManager poCIEvaluation;
        private final onPostCIEvaluation callback;
        private final ConnectionUtil poConn;
        public PostEvaluationResultTask(Application app, EvaluatorManager poCIEvaluation,onPostCIEvaluation callback) {
            this.poCIEvaluation = poCIEvaluation;
            this.callback = callback;
            this.poConn = new ConnectionUtil(app);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnPost();
        }

        @Override
        protected String doInBackground(String... transNox) {
            try {
                final String[] response = {""};
                if (!poConn.isDeviceConnected()) {
                    response[0] = "No internet connection.";
                } else {
                    poCIEvaluation.PostCIApproval(transNox[0], new EvaluatorManager.OnActionCallback() {
                        @Override
                        public void OnSuccess(String args) {
                            response[0] = "success";
                            Log.e("upload success upload", args);
                        }

                        @Override
                        public void OnFailed(String message) {
                            response[0] =  message;
                            Log.e("upload failed upload", message);
                        }
                    });

                }
                Thread.sleep(1000);
                return response[0];

            } catch (Exception e){
                e.printStackTrace();
                return e.getMessage();
            }
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equalsIgnoreCase("success")){
                callback.OnSuccessPost("Approval sent successfully.");
            } else {
                callback.OnFailedPost(s);
            }
        }
    }

    public void SaveImageInfo(EImageInfo foImage, boolean isPrimary, EvaluatorManager.OnActionCallback callback){
        SaveImageInfoTask loTask = new SaveImageInfoTask(app, callback);
        loTask.setTransNox(TransNox.getValue());
        loTask.setFoImage(foImage);
        loTask.setPrimary(isPrimary);
        loTask.execute();
    }

    private class SaveImageInfoTask extends AsyncTask<String, Void, String>{
        private final Application instance;
        private final EvaluatorManager poCIEvaluation;
        private final EvaluatorManager.OnActionCallback callback;

        private String TransNox;
        private EImageInfo foImage;
        private String message;
        private boolean isPrimary;

        private SaveImageInfoTask(Application instance, EvaluatorManager.OnActionCallback callback) {
            this.instance = instance;
            this.poCIEvaluation = new EvaluatorManager(instance);
            this.callback = callback;
        }

        public void setTransNox(String transNox) {
            TransNox = transNox;
        }

        public void setFoImage(EImageInfo foImage) {
            this.foImage = foImage;
        }

        public void setPrimary(boolean primary) {
            isPrimary = primary;
        }

        @Override
        protected String doInBackground(String... strings) {
            poCIEvaluation.SaveImageInfo(TransNox,
                    foImage,
                    isPrimary, new EvaluatorManager.OnActionCallback() {
                        @Override
                        public void OnSuccess(String args) {
                            message = args;
                        }

                        @Override
                        public void OnFailed(String mssage) {
                            message = mssage;
                        }
                    });
            return message;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(message.equalsIgnoreCase("success")){
                callback.OnSuccess("Approval sent successfully.");
            } else {
                callback.OnSuccess(s);
            }
        }
    }

    public void SaveEvaluationResult(String fsParnt, String fsKEyxx, String fsValue, onSaveAdditionalInfo callback){
        new SaveEvaluationTask(callback).execute(fsParnt, fsKEyxx, fsValue);
    }

    private class SaveEvaluationTask extends AsyncTask<String, Void, Boolean>{
        private final EvaluatorManager poCIEvaluation;
        private final onSaveAdditionalInfo callback;
        private String message;

        private SaveEvaluationTask(onSaveAdditionalInfo callback) {
            this.callback = callback;
            this.poCIEvaluation = new EvaluatorManager(app);
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                String lsTransNox = TransNox.getValue();
                Log.d(TAG, "Updating result : " + lsTransNox);
                boolean isSuccess = poCIEvaluation.SaveCIResult(lsTransNox, strings[0], strings[1], strings[2]);
                if (!isSuccess) {
                    message = poCIEvaluation.getMessage();
                    return false;
                } else {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                message = e.getMessage();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean){
                callback.OnSuccessResult("", "");
            } else {
                callback.OnFailedResult(message);
            }
        }
    }

    public void SaveAddressResult(String fsPar, String fsAlttude, String fsLongtde, onSaveAdditionalInfo callback){
        new SaveAddressTask(callback).execute(fsPar, fsAlttude, fsLongtde);
    }

    private class SaveAddressTask extends AsyncTask<String, Void, Boolean>{

        private final EvaluatorManager poCIEvaluation;
        private final onSaveAdditionalInfo callback;
        private String message;

        private SaveAddressTask(onSaveAdditionalInfo callback) {
            this.callback = callback;
            this.poCIEvaluation = new EvaluatorManager(app);
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                String lsTransNox = TransNox.getValue();
                Log.d(TAG, "Updating result : " + lsTransNox);
                boolean isSuccess = poCIEvaluation.SaveAddressLocation(lsTransNox, strings[0], strings[1], strings[2]);
                if (!isSuccess) {
                    message = poCIEvaluation.getMessage();
                    return false;
                } else {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                message = e.getMessage();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean){
                callback.OnSuccessResult("", "");
            } else {
                callback.OnFailedResult(message);
            }
        }
    }
}
