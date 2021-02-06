package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.app.Application;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.rmj.g3appdriver.GRider.Database.Repositories.RProvince;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class VMResidenceInfoTest {

    private RProvince poProvince;

    public void initData(Application application){
        poProvince = new RProvince(application);
    }

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getProvinceNameList() {

    }
}