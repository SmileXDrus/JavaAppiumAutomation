package lib.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import io.appium.java_client.AppiumDriver;

public class SearchPageObject extends MainPageObject {

    private static final String
        SEARCH_INIT_ELEMENT = "//android.widget.ImageView[@content-desc=\"Search Wikipedia\"]",
        SEARCH_INPUT = "//*[contains(@text,'Search…')]",
        SEARCH_CANCEL_BTN = "org.wikipedia:id/search_close_btn",
        SEARCH_DESCRIPTION_RESULT_TEMPLATE = "//*[@resource-id='org.wikipedia:id/page_list_item_description'][@text='{SUBSTRING}']",
        SEARCH_TITLE_RESULT_TEMPLATE = "//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='{SUBSTRING}']",
        SEARCH_RESULT_ELEMENT = "//*[@resource-id='org.wikipedia:id/search_results_list']" +
            "/*[@resource-id='org.wikipedia:id/page_list_item_container']",
        SEARCH_EMPTY_RESULT = "//*[@text = 'No results found']",
        SEARCH_FOR_ANY_TEXT = "//*[@text = '{TEXT}']";


    /* TEMPLATES METHODS */
    private static String getResultSearchElementByDescription(String substring) {
        return SEARCH_DESCRIPTION_RESULT_TEMPLATE.replace("{SUBSTRING}", substring);
    }

    private static String getResultSearchElementByTitle(String substring) {
        return SEARCH_TITLE_RESULT_TEMPLATE.replace("{SUBSTRING}", substring);
    }

    private static String getResultStringSearchForByText(String substring) {
        return SEARCH_FOR_ANY_TEXT.replace("{TEXT}", substring);
    }
    /*/ TEMPLATES METHODS /*/

    public void waitForSearchResultContainsText(String substring) {
        this.waitForElementPresent(By.xpath(getResultStringSearchForByText(substring)), "Cannot find search result with text: " + substring);
    }

    public SearchPageObject(AppiumDriver<?> driver) {
        super(driver);
    }

    public void initSearchInput() {
        this.waitForElementAndClick(
                By.xpath(SEARCH_INIT_ELEMENT),
                "Cannot find and click search init element",
                5);
    }

    public void typeSearchLine(String search_line)
    {
        this.waitForElementAndSendKeys(
                By.xpath(SEARCH_INPUT),
                search_line,
                "Cannot find and type for search line",
                5);
    }

    public void waitForSearchResultByDescription(String substring) {
        this.waitForElementPresent(
                By.xpath(getResultSearchElementByDescription(substring)),
                "Cannot find search result with substring: " + substring);
    }

    public void clickByArticleWithSubstringByDescription(String substring) {
        this.waitForElementAndClick(
                By.xpath(getResultSearchElementByDescription(substring)),
                "Cannot find and click search result with substring: " + substring,
                10);
    }

    public void waitForSearchResultByTitle(String substring) {
        this.waitForElementPresent(
                By.xpath(getResultSearchElementByTitle(substring)),
                "Cannot find search result with substring: " + substring);
    }

    public void clickByArticleWithSubstringByTitle(String substring) {
        String search_result_xpath = getResultSearchElementByDescription(substring);
        this.waitForElementAndClick(By.xpath(getResultSearchElementByTitle(substring)),
                "Cannot find and click search result with substring: " + substring,
                10);
    }

    public void waitForCancelButtonToAppear() {
        this.waitForElementPresent(By.id(SEARCH_CANCEL_BTN), "Cannot find search cancel button", 5);
    }

    public void waitForCancelButtonToDisappear() {
        this.waitForElementNotPresent(By.id(SEARCH_CANCEL_BTN), "Search cancel button is still present", 5);
    }

    public void clickCancelButton() {
        this.waitForElementAndClick(By.id(SEARCH_CANCEL_BTN), "Cannot find and click cancel button", 5);
    }

    public int getAmountOfFoundArticles() {
        this.waitForElementPresent(
                By.xpath(SEARCH_RESULT_ELEMENT),
                "Cannot find anything by the request",
                15
        );
        return this.getAmountOfElements(By.xpath(SEARCH_RESULT_ELEMENT));
    }

    public void waitForEmptyResultsLabel() {
        this.waitForElementPresent(
                By.xpath(SEARCH_EMPTY_RESULT),
                "Cannot find empty result label by the request",
                15
        );

    }

    public void assertThereIsNoResultOfSearch() {
        this.assertElementNotPresent(
                By.xpath(SEARCH_RESULT_ELEMENT),
                "Find some unexpected result by request"
        );
    }

    public ArrayList<String> getTitleListOfArticlesContainsSearchWord() {
        List<WebElement> elements = this.getListOfAllElementsLocatedBy(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']"),
                "No one articles after search"
        );

        ArrayList<String> list_title_of_articles = new ArrayList<>();
        for(int i = 0; i < elements.size(); i++) {
            list_title_of_articles.add(elements
                    .get(i)
                    .getAttribute("text")
                    .toLowerCase());
        }
        return list_title_of_articles;
    }
}