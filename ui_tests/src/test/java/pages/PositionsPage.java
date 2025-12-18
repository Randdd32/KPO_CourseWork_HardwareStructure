package pages;

import core.BaseSeleniumPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PositionsPage extends BaseSeleniumPage {
    public final By nameField = By.name("name");
    public final By descriptionField = By.name("description");
    private final By saveBtn = By.xpath("//button[text()='Сохранить']");

    public PositionsPage(WebDriver driver) {
        super(driver);
    }

    public void fillForm(String name, String description) {
        write(nameField, name);
        write(descriptionField, description);
        click(saveBtn);
    }
}
