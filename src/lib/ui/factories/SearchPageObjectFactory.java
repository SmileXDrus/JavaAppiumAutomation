package lib.ui.factories;

import org.openqa.selenium.remote.RemoteWebDriver;

import lib.Platform;
import lib.ui.SearchPageObject;
import lib.ui.android.AndroidSearchPageObject;
import lib.ui.ios.IOSSearchPageObject;

public class SearchPageObjectFactory {
    public static SearchPageObject get(RemoteWebDriver driver){
        if (Platform.getInstance().isAndroid()) {
            return new AndroidSearchPageObject(driver);
        }
        else {
            return new IOSSearchPageObject(driver);
        }
    }
}
