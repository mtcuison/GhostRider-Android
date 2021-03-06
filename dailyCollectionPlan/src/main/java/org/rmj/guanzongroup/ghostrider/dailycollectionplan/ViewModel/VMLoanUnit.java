/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.dailyCollectionPlan
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.database.Cursor;
import android.graphics.ImageDecoder;
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
import org.rmj.g3appdriver.GRider.Database.Entities.EClientUpdate;
import org.rmj.g3appdriver.GRider.Database.Entities.ECountryInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionMaster;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EProvinceInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ETownInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBarangay;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.RClientUpdate;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCountry;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RProvince;
import org.rmj.g3appdriver.GRider.Database.Repositories.RTown;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Etc.DCP_Constants;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model.LoanUnitModel;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model.PromiseToPayModel;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class VMLoanUnit extends AndroidViewModel {
    private static final String TAG = VMLoanUnit.class.getSimpleName();
    private final RBranch poBranch;
    private final RDailyCollectionPlan poDcp;
    private final RImageInfo poImage;
    private final RClientUpdate poClient;
    private final MutableLiveData<EDCPCollectionDetail> poDcpDetail = new MutableLiveData<>();
    private final MutableLiveData<EClientUpdate> eClientDetail = new MutableLiveData<>();
    private final MutableLiveData<String> psTransNox = new MutableLiveData<>();
    private final MutableLiveData<Integer> psEntryNox = new MutableLiveData<>();
    private final MutableLiveData<String> sRemarksx = new MutableLiveData<>();
    private final MutableLiveData<String> sImgName = new MutableLiveData<>();
    private final MutableLiveData<String> sLatitude = new MutableLiveData<>();
    private final MutableLiveData<String> sLongitude = new MutableLiveData<>();
    private final MutableLiveData<String> imgPath = new MutableLiveData<>();

    private List<EClientUpdate> clientData =  new ArrayList<>();
    private List<EImageInfo> imgInfo = new ArrayList<>();
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
        poClient = new RClientUpdate(application);
        provinceInfoList = RProvince.getAllProvinceInfo();
        this.poImage = new RImageInfo(application);
        this.luCivilStats.setValue(getCivilStats().getValue());
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
    public void setClientData(List<EClientUpdate> eClientUpdate){
        this.clientData = eClientUpdate;
    }
    public LiveData<List<EClientUpdate>>  getClientData(){
        return poClient.selectClientUpdate();
    }
    public LiveData<String>  getTransNox(){
        return this.psTransNox;
    }
    public void setImgPath(String imgPaths){
        this.imgPath.setValue(imgPaths);
    }
    public void setClient(EClientUpdate client){
        this.eClientDetail.setValue(client);
    }
    public LiveData<EClientUpdate> getClient(String sSourceNo, String DtlSrcNo){
        return poClient.selectClient(sSourceNo, DtlSrcNo);
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
    public void setBrgyID(String brgyID){
        this.lsBrgyID.setValue(brgyID);
    }
    public void setLsBPlaceID(String townID){
        this.lsBPlace.setValue(townID);
    }
    //Setter Civil Status
    public void setSpnCivilStats(String type) { this.luCivilStats.setValue(type); }
    public void setGender(String lsGender) { this.luGender.setValue(lsGender); }
    public LiveData<String>  getGender() { return this.luGender; }
    public LiveData<String> getCivilStats(){
        return this.luCivilStats;
    }
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
                detail.setModified(new AppConstants().DATE_MODIFIED);
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

    public void setLatitude(String sLatitude) {
        this.sLatitude.setValue(sLatitude);
    }

    public void setLongitude(String sLongitude) {
        this.sLongitude.setValue(sLongitude);
    }
    public LiveData<String> getLatitude() {
       return this.sLatitude;
    }

    public LiveData<String>  getLongitude() {
        return this.sLongitude;
    }
    public void setImgName(String imgName) {
        this.sImgName.setValue(imgName);
    }

    public void setImgInfo(List<EImageInfo> imgInfo) {
        this.imgInfo = imgInfo;
    }
    public LiveData<List<EImageInfo>>  getImgInfo(){
        return poImage.getAllImageInfo();
    }
    //Added by Mike -> Saving ImageInfo
    public void saveLUnImageInfo(EImageInfo foImage){
        try{
            boolean isImgExist = false;
            String tansNo = "";
            for (int i = 0; i < imgInfo.size(); i++){
                if(foImage.getSourceNo().equalsIgnoreCase(imgInfo.get(i).getSourceNo())
                        && foImage.getDtlSrcNo().equalsIgnoreCase(imgInfo.get(i).getDtlSrcNo())) {
                    tansNo = imgInfo.get(i).getTransNox();
                    File finalFile = new File(getRealPathFromURI(imgInfo.get(i).getFileLoct()));
                    finalFile.delete();
                    isImgExist = true;
                }
            }
            if (isImgExist){
                foImage.setTransNox(tansNo);
                Log.e("Img TransNox", tansNo);
                poImage.updateImageInfo(foImage);
                Log.e(TAG, "Image info has been updated!");
            }else{
                foImage.setTransNox(poImage.getImageNextCode());
                poImage.insertImageInfo(foImage);
                Log.e(TAG, "Image info has been save!");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getRealPathFromURI (String contentUri) {
        File target = new File(contentUri);
        if (target.exists() && target.isFile() && target.canWrite()) {
            target.delete();
            Log.d("d_file", "" + target.getName());
        }
        return target.toString();
    }
    public boolean saveLUnInfo(LoanUnitModel infoModel, ViewModelCallback callback) {
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
                boolean isExist = false;
                String clientID = "";
                for (int i = 0; i < clientData.size(); i++){
                    if(detail[0].getTransNox().equalsIgnoreCase(clientData.get(i).getSourceNo())
                            && detail[0].getAcctNmbr().equalsIgnoreCase(clientData.get(i).getDtlSrcNo())){
                        isExist = true;
                        clientID = clientData.get(i).getClientID();;
                    }
                }

                infoModel.setLuCivilStats(luCivilStats.getValue());
                infoModel.setLuGender(luGender.getValue());
                if (!infoModel.isValidData()) {
                    return infoModel.getMessage();
                } else {
                    EDCPCollectionDetail loDetail = detail[0];
                    loDetail.setRemCodex(sRemarksx.getValue());
                    String fullName = infoModel.getLuLastName() + ", " +
                            infoModel.getLuFirstName() + " " +
                            infoModel.getLuMiddleName() + " " +
                            infoModel.getLuSuffix();
                    loDetail.setFullName(fullName);
                    loDetail.setBrgyName(lsBrgyID.getValue());
                    loDetail.setHouseNox(infoModel.getLuHouseNo());
                    loDetail.setAddressx(infoModel.getLuStreet());
                    loDetail.setTownName(lsTownID.getValue());
                    loDetail.setMobileNo(infoModel.getLuMobile());
                    loDetail.setTranStat("1");
                    loDetail.setLatitude(sLatitude.getValue());
                    loDetail.setLongitud(sLongitude.getValue());
                    loDetail.setImageNme(sImgName.getValue());
                    loDetail.setRemarksx(infoModel.getLuRemark());
                    loDetail.setModified(new AppConstants().DATE_MODIFIED);
                    poDcp.updateCollectionDetailInfo(loDetail);

                    EClientUpdate eClientUpdate = new EClientUpdate();
                    eClientUpdate.setSourceNo(detail[0].getTransNox());
                    eClientUpdate.setDtlSrcNo(detail[0].getAcctNmbr());
                    eClientUpdate.setFrstName( infoModel.getLuFirstName());
                    eClientUpdate.setLastName(infoModel.getLuLastName());
                    eClientUpdate.setMiddName(infoModel.getLuMiddleName());
                    eClientUpdate.setSuffixNm(infoModel.getLuSuffix());
                    eClientUpdate.setAddressx(infoModel.getLuStreet());
                    eClientUpdate.setBirthDte(infoModel.getLuBDate());
                    eClientUpdate.setBirthPlc(lsBPlace.getValue());
                    eClientUpdate.setEmailAdd(infoModel.getLuEmail());
                    eClientUpdate.setHouseNox(infoModel.getLuHouseNo());
                    eClientUpdate.setImageNme(sImgName.getValue());
                    eClientUpdate.setLandline(infoModel.getLuPhone());
                    eClientUpdate.setMobileNo(infoModel.getLuMobile());
                    eClientUpdate.setModified(new AppConstants().DATE_MODIFIED);
                    eClientUpdate.setSendStat("0");
                    eClientUpdate.setSourceCd("DCPa");
                    eClientUpdate.setTownIDxx(lsTownID.getValue());
                    eClientUpdate.setBarangay(lsBrgyID.getValue());
                    eClientUpdate.setGenderxx(infoModel.getLuGender());
                    eClientUpdate.setCivlStat(infoModel.getLuCivilStats());
                    if (isExist){
                        eClientUpdate.setClientID(clientID);
                        poClient.updateClientInfo(eClientUpdate);
                        Log.e(TAG, "Client info has been updated!");
                    }else {
                        eClientUpdate.setClientID(poClient.getClientNextCode());
                        poClient.insertClientUpdateInfo(eClientUpdate);
                        Log.e(TAG, "Client info has been insert!");
                    }


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
                callback.OnSuccessResult(new String[]{DCP_Constants.getRemarksDescription(sRemarksx.getValue()) +" info has been save."});
            } else {
                callback.OnFailedResult(s);
            }
        }
    }


    public LiveData<DTownInfo.BrgyTownProvinceInfo> getTownProvinceInfo(String fsID){
        return RTown.getTownProvinceInfo(fsID);
    }
    public LiveData<DTownInfo.BrgyTownProvinceInfo> getBrgyTownProvinceInfo(String fsID){
        return RTown.getBrgyTownProvinceInfo(fsID);
    }
}