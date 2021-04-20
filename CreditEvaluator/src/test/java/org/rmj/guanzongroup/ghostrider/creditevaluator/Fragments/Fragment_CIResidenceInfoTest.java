package org.rmj.guanzongroup.ghostrider.creditevaluator.Fragments;

import android.os.Build;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Activity.Activity_CIApplication;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Etc.ViewModelCallBack;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Model.CIResidenceInfoModel;
import org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel.VMCIResidenceInfo;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest= Config.NONE)
public class Fragment_CIResidenceInfoTest {
    private CIResidenceInfoModel infoModel;
    private VMCIResidenceInfo mViewModel;
    String TransNox;
    String LandMark;
    String Ownershp;
    String OwnOther;
    String HouseTyp;
    String Garagexx;
    String Latitude;
    String Longitud;
    @Mock
    ViewModelCallBack callBack;
    @Before
    public void setUp() throws Exception {
        TransNox = "C0YNQ2100035";
        LandMark = "Sample Landmark";
        Ownershp = "0";
        OwnOther = "1";
        HouseTyp = "1";
        Garagexx = "1";
        Latitude = "16.0357421";
        Longitud = "120.3316118";
        infoModel = new CIResidenceInfoModel();
        mViewModel = new VMCIResidenceInfo(ApplicationProvider.getApplicationContext());
        mViewModel.setsTransNox(TransNox);
    }

    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void test_saveCIResisdenceInfo(){
        try {
            mViewModel.setsTransNox(TransNox);

            infoModel.setTransNox(TransNox);
            infoModel.setLandMark(LandMark);
            infoModel.setOwnershp(Ownershp);
            infoModel.setOwnOther(OwnOther);
            infoModel.setHouseTyp(HouseTyp);
            infoModel.setGaragexx(Garagexx);
            infoModel.setLatitude(Latitude);
            infoModel.setLongitud(Longitud);
            System.out.println("TransNox = " + infoModel.getTransNox());
            assertTrue(mViewModel.saveCIResidence(infoModel,callBack));
        }catch (NullPointerException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}