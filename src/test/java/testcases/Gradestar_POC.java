package testcases;

import io.appium.java_client.windows.WindowsDriver;
import page.locators.PageLocators;
import pages.StartupPage;
import winapp.automation.core.Common;
import winapp.automation.core.DriverActions;
import winapp.automation.core.TestBase;

import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import coreUtilities.testutils.ApiHelper;
import coreUtilities.utils.FileOperations;

import java.net.MalformedURLException;
import java.net.URL;

public class  Gradestar_POC{
	// this is to instantiate the PageLocators for accessing all the locators
	 public PageLocators pageLocators = new PageLocators();
	 
	 /*
	  * About BeforeClass annotation: ll start as the first thing as and when this class get executed by testng
	  * it ll run once per class and for all the test methods
	  * */
	 
	@BeforeClass(alwaysRun = true)
	public void initWinAppDriverServer() throws Exception {
		TestBase.startTheAppiumDriverServer();
	}
	
	// this is the actual test case as per the business requirements
	@Test(  description = "Add 2 numbers",
            groups = {"GradestarTest"} )
    public void testAdditionOperation() throws InterruptedException, MalformedURLException{
    	        System.out.println("first lin5");
    	        TestBase.runTheApplication();       
    	        //to do
    	        //perform the login
          
				DriverActions.Click(pageLocators.BTN_NUM_1);
			
				DriverActions.Click(pageLocators.BTN_PLUS);

				DriverActions.Click(pageLocators.BTN_NUM_2);

				DriverActions.Click(pageLocators.BTN_EQUALS);

				Assertions.assertThat(getDisplayOutputText()).isEqualTo(Integer.toString(3), "The addition is not matching!");
        
    }
	
	//below method ll return a substring based upon the calculations
	public String getDisplayOutputText() {
        String result = DriverActions.GetText(pageLocators.CALC_RESULTS);
        return StringUtils.substringAfter(result, "Display is ");
    }



	/*
	  * About AfterClass annotation: ll be called at the end of all the test executions.
	  * it ll run once per class and for all the test methods
	  * */
@AfterClass(alwaysRun = true)
public void tearDown() {
	System.out.println("Closing the application in the AfterClass annotation");
	Common.AppSession.quit();

}

/*
 * About @AfterMethod annotation: ll be called at the end of each test executions.
 * it ll run once per each test methods
 * */
@AfterMethod
public void retryIfTestFails() throws Exception {
	System.out.println("TODO!!!!! code for retry mechanism");
}
}