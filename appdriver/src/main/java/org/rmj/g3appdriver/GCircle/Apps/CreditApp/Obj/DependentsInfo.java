package org.rmj.g3appdriver.GCircle.Apps.CreditApp.Obj;

import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.CreditApp;
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.model.Dependent;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DCreditApplication;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EBarangayInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.ECountryInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EOccupationInfo;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.g3appdriver.lib.Etc.Town;
import org.rmj.gocas.base.GOCASApplication;

import java.util.ArrayList;
import java.util.List;

public class DependentsInfo implements CreditApp {
    private static final String TAG = DependentsInfo.class.getSimpleName();

    private final DCreditApplication poDao;
    private final Town poTown;

    private Dependent poDetail;

    private String message;

    public DependentsInfo(Application instance) {
        this.poDao = GGC_GCircleDB.getInstance(instance).CreditApplicationDao();
        this.poTown = new Town(instance);
    }

    @Override
    public LiveData<ECreditApplicantInfo> GetApplication(String args) {
        return poDao.GetApplicantInfo(args);
    }

    @Override
    public Object Parse(ECreditApplicantInfo args) {
        try{
            Dependent loDetail = new Dependent();
            if(args.getDependnt() != null){
                String lsDetail = args.getDependnt();
                GOCASApplication gocas = new GOCASApplication();
                JSONParser loJson = new JSONParser();
                JSONObject joDetail = (JSONObject) loJson.parse(lsDetail);
                gocas.DisbursementInfo().DependentInfo().setData(joDetail);
                JSONArray loList = (JSONArray) joDetail.get("children");
                List<Dependent.DependentInfo> loDpds = new ArrayList<>();
                if(loList.size() > 0){
                    for(int x = 0; x < loList.size(); x++){
                        JSONObject loChildren = (JSONObject) loList.get(x);
                        Dependent.DependentInfo loInfo = new Dependent.DependentInfo();
                        loInfo.setFullName((String) loChildren.get("sFullName"));
                        loInfo.setRelation((String) loChildren.get("sRelatnCD"));
                        long lnAge = (long) loChildren.get("nDepdAgex");
                        loInfo.setDpdntAge((int) lnAge);
                        loInfo.setStudentx((String) loChildren.get("cIsPupilx"));
                        loInfo.setSchoolNm((String) loChildren.get("sSchlName"));
                        loInfo.setSchlAddx((String) loChildren.get("sSchlAddr"));
                        loInfo.setSchlTown((String) loChildren.get("sSchlTown"));
                        loInfo.setSchoolTp((String) loChildren.get("cIsPrivte"));
                        loInfo.setEduLevel((String) loChildren.get("sEducLevl"));
                        loInfo.setSchoolar((String) loChildren.get("cIsSchlrx"));
                        loInfo.setEmployed((String) loChildren.get("cHasWorkx"));
                        loInfo.setEmpSctor((String) loChildren.get("cWorkType"));
                        loInfo.setCompName((String) loChildren.get("sCompanyx"));
                        loInfo.setHouseHld((String) loChildren.get("cHouseHld"));
                        loInfo.setDependnt((String) loChildren.get("cDependnt"));
                        loInfo.setMarriedx((String) loChildren.get("cIsMarrdx"));
                        loDpds.add(loInfo);
                    }
                }

                loDetail.setDependentList(loDpds);
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
    public String Save(Object args) {
        try{
            Dependent loDetail = (Dependent) args;

            ECreditApplicantInfo loApp = poDao.GetApplicantDetails(loDetail.getTransNox());

            if(loApp == null){
                message = "Unable to find record for update. Please restart credit app and try again.";
                return null;
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
        return null;
    }
}
