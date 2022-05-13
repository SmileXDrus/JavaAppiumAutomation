package tests;

import lib.Platform;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;

public class MyListsTests extends CoreTestCase {

    private static final String
            name_of_folder = "Learning programming",
            search_line = "java";

    @Test
    public void testSaveFirstArticleToMyList() {
        SearchPageObject Search = SearchPageObjectFactory.get(driver);
        Search.initSearchInput();
        Search.typeSearchLine("java");
        Search.clickByArticleWithSubstringByDescription("Object-oriented programming language");

        ArticlePageObject Article = ArticlePageObjectFactory.get(driver);
        Article.waitForTitleElement();
        String name_of_article = Article.getArticleTitle();

        if(Platform.getInstance().isAndroid()) {
            Article.addArticleToMyList(name_of_folder);
        } else {
            Article.addArticlesToMySaved();
        }
        Article.closeArticle();

        NavigationUI Navigation = NavigationUIFactory.get(driver);
        Navigation.clickMyLists();

        MyListsPageObject MyList = MyListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            MyList.openFolderByName(name_of_folder);
        }
        MyList.swipeByArticleToDelete(name_of_article);
    }

    @Test
    public void testSaveSeveralArticleToMyListAndCheckRemoval() {
        String
                name_of_article = "Java (programming language)",
                name_of_article_for_delete = "JavaScript";

        SearchPageObject Search = SearchPageObjectFactory.get(driver);
        Search.initSearchInput();
        Search.typeSearchLine(search_line);
        Search.clickByArticleWithSubstringByTitle(name_of_article);

        ArticlePageObject Article = ArticlePageObjectFactory.get(driver);
        Article.waitForTitleElement();
        String name_of_article_before_save_in_my_list = Article.getArticleTitle();
        if(Platform.getInstance().isAndroid()) {
            Article.addArticleToMyList(name_of_folder);
        } else {
            Article.addArticlesToMySaved();
        }
        Article.closeArticle();

        Search.initSearchInput();
        Search.typeSearchLine(search_line);
        Search.clickByArticleWithSubstringByTitle(name_of_article_for_delete);
        if (Platform.getInstance().isAndroid()) {
            Article.addArticleToMyListInExistingFolder(name_of_folder);
        } else {
            Article.addArticlesToMySaved();
        }
        Article.closeArticle();

        NavigationUI Navigation = NavigationUIFactory.get(driver);
        Navigation.clickMyLists();

        MyListsPageObject MyList = MyListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            MyList.openFolderByName(name_of_folder);
        }
        MyList.swipeByArticleToDelete(name_of_article);

        MyList.waitForArticleToAppearByTitle(name_of_article);
        Search.clickByArticleWithSubstringByTitle(name_of_article);

        String title = Article.getArticleTitle();

        assertEquals(
                "Text attribute " + title + " not equal " + name_of_article_before_save_in_my_list,
                name_of_article_before_save_in_my_list,
                title);
    }

}
