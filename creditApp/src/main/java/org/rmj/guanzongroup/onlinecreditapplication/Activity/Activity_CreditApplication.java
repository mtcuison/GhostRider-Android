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

package org.rmj.guanzongroup.onlinecreditapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.gocas.base.GOCASApplication;
import org.rmj.gocas.pojo.ApplicantInfo;
import org.rmj.gocas.pojo.MeansEmployed;
import org.rmj.gocas.pojo.MeansFinancer;
import org.rmj.gocas.pojo.MeansInfo;
import org.rmj.gocas.pojo.MeansPensioner;
import org.rmj.gocas.pojo.MeansSelfEmployed;
import org.rmj.gocas.pojo.ResidenceInfo;
import org.rmj.gocas.pojo.SpouseInfo;
import org.rmj.guanzongroup.onlinecreditapplication.Adapter.FragmentAdapter;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_EmploymentInfo;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_MeansInfoSelection;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_PersonalInfo;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_SelfEmployedInfo;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_SpouseInfo;
import org.rmj.guanzongroup.onlinecreditapplication.R;

import java.util.Objects;

public class Activity_CreditApplication extends AppCompatActivity {
    private static final String TAG = Activity_CreditApplication.class.getSimpleName();
    private static Activity_CreditApplication instance;
    private ViewPager viewPager;
    private String transNox, otherIncomeNature;
    private long otherIncomeAmount;
    private ApplicantInfo applicantInfo;
    private ResidenceInfo residenceInfo;
    private MeansEmployed meansEmployedInfo;
    private MeansSelfEmployed meansSelfEmployed;
    private MeansPensioner meansPensioner;
    private MeansFinancer meansFinancer;
    private ApplicantInfo spouseInfo;
    private ResidenceInfo spouseResidenceInfo;

    private GOCASApplication poGoCas;
    public static Activity_CreditApplication getInstance(){
        return instance;
    }

    public String getTransNox(){
        return transNox;
    }

    public void moveToPageNumber(int fnPageNum){
        viewPager.setCurrentItem(fnPageNum);
    }

//    SETTER
//      PPOJO CLASS of GOCAS

    public void setApplicantInfo(ApplicantInfo applicantInfo){
        this.applicantInfo = applicantInfo;
        Log.e(TAG, poGoCas.ApplicantInfo().toJSONString());
    }
    public void setResidenceInfo(ResidenceInfo residenceInfo){
        this.residenceInfo = residenceInfo;
        Log.e(TAG, "residence = " + residenceInfo.toJSONString());
    }
    public void setMeansEmployedInfo(MeansEmployed meansEmployedInfo) {
        this.meansEmployedInfo = meansEmployedInfo;
    }
    public void setSpouseInfo(ApplicantInfo spouseInfo) {
        this.spouseInfo = spouseInfo;
    }
    public void setSpouseResidenceInfo(ResidenceInfo spouseresidenceInfo) {
        this.spouseResidenceInfo = spouseresidenceInfo;
    }
    public void setMeansSelfEmployed(MeansSelfEmployed meansSelfEmployed) {
        this.meansSelfEmployed = meansSelfEmployed;
    }
    public void setMeansPensioner(MeansPensioner meansPensioner) {
        this.meansPensioner = meansPensioner;
    }
    public void setOtherIncomeNature(String otherIncomeNature) {
        this.otherIncomeNature = otherIncomeNature;
    }
    public void setOtherIncomeAmount(long otherIncomeAmount) {
        this.otherIncomeAmount = otherIncomeAmount;
    }

//    GETTER
//      PPOJO CLASS of GOCAS
    public ApplicantInfo getApplicantInfo(){
        return applicantInfo;
    }
    public ResidenceInfo getResidenceInfo(){
        return residenceInfo;
    }
    public MeansEmployed getMeansEmployedInfo() {
        return meansEmployedInfo;
    }

    public ApplicantInfo getSpouseInfo() {
        return spouseInfo;
    }

    public MeansSelfEmployed getMeansSelfEmployed() {
        return meansSelfEmployed;
    }
    public MeansPensioner getMeansPensioner() {
        return meansPensioner;
    }

    public MeansFinancer getMeansFinancer() {
        return meansFinancer;
    }

    public void setMeansFinancer(MeansFinancer meansFinancer) {
        this.meansFinancer = meansFinancer;
    }

    public void sendCreditApplication(){
        poGoCas.ApplicantInfo().setData(applicantInfo.toJSON());
        poGoCas.ResidenceInfo().setData(residenceInfo.toJSON());

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_application);
        instance = this;
        poGoCas = new GOCASApplication();
        transNox = getIntent().getStringExtra("transno");
        initWidgets();
    }

    @Override
    public void onBackPressed() {
        MessageBox loMessage = new MessageBox(Activity_CreditApplication.this);
        loMessage.initDialog();
        loMessage.setTitle("Loan Application");
        loMessage.setMessage("Exit loan application?");
        loMessage.setPositiveButton("Yes", (view, dialog) -> {
            dialog.dismiss();
            finish();
        });
        loMessage.setNegativeButton("No", (view, dialog) -> dialog.dismiss());
        loMessage.show();
    }

    private void initWidgets(){
        Toolbar toolbar = findViewById(R.id.toolbar_application);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Bundle loBundle = new Bundle();
        loBundle.putString("transno", transNox);
        Fragment_PersonalInfo personalInfo = new Fragment_PersonalInfo();
        personalInfo.setArguments(loBundle);
        viewPager = findViewById(R.id.viewpager_creditApp);
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), CreditAppConstants.APPLICATION_PAGES));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            MessageBox loMessage = new MessageBox(Activity_CreditApplication.this);
            loMessage.initDialog();
            loMessage.setTitle("Credit Application");
            loMessage.setMessage("Exit credit online application?");
            loMessage.setPositiveButton("Yes", (view, dialog) -> {
                dialog.dismiss();
                finish();
            });
            loMessage.setNegativeButton("No", (view, dialog) -> dialog.dismiss());
            loMessage.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getViewModelStore().clear();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}