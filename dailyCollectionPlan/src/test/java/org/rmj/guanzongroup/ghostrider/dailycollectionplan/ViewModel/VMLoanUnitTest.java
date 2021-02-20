package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model.LoanUnitModel;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model.PromiseToPayModel;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;

public class VMLoanUnitTest {

    @Mock
    ViewModelCallback callback;

    @Mock
    LoanUnitModel infoModel;

    @Mock
    VMLoanUnit mViewModel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        infoModel.setLuLastName("Sabiniano");
        infoModel.setLuFirstName("Jonathan");
        infoModel.setLuMiddleName("Tamayo");
        infoModel.setLuSuffix("");
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
        Assert.assertTrue(mViewModel.saveLuInfo(infoModel, callback));
    }
}