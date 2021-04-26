/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.creditApp
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.onlinecreditapplication.Model;

import org.json.JSONArray;
import org.json.JSONObject;

class MeansInfoModel {

    private String sEmployed;
    private String sSEmployd;
    private String sFinancex;
    private String sPensionx;

    public MeansInfoModel() {
    }

    public void setsEmployed(String sEmployed) {
        this.sEmployed = sEmployed;
    }

    public void setsSEmployd(String sSEmployd) {
        this.sSEmployd = sSEmployd;
    }

    public void setsFinancex(String sFinancex) {
        this.sFinancex = sFinancex;
    }

    public void setsPensionx(String sPensionx) {
        this.sPensionx = sPensionx;
    }

    public String getMeansInfo(){
        String lsMeansIf = "";
        try{
            JSONObject loJson = new JSONObject();
            JSONArray laJson = new JSONArray();
        } catch (Exception e){
            e.printStackTrace();
        }
        return lsMeansIf;
    }
}
