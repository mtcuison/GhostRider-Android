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

import android.app.Application;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DAddressRequest;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EAddressUpdate;
import org.rmj.g3appdriver.GRider.Database.Entities.EBarangayInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EFileCode;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EMobileUpdate;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBarangay;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCollectionUpdate;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GRider.Database.Repositories.RFileCode;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RTown;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Etc.DCP_Constants;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model.AddressUpdate;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model.MobileUpdate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VMCustomerNotAround extends AndroidViewModel {
    private static final String TAG = VMCustomerNotAround.class.getSimpleName();
    private static final String ZERO = "0";
    private final Application instance;
    private final RDailyCollectionPlan poDcp;
    private final RCollectionUpdate poUpdate;
    private final RImageInfo poImage;
    private final RBranch poBranch;
    private final RTown poTownRepo; //Town Repository
    private final RBarangay poBarangay;
    private final RFileCode poFileCode;

    private String ImgTransNox;
    private String imgFileNme;

    private final MutableLiveData<EDCPCollectionDetail> poDcpDetail = new MutableLiveData<>();
    private final MutableLiveData<String> psTransNox = new MutableLiveData<>();
    private final MutableLiveData<Integer> psEntryNox = new MutableLiveData<>();
    private final MutableLiveData<String> clientID = new MutableLiveData<>();
    private final MutableLiveData<String> requestCode = new MutableLiveData<>();
    private final MutableLiveData<String> addressType = new MutableLiveData<>();
    private final MutableLiveData<String> primeContact = new MutableLiveData<>();
    private final MutableLiveData<String> primeAddress = new MutableLiveData<>();
    private final MutableLiveData<String> psProvID = new MutableLiveData<>();
    private final MutableLiveData<String> psTownID = new MutableLiveData<>();
    private final MutableLiveData<String> psBrgyID = new MutableLiveData<>();
    private final MutableLiveData<String> sImgPathx = new MutableLiveData<>();
    private final MutableLiveData<String> sAccntNox = new MutableLiveData<>();

    private final MutableLiveData<List<DAddressRequest.CustomerAddressInfo>> plAddress = new MutableLiveData<>();
    private final MutableLiveData<List<EMobileUpdate>> plMobile = new MutableLiveData<>();

    public VMCustomerNotAround(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poDcp = new RDailyCollectionPlan(application);
        this.poUpdate = new RCollectionUpdate(application);
        this.poBranch = new RBranch(application);
        poTownRepo = new RTown(application);
        poBarangay = new RBarangay(application);
        this.primeContact.setValue(ZERO);
        this.primeAddress.setValue(ZERO);
        this.poImage = new RImageInfo(application);
        this.plAddress.setValue(new ArrayList<>());
        this.plMobile.setValue(new ArrayList<>());
        this.poFileCode = new RFileCode(application);
    }

    public void saveImageInfo(EImageInfo foImageInfo){
//        ImgTransNox = poImage.getImageNextCode();
        foImageInfo.setTransNox(ImgTransNox);
        foImageInfo.setDtlSrcNo(sAccntNox.getValue());
//        poImage.insertImageInfo(foImageInfo);
    }

    public void setParameter(String TransNox, int EntryNox){
        this.psTransNox.setValue(TransNox);
        this.psEntryNox.setValue(EntryNox);
    }

    public void setAccountNo(String fsAccntNo){
        this.sAccntNox.setValue(fsAccntNo);
    }

    public void setImgFileNme(String imgFileNme) {
        this.imgFileNme = imgFileNme;
    }

    public void setTownID(String fsID){
        this.psTownID.setValue(fsID);
    }
    public void setBrgyID(String fsID) {this.psBrgyID.setValue(fsID);}

    public void setPrimeAddress(String primeAddress) {
        this.primeAddress.setValue(primeAddress);
    }

    public MutableLiveData<String> getPrimeAddress() {
        return primeAddress;
    }

    public void setClientID(String clientID) {
        this.clientID.setValue(clientID);
    }

    public void setRequestCode(String requestCode) {
        this.requestCode.setValue(requestCode);
    }

    public MutableLiveData<String> getRequestCode() {
        return requestCode;
    }

    public void setPrimeContact(String primeContact) {
        this.primeContact.setValue(primeContact);
    }

    public MutableLiveData<String> getPrimeContact() {
        return primeContact;
    }

    public void setAddressType(String addressType) {
        this.addressType.setValue(addressType);
    }

    public void setImagePath(String fsPath){
        this.sImgPathx.setValue(fsPath);
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

    public LiveData<ArrayAdapter<String>> getRequestCodeOptions() {
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(DCP_Constants.getAdapter(getApplication(), DCP_Constants.REQUEST_CODE));
        return liveData;
    }

    public LiveData<List<DTownInfo.TownProvinceInfo>> getTownProvinceInfo(){
        return poTownRepo.getTownProvinceInfo();
    }

    public LiveData<String[]> getBarangayNameList(){
        return poBarangay.getBarangayNamesFromTown(psTownID.getValue());
    }

    public LiveData<List<EBarangayInfo>> getBarangayInfoList(){
        return poBarangay.getAllBarangayFromTown(psTownID.getValue());
    }

//
//    public LiveData<List<EAddressUpdate>> getAddressRequestList(){
//        //return poUpdate.getAddressList();
//        return plAddress;
//    }
//
//    public LiveData<List<DAddressRequest.CustomerAddressInfo>> getAddressNames() {
//        return poUpdate.getAddressNames();
//    }

    public LiveData<List<DAddressRequest.CustomerAddressInfo>> getAddressRequesListForClient(){
        return plAddress;
    }

    public LiveData<List<EMobileUpdate>> getMobileRequestListForClient(){
        //return poUpdate.getMobileListForClient(clientID.getValue());
        return plMobile;
    }

    public void updateCollectionDetail(String RemarksCode){
        try{
            EDCPCollectionDetail detail = poDcpDetail.getValue();
            Objects.requireNonNull(detail).setRemCodex(RemarksCode);
            detail.setImageNme(imgFileNme);
            new UpdateCollectionTask(poDcp, RemarksCode).execute(detail);
        }catch (NullPointerException e){
            e.printStackTrace();
        }catch (RuntimeException e){
            e.printStackTrace();
        }
    }

    public LiveData<List<EFileCode>> getAllFileCode() {
        return poFileCode.getAllFileCode();
    }

    public boolean addAddressToList(AddressUpdate foAddress, ViewModelCallback callback){
        try {
            foAddress.setRequestCode(requestCode.getValue());
            foAddress.setcAddrssTp(addressType.getValue());
            foAddress.setsProvIDxx(psProvID.getValue());
            foAddress.setTownID(psTownID.getValue());
            foAddress.setBarangayID(psBrgyID.getValue());
            foAddress.setPrimaryStatus(primeAddress.getValue());
            if (foAddress.isDataValid()) {
                DAddressRequest.CustomerAddressInfo info = new DAddressRequest.CustomerAddressInfo();
                //Auto Generated random String
                info.sTransNox = poDcp.getNextAddressCode();
                info.sClientID = clientID.getValue();
                info.cReqstCDe = foAddress.getRequestCode();
                info.cAddrssTp = foAddress.getcAddrssTp();
                info.sHouseNox = foAddress.getHouseNumber();
                info.sAddressx = foAddress.getAddress();
                info.sProvName = foAddress.getsProvName();
                info.sTownName = foAddress.getsTownName();
                info.sBrgyName = foAddress.getsBrgyName();
                info.sTownIDxx = foAddress.getTownID();
                info.sBrgyIDxx = foAddress.getBarangayID();
                info.cPrimaryx = foAddress.getPrimaryStatus();
                info.sLongitud = foAddress.getLongitude();
                info.sLatitude = foAddress.getLatitude();
                info.sRemarksx = foAddress.getRemarks();
                Objects.requireNonNull(this.plAddress.getValue()).add(info);
            } else {
                callback.OnFailedResult(foAddress.getMessage());
                return false;
            }
        } catch (Exception e){
            e.printStackTrace();
            callback.OnFailedResult(e.getMessage());
            return false;
        }

        return true;
    }

    public boolean AddMobileToList(MobileUpdate foMobile, ViewModelCallback callback){
        try {
            if (foMobile.isDataValid()) {
                EMobileUpdate info = new EMobileUpdate();
                info.setTransNox(poDcp.getNextMobileCode());
                info.setClientID(clientID.getValue());
                info.setReqstCDe(foMobile.getcReqstCde());
                info.setMobileNo(foMobile.getsMobileNo());
                info.setPrimaryx(foMobile.getcPrimaryx());
                info.setRemarksx(foMobile.getsRemarksx());
                info.setTranStat("0");
                info.setSendStat("0");
                info.setModified(new AppConstants().DATE_MODIFIED);
                info.setTimeStmp(new AppConstants().DATE_MODIFIED);
                Objects.requireNonNull(this.plMobile.getValue()).add(info);
            } else {
                callback.OnFailedResult(foMobile.getMessage());
                return false;
            }
        } catch (Exception e){
            e.printStackTrace();
            callback.OnFailedResult(e.getMessage());
            return false;
        }

        return true;
    }

    public boolean saveAddressToLocal(ViewModelCallback callback){
        try {
            List<DAddressRequest.CustomerAddressInfo> loAdd = plAddress.getValue();
            List<EAddressUpdate> addList = new ArrayList<>();
            for(int x = 0; x < loAdd.size(); x++){
                EAddressUpdate info = new EAddressUpdate();
                info.setTransNox(loAdd.get(x).sTransNox);
                info.setClientID(loAdd.get(x).sClientID);
                info.setReqstCDe(loAdd.get(x).cReqstCDe);
                info.setAddrssTp(loAdd.get(x).cAddrssTp);
                info.setHouseNox(loAdd.get(x).sHouseNox);
                info.setAddressx(loAdd.get(x).sAddressx);
                info.setTownIDxx(loAdd.get(x).sTownIDxx);
                info.setBrgyIDxx(loAdd.get(x).sBrgyIDxx);
                info.setPrimaryx(loAdd.get(x).cPrimaryx);
                info.setLongitud(loAdd.get(x).sLongitud);
                info.setLatitude(loAdd.get(x).sLatitude);
                info.setRemarksx(loAdd.get(x).sRemarksx);
                info.setTranStat("1");
                info.setSendStat("0");
                info.setModified(new AppConstants().DATE_MODIFIED);
                info.setTimeStmp(new AppConstants().DATE_MODIFIED);
                addList.add(info);
            }
            poUpdate.insertUpdateAddress(addList);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            callback.OnFailedResult(e.getMessage());
            return false;
        }
    }

//    public void deleteAddress(String TransNox){
//        poUpdate.deleteAddress(TransNox);
//    }

    public void deleteAddress(int position){
        Objects.requireNonNull(this.plAddress.getValue()).remove(position);
    }

    public boolean saveMobileToLocal(ViewModelCallback callback){
        try{
            poUpdate.insertUpdateMobile(this.plMobile.getValue());
            return true;
        } catch (Exception e){
            e.printStackTrace();
            callback.OnFailedResult(e.getMessage());
            return false;
        }
    }

//    public void deleteMobile(String TransNox){
//        poUpdate.deleteMobile(TransNox);
//    }

    public void deleteMobile(int position){
        Objects.requireNonNull(plMobile.getValue()).remove(position);
    }

    private String getValidatedAddress(AddressUpdate foAddress) {
        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("RqstCode", foAddress.getRequestCode());
            jsonObj.put("AddressTp", foAddress.getcAddrssTp());
            jsonObj.put("HouseNo", foAddress.getHouseNumber());
            jsonObj.put("Address", foAddress.getAddress());
            jsonObj.put("TownId", foAddress.getTownID());
            jsonObj.put("BrgyId", foAddress.getBarangayID());
            jsonObj.put("PrimaryStat", foAddress.getPrimaryStatus());
            jsonObj.put("Remarks",foAddress.getRemarks());

            return jsonObj.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    private String getValidatedMobilenox(MobileUpdate foMobile) {
        try {
            JSONObject jsonObj = new JSONObject();

            jsonObj.put("RequestCode", foMobile.getcReqstCde());
            jsonObj.put("MobileNox", foMobile.getsMobileNo());
            jsonObj.put("isPrimary", foMobile.getcPrimaryx());
            jsonObj.put("Remarks", foMobile.getsRemarksx());

            return jsonObj.toString();
        }
        catch (JSONException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    private static class UpdateCollectionTask extends AsyncTask<EDCPCollectionDetail, Void, String> {
        private final RDailyCollectionPlan poDcp;
        private final String Remarksx;

        public UpdateCollectionTask(RDailyCollectionPlan poDcp, String Remarks){
            this.poDcp = poDcp;
            this.Remarksx = Remarks;
        }

        @Override
        protected String doInBackground(EDCPCollectionDetail... detail) {
            EDCPCollectionDetail loDetail = detail[0];
            poDcp.updateCNADetail(loDetail.getAcctNmbr(), loDetail.getEntryNox(), Remarksx);
            return null;
        }
    }
}