package org.rmj.g3appdriver.lib.integsys.CreditApp.Obj;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DBarangayInfo;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DCreditApplication;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EBarangayInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECountryInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EOccupationInfo;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;
import org.rmj.g3appdriver.dev.Database.Repositories.RBarangay;
import org.rmj.g3appdriver.dev.Database.Repositories.RTown;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditApp;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.ClientResidence;
import org.rmj.gocas.base.GOCASApplication;

import java.util.List;

public class ResidenceInfo implements CreditApp {
    private static final String TAG = ResidenceInfo.class.getSimpleName();

    private final DCreditApplication poDao;
    private final RTown poTown;
    private final RBarangay poBrgy;

    private ClientResidence poDetail;

    private String message;

    public ResidenceInfo(Application instance) {
        this.poDao = GGC_GriderDB.getInstance(instance).CreditApplicationDao();
        this.poTown = new RTown(instance);
        this.poBrgy = new RBarangay(instance);
    }

    @Override
    public LiveData<ECreditApplicantInfo> GetApplication(String args) {
        return poDao.GetApplicantInfo(args);
    }

    @Override
    public Object Parse(ECreditApplicantInfo args) {
        try{
            String lsDetail = args.getResidnce();
            GOCASApplication gocas = new GOCASApplication();
            JSONParser loJson = new JSONParser();
            JSONObject joDetail = (JSONObject) loJson.parse(lsDetail);
            gocas.ResidenceInfo().setData(joDetail);

            ClientResidence loDetail = new ClientResidence();
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

            loDetail.setOwnerRelation(gocas.ResidenceInfo().getOwnership());
            loDetail.setLenghtOfStay(gocas.ResidenceInfo().getRentNoYears());
            loDetail.setMonthlyExpenses(gocas.ResidenceInfo().getRentExpenses());

            //TODO: make a validation of value for length of stay which
            // will display if the applicant stays for a year or only for a month
            double lnLength = gocas.ResidenceInfo().getRentNoYears();

            if(lnLength % 1 == 0){
                loDetail.setIsYear(1);
            } else {
                loDetail.setIsYear(0);
            }

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
            return loDetail;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
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
    public boolean Save(Object args) {
        try{
            ClientResidence loDetail = (ClientResidence) args;

            ECreditApplicantInfo loApp = poDao.GetApplicantDetails(loDetail.getTransNox());

            if(loApp == null){
                message = "Unable to find record for update. Please restart credit app and try again.";
                return false;
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
            loApp.setResidnce(gocas.ResidenceInfo().toJSONString());
            if(loDetail.isOneAddress()){
                loApp.setSameAddx("1");
            } else {
                loApp.setSameAddx("0");
            }
            poDao.Update(loApp);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
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
