CucumberEspressoDemo
====================

##Setup
1. Create custom instrumentation runner

    ```
    public class Instrumentation extends MonitoringInstrumentation {

      private final CucumberInstrumentationCore mInstrumentationCore = new CucumberInstrumentationCore(this);

      @Override
      public void onCreate(Bundle arguments) {
        super.onCreate(arguments);

        mInstrumentationCore.create(arguments);
        start();
      }

      @Override
      public void onStart() {
        super.onStart();

        waitForIdleSync();
        mInstrumentationCore.start();
      }
    }
    ```

2. Application ID / Runner setup in app/build.gradle

    ```
    testApplicationId "com.emmasuzuki.cucumberespressodemo.test"
    testInstrumentationRunner "com.emmasuzuki.cucumberespressodemo.test.Instrumentation"
    ```

3. Set assets directory for feature files in app/build.gradle

    ```
    sourceSets {
        androidTest {
            assets.srcDirs = ['src/androidTest/assets']
        }
    }
    ```

4. Add dependencies

    ```
    androidTestCompile 'com.android.support.test.espresso:espresso-core:2.0'
    androidTestCompile 'com.android.support.test:testing-support-lib:0.1'
    androidTestCompile 'info.cukes:cucumber-android:1.2.0@jar'
    androidTestCompile 'info.cukes:cucumber-picocontainer:1.2.0'
    ```
    
##Write behavior
    
    Feature: Login
    Perform login on email and password are inputted

      Scenario Outline: Input email and password in correct format
        Given I have a LoginActivity
        When I input email <email>
        And I input password "<password>"
        And I press submit button
        Then I should <see> auth error

      Examples:
        | email              | password   | see   |
        | espresso@spoon.com | bananacake | true  |
        | espresso@spoon.com | lemoncake  | false |     <-- valid email and password
        | latte@spoon.com    | lemoncake  | true  |
    
    
##Write step definition

    EX) 
    "Given I have a LoginActivity" in behavior translates to
    
    @Given("^I have a LoginActivity")
    public void I_have_a_LoginActivity(){}
    
    in step definition
    
    "Then I should see error on the <view>" in behavior translates to
    
    @Then("^I should see error on the (\\S+)$")
    public void I_should_see_error_on_the_editTextView(final String viewName) {}
    
##Write Espresso test in step definition

    @When("^I input email (\\S+)$")
    public void I_input_email(final String email) {
        onView(withId(R.id.email)).perform(typeText(email));
    }
    
    @Then("^I should (true|false) auth error$")
    public void I_should_see_auth_error(boolean shouldSeeError) {
        if (shouldSeeError) {
            onView(withId(R.id.error)).check(matches(isDisplayed()));
        } else {
            onView(withId(R.id.error)).check(matches(not(isDisplayed())));
        }
    }

##Run
On command line, run with `$./gradlew connectedCheck`

On Android Studio, take the following steps:

1. Run > Edit Configurations
2. Click "+" on left pane
3. Select "app" for module
4. Select custom instrumentation runner we created at Setup for specific instrumentation runner
5. Apply
6. Choose the craeted Run configuration 
7. Click Run
    
##Write code to make the behavior pass

##Any Questions ? 
Please feel free to contact me at emma11suzuki@gmail.com
