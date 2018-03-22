package uk.dvla.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import static org.openqa.selenium.support.PageFactory.initElements;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author Raj
 */
public class BasePage {
	protected WebDriver driver;
	protected WebDriverWait wait;

	public BasePage(final WebDriver driver, final WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		initElements(driver, this);
	}

	// Click Method
	public void click(final WebElement element) {
		element.click();
	}

	// Read Text
	public String readText(final WebElement element) {
		return element.getText();
	}

	public boolean isElementDisplayed(final WebElement element) {
		return element.isDisplayed();
	}
}
