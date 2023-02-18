package org.rmj.g3appdriver.lib.Version;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VersionInfo {

    private int sVrsionCd = 0;
    private String sVrsionNm = "";
    private JSONObject sVrsnNote;
    private String cNewUpdte = "0";

    public VersionInfo() {
    }

    public int getsVrsionCd() {
        return sVrsionCd;
    }

    public void setsVrsionCd(int sVrsionCd) {
        this.sVrsionCd = sVrsionCd;
    }

    public String getsVrsionNm() {
        return sVrsionNm;
    }

    public void setsVrsionNm(String sVrsionNm) {
        this.sVrsionNm = sVrsionNm;
    }

    public void setsVrsnNote(String sVrsnNote) {
        try {
            this.sVrsnNote = new JSONObject(sVrsnNote);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getcNewUpdte() {
        return cNewUpdte;
    }

    public void setcNewUpdte(String cNewUpdte) {
        this.cNewUpdte = cNewUpdte;
    }

    public boolean hasNewUpdate(){
        return sVrsnNote.has("sNFeature");
    }

    public List<NewFeature> getNewFeatures() throws Exception{
        JSONArray laDetail = sVrsnNote.getJSONArray("sNFeature");
        List<NewFeature> loDetail = new ArrayList<>();
        for(int x = 0; x < laDetail.length(); x++){
            JSONObject loJson = laDetail.getJSONObject(x);
            loDetail.add(
                    new NewFeature(
                            loJson.getString("sFeaturex"),
                            loJson.getString("sDescript")));
        }

        return loDetail;
    }

    public boolean hasFixes(){
        return sVrsnNote.has("sOthersxx");
    }

    public List<String> getOthers() throws Exception {
        JSONArray laDetail = sVrsnNote.getJSONArray("sOthersxx");
        List<String> loDetail = new ArrayList<>();
        for(int x = 0; x < laDetail.length(); x++){
            JSONObject loJson = laDetail.getJSONObject(x);
            loDetail.add(loJson.getString("sDescript"));
        }

        return loDetail;
    }

    public class NewFeature{
        private final String sFeaturex;
        private final String sDescript;

        public NewFeature(String sFeaturex, String sDescript) {
            this.sFeaturex = sFeaturex;
            this.sDescript = sDescript;
        }

        public String getsFeaturex() {
            return sFeaturex;
        }

        public String getsDescript() {
            return sDescript;
        }
    }
}
