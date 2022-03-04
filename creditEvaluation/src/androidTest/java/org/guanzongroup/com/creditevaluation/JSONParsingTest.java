package org.guanzongroup.com.creditevaluation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import androidx.dynamicanimation.animation.FloatValueHolder;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.guanzongroup.com.creditevaluation.Core.FindingsParser;
import org.guanzongroup.com.creditevaluation.Core.oChildFndg;
import org.json.JSONArray;
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
public class JSONParsingTest {

    private String lsAddress = "{\"present_address\":{\"cAddrType\":\"0\",\"sAddressx\":\"B3 Lt4 Lingueta St., Pio Cruzcosa, Calumpit Bulacan\",\"sAddrImge\":\"\",\"nLatitude\":0.0,\"nLongitud\":0.0},\"primary_address\":{\"cAddrType\":\"1\",\"sAddressx\":\"B3 Lt4 Lingueta St., Pio Cruzcosa, Calumpit Bulacan\",\"sAddrImge\":\"\",\"nLatitude\":0.0,\"nLongitud\":0.0}}";
    private String lsAddFndg = "{\"present_address\":{\"cAddrType\":null,\"sAddressx\":\"-1\",\"sAddrImge\":null,\"nLatitude\":0.0,\"nLongitud\":0.0},\"primary_address\":{\"cAddrType\":null,\"sAddressx\":\"-1\",\"sAddrImge\":null,\"nLatitude\":0.0,\"nLongitud\":0.0}}";
    private String lsAddRslt = "{\"present_address\":{\"cAddrType\":null,\"sAddressx\":\"1\",\"sAddrImge\":null,\"nLatitude\":0.0,\"nLongitud\":0.0},\"primary_address\":{\"cAddrType\":null,\"sAddressx\":\"1\",\"sAddrImge\":null,\"nLatitude\":0.0,\"nLongitud\":0.0}}";

    private List<JSONObject> poParent = new ArrayList<>();
    private List<oChildFndg> poChild = new ArrayList<>();

    @Before
    public void setup() throws Exception{

    }

    @Test
    public void test01ParseJSONKeysToList() throws Exception {
        JSONObject loJson = new JSONObject(lsAddFndg);
        JSONArray laParent = loJson.names();
        for(int x = 0; x < laParent.length(); x++){
            JSONObject loParent = loJson.getJSONObject(laParent.getString(x));
            poParent.add(loParent);
            JSONArray laChild = loParent.names();
            for(int i = 0; i < laChild.length(); i++){
                poChild.add(new oChildFndg(
                        oChildFndg.FIELDS.ADDRESS,
                        laParent.getString(x),
                        "",
                        laChild.getString(i),
                        loParent.getString(laChild.getString(i))));
            }
        }
        assertTrue(poParent.size() > 0);
        assertTrue(poChild.size() > 0);
    }

    @Test
    public void test02GetForEvaluationList() throws Exception{
        List<oChildFndg> poChild = new ArrayList<>();
        poChild = FindingsParser.getForEvaluation(oChildFndg.FIELDS.ADDRESS,lsAddress, lsAddFndg);
        assertTrue(poChild.size()>0);
    }

    @Test
    public void test03UpdateEvaluationResult() throws Exception{
        List<oChildFndg> poChild = FindingsParser.getForEvaluation(oChildFndg.FIELDS.ADDRESS,lsAddress, lsAddFndg);
        JSONObject loParent = new JSONObject(lsAddFndg);
        for(int x = 0; x < poChild.size(); x++){
            poChild.get(x).setsValue("1");

            oChildFndg loResult = poChild.get(x);

            if (loParent.has(loResult.getParent())){

                JSONObject loChild = loParent.getJSONObject(loResult.getParent());

                loChild.put(loResult.getKey(), loResult.getValue());
                String lsResVal = loChild.getString("sAddressx");
                loParent.put(loResult.getParent(), loChild);
            }
        }
        String lsResult = loParent.toString();
        assertEquals(lsAddRslt, lsResult);
    }

    @Test
    public void test04CreateJsonParameter() throws Exception {

    }
}
