package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EBarangayInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ECountryInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionMaster;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EProvinceInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ETownInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBarangay;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCountry;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RProvince;
import org.rmj.g3appdriver.GRider.Database.Repositories.RTown;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Etc.DCP_Constants;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model.LoanUnitModel;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model.PromiseToPayModel;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class VMLoanUnit extends AndroidViewModel {
    private static final String TAG = VMLoanUnit.class.getSimpleName();
    private final RBranch poBranch;
    private final RDailyCollectionPlan poDcp;
    private final RImageInfo poImage;
    private final MutableLiveData<EDCPCollectionDetail> poDcpDetail = new MutableLiveData<>();
    private final MutableLiveData<String> psTransNox = new MutableLiveData<>();
    private final MutableLiveData<Integer> psEntryNox = new MutableLiveData<>();

    private MutableLiveData<String> lsBPlace = new MutableLiveData<>();
    private MutableLiveData<String> lsProvID = new MutableLiveData<>();
    private MutableLiveData<String> lsTownID = new MutableLiveData<>();
    private MutableLiveData<String> lsBrgyID = new MutableLiveData<>();

    private final MutableLiveData<String> spnCivilStats = new MutableLiveData<>();
    private final MutableLiveData<String> luGender = new MutableLiveData<>();
    private final MutableLiveData<String> luCivilStats = new MutableLiveData<>();

    private final RProvince RProvince;
    private final RTown RTown;
    private final RCountry RCountry;
    private final RBarangay Brgy;
    private final LiveData<List<EProvinceInfo>> provinceInfoList;
    public VMLoanUnit(@NonNull Application application) {
        super(application);
        this.poBranch = new RBranch(application);
        this.poDcp = new RDailyCollectionPlan(application);
        RProvince = new RProvince(application);
        RTown = new RTown(application);
        RCountry = new RCountry(application);
        Brgy = new RBarangay(application);
        provinceInfoList = RProvince.getAllProvinceInfo();
        this.poImage = new RImageInfo(application);
    }
    // TODO: Implement the ViewModel
    public void setParameter(String TransNox, int EntryNox){
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

    public LiveData<EBranchInfo> getUserBranchEmployee(){
        return poBranch.getUserBranchInfo();
    }
    public LiveData<List<DTownInfo.TownProvinceInfo>> getTownProvinceInfo(){
        return RTown.getTownProvinceInfo();
    }

    public LiveData<String[]> getBarangayNameList(){
        return Brgy.getBarangayNamesFromTown(lsTownID.getValue());
    }

    public LiveData<List<EBarangayInfo>> getBarangayInfoList(){
        return Brgy.getAllBarangayFromTown(lsTownID.getValue());
    }


    public void setProvID(String ProvID) { this.lsProvID.setValue(ProvID); }
    public void setTownID(String townID){
        this.lsTownID.setValue(townID);
    }
    public void setBrgyID(String townID){
        this.lsBrgyID.setValue(townID);
    }
    public void setLsBPlaceID(String townID){
        this.lsBPlace.setValue(townID);
    }
    //Setter Civil Status
    public void setSpnCivilStats(String type) { this.spnCivilStats.setValue(type); }
    public void setGender(String lsGender) { this.luGender.setValue(lsGender); }
    //Spinner Getter
    public LiveData<ArrayAdapter<String>> getSpnCivilStats(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_spinner_dropdown_item, DCP_Constants.CIVIL_STATUS)
        {
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = super.getView(position, convertView, parent);
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
                ((TextView) v).setTextColor(getApplication().getResources().getColorStateList(R.color.textColor_White));
            }else {
                ((TextView) v).setTextColor(getApplication().getResources().getColorStateList(R.color.textColor_Black));
            }
            return v;
        }
    };
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }

    public boolean saveLuInfo(LoanUnitModel infoModel, ViewModelCallback callback){
        try{
            infoModel.setLuGender(luGender.getValue());
//            infoModel.setLuCivilStats(luCivilStats.getValue());
            if(!infoModel.isValidData()){
                callback.OnFailedResult(infoModel.getMessage());
                return false;
            } else {
                String fullName = infoModel.getLuLastName() + ", " +
                        infoModel.getLuFirstName() + " " +
                        infoModel.getLuMiddleName() + " " +
                        infoModel.getLuSuffix();
                EDCPCollectionDetail detail = poDcpDetail.getValue();
                detail.setFullName(fullName);
                detail.setBrgyName(infoModel.getLuBrgy());
                detail.setHouseNox(infoModel.getLuHouseNo());
                detail.setAddressx(infoModel.getLuStreet());
                detail.setTownName(infoModel.getLuTown());
                detail.setMobileNo(infoModel.getLuMobile());
                detail.setSendStat("0");
                detail.setModified(AppConstants.DATE_MODIFIED);
                poDcp.updateCollectionDetailInfo(detail);
                //Log.e(TAG, "Promise to Pay info has been set." + poDcp.getCollectionDetail(psTransNox.getValue(),psEntryNox.getValue()).getValue().toString());
                callback.OnSuccessResult(new String[]{"Dcp Save!"});
                return true;
            }
        } catch (NullPointerException e){
            e.printStackTrace();
            callback.OnFailedResult(e.getMessage());
            return false;
        } catch (Exception e){
            e.printStackTrace();
            callback.OnFailedResult(e.getMessage());
            return false;
        }
    }
    public void saveLUnInfo(LoanUnitModel infoModel, ViewModelCallback callback) {
        try {

            new UpdateTask(poDcp, infoModel, callback).execute(poDcpDetail.getValue());
        } catch (NullPointerException e) {
            e.printStackTrace();
//            callback.OnFailedResult(e.getMessage());
            callback.OnFailedResult("NullPointerException error");
        } catch (Exception e) {
            e.printStackTrace();
            callback.OnFailedResult("Exception error");
        }
    }

    //Added by Mike -> Saving ImageInfo
    public void saveLUnImageInfo(EImageInfo foImage){
        try{
            foImage.setTransNox(poDcp.getImageNextCode());
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
        private final LoanUnitModel infoModel;
        private final ViewModelCallback callback;

        public UpdateTask(RDailyCollectionPlan poDcp, LoanUnitModel infoModel, ViewModelCallback callback) {
            this.poDcp = poDcp;
            this.infoModel = infoModel;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(EDCPCollectionDetail... detail) {
            try {

                infoModel.setLuGender(luGender.getValue());
                if (!infoModel.isValidData()) {
                    return infoModel.getMessage();
                } else {
                    EDCPCollectionDetail loDetail = detail[0];
                    loDetail.setRemCodex(DCP_Constants.TRANSACT_LUn);
                    String fullName = infoModel.getLuLastName() + ", " +
                            infoModel.getLuFirstName() + " " +
                            infoModel.getLuMiddleName() + " " +
                            infoModel.getLuSuffix();
                    loDetail.setFullName(fullName);
                    loDetail.setBrgyName(infoModel.getLuBrgy());
                    loDetail.setHouseNox(infoModel.getLuHouseNo());
                    loDetail.setAddressx(infoModel.getLuStreet());
                    loDetail.setTownName(infoModel.getLuTown());
                    loDetail.setMobileNo(infoModel.getLuMobile());
                    loDetail.setTranStat("1");
                    loDetail.setSendStat("0");
                    loDetail.setModified(AppConstants.DATE_MODIFIED);
                    poDcp.updateCollectionDetailInfo(loDetail);
                    Log.e("Detail Info ", poDcp.toString());
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