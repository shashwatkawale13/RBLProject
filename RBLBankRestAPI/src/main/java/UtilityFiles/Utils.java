package UtilityFiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import freemarker.core.ReturnInstruction.Return;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Utils {

	//static String [] userList;
	
	static String userData;

	public static int getIdOfUser(String response){
		int id = com.jayway.jsonpath.JsonPath.read(response, "id");
		return id;

	}

	public static String getValueFromResponse(String response, String attribute){
		return com.jayway.jsonpath.JsonPath.read(response, attribute);
	}

	public static String removeNewLineCharSpaces(String input){
		String stringForComparision = input.replaceAll("[\\n\\t ]", "");  // remove new line and spaces
		return stringForComparision;
	}

	public static String createUserExpectedResponse(String inputData, int idOfUser){
		// remove new line and spaces
		String createUserExpectedResponse = inputData.replaceAll("[\\n\\t ]", ""); 
		// remove curly braces {
		createUserExpectedResponse = createUserExpectedResponse.substring(0, createUserExpectedResponse.indexOf("}"))+",\"id\":"; 
		//Add id of a user from post request response
		createUserExpectedResponse = createUserExpectedResponse +idOfUser+"}";
		return createUserExpectedResponse;
	}

	public static List<String> readUserDataFromSheet(List<String> userList,String sheetName) throws IOException{
		FileInputStream fis = null;
		XSSFSheet sheet=null;
		try {
			fis = new FileInputStream(new File("D:\\Cogniwise\\ExcelData\\RBL_Excel_Sheet.xlsx"));
			XSSFWorkbook workbook=new XSSFWorkbook(fis);
			if(sheetName.equals("AccountSheet"))
			{
				sheet=workbook.getSheet("AccountSheet");
			}
			else if(sheetName.equals("UpdateAccountSheet"))
			{
				sheet=workbook.getSheet("UpdateAccountSheet");
			}
			
			//getCell ==>> column 	//row ==> row...
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
						userList.add(userData);
					}
				}
			}
		}catch(NullPointerException e){
		}
		fis.close();
		return userList;
	}
}
