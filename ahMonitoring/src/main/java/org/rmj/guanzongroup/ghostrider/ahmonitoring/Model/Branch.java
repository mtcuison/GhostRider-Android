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

public class Branch {
    private String BranchCode;
    private String BranchName;
    private float Goal;
    private float Actual;

    private final DecimalFormat format = new DecimalFormat("##.##");

    public Branch(String branchCode,
                  String branchName,
                  String goal,
                  String actual){
        this.BranchCode = branchCode;
        this.BranchName = branchName;
        this.Goal = Float.parseFloat(goal);
        this.Actual = Float.parseFloat(actual);
    }

    public String getBranchCode() {
        return BranchCode;
    }

    public String getBranchName() {
        return BranchName;
    }


    public String getSalesPercentage() {
        float percentage = Actual/100;
        float result = percentage * 100;
        return new DecimalFormat("##").format(result) + "%";
    }

    public int getDynamicSize() {
        float adds = 100 * .05f;
        return Integer.parseInt(new DecimalFormat("##").format(100 + adds));
    }
}
