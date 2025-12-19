package pages;

import core.BaseSeleniumPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Objects;

public class SearchPage extends BaseSeleniumPage {
    private final By navSearchInput = By.xpath("//input[@type='search']");
    private final By navSearchBtn = By.xpath("//button[text()='Поиск']");

    private final By manufacturerFilter = By.xpath("//h6[text()='Производители']/following-sibling::div[1]");
    private final By statusRadioWorking = By.id("isWorkingTrue");
    private final By resetFiltersBtn = By.xpath("//button[text()='Сбросить фильтры']");

    private final By deviceCard = By.className("device-card-link");

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    public void searchFromNavbar(String text) {
        write(navSearchInput, text);
        click(navSearchBtn);
    }

    public void filterByManufacturer(String brand) {
        selectFromSearchable(manufacturerFilter, brand);
    }

    public void filterByStatusWorking() {
        click(statusRadioWorking);
    }

    public int getResultsCount() {
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        return driver.findElements(deviceCard).size();
    }

    public String getFirstCardTitle() {
        return Objects.requireNonNull(wait.until(ExpectedConditions.visibilityOfElementLocated(deviceCard))).getText();
    }
}
