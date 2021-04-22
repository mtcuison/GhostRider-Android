package org.rmj.guanzongroup.ghostrider.creditevaluator.Model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class CreditEvaluationModelTest {
    List<CreditEvaluationModel> infoList;
    @Before
    public void setUp() throws Exception {
        infoList = new ArrayList<>();


    }

    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void getFAKEDATA() throws JSONException, ParseException {
        String FAKE_DATA = "[{\"cTranStat\": \"1\",\"dTimeStmp\": \"2021-03-24 14:34:03\",\"dTransact\": \"2021-03-24\",\"nAcctTerm\": \"6\",\"nDownPaym\": \"200.00\",\"sAddressx\": \"156 Ambuetel, Ambuetel, Calasiao\",\"sCompnyNm\": \"Soriano, Reynaldo Uson\",\"sCredInvx\": \"M00115000623\",\"sMobileNo\": \"09095651819\",\"sModelNme\": \"TMX 125 ALPHA - CCG125MII\",\"sQMAppCde\": \"empty\",\"sSpouseNm\": \"Soriano, Rosita Sanchez\",\"sTransNox\": \"C0YNQ2100036\"}, "+
                "{\"cTranStat\": \"1\",\"dTimeStmp\": \"2021-03-25 11:22:53\",\"dTransact\": \"2021-03-25\",\"nAcctTerm\": \"6\",\"nDownPaym\": \"200.00\",\"sAddressx\": \"156 Dinalaoan, Dinalaoan, Calasiao\", \"sCompnyNm\": \"Soriano, Reynaldo Uson\",\"sCredInvx\": \"M00115000623\",\"sMobileNo\": \"09095651819\",\"sModelNme\": \"TMX 125 ALPHA - CCG125MII\",\"sQMAppCde\": \"empty\",\"sSpouseNm\": \"Soriano, Rosita Sanchez\",\"sTransNox\": \"C0YNQ2100037\"}, "+
                "{\"cTranStat\": \"1\",\"dTimeStmp\": \"2021-03-31 14:23:00\",\"dTransact\": \"2021-03-31\",\"nAcctTerm\": \"12\",\"nDownPaym\": \"200.00\",\"sAddressx\": \"12 Leteg, Bonuan Boquig, Dagupan City\",\"sCompnyNm\": \"Uson, Rozel Clyde De Vera\",\"sCredInvx\": \"M00115000623\",\"sMobileNo\": \"09359533455\",\"sModelNme\": \"ADV150 - ADV150ALII\",\"sQMAppCde\": \"empty\",\"sSpouseNm\": \"Soriano, Rosita Sanchez\",\"sTransNox\": \"C0YNQ2100039\"}, "+
                "{\"cTranStat\": \"1\",\"dTimeStmp\": \"2021-04-08 12:45:14\", \"dTransact\": \"2021-04-08\", \"nAcctTerm\": \"12\", \"nDownPaym\": \"200.00\", \"sAddressx\": \"1927 Malued, Malued, Dagupan City\", \"sCompnyNm\": \"Ticman, Erwin Reyes\", \"sCredInvx\": \"M00115000623\", \"sMobileNo\": \"09321056153\", ,sModelNme\": \"CLICK125i - ACB125CBFM\", \"sQMAppCde\": \"empty\", \"sSpouseNm\": \"Ticman, Mary Ann Que\", \"sTransNox\": \"C0YNQ2100042\"}, "+
                "{\"cTranStat\": \"1\",\"dTimeStmp\": \"2021-04-10 13:22:36\", \"dTransact\": \"2021-03-31\", \"nAcctTerm\": \"12\", \"nDownPaym\": \"200.00\", \"sAddressx\": \"676 Bolosan, Bolosan, Dagupan City\", \"sCompnyNm\": \"Apigo, Troy Magsano\", \"sCredInvx\": \"M00115000623\", \"sMobileNo\": \"09276707137\", \"sModelNme\": \"TMX 125 ALPHA - CCG125WHF\", \"sQMAppCde\": \"empty\", \"sSpouseNm\": \"Soriano, Rosita Sanchez\", \"sTransNox\": \"C0YNQ2100040\"},  "+
                "{\"cTranStat\": \"1\", \"dTimeStmp\": \"2021-04-13 16:03:11\", \"dTransact\": \"2021-04-05\", \"nAcctTerm\": \"12\", \"nDownPaym\": \"14.94\", \"sAddressx\": \"Jemv Building Mc Arthur Hi-way, Tapuac, Dagupan City\", \"sCompnyNm\": \"Brun, Felix Jr. Panal\", \"sCredInvx\": \"M00115000623\", \"sMobileNo\": \"09399233306\", \"sModelNme\": \"BeAt-FI(Standard) - ACH110CSFMII\", \"sQMAppCde\": \"empty\", \"sSpouseNm\": \"Brun, Lilibeth Nebrija\", \"sTransNox\": \"C0YNQ2100041\"},"+
                "{\"cTranStat\": \"1\", \"dTimeStmp\": \"2021-04-13 16:03:40\", \"dTransact\": \"2021-04-08\", \"nAcctTerm\": \"36\", \"nDownPaym\": \"11.28\", \"sAddressx\": \"17 Maligaya St., Bonuan Gueset, Dagupan City\", \"sCompnyNm\": \"Ubando, Michael Valentino\", \"sCredInvx\": \"M00115000726\", \"sMobileNo\": \"09465489411\", \"sModelNme\": \"TMX 125 ALPHA - CCG125MII\", \"sQMAppCde\": \"empty\", \"sSpouseNm\": \"Soriano, Rosita Sanchez\", \"sTransNox\": \"C0YNQ2100044\" },"+
                "{\"cTranStat\": \"1\", \"dTimeStmp\": \"2021-04-15 09:48:14\", \"dTransact\": \"2021-04-15\", \"nAcctTerm\": \"6\", \"nDownPaym\": \"200.00\", \"sAddressx\": \"349 Puelay, Caranglaan, Dagupan City\", \"sCompnyNm\": \"Dela Cruz, Ricardo Jr. Mariñas\", \"sCredInvx\": \"M00115000623\", \"sMobileNo\": \"09433933345\", \"sModelNme\": \"CLICK 150 I - ACB150CBTL\", \"sQMAppCde\": \"empty\", \"sSpouseNm\": \"Dela Cruz, Faye Colobong\",\"sTransNox\": \"C0YNQ2100045\"},"+
                "{\"cTranStat\": \"1\",\"dTimeStmp\": \"2021-04-17 14:31:30\",\"dTransact\": \"2021-04-17\",\"nAcctTerm\": \"6\",\"nDownPaym\": \"200.00\",\"sAddressx\": \"45 Ayusip St., Bonuan Boquig, Dagupan City\",\"sCompnyNm\": \"Leal, Gerald Pablo\",\"sCredInvx\": \"M00115000623\",\"sMobileNo\": \"09677655976\",\"sModelNme\": \"Click 125i - ACB125CBFL\", \"sQMAppCde\": \"empty\",\"sSpouseNm\": \"Leal, Jean Gonzales\",\"sTransNox\": \"C0YNQ2100046\"},"+
                "{\"cTranStat\": \"1\",\"dTimeStmp\": \"2021-04-20 13:22:57\",\"dTransact\": \"2021-04-20\",\"nAcctTerm\": \"6\",\"nDownPaym\": \"200.00\",\"sAddressx\": \"65 Kanit Lucao, Lucao, Dagupan City\",\"sCompnyNm\": \"Muyalde, Perfecto Jr. Calimlim\",\"sCredInvx\": \"M00115000623\",\"sMobileNo\": \"09187588288\",\"sModelNme\": \"CLICK125i - ACB125CBFM\",\"sQMAppCde\": \"empty\",\"sSpouseNm\": \"Ortequiro, Sharlyn Genovania\",\"sTransNox\": \"C0YNQ2100047\"}]";
        JSONArray jsonArray = new JSONArray(FAKE_DATA);
        for (int i=0;i<jsonArray.length();i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            CreditEvaluationModel loan = new CreditEvaluationModel();
            loan.setsTransNox(jsonObject.getString("sTransNox"));
            loan.setdTransact(jsonObject.getString("dTransact"));
            loan.setsCredInvx(jsonObject.getString("sCredInvx"));
            loan.setsCompnyNm(jsonObject.getString("sCompnyNm"));
            loan.setsSpouseNm(jsonObject.getString("sSpouseNm"));
            loan.setsAddressx(jsonObject.getString("sAddressx"));
            loan.setsMobileNo(jsonObject.getString("sMobileNo"));
            loan.setsQMAppCde(jsonObject.getString("sQMAppCde"));
            loan.setsModelNme(jsonObject.getString("sModelNme"));
            loan.setnDownPaym(jsonObject.getString("nDownPaym"));
            loan.setnAcctTerm(jsonObject.getString("nAcctTerm"));
            loan.setcTranStat(jsonObject.getString("cTranStat"));
            loan.setdTimeStmp(jsonObject.getString("dTimeStmp"));

            System.out.println("dTransact = " +jsonObject.getString("dTransact"));
            infoList.add(loan);
        }

    }
}