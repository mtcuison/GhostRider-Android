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

import android.os.Build;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rmj.guanzongroup.onlinecreditapplication.Model.SpouseEmploymentInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.TestConstants;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMSpouseEmploymentInfo;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.FAKE_CODE;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.FAKE_COMPANY;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.FAKE_STRING;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.FAKE_STRING_AMOUNT;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.STRING_ONE;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.STRING_TWO;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.STRING_ZERO;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.displayArrayAdapterItem;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.displayValue;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest=Config.NONE)
public class Fragment_SpouseEmploymentInfoTest extends TestCase {
    private VMSpouseEmploymentInfo mViewModel;
    private SpouseEmploymentInfoModel infoModel;
    private boolean cTransNox, cSectorxx, isUniform, isMlitary, cProvIdxx, cTownIdxx, cCountryx,
    cJobTitle, cEmpStatx;

    @Before
    public void setUp() {
        mViewModel = new VMSpouseEmploymentInfo(TestConstants.APPLICATION);
        infoModel = new SpouseEmploymentInfoModel();

        mViewModel.setDetailInfo(TestConstants.getDummyCreditApp());
        cTransNox = mViewModel.setTransNox(TestConstants.TRANSACTION_NO);
        cSectorxx = mViewModel.setEmploymentSector(TestConstants.STRING_TWO);
        mViewModel.setPsCmpLvl(TestConstants.STRING_ONE);
        mViewModel.setPsEmpLvl(TestConstants.STRING_TWO);
        mViewModel.setPsBsnssLvl(TestConstants.STRING_ZERO);
        mViewModel.setPsService(TestConstants.STRING_ZERO);
        isUniform = mViewModel.setUniformPersonnel(TestConstants.STRING_ONE);
        isMlitary = mViewModel.setMilitaryPersonnel(TestConstants.STRING_ZERO);
        cProvIdxx = mViewModel.setProvinceID(FAKE_CODE);
        cTownIdxx = mViewModel.setTownID(FAKE_CODE);
        cCountryx = mViewModel.setCountry(FAKE_CODE);
        cJobTitle = mViewModel.setJobTitle(STRING_TWO);
        cEmpStatx = mViewModel.setEmploymentStatus(FAKE_STRING);
    }

    @Test
    public void test_setTransNox() {
        assertTrue(cTransNox);
    }

    @Test
    public void test_setEmploymentSector() {
        assertTrue(cSectorxx);
    }

    @Test
    public void test_setPsCmpLvl() {
        mViewModel.getPsCmpLvl().observeForever(s -> {
            displayValue("Company Level", s);
            assertEquals(TestConstants.STRING_ONE, s);
        });
    }

    @Test
    public void test_setPsEmpLvl() {
        mViewModel.getPsEmpLvl().observeForever(s -> {
            displayValue("Employee Level", s);
            assertEquals(TestConstants.STRING_TWO, s);
        });
    }

    @Test
    public void test_setPsBsnssLvl() {
        mViewModel.getPsBsnssLvl().observeForever(s -> {
            displayValue("Business Level", s);
            assertEquals(TestConstants.STRING_ZERO, s);
        });
    }

    @Test
    public void test_setPsService() {
        mViewModel.getPsService().observeForever(s -> {
            displayValue("Service", s);
            assertEquals(TestConstants.STRING_ZERO, s);
        });
    }

    @Test
    public void test_setUniformPersonnel() {
        assertTrue(isUniform);
    }

    @Test
    public void test_setMilitaryPersonnel() {
        assertTrue(isMlitary);
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
    public void test_setProvinceID() {
        assertTrue(cProvIdxx);
    }

    @Test
    public void test_setTownID() {
        assertTrue(cTownIdxx);
    }

    @Test
    public void test_setCountry() {
        assertTrue(cCountryx);
    }

    @Test
    public void test_setJobTitle() {
        assertTrue(cJobTitle);
    }

    @Test
    public void test_getEmploymentStatus() {
        mViewModel.getEmploymentStatus().observeForever(stringArrayAdapter -> {
            assertNotNull(stringArrayAdapter);
            displayArrayAdapterItem(stringArrayAdapter, "Employment Status");
        });
    }

    @Test
    public void test_setEmploymentStatus() {
        assertTrue(cEmpStatx);
    }

    @Test
    public void test_getLengthOfService() {
        mViewModel.getLengthOfService().observeForever(stringArrayAdapter -> {
            assertNotNull(stringArrayAdapter);
            displayArrayAdapterItem(stringArrayAdapter, "Length of Service");
        });
    }

    @Test
    public void test_Save_PrivateEmp() {
        setPrivateEmployment();
        assertTrue(mViewModel.Save(infoModel, new ViewModelCallBack() {
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

    @Test
    public void test_Save_GovernmentEmp() {
        setGovEmployment();
        assertTrue(mViewModel.Save(infoModel, new ViewModelCallBack() {
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

    @Test
    public void test_Save_OfwEmp() {
        setOfwEmployment();
        assertTrue(mViewModel.Save(infoModel, new ViewModelCallBack() {
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
        infoModel.setSector(STRING_ONE);
        infoModel.setCompanyLvl(STRING_TWO);
        infoModel.setEmployeeLvl(STRING_ONE);
        infoModel.setBizIndustry(STRING_TWO);
        infoModel.setCompanyName(FAKE_COMPANY);
        infoModel.setCompAddress(FAKE_STRING);
        infoModel.setCompTown(FAKE_CODE);
        infoModel.setJobTitle(STRING_TWO);
        infoModel.setJobSpecific(FAKE_STRING);
        infoModel.setEmploymentStat(STRING_ONE);
        infoModel.setLengthOfService(STRING_TWO);
        infoModel.setGrossMonthly(FAKE_STRING_AMOUNT);
    }

    private void setGovEmployment() {
        infoModel.setSector(STRING_ZERO);
        infoModel.setUniformedPersonnel(STRING_ZERO);
        infoModel.setMilitaryPersonnel(STRING_ONE);
        infoModel.setCompanyLvl(STRING_TWO);
        infoModel.setEmployeeLvl(STRING_ONE);
        infoModel.setBizIndustry(STRING_TWO);
        infoModel.setCompanyName(FAKE_COMPANY);
        infoModel.setCompAddress(FAKE_STRING);
        infoModel.setCompTown(FAKE_CODE);
        infoModel.setJobTitle(STRING_TWO);
        infoModel.setJobSpecific(FAKE_STRING);
        infoModel.setEmploymentStat(STRING_ONE);
        infoModel.setLengthOfService(STRING_TWO);
        infoModel.setGrossMonthly(FAKE_STRING_AMOUNT);
    }

    private void setOfwEmployment() {
        infoModel.setSector(STRING_TWO);
        infoModel.setCompanyLvl(STRING_TWO);
        infoModel.setEmployeeLvl(STRING_ONE);
        infoModel.setCountry(STRING_TWO);
        infoModel.setLengthOfService(STRING_TWO);
        infoModel.setMonthOrYear(STRING_ZERO);
        infoModel.setGrossMonthly(FAKE_STRING_AMOUNT);
    }

}
