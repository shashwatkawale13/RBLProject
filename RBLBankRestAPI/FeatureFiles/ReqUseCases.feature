Feature: Check Bank Management APIs

#Positive GET Request Scenario
Scenario: Details of Account Holder
	Given Get the details of account holder

#Negative GET Request Scenario	
Scenario: Invalid Account Details
	Given Try to fetch the details of Account holder which is not present

#DELETE Request Scenario
Scenario: Delete Account
	Given Delete Account

#Positive POST Request Scenario
Scenario: Crete new account
	Given Create new account

#Negative POST Request Scenario	
Scenario: Crete new account with invalid url
	Given Try to Create new account with invalid url

#PUT Request Scenario
Scenario: Update Account
	Given Update profile of account holder


