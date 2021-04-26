/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.CreditEvaluator
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.creditevaluator.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CharacterTraitsInfoModelTest {
    private String cbGambler;
    private String cbWomanizer;
    private String cbHeavyBrrw;
    private String cbRepo;
    private String cbMortage;
    private String cbArrogance;
    private String cbOthers;
    private String cTranstat;
    private String sRemarks;
    CharacterTraitsInfoModel infoModel;
    @Before
    public void setUp() throws Exception {
        infoModel = new CharacterTraitsInfoModel();
        cbGambler = "0";
        cbWomanizer = "0";
        cbHeavyBrrw = "0";
        cbRepo = "0";
        cbMortage = "0";
        cbArrogance = "0";
        cbOthers = "0";
        cTranstat = "1";
        sRemarks = "Okay for Approval";
    }

    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void isValidCharaterData(){
        infoModel.setCbGambler(cbGambler);
        infoModel.setCbWomanizer(cbWomanizer);
        infoModel.setCbHeavyBrrw(cbHeavyBrrw);
        infoModel.setCbRepo(cbRepo);
        infoModel.setCbMortage(cbMortage);
        infoModel.setCbArrogance(cbArrogance);
        infoModel.setCbOthers(cbOthers);
        infoModel.setcTranstat(cTranstat);
        infoModel.setsRemarks(sRemarks);
        assertTrue(infoModel.isValidCharaterData());
    }
}