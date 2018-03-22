package uk.dvla.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author Raj
 */
public class HomePage extends BasePage {

	private static final String BASE_URL = "https://www.gov.uk/get-vehicle-information-from-dvla";

	@FindBy(className = "pub-c-button")
	private WebElement startButton;

	public HomePage(final WebDriver driver, final WebDriverWait wait) {
		super(driver, wait);
	}

	// Go to home page
	public void goToHomePage() {
		driver.get(BASE_URL);
	}

	// Go to vehicle lookup page
	public void goToVehicleLookupPage() {
		click(startButton);
	}

}
