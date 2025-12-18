package pages;

import core.BaseSeleniumPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DeviceModelsPage extends BaseSeleniumPage {
    public final By nameField = By.name("name");
    public final By descriptionField = By.name("description");

    public final By efficiencyField = By.name("workEfficiency");
    public final By reliabilityField = By.name("reliability");

    public final By typeSelect = By.xpath("//div[contains(text(), 'Тип устройства')]/following-sibling::div[1]");
    public final By manufacturerSelect = By.xpath("//div[contains(text(), 'Производитель')]/following-sibling::div[1]");
    public final By structureSelect = By.xpath("//div[contains(text(), 'Структура')]/following-sibling::div[1]");

    private final By saveBtn = By.xpath("//button[text()='Сохранить']");

    public DeviceModelsPage(WebDriver driver) { super(driver); }

    public void fillForm(String name, String description, String type, String manufacturer, String structureItem) {
        write(nameField, name);
        write(descriptionField, description);

        selectFromSearchable(typeSelect, type);
        selectFromSearchable(manufacturerSelect, manufacturer);

        if (structureItem != null) {
            selectFromSearchable(structureSelect, structureItem);
        }

        click(saveBtn);
    }
}
