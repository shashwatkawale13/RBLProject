Feature: Open Account

Scenario: Error message test

Given browser is open and page visited
When I click on Open Account button
Then it should prompt to enter email address and mobile number

When I enter invalid email id 
And click on get started button
Then error message should appear

#parameterize via excel file
#display the result in console
#view reports
#add screenshots