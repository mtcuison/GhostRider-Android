package org.rmj.g3appdriver.GCircle.Apps.CreditApp.Obj;

import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.CreditApp;
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.model.ClientResidence;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DBarangayInfo;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DCreditApplication;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EBarangayInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.ECountryInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EOccupationInfo;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.g3appdriver.lib.Etc.Barangay;
import org.rmj.g3appdriver.lib.Etc.Town;
import org.rmj.gocas.base.GOCASApplication;

import java.util.List;

public class ResidenceInfo implements CreditApp {
    private static final String TAG = ResidenceInfo.class.getSimpleName();

    private final DCreditApplication poDao;
    private final Town poTown;
    private final Barangay poBrgy;

    private ClientResidence poDetail;

    private String message;

    public ResidenceInfo(Application instance) {
        this.poDao = GGC_GCircleDB.getInstance(instance).CreditApplicationDao();
        this.poTown = new Town(instance);
        this.poBrgy = new Barangay(instance);
    }

    @Override
    public LiveData<ECreditApplicantInfo> GetApplication(String args) {
        return poDao.GetApplicantInfo(args);
    }

    @Override
    public Object Parse(ECreditApplicantInfo args) {
        try{
            ClientResidence loDetail = new ClientResidence();
            if(args.getResidnce() != null){
                String lsDetail = args.getResidnce();
                GOCASApplication gocas = new GOCASApplication();
                JSONParser loJson = new JSONParser();
                JSONObject joDetail = (JSONObject) loJson.parse(lsDetail);
                gocas.ResidenceInfo().setData(joDetail);

                loDetail.setOneAddress(args.getSameAddx().equalsIgnoreCase("1"));
                loDetail.setLandMark(gocas.ResidenceInfo().PresentAddress().getLandMark());
                loDetail.setHouseNox(gocas.ResidenceInfo().PresentAddress().getHouseNo());
                loDetail.setAddress1(gocas.ResidenceInfo().PresentAddress().getAddress1());
                loDetail.setAddress2(gocas.ResidenceInfo().PresentAddress().getAddress2());

                String lsBrgy = gocas.ResidenceInfo().PresentAddress().getBarangay();
                DBarangayInfo.BrgyTownProvNames loBrgy = poBrgy.getBrgyTownProvName(lsBrgy);

                loDetail.setMunicipalID(gocas.ResidenceInfo().PresentAddress().getTownCity());
                loDetail.setBarangayID(lsBrgy);

                loDetail.setProvinceNm(loBrgy.sProvName);
                loDetail.setMunicipalNm(loBrgy.sTownName);
                loDetail.setBarangayName(loBrgy.sBrgyName);

                //TODO: make a validation of value for length of stay which
                // will display if the applicant stays for a year or only for a month
                double lnLength = gocas.ResidenceInfo().getRentNoYears();

                if(lnLength % 1 == 0){
                    loDetail.setIsYear(1);
                } else {
                    loDetail.setIsYear(0);
                }

                Log.d(TAG, "House Ownership: " + gocas.ResidenceInfo().getOwnership());
                loDetail.setOwnerRelation(gocas.ResidenceInfo().getCareTakerRelation());

                Log.d(TAG, "Length Of Stay: " + gocas.ResidenceInfo().getRentNoYears());
                loDetail.setLenghtOfStay(gocas.ResidenceInfo().getRentNoYears());

                Log.d(TAG, "Monthly Expense: " + gocas.ResidenceInfo().getRentExpenses());
                loDetail.setMonthlyExpenses(gocas.ResidenceInfo().getRentExpenses());

                loDetail.setHouseOwn(gocas.ResidenceInfo().getOwnership());

                if ("0".equals(gocas.ResidenceInfo().getOwnership())) {
                    loDetail.setHouseHold(gocas.ResidenceInfo().getOwnedResidenceInfo());
                } else {
                    loDetail.setHouseHold(gocas.ResidenceInfo().getRentedResidenceInfo());
                }
                loDetail.setHouseType(gocas.ResidenceInfo().getHouseType());
                loDetail.setHasGarage(gocas.ResidenceInfo().hasGarage());

                loDetail.setPermanentLandMark(gocas.ResidenceInfo().PermanentAddress().getLandMark());
                loDetail.setPermanentHouseNo(gocas.ResidenceInfo().PermanentAddress().getHouseNo());
                loDetail.setPermanentAddress1(gocas.ResidenceInfo().PermanentAddress().getAddress1());
                loDetail.setPermanentAddress2(gocas.ResidenceInfo().PermanentAddress().getAddress2());

                lsBrgy = gocas.ResidenceInfo().PermanentAddress().getBarangay();
                loBrgy = poBrgy.getBrgyTownProvName(lsBrgy);

                loDetail.setPermanentMunicipalID(gocas.ResidenceInfo().PermanentAddress().getTownCity());
                loDetail.setPermanentBarangayID(lsBrgy);

                loDetail.setPermanentProvinceNm(loBrgy.sProvName);
                loDetail.setPermanentMunicipalNm(loBrgy.sTownName);
                loDetail.setPermanentBarangayName(loBrgy.sBrgyName);

                poDetail = loDetail;
            }
            return loDetail;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return null;
        }
    }

    @Override
    public int Validate(Object args) {
        ClientResidence loDetail = (ClientResidence) args;

        if(poDetail == null){
            if(!loDetail.isDataValid()){
                message = loDetail.getMessage();
                return 0;
            }

        } else {

            //TODO: if all information inside each old object and new object is not the same,
            // return 2 to indicate validation needs confirmation from user to update the
            // previous information being save.

//            if(!poDetail.isEqual(loDetail)){
//                return 2;
//            } else {
//                return 1;
//            }
        }

        return 1;
    }

    @Override
    public String Save(Object args) {
        try{
            ClientResidence loDetail = (ClientResidence) args;

            ECreditApplicantInfo loApp = poDao.GetApplicantDetails(loDetail.getTransNox());

            if(loApp == null){
                message = "Unable to find record for update. Please restart credit app and try again.";
                return null;
            }

            GOCASApplication gocas = new GOCASApplication();

            gocas.ResidenceInfo().PresentAddress().setLandMark(loDetail.getLandMark());
            gocas.ResidenceInfo().PresentAddress().setHouseNo(loDetail.getHouseNox());
            gocas.ResidenceInfo().PresentAddress().setAddress1(loDetail.getAddress1());
            gocas.ResidenceInfo().PresentAddress().setAddress2(loDetail.getAddress2());
            gocas.ResidenceInfo().PresentAddress().setTownCity(loDetail.getMunicipalID());
            gocas.ResidenceInfo().PresentAddress().setBarangay(loDetail.getBarangayID());
            gocas.ResidenceInfo().setOwnership(loDetail.getHouseOwn());
            gocas.ResidenceInfo().setCareTakerRelation(loDetail.getOwnerRelation());
            gocas.ResidenceInfo().setOwnedResidenceInfo(loDetail.getHouseHold());
            gocas.ResidenceInfo().setHouseType(loDetail.getHouseType());
            gocas.ResidenceInfo().setRentedResidenceInfo(loDetail.getHouseHold());
            gocas.ResidenceInfo().setRentExpenses(loDetail.getMonthlyExpenses());
            gocas.ResidenceInfo().setRentNoYears(loDetail.getLenghtofStay());
            gocas.ResidenceInfo().hasGarage(loDetail.getHasGarage());
            gocas.ResidenceInfo().PermanentAddress().setLandMark(loDetail.getPermanentLandMark());
            gocas.ResidenceInfo().PermanentAddress().setHouseNo(loDetail.getPermanentHouseNo());
            gocas.ResidenceInfo().PermanentAddress().setAddress1(loDetail.getPermanentAddress1());
            gocas.ResidenceInfo().PermanentAddress().setAddress2(loDetail.getPermanentAddress2());
            gocas.ResidenceInfo().PermanentAddress().setTownCity(loDetail.getPermanentMunicipalID());
            gocas.ResidenceInfo().PermanentAddress().setBarangay(loDetail.getPermanentBarangayID());
            Log.d(TAG, gocas.ResidenceInfo().toJSONString());
            loApp.setResidnce(gocas.ResidenceInfo().toJSONString());
            if(loDetail.isOneAddress()){
                loApp.setSameAddx("1");

            } else {
                loApp.setSameAddx("0");
            }
            poDao.Update(loApp);
            return loDetail.getTransNox();
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return null;
        }
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public LiveData<List<EBarangayInfo>> GetBarangayList(String args) {
        return poBrgy.getAllBarangayFromTown(args);
    }

    @Override
    public LiveData<List<DTownInfo.TownProvinceInfo>> GetTownProvinceList() {
        return poTown.getTownProvinceInfo();
    }

    @Override
    public LiveData<List<ECountryInfo>> GetCountryList() {
        return null;
    }

    @Override
    public LiveData<List<EOccupationInfo>> GetOccupations() {
        return null;
    }
}
