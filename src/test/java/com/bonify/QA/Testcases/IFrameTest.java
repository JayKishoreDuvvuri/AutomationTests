package com.bonify.QA.Testcases;

import static org.testng.Assert.assertTrue;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class IFrameTest {

	WebDriver driver;
	WebDriverWait wait;

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
		// driver.manage().window().fullscreen();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		// driver.manage().deleteAllCookies();
	}

	@Parameters("expectedTitle") // Login to Application as an Already Existing User
	@Test(priority = 5)
	public void ValidateIFrameinFinanzen(String expectedTitle) {
		driver.findElement(By.xpath("//input[@name='email']")).sendKeys("jaykishore999@gmail.com");
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys("bonify@123");
		driver.findElement(By.xpath("//button[@type='submit']//div[@class='ripple-wrapper']")).click();
		wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Angebote')]")))
				.click();

		driver.findElement(By
				.xpath("//li[contains(@id,'/my-products/financial-products')]//div[contains(@class,'ripple-wrapper')]"))
				.click();

		// Handling IFrame - IFrame xpath is
		//iframe[contains(@src,'/dist/9ab29bbcb5ffa57e176b6422412b40c8.html?campaignId=iframe&postcode=10117&subPartnerId=389179')]
		WebElement iframeElement = driver.findElement(By.xpath("//*[@id=\"main-body\"]/div[2]/section/section/iframe"));
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

		wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("/html/body/div[2]/section/div/div/div[3]/div/div[2]/a"))).click();
		//You can use the below xpath instead of the above xpath
		//Xpath----> //div[@class='col-md-4 col-sm-4']//a[contains(text(),'zum Anbieter')]

		String Parentwindow = driver.getWindowHandle();
		Set<String> allWindows = driver.getWindowHandles();
		System.out.println("Parent Window is :" +Parentwindow); 
		System.out.println("all Windows is :" +allWindows);

		for (String currentwindow : allWindows) {
			if (!Parentwindow.equalsIgnoreCase(currentwindow)) {
				driver.switchTo().window(currentwindow);
			}
			System.out.println("current Window is :" +currentwindow);
		}
		driver.switchTo().window(Parentwindow);

		driver.switchTo().defaultContent();
		driver.findElement(By.xpath("//span[contains(text(),'Einstellungen')]")).click();

	  //Use this Xpath Below
	  //WebElement Element = driver.findElement(By.xpath("//i[@class='ico-abmelden']//span[@class='path1']"));
		WebElement Element = driver.findElement(By.xpath("//*[@id='main-body']//ul[1]//li[4]//button//span"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", Element);

		String ActualTitle = driver.getTitle();
		String ExpectedTitle = expectedTitle;
		Assert.assertEquals(ActualTitle, ExpectedTitle);
	}

	@Parameters({ "href", "LoginTitle" })
	@Test(priority = 6)
	public void ValidateIFrameinMobilFunk(String href, String LoginTitle) {
		driver.findElement(By.xpath("//input[@name='email']")).sendKeys("jaykishore999@gmail.com");
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys("bonify@123");
		driver.findElement(By.xpath("//button[@type='submit']//div[@class='ripple-wrapper']")).click();
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Angebote')]")))
				.click();

		driver.findElement(
				By.xpath("//li[contains(@id,'/my-products/mobile')]//div[contains(@class,'ripple-wrapper')]")).click();

		// Handling IFrame
		// Use the this Xpath --> //iframe[starts-with(@src,'/dist/92397488724f14f36b4fbd443a90760f.html?campaignId=iframe&postcode')]
		WebElement iframeElement = driver.findElement(By.xpath("//*[@id=\"main-body\"]/div[2]/section/section/iframe"));
		

		driver.switchTo().frame(iframeElement);// Login to First Frame */

		WebElement iframe_Element = driver.findElement(By.xpath(
				"//iframe[contains(@src,'https://tools.communicationads.net/calc.php?tp=dif&cl=bundle&h=1&wf=10865&country=DE&subid=')]"));
		driver.switchTo().frame(iframe_Element);// Login to Second Frame

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
	    List<WebElement> Links = driver.findElements(By.xpath("//a[contains(@href,'https://www.communicationads.net/tc.php?t=10865C12507003D&deeplink=bundle&product=31924369') and @class='ca_button']"));
	//	List<WebElement> Links = driver.findElements(By.xpath("//a[contains(@href,'product=31924369&subid=390793')]"));
		System.out.println("Links is :" +Links);
		// Total Number of links
		int Linkcount = Links.size();
		System.out.println("Linkcount is :" +Linkcount);
		String[] Texts = new String[Linkcount];
		for (int i = 0; i < Linkcount; i++) {
			Texts[i] = Links.get(i).getText();
			if (Texts[i].equalsIgnoreCase(href)) {
				WebElement Element = driver.findElement(By.linkText(Texts[i]));
				System.out.println("Element is :" +Element); 
				System.out.println("Texts[i] is :" +Texts[i]);
				Element.click();
			}
		} 
		String Parentwindow = driver.getWindowHandle();
		Set<String> allWindows = driver.getWindowHandles();
		System.out.println("Parent Window is :" +Parentwindow); 
		System.out.println("all Windows is :" +allWindows);
		for (String currentwindow : allWindows) {
			if (!Parentwindow.equalsIgnoreCase(currentwindow)) {
				driver.switchTo().window(currentwindow);
			}
			System.out.println("current Window is :" +currentwindow); 
		}
		driver.switchTo().window(Parentwindow);

		driver.switchTo().defaultContent();
		
		// Use this Xpath Below
        WebElement Element = driver.findElement(By.xpath("//i[@class='ico-abmelden']//span[@class='path1']"));
	//	Don't use this xpath - WebElement Element = driver.findElement(By.xpath("//*[@id='main-body']//ul[1]//li[4]//button//span"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", Element);

		assertTrue(driver.getTitle().equals(LoginTitle));
		assertTrue(driver.getCurrentUrl().contains("my-products/mobile/mobile"));
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}
