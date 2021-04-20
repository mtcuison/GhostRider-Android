package org.rmj.guanzongroup.ghostrider.creditevaluator.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Etc.CIConstants;

import static org.junit.Assert.*;

public class CIResidenceInfoModelTest {
    String TransNox;
    String LandMark;
    String Ownershp;
    String OwnOther;
    String HouseTyp;
    String Garagexx;
    String Latitude;
    String Longitud;
    CIResidenceInfoModel infoModel;
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
    }

    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void isValidData(){
        infoModel.setTransNox(TransNox);
        infoModel.setLandMark(LandMark);
        infoModel.setOwnershp(Ownershp);
        infoModel.setOwnOther(OwnOther);
        infoModel.setHouseTyp(HouseTyp);
        infoModel.setGaragexx(Garagexx);
        infoModel.setLatitude(Latitude);
        infoModel.setLongitud(Longitud);
        assertTrue(infoModel.isValidData());
    }
    @Test
    public void printInfoModelValue(){
        infoModel.setTransNox(TransNox);
        infoModel.setLandMark(LandMark);
        infoModel.setOwnershp(Ownershp);
        infoModel.setOwnOther(OwnOther);
        infoModel.setHouseTyp(HouseTyp);
        infoModel.setGaragexx(Garagexx);
        infoModel.setLatitude(Latitude);
        infoModel.setLongitud(Longitud);
        assertEquals(TransNox, infoModel.getTransNox());
        assertEquals(LandMark, infoModel.getLandMark());
        assertEquals(Ownershp, infoModel.getOwnershp());
        assertEquals(OwnOther, infoModel.getOwnOther());
        assertEquals(HouseTyp, infoModel.getHouseTyp());
        assertEquals(Garagexx, infoModel.getGaragexx());
        assertEquals(Latitude, infoModel.getLatitude());
        assertEquals(Longitud, infoModel.getLongitud());

        System.out.println("TransNox = " + TransNox + ", infoModel value = " + infoModel.getTransNox());
        for (int x = 0;x < CIConstants.HOUSE_OWNERSHIP.length; x++){
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
            if (x == Integer.parseInt(Ownershp)){
                System.out.println("Has Garage = " + CIConstants.GARAGEXX[x] + ", infoModel value = " + infoModel.getGaragexx());
            }
        }
        System.out.println("Latitude = " + Latitude + ", infoModel value = " + infoModel.getLatitude());
        System.out.println("Longitud = " + Longitud + ", infoModel value = " + infoModel.getLongitud());

    }
}