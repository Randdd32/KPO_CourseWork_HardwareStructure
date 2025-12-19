package pages;

import core.BaseSeleniumPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class DepartmentsPage extends BaseSeleniumPage {
    public final By nameField = By.name("name");

    public final By positionsSelect = By.xpath("//div[text()='Должности']/following-sibling::div[1]");

    private final By saveBtn = By.xpath("//button[text()='Сохранить']");

    public DepartmentsPage(WebDriver driver) { super(driver); }

    public void fillForm(String name, List<String> positions) {
        write(nameField, name);
        for (String pos : positions) {
            selectFromSearchable(positionsSelect, pos);
        }
        click(saveBtn);
    }

    public boolean isPositionSelected(String positionName) {
        try {
            return wait.until(ExpectedConditions.textToBePresentInElementLocated(positionsSelect, positionName));
        } catch (Exception e) {
            return false;
        }
    }
}
