
Feature: A 2nd testing feature

  Scenario: user wants to access app under SG market
    Given user is on country selection page
    When user select "Singapore" country
    And user confirms country
    Then user will see get started page
