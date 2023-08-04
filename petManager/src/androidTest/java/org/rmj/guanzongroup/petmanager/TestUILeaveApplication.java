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

package org.rmj.guanzongroup.petmanager;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.contrib.PickerActions.setDate;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.app.Application;
import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.view.View;
import android.widget.DatePicker;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rmj.g3appdriver.GCircle.Apps.PetManager.Obj.EmployeeLeave;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.lib.Account.AccountMaster;
import org.rmj.g3appdriver.lib.Account.Model.Auth;
import org.rmj.g3appdriver.lib.Account.Model.iAuth;
import org.rmj.g3appdriver.lib.Etc.Branch;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.guanzongroup.petmanager.Fragment.Fragment_LeaveApplication;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestUILeaveApplication {
    private static boolean isSuccess = false;
    private String sEmail, sPassword, sMobileNo;
    private View cAgree;
    private Application instance;

    private AccountMaster poAccount;
    private iAuth poSys;
    private Branch pobranch;
    private  EmployeeLeave poSyss;
    private  ConnectionUtil poConn;
    String searchText = "Birthday";
    String xRemarks = "Birthday ko na!";
    String xDate = "August 25, 2023";
    @Before
    public void setUp() throws Exception {
        this.instance = ApplicationProvider.getApplicationContext();
        AppConfigPreference.getInstance(instance).setProductID("GuanzonApp");
        AppConfigPreference.getInstance(instance).setTestCase(true);
        this.poAccount = new AccountMaster(instance);
        this.poSys = poAccount.initGuanzonApp().getInstance(Auth.AUTHENTICATE);
        this.pobranch = new Branch(instance);
        this.poSyss = new EmployeeLeave(instance);
        this.poConn = new ConnectionUtil(instance);
        final java.util.Calendar newCalendar = java.util.Calendar.getInstance();
    }
    @Test
    public void Test01() {
        // Launch the fragment using FragmentScenario
        FragmentScenario.launchInContainer(Fragment_LeaveApplication.class);

//        // Find the TextInputEditText by its resource ID, enter text, and close the keyboard

        testleavetype();
//        testDateFrom();
        testDateto();
        /*SUBMIT APPLICAITON*/
        onView(withId(R.id.txt_remarks))
                .perform(ViewActions.typeText(xRemarks), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btn_submit))
                .perform(click());



    }

    private void testleavetype(){
        onView(withId(R.id.spn_leaveType))
                .perform(click());
    }
    private  void  testDateFrom(){
        /*DATE FROM*/

        Espresso.onView(ViewMatchers.withId(R.id.txt_dateFrom))
                .perform(ViewActions.click());

        // Interact with the DatePicker and select a specific date
        Calendar desiredDate = Calendar.getInstance();
        desiredDate.set(Calendar.YEAR, 2023);
        desiredDate.set(Calendar.MONTH, Calendar.AUGUST); // Note: Months are 0-based (0 = January, 1 = February, etc.)
        desiredDate.set(Calendar.DAY_OF_MONTH, 10);
        Espresso.onView(ViewMatchers.withClassName(Matchers.equalTo(DatePickerDialog.class.getName())))
                .perform(setDate(2023, Calendar.MONTH, 10));

        // Click the "OK" button in the DatePicker dialog
        Espresso.onView(ViewMatchers.withText("OK"))
                .perform(ViewActions.click());

        // Verify that the selected date is populated in the TextInputEditText
        Espresso.onView(ViewMatchers.withId(R.id.txt_dateFrom))
                .check(ViewAssertions.matches(ViewMatchers.withText("08/10/2023")));
//        onView(ViewMatchers.withClassName(equalTo(DatePicker.class.getName())))
//                .perform(PickerActions.setDate(2023, 8, 25));
//        onView(withId(android.R.id.button1))
//                .perform(ViewActions.click());
//        onView(withId(R.id.txt_dateFrom))
//                .check(ViewAssertions.matches(ViewMatchers.withText(xDate)));


    }
    private  void  testDateto(){
        /*DATE TO*/
        // Click on the TextInputEditText
        Espresso.onView(ViewMatchers.withId(R.id.txt_dateTo))
                .perform(ViewActions.click());

        // Get the current date from the device
        Calendar currentDate = Calendar.getInstance();
        int year = currentDate.get(Calendar.YEAR);
        int month = currentDate.get(Calendar.MONTH);
        int day = currentDate.get(Calendar.DAY_OF_MONTH);

        // Select a specific date from the DatePicker
        int yearToSet = 2023; // Change this to the desired year
        int monthToSet = 7;   // Change this to the desired month (0-based index)
        int dayToSet = 3;     // Change this to the desired day
        onView(isAssignableFrom(DatePicker.class)).perform(setDate(1980, 10, 30));

// Confirm the selected date. This example uses a standard DatePickerDialog
// which uses
// android.R.id.button1 for the positive button id.
        onView(withId(android.R.id.button1)).perform(click());
//        Espresso.onView(ViewMatchers.withClassName(Matchers.equalTo(DatePicker.class.getName())))
//                .perform(PickerActions.setDate(yearToSet, monthToSet, dayToSet));
//
//        // Click the "OK" button in the DatePicker dialog
//        Espresso.onView(ViewMatchers.withText("OK"))
//                .perform(ViewActions.click());

        // Verify that the selected date is displayed in the TextInputEditText
//        String expectedDateText = String.format("%04d-%02d-%02d", yearToSet, monthToSet + 1, dayToSet);
//        Espresso.onView(ViewMatchers.withId(R.id.txt_dateTo))
//                .check(ViewAssertions.matches(ViewMatchers.withText(expectedDateText)));

    }
}