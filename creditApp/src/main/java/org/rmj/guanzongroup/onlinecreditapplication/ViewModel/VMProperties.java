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

package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplicant;
import org.rmj.gocas.base.GOCASApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.GOCASHolder;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;

public class VMProperties extends AndroidViewModel {
    private static final String TAG = VMProperties.class.getSimpleName();

    private final RCreditApplicant poCreditApp;

    private final MutableLiveData<String> psTransNox = new MutableLiveData<>();
    private String psLot1Addx = "";
    private String psLot2Addx = "";
    private String psLot3Addx = "";
    private String ps4Wheelsx = "";
    private String ps3Wheelsx = "";
    private String ps2Wheelsx = "";
    private String psAirConxx = "";
    private String psFridgexx = "";
    private String psTelevsnx = "";

    private ECreditApplicantInfo poInfo;
    private GOCASApplication poGOcas;

    public VMProperties(@NonNull Application application) {
        super(application);
        this.poCreditApp = new RCreditApplicant(application);
        this.poGOcas = GOCASHolder.getInstance().getGOCAS();
        this.poInfo = new ECreditApplicantInfo();
    }

    public void setTransNox(String transNox){
        this.psTransNox.setValue(transNox);
    }

    public LiveData<ECreditApplicantInfo> getCurrentApplicantInfo(){
        return poCreditApp.getCreditApplicantInfoLiveData(psTransNox.getValue());
    }

    public void setCreditApplicantInfo(ECreditApplicantInfo applicantInfo) throws Exception{
        this.poInfo = applicantInfo;
    }

    public void SavePropertiesInfo(ViewModelCallBack callBack){
        poGOcas.DisbursementInfo().PropertiesInfo().setLotName1(psLot1Addx);
        poGOcas.DisbursementInfo().PropertiesInfo().setLotName2(psLot2Addx);
        poGOcas.DisbursementInfo().PropertiesInfo().setLotName3(psLot3Addx);
        poGOcas.DisbursementInfo().PropertiesInfo().Has4Wheels(ps4Wheelsx);
        poGOcas.DisbursementInfo().PropertiesInfo().Has3Wheels(ps3Wheelsx);
        poGOcas.DisbursementInfo().PropertiesInfo().Has2Wheels(ps2Wheelsx);
        poGOcas.DisbursementInfo().PropertiesInfo().WithAirCon(psAirConxx);
        poGOcas.DisbursementInfo().PropertiesInfo().WithRefrigerator(psFridgexx);
        poGOcas.DisbursementInfo().PropertiesInfo().WithTelevision(psTelevsnx);
        poInfo.setProperty(poGOcas.DisbursementInfo().PropertiesInfo().toJSONString());
        Log.e(TAG, poGOcas.toJSONString());
        poCreditApp.updateGOCasData(poInfo);
        callBack.onSaveSuccessResult(psTransNox.getValue());
    }

    public String getPsLot1Addx() {
        return psLot1Addx;
    }

    public void setPsLot1Addx(String psLot1Addx) {
        this.psLot1Addx = psLot1Addx;
    }

    public String getPsLot2Addx() {
        return psLot2Addx;
    }

    public void setPsLot2Addx(String psLot2Addx) {
        this.psLot2Addx = psLot2Addx;
    }

    public String getPsLot3Addx() {
        return psLot3Addx;
    }

    public void setPsLot3Addx(String psLot3Addx) {
        this.psLot3Addx = psLot3Addx;
    }

    public String getPs4Wheelsx() {
        return ps4Wheelsx;
    }

    public void setPs4Wheelsx(String ps4Wheelsx) {
        this.ps4Wheelsx = ps4Wheelsx;
    }

    public String getPs3Wheelsx() {
        return ps3Wheelsx;
    }

    public void setPs3Wheelsx(String ps3Wheelsx) {
        this.ps3Wheelsx = ps3Wheelsx;
    }

    public String getPs2Wheelsx() {
        return ps2Wheelsx;
    }

    public void setPs2Wheelsx(String ps2Wheelsx) {
        this.ps2Wheelsx = ps2Wheelsx;
    }

    public String getPsAirConxx() {
        return psAirConxx;
    }

    public void setPsAirConxx(String psAirConxx) {
        this.psAirConxx = psAirConxx;
    }

    public String getPsFridgexx() {
        return psFridgexx;
    }

    public void setPsFridgexx(String psFridgexx) {
        this.psFridgexx = psFridgexx;
    }

    public String getPsTelevsnx() {
        return psTelevsnx;
    }

    public void setPsTelevsnx(String psTelevsnx) {
        this.psTelevsnx = psTelevsnx;
    }
}