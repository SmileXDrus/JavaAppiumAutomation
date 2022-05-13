package lib.ui;


import org.openqa.selenium.remote.RemoteWebDriver;

import lib.Platform;

abstract public class MyListsPageObject extends MainPageObject {

    protected static String
        FOLDER_BY_NAME_TEMPLATE = "xpath://*[@text='{FOLDER_NAME}']",
        ARTICLE_BY_TITLE_TEMPLATE = "xpath://*[@text='{TITLE}']";

    /* TEMPLATES METHODS */
    private static String getFolderXpathByName(String substring_1) {
        return FOLDER_BY_NAME_TEMPLATE.replace("{FOLDER_NAME}", substring_1);
    }

    private static String getSavedArticleXpathByTitle(String substring) {
        return ARTICLE_BY_TITLE_TEMPLATE.replace("{TITLE}", substring);
    }
    /*/ TEMPLATES METHODS /*/

    public MyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public void openFolderByName(String name_of_folder) {
        this.waitForElementAndClick(
                getFolderXpathByName(name_of_folder),
                "Cannot find created folder in My lists",
                10
        );
    }
    public void swipeByArticleToDelete(String name_of_article) {
        this.waitForArticleToAppearByTitle(name_of_article);
        this.swipeElementToLeft(
                getSavedArticleXpathByTitle(name_of_article),
                "Cannot find saved article"
        );
        if (Platform.getInstance().isIOS()) {
            this.clickElementToTheRightUpperCorner(
                    name_of_article,
                    "Cannot find saved article"
            );
        }
        this.waitForArticleToDissapearByTitle(name_of_article);
    }

    public void waitForArticleToDissapearByTitle(String name_of_article) {
        this.waitForElementNotPresent(
                getSavedArticleXpathByTitle(name_of_article),
                "Saved article still present with title " + name_of_article,
                10
        );
    }
    public void waitForArticleToAppearByTitle(String name_of_article) {
        this.waitForElementPresent(
                getSavedArticleXpathByTitle(name_of_article),
                "Cannot find saved article by title",
                10
        );
    }
}
