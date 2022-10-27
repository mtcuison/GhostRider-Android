package org.rmj.g3appdriver.lib.integsys.CreditApp.Obj;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DCreditApplication;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EBarangayInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECountryInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EOccupationInfo;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditApp;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.Dependent;
import org.rmj.gocas.base.GOCASApplication;

import java.util.List;

public class DependentsInfo implements CreditApp {
    private static final String TAG = DependentsInfo.class.getSimpleName();

    private final DCreditApplication poDao;

    private Dependent poDetail;

    private String message;

    public DependentsInfo(Application instance) {
        this.poDao = GGC_GriderDB.getInstance(instance).CreditApplicationDao();
    }

    @Override
    public LiveData<ECreditApplicantInfo> GetApplication(String args) {
        return poDao.GetApplicantInfo(args);
    }

    @Override
    public Object Parse(ECreditApplicantInfo args) {
        return null;
    }

    @Override
    public int Validate(Object args) {
        Dependent loDetail = (Dependent) args;

        if(poDetail == null){

//            if(!loDetail.isDataValid()){
//                message = loDetail.getMessage();
//                return 0;
//            }

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
            Dependent loDetail = (Dependent) args;

            ECreditApplicantInfo loApp = poDao.GetApplicantDetails(loDetail.getTransNox());

            if(loApp == null){
                message = "Unable to find record for update. Please restart credit app and try again.";
                return false;
            }

            GOCASApplication gocas = new GOCASApplication();

            List<Dependent.DependentInfo> loList = loDetail.getDependentList();

            for (int x = 0; x < loList.size(); x++) {
                Dependent.DependentInfo loInfo = loList.get(x);
                gocas.DisbursementInfo().DependentInfo().addDependent();
                gocas.DisbursementInfo().DependentInfo().setFullName(x, loInfo.getFullName());
                gocas.DisbursementInfo().DependentInfo().setRelation(x, loInfo.getRelation());
                gocas.DisbursementInfo().DependentInfo().setAge(x, loInfo.getDpdntAge());
                gocas.DisbursementInfo().DependentInfo().IsStudent(x, loInfo.getStudentx());
                gocas.DisbursementInfo().DependentInfo().IsWorking(x, loInfo.getEmployed());
                gocas.DisbursementInfo().DependentInfo().IsDependent(x, loInfo.getDependnt());
                gocas.DisbursementInfo().DependentInfo().IsHouseHold(x, loInfo.getHouseHld());
                gocas.DisbursementInfo().DependentInfo().IsMarried(x, loInfo.getMarriedx());
                gocas.DisbursementInfo().DependentInfo().setSchoolName(x, loInfo.getSchoolNm());
                gocas.DisbursementInfo().DependentInfo().setSchoolAddress(x, loInfo.getSchlAddx());
                gocas.DisbursementInfo().DependentInfo().setSchoolTown(x, loInfo.getSchlTown());
                gocas.DisbursementInfo().DependentInfo().setEducationalLevel(x, loInfo.getEduLevel());
                gocas.DisbursementInfo().DependentInfo().IsPrivateSchool(x, loInfo.getSchoolTp());
                gocas.DisbursementInfo().DependentInfo().IsScholar(x, loInfo.getSchoolar());
                gocas.DisbursementInfo().DependentInfo().IsWorking(x, loInfo.getEmployed());
                gocas.DisbursementInfo().DependentInfo().setWorkType(x, loInfo.getEmpSctor());
                gocas.DisbursementInfo().DependentInfo().setCompany(x, loInfo.getCompName());
            }

            loApp.setDependnt(gocas.DisbursementInfo().DependentInfo().toJSONString());
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
        return null;
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
