package tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.EmployeesPage;

public class EmployeesCrudTest extends AbstractCrudTest {

    @Test
    public void testEmployeesFullCycle() {
        EmployeesPage page = new EmployeesPage(driver);

        String lastName = "ТestName";
        String firstName = "Иван";
        String patronymic = "Иванович";

        String fullName = lastName + " " + firstName + " " + patronymic;
        String updatedLastName = "UpdТestName";
        String updatedFullName = updatedLastName + " " + firstName + " " + patronymic;

        navigateToEntity("Сотрудники");

        clickAddNew();
        page.fillForm(lastName, firstName, patronymic, "Финансовый отдел", "Бухгалтер");

        clickEdit(fullName);
        Assert.assertEquals(page.getInputValue(page.lastNameField), lastName);
        Assert.assertEquals(page.getInputValue(page.firstNameField), firstName);

        page.fillForm(updatedLastName, firstName, patronymic, "Служба безопасности", "Охранник");

        clickEdit(updatedFullName);
        Assert.assertEquals(page.getInputValue(page.lastNameField), updatedLastName);
        driver.findElement(By.xpath("//button[text()='Отмена']")).click();

        clickDelete(updatedFullName);
        boolean isDeleted = driver.findElements(By.xpath("//td[contains(text(), '" + updatedFullName + "')]")).isEmpty();
        Assert.assertTrue(isDeleted, "Элемент должен быть удален из таблицы");
    }
}
