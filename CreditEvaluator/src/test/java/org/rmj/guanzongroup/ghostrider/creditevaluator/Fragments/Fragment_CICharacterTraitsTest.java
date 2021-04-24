package org.rmj.guanzongroup.ghostrider.creditevaluator.Fragments;

import android.os.Build;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Assert;
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

        Assert.assertEquals(cbGambler ,infoModel.getCbGambler());
        Assert.assertEquals(cbWomanizer ,infoModel.getCbWomanizer());
        Assert.assertEquals(cbHeavyBrrw ,infoModel.getCbHeavyBrrw());
        Assert.assertEquals(cbRepo ,infoModel.getCbRepo());
        Assert.assertEquals(cbMortage ,infoModel.getCbMortage());
        Assert.assertEquals(cbArrogance ,infoModel.getCbArrogance());
        Assert.assertEquals(cbOthers ,infoModel.getCbOthers());
        Assert.assertEquals(cTranstat ,infoModel.getcTranstat());
        Assert.assertEquals(sRemarks ,infoModel.getsRemarks());

        System.out.println("cbGambler : " +infoModel.getCbGambler());
        System.out.println("cbWomanizer : " +infoModel.getCbWomanizer());
        System.out.println("cbHeavyBrrw : " +infoModel.getCbHeavyBrrw());
        System.out.println("cbRepo : " +infoModel.getCbRepo());
        System.out.println("cbMortage : " +infoModel.getCbMortage());
        System.out.println("cbArrogance : " +infoModel.getCbArrogance());
        System.out.println("cbOthers : " +infoModel.getCbOthers());
        System.out.println("cTranstat : " +infoModel.getcTranstat());
        System.out.println("sRemarks : " +infoModel.getsRemarks());

        assertTrue(infoModel.isValidCharaterData());
    }
}