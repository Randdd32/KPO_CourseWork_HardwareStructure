package tests;

import core.BaseSeleniumTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ProfilePage;
import readProperties.ConfigReader;

import java.util.Objects;

public class ProfileTest extends BaseSeleniumTest {

    @Test
    public void testAdminProfileDataDisplay() {
        ProfilePage profilePage = new ProfilePage(driver);
        String adminEmail = ConfigReader.getProperty("admin.email");

        click(By.xpath("//a[contains(text(), '" + adminEmail + "')]"));

        click(By.xpath("//a[contains(text(), 'Профиль')]"));

        Assert.assertEquals(profilePage.getEmailValue(), adminEmail,
                "Email в профиле не соответствует логину!");

        Assert.assertEquals(profilePage.getRoleValue(), "Супер-администратор",
                "Отображается неверная роль!");

        Assert.assertEquals(profilePage.getNameValue(), "Яковиченко Валерий Алексеевич",
                "ФИО сотрудника в профиле неверное!");

        Assert.assertFalse(profilePage.getPhoneValue().isEmpty(), "Номер телефона не должен быть пустым!");
    }

    private void click(By locator) {
        Objects.requireNonNull(wait.until(ExpectedConditions.elementToBeClickable(locator))).click();
    }
}
