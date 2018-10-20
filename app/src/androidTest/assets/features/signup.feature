Feature: Signup
    Perform sign up

    @signup-feature
    Scenario: Tap login button and show login screen
        Given I am on sign up screen
        When I tap login button
        Then I should see login screen
