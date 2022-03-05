package org.guanzongroup.com.creditevaluation.Core;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FindingsParser {

    private final List<JSONObject> poParentLst = new ArrayList<>();
    private List<oChildFndg> poChildLst;
    private final HashMap<oParentFndg, List<oChildFndg>> poChild = new HashMap<>();

    public static HashMap<oParentFndg, List<oChildFndg>> getForEvaluation(String Field, String fsLabel, String fsFindings) throws Exception{
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

            JSONObject loParent = loForLbel.getJSONObject(laForLbel.getString(x));
            JSONArray laChild = loParent.names();
            for(int i = 0; i < laChild.length(); i++){

                //get the value of saddressxx key inside the child json
                poChlLabel.add(loParent.getString(laChild.getString(i)));
            }
        }

        //Parse and Scan the JSON to identify which parameters will be evaluated
        JSONArray laParent = loForEval.names();
        for(int x = 0; x < laParent.length(); x++){
            JSONObject loParent = loForEval.getJSONObject(laParent.getString(x));
            oParentFndg loPrntObj = new oParentFndg(oChildFndg.FIELDS.ADDRESS, laParent.getString(x));
            poParentLst.add(new oParentFndg(oChildFndg.FIELDS.ADDRESS, laParent.getString(x)));
            poChlFndng = new ArrayList<>();
            JSONArray laChild = loParent.names();
            for(int i = 0; i < laChild.length(); i++){

                //Get all value which has negative 1
                if(loParent.getString(laChild.getString(i)).equalsIgnoreCase("-1")) {
                    oChildFndg loChild = new oChildFndg(poChlLabel.get(i),
                            laChild.getString(i),
                            loParent.getString(laChild.getString(i)));
                    poChlFndng.add(loChild);
                }
                poChild.put(loPrntObj, poChlFndng);

            }
        }
        return poChild;
    }


    public static HashMap<oParentFndg, List<oChildFndg>> getWithParent(String Field, String fsLabel, String fsFindings) throws Exception{
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

            JSONObject loParent = loForLbel.getJSONObject(laForLbel.getString(x));
            JSONArray laChild = loParent.names();
            for(int i = 0; i < laChild.length(); i++){

                //get the value of saddressxx key inside the child json
                poChlLabel.add(loParent.getString(laChild.getString(i)));
            }
        }

        //Parse and Scan the JSON to identify which parameters will be evaluated
        JSONArray laParent = loForEval.names();
        for(int x = 0; x < laParent.length(); x++){
            JSONObject loParent = loForEval.getJSONObject(laParent.getString(x));
            oParentFndg loPrntObj = new oParentFndg(oChildFndg.FIELDS.ADDRESS, laParent.getString(x));
            poParentLst.add(new oParentFndg(oChildFndg.FIELDS.ADDRESS, laParent.getString(x)));
            poChlFndng = new ArrayList<>();
            JSONArray laChild = loParent.names();
            for(int i = 0; i < laChild.length(); i++){

                //Get all value which has negative 1
                if(loParent.getString(laChild.getString(i)).equalsIgnoreCase("-1")) {
                    oChildFndg loChild = new oChildFndg(poChlLabel.get(i),
                            laChild.getString(i),
                            loParent.getString(laChild.getString(i)));
                    poChlFndng.add(loChild);
                }
                poChild.put(loPrntObj, poChlFndng);

            }
        }
        return poChild;
    }

    public static HashMap<oParentFndg, List<oChildFndg>> getChild(String Field, String fsLabel, String fsFindings) throws Exception{
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

            JSONObject loParent = loForLbel.getJSONObject(laForLbel.getString(x));
            JSONArray laChild = loParent.names();
            for(int i = 0; i < laChild.length(); i++){

                //get the value of saddressxx key inside the child json
                poChlLabel.add(loParent.getString(laChild.getString(i)));
            }
        }

        //Parse and Scan the JSON to identify which parameters will be evaluated
        JSONArray laParent = loForEval.names();
        for(int x = 0; x < laParent.length(); x++){
            JSONObject loParent = loForEval.getJSONObject(laParent.getString(x));
            oParentFndg loPrntObj = new oParentFndg(oChildFndg.FIELDS.ADDRESS, laParent.getString(x));
            poParentLst.add(new oParentFndg(oChildFndg.FIELDS.ADDRESS, laParent.getString(x)));
            poChlFndng = new ArrayList<>();
            JSONArray laChild = loParent.names();
            for(int i = 0; i < laChild.length(); i++){

                //Get all value which has negative 1
                if(loParent.getString(laChild.getString(i)).equalsIgnoreCase("-1")) {
                    oChildFndg loChild = new oChildFndg(poChlLabel.get(i),
                            laChild.getString(i),
                            loParent.getString(laChild.getString(i)));
                    poChlFndng.add(loChild);
                }
                poChild.put(loPrntObj, poChlFndng);
            }
        }
        return poChild;
    }
}
