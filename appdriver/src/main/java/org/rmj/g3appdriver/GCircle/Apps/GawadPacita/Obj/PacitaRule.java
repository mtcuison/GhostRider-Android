package org.rmj.g3appdriver.GCircle.Apps.GawadPacita.Obj;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.GCircle.room.Entities.EPacitaRule;
import org.rmj.g3appdriver.GCircle.Apps.GawadPacita.pojo.BranchRate;

import java.util.ArrayList;
import java.util.List;

public class PacitaRule {

    public static List<BranchRate> ParseBranchRate(String PayLoad, List<EPacitaRule> Rules){
        try{
            List<BranchRate> loBranch = new ArrayList<>();

            JSONObject loPayload;
            JSONArray laJson;
            if(isJSONObject(PayLoad)) {
                loPayload = new JSONObject(PayLoad);
                if(loPayload.has("sEvalType")){
                    laJson = loPayload.getJSONArray("sPayloadx");
                } else {
                    laJson = new JSONArray(PayLoad);
                }
            } else {
                laJson = new JSONArray(PayLoad);
            }

            for(int x = 0; x < laJson.length(); x++){
                JSONObject loJson = laJson.getJSONObject(x);
                int lnEntryNo = loJson.getInt("nEntryNox");

                for(int i = 0; i < Rules.size(); i++) {
                    EPacitaRule loRule = Rules.get(i);

                    if(lnEntryNo == Rules.get(i).getEntryNox()) {
                        BranchRate loRate = new BranchRate(
                                loJson.getInt("nEntryNox"),
                                loRule.getFieldNmx(),
                                loJson.getString("xRatingxx"));
                        loBranch.add(loRate);
                        break;
                    }
                }
            }

            return loBranch;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private static boolean isJSONObject(String val){
        try{
            JSONObject loPayload = new JSONObject(val);
            return true;
        } catch (Exception e){
//            e.printStackTrace();
            return false;
        }
    }
}
