package org.guanzongroup.com.creditevaluation.Core;

import android.annotation.SuppressLint;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditOnlineApplicationCI;
import org.rmj.g3appdriver.GRider.Etc.FormatUIText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PreviewParser {

    private final List<JSONObject> poParentLst = new ArrayList<>();
    private List<oChildFndg> poChildLst;
    private final HashMap<oParentFndg, List<oChildFndg>> poChild = new HashMap<>();

    @SuppressLint("NewApi")
    public static List<oPreview> getCIResultPreview(ECreditOnlineApplicationCI foDetail){
        List<oPreview> loResult = new ArrayList<>();
        try{
            HashMap<oParentFndg, List<oChildFndg>> loDetail = parseToPreviewResult(foDetail);
            loDetail.forEach((loParent, loChild) ->{
                for(int x = 0; x < loChild.size(); x++){
                    String lsValue = loChild.get(x).getValue();
                    if(lsValue.equalsIgnoreCase("10")){
                        lsValue = "Incorrect";
                    } else {
                        lsValue = "Correct";
                    }
                    loResult.add(new oPreview(loParent.getTitle(),
                            loParent.getParentDescript(),
                            loChild.get(x).getLabel(),
                            lsValue));
                }
            });
            if(foDetail.getHasRecrd().equalsIgnoreCase("1")){
                loResult.add(new oPreview("Additional Info", "Barangay Record", "Has Record", "YES"));
                loResult.add(new oPreview("", "", "Record/Issue", foDetail.getRecrdRem()));
            } else {
                loResult.add(new oPreview("", "Barangay Record", "Has Record", "NO"));
            }
            loResult.add(new oPreview("", "Barangay Record", "Brgy Official Name", foDetail.getPrsnBrgy()));
            loResult.add(new oPreview("", "Barangay Record", "Brgy Official Position", foDetail.getPrsnPstn()));
            loResult.add(new oPreview("", "Barangay Record", "Brgy Official Contact", foDetail.getPrsnNmbr()));
            loResult.add(new oPreview("", "Barangay Record", "Neighbor 1", foDetail.getNeighBr1()));
            loResult.add(new oPreview("", "Barangay Record", "Neighbor 2", foDetail.getNeighBr2()));
            loResult.add(new oPreview("", "Barangay Record", "Neighbor 3", foDetail.getNeighBr3()));
            loResult.add(new oPreview("Evaluation Info", "", "Date Receive by CI", FormatUIText.formatGOCasBirthdate(foDetail.getRcmdRcd1())));
            loResult.add(new oPreview("", "", "Evaluation Date", FormatUIText.formatGOCasBirthdate(foDetail.getRcmdtnx1())));
            if(foDetail.getRcmdtnc1().equalsIgnoreCase("1")){
                loResult.add(new oPreview("", "", "CI Recommendation", "Approve"));
            } else {
                loResult.add(new oPreview("", "", "CI Recommendation", "Disapproved"));
            }
            loResult.add(new oPreview("", "", "Approval/Disapproval Remarks", foDetail.getRcmdtns1()));
        } catch (Exception e){
            e.printStackTrace();
        }
        return loResult;
    }

    private static HashMap<oParentFndg, List<oChildFndg>> parseToPreviewResult(ECreditOnlineApplicationCI foDetail) throws Exception{
        HashMap<oParentFndg, List<oChildFndg>> loForEval = new HashMap<>();

        loForEval.putAll(PreviewParser.getForPreviewResult(oChildFndg.FIELDS.ADDRESS, foDetail.getAddressx(), foDetail.getAddrFndg()));
        loForEval.putAll(PreviewParser.getForPreviewResult(oChildFndg.FIELDS.MEANS, foDetail.getIncomexx(), foDetail.getIncmFndg()));
        loForEval.putAll(PreviewParser.getForPreviewResult(oChildFndg.FIELDS.ASSETS, foDetail.getAssetsxx(), foDetail.getAsstFndg()));

        return loForEval;
    }

    private static HashMap<oParentFndg, List<oChildFndg>> getForPreviewResult(String fsField, String fsLabel, String fsFindings) throws Exception{
        switch (fsField){
            case oChildFndg.FIELDS.ADDRESS:
            case oChildFndg.FIELDS.MEANS:
                return getWithParent(fsField, fsLabel, fsFindings);
            default:
                return getChild(fsField, fsLabel, fsFindings);
        }
    }

    private static HashMap<oParentFndg, List<oChildFndg>> getWithParent(String Field, String fsLabel, String fsFindings) throws Exception{
        List<String> poParentEvl = new ArrayList<>();

        List<oParentFndg> poParentLst = new ArrayList<>();
        List<oChildFndg> poChlFndng;

        //Arraylist for storing labels which will be displayed on UI for confirmation
        List<String> poChlLabel = new ArrayList<>();
        HashMap<oParentFndg, List<oChildFndg>> poChild = new HashMap<>();

        //Parse Json for Evaluation
        JSONObject loForEval = new JSONObject(fsFindings);

        //Parse Json for Label
        JSONObject loForLbel = new JSONObject(fsLabel);

        // Get the list of keys in parent JSONObject
        JSONArray laForLbel = loForLbel.names();

        // Scan the JSON Inside the parent JSON
        for(int x = 0; x < laForLbel.length(); x++) {

            //Check if the child json on parent is notNull
            if (!loForLbel.isNull(laForLbel.getString(x))){
                poParentEvl.add(laForLbel.getString(x));
                JSONObject loParent = loForLbel.getJSONObject(laForLbel.getString(x));
                JSONArray laChild = loParent.names();
                for (int i = 0; i < laChild.length(); i++) {
                    //get the value of sAddressx key inside the child json
                    poChlLabel.add(loParent.getString(laChild.getString(i)));
                }
            }
        }

        //Parse and Scan the JSON to identify which parameters will be evaluated

        JSONArray laParent = loForEval.names();
        for (int j = 0; j < poParentEvl.size(); j++){

            String lsParentNme = poParentEvl.get(j);

            for(int x = 0; x < laParent.length(); x++){

                if(lsParentNme.equalsIgnoreCase(laParent.getString(x))) {
                    JSONObject loParent = loForEval.getJSONObject(laParent.getString(x));
                    oParentFndg loPrntObj = new oParentFndg(Field, laParent.getString(x));
                    poParentLst.add(new oParentFndg(Field, laParent.getString(x)));
                    poChlFndng = new ArrayList<>();
                    JSONArray laChild = loParent.names();

                    for (int i = 0; i < laChild.length(); i++) {

                        //Get all value which has negative 1
//                        if (loParent.getString(laChild.getString(i)).equalsIgnoreCase("1") ||
////                                    loParent.getString(laChild.getString(i)).equalsIgnoreCase("0") ||
////                                    Double.parseDouble(loParent.getString(laChild.getString(x)))>=0) {
////                                oChildFndg loChild = new oChildFndg(poChlLabel.get(i),
////                                        laChild.getString(i),
////                                        loParent.getString(laChild.getString(i)));
////                                poChlFndng.add(loChild);
////                            }
                        if(!poChlLabel.get(i).isEmpty()){
                            if(isNumericSpace(poChlLabel.get(i))){
                                if (loParent.getString(laChild.getString(i)).equalsIgnoreCase("20") ||
                                        loParent.getString(laChild.getString(i)).equalsIgnoreCase("10") ||
                                        Double.parseDouble(poChlLabel.get(i)) > 0) {
                                    oChildFndg loChild = new oChildFndg(poChlLabel.get(i),
                                            laChild.getString(i),
                                            loParent.getString(laChild.getString(i)));
                                    poChlFndng.add(loChild);
                                }
                            }else{
                                if (loParent.getString(laChild.getString(i)).equalsIgnoreCase("20") ||
                                        loParent.getString(laChild.getString(i)).equalsIgnoreCase("10")) {
                                    oChildFndg loChild = new oChildFndg(poChlLabel.get(i),
                                            laChild.getString(i),
                                            loParent.getString(laChild.getString(i)));
                                    poChlFndng.add(loChild);
                                }
                            }
                        }

                        poChild.put(loPrntObj, poChlFndng);
                    }
                }
            }
        }
        return poChild;
    }

    private static HashMap<oParentFndg, List<oChildFndg>> getChild(String Field, String fsLabel, String fsFindings) throws Exception{
        List<oChildFndg> poChlFndng;
        List<String> poChlLabel = new ArrayList<>();
        HashMap<oParentFndg, List<oChildFndg>> poChild = new HashMap<>();

        //Parse Json for Evaluation
        JSONObject loForEval = new JSONObject(fsFindings);

        //Parse Json for Label
        JSONObject loForLbel = new JSONObject(fsLabel);
        JSONArray laForLbel = loForLbel.names();
        for(int x = 0; x < laForLbel.length(); x++) {
            poChlLabel.add(loForLbel.getString(laForLbel.getString(x)));
        }

        JSONArray laParent = loForEval.names();
        for(int x = 0; x < laParent.length(); x++) {
            poChlFndng = new ArrayList<>();
            oParentFndg loPrntObj = new oParentFndg(Field, null);
            if (loForEval.getString(laParent.getString(x)).equalsIgnoreCase("20") ||
                loForEval.getString(laParent.getString(x)).equalsIgnoreCase("10")) {
                oChildFndg loChild = new oChildFndg(poChlLabel.get(x),
                        laParent.getString(x),
                        loForEval.getString(laParent.getString(x)));
                poChlFndng.add(loChild);

                poChild.put(loPrntObj, poChlFndng);
            }

        }
        return poChild;
    }
    private static boolean isNumericSpace(String str){
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

}
