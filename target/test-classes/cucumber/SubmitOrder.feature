
@tag
Feature: Purchase Order from Ecommerce Website
  I want to submit my order on my Ecommerce Website

	Background:
	Given I landed on Ecommerce Page

  @Regression
  Scenario Outline: Positive Test of Submitting the Order
  
    Given I logged in with username <name> and password <password>
    When I add product <productName> to Cart
    And I checkout <productName> and submit the order
    Then "THANKYOU FOR THE ORDER." message is displayed on ConfirmationPage

    Examples: 
      | name  								|  password		    |	productName |
      | rahulshetty@gmail.com |  Iamking@000    | ZARA COAT 3 | 

