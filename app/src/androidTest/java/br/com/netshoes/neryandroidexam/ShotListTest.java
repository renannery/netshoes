package br.com.netshoes.neryandroidexam;

/**
 * Created by nery on 6/27/2015.
 */

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import br.com.netshoes.neryandroidexam.activity.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ShotListTest {
    CountDownLatch countDownLatch = new CountDownLatch(10);

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkItemClick() throws InterruptedException {
        countDownLatch.await(5, TimeUnit.SECONDS);
        onView(withId(R.id.rvShots)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, click()));
        countDownLatch.await(5, TimeUnit.SECONDS);
        onView(withId(R.id.tvShotDescription))
                .check(matches(isDisplayed()));
        pressBack();
        countDownLatch.await(5, TimeUnit.SECONDS);
        onView(withId(R.id.rvShots)).perform(
                RecyclerViewActions.actionOnItemAtPosition(2, click()));
        countDownLatch.await(5, TimeUnit.SECONDS);
        onView(withId(R.id.tvShotDescription))
                .check(matches(isDisplayed()));
    }
    @After
    public void afterTest() {
        mActivityRule.getActivity().finish();
    }
}
