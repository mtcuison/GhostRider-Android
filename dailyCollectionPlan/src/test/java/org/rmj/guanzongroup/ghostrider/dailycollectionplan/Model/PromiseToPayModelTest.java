package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PromiseToPayModelTest {

    private PromiseToPayModel infoModel;
    @Before
    public void setUp() throws Exception {
        infoModel = new PromiseToPayModel();
        infoModel.setPtpDate("February 15, 2021");
        infoModel.setPtpRemarks("Promise To Pay");
        infoModel.setPtpAppointmentUnit("1");
        infoModel.setPtpBranch("M126");
        infoModel.setPtpImgPath("/storage/emulated/0/Android/data/org.rmj.guanzongroup.ghostrider.epacss/files/DCP/PromiseToPay/M00110006088_20210215_133829_6041598954179008174.jpg");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test_isPtpDate(){
        Assert.assertEquals(true, infoModel.isPtpDate());
    }

    @Test
    public void isPtpAppoint(){
        Assert.assertEquals(true, infoModel.isPtpAppoint());
    }


    @Test
    public void isPtpImgPath(){
        Assert.assertEquals(true, infoModel.isPtpImgPath());
        System.out.print(infoModel.getPtpImgPath());
    }

    @Test
    public void test_isDataValid(){
        Assert.assertEquals(true, infoModel.isDataValid());
    }
}