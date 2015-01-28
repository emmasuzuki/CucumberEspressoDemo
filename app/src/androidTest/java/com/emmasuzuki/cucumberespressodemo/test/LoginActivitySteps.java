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

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.EditText;

import com.emmasuzuki.cucumberespressodemo.LoginActivity;
import com.emmasuzuki.cucumberespressodemo.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.internal.matchers.TypeSafeMatcher;

import cucumber.api.CucumberOptions;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

@CucumberOptions(features = "features")
public class LoginActivitySteps extends ActivityInstrumentationTestCase2<LoginActivity> {

    public LoginActivitySteps(LoginActivity activityClass) {
        super(LoginActivity.class);
    }

    @Given("^I have a LoginActivity")
    public void I_have_a_LoginActivity() {
        assertNotNull(getActivity());
    }

    @When("^I input email (\\S+)$")
    public void I_input_email(final String email) {
        onView(withId(R.id.email)).perform(typeText(email));
    }

    @When("^I input password \"(.*?)\"$")
    public void I_input_password(final String password) {
        onView(withId(R.id.password)).perform(typeText(password));
    }

    @When("^I press submit button$")
    public void I_press_submit_button() {
        onView(withId(R.id.submit)).perform(click());
    }

    @Then("^I should see error on the (\\S+)$")
    public void I_should_see_error_on_the_editTextView(final String viewName) {
        int viewId = (viewName.equals("email")) ? R.id.email : R.id.password;
        int messageId = (viewName.equals("email")) ? R.string.msg_email_error : R.string.msg_password_error;

        onView(withId(viewId)).check(matches(hasErrorText(getActivity().getString(messageId))));
    }

    @Then("^I should (true|false) auth error$")
    public void I_should_see_auth_error(boolean shouldSeeError) {
        if (shouldSeeError) {
            onView(withId(R.id.error)).check(matches(isDisplayed()));
        } else {
            onView(withId(R.id.error)).check(matches(not(isDisplayed())));
        }
    }

    private static Matcher<? super View> hasErrorText(String expectedError) {
        return new ErrorTextMatcher(expectedError);
    }

    /**
     * Custom matcher to assert equal EditText.setError();
     */
    private static class ErrorTextMatcher extends TypeSafeMatcher<View> {

        private final String mExpectedError;

        private ErrorTextMatcher(String expectedError) {
            mExpectedError = expectedError;
        }

        @Override
        public boolean matchesSafely(View view) {
            if(!(view instanceof EditText)) {
                return false;
            }

            EditText editText = (EditText) view;

            return mExpectedError.equals(editText.getError());
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("with error: " + mExpectedError);
        }
    }
}
