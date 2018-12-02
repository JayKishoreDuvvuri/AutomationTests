package com.bonify.QA.Individual.Testcases;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ValidateIFrameinFinanzen {

	WebDriver driver;
	WebDriverWait wait;

	@BeforeTest
	public void setUp() {

		System.setProperty("webdriver.chrome.driver", "src/test/resources/Test Suite/chromedriver.exe");
		driver = new ChromeDriver(); 
	/*	System.setProperty("webdriver.edge.driver", "src/test/resources/Test Suite/MicrosoftWebDriver.exe");
		driver = new EdgeDriver();  */
		driver.get("https://my.bonify.de");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.manage().deleteAllCookies();
	}

	// Login to Application as an Already Existing User
	@Test
	public void IFrameinFinanzen() {
		driver.findElement(By.xpath("//input[@name='email']")).sendKeys("jaykishore999@gmail.com");
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys("bonify@123");
		driver.findElement(By.xpath("//button[@type='submit']//div[@class='ripple-wrapper']")).click();
		wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Angebote')]")))
				.click();

		driver.findElement(By
				.xpath("//li[contains(@id,'/my-products/financial-products')]//div[contains(@class,'ripple-wrapper')]"))
				.click();

		// Handling IFrame
		//WebElement iframeElement = driver.findElement(By.xpath("//*[@id=\"main-body\"]/div[2]/section/section/iframe"));
		
		// Use the Below IFrame xpath -
		 WebElement iframeElement = driver.findElement(By.xpath(
		 "//iframe[contains(@src,'/dist/9ab29bbcb5ffa57e176b6422412b40c8.html?campaignId=iframe&postcode=10117&subPartnerId=389179')]"));

		driver.switchTo().frame(iframeElement);// Login to First Frame

		// Second Frame ID is @id="vxcp_frame"
		driver.switchTo().frame("vxcp_frame");// Login to Second Frame

		driver.findElement(By.xpath("//input[contains(@name,'umsatzeuroland')]")).clear();
		driver.findElement(By.xpath("//input[contains(@name,'umsatzeuroland')]")).sendKeys("1000");

		driver.findElement(By.xpath("//input[contains(@name,'umsatznichteuroland')]")).clear();
		driver.findElement(By.xpath("//input[contains(@name,'umsatznichteuroland')]")).sendKeys("500");

		Select cardtype = new Select(driver.findElement(By.name("kartengesellschaft")));
		cardtype.selectByVisibleText("Visa");

		Select Status = new Select(driver.findElement(By.name("anzeige")));
		Status.selectByValue("2");

		Select Payment = new Select(driver.findElement(By.name("zahlungsart")));
		Payment.selectByValue("3");

		driver.findElement(By.xpath("//input[contains(@name,'submit')][@class='btn fa_button_submit btn-block']"))
				.click();

		/*
		 * wait = new WebDriverWait(driver, 10); 
		 * wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[2]/section/div/div/div[3]/div/div[2]/a")))
		 * .click();
		 */

		// Use the Below Xpath
		wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//div[@class='col-md-4 col-sm-4']//a[contains(text(),'zum Anbieter')]"))).click();

		String Parentwindow = driver.getWindowHandle();
		Set<String> allWindows = driver.getWindowHandles();

		for (String currentwindow : allWindows) {
			if (!Parentwindow.equalsIgnoreCase(currentwindow)) {
				driver.switchTo().window(currentwindow);
			}
		}
		driver.switchTo().window(Parentwindow);

		driver.switchTo().defaultContent();
		driver.findElement(By.xpath("//span[contains(text(),'Einstellungen')]")).click();

		// WebElement Element =
		// driver.findElement(By.xpath("//*[@id='main-body']//ul[1]//li[4]//button//span"));

		// OR

		// Use this Xpath Below
		WebElement Element = driver.findElement(By.xpath("//i[@class='ico-abmelden']//span[@class='path1']"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", Element);

		String ActualTitle = driver.getTitle();
		String ExpectedTitle = "bonify";
		Assert.assertEquals(ActualTitle, ExpectedTitle);
	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}
