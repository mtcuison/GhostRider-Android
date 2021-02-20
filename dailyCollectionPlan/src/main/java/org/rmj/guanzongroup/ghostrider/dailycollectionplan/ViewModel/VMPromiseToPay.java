package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import android.app.Application;
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
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.etc.SessionManager;
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
    private final MutableLiveData<EDCPCollectionDetail> poDcpDetail = new MutableLiveData<>();

    private final MutableLiveData<String> psBrnchCd = new MutableLiveData<>();
    public MutableLiveData<String> psPtpDate = new MutableLiveData<>();
    private final MutableLiveData<String> psTransNox = new MutableLiveData<>();
    private final MutableLiveData<String> psEntryNox = new MutableLiveData<>();

    private final MutableLiveData<Integer> viewPtpBranch = new MutableLiveData<>();
    private final MutableLiveData<String> isAppointmentUnitX = new MutableLiveData<>();

    private final LiveData<String[]> paBranchNm;
    public VMPromiseToPay(@NonNull Application application) {
        super(application);
        this.poBranch = new RBranch(application);
        this.poDcp = new RDailyCollectionPlan(application);
        paBranchNm = poBranch.getAllMcBranchNames();
        this.viewPtpBranch.setValue(View.GONE);
    }
    // TODO: Implement the ViewModel
    public void setParameter(String TransNox, String EntryNox){
        this.psTransNox.setValue(TransNox);
        this.psEntryNox.setValue(EntryNox);
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

    public boolean savePtpInfo(PromiseToPayModel infoModel, ViewModelCallback callback){
        try{
            infoModel.setPtpDate(psPtpDate.getValue());
            if(!infoModel.isDataValid()){
                callback.OnFailedResult(infoModel.getMessage());
                return false;
            } else {
                Date parseDate = new SimpleDateFormat("MMMM dd, yyyy").parse(infoModel.getPtpDate());
                String lsDate = new SimpleDateFormat("yyyy-MM-dd").format(Objects.requireNonNull(parseDate));;
                String lsPromiseDate = toJsonObject(lsDate, psBrnchCd.getValue(), infoModel.getPtpCollectorName());

                EDCPCollectionDetail detail = poDcpDetail.getValue();
                Objects.requireNonNull(detail).setPromised(lsPromiseDate);
                detail.setSendStat("0");
                detail.setModified(AppConstants.DATE_MODIFIED);
                poDcp.updateCollectionDetailInfo(detail);
                Log.e(TAG, "Date ." + lsPromiseDate);
                //Log.e(TAG, "Promise to Pay info has been set." + poDcp.getCollectionDetail(psTransNox.getValue(),psEntryNox.getValue()).getValue().toString());
                callback.OnSuccessResult(new String[]{"Dcp Save!"});
                return true;
            }
        } catch (NullPointerException e){
            e.printStackTrace();
//            callback.OnFailedResult(e.getMessage());
            callback.OnFailedResult("NullPointerException error");
            return false;
        } catch (Exception e){
            e.printStackTrace();
//            callback.OnFailedResult(e.getMessage());

            callback.OnFailedResult("Exception error");
            return false;
        }

    }
    public String toJsonObject(String dPromised, String sBrnCde, String sCollector){
        JSONObject jsonObject = new JSONObject();
        try {
            //yyyy-MM-dd
            jsonObject.put("dPromised", dPromised);
            jsonObject.put("sBrnCde", sBrnCde);
            jsonObject.put("sCollector", sCollector);
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return  e.getMessage();
        }
    }
}