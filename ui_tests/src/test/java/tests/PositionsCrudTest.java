package tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.PositionsPage;

public class PositionsCrudTest extends AbstractCrudTest {

    @Test
    public void testPositionsFullCycle() {
        PositionsPage page = new PositionsPage(driver);

        String posName = "Должность-" + System.currentTimeMillis();
        String updatedName = posName + "-UPD";
        String description = "Тестовое описание функциональных обязанностей";
        String updatedDescription = "Обновленное описание обязанностей";

        navigateToEntity("Должности");

        clickAddNew();
        page.fillForm(posName, description);

        clickEdit(posName);
        Assert.assertEquals(page.getInputValue(page.nameField), posName);
        Assert.assertEquals(page.getInputValue(page.descriptionField), description);

        page.fillForm(updatedName, updatedDescription);

        clickEdit(updatedName);
        Assert.assertEquals(page.getInputValue(page.nameField), updatedName);
        Assert.assertEquals(page.getInputValue(page.descriptionField), updatedDescription);

        driver.findElement(By.xpath("//button[text()='Отмена']")).click();

        clickDelete(updatedName);
        boolean isDeleted = driver.findElements(By.xpath("//td[contains(text(), '" + updatedName + "')]")).isEmpty();
        Assert.assertTrue(isDeleted, "Элемент должен быть удален из таблицы");
    }
}
