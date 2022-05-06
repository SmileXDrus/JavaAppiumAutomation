package lib.ui;

import org.openqa.selenium.By;

import io.appium.java_client.AppiumDriver;

public class MyListPageObject extends MainPageObject {

    public static final String
        FOLDER_BY_NAME_TEMPLATE = "//*[@text='{FOLDER_NAME}']",
        ARTICLE_BY_TITLE_TEMPLATE = "//*[@text='{TITLE}']";

    /* TEMPLATES METHODS */
    private static String getFolderXpathByName(String substring_1) {
        return FOLDER_BY_NAME_TEMPLATE.replace("{FOLDER_NAME}", substring_1);
    }

    private static String getSavedArticleXpathByTitle(String substring) {
        return ARTICLE_BY_TITLE_TEMPLATE.replace("{TITLE}", substring);
    }
    /*/ TEMPLATES METHODS /*/

    public MyListPageObject(AppiumDriver<?> driver) {
        super(driver);
    }

    public void openFolderByName(String name_of_folder) {
        this.waitForElementAndClick(
                By.xpath(getFolderXpathByName(name_of_folder)),
                "Cannot find created folder in My lists",
                10
        );
    }
    public void swipeByArticleToDelete(String name_of_article) {
        this.waitForArticleToAppearByTitle(name_of_article);
        this.swipeElementToLeft(
                By.xpath(getSavedArticleXpathByTitle(name_of_article)),
                "Cannot find saved article"
        );
    }

    public void waitForArticleToDissapearByTitle(String name_of_article) {
        this.waitForElementNotPresent(
                By.xpath(getSavedArticleXpathByTitle(name_of_article)),
                "Saved article still present with title " + name_of_article,
                10
        );
    }
    public void waitForArticleToAppearByTitle(String name_of_article) {
        this.waitForElementPresent(
                By.xpath(getSavedArticleXpathByTitle(name_of_article)),
                "Cannot find saved article by title",
                10
        );
    }
}
