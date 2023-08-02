package org.rmj.g3appdriver.GCircle.Apps.CreditApp.Obj;

import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.CreditApp;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DCreditApplication;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EBarangayInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.ECountryInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EOccupationInfo;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.gocas.base.GOCASApplication;

import java.util.List;

public class ApplicationInfo implements CreditApp {
    private static final String TAG = ApplicationInfo.class.getSimpleName();

    private final DCreditApplication poDao;

    private String message;

    public ApplicationInfo(Application instance) {
        this.poDao = GGC_GCircleDB.getInstance(instance).CreditApplicationDao();
    }

    @Override
    public LiveData<ECreditApplicantInfo> GetApplication(String args) {
        return poDao.GetApplicantInfo(args);
    }

    @Override
    public Object Parse(ECreditApplicantInfo args) {
        try {
            ECreditApplicantInfo loDetail = (ECreditApplicantInfo) args;

            GOCASApplication loMaster = new GOCASApplication();

            JSONParser loJson = new JSONParser();

            loMaster.ApplicantInfo().setData((JSONObject) loJson.parse(loDetail.getApplInfo()));
            loMaster.ResidenceInfo().setData((JSONObject) loJson.parse(loDetail.getResidnce()));
            loMaster.MeansInfo().setData((JSONObject) loJson.parse(loDetail.getOtherInc()));
            loMaster.MeansInfo().EmployedInfo().setData((JSONObject) loJson.parse(loDetail.getEmplymnt()));
            loMaster.MeansInfo().SelfEmployedInfo().setData((JSONObject) loJson.parse(loDetail.getBusnInfo()));
            loMaster.MeansInfo().FinancerInfo().setData((JSONObject) loJson.parse(loDetail.getFinancex()));
            loMaster.MeansInfo().PensionerInfo().setData((JSONObject) loJson.parse(loDetail.getPensionx()));
            loMaster.SpouseInfo().PersonalInfo().setData((JSONObject) loJson.parse(loDetail.getSpousexx()));
            loMaster.SpouseInfo().ResidenceInfo().setData((JSONObject) loJson.parse(loDetail.getResidnce()));
            loMaster.SpouseMeansInfo().setData((JSONObject) loJson.parse(loDetail.getSpOthInc()));
            loMaster.SpouseMeansInfo().EmployedInfo().setData((JSONObject) loJson.parse(loDetail.getSpsEmplx()));
            loMaster.SpouseMeansInfo().SelfEmployedInfo().setData((JSONObject) loJson.parse(loDetail.getSpsBusnx()));
            loMaster.SpouseMeansInfo().PensionerInfo().setData((JSONObject) loJson.parse(loDetail.getSpsPensn()));
            loMaster.DisbursementInfo().setData((JSONObject) loJson.parse(loDetail.getDisbrsmt()));
            loMaster.DisbursementInfo().DependentInfo().setData((JSONObject) loJson.parse(loDetail.getDependnt()));
            loMaster.DisbursementInfo().PropertiesInfo().setData((JSONObject) loJson.parse(loDetail.getProperty()));
            loMaster.OtherInfo().setData((JSONObject) loJson.parse(loDetail.getOthrInfo()));
            loMaster.CoMakerInfo().setData((JSONObject) loJson.parse(loDetail.getComakerx()));
            loMaster.CoMakerInfo().ResidenceInfo().setData((JSONObject) loJson.parse(loDetail.getCmResidx()));

            Log.d(TAG, loMaster.toJSONString());
            return loMaster;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return null;
        }
    }

    @Override
    public int Validate(Object args) {
        return 0;
    }

    @Override
    public String Save(Object args) {
        try {
            ECreditApplicantInfo loDetail = (ECreditApplicantInfo) args;

            GOCASApplication loMaster = new GOCASApplication();

            JSONParser loJson = new JSONParser();

            loMaster.ApplicantInfo().setData((JSONObject) loJson.parse(loDetail.getApplInfo()));
            loMaster.ResidenceInfo().setData((JSONObject) loJson.parse(loDetail.getResidnce()));
            loMaster.MeansInfo().setData((JSONObject) loJson.parse(loDetail.getOtherInc()));
            loMaster.MeansInfo().EmployedInfo().setData((JSONObject) loJson.parse(loDetail.getEmplymnt()));
            loMaster.MeansInfo().SelfEmployedInfo().setData((JSONObject) loJson.parse(loDetail.getBusnInfo()));
            loMaster.MeansInfo().FinancerInfo().setData((JSONObject) loJson.parse(loDetail.getFinancex()));
            loMaster.MeansInfo().PensionerInfo().setData((JSONObject) loJson.parse(loDetail.getPensionx()));
            loMaster.SpouseInfo().PersonalInfo().setData((JSONObject) loJson.parse(loDetail.getSpousexx()));
            loMaster.SpouseInfo().ResidenceInfo().setData((JSONObject) loJson.parse(loDetail.getResidnce()));
            loMaster.SpouseMeansInfo().setData((JSONObject) loJson.parse(loDetail.getSpOthInc()));
            loMaster.SpouseMeansInfo().EmployedInfo().setData((JSONObject) loJson.parse(loDetail.getSpsEmplx()));
            loMaster.SpouseMeansInfo().SelfEmployedInfo().setData((JSONObject) loJson.parse(loDetail.getSpsBusnx()));
            loMaster.SpouseMeansInfo().PensionerInfo().setData((JSONObject) loJson.parse(loDetail.getSpsPensn()));
            loMaster.DisbursementInfo().setData((JSONObject) loJson.parse(loDetail.getDisbrsmt()));
            loMaster.DisbursementInfo().DependentInfo().setData((JSONObject) loJson.parse(loDetail.getDependnt()));
            loMaster.DisbursementInfo().PropertiesInfo().setData((JSONObject) loJson.parse(loDetail.getProperty()));
            loMaster.OtherInfo().setData((JSONObject) loJson.parse(loDetail.getOthrInfo()));
            loMaster.CoMakerInfo().setData((JSONObject) loJson.parse(loDetail.getComakerx()));
            loMaster.CoMakerInfo().ResidenceInfo().setData((JSONObject) loJson.parse(loDetail.getCmResidx()));

            Log.d(TAG, loMaster.toJSONString());
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
