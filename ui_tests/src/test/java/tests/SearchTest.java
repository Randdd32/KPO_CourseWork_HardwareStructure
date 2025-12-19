package tests;

import core.BaseSeleniumTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.SearchPage;

public class SearchTest extends BaseSeleniumTest {

    @Test
    public void testSearchAndFilters() {
        SearchPage searchPage = new SearchPage(driver);

        searchPage.searchFromNavbar("SN1593827406");

        Assert.assertTrue(searchPage.getResultsCount() > 0, "Устройство не найдено через поиск!");
        Assert.assertTrue(searchPage.getFirstCardTitle().contains("SN1593827406"));

        driver.get(readProperties.ConfigReader.getProperty("base.url") + "/search");

        int initialCount = searchPage.getResultsCount();
        searchPage.filterByManufacturer("Apple");

        int filteredCount = searchPage.getResultsCount();
        Assert.assertNotEquals(initialCount, filteredCount, "Количество результатов не изменилось после фильтрации!");

        searchPage.filterByStatusWorking();
        Assert.assertTrue(searchPage.getResultsCount() >= 0);
    }
}
