package org.guanzongroup.com.creditevaluation.Core;

import org.json.JSONObject;

public class CIResultHandler {

    public static String toStringResult(String fsField, String fsFindings, oParentFndg foParent, oChildFndg foChild) throws Exception{
        switch (fsField){
            case oChildFndg.FIELDS.ADDRESS:
            case oChildFndg.FIELDS.MEANS:
                return getWithParentResult(fsFindings, foParent, foChild);
            default:
                return getNonParentResult(fsFindings, foParent, foChild);
        }
    }

    private static String getWithParentResult(String fsFindings, oParentFndg foParent, oChildFndg foChild) throws Exception{
        JSONObject loParent = new JSONObject(fsFindings);

        JSONObject loChild = loParent.getJSONObject(foParent.getParent());
        String lsKeyxxx = foChild.getKey();
        if(loChild.has(lsKeyxxx)){
            String lsValuex = foChild.getValue();
            loChild.put(lsKeyxxx, lsValuex);
        }
        loParent.put(foParent.getParent(), loChild);

        String lsResult = loParent.toString();
        return lsResult;
    }

    private static String getNonParentResult(String fsFindings, oParentFndg foParent, oChildFndg foChild) throws Exception{
        JSONObject loFndng = new JSONObject(fsFindings);
        String lsKeyxxx = foChild.getKey();
        if(loFndng.has(lsKeyxxx)){
            String lsValuex = foChild.getValue();
            loFndng.put(lsKeyxxx, lsValuex);
        }

        return loFndng.toString();
    }
}
