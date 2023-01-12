package org.guanzongroup.com.creditevaluation;

import static org.junit.Assert.assertNotNull;

import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.guanzongroup.com.creditevaluation.Core.FindingsParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class CreateTestNewParsing {
    private static final String TAG = CreateTestNewParsing.class.getSimpleName();

    @Before
    public void setup() throws Exception{

    }

    @Test
    public void test01CreateForEvaluation() throws Exception{
        JSONObject params = new JSONObject();
        String lsFrLabel = "{\"present_address\":{\"cAddrType\":\"0\",\"sAddressx\":\"B3 Lt4 Lingueta St., Pio Cruzcosa, Calumpit Bulacan\",\"sAddrImge\":\"\",\"nLatitude\":0.0,\"nLongitud\":0.0},\"primary_address\":{\"cAddrType\":\"1\",\"sAddressx\":\"B3 Lt4 Lingueta St., Pio Cruzcosa, Calumpit Bulacan\",\"sAddrImge\":\"\",\"nLatitude\":0.0,\"nLongitud\":0.0}}";
        String lsForEval = "{\"present_address\":{\"cAddrType\":null,\"sAddressx\":\"-1\",\"sAddrImge\":null,\"nLatitude\":0.0,\"nLongitud\":0.0},\"primary_address\":{\"cAddrType\":null,\"sAddressx\":\"-1\",\"sAddrImge\":null,\"nLatitude\":0.0,\"nLongitud\":0.0}}";
//        String lsFrLabel = "{\"employed\":{\"sEmployer\":\"United Phlp And Phper Inc.\",\"sWrkAddrx\":\"Km 48 Mc Arthur Hi-way Iba Este, Calumpit Bulacan\",\"sPosition\":\"M009680\",\"nLenServc\":23.0,\"nSalaryxx\":35000.0},\"self_employed\":null,\"financed\":null,\"pensioner\":null}";
//        String lsForEval = "{\"employed\":{\"sEmployer\":null,\"sWrkAddrx\":\"-1\",\"sPosition\":null,\"nLenServc\":0.0,\"nSalaryxx\":-1.0},\"self_employed\":{\"sBusiness\":null,\"sBusAddrx\":null,\"nBusLenxx\":0.0,\"nBusIncom\":0.0,\"nMonExpns\":0.0},\"financed\":{\"sFinancer\":null,\"sReltnDsc\":null,\"sCntryNme\":null,\"nEstIncme\":0.0},\"pensioner\":{\"sPensionx\":null,\"nPensionx\":0.0}}";
        List<String> poParentEvl = new ArrayList<>();
        List<String> poChlLabel = new ArrayList<>();

        JSONObject loForEval = new JSONObject(lsForEval);

        JSONObject loForLbel = new JSONObject(lsFrLabel);

        JSONArray laForLbel = loForLbel.names();

        for(int x = 0; x < laForLbel.length(); x++){

            if(!loForLbel.isNull(laForLbel.getString(x))){
                poParentEvl.add(laForLbel.getString(x));
                JSONObject loParent = loForLbel.getJSONObject(laForLbel.getString(x));
                JSONArray laChild = loParent.names();

                for(int i = 0; i < laChild.length(); i++){
                    poChlLabel.add(loParent.getString(laChild.getString(i)));
                }
            }
        }

        JSONArray laParent = loForEval.names();

        for(int x = 0; x < poParentEvl.size(); x++){

            String lsParent = poParentEvl.get(x);

            for(int i = 0; i < laParent.length(); i++){

                if(lsParent.equalsIgnoreCase(laParent.getString(i))){
                    JSONObject loParent = loForEval.getJSONObject(laParent.getString(i));

                    JSONArray laChild = loParent.names();

                    for(int j = 0; j < laChild.length(); j++){
                        String lsParVal = loParent.getString(laChild.getString(j));
                        if(!lsParVal.equalsIgnoreCase("NULL") &&
                                !lsParVal.equalsIgnoreCase("0.0")){
                            JSONObject loJson = new JSONObject();
                            loJson.put("sKeyNamex", laChild.getString(j));
                            loJson.put("sLabelxxx", poChlLabel.get(j));
                            loJson.put("sValuexxx", loParent.getString(laChild.getString(j)));
                            params.put(lsParent, loJson);
                        }
                    }
                }
            }
        }
        Log.d(TAG, "For label : " + loForLbel);
        Log.d(TAG, "For evaluation : " + loForEval);
        Log.d(TAG, params.toString());
        assertNotNull(params);
    }

    @Test
    public void test02CreateForEvaluation() throws Exception{
        JSONObject params = new JSONObject();
        String lsFrLabel = "{\"sProprty1\":\"\",\"sProprty2\":\"\",\"sProprty3\":\"\",\"cWith4Whl\":\"0\",\"cWith3Whl\":\"0\",\"cWith2Whl\":\"0\",\"cWithRefx\":\"1\",\"cWithTVxx\":\"1\",\"cWithACxx\":\"1\"}";
        String lsForEval = "{\"sProprty1\":null,\"sProprty2\":null,\"sProprty3\":null,\"cWith4Whl\":null,\"cWith3Whl\":null,\"cWith2Whl\":null,\"cWithRefx\":\"-1\",\"cWithTVxx\":\"-1\",\"cWithACxx\":null}";
//        String lsFrLabel = "{\"employed\":{\"sEmployer\":\"United Phlp And Phper Inc.\",\"sWrkAddrx\":\"Km 48 Mc Arthur Hi-way Iba Este, Calumpit Bulacan\",\"sPosition\":\"M009680\",\"nLenServc\":23.0,\"nSalaryxx\":35000.0},\"self_employed\":null,\"financed\":null,\"pensioner\":null}";
//        String lsForEval = "{\"employed\":{\"sEmployer\":null,\"sWrkAddrx\":\"-1\",\"sPosition\":null,\"nLenServc\":0.0,\"nSalaryxx\":-1.0},\"self_employed\":{\"sBusiness\":null,\"sBusAddrx\":null,\"nBusLenxx\":0.0,\"nBusIncom\":0.0,\"nMonExpns\":0.0},\"financed\":{\"sFinancer\":null,\"sReltnDsc\":null,\"sCntryNme\":null,\"nEstIncme\":0.0},\"pensioner\":{\"sPensionx\":null,\"nPensionx\":0.0}}";
        List<String> poParentEvl = new ArrayList<>();
        List<String> poChlLabel = new ArrayList<>();

        JSONObject loForEval = new JSONObject(lsForEval);

        JSONObject loForLbel = new JSONObject(lsFrLabel);

        JSONArray laForLbel = loForLbel.names();

        boolean cNoChild = false;
        for(int lnCtr = 0; lnCtr < laForLbel.length(); lnCtr++){
            if(isJson(laForLbel.getString(lnCtr))){
                cNoChild = true;
                if(!loForLbel.isNull(laForLbel.getString(lnCtr))){
                    poParentEvl.add(laForLbel.getString(lnCtr));
                    JSONObject loParent = loForLbel.getJSONObject(laForLbel.getString(lnCtr));
                    JSONArray laChild = loParent.names();

                    for(int i = 0; i < laChild.length(); i++){
                        poChlLabel.add(loParent.getString(laChild.getString(i)));
                    }
                }
            } else {
                cNoChild = false;
                poParentEvl.add(loForLbel.getString(laForLbel.getString(lnCtr)));
            }
        }
        JSONArray laParent = loForEval.names();
        if(cNoChild){
            for(int x = 0; x < poParentEvl.size(); x++){

                String lsParent = poParentEvl.get(x);

                for(int i = 0; i < laParent.length(); i++){

                    if(lsParent.equalsIgnoreCase(laParent.getString(i))){
                        JSONObject loParent = loForEval.getJSONObject(laParent.getString(i));

                        JSONArray laChild = loParent.names();

                        for(int j = 0; j < laChild.length(); j++){
                            String lsParVal = loParent.getString(laChild.getString(j));
                            if(!lsParVal.equalsIgnoreCase("NULL") &&
                                    !lsParVal.equalsIgnoreCase("0.0")){
                                JSONObject loJson = new JSONObject();
                                loJson.put("sKeyNamex", laChild.getString(j));
                                loJson.put("sLabelxxx", poChlLabel.get(j));
                                loJson.put("sValuexxx", loParent.getString(laChild.getString(j)));
                                params.put(laParent.getString(x), loJson);
                            }
                        }
                    }
                }
            }
        } else {
            JSONObject loJson = new JSONObject();
            for(int i = 0; i < laParent.length(); i++) {
                String lsParVal = loForEval.getString(laParent.getString(i));
                if (!lsParVal.equalsIgnoreCase("NULL") &&
                        !lsParVal.equalsIgnoreCase("0.0")) {
                    Log.d(TAG, poParentEvl.get(i));
                    Log.d(TAG, laParent.getString(i));
                    loJson.put("sKeyNamex", laParent.getString(i));
                    loJson.put("sLabelxxx", poParentEvl.get(i));
                    loJson.put("sValuexxx", loForEval.getString(laParent.getString(i)));
                    params.put(laParent.getString(i), loJson);
                }
            }
        }

        Log.d(TAG, "For label : " + loForLbel);
        Log.d(TAG, "For evaluation : " + loForEval);
        Log.d(TAG, params.toString());
        assertNotNull(params);
    }

    @Test
    public void test03CreateForEvaluation() throws Exception{
        JSONObject params = new JSONObject();
//        String lsFrLabel = "{\"present_address\":{\"cAddrType\":\"0\",\"sAddressx\":\"B3 Lt4 Lingueta St., Pio Cruzcosa, Calumpit Bulacan\",\"sAddrImge\":\"\",\"nLatitude\":0.0,\"nLongitud\":0.0},\"primary_address\":{\"cAddrType\":\"1\",\"sAddressx\":\"B3 Lt4 Lingueta St., Pio Cruzcosa, Calumpit Bulacan\",\"sAddrImge\":\"\",\"nLatitude\":0.0,\"nLongitud\":0.0}}";
//        String lsForEval = "{\"present_address\":{\"cAddrType\":null,\"sAddressx\":\"-1\",\"sAddrImge\":null,\"nLatitude\":0.0,\"nLongitud\":0.0},\"primary_address\":{\"cAddrType\":null,\"sAddressx\":\"-1\",\"sAddrImge\":null,\"nLatitude\":0.0,\"nLongitud\":0.0}}";
        String lsFrLabel = "{\"sProprty1\":\"\",\"sProprty2\":\"\",\"sProprty3\":\"\",\"cWith4Whl\":\"0\",\"cWith3Whl\":\"0\",\"cWith2Whl\":\"0\",\"cWithRefx\":\"1\",\"cWithTVxx\":\"1\",\"cWithACxx\":\"1\"}";
        String lsForEval = "{\"sProprty1\":null,\"sProprty2\":null,\"sProprty3\":null,\"cWith4Whl\":null,\"cWith3Whl\":null,\"cWith2Whl\":null,\"cWithRefx\":\"-1\",\"cWithTVxx\":\"-1\",\"cWithACxx\":null}";
//        String lsFrLabel = "{\"employed\":{\"sEmployer\":\"United Phlp And Phper Inc.\",\"sWrkAddrx\":\"Km 48 Mc Arthur Hi-way Iba Este, Calumpit Bulacan\",\"sPosition\":\"M009680\",\"nLenServc\":23.0,\"nSalaryxx\":35000.0},\"self_employed\":null,\"financed\":null,\"pensioner\":null}";
//        String lsForEval = "{\"employed\":{\"sEmployer\":null,\"sWrkAddrx\":\"-1\",\"sPosition\":null,\"nLenServc\":0.0,\"nSalaryxx\":-1.0},\"self_employed\":{\"sBusiness\":null,\"sBusAddrx\":null,\"nBusLenxx\":0.0,\"nBusIncom\":0.0,\"nMonExpns\":0.0},\"financed\":{\"sFinancer\":null,\"sReltnDsc\":null,\"sCntryNme\":null,\"nEstIncme\":0.0},\"pensioner\":{\"sPensionx\":null,\"nPensionx\":0.0}}";

        List<String> poParentEvl = new ArrayList<>();
        List<String> poChlLabel = new ArrayList<>();

        JSONObject loForEval = new JSONObject(lsForEval);

        JSONObject loForLbel = new JSONObject(lsFrLabel);

        JSONArray laForLbel = loForLbel.names();

        boolean cNoChild = false;
        for(int lnCtr = 0; lnCtr < laForLbel.length(); lnCtr++){
            Log.d(TAG, "KEY : " + loForLbel);
            Log.d(TAG, "KEY : " + laForLbel.getString(lnCtr));
            Log.d(TAG, "KEY : " + loForLbel.getString(laForLbel.getString(lnCtr)));
            if(loForLbel.isNull(laForLbel.getString(lnCtr))){
                Log.d(TAG, "KEY : " + laForLbel.getString(lnCtr) + " is null");
            } else {
                Log.d(TAG, "KEY : " + laForLbel.getString(lnCtr) + " is not null");
            }
            if(!loForLbel.isNull(laForLbel.getString(lnCtr)) &&
                    isJson(loForLbel.getString(laForLbel.getString(lnCtr)))){
                Log.d(TAG, "JSON has child");
                cNoChild = true;
                if(!loForLbel.isNull(laForLbel.getString(lnCtr))){
                    poParentEvl.add(laForLbel.getString(lnCtr));
                    JSONObject loParent = loForLbel.getJSONObject(laForLbel.getString(lnCtr));
                    JSONArray laChild = loParent.names();

                    for(int i = 0; i < laChild.length(); i++){
                        poChlLabel.add(loParent.getString(laChild.getString(i)));
                    }
                }
            } else if(!loForLbel.isNull(laForLbel.getString(lnCtr)) &&
                    !isJson(loForLbel.getString(laForLbel.getString(lnCtr)))) {
                Log.d(TAG, "JSON has no child");
                cNoChild = false;
                poParentEvl.add(loForLbel.getString(laForLbel.getString(lnCtr)));
            }
        }
        JSONArray laParent = loForEval.names();
        if(cNoChild){
            Log.d(TAG, "Parsing json with child");
            JSONArray loDetail = new JSONArray();
            for(int x = 0; x < poParentEvl.size(); x++){

                String lsParent = poParentEvl.get(x);

                if(lsParent.equalsIgnoreCase(laParent.getString(x))){
                    JSONObject loSub = new JSONObject();
                    Log.d(TAG, "Parent KEY: " + laParent.getString(x));

                    JSONObject loParent = loForEval.getJSONObject(laParent.getString(x));

                    JSONArray laChild = loParent.names();

                    for(int j = 0; j < laChild.length(); j++){
                        String lsParVal = loParent.getString(laChild.getString(j));
                        Log.d(TAG, lsParVal);
                        if(!lsParVal.equalsIgnoreCase("NULL") &&
                                !lsParVal.equalsIgnoreCase("0.0")){
                            JSONObject loJson = new JSONObject();
                            loJson.put("sKeyNamex", laChild.getString(j));
                            loJson.put("sLabelxxx", poChlLabel.get(j));
                            loJson.put("sValuexxx", loParent.getString(laChild.getString(j)));

                            loSub.put(lsParent, loJson);
                        }
                    }
                    loDetail.put(loSub);
                }
            }
            params.put(GetHeaderKey(lsForEval), loDetail);
        } else {
            Log.d(TAG, "Parsing json without child");
            JSONObject loJson = new JSONObject();
            JSONArray loDetail = new JSONArray();
            for(int i = 0; i < laParent.length(); i++) {
                String lsParVal = loForEval.getString(laParent.getString(i));
                if (!lsParVal.equalsIgnoreCase("NULL") &&
                        !lsParVal.equalsIgnoreCase("0.0")) {
                    Log.d(TAG, poParentEvl.get(i));
                    Log.d(TAG, laParent.getString(i));
                    loJson.put("sKeyNamex", laParent.getString(i));
                    loJson.put("sLabelxxx", poParentEvl.get(i));
                    loJson.put("sValuexxx", loForEval.getString(laParent.getString(i)));
                    loDetail.put(loJson);
                }
            }
            params.put(GetHeaderKey(lsForEval), loDetail);
        }

        Log.d(TAG, "For label : " + loForLbel);
        Log.d(TAG, "For evaluation : " + loForEval);
        Log.d(TAG, params.toString());
        assertNotNull(params);
    }

    @Test
    public void test04CreateForEvaluation() throws Exception{
        String lsFrLabel = "{\"sProprty1\":\"\",\"sProprty2\":\"\",\"sProprty3\":\"\",\"cWith4Whl\":\"0\",\"cWith3Whl\":\"0\",\"cWith2Whl\":\"0\",\"cWithRefx\":\"1\",\"cWithTVxx\":\"1\",\"cWithACxx\":\"1\"}";
        String lsForEval = "{\"sProprty1\":null,\"sProprty2\":null,\"sProprty3\":null,\"cWith4Whl\":null,\"cWith3Whl\":null,\"cWith2Whl\":null,\"cWithRefx\":\"-1\",\"cWithTVxx\":\"-1\",\"cWithACxx\":null}";
        String lsFrLabel1 = "{\"employed\":{\"sEmployer\":\"United Phlp And Phper Inc.\",\"sWrkAddrx\":\"Km 48 Mc Arthur Hi-way Iba Este, Calumpit Bulacan\",\"sPosition\":\"M009680\",\"nLenServc\":23.0,\"nSalaryxx\":35000.0},\"self_employed\":null,\"financed\":null,\"pensioner\":null}";
        String lsForEval1 = "{\"employed\":{\"sEmployer\":null,\"sWrkAddrx\":\"-1\",\"sPosition\":null,\"nLenServc\":0.0,\"nSalaryxx\":-1.0},\"self_employed\":{\"sBusiness\":null,\"sBusAddrx\":null,\"nBusLenxx\":0.0,\"nBusIncom\":0.0,\"nMonExpns\":0.0},\"financed\":{\"sFinancer\":null,\"sReltnDsc\":null,\"sCntryNme\":null,\"nEstIncme\":0.0},\"pensioner\":{\"sPensionx\":null,\"nPensionx\":0.0}}";
        String lsFrLabel2 = "{\"present_address\":{\"cAddrType\":\"0\",\"sAddressx\":\"B3 Lt4 Lingueta St., Pio Cruzcosa, Calumpit Bulacan\",\"sAddrImge\":\"\",\"nLatitude\":0.0,\"nLongitud\":0.0},\"primary_address\":{\"cAddrType\":\"1\",\"sAddressx\":\"B3 Lt4 Lingueta St., Pio Cruzcosa, Calumpit Bulacan\",\"sAddrImge\":\"\",\"nLatitude\":0.0,\"nLongitud\":0.0}}";
        String lsForEval2 = "{\"present_address\":{\"cAddrType\":null,\"sAddressx\":\"-1\",\"sAddrImge\":null,\"nLatitude\":0.0,\"nLongitud\":0.0},\"primary_address\":{\"cAddrType\":null,\"sAddressx\":\"-1\",\"sAddrImge\":null,\"nLatitude\":0.0,\"nLongitud\":0.0}}";
//        JSONObject params = new JSONObject();
//        params.put("Assets", FindingsParser.parseToJsonData(lsFrLabel, lsForEval));
//        params.put("Means", FindingsParser.parseToJsonData(lsFrLabel1, lsForEval1));
//        params.put("Address", FindingsParser.parseToJsonData(lsFrLabel2, lsForEval2));
//
        JSONArray laEval = new JSONArray();
        laEval.put(FindingsParser.scanForEvaluation(lsFrLabel, lsForEval));
        laEval.put(FindingsParser.scanForEvaluation(lsFrLabel1, lsForEval1));
        laEval.put(FindingsParser.scanForEvaluation(lsFrLabel2, lsForEval2));
        Log.d(TAG, laEval.toString());
        assertNotNull(laEval);
    }

    private boolean isJson(String fsVal){
        try{
            JSONObject loJson = new JSONObject(fsVal);
            Log.d(TAG, fsVal + "Parameter is valid JSONObject.");
            return true;
        } catch (Exception e){
            e.printStackTrace();
            Log.d(TAG, fsVal + "Parameter is invalid JSONObject.");
            return false;
        }
    }

    private String GetHeaderKey(String fsVal) throws JSONException {
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
