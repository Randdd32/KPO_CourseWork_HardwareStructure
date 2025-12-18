package tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DeviceModelsPage;

public class DeviceModelsCrudTest extends AbstractCrudTest {

    @Test
    public void testDeviceModelsFullCycle() {
        DeviceModelsPage page = new DeviceModelsPage(driver);

        String modelName = "Model-" + System.currentTimeMillis();
        String updatedName = modelName + "-UPD";

        String type = "Персональный компьютер";
        String manufacturer = "Samsung";
        String structureElement = "Intel Core i5-10400";

        navigateToEntity("Модели устройств");

        clickAddNew();
        page.fillForm(modelName, "Тестовое описание модели", type, manufacturer, structureElement);

        clickEdit(modelName);
        Assert.assertEquals(page.getInputValue(page.nameField), modelName);
        Assert.assertEquals(page.getInputValue(page.descriptionField), "Тестовое описание модели");

        String effValue = page.getInputValue(page.efficiencyField);
        Assert.assertNotEquals(effValue, "0", "Эффективность модели не была рассчитана!");

        page.fillForm(updatedName, "Обновленное описание", "Ноутбук", "Apple", "AMD Ryzen 5 3600");

        clickEdit(updatedName);
        Assert.assertEquals(page.getInputValue(page.nameField), updatedName);
        Assert.assertEquals(page.getInputValue(page.descriptionField), "Обновленное описание");
        driver.findElement(By.xpath("//button[text()='Отмена']")).click();

        clickDelete(updatedName);
        boolean isDeleted = driver.findElements(By.xpath("//td[contains(text(), '" + updatedName + "')]")).isEmpty();
        Assert.assertTrue(isDeleted, "Модель устройства должна быть удалена из таблицы");
    }
}
