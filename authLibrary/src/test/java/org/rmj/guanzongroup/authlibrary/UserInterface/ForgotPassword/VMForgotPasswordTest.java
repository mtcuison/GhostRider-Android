/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.authLibrary
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.authlibrary.UserInterface.ForgotPassword;

import junit.framework.TestCase;

import org.junit.Test;
import org.mockito.Mockito;

public class VMForgotPasswordTest extends TestCase {

    private VMForgotPassword mViewModel;

    public void setUp() throws Exception {
        super.setUp();
        mViewModel = Mockito.mock(VMForgotPassword.class);
    }

    public void tearDown() throws Exception {
    }

    @Test
    public void testEmailValidator(){
        String email = "mikegarcia8748@gmail.com";
        boolean result = mViewModel.isEmailValid(email);
        assertTrue(result);
    }
}