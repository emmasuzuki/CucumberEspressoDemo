/*
 * Copyright (C) 2015 emmasuzuki <emma11suzuki@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.emmasuzuki.cucumberespressodemo.test;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import com.emmasuzuki.cucumberespressodemo.R;
import com.emmasuzuki.cucumberespressodemo.SignupActivity;

import org.junit.Rule;

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
//        Close the keyboard else the login button is not available for click on the screen
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.login)).perform(click());
    }

    @Then("^I should see login screen$")
    public void I_should_see_login_screen() {
        onView(withId(R.id.page_title)).check(matches(withText(R.string.login)));
    }
}
