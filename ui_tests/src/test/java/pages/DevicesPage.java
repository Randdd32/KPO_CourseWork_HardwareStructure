package pages;

import core.BaseSeleniumPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DevicesPage extends BaseSeleniumPage {
    public final By serialField = By.name("serialNumber");
    public final By priceField = By.name("price");
    public final By workingCheckbox = By.id("isWorking");

    public final By purchaseDateField = By.xpath("//div[div[text()='Дата покупки']]//input");
    public final By warrantyDateField = By.xpath("//div[div[text()='Дата окончания гарантии']]//input");

    public final By modelSelect = By.xpath("//div[contains(text(), 'Модель устройства')]/following-sibling::div[1]");
    public final By locationSelect = By.xpath("//div[text()='Помещение']/following-sibling::div[1]");
    public final By employeeSelect = By.xpath("//div[text()='Ответственный сотрудник']/following-sibling::div[1]");

    private final By saveButton = By.xpath("//button[text()='Сохранить']");

    public DevicesPage(WebDriver driver) {
        super(driver);
    }

    public void fillForm(String sn, String date, String warranty, String price,
                         boolean working, String model, String loc, String emp) {
        write(serialField, sn);
        enterDate(purchaseDateField, date);
        enterDate(warrantyDateField, warranty);
        write(priceField, price);

        if (driver.findElement(workingCheckbox).isSelected() != working) {
            click(workingCheckbox);
        }

        selectFromSearchable(modelSelect, model);
        selectFromSearchable(locationSelect, loc);
        selectFromSearchable(employeeSelect, emp);

        click(saveButton);
    }
}
