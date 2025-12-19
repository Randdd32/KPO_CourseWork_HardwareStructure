package tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.UsersPage;

public class UsersCrudTest extends AbstractCrudTest {

    @Test
    public void testUsersFullCycle() {
        UsersPage page = new UsersPage(driver);

        String email = "test-user-" + System.currentTimeMillis() + "@example.com";
        String updatedEmail = "upd-" + email;
        String pass = "TestPass123!";
        String phone = "89112223344";

        navigateToEntity("Пользователи");

        clickAddNew();
        page.fillForm(email, pass, phone, "USER", "Баранова Оксана Геннадьевна");

        clickEdit(email);
        Assert.assertEquals(page.getInputValue(page.emailField), email);
        Assert.assertEquals(page.getInputValue(page.phoneField), "+79112223344");

        page.fillForm(updatedEmail, "", "89000000000", "USER", null);

        clickEdit(updatedEmail);
        Assert.assertEquals(page.getInputValue(page.emailField), updatedEmail);
        Assert.assertEquals(page.getInputValue(page.phoneField), "+79000000000");
        driver.findElement(By.xpath("//button[text()='Отмена']")).click();

        clickDelete(updatedEmail);
        boolean isDeleted = driver.findElements(By.xpath("//td[text()='" + updatedEmail + "']")).isEmpty();
        Assert.assertTrue(isDeleted, "Элемент должен быть удален из таблицы");
    }
}
