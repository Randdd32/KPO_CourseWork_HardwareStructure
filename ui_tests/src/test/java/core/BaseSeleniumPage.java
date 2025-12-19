package core;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Objects;

public abstract class BaseSeleniumPage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static final Logger logger = LoggerFactory.getLogger(BaseSeleniumPage.class);

    public BaseSeleniumPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected void click(By locator) {
        Objects.requireNonNull(wait.until(ExpectedConditions.elementToBeClickable(locator))).click();
    }

    protected void write(By locator, String text) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        if (el != null) {
            el.sendKeys(Keys.CONTROL + "a");
            el.sendKeys(Keys.BACK_SPACE);
            el.sendKeys(text);
        }
    }

    public String getInputValue(By locator) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

        try {
            new WebDriverWait(driver, Duration.ofSeconds(5)).until(d -> {
                String value = null;
                if (el != null) {
                    value = el.getAttribute("value");
                }
                return value != null && !value.isEmpty();
            });
        } catch (TimeoutException e) {
            logger.warn("Поле {} осталось пустым после ожидания", locator);
        }

        if (el == null) {
            return null;
        }
        return el.getAttribute("value");
    }

    protected void setCheckbox(By locator, boolean state) {
        WebElement cb = driver.findElement(locator);
        if (cb.isSelected() != state) {
            cb.click();
        }
    }

    protected void selectFromSearchable(By containerLocator, String text) {
        try {
            WebElement container = wait.until(ExpectedConditions.presenceOfElementLocated(containerLocator));
            container.click();

            WebElement input = container.findElement(By.tagName("input"));
            input.sendKeys(text);

            Thread.sleep(1500);
            input.sendKeys(Keys.ENTER);
        } catch (Exception e) {
            logger.error("Ошибка выбора в SearchableSelect для {}: {}", containerLocator, e.getMessage());
        }
    }

    protected void enterDate(By locator, String dateTime) {
        WebElement element = driver.findElement(locator);
        element.sendKeys(Keys.CONTROL + "a");
        element.sendKeys(Keys.BACK_SPACE);
        element.sendKeys(dateTime);
        element.sendKeys(Keys.ENTER);
    }
}
