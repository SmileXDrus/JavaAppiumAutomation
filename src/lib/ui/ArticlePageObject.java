package lib.ui;

import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;

public class ArticlePageObject extends MainPageObject {
    private static final String
            TITLE = "id:org.wikipedia:id/view_page_title_text",
            FOOTER_ELEMENT = "xpath://*[@text='View page in browser']",
            OPTIONS_BUTTON = "xpath://android.widget.ImageView[@content-desc='More options']",
            OPTIONS_ADD_TO_MY_LIST_BUTTON = "xpath://*[@text='Add to reading list']",
            ADD_TO_MY_LIST_OVERLAY = "id:org.wikipedia:id/onboarding_button",
            MY_LIST_NAME_INPUT = "id:org.wikipedia:id/text_input",
            MY_LIST_OK_BUTTON = "id:android:id/button1",
            CLOSE_ARTICLE_BUTTON = "xpath://android.widget.ImageButton[@content-desc='Navigate up']",
            CLICK_TO_EXISTING_FOLDER_IN_MY_LIST = "xpath://*[@text='{SUBSTRING}']",
            COUNT_OF_ARTICLES = "xpath://android.widget.ListView/android.widget.LinearLayout";

    public ArticlePageObject(AppiumDriver<?> driver) {
        super(driver);
    }

    /* TEMPLATES METHODS */
    private static String getNameFolderString(String substring) {
        return CLICK_TO_EXISTING_FOLDER_IN_MY_LIST.replace("{SUBSTRING}", substring);
    }
    /*/ TEMPLATES METHODS /*/

    public WebElement waitForTitleElement() {
        return this.waitForElementPresent(TITLE, "Cannot find article title on the page", 15);
    }

    public String getArticleTitle() {
        WebElement title_element = waitForTitleElement();
        return title_element.getAttribute("text");
    }

    public void swipeToFooter() {
        this.swipeUpToFindElement(
                FOOTER_ELEMENT,
                "Cannot find the end of the article",
                15
        );
    }

    public void addArticleToMyList(String name_of_folder) {
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find 'more option button'",
                5
        );
        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add article to reading list",
                5
        );
        this.waitForElementAndClick(
                ADD_TO_MY_LIST_OVERLAY,
                "Cannot find 'Got id' button",
                5
        );
        this.waitForElementAndClear(
                MY_LIST_NAME_INPUT,
                "Cannot find text input for set name",
                5
        );

        this.waitForElementAndSendKeys(
                MY_LIST_NAME_INPUT,
                name_of_folder,
                "Cannot put text for set name",
                5
        );
        this.waitForElementAndClick(
                MY_LIST_OK_BUTTON,
                "Cannot press OK button",
                5
        );
    }

    public void addArticleToMyListInExistingFolder(String name_of_folder) {
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find 'more option button'",
                5
        );
        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add article to reading list",
                5
        );
        this.waitForElementAndClick(
                getNameFolderString(name_of_folder),
                "Cannot find existing folder with name " + name_of_folder,
                5
        );
    }

    public int getAmountOfArticles() {
        return this.getAmountOfElements(
                COUNT_OF_ARTICLES
        );
    }

    public void closeArticle() {
        this.waitForElementAndClick(
                CLOSE_ARTICLE_BUTTON,
                "Cannot close article, cannot find X button",
                5
        );
    }

}

