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

    private String lsAddress = "{\"present_address\":{\"cAddrType\":\"0\",\"sAddressx\":\"B3 Lt4 Lingueta St., Pio Cruzcosa, Calumpit Bulacan\",\"sAddrImge\":\"\",\"nLatitude\":0.0,\"nLongitud\":0.0},\"primary_address\":{\"cAddrType\":\"1\",\"sAddressx\":\"B3 Lt4 Lingueta St., Pio Cruzcosa, Calumpit Bulacan\",\"sAddrImge\":\"\",\"nLatitude\":0.0,\"nLongitud\":0.0}}";
    private String lsAddFndg = "{\"present_address\":{\"cAddrType\":null,\"sAddressx\":\"-1\",\"sAddrImge\":null,\"nLatitude\":0.0,\"nLongitud\":0.0},\"primary_address\":{\"cAddrType\":null,\"sAddressx\":\"-1\",\"sAddrImge\":null,\"nLatitude\":0.0,\"nLongitud\":0.0}}";
    private String lsAddRslt = "{\"present_address\":{\"cAddrType\":null,\"sAddressx\":\"1\",\"sAddrImge\":null,\"nLatitude\":0.0,\"nLongitud\":0.0},\"primary_address\":{\"cAddrType\":null,\"sAddressx\":\"1\",\"sAddrImge\":null,\"nLatitude\":0.0,\"nLongitud\":0.0}}";

//    private String lsAddress = "{\"sProprty1\":\"\",\"sProprty2\":\"\",\"sProprty3\":\"\",\"cWith4Whl\":\"0\",\"cWith3Whl\":\"0\",\"cWith2Whl\":\"0\",\"cWithRefx\":\"1\",\"cWithTVxx\":\"1\",\"cWithACxx\":\"1\"}";
//    private String lsAddFndg = "{\"sProprty1\":null,\"sProprty2\":null,\"sProprty3\":null,\"cWith4Whl\":null,\"cWith3Whl\":null,\"cWith2Whl\":null,\"cWithRefx\":\"-1\",\"cWithTVxx\":\"-1\",\"cWithACxx\":null}";
//    private String lsAddRslt = "{\"sProprty1\":null,\"sProprty2\":null,\"sProprty3\":null,\"cWith4Whl\":null,\"cWith3Whl\":null,\"cWith2Whl\":null,\"cWithRefx\":\"1\",\"cWithTVxx\":\"1\",\"cWithACxx\":null}";

    private List<JSONObject> poParent = new ArrayList<>();
    private List<oChildFndg> poChild = new ArrayList<>();

    @Before
    public void setup() throws Exception{

    }

    @Test
    public void test01ParentAndChildObject() throws Exception {
        List<oParentFndg> poParentLst = new ArrayList<>();
        List<oChildFndg> poChlFndng;
        List<String> poChlLabel = new ArrayList<>();
        HashMap<oParentFndg, List<oChildFndg>> poChild = new HashMap<>();

        //Parse Json for Evaluation
        JSONObject loForEval = new JSONObject(lsAddFndg);

        //Parse Json for Label
        JSONObject loForLbel = new JSONObject(lsAddress);
        JSONArray laForLbel = loForLbel.names();
        for(int x = 0; x < laForLbel.length(); x++) {
            poChlLabel.add(loForLbel.getString(laForLbel.getString(x)));
        }

        JSONArray laParent = loForEval.names();
        for(int x = 0; x < laParent.length(); x++) {
            poChlFndng = new ArrayList<>();
            oParentFndg loPrntObj = new oParentFndg(oChildFndg.FIELDS.ASSETS, null);
            if (loForEval.getString(laParent.getString(x)).equalsIgnoreCase("-1")) {
                oChildFndg loChild = new oChildFndg(poChlLabel.get(x),
                        laParent.getString(x),
                        loForEval.getString(laParent.getString(x)));
                poChlFndng.add(loChild);

                poChild.put(loPrntObj, poChlFndng);
            }
        }

//                loForEval.add(new oChildFndg(
//                        Field,
//                        laParent.getString(x),
//                        laChild.getString(i),
//                        laChild.getString(i),
//                        loParent.getString(laChild.getString(i))));
        assertNotNull(poChild);
    }

    @Test
    public void test02UpdateEvaluationResult() throws Exception{
        HashMap<oParentFndg, List<oChildFndg>> poChild = FindingsParser.getForEvaluation(oChildFndg.FIELDS.ADDRESS,lsAddress, lsAddFndg);
        JSONObject loParent = new JSONObject(lsAddFndg);

        poChild.forEach((k, v) -> {
            oParentFndg loPrntObj = k;
            for(int x = 0; x < v.size(); x++){

                oChildFndg loResult = v.get(x);

                if (loParent.has(loResult.getKey())){
                    try {

                        JSONObject loChild = loParent.getJSONObject(loPrntObj.getParent());

                        loChild.put(loResult.getKey(), loResult.getValue());
                        String lsResVal = loChild.getString("sAddressx");
                        loParent.put(loPrntObj.getParent(), loChild);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });

        String lsResult = loParent.toString();
        assertEquals(lsAddRslt, lsResult);
    }
}
