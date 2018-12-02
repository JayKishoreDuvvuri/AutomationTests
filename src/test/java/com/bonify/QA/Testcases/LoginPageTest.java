package com.bonify.QA.Testcases;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LoginPageTest {

	WebDriver driver;

	@Parameters({ "browser", "url" })
	@BeforeMethod
	public void setUp(String browser, String url) {
		if (browser.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver", "src/test/resources/Test Suite/chromedriver.exe");
			driver = new ChromeDriver();
		} 
		else if (browser.equalsIgnoreCase("Firefox")) {
			System.setProperty("webdriver.gecko.driver", "src/test/resources/Test Suite/geckodriver.exe");
			driver = new FirefoxDriver();
		} 
		else if (browser.equalsIgnoreCase("Edge")) {
			System.setProperty("webdriver.edge.driver", "src/test/resources/Test Suite/MicrosoftWebDriver.exe");
			driver = new EdgeDriver();
		}
		driver.get(url);
		//driver.manage().window().fullscreen();
		driver.manage().window().maximize();
		//driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
	}

	@Test(priority = 1) // Forgot Password and Checking Back Button
	public void PasswortVergessen() {
		driver.findElement(By.xpath("//span[contains(text(),'Passwort vergessen?')]")).click();
		driver.findElement(By.xpath("//button[@type='button' and @class='btn-flat btn-flat-negative']")).click();
		driver.findElement(By.xpath("//span[contains(text(),'Passwort vergessen?')]")).click();
		driver.findElement(By.xpath("//input[@name='email']")).sendKeys("test123@gmail.com");
		driver.findElement(By.xpath("//button[@type='submit' and @class='btn-flat-cta btn-flat-cta-default']")).click();
		driver.findElement(By.xpath("//button[@type='button' and @class='btn btn-cta']")).click();
	}

	@Parameters({ "ExpectedTitle", "Expected_Title", "expectedurl" })
	@Test(priority = 2) // Validate Impressum link
	public void ValidationofLinks(String ExpectedTitle, String Expected_Title, String expectedurl) {
		driver.findElement(By.xpath("//span[contains(text(),'Impressum')]")).click();
		driver.navigate().refresh();
		String Parentwindow = driver.getWindowHandle();
		System.out.println("Parent Window is :" +Parentwindow); 
		Set<String> allWindows = driver.getWindowHandles();
		System.out.println("all Windows is :" +allWindows); 
		for (String currentwindow : allWindows) {
			if (!Parentwindow.equalsIgnoreCase(currentwindow)) {
				driver.switchTo().window(currentwindow);
				System.out.println("current Window is :" +currentwindow); 
			}
		}
		String actualTitle = driver.getTitle();
		Assert.assertEquals(actualTitle, ExpectedTitle);
		driver.close();
		driver.switchTo().window(Parentwindow);

		// Validate the Link AGB - Conditions link
		driver.findElement(By.xpath("//span[contains(text(),'AGB')]")).click();
		driver.navigate().refresh();
		String Parentwindow2 = driver.getWindowHandle();
		Set<String> allWindows2 = driver.getWindowHandles();

		for (String currentwindow : allWindows2) {
			if (!Parentwindow2.equalsIgnoreCase(currentwindow)) {
				driver.switchTo().window(currentwindow);
			}
		}
		String ActualTitle = driver.getTitle();
		Assert.assertEquals(ActualTitle, Expected_Title);
		driver.close();
		driver.switchTo().window(Parentwindow);

		// Validate the Link Datenschutz - Data Protection Link
		driver.findElement(By.xpath("//span[contains(text(),'Datenschutz')]")).click();
		driver.navigate().refresh();
		String Parentwindow3 = driver.getWindowHandle();
		Set<String> allWindows3 = driver.getWindowHandles();

		for (String currentwindow : allWindows3) {
			if (!Parentwindow3.equalsIgnoreCase(currentwindow)) {
				driver.switchTo().window(currentwindow);
			}
		}
		String actualurl = driver.getCurrentUrl();
		Assert.assertEquals(actualurl, expectedurl);
		driver.close();
		driver.switchTo().window(Parentwindow);
	}

	@Parameters("LoginTitle")
	@Test(priority = 3) // SignUp OR Register as a New User for creating a new account
	public void SignUpAsaNewUser(String LoginTitle) {
		String timestamp = String.valueOf(new Date().getTime());
		String email = "qa_" + timestamp + "@te" + timestamp.substring(7) + ".com";
		driver.findElement(By.xpath("//button[@type='button']//div[@class='ripple-wrapper']")).click();
		driver.findElement(By.xpath("//input[@name='email']")).sendKeys(email);
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys("Testabc@111");

		// Toggle the button to show the password visible
		WebElement Element = driver.findElement(By.xpath("//div[@class='toggle toggle-password bottom']//div"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", Element);

		driver.findElement(By.xpath("//input[@name='newsletter']")).click();
		driver.findElement(By.xpath("//div[@class='ripple-wrapper']")).click();

		driver.findElement(By.xpath("//input[contains(@id,'gender-male')][contains(@name,'gender')]")).click();
		// Select a Title from the Dropdown
		Select Title = new Select(driver.findElement(By.xpath("//select[@name='title']")));
		Title.selectByVisibleText("Prof.");

		driver.findElement(By.xpath("//input[@name='firstName']")).sendKeys("William");
		driver.findElement(By.xpath("//input[@name='lastName']")).sendKeys("Hills");
		driver.findElement(By.xpath("//input[@name='dateOfBirth']")).sendKeys("10.04.1988");
		driver.findElement(By.xpath("//input[@name='street']")).sendKeys("Rue Tason Snel");
		driver.findElement(By.xpath("//input[@name='houseNumber']")).sendKeys("122P");
		driver.findElement(By.xpath("//input[@name='zipCode']")).sendKeys("23683");
		driver.findElement(By.xpath("//input[@name='city']")).sendKeys("Frankfurt");
		driver.findElement(By.xpath("//button[@type='submit']//div[@class='ripple-wrapper']")).click();

		// Fill in the BankName/BankCode
		driver.findElement(By.xpath("//input[@name='blz']")).click();
		driver.findElement(By.xpath("//input[@name='blz']")).sendKeys("48080020");
		int size = driver.findElements(By.xpath("//*[contains(@id,'step-')]//div//div//div//ul//li//button//span"))
				.size();
		driver.findElements(By.xpath("//*[contains(@id,'step-')]//div//div//div//ul//li//button//span")).get(size - 1)
				.click();

		// Enter the Bank Username
		WebElement elementName = driver.findElement(By.xpath("//input[contains(@name,'accountId')]"));
		JavascriptExecutor username = (JavascriptExecutor) driver;
		username.executeScript("arguments[0].click();", elementName);
		elementName.sendKeys("Test123TQA");

		// Enter Bank's online banking Password
		driver.findElement(By.xpath("//input[contains(@type,'password')][contains(@name,'pin')]"))
				.sendKeys("123online*&^%pwd");

		driver.findElement(By.xpath("//span[contains(text(),'mit Personalausweis fortfahren')]")).click();
		WebElement identificationButton = driver
				.findElement(By.xpath("//button[contains(@class,'btn-flat btn-flat-negative')]"));
		JavascriptExecutor Button = (JavascriptExecutor) driver;
		Button.executeScript("arguments[0].click();", identificationButton);

		driver.findElement(By.xpath(
				"//button[contains(@class,'btn-flat-cta btn-flat-cta-alert')]//div[contains(@class,'ripple-wrapper')]"))
				.click();
		driver.navigate().back();
		String ActualTitle = driver.getTitle();
		Assert.assertEquals(ActualTitle, LoginTitle);
	}

	@Parameters("LoginTitle")
	@Test(priority = 4) // Login to Application as an Already Existing User
	public void LogintoApplication(String LoginTitle) {
		driver.findElement(By.xpath("//input[@name='email']")).sendKeys("jaykishore999@gmail.com");
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys("bonify@123");
		driver.findElement(By.xpath("//button[@type='submit']//div[@class='ripple-wrapper']")).click();
		String actualTitle = driver.getTitle();
		Assert.assertEquals(actualTitle, LoginTitle);
	}

	@AfterMethod
	public void tearDown() {
		driver.close();
	}
}
