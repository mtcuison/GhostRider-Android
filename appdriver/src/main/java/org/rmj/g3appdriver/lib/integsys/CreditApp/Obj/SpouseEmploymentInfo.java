package org.rmj.g3appdriver.lib.integsys.CreditApp.Obj;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DCreditApplication;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EBarangayInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECountryInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EOccupationInfo;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;
import org.rmj.g3appdriver.dev.Database.Repositories.RBarangay;
import org.rmj.g3appdriver.dev.Database.Repositories.RCountry;
import org.rmj.g3appdriver.dev.Database.Repositories.ROccupation;
import org.rmj.g3appdriver.dev.Database.Repositories.RTown;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditApp;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.Employment;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.SpouseEmployments;
import org.rmj.gocas.base.GOCASApplication;

import java.util.List;

public class SpouseEmploymentInfo implements CreditApp {
    private static final String TAG = SpouseEmploymentInfo.class.getSimpleName();

    private final DCreditApplication poDao;
    private final RTown poTown;
    private final RCountry poCountry;
    private final ROccupation poPosition;

    private SpouseEmployments poDetail;

    private String message;

    public SpouseEmploymentInfo(Application instance) {
        this.poDao = GGC_GriderDB.getInstance(instance).CreditApplicationDao();
        this.poTown = new RTown(instance);
        this.poCountry = new RCountry(instance);
        this.poPosition = new ROccupation(instance);
    }

    @Override
    public LiveData<ECreditApplicantInfo> GetApplication(String args) {
        return poDao.GetApplicantInfo(args);
    }

    @Override
    public Object Parse(ECreditApplicantInfo args) {
        try{
            String lsDetail = args.getSpsEmplx();
            GOCASApplication gocas = new GOCASApplication();
            JSONParser loJson = new JSONParser();
            JSONObject joDetail = (JSONObject) loJson.parse(lsDetail);
            gocas.SpouseMeansInfo().EmployedInfo().setData(joDetail);

            SpouseEmployments loDetail = new SpouseEmployments();
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
            return loDetail;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
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
    public boolean Save(Object args) {
        try{
            SpouseEmployments loDetail = (SpouseEmployments) args;

            ECreditApplicantInfo loApp = poDao.GetApplicantDetails(loDetail.getTransNox());

            if(loApp == null){
                message = "Unable to find record for update. Please restart credit app and try again.";
                return false;
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
