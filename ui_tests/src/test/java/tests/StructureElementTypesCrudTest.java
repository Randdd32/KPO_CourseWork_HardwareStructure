package tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.StructureElementTypesPage;

public class StructureElementTypesCrudTest extends AbstractCrudTest {

    @Test
    public void testStructureElementTypesFullCycle() {
        StructureElementTypesPage page = new StructureElementTypesPage(driver);

        String name = "Компонент-" + System.currentTimeMillis();
        String updatedName = name + "-UPD";

        navigateToEntity("Типы элементов структуры");

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
