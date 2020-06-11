
Feature: Submit KYC with example table

  @broken
  Scenario Outline: user submits KYC
    Given user wants to order "PC" card
    When user submits KYC with "<surname>", "<firstname>", "<name on card>", "<date of birth>", "<nationality>", "<addressline1>", "<addressline2>", "<postcode>"
    Then user will see limited home page

    Examples:
      |surname|firstname|name on card|date of birth|nationality|addressline1|addressline2|postcode|
      |Tester |Nric Manual|Nric Tester|01-01-1980  |Singaporean|Autotest Line 1|2nd Address|123456|
      |Tester|Employment Pass|EPass Tester|10-10-1980|Singaporean|Autotest NewLine1|Another Address|555666|

