package com.AutomationTalks.demoProject1;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.Field;
import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestClass1 {
	
	
	public static WebDriver driver;
	
	
	@BeforeMethod
	public void launchDriver() throws MalformedURLException {
		// DesiredCapabilities dr = null;
		// dr = DesiredCapabilities.chrome();
		// dr.setBrowserName("chrome");
		// dr.setPlatform(Platform.LINUX);
		// System.setProperty("webdriver.chrome.driver", "D:\\Personal\\chromedriver.exe");
		
		// driver = new RemoteWebDriver(new URL("http://localhost:4446/wd/hub"), dr);
		
		// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// driver.manage().window().fullscreen();
	String CHROMEDRIVER_PATH = System.getenv("JENKINS_HOME");
	   System.setProperty("webdriver.chrome.driver", CHROMEDRIVER_PATH+"/chromedriver-linux64/chromedriver");

		Path tempDir = Files.createTempDirectory("chrome-user-data");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--user-data-dir=" + tempDir.toString());

         driver = new ChromeDriver(options);
		
            // Set timeouts and window management
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.manage().window().fullscreen();
	}
	
	@Test
	public void Test1() {
		driver.navigate().to("https://automationtalks.com/");
		System.out.println("Test 1 title is "+driver.getTitle());
	}
	
	@Test
	public void Test2() {
		driver.navigate().to("https://automationtalks.com/");
		System.out.println("Test 2 title is "+driver.getTitle());
	}
	
	@Test
	public void Test3() {
		driver.navigate().to("https://automationtalks.com/");
		System.out.println("Test 3 title is "+driver.getTitle());
		Assert.assertEquals("Expected title", driver.getTitle());
	}
	
	@AfterMethod
	public void quit(ITestResult result) throws JiraException {
		driver.quit();
		//if test case fails then log the defect in JIRA
		if(result.getStatus() == ITestResult.FAILURE) {
			
			BasicCredentials creds = new BasicCredentials("admin", "admin");
			JiraClient jira = new JiraClient("http://localhost:8081", creds);
			Issue issueName = jira.createIssue("AUT", "Bug").field(Field.SUMMARY, result.getMethod().getMethodName() +"is failed due to: "+ result.getThrowable().toString()).field(Field.DESCRIPTION, "get the description").execute();
			System.out.println("Issue is created in JIRA with issue key: "+issueName.getKey());
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}
