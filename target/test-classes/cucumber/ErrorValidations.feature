
@tag
Feature: Login Error validation with invalid credential
  I want to validate an invalid credential on Login

  @ErrorValidation
  Scenario Outline: Negative Test of Error Validation on Login
    Given I landed on Ecommerce Page
    When I logged in with username <name> and password <password>
    Then "Incorrect email or password." message is displayed

     Examples: 
      | name  								|  password		    |
      | rahulshetty@gmail.com |  Iamking@0      | 
