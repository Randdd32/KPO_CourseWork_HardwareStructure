package pages;

import core.BaseSeleniumPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Objects;

public class DeviceDetailsPage extends BaseSeleniumPage {
    private final By serialNumberTitle = By.className("device-serial-number");
    private final By statusText = By.xpath("//div[contains(@class, 'device-status-container')]//p");

    private final By charBtn = By.xpath("//button[contains(., 'Характеристики модели')]");
    private final By structBtn = By.xpath("//button[contains(., 'Структура устройства')]");

    private final By modalTitle = By.className("modal-title");
    private final By modalCloseBtn = By.xpath("//div[contains(@class, 'modal-footer')]//button[text()='Закрыть']");

    public DeviceDetailsPage(WebDriver driver) {
        super(driver);
    }

    public String getSerialNumber() {
        return Objects.requireNonNull(wait.until(ExpectedConditions.visibilityOfElementLocated(serialNumberTitle))).getText();
    }

    public String getStatus() {
        return driver.findElement(statusText).getText();
    }

    public void openCharacteristics() {
        click(charBtn);
    }

    public void openStructure() {
        click(structBtn);
    }

    public String getModalHeaderText() {
        return Objects.requireNonNull(wait.until(ExpectedConditions.visibilityOfElementLocated(modalTitle))).getText();
    }

    public void closeModal() {
        click(modalCloseBtn);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(modalTitle));
    }
}
