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

    @Then("^I should see (\\S+) on the (\\S+)$")
    public void I_should_see_error_on_the_editTextView(final String error, final String viewName) {
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
