test
CucumberEspressoDemo
====================

Cucumber is BDD framework which works for iOS, Android and more.
For Android, we are going to use cucumber-jvm, java port of cucumber.

BDD's behavior text is written in a business-readable domain-specific language.
It aims to communicate better between non-tech to tech over Software trueness and quality.  
The readable behavior also serves as documentation.

Gherkin plugin works with Android Studio 2.0. Manual translation is still required but .feature file has pretty syntax highlighting and any invalid cucumber syntax will be flagged with an error.

Install Plugin: Android Studio > Preferences > Plugins > Search "Gherkin" > Install & Restart Android Studio

## Setup
1. Add dependencies

    ```
    androidTestCompile 'com.android.support.test.espresso:espresso-core:3.0.1'
    androidTestCompile 'com.android.support:support-annotations:26.1.0'  // <-- match with the support lib version
    androidTestCompile 'info.cukes:cucumber-android:1.2.5@jar'
    androidTestCompile 'info.cukes:cucumber-picocontainer:1.2.4'
    ```
    
2. Create custom instrumentation runner under androidTest package

    ```java
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

3. Application ID / Runner setup in app/build.gradle. Make sure this matches with the package name of the test. 

    ```
    testApplicationId "com.emmasuzuki.cucumberespressodemo.test"
    testInstrumentationRunner "com.emmasuzuki.cucumberespressodemo.test.Instrumentation"
    ```

4. Create assets/features directory under androidTest. This directory holds behavior(.feature) files.

5. Set assets directory in app/build.gradle.

    ```
    android {
        ...
        sourceSets {
            androidTest {
                assets.srcDirs = ['src/androidTest/assets']
            }
        }
    }
    ```
    
## Write behavior
   
   Make a file, login.feature, and put under src/androidTest/assets/features.
    
   ```cucumber
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
   ```
    
## Write step definition

   EX) 
   "Given I have a LoginActivity" in behavior translates to
    
   ```java
   @Given("^I have a LoginActivity")
   public void I_have_a_LoginActivity(){}
   ``` 
   in step definition.
    
   "Then I should see error on the <view>" in behavior translates to
    
   ```java
   @Then("^I should see error on the (\\S+)$")
   public void I_should_see_error_on_the_editTextView(final String viewName) {}
   ```
 
## Write Espresso test in step definition

   ```java
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
   ```
   
## Run
On command line, run with `$./gradlew connectedCheck`

On Android Studio, take the following steps:

1. Run > Edit Configurations
2. Click "+" on left pane
3. Select Android Tests
4. Put any name at Name: 
5. Select "app" for module
6. Hit Apply
7. Choose the craeted Run configuration 
8. Click Run
    
## Write code to make the behavior pass
Write code and run test again.  Observe the tests pass.
