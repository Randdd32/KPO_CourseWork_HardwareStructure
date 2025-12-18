package tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DepartmentsPage;

import java.util.Arrays;
import java.util.List;

public class DepartmentsCrudTest extends AbstractCrudTest {

    @Test
    public void testDepartmentsFullCycle() {
        DepartmentsPage page = new DepartmentsPage(driver);

        String deptName = "Тестовый Отдел " + System.currentTimeMillis();
        String updatedName = deptName + " (Обновлен)";

        List<String> initialPositions = Arrays.asList("Менеджер по продажам", "Бухгалтер");
        List<String> addedPosition = List.of("Разработчик");

        navigateToEntity("Отделы");

        clickAddNew();
        page.fillForm(deptName, initialPositions);

        clickEdit(deptName);
        Assert.assertEquals(page.getInputValue(page.nameField), deptName);
        Assert.assertTrue(page.isPositionSelected("Менеджер по продажам"));
        Assert.assertTrue(page.isPositionSelected("Бухгалтер"));

        page.fillForm(updatedName, addedPosition);

        clickEdit(updatedName);
        Assert.assertEquals(page.getInputValue(page.nameField), updatedName);
        Assert.assertTrue(page.isPositionSelected("Разработчик"));
        driver.findElement(org.openqa.selenium.By.xpath("//button[text()='Отмена']")).click();

        clickDelete(updatedName);
        boolean isDeleted = driver.findElements(By.xpath("//td[text()='" + updatedName + "']")).isEmpty();
        Assert.assertTrue(isDeleted, "Элемент должен быть удален из таблицы");
    }
}
