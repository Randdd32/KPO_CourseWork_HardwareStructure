package pages;

import core.BaseSeleniumPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class StructureElementTypesPage extends BaseSeleniumPage {
    public final By nameField = By.name("name");
    private final By saveBtn = By.xpath("//button[text()='Сохранить']");

    public StructureElementTypesPage(WebDriver driver) { super(driver); }

    public void fillForm(String name) {
        write(nameField, name);
        click(saveBtn);
    }
}
