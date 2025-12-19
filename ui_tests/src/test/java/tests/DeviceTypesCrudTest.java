package tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DeviceTypesPage;

public class DeviceTypesCrudTest extends AbstractCrudTest {

    @Test
    public void testDeviceTypesFullCycle() {
        DeviceTypesPage page = new DeviceTypesPage(driver);

        String name = "Тип-" + System.currentTimeMillis();
        String updatedName = name + "-UPD";

        navigateToEntity("Типы устройств");

        clickAddNew();
        page.fillForm(name);

        clickEdit(name);
        Assert.assertEquals(page.getInputValue(page.nameField), name);
        page.fillForm(updatedName);

        clickEdit(updatedName);
        Assert.assertEquals(page.getInputValue(page.nameField), updatedName);
        driver.findElement(By.xpath("//button[text()='Отмена']")).click();

        clickDelete(updatedName);
        boolean isDeleted = driver.findElements(By.xpath("//td[contains(text(), '" + updatedName + "')]")).isEmpty();
        Assert.assertTrue(isDeleted, "Элемент должен быть удален из таблицы");
    }
}
