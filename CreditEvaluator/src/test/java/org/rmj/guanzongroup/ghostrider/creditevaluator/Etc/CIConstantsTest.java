package org.rmj.guanzongroup.ghostrider.creditevaluator.Etc;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Model.CreditEvaluationModel;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CIConstantsTest {
    public static List<CreditEvaluationModel> ciList;
    public static String FAKE_DATA = "{\"detail\" : [\n" +
            "  {\n" +
            "    \"sTransNox\": \"C0YNQ2100036\",\n" +
            "    \"sBranchCd\": \"M001\",\n" +
            "    \"dTransact\": \"2021-03-24\",\n" +
            "    \"sCredInvx\": \"\",\n" +
            "    \"sCompnyNm\": \"Soriano, Reynaldo Uson\",\n" +
            "    \"sQMAppCde\": \"\",\n" +
            "    \"nDownPaym\": \"200.00\",\n" +
            "    \"sCreatedx\": \"GAP020200310\",\n" +
            "    \"cTranStat\": \"1\",\n" +
            "    \"dTimeStmp\": \"2021-03-24 14:34:03\",\n" +
            "    \"sSpouseNm\": \"Soriano, Rosita Sanchez\",\n" +
            "    \"sAddressx\": \"156 Ambuetel, Ambuetel, Calasiao\",\n" +
            "    \"sModelNme\": \"TMX 125 ALPHA - CCG125MII\",\n" +
            "    \"nAcctTerm\": \"6\",\n" +
            "    \"sMobileNo\": \"09095651819\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"sTransNox\": \"C0YNQ2100037\",\n" +
            "    \"sBranchCd\": \"M001\",\n" +
            "    \"dTransact\": \"2021-03-25\",\n" +
            "    \"sCredInvx\": \"\",\n" +
            "    \"sCompnyNm\": \"Soriano, Reynaldo Uson\",\n" +
            "    \"sQMAppCde\": \"\",\n" +
            "    \"nDownPaym\": \"200.00\",\n" +
            "    \"sCreatedx\": \"GAP020200310\",\n" +
            "    \"cTranStat\": \"1\",\n" +
            "    \"dTimeStmp\": \"2021-03-25 11:22:53\",\n" +
            "    \"sSpouseNm\": \"Soriano, Rosita Sanchez\",\n" +
            "    \"sAddressx\": \"156 Dinalaoan, Dinalaoan, Calasiao\",\n" +
            "    \"sModelNme\": \"TMX 125 ALPHA - CCG125MII\",\n" +
            "    \"nAcctTerm\": \"6\",\n" +
            "    \"sMobileNo\": \"09095651819\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"sTransNox\": \"437Z21000003\",\n" +
            "    \"sBranchCd\": \"M001\",\n" +
            "    \"dTransact\": \"2021-03-25\",\n" +
            "    \"sCredInvx\": \"M00120001760\",\n" +
            "    \"sCompnyNm\": \"Garcia, Michael Permison\",\n" +
            "    \"sQMAppCde\": \"\",\n" +
            "    \"nDownPaym\": \"7500.00\",\n" +
            "    \"sCreatedx\": \"GAP0190799\",\n" +
            "    \"cTranStat\": \"4\",\n" +
            "    \"dTimeStmp\": \"2021-03-26 16:37:36\",\n" +
            "    \"sSpouseNm\": \"Ndjbsbdb, Jdjhshd Jdjshfd\",\n" +
            "    \"sAddressx\": \"231 Bdjshhsbe  Ndjjehdhe, Bantayan, Mangaldan\",\n" +
            "    \"sModelNme\": \"CLICK 150 I - ACB150CBTL\",\n" +
            "    \"nAcctTerm\": \"36\",\n" +
            "    \"sMobileNo\": \"09431881648\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"sTransNox\": \"C0YNQ2100039\",\n" +
            "    \"sBranchCd\": \"M001\",\n" +
            "    \"dTransact\": \"2021-03-31\",\n" +
            "    \"sCredInvx\": \"M00115000623\",\n" +
            "    \"sCompnyNm\": \"Uson, Rozel Clyde De Vera\",\n" +
            "    \"sQMAppCde\": \"\",\n" +
            "    \"nDownPaym\": \"200.00\",\n" +
            "    \"sCreatedx\": \"GAP020200310\",\n" +
            "    \"cTranStat\": \"1\",\n" +
            "    \"dTimeStmp\": \"2021-03-31 14:23:00\",\n" +
            "    \"sSpouseNm\": \",  \",\n" +
            "    \"sAddressx\": \"12 Leteg, Bonuan Boquig, Dagupan City\",\n" +
            "    \"sModelNme\": \"ADV150 - ADV150ALII\",\n" +
            "    \"nAcctTerm\": \"12\",\n" +
            "    \"sMobileNo\": \"09359533455\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"sTransNox\": \"437Z21000005\",\n" +
            "    \"sBranchCd\": \"M001\",\n" +
            "    \"dTransact\": \"2021-03-27\",\n" +
            "    \"sCredInvx\": \"M00120001760\",\n" +
            "    \"sCompnyNm\": \"Garcia, Michael Permison\",\n" +
            "    \"sQMAppCde\": \"\",\n" +
            "    \"nDownPaym\": \"7500.00\",\n" +
            "    \"sCreatedx\": \"GAP0190799\",\n" +
            "    \"cTranStat\": \"4\",\n" +
            "    \"dTimeStmp\": \"2021-04-01 13:40:29\",\n" +
            "    \"sSpouseNm\": \"Bfjabdhs Kdbjs, Bdibsbs Njsbdna\",\n" +
            "    \"sAddressx\": \"231 Bdkabd Nabsb, Bantayan, Mangaldan\",\n" +
            "    \"sModelNme\": \"CLICK 150 I - ACB150CBTL\",\n" +
            "    \"nAcctTerm\": \"36\",\n" +
            "    \"sMobileNo\": \"09739491538\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"sTransNox\": \"C10A02100002\",\n" +
            "    \"sBranchCd\": \"M001\",\n" +
            "    \"dTransact\": \"2021-03-29\",\n" +
            "    \"sCredInvx\": \"\",\n" +
            "    \"sCompnyNm\": \"Sample, Sample Sample\",\n" +
            "    \"sQMAppCde\": \"\",\n" +
            "    \"nDownPaym\": \"13500.00\",\n" +
            "    \"sCreatedx\": \"GAP020202408\",\n" +
            "    \"cTranStat\": \"4\",\n" +
            "    \"dTimeStmp\": \"2021-04-01 13:40:46\",\n" +
            "    \"sSpouseNm\": \"\",\n" +
            "    \"sAddressx\": \"123 sample sample, Cawayan Bogtong, Malasiqui\",\n" +
            "    \"sModelNme\": \"SNIPER 150 DOXOU\",\n" +
            "    \"nAcctTerm\": \"36\",\n" +
            "    \"sMobileNo\": \"09123456789\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"sTransNox\": \"437Z21000014\",\n" +
            "    \"sBranchCd\": \"M001\",\n" +
            "    \"dTransact\": \"2021-03-31\",\n" +
            "    \"sCredInvx\": \"M00120001760\",\n" +
            "    \"sCompnyNm\": \"Garcia, Michael Permison\",\n" +
            "    \"sQMAppCde\": \"\",\n" +
            "    \"nDownPaym\": \"7500.00\",\n" +
            "    \"sCreatedx\": \"GAP0190799\",\n" +
            "    \"cTranStat\": \"4\",\n" +
            "    \"dTimeStmp\": \"2021-04-01 14:05:58\",\n" +
            "    \"sSpouseNm\": \"Jfjsbs, Jfjsbd Jfjjdd\",\n" +
            "    \"sAddressx\": \", Bantayan, Mangaldan\",\n" +
            "    \"sModelNme\": \"CLICK 150 I - ACB150CBTL\",\n" +
            "    \"nAcctTerm\": \"36\",\n" +
            "    \"sMobileNo\": \"09171870011\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"sTransNox\": \"437Z21000011\",\n" +
            "    \"sBranchCd\": \"M001\",\n" +
            "    \"dTransact\": \"2021-03-31\",\n" +
            "    \"sCredInvx\": \"M00120001760\",\n" +
            "    \"sCompnyNm\": \"Bfjja, Fjekhd Jfjdd\",\n" +
            "    \"sQMAppCde\": \"\",\n" +
            "    \"nDownPaym\": \"7500.00\",\n" +
            "    \"sCreatedx\": \"GAP0190799\",\n" +
            "    \"cTranStat\": \"4\",\n" +
            "    \"dTimeStmp\": \"2021-04-01 14:43:38\",\n" +
            "    \"sSpouseNm\": \"\",\n" +
            "    \"sAddressx\": \"Fbjwbdh, Bantayan, Mangaldan\",\n" +
            "    \"sModelNme\": \"CLICK 150 I - ACB150CBTL\",\n" +
            "    \"nAcctTerm\": \"36\",\n" +
            "    \"sMobileNo\": \"09171870011\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"sTransNox\": \"437Z21000013\",\n" +
            "    \"sBranchCd\": \"M001\",\n" +
            "    \"dTransact\": \"2021-03-31\",\n" +
            "    \"sCredInvx\": \"M00120001760\",\n" +
            "    \"sCompnyNm\": \"Bdjbsd, Vdjs Jdjsbd\",\n" +
            "    \"sQMAppCde\": \"\",\n" +
            "    \"nDownPaym\": \"7500.00\",\n" +
            "    \"sCreatedx\": \"GAP0190799\",\n" +
            "    \"cTranStat\": \"4\",\n" +
            "    \"dTimeStmp\": \"2021-04-01 14:43:45\",\n" +
            "    \"sSpouseNm\": \"\",\n" +
            "    \"sAddressx\": \", Bantayan, Mangaldan\",\n" +
            "    \"sModelNme\": \"CLICK 150 I - ACB150CBTL\",\n" +
            "    \"nAcctTerm\": \"36\",\n" +
            "    \"sMobileNo\": \"09171870011\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"sTransNox\": \"C10A02100005\",\n" +
            "    \"sBranchCd\": \"M001\",\n" +
            "    \"dTransact\": \"2021-03-30\",\n" +
            "    \"sCredInvx\": \"\",\n" +
            "    \"sCompnyNm\": \"sample, sample sample\",\n" +
            "    \"sQMAppCde\": \"\",\n" +
            "    \"nDownPaym\": \"13500.00\",\n" +
            "    \"sCreatedx\": \"GAP020202408\",\n" +
            "    \"cTranStat\": \"4\",\n" +
            "    \"dTimeStmp\": \"2021-04-01 14:52:31\",\n" +
            "    \"sSpouseNm\": \"\",\n" +
            "    \"sAddressx\": \"123 gsgs dud, Cawayan Bogtong, Malasiqui\",\n" +
            "    \"sModelNme\": \"Sniper 150 - B18A00-010\",\n" +
            "    \"nAcctTerm\": \"36\",\n" +
            "    \"sMobileNo\": \"09123456789\"\n" +
            "  }\n" +
            "]}";
    public static List<CreditEvaluationModel> getDataList(){
        ciList = new ArrayList<>();
        try {
            // Create the root JSONObject from the JSON string.
            JSONObject jsonRootObject = new JSONObject(FAKE_DATA);

            //Get the instance of JSONArray that contains JSONObjects
            JSONArray jsonArray = jsonRootObject.optJSONArray("detail");

            //Iterate the jsonArray and print the info of JSONObjects
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                CreditEvaluationModel ciModel = new CreditEvaluationModel();
//              MODEL INITIALIZATION
                ciModel.setsTransNox(jsonObject.getString("sTransNox"));
                ciModel.setdTransact(jsonObject.getString("dTransact"));
                ciModel.setsCredInvx(jsonObject.getString("sCredInvx"));
                ciModel.setsCompnyNm(jsonObject.getString("sCompnyNm"));
                ciModel.setsSpouseNm(jsonObject.getString("sSpouseNm"));
                ciModel.setsAddressx(jsonObject.getString("sAddressx"));
                ciModel.setsMobileNo(jsonObject.getString("sMobileNo"));
                ciModel.setsQMAppCde(jsonObject.getString("sQMAppCde"));
                ciModel.setsModelNme(jsonObject.getString("sModelNme"));
                ciModel.setnDownPaym(jsonObject.getString("nDownPaym"));
                ciModel.setnAcctTerm(jsonObject.getString("nAcctTerm"));
                ciModel.setcTranStat(jsonObject.getString("cTranStat"));
                ciModel.setdTimeStmp(jsonObject.getString("dTimeStmp"));
                ciList.add(ciModel);
            }
            return ciList;
        } catch (JSONException e) {
            e.printStackTrace();

            return null;
        }
    }
}