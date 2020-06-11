
Feature: Display transaction - using data table ((No Steps attached)
  @broken
  Scenario: top up transaction
    Given user is on homepage
    When user perform top up
    Then user will see transaction details
      |txn description|amount|
      |Top Up         |+$20.00 SGD |

  @broken
  Scenario: sales transaction
    Given user is on homepage
    When user perform sales transaction
    Then user will see transaction details
      |txn description|amount|
      |Mcdonalds      |$5.00SGD|

  @broken
  Scenario: refund transaction
    Given user is on homepage
    When user perform sales transaction
    And transaction is then refunded
    Then user will see transaction details
      |txn description|amount|
      |Refund: Mcdonalds|+$5.00SGD|
      |Mcdonalds      |$5.00SGD|