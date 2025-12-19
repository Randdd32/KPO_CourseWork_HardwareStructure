package pages;

import core.BaseSeleniumPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Objects;

public class ReportsPage extends BaseSeleniumPage {
    private final By displayOnSiteBtn = By.xpath("//button[contains(text(), 'Вывести отчет на сайте')]");
    private final By reportContainer = By.className("report-content");
    private final By loadingSpinner = By.className("spinner-border");

    private final By dateFromInput = By.xpath("//label[contains(text(), 'Начало периода')]/..//input");
    private final By dateToInput = By.xpath("//label[contains(text(), 'Конец периода')]/..//input");

    private final By devicesSelect = By.xpath("//div[contains(text(), 'Выберите устройства')]/following-sibling::div[1]");
    private final By locationsSelect = By.xpath("//div[contains(text(), 'Выберите помещения')]/following-sibling::div[1]");

    public ReportsPage(WebDriver driver) {
        super(driver);
    }

    public void generateDevicesByDate(String from, String to) {
        enterDate(dateFromInput, from);
        enterDate(dateToInput, to);
        clickDisplay();
    }

    public void generateDevicesWithStructure(String serialNumber) {
        selectFromSearchable(devicesSelect, serialNumber);
        clickDisplay();
    }

    public void generateLocationsWithEmployees(String locationName) {
        selectFromSearchable(locationsSelect, locationName);
        clickDisplay();
    }

    private void clickDisplay() {
        click(displayOnSiteBtn);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingSpinner));
    }

    public boolean isReportTableVisible() {
        try {
            return Objects.requireNonNull(wait.until(ExpectedConditions.visibilityOfElementLocated(reportContainer))).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getReportText() {
        return Objects.requireNonNull(wait.until(ExpectedConditions.visibilityOfElementLocated(reportContainer))).getText();
    }
}
