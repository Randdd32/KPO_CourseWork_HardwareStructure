package pages;

import core.BaseSeleniumPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class LocationsPage extends BaseSeleniumPage {
    public final By nameField = By.name("name");
    public final By typeSelect = By.name("type");

    public final By buildingSelect = By.xpath("//div[contains(text(), 'Здание')]/following-sibling::div[1]");
    public final By departmentSelect = By.xpath("//div[text()='Отдел']/following-sibling::div[1]");
    public final By employeesSelect = By.xpath("//div[text()='Сотрудники']/following-sibling::div[1]");

    private final By saveBtn = By.xpath("//button[text()='Сохранить']");

    public LocationsPage(WebDriver driver) { super(driver); }

    public void fillForm(String name, String typeValue, String building, String dept, String employee) {
        write(nameField, name);

        Select selectElement = new Select(driver.findElement(typeSelect));
        selectElement.selectByValue(typeValue);

        selectFromSearchable(buildingSelect, building);
        selectFromSearchable(departmentSelect, dept);

        if (employee != null) {
            selectFromSearchable(employeesSelect, employee);
        }

        click(saveBtn);
    }
}
