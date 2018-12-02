package com.bonify.QA.Individual.Testcases;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SignUpTest {

	WebDriver driver;

	@BeforeTest
	public void setUp() {

		System.setProperty("webdriver.chrome.driver", "src/test/resources/Test Suite/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://my.bonify.de");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.manage().deleteAllCookies();
	}

	@Test // SignUp OR Register as a New User for creating a new account
	public void SignUpAsaNewUser() {
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

		System.out.println("Size is :" + size);

		// Enter the Bank Username
		WebElement elementName = driver.findElement(By.xpath("//input[contains(@name,'accountId')]"));
	//	JavascriptExecutor username = (JavascriptExecutor) driver;
	//	username.executeScript("arguments[0].click();", elementName);
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
		String LoginTitle = "bonify";
		System.out.println("ActualTitle is :" + ActualTitle);
		Assert.assertEquals(ActualTitle, LoginTitle);
	}

	@AfterTest
	public void tearDown() {
		driver.close();
	}
}
