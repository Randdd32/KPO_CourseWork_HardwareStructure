package pages;

import core.BaseSeleniumPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Objects;

public class ProfilePage extends BaseSeleniumPage {
    private final By emailVal = By.xpath("//dt[text()='Электронная почта']/following-sibling::dd[1]");
    private final By phoneVal = By.xpath("//dt[text()='Номер телефона']/following-sibling::dd[1]");
    private final By roleVal = By.xpath("//dt[text()='Ваша роль']/following-sibling::dd[1]");
    private final By nameVal = By.xpath("//dt[text()='ФИО']/following-sibling::dd[1]");

    public ProfilePage(WebDriver driver) {
        super(driver);
    }

    public String getEmailValue() {
        return Objects.requireNonNull(wait.until(ExpectedConditions.visibilityOfElementLocated(emailVal))).getText();
    }

    public String getPhoneValue() {
        return Objects.requireNonNull(wait.until(ExpectedConditions.visibilityOfElementLocated(phoneVal))).getText();
    }

    public String getRoleValue() {
        return Objects.requireNonNull(wait.until(ExpectedConditions.visibilityOfElementLocated(roleVal))).getText();
    }

    public String getNameValue() {
        return Objects.requireNonNull(wait.until(ExpectedConditions.visibilityOfElementLocated(nameVal))).getText();
    }
}
