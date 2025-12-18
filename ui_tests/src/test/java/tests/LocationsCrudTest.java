package tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LocationsPage;

public class LocationsCrudTest extends AbstractCrudTest {

    @Test
    public void testLocationsFullCycle() {
        LocationsPage page = new LocationsPage(driver);

        String locName = "Локация-" + System.currentTimeMillis();
        String updatedName = locName + "-UPD";

        navigateToEntity("Помещения");

        // 1. CREATE
        clickAddNew();
        // Данные из Initializer: "Главный корпус", "Отдел маркетинга", "Щеглова Мария Сергеевна"
        page.fillForm(locName, "OFFICE", "Главный корпус", "Отдел маркетинга", "Щеглова Мария Сергеевна");

        // 2. VERIFY CREATE
        clickEdit(locName);
        Assert.assertEquals(page.getInputValue(page.nameField), locName);
        // Проверка значения в обычном селекте
        String selectedType = new org.openqa.selenium.support.ui.Select(driver.findElement(page.typeSelect))
                .getFirstSelectedOption().getAttribute("value");
        Assert.assertEquals(selectedType, "OFFICE");

        // 3. UPDATE
        // Меняем тип на "Серверная", здание и ответственного
        page.fillForm(updatedName, "SERVER_ROOM", "Корпус B", "Техническая поддержка", "Целобанова Зинаида Арсеньевна");

        // 4. VERIFY UPDATE
        clickEdit(updatedName);
        Assert.assertEquals(page.getInputValue(page.nameField), updatedName);
        String updatedType = new org.openqa.selenium.support.ui.Select(driver.findElement(page.typeSelect))
                .getFirstSelectedOption().getAttribute("value");
        Assert.assertEquals(updatedType, "SERVER_ROOM");
        driver.findElement(By.xpath("//button[text()='Отмена']")).click();

        // 5. DELETE
        clickDelete(updatedName);
        boolean isDeleted = driver.findElements(By.xpath("//td[contains(text(), '" + updatedName + "')]")).isEmpty();
        Assert.assertTrue(isDeleted, "Элемент должен быть удален из таблицы");
    }
}
