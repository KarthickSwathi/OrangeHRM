package listeners;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;

public class PopupListener implements WebDriverListener {

    private final List<By> popupLocators;

    public PopupListener(List<By> popupLocators) {
        this.popupLocators = popupLocators;
    }

    @Override
    public void beforeFindElement(WebDriver driver, By locator) {
        closePopups(driver);
    }

    @Override
    public void beforeFindElements(WebDriver driver, By locator) {
        closePopups(driver);
    }
    
    @Override
    public void afterFindElement(WebDriver driver, By locator, WebElement result) {
        closePopups(driver);
    }

    @Override
    public void afterFindElements(WebDriver driver, By locator, List<WebElement> result) {
        closePopups(driver);
    }

    private void closePopups(WebDriver driver) {
    	Duration ImpWait = driver.manage().timeouts().getImplicitWaitTimeout();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
		try {

			for (By loc : popupLocators) {

				WebElement closeButton = driver.findElement(loc);

				JavascriptExecutor js = (JavascriptExecutor) driver;

				Boolean visible = (Boolean) js.executeScript(
						"var elem = arguments[0],"
								+ "box = elem.getBoundingClientRect(),"
								+ "cx = box.left + box.width/2,"
								+ "cy = box.top + box.height/2,"
								+ "e = document.elementFromPoint(cx, cy);"
								+ "return e === elem || elem.contains(e);",
								closeButton
						);

				if (visible != null && visible) {
					closeButton.click();
					break;
				}
				driver.manage().timeouts().implicitlyWait(Duration.ZERO);
			}

		} catch (Exception ignored) {}

		driver.manage().timeouts().implicitlyWait(ImpWait);
    }
}
