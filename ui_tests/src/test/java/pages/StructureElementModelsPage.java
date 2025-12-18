package pages;

import core.BaseSeleniumPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class StructureElementModelsPage extends BaseSeleniumPage {
    public final By nameField = By.name("name");
    public final By descriptionField = By.name("description");

    public final By typeSelect = By.xpath("//div[contains(text(), 'Тип элемента структуры')]/following-sibling::div[1]");
    public final By manufacturerSelect = By.xpath("//div[contains(text(), 'Производитель')]/following-sibling::div[1]");

    public final By efficiencyField = By.name("workEfficiency");
    public final By reliabilityField = By.name("reliability");
    public final By energyField = By.name("energyEfficiency");
    public final By userFriendlyField = By.name("userFriendliness");
    public final By durabilityField = By.name("durability");
    public final By aestheticField = By.name("aestheticQualities");

    private final By saveBtn = By.xpath("//button[text()='Сохранить']");

    public StructureElementModelsPage(WebDriver driver) { super(driver); }

    public void fillForm(String name, String desc, String type, String manuf, String score) {
        write(nameField, name);
        write(descriptionField, desc);

        selectFromSearchable(typeSelect, type);
        selectFromSearchable(manufacturerSelect, manuf);

        write(efficiencyField, score);
        write(reliabilityField, score);
        write(energyField, score);
        write(userFriendlyField, score);
        write(durabilityField, score);
        write(aestheticField, score);

        click(saveBtn);
    }
}
