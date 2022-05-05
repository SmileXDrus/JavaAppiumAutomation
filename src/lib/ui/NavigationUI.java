package lib.ui;

import org.openqa.selenium.By;

import io.appium.java_client.AppiumDriver;

public class NavigationUI extends MainPageObject {

    private static final String
        MY_LISTS_LINK = "//android.widget.FrameLayout[@content-desc='My lists']";

    public NavigationUI (AppiumDriver<?> driver) {
        super(driver);
    }

    public void clickMyLists()
    {
        this.waitForElementAndClick(
                By.xpath(MY_LISTS_LINK),
                "Cannot find navigation button to My lists",
                5
        );
    }
}
