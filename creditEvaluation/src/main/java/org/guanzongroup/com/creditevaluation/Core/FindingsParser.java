package org.guanzongroup.com.creditevaluation.Core;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FindingsParser {

    public static List<oChildFndg> getForEvaluation(String Field, String fsLabel, String fsFindings) throws Exception{
        List<oChildFndg> loResult = new ArrayList<>();
        List<oChildFndg> loLabel = getLabels(Field, fsLabel);
        List<oChildFndg> loValue = getFindings(Field, fsFindings);

        for(int x = 0; x < loValue.size(); x++){

            if(loValue.get(x).getValue().equalsIgnoreCase("-1")){
                String sParent = loValue.get(x).getParent();
                String sLabelx = loLabel.get(x).getLabel();
                String sKeyxxx = loValue.get(x).getKey();
                String sValxxx = loValue.get(x).getValue();
                loResult.add(new oChildFndg(Field, sParent, sLabelx, sKeyxxx, sValxxx));
            }
        }
        return loResult;
    }

    private static List<oChildFndg> getLabels(String Field, String fsLabel) throws Exception{
        List<oChildFndg> loForEval = new ArrayList<>();
        List<JSONObject> poParent = new ArrayList<>();

        JSONObject loJson = new JSONObject(fsLabel);
        JSONArray laParent = loJson.names();
        for(int x = 0; x < laParent.length(); x++){
            JSONObject loParent = loJson.getJSONObject(laParent.getString(x));
            poParent.add(loParent);
            JSONArray laChild = loParent.names();
            for(int i = 0; i < laChild.length(); i++){
                loForEval.add(new oChildFndg(Field,
                        laParent.getString(x),
                        loParent.getString(laChild.getString(i)),
                        laChild.getString(i),
                        loParent.getString(laChild.getString(i))));
            }
        }
        return loForEval;
    }

    private static List<oChildFndg> getFindings(String Field, String fsFindings) throws Exception{
        List<oChildFndg> loForEval = new ArrayList<>();
        List<JSONObject> poParent = new ArrayList<>();

        JSONObject loJson = new JSONObject(fsFindings);
        JSONArray laParent = loJson.names();
        for(int x = 0; x < laParent.length(); x++){
            JSONObject loParent = loJson.getJSONObject(laParent.getString(x));
            poParent.add(loParent);
            JSONArray laChild = loParent.names();
            for(int i = 0; i < laChild.length(); i++){
                loForEval.add(new oChildFndg(
                        Field,
                        laParent.getString(x),
                        laChild.getString(i),
                        laChild.getString(i),
                        loParent.getString(laChild.getString(i))));
            }
        }
        return loForEval;
    }
}
