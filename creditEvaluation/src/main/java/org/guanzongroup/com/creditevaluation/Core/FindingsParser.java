package org.guanzongroup.com.creditevaluation.Core;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class FindingsParser {
    private static final String TAG = FindingsParser.class.getSimpleName();

    public static HashMap<oParentFndg, List<oChildFndg>> getForEvaluation(String fsField, String fsLabel, String fsFindings) throws Exception{
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
                        String lsParVal = loParent.getString(laChild.getString(i));
                        if (lsParVal.equalsIgnoreCase("-1") ||
                                lsParVal.equalsIgnoreCase("-1.0")) {
                            oChildFndg loChild = new oChildFndg(poChlLabel.get(i),
                                    laChild.getString(i),
                                    loParent.getString(laChild.getString(i)));
                            poChlFndng.add(loChild);
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
            String lsParVal = loForEval.getString(laParent.getString(x));
            if (lsParVal.equalsIgnoreCase("-1") ||
                    lsParVal.equalsIgnoreCase("-1.0")) {
                oChildFndg loChild = new oChildFndg(poChlLabel.get(x),
                        laParent.getString(x),
                        loForEval.getString(laParent.getString(x)));
                poChlFndng.add(loChild);

                poChild.put(loPrntObj, poChlFndng);
            }
        }
        return poChild;
    }

    private static boolean isJson(String fsVal){
        try{
            JSONObject loJson = new JSONObject(fsVal);
            Log.d(TAG, fsVal + "Parameter is valid JSONObject.");
            return true;
        } catch (Exception e){
            Log.d(TAG, fsVal + "Parameter is invalid JSONObject.");
            return false;
        }
    }

    public static JSONObject scanForEvaluation(String fsLabel, String fsEval) {
        JSONObject params = new JSONObject();
        try {
            JSONArray loDetail = new JSONArray();
            List<String> poParentEvl = new ArrayList<>();
            List<List<String>> poChild = new ArrayList<>();

            JSONObject loForEval = new JSONObject(fsEval);

            JSONObject loForLbel = new JSONObject(fsLabel);

            JSONArray laForLbel = loForLbel.names();

            boolean cNoChild = false;
            for (int lnCtr = 0; lnCtr < Objects.requireNonNull(laForLbel).length(); lnCtr++) {
//                Log.d(TAG, "KEY : " + loForLbel);
//                Log.d(TAG, "KEY : " + laForLbel.getString(lnCtr));
//                Log.d(TAG, "KEY : " + loForLbel.getString(laForLbel.getString(lnCtr)));
                if (loForLbel.isNull(laForLbel.getString(lnCtr))) {
//                    Log.d(TAG, "KEY : " + laForLbel.getString(lnCtr) + " is null");
                } else {
//                    Log.d(TAG, "KEY : " + laForLbel.getString(lnCtr) + " is not null");
                }
                if (!loForLbel.isNull(laForLbel.getString(lnCtr)) &&
                        isJson(loForLbel.getString(laForLbel.getString(lnCtr)))) {
//                    Log.d(TAG, "JSON has child");
                    cNoChild = true;
                    if (!loForLbel.isNull(laForLbel.getString(lnCtr))) {
                        poParentEvl.add(laForLbel.getString(lnCtr));
                        JSONObject loParent = loForLbel.getJSONObject(laForLbel.getString(lnCtr));
                        JSONArray laChild = loParent.names();

                        List<String> poChlLabel = new ArrayList<>();
                        for (int i = 0; i < Objects.requireNonNull(laChild).length(); i++) {
                            poChlLabel.add(loParent.getString(laChild.getString(i)));
                        }
                        poChild.add(poChlLabel);
                    }
                } else if (!loForLbel.isNull(laForLbel.getString(lnCtr)) &&
                        !isJson(loForLbel.getString(laForLbel.getString(lnCtr)))) {
//                    Log.d(TAG, "JSON has no child");
                    cNoChild = false;
                    poParentEvl.add(loForLbel.getString(laForLbel.getString(lnCtr)));
                }
            }
            JSONArray laParent = loForEval.names();
            if (cNoChild) {
//                Log.d(TAG, "Parsing json with child");
                for (int x = 0; x < poParentEvl.size(); x++) {
                    String lsParent = poParentEvl.get(x);

                    for(int i = 0; i < laParent.length(); i++) {

                        if (lsParent.equalsIgnoreCase(Objects.requireNonNull(laParent).getString(i))) {
                            List<String> poChlLabel = poChild.get(x);
                            JSONObject loSub = new JSONObject();

//                            Log.d(TAG, "Parent KEY: " + laParent.getString(i));

                            JSONObject loParent = loForEval.getJSONObject(laParent.getString(i));

//                            Log.d(TAG, "Info for evaluation: " + loParent);

                            JSONArray laChild = loParent.names();

                            JSONArray laSub = new JSONArray();

                            for (int j = 0; j < Objects.requireNonNull(laChild).length(); j++) {
                                String lsParVal = loParent.getString(laChild.getString(j));
//                                Log.d(TAG, lsParVal);
                                if (lsParVal.equalsIgnoreCase("20")) {
                                    JSONObject loJson = new JSONObject();

//                                    Log.d(TAG, "Info for evaluation child key name: " + laChild.getString(j));
//                                    Log.d(TAG, "Info for evaluation label: " + poChlLabel.get(j));
//                                    Log.d(TAG, "Info for evaluation value: " + loParent.getString(laChild.getString(j)));

                                    loJson.put("sKeyNamex", laChild.getString(j));
                                    loJson.put("sLabelxxx", poChlLabel.get(j));
                                    loJson.put("sValuexxx", loParent.getString(laChild.getString(j)));
                                    laSub.put(loJson);
                                } else if (lsParVal.equalsIgnoreCase("10")) {
                                    JSONObject loJson = new JSONObject();

//                                    Log.d(TAG, "Info for evaluation child key name: " + laChild.getString(j));
//                                    Log.d(TAG, "Info for evaluation label: " + poChlLabel.get(j));
//                                    Log.d(TAG, "Info for evaluation value: " + loParent.getString(laChild.getString(j)));

                                    loJson.put("sKeyNamex", laChild.getString(j));
                                    loJson.put("sLabelxxx", poChlLabel.get(j));
                                    loJson.put("sValuexxx", loParent.getString(laChild.getString(j)));
                                    laSub.put(loJson);
                                } else if (lsParVal.equalsIgnoreCase("-1")) {
                                    JSONObject loJson = new JSONObject();

//                                    Log.d(TAG, "Info for evaluation child key name: " + laChild.getString(j));
//                                    Log.d(TAG, "Info for evaluation label: " + poChlLabel.get(j));
//                                    Log.d(TAG, "Info for evaluation value: " + loParent.getString(laChild.getString(j)));

                                    loJson.put("sKeyNamex", laChild.getString(j));
                                    loJson.put("sLabelxxx", poChlLabel.get(j));
                                    loJson.put("sValuexxx", loParent.getString(laChild.getString(j)));
                                    laSub.put(loJson);
                                }
                                loSub.put("category", laParent.getString(i));
                                loSub.put(lsParent, laSub);
                            }
                            loDetail.put(loSub);
                        }
                    }
                }
            } else {
//                Log.d(TAG, "Parsing json without child");
                for (int i = 0; i < Objects.requireNonNull(laParent).length(); i++) {
                    String lsParVal = loForEval.getString(laParent.getString(i));
                    if (!lsParVal.equalsIgnoreCase("NULL") &&
                            !lsParVal.equalsIgnoreCase("0.0")) {
                        JSONObject loJson = new JSONObject();
//                        Log.d(TAG, "Parent list size: " + poParentEvl.size());
//                        Log.d(TAG, "Enumerated parent list: " + laParent.length());
//                        Log.d(TAG, poParentEvl.get(i));
//                        Log.d(TAG, laParent.getString(i));
                        loJson.put("sKeyNamex", laParent.getString(i));
                        loJson.put("sLabelxxx", poParentEvl.get(i));
                        loJson.put("sValuexxx", loForEval.getString(laParent.getString(i)));
                        loDetail.put(loJson);
                    }
                }
            }
            params.put("header", GetHeaderKey(fsEval));
            params.put("detail", loDetail);

//            Log.d(TAG, "For label : " + loForLbel);
//            Log.d(TAG, "For evaluation : " + loForEval);
            Log.d(TAG, params.toString());
        } catch (Exception e){
            e.printStackTrace();
        }
        return params;
    }

    public static List<String> ScanForEvaluationTransferredApplication(String fsLabel, String fsEval) {
        List<String> loList = new ArrayList<>();
//        JSONObject params = new JSONObject();
        try {
            JSONArray loDetail = new JSONArray();
            List<String> poParentEvl = new ArrayList<>();
            List<List<String>> poChild = new ArrayList<>();

            JSONObject loForEval = new JSONObject(fsEval);

            JSONObject loForLbel = new JSONObject(fsLabel);

            JSONArray laForLbel = loForLbel.names();

            boolean cNoChild = false;
            for (int lnCtr = 0; lnCtr < Objects.requireNonNull(laForLbel).length(); lnCtr++) {
//                Log.d(TAG, "KEY : " + loForLbel);
//                Log.d(TAG, "KEY : " + laForLbel.getString(lnCtr));
//                Log.d(TAG, "KEY : " + loForLbel.getString(laForLbel.getString(lnCtr)));
                if (loForLbel.isNull(laForLbel.getString(lnCtr))) {
//                    Log.d(TAG, "KEY : " + laForLbel.getString(lnCtr) + " is null");
                } else {
//                    Log.d(TAG, "KEY : " + laForLbel.getString(lnCtr) + " is not null");
                }
                if (!loForLbel.isNull(laForLbel.getString(lnCtr)) &&
                        isJson(loForLbel.getString(laForLbel.getString(lnCtr)))) {
//                    Log.d(TAG, "JSON has child");
                    cNoChild = true;
                    if (!loForLbel.isNull(laForLbel.getString(lnCtr))) {
                        poParentEvl.add(laForLbel.getString(lnCtr));
                        JSONObject loParent = loForLbel.getJSONObject(laForLbel.getString(lnCtr));
                        JSONArray laChild = loParent.names();

                        List<String> poChlLabel = new ArrayList<>();
                        for (int i = 0; i < Objects.requireNonNull(laChild).length(); i++) {
                            poChlLabel.add(loParent.getString(laChild.getString(i)));
                        }
                        poChild.add(poChlLabel);
                    }
                } else if (!loForLbel.isNull(laForLbel.getString(lnCtr)) &&
                        !isJson(loForLbel.getString(laForLbel.getString(lnCtr)))) {
//                    Log.d(TAG, "JSON has no child");
                    cNoChild = false;
                    poParentEvl.add(loForLbel.getString(laForLbel.getString(lnCtr)));
                }
            }

            JSONArray laParent = loForEval.names();
            if (cNoChild) {
//                Log.d(TAG, "Parsing json with child");
                for (int x = 0; x < poParentEvl.size(); x++) {
                    String lsParent = poParentEvl.get(x);

                    for(int i = 0; i < laParent.length(); i++) {

                        if (lsParent.equalsIgnoreCase(Objects.requireNonNull(laParent).getString(i))) {
                            JSONObject loSub = new JSONObject();

//                            Log.d(TAG, "Parent KEY: " + laParent.getString(i));

                            JSONObject loParent = loForEval.getJSONObject(laParent.getString(i));

//                            Log.d(TAG, "Info for evaluation: " + loParent);

                            JSONArray laChild = loParent.names();

                            JSONArray laSub = new JSONArray();

                            for (int j = 0; j < Objects.requireNonNull(laChild).length(); j++) {
                                String lsParVal = loParent.getString(laChild.getString(j));
//                                Log.d(TAG, lsParVal);
                                if (lsParVal.equalsIgnoreCase("20")) {
                                    Log.d(TAG, "Added to list: " + laChild.getString(j));
                                    loList.add(laChild.getString(j));
                                } else if (lsParVal.equalsIgnoreCase("10")) {
                                    Log.d(TAG, "Added to list: " + laChild.getString(j));
                                    loList.add(laChild.getString(j));
                                }
                                loSub.put("category", laParent.getString(i));
                                loSub.put(lsParent, laSub);
                            }
                            loDetail.put(loSub);
                        }
                    }
                }
            } else {
                for (int i = 0; i < Objects.requireNonNull(laParent).length(); i++) {
                    String lsParVal = loForEval.getString(laParent.getString(i));
                    if (lsParVal.equalsIgnoreCase("10") ||
                            lsParVal.equalsIgnoreCase("20")) {
                        Log.d(TAG, "Added to list: " + laParent.getString(i));
                        loList.add(laParent.getString(i));
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return loList;
    }


    private static String GetHeaderKey(String fsVal) throws JSONException {
        JSONObject loJson = new JSONObject(fsVal);
        if(loJson.has("present_address")){
            return "Address Info";
        } else if(loJson.has("sProprty1")){
            return "Other Info";
        } else {
            return "Means Info";
        }
    }
}
