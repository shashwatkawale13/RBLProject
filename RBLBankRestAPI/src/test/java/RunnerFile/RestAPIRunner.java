package RunnerFile;

import org.junit.AfterClass;
import org.junit.runner.RunWith;

/*import org.junit.AfterClass;
import org.junit.runner.RunWith;*/

import com.cucumber.listener.Reporter;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features="./FeatureFiles/ReqUseCases.feature",
				 glue="StepDefinationFiles",
				 dryRun=false,
				 strict=true,
				 monochrome=true,
				 plugin = { "com.cucumber.listener.ExtentCucumberFormatter:target/RBL.html"}
				 )
public class RestAPIRunner {

	@AfterClass
	public static void writeExtentReport() {
		Reporter.loadXMLConfig("./extent-config.xml");
	}
}
