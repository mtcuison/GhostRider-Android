package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PromiseToPayModelTest {

    private PromiseToPayModel infoModel;
    private String ptpDate, ptpRemCode, ptpAppoinment, ptpBranch = "", ptpImgPath, collector;
    @Before
    public void setUp() throws Exception {
        ptpDate = "February 15, 2021";
        ptpAppoinment = "1";
        if (Integer.parseInt(ptpAppoinment) > 0){
            ptpBranch = "M126";
        }
        collector = "";
        ptpRemCode = "Promise To Pay";
        ptpImgPath = "/storage/emulated/0/Android/data/org.rmj.guanzongroup.ghostrider.epacss/files/DCP/PromiseToPay/M00110006088_20210215_133829_6041598954179008174.jpg";
        infoModel = new PromiseToPayModel();
        infoModel.setPtpDate(ptpDate);
        infoModel.setPtpRemarks(ptpRemCode);
        infoModel.setPtpAppointmentUnit(ptpAppoinment);
        infoModel.setPtpBranch(ptpBranch);
        infoModel.setPtpCollectorName(collector);
        infoModel.setPtpImgPath(ptpImgPath);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test_isPtpDate(){
        Assert.assertEquals(ptpDate,infoModel.getPtpDate());
        Assert.assertEquals(true, infoModel.isPtpDate());
    }

    @Test
    public void isPtpAppoint(){

        Assert.assertEquals(ptpAppoinment,infoModel.getPtpAppointmentUnit());
        Assert.assertEquals(true, infoModel.isPtpAppoint());
    }


    @Test
    public void isPtpImgPath(){

        Assert.assertEquals(ptpImgPath,infoModel.getPtpImgPath());
        Assert.assertEquals(true, infoModel.isPtpImgPath());
        System.out.print(infoModel.getPtpImgPath());
    }
    @Test
    public void isPtpBranch(){

        Assert.assertEquals(ptpBranch,infoModel.getPtpBranch());
        Assert.assertEquals(true, infoModel.isPtpAppointBranch());
        System.out.print(infoModel.getPtpBranch());
    }

    @Test
    public void test_isDataValid(){
        Assert.assertEquals(true, infoModel.isDataValid());
    }
}