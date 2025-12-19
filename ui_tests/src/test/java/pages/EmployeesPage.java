package pages;

import core.BaseSeleniumPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EmployeesPage extends BaseSeleniumPage {
    public final By lastNameField = By.name("lastName");
    public final By firstNameField = By.name("firstName");
    public final By patronymicField = By.name("patronymic");

    public final By departmentSelect = By.xpath("//div[text()='Отдел']/following-sibling::div[1]");
    public final By positionSelect = By.xpath("//div[text()='Должность']/following-sibling::div[1]");

    private final By saveBtn = By.xpath("//button[text()='Сохранить']");

    public EmployeesPage(WebDriver driver) { super(driver); }

    public void fillForm(String ln, String fn, String pn, String dept, String pos) {
        write(lastNameField, ln);
        write(firstNameField, fn);
        write(patronymicField, pn);

        selectFromSearchable(departmentSelect, dept);
        selectFromSearchable(positionSelect, pos);

        click(saveBtn);
    }
}
