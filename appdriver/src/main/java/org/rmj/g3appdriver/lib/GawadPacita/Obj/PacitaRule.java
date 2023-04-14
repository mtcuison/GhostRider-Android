package org.rmj.g3appdriver.lib.GawadPacita.Obj;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.dev.Database.Entities.EPacitaEvaluation;
import org.rmj.g3appdriver.dev.Database.Entities.EPacitaRule;
import org.rmj.g3appdriver.lib.GawadPacita.pojo.BranchRate;

import java.util.ArrayList;
import java.util.List;

public class PacitaRule {

    public static List<BranchRate> ParseBranchRate(String PayLoad, List<EPacitaRule> Rules){
        try{
            List<BranchRate> loBranch = new ArrayList<>();
            JSONArray laJson = new JSONArray(PayLoad);
            for(int x = 0; x < laJson.length(); x++){

                for(int i = 0; i < Rules.size(); i++) {
                    EPacitaRule loRule = Rules.get(i);
                    JSONObject loJson = laJson.getJSONObject(x);
                    int lnEntryNo = loJson.getInt("nEntryNox");

                    if(lnEntryNo == Rules.get(x).getEntryNox()) {
                        BranchRate loRate = new BranchRate(
                                loJson.getInt("nEntryNox"),
                                loRule.getFieldNmx(),
                                loJson.getString("xRatingxx"));
                        loBranch.add(loRate);
                    }
                }
            }

            return loBranch;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
