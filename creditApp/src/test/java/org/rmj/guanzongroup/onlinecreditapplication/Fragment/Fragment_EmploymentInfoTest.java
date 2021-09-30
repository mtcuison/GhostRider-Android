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

package org.rmj.guanzongroup.onlinecreditapplication.Fragment;

import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.FAKE_CODE;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.FAKE_COMPANY;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.FAKE_NUMBER;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.FAKE_STRING;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.FAKE_STRING_AMOUNT;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.STRING_ONE;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.STRING_TWO;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.STRING_ZERO;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.displayArrayAdapterItem;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.displayValue;

import android.os.Build;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rmj.guanzongroup.onlinecreditapplication.Model.EmploymentInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.SpouseEmploymentInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.TestConstants;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMEmploymentInfo;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMSpouseEmploymentInfo;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest=Config.NONE)
public class Fragment_EmploymentInfoTest extends TestCase {
    private VMEmploymentInfo mViewModel;
    private EmploymentInfoModel infoModel;

    private int empSector;
    @Before
    public void setUp() {
        mViewModel = new VMEmploymentInfo(TestConstants.APPLICATION);
        infoModel = new EmploymentInfoModel();
        empSector = 0;
        mViewModel.setCreditApplicantInfo(TestConstants.getDummyCreditApp());

    }


    @Test
    public void test_getCompanyLevelList() {
        mViewModel.getCompanyLevelList().observeForever(stringArrayAdapter -> {
            assertNotNull(stringArrayAdapter);
            displayArrayAdapterItem(stringArrayAdapter, "Company Level");
        });
    }

    @Test
    public void test_getGovernmentLevelList() {
        mViewModel.getGovernmentLevelList().observeForever(stringArrayAdapter -> {
            assertNotNull(stringArrayAdapter);
            displayArrayAdapterItem(stringArrayAdapter, "Government Level");
        });
    }

    @Test
    public void test_getEmployeeLevelList() {
        mViewModel.getEmployeeLevelList().observeForever(stringArrayAdapter -> {
            assertNotNull(stringArrayAdapter);
            displayArrayAdapterItem(stringArrayAdapter, "Employee Level");
        });
    }

    @Test
    public void test_getWorkCategoryList() {
        mViewModel.getWorkCategoryList().observeForever(stringArrayAdapter -> {
            assertNotNull(stringArrayAdapter);
            displayArrayAdapterItem(stringArrayAdapter, "Work Category");
        });
    }

    @Test
    public void test_getRegionList() {
        mViewModel.getRegionList().observeForever(stringArrayAdapter -> {
            assertNotNull(stringArrayAdapter);
            displayArrayAdapterItem(stringArrayAdapter, "Region");
        });
    }

    @Test
    public void test_getBusinessNature() {
        mViewModel.getBusinessNature().observeForever(stringArrayAdapter -> {
            assertNotNull(stringArrayAdapter);
            displayArrayAdapterItem(stringArrayAdapter, "Business Nature");
        });
    }


    @Test
    public void test_getEmploymentStatus() {
        mViewModel.getEmploymentStatus().observeForever(stringArrayAdapter -> {
            assertNotNull(stringArrayAdapter);
            displayArrayAdapterItem(stringArrayAdapter, "Employment Status");
        });
    }
    @Test
    public void test_getLengthOfService() {
        mViewModel.getLengthOfService().observeForever(stringArrayAdapter -> {
            assertNotNull(stringArrayAdapter);
            displayArrayAdapterItem(stringArrayAdapter, "Length of Service");
        });
    }

    @Test
    public void test_saveSpouseEmploymentInfo(){
        if (empSector == 0){
            test_Save_PrivateEmp();
        }else if (empSector == 1){
            test_Save_GovernmentEmp();
        }else if (empSector == 2){
            test_Save_OfwEmp();
        }
    }


    public void test_Save_PrivateEmp() {
        setPrivateEmployment();
        assertTrue(mViewModel.SaveEmploymentInfo(infoModel, new ViewModelCallBack() {
            @Override
            public void onSaveSuccessResult(String args) {
                System.out.println(args);
            }

            @Override
            public void onFailedResult(String message) {
                System.out.println(message);
            }
        }));
    }


    public void test_Save_GovernmentEmp() {
        setGovEmployment();
        assertTrue(mViewModel.SaveEmploymentInfo(infoModel, new ViewModelCallBack() {
            @Override
            public void onSaveSuccessResult(String args) {
                System.out.println(args);
            }

            @Override
            public void onFailedResult(String message) {
                System.out.println(message);
            }
        }));
    }

    public void test_Save_OfwEmp() {
        setOfwEmployment();
        assertTrue(mViewModel.SaveEmploymentInfo(infoModel, new ViewModelCallBack() {
            @Override
            public void onSaveSuccessResult(String args) {
                System.out.println(args);
            }

            @Override
            public void onFailedResult(String message) {
                System.out.println(message);
            }
        }));
    }

    @After
    public void tearDown() {
        mViewModel = null;
        infoModel = null;
    }

    private void setPrivateEmployment() {
        infoModel.setEmploymentSector(STRING_ZERO);
        infoModel.setCompanyLevel(STRING_TWO);
        infoModel.setEmployeeLevel(STRING_ONE);
        infoModel.setBusinessNature(STRING_TWO);
        infoModel.setCompanyName(FAKE_COMPANY);
        infoModel.setCompanyAddress(FAKE_STRING);
        mViewModel.setProvinceID(FAKE_CODE);
        infoModel.setTownID(FAKE_CODE);
        infoModel.setJobTitle(STRING_TWO);
        infoModel.setSpecificJob(FAKE_STRING);
        mViewModel.setEmploymentStatus(STRING_ONE);
        infoModel.setLengthOfService(STRING_TWO);
        infoModel.setIsYear(STRING_TWO);
        infoModel.setsMonthlyIncome(FAKE_STRING_AMOUNT);
        infoModel.setContact(FAKE_NUMBER);
    }

    private void setGovEmployment() {
        infoModel.setEmploymentSector(STRING_ONE);
        infoModel.setUniformPersonal(STRING_ZERO);
        infoModel.setMilitaryPersonal(STRING_ONE);
        infoModel.setCompanyLevel(STRING_TWO);
        infoModel.setEmployeeLevel(STRING_ONE);
        infoModel.setBusinessNature(STRING_TWO);
        infoModel.setCompanyName(FAKE_COMPANY);
        infoModel.setCompanyAddress(FAKE_STRING);
        infoModel.setTownID(FAKE_CODE);
        infoModel.setJobTitle(STRING_TWO);
        infoModel.setSpecificJob(FAKE_STRING);
        infoModel.setEmployeeStatus(STRING_ONE);
        infoModel.setLengthOfService(STRING_TWO);
        infoModel.setsMonthlyIncome(FAKE_STRING_AMOUNT);
    }

    private void setOfwEmployment() {
        infoModel.setEmploymentSector(STRING_TWO);
        infoModel.setCompanyLevel(STRING_TWO);
        infoModel.setEmployeeLevel(STRING_ONE);
        infoModel.setCountry(STRING_TWO);
        infoModel.setLengthOfService(STRING_TWO);
        infoModel.setIsYear(STRING_ZERO);
        infoModel.setsMonthlyIncome(FAKE_STRING_AMOUNT);
    }

}
