package tests;

import lib.Platform;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;

public class ChangeAppConditionsTests extends CoreTestCase {
    @Test
    public void testChangeScreenOrientationOnResults() {
        SearchPageObject Search = SearchPageObjectFactory.get(driver);
        Search.initSearchInput();
        Search.typeSearchLine("java");
        Search.clickByArticleWithSubstringByDescription("Object-oriented programming language");
        ArticlePageObject Article = ArticlePageObjectFactory.get(driver);
        String title_before_rotation = Article.getArticleTitle();

        //screen rotate (смена ориентации экрана)
        this.rotateScreenLandscape();

        String title_after_rotation = Article.getArticleTitle();

        assertEquals(
                "Article title have been changed after screen rotation",
                title_before_rotation,
                title_after_rotation
        );

        this.rotateScreenPortrait();

        String title_after_second_rotation = Article.getArticleTitle();

        assertEquals(
                "Article title have been changed after screen rotation",
                title_before_rotation,
                title_after_second_rotation
        );
    }

    @Test
    public void testCheckSearchArticleInBackground() {
        SearchPageObject Search = SearchPageObjectFactory.get(driver);
        Search.initSearchInput();
        Search.typeSearchLine("java");
        Search.waitForSearchResultByDescription("Object-oriented programming language");
        this.backgroundApp(2); // go app to background (ставим приложение в ожидание)
        Search.waitForSearchResultByDescription("Object-oriented programming language");
    }
}
