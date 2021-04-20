package org.rmj.guanzongroup.ghostrider.creditevaluator.Fragments;

import android.os.Build;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Etc.ViewModelCallBack;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Model.CIBarangayRecordInfoModel;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Model.CharacterTraitsInfoModel;
import org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel.VMCIBarangayRecords;
import org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel.VMCIResidenceInfo;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest= Config.NONE)
public class Fragment_CIBarangayRecordTest {
    private String HasOther;
    private String HasRecrd;
    private String RemRecrd;

    private String Neighbr1;
    private String Address1;
    private String ReltnCD1;
    private String MobileN1;
    private String FeedBck1;
    private String FBRemrk1;

    private String Neighbr2;
    private String Address2;
    private String ReltnCD2;
    private String MobileN2;
    private String FeedBck2;
    private String FBRemrk2;

    private String Neighbr3;
    private String Address3;
    private String ReltnCD3;
    private String MobileN3;
    private String FeedBck3;
    private String FBRemrk3;
    private CIBarangayRecordInfoModel infoModel;
    private VMCIBarangayRecords mViewModel;
    @Mock
    ViewModelCallBack callBack;
    @Before
    public void setUp() throws Exception {
        infoModel = new CIBarangayRecordInfoModel();
        mViewModel = new VMCIBarangayRecords(ApplicationProvider.getApplicationContext());
        HasRecrd = "0";
        RemRecrd = "Sample Record";

        Neighbr1 = "Sample 1";
        ReltnCD1 = "sample 1";
        MobileN1 = "09987654321";
        FeedBck1 = "0";
        FBRemrk1 = "Sample Feedback 1";

        Neighbr2 = "Sample 2";
        ReltnCD2 = "sample 2";
        MobileN2 = "09949494949";
        FeedBck2 = "0";
        FBRemrk2 = "Sample Feedback 2";

        Neighbr3 = "Sample 3";
        ReltnCD3 = "sample 3";
        MobileN3 = "09454546466";
        FeedBck3 = "0";
        FBRemrk3 = "Sample Feedback 3";
    }

    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void setNeighbor1(){
        infoModel.setNeighbr1(Neighbr1);
        infoModel.setReltnCD1(ReltnCD1);
        infoModel.setMobileN1(MobileN1);
        infoModel.setFeedBck1(FeedBck1);
        infoModel.setFBRemrk1(FBRemrk1);
        assertTrue(mViewModel.saveNeighbor(infoModel,"Neighbor3",callBack));
    }
    @Test
    public void setNeighbor2(){
        infoModel.setNeighbr2(Neighbr2);
        infoModel.setReltnCD2(ReltnCD2);
        infoModel.setMobileN2(MobileN2);
        infoModel.setFeedBck2(FeedBck2);
        infoModel.setFBRemrk2(FBRemrk2);
        assertTrue(mViewModel.saveNeighbor(infoModel,"Neighbor2",callBack));
    }
    @Test
    public void setNeighbor3(){
        infoModel.setNeighbr3(Neighbr3);
        infoModel.setReltnCD3(ReltnCD3);
        infoModel.setMobileN3(MobileN3);
        infoModel.setFeedBck3(FeedBck3);
        infoModel.setFBRemrk3(FBRemrk3);
        assertTrue(mViewModel.saveNeighbor(infoModel,"Neighbor3",callBack));
    }
    @Test
    public void saveBrangayRecord(){
        infoModel.setHasRecrd(HasRecrd);
        if (infoModel.getHasRecrd().equalsIgnoreCase("1")){
            infoModel.setRemRecrd(RemRecrd);
        }else if (infoModel.getHasRecrd().equalsIgnoreCase("0")){
            infoModel.setRemRecrd("");
        }
        setNeighbor1();
        setNeighbor2();
        setNeighbor3();
        assertTrue(mViewModel.saveNeighbor(infoModel,"",callBack));
    }
}