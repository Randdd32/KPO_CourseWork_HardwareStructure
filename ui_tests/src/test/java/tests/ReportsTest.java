package tests;

import core.BaseSeleniumTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ReportsPage;

import java.util.Objects;

public class ReportsTest extends BaseSeleniumTest {

    private void navigateToReport(String reportName) {
        click(By.id("reports-dropdown"));
        click(By.xpath("//a[contains(@class, 'dropdown-item') and text()='" + reportName + "']"));
    }

    @Test
    public void testDevicesByDateReportDisplay() {
        ReportsPage reportsPage = new ReportsPage(driver);
        navigateToReport("Устройства по дате");

        reportsPage.generateDevicesByDate("2020-01-01 00:00:00", "2026-01-01 00:00:00");

        Assert.assertTrue(reportsPage.isReportTableVisible(), "Таблица отчета не появилась!");

        String reportContent = reportsPage.getReportText();
        Assert.assertTrue(reportContent.contains("SN1593827406"), "В отчете не найдено ожидаемое устройство!");
    }

    @Test
    public void testDevicesWithStructureReportDisplay() {
        ReportsPage reportsPage = new ReportsPage(driver);
        navigateToReport("Устройства со структурой");

        String sn = "SN1593827406";
        reportsPage.generateDevicesWithStructure(sn);

        Assert.assertTrue(reportsPage.isReportTableVisible());
        String reportContent = reportsPage.getReportText();
        Assert.assertTrue(reportContent.contains(sn));
        Assert.assertTrue(reportContent.contains("Intel"), "В структуре устройства не найдены компоненты!");
    }

    @Test
    public void testLocationsWithEmployeesReportDisplay() {
        ReportsPage reportsPage = new ReportsPage(driver);
        navigateToReport("Помещения с сотрудниками");

        String location = "Склад 1";
        reportsPage.generateLocationsWithEmployees(location);

        Assert.assertTrue(reportsPage.isReportTableVisible());
        String reportContent = reportsPage.getReportText();
        Assert.assertTrue(reportContent.contains("склад 1"));
        Assert.assertTrue(reportContent.contains("Яковиченко"), "Сотрудник не найден в отчете по помещению!");
    }

    private void click(By locator) {
        Objects.requireNonNull(wait.until(ExpectedConditions.elementToBeClickable(locator))).click();
    }
}
