package org.guanzongroup.com.creditevaluation.ViewModel;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DEmployeeInfo;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.ECreditOnlineApplicationCI;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EOccupationInfo;
import org.rmj.g3appdriver.dev.Database.GCircle.Repositories.ROccupation;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.lib.Location.LocationRetriever;
import org.rmj.g3appdriver.lib.integsys.CreditInvestigator.pojo.BarangayRecord;
import org.rmj.g3appdriver.lib.integsys.CreditInvestigator.pojo.CIImage;
import org.rmj.g3appdriver.lib.integsys.CreditInvestigator.Obj.CITagging;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.etc.ImageFileCreator;

import java.util.List;

public class VMEvaluation extends AndroidViewModel {
    private static final String TAG = VMEvaluation.class.getSimpleName();

    private final Application instance;

    private final CITagging poSys;
    private final ROccupation poJob;

    private final ConnectionUtil poConn;

    public interface OnValidateTaggingResult{
        void OnValid();
        void OnInvalid(String message);
    }

    public interface OnSaveCIResultListener {
        void OnSuccess();
        void OnError(String message);
    }

    public interface OnSaveAddressResult {
        void OnSuccess(String args, String args1, String args2);
        void OnError(String message);
    }

    public interface OnUpdateOtherDetail{
        void OnSuccess();
        void OnError(String message);
    }

    public interface OnUploadResultListener{
        void OnUpload();
        void OnSuccess();
        void OnFailed(String message);
    }

    public interface OnPostRecommendationCallback{
        void OnPost();
        void OnSuccess();
        void OnFailed(String message);
    }

    public VMEvaluation(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poSys = new CITagging(application);
        this.poJob = new ROccupation(application);
        this.poConn = new ConnectionUtil(application);
    }

    public LiveData<DEmployeeInfo.EmployeeBranch> GetUserInfo(){
        return poSys.GetUserInfo();
    }

    public LiveData<ECreditOnlineApplicationCI> GetApplicationDetail(String fsVal){
        return poSys.GetApplicationDetail(fsVal);
    }

    public LiveData<List<EOccupationInfo>> GetOccupationList(){
        return poJob.getAllOccupationInfo();
    }

    public void ValidateTagging(String TransNox, String fsKeyxx, List<String> foList, OnValidateTaggingResult listener){
        new ValidateTaggingTask(TransNox, fsKeyxx, listener).execute(foList);
    }

    private class ValidateTaggingTask extends AsyncTask<List<String>, Void, Boolean>{

        private final OnValidateTaggingResult mListener;
        private final String TransNox;
        private final String fsKeyxx;

        private String message;

        public ValidateTaggingTask(String TransNox, String fsKeyxx, OnValidateTaggingResult listener) {
            this.TransNox = TransNox;
            this.fsKeyxx = fsKeyxx;
            this.mListener = listener;
        }

        @Override
        protected Boolean doInBackground(List<String>... lists) {

            if(!poSys.ValidateTagging(TransNox, fsKeyxx, lists[0])){
                message = poSys.getMessage();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if(!isSuccess){
                mListener.OnInvalid(message);
            } else {
                mListener.OnValid();
            }
        }
    }

    public void SaveCIResult(String args, String fsPar, String fsKey, String fsRes, List<String> foList, OnSaveCIResultListener listener){
        new SaveCIResult(foList, listener).execute(args, fsPar, fsKey, fsRes);
    }

    private class SaveCIResult extends AsyncTask<String, Void, Boolean>{

        private final OnSaveCIResultListener listener;

        private String message;

        public SaveCIResult(List<String> foList, OnSaveCIResultListener listener) {
            this.listener = listener;
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            try{
                String TransNox = strings[0];
                String Parentxx = strings[1];
                String KeyNamex = strings[2];
                String Resultxx = strings[3];


                if (!poSys.SaveCIResult(TransNox, Parentxx, KeyNamex, Resultxx)) {
                    message = poSys.getMessage();
                    return false;
                }

                return true;
            } catch (Exception e){
                e.printStackTrace();
                message = e.getMessage();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if(!isSuccess){
                listener.OnError(message);
            } else {
                listener.OnSuccess();
            }
        }
    }

    public void SaveBarangayRecord(BarangayRecord foVal, OnUpdateOtherDetail callback){
        new SaveBarangayRecordCallback(callback).execute(foVal);
    }

    private class SaveBarangayRecordCallback extends AsyncTask<BarangayRecord, Void, Boolean>{

        private final OnUpdateOtherDetail callback;

        private String message;

        public SaveBarangayRecordCallback(OnUpdateOtherDetail callback) {
            this.callback = callback;
        }

        @Override
        protected Boolean doInBackground(BarangayRecord... record) {
            try{
                if(!poSys.UpdateBarangayRecord(record[0])){
                    message = poSys.getMessage();
                    return false;
                }

                return true;
            } catch (Exception e){
                e.printStackTrace();
                message = e.getMessage();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if(!isSuccess){
                callback.OnError(message);
            } else {
                callback.OnSuccess();
            }
        }
    }

    public void UpdateOtherDetail(String args, String args1, String args2, OnUpdateOtherDetail callback){
        new UpdateOtherDetailTask(callback).execute(args, args1, args2);
    }

    private class UpdateOtherDetailTask extends AsyncTask<String, Void, Boolean>{

        private final OnUpdateOtherDetail callback;

        private String message;

        public UpdateOtherDetailTask(OnUpdateOtherDetail callback) {
            this.callback = callback;
        }

        @Override
        protected Boolean doInBackground(String... args) {
            try {
                String lsTransN = args[0];
                String lsUpdate = args[1];
                String lsValuex = args[2];
                switch (lsUpdate) {
                    case "n1":
                        poSys.UpdateNeighbor1(lsTransN, lsValuex);
                        break;
                    case "n2":
                        poSys.UpdateNeighbor2(lsTransN, lsValuex);
                        break;
                    case "n3":
                        poSys.UpdateNeighbor3(lsTransN, lsValuex);
                        break;
                }
                return true;
            } catch (Exception e){
                e.printStackTrace();
                message = e.getMessage();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if(!isSuccess){
                callback.OnError(message);
            } else {
                callback.OnSuccess();
            }
        }
    }

    public void UploadResult(String args, OnUploadResultListener listener){
        new UploadResultTask(listener).execute(args);
    }

    private class UploadResultTask extends AsyncTask<String, Void, Boolean>{

        private final OnUploadResultListener listener;

        private String message;

        public UploadResultTask(OnUploadResultListener listener) {
            this.listener = listener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listener.OnUpload();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            try{
                if(!poConn.isDeviceConnected()){
                    message = poConn.getMessage();
                    return false;
                }

                if(!poSys.UploadEvaluationResult(strings[0])){
                    message = poSys.getMessage();
                    return false;
                }

                return true;
            } catch (Exception e){
                e.printStackTrace();
                message = e.getMessage();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if(!isSuccess){
                listener.OnFailed(message);
            } else {
                listener.OnSuccess();
            }
        }
    }

    public void SaveRecommendation(String args, String args1, String args2, OnPostRecommendationCallback callback){
        new PostRecommendationTask(callback).execute(args, args1, args2);
    }

    private class PostRecommendationTask extends AsyncTask<String, Void, Boolean>{

        private final OnPostRecommendationCallback callback;

        private String message;

        public PostRecommendationTask(OnPostRecommendationCallback callback) {
            this.callback = callback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnPost();
        }

        @Override
        protected Boolean doInBackground(String... args) {
            try{
                String TransNox = args[0];
                String cApprove = args[1];
                String sRemarks = args[2];
                if(!poSys.SaveCIApproval(TransNox, cApprove, sRemarks)){
                    message = poSys.getMessage();
                    return false;
                }

                if(!poConn.isDeviceConnected()){
                    message = poConn.getMessage();
                    return false;
                }

                if(!poSys.PostCIApproval(args[0])){
                    message = poSys.getMessage();
                    return false;
                }
                return true;
            } catch (Exception e){
                e.printStackTrace();
                message = e.getMessage();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if(!isSuccess){
                callback.OnFailed(message);
            } else {
                callback.OnSuccess();
            }
        }
    }

    public void InitCameraLaunch(Activity activity, String TransNox, OnInitializeCameraCallback callback){
        new InitializeCameraTask(activity, TransNox, instance, callback).execute();
    }

    private static class InitializeCameraTask extends AsyncTask<String, Void, Boolean>{

        private final Activity activity;
        private final Application instance;
        private final OnInitializeCameraCallback callback;
        private final ImageFileCreator loImage;

        private Intent loIntent;
        private String[] args = new String[4];
        private String message;

        public InitializeCameraTask(Activity activity, String TransNox, Application instance, OnInitializeCameraCallback callback){
            this.activity = activity;
            this.instance = instance;
            this.callback = callback;
            this.loImage = new ImageFileCreator(instance, AppConstants.SUB_FOLDER_CI_ADDRESS, TransNox);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnInit();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            if(!loImage.IsFileCreated(false)){
                message = loImage.getMessage();
                return false;
            } else {
                LocationRetriever loLrt = new LocationRetriever(instance, activity);
                if(loLrt.HasLocation()){
                    args[0] = loImage.getFilePath();
                    args[1] = loImage.getFileName();
                    args[2] = loLrt.getLatitude();
                    args[3] = loLrt.getLongitude();
                    loIntent = loImage.getCameraIntent();
                    return true;
                } else {
                    args[0] = loImage.getFilePath();
                    args[1] = loImage.getFileName();
                    args[2] = loLrt.getLatitude();
                    args[3] = loLrt.getLongitude();
                    loIntent = loImage.getCameraIntent();
                    message = loLrt.getMessage();
                    return false;
                }
            }
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if(isSuccess){
                callback.OnSuccess(loIntent, args);
            } else {
                callback.OnFailed(message, loIntent, args);
            }
        }
    }

    public void SaveAddressImage(CIImage foVal, OnSaveAddressResult listener){
        new SaveAddressImageTask(listener).execute(foVal);
    }

    private class SaveAddressImageTask extends AsyncTask<CIImage, Void, Boolean>{

        private final OnSaveAddressResult listener;

        private String message;

        private String args, args1, args2;

        public SaveAddressImageTask(OnSaveAddressResult listener) {
            this.listener = listener;
        }

        @Override
        protected Boolean doInBackground(CIImage... ciImages) {
            try{
                if(!poSys.SaveImageInfo(ciImages[0])){
                    message = poSys.getMessage();
                    return false;
                }
                args = ciImages[0].getsParentxx();
                args1 = ciImages[0].getsKeyNamex();
                args2 = ciImages[0].getcResultxx();
                return true;
            } catch (Exception e){
                e.printStackTrace();
                message = e.getMessage();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if(!isSuccess){
                listener.OnError(message);
            } else {
                listener.OnSuccess(args, args1, args2);
            }
        }
    }
}
