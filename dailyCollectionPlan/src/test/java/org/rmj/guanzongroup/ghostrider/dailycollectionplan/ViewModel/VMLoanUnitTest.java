package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import android.os.Build;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Fragments.Fragment_LoanUnit;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model.LoanUnitModel;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model.PromiseToPayModel;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;

public class VMLoanUnitTest {


    @Mock
    LoanUnitModel infoModel;
    @Mock
    VMLoanUnit mViewModel;

    @Mock
    ViewModelCallback callback;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        infoModel = new LoanUnitModel();
        infoModel.setLuCivilStats("0");
        infoModel.setLuGender("0");
        infoModel.setLuBDate("03/06/1990");
        infoModel.setLuPhone("");
        infoModel.setLuMobile("09452086661");
        infoModel.setLuEmail("jonsabiniano03@gmail.com");
        infoModel.setLuLastName("Sabiniano");
        infoModel.setLuFirstName("Jonathan");
        infoModel.setLuMiddleName("Tamayo");
        infoModel.setLuSuffix("");
        infoModel.setLuImgPath("sadsdasdas");
        //Address
        infoModel.setLuHouseNo("627");
        infoModel.setLuStreet("Ampongan");
        infoModel.setLuTown("0335");
        infoModel.setLuBrgy("1200145");
        infoModel.setLuBPlace("0335");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test_saveLuInfo() {
        doReturn(true).when(mViewModel).saveLuInfo(infoModel, callback);
        Assert.assertEquals(true, mViewModel.saveLuInfo(infoModel, callback));
    }
}