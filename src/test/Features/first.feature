
Feature: A testing feature

  @broken
  Scenario: user wants to access app under SG market
    Given user is on country selection page
    When user select "Singapore" country
    And user confirms country
    Then user will see get started page

  @broken
  Scenario: user wants to access app under TH market
    Given user is on country selection page
    When user select "Thailand" country
    And user confirms country
    Then user will see get started page
