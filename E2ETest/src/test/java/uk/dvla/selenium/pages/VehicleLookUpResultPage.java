package uk.dvla.selenium.pages;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author Raj
 */
public class VehicleLookUpResultPage extends BasePage {

	@FindBy(xpath = "//*[@id=\"pr3\"]//li[2]//strong")
	private WebElement vehicleMake;

	@FindBy(xpath = "//*[@id=\"pr3\"]//li[3]//strong")
	private WebElement vehicleColour;

	public VehicleLookUpResultPage(WebDriver driver, WebDriverWait wait) {
		super(driver, wait);
	}

	/**
	 * This method returns vechicle make element text
	 * 
	 * @return String
	 */
	public String getVehicleMake() {
		return readText(vehicleMake);
	}

	/**
	 * This method returns vechicle colour element text
	 * 
	 * @return String
	 */
	public String getVehicleColour() {
		return readText(vehicleColour);
	}

	/**
	 * This method verifies expected vehicle make value with actual vehicle make
	 * value
	 */
	public void verifyVehicleMake(final String expectedMake) {
		assertTrue(expectedMake.trim().equalsIgnoreCase(getVehicleMake().trim()));
	}

	/**
	 * This method verifies expected vehicle colour value with actual vehicle colour
	 * value
	 */
	public void verifyVehicleColour(final String expectedColour) {
		assertTrue(expectedColour.trim().equalsIgnoreCase(getVehicleColour().trim()));
	}
}
