package uk.dvla.test.elements.path;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * This class is used to identify web elements by using class names , name and
 * xpath etc...
 * 
 * @author Raj
 *
 */
public class ElementsPath {

	private WebDriver driver;
	private WebDriverWait wait;

	@FindBy(className = "pub-c-button")
	public WebElement startButton;

	@FindBy(name = "Vrm")
	public WebElement textBox;

	@FindBy(name = "Continue")
	public WebElement continueButton;

	@FindBy(xpath = "//*[@id=\"pr3\"]//li[2]//strong")
	public WebElement vehicleMake;

	@FindBy(xpath = "//*[@id=\"pr3\"]//li[3]//strong")
	public WebElement vehicleColour;

	public ElementsPath(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, 10);
		PageFactory.initElements(driver, this);
	}

	public void clickOnStartButton() {
		wait.until(ExpectedConditions.visibilityOf(startButton));
		startButton.click();
	}

	public void enterVehicaleRegisterationNumber(String vehicleRegisterationNumber) {
		wait.until(ExpectedConditions.visibilityOf(textBox));
		textBox.sendKeys(vehicleRegisterationNumber);
	}

	public void submitRegisterationNumber() {
		wait.until(ExpectedConditions.visibilityOf(continueButton));
		continueButton.click();
	}
}
