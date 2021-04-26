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

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.rmj.guanzongroup.onlinecreditapplication.Model.PersonalInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class VMPersonalInfoTest {

    @Mock
    ViewModelCallBack callBack;

    @InjectMocks
    VMPersonalInfo mViewModel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void setTransNox() {
        mViewModel.setTransNox("sTransNox");
    }

    @Test
    public void savePersonalInfo() {
        mViewModel.SavePersonalInfo(new PersonalInfoModel(), callBack);

        verify(callBack, times(1)).onSaveSuccessResult("Sample");
        verify(callBack, times(0)).onFailedResult("error");
    }

    @Test
    public void setProvID() {
    }

    @Test
    public void setTownID() {
    }

    @Test
    public void setGender() {
    }

    @Test
    public void setCvlStats() {
    }

    @Test
    public void setCitizenship() {
    }

    @Test
    public void setMotherMaidenNameVisibility() {
    }
}