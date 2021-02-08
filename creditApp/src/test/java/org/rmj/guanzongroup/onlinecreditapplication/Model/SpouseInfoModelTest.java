    package org.rmj.guanzongroup.onlinecreditapplication.Model;

    import org.junit.Assert;
    import org.junit.Assert.*;
    import org.junit.Before;
    import org.junit.Test;

    public class SpouseInfoModelTest {

        private SpouseInfoModel loSpouseInfoModel;
        private String fLastName = null,
                fFirstName = null,
                fMiddName = null,
                fBdate = null,
                fBPlace = null,
                fCitizenx = null,
                fPrimeContact = null,
                fSecContact = null,
                fThirdContact = null,
                fProvince = null,
                fTown = null,
                fPhoneNox = null,
                fEmailAdd = null,
                fFbAcct = null,
                fVbrAcct = null;
        private int x;

        @Before
        public void setUp() {
            loSpouseInfoModel = new SpouseInfoModel();
            fLastName = "Dela Cruz";
            fFirstName = "Juan";
            fMiddName = "Sison";
            fBdate = "01/01/1980";
            fBPlace = "0130";
            fCitizenx = "1";
            fPrimeContact = "09123456789";
            fSecContact = "09129876543";
            fThirdContact = "09090909090";
            fProvince = "028";
            fTown = "0282";
            fPhoneNox = "542-1234";
            fEmailAdd = "juandelacruz@email.com";
            fFbAcct = "Juan Dela Cruz";
            fVbrAcct = "09093535353";

            loSpouseInfoModel.setLastName(fLastName);
            loSpouseInfoModel.setFrstName(fFirstName);
            loSpouseInfoModel.setMiddName(fMiddName);
            loSpouseInfoModel.setBirthDate(fBdate);
            loSpouseInfoModel.setBirthPlace(fBPlace);
            loSpouseInfoModel.setCitizenx(fCitizenx);
            loSpouseInfoModel.setMobileNo(fPrimeContact, "0", 0);
            loSpouseInfoModel.setMobileNo(fSecContact, "1", 2);
            loSpouseInfoModel.setMobileNo(fThirdContact, "1", 3);
            loSpouseInfoModel.setProvNme(fProvince);
            loSpouseInfoModel.setTownNme(fTown);
            loSpouseInfoModel.setPhoneNo(fPhoneNox);
            loSpouseInfoModel.setEmailAdd(fEmailAdd);
            loSpouseInfoModel.setFBacct(fFbAcct);
            loSpouseInfoModel.setVbrAcct(fVbrAcct);
        }

        @Test
        public void testIsSpouseInfoValid() {
            Assert.assertTrue(loSpouseInfoModel.isSpouseInfoValid());
        }


        @Test
        public void testGetLastName() {
            Assert.assertNotNull(loSpouseInfoModel.getLastName());
            Assert.assertEquals(fLastName, loSpouseInfoModel.getLastName());
        }

        @Test
        public void testGetFrstName() {
            Assert.assertNotNull(loSpouseInfoModel.getFrstName());
            Assert.assertEquals(fFirstName, loSpouseInfoModel.getFrstName());
        }


        @Test
        public void testGetMiddName() {
            Assert.assertNotNull(loSpouseInfoModel.getMiddName());
            Assert.assertEquals(fMiddName, loSpouseInfoModel.getMiddName());
        }

        @Test
        public void testGetBirthDate() {
            Assert.assertNotNull(loSpouseInfoModel.getBirthDate());
            Assert.assertEquals(fBdate, loSpouseInfoModel.getBirthDate());
        }

        @Test
        public void testGetBPlace() {
            Assert.assertNotNull(loSpouseInfoModel.getBirthPlace());
        }

        @Test
        public void testGetCitizenx() {
            Assert.assertNotNull(loSpouseInfoModel.getCitizenx());
        }

        @Test
        public void testGetMobileNoQty() {
            Assert.assertEquals(3, loSpouseInfoModel.getMobileNoQty());
        }

        @Test
        public void testGetMobileNo() {
            for(x = 0; x < loSpouseInfoModel.getMobileNoQty(); x++)
            {
                Assert.assertNotNull(loSpouseInfoModel.getMobileNo(x));
            }
        }

        @Test
        public void testGetPostYear() {
            for(x = 0; x < loSpouseInfoModel.getMobileNoQty(); x++) {
                if(loSpouseInfoModel.getPostPaid(x) == "1") {
                    Assert.assertNotEquals(0, loSpouseInfoModel.getPostYear(x));
                }
            }
        }

        @Test
        public void testGetPhoneNo() {
            Assert.assertNotNull(loSpouseInfoModel.getPhoneNo());
            Assert.assertEquals(fPhoneNox, loSpouseInfoModel.getPhoneNo());
        }

        @Test
        public void testGetEmailAdd() {
            Assert.assertNotNull(loSpouseInfoModel.getEmailAdd());
            Assert.assertEquals(fEmailAdd, loSpouseInfoModel.getEmailAdd());
        }

        @Test
        public void testGetFBAcct() {
            Assert.assertNotNull(loSpouseInfoModel.getFBacct());
            Assert.assertEquals(fFbAcct, loSpouseInfoModel.getFBacct());
        }

        @Test
        public void testGetVbrAcct() {
            Assert.assertNotNull(loSpouseInfoModel.getVbrAcct());
            Assert.assertEquals(fVbrAcct, loSpouseInfoModel.getVbrAcct());
        }





    }
