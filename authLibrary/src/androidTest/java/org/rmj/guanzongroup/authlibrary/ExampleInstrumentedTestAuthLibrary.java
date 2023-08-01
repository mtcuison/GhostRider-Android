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

package org.rmj.guanzongroup.authlibrary;

import android.app.Application;
import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.navigation.Navigation;
import androidx.navigation.testing.TestNavHostController;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rmj.guanzongroup.authlibrary.Activity.Activity_Authenticate;
import org.rmj.guanzongroup.authlibrary.UserInterface.Login.Fragment_Login;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExampleInstrumentedTestAuthLibrary {

    @Rule
    public ActivityScenarioRule<Activity_Authenticate> activityRule =
            new ActivityScenarioRule<>(Activity_Authenticate.class);

   @Test
    public void fillupForms(){
       TestNavHostController navHostController = new TestNavHostController(ApplicationProvider.getApplicationContext());
       FragmentScenario scenario = FragmentScenario.launchInContainer(Fragment_Login.class);

       scenario.onFragment(fragment -> {
           navHostController.setGraph(R.navigation.nav_authentication);
           navHostController.setCurrentDestination(R.id.fragment_Login);
           Navigation.setViewNavController(fragment.requireView(), navHostController);
       });

       /*onView(withId(R.id.tie_loginEmail))
               .perform(typeText("ggutoman@gmail.com"));
       closeSoftKeyboard();

       onView(withId(R.id.tie_loginPassword))
               .perform(typeText("ggutoman1998"));
       closeSoftKeyboard();

       onView(withId(R.id.cbAgree))
               .check(matches(isChecked())) //isChecked -> test object check state
               .perform(click()) //click -> clicks object
               .check(matches(isNotChecked())) //isNotChecked -> test object uncheck state
               .perform(click());

       onView(withId(R.id.btn_login))
               .perform(click());*/
    }
}