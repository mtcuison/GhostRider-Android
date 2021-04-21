package org.rmj.guanzongroup.ghostrider.creditevaluator.Fragments;

import android.os.Build;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Activity.Activity_CIApplication;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Etc.CIConstants;
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
        TransNox = "C0YNQ2100036";
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
            mViewModel.getCIByTransNox(TransNox).observeForever(observe -> System.out.println(observe.getLandMark()));
            infoModel.setTransNox(TransNox);
            infoModel.setLandMark(LandMark);
            infoModel.setOwnershp(Ownershp);
            infoModel.setOwnOther(OwnOther);
            infoModel.setHouseTyp(HouseTyp);
            infoModel.setGaragexx(Garagexx);
            infoModel.setLatitude(Latitude);
            infoModel.setLongitud(Longitud);

            Assert.assertEquals(LandMark ,infoModel.getLandMark());
            Assert.assertEquals(Ownershp ,infoModel.getOwnershp());
            Assert.assertEquals(OwnOther ,infoModel.getOwnOther());
            Assert.assertEquals(HouseTyp ,infoModel.getHouseTyp());
            Assert.assertEquals(Garagexx ,infoModel.getGaragexx());
            Assert.assertEquals(Latitude ,infoModel.getLatitude());
            Assert.assertEquals(Longitud ,infoModel.getLongitud());

            System.out.println("TransNox = " + TransNox + ", infoModel value = " + infoModel.getTransNox());
            for (int x = 0; x < CIConstants.HOUSE_OWNERSHIP.length; x++){
                if (x == Integer.parseInt(Ownershp)){
                    System.out.println("Ownershp = " + CIConstants.HOUSE_OWNERSHIP[x] + ", infoModel value = " + infoModel.getOwnershp());
                }
            }
            for (int x = 0;x < CIConstants.HOUSEHOLDS.length; x++){
                if (x == Integer.parseInt(OwnOther)){
                    System.out.println("OwnOther = " + CIConstants.HOUSEHOLDS[x] + ", infoModel value = " + infoModel.getOwnOther());
                }
            }
            for (int x = 0;x < CIConstants.HOUSE_TYPE.length; x++){
                if (x == Integer.parseInt(HouseTyp)){
                    System.out.println("HouseTyp = " + CIConstants.HOUSE_TYPE[x] + ", infoModel value = " + infoModel.getHouseTyp());
                }
            }
            for (int x = 0;x < CIConstants.GARAGEXX.length; x++){
                if (x == Integer.parseInt(Garagexx)){
                    System.out.println("Has Garage = " + CIConstants.GARAGEXX[x] + ", infoModel value = " + infoModel.getGaragexx());
                }
            }
            System.out.println("Latitude = " + Latitude + ", infoModel value = " + infoModel.getLatitude());
            System.out.println("Longitud = " + Longitud + ", infoModel value = " + infoModel.getLongitud());
            assertTrue(mViewModel.saveCIResidence(infoModel,callBack));
        }catch (NullPointerException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}