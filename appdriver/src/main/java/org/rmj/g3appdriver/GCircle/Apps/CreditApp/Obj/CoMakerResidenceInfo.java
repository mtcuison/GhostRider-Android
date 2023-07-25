package org.rmj.g3appdriver.GCircle.Apps.CreditApp.Obj;

import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.CreditApp;
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.model.CoMakerResidence;
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

public class CoMakerResidenceInfo implements CreditApp {
    private static final String TAG = CoMakerResidenceInfo.class.getSimpleName();

    private final DCreditApplication poDao;
    private final Town poTown;
    private final Barangay poBrgy;

    private CoMakerResidence poDetail;

    private String message;

    public CoMakerResidenceInfo(Application instance) {
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
            CoMakerResidence loDetail = new CoMakerResidence();
            if(args.getCmResidx() != null){
                String lsDetail = args.getCmResidx();
                GOCASApplication gocas = new GOCASApplication();
                JSONParser loJson = new JSONParser();
                JSONObject joDetail = (JSONObject) loJson.parse(lsDetail);
                gocas.CoMakerInfo().ResidenceInfo().setData(joDetail);

                loDetail.setLandMark(gocas.CoMakerInfo().ResidenceInfo().PresentAddress().getLandMark());
                loDetail.setHouseNox(gocas.CoMakerInfo().ResidenceInfo().PresentAddress().getHouseNo());
                loDetail.setAddress1(gocas.CoMakerInfo().ResidenceInfo().PresentAddress().getAddress1());
                loDetail.setAddress2(gocas.CoMakerInfo().ResidenceInfo().PresentAddress().getAddress2());

                String lsBrgy = gocas.CoMakerInfo().ResidenceInfo().PresentAddress().getBarangay();
                DBarangayInfo.BrgyTownProvNames loBrgy = poBrgy.getBrgyTownProvName(lsBrgy);

                loDetail.setMunicipalID(gocas.CoMakerInfo().ResidenceInfo().PresentAddress().getTownCity());
                loDetail.setBarangayID(lsBrgy);

                loDetail.setProvinceNm(loBrgy.sProvName);
                loDetail.setMunicipalNm(loBrgy.sTownName);
                loDetail.setBarangayName(loBrgy.sBrgyName);

                //TODO: make a validation of value for length of stay which
                // will display if the applicant stays for a year or only for a month
                double lnLength = gocas.CoMakerInfo().ResidenceInfo().getRentNoYears();

                if(lnLength % 1 == 0){
                    loDetail.setIsYear(1);
                } else {
                    loDetail.setIsYear(0);
                }

                Log.d(TAG, "House Ownership: " + gocas.CoMakerInfo().ResidenceInfo().getOwnership());
                loDetail.setOwnerRelation(gocas.CoMakerInfo().ResidenceInfo().getCareTakerRelation());

                Log.d(TAG, "Length Of Stay: " + gocas.CoMakerInfo().ResidenceInfo().getRentNoYears());
                loDetail.setLenghtOfStay(gocas.CoMakerInfo().ResidenceInfo().getRentNoYears());

                Log.d(TAG, "Monthly Expense: " + gocas.CoMakerInfo().ResidenceInfo().getRentExpenses());
                loDetail.setMonthlyExpenses(gocas.CoMakerInfo().ResidenceInfo().getRentExpenses());

                loDetail.setHouseOwn(gocas.CoMakerInfo().ResidenceInfo().getOwnership());
                if ("0".equals(gocas.CoMakerInfo().ResidenceInfo().getOwnership())) {
                    loDetail.setHouseHold(gocas.CoMakerInfo().ResidenceInfo().getOwnedResidenceInfo());
                } else {
                    loDetail.setHouseHold(gocas.CoMakerInfo().ResidenceInfo().getRentedResidenceInfo());
                }
                loDetail.setHouseType(gocas.CoMakerInfo().ResidenceInfo().getHouseType());
                loDetail.setHasGarage(gocas.CoMakerInfo().ResidenceInfo().hasGarage());

                loDetail.setOwnerRelation(gocas.CoMakerInfo().ResidenceInfo().getOwnership());
                loDetail.setLenghtOfStay(gocas.CoMakerInfo().ResidenceInfo().getRentNoYears());
                loDetail.setMonthlyExpenses(gocas.CoMakerInfo().ResidenceInfo().getRentExpenses());

                poDetail = loDetail;
            }
            return loDetail;
        } catch (NullPointerException e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return null;
        }catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return null;
        }
    }

    @Override
    public int Validate(Object args) {
        CoMakerResidence loDetail = (CoMakerResidence) args;

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
            CoMakerResidence loDetail = (CoMakerResidence) args;

            ECreditApplicantInfo loApp = poDao.GetApplicantDetails(loDetail.getTransNox());

            if(loApp == null){
                message = "Unable to find record for update. Please restart credit app and try again.";
                return null;
            }

            GOCASApplication gocas = new GOCASApplication();
            gocas.CoMakerInfo().ResidenceInfo().PresentAddress().setLandMark(loDetail.getLandMark());
            gocas.CoMakerInfo().ResidenceInfo().PresentAddress().setHouseNo(loDetail.getHouseNox());
            gocas.CoMakerInfo().ResidenceInfo().PresentAddress().setAddress1(loDetail.getAddress1());
            gocas.CoMakerInfo().ResidenceInfo().PresentAddress().setAddress2(loDetail.getAddress2());
            gocas.CoMakerInfo().ResidenceInfo().PresentAddress().setTownCity(loDetail.getMunicipalID());
            gocas.CoMakerInfo().ResidenceInfo().PresentAddress().setBarangay(loDetail.getBarangayID());
            gocas.CoMakerInfo().ResidenceInfo().setOwnership(loDetail.getHouseOwn());
            gocas.CoMakerInfo().ResidenceInfo().setCareTakerRelation(loDetail.getOwnerRelation());
            gocas.CoMakerInfo().ResidenceInfo().setOwnedResidenceInfo(loDetail.getHouseHold());
            gocas.CoMakerInfo().ResidenceInfo().setHouseType(loDetail.getHouseType());
            gocas.CoMakerInfo().ResidenceInfo().setRentedResidenceInfo(loDetail.getHouseHold());
            gocas.CoMakerInfo().ResidenceInfo().setRentExpenses(loDetail.getMonthlyExpenses());
            gocas.CoMakerInfo().ResidenceInfo().setRentNoYears(loDetail.getLenghtofStay());
            gocas.CoMakerInfo().ResidenceInfo().hasGarage(loDetail.getHasGarage());
            Log.d(TAG, gocas.CoMakerInfo().ResidenceInfo().toJSONString());
            loApp.setCmResidx(gocas.CoMakerInfo().ResidenceInfo().toJSONString());
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
