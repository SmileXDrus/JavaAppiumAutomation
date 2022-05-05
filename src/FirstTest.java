import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MainPageObject;
import lib.ui.MyListPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;

import org.junit.Assert;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;

import java.util.List;

public class FirstTest extends CoreTestCase {

    private MainPageObject MainPageObject;

    protected void setUp() throws Exception {
        super.setUp();
        MainPageObject = new MainPageObject(driver);
    }

    @Test
    public void testFindStringBySearch() {
        SearchPageObject Search = new SearchPageObject(driver);
        Search.initSearchInput();
        Search.typeSearchLine("java");
        Search.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testCancelSearch() {
        SearchPageObject Search = new SearchPageObject(driver);
        Search.initSearchInput();
        Search.typeSearchLine("java");
        Search.waitForCancelButtonToAppear();
        Search.clickCancelButton(); // Clear search input
        Search.clickCancelButton(); // Close searching window
        Search.waitForCancelButtonToDisappear();
    }

    @Test
    public void testCompareArticleTitle() {
        SearchPageObject Search = new SearchPageObject(driver);
        Search.initSearchInput();
        Search.typeSearchLine("java");
        Search.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject Article = new ArticlePageObject(driver);
        String article_title = Article.getArticleTitle();

        Assert.assertEquals(
                "We see unexpected title",
                "Java (programming language)",
                article_title
        );

    }

    @Test
    public void testSwapeArticle() {
        SearchPageObject Search = new SearchPageObject(driver);
        Search.initSearchInput();
        Search.typeSearchLine("Appium");
        Search.clickByArticleWithSubstring("Automation for Apps");

        ArticlePageObject Article = new ArticlePageObject(driver);
        Article.waitForTitleElement();
        Article.swipeToFooter();
    }

    @Test
    public void testCompareTextOfTitle() {
        MainPageObject.assertElementHasText(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Search Wikipedia",
                "We see unexpected text"
        );
    }

    @Test
    public void testVisibilityArticlesAfterSearchAndClear() {
        SearchPageObject Search = new SearchPageObject(driver);
        Search.initSearchInput();
        Search.typeSearchLine("java");

        List<WebElement> elements = MainPageObject.getListOfAllElementsLocatedBy(
                By.xpath("//android.widget.ListView/android.widget.LinearLayout"),
                "No one articles after search"
                );
        Assert.assertTrue("No one articles after search",elements.size() >= 1);

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find close_btn element",
                5
        );
        MainPageObject.waitForElementPresent(
                By.xpath("//*[contains(@text,'Search and read the free encyclopedia')]"),
                "Articles presents on the page", 5
        );
    }

    @Test
    public void testSaveFirstArticleToMyList() {
        SearchPageObject Search = new SearchPageObject(driver);
        Search.initSearchInput();
        Search.typeSearchLine("java");
        Search.waitForSearchResult("Object-oriented programming language");
        Search.clickByArticleWithSubstring("Object-oriented programming language");
        ArticlePageObject Article = new ArticlePageObject(driver);
        Article.waitForTitleElement();
        String name_of_article = Article.getArticleTitle();
        String name_of_folder = "Learning programming";
        Article.addArticleToMyList(name_of_folder);
        Article.closeArticle();
        NavigationUI Navigation = new NavigationUI(driver);
        Navigation.clickMyLists();
        MyListPageObject MyList = new MyListPageObject(driver);
        MyList.openFolderByName(name_of_folder);
        MyList.swipeByArticleToDelete(name_of_article);
        MyList.waitForArticleToDissapearByTitle(name_of_article);



    }

    @Test
    public void testSaveSeveralArticleToMyListAndCheckRemoval() {
        SearchPageObject Search = new SearchPageObject(driver);
        Search.initSearchInput();
        Search.typeSearchLine("java");
        Search.clickByArticleWithSubstring("Object-oriented programming language");

        String name_of_article_1 = "Java (programming language)";

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='" + name_of_article_1 + "']"),
                "Cannot find search input",
                5
        );
        MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                10
        );
        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find 'more option button'",
                5
        );
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                5
        );
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got id' button",
                5
        );
        MainPageObject.waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find text input for set name",
                5
        );

        String name_of_folder = "Learning programming";

        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Cannot put text for set name",
                5
        );
        MainPageObject.waitForElementAndClick(
                By.id("android:id/button1"),
                "Cannot press OK button",
                5
        );
        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X button",
                5
        );

        Search.initSearchInput();
        Search.typeSearchLine("java");

        String name_of_article_2 = "JavaScript";

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='" + name_of_article_2 + "']"),
                "Cannot find search input",
                5
        );
        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find 'more option button'",
                5
        );
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                5
        );
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='" + name_of_folder + "']"),
                "Cannot save article in existing folder",
                5
        );
        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X button",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to My lists",
                5
        );
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='" + name_of_folder + "']"),
                "Cannot find created folder in My lists",
                5
        );
        MainPageObject.swipeElementToLeft(
                By.xpath("//*[@text='" + name_of_article_1 + "']"),
                "Cannot find saved article"
        );
        MainPageObject.waitForElementNotPresent(
                By.xpath("//*[@text='" + name_of_article_1 + "']"),
                "Cannot delete saved article",
                5
        );
        MainPageObject.waitForElementPresent(
                By.xpath("//*[@text='" + name_of_article_2 + "']"),
                "Cannot find second saved article",
                5
        );
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='" + name_of_article_2 + "']"),
                "Cannot find second saved article",
                5
        );
        String title = MainPageObject.waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find text attribute for view_page_title_text",
                5
        );
        Assert.assertEquals(
                "Text attribute " + title + " not equal " + name_of_article_2,
                name_of_article_2,
                title);

    }

    @Test
    public void testAmountOfNonEmptySearch() {
        SearchPageObject Search = new SearchPageObject(driver);
        Search.initSearchInput();
        String search_line = "Linkin Park Diskography";
        Search.typeSearchLine(search_line);
        int amount_of_search_results = Search.getAmountOfFoundArticles();

        Assert.assertTrue(
                "We found too few result!",
                amount_of_search_results > 0
        );
    }

    @Test
    public void testAmountOfEmptySearch() {
        SearchPageObject Search = new SearchPageObject(driver);
        Search.initSearchInput();
        String search_line = "Jgtjhntb";
        Search.typeSearchLine(search_line);
        Search.waitForEmptyResultsLabel();
        Search.assertThereIsNoResultOfSearch();

    }

    @Test
    public void testChangeScreenOrientationOnResults() {
        SearchPageObject Search = new SearchPageObject(driver);
        Search.initSearchInput();
        Search.typeSearchLine("java");
        Search.clickByArticleWithSubstring("Object-oriented programming language");
        ArticlePageObject Article = new ArticlePageObject(driver);
        String title_before_rotation = Article.getArticleTitle();

        //screen rotate (смена ориентации экрана)
        this.rotateScreenLandscape();

        String title_after_rotation = Article.getArticleTitle();

        Assert.assertEquals(
                "Article title have been changed after screen rotation",
                title_before_rotation,
                title_after_rotation
        );

        this.rotateScreenPortrait();

        String title_after_second_rotation = Article.getArticleTitle();

        Assert.assertEquals(
                "Article title have been changed after screen rotation",
                title_before_rotation,
                title_after_second_rotation
        );
    }

    @Test
    public void testCheckSearchArticleInBackground() {
        SearchPageObject Search = new SearchPageObject(driver);
        Search.initSearchInput();
        Search.typeSearchLine("java");
        Search.waitForSearchResult("Object-oriented programming language");
        this.backgroundApp(2); // go app to background (ставим приложение в ожидание)
        Search.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testCheckArticlesContainsSearchWord() {
        SearchPageObject Search = new SearchPageObject(driver);
        Search.initSearchInput();
        String search_line = "java";
        Search.typeSearchLine(search_line);

        List<WebElement> elements = MainPageObject.getListOfAllElementsLocatedBy(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']"),
                "No one articles after search"
        );

        for(int i = 0; i < elements.size(); i++) {
            String el_i = elements
                    .get(i)
                    .getAttribute("text")
                    .toLowerCase();
            Assert.assertTrue(el_i + " attribute doesn't contain '" + search_line + "'",
                    el_i.contains(search_line));
        }
    }

    @Test
    public void testCheckArticleTitleElement() {

        SearchPageObject Search = new SearchPageObject(driver);
        Search.initSearchInput();
        Search.typeSearchLine("java");
        Search.clickByArticleWithSubstring("Object-oriented programming language");

        Assert.assertTrue("Cannot find article title ('title' element)",
                MainPageObject.assertElementPresent(By.id("org.wikipedia:id/view_page_title_text"))
        );
    }


}
