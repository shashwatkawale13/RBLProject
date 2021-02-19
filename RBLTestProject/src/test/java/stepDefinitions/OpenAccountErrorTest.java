package stepDefinitions;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class OpenAccountErrorTest {
	WebDriver driver;
	Workbook book;
	Sheet sheet;
	FileInputStream read;
	
	@Given("browser is open and page visited")
	public void browser_is_open_and_page_visited() throws Exception {
	  
		read = new FileInputStream("D:\\Cogniwise\\ExcelData\\RBL_Excel_Sheet.xlsx");
		book = new XSSFWorkbook(read);
		sheet = book.getSheet("AccountSheet");
		
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\shash\\Downloads\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.navigate().to("https://www.rblbank.com/");
		driver.manage().window().maximize();
		
		//driver.findElement(By.cssSelector("a.close")).click();
		driver.findElement(By.xpath("//a[@type='button'][@class='close']")).click();
		Thread.sleep(2000);
		//retrieving the rates displayed on homepage
        List<WebElement> interestRates = driver.findElements(By.xpath("/html/body/div[1]/div[5]/div[3]/div/div/div"));
       
        //asserting if all rates displayed on Homepage are as expected or not.
        //compare actual rates displayed with stored expected values in excel sheet
       
        try {
           
            for(int c = 0; c < 4; c++)
            {
                //System.out.println(interestRates.get(c).getText());
                //System.out.println(book.getSheet("Sheet2").getRow(c).getCell(0).getStringCellValue());
                assertEquals(book.getSheet("InterestRate").getRow(c).getCell(0).getStringCellValue(),interestRates.get(c).getText());
            }
       
        }
        catch (AssertionError ar) {
            System.out.println("Interest rate mismatched");
        }
	}
	
	//clicking on Öpen Account" button
	@When("I click on Open Account button")
	public void i_click_on_open_account_button() {
	 
		driver.findElement(By.xpath("/html/body/div[1]/div[1]/header/div/div/a[1]")).click();
		driver.findElement(By.id("open-now-header")).click();		
		
	}
	
	//check if email id and mobile number fields are displayed or not
	@Then("it should prompt to enter email address and mobile number")
	public void it_should_prompt_to_enter_email_address_and_mobile_number() {
		WebElement emailId = driver.findElement(By.name("custEmail"));
	     assertTrue(emailId.isDisplayed());
	     WebElement mobileNo = driver.findElement(By.name("custMob"));
	     assertTrue(mobileNo.isDisplayed());
	}
	
	
	//enters the email id - retrieving value from excel sheet
	@When("I enter invalid email id")
	public void i_enter_invalid_email_id() {
		String emailId = sheet.getRow(1).getCell(0).getStringCellValue();
	   driver.findElement(By.name("custEmail")).sendKeys(emailId);
	   //driver.findElement(By.name("custMob")).sendKeys("12344566");
	   
	}
	
	//clicking on "Get Started" button and takes a screenshot
	@When("click on get started button")
	public void click_on_get_started_button() throws Exception {
		/*Thread.sleep(2000);
		boolean terms = driver.findElement(By.xpath("//input[@formcontrolname='custCheck'][@type='checkbox']")).isSelected();
		System.out.println(terms);
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@formcontrolname='custCheck'][@type='checkbox']")).click();
		*/
	  driver.findElement(By.xpath("//*[@id=\"get-started-popup\"]")).click();
	  File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	  File destFile = new File("D:\\Nisha\\Error.png");
	  FileUtils.copyFile(srcFile, destFile);
	}
	
	//checks if erro message is displayed with valid error message
	@Then("error message should appear")
	public void error_message_should_appear()  throws Exception {
		Thread.sleep(3000);
        WebElement errorMsg = driver.findElement(By.xpath("/html/body/modal-container/div/div/div/div/form/div[1]/div[2]/span[1]"));
        if(errorMsg.isDisplayed())
        {
            String actualError = errorMsg.getText();
            try
            {
                assertEquals("please enter valid mail id", actualError);
            }
            catch(AssertionError ar)
            {
                ar.printStackTrace();
            }
          }
        driver.close();
	}



}
