package org.guanzongroup.com.creditevaluation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import androidx.dynamicanimation.animation.FloatValueHolder;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.guanzongroup.com.creditevaluation.Core.FindingsParser;
import org.guanzongroup.com.creditevaluation.Core.oChildFndg;
import org.guanzongroup.com.creditevaluation.Core.oParentFndg;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class JSONParsingTest {

    @Before
    public void setup() throws Exception{

    }

    @Test
    public void test01ForEvaluationWithParent() throws Exception{
        String lsFrLabel = "{\"present_address\":{\"cAddrType\":\"0\",\"sAddressx\":\"B3 Lt4 Lingueta St., Pio Cruzcosa, Calumpit Bulacan\",\"sAddrImge\":\"\",\"nLatitude\":0.0,\"nLongitud\":0.0},\"primary_address\":{\"cAddrType\":\"1\",\"sAddressx\":\"B3 Lt4 Lingueta St., Pio Cruzcosa, Calumpit Bulacan\",\"sAddrImge\":\"\",\"nLatitude\":0.0,\"nLongitud\":0.0}}";
        String lsForEval = "{\"present_address\":{\"cAddrType\":null,\"sAddressx\":\"-1\",\"sAddrImge\":null,\"nLatitude\":0.0,\"nLongitud\":0.0},\"primary_address\":{\"cAddrType\":null,\"sAddressx\":\"-1\",\"sAddrImge\":null,\"nLatitude\":0.0,\"nLongitud\":0.0}}";

        HashMap<oParentFndg, List<oChildFndg>> loForEval = FindingsParser.getForEvaluation(oChildFndg.FIELDS.ADDRESS, lsFrLabel, lsForEval);

        assertNotNull(loForEval);
        assertTrue(loForEval.size() > 0);
    }

    @Test
    public void test02ForEvaluationChildOnly() throws Exception{
        String lsFrLabel = "{\"sProprty1\":\"\",\"sProprty2\":\"\",\"sProprty3\":\"\",\"cWith4Whl\":\"0\",\"cWith3Whl\":\"0\",\"cWith2Whl\":\"0\",\"cWithRefx\":\"1\",\"cWithTVxx\":\"1\",\"cWithACxx\":\"1\"}";
        String lsForEval = "{\"sProprty1\":null,\"sProprty2\":null,\"sProprty3\":null,\"cWith4Whl\":null,\"cWith3Whl\":null,\"cWith2Whl\":null,\"cWithRefx\":\"-1\",\"cWithTVxx\":\"-1\",\"cWithACxx\":null}";

        HashMap<oParentFndg, List<oChildFndg>> loForEval = FindingsParser.getForEvaluation(oChildFndg.FIELDS.ASSETS, lsFrLabel, lsForEval);

        assertNotNull(loForEval);
        assertTrue(loForEval.size() > 0);
    }

    @Test
    public void test03ForEvaluationWithNullChild() throws Exception{
        String lsFrLabel = "{\"employed\":{\"sEmployer\":\"United Phlp And Phper Inc.\",\"sWrkAddrx\":\"Km 48 Mc Arthur Hi-way Iba Este, Calumpit Bulacan\",\"sPosition\":\"M009680\",\"nLenServc\":23.0,\"nSalaryxx\":35000.0},\"self_employed\":null,\"financed\":null,\"pensioner\":null}";
        String lsForEval = "{\"employed\":{\"sEmployer\":\"NULL\",\"sWrkAddrx\":\"NULL\",\"sPosition\":\"NULL\",\"nLenServc\":-1.0,\"nSalaryxx\":-1.0},\"self_employed\":{\"sBusiness\":\"NULL\",\"sBusAddrx\":\"NULL\",\"nBusLenxx\":-1.0,\"nBusIncom\":-1.0,\"nMonExpns\":0.0},\"financed\":{\"sFinancer\":\"NULL\",\"sReltnDsc\":\"NULL\",\"sCntryNme\":\"NULL\",\"nEstIncme\":-1.0},\"pensioner\":{\"sPensionx\":\"NULL\",\"nPensionx\":-1.0}}";

        HashMap<oParentFndg, List<oChildFndg>> loForEval = FindingsParser.getForEvaluation(oChildFndg.FIELDS.MEANS, lsFrLabel, lsForEval);

        assertNotNull(loForEval);
        assertTrue(loForEval.size() > 0);
    }

    @Test
    public void test04ForEvaluationWithNull() throws Exception{
        String lsFrLabel = "{\"employed\":{\"sEmployer\":\"United Phlp And Phper Inc.\",\"sWrkAddrx\":\"Km 48 Mc Arthur Hi-way Iba Este, Calumpit Bulacan\",\"sPosition\":\"M009680\",\"nLenServc\":23.0,\"nSalaryxx\":35000.0},\"self_employed\":null,\"financed\":null,\"pensioner\":null}";
        String lsForEval = "{\"employed\":{\"sEmployer\":null,\"sWrkAddrx\":\"-1\",\"sPosition\":null,\"nLenServc\":0.0,\"nSalaryxx\":-1.0},\"self_employed\":{\"sBusiness\":null,\"sBusAddrx\":null,\"nBusLenxx\":0.0,\"nBusIncom\":0.0,\"nMonExpns\":0.0},\"financed\":{\"sFinancer\":null,\"sReltnDsc\":null,\"sCntryNme\":null,\"nEstIncme\":0.0},\"pensioner\":{\"sPensionx\":null,\"nPensionx\":0.0}}";

        List<String> poParentEvl = new ArrayList<>();

        List<oParentFndg> poParentLst = new ArrayList<>();
        List<oChildFndg> poChlFndng;

        //Arraylist for storing labels which will be displayed on UI for confirmation
        List<String> poChlLabel = new ArrayList<>();
        HashMap<oParentFndg, List<oChildFndg>> poChild = new HashMap<>();

        //Parse Json for Evaluation
        JSONObject loForEval = new JSONObject(lsForEval);

        //Parse Json for Label
        JSONObject loForLbel = new JSONObject(lsFrLabel);

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
                    oParentFndg loPrntObj = new oParentFndg(oChildFndg.FIELDS.MEANS, laParent.getString(x));
                    poParentLst.add(new oParentFndg(oChildFndg.FIELDS.ADDRESS, laParent.getString(x)));
                    poChlFndng = new ArrayList<>();
                    JSONArray laChild = loParent.names();

                    for (int i = 0; i < laChild.length(); i++) {
                        //Get all value which has negative 1
                        if (loParent.getString(laChild.getString(i)).equalsIgnoreCase("-1")) {
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
        assertNotNull(loForEval);
        assertTrue(poChild.size() > 0);
    }

    @Test
    public void test05TestNullValuesOnParent() throws Exception{
        String lsFrLabel = "{\"employed\":{\"sEmployer\":\"United Phlp And Phper Inc.\",\"sWrkAddrx\":\"Km 48 Mc Arthur Hi-way Iba Este, Calumpit Bulacan\",\"sPosition\":\"M009680\",\"nLenServc\":23.0,\"nSalaryxx\":35000.0},\"self_employed\":null,\"financed\":null,\"pensioner\":null}";

        List<String> poParentEvl = new ArrayList<>();

        // Use this JSONOBject to identify keywords for evaluation on Label Field
        JSONObject loCheckEval = new JSONObject(lsFrLabel);
        JSONArray laJSON = loCheckEval.names();
        for(int x = 0; x < laJSON.length(); x++){
            if(!loCheckEval.isNull(laJSON.getString(x))){
                poParentEvl.add(laJSON.getString(x));
            }
        }
        assertNotNull(poParentEvl);
        assertTrue(poParentEvl.size() > 0);
    }

    @Test
    public void test06NullParent() throws Exception{
        String lsForEval = "{\"sProprty1\":null,\"sProprty2\":null,\"sProprty3\":null,\"cWith4Whl\":null,\"cWith3Whl\":null,\"cWith2Whl\":null,\"cWithRefx\":\"-1\",\"cWithTVxx\":\"-1\",\"cWithACxx\":null}";
        JSONObject loParent = new JSONObject(lsForEval);
        JSONObject loChild = new JSONObject("cWith4Whl");
        String lsKeyxxx = "cWith4Whl";
        if(loChild.has(lsKeyxxx)){
            String lsValuex = "-1";
            loChild.put(lsKeyxxx, lsValuex);
        }
        assertNotNull(loParent);
    }
}
