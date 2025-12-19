package pages;

import core.BaseSeleniumPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BuildingsPage extends BaseSeleniumPage {
    public final By nameField = By.name("name");
    public final By addressField = By.name("address");
    private final By saveButton = By.xpath("//button[contains(text(), 'Сохранить')]");

    public BuildingsPage(WebDriver driver) {
        super(driver);
    }

    public void fillForm(String name, String address) {
        write(nameField, name);
        write(addressField, address);
        click(saveButton);
    }
}
