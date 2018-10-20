package com.emmasuzuki.cucumberespressodemo.test;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import com.emmasuzuki.cucumberespressodemo.R;
import com.emmasuzuki.cucumberespressodemo.SignupActivity;

import org.junit.Rule;

import cucumber.api.CucumberOptions;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertNotNull;

public class SignupActivitySteps {

    @Rule
    private ActivityTestRule<SignupActivity> activityTestRule = new ActivityTestRule<>(SignupActivity.class);

    private SignupActivity activity;

    @Before("@signup-feature")
    public void setUp() {
        activityTestRule.launchActivity(new Intent());
        activity = activityTestRule.getActivity();
    }

    @After("@signup-feature")
    public void tearDown() {
        activityTestRule.finishActivity();
    }

    @Given("^I am on sign up screen$")
    public void I_am_on_sign_up_screen() {
        assertNotNull(activity);
    }

    @When("^I tap login button$")
    public void I_tap_login_button() {
        onView(withId(R.id.login)).perform(click());
    }

    @Then("^I should see login screen$")
    public void I_should_see_login_screen() {
        onView(withId(R.id.page_title)).check(matches(withText(R.string.login)));
    }
}
