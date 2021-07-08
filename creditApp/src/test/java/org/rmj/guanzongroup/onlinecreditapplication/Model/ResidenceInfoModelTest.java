/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.creditApp
 * Electronic Personnel Access Control Security System
 * project file created : 7/5/21 11:00 AM
 * project file last modified : 7/5/21 11:00 AM
 */

package org.rmj.guanzongroup.onlinecreditapplication.Model;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

public class ResidenceInfoModelTest {

    @Mock
    public ResidenceInfoModel poModel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        poModel = new ResidenceInfoModel();
        poModel.setAddress1("");
        poModel.setAddress2("");
        poModel.setBarangayID("0001");
        poModel.setBarangayName("Sample");
        poModel.setHouseNox("231");
        poModel.setLandMark("sample");
        poModel.setMunicipalID("123");
        poModel.setOneAddress(false);
    }

    @Test
    public void isDataValid() {
    }
}