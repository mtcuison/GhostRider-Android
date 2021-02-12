package org.rmj.guanzongroup.onlinecreditapplication.Fragment;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.rmj.guanzongroup.onlinecreditapplication.Model.CoMakerModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMCoMaker;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Fragment_CoMakerTest {

    private CoMakerModel infoModel;
    private VMCoMaker mViewModel;
    private String lName, fName, mName, suffix, nName,bDate, bTown, fbAcct, srcIncome, coRelation;
    private String primaryContact, secondaryContact, tertiaryContact;
    private String primarySimStats, secondarySimStats, tertiarySimStats;
    private String primaryContactPlan, secondaryContactPlan, tertiaryContactPlan;
    @Mock
    ViewModelCallBack callBack;
    @Before
    public void setUp() throws Exception {
        infoModel = new CoMakerModel();
        mViewModel =  mock(VMCoMaker.class);
        lName = "Sabiniano";
        fName = "Jonathan";
        mName = "Tamayo";
        suffix = "";
        nName= "";
        bDate= "03/06/1990";
        bTown= "20";
        fbAcct= "";
        srcIncome = "2";
        coRelation = "2";
        primaryContact = "09452086661";
        secondaryContact = "0";
        tertiaryContact = "0";
        primarySimStats  = "";
        secondarySimStats  = "0";
        tertiarySimStats  = "0";
        primaryContactPlan  = "";
        secondaryContactPlan  = "0";
        tertiaryContactPlan  = "0";
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test_submitCoMaker(){
        try {
            infoModel = new CoMakerModel(lName, fName, mName, suffix, nName,bDate, bTown, fbAcct, srcIncome, coRelation);
            when(mViewModel.SubmitComaker(infoModel,callBack)).thenReturn(true);
            assertTrue(mViewModel.SubmitComaker(infoModel, callBack));
            assertTrue(mViewModel.SubmitComaker(infoModel,callBack));
        }catch (NullPointerException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}