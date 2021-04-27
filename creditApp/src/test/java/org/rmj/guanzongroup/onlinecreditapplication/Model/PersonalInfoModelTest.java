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

package org.rmj.guanzongroup.onlinecreditapplication.Model;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

public class PersonalInfoModelTest {

    @Mock
    PersonalInfoModel infoModel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        infoModel = new PersonalInfoModel();

        infoModel.setMobileNo("09171870011", "1", 2);
    }

    @Test
    public void testMobile1DuplicateMobileValidation(){
        boolean result = infoModel.isPrimaryContactValid();
        assertTrue(result);
    }

    @Test
    public void testMobile2DuplicateMobileValidation(){
        boolean result = infoModel.isSecondaryContactValid();
        assertTrue(result);
    }
}