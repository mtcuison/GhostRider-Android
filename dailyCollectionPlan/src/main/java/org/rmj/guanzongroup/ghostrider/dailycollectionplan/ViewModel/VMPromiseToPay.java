package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.AppDatabase;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DDCPCollectionMaster;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionMaster;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;
import org.rmj.g3appdriver.etc.SessionManager;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Etc.DCP_Constants;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model.PaidTransactionModel;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model.PromiseToPayModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class VMPromiseToPay extends AndroidViewModel {
    private static final String TAG = VMPromiseToPay.class.getSimpleName();
    private final RBranch poBranch;
    private final RDailyCollectionPlan poDcp;
    private final RImageInfo poImage;
    private final EImageInfo poImageInfo;
    private final MutableLiveData<EDCPCollectionDetail> poDcpDetail = new MutableLiveData<>();

    private final MutableLiveData<String> psBrnchCd = new MutableLiveData<>();
    public MutableLiveData<String> psPtpDate = new MutableLiveData<>();
    private final MutableLiveData<String> psTransNox = new MutableLiveData<>();
    private final MutableLiveData<Integer> psEntryNox = new MutableLiveData<>();
    private final MutableLiveData<String> psAccountNox = new MutableLiveData<>();
    private final MutableLiveData<String> sRemarksx = new MutableLiveData<>();
    private final MutableLiveData<String> sImgName = new MutableLiveData<>();
    private final MutableLiveData<String> sLatitude = new MutableLiveData<>();
    private final MutableLiveData<String> sLongitude = new MutableLiveData<>();

    private final MutableLiveData<Integer> viewPtpBranch = new MutableLiveData<>();
    private final MutableLiveData<String> isAppointmentUnitX = new MutableLiveData<>();

    private final LiveData<String[]> paBranchNm;
    public VMPromiseToPay(@NonNull Application application) {
        super(application);
        this.poBranch = new RBranch(application);
        this.poDcp = new RDailyCollectionPlan(application);
        paBranchNm = poBranch.getAllMcBranchNames();
        this.viewPtpBranch.setValue(View.GONE);
        this.poImage = new RImageInfo(application);
        this.poImageInfo = new EImageInfo();
    }
    // TODO: Implement the ViewModel
    public void setParameter(String TransNox, int EntryNox, String fsRemarksx){
        this.psTransNox.setValue(TransNox);
        this.psEntryNox.setValue(EntryNox);
        this.sRemarksx.setValue(DCP_Constants.getRemarksCode(fsRemarksx));
    }

    public LiveData<EDCPCollectionMaster> getCollectionMaster(){
        return poDcp.getCollectionMaster();
    }
    public LiveData<EDCPCollectionDetail> getCollectionDetail(){
        return poDcp.getCollectionDetail(psTransNox.getValue(), psEntryNox.getValue());
    }
    public void setCurrentCollectionDetail(EDCPCollectionDetail detail){
        this.poDcpDetail.setValue(detail);
    }

    public LiveData<String> getPtpDate(){
        return this.psPtpDate;
    }
    public void setPsPtpDate(String date){
        this.psPtpDate.setValue(date);
    }
    public LiveData<List<EBranchInfo>> getAllBranchInfo(){
        return poBranch.getAllMcBranchInfo();
    }

    public LiveData<EBranchInfo> getUserBranchInfo(){
        return poBranch.getUserBranchInfo();
    }

    public LiveData<String[]> getAllBranchNames(){
        return paBranchNm;
    }
    public void setBanchCde(String psBrnchCd) {
        Log.e("Branch code", psBrnchCd);
        this.psBrnchCd.setValue(psBrnchCd);
    }
    public LiveData<EBranchInfo> getUserBranchEmployee(){
        return poBranch.getUserBranchInfo();
    }

    public void setIsAppointmentUnitX(String type){

        try {
            if(Integer.parseInt(type) == 1 || type.equalsIgnoreCase("1")){
                this.viewPtpBranch.setValue(View.VISIBLE);
            } else {
                this.viewPtpBranch.setValue(View.GONE);
            }
        } catch (NullPointerException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        this.isAppointmentUnitX.setValue(type);
    }

    public LiveData<Integer> setViewPtpBranch(){
        return this.viewPtpBranch;
    }

    public void setLatitude(String sLatitude) {
        this.sLatitude.setValue(sLatitude);
    }

    public void setLongitude(String sLongitude) {
        this.sLongitude.setValue(sLongitude);
    }
    public void setImgName(String imgName) {
        this.sImgName.setValue(imgName);
    }

    public void setAccountNox(String sAccountNo) {
        this.psAccountNox.setValue(sAccountNo);
    }
    public boolean savePtpInfo(PromiseToPayModel infoModel, ViewModelCallback callback) {
        try {

            new UpdateTask(poDcp, infoModel, callback).execute(poDcpDetail.getValue());
            return true;
        } catch (NullPointerException e) {
            e.printStackTrace();
//            callback.OnFailedResult(e.getMessage());
            callback.OnFailedResult("NullPointerException error");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            callback.OnFailedResult("Exception error");
            return false;
        }
    }

    //Added by Mike -> Saving ImageInfo
    public void saveImageInfo(EImageInfo foImage){
        try{
            foImage.setTransNox(poImage.getImageNextCode());
             poImage.insertImageInfo(foImage);
            Log.e(TAG, "Image info has been save!");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //Added by Mike 2021/02/27
    //Need AsyncTask for background threading..
    //RoomDatabase requires background task in order to manipulate Tables...
    private  class UpdateTask extends AsyncTask<EDCPCollectionDetail, Void, String> {
        private final RDailyCollectionPlan poDcp;
        private final PromiseToPayModel infoModel;
        private final ViewModelCallback callback;

        public UpdateTask(RDailyCollectionPlan poDcp, PromiseToPayModel infoModel, ViewModelCallback callback) {
            this.poDcp = poDcp;
            this.infoModel = infoModel;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(EDCPCollectionDetail... detail) {
            try {

                infoModel.setPtpBranch(psBrnchCd.getValue());
                if (!infoModel.isDataValid()) {
                    return infoModel.getMessage();
                } else {
                    String lsSelectedDate = Objects.requireNonNull(infoModel.getPtpDate());
                    @SuppressLint("SimpleDateFormat") Date parseDate = new SimpleDateFormat("MMMM dd, yyyy").parse(lsSelectedDate);
                    @SuppressLint("SimpleDateFormat") String lsDate = new SimpleDateFormat("yyyy-MM-dd").format(Objects.requireNonNull(parseDate));
                    EDCPCollectionDetail loDetail = detail[0];
                    loDetail.setRemCodex(sRemarksx.getValue());
                    Objects.requireNonNull(loDetail).setPromised(lsDate);
                    loDetail.setApntUnit(infoModel.getPtpAppointmentUnit());
                    loDetail.setBranchCd(infoModel.getPtpBranch());
                    loDetail.setTranStat("1");
                    loDetail.setSendStat("0");
                    loDetail.setRemarksx(infoModel.getPtpRemarks());
                    loDetail.setLatitude(sLatitude.getValue());
                    loDetail.setLongitud(sLongitude.getValue());
                    loDetail.setImageNme(sImgName.getValue());
                    loDetail.setModified(AppConstants.DATE_MODIFIED);
                    poDcp.updateCollectionDetailInfo(loDetail);

                    return "success";
                }
            } catch (Exception e){
                e.printStackTrace();
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equalsIgnoreCase("success")){
                callback.OnSuccessResult(new String[]{"Promise to pay Info has been save."});
            } else {
                 callback.OnFailedResult(s);
            }
        }
    }
}