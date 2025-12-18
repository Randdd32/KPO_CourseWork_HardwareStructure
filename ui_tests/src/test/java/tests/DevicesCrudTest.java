package tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DevicesPage;

import java.util.UUID;

public class DevicesCrudTest extends AbstractCrudTest {

    @Test
    public void testDevicesFullCycle() {
        DevicesPage page = new DevicesPage(driver);

        String model = "HP Pavilion 15";
        String location = "Кабинет 102";
        String employee = "Кузарин Максим Романович";

        String sn = "SN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        navigateToEntity("Устройства");

        clickAddNew();
        page.fillForm(sn, "2023-10-10 10:00:00", "2025-10-10 10:00:00", "45000", true, model, location, employee);

        clickEdit(sn);
        Assert.assertEquals(page.getInputValue(page.serialField), sn);
        Assert.assertEquals(page.getInputValue(page.priceField), "45000");

        page.fillForm(sn, "2023-10-10 10:00:00", "2025-10-10 10:00:00", "99000", false,
                "Apple MacBook Pro 16", "Серверная 1", "Фарест Никита Григорьевич");

        clickEdit(sn);
        Assert.assertEquals(page.getInputValue(page.priceField), "99000");
        Assert.assertFalse(driver.findElement(page.workingCheckbox).isSelected());
        driver.findElement(By.xpath("//button[text()='Отмена']")).click();

        clickDelete(sn);

        boolean isDeleted = driver.findElements(By.xpath("//td[text()='" + sn + "']")).isEmpty();
        Assert.assertTrue(isDeleted, "Элемент должен быть удален из таблицы");
    }
}
