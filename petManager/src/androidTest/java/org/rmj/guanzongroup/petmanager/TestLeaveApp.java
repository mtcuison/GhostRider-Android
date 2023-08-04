package org.rmj.guanzongroup.petmanager;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class TestLeaveApp {
    @Rule
//    public FragmentScenarioRule<Fragment_LeaveApplication> fragmentScenarioRule = new FragmentScenarioRule<>(Fragment_LeaveApplication.class);
//    public FragmentScenarioRule<Fragment_LeaveApplication> fragmentScenarioRules = new FragmentScenarioRule<>(Fragment_LeaveApplication.class);
    @Test
    public void testFragmentTextInputAndSubmitButton() {
        // Launch the fragment using FragmentScenario
//        FragmentScenario<Fragment_LeaveApplication> fragmentScenario = fragmentScenarioRule.getFragmentScenario();

        // Find the views in the fragment
        onView(withId(R.id.spn_leaveType)).perform(click());
        onView(withId(R.id.spn_leaveType)).perform(typeText("John Doe"), closeSoftKeyboard());
        onView(withId(R.id.btn_submit)).perform(click());

        // You can add further assertions here to verify the expected behavior after the submit button is clicked.
        // For example, you can check if a Toast message is displayed or navigate to another fragment/activity.

        // Here's an example of checking a Toast message (requires androidx.test.espresso:espresso-contrib):
        // onView(withText("Submission Successful")).inRoot(withDecorView(not(activityScenario.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));

    }
}