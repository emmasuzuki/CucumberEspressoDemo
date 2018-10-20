Feature: Login
    Perform login on email and password are inputted

    @login-feature
    Scenario Outline: Input email and password in wrong format
        Given I am on login screen
        When I input email <email>
        And I input password "<password>"
        And I press submit button
        Then I should see error on the <view>

    Examples:
        | email | password  | view  |
        | test  | lemoncake | email |
        | test@test.com || password |

    @login-feature
    Scenario Outline: Input email and password in correct format
        Given I am on login screen
        When I input email <email>
        And I input password "<password>"
        And I press submit button
        Then I should <see> auth error

    Examples:
        | email              | password   | see   |
        | espresso@spoon.com | bananacake | true  |
        | espresso@spoon.com | lemoncake  | false |
        | latte@spoon.com    | lemoncake  | true  |

    @login-feature
    Scenario: Tap login button and show login screen
        Given I am on login screen
        When I tap sign up button
        Then I should see sign up screen