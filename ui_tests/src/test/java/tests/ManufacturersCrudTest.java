package tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ManufacturersPage;

public class ManufacturersCrudTest extends AbstractCrudTest {

    @Test
    public void testManufacturersFullCycle() {
        ManufacturersPage page = new ManufacturersPage(driver);

        String name = "Бренд-" + System.currentTimeMillis();
        String updatedName = name + "-UPD";

        navigateToEntity("Производители");

        clickAddNew();
        page.fillForm(name);

        clickEdit(name);
        Assert.assertEquals(page.getInputValue(page.nameField), name, "Имя производителя не совпадает!");
        page.fillForm(updatedName);

        clickEdit(updatedName);
        Assert.assertEquals(page.getInputValue(page.nameField), updatedName, "Обновленное имя не сохранилось!");
        driver.findElement(By.xpath("//button[text()='Отмена']")).click();

        clickDelete(updatedName);
        boolean isDeleted = driver.findElements(By.xpath("//td[contains(text(), '" + updatedName + "')]")).isEmpty();
        Assert.assertTrue(isDeleted, "Элемент должен быть удален из таблицы");
    }
}
