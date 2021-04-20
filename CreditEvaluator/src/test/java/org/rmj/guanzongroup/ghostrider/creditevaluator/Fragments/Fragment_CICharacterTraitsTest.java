package org.rmj.guanzongroup.ghostrider.creditevaluator.Fragments;

import android.os.Build;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Model.CharacterTraitsInfoModel;
import org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel.VMCIBarangayRecords;
import org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel.VMCICharacteristics;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest= Config.NONE)
public class Fragment_CICharacterTraitsTest {

    private String cbGambler;
    private String cbWomanizer;
    private String cbHeavyBrrw;
    private String cbRepo;
    private String cbMortage;
    private String cbArrogance;
    private String cbOthers;
    private String cTranstat;
    private String sRemarks;
    private CharacterTraitsInfoModel infoModel;
    private VMCICharacteristics mViewModel;
    @Before
    public void setUp() throws Exception {
        infoModel = new CharacterTraitsInfoModel();
        mViewModel = new VMCICharacteristics(ApplicationProvider.getApplicationContext());
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
    public void saveCharacterTraits(){

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