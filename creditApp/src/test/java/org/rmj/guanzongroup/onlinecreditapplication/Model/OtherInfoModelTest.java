package org.rmj.guanzongroup.onlinecreditapplication.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OtherInfoModelTest {
    private OtherInfoModel infoModels;

    private ArrayList<PersonalReferenceInfoModel> arrayList;
    private  PersonalReferenceInfoModel poRefInfo;
    @Before
    public void setUp() throws Exception {
        infoModels = new OtherInfoModel();
        arrayList = new ArrayList<>();
//        infoModels = new OtherInfoModel("Jonathan Sabiniano", "Cawayan Bogtong", "0335", "09452086661");

    }

    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void test_otherInfo_size() {
        addList();
    }
    private void addList(){
        int i = 0;
        for (int x = 0; x < 3; x++){
            i++;
            infoModels = new OtherInfoModel();
            infoModels.setUnitUser("0");
            infoModels.setUnitPrps("2");
            infoModels.setUnitPayr("0");
            infoModels.setSource("0");
            infoModels.setPayrRltn("2");
            poRefInfo = new PersonalReferenceInfoModel("Jonathan Sabiniano", "Cawayan Bogtong", "0335", "09452086661");
            arrayList.add(poRefInfo);
        }
        infoModels.setPersonalReferences(arrayList);
        assertEquals(i, arrayList.size());
    }
    @Test
    public void test_isValidSpinner() {
        addList();
        assertTrue(infoModels.isDataValid());
        for (int x = 0; x < arrayList.size(); x++) {
            System.out.println("Full Name : " + arrayList.get(x).getFullname());
            System.out.println("Address : " + arrayList.get(x).getAddress1());
            System.out.println("Town ID : " + arrayList.get(x).getTownCity());
            System.out.println("Contact No: " + arrayList.get(x).getContactN());
            System.out.println("Unit User index: " + infoModels.getUnitUser());
            System.out.println("Unit Purpose index: " + infoModels.getUnitPrps());
            System.out.println("Payer index: " + infoModels.getUnitPayr());
            System.out.println("Company Source index: " + infoModels.getSource());
            System.out.println("\n");
        }

    }


}