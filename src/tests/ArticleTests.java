package tests;

import org.junit.Test;
import org.openqa.selenium.By;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MainPageObject;
import lib.ui.SearchPageObject;

public class ArticleTests extends CoreTestCase {

    @Test
    public void testCompareArticleTitle() {
        SearchPageObject Search = new SearchPageObject(driver);
        Search.initSearchInput();
        Search.typeSearchLine("java");
        Search.clickByArticleWithSubstringByDescription("Object-oriented programming language");

        ArticlePageObject Article = new ArticlePageObject(driver);
        String article_title = Article.getArticleTitle();

        assertEquals(
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
        Search.clickByArticleWithSubstringByDescription("Automation for Apps");

        ArticlePageObject Article = new ArticlePageObject(driver);
        Article.waitForTitleElement();
        Article.swipeToFooter();
    }

    @Test
    public void testVisibilityArticlesAfterSearchAndClear() {
        SearchPageObject Search = new SearchPageObject(driver);
        Search.initSearchInput();
        Search.typeSearchLine("java");
        ArticlePageObject Article = new ArticlePageObject(driver);
        Search.waitForSearchResultByTitle("Java");

        assertTrue("No one articles after search",Article.getAmountOfArticles() > 0);

        Search.clickCancelButton();
        Search.waitForSearchResultContainsText("Search and read the free encyclopedia in your language");
    }

    @Test
    public void testCheckArticlesContainsSearchWord() {
        SearchPageObject Search = new SearchPageObject(driver);
        Search.initSearchInput();
        String search_line = "java";
        Search.typeSearchLine(search_line);
        for(String name_of_articles : Search.getTitleListOfArticlesContainsSearchWord()) {
            assertTrue(
                    name_of_articles + " attribute doesn't contain '" + search_line + "'",
                    name_of_articles.contains(search_line));
        }
    }

    @Test
    public void testCheckArticleTitleElement() {

        SearchPageObject Search = new SearchPageObject(driver);
        Search.initSearchInput();
        Search.typeSearchLine("java");
        Search.clickByArticleWithSubstringByDescription("Object-oriented programming language");
        ArticlePageObject Article = new ArticlePageObject(driver);
        Article.waitForTitleElement();
    }
}
