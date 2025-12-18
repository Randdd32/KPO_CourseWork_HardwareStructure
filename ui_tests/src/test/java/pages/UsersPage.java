package pages;

import core.BaseSeleniumPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class UsersPage extends BaseSeleniumPage {
    public final By emailField = By.name("email");
    public final By passwordField = By.name("password");
    public final By phoneField = By.name("phoneNumber");
    public final By roleSelect = By.name("role");

    public final By employeeSelect = By.xpath("//div[contains(text(), 'Владелец аккаунта')]/following-sibling::div[1]");

    private final By saveBtn = By.xpath("//button[text()='Сохранить']");

    public UsersPage(WebDriver driver) {
        super(driver);
    }

    public void fillForm(String email, String password, String phone, String role, String employee) {
        write(emailField, email);

        if (password != null && !password.isEmpty()) {
            write(passwordField, password);
        }

        write(phoneField, phone);

        new Select(driver.findElement(roleSelect)).selectByValue(role);

        if (employee != null) {
            selectFromSearchable(employeeSelect, employee);
        }

        click(saveBtn);
    }
}
