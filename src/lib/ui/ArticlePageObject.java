package lib.ui;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import lib.Platform;


abstract public class ArticlePageObject extends MainPageObject {
    protected static String
            TITLE,
            FOOTER_ELEMENT,
            OPTIONS_BUTTON,
            OPTIONS_ADD_TO_MY_LIST_BUTTON,
            ADD_TO_MY_LIST_OVERLAY,
            MY_LIST_NAME_INPUT,
            MY_LIST_OK_BUTTON,
            CLOSE_ARTICLE_BUTTON,
            CLICK_TO_EXISTING_FOLDER_IN_MY_LIST,
            COUNT_OF_ARTICLES;

    public ArticlePageObject(RemoteWebDriver driver) {
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
        if (Platform.getInstance().isAndroid()) {
            return title_element.getAttribute("text");
        }
        else
             return title_element.getAttribute("name");
    }

    public void swipeToFooter() {
        if (Platform.getInstance().isAndroid()) {
            this.swipeUpToFindElement(
                    FOOTER_ELEMENT,
                    "Cannot find the end of the article",
                    40
            );
        } else {
            this.swipeTillElementAppear(
                    FOOTER_ELEMENT,
                    "Cannot find the end of the article",
                    40
                    );
        }
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

    public void addArticlesToMySaved() {
        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add article to reading list",
                5
        );
    }

}

