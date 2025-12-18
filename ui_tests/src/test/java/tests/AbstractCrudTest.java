package tests;

import core.BaseSeleniumPage;
import core.BaseSeleniumTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

public abstract class AbstractCrudTest extends BaseSeleniumTest {
    protected static final Logger logger = LoggerFactory.getLogger(AbstractCrudTest.class);

    protected void navigateToEntity(String entityName) {
        click(By.id("entities-dropdown"));
        click(By.xpath("//a[contains(@class, 'dropdown-item') and contains(text(), '" + entityName + "')]"));
    }

    protected void clickAddNew() {
        click(By.xpath("//*[contains(@class, 'btn-success') and contains(text(), 'Добавить')]"));
    }

    private void ensureElementIsVisible(String identifier) {
        By rowLocator = By.xpath("//td[contains(text(), '" + identifier + "')]");

        try {
            new WebDriverWait(driver, Duration.ofSeconds(3))
                    .until(ExpectedConditions.visibilityOfElementLocated(rowLocator));
        } catch (Exception e) {
            By lastPageBtn = By.xpath("//ul[contains(@class, 'pagination')]//a[contains(., '»»')]");

            List<WebElement> buttons = driver.findElements(lastPageBtn);
            if (!buttons.isEmpty() && buttons.get(0).isDisplayed()) {
                click(lastPageBtn);
                wait.until(ExpectedConditions.visibilityOfElementLocated(rowLocator));
            } else {
                logger.warn("Кнопка перехода на последнюю страницу не найдена. Возможно, в таблице всего одна страница.");
                throw e;
            }
        }
    }

    protected void clickEdit(String identifier) {
        ensureElementIsVisible(identifier);
        click(By.xpath("//td[contains(text(), '" + identifier + "')]/..//button[contains(@class, 'btn-success')]"));
    }

    protected void clickDelete(String identifier) {
        ensureElementIsVisible(identifier);

        click(By.xpath("//td[contains(text(), '" + identifier + "')]/..//button[contains(@class, 'btn-danger')]"));
        click(By.xpath("//div[contains(@class, 'modal-footer')]//button[contains(text(), 'Да')]"));

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//td[contains(text(), '" + identifier + "')]")));
    }

    private void click(By loc) {
        Objects.requireNonNull(wait.until(ExpectedConditions.elementToBeClickable(loc))).click();
    }
}
