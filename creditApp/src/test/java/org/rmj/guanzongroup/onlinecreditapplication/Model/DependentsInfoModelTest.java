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

import android.os.Build;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest=Config.NONE)
public class DependentsInfoModelTest {
    private DependentsInfoModel infoModel;
    private ArrayList<DependentsInfoModel> arrayList;
    @Before
    public void setUp() throws Exception {
        infoModel = new DependentsInfoModel();
        arrayList = new ArrayList<>();
        infoModel.setDpdFullname("Jonathan Sabiniano");
        infoModel.setDpdRlationship("2");
        infoModel.setDpdAge("26");
        infoModel.setIsStudent("1");
        infoModel.setDpdSchoolType("0");
        infoModel.setDpdEducLevel("0");
        infoModel.setDpdIsScholar("1");
        infoModel.setDpdSchoolName("University of Pangasinan");
        infoModel.setDpdSchoolAddress("Arellano Street");
        infoModel.setDpdSchoolProv("20");
        infoModel.setDpdSchoolTown("20");
        infoModel.setIsEmployed("1");
        infoModel.setDpdEmployedSector("1");
        infoModel.setDpdCompanyName("Guanzon Group of Companies");
        infoModel.setIsDependent("1");
        infoModel.setIsHouseHold("1");
        infoModel.setIsMarried("0");
        arrayList.add(infoModel);
    }

    @After
    public void tearDown() throws Exception {
        infoModel = null;
        arrayList = null;
    }
    @Test
    public void test_addDependents() {
        DependentsInfoModel infoModels = new DependentsInfoModel();
        infoModels.setDpdFullname("Jonathan Sabiniano");
        infoModels.setDpdRlationship("2");
        infoModels.setDpdAge("26");
        infoModels.setIsStudent("1");
        infoModels.setDpdSchoolType("0");
        infoModels.setDpdEducLevel("0");
        infoModels.setDpdIsScholar("1");
        infoModels.setDpdSchoolName("University of Pangasinan");
        infoModels.setDpdSchoolAddress("Arellano Street");
        infoModels.setDpdSchoolProv("20");
        infoModels.setDpdSchoolTown("20");
        infoModels.setIsEmployed("1");
        infoModels.setDpdEmployedSector("1");
        infoModels.setDpdCompanyName("Guanzon Group of Companies");
        infoModels.setIsDependent("1");
        infoModels.setIsHouseHold("1");
        infoModels.setIsMarried("0");
        arrayList.add(infoModels);
        assertEquals(2, arrayList.size());
    }
    @Test
    public void test_getSize() {
        assertEquals(1, arrayList.size());
    }
    @Test
    public void test_removeDependent() {
        arrayList.remove(0);
        assertEquals(0, arrayList.size());
    }
    @Test
    public void test_isDataValid() {
        assertTrue(infoModel.isDataValid());
    }
}