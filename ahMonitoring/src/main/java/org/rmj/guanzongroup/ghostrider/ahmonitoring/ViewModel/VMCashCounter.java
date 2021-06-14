/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 6/7/21 2:35 PM
 * project file last modified : 6/7/21 2:35 PM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;

public class VMCashCounter extends AndroidViewModel {

    private static final String TAG = VMCashCounter.class.getSimpleName();
    private final Application instance;
    private final REmployee poEmploye;
    private final RBranch poBranch;

    private final MutableLiveData<Double> p1000 = new MutableLiveData<>();
    private final MutableLiveData<Double> p500 = new MutableLiveData<>();
    private final MutableLiveData<Double> p200 = new MutableLiveData<>();
    private final MutableLiveData<Double> p100 = new MutableLiveData<>();
    private final MutableLiveData<Double> p50 = new MutableLiveData<>();
    private final MutableLiveData<Double> p20 = new MutableLiveData<>();

    private final MutableLiveData<Double> p10 = new MutableLiveData<>();
    private final MutableLiveData<Double> p5 = new MutableLiveData<>();
    private final MutableLiveData<Double> p1 = new MutableLiveData<>();
    private final MutableLiveData<Double> c50 = new MutableLiveData<>();
    private final MutableLiveData<Double> c25 = new MutableLiveData<>();
    private final MutableLiveData<Double> c10 = new MutableLiveData<>();
    private final MutableLiveData<Double> c5 = new MutableLiveData<>();

    private final MutableLiveData<Double> pnTotalx = new MutableLiveData<>();
    private final MutableLiveData<Double> cnTotalx = new MutableLiveData<>();
    private final MutableLiveData<Double> grandTotalx = new MutableLiveData<>();
    private final MutableLiveData<JSONObject> jsonData = new MutableLiveData<>();
    public VMCashCounter(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poEmploye = new REmployee(application);
        this.poBranch = new RBranch(application);
        this.p1000.setValue((double) 0);
        this.p500.setValue((double) 0);
        this.p200.setValue((double) 0);
        this.p100.setValue((double) 0);
        this.p50.setValue((double) 0);
        this.p20.setValue((double) 0);

        this.p10.setValue((double) 0);
        this.p5.setValue((double) 0);
        this.p1.setValue((double) 0);
        this.c50.setValue((double) 0);
        this.c25.setValue((double) 0);
        this.c10.setValue((double) 0);
        this.c5.setValue((double) 0);

        this.pnTotalx.setValue((double) 0);
        this.cnTotalx.setValue((double) 0);
        this.grandTotalx.setValue((double) 0);
    }
    public LiveData<EEmployeeInfo> getEmplopyeInfo(){
        return this.poEmploye.getEmployeeInfo();
    }
    public LiveData<EBranchInfo> getUserBranchInfo(){
        return poBranch.getUserBranchInfo();
    }
    //Added by Jonathan 2021/06/08
    //Computation for all peso bill
    private void calculatePesoTotal(){
        double d1000 = p1000.getValue();
        double d500 = p500.getValue();
        double d200 = p200.getValue();
        double d100 = p100.getValue();
        double d50 = p50.getValue();
        double d20 = p20.getValue();
        double lnTotal = d1000 + d500 + d200 + d100 + d50 + d20;
        pnTotalx.setValue(lnTotal);
        calc_grandTotal();

    }

    //Added by Jonathan 2021/04/13
    //set  peso bill value
    public void setP1000(Double fnAmount){
        this.p1000.setValue(fnAmount);
        calculatePesoTotal();
    }
    public void setP500(Double fnAmount){
        this.p500.setValue(fnAmount);
        calculatePesoTotal();
    }
    public void setP200(Double fnAmount){
        this.p200.setValue(fnAmount);
        calculatePesoTotal();
    }
    public void setP100(Double fnAmount){
        this.p100.setValue(fnAmount);
        calculatePesoTotal();
    }
    public void setP50(Double fnAmount){
        this.p50.setValue(fnAmount);
        calculatePesoTotal();
    }
    public void setP20(Double fnAmount){
        this.p20.setValue(fnAmount);
        calculatePesoTotal();
    }
    public LiveData<Double> getPesoTotalAmount(){
        return pnTotalx;
    }

    //Added by Jonathan 2021/06/08
    //Computation for all peso bill
    private void calculateCoinsTotal(){

        double d10 = p10.getValue();
        double d5 = p5.getValue();
        double d1 = p1.getValue();
        double dc50 = c50.getValue();
        double dc25 = c25.getValue();
        double dc10 = c10.getValue();
        double dc5 = c5.getValue();
        double lnCTotal = d10 + d5 + d1 + dc50 + dc25 + dc10+ dc5;
        cnTotalx.setValue(lnCTotal);
        calc_grandTotal();
    }

    //Added by Jonathan 2021/04/13
    //set coins bill value
    public void setP10(Double fnAmount){
        this.p10.setValue(fnAmount);
        calculateCoinsTotal();
    }
    public void setP5(Double fnAmount){
        this.p5.setValue(fnAmount);
        calculateCoinsTotal();
    }
    public void setP1(Double fnAmount){
        this.p1.setValue(fnAmount);
        calculateCoinsTotal();
    }
    public void setC50(Double fnAmount){
        this.c50.setValue(fnAmount);
        calculateCoinsTotal();
    }
    public void setC25(Double fnAmount){
        this.c25.setValue(fnAmount);
        calculateCoinsTotal();
    }
    public void setC10(Double fnAmount){
        this.c10.setValue(fnAmount);
        calculateCoinsTotal();
    }
    public void setC5(Double fnAmount){
        this.c5.setValue(fnAmount);
        calculateCoinsTotal();
    }
    public LiveData<Double> getCoinsTotalAmount(){
        return cnTotalx;
    }
    public void calc_grandTotal(){
        double pesoTotal = pnTotalx.getValue();
        double coinTotal = cnTotalx.getValue();
        double total = pesoTotal + coinTotal;
        grandTotalx.setValue(total);
        createJSONParameters();
    }
    public LiveData<Double> getGrandTotalAmount(){
        return grandTotalx;
    }
    public LiveData<JSONObject> getJsonData(){
        return jsonData;
    }
    private JSONObject createJSONParameters(){
        JSONObject param = new JSONObject();
        try {
            param.put("trandate", AppConstants.CURRENT_DATE);
            param.put("nCn0005cx", c5.getValue());
            param.put("nCn0010cx", c10.getValue());
            param.put("nCn0025cx", c25.getValue());
            param.put("nCn0050cx", c50.getValue());
            param.put("nCn0001px", p1.getValue());
            param.put("nCn0005px", p5.getValue());
            param.put("nCn0010px", p10.getValue());
            param.put("nNte0020p", p20.getValue());
            param.put("nNte0050p", p50.getValue());
            param.put("nNte0100p", p100.getValue());
            param.put("nNte0200p", p200.getValue());
            param.put("nNte0500p", p500.getValue());
            param.put("nNte1000p", p1000.getValue());
        }catch (JSONException e) {
            e.printStackTrace();
        }
        jsonData.setValue(param);
        return param;
    }
}
