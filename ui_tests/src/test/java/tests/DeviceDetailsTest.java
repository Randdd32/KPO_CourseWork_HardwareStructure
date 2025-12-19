package tests;

import core.BaseSeleniumTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DeviceDetailsPage;

import java.util.Objects;

public class DeviceDetailsTest extends BaseSeleniumTest {

    @Test
    public void testDeviceDetailsAndModals() {
        DeviceDetailsPage detailsPage = new DeviceDetailsPage(driver);

        String targetSN = "SN1593827406";

        write(By.xpath("//input[@type='search']"), targetSN);
        click(By.xpath("//button[text()='Поиск']"));

        click(By.xpath("//h6[text()='" + targetSN + "']"));

        Assert.assertEquals(detailsPage.getSerialNumber(), targetSN);

        Assert.assertTrue(detailsPage.getStatus().contains("Работает"));

        detailsPage.openCharacteristics();
        Assert.assertEquals(detailsPage.getModalHeaderText(), "Характеристики модели устройства");
        detailsPage.closeModal();

        detailsPage.openStructure();
        Assert.assertEquals(detailsPage.getModalHeaderText(), "Структура устройства");
        detailsPage.closeModal();
    }

    private void write(By loc, String text) {
        Objects.requireNonNull(wait.until(ExpectedConditions.visibilityOfElementLocated(loc))).sendKeys(text);
    }

    private void click(By loc) {
        Objects.requireNonNull(wait.until(ExpectedConditions.elementToBeClickable(loc))).click();
    }
}
