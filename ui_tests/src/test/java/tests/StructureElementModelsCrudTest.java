package tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.StructureElementModelsPage;

public class StructureElementModelsCrudTest extends AbstractCrudTest {

    @Test
    public void testStructureElementModelsFullCycle() {
        StructureElementModelsPage page = new StructureElementModelsPage(driver);

        String name = "ModelElem-" + System.currentTimeMillis();
        String updatedName = name + "-UPD";

        String initialType = "Процессор";
        String initialManuf = "Intel";

        navigateToEntity("Модели элементов структуры");

        clickAddNew();
        page.fillForm(name, "Описание компонента", initialType, initialManuf, "85");

        clickEdit(name);
        Assert.assertEquals(page.getInputValue(page.nameField), name);
        Assert.assertEquals(page.getInputValue(page.efficiencyField), "85");
        Assert.assertEquals(page.getInputValue(page.durabilityField), "85");

        page.fillForm(updatedName, "Новое описание", initialType, "AMD", "99");

        clickEdit(updatedName);
        Assert.assertEquals(page.getInputValue(page.nameField), updatedName);
        Assert.assertEquals(page.getInputValue(page.efficiencyField), "99");
        driver.findElement(By.xpath("//button[text()='Отмена']")).click();

        clickDelete(updatedName);
        boolean isDeleted = driver.findElements(By.xpath("//td[contains(text(), '" + updatedName + "')]")).isEmpty();
        Assert.assertTrue(isDeleted, "Элемент должен быть удален из таблицы");
    }
}
