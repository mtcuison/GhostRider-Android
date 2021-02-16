    package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

    import android.app.Application;
    import android.util.Log;
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
    import org.rmj.g3appdriver.GRider.Database.Entities.EMobileUpdate;
    import org.rmj.g3appdriver.GRider.Database.Entities.EProvinceInfo;
    import org.rmj.g3appdriver.GRider.Database.Entities.ETownInfo;
    import org.rmj.g3appdriver.GRider.Database.Repositories.RBarangay;
    import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
    import org.rmj.g3appdriver.GRider.Database.Repositories.RCollectionUpdate;
    import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
    import org.rmj.g3appdriver.GRider.Database.Repositories.RProvince;
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
        private final RBranch poBranch;
        private final RTown poTownRepo; //Town Repository
        private final RBarangay poBarangay;

        private final MutableLiveData<EDCPCollectionDetail> poDcpDetail = new MutableLiveData<>();
        private final MutableLiveData<String> psTransNox = new MutableLiveData<>();
        private final MutableLiveData<String> psEntryNox = new MutableLiveData<>();
        private final MutableLiveData<String> clientID = new MutableLiveData<>();
        private final MutableLiveData<String> requestCode = new MutableLiveData<>();
        private final MutableLiveData<String> addressType = new MutableLiveData<>();
        private final MutableLiveData<String> primeContact = new MutableLiveData<>();
        private final MutableLiveData<String> primeAddress = new MutableLiveData<>();
        private final MutableLiveData<String> psProvID = new MutableLiveData<>();
        private final MutableLiveData<String> psTownID = new MutableLiveData<>();
        private final MutableLiveData<String> psBrgyID = new MutableLiveData<>();

        private MutableLiveData<List<EAddressUpdate>> plAddress = new MutableLiveData<>();
        private MutableLiveData<List<EMobileUpdate>> plMobile = new MutableLiveData<>();

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

            this.plAddress.setValue(new ArrayList<>());
            this.plMobile.setValue(new ArrayList<>());
        }

        public void setParameter(String TransNox, String EntryNox){
            this.psTransNox.setValue(TransNox);
            this.psEntryNox.setValue(EntryNox);
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
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, DCP_Constants.REQUEST_CODE);
            MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
            liveData.setValue(adapter);
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

        public LiveData<List<EAddressUpdate>> getAddressRequestList(){
            return poUpdate.getAddressList();
        }

        public LiveData<List<DAddressRequest.CustomerAddressInfo>> getAddressNames() {
            return poUpdate.getAddressNames();
        }

        public LiveData<List<EMobileUpdate>> getMobileRequestList(){
            return poUpdate.getMobileList();
        }

        public void addAddress(AddressUpdate foAddress, ViewModelCallback callback){
            try {
                foAddress.setRequestCode(requestCode.getValue());
                foAddress.setcAddrssTp(addressType.getValue());
                foAddress.setsProvIDxx(psProvID.getValue());
                foAddress.setTownID(psTownID.getValue());
                foAddress.setBarangayID(psBrgyID.getValue());
                foAddress.setPrimaryStatus(primeAddress.getValue());
                if (foAddress.isDataValid()) {
                    EAddressUpdate info = new EAddressUpdate();
                    //Auto Generated random String
                    info.setTransNox(getAlphaNumericString());
                    info.setClientID(clientID.getValue());
                    info.setReqstCDe(foAddress.getRequestCode());
                    info.setAddrssTp(foAddress.getcAddrssTp());
                    info.setHouseNox(foAddress.getHouseNumber());
                    info.setAddressx(foAddress.getAddress());
                    info.setTownIDxx(foAddress.getTownID());
                    info.setBrgyIDxx(foAddress.getBarangayID());
                    info.setPrimaryx(foAddress.getPrimaryStatus());
                    info.setLongitud("");
                    info.setLatitude("");
                    info.setRemarksx(foAddress.getRemarks());
                    info.setTranStat("");
                    info.setSendStat("0");
                    info.setModified(AppConstants.DATE_MODIFIED);
                    info.setTimeStmp(AppConstants.DATE_MODIFIED);
                    poUpdate.insertUpdateAddress(info);

                    callback.OnSuccessResult(new String[]{"Address added into local database."});
                    Log.e(TAG, getValidatedAddress(foAddress));
                }
                else {
                    callback.OnFailedResult(foAddress.getMessage());
                }
            } catch (Exception e){
                e.printStackTrace();
                callback.OnFailedResult(e.getMessage());
            }
        }

        public void deleteAddress(String TransNox){
            poUpdate.deleteAddress(TransNox);
        }

        public void addMobile(MobileUpdate foMobile, ViewModelCallback callback){
            try{
                if(foMobile.isDataValid()){
                    EMobileUpdate info = new EMobileUpdate();
                    info.setTransNox(getAlphaNumericString());
                    info.setClientID(clientID.getValue());
                    info.setReqstCDe(foMobile.getcReqstCde());
                    info.setMobileNo(foMobile.getsMobileNo());
                    info.setPrimaryx(foMobile.getcPrimaryx());
                    info.setRemarksx(foMobile.getsRemarksx());
                    info.setTranStat("");
                    info.setSendStat("0");
                    info.setModified(AppConstants.DATE_MODIFIED);
                    info.setTimeStmp(AppConstants.DATE_MODIFIED);
                    poUpdate.insertUpdateMobile(info);

                    callback.OnSuccessResult(new String[]{"Mobile added into local database."});
                    Log.e(TAG, getValidatedMobilenox(foMobile));
                }
                else {
                    callback.OnFailedResult(foMobile.getMessage());
                }
            } catch (Exception e){
                e.printStackTrace();
                callback.OnFailedResult(e.getMessage());
            }
        }

        public void deleteMobile(String TransNox){
            poUpdate.deleteMobile(TransNox);
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

        private String getAlphaNumericString()
        {
            int charCount = 10;
            // chose a Character random from this String
            String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                    + "0123456789"
                    + "abcdefghijklmnopqrstuvxyz";

            // create StringBuffer size of AlphaNumericString
            StringBuilder sb = new StringBuilder(charCount);

            for (int i = 0; i < charCount; i++) {

                // generate a random number between
                // 0 to AlphaNumericString variable length
                int index
                        = (int)(AlphaNumericString.length()
                        * Math.random());

                // add Character one by one in end of sb
                sb.append(AlphaNumericString
                        .charAt(index));
            }

            return sb.toString();
        }


    }