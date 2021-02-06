package org.rmj.guanzongroup.onlinecreditapplication.Activity;

import androidx.test.rule.ActivityTestRule;
import androidx.viewpager.widget.ViewPager;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_MeansInfoSelection;
import org.rmj.guanzongroup.onlinecreditapplication.R;

import static junit.framework.TestCase.assertEquals;

public class Activity_CreditApplicationTest {

    private Fragment_MeansInfoSelection mFragment = null;

    @Rule
    public ActivityTestRule activityTestRule = new ActivityTestRule(Activity_CreditApplication.class);

    public Activity_CreditApplication mActivity;

    @Before
    public void setUp() throws Exception {
        mActivity = (Activity_CreditApplication) activityTestRule.getActivity();
    }

    @Test
    public void testGoToNextPage(){
        ViewPager viewPager = mActivity.findViewById(R.id.viewpager_creditApp);
        assertEquals(0, viewPager.getCurrentItem());
    }

    @Test
    public void addMeansInfo() {

    }

    @Test
    public void getMeansInfoCount() {

    }

    @Test
    public void removeMeansInfo() {

    }

    @After
    public void tearDown() throws Exception {
    }
}