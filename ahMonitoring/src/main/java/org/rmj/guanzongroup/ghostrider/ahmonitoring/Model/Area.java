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

