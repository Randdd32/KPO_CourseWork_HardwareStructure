package tests;

import core.BaseSeleniumTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import readProperties.ConfigReader;

import java.util.Objects;

public class LogoutTest extends BaseSeleniumTest {
    @Test
    public void testLogout() {
        String adminEmail = ConfigReader.getProperty("admin.email");;

        click(By.xpath("//a[contains(text(), '" + adminEmail + "')]"));

        click(By.xpath("//a[contains(text(), 'Выйти из аккаунта')]"));

        boolean isLoginModalVisible = Objects.requireNonNull(wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h1[text()='Вход в систему']")))).isDisplayed();

        Assert.assertTrue(isLoginModalVisible, "После выхода не отобразилась форма логина!");

        Object token = ((JavascriptExecutor) driver).executeScript("return localStorage.getItem('token');");
        Assert.assertNull(token, "Токен в localStorage должен быть удален!");
    }

    private void click(By loc) {
        Objects.requireNonNull(wait.until(ExpectedConditions.elementToBeClickable(loc))).click();
    }
}
