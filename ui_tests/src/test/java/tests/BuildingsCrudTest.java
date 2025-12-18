package tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.BuildingsPage;

public class BuildingsCrudTest extends AbstractCrudTest {

    @Test
    public void testBuildingsFullCycle() {
        BuildingsPage buildingsPage = new BuildingsPage(driver);
        String name = "Тестовое Здание " + System.currentTimeMillis();
        String address = "г. Москва, ул. Тестов, д. 1";
        String updatedName = name + " UPD";
        String updatedAddress = "Новый адрес 202";

        navigateToEntity("Здания");

        clickAddNew();
        buildingsPage.fillForm(name, address);

        clickEdit(name);
        Assert.assertEquals(buildingsPage.getInputValue(buildingsPage.nameField), name);
        Assert.assertEquals(buildingsPage.getInputValue(buildingsPage.addressField), address);

        buildingsPage.fillForm(updatedName, updatedAddress);

        clickEdit(updatedName);
        Assert.assertEquals(buildingsPage.getInputValue(buildingsPage.nameField), updatedName);
        Assert.assertEquals(buildingsPage.getInputValue(buildingsPage.addressField), updatedAddress);
        driver.findElement(By.xpath("//button[text()='Отмена']")).click();

        clickDelete(updatedName);
        boolean isDeleted = driver.findElements(By.xpath("//td[text()='" + updatedName + "']")).isEmpty();
        Assert.assertTrue(isDeleted, "Элемент должен быть удален из таблицы");
    }
}
