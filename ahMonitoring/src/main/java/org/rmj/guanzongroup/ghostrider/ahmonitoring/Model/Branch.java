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
    private float mcGoal;
    private float mcActual;
    private float spGoal;
    private float spActual;
    private float joGoal;
    private float joActual;

    private final DecimalFormat format = new DecimalFormat("##.##");

    public Branch(String branchCode,
                  String branchName,
                  String goal,
                  String mcActual){
        this.BranchCode = branchCode;
        this.BranchName = branchName;
        this.mcGoal = Float.parseFloat(goal);
        this.mcActual = Float.parseFloat(mcActual);
    }

    public String getBranchCode() {
        return BranchCode;
    }

    public String getBranchName() {
        return BranchName;
    }

    public String getMCSalesPercentage() {
        float percentage = mcActual /100;
        float result = percentage * 100;
        return new DecimalFormat("##").format(result) + "%";
    }

    public String getSPSalesPercentage() {
        float percentage = mcActual /100;
        float result = percentage * 100;
        return new DecimalFormat("##").format(result) + "%";
    }

    public String getJobOrderPercentage() {
        float percentage = mcActual /100;
        float result = percentage * 100;
        return new DecimalFormat("##").format(result) + "%";
    }

    public int getDynamicSize() {
        float adds = 100 * .05f;
        return Integer.parseInt(new DecimalFormat("##").format(100 + adds));
    }

    public void setBranchCode(String branchCode) {
        BranchCode = branchCode;
    }

    public void setBranchName(String branchName) {
        BranchName = branchName;
    }

    public float getMcGoal() {
        return mcGoal;
    }

    public void setMcGoal(float mcGoal) {
        this.mcGoal = mcGoal;
    }

    public float getMcActual() {
        return mcActual;
    }

    public void setMcActual(float mcActual) {
        this.mcActual = mcActual;
    }

    public float getSpGoal() {
        return spGoal;
    }

    public void setSpGoal(float spGoal) {
        this.spGoal = spGoal;
    }

    public float getSpActual() {
        return spActual;
    }

    public void setSpActual(float spActual) {
        this.spActual = spActual;
    }

    public float getJoGoal() {
        return joGoal;
    }

    public void setJoGoal(float joGoal) {
        this.joGoal = joGoal;
    }

    public float getJoActual() {
        return joActual;
    }

    public void setJoActual(float joActual) {
        this.joActual = joActual;
    }
}
