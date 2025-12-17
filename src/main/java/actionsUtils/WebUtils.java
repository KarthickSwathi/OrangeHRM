package actionsUtils;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.events.EventFiringDecorator;

import listeners.PopupListener;

public class WebUtils {

	private static final Logger log = LogManager.getLogger(WebUtils.class);

	WebDriver driver;
	int delay;

	public void setDelay(int seconds)
	{
		delay=seconds*1000;
	}

	public WebElement getElement(List<Object> locators)
	{

		WebElement ele=null;
		Duration impWait = driver.manage().timeouts().getImplicitWaitTimeout();
		for (int i = 2; i < locators.size(); i++) {
			try {
				By loc = (By)locators.get(i);
				ele=driver.findElement(loc);
				String[] locValue = loc.toString().split(": ", 2);
				log.info("Locator used : "+locValue[0].replace("By.","")+"="+locValue[1]);
				break;
			} catch (Exception e) {
				if (i==locators.size()-1) {
					log.error("Failed to find element: "+e);
					throw e;
				}
				driver.manage().timeouts().implicitlyWait(Duration.ZERO);
				continue;
			}
		}
		driver.manage().timeouts().implicitlyWait(impWait);
		return ele;
	}

	public void openChromeForTesting(String version) throws InterruptedException
	{
		try {

			System.setProperty("SE_FORCE_BROWSER_DOWNLOAD", "true");
			ChromeOptions opt=new ChromeOptions();
			opt.setBrowserVersion(version);
			opt.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
			driver=new ChromeDriver(opt);
			log.info("Chrome for Testing is launched");

		} catch (Exception e) {

			log.error("Failed to open CFT. Reason: "+e);
			throw e;
		}

		Thread.sleep(delay);
	}

	public void attachPopupHandler(List<By> popupLocators) throws InterruptedException
	{
		try {

			PopupListener listener = new PopupListener(popupLocators);
			driver=new EventFiringDecorator(listener).decorate(driver);
			log.info("Popup handler is added");

		} catch (Exception e) {

			log.error("Failed to attach popup handler. Reason: "+e);
			throw e;
		}

		Thread.sleep(delay);
	}

	public void openBrowser(String browserName) throws InterruptedException
	{
		try {
			browserName=browserName.toLowerCase();
			switch (browserName) {
			case "chrome":
				driver=new ChromeDriver();
				break;
			case "firefox":
				driver=new FirefoxDriver();
				break;
			case "edge":
				driver=new EdgeDriver();
				break;

			default:
				driver=new ChromeDriver();
				break;
			}
			log.info(browserName.toUpperCase()+" is launched");	
		} catch (Exception e) {

			log.error("Failed to open "+browserName.toUpperCase()+" browser. Reason: "+e);
			throw e;
		}


		Thread.sleep(delay);
	}
	
	public void openBrowser() throws InterruptedException
	{
		String browserName = System.getProperty("browser", "chrome").toLowerCase();
        boolean headless = Boolean.parseBoolean(
                System.getProperty("headless", "false"));
		try {
			
			switch (browserName) {
			case "chrome":
				 ChromeOptions chromeOptions = new ChromeOptions();
	                if (headless) {
	                    chromeOptions.addArguments("--headless=new");
	                    chromeOptions.addArguments("--no-sandbox");
	                    chromeOptions.addArguments("--disable-dev-shm-usage");
	                }
	                driver = new ChromeDriver(chromeOptions);
				break;
			case "firefox":
				 FirefoxOptions ffOptions = new FirefoxOptions();
	                if (headless) {
	                    ffOptions.addArguments("--headless");
	                }
	                driver = new FirefoxDriver(ffOptions);
				break;
			case "edge":
				EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) {
                    edgeOptions.addArguments("--headless=new");
                }
                driver = new EdgeDriver(edgeOptions);
				break;

			default:
				driver=new ChromeDriver();
				break;
			}
			log.info(browserName.toUpperCase()+" is launched");	
		} catch (Exception e) {

			log.error("Failed to open "+browserName.toUpperCase()+" browser. Reason: "+e);
			throw e;
		}


		Thread.sleep(delay);
	}

	public void maximize() throws InterruptedException
	{
		try {
			driver.manage().window().maximize();
			log.info("Browser is maximised");

		} catch (Exception e) {
			log.error("Failed to maximize. Reason: "+e);
			throw e;
		}
		Thread.sleep(delay);
	}

	public void navigateTo(String url) throws InterruptedException
	{
		try {
			driver.get(url);
			log.info("Navigated to "+url);

		} catch (Exception e) {
			log.error("Failed to navigate. Reason: "+e);
			throw e;
		}
		Thread.sleep(delay);
	}

	public void setImplicitwait(int seconds) throws InterruptedException
	{
		try {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(seconds));
			log.info("Implicit wait set to "+seconds+" seconds");

		} catch (Exception e) {
			log.error("Failed to set implicit wait. Reason: "+e);
			throw e;
		}
		Thread.sleep(delay);
	}

	public void clickOn(List<Object> locators) throws InterruptedException
	{
		String name = (String)locators.get(0);
		String type = (String)locators.get(1);
		try {

			getElement(locators).click();
			log.info("clicked on "+name+" "+type);

		} catch (Exception e) {
			log.error("Failed to click on "+name+" "+type+". Reason: "+e);
			throw e;
		}
		Thread.sleep(delay);
	}

	public void EnterInto(List<Object> locators, String input) throws InterruptedException
	{
		String name = (String)locators.get(0);
		String type = (String)locators.get(1);
		try {
		getElement(locators).sendKeys(input);
		log.info("Entered "+input+" into "+name+" "+type);
		} catch (Exception e) {
			log.error("Failed to enter "+input+" into "+name+" "+type+". Reason: "+e);
			throw e;
		}
		Thread.sleep(delay);
	}

	public Boolean verifyDisplayOf(List<Object> locators) throws InterruptedException
	{
		boolean bval=false;
		String name = (String)locators.get(0);
		String type = (String)locators.get(1);
		try {
		bval = getElement(locators).isDisplayed();
		log.info(name+" "+type+" is displayed");
		} catch (Exception e) {
			log.error("Failed to verify display of "+name+" "+type+". Reason: "+e);
			throw e;
		}
		Thread.sleep(delay);
		return bval;
	}

	public void quitBrowser() throws InterruptedException
	{
		try {
		driver.quit();
		log.info("Browser is closed");
		} catch (Exception e) {
			log.error("Failed to quit browser. Reason: "+e);
			throw e;
		}
		Thread.sleep(delay);
	}
}

