package uk.dvla.selenium.pages;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author Raj
 */
public class VehicleLookupPage extends BasePage {

	@FindBy(name = "Vrm")
	private WebElement textBox;

	@FindBy(name = "Continue")
	private WebElement continueButton;

	public VehicleLookupPage(final WebDriver driver, final WebDriverWait wait) {
		super(driver, wait);
	}

	/**
	 * This method is used to enter vehicle registration number in vehicle lookup
	 * page text box.
	 * 
	 * @param vehicleRegistrationNumber
	 */
	public void enterVehicleRegistrationNumber(final String vehicleRegistrationNumber) {
		textBox.sendKeys(vehicleRegistrationNumber);
	}

	/**
	 * This method navigates to vehicle lookup result page.
	 */
	public void goToVehicleLookUpResultPage() {
		click(continueButton);
	}

	public void verifyTextBoxIsDisplayed() {
		assertTrue(isElementDisplayed(textBox));
	}

	public void verifyContinueButtonIsDisplayed() {
		assertTrue(isElementDisplayed(continueButton));
	}

}
