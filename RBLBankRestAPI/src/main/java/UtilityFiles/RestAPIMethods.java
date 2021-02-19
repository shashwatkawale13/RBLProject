package UtilityFiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


public class RestAPIMethods {

	public static String userData;

	//Get details of account holder
	public static void getRequest(String url,int expectedStatusCode)
	{
		System.out.println("GET : "+url);
		//Get method call with Authorization
		Response getResponse=RestAssured.given()
				.auth().preemptive().basic("shashwat13", "sk@123")
				.get(url);
		System.out.println("Details of Account Holder : "+getResponse.asString());
		
		//Status Code
		int actualStatusCode=getResponse.getStatusCode();
		System.out.println("Actual Status Code : "+actualStatusCode);

		long actualTimeRequired=getResponse.getTime();
		System.out.println("Actual Time Rquired for response: "+actualTimeRequired);

		//Validation
		Assert.assertEquals("Test Case Failed...", expectedStatusCode, actualStatusCode);
	}

	public static void invalidGetRequest(String url,int expectedStatusCode)
	{
		System.out.println("GET  : "+url);
		//Get method call with Authorization
		Response getResponse=RestAssured.given()
				.get(url);
		System.out.println("Details of Account Holder : "+getResponse.asString());
		//Status Code
		int actualStatusCode=getResponse.getStatusCode();
		System.out.println("Actual Status Code : "+actualStatusCode);

		//Validation
		Assert.assertEquals("Test Case Failed...", expectedStatusCode, actualStatusCode);
	}

	//Delete account
	public static void deleteRequest(String url,int expectedStatusCode) throws Exception{

		System.out.println("DELETE : "+url);
		Response deleteResponse=RestAssured.given().delete(url);
		//Status code
		int actualStatusCode=deleteResponse.getStatusCode();
		System.out.println("Actual Status Code : "+actualStatusCode);
		//Validation
		Assert.assertEquals("Test Case Failed...", expectedStatusCode, actualStatusCode);

	}

	public static void postRequest1(String url,String userData)
	{

		System.out.println("POST : "+url);
		Response ResponseData=RestAssured.given()
				.contentType(ContentType.JSON)
				.body(userData)
				.post(url);
		System.out.println("Response Body as a String:: \n"+ResponseData.asString());
		
		//Validation
		
		//Get id of a new user 
		int idOfUser = Utils.getIdOfUser(ResponseData.asString());

		//Prepare expected response
		String createUserExpectedResponse = Utils.createUserExpectedResponse(userData, idOfUser);
		String createUserActualReponse = Utils.removeNewLineCharSpaces(ResponseData.asString());

		/*System.out.println("createUserActualReponse::\n"+createUserActualReponse);
		System.out.println("------------------------------------------------------------------");
		System.out.println("createUserExpectedResponse::\n"+createUserExpectedResponse);*/

		String user = Utils.getValueFromResponse(ResponseData.asString(), "First-Name") + " "+ Utils.getValueFromResponse(ResponseData.asString(), "Last-Name");
		if(createUserActualReponse.contains(createUserExpectedResponse)){
			System.out.println("Account of "+user+" has been created successfully..");	
		}else{
			System.out.println("Something went wrong !! Please check..");
			throw new AssertionError();
		}
		
	}

	//Create Account
	public static void postRequest(String url, String userData, int expectedStatusCode) throws Exception {
		Response postRequestResponse=RestAssured.given()
				.contentType(ContentType.JSON)
				.body(userData)
				.post(url);
		System.out.println("Response Body as a String:: \n"+postRequestResponse.asString());
		Assert.assertEquals("Status code does not match..",expectedStatusCode, postRequestResponse.getStatusCode());
	}
	
	public static void putRequest(String url) throws Exception {

		System.out.println("PUT : "+url);
		FileInputStream fis=new FileInputStream(new File("D:\\Cogniwise\\ExcelData\\RBL_Excel_Sheet.xlsx"));

		XSSFWorkbook workbook=new XSSFWorkbook(fis);
		XSSFSheet sheet=workbook.getSheet("UpdateAccountSheet");

		//getCell ==>> column 	//row ==> row...

		try{
			for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
				String valueFirstName=null, valueLastName=null,
						valueMobile=null, valueCity=null, valueCountry=null,
						valueBranch=null, valueAccountNo=null, valueBalance=null;

				for (int colIndex = 0; colIndex <= 7; colIndex++) {	
					if (colIndex == 0){
						valueFirstName = sheet.getRow(rowIndex).getCell(colIndex).toString();
					}
					else if	(colIndex == 1){
						valueLastName = sheet.getRow(rowIndex).getCell(colIndex).toString();
					}
					else if(colIndex == 2){
						valueMobile = sheet.getRow(rowIndex).getCell(colIndex).toString();

					}else if (colIndex == 3){
						valueCity = sheet.getRow(rowIndex).getCell(colIndex).toString();

					}else if (colIndex == 4){
						valueCountry = sheet.getRow(rowIndex).getCell(colIndex).toString();

					}else if (colIndex == 5){
						valueBranch = sheet.getRow(rowIndex).getCell(colIndex).toString();

					}else if (colIndex == 6){
						valueAccountNo = sheet.getRow(rowIndex).getCell(colIndex).toString();

					}else if (colIndex == 7){
						valueBalance = sheet.getRow(rowIndex).getCell(colIndex).toString();
					}

					userData = "{\"First-Name\": \""+valueFirstName+"\",\"Last-Name\": \""+valueLastName+"\",\"Mobile No\": \""+valueMobile+"\",\"City\": \""+valueCity+"\",\"Country\": \""+valueCountry+"\",\"Branch-Name\": \""+valueBranch+"\",\"Account Type\": \""+valueAccountNo+"\",\"Current Balance\": \""+valueBalance+"\"}";
					if(colIndex == 7 && rowIndex !=0)
					{
						System.out.println("Calling method with data::"+userData);
						Response ResponseData=RestAssured.given()
								.contentType(ContentType.JSON)
								.body(userData)
								.put(url);
						System.out.println("Response Body :: "+ResponseData.asString());

						//Status code
						int patchResponseStatusCode=ResponseData.getStatusCode();
						System.out.println("Status Code :: "+patchResponseStatusCode);
					}

				}
			}
		}catch(NullPointerException e){

		}

		fis.close();
	}
}
