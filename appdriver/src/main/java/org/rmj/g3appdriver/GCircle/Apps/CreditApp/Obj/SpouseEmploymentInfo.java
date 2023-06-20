package org.rmj.g3appdriver.GCircle.Apps.CreditApp.Obj;

import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.CreditApp;
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.model.SpouseEmployments;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DCreditApplication;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EBarangayInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.ECountryInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EOccupationInfo;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.g3appdriver.lib.Etc.Country;
import org.rmj.g3appdriver.GCircle.room.Repositories.ROccupation;
import org.rmj.g3appdriver.lib.Etc.Town;
import org.rmj.gocas.base.GOCASApplication;

import java.util.List;

public class SpouseEmploymentInfo implements CreditApp {
    private static final String TAG = SpouseEmploymentInfo.class.getSimpleName();

    private final DCreditApplication poDao;
    private final Town poTown;
    private final Country poCountry;
    private final ROccupation poPosition;

    private SpouseEmployments poDetail;

    private String message;

    public SpouseEmploymentInfo(Application instance) {
        this.poDao = GGC_GCircleDB.getInstance(instance).CreditApplicationDao();
        this.poTown = new Town(instance);
        this.poCountry = new Country(instance);
        this.poPosition = new ROccupation(instance);
    }

    @Override
    public LiveData<ECreditApplicantInfo> GetApplication(String args) {
        return poDao.GetApplicantInfo(args);
    }

    @Override
    public Object Parse(ECreditApplicantInfo args) {
        try{
            SpouseEmployments loDetail = new SpouseEmployments();
            if (args.getSpsEmplx() != null){

                String lsDetail = args.getSpsEmplx();
                GOCASApplication gocas = new GOCASApplication();
                JSONParser loJson = new JSONParser();
                JSONObject joDetail = (JSONObject) loJson.parse(lsDetail);
                gocas.SpouseMeansInfo().EmployedInfo().setData(joDetail);

                loDetail.setEmploymentSector(gocas.SpouseMeansInfo().EmployedInfo().getEmploymentSector());
                loDetail.setUniformPersonal(gocas.SpouseMeansInfo().EmployedInfo().IsUniformedPersonel());
                loDetail.setMilitaryPersonal(gocas.SpouseMeansInfo().EmployedInfo().IsMilitaryPersonel());
//            loDetail.setOfwRegion("");
                loDetail.setCompanyLevel(gocas.SpouseMeansInfo().EmployedInfo().getCompanyLevel());
                loDetail.setEmployeeLevel(gocas.SpouseMeansInfo().EmployedInfo().getEmployeeLevel());
//            loDetail.setOfwWorkCategory("");
                loDetail.setCountry(gocas.SpouseMeansInfo().EmployedInfo().getOFWNation());

                String lsCountryID = gocas.SpouseMeansInfo().EmployedInfo().getOFWNation();
                if(!lsCountryID.isEmpty()) {
                    String lsCountryNm = poCountry.getCountryInfo(lsCountryID).getCntryNme();
                    loDetail.setCountry(lsCountryID);
                    loDetail.setsCountryN(lsCountryNm);
                }
                loDetail.setBusinessNature(gocas.SpouseMeansInfo().EmployedInfo().getNatureofBusiness());
                loDetail.setCompanyName(gocas.SpouseMeansInfo().EmployedInfo().getCompanyName());
                loDetail.setCompanyAddress(gocas.SpouseMeansInfo().EmployedInfo().getCompanyAddress());
                loDetail.setTownID(gocas.SpouseMeansInfo().EmployedInfo().getCompanyTown());

                String lsTown = gocas.SpouseMeansInfo().EmployedInfo().getCompanyTown();
                DTownInfo.TownProvinceName loTown = poTown.getTownProvinceName(lsTown);

                loDetail.setProvName(loTown.sProvName);
                loDetail.setTownName(loTown.sTownName);
                loDetail.setJobTitle(gocas.SpouseMeansInfo().EmployedInfo().getPosition());

                String lsJobID = gocas.SpouseMeansInfo().EmployedInfo().getPosition();
                String lsJobNm = poPosition.getOccupationName(lsJobID);

                loDetail.setJobTitle(lsJobID);
                loDetail.setsJobName(lsJobNm);

                loDetail.setSpecificJob(gocas.SpouseMeansInfo().EmployedInfo().getJobDescription());
                loDetail.setEmployeeStatus(gocas.SpouseMeansInfo().EmployedInfo().getEmployeeStatus());
                double lnLength = gocas.SpouseMeansInfo().EmployedInfo().getLengthOfService();
                if(lnLength % 1 == 0){
                    loDetail.setIsYear("1");
                } else {
                    loDetail.setIsYear("0");
                }
                loDetail.setLengthOfService(gocas.SpouseMeansInfo().EmployedInfo().getLengthOfService());
                loDetail.setMonthlyIncome(gocas.SpouseMeansInfo().EmployedInfo().getSalary());
                loDetail.setContact(gocas.SpouseMeansInfo().EmployedInfo().getCompanyNo());
                poDetail = loDetail;
            }
            return loDetail;
        } catch (NullPointerException e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return null;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return null;
        }
    }

    @Override
    public int Validate(Object args) {
        SpouseEmployments loDetail = (SpouseEmployments) args;

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
            SpouseEmployments loDetail = (SpouseEmployments) args;

            ECreditApplicantInfo loApp = poDao.GetApplicantDetails(loDetail.getTransNox());

            if(loApp == null){
                message = "Unable to find record for update. Please restart credit app and try again.";
                return null;
            }

            GOCASApplication gocas = new GOCASApplication();

            gocas.SpouseMeansInfo().EmployedInfo().setEmploymentSector(loDetail.getEmploymentSector());
            gocas.SpouseMeansInfo().EmployedInfo().IsUniformedPersonel(loDetail.getUniformPersonal());
            gocas.SpouseMeansInfo().EmployedInfo().IsMilitaryPersonel(loDetail.getMilitaryPersonal());
            gocas.SpouseMeansInfo().EmployedInfo().setGovernmentLevel(loDetail.getGovermentLevel());
            gocas.SpouseMeansInfo().EmployedInfo().setOFWRegion(loDetail.getOfwRegion());
            gocas.SpouseMeansInfo().EmployedInfo().setCompanyLevel(loDetail.getCompanyLevel());
            gocas.SpouseMeansInfo().EmployedInfo().setEmployeeLevel(loDetail.getEmployeeLevel());
            gocas.SpouseMeansInfo().EmployedInfo().setOFWCategory(loDetail.getOfwWorkCategory());
            gocas.SpouseMeansInfo().EmployedInfo().setOFWNation(loDetail.getCountry());
            gocas.SpouseMeansInfo().EmployedInfo().setNatureofBusiness(loDetail.getBusinessNature());
            gocas.SpouseMeansInfo().EmployedInfo().setCompanyName(loDetail.getCompanyName());
            gocas.SpouseMeansInfo().EmployedInfo().setCompanyAddress(loDetail.getCompanyAddress());
            gocas.SpouseMeansInfo().EmployedInfo().setCompanyTown(loDetail.getTownID());
            gocas.SpouseMeansInfo().EmployedInfo().setPosition(loDetail.getJobTitle());
            gocas.SpouseMeansInfo().EmployedInfo().setJobDescription(loDetail.getSpecificJob());
            gocas.SpouseMeansInfo().EmployedInfo().setEmployeeStatus(loDetail.getEmployeeStatus());
            gocas.SpouseMeansInfo().EmployedInfo().setLengthOfService(loDetail.getLengthOfService());
            gocas.SpouseMeansInfo().EmployedInfo().setSalary(loDetail.getMonthlyIncome());
            gocas.SpouseMeansInfo().EmployedInfo().setCompanyNo(loDetail.getContact());

            loApp.setSpsEmplx(gocas.SpouseMeansInfo().EmployedInfo().toJSONString());
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
        return null;
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
        return poPosition.getAllOccupationInfo();
    }
}
