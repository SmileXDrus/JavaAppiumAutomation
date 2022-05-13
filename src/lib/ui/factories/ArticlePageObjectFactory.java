package lib.ui.factories;

import org.openqa.selenium.remote.RemoteWebDriver;

import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.android.AndroidArticlePageObject;
import lib.ui.ios.IOSArticlePageObject;

// pattern factory https://javarush.ru/groups/posts/2370-pattern-proektirovanija-factory
public class ArticlePageObjectFactory {

    public static ArticlePageObject get(RemoteWebDriver driver){
        if (Platform.getInstance().isAndroid()) {
            return new AndroidArticlePageObject(driver);
        }
        else {
            return new IOSArticlePageObject(driver);
        }
    }
}
