package winapp.automation.core;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import io.appium.java_client.windows.WindowsDriver;

public class TestBase {
    
    public static void startTheAppiumDriverServer()  {
    	System.out.println("inside startTheAppiumDriverServer method");
   
    	String batchFilePath = System.getProperty("user.dir")+"\\winAppDriver\\run-win-app-driver.bat"; 
    	System.out.println("batchFilePath is : "+batchFilePath);

        try {
            // Create the ProcessBuilder with the batch file as a command
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", batchFilePath);
            processBuilder.redirectErrorStream(true); // Redirect error stream to the output stream

            // Start the process
            Process process = processBuilder.start();

            // Wait for the process to complete
            int exitCode = process.waitFor();

            // Check the exit code to determine if the batch file ran successfully
            if (exitCode == 0) {
                System.out.println("Batch file executed successfully.");
            } else {
                System.out.println("Batch file execution failed. Exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public static void stopTheAppiumDriverServer()  {
    	
    	String processName = "WinAppDriver.exe"; // The name of the process you want to stop (e.g., cmd.exe)
        
        try {
            // Find and kill the process using the taskkill command
            ProcessBuilder processBuilder = new ProcessBuilder("taskkill", "/IM", processName, "/F");
            Process process = processBuilder.start();
            
            // Wait for the process to complete (optional)
            int exitCode = process.waitFor();
            
            if (exitCode == 0) {
                System.out.println("Process " + processName + " stopped successfully.");
            } else {
                System.out.println("Failed to stop process " + processName + ". Exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public static void runTheApplication() throws MalformedURLException, InterruptedException {
    	
    	System.out.println("first line");
    	URL winAppDriverUrl = new URL("http://127.0.0.1:4723");
    	System.out.println("first line");

        // Set Calculator application capabilities
        DesiredCapabilities appCapabilities = new DesiredCapabilities();
        System.out.println("first line");
        appCapabilities.setCapability("app", "Microsoft.WindowsCalculator_8wekyb3d8bbwe!App");
        
        
        // Create WindowsDriver instance
        Common.AppSession = new WindowsDriver<>(winAppDriverUrl, appCapabilities);

        // OTHER INITIALIZATIONS
        Common.explicitWait = new WebDriverWait(Common.AppSession, 60);
        Common.keyAction = new Actions(Common.AppSession);

        Thread.sleep(2000);

    }
    	
    	
    }

