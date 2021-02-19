package runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"RBLFeatures"}, glue = "stepDefinitions", plugin = {"pretty", "json:target/jsonreports/jsonreport.json"}, monochrome = true, publish = false)
public class RunOpenAccountFeature {

}
