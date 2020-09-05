Feature: annotation
#This is how background can be used to eliminate duplicate steps

  Background: User navigates to Facebook

#Scenario with AND
  Scenario: Scenario with AND
    Given I am on Linkedin login page
    When I enter username as "TOM" on Linkedin
    And I enter password as "JERRY" on Linkedin
    Then Login should fail on Linkedin

#Scenario with but
#  Scenario: Scenario with BUT
#    Given I am on Facebook login page
#    When I enter username as "TOM"
#    And I enter password as "JERRY"
#    Then Login should fail
#    But Relogin option should be available