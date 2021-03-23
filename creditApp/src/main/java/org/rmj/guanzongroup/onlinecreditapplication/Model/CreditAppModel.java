package org.rmj.guanzongroup.onlinecreditapplication.Model;

import android.util.Log;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;
import org.rmj.gocas.base.GOCASApplication;

public class CreditAppModel {
    private static final String TAG = CreditAppModel.class.getSimpleName();

    private final GOCASApplication poGOCas;
    private final ECreditApplicantInfo poInfo;

    public CreditAppModel(ECreditApplicantInfo foInfo){
        this.poInfo = foInfo;
        this.poGOCas = new GOCASApplication();
    }

    public void createDetailInfo(){
        try {
            JSONParser loParser = new JSONParser();
            poGOCas.ApplicantInfo().setData((JSONObject) loParser.parse(poInfo.getApplInfo()));
            poGOCas.ResidenceInfo().setData((JSONObject) loParser.parse(poInfo.getResidnce()));

            // get json object from Credit_Applicant_Info column sAppMeans
            // to check which field of means info is not null to avoid NullPointerException...
            org.json.JSONObject loJson = new org.json.JSONObject(poInfo.getAppMeans());
            if(loJson.getString("employed").equalsIgnoreCase("1")){
                poGOCas.MeansInfo().EmployedInfo().setData((JSONObject) loParser.parse(poInfo.getEmplymnt()));
            }
            if(loJson.getString("sEmplyed").equalsIgnoreCase("1")){
                poGOCas.MeansInfo().SelfEmployedInfo().setData((JSONObject) loParser.parse(poInfo.getBusnInfo()));
            }
            if(loJson.getString("financer").equalsIgnoreCase("1")){
                poGOCas.MeansInfo().FinancerInfo().setData((JSONObject) loParser.parse(poInfo.getFinancex()));
            }
            if(loJson.getString("pensionx").equalsIgnoreCase("1")){
                poGOCas.MeansInfo().PensionerInfo().setData((JSONObject)loParser.parse(poInfo.getPensionx()));
            }

            //TODO: set other income source and range of income...

            if(poInfo.getIsSpouse().equalsIgnoreCase("1")){
                poGOCas.SpouseInfo().PersonalInfo().setData((JSONObject) loParser.parse(poInfo.getSpousexx()));
                poGOCas.SpouseInfo().ResidenceInfo().setData((JSONObject) loParser.parse(poInfo.getSpsResdx()));
                poGOCas.SpouseMeansInfo().EmployedInfo().setData((JSONObject) loParser.parse(poInfo.getSpsEmplx()));
                poGOCas.SpouseMeansInfo().SelfEmployedInfo().setData((JSONObject) loParser.parse(poInfo.getSpsBusnx()));
                poGOCas.SpouseMeansInfo().PensionerInfo().setData((JSONObject) loParser.parse(poInfo.getPensionx()));
            }

            GOCASApplication loGOCas = new GOCASApplication();
            loGOCas.DisbursementInfo().setData((JSONObject) loParser.parse(poInfo.getDisbrsmt()));

            poGOCas.DisbursementInfo().Expenses().setData(loGOCas.DisbursementInfo().Expenses().toJSON());
            poGOCas.DisbursementInfo().BankAccount().setData(loGOCas.DisbursementInfo().Expenses().toJSON());
            poGOCas.DisbursementInfo().CreditCard().setData(loGOCas.DisbursementInfo().Expenses().toJSON());

            poGOCas.DisbursementInfo().DependentInfo().setData((JSONObject) loParser.parse(poInfo.getDependnt()));
            poGOCas.DisbursementInfo().PropertiesInfo().setData((JSONObject) loParser.parse(poInfo.getProperty()));
            poGOCas.OtherInfo().setData((JSONObject) loParser.parse(poInfo.getOthrInfo()));

            if(poInfo.getIsComakr().equalsIgnoreCase("1")){
                poGOCas.CoMakerInfo().setData((JSONObject) loParser.parse(poInfo.getComakerx()));
                poGOCas.CoMakerInfo().ResidenceInfo().setData((JSONObject) loParser.parse(poInfo.getCmResidx()));
            }
            Log.e(TAG, poGOCas.toJSONString());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getConstructedDetailedInfo(){
        return poGOCas.toJSONString();
    }
}
