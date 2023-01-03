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
import org.rmj.g3appdriver.dev.Database.Repositories.RCountry;
import org.rmj.g3appdriver.dev.Database.Repositories.ROccupation;
import org.rmj.g3appdriver.dev.Database.Repositories.RTown;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditApp;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.Employment;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.Means;
import org.rmj.gocas.base.GOCASApplication;

import java.util.List;

public class EmploymentInfo implements CreditApp {
    private static final String TAG = EmploymentInfo.class.getSimpleName();

    private final DCreditApplication poDao;
    private final RTown poTown;
    private final RCountry poCountry;
    private final ROccupation poPosition;

    private Employment poDetail;
    private MeansSelectionInfo poMeans;

    private String message;

    public EmploymentInfo(Application instance) {
        this.poDao = GGC_GriderDB.getInstance(instance).CreditApplicationDao();
        this.poTown = new RTown(instance);
        this.poCountry = new RCountry(instance);
        this.poPosition = new ROccupation(instance);
        this.poMeans = new MeansSelectionInfo(instance);
    }

    @Override
    public LiveData<ECreditApplicantInfo> GetApplication(String args) {
        return poDao.GetApplicantInfo(args);
    }

    @Override
    public Object Parse(ECreditApplicantInfo args) {
        try{
            Employment loDetail = new Employment();
            if(args.getEmplymnt() != null){
                String lsDetail = args.getEmplymnt();
                GOCASApplication gocas = new GOCASApplication();
                JSONParser loJson = new JSONParser();
                JSONObject joDetail = (JSONObject) loJson.parse(lsDetail);
                gocas.MeansInfo().EmployedInfo().setData(joDetail);

                loDetail.setEmploymentSector(gocas.MeansInfo().EmployedInfo().getEmploymentSector());
                loDetail.setUniformPersonal(gocas.MeansInfo().EmployedInfo().IsUniformedPersonel());
                loDetail.setMilitaryPersonal(gocas.MeansInfo().EmployedInfo().IsMilitaryPersonel());
//            loDetail.setOfwRegion("");
                loDetail.setCompanyLevel(gocas.MeansInfo().EmployedInfo().getCompanyLevel());
                loDetail.setEmployeeLevel(gocas.MeansInfo().EmployedInfo().getEmployeeLevel());
//            loDetail.setOfwWorkCategory("");
                loDetail.setCountry(gocas.MeansInfo().EmployedInfo().getOFWNation());

                String lsCountryID = gocas.MeansInfo().EmployedInfo().getOFWNation();
                if(!lsCountryID.isEmpty()) {
                    String lsCountryNm = poCountry.getCountryInfo(lsCountryID).getCntryNme();
                    loDetail.setCountry(lsCountryID);
                    loDetail.setsCountryN(lsCountryNm);
                }
                loDetail.setBusinessNature(gocas.MeansInfo().EmployedInfo().getNatureofBusiness());
                loDetail.setCompanyName(gocas.MeansInfo().EmployedInfo().getCompanyName());
                loDetail.setCompanyAddress(gocas.MeansInfo().EmployedInfo().getCompanyAddress());
                loDetail.setTownID(gocas.MeansInfo().EmployedInfo().getCompanyTown());

                String lsTown = gocas.MeansInfo().EmployedInfo().getCompanyTown();
                DTownInfo.TownProvinceName loTown = poTown.getTownProvinceName(lsTown);

                loDetail.setProvName(loTown.sProvName);
                loDetail.setTownName(loTown.sTownName);
                loDetail.setJobTitle(gocas.MeansInfo().EmployedInfo().getPosition());

                String lsJobID = gocas.MeansInfo().EmployedInfo().getPosition();
                String lsJobNm = poPosition.getOccupationName(lsJobID);

                loDetail.setJobTitle(lsJobID);
                loDetail.setsJobName(lsJobNm);

                loDetail.setSpecificJob(gocas.MeansInfo().EmployedInfo().getJobDescription());
                loDetail.setEmployeeStatus(gocas.MeansInfo().EmployedInfo().getEmployeeStatus());
                double lnLength = gocas.MeansInfo().EmployedInfo().getLengthOfService();
                if(lnLength % 1 == 0){
                    loDetail.setIsYear("1");
                } else {
                    loDetail.setIsYear("0");
                }
                loDetail.setLengthOfService(gocas.MeansInfo().EmployedInfo().getLengthOfService());
                loDetail.setMonthlyIncome(gocas.MeansInfo().EmployedInfo().getSalary());
                loDetail.setContact(gocas.MeansInfo().EmployedInfo().getCompanyNo());
                poDetail = loDetail;
            }

            return loDetail;
        } catch (NullPointerException e){
            e.printStackTrace();
            message = e.getMessage();
            return null;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return null;
        }
    }

    @Override
    public int Validate(Object args) {
        Employment loDetail = (Employment) args;
        if(poDetail == null){
            if(loDetail.isPrimary()){
                if(!loDetail.isDataValid()){
                    message = loDetail.getMessage();
                    return 0;
                }
            }else{
                return 1;
            }
        } else {

            //TODO: if all information inside each old object and new object is not the same,
            // return 2 to indicate validation needs confirmation from user to update the
            // previous information being save.
            if(loDetail.isPrimary()){
                if(!loDetail.isDataValid()){
                    message = loDetail.getMessage();
                    return 0;
                }
            }else{
                return 1;
            }
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
            Employment loDetail = (Employment) args;

            ECreditApplicantInfo loApp = poDao.GetApplicantDetails(loDetail.getTransNox());

            if(loApp == null){
                message = "Unable to find record for update. Please restart credit app and try again.";
                return false;
            }

            GOCASApplication gocas = new GOCASApplication();

            gocas.MeansInfo().EmployedInfo().setEmploymentSector(loDetail.getEmploymentSector());
            gocas.MeansInfo().EmployedInfo().IsUniformedPersonel(loDetail.getUniformPersonal());
            gocas.MeansInfo().EmployedInfo().IsMilitaryPersonel(loDetail.getMilitaryPersonal());
            gocas.MeansInfo().EmployedInfo().setGovernmentLevel(loDetail.getGovermentLevel());
            gocas.MeansInfo().EmployedInfo().setOFWRegion(loDetail.getOfwRegion());
            gocas.MeansInfo().EmployedInfo().setCompanyLevel(loDetail.getCompanyLevel());
            gocas.MeansInfo().EmployedInfo().setEmployeeLevel(loDetail.getEmployeeLevel());
            gocas.MeansInfo().EmployedInfo().setOFWCategory(loDetail.getOfwWorkCategory());
            gocas.MeansInfo().EmployedInfo().setOFWNation(loDetail.getCountry());
            gocas.MeansInfo().EmployedInfo().setNatureofBusiness(loDetail.getBusinessNature());
            gocas.MeansInfo().EmployedInfo().setCompanyName(loDetail.getCompanyName());
            gocas.MeansInfo().EmployedInfo().setCompanyAddress(loDetail.getCompanyAddress());
            gocas.MeansInfo().EmployedInfo().setCompanyTown(loDetail.getTownID());
            gocas.MeansInfo().EmployedInfo().setPosition(loDetail.getJobTitle());
            gocas.MeansInfo().EmployedInfo().setJobDescription(loDetail.getSpecificJob());
            gocas.MeansInfo().EmployedInfo().setEmployeeStatus(loDetail.getEmployeeStatus());
            gocas.MeansInfo().EmployedInfo().setLengthOfService(loDetail.getLengthOfService());
            gocas.MeansInfo().EmployedInfo().setSalary(loDetail.getMonthlyIncome());
            gocas.MeansInfo().EmployedInfo().setCompanyNo(loDetail.getContact());

            loApp.setEmplymnt(gocas.MeansInfo().EmployedInfo().toJSONString());
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
