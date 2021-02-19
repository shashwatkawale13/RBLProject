package StepDefinationFiles;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import UtilityFiles.RestAPIMethods;
import UtilityFiles.RestURLs;
import UtilityFiles.Utils;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;

public class StepDef {

	static List<String> updateUserList = new ArrayList<>(); 
	static List<String> userList = new ArrayList<>(); 
	

	@Before
	public void readUserList() throws IOException{
		//System.out.println("Reading The User List................");
		userList= Utils.readUserDataFromSheet(userList,"AccountSheet");
		updateUserList= Utils.readUserDataFromSheet(updateUserList,"UpdateAccountSheet");
	
		
		//System.out.println("Just trying to print the values of json...");
		
//		for (String user : updateUserList) {
//			System.out.println(user);
//		}
		
//		System.out.println("------------------------------------------------------------------------------------");
//		for (String user : userList) {
//			System.out.println(user);
//		}
				
	}
	
	@Given("^Get the details of account holder$")
	public void getAccountHolderDetails()
	{
		System.out.println(".........Get Account Details...........");
		RestAPIMethods.getRequest(RestURLs.getAccountDetailsURL,200);
	}
	
	@Given("^Try to fetch the details of Account holder which is not present$")
	public void invalidAccountHolderDetails()
	{
		System.out.println(".........Get invalid Account holder Details...........");
		RestAPIMethods.invalidGetRequest(RestURLs.getInvalidAccountDetailsURL,200);
	}
	
	@Given("^Delete Account$")
	public void deleteAccount() throws Exception
	{
		System.out.println(".........Delete Account...........");
		RestAPIMethods.deleteRequest(RestURLs.deleteAccountURL,200);
	}
	
	/*@Given("^Create new account$")
	public void createAccount() throws Exception
	{
		System.out.println(".........Create new Account...........");
		RestAPIMethods.postRequest(RestURLs.createAccountURL);
	
	}*/
	
	@Given("^Create new account$")
	public void createAccount() throws Exception
	{
		System.out.println(".........Create new Account...........");		
		for (String user : userList) {
			System.out.println("Create user with data :: "+user);
			RestAPIMethods.postRequest1(RestURLs.createAccountURL,user);
		}
	
	}
	
	@Given("^Update profile of account holder$")
	public void updateAccount() throws Exception
	{
		System.out.println(".........Update Account...........");
		RestAPIMethods.putRequest(RestURLs.updateProfileAccountURL);
	}
	
	@Given("^Try to Create new account with invalid url$")
	public void create_new_account_with_invalid_url() throws Throwable {
	//	System.out.println("ssssssssssss==="+userList.get(0));
		RestAPIMethods.postRequest(RestURLs.createAccountURL+"s", userList.get(0), 201);

	}

	@After
	public void afterScenario()
	{
		System.out.println("======================================================================================================================");
	}
}
