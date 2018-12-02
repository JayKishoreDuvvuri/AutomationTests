package com.bonify.QA.Individual.Testcases;

import static org.testng.Assert.assertTrue;
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
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class ValidateIFrameinMobilFunk {

	WebDriver driver;
	WebDriverWait wait;

	@BeforeTest
	public void setUp() {

		System.setProperty("webdriver.chrome.driver", "src/test/resources/Test Suite/chromedriver.exe");
		driver = new ChromeDriver();
		/*
		 * System.setProperty("webdriver.edge.driver",
		 * "src/test/resources/Test Suite/MicrosoftWebDriver.exe"); driver = new
		 * EdgeDriver();
		 */
		driver.get("https://my.bonify.de");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().deleteAllCookies();
	}

	@Test
	public void IFrameinMobilFunk() {
		driver.findElement(By.xpath("//input[@name='email']")).sendKeys("jaykishore999@gmail.com");
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys("bonify@123");
		driver.findElement(By.xpath("//button[@type='submit']//div[@class='ripple-wrapper']")).click();
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Angebote')]")))
				.click();

		driver.findElement(
				By.xpath("//li[contains(@id,'/my-products/mobile')]//div[contains(@class,'ripple-wrapper')]")).click();

		// Handling IFrame
		// WebElement iframeElement =
		// driver.findElement(By.xpath("//*[@id=\"main-body\"]/div[2]/section/section/iframe"));

		// Use the Below IFrame xpath
		// WebElement iframeElement = driver.findElement(By.xpath(
		// "//iframe[@src='/dist/92397488724f14f36b4fbd443a90760f.html?campaignId=iframe&postcode=10117&subPartnerId=390793']"));
		WebElement iframeElement = driver.findElement(By
				.xpath("//iframe[starts-with(@src,'/dist/92397488724f14f36b4fbd443a90760f.html?campaignId=iframe&postcode')]"));
		driver.switchTo().frame(iframeElement);// Login to First Frame */

		WebElement iframe_Element = driver.findElement(By.xpath(
				"//iframe[contains(@src,'https://tools.communicationads.net/calc.php?tp=dif&cl=bundle&h=1&wf=10865&country=DE&subid=')]"));
		driver.switchTo().frame(iframe_Element);// Login to Second IFrame

		Select deviceID = new Select(driver.findElement(By.id("deviceid")));
		deviceID.selectByValue("183");

		Select devicememory = new Select(driver.findElement(By.id("device_memory")));
		devicememory.selectByVisibleText("Ab 256 GB");

		Select phonevolume = new Select(driver.findElement(By.name("phone_volume")));
		phonevolume.selectByValue("250");

		Select numberport = new Select(driver.findElement(By.name("numberporting")));
		numberport.selectByValue("1");

		Select colorID = new Select(driver.findElement(By.id("device_colorid")));
		colorID.selectByVisibleText("Gold");

		Select cost = new Select(driver.findElement(By.name("cost_setup_max")));
		cost.selectByValue("300");

		Select volume = new Select(driver.findElement(By.name("mobileweb_volume")));
		volume.selectByVisibleText("Ab 2 GB");

		driver.findElement(By.name("submit")).click();
		
		// To get and select the correct Mobile Provider from the list of Mobile
		// Providers available and navigate to Providers Website
//		driver.findElement(
//				By.xpath("//a[contains(@href,'product=31924369&subid=390793') and contains(text(),'zum Anbieter')]"))
//				.click();
		
	     driver.findElement(By.xpath("//a[contains(@href,'https://www.communicationads.net/tc.php?t=10865C12507003D&deeplink=bundle&product=31924369') and @class='ca_button']"))
		.click();
		
	     System.out.println("Clicked on the required element");
				// OR 

     	//List<WebElement> Links = driver.findElements(By.xpath("//a[contains(@href,'product=31924369&subid=390793')]"));
    /* 	 List<WebElement> Links = driver.findElements(By.xpath("//a[contains(@href,'https://www.communicationads.net/tc.php?t=10865C12507003D&deeplink=bundle&product=31924369') and @class='ca_button']"));
		 int Linkcount = Links.size(); 
		 String[] Texts = new String[Linkcount];
		 System.out.println("LinkCount is:" +Linkcount);
		 System.out.println("Texts is :" +Texts); 
		 for (int i = 0; i < Linkcount; i++) { 
			 Texts[i] = Links.get(i).getText(); 
			 if(Texts[i].equalsIgnoreCase("zum Anbieter")) { 
				 WebElement Element = driver.findElement(By.linkText(Texts[i])); 
				 System.out.println("Element is :"+Element); 
				 System.out.println("Texts[i] is: " +Texts[i]); 
				 Element.click(); 
				 } 
		 } */
		 
		 
		 
		String Parentwindow = driver.getWindowHandle();
		System.out.println("Parent Window is :" +Parentwindow); 
		Set<String> allWindows = driver.getWindowHandles();
		System.out.println("all Windows is :" +allWindows);

		for (String currentwindow : allWindows) {
			if (!Parentwindow.equalsIgnoreCase(currentwindow)) {
				driver.switchTo().window(currentwindow);
			}
			System.out.println("current Window is :" +currentwindow); 
		}
		driver.switchTo().window(Parentwindow);

		driver.switchTo().defaultContent();

		// WebElement Element =
		// driver.findElement(By.xpath("//*[@id='main-body']//ul[1]//li[4]//button//span"));

		// OR

		// Use this Xpath Below
		WebElement Element = driver.findElement(By.xpath("//i[@class='ico-abmelden']//span[@class='path1']"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", Element);

		assertTrue(driver.getTitle().equals("bonify"));
		assertTrue(driver.getCurrentUrl().contains("my-products/mobile/mobile"));
	}

	@AfterTest 
	public void tearDown() {
		driver.quit();
	}

}
