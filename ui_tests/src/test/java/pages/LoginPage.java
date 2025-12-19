package pages;

import core.BaseSeleniumPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class LoginPage extends BaseSeleniumPage {
    private final By emailField = By.name("email");
    private final By passwordField = By.name("password");
    private final By loginButton = By.xpath("//button[contains(text(), 'Войти в систему')]");
    private final By otpInputs = By.cssSelector(".otp_input input");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String email, String pass, String otpCode) {
        write(emailField, email);
        write(passwordField, pass);
        click(loginButton);

        List<WebElement> inputs = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(otpInputs));
        for (int i = 0; i < 6; i++) {
            inputs.get(i).sendKeys(String.valueOf(otpCode.charAt(i)));
        }
        click(By.xpath("//button[contains(text(), 'Подтвердить')]"));
    }
}
