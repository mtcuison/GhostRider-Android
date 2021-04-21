package org.rmj.guanzongroup.ghostrider.creditevaluator.Fragments;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.Build;
import android.view.View;
import android.widget.RadioButton;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Activity.Activity_CIApplication;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Etc.ViewModelCallBack;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Model.CIBarangayRecordInfoModel;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Model.CharacterTraitsInfoModel;
import org.rmj.guanzongroup.ghostrider.creditevaluator.R;
import org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel.VMCIBarangayRecords;
import org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel.VMCIResidenceInfo;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest= Config.NONE, application = Application.class)
public class Fragment_CIBarangayRecordTest  implements ViewModelCallBack {
    public static String HasOther;
    public static String HasRecrd;
    public static String RemRecrd;

    public static String Neighbr1;
    public static String Address1;
    public static String ReltnCD1;
    public static String MobileN1;
    public static String FeedBck1;
    public static String FBRemrk1;

    public static String Neighbr2;
    public static String Address2;
    public static String ReltnCD2;
    public static String MobileN2;
    public static String FeedBck2;
    public static String FBRemrk2;

    public static String Neighbr3;
    public static String Address3;
    public static String ReltnCD3;
    public static String MobileN3;
    public static String FeedBck3;
    public static String FBRemrk3;

    public CIBarangayRecordInfoModel infoModel;

    VMCIBarangayRecords mViewModel;
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
        FBRemrk1 = "";

        Neighbr2 = "Sample 2";
        ReltnCD2 = "sample 2";
        MobileN2 = "09949494949";
        FeedBck2 = "0";
        FBRemrk2 = "";

        Neighbr3 = "Sample 3";
        ReltnCD3 = "sample 3";
        MobileN3 = "09454546466";
        FeedBck3 = "0";
        FBRemrk3 = "";
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
        if (infoModel.getFeedBck1().equalsIgnoreCase("1")){
            infoModel.setFBRemrk1("Sample Feedback 1");
        }else if (infoModel.getFeedBck1().equalsIgnoreCase("0")){
            infoModel.setFBRemrk1("");
        }

        Assert.assertEquals(Neighbr1 ,infoModel.getNeighbr1());
        Assert.assertEquals(ReltnCD1 ,infoModel.getReltnCD1());
        Assert.assertEquals(MobileN1 ,infoModel.getMobileN1());
        Assert.assertEquals(FeedBck1 ,infoModel.getFeedBck1());
        Assert.assertEquals(FBRemrk1 ,infoModel.getFBRemrk1());

        System.out.print("Neighbr1 : " +infoModel.getNeighbr1() + "\n");
        System.out.print("ReltnCD1 : " +infoModel.getReltnCD1() + "\n");
        System.out.print("MobileN1 : " +infoModel.getMobileN1() + "\n");
        System.out.print("FeedBck1 : " +infoModel.getFeedBck1() + "\n");
        System.out.print("FBRemrk1 : " +infoModel.getFBRemrk1() + "\n");
        assertTrue(mViewModel.saveNeighbor(infoModel,"Neighbor1",callBack));
    }
    @Test
    public void setNeighbor2(){
        infoModel.setNeighbr2(Neighbr2);
        infoModel.setReltnCD2(ReltnCD2);
        infoModel.setMobileN2(MobileN2);
        infoModel.setFeedBck2(FeedBck2);

        if (infoModel.getFeedBck2().equalsIgnoreCase("1")){
            infoModel.setFBRemrk2("Sample Feedback 2");
        }else if (infoModel.getFeedBck2().equalsIgnoreCase("0")){
            infoModel.setFBRemrk2("");
        }

        Assert.assertEquals(Neighbr2 ,infoModel.getNeighbr2());
        Assert.assertEquals(ReltnCD2 ,infoModel.getReltnCD2());
        Assert.assertEquals(MobileN2 ,infoModel.getMobileN2());
        Assert.assertEquals(FeedBck2 ,infoModel.getFeedBck2());
        Assert.assertEquals(FBRemrk2 ,infoModel.getFBRemrk2());

        System.out.print("Neighbr2 : " +infoModel.getNeighbr2() + "\n");
        System.out.print("ReltnCD2 : " +infoModel.getReltnCD2() + "\n");
        System.out.print("MobileN2 : " +infoModel.getMobileN2() + "\n");
        System.out.print("FeedBck2 : " +infoModel.getFeedBck2() + "\n");
        System.out.print("FBRemrk2 : " +infoModel.getFBRemrk2() + "\n");
        assertTrue(mViewModel.saveNeighbor(infoModel,"Neighbor2",callBack));
    }
    @Test
    public void setNeighbor3(){
        infoModel.setNeighbr3(Neighbr3);
        infoModel.setReltnCD3(ReltnCD3);
        infoModel.setMobileN3(MobileN3);
        infoModel.setFeedBck3(FeedBck3);

        if (infoModel.getFeedBck3().equalsIgnoreCase("1")){
            infoModel.setFBRemrk3("Sample Feedback 3");
        }else if (infoModel.getFeedBck3().equalsIgnoreCase("0")){
            infoModel.setFBRemrk3("");
        }

        Assert.assertEquals(Neighbr3 ,infoModel.getNeighbr3());
        Assert.assertEquals(ReltnCD3 ,infoModel.getReltnCD3());
        Assert.assertEquals(MobileN3 ,infoModel.getMobileN3());
        Assert.assertEquals(FeedBck3 ,infoModel.getFeedBck3());
        Assert.assertEquals(FBRemrk3 ,infoModel.getFBRemrk3());

        System.out.print("Neighbr3 : " +infoModel.getNeighbr3() + "\n");
        System.out.print("ReltnCD3 : " +infoModel.getReltnCD3() + "\n");
        System.out.print("MobileN3 : " +infoModel.getMobileN3() + "\n");
        System.out.print("FeedBck3 : " +infoModel.getFeedBck3() + "\n");
        System.out.print("FBRemrk3 : " +infoModel.getFBRemrk3() + "\n");
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

        infoModel.setNeighbr1(Neighbr1);
        infoModel.setReltnCD1(ReltnCD1);
        infoModel.setMobileN1(MobileN1);
        infoModel.setFeedBck1(FeedBck1);
        infoModel.setFBRemrk1(FBRemrk1);

        infoModel.setNeighbr2(Neighbr2);
        infoModel.setReltnCD2(ReltnCD2);
        infoModel.setMobileN2(MobileN2);
        infoModel.setFeedBck2(FeedBck2);
        infoModel.setFBRemrk2(FBRemrk2);

        infoModel.setNeighbr3(Neighbr3);
        infoModel.setReltnCD3(ReltnCD3);
        infoModel.setMobileN3(MobileN3);
        infoModel.setFeedBck3(FeedBck3);
        infoModel.setFBRemrk3(FBRemrk3);
        if (infoModel.getFeedBck1().equalsIgnoreCase("1")){
            infoModel.setFBRemrk1("Sample Feedback 1");
        }else if (infoModel.getFeedBck1().equalsIgnoreCase("0")){
            infoModel.setFBRemrk1("");
        }
        if (infoModel.getFeedBck2().equalsIgnoreCase("1")){
            infoModel.setFBRemrk2("Sample Feedback 2");
        }else if (infoModel.getFeedBck2().equalsIgnoreCase("0")){
            infoModel.setFBRemrk2("");
        }
        if (infoModel.getFeedBck3().equalsIgnoreCase("1")){
            infoModel.setFBRemrk3("Sample Feedback 3");
        }else if (infoModel.getFeedBck3().equalsIgnoreCase("0")){
            infoModel.setFBRemrk3("");
        }

        Assert.assertEquals(Neighbr1 ,infoModel.getNeighbr1());
        Assert.assertEquals(ReltnCD1 ,infoModel.getReltnCD1());
        Assert.assertEquals(MobileN1 ,infoModel.getMobileN1());
        Assert.assertEquals(FeedBck1 ,infoModel.getFeedBck1());
        Assert.assertEquals(FBRemrk1 ,infoModel.getFBRemrk1());

        System.out.print("Neighbr1 : " +infoModel.getNeighbr1() + "\n");
        System.out.print("ReltnCD1 : " +infoModel.getReltnCD1() + "\n");
        System.out.print("MobileN1 : " +infoModel.getMobileN1() + "\n");
        System.out.print("FeedBck1 : " +infoModel.getFeedBck1() + "\n");
        System.out.print("FBRemrk1 : " +infoModel.getFBRemrk1() + "\n");

        Assert.assertEquals(Neighbr2 ,infoModel.getNeighbr2());
        Assert.assertEquals(ReltnCD2 ,infoModel.getReltnCD2());
        Assert.assertEquals(MobileN2 ,infoModel.getMobileN2());
        Assert.assertEquals(FeedBck2 ,infoModel.getFeedBck2());
        Assert.assertEquals(FBRemrk2 ,infoModel.getFBRemrk2());

        System.out.print("\nNeighbr2 : " +infoModel.getNeighbr2() + "\n");
        System.out.print("ReltnCD2 : " +infoModel.getReltnCD2() + "\n");
        System.out.print("MobileN2 : " +infoModel.getMobileN2() + "\n");
        System.out.print("FeedBck2 : " +infoModel.getFeedBck2() + "\n");
        System.out.print("FBRemrk2 : " +infoModel.getFBRemrk2() + "\n");

        Assert.assertEquals(Neighbr3 ,infoModel.getNeighbr3());
        Assert.assertEquals(ReltnCD3 ,infoModel.getReltnCD3());
        Assert.assertEquals(MobileN3 ,infoModel.getMobileN3());
        Assert.assertEquals(FeedBck3 ,infoModel.getFeedBck3());
        Assert.assertEquals(FBRemrk3 ,infoModel.getFBRemrk3());

        System.out.print("\nNeighbr3 : " +infoModel.getNeighbr3() + "\n");
        System.out.print("ReltnCD3 : " +infoModel.getReltnCD3() + "\n");
        System.out.print("MobileN3 : " +infoModel.getMobileN3() + "\n");
        System.out.print("FeedBck3 : " +infoModel.getFeedBck3() + "\n");
        System.out.print("FBRemrk3 : " +infoModel.getFBRemrk3() + "\n");
        assertTrue(mViewModel.saveNeighbor(infoModel,"",Fragment_CIBarangayRecordTest.this));
    }

    @Override
    public void onSaveSuccessResult(String args) {
        if (args.equalsIgnoreCase("Neighbor1")){
            System.out.println("Neighbor 1 save successful.");
        }else if (args.equalsIgnoreCase("Neighbor2")){
            System.out.println("Neighbor 2 save successful.");
        }else if (args.equalsIgnoreCase("Neighbor3")){
            System.out.println("Neighbor 3 save successful.");
        }else{
            System.out.println("Barangay record save successful.");;
        }
    }

    @Override
    public void onFailedResult(String message) {
        System.out.println(message);
    }

}