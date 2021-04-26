/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Model;

import java.text.DecimalFormat;

public class Area {
    private String AreaCode;
    private String AreaName;
    private float Goal;
    private float Actual;

    public Area(String areaCode,
                      String areaName,
                      String goal,
                      String actual){
        this.AreaCode = areaCode;
        this.AreaName = areaName;
        this.Goal = Float.parseFloat(goal);
        this.Actual = Float.parseFloat(actual);
    }

    public String getAreaCode() {
        return AreaCode;
    }

    public String getAreaName() {
        return AreaName;
    }

    public String getSalesPercentage() {
        float percentage = Actual/Goal;
        float result = percentage * 100;
        return new DecimalFormat("##").format(result) + "%";
    }

    public int getDynamicSize() {
        float adds = Goal * .05f;
        return Integer.parseInt(new DecimalFormat("##").format(100 + adds));
    }
}

